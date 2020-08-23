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
public class MessageDisplayChangeJProgressBar extends Message{

    String id;
    Color foreCol;
    int value;
    String text;
    
    public MessageDisplayChangeJProgressBar(String id, String text, Color foreCol, int value) {
          super("server","server");
          this.id=id;
          this.foreCol=foreCol;
          this.value=value;
          this.text=text;
    }

    


    public String getId() {
        return id;
    }

    public Color getColor() {
        return this.foreCol;
    }

    public String gettext(){
        return this.text;
    }

    public int getValue(){
        return this.value;
    }
}
