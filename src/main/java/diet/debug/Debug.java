/*
 *  
 *
 *
 * For newer changes to the software - especially about debugging / integrating LLMs please contact the team for the experimental version of the software.
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
      
      public static boolean debugDuplicate = true;
      
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
