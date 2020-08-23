/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author gj
 */
public class ThrowableOps {
 
    
    
    public static String getStackTraceAsString(Exception e){
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
    
    public String getStackTraceAsStringWithNoNewlines(Exception e){
         StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        
        
        String cleanedString = errors.toString().replace("\n", "~");
        return cleanedString;
    }
    
}
