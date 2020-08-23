
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
import diet.server.Configuration;
import diet.server.ConnectionListener;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.server.wysiwyg.WYSIWYGReconstructingTurnsFromClientEvents;
import diet.server.wysiwyg.WYSIWYGReconstructingTurnsFromDocInserts;
import diet.server.wysiwyg.WYSIWYGMultiTrackManager;
import diet.utils.HashtableWithDefaultvalue;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.text.MutableAttributeSet;




//Please note that this code is released on a slightly more restrictive license. Contact g.j.mills@rug.nl before use



public class DefaultWYSIWYGConversationControllerInterface extends DefaultConversationController{

    
    //Floorholder fh = new Floorholder(this);    
    //long durationFloorHolding =  CustomDialog.getLong("How long should participants hold floor for after typing? (ms)", 1000);
    
    //int numberOfParticipantsPerConversation = CustomDialog.getInteger("How many participants?", 3); 
    long durationOfTextFadeout =3000;//= CustomDialog.getLong("How long should text be displayed for? (ms)", 3000);
    //int numberOfTracks = 1;//CustomDialog.getInteger("How many tracks?", 1);
    public boolean singleTrackOption =true;
    
    //public boolean doMultitrackspace = false; //CustomDialog.getBoolean("On multitrack - add spaces whenever someone types");
    
    
    //This is used to store previous character - e.g. to detect commands with slash
    HashtableWithDefaultvalue saveOutputFromDocInserts_hashtableMOSTRECENTTEXT = new HashtableWithDefaultvalue("");
    
    
    WYSIWYGReconstructingTurnsFromDocInserts wysiwygRTFDI= new  WYSIWYGReconstructingTurnsFromDocInserts(this);
    WYSIWYGReconstructingTurnsFromClientEvents wysiwygRTFCI = new  WYSIWYGReconstructingTurnsFromClientEvents(this);
    WYSIWYGMultiTrackManager wysiwygtm = new WYSIWYGMultiTrackManager(this);
    
    
    
    public static boolean showcCONGUI(){
        return false;
    } 

    
    
    public DefaultWYSIWYGConversationControllerInterface(Conversation c) {
        super(c);
       // DefaultConversationController.sett.login_numberOfParticipants = numberOfParticipantsPerConversation;
        // if(numberOfTracks>1)  doMultitrackspace = CustomDialog.getBoolean("On multitrack - add spaces whenever someone types? (NOT IMPLEMENTED YET)");
        String portNumberOfServer = ""+ConnectionListener.staticGetPortNumber();
        this.setID("WYSIWYGInterface");
        this.experimentHasStarted=true; 
        //this.numberOfTracks = CustomDialog.getInteger("How many tracks?", 1);
        String[]  options ={"single track","multi track"};
        String option = CustomDialog.show2OptionDialog(options, "How many tracks?", "WYSIWYG GUI Configuration");
        if(option.equalsIgnoreCase(options[0])){ this.singleTrackOption=true; }
        else{this.singleTrackOption=false;}
        
        
        this.wysiwygtm.setAllOnSameTrack(singleTrackOption);
        durationOfTextFadeout = CustomDialog.getLong("How long should text be displayed for? (ms)", 3000);
        //this.fh.setFloorHoldingTime(durationFloorHolding);
        
    }
    
   
   public DefaultWYSIWYGConversationControllerInterface(Conversation c, int numberOfTracks, int durationOfTimeout) {
        super(c);
       // DefaultConversationController.sett.login_numberOfParticipants = numberOfParticipantsPerConversation;
        // if(numberOfTracks>1)  doMultitrackspace = CustomDialog.getBoolean("On multitrack - add spaces whenever someone types? (NOT IMPLEMENTED YET)");
        String portNumberOfServer = ""+ConnectionListener.staticGetPortNumber();
        this.setID("WYSIWYGInterface");
        this.experimentHasStarted=true; 
        
        if(numberOfTracks>1){
            this.wysiwygtm.setAllOnSameTrack(false);
            singleTrackOption=false;
        }
        this.durationOfTextFadeout=durationOfTimeout;
        //this.fh.setFloorHoldingTime(durationFloorHolding);
        
      
   }

    @Override
    public void initializePostSetup() {
       this.doSaveOfLicense();
    }
    
   
    private void doSaveOfLicense(){
        
        try{
          String text = ""+sett.wysiwyglicense;
          System.err.println("SL01");
          c.saveAdditionalRowOfDataToSpreadsheetOfTurns("Important", "license info", "license info", "license info", "license info", 0, 0, 0, new Vector(),text, new Vector());
         //System.err.println("SLE");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
     @Override
    public boolean requestParticipantJoinConversation(String participantID) {
       // this.doSaveOfLicense();
        return true;        
    }
    
    
    
    
    @Override
    public synchronized void participantJoinedConversation(final Participant p) {
        super.participantJoinedConversation(p);
        if(singleTrackOption){
             this.changeClientInterfaceToRightJustified(p,800,100,durationOfTextFadeout, 2,1);
        }
        else{
            this.changeClientInterfaceToRightJustified(p,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
        }
        
       
        //if(c.getParticipants().getAllParticipants().size()==DefaultConversationController.sett.login_numberOfParticipants) {
            // pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
         
             //c.changeClientInterface_doRobotDebug(p, "scenario1", 1000);
        //}
        
    }
    
    
    
    

    
    @Override
    public void participantRejoinedConversation(Participant p) {     
        super.participantRejoinedConversation(p);      
        this.changeClientInterfaceToRightJustified(p,800,100,durationOfTextFadeout, 2,2);
    }
    
    
    
   
   
   
   
   
   
   
   public synchronized void processTaskMove(MessageTask mtm, Participant origin) {            
    }
   
   
   
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct){    
          super.processChatText(sender, mct);
    }
    
    
    
    
    
    
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
       super.processKeyPress(sender, mkp);
      
       
        
    }

   
    
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
        
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
    public Vector<AttribVal> getAdditionalInformationForParticipant(Participant p) {
        Vector vResults = new Vector();
        Vector vRecip = pp.getRecipients(p);
        
        
        for(int i=0;i<vRecip.size();i++){
            Participant pRecipient = (Participant)vRecip.elementAt(i);
            AttribVal av = new AttribVal("Recipient"+i, pRecipient.getParticipantID()+","+pRecipient.getUsername());
            vResults.addElement(av);
        }
        
        
        for(int i=0;i<vRecip.size();i++){
            Participant pRecipient = (Participant)vRecip.elementAt(i);
            int trackNoForRecip = this.wysiwygtm.getTrackInWhichSendersTextIsDisplayedOnRecepientsScreen(p, pRecipient); 
            AttribVal av = new AttribVal("DestinationTrackForRecipient"+i, trackNoForRecip);
            vResults.addElement(av);
        }
        
        
       
       
        vResults.addAll(super.getAdditionalInformationForParticipant(p));
        return vResults;
        
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
     public void changeClientInterfaceToTurnByTurn(Participant recipient){
         MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(c.generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.changetoTBT);
         c.getParticipants().sendMessageToParticipant(recipient, mccip);   
     }

}
