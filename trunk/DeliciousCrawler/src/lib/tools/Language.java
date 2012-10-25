/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sound.sampled.AudioFormat;
import model.dao.TagCollectDAO;
import model.pojo.TagCollect;
import org.hibernate.HibernateException;

/**
 *
 * @author THANHTUNG
 */
public class Language {

    Pattern input = Pattern.compile("\\w+");

    public void CleanNotEnglish() {
        TagCollectDAO dao = new TagCollectDAO();
        System.out.println("Start...");
        System.out.println("Reading...");
        List<TagCollect> list = dao.getList();
        System.out.println("Read completed");
        Pattern p = Pattern.compile("[^\u0000-\u0080]+");
        int count = 0;
        int count1 = 0;
        int scale = list.size() / 100;
        for (int i = 0; i < list.size(); i++) {
            Matcher m = p.matcher(list.get(i).getTagName());
            if (i % scale == 0) {
                System.out.println(i / scale);
            }

            if (!m.find()) {
                count++;
            } else {
                dao.deleteObject(list.get(i));
                count1++;
            }

        }
        System.out.printf("English %d  => Not English: %d", count, count1);
    }

    public void CheckEnglish() {
        //get data
        TagCollectDAO dao = new TagCollectDAO();
        System.out.println("Start...");
        System.out.println("Reading...");
        List<TagCollect> list = dao.getList();
        System.out.println("Read completed");
        Pattern p = Pattern.compile("[^\u0000-\u0080]+");
        int count = 0;
        int count1 = 0;
        for (int i = 0; i < list.size(); i++) {
            Matcher m = p.matcher(list.get(i).getTagName());

            if (!m.find()) {
                count++;
            } else {
                count1++;
            }

        }
        System.out.printf("English %d  => Not English: %d", count, count1);
    }
     public boolean isContainUnicodeCharacterOnly(String s) {
        //get data
        
        Pattern p = Pattern.compile("[^\u0000-\u0080]+");
     
        
            Matcher m = p.matcher(s);

            if (!m.find()) {
               return true;
            } else {
                return false;
            }

      
    }

    public boolean isContainOnlySpecialCharacter(String s) {
        String spec = "!@#$%^&*()_-=+<>?,.";
        char[] specialCh =spec.toCharArray() ;
        char[] arr = s.toCharArray();
        
        for (int i = 0; i < arr.length; i++) {
            boolean f = false;
            //is special char
            //
            for (int j = 0; j < specialCh.length; j++) {
                if (arr[i] == specialCh[j]) {
                    f = true;
                    break;
                }
            }
            if (!f) {
                return false;
            }
        }
        return true;
    }

    public void CheckAndCleanSpecialCharacter() {
        //get data
        TagCollectDAO dao = new TagCollectDAO();
        System.out.println("Start...");
        System.out.println("Reading...");
        List<TagCollect> list = dao.getList();
        System.out.println("Read completed");
       
        int count = 0;
        int count1 = 0;
        for (int i = 0; i < list.size(); i++) {

            if (!isContainOnlySpecialCharacter(list.get(i).getTagName())) {

                count++;
            } else {
                //System.out.printf("%s => only\n", list.get(i).getTagName());
                dao.deleteObject(list.get(i));
                count1++;
            }

        }
        System.out.printf("English %d  => Not English: %d", count, count1);
    }
    public void lowerizeText(){
        //use for tag collect
        TagCollectDAO dao = new TagCollectDAO();
        System.out.println("Reading........!");
        List<TagCollect> list = dao.getList();
        System.out.println("Read comletely!");
        for (int i =0;i<list.size();i++){
            TagCollect tc = list.get(i);
            tc.setTagName(list.get(i).getTagName().toLowerCase());
            try{
            dao.saveOrUpdateObject(tc);
        
            } catch (HibernateException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("Converted comletely!");
    }
    
}
