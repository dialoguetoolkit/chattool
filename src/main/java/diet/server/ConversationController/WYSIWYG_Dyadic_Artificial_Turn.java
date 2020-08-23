
//Note this is updated code

//Please note that this code is released on a slightly more restrictive license. Contact g.j.mills@rug.nl before use



package diet.server.ConversationController;
import diet.attribval.AttribVal;
import diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker;
import diet.message.MessageButtonPressFromClient;
import diet.message.MessageChangeClientInterfaceProperties;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageClientInterfaceEvent;
import diet.message.MessageKeypressed;
import diet.message.MessageTask;
import diet.message.MessageWYSIWYGAppendText;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.ConversationController.WYSIWYGTube.Content.TubeFakeInsertedText;
import diet.server.Participant;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.text.MutableAttributeSet;




//Please note that this code is released on a slightly more restrictive license. Contact g.j.mills@rug.nl before use



public class WYSIWYG_Dyadic_Artificial_Turn extends DefaultWYSIWYGConversationControllerInterface{

    
     diet.server.ConversationController.WYSIWYGTube.WYSIWYGTube wt = new  diet.server.ConversationController.WYSIWYGTube.TubeIn_InsertingFakeText(this);
     
    
     
     
    
    public static boolean showcCONGUI(){
        return true;
    } 

    
    
    public WYSIWYG_Dyadic_Artificial_Turn(Conversation c) {
          super(c,2,3000);
       
        
    }
    
   
   public WYSIWYG_Dyadic_Artificial_Turn(Conversation c, int numberOfTracks, int durationOfTimeout) {
        super(c,2,3000);
      
   }

    
    
   
    

    
   
   
   
   
   public synchronized void processTaskMove(MessageTask mtm, Participant origin) {            
    }
   
   
   
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct){    
          super.processChatText(sender, mct);
    }
    
    
    
    
    
    
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
      // super.processKeyPress(sender, mkp);
      
       
        
    }

   
    
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
        //Simplest manipulation - put everything in a queue that can be manipulated
        wt.add(sender, mWYSIWYGkp);
    }
    
    
    public void processWYSIWYGTextInserted_AfterManipulationByQueue(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
        this.saveOutputFromDocInserts_hashtableMOSTRECENTTEXT.putObject(sender, mWYSIWYGkp.getTextTyped()); 
        
         
        Vector recipients = this.pp.getRecipients(sender);
        for(int i=0;i<recipients.size();i++){    
            Participant pRecipient = (Participant)recipients.elementAt(i);
            int windowNumber = this.wysiwygtm.getTrackInWhichSendersTextIsDisplayedOnRecepientsScreen(sender, pRecipient);
            if(windowNumber < 0) windowNumber =0;
            MutableAttributeSet mas=sm.getStyleForRecipient(sender, pRecipient); 
            this.sendWYSIWYGTextToClient(pRecipient, windowNumber,mWYSIWYGkp.getTextTyped() , mas, sender.getUsername());
            
        }
        
        String subdialogueID = "UNSET";
        try{
        subdialogueID = this.pp.getSubdialogueID(sender);
        }catch (Exception e){
            e.printStackTrace();
        }
        this.wysiwygRTFDI.processAppendedDocumentInsert(sender, mWYSIWYGkp);    
    }
    
    
    public void processWYSIWYGTextInserted_AfterManipulationByQueue(TubeFakeInsertedText wfi){
        //this.saveOutputFromDocInserts_hashtableMOSTRECENTTEXT.putObject(sender, mWYSIWYGkp.getTextTyped()); 
      
        
        
        Vector recipients = this.pp.getRecipients(wfi.apparentSender);
        for(int i=0;i<recipients.size();i++){    
            Participant pRecipient = (Participant)recipients.elementAt(i);
            int windowNumber = this.wysiwygtm.getTrackInWhichSendersTextIsDisplayedOnRecepientsScreen(wfi.apparentSender, pRecipient);
            if(windowNumber < 0) windowNumber =0;
            MutableAttributeSet mas=sm.getStyleForRecipient(wfi.apparentSender, pRecipient); 
            this.sendWYSIWYGTextToClient(pRecipient, windowNumber,wfi.text , mas, wfi.apparentSender.getUsername());
            
        }
        
        String subdialogueID = "UNSET";
        try{
        subdialogueID = this.pp.getSubdialogueID(wfi.apparentSender);
        }catch (Exception e){
            e.printStackTrace();
        }
        this.wysiwygRTFDI.processFakeInsertedText(wfi); 
    }
    
    
   
  
    @Override
    public void processWYSIWYGTextRemoved(Participant sender,MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp){
         
    }

    
    @Override
    public void processClientEvent(Participant origin, MessageClientInterfaceEvent mce) {
        this.wysiwygRTFCI.processClientEvent(origin, mce);
     }
    
   
   

    @Override
    public Vector<AttribVal> getAdditionalInformationForParticipant(Participant p) {
         return new Vector();
        
    }

    
   
   


    
   

    
    
   
    
 

    @Override
    public void processButtonPress(Participant sender, MessageButtonPressFromClient mbfc) {
        super.processButtonPress(sender, mbfc); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
    
    
    /// All the m
    
    
    
     public void sendWYSIWYGTextToClient(Participant recipient, int wysiwygWindownumber, String textToBeAppended, MutableAttributeSet mas, String originUsername){
          MessageWYSIWYGAppendText mwysiwygat = new  MessageWYSIWYGAppendText(wysiwygWindownumber,textToBeAppended,  mas, originUsername);
          c.getParticipants().sendMessageToParticipant(recipient, mwysiwygat);
     }
    
      public void changeClientInterfaceToRightJustified(Participant recipient, int width, int height, long duration , int state, int numberOfWindows){
         Dimension d = new Dimension(width,height);
         MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(c.generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.changetoCBYC_MultipleTrack, d,duration, state, numberOfWindows);
         c.getParticipants().sendMessageToParticipant(recipient, mccip);    
     }
    
      public void changeClientInterfaceCharacterWhitelist(Participant recipient, String whitelist){
         
         MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(c.generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.setCharWhitelist, whitelist);
         c.getParticipants().sendMessageToParticipant(recipient, mccip);    
        
     }

      
     
      
}
