/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.demomode;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author sre
 */
public class DemoModeChecker {
    
    
      
      
    
      public DemoModeChecker(){
           String r = System.getProperty("user.dir");
           System.out.println("DEMOMODECHECKER - USERDIR IS: "+r);
           File f1 = new File(r+File.separator+"data");
           if(!f1.exists()){
              f1.mkdir();
              System.out.println("Making data directory: "+f1.getAbsolutePath());
              
              
              JOptionPane.showMessageDialog(null, "The chattool is creating the subfolderfolder:\n\n\n"
                      + "/data/saved experimental data/","",
              JOptionPane.WARNING_MESSAGE);
              
              
           }
           
           
           
           File f2 = new File(r+File.separator+"data"+File.separator+"saved experimental data");
           if(!f2.exists()){
              f2.mkdir();
              System.out.println("Making saved experimental data");
             
           }
           
           
           
           
           
           
           System.out.println("Exiting demo mode checker");
           //System.exit(-23423) ;
           
           
      }
    
    
    
}
