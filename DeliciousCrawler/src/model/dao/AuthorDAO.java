/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.pojo.Author;
import model.pojo.Link;
import model.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

/**
 *
 * @author THANHTUNG
 */
public class AuthorDAO extends  ObjectDAO<Author, Integer> {

    @Override
    protected Class getPOJOClass() {
        
        return Author.class;
    }
     public static int nextIndex(){
         Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select max(obj.authorId) from Author obj";
        Query query = session.createQuery(hql);
        Object kq = query.uniqueResult();
        
        if (kq!=null){
        int maxItem = Integer.parseInt(kq.toString());
        session.close();
        return maxItem+1;
        }
        else {
            session.close();
            return 1;
        }
        
     }
     public static int nextIndexForFollowee(){
         Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select max(obj.authorId) from Author obj";
        Query query = session.createQuery(hql);
        Object kq = query.uniqueResult();
        
        if (kq!=null){
        int maxItem = Integer.parseInt(kq.toString());
        session.close();
        return maxItem+2;
        }
        else {
            session.close();
            return 1;
        }
        
     }
      public List<Author> getListFiltered() throws HibernateException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Author> list= null;
        String hql = String.format("select obj from Author obj  where obj.isFollowed = :follow order by obj.authorId ");
        Query query = session.createQuery(hql);
        query.setParameter("follow",1);
        list = query.list();
        session.close();
        return list;
    }
      public List<Author> getListOrdered() throws HibernateException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Author> list= null;
        String hql = String.format("select obj from Author obj order by obj.authorId ");
        Query query = session.createQuery(hql);

        list = query.list();
        session.close();
        return list;
    }
      public Author getObjectByName(String name){
         Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Author obj where obj.authorName=:name";
        Query query = session.createQuery(hql);
        query.setParameter("name", name);
        Author kq = (Author)query.uniqueResult();
        session.close();
        return  kq;
     }
}
