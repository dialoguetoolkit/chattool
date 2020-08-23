/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.message;

/**
 *
 * @author sre
 */
public class MessageGridImageStimuliSelectionFromClient extends MessageTask{
    
    public int position;
    public String nameofImage;
    public long timeonClientofSelection;
    public long serverID;
    
    public MessageGridImageStimuliSelectionFromClient(String email, String userName, long serverIDD, int position, String nameOfImage, long timeOnClientOfSelection)
    {
        super(email,userName);
        this.nameofImage=nameOfImage;
        this.position=position;
        this.timeonClientofSelection=timeOnClientOfSelection;
        this.serverID = serverIDD;
    }

    public String getNameofImage() {
        return nameofImage;
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
