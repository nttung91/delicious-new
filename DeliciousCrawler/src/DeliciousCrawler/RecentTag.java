/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeliciousCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author THANHTUNG
 */
public class RecentTag extends Thread{


    boolean isContinue;
    String name;
    public RecentTag(String name,ThreadGroup tg) {
        super(tg, name);
        isContinue = true;
        this.name = name;
       
    }
    @Override
    public void run(){
        try {
            getRecentTag();
        } catch (InterruptedException ex) {
            Logger.getLogger(RecentTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean isIsContinue() {
        return isContinue;
    }

    public void setIsContinue(boolean isContinue) {
        this.isContinue = isContinue;
    }
    public void getRecentTag() throws InterruptedException{
        int delaytime = 50;
        while (isContinue){
            try {
                delaytime +=DeliciousHepler.getRecentTag();
            } catch (MalformedURLException ex) {
                Logger.getLogger(RecentTag.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RecentTag.class.getName()).log(Level.SEVERE, null, ex);
            }
               
              System.out.println("Pause "+delaytime+" sec.........................");
              Thread.sleep(delaytime*1000);
        }
    }
    
}
