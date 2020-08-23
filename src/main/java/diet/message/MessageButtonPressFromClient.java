/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;

/**
 *
 * @author gj
 */
public class MessageButtonPressFromClient extends Message{

    public String buttonname;
    public long timeOfPress;
    
    public MessageButtonPressFromClient(String email, String username, String buttonname, long timeOfPress) {
        super(email, username);
        this.buttonname=buttonname;
        this.timeOfPress=timeOfPress;
    }
    
}
