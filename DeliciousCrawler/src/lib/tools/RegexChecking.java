/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author THANHTUNG
 */
public class RegexChecking {
    public static boolean CheckEmail(String email){
        Pattern p = Pattern.compile("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);

        if (m.find()) {
            return true;
        } else {
            return false;
        }
            
    }
     public static boolean CheckDienThoai(String sdt){
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(sdt);

        if (m.find()) {
            return true;
        } else {
            return false;
        }
            
    }
}
