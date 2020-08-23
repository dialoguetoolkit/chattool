/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController.WYSIWYGTube.Content;

import diet.server.Participant;

/**
 *
 * @author LX1C
 */
public class TubeFakeInsertedText {
    
    public Participant apparentSender;
    public String text;
    public long delayBeforeSending = 150;
    //public Participant recipient;
    

    public TubeFakeInsertedText(Participant apparentSender, String text) {
        this.apparentSender = apparentSender;
        this.text = text;
        //this.recipient = recipient;
    }
    
    
    
   
    
    
    
}
