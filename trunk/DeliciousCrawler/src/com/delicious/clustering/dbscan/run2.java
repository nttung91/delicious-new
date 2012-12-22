/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.dbscan;

import com.delicious.clustering.HelperLib;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.pojo.Link;

/**
 *
 * @author THANHTUNG
 */
public class run2 {

    public static void main(String[] args) {
        HelpLibDBSCAN dbdao = new HelpLibDBSCAN();
        HelperLib dao = new HelperLib();
        List<Link> l = dao.getListLinks(100);
        ArrayList<List<Object[]>> arr = dao.getListLinkData(l);
        double min = Double.MIN_VALUE;
        int posi=0,posj=0;
        for (int i=0;i<l.size();i++){
            for (int j=0;j<i;j++){
                double val = dao.getDistanceBetweenLinks(arr, i, j);
                if (val>min){
                    min = val;
                    posi = i;
                    posj = j;
                }
                    
            }
        }
        
        double dist = dao.getDistanceBetweenLinks(arr, posi, posj);
        System.out.println(dist);
        
            List<Object[]> ll = dao.getDistinctTags(l.get(posi));
            List<Object[]> ll1 = dao.getDistinctTags(l.get(posj));
            for (int k = 0; k < ll.size(); k++) {
                System.out.print(ll.get(k)[0].toString() + "(" + ll.get(k)[1].toString() + ") ");
            }
            System.out.println();
            for (int k = 0; k < ll1.size(); k++) {
                System.out.print(ll1.get(k)[0].toString() + "(" + ll1.get(k)[1].toString() + ") ");
            }

    }
}
