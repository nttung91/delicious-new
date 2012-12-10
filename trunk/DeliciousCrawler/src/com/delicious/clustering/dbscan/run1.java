/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.dbscan;

import java.util.Random;

/**
 *
 * @author THANHTUNG
 */
public class run1 {
     public static void main(String[] args) {
         DBSCANParameter d = new DBSCANParameter(null);
         double a[] = new double[]{1.0,10.0,1.0,7.0,3.0,1.0,5.0,3.0};
         d.QuickSortDesc(a, 0, a.length-1);
         for (int i=0;i<a.length;i++){
             System.out.printf("%10f",a[i]);
         }
            
     }
     
}
