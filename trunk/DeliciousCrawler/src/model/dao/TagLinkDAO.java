/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import model.pojo.TagLink;



/**
 *
 * @author THANHTUNG
 */
public class TagLinkDAO extends ObjectDAO<TagLink, Integer> {

    @Override
    protected Class getPOJOClass() {
        return TagLink.class;
    }
    
    
    
    
}
