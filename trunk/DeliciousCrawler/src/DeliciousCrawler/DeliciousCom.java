/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeliciousCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.AuthorDAO;
import model.dao.LinkDAO;
import model.pojo.Author;
import model.pojo.Link;
import org.json.simple.parser.ParseException;

/**
 *
 * @author THANHTUNG
 */
public class DeliciousCom {

    public static void main(String[] args) throws InterruptedException {
        AuthorDAO dao1 = new AuthorDAO();
        List<Author> list = dao1.getList();
        for (int i = 0; i < list.size(); i++) {
            try {
                DeliciousHepler.getFollower(list.get(i));
            } catch (MalformedURLException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LinkByTag.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

//        try {
//            LinkDAO dao = new LinkDAO();
//            Link li = dao.getObject(104);
//            DeliciousHepler.getAndSaveBookmarkHistoryByLink(li);
//        } catch (ParseException ex) {
//            Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(DeliciousCom.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
