/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.recommend;

import com.delicious.clustering.HelperLib;
import java.util.ArrayList;
import java.util.List;
import model.dao.LinkDAO;

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
    public ArrayList<String> getRecommendTagsByUser(String user,String url){
        ArrayList<String> userTags = getUserTags(user);
        
        return null;
    }
    public ArrayList<String> getUserTags(String user) {
        List<Object[]> list = (new HelperLib()).getUserTags(user);
        ArrayList<String> arr = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size() && i < 5; i++) {
                arr.add(String.format("%s(%s), ", list.get(i)[0].toString(), list.get(i)[1].toString()));

            }
            return arr;
        } else {
            return null;
        }



    }
}
