/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;

import javax.swing.text.MutableAttributeSet;

/**
 *
 * @author gj
 */
public class MessageWYSIWYGAppendText extends Message {

    public int windowNumber;
    public String text;
    public MutableAttributeSet mas;
    public String originUsername;
    
    public MessageWYSIWYGAppendText(int windownumber, String text, MutableAttributeSet mas, String originUsername) {
        super("server", "server");
        this.windowNumber=windownumber;
        this.text=text;
        this.mas=mas;
        this.originUsername=originUsername;
        
    }

    public int getWindowNumber() {
        return windowNumber;
    }

    public String getText() {
        return text;
    }

    public MutableAttributeSet getMas() {
        return mas;
    }
    public String getOriginUsername(){
        return this.originUsername;
    }
    
}
