/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.message;

import java.awt.Color;

/**
 *
 * @author sre
 */
public class MessageGridTextStimuliSelectionFromClient extends MessageTask{
    
    public int position;
    public String name;
    public long timeonClientofSelection;
    public long serverID;
    public Color innerColor;
    public Color innerMostColor;
    
    
    
    public MessageGridTextStimuliSelectionFromClient(String email, String userName, long serverIDD, int position, String name, long timeOnClientOfSelection, Color innerColor, Color innerMostColor)
    {
        super(email,userName);
        this.name=name;
        this.position=position;
        this.timeonClientofSelection=timeOnClientOfSelection;
        this.serverID = serverIDD;
        this.innerColor=innerColor;
        this.innerMostColor=innerMostColor;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public long getTimeOnClientofSelection() {
        return timeonClientofSelection;
    }

    public long getServerID() {
        return serverID;
    }

    

}
