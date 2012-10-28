/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeliciousCrawler;

import java.util.List;
import model.pojo.Link;
import model.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author THANHTUNG
 */
public class DatabaseHelper {
    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DeliciousCom.class);
    public static List<String> getMostPopularTag(int count) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select t.tagName from TagCollect t group by  t.tagName having count(*) > 10 order by count(t.id) desc";
        Query query = session.createQuery(hql);
       query.setMaxResults(count);
        List<String> l = query.list();
       
        session.close();
        return l;
    }
    public static boolean isCrawled(Link li){
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select count(s.author.authorId) from SaveLink s where s.link.linkId = :linkid";
        Query query = session.createQuery(hql);
        query.setParameter("linkid", li.getLinkId());
        Object obj = query.uniqueResult();
        if (Integer.parseInt(obj.toString())>0) {
            return true;
        }
        else {
            return false;
        }
    }
}
