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
public class MessagePong extends Message{

    public long timeOnServerOfCreation =0;
    public long timeOnClientOfReceipt=0;
    
    public MessagePong(String email, String username, long timeOnServerOfCreation, long timeOnClientOfReceipt) {
        super(email, username);
        this.timeOnServerOfCreation=timeOnServerOfCreation;
        this.timeOnClientOfReceipt=timeOnClientOfReceipt;
    }
    
}
