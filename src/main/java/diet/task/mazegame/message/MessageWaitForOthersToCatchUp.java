/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame.message;

import java.io.Serializable;

import diet.message.MessageTask;
import java.awt.Color;

/**
 *
 * @author user
 */
public class MessageWaitForOthersToCatchUp extends MessageTask implements Serializable{

   String text;
   Color colorBACKGROUND; 
   
    public MessageWaitForOthersToCatchUp(String email, String username,String text, Color colorBackground) {
      super(email,username);
      this.text=text;
      this.colorBACKGROUND=colorBackground;
    }
    
  public String getText(){
    return text;
  }
  public Color getBackground(){
      return colorBACKGROUND;
  }
  
}
