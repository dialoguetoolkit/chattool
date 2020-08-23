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
public class MessageGridImagesStimuliSendSetToClient extends Message{

    Vector v;
    int widthheight;
    int rows;
    int columns;
    long serverID;
    
    public MessageGridImagesStimuliSendSetToClient(int rows, int columns, Vector vSerializableImages, int widthheight, long serverID) {
          super("server","server");
          this.v=vSerializableImages;
          this.widthheight = widthheight;
          this.rows = rows;
          this.columns=columns;
    }
 
    
  public Vector getSerializableImages()  {
      return v;
  }
 
  public int getWidthHeight(){
      return this.widthheight;
  }
  public int getROWS(){
      return this.rows;
      
  }
  public int getCOLUMNS(){
      return this.columns;
      
  }
  public long getServerID(){
      return this.serverID;
  }
  
}
