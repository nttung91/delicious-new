/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.evaluate;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author THANHTUNG
 */
public class Visualize {
    void swap(double[][] arr,int i,int j){
        for (int k=0;k<arr.length;k++){
            double temp = arr[k][i];
            arr[k][i] = arr[k][j];
            arr[k][j] = temp;
        }
    }
    public void Visualize1(double[][] kq){
        double[] mul = new double[kq[0].length];
        int[] count = new int[kq[0].length];
        for (int i=0;i<mul.length;i++){
            double m =1;
            int s=0;
            for (int j=0;j<kq.length;j++){
                m=m*kq[j][i];
                if (kq[j][i]!=0) s++;
                //if (m==0) break;
            }
            mul[i]=m;
            count[i]=s;
        }
      
        
        //order
        for (int i=0;i<kq[0].length;i++){
            for (int j=i+1;j<kq[0].length;j++){
                if (mul[i]<mul[j]){
                    double temp = mul[i];
                    int temp1 = count[i];
                    mul[i]= mul[j];
                    count[i]= count[j];
                    mul[j] = temp;
                    count[j] = temp1;
                    swap(kq, i, j);
                }
            }
        }
          //step 2
        int start =0;
        for (int i=0;i<mul.length;i++){
            if (mul[i]==0){
                start=i;
                break;
            }
        }
        //System.out.println("fdsf"+start);
        for (int i=start;i<kq[0].length;i++){
            for (int j=i+1;j<kq[0].length;j++){
                if (count[i]<count[j]){
                    int temp = count[i];
                    count[i]= count[j];
                    count[j] = temp;
                    swap(kq, i, j);
                }
            }
        }
       // return kq;
    }
     public void VisualizeWithHeader(double[][] kq,ArrayList<String> header){
        double[] mul = new double[kq[0].length];
        int[] count = new int[kq[0].length];
        for (int i=0;i<mul.length;i++){
            double m =1;
            int s=0;
            for (int j=0;j<kq.length;j++){
                m=m*kq[j][i];
                if (kq[j][i]!=0) s++;
                //if (m==0) break;
            }
            mul[i]=m;
            count[i]=s;
        }
      
        
        //order
        for (int i=0;i<kq[0].length;i++){
            for (int j=i+1;j<kq[0].length;j++){
                if (mul[i]<mul[j]){
                    double temp = mul[i];
                    int temp1 = count[i];
                    mul[i]= mul[j];
                    count[i]= count[j];
                    mul[j] = temp;
                    count[j] = temp1;
                    swap(kq, i, j);
                   // System.out.println(header.get(i)+header.get(j));
                    Collections.swap(header, i, j);
                 //   System.out.println(header.get(i)+header.get(j));
                }
            }
        }
          //step 2
        int start =0;
        for (int i=0;i<mul.length;i++){
            if (mul[i]==0){
                start=i;
                break;
            }
        }
        //System.out.println("fdsf"+start);
        for (int i=start;i<kq[0].length;i++){
            for (int j=i+1;j<kq[0].length;j++){
                if (count[i]<count[j]){
                    int temp = count[i];
                    count[i]= count[j];
                    count[j] = temp;
                    swap(kq, i, j);
                    Collections.swap(header, i, j);
                }
            }
        }
       // return kq;
    }
}
