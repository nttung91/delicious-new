/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.method;

import com.delicious.clustering.dbscan.DBPoint;
import java.util.ArrayList;

/**
 *
 * @author THANHTUNG
 */
public class MyMethod {

    public final int UNCLASSIFIED = -1;
    double[][] arrDistance;
    double[][] arrVetor;

    public MyMethod(double[][] arrDistance, double[][] arrVetor) {
        this.arrDistance = arrDistance;
        this.arrVetor = arrVetor;
    }

    public void Algorithm(ArrayList<DBPoint> SetOfPoints, double Eps) {
        //dat cac diem la chua xet
        for (int i = 0; i < SetOfPoints.size(); i++) {
            SetOfPoints.get(i).setClusterID(UNCLASSIFIED);
        }
        //B1. thiet lap cac cum
        ArrayList<Integer> clusterCount = new ArrayList<>();
        int ClusterID = 0;
        for (int i = 0; i < SetOfPoints.size(); i++) {
            //kiem tra xem diem thu i da xe chua 
            if (SetOfPoints.get(i).getClusterID() == UNCLASSIFIED) {
                //lay cac diem lan can cua diem thu i chua duowc xet va danh dau da xet cho cac diem lan can
                //lay diem mac dinh lam tam cho cluster
                
                ArrayList<DBPoint> seeds = getRegionQuery(SetOfPoints, arrDistance, SetOfPoints.get(i), Eps);
                //set clusterID
                for (int j = 0; j < seeds.size(); j++) {

                    seeds.get(j).setClusterID(ClusterID);
                }
                clusterCount.add(seeds.size());
                ClusterID++;

            }
        }
        System.out.println("Numbers of Cluster is: " + (ClusterID));
        //b2. trong moi cluster tinh khoang cach den tam
        //tinh ma tran khoang cach tu tam cac cluster den cac phan tu
         boolean change = false;
        do {
            change =false;
            ArrayList<double[]> centers = new ArrayList<>();
            //duyet cac cluster
            for (int i = 0; i < clusterCount.size(); i++) {
                //tinh toa do tam
                double[] arr = new double[arrVetor[0].length];
                int count = 0;
                for (int j = 0; j < SetOfPoints.size(); j++) {
                    if (SetOfPoints.get(j).getClusterID() == i) {
                        count++;
                        for (int k = 0; k < arrVetor[0].length; k++) {
                            arr[k] += arrVetor[j][k];
                        }
                    }
                }
                for (int j = 0; j < arrVetor[0].length; j++) {
                    arr[j] = arr[j] / count;
                }
                centers.add(arr);
            }

            //buoc 3: tinh khoang cach tu cac diem toi cac tam
            //ma tran bieu thi khoang cach tu cac tam toi tat cac diem trong khong gian
            double[][] distance = new double[clusterCount.size()][SetOfPoints.size()];
            for (int i = 0; i < distance.length; i++) {
                for (int j = 0; j < distance[0].length; j++) {
                    distance[i][j] = calculateCosi(centers.get(i), arrVetor[j]);
                }
            }
            //buoc 4: chon lai tam cho cac phan tu
           
            for (int i = 0; i < distance[0].length; i++) {
                double min = Double.MAX_VALUE;
                int pos = -1;
                for (int j = 0; j < distance.length; j++) {
                    if (distance[j][i] < min) {
                        min = distance[j][i];
                        pos = j;
                    }
                }
                if (SetOfPoints.get(i).getClusterID() != pos) {
                    change = true;
                     System.out.printf("%d => %d\n",SetOfPoints.get(i).getClusterID(),pos);
                }
                SetOfPoints.get(i).setClusterID(pos);
            }
            System.out.println("New Loop");
        } while (change);
        
    }

    public ArrayList<DBPoint> getRegionQuery(ArrayList<DBPoint> SetOfPoints, double[][] arrDistance, DBPoint p, double Eps) {
        //lay cac diem lan can p thoa |p->q|<=Eps
        ArrayList<DBPoint> arr = new ArrayList<>();
        arr.add(p);
        for (int i = 0; i < SetOfPoints.size(); i++) {
            if (SetOfPoints.get(i).getClusterID() == UNCLASSIFIED) {
                int a = p.getId();
                int b = SetOfPoints.get(i).getId();
                if (a != b) {
                    if (a > b) {
                        if (arrDistance[a][b] <= Eps) {
                            arr.add(SetOfPoints.get(i));
                        }
                    } else {
                        if (arrDistance[b][a] <= Eps) {
                            arr.add(SetOfPoints.get(i));
                        }
                    }
                }
            }
        }
        return arr;
    }

    public double calculateCosi(double[] a, double[] b) {
        //tinh tich vo huong
        double sum = 0;
        double lena = 0;
        double lenb = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
            lena += a[i] * a[i];//do dai cua a
            lenb += b[i] * b[i];//do dai cua b
        }
        double kq = 0;
        if (lena * lenb == 0) {
            return 0;
        }
        kq = sum / (Math.sqrt(lena) * Math.sqrt(lenb));
        return (1 - kq);
    }
}
