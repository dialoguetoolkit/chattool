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
public class MessageGridImagesStimuliChangeImages extends Message{

    public Vector imagenames = new Vector();
    public long serverID;

    
    public MessageGridImagesStimuliChangeImages(Vector imageNames, long serverID ){
        super("server","server");
        this.imagenames=imageNames;
           
    }

    public Vector getImageNames(){
        return this.imagenames;
    }
    
   public long getServerID(){
       return this.serverID;
   }

   
}
