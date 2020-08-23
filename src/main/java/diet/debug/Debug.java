/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.debug;

/**
 *
 * @author gj
 */
public class Debug {
      public static boolean debugtimers = true;
      public static boolean debugmazegamereconnect = false;  
      public static boolean debugIO = false;
      
      static JDebugFrame jdf;
      
      public static void showDebug2(String s){
          //System.exit(-234);
          
          if(jdf==null){
             jdf = new JDebugFrame();
             jdf.setVisible(true);
            
          }
           jdf.append(s);
      }
      
}
