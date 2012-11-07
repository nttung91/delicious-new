/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.pojo.SaveLink;
import model.pojo.TagLink;
import model.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;



/**
 *
 * @author THANHTUNG
 */
public class TagLinkDAO extends ObjectDAO<TagLink, Integer> {

    @Override
    protected Class getPOJOClass() {
        return TagLink.class;
    }
     public List<Object[]> getListOrdered() throws HibernateException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> list= null;
        String hql = String.format("select obj.saveLink.link.linkId,obj.saveLink.author.authorName,obj.tag.tagName from TagLink obj order by obj.saveLink.link.linkId");
        Query query = session.createQuery(hql);
        list = query.list();
        session.close();
        return list;
    }
    
    
    
}
