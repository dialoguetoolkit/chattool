/*
 * MessageClientCloseDown.java
 *
 * Created on 31 October 2007, 23:09
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
public class MessageClientCloseDown extends Message implements Serializable {
    
    /** Creates a new instance of MessageClientCloseDown */
    public MessageClientCloseDown(String sender, String username) {
        super(sender,username);
    }
    
}
