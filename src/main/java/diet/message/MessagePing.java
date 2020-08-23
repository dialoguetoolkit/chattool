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
public class MessagePing extends Message{

    public long timeOnServerOfC =0;
    
    public MessagePing(String email, String username, long timeOnServerOfCreation) {
        super(email, username);
        this.timeOnServerOfC=timeOnServerOfCreation;
    }
    
}
