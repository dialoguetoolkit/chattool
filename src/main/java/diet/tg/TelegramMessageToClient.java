/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.tg;

import diet.message.Message;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 *
 * @author LX1C
 */
public class TelegramMessageToClient extends Message{

    SendMessage sm;
    
    public TelegramMessageToClient(String email, String username, String apparentUsername) {
        super(email, username);
    }
    
     
    
}
