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
    int start;
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DeliciousCom.class);
    int state;
    int peroid;
    int startPos;
    boolean isEndWithoutError;
    LinkDAO dao = new LinkDAO();
    List<Link> l = dao.getListOrdered();

    public GetLinkInfo(int startPos, int threadNumber, int currentPosition, int numberOfThread, ThreadGroup tg) {
        super(tg, "Thread #" + (threadNumber + 1));
        this.state = 0;
        this.name = "Thread #" + (threadNumber + 1);
        this.startPos = startPos;
        //this.peroid = l.size()/numberOfThread+1;
        this.peroid = 1000;
        this.start = threadNumber * this.peroid + currentPosition;
        
        logger.info(String.format("%s started.\n", name));
        System.out.printf("%s started.\n", name);
        start();
    }

    @Override
    public void run() {
        //getLinkHistory();
        getAndSaveLinkInfoByLink(l, start, start + peroid);
    }

    public void getAndSaveLinkInfoByLink(List<Link> l, int start, int end) {
        System.out.println("Reading......link............");
        System.out.println("Read completely !!");
        for (int i = startPos + start; i < startPos + end && i < l.size(); i++) {
            try {
                if (l.get(i).getLinkId() < startPos + start) {
                    continue;
                }
                if (l.get(i).getHash() != null) {
                    continue;
                }
                DeliciousHepler.getAndSaveBookmarkInfo(l.get(i));
                System.out.println(name + " Get info at link #" + i);
                logger.info(name + " Get info for at link #" +i);
            } catch (ParseException ex) {
                isEndWithoutError = false;
                Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                isEndWithoutError = false;
                Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                isEndWithoutError = false;
                Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isEndWithoutError = true;
    }

    public void getLinkInfoByBookmark() {
        System.out.println("Reading......link............");
        LinkDAO dao = new LinkDAO();
        int start = 0;
        List<Link> list = dao.getListUrl(start);
        for (int i = 0; i < list.size(); i++) {
            try {
                DeliciousHepler.getAndSaveBookmarkInfo(list.get(i).getUrl());
                logger.info("Get info for link have id =#" + list.get(i).getLinkId());
            } catch (ParseException ex) {
                Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GetLinkInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

     

        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parentGroup;
        while ((parentGroup = rootGroup.getParent()) != null) {
            rootGroup = parentGroup;
        }
        int ContinueAt = 125000;
        GetLinkInfo[] threads = new GetLinkInfo[3];
        for (int i = 0; i < threads.length; i++) {
            if (threads[i] == null) {
               
                threads[i] = new GetLinkInfo(ContinueAt, i, 0, threads.length, rootGroup);
               
                Thread.sleep(5000);
            }
        }
        int[] restartCount = new int[threads.length];
        boolean stopAll = false;
        int maxRetry = 10000;
          int newStartPos=0;
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
                    int current = threads[i].state;
                    //System.out.println("cf" + current);
                    if (threads[i].isEndWithoutError) {
                       newStartPos = threads.length * threads[i].peroid + threads[i].startPos;
                        threads[i] = new GetLinkInfo(newStartPos, i, 0, threads.length, rootGroup);
                         
                        System.out.println("#" + threads[i].getName() + "Start again with new loop!");
                    } else {
                        newStartPos = threads[i].startPos;
                        threads[i] = new GetLinkInfo(newStartPos, i, current, threads.length, rootGroup);
                        System.out.println("#" + threads[i].getName() + "Start again");
                        restartCount[i]++;
                    }
                }
            }

        }
    }
}
