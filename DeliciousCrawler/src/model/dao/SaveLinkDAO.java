/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Timestamp;
import java.util.List;
import model.pojo.SaveLink;
import model.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.classic.Session;

/**
 *
 * @author THANHTUNG
 */
public class SaveLinkDAO extends ObjectDAO<SaveLink, Integer> {

    public int checkDuplicateItem(int linkID, int authorId, Timestamp date) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from SaveLink p where p.author.authorId=:userid and p.link.linkId=:linkId";
        Query query = session.createQuery(hql);
        query.setParameter("userid",authorId);
        query.setParameter("linkId", linkID);
        SaveLink sl = (SaveLink)query.uniqueResult();
        if (sl != null){
            if (date.compareTo(sl.getDateSave()) > 0) {
                return sl.getSaveLinkId();
            } else {
                //cu hon
                return -2;
            }

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
}
