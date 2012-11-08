package DeliciousCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.*;
import model.pojo.*;

/**
 *
 * @author THANHTUNG
 */
public class GetFollowerByAuthor extends Thread {

    String name;
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DeliciousCom.class);
    int order, duration, totalThread;
    AuthorDAO dao = new AuthorDAO();
    List<Author> l;

    public GetFollowerByAuthor(ThreadGroup tg, int threadOrder, int duration, int totalThread) {
        super(tg, "Thread #" + (threadOrder + 1));
        name = "Thread #" + (threadOrder + 1);
        System.out.println("Reading......link............");
        l = dao.getListFiltered();
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
        getAndSaveFollowerInfo(l);
    }

    public void getAndSaveFollowerInfo(List<Author> list) {
        AuthorDAO dao = new AuthorDAO();
        int increment = 0;
        while (true) {

            int start = order * duration + increment * duration * totalThread;
            int end = start + duration;
            int i;

            for (i = start; i < end && i< list.size(); i++) {
                //System.out.println(name +" - " +i);
                try {
                    if (!DatabaseHelper.isGetFollowee(list.get(i).getAuthorId())){
                   //     System.out.println(name +" - getting -"+i);
                    DeliciousHepler.getFollower(list.get(i));
                    logger.info(String.format("Save follower of #%d:%s", list.get(i).getAuthorId(), list.get(i).getAuthorName()));
                    }
                    } catch (MalformedURLException ex) {
                    Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (i >= l.size()) {
                return;
            }
            increment++;
        }
    }

  

    public static void main(String[] args) throws InterruptedException {



        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parentGroup;
        while ((parentGroup = rootGroup.getParent()) != null) {
            rootGroup = parentGroup;
        }
        int duration = 1000;
        GetFollowerByAuthor[] threads = new GetFollowerByAuthor[3];
        for (int i = 0; i < threads.length; i++) {
            if (threads[i] == null) {

                threads[i] = new GetFollowerByAuthor(rootGroup, i, duration, threads.length);
                
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


                    threads[i] = new GetFollowerByAuthor(rootGroup, i, duration, threads.length);
                    System.out.println("#" + threads[i].getName() + "Start again");


                }
            }

        }
    }
}
