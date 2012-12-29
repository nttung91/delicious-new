/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.pojo.ClusterSpace;
import model.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author THANHTUNG
 */
public class ClusterSpaceDAO  extends ObjectDAO<ClusterSpace, Integer> {

    @Override
    protected Class getPOJOClass() {
       return  ClusterSpace.class;
    }
    public List<ClusterSpace> getClusterIdByLinkID(int linkId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = String.format("select obj.id.clusterId from ClusterSpace obj where obj.id.link = :linkid");
        Query query = session.createQuery(hql);
        query.setParameter("linkid",linkId);
        Object obj = query.uniqueResult();
        
        if (obj!=null){
            int ClID = (int)obj;
           
            hql = "from ClusterSpace obj where obj.id.clusterId = :clId";
            query = session.createQuery(hql);
            query.setParameter("clId", ClID);
            List<ClusterSpace> arr = query.list();            
            session.close();
             
            return arr;
        }
        else {
            return null;
        }
        
    }
    public void ClearAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String hql = "delete from ClusterSpace where id.clusterId>=0";
        Query query = session.createQuery(hql);
        query.executeUpdate();
        tx.commit();
        session.close();
        
    }
    
}
