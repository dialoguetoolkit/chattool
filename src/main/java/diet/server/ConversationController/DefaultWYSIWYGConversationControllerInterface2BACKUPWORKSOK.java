

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
import diet.server.ConnectionListener;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.server.wysiwyg.WYSIWYGReconstructingTurnsFromClientEvents;
import diet.server.wysiwyg.WYSIWYGReconstructingTurnsFromDocInserts;
import diet.utils.HashtableWithDefaultvalue;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.text.MutableAttributeSet;




public class DefaultWYSIWYGConversationControllerInterface2BACKUPWORKSOK extends DefaultConversationController{

    
    //Floorholder fh = new Floorholder(this);    
    //long durationFloorHolding =  CustomDialog.getLong("How long should participants hold floor for after typing? (ms)", 1000);
    
    long durationOfTextFadeout = CustomDialog.getLong("How long should text be displayed for? (ms)", 3000);
    int numberOfTracks = CustomDialog.getInteger("How many tracks?", 1);
    public boolean doMultitrackspace = false; //CustomDialog.getBoolean("On multitrack - add spaces whenever someone types");
    
    
    HashtableWithDefaultvalue saveOutputFromDocInserts_hashtableMOSTRECENTTEXT = new HashtableWithDefaultvalue("");
    
    WYSIWYGReconstructingTurnsFromDocInserts wysiwygRTFDI= new  WYSIWYGReconstructingTurnsFromDocInserts(this);
    WYSIWYGReconstructingTurnsFromClientEvents wysiwygRTFCI = new  WYSIWYGReconstructingTurnsFromClientEvents(this);
    
    public static boolean showcCONGUI(){
        return false;
    } 

    
    
    public DefaultWYSIWYGConversationControllerInterface2BACKUPWORKSOK(Conversation c) {
        super(c);
        if(numberOfTracks>1)  doMultitrackspace = CustomDialog.getBoolean("On multitrack - add spaces whenever someone types? (NOT IMPLEMENTED YET)");
        String portNumberOfServer = ""+ConnectionListener.staticGetPortNumber();
        this.setID("WYSIWYGInterface1");
        this.experimentHasStarted=true; 
        //this.fh.setFloorHoldingTime(durationFloorHolding);
    }
   
   
    
   
    
    
    
    
     @Override
    public boolean requestParticipantJoinConversation(String participantID) {
        return true;        
    }
    
    
    @Override
    public synchronized void participantJoinedConversation(final Participant p) {
        super.participantJoinedConversation(p);
        
        this.changeClientInterfaceToRightJustified(p,800,100,durationOfTextFadeout, 2,this.numberOfTracks);
        if(c.getParticipants().getAllParticipants().size()==2) {
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
         
             //c.changeClientInterface_doRobotDebug(p, "scenario1", 1000);
        }
        
    }
    
    
    
    

    
    @Override
    public void participantRejoinedConversation(Participant p) {     
        super.participantRejoinedConversation(p);      
        this.changeClientInterfaceToRightJustified(p,1500,100,durationOfTextFadeout, 2,this.numberOfTracks);
    }
    
    
    
   
   
   
   
   
   
   
   public synchronized void processTaskMove(MessageTask mtm, Participant origin) {            
    }
   
   
   
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct){    
          
    }
    
    
    
    
    
    
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
       super.processKeyPress(sender, mkp);
      
       
        
    }

   
    
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
        //fh.processWYSIWYGTextInserted(sender, mWYSIWYGkp);
        this.saveOutputFromDocInserts_hashtableMOSTRECENTTEXT.putObject(sender, mWYSIWYGkp.getTextTyped());
        //System.err.println("RECEIVED DOCINSERT:"+mWYSIWYGkp.getTextTyped());
        
        Vector recipients = this.pp.getRecipients(sender);
        for(int i=0;i<recipients.size();i++){    
            Participant pRecipient = (Participant)recipients.elementAt(i);
            MutableAttributeSet mas = sm.getStyleForRecipient(sender, pRecipient);
            int windowNumber =0;
            if (this.doMultitrackspace){
                windowNumber = recipients.indexOf(pRecipient);
                if(windowNumber < 0) windowNumber =0;
            }
            this.sendWYSIWYGTextToClient(pRecipient, windowNumber,mWYSIWYGkp.getTextTyped() , mas, sender.getUsername());
            
        }
        this.wysiwygRTFDI.processAppendedDocumentInsert(sender, mWYSIWYGkp);
        
    }
    
    
  
    @Override
    public void processWYSIWYGTextRemoved(Participant sender,MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp){
         
    }

    
   
    
    @Override
    public void processClientEvent(Participant origin, MessageClientInterfaceEvent mce) {
        //System.err.println("----------------------------");
        //System.err.println("Processing client event: "+origin.getUsername()+" "+mce.getClientInterfaceEvent().getAllAttribValuesAsStringForDebug());
        //System.err.println("----------------------------");
        this.wysiwygRTFCI.processClientEvent(origin, mce);
        //System.err.println("PROCESSING CLIENT EVENT------PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT----PROCESSING CLIENT EVENT");
    }
    
   
  

    
   
   


    
   

    
    
   
    
 

    @Override
    public void processButtonPress(Participant sender, MessageButtonPressFromClient mbfc) {
        super.processButtonPress(sender, mbfc); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
    
    
    /// All the m
    
    
    
     public void sendWYSIWYGTextToClient(Participant recipient, int wysiwygWindownumber, String textToBeAppended, MutableAttributeSet mas, String senderUsername){
          MessageWYSIWYGAppendText mwysiwygat = new  MessageWYSIWYGAppendText(wysiwygWindownumber,textToBeAppended,  mas, senderUsername );
          c.getParticipants().sendMessageToParticipant(recipient, mwysiwygat);
     }
    
      public void changeClientInterfaceToRightJustified(Participant recipient, int width, int height, long duration , int state, int numberOfWindows){
         Dimension d = new Dimension(width,height);
         MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(c.generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.changetoCBYC_MultipleTrack, d,duration, state, numberOfWindows);
         c.getParticipants().sendMessageToParticipant(recipient, mccip);
         
    }
    

}
