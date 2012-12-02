/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.dbscan;

import java.util.List;
import model.pojo.Link;
import com.delicious.clustering.HelperLib;
import java.util.ArrayList;
/**
 *
 * @author THANHTUNG
 */
public class HelpLibDBSCAN {
    public final int UNCLASSIFIED = -1;
    public final int NOISE = -2;
    HelperLib dao = new HelperLib();
    public double[][] getSimilarityDistanceBetweenLinks(List<Link> list) {
        double[][] kq = new double[list.size()][list.size()];
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+"...");
            for (int j = 0;j<=i;j++){
                if (i==j) kq[i][j]=0;
                else {
                    kq[i][j] = dao.getDistanceBetweenLinks(list.get(i), list.get(j));
                }
            }
        }
        return kq;
    }
    public ArrayList<DBPoint> convertData(List<Link> list){
        ArrayList<DBPoint> arr = new ArrayList<>();
        for (int i =0;i<list.size();i++){
            DBPoint db = new DBPoint();
            db.setId(i);
            db.setClusterID(UNCLASSIFIED);
            arr.add(db);
        }
        return arr;
    }
    public  ArrayList<DBPoint> getRegionQuery(ArrayList<DBPoint> SetOfPoints,double[][] arrDistance,DBPoint p,double Eps) {
        //lay cac diem lan can p thoa |p->q|<=Eps
        ArrayList<DBPoint> arr = new ArrayList<>();
        arr.add(p);
        for (int i=0;i<SetOfPoints.size();i++){
            int a = p.getId();
            int b = SetOfPoints.get(i).getId();
            if (a!=b){
                if (a>b)
                {
                    if (arrDistance[a][b]<=Eps){
                        arr.add(SetOfPoints.get(i));
                    }
                }
                else 
                {
                    if (arrDistance[b][a]<=Eps){
                         arr.add(SetOfPoints.get(i));
                    }
                }
            }
        }
        return arr;
    }
    
}
