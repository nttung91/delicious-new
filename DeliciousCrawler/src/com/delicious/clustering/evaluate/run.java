/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.evaluate;

import com.delicious.clustering.HelperLib;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author THANHTUNG
 */
public class run {

    static void swap(double[][] arr,int i,int j){
        for (int k=0;k<arr.length;k++){
            double temp = arr[k][i];
            arr[k][i] = arr[k][j];
            arr[k][j] = temp;
        }
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        DataCollect dao = new DataCollect();
        HelperLib help = new HelperLib();

        double[][] kq = dao.getListVector(help.getListLinks(3));
        
        
        //List<Object[]> list = dao.getDistinctTags(help.getListLinks(1).get(0));
        //order column
        
        //step 1
        
        (new Visualize()).Visualize1(kq);
       
      ///print header
          for (int j = 0; j < kq[0].length; j++) {
              System.out.printf("%4d ",j+1);
          }
          //System.out.printf("%s\n",kq[0].length);
          System.out.println();
        for (int i = 0; i < kq.length; i++) {
            for (int j = 0; j < kq[0].length; j++) {
                System.out.printf("%4.0f ",kq[i][j]);
            }
            System.out.println();
        }
    }
}
