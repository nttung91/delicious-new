/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.method;

import com.delicious.clustering.dbscan.*;
import com.delicious.clustering.HelperLib;
import com.delicious.clustering.evaluate.DataCollect;
import com.delicious.clustering.evaluate.ReadWriteExcelFile;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.dao.ClusterSpaceDAO;
import model.pojo.ClusterSpace;
import model.pojo.ClusterSpaceId;
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
    public static double[][] readFromFile1(String fin,int len) throws FileNotFoundException, IOException {
        double[][] kq = new double[len][];


        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fin)));
        int line = 0;
        while (br.ready()) {
            String s = br.readLine();
            String[] str = s.split(" ");
            kq[line] = new double[str.length];
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

    static void writeToFile(double[][] kq) throws FileNotFoundException, IOException {
        String fout = "D:/distance" + Calendar.getInstance().getTime().getTime() + "-" + kq[0].length + ".txt";
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fout)));
        for (int i = 0; i < kq[0].length; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.printf("%f ", kq[i][j]);
                bw.write(kq[i][j] + " ");
            }
            bw.write("\n");
            bw.flush();
            System.out.println();
        }
    }

    static void distribute(ArrayList<DBPoint> SetOfPoints, double[][] kq) {
        AlgorithmDBSCAN dbscan = new AlgorithmDBSCAN(kq);
        for (int d = -1; d < 14; d++) {
            System.out.printf("%5.2f", 0.2 + 0.05 * d);
        }
        System.out.println();
        for (int k = 2; k < 10; k++) {
            System.out.printf("%5d", k);
            for (int d = 0; d < 14; d++) {
                dbscan.DBSCAN(SetOfPoints, 0.2 + 0.05 * d, k, 0);
                int max = -1;
                for (int i = 0; i < SetOfPoints.size(); i++) {
                    DBPoint p = SetOfPoints.get(i);
                    if (p.getClusterID() > max) {
                        max = p.getClusterID();
                    }
                }
                System.out.printf("%5d", max + 1);

            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        HelpLibDBSCAN dbdao = new HelpLibDBSCAN();
        HelperLib dao = new HelperLib();
        long start = Calendar.getInstance().getTimeInMillis();
        List<Link> l = dao.getListLinks(400);
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to get link " + (end - start));
        double[][] kq = readFromFile("D:/Data/400.txt", l.size());
        //double[][] kq = calculateFromData(l);

        //writeToFile(kq);

        ArrayList<DBPoint> SetOfPoints = dbdao.convertData(l);
        //
        
        double[][] arrVector = (new DataCollect()).getListVector(l);
        System.out.println("Reading file");
       // double[][] arrVector =  readFromFile1("D:/Data1356155981040_Vector_1000.txt",l.size());
        System.out.println("Read file complete");
        double Eps = 0.65;
        MyMethod method = new MyMethod(kq, arrVector);
        method.Algorithm(SetOfPoints, Eps);

        String fout = "D:/Result" + Calendar.getInstance().getTime().getTime() + "_MeThod_" + l.size() + "_" + Eps + ".txt";
        int max = -1;
        for (int i = 0; i < SetOfPoints.size(); i++) {
            DBPoint p = SetOfPoints.get(i);
            if (p.getClusterID() > max) {
                max = p.getClusterID();
            }
        }
      


      (new ClusterSpaceDAO()).ClearAll();
         //FileOutputStream fos = new FileOutputStream(fout);
           // BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (int i = -2; i <= max; i++) {
            System.out.println("---------------Cluster " + (i));
            int count = 0;
            ArrayList<Link> arr = new ArrayList<>();
            for (int j = 0; j < SetOfPoints.size(); j++) {

                if (SetOfPoints.get(j).getClusterID() == i) {
                    count++;
                     ClusterSpace cl = new ClusterSpace(new ClusterSpaceId(l.get(SetOfPoints.get(j).getId()).getLinkId(), i));
                    (new ClusterSpaceDAO()).saveOrUpdateObject(cl);
                   // arr.add(l.get(SetOfPoints.get(j).getId()));
//                    System.out.printf("%s ", l.get(SetOfPoints.get(j).getId()).getUrl());
//                    List<Object[]> ll = dao.getDistinctTags(l.get(SetOfPoints.get(j).getId()));
//                    for (int k = 0; k < ll.size(); k++) {
//                        System.out.printf("%s(%.0f) ", ll.get(k)[0].toString(), Double.parseDouble(ll.get(k)[1].toString()));
//                    }
//                    System.out.println();
                }
            }
            System.out.printf("Numbers of Cluster %d is %d\n", i, count);
            if (count > 0) {
               // bw.write(String.format("[Cluster] %d\n",count));
             //   (new DataCollect()).getMatrixDataAndWriteFile(arr, bw);
            }
        }
    }
}
