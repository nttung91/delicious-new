/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.evaluate;

import com.delicious.clustering.HelperLib;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.pojo.Link;
import model.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author THANHTUNG
 */
public class DataCollect {

    public List<Object[]> getDistinctTags(Link l) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select t.tag.tagName,count(t.tag.tagName) from TagLink t where t.saveLink.link.linkId = :linkid group by t.tag.tagName order by t.tag.tagName";
        Query query = session.createQuery(hql);
        query.setParameter("linkid", l.getLinkId());
        List<Object[]> list = query.list();
        for (int i = 0; i < list.size(); i++) {
            list.get(i)[1] = Integer.parseInt(list.get(i)[1].toString());
        }
        session.close();
        return list;
    }

    private List<String> getDistinctTagsWithoutWeight(Link l) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "select t.tag.tagName from TagLink t where t.saveLink.link.linkId = :linkid group by t.tag.tagName order by count(t.tag.tagName) desc";
        Query query = session.createQuery(hql);
        query.setParameter("linkid", l.getLinkId());
        List<String> list = query.list();
        session.close();
        return list;
    }

    private ArrayList<List<Object[]>> getListLinkData(List<Link> l) {
        ArrayList<List<Object[]>> linkdata = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            List<Object[]> list = getDistinctTags(l.get(i));

            linkdata.add(list);
        }
        return linkdata;
    }
    void writeToFile(double kq[][]) throws FileNotFoundException, IOException{
        String fout = "D:/Data" + Calendar.getInstance().getTime().getTime() + "_Vector_" + kq.length +".txt";
         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fout)));
          for (int i = 0; i < kq.length; i++) {
            for (int j = 0; j < kq[0].length; j++) {
                  bw.write(kq[i][j]+" ");
                  bw.flush();
            }
            bw.write("\n");
            bw.flush();
          }
          bw.close();
    }
    public double[][] getListVector(List<Link> list) throws FileNotFoundException, IOException {
        
        
        Set<String> set = new HashSet<>();
        ArrayList<List<Object[]>> linkdata = getListLinkData(list);
        int cout = 0;
        for (int i = 0; i < list.size(); i++) {
            List<String> l = getDistinctTagsWithoutWeight(list.get(i));
            cout += l.size();
            set.addAll(l);
        }
        System.out.printf("%d\n", cout);
        System.out.printf("%d\n", set.size());
        ArrayList<String> arr = new ArrayList<>();
        arr.addAll(set);
        double[][] kq = new double[list.size()][set.size()];
        int c = 0;
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+"....");
            for (int j = 0; j < arr.size(); j++) {
                //kiem tra contains
                for (int k = 0; k < linkdata.get(i).size(); k++) {
                    if (arr.get(j).equals(linkdata.get(i).get(k)[0].toString())) {
                        kq[i][j] = Double.parseDouble(linkdata.get(i).get(k)[1].toString());
                      
                        break;
                    }
                    //else kq[i][j]=0;
                }
               
            }
            
            
        }
        writeToFile(kq);
        return kq;
    }

    public double[][] getListVector(List<Link> list, ArrayList<String> arr) {
        Set<String> set = new HashSet<String>();
        ArrayList<List<Object[]>> linkdata = getListLinkData(list);
        int cout = 0;
        for (int i = 0; i < list.size(); i++) {
            List<String> l = getDistinctTagsWithoutWeight(list.get(i));
            cout += l.size();
            set.addAll(l);
        }
        // System.out.printf("%d\n", cout);
        //System.out.printf("%d\n", set.size());
        //arr = new ArrayList<>();
        arr.addAll(set);
        double[][] kq = new double[list.size()][set.size()];
        int c = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < arr.size(); j++) {
                //kiem tra contains
                for (int k = 0; k < linkdata.get(i).size(); k++) {
                    if (arr.get(j).equals(linkdata.get(i).get(k)[0].toString())) {
                        kq[i][j] = Double.parseDouble(linkdata.get(i).get(k)[1].toString());
                        break;
                    }
                    //else kq[i][j]=0;
                }
            }
        }


        return kq;
    }
   

    public void getMatrixData(List<Link> l) throws FileNotFoundException, IOException {
        DataCollect dao = new DataCollect();
        HelperLib help = new HelperLib();
        double[][] kq = dao.getListVector(l);



        //step 1

        // (new Visualize()).Visualize1(kq);

        ///print header
        for (int j = 0; j < kq[0].length; j++) {
            System.out.printf("%4d ", j + 1);
        }
        //System.out.printf("%s\n",kq[0].length);
        System.out.println();
        for (int i = 0; i < kq.length; i++) {
            for (int j = 0; j < kq[0].length; j++) {
                System.out.printf("%4.0f ", kq[i][j]);
            }
            System.out.println();
        }
    }

    void writeToFile(double[][] kq, BufferedWriter bw) throws FileNotFoundException, IOException {


        for (int j = 0; j < kq[0].length; j++) {
            System.out.printf("%4d ", j + 1);
        }
        bw.flush();
        for (int i = 0; i < kq.length; i++) {
            for (int j = 0; j < kq[0].length; j++) {
                // System.out.printf("%f ", kq[i][j]);
                int d = (int) kq[i][j];
                bw.write(String.format("%4d", d));
            }
            bw.write("\n");
            bw.flush();
            //System.out.println();
        }
    }

    public void getMatrixDataAndWriteFile(List<Link> l, BufferedWriter bw) throws FileNotFoundException, IOException {
        DataCollect dao = new DataCollect();
        HelperLib help = new HelperLib();
      

        ArrayList<String> Taglist = new ArrayList<>();
        double[][] kq = dao.getListVector(l, Taglist);


        //List<Object[]> list = dao.getDistinctTags(help.getListLinks(1).get(0));
        //order column

        //step 1

        (new Visualize()).VisualizeWithHeader(kq,Taglist);
        //writeToFile(kq, bw);
        ///print header
        for (int j = 0; j < Taglist.size(); j++) {
            //System.out.printf("%s><", Taglist.get(j));
            bw.write(String.format("%s><", Taglist.get(j)));
            bw.flush();
        }
        bw.flush();
         bw.write("\n");
        //System.out.printf("%s\n",kq[0].length);
       // System.out.println();
        for (int i = 0; i < kq.length; i++) {
            for (int j = 0; j < kq[0].length; j++) {
                //System.out.printf("%4.0f ", kq[i][j]);
                 bw.write(String.format("%.0f ", kq[i][j]));
            }
           // System.out.println();
             bw.write("\n");
             bw.flush();
            
        }
    }
}
