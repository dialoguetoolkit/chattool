package diet.server.conversationhistory.turn;
import diet.attribval.AttribVal;
import diet.client.ClientInterfaceEvents.ClientInterfaceEvent;
import diet.client.DocumentChange.DocChange;
import diet.message.Keypress;
import java.io.Serializable;
import java.util.Vector;

import diet.server.conversationhistory.ConversationHistory;
//import edu.stanford.nlp.trees.Tree;



/**
 * This is the representation of individual turns in the conversation. It stores the origin, the recipients,
 * the keypresses, the individual words used and their associated patterns of usage, the timing data and the 
 * parse tree (if stored).
 * @author user
 */
public class DataToBeSaved extends Turn implements Serializable{
  
   private String senderID ="";   
   private String senderUsername ="";
   private String apparentSenderUsername ="";
 
  
  private Vector keyPresses = new Vector();
  private Vector documentUpdates = new Vector();

 
  
 

  //private transient ConversationHistory cH;
  //private String subDialogueID = "";
  //private Vector additionalValues;
  
  
   long timeOnClientOfStartTyping;
   long timeOnClientOfSending;
   long timeOnServerOfRELAY ;
   
   public String dataType ="";

    public String getDataType() {
        return dataType;
    }
  
  
   public DataToBeSaved(){
   }

    
    public DataToBeSaved (ConversationHistory cH, String subdialogueID, String dataType, long timeOnClientOfStartTyping, long timeOnClientOfSending, long timeOnServerOfRELAY, String senderID, String senderUsername,String apparentSenderUsername,String dataValue,
          Vector recipients,boolean wasBlocked  ,  Vector<Keypress> keyPresses,Vector<DocChange> documentUpdates,Vector<ClientInterfaceEvent> clientInterfaceEvents ,Vector<AttribVal> additionalVals){
        
        
         this.dataType=dataType;
          this.timeOnServerOfRELAY = timeOnServerOfRELAY;
        
          this.senderID=senderID;
          this.senderUsername = senderUsername;
          this.apparentSenderUsername =apparentSenderUsername;
        
          this.text=dataValue;
          this.recipients = recipients;
         
          this.keyPresses = keyPresses;
          this.documentUpdates=documentUpdates;
            
          this.cH=cH;
          
          super.subdialogueID=subdialogueID;
          super.additionalValues=additionalVals;
          //Calculate typing onset and enter of the turn
         
          
          this.timeOnClientOfStartTyping = timeOnClientOfStartTyping;
          this.timeOnClientOfSending = timeOnClientOfSending;
          this.timeOnServerOfRELAY = timeOnServerOfRELAY;
         
    }  
          
    
    
    
    
   
    public String getSenderID(){
        return this.senderID;
    }

    public String getSenderName(){
        return this.senderUsername;
    }
    
    public String getTextString(){
        return text;
    }
    
    
    
    
  
      public ConversationHistory getCH() {
        return cH;
    }

    public void setCH(ConversationHistory cH) {
        this.cH = cH;
    }

    

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

   

   
    
    
    
   
    public long getTimeOnServerOfRELAY(){
        return this.timeOnServerOfRELAY;
    }
    
    public String getApparentSenderUsername(){
        return this.apparentSenderUsername;
    }

     public long getTimeOnClientOfStartTyping() {
        return timeOnClientOfStartTyping;
    }

    public long getTimeOnClientOfSending() {
        return timeOnClientOfSending;
    }

   
   
}
