/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Timestamp;
import java.util.List;
import model.pojo.Link;
import model.pojo.SaveLink;
import model.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

/**
 *
 * @author THANHTUNG
 */
public class SaveLinkDAO extends ObjectDAO<SaveLink, Integer> {

    public int checkDuplicateItem(int linkID, int authorId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select count(*) from SaveLink p where p.author.authorId=:userid and p.link.linkId=:linkId";
        Query query = session.createQuery(hql);
        query.setMaxResults(1);
        query.setParameter("userid",authorId);
        query.setParameter("linkId", linkID);
        long sl = (long)query.uniqueResult();
        if (sl >0){
            return 0;

        } else {
            return -1;
        }
    }

    public static int nextIndex() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select max(obj.saveLinkId) from SaveLink obj";
        Query query = session.createQuery(hql);
        Object kq = query.uniqueResult();
        if (kq != null) {
            int maxItem = Integer.parseInt(kq.toString());
            return maxItem + 1;
        } else {
            return 1;
        }

    }

    @Override
    protected Class getPOJOClass() {
        return SaveLink.class;
    }
     public List<Object[]> getListOrdered() throws HibernateException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> list= null;
        String hql = String.format("select obj.link.linkId,obj.author.authorName from SaveLink obj order by obj.link.linkId");
        Query query = session.createQuery(hql);
        list = query.list();
        session.close();
        return list;
    }
}
