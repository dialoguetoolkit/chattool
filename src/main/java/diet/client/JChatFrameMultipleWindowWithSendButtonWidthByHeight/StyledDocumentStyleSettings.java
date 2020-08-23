/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client.JChatFrameMultipleWindowWithSendButtonWidthByHeight;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.swing.text.AttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import diet.server.Conversation;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;

/**
 *
 * @author Greg
 */
public class StyledDocumentStyleSettings implements Serializable{
    
    Color colorBackground = Color.WHITE;
    Color caretCOLOR = Color.BLACK;
    MutableAttributeSet masSELF;
    
    

    
    
    public StyledDocumentStyleSettings(Color windowbackground, Color caretCOLOR, MutableAttributeSet masSELF){

        this.masSELF = masSELF;
        this.caretCOLOR = caretCOLOR;
        this.colorBackground=windowbackground;
    }    
   
    public void setWindowBackground(JTextPane jtp){
        
        jtp.setBackground(this.colorBackground);
    }

   
   
            
    
    
   
    
    public Color getBackgroundColor(){
        return this.colorBackground;
    }
    
    public Color getCaretColor(){
        return this.caretCOLOR;
    }
    
    static public Color getColor(String colorName) {
        colorName = colorName.toUpperCase();
        try {
            // Find the field and value of colorName
            Field field = Class.forName("java.awt.Color").getField(colorName);
            return (Color)field.get(null);
        } catch (Exception e) {
            Conversation.printWSln("Main", "Bad color name: "+colorName);
            e.printStackTrace();
            return null;
        }
    }

   
}
