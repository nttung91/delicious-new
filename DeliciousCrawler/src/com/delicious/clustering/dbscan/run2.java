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
        List<Link> l = dao.getListLinks(2);
        ArrayList<List<Object[]>> arr = dao.getListLinkData(l);
        double dist = dao.getDistanceBetweenLinks(arr, 0, 1);
        System.out.println(dist);
        for (int i = 0; i < l.size(); i++) {
            List<Object[]> ll = dao.getDistinctTags(l.get(i));
            for (int k = 0; k < ll.size(); k++) {
                System.out.print(ll.get(k)[0].toString() + "(" + ll.get(k)[1].toString() + ") ");
            }
            System.out.println();
        }

    }
}
