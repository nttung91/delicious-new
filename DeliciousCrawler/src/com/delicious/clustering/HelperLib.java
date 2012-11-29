/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.pojo.Author;
import model.pojo.Link;
import model.pojo.Tag;
import model.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author THANHTUNG
 */
public class HelperLib {

    public List<Object[]> getPopularTagsByLink(Link l) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select t.tag.tagName,count(t.tag.tagName) from TagLink t where t.saveLink.link.linkId = :linkid group by t.tag.tagName order by count(t.tag.tagName) desc";
        Query query = session.createQuery(hql);
        query.setParameter("linkid", l.getLinkId());
        List<Object[]> list = query.list();
        session.close();
        return list;
    }

    public List<Object[]> getDistinctTags(Link l) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select t.tag.tagName,count(t.tag.tagName) from TagLink t where t.saveLink.link.linkId = :linkid group by t.tag.tagName order by count(t.tag.tagName) desc";
        Query query = session.createQuery(hql);
        query.setParameter("linkid", l.getLinkId());
        List<Object[]> list = query.list();
        session.close();
        return list;
    }

    public double getAffinityBetweenUserAndTag(String a, String t) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select count(*) from TagLink tl where tl.tag.tagName=:tName and tl.saveLink.author.authorName = :aName";
        Query query = session.createQuery(hql);
        query.setParameter("aName", a);
        query.setParameter("tName", t);
        long obj = (long) query.uniqueResult();
        if (obj == 0) {
            return 0;
        }
        hql = "select count(*) from SaveLink sl where sl.author.authorName = :aName";

        query = session.createQuery(hql);
        query.setParameter("aName", a);

        //   query.setParameter("aName",a);

        long obj1 = (long) query.uniqueResult();
        if (obj1 == 0) {
            return 0;
        }
        double d = obj * 1.0 / obj1;
        return d;

    }

    public List<Link> getListLinks() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Link l where l.totalPosts>:limit";
        Query query = session.createQuery(hql);
        query.setParameter("limit", 100);
        query.setMaxResults(100);
        List<Link> l = query.list();
        session.close();
        return l;

    }

    public ArrayList<String> getTagSetByLinks(Link l1, Link l2) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select t.tag.tagName from TagLink t where t.saveLink.link.linkId = :linkid1 or t.saveLink.link.linkId = :linkid2  group by t.tag.tagName order by count(t.tag.tagName) desc";
        Query query = session.createQuery(hql);
        query.setParameter("linkid1", l1.getLinkId());
        query.setParameter("linkid2", l2.getLinkId());
        List<String> list = query.list();
        ArrayList<String> arr = new ArrayList<>();
        arr.addAll(list);
        session.close();
        return arr;
    }

    public int[] convertToVector(ArrayList<String> listAll, List<Object[]> list) {
        int[] arr = new int[listAll.size()];
        for (int i = 0; i < listAll.size(); i++) {
            for (int j=0;j<list.size();j++){
                if (list.get(j)[0].equals(listAll.get(i))){
                    arr[i] = Integer.parseInt(list.get(j)[1].toString());
                    break;
                }
            }
        }
        return arr;
    }

    public double calculateCosi(int[] a, int[] b) {
        //tinh tich vo huong
        int sum = 0;
        double lena = 0;
        double lenb = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
            lena += a[i] * a[i];//do dai cua a
            lenb += b[i] * b[i];//do dai cua b
        }
        double kq = 0;
        if (lena * lenb == 0) {
            return 0;
        }
        kq = sum / (Math.sqrt(lena) * Math.sqrt(lenb));
        return (1 - kq);
    }

    public double getDistanceBetweenLinks(Link l1, Link l2) {
        ArrayList<String> set = getTagSetByLinks(l1, l2);
        int[] a = convertToVector(set, getDistinctTags(l1));
        int[] b = convertToVector(set, getDistinctTags(l2));

        return calculateCosi(a, b);
    }

    public double getDistanceBetweenLinkAndCluster(Link l1, ArrayList<Link> cluster) {
        double avg = 0;
        if (cluster.size() == 0) {
            return 0;
        }
        for (int i = 0; i < cluster.size(); i++) {
            avg += getDistanceBetweenLinks(l1, cluster.get(i));
        }
        return avg / cluster.size();

    }
}
