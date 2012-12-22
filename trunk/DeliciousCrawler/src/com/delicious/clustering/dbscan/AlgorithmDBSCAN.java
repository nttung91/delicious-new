/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.dbscan;

import com.delicious.clustering.dbscan.HelpLibDBSCAN;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author THANHTUNG
 */
public class AlgorithmDBSCAN {

    public AlgorithmDBSCAN(double[][] arrDistance) {
        this.arrDistance = arrDistance;
    }
    HelpLibDBSCAN dao = new HelpLibDBSCAN();
    double[][] arrDistance;

    public void DBSCAN(ArrayList<DBPoint> SetOfPoints, double Eps, int MinPts,int ClusterStart) {
        //mix array
//        int[] a = new int[SetOfPoints.size()];
//        Random ran = new Random();
//        for (int i = 0; i < a.length; i++) {
//            a[i] = i;
//        }
//        for (int i = 0; i < SetOfPoints.size(); i++) {
//            int ran1 = ran.nextInt(SetOfPoints.size());
//            int ran2 = ran.nextInt(SetOfPoints.size());
//            int temp = a[ran1];
//            a[ran1] = a[ran2];
//            a[ran2] = temp;
//        }

        //SetOfPoints set all to UNCLASSIFIED
        for (int i = 0; i < SetOfPoints.size(); i++) {
            SetOfPoints.get(i).setClusterID(dao.UNCLASSIFIED);
        }
        //
        int ClusterID = ClusterStart;
        //System.out.println();
       
            
            for (int i = 0; i < SetOfPoints.size(); i++) {
                DBPoint Point = SetOfPoints.get(i);
                //  System.out.print(a[i]+" ");
                if (Point.getClusterID() == dao.UNCLASSIFIED) {
                    if (ExpandCluster(SetOfPoints, Point, ClusterID, Eps, MinPts)) {
                        ClusterID++;
                       System.out.printf("Cluster %d with Eps=%f and MinPoints = %d\n",(ClusterID-1),Eps,MinPts);
                    }

                }
            }
            //count noise
           
             
       
    }

    public double getDistance(int a, int b) {
        if (a > b) {
            return arrDistance[a][b];
        } else {
            return arrDistance[b][a];
        }
    }

    public boolean ExpandCluster(ArrayList<DBPoint> SetOfPoints, DBPoint Point, int ClusterID, double Eps, int MinPts) {
        ArrayList<DBPoint> seeds = dao.getRegionQuery(SetOfPoints, arrDistance, Point, Eps);
        if (seeds.size() < MinPts) {
            int index = SetOfPoints.indexOf(Point);
            Point.setClusterID(dao.NOISE);
            SetOfPoints.set(index, Point);
            return false;
        } else {
            //set points seeds.ClusterID  = ClusterID;
            for (int i = 0; i < seeds.size(); i++) {
                DBPoint p = seeds.get(i);
                int index = SetOfPoints.indexOf(p);
//                p.setClusterID(ClusterID);
//                seeds.set(i, p);
//                SetOfPoints.set(index, p);
                seeds.get(i).setClusterID(ClusterID);
                SetOfPoints.get(index).setClusterID(ClusterID);
            }
            //delete Point
            seeds.remove(Point);
            while (!seeds.isEmpty()) {
                DBPoint CurrentP = seeds.get(0);
                ArrayList<DBPoint> result = dao.getRegionQuery(SetOfPoints, arrDistance, CurrentP, Eps);
                if (result.size() >= MinPts) {
                    for (int i = 0; i < result.size(); i++) {
                        DBPoint resultP = result.get(i);
                        if (resultP.getClusterID() == dao.UNCLASSIFIED || resultP.getClusterID() == dao.NOISE) {
                            if (resultP.getClusterID() == dao.UNCLASSIFIED) {
                                seeds.add(resultP);
                            }
                            SetOfPoints.get(SetOfPoints.indexOf(resultP)).setClusterID(ClusterID);
                        }
                    }
                }
                seeds.remove(CurrentP);
            }
            return true;
        }
    }
}
