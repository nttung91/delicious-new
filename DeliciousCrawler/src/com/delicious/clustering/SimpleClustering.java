/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering;

import java.util.ArrayList;
import java.util.List;
import model.pojo.Link;
import com.delicious.clustering.HelperLib;
import java.util.Calendar;

/**
 *
 * @author THANHTUNG
 */
public class SimpleClustering {
    public SimpleClustering(double distance){
        this.distance = distance;
    }
    double distance;

    public void run(List<Link> list) {
        ArrayList<ArrayList<Link>> result = new ArrayList<>();
        //duyet cac link
        HelperLib dao = new HelperLib();
        for (int i = 0; i < list.size(); i++) {
            long start = Calendar.getInstance().getTimeInMillis();
            System.out.println(i + "......");
            if (result.isEmpty()) {
                ArrayList<Link> l = new ArrayList<>();
                l.add(list.get(i));
                result.add(l);
            } else {
                //khoang cach tu 1 link toi cac cum da co
                ArrayList<Double> arrDistance = new ArrayList<>();
                //duyet cac cluster
                System.out.println("Cluster size "+result.size());
                for (int j = 0; j < result.size(); j++) {
                    double dist = dao.getDistanceBetweenLinkAndCluster(list.get(i), result.get(j));
                    arrDistance.add(dist);
                }
                //chọn cum có khoảng cách gần nhất
                int c = 0;
                double min = arrDistance.get(0);
                for (int k = 1; k < arrDistance.size(); k++) {
                    if (arrDistance.get(k) < min) {
                        min = arrDistance.get(k);
                        c = k;
                    }
                }
                //khoang cach nam trong muc cho phep
                if (min <= distance) {
                    result.get(c).add(list.get(i));
                    System.out.printf("Add %d into Cluster %d : Distance = %f\n", list.get(i).getLinkId(), c, min);
                } else {
                    ArrayList<Link> l = new ArrayList<>();
                    l.add(list.get(i));
                    result.add(l);
                   System.out.printf("Add %d into new Cluster %d\n", list.get(i).getLinkId(),result.size());
                }              
            }
            long end = Calendar.getInstance().getTimeInMillis();
            System.out.printf("Time running %d is %d\n",i,end -start);
        }
        System.out.printf("Clustered %d Cluster\n", result.size());
        for (int i = 0; i < result.size(); i++) {
            System.out.println("---------------Cluster " + (i + 1));
            for (int j = 0; j < result.get(i).size(); j++) {
                List<Object[]> ll = dao.getDistinctTags(result.get(i).get(j));
                for (int k = 0; k < ll.size(); k++) {
                    System.out.print(ll.get(k)[0].toString()+"("+ll.get(k)[1].toString()+")");
                }
                System.out.println();
            }
        }
    }
    //tinh vector dai dien cho 1 cluster
    public void run2(List<Link> list) {
        ArrayList<ArrayList<Link>> result = new ArrayList<>();
        ArrayList<int[]> VectorCentroid = new ArrayList<>();
        //duyet cac link
        HelperLib dao = new HelperLib();
        for (int i = 0; i < list.size(); i++) {
            long start = Calendar.getInstance().getTimeInMillis();
            System.out.println(i + "......");
            //neu cac cluster hien tai ko có thì thêm vào cluster mới
            if (result.isEmpty()) {
                ArrayList<Link> l = new ArrayList<>();
                l.add(list.get(i));
                result.add(l);
            } else {
                //khoang cach tu 1 link toi cac cum da co
                ArrayList<Double> arrDistance = new ArrayList<>();
                //duyet cac cluster
                System.out.println("Cluster size "+result.size());
                for (int j = 0; j < result.size(); j++) {
                    double dist = dao.getDistanceBetweenLinkAndCluster(list.get(i), result.get(j));
                    arrDistance.add(dist);
                }
                //chọn cum có khoảng cách gần nhất
                int c = 0;
                double min = arrDistance.get(0);
                for (int k = 1; k < arrDistance.size(); k++) {
                    if (arrDistance.get(k) < min) {
                        min = arrDistance.get(k);
                        c = k;
                    }
                }
                //khoang cach nam trong muc cho phep
                if (min <= distance) {
                    result.get(c).add(list.get(i));
                    System.out.printf("Add %d into Cluster %d : Distance = %f", list.get(i).getLinkId(), c, min);
                } else {
                    ArrayList<Link> l = new ArrayList<>();
                    l.add(list.get(i));
                    result.add(l);
                   System.out.printf("Add %d into new Cluster %d", list.get(i).getLinkId(),result.size());
                }
                
            }
            long end = Calendar.getInstance().getTimeInMillis();
            System.out.printf("Time running %d is %d\n",i,end -start);
        }
        System.out.printf("Clustered %d Cluster\n", result.size());
        for (int i = 0; i < result.size(); i++) {
            System.out.println("---------------Cluster " + (i + 1));
            for (int j = 0; j < result.get(i).size(); j++) {
                List<Object[]> ll = dao.getDistinctTags(result.get(i).get(j));
                for (int k = 0; k < ll.size(); k++) {
                    System.out.println(ll.get(i)[0].toString()+"("+ll.get(i)[1].toString()+")");
                }
                System.out.println();
            }
        }
    }
}
