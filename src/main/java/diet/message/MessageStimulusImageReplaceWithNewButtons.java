/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;

import java.util.Vector;

/**
 *
 * @author Greg
 */
public class MessageStimulusImageReplaceWithNewButtons extends Message{

   
    public String[] buttonnames;
    public boolean enable;

    
    public MessageStimulusImageReplaceWithNewButtons(String[] buttonnames , boolean enable ){
        super("server","server");
        this.buttonnames = buttonnames;
        this.enable=enable;
        
    }

    

   
}
