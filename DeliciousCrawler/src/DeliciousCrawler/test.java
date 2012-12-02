/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeliciousCrawler;

import com.delicious.clustering.HelperLib;
import com.delicious.clustering.SimpleClustering;
import java.util.List;
import java.util.Random;
import model.dao.AuthorDAO;
import model.dao.LinkDAO;
import model.dao.TagDAO;
import model.pojo.Link;
import org.json.simple.parser.ParseException;

/**
 *
 * @author THANHTUNG
 */
public class test {

    public static void main(String[] args) throws InterruptedException, ParseException {
          HelperLib dao = new HelperLib();
          LinkDAO ldao = new LinkDAO();
          AuthorDAO adao = new AuthorDAO();
          TagDAO tdao = new TagDAO();
          Random ran = new Random();
          int id = Math.abs(ran.nextInt()%400000);
          List<Link> l = dao.getListLinks(200);
          
//          long start = Calendar.getInstance().getTimeInMillis();
//          System.out.println(dao.getDistanceBetweenLinks(l.get(0),l.get(99)));
//          long end = Calendar.getInstance().getTimeInMillis();
//          System.out.println(end -start);
          
          //Iterator<String> it = set.iterator();
//          while (it.hasNext()){
//              System.out.println(it.next());
//          }
          System.out.println(l.size());
          SimpleClustering sc = new SimpleClustering(0.7);
          sc.run(l);
          
//            ArrayList<String> str = dao.getTagSetByLinks(l.get(0), l.get(1));
//            for(String s:str){
//                System.out.println(s);
//            }
//         
        

    }
}
