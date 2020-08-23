/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;

import java.awt.Color;
import java.util.Vector;

/**
 *
 * @author Greg
 */
public class MessageGridTextStimuliInitialize extends Message{

    
    int width;
    int height;
    int rows;
    int columns;
    long serverID;
    
    
    String[][] texts;
    Color [][] innermostC;
    Color [][]innerC;
    
    public MessageGridTextStimuliInitialize(int rows, int columns, String[][] texts, Color[][] innerMostc, Color[][] innerC, int width, int height, long serverID) {
          super("server","server");
          this.innerC=innerC;
          this.innermostC=innerMostc;
          this.texts=texts;
         
          this.width = width;
          this.height =height;
          this.rows = rows;
          this.columns=columns;
    }

    public String[][] getTexts() {
        return texts;
    }

    public Color[][] getInnermostC() {
        return innermostC;
    }

    public Color[][] getInnerC() {
        return innerC;
    }
 
    
 
 
  public int getWidth(){
      return this.width;
  }
  
  public int getHeight(){
      return this.height;
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
