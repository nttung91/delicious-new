/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeliciousCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.LinkDAO;
import model.pojo.Link;
import org.json.simple.parser.ParseException;


/**
 *
 * @author THANHTUNG
 */
public class DeliciousCom extends Thread {

    String name;
    int delay;
    List<String> list=null;
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DeliciousCom.class);
    public DeliciousCom(String name, int delay,ThreadGroup tg) {
        super(tg,name);
        this.delay = delay;
        this.name = name;
         list = DatabaseHelper.getMostPopularTag(100000);
         logger.info(String.format("%s started.\n", name));
        System.out.printf("%s started.\n", name);
        start();
    }

    @Override
    public void run() {

      // getRecentBookmarkByTag(list,name);
       getLinkHistory();
    }

    public void getRecentBookmarkByTag(List<String> list,String threadname) {
        ArrayList<String> l = null;
       
        Random random = new Random();

        try {
            while (true) {
                int j = random.nextInt(list.size());
                try {
                    l = DeliciousHepler.getRecentListBookmarkByTag(list.get(j), 1000);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (l != null && l.size() > 0) {
                    int count =0;
                    for (int i = 0; i < l.size(); i++) {
                        //System.out.println(threadname+" -- #" + i);
                        try {
                          if (DeliciousHepler.getAndSaveBookmarkInfo(l.get(i))) count++;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                     logger.info(String.format(threadname+" -- So Link lay dc tu tag #%s: %d/%d", list.get(j),count,l.size()));
                     System.out.println(String.format(threadname+" -- So Link lay dc tu tag #%s: %d/%d", list.get(j),count,l.size()));
                     System.out.println(Calendar.getInstance().getTime().toString());
                }
            }
        } catch (ParseException ex) {
            System.out.println(threadname+" -------------------------------Thread end-------------------------");
            Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(threadname+" -------------------------------Thread end-------------------------");
    }
    public void getRecentTag() throws InterruptedException{
         int i = 10;
        int delaytime = 50;
        while (true){
            try {
                delaytime +=DeliciousHepler.getRecentTag();
            } catch (MalformedURLException ex) {
                Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
            }
               
              System.out.println("Pause "+delaytime+" sec.........................");
              Thread.sleep(delaytime*1000);
        }
    }
    public void getLinkHistory(){
        System.out.println("Reading......links............");
        
        LinkDAO dao = new LinkDAO();
        List<Link> list =dao.getListOrdered();
        System.out.println("Lay xong ds link!");
        for (int i=0;i<list.size();i++){
            try {
                try {
                    DeliciousHepler.getAndSaveBookmarkHistoryByLink(list.get(i));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                } catch (IOException ex) {
                    Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            } catch (ParseException ex) {
                Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        
         // getLinkHistory();
//        
        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parentGroup;
        while ((parentGroup = rootGroup.getParent()) != null) {
            rootGroup = parentGroup;
        }
         
         DeliciousCom[] threads = new DeliciousCom[1];
          for (int i = 0;i<threads.length;i++){
                    if (threads[i]==null) {
                        threads[i] = new DeliciousCom("Thread #"+(i+1),0, rootGroup);
                    }
                }
        int[] restartCount = new int[threads.length];
        boolean stopAll = false;
        int maxRetry = 100;
        while (true){
                for (int i = 0;i<threads.length;i++){
                    if (restartCount[i]>maxRetry) {
                        stopAll = true;
                    }
                    if (stopAll) {
                        return;
                    }
                    if (!threads[i].isAlive()) {
                        Thread.sleep(0);
                        threads[i] = new DeliciousCom("Thread #"+(i+1),0, rootGroup);
                        System.out.println("#"+threads[i].getName()+"Start again");
                        restartCount[i]++;
                    }
                }
                
        }
    }
}
