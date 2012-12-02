/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.dbscan;

import com.delicious.clustering.dbscan.HelpLibDBSCAN;
import java.util.ArrayList;

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

    public void DBSCAN(ArrayList<DBPoint> SetOfPoints, double Eps, int MinPts) {
        //SetOfPoints set all to UNCLASSIFI
        int ClusterID = 0;
        for (int i = 0; i < SetOfPoints.size(); i++) {
            DBPoint Point = SetOfPoints.get(i);
            if (Point.getClusterID() == dao.UNCLASSIFIED) {
                if (ExpandCluster(SetOfPoints, Point, ClusterID, Eps, MinPts)) {
                    ClusterID++;
                }

            }
        }
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
                p.setClusterID(ClusterID);
                seeds.set(i, p);
                SetOfPoints.set(index, p);
            }
            //delete Point
            seeds.remove(Point);
            while (!seeds.isEmpty()) {
                DBPoint CurrentP = seeds.get(0);
                ArrayList<DBPoint> result = dao.getRegionQuery(SetOfPoints, arrDistance, CurrentP, Eps);
                if (result.size() >= MinPts) {
                    for (int i = 0; i < result.size(); i++) {
                        DBPoint resultP = result.get(i);
                        if (resultP.getClusterID()==dao.UNCLASSIFIED || resultP.getClusterID()==dao.NOISE){
                            if (resultP.getClusterID()==dao.UNCLASSIFIED){
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
