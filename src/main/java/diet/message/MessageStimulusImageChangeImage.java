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
public class MessageStimulusImageChangeImage extends Message{

   
    public String nameOfImage;
    public long duration;
   // public boolean blocktextentryduringcountdown = false;
    public boolean  isindirectory;
    
    public MessageStimulusImageChangeImage(String nameOfImage , boolean isindirectory, long duration ){
        super("server","server");
        this.duration = duration;
        this.nameOfImage=nameOfImage;    
        this.isindirectory=isindirectory;
    }

    

   
}
