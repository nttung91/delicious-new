/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.dbscan;

import com.delicious.clustering.HelperLib;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.pojo.Link;

/**
 *
 * @author THANHTUNG
 */
public class run {

    public static void a(List<DBPoint> b) {
        b.get(0).setId(-1);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        HelpLibDBSCAN dbdao = new HelpLibDBSCAN();
        HelperLib dao = new HelperLib();
        List<Link> l = dao.getListLinks(200);
        double[][] kq = new double[l.size()][l.size()]; //dbdao.getSimilarityDistanceBetweenLinks(l);
        String fin  = "D:/StudyMaterial/TieuLuanChuyenNganh/200Link100Distance.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fin)) );
        int line=0;
        while (br.ready()){
            String s = br.readLine();
            String[] str = s.split(" ");
            for (int j=0;j<str.length;j++){
                kq[line][j] = Double.parseDouble(str[j]);
            }
            line++;
        }
        for (int i = 0; i < kq[0].length; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.printf("%f ", kq[i][j]);

            }
            System.out.println();
        }
        ArrayList<DBPoint> SetOfPoints = dbdao.convertData(l);
        ArrayList<DBPoint> region = dbdao.getRegionQuery(SetOfPoints, kq, SetOfPoints.get(3), 0.8);
        for (int i = 0; i < region.size(); i++) {
            if (SetOfPoints.get(3).getId() > region.get(i).getId()) {
                System.out.printf("%d - %f ", region.get(i).getId(), kq[SetOfPoints.get(3).getId()][region.get(i).getId()]);
            } else {
                System.out.printf("%d - %f ", region.get(i).getId(), kq[region.get(i).getId()][SetOfPoints.get(3).getId()]);
            }
        }
        AlgorithmDBSCAN dbscan = new AlgorithmDBSCAN(kq);
        dbscan.DBSCAN(SetOfPoints, 0.65, 1);
        int max =-1;
        for (int i = 0; i < SetOfPoints.size(); i++) {
            DBPoint p = SetOfPoints.get(i);
            if (p.getClusterID()>max) {
                max = p.getClusterID();
            }
        }
        System.out.printf("Number of Cluster %d\n",max);
        //System.out.printf("Clustered %d Cluster\n", result.size());
        for (int i = -2; i <= max; i++) {
            System.out.println("---------------Cluster " + (i));
            int count = 0;
            for (int j = 0; j < SetOfPoints.size(); j++) {
                if (SetOfPoints.get(j).getClusterID() == i) {
                    count++;
                    List<Object[]> ll = dao.getDistinctTags(l.get(SetOfPoints.get(j).getId()));
                    for (int k = 0; k < ll.size(); k++) {
                        System.out.print(ll.get(k)[0].toString() + "(" + ll.get(k)[1].toString() + ") ");
                    }
                    System.out.println();
                }
            }
            System.out.printf("Numbers of Cluster %d is %d\n",i,count);
        }
//        ArrayList<DBPoint> a = new ArrayList<>();
//        DBPoint p = new DBPoint();
//        p.setId(0);
//        p.setClusterID(0);
//        a.add(p);
//        p = new DBPoint();
//        p.setId(2);
//        p.setClusterID(0);
//        a.add(p);
//        DBPoint pp = new DBPoint();
//        pp.setClusterID(0);
//        pp.setId(2);
//        System.out.println(a.get(a.indexOf(pp)).getId());



    }
}
