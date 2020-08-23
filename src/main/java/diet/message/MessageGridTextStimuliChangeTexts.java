/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;

import java.util.Vector;

/**
 *
 * @author Greg
 */
public class MessageGridTextStimuliChangeTexts extends Message{

    public String[][] textNames;
    public long serverID;

    
    public MessageGridTextStimuliChangeTexts(String[][] textNames, long serverID ){
        super("server","server");
        this.textNames=textNames;
           
    }

    public String[][] getTexts(){
        return this.textNames;
    }
    
   public long getServerID(){
       return this.serverID;
   }

   
}
