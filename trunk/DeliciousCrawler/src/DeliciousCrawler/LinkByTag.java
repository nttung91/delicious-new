
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
public class LinkByTag extends Thread {

    String name;
    int start;
    List<String> list=null;
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DeliciousCom.class);
    int state;
    int peroid;
    public LinkByTag(String name,int thread,int currentPosition,int numberOfThread,ThreadGroup tg) {
        super(tg,name);
        this.state =0;
        this.name = name;
        list = DatabaseHelper.getMostPopularTag(500);
        this.peroid = list.size()/numberOfThread+1;
        this.start = thread * this.peroid+currentPosition;
        logger.info(String.format("%s started.\n", name));
        System.out.printf("%s started.\n", name);
        start();
    }
     public LinkByTag(String name,int count) {
        super(name);
       
        this.name = name;
         list = DatabaseHelper.getMostPopularTag(count);
         logger.info(String.format("%s started.\n", name));
        System.out.printf("%s started.\n", name);
        start();
    }
    @Override
    public void run() {

       getRecentBookmarkByTag(list,name,start,start+peroid);
       //getLinkHistory();
       
   
    }

    public void getRecentBookmarkByTag(List<String> list,String threadname,int start,int end) {
        ArrayList<String> l = null;
       
       // Random random = new Random();
        int j=0;
        try {
            for (j=start;j<=end && j<list.size();j++) {
         //       int j = random.nextInt(list.size());
                if (list.get(j).contains(" ")) continue;
            //    Thread.currentThread().sleep(500);
                l = DeliciousHepler.getPopularListBookmarkByTag(list.get(j), 1000);
                
                if (l != null && l.size() > 0) {
                    int count =0;
                    for (int i = 0; i < l.size(); i++) {
                        //System.out.println(threadname+" -- #" + i);
                        try {
                          if (DeliciousHepler.getAndSaveBookmarkOnly(l.get(i))) count++;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                     logger.info(String.format(threadname+" -- So Link lay dc tu tag #%d %s: %d/%d",j,list.get(j),count,l.size()));
                     System.out.println(String.format(threadname+" -- So Link lay dc tu tag #%d %s: %d/%d",j, list.get(j),count,l.size()));
                }
            }
        } catch (MalformedURLException ex) {
             this.state = j;
               ex.printStackTrace();
            Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
             this.state = j;
               ex.printStackTrace();
            Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
        } catch ( ParseException ex) {
            this.state = j;
            System.out.println(threadname+" -------------------------------Thread end-------------------------");
            ex.printStackTrace();
            Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(threadname+" -------------------------------Thread end no error-------------------------");
    }
    public void getLinkHistory(){
        System.out.println("Reading......link............");
        
        LinkDAO dao = new LinkDAO();
        List<Link> list =dao.getListOrdered();
        System.out.println("Lay xong ds Link!");
        for (int i=0;i<list.size();i++){
            try {
                DeliciousHepler.getAndSaveBookmarkHistoryByLink(list.get(i));
            } catch (ParseException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void getAndSaveLinkInfo(List<Link> l,int start,int end){
        System.out.println("Reading......link............");
        
       
        
         System.out.println("Read completely !!");
         for (int i=start;i<end && i<l.size();i++){
            try {
                if (l.get(i).getLinkId()< start) continue;
                if (!l.get(i).getHash().equals("")) continue;
                DeliciousHepler.getAndSaveBookmarkInfo(l.get(i));
                System.out.println("Get info for link have id =#"+l.get(i).getLinkId());
                logger.info("Get info for link have id =#"+l.get(i).getLinkId());
            } catch (ParseException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }
    public void getLinkInfo(){
         System.out.println("Reading......link............");
         LinkDAO dao = new LinkDAO();
         int start =0;
         List<Link> list =dao.getListUrl(start);
         for (int i=0;i<list.size();i++){
            try {
                DeliciousHepler.getAndSaveBookmarkInfo(list.get(i).getUrl());
                logger.info("Get info for link have id =#"+list.get(i).getLinkId());
            } catch (ParseException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }
    public void getAndSaveFollowerInfo(){
        AuthorDAO dao = new AuthorDAO();
        
        List<Author> list = dao.getList();
        for (int i=0;i<list.size();i++){
            try {
                DeliciousHepler.getFollower(list.get(i));
                logger.info(String.format("Save follower of #%d:%s",list.get(i).getAuthorId(),list.get(i).getAuthorName()));
            } catch (MalformedURLException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        
         LinkByTag[] threads = new LinkByTag[3];
          for (int i = 0;i<threads.length;i++){
                    if (threads[i]==null) {
                        threads[i] = new LinkByTag("Thread #"+(i+1),i,0,threads.length, rootGroup);
                        
                    }
                }
        int[] restartCount = new int[threads.length];
        boolean stopAll = false;
        int maxRetry = 10000;
        while (true){
               Thread.sleep(60000);
                for (int i = 0;i<threads.length;i++){
                    if (restartCount[i]>maxRetry) {
                        stopAll = true;
                    }
                    if (stopAll) {
                        return;
                    }
                    if (!threads[i].isAlive()) {
                        Thread.sleep(3000);
                        int current = threads[i].state;
                        System.out.println("cf" + current);
                        threads[i] = new LinkByTag("Thread #"+(i+1),i,current,threads.length, rootGroup);
                        System.out.println("#"+threads[i].getName()+"Start again");
                        restartCount[i]++;
                    }
                }
                
        }
    }
}
