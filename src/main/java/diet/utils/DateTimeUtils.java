/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author gj
 */
public class DateTimeUtils {
    
     static SimpleDateFormat sdfDate =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


    
    public static String getString(Date d){
            String strDate = sdfDate.format(d);
            return strDate;
    }
    
    public static String getString(long l){
            Date d = new Date(l);
            String strDate = sdfDate.format(d);
            return strDate;
    }
            
}
