/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.dbscan;

import com.delicious.clustering.HelperLib;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
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

    public static double[][] readFromFile(String fin, int dim) throws FileNotFoundException, IOException {
        double[][] kq = new double[dim][dim];


        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fin)));
        int line = 0;
        while (br.ready()) {
            String s = br.readLine();
            String[] str = s.split(" ");
            for (int j = 0; j < str.length; j++) {
                kq[line][j] = Double.parseDouble(str[j]);
            }
            line++;
        }
        return kq;
    }

    public static double[][] calculateFromData(List<Link> l) {
        HelpLibDBSCAN dbdao = new HelpLibDBSCAN();
        HelperLib dao = new HelperLib();
        ArrayList<List<Object[]>> LinkData = dao.getListLinkData(l);
        double[][] kq = dbdao.getSimilarityDistanceBetweenLinks(LinkData);
        return kq;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        HelpLibDBSCAN dbdao = new HelpLibDBSCAN();
        HelperLib dao = new HelperLib();
        long start = Calendar.getInstance().getTimeInMillis();
        List<Link> l = dao.getListLinks(1000);
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to get link " + (end - start));
        double[][] kq = readFromFile("D:/distance1354571484353.txt", l.size());
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to calculate distance " + (start - end));
        String fout = "D:/distance" + Calendar.getInstance().getTime().getTime() + "-" + l.size() + ".txt";
        DBSCANParameter param = new DBSCANParameter(kq);
//        for (int i=2;i<50;i++)
//        {
        double Eps = param.getEpsByErrorPercent(0.4, 4);
//        System.out.printf("K= %d -> Eps = %f\n",i,Eps);
//        }

//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fout)));
//        for (int i = 0; i < kq[0].length; i++) {
//            for (int j = 0; j <= i; j++) {
//                System.out.printf("%f ", kq[i][j]);
//               bw.write(kq[i][j]+" ");
//
//            }
//            bw.write("\n");
//            bw.flush();
//            System.out.println();
//        }
        ArrayList<DBPoint> SetOfPoints = dbdao.convertData(l);
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to Convert data link " + (end - start));
//        ArrayList<DBPoint> region = dbdao.getRegionQuery(SetOfPoints, kq, SetOfPoints.get(3), 0.8);
//        for (int i = 0; i < region.size(); i++) {
//            if (SetOfPoints.get(3).getId() > region.get(i).getId()) {
//                System.out.printf("%d - %f "ssssssssssssssssssssssx, region.get(i).getId(), kq[SetOfPoints.get(3).getId()][region.get(i).getId()]);
//            } else {
//                System.out.printf("%d - %f ", region.get(i).getId(), kq[region.get(i).getId()][SetOfPoints.get(3).getId()]);
//            }
//        }

        AlgorithmDBSCAN dbscan = new AlgorithmDBSCAN(kq);
      
            dbscan.DBSCAN(SetOfPoints, Eps, 4);
            int max = -1;
            for (int i = 0; i < SetOfPoints.size(); i++) {
                DBPoint p = SetOfPoints.get(i);
                if (p.getClusterID() > max) {
                    max = p.getClusterID();
                }
            }
            start = Calendar.getInstance().getTimeInMillis();
            System.out.println("Time to run DBSCAN " + (start - end));
            System.out.printf("Eps = %4f => Number of Cluster %d\n", Eps, max);
        
      //  System.out.printf("Clustered %d Cluster\n", result.size());
        for (int i = -2; i <= max; i++) {
            System.out.println("---------------Cluster " + (i));
            int count = 0;
            for (int j = 0; j < SetOfPoints.size(); j++) {
                if (SetOfPoints.get(j).getClusterID() == i) {
                    count++;
                    System.out.print(l.get(SetOfPoints.get(j).getId()).getUrl() + " ");;
                    List<Object[]> ll = dao.getDistinctTags(l.get(SetOfPoints.get(j).getId()));
                    for (int k = 0; k < ll.size(); k++) {
                        System.out.print(ll.get(k)[0].toString() + "(" + ll.get(k)[1].toString() + ") ");
                    }

                    System.out.println();
                }
            }
            System.out.printf("Numbers of Cluster %d is %d\n", i, count);
        }
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to write result " + (end - start));


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
