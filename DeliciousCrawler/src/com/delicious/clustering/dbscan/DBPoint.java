/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.dbscan;

/**
 *
 * @author THANHTUNG
 */
public class DBPoint {
    int id;
    int ClusterID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClusterID() {
        return ClusterID;
    }

    public void setClusterID(int ClusterID) {
        this.ClusterID = ClusterID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DBPoint)
        {
            DBPoint p = (DBPoint)obj;
            if (p.id == this.id && p.ClusterID == p.ClusterID)
                return true;
        }
        return false;
    }

    
    
    
}
