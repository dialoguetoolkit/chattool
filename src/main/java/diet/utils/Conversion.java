/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils;

import diet.message.MessageChatTextFromClient;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class Conversion {
    
      
      public static String convertMillisecondsIntoText(long milliseconds){
        try{  
          long seconds = milliseconds / 1000;
          int numberOfMinutes = ((int)seconds / 60);
          int numberOfSeconds = ((int)seconds) % 60;
          if(numberOfMinutes>0){
              return numberOfMinutes+" min  "+numberOfSeconds+" sec.";
          }
          else{
              return numberOfSeconds+" sec.";
          }
        }catch (Exception e){
            e.printStackTrace();
            return ""+milliseconds;
        }  
          
      }
    
      
      public static void main(String[] args){
          System.out.println(convertMillisecondsIntoText(61000));
      }
    
      
      public static void replacePipeCharacterFromChatTextWithSlash(Vector[] allChatText){
      for(int j=0;j<allChatText.length;j++){   
          Vector v = allChatText[j];  
          
           for(int i=0;i<v.size();i++){
                MessageChatTextFromClient mcu = (MessageChatTextFromClient)v.elementAt(i);
                String text = mcu.getText();
                if(text.contains("|")){
                    //System.exit(-3242366);
                    mcu.setText(mcu.getText().replace("|", "/"));
                }
                
           }
        }   
      }
      
      
}
