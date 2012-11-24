package DeliciousCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
public class GetLinkInfo extends Thread {

    String name;
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DeliciousCom.class);
    int order, duration, totalThread;
    LinkDAO dao = new LinkDAO();
    List<Link> l;

    public GetLinkInfo(ThreadGroup tg, int threadOrder, int duration, int totalThread) {
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
        //getLinkHistory();
        getAndSaveLinkInfoByLink(l);
    }

    public void getAndSaveLinkInfoByLink(List<Link> l) {
        int increment = 0;
        while (true) {

            int start = order * duration + increment * duration * totalThread;
            int end = start + duration;
            int i;
            for (i = start; i < end && i < l.size(); i++) {

                try {

                    if (l.get(i).getHash() != null && !l.get(i).equals("")) {
                        continue;
                    }
                    if (DeliciousHepler.getAndSaveBookmarkInfo(l.get(i)))
                    System.out.println(name + " Get info at link #" + l.get(i).getLinkId());
                    else System.out.println("Nothing");
                  //  logger.info(name + " Get info for at link #" + i);
                } catch (ParseException ex) {

                    Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {

                    Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {

                    Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (i >= l.size()) {
                return;
            }
            increment++;
        }

    }

    public static void main(String[] args) throws InterruptedException {
//        LinkDAO dao = new LinkDAO();
//        List<Link> l = dao.getListOrdered();
//        for (int i = 0; i< l.size();i++)
//        {
//                
//                try {
//
//                if (l.get(i).getHash() != null) {
//                    continue;
//                }
//                DeliciousHepler.getAndSaveBookmarkInfo(l.get(i));
//                System.out.printf(" Get info Id :%d at link #%d\n",l.get(i).getLinkId(),i);
//            } catch (ParseException ex) {
//
//                Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (MalformedURLException ex) {
//
//                Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//
//                Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }



        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parentGroup;
        while ((parentGroup = rootGroup.getParent()) != null) {
            rootGroup = parentGroup;
        }
        int duration = 1000;
        GetLinkInfo[] threads = new GetLinkInfo[2];
        for (int i = 0; i < threads.length; i++) {
            if (threads[i] == null) {

                threads[i] = new GetLinkInfo( rootGroup,i,duration,threads.length);
            }
        }
        int[] restartCount = new int[threads.length];
        boolean stopAll = false;
        int maxRetry = 10000;
        
//        while (true) {
//            Thread.sleep(60000);
//            for (int i = 0; i < threads.length; i++) {
//                if (restartCount[i] > maxRetry) {
//                    stopAll = true;
//                }
//                if (stopAll) {
//                    return;
//                }
//                if (!threads[i].isAlive()) {
//                    
//                       
//                        threads[i] = new GetLinkInfo( rootGroup,i,duration,threads.length);
//                        System.out.println("#" + threads[i].getName() + "Start again");
//                        
//                  
//                }
//            }
//
//        }
    }
}
