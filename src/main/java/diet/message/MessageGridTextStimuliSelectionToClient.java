/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.message;

import java.awt.Color;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class MessageGridTextStimuliSelectionToClient extends MessageTask{
    
   
    public Color[][]  innerMostColour;
    public Color[][]  innerColor;
    public long serverID; //This is so the server can synchronize states properly
    
    public MessageGridTextStimuliSelectionToClient(String email, String userName, long serverID, Color[][] innermostColour, Color[][] innerColor)
    {
        super(email,userName);
        //this.selections.addElement(imageNameORindex);
        //this.colour=colour;
        this.innerMostColour=innermostColour;
        this.innerColor=innerColor;
        this.serverID=serverID;
    }
     

    public Color[][] getInnerColors() {
        return this.innerColor=innerColor;
    }
    
    public Color[][] getInnerMostSColors() {
       return this.innerColor=innerMostColour;   
    }

    public long getServerID() {
        return serverID;
    }

   
}
