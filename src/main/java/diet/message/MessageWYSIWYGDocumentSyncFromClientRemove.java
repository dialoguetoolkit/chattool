package diet.message;

import java.io.Serializable;

import javax.swing.text.AttributeSet;

public class MessageWYSIWYGDocumentSyncFromClientRemove extends Message implements Serializable{

   
    private int offset;
    private int length;
    private AttributeSet attr;
    public String textInWindow="";
    public String[] priorTextByOther;

    
    
    
    public MessageWYSIWYGDocumentSyncFromClientRemove(String email, String username, int offset, int length, String textInWindow, String[] priorTextFromOther) {
        super(email, username);
        this.setOffset(offset);
        this.setLength(length);
        this.textInWindow=textInWindow;
        this.priorTextByOther=priorTextFromOther;
    }

  
    public AttributeSet getAttributeSet(){
        return attr;
    }
    
    public int getOffset(){
        return offset;
    }
    
    public int getLength(){
        return length;
    }
    
    public String getMessageClass(){
    return "WYSIWYGDocSyncFromClientRemove";
}

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getAllTextInWindow(){
        return this.textInWindow;
    }
    public String[] getPriorTextByOther(){
        return this.priorTextByOther;
    }
}
