package DeliciousCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.*;
import model.pojo.*;
import org.json.simple.parser.ParseException;

/**
 *
 * @author THANHTUNG
 */
public class GetLinkHistory extends Thread {

    String name;
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DeliciousCom.class);
    int order, duration, totalThread;
    LinkDAO dao = new LinkDAO();
    List<Link> l;

    public GetLinkHistory(ThreadGroup tg, int threadOrder, int duration, int totalThread) {
        super(tg, "Thread #" + (threadOrder + 1));
        name = "Thread #" + (threadOrder + 1);
        System.out.println("Reading......link............");
        l = dao.getListOrdered();
        System.out.println("Read completely !!");
        order = threadOrder;
        this.duration = duration;
        this.totalThread = totalThread;
        logger.info(String.format("%s started.\n", name));
        System.out.printf("%s started.\n", name);
        start();
    }

    @Override
    public void run() {

        getLinkHistory(l);
    }

    public void getLinkHistory(List<Link> list) {
        int increment = 0;
        while (true) {
            int start = order * duration + increment * duration * totalThread;
            int end = start + duration;
            int i;
            for (i = start; i < end && i<list.size(); i++) {
               
                if (DatabaseHelper.isCrawled(list.get(i))) 
                {
                    continue;
                }
                try {
                 
                    DeliciousHepler.getAndSaveBookmarkHistoryByLink(list.get(i));
                  
                } catch (ParseException ex) {
                    Logger.getLogger(GetLinkHistory.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GetLinkHistory.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GetLinkHistory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (i >= l.size()) {
                return;
            }
            increment++;
        }
    }
 public static void main(String[] args) throws InterruptedException {

        // getLinkHistory();
//        //run only one thread
//        List<String> list = DatabaseHelper.getMostPopularTag(1000);
//        LinkByTag lbt = new LinkByTag("Main",1000);
//        lbt.getRecentBookmarkByTag(list,"main",1,300);

        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parentGroup;
        while ((parentGroup = rootGroup.getParent()) != null) {
            rootGroup = parentGroup;
        }

        GetLinkHistory[] threads = new GetLinkHistory[2];
        for (int i = 0; i < threads.length; i++) {
            if (threads[i] == null) {
                threads[i] = new GetLinkHistory(rootGroup,i,100,threads.length);

            }
        }
        int[] restartCount = new int[threads.length];
        boolean stopAll = false;
        int maxRetry = 10000;
        while (true) {
            Thread.sleep(60000);
            for (int i = 0; i < threads.length; i++) {
                if (restartCount[i] > maxRetry) {
                    stopAll = true;
                }
                if (stopAll) {
                    return;
                }
                if (!threads[i].isAlive()) {
                    Thread.sleep(3000);
                    threads[i] = new GetLinkHistory(rootGroup,i,100,threads.length);
                    System.out.println("#" + threads[i].getName() + "Start again");
                    restartCount[i]++;
                }
            }

        }
    }
}
