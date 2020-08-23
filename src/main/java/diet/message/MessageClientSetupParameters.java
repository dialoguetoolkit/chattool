/*
 * MessageClientSetupParameters.java
 *
 * Created on 31 October 2007, 22:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.message;

import java.io.Serializable;
/**
 *
 * @author user
 */
public class MessageClientSetupParameters extends Message implements Serializable{
        
     private String newClientEmail;
     private String newClientUsername;
    
    
     public MessageClientSetupParameters(String email, String username) {
        super(email, username);
    }

    public String getMessageClass(){
        return "MessageClientLogon";
    }
    
    public void setNewEmailAndUsername(String newEmail, String newUsername){
        this.newClientEmail=newEmail;
        this.newClientUsername=newUsername;
    }

    public String getNewEmail(){
        return newClientEmail;
    }
    public String getNewUsername(){
        return newClientUsername;
    }
}

