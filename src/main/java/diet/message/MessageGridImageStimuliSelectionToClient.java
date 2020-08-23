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
public class MessageGridImageStimuliSelectionToClient extends MessageTask{
    
    public Vector selections = new Vector();
    public Color  innerMostColour;
    public Color  innerColor;
    public long serverID; //This is so the server can synchronize states properly
    
    public MessageGridImageStimuliSelectionToClient(String email, String userName, long serverID,String imageName, Color innermostColour, Color innerColor)
    {
        super(email,userName);
        this.selections.addElement(imageName);
        //this.colour=colour;
        this.innerMostColour=innermostColour;
        this.innerColor=innerColor;
        this.serverID=serverID;
    }
    
    public MessageGridImageStimuliSelectionToClient(String email, String userName, long serverID, Vector selections, Color innermostColour, Color innerColor)
    {
        super(email,userName);
        this.selections=selections;
        this.innerMostColour=innermostColour;
        this.innerColor=innerColor;
        this.serverID=serverID;
    }

    public Color getInnerColour() {
        return innerColor;
    }

    public Color getInnerMostColour() {
        return innerMostColour;
    }

    

    public Vector getSelections() {
        return selections;
    }

    public long getServerID() {
        return serverID;
    }

   
}
