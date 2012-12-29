/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.recommend;

import com.delicious.clustering.HelperLib;
import com.delicious.clustering.evaluate.DataCollect;
import com.delicious.clustering.evaluate.Visualize;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.dao.ClusterSpaceDAO;
import model.dao.LinkDAO;
import model.pojo.ClusterSpace;
import model.pojo.Link;

/**
 *
 * @author THANHTUNG
 */
public class RecommendHelps {

    public ArrayList<String> getRecommendTags(String url) {
        int id = (new LinkDAO()).getLinkIDByUrl(url);
        if (id == -1) {
            return null;
        } else {
            List<Object[]> list = (new HelperLib()).getPopularTagsByLinkId(id);
            ArrayList<String> arr = new ArrayList<>();

            for (int i = 0; i < list.size() && i < 5; i++) {
                arr.add(String.format("%s(%s), ", list.get(i)[0].toString(), list.get(i)[1].toString()));
            }

            return arr;
        }
    }

    public ArrayList<ArrayList<String>> getRecommendTagsByUser(String user, String url) {
        ArrayList<ArrayList<String>> recommends = new ArrayList<>();
        ArrayList<String> userTags = getUserTags(user);

        ArrayList<String> ClusterTags = new ArrayList<>();
        ArrayList<String> PopularTags = new ArrayList<>();
        int Mutual = getDataByLink(url, ClusterTags,PopularTags);
        //session 1

        ArrayList<String> arr = new ArrayList<>();
        if (ClusterTags != null) {
            if (userTags != null) {
                for (String s : ClusterTags) {
                    if (userTags.contains(s)) {
                        arr.add(s);
                    }
                }
                recommends.add(arr);
                
              
                ArrayList<String> arr1 = new ArrayList<>();
                for (int i = 0; i < PopularTags.size(); i++) {
                    if (userTags.contains(PopularTags.get(i))) {
                        arr1.add(PopularTags.get(i));
                    }
                }
                recommends.add(arr1);

            } else {
                recommends = null;
            }
        } else {
            recommends = null;
        }
        //session 2


        recommends.add(ClusterTags);
        return recommends;
    }

    public ArrayList<String> getUserTags(String user) {
        List<Object[]> list = (new HelperLib()).getUserTags(user);
        ArrayList<String> arr = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size() && i < 10; i++) {
                arr.add(String.format("%s", list.get(i)[0].toString()));
            }
            return arr;
        } else {
            return null;
        }
    }

    public int getDataByLink(String linkUrl, ArrayList<String> MutualTags, ArrayList<String> PopularTags) {
        LinkDAO dao = new LinkDAO();
        int id = dao.getLinkIDByUrl(linkUrl);
        if (id == -1) {
            return -1;
        }
        ClusterSpaceDAO csdao = new ClusterSpaceDAO();
        List<ClusterSpace> arr = csdao.getClusterIdByLinkID(id);
      //  JOptionPane.showMessageDialog(null, arr.size());
        ArrayList<Link> links = new ArrayList<>();
        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                links.add(dao.getObject(arr.get(i).getId().getLink()));
            }
            ArrayList<String> Taglist = new ArrayList<>();
            double[][] kq = (new DataCollect()).getListVector(links, Taglist);
            (new Visualize()).VisualizeWithHeader(kq, Taglist);

            int chung = 0;
            for (int i = 0; i < kq[0].length; i++) {
                double m = 1;
                for (int j = 0; j < kq.length; j++) {
                    m = m * kq[j][i];
                }
                if (m == 0) {
                    chung = i;
                    break;
                }
            }
            if (kq.length==1) chung = kq[0].length-1;
            int pos =-1;
            for (int i = chung; i < kq[0].length; i++) {
              
                int count = 0;
                for (int j = 0; j < kq.length; j++) {
                    if (kq[j][i]!=0)
                    count++;
                }
                double scale = count * 1.0 / kq.length;
                if (count != kq[0].length && scale <0.8) {
                    pos=i;
                    break;
                }
               
            }
            MutualTags.addAll(Taglist.subList(0, chung));
         //   JOptionPane.showMessageDialog(null,chung);
            if (pos!=-1) {
                PopularTags.addAll(Taglist.subList(chung+1,pos));
            }
            return 1;
        } else {
            return -1;
        }
    }

    public ArrayList<String> getClusterTags(int ClusterID) {

        return null;
    }
}
