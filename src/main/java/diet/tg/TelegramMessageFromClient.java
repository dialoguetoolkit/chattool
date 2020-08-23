/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.tg;

import diet.message.Message;
import java.io.Serializable;
import java.util.Date;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author LX1C
 */

/**
 *
 * @author LX1C
 */
public class TelegramMessageFromClient extends Message implements Serializable{
    
    
    
    public boolean hasBeenRelayed = false;
    public Update u ;
    public long timeOfCreation = new Date().getTime();

    public TelegramMessageFromClient(Update u, String email, String username) {
        super(email, username);
        this.u = u;
    }
    
}
