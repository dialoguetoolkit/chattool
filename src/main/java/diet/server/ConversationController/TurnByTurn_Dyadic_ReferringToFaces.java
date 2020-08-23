/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.attribval.AttribVal;
import diet.message.MessageButtonPressFromClient;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.IsTypingController.IsTypingOrNotTyping;
import diet.server.Participant;
import diet.task.FaceComms.FaceCommsTaskControllerDyadic;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class TurnByTurn_Dyadic_ReferringToFaces extends TurnByTurn_Dyadic{

    
    FaceCommsTaskControllerDyadic fctc;
   
    Random r = new Random();
    int spooftypingmode = r.nextInt(3); // 0=nothing, 1= on pA, 2=BOTH
    
    
    long startTimeOfExperiment = new Date().getTime();
    long timeToStartWithFakeTyping = new Date().getTime();
    long durationOfFirstStage = 35 * 60 * 1000;
   
    
    public TurnByTurn_Dyadic_ReferringToFaces(Conversation c) {
        super(c);
        this.setID("rapturnbyturn");
        sett.client_MainWindow_width=800;  
        fctc = new FaceCommsTaskControllerDyadic (this,5000);
        boolean doLongIsTyping= r.nextBoolean();
        if(doLongIsTyping){
            itnt.setInactivityThreshold(3500);
        }
        else{
              itnt.setInactivityThreshold(200);
        }
       
        
    }

    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
        super.processChatText(sender, mct); //To change body of generated methods, choose Tools | Templates.
        fctc.processChatText(sender, mct.getText());        
        long timeTillStartTyping = (this.timeToStartWithFakeTyping - new Date().getTime())/60000; 
        if(timeTillStartTyping>0){            
             Conversation.printWSln("Main", "Countdown till manipulation of is typing= "+timeTillStartTyping+" mins");
            
        }else{
            Conversation.printWSln("Main", "Is now manipulating the is typing");
            
        }
       
    }

    @Override
    public synchronized void participantJoinedConversation(Participant p) {   
       // super.participantJoinedConversation(p);
        c.textOutputWindow_Initialize(p, "instructions", "instructions", "", 500, 500, false, true);
        if(c.getParticipants().getAllParticipants().size()==2){
            Participant pA = (Participant)c.getParticipants().getAllParticipants().elementAt(0);
            Participant pB = (Participant)c.getParticipants().getAllParticipants().elementAt(1);
            pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
            this.itnt.setWhoSeesEachOthersTyping( pp);
            CustomDialog.showDialog("PRESS OK TO START THE EXPERIMENT!");
            fctc.startTask(pA, pB);
            this.startTimeOfExperiment= new Date().getTime();
            this.timeToStartWithFakeTyping= startTimeOfExperiment+this.durationOfFirstStage;
        }
        
    }

    
    
    @Override
    public void participantRejoinedConversation(Participant p) {
        super.participantRejoinedConversation(p); //To change body of generated methods, choose Tools | Templates.
        c.textOutputWindow_Initialize(p, "instructions", "instructions", "", 500, 500, false, true);
        fctc.processChatText(p, "/d");
    }
    
    
    
    
    

    @Override
    public Vector getAdditionalInformationForParticipant(Participant p) {
        //return super.getAdditionalInformationForParticipant(p); //To change body of generated methods, choose Tools | Templates.
        
       
        
        Vector fctcadditionalvalues = this.fctc.getAdditionalValues(p);
        
        Vector spoof = this.getAdditionalInfoAboutSppofTyping(p);
        fctcadditionalvalues.addAll(spoof);
        
        fctcadditionalvalues.addAll(super.getAdditionalInformationForParticipant(p));
       
        return fctcadditionalvalues;
    }

    @Override
    public void processKeyPress(Participant sender, MessageKeypressed mkp) {
        // super.processKeyPress(sender, mkp); //To change body of generated methods, choose Tools | Templates.
         
         if(!mkp.isENTER()){
            this.determineIfToDoSpoofTyping(sender);
         }
        
         
         
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
       // itnt.processDoingsByClient(sender);
       // this.determineIfToDoSpoofTyping(sender);
       // doSpoofTyping(sender);
         
        
    }

    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
       String sN = System.getProperty("line.separator");
        if(!mWYSIWYGkp.getTextTyped().equalsIgnoreCase("\n") && !mWYSIWYGkp.getTextTyped().equalsIgnoreCase("\r") &&!mWYSIWYGkp.getTextTyped().equalsIgnoreCase(sN)){
            this.itnt.processDoingsByClient(sender);
        }
        
      
        
        
      if (determineIfToDoSpoofTyping(sender)) {
          Participant recip = pp.getRecipients(sender).firstElement();
          itnt.addSpoofTypingInfo(recip, new Date().getTime()+   ((long) r.nextInt(5000)));
      }
        
        
       
       
        //doSpoofTyping(sender);
    }
    
   
   
    
    
    
    
    public boolean determineIfToDoSpoofTyping(Participant p){
        
  
        
        if(this.spooftypingmode==0)return false;
        long timeTillStartTyping = (this.timeToStartWithFakeTyping - new Date().getTime())/60000;
 
        if (timeTillStartTyping <=0  ) {    
            Participant recip = pp.getRecipients(p).firstElement();
            int doSpoofTyping = 0;
            if(doSpoofTyping==0  && this.spooftypingmode==1 && p==this.fctc.pA ){
                return true;          
            }
            if(doSpoofTyping==0  && this.spooftypingmode==2 && p==this.fctc.pA ){
                return true;          
            }
            if(doSpoofTyping==0  && this.spooftypingmode==2 && p==this.fctc.pB ){
                return true;          
            }
             
        }
        return false;
    }
    
    
    
    
    
    public Vector getAdditionalInfoAboutSppofTyping(Participant p){
        Vector returnVal = new Vector();
        
        AttribVal av1 = new AttribVal("spooftypingmode",this.spooftypingmode);
        
        AttribVal av2 = new AttribVal("spoofing","not");
        Participant recip = pp.getRecipients(p).firstElement();
        if(determineIfToDoSpoofTyping(p)){
            av2 = new AttribVal("spoofing","victim");
        }
        if(determineIfToDoSpoofTyping(recip)){
            av2 = new AttribVal("spoofing","apparenttyper");
        }
        
        returnVal.add(av1);
        returnVal.add(av2);
            
        return returnVal;
    
    }
    
    
     @Override
    public void processButtonPress(Participant sender, MessageButtonPressFromClient mbfc) {
       // this.fctc.processChatText(sender, "/"+mbfc.buttonname.substring(1));
    }
    
    public static boolean showcCONGUI(){
        return true;
    } 
    
    
}
