/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import model.pojo.Author;
import model.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.classic.Session;

/**
 *
 * @author THANHTUNG
 */
public class AuthorDAO extends  ObjectDAO<Author, Integer> {

    @Override
    protected Class getPOJOClass() {
        throw new UnsupportedOperationException("Not supported yet.");
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
