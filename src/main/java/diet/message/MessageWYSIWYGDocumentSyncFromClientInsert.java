package diet.message;

import java.io.Serializable;

import javax.swing.text.AttributeSet;

public class MessageWYSIWYGDocumentSyncFromClientInsert extends Message implements Serializable{

    private String textTyped;
    private int offset;
    private int length;
    private AttributeSet attr;
    private String allTextInWindow ="";
    private long fadeout= -1;
    
    private boolean controlPressed = false;

 /*   public MessageWYSIWYGDocumentSyncFromClientInsert(String email, String username, String textAppended, int offset,int length,AttributeSet a) {
        this(email, username, textAppended, offset,length);
        attr=a;
    }
   */
    
    
    public MessageWYSIWYGDocumentSyncFromClientInsert(String email, String username, String textTyped, int offset,int length, String allTextInWindow,  boolean controlPressed, long fadeOut) {
        super(email, username);
        this.textTyped = textTyped;
        this.setOffset(offset);
        this.setLength(length);
        this.allTextInWindow=allTextInWindow;
        if(allTextInWindow==null)allTextInWindow="";
        
        this.controlPressed=controlPressed;
        this.fadeout=fadeOut;
    }

    public AttributeSet getAttr(){
        return attr;
    }
    
    public String getTextTyped(){
        return textTyped;
    }

    public int getOffset(){
        return offset;
    }
    public int getLength(){
        return length;
    }
    
    public String getTextToAppendToWindow(){
        return getTextTyped();
    }
    public String getMessageClass(){
    return "WYSIWYGDocSyncFromClient";
}

  

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getAllTextInWindow(){
        return this.allTextInWindow;
    }
   

    public boolean isControlPressed() {
        return controlPressed;
    }

    public long getFadeout() {
        return fadeout;
    }
    
    
}
