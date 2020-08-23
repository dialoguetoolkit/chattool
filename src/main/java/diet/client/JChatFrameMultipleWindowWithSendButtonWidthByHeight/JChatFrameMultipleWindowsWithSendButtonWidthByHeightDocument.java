/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client.JChatFrameMultipleWindowWithSendButtonWidthByHeight;

import diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker;
import diet.attribval.AttribVal;
import diet.server.ConversationController.DefaultConversationController;
import java.util.Date;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 *
 * @author sre
 */
public class JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument extends DefaultStyledDocument{

    String whitelist = "";
    
    JChatFrameMultipleWindowsWithSendButtonWidthByHeight jcfmwwsbwbh;

    public JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument(JChatFrameMultipleWindowsWithSendButtonWidthByHeight jcfmwwsbwbh) {
       this.jcfmwwsbwbh = jcfmwwsbwbh;
    }

    public boolean permitDeletes = true;
    
    public void setPermitDeletes(boolean permitDeletes){
        this.permitDeletes=permitDeletes;
    }
    

    
    

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
         System.err.println("str is:"+str+":");
        
        if(whitelist!=null && whitelist!=""){
              
              if(!whitelist.contains(str)){
                   System.err.println("str is:"+str+":EXIT1");           
                  return;
              }
              if(str.equalsIgnoreCase("\n")){
                   System.err.println("str is:"+str+":EXIT2");  
                  return;
              }
              if(str.equalsIgnoreCase("")){
                   System.err.println("str is:"+str+":EXIT3");  
                  return;
              }
              if(str.equalsIgnoreCase("\r")){
                   System.err.println("str is:"+str+":EXIT4");  
                  return;
              }
              else{
                 
              }
              
              
        }
       
        
        
        if(super.getLength()+str.length()>this.jcfmwwsbwbh.maxcharlength)return;

        
        System.err.println("INSERT:"+offs+" STRING: "+str+" DOCUMENTLENGTH:"+super.getLength()); 
        
        long timeOfDisplay = new Date().getTime();
        
        super.insertString(offs, str, jcfmwwsbwbh.getSelfStyle());
        
        AttribVal av1 = new AttribVal("offset",offs);
        AttribVal av2 = new AttribVal("text",str);
         String finalstring = super.getText(0, super.getLength());
        AttribVal av5 = new AttribVal("finalstring", finalstring);
        
        jcfmwwsbwbh.getClientEventHandler().reportInterfaceEvent(ClientInterfaceEventTracker.textentryfield_insertstring,  timeOfDisplay, av1, av2, av5);
    }

 
     @Override
    public void remove(int offs, int len) throws BadLocationException {
        if(!this.permitDeletes)return;
        this.remove(offs, len, false);
    }
    
    
    
    public void remove(int offs, int len, boolean isAutoClearAfterSendingMessage) throws BadLocationException {
        
        String textbeingDeleted = "NOTSETCORRECTLY";
        try{
            textbeingDeleted =      super.getText(offs, len)   ;        
        }catch (Exception e){
            e.printStackTrace();
        }
      
        long timeOfDisplay = new Date().getTime();
        
        super.remove(offs, len);
        AttribVal av1 = new AttribVal("offset",offs);
        AttribVal av2 = new AttribVal("length",len);
        AttribVal av3 = new AttribVal("textbeingdeleted",textbeingDeleted);
        //AttribVal av4 = new AttribVal("timeofdisplay",timeOfDisplay);
        AttribVal av5 = new AttribVal("autoclear","FALSE");  //isautoclearpostsend
        if(isAutoClearAfterSendingMessage){
            av5.value="TRUE";
        }
        
        if(!isAutoClearAfterSendingMessage)jcfmwwsbwbh.getClientEventHandler().reportInterfaceEvent(ClientInterfaceEventTracker.textentryfield_removestring,  timeOfDisplay, av1, av2, av3, av5);   
    }

    @Override
    public void replace(int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        
                     if(whitelist!=null && whitelist!=""){
                            
                           String textSoFar=         super.getText(0, super.getLength());
                           if(textSoFar.equalsIgnoreCase("")&&text.equalsIgnoreCase("\n"))return;
                    }
        
        
        
        
        //text = text + "Offset: "+offset+" LENGTH:"+length+"    "   ;
        String textBeingDeleted = "";
        
        
        
        
        int totallengthOfDocument = super.getLength();
        int totallengthOfProposedChanges = text.length()-offset;
        if(totallengthOfProposedChanges>totallengthOfDocument + this.jcfmwwsbwbh.maxcharlength)return;

        long timeOfDisplay = new Date().getTime();
        
        super.replace(offset, length, text, jcfmwwsbwbh.getSelfStyle());
        
        AttribVal av1 = new AttribVal("offset",offset);
        AttribVal av2 = new AttribVal("text",text);
       
        
        AttribVal av4 = new AttribVal("length",length);
        String finalstring = super.getText(0, super.getLength());
        AttribVal av5 = new AttribVal("finalstring", finalstring);
   
        try{
            
            textBeingDeleted = super.getText(offset, length);
            System.err.println("Offset: "+offset+" LENGTH:"+length+" textbeingdeleted:"+textBeingDeleted);
            
            if(textBeingDeleted!=null && !textBeingDeleted.equals("")){
                AttribVal av6 = new AttribVal("textbeingdeleted",textBeingDeleted);
                 jcfmwwsbwbh.getClientEventHandler().reportInterfaceEvent(ClientInterfaceEventTracker.textentryfield_replacestring,  timeOfDisplay,  av1, av2, av4, av5, av6);
                 if(text.contains(System.lineSeparator())  || text.contains("\n")){
                     
                     
                     this.jcfmwwsbwbh.getClientEventHandler().returnPressed();
                 }
                 return;
            }
            
            
            
            
        }catch (Exception e){
            e.printStackTrace();
        }
        
        
        jcfmwwsbwbh.getClientEventHandler().reportInterfaceEvent(ClientInterfaceEventTracker.textentryfield_replacestring,  timeOfDisplay,  av1, av2, av4, av5);
        
        System.err.println("TEXT IS:"+text+"--");
        
       if(text.contains(System.lineSeparator())  || text.contains("\n")){
                     
                     this.jcfmwwsbwbh.getClientEventHandler().returnPressed();
        }
        
        
    }

   public void setWhitelist(String s){
        this.whitelist=s;
    }

}
