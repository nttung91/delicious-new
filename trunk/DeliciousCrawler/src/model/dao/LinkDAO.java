/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.pojo.Link;
import model.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

/**
 *
 * @author THANHTUNG
 */
public class LinkDAO extends ObjectDAO<Link, Integer> {
     
       public static int nextIndex(){
         Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select max(obj.linkId) from Link obj";
        Query query = session.createQuery(hql);
        Object kq = query.uniqueResult();
        
        if (kq!=null){
        int maxItem = Integer.parseInt(kq.toString());
        session.close();
        return maxItem+1;
        }
        else {
            return 1;
        }
        
     }
     public int processDuplicate(String url){
         //if has a doc that exist in database it'll delete that doc and reference object 
         //check dup
         Session session = HibernateUtil.getSessionFactory().openSession();
         String hql = "from Link d where d.url = :url";
         Query query = session.createQuery(hql);
         query.setParameter("url",url );
         Link doc = (Link)query.uniqueResult();
         //if dup
         session.close();
         
         if (doc != null){
//              LinkDAO docdao =new LinkDAO();
//              docdao.deleteObject(doc);
              return doc.getLinkId();//inform dup
         }
         else {
             return -1; //inform not dup
         }
         
     }
      
      public List<Link> getListOrdered() throws HibernateException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Link> list= null;
        String hql = String.format("select obj from Link obj order by obj.linkId");
        Query query = session.createQuery(hql);
        query.setFirstResult(400000);
        list = query.list();
        session.close();
        return list;
    }
      public List<Link> getListUrl(int start) throws HibernateException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Link> list= null;
        String hql = String.format("select obj from Link obj where obj.linkId > :val order by obj.linkId");
        Query query = session.createQuery(hql);
        query.setParameter("val", start);
        list = query.list();
        session.close();
        return list;
    }
    @Override
    protected Class getPOJOClass() {
        return Link.class;
    }
    
}
