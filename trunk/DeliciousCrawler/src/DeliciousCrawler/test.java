/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeliciousCrawler;

import lib.tools.Language;
import lib.tools.RegexChecking;
import model.dao.AuthorDAO;
import model.pojo.Author;
import sun.security.util.AuthResources;

/**
 *
 * @author THANHTUNG
 */
public class test {
      public static void main(String[] args) throws InterruptedException {
      AuthorDAO dao = new AuthorDAO();
      int i = AuthorDAO.nextIndex();
          System.out.println();
              
      Language lang = new Language();
      //lang.CheckSpecialCharacter();
        //lang.CheckSpecialCharacter();
    //  lang.CheckAndCleanSpecialCharacter();
      
    }
}
