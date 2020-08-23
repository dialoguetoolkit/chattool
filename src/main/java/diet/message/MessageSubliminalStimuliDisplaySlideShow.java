/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;

import java.awt.Color;

/**
 *
 * @author Greg
 */
public class MessageSubliminalStimuliDisplaySlideShow extends Message{

     String text;
     int positionX;
     int positionY;
     Color colour;
     String nameOfBackgroundPanel ="";
     long lengthOfTime =0;
     
    public MessageSubliminalStimuliDisplaySlideShow(String text, Color textcolour, String nameOfBackgroundPanel,int positionX, int positionY, long lengthOfTime){
       super("server","server");
       this.text=text;
       this.positionX=positionX;
       this.positionY=positionY;
       this.nameOfBackgroundPanel = nameOfBackgroundPanel;
       if(nameOfBackgroundPanel==null)nameOfBackgroundPanel="";
       this.lengthOfTime=lengthOfTime;
    }

    public String getText(){
      return text;   
    }
    
    public int getPositionX(){
        return this.positionX;
    }
    public int getPositionY(){
        return this.positionY;
    }
    public Color getColour(){
        return colour;
    }
    public String getNameOfBackgroundPanel(){
        return this.nameOfBackgroundPanel;
        
    }
    public long getLengthOfTime(){
        return lengthOfTime;
    }
}