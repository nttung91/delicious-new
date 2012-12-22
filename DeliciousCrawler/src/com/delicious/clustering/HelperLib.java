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
    //lấy tags và % số lượng lần tag được sử dụng trên link
    public List<Object[]> getDistinctTags(Link l) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql1 = "select count(*) from SaveLink sl where sl.link.linkId = :linkid1";
         Query query = session.createQuery(hql1);
         query.setParameter("linkid1", l.getLinkId());
         long count = (long)query.uniqueResult();
         
         
        String hql = "select t.tag.tagName,count(t.tag.tagName) from TagLink t where t.saveLink.link.linkId = :linkid group by t.tag.tagName order by count(t.tag.tagName) desc";
        query = session.createQuery(hql);
        query.setParameter("linkid", l.getLinkId());
        List<Object[]> list = query.list();
        for (int i=0;i<list.size();i++){
            list.get(i)[1] = Integer.parseInt(list.get(i)[1].toString())*1.0/1; 
        }
        session.close();
        return list;
    }
    //lấy dữ liệu cho các link
    public ArrayList<List<Object[]>> getListLinkData(List<Link> l) {
        ArrayList<List<Object[]>> LinkData = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            System.out.printf("Getting Tag set for link %d\n", i);
            LinkData.add(getDistinctTags(l.get(i)));
        }
        return LinkData;
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
    
    public List<Link> getListLinks(int limit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Link l where l.totalPosts>:limit order by l.linkId";
        Query query = session.createQuery(hql);
        query.setParameter("limit", 100);
     //   query.setFirstResult(100);
        query.setMaxResults(limit);
        List<Link> l = query.list();
        session.close();
        return l;
    }
    public ArrayList<String> getTagSetByLinks(ArrayList<List<Object[]>> LinkData, int l1, int l2) {
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < LinkData.get(l1).size(); i++) {
            List<Object[]> list = LinkData.get(l1);
            arr.add(list.get(i)[0].toString());
        }
        for (int i = 0; i < LinkData.get(l2).size(); i++) {
            List<Object[]> list = LinkData.get(l2);
            if (!arr.contains(list.get(i)[0].toString())) {
                arr.add(list.get(i)[0].toString());
            }
        }
        return arr;
    }
    //chu
    public double[] convertToVector(ArrayList<String> listAll, List<Object[]> list) {
        double[] arr = new double[listAll.size()];
        for (int i = 0; i < listAll.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j)[0].equals(listAll.get(i))) {
                    arr[i] = Double.parseDouble(list.get(j)[1].toString());
                    break;
                }
            }
        }
        return arr;
    }

    public double calculateCosi(double[] a, double[] b) {
        //tinh tich vo huong
        double sum = 0;
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

    public double getDistanceBetweenLinks(ArrayList<List<Object[]>> LinkData, int l1, int l2) {
        ArrayList<String> set = getTagSetByLinks(LinkData, l1, l2);
        double[] a = convertToVector(set, LinkData.get(l1));
        double[] b = convertToVector(set, LinkData.get(l2));

        return calculateCosi(a, b);
    }

    public double getDistanceBetweenLinkAndCluster(ArrayList<List<Object[]>> LinkData, Link l1, ArrayList<Link> cluster) {
        double avg = 0;
//        if (cluster.isEmpty()) {
//            return 0;
//        }
//        for (int i = 0; i < cluster.size(); i++) {
//            avg += getDistanceBetweenLinks(LinkData,l1,cluster.get(i));
//        }
        return avg / cluster.size();
    }

    public double[] getRepresentativeVectorByCluster(ArrayList<Link> cluster, int numberOfDim) {
        double[] d = new double[numberOfDim];
        for (int i = 0; i < cluster.size(); i++) {
            //int[] a = 
        }
        return null;
    }
}
