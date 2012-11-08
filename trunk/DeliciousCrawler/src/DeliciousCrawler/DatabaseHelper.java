/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeliciousCrawler;

import java.util.List;
import model.pojo.Following;
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

    public static boolean isCrawled(Link li) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select count(s.author.authorId) from SaveLink s where s.link.linkId = :linkid";
        Query query = session.createQuery(hql);
        query.setParameter("linkid", li.getLinkId());
        Object obj = query.uniqueResult();
        int count = Integer.parseInt(obj.toString());
//        int total = (li.getTotalPosts()>=1000)?1000:li.getTotalPosts();
//        
//            if (count >= 100) {
//                if ((count * 1.0 / total) > 0.7) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else {
//                if (count == 0) {
//                    return false;
//                } else {
//                    return true;
//                }
//            }
        if (count>0) {
            return true;
        }
        else {
            return false;
        }
    }

    static boolean isGetFollowee(int AuthorID) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Following f where f.authorByFollower.authorId = :id";
        Query query  = session.createQuery(hql);
        query.setParameter("id", AuthorID);
        List<Following> f = query.list();
        if (f!=null && f.size()>0) {
            return true;
        }
        else {
            return false;
        }
    }
}
