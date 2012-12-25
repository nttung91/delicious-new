/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.dbscan;

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
        List<Link> l = dao.getListLinks(1000);
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to get link " + (end - start));
        double[][] kq = readFromFile("D:/Data/1000.txt", l.size());
        //double[][] kq = calculateFromData(l);
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to calculate distance " + (start - end));
        //writeToFile(kq);
        HelpLibDBSCAN param = new HelpLibDBSCAN();
        ArrayList<DBPoint> SetOfPoints = dbdao.convertData(l);

        //distribute(SetOfPoints, kq);
       // return;

        end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to Convert data link " + (end - start));
        AlgorithmDBSCAN dbscan = new AlgorithmDBSCAN(kq);
        double Eps = 0.4;
        double temp =Eps;
        int MinPoints = 3;
        int org=MinPoints;
        dbscan.DBSCAN(SetOfPoints, Eps, MinPoints, 0);
        //Cluster noise
        //get noise points
        
        while (true) {
            //calculate last cluster id
            int max = -1;
            for (int i = 0; i < SetOfPoints.size(); i++) {
                DBPoint p = SetOfPoints.get(i);
                if (p.getClusterID() > max) {
                    max = p.getClusterID();
                }
            }
            ////
            if (Eps<=0.64) {
                Eps += 0.05;
            }
             else 
            {
                MinPoints--;
                Eps = temp;
            }
            System.out.println(Eps);
            boolean isHasNoise = false;
            ArrayList<DBPoint> SetOfNoise = new ArrayList<>();
            for (int i = 0; i < SetOfPoints.size(); i++) {
                if (SetOfPoints.get(i).getClusterID() == param.NOISE) {
                    SetOfNoise.add(SetOfPoints.get(i));
                    isHasNoise = true;
                }
            }
           
            if (SetOfNoise.size()<=MinPoints) {
                MinPoints--;
            }
            if (!isHasNoise) {
                break;
            }
            for (int i = 0; i < SetOfNoise.size(); i++) {
                SetOfNoise.get(i).setClusterID(param.UNCLASSIFIED);
            }
            //run Scan again
            dbscan.DBSCAN(SetOfNoise, Eps, MinPoints, max + 1);
            //
            //update setPoints
            for (int i = 0; i < SetOfNoise.size(); i++) {
                for (int j = 0; j < SetOfPoints.size(); j++) {
                    if (SetOfNoise.get(i).getId() == SetOfPoints.get(j).getId()) {
                        SetOfPoints.get(j).setClusterID(SetOfNoise.get(i).getClusterID());
                        break;
                    }
                }
            }
        }
        ///end ......................................
        String fout = "D:/Result" + Calendar.getInstance().getTime().getTime() + "_DBSCAN_Loop_" + l.size() + "_" + org + "_"+ temp + ".txt";
        int max = -1;
        for (int i = 0; i < SetOfPoints.size(); i++) {
            DBPoint p = SetOfPoints.get(i);
            if (p.getClusterID() > max) {
                max = p.getClusterID();
            }
        }


        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to run DBSCAN " + (start - end));
        
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        //(new ReadWriteExcelFile()).writeResultToExcelFile(SetOfPoints, l, max, fout); 
        (new ClusterSpaceDAO()).ClearAll();
        for (int i = -2; i <= max; i++) {
            System.out.println("---------------Cluster " + (i));
            int count = 0;
            ArrayList<Link> arr = new ArrayList<>();
            for (int j = 0; j < SetOfPoints.size(); j++) {
                
                if (SetOfPoints.get(j).getClusterID() == i) {
                    count++;
                    ClusterSpace cl = new ClusterSpace(new ClusterSpaceId(l.get(SetOfPoints.get(j).getId()).getLinkId(), i));
                    (new ClusterSpaceDAO()).saveOrUpdateObject(cl);
                    arr.add(l.get(SetOfPoints.get(j).getId()));
                    //System.out.printf("%s ", l.get(SetOfPoints.get(j).getId()).getUrl());
//                    List<Object[]> ll = dao.getDistinctTags(l.get(SetOfPoints.get(j).getId()));
//                    for (int k = 0; k < ll.size(); k++) {
//                        System.out.printf("%s(%.0f) ", ll.get(k)[0].toString(), Double.parseDouble(ll.get(k)[1].toString()));
//                    }
//                    System.out.println();
                }
            }
            System.out.printf("Numbers of Cluster %d is %d\n", i, count);

            if (count > 0) {
                bw.write(String.format("[Cluster] %d\n",count));
                (new DataCollect()).getMatrixDataAndWriteFile(arr, bw);
            }
        }
        System.out.println();
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to write result " + (end - start));

    }
}
