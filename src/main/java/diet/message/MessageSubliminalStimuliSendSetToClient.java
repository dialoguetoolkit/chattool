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
public class MessageSubliminalStimuliSendSetToClient extends Message{

    Vector v;
    int width;
    int height;
    
    public MessageSubliminalStimuliSendSetToClient(Vector vSerializableImages, int width,int height) {
          super("server","server");
          this.v=vSerializableImages;
          this.width = width;
          this.height = height;
    }
 
    
  public Vector getSerializableImages()  {
      return v;
  }
 
  public int getWidth(){
      return this.width;
  }
  public int getHeight(){
      return this.height;
  }
}
