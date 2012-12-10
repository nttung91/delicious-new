/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.dbscan;

import java.util.ArrayList;

/**
 *
 * @author THANHTUNG
 */
public class DBSCANParameter {

    double[][] Dist;

    public DBSCANParameter(double[][] distance) {
        this.Dist = distance;
    }
    public double getEpsByErrorPercent(double errorP,int k){
        double[] arr = getKDistGraphData(k);
        int pos = (int)(arr.length*errorP);
        return  arr[pos];
    }
    public double[] getKDistGraphData(int k) {
        double[] arr = new double[Dist[0].length];
        for (int i=0;i<arr.length;i++){
            arr[i] = getDistanceToPointsNearest(i, k);
        }
        QuickSortDesc(arr, 0, arr.length-1);
        return arr;
    }
    public double getDistanceToPointsNearest(int pointsID, int kNearest) {
        double[] arr = new double[Dist[0].length];
        for (int i = 0; i < Dist[0].length; i++) {
            if (i > pointsID) {
                arr[i] = Dist[i][pointsID];
            } else {
                arr[i] = Dist[pointsID][i];
            }
        }
        QuickSort(arr, 0, arr.length-1);
//        double[] kq = new double[kNearest];
//        for (int i=0;i<kq.length;i++){
//            kq[i] = arr[i];
//        }
        return arr[kNearest];

    }

    void QuickSort(double a[], int left, int right) {
        int i, j;
        double x;
        x = a[(left + right) / 2];
        i = left;
        j = right;
        do {
            while (a[i] < x) {
                i++;
            }
            while (a[j] > x) {
                j--;
            }
            if (i <= j) {
                double temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                i++;
                j--;
            }
        } while (i < j);
        if (left < j) {
            QuickSort(a, left, j);
        }
        if (i < right) {
            QuickSort(a, i, right);
        }
    }
    void QuickSortDesc(double a[], int left, int right) {
        int i, j;
        double x;
        x = a[(left + right) / 2];
        i = left;
        j = right;
        do {
            while (a[i] > x) {
                i++;
            }
            while (a[j] < x) {
                j--;
            }
            if (i <= j) {
                double temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                i++;
                j--;
            }
        } while (i < j);
        if (left < j) {
            QuickSortDesc(a, left, j);
        }
        if (i < right) {
            QuickSortDesc(a, i, right);
        }
    }
}