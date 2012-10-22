/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.io.Serializable;
import model.pojo.Following;

/**
 *
 * @author THANHTUNG
 */
public class FollowingDAO extends ObjectDAO<Following,Integer> {

    @Override
    protected Class getPOJOClass() {
       return Following.class;
    }
    
}
