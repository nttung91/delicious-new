/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeliciousCrawler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author THANHTUNG
 */
public class ReadDataFormFile {

    public static void main(String argv[]) {
        JSONParser jsonParser = new JSONParser();

     //   for (int i = 1; i <= 2; i++) {
           // String filename = "D:/output/delicious-" + i + ".all";
              String filename = "D:/StudyMaterial/TieuLuanChuyenNganh/delicious.all";  
        try {
                FileInputStream fis = new FileInputStream(filename);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                while (true) {
                    try {
                        String json = br.readLine();
                        if (json ==null || json.equals("")) break;
                        JSONObject jsonObj = (JSONObject) jsonParser.parse(json);
                        System.out.println(jsonObj.get("link").toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(ReadDataFormFile.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ReadDataFormFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReadDataFormFile.class.getName()).log(Level.SEVERE, null, ex);
            }
       // }
    }
}
