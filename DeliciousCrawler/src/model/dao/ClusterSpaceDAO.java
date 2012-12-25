/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.io.Serializable;
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
