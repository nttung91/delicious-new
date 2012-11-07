/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.io.Serializable;
import java.util.List;
import model.pojo.Following;
import model.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

/**
 *
 * @author THANHTUNG
 */
public class FollowingDAO extends ObjectDAO<Following,Integer> {

    @Override
    protected Class getPOJOClass() {
       return Following.class;
    }
     public List<Object[]> getListOrdered() throws HibernateException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> list= null;
        String hql = String.format("select  obj.authorByFollowee.authorName, obj.authorByFollower.authorName from Following obj order by obj.authorByFollowee.authorId");
        Query query = session.createQuery(hql);
        list = query.list();
        session.close();
        return list;
    }
}
