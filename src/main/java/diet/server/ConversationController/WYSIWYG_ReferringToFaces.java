/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.message.MessageButtonPressFromClient;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.ConversationController.ui.JInterfaceTenButtons;
import diet.server.ConversationController.ui.JInterfaceMenuButtonsReceiverInterface;
import diet.server.Participant;
import diet.task.FaceComms.FaceCommsTaskControllerDyadic;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class WYSIWYG_ReferringToFaces  extends DefaultWYSIWYGConversationControllerInterface  implements JInterfaceMenuButtonsReceiverInterface {

    
    
   JInterfaceTenButtons jisp; 
    Vector taskControllers = new Vector();
    Vector participantsQueuedLLLL = new Vector();
    Vector participantsQueuedRRRRR = new Vector();
    
    
    public WYSIWYG_ReferringToFaces(Conversation c) {
        super(c, 2, 3000);
        super.singleTrackOption=false;
        
        
        jisp = new JInterfaceTenButtons(this,
                "Create pairs from queued participants and START SINGLETRACK",
                "Create pairs from queued participants and START DOUBLETRACK",
                "Swap to singletrack UI",
                "Swap to twotrack UI",
                "Swap to normal text-chat",
                "",
                "",
                "",
                "",
                "KILL any queued participants"
         );
        
        
        sett.login_numberOfParticipants=4;
        sett.client_MainWindow_width=800;
       
    }
    
     @Override
     public boolean requestParticipantJoinConversation(String participantID) {  
         return true;
     }
    
     
    public boolean requestParticipantJoinConversationDEPRECATED(String participantID) {    
        
        //This section is only for autologin (i.e. when programming / testing the setup)
         if(DefaultConversationController.sett.login_autologin){
            if(c.getParticipants().findParticipantWithEmail(participantID)!=null) return false; 
             
            if(this.participantsQueuedLLLL.size()>this.participantsQueuedRRRRR.size()){
                  if(participantID.equals("RRRR1"))return true;
                  if(participantID.equals("RRRR2"))return true;
                  if(participantID.equals("RRRR3"))return true;
                  if(participantID.equals("RRRR4"))return true;
                  if(participantID.equals("RRRR5"))return true;
                  if(participantID.equals("RRRR6"))return true;
                  if(participantID.equals("RRRR7"))return true;
                  if(participantID.equals("RRRR8"))return true;
                  if(participantID.equals("RRRR9"))return true;
                  if(participantID.equals("RRRR0"))return true;
                  if(participantID.equals("RRRRX"))return true;
                  if(participantID.equals("RRRRY"))return true;
            }
            else if (this.participantsQueuedLLLL.size()<this.participantsQueuedRRRRR.size()){
                  if(participantID.equals("LLLL1"))return true;
                  if(participantID.equals("LLLL2"))return true;
                  if(participantID.equals("LLLL3"))return true;
                  if(participantID.equals("LLLL4"))return true; 
                  if(participantID.equals("LLLL5"))return true;
                  if(participantID.equals("LLLL6"))return true;
                  if(participantID.equals("LLLL7"))return true;
                  if(participantID.equals("LLLL8"))return true;
                  if(participantID.equals("LLLL9"))return true;
                  if(participantID.equals("LLLL0"))return true;
                  if(participantID.equals("LLLLX"))return true;
                  if(participantID.equals("LLLLY"))return true;
            }
            else if(this.participantsQueuedLLLL.size()==this.participantsQueuedRRRRR.size()) {
                if(participantID.startsWith("LLLL") || participantID.startsWith("RRRR")) return true;
               
            }
            return false;
         }
        
        //
        
        if(participantID.equals("LLLL1"))return true;
        if(participantID.equals("LLLL2"))return true;
        if(participantID.equals("LLLL3"))return true;
        if(participantID.equals("LLLL4"))return true; 
        if(participantID.equals("LLLL5"))return true;
        if(participantID.equals("LLLL6"))return true;
        if(participantID.equals("LLLL7"))return true;
        if(participantID.equals("LLLL8"))return true;
        if(participantID.equals("LLLL9"))return true;
        if(participantID.equals("LLLL0"))return true;
        if(participantID.equals("LLLLX"))return true;
        if(participantID.equals("LLLLY"))return true;
        if(participantID.startsWith("LLLLZ"))return true;
        
        if(participantID.equals("RRRR1"))return true;
        if(participantID.equals("RRRR2"))return true;
        if(participantID.equals("RRRR3"))return true;
        if(participantID.equals("RRRR4"))return true;
        if(participantID.equals("RRRR5"))return true;
        if(participantID.equals("RRRR6"))return true;
        if(participantID.equals("RRRR7"))return true;
        if(participantID.equals("RRRR8"))return true;
        if(participantID.equals("RRRR9"))return true;
        if(participantID.equals("RRRR0"))return true;
        if(participantID.equals("RRRRX"))return true;
        if(participantID.equals("RRRRY"))return true;
        if(participantID.startsWith("RRRRZ"))return true;
        return false;
    }
    
    
    
      @Override
    public synchronized void participantJoinedConversation(final Participant p) {
        super.participantJoinedConversation(p);
        c.changeClientInterface_disableScrolling(p);
        if(p.getParticipantID().startsWith("LLLL")){
            this.participantsQueuedLLLL.addElement(p);
        }
        else if (p.getParticipantID().startsWith("RRRR")){
            this.participantsQueuedRRRRR.addElement(p);
        }
        else if (c.getParticipants().getAllParticipants().size() % 2 == 0){
             this.participantsQueuedLLLL.addElement(p);
        }
        else{
             this.participantsQueuedRRRRR.addElement(p);
        }
        
        
        
        
        Conversation.printWSln("Main", "No. of participants in set 1:"+this.participantsQueuedLLLL.size()+"   No. of participants in set 2:"+ this.participantsQueuedRRRRR.size());
        if(this.participantsQueuedLLLL.size()!=this.participantsQueuedRRRRR.size()){
            Conversation.printWSln("Main","UNEQUAL SET SIZES - DO NOT START THE EXPERIMENT");
        }
        else{
            Conversation.printWSln("Main","EQUAL SET SIZES: OK TO START");
        }
        
        
        if(c.getParticipants().getAllParticipants().size()==2) {
             
             //this.startmazegame();
        }
        
    }

    
    
    
    
    
    public synchronized void participantJoinedConversationDEPRECATED(final Participant p) {
        super.participantJoinedConversation(p);
        c.textOutputWindow_Initialize(p, "instructions", "instructions", "", 500, 500, false, true);
        
        if(p.getParticipantID().startsWith("LLLL")){
            this.participantsQueuedLLLL.addElement(p);
        }
        else if (p.getParticipantID().startsWith("RRRR")){
            this.participantsQueuedRRRRR.addElement(p);
        }
        Conversation.printWSln("Main", "No. of LLLL:"+this.participantsQueuedLLLL.size()+"   No. of RRRR:"+ this.participantsQueuedRRRRR.size());
        if(this.participantsQueuedLLLL.size()!=this.participantsQueuedRRRRR.size()){
            Conversation.printWSln("Main","UNEQUAL GROUP SIZES - DO NOT START THE EXPERIMENT----ALLP:"+ this.c.getParticipants().getAllParticipants().size());
        }
        else{
            Conversation.printWSln("Main","EQUAL SIZES: OK TO START");
        }
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
        c.textOutputWindow_Initialize(p, "instructions", "instructions", "", 500, 500, false, true);
        final  String[] buttons = {"same","different"};
        c.showStimulusImageFromJarFile_InitializeWindow(p, 500, 580, "",false, buttons);
        super.participantRejoinedConversation(p); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    long durationOfStimulus = 5000;

     public void doPairingOfEnqueudDyadsAndStartDyads(){
        if(this.participantsQueuedLLLL.size()!=this.participantsQueuedRRRRR.size()){
            Conversation.printWSln("Main", "CANNOT START - THERE IS AN UNEQUAL NUMBER OF GROUPS");
            return;
        }
        for(int i=0;i<this.participantsQueuedLLLL.size();i++){
             Participant pL = (Participant)participantsQueuedLLLL.elementAt(i);
             Participant pR = (Participant)participantsQueuedRRRRR.elementAt(i);
             
            FaceCommsTaskControllerDyadic  fctc = new FaceCommsTaskControllerDyadic (this,durationOfStimulus);
            this.taskControllers.addElement(fctc);
            fctc.startTask(pL, pR);
             
            pp.createNewSubdialogue(pL,pR);
            itnt.addPairWhoAreMutuallyInformedOfTyping(pL, pR);
            Vector recipients = new Vector();
            recipients.addElement(pL); recipients.addElement(pR);
            c.sendInstructionToMultipleParticipants(recipients, "Please start!");
        }
        this.participantsQueuedLLLL.removeAllElements();
        this.participantsQueuedRRRRR.removeAllElements();
    }
    
    
    
    
    
    
    
    
    @Override
    public void performActionTriggeredByUI(String s) {
        if(s.equalsIgnoreCase("Create pairs from queued participants and START SINGLETRACK")){
            super.wysiwygtm.setAllOnSameTrack(true);
            doPairingOfEnqueudDyadsAndStartDyads();
            
        }
        else if(s.equalsIgnoreCase("Create pairs from queued participants and START DOUBLETRACK")){
            super.wysiwygtm.setAllOnSameTrack(false);
            doPairingOfEnqueudDyadsAndStartDyads();
            
        }
        else if(s.equalsIgnoreCase("KILL any queued participants")){
             int numberKilled=0;
            
             if(this.participantsQueuedLLLL.size()>0){
                 numberKilled = numberKilled+participantsQueuedLLLL.size();
                 this.participantsQueuedLLLL.removeAllElements();
             }
             else if (this.participantsQueuedRRRRR.size()>0){
                 numberKilled = numberKilled+participantsQueuedRRRRR.size();
                 this.participantsQueuedRRRRR.removeAllElements();
             }
             Conversation.printWSln("Main","Killed: "+numberKilled+" participants");
             
        }
        else if (s.equalsIgnoreCase("Swap to twotrack UI")){
           super.wysiwygtm.setAllOnSameTrack(false); 
           //System.exit(-56);
        }
        else if (s.equalsIgnoreCase("Swap to singletrack UI")){
           super.wysiwygtm.setAllOnSameTrack(true);
            
        }
        
       
    }

    
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
       FaceCommsTaskControllerDyadic  fctc = this.getTaskControllerForParticipant(sender);
       if(fctc==null){Conversation.printWSln("Main", "ERROR COULD NOT FIND TASKCONTROLLER FOR: "+sender.getUsername());}
       if(fctc!=null)fctc.processChatText(sender, mct.getText());        
        
    }

   
    
        
    

    @Override
    public void processButtonPress(Participant sender, MessageButtonPressFromClient mbfc) {
        //System.err.println("NAME: "+mbfc.buttonname);
        //System.err.println("SUBSTRING: "+mbfc.buttonname.substring(0, 1));
        
        //System.exit(-56);
        super.processButtonPress(sender, mbfc);
         FaceCommsTaskControllerDyadic  fctc = this.getTaskControllerForParticipant(sender);
       if(fctc==null){Conversation.printWSln("Main", "ERROR COULD NOT FIND TASKCONTROLLER FOR: "+sender.getUsername());}
       if(fctc!=null) fctc.processChatText(sender, "/"+mbfc.buttonname.substring(0,1));
       
    }
    
    
   
    
    

    @Override
    public Vector getAdditionalInformationForParticipant(Participant p) {
        //return super.getAdditionalInformationForParticipant(p); //To change body of generated methods, choose Tools | Templates.
        
       
        
        Vector fctcadditionalvalues = new Vector();
        FaceCommsTaskControllerDyadic  fctc = this.getTaskControllerForParticipant(p);
        if(fctc==null){Conversation.printWSln("Main", "ERROR COULD NOT FIND TASKCONTROLLER FOR: "+p.getUsername());}
        if(fctc!=null) fctc.getAdditionalValues(p);
     
        fctcadditionalvalues.addAll(super.getAdditionalInformationForParticipant(p));
       
        return fctcadditionalvalues;
    }

    @Override
    public void processKeyPress(Participant sender, MessageKeypressed mkp) {
         super.processKeyPress(sender, mkp); //To change body of generated methods, choose Tools | Templates.
         
        
        
         
         
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
        super.processWYSIWYGTextRemoved(sender, mWYSIWYGkp);
         
        
    }

    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
        if(mWYSIWYGkp.isControlPressed()){
           // System.exit(-567);
        }
        
        String previous = (String)super.saveOutputFromDocInserts_hashtableMOSTRECENTTEXT.getObject(sender);
        super.processWYSIWYGTextInserted(sender, mWYSIWYGkp);
       
        System.err.println("PREVIOUS IS:"+previous + "  CURRENT IS:"+mWYSIWYGkp.getTextTyped());
        
        if(previous.contains("/")){
            if(mWYSIWYGkp.getTextTyped().contains("s")) {
                 FaceCommsTaskControllerDyadic  fctc = this.getTaskControllerForParticipant(sender);
                 if(fctc==null){Conversation.printWSln("Main", "ERROR COULD NOT FIND TASKCONTROLLER FOR: "+sender.getUsername());}
                 if(fctc!=null) fctc.processChatText(sender, "/s");
                //System.exit(-5);
            }
            else if(mWYSIWYGkp.getTextTyped().contains("d")){
                
              FaceCommsTaskControllerDyadic  fctc = this.getTaskControllerForParticipant(sender);
               if(fctc==null){Conversation.printWSln("Main", "ERROR COULD NOT FIND TASKCONTROLLER FOR: "+sender.getUsername());}
               if(fctc!=null)  fctc.processChatText(sender, "/d");
            }
        }
                
    }            
                
    
    
       public FaceCommsTaskControllerDyadic getTaskControllerForParticipant(Participant p){
           for(int i=0;i<this.taskControllers.size();i++){
               FaceCommsTaskControllerDyadic ftcd = (FaceCommsTaskControllerDyadic)this.taskControllers.elementAt(i);
               if(ftcd.pA==p) return ftcd;
               if(ftcd.pB==p)return ftcd;
           }
           return null;
       }
           
       
       
       
       public static boolean showcCONGUI(){
        return true;
    }     
       
       
    }
    
       
    
    
    
  
    
    


