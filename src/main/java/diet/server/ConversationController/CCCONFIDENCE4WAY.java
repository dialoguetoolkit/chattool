package diet.server.ConversationController;
import diet.message.*;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.ConversationController.ui.InterfaceForJCountDown;
import diet.server.ConversationController.ui.JCountDown;
import diet.server.ConversationController.ui.JServerSendMessagesToClients;
import diet.server.Participant;
import diet.task.stimuliset.ConfidenceTaskController;
import diet.task.stimuliset.ConfidenceTaskControllerSequence;
import diet.task.stimuliset.ConfidenceTaskControllerSequenceSet;
import diet.task.stimuliset.JConfidenceTaskControllerInitialFrame4WAY;
import diet.utils.FilenameToolkit;
import java.awt.Color;
import java.io.File;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;



//be able to load whicheveers samedif dif same sequence required
//be able to have following dialogue without it crashing (to pave way for 8 participant chat)



public class CCCONFIDENCE4WAY extends CCCONFIDENCE implements InterfaceForJCountDown{
   
     //String s = CustomDialog.getString("There is an error in this code - the control for transitioning is on both threads...there is no guarantee that there will actually be a break","");
    
     Long l = CustomDialog.getLong("What should the stimulus duration be? (milliseconds)", 100);
     
     
     
     
    //ConfidenceTaskControllerSequenceSet ctcss= new ConfidenceTaskControllerSequenceSet();
    ConfidenceTaskControllerSequenceSet ctcss= new ConfidenceTaskControllerSequenceSet(new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"randomizedsequence.txt"));
    ConfidenceTaskController ctc1 = new ConfidenceTaskController(this,ctcss.ctcseq1,"ctc1","TRAINING1",99999999,l);
    ConfidenceTaskController ctc2 = new ConfidenceTaskController(this,ctcss.ctcseq1,"ctc2","TRAINING2",99999999,l);
    
    String surveyADDRESS = "http://goo.gl/forms/jV2zQA0q4S\n";
  
    String message = "The experiment is now finished.\nCould you please fill in the questionnaire.\nThe questionnaire should open automatically.\n"
            + "If it doesn't, please open a web-browser at the following address:\n\n"+surveyADDRESS;
    
    
    JServerSendMessagesToClients jssmtc = new JServerSendMessagesToClients(this,message,surveyADDRESS);
    
    
    Participant p1;
    Participant p2;
    Participant p3;
    Participant p4;
    
    
    //
    
    long practiceTime =  (1000*60*6);//  (1000*60*26)
    long condnTime=      (1000*60*6);//  (1000*60*6)
    
    
     
    long minimumDisplayDurationOfSpoofMessage = 4000;
    long maximumSpoofDisplayDurationOfSpoofMessage = 6000;
    
    
    
   
    long spoofDuringPracticePhaseMaximumInterSpoofDistance = (1000 * 60 * 8);// (1000 * 60 * 8);
    long spoofDuringPracticePhaseMinimumInterSpoofDistance  =(1000 * 60 * 3);// (1000 * 60 * 4);
    long timeOfNextSpoof = new Date().getTime() + spoofDuringPracticePhaseMinimumInterSpoofDistance + 
                r.nextInt((int)(spoofDuringPracticePhaseMaximumInterSpoofDistance-spoofDuringPracticePhaseMinimumInterSpoofDistance));
        
    
    boolean  addConditionTimetoLastCondition= false;
    
    String[] apparentPartnerChanges;
        
    String choice = "NOTSET";
    
    String choice1 = "SamePhysical/SameApparent (HONEST)";
    String choice2 = "DifferentPhysical/DifferentApparent (HONEST)";
    String choice3 = "SamePhysical/DifferentApparent (DECEPTION)";
    String choice4 = "DifferentPhysical/SameApparent (DECEPTION)";
    
   
    String sMessagePostTraining1 ="Not set";
    String sMessagePostTraining2 = "Not set";
    
    
   
     JCountDown jc;
     
    
    public static boolean showcCONGUI() {
        return false;
    }
    
    

    public CCCONFIDENCE4WAY(Conversation c) {
        super(c);
        this.initializeConditions();
        DefaultConversationController.sett.login_numberOfParticipants = 4;
    }
    
    
    
    
    
    
    
     String msgSWAP1 = "Not set";
     String msgSWAP2 = "Not set";
     String msgSAME1 = "Not set";
     String msgSAME2 = "Not set";
    
    public void initializeConditions(){
    
        String directory = System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message";
        File fSWAP1 = new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message"+File.separator+"messageswap1.txt");
       // File fSWAP2 = new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message"+File.separator+"messageswap2.txt");
        File fSAME1 = new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message"+File.separator+"messagesame1.txt");
        //File fSAME2 = new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message"+File.separator+"messagesame2.txt");
        
        msgSWAP1 = FilenameToolkit.getStringFromFile(fSWAP1);
       // msgSWAP2 = FilenameToolkit.getStringFromFile(fSWAP2);
        msgSAME1 = FilenameToolkit.getStringFromFile(fSAME1);
       // msgSAME2 = FilenameToolkit.getStringFromFile(fSAME2);
    
        /*
        msgSAME1 = "You have now finished the first part.\n" +
                   "Please take a short break!\n";
        
        msgSWAP1 = "You have now finished the first part.\n" +
                   "Please take a short break!\n" +
                   "\n" +
                   "When you continue, you will be paired\n" +
                   "with a NEW PARTNER!";
        */
        
        
        
        Vector v = new Vector();v.addElement(choice1);v.addElement(choice2);v.addElement(choice3);v.addElement(choice4);
        JConfidenceTaskControllerInitialFrame4WAY jctc = new JConfidenceTaskControllerInitialFrame4WAY (v);
        jctc.waitFOROK();
        String s = jctc.getSAMEDIFFTRANSTIONS();
        practiceTime = 1000*60* jctc.getPracticeMinutes();
        condnTime= 1000*60*jctc.getConditionMinutes();
        addConditionTimetoLastCondition = jctc.getAddExtraTimetoEnd();

       if(s.equalsIgnoreCase(choice1)){
           choice = choice1;
       }
       else if(s.equalsIgnoreCase(choice2)){
           choice = choice2;
       }
       else if(s.equalsIgnoreCase(choice3)){
           choice = choice3;
       }
       else if(s.equalsIgnoreCase(choice4)){
           choice = choice4;
       }
       else{
           System.err.println("ERRORCODE9");
           System.err.println(s);
           System.exit(-5);
       }
        //String directory = System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message1";        
        //   File f = CustomDialog.loadFile(s);
       
       
       
      jc = new JCountDown(this, "Time till swap",practiceTime);
}

//If you're here, the return value was null/empty.

            
            
    public int getIDXState(){ ///This should not have to be called
        return -999999;
    }
    
    public void accelerateTime(long milliseconds){
        //sdmt.accelerateTime(milliseconds);
    }
     public void changeAPPARENT(String s){
        //sdm.goStageAPPARENT(s);
    }
     public void changePHYSICAL(String s){
        //sdm.goStagePHYSICAL(s);
    }

    @Override
    public void performUITriggeredEvent(String eventname, int value) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        this.nextState();
    }

    @Override
    public void performUIJProgressBarDisplayOnClient(int percentage, String text) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    int currentState =0;
    
    public void nextState(){
        Vector pss = (Vector)c.getParticipants().getAllParticipants();

       
        c.changeClientInterface_clearMainWindows(p1);
        c.changeClientInterface_clearMainWindows(p2);
        c.changeClientInterface_clearMainWindows(p3);
        c.changeClientInterface_clearMainWindows(p4);
        
        
        ConfidenceTaskControllerSequence ctcs=ctcss.ctcseq2;
       
        if(this.choice==choice1 || this.choice == choice3){
            
            ctc1.resetToStartwithNoStartMessage();
            ctc2.resetToStartwithNoStartMessage();
            
            ctc1.disableTextDisplayOfClients();
            ctc2.disableTextDisplayOfClients();
            
            if(choice==choice1) displayMessageOnClientsStimulisetOverLongerDuration(this.msgSAME1); 
            if(choice==choice3) displayMessageOnClientsStimulisetOverLongerDuration(this.msgSWAP1); 
            
            this.displaymessageOnServer("PRESS OK TO START THE SECOND STAGE!\n(Please wait at least 1 min after seeing this before starting the second stage)");
            
            
            
            ctc1.nextState_assignNEWSPEAKERS_STARTINGAGAIN(p1, p2,ctcs);  
            ctc2.nextState_assignNEWSPEAKERS_STARTINGAGAIN(p3, p4,ctcs);    
           
            ctc1.sendStartMessageToParticipants();
            ctc2.sendStartMessageToParticipants();
            
        }
        else if(this.choice==choice2 ||  this.choice == choice4){
            
            
            ctc1.resetToStartwithNoStartMessage();
            ctc2.resetToStartwithNoStartMessage();
            
            ctc1.disableTextDisplayOfClients();
            ctc2.disableTextDisplayOfClients();
            
            if(choice==choice2) displayMessageOnClientsStimulisetOverLongerDuration(this.msgSWAP1); 
            if(choice==choice4) displayMessageOnClientsStimulisetOverLongerDuration(this.msgSAME1);
            
            
            displaymessageOnServer("PRESS OK TO START THE SECOND STAGE!\n(Please wait at least 1 min after seeing this before starting the second stage)");
            
            ctc1.nextState_assignNEWSPEAKERS_STARTINGAGAIN(p1, p3,ctcs);    
            ctc2.nextState_assignNEWSPEAKERS_STARTINGAGAIN(p2, p4,ctcs);   
            
            ctc1.sendStartMessageToParticipants();
            ctc2.sendStartMessageToParticipants();
           
        }
        currentState=1;
        
        c.changeClientInterface_clearMainWindows(p1);
        c.changeClientInterface_clearMainWindows(p2);
        c.changeClientInterface_clearMainWindows(p3);
        c.changeClientInterface_clearMainWindows(p4);
        
        
    }
    
   
    
    
    public void displayPopupMessageOnClients(String popupmessage, String stimulisetmessage){
        String[] ack = {"ok"};
        getC().subliminalstimuliset_displayText(p1, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p1));
        getC().subliminalstimuliset_displayText(p2, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p2));
        getC().subliminalstimuliset_displayText(p3, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p3));
        getC().subliminalstimuliset_displayText(p4, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p4));
        this.getC().showPopupOnClientQueryInfo("ID", p1, popupmessage, ack, "Information!", 0);
        this.getC().showPopupOnClientQueryInfo("ID", p2, popupmessage, ack, "Information!", 0);
        this.getC().showPopupOnClientQueryInfo("ID", p3, popupmessage, ack, "Information!", 0);
        this.getC().showPopupOnClientQueryInfo("ID", p4, popupmessage, ack, "Information!", 0);
        
    }
    public void displayMessageOnClientsStimuliset( String stimulisetmessage){
        
        getC().subliminalstimuliset_displayText(p1, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p1));
        getC().subliminalstimuliset_displayText(p2, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p2));
        getC().subliminalstimuliset_displayText(p3, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p3));
        getC().subliminalstimuliset_displayText(p4, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p4));
            
    }
    
    public void displayMessageOnClientsStimulisetOverLongerDuration( final String stimulisetmessage){
       
        Thread t = new Thread(){
          public void run(){
              
               CustomDialog.showModeLessDialog("Server is sending multiple 'take a break' msgs for 30sec. Please wait for this window to disappear before starting the second stage!", 30000);
                      
               
                 
               
               
                Conversation.printWSln("Main", "SERVER IS SENDING MESSAGE AT MULTIPLE DURATIONS.   SENDING EVERY 300 msecs");
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{  Thread.sleep(300); }catch (Exception e){  e.printStackTrace(); }
              
              
                Conversation.printWSln("Main", "SERVER IS SENDING MESSAGE AT MULTIPLE DURATIONS.   SENDING");
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{
                    Thread.sleep(5000);
                }catch (Exception e){  e.printStackTrace(); }
                
                Conversation.printWSln("Main", "SERVER IS SENDING MESSAGE AT MULTIPLE DURATIONS.   SENDING");
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{
                    Thread.sleep(5000);
                }catch (Exception e){  e.printStackTrace(); }
                Conversation.printWSln("Main", "SERVER IS SENDING MESSAGE AT MULTIPLE DURATIONS.   SENDING");
                displayMessageOnClientsStimuliset( stimulisetmessage);
                try{
                    Thread.sleep(8000);
                }catch (Exception e){  e.printStackTrace(); }
                 Conversation.printWSln("Main", "SERVER IS SENDING MESSAGE AT MULTIPLE DURATIONS.   SENDING");
                 displayMessageOnClientsStimuliset( stimulisetmessage);
          }  
        };
        t.start();
        
        
        //getC().subliminalstimuliset_displayText(p1, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p1));
        //getC().subliminalstimuliset_displayText(p2, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p2));
        //getC().subliminalstimuliset_displayText(p3, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p3));
        //getC().subliminalstimuliset_displayText(p4, stimulisetmessage,"backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p4));
            
    }
    
    
    
    
     //fixation1time + stimulus1time + blankscreen1time + fixation2time + stimulus2time +blankscreen2time
    
    
    
    
    public void displaymessageOnServer(String s){
          JOptionPane.showMessageDialog(null, s,  "Message",  JOptionPane.PLAIN_MESSAGE);
    }
    
    
    
    
    
    public String  getDescriptionForP(Participant p){
        String description = "";
        
        if(p!=null&&ctc1.participantIsInConversation(p))description = description+ ctc1.getID();
        if(p!=null&&ctc2.participantIsInConversation(p))description = description+ ctc2.getID();
        
        description = description +" State:"+currentState;
        
        if (currentState==0) description = description + "(TrainingPhase)";
        else if (currentState==1) description = description + "(TestPhase)";
        else description = description + "(UNKNOWNPHASE)";
        
        return description;
    }
    
    
   
    
    
     
    
    
        public ConfidenceTaskController getTaskController(Participant p){
            if(ctc1.participantIsInConversation(p))return ctc1;
            if(ctc2.participantIsInConversation(p))return ctc2;
            return null;
        }
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
        if(c.getParticipants().getAllParticipants().size()<4){
               //c.sendArtificialTurnToRecipient(sender, "PLEASE WAIT TILL EVERYONE HAS LOGGED IN! THANKS", 0);
               c.sendInstructionToParticipant(sender, "PLEASE WAIT TILL EVERYONE HAS LOGGED IN! THANKS");
               return;
        }   
        
        
        
        
        
        
        ConfidenceTaskController ctcOfP = null; 
        
           if(ctc1.participantIsInConversation(sender)) {
               ctcOfP = ctc1;
           }
           else if(ctc2.participantIsInConversation(sender))  {
               ctcOfP = ctc2;
           }
           else{
               Conversation.printWSln("Main", "ERROR IT CAN'T FIND THE CONFIDENCETASK");
                System.err.println("ERROR IT CAN'T FIND THE CONFIDENCETASK");
                Conversation.saveErr("PROBLEM - IT CAN'T FIND THE CONFIDENCE TASK");
           }
        
           String description =this.getDescriptionForP(sender);
          
           if(mct.getText().startsWith("/")){
              
               ctcOfP.processChatText(sender, mct);  
           }
           else if(!mct.getText().startsWith("/")){
               
               
                     Participant pPhysicalPartner = this.getPhysicalPartner(sender);
                     Participant pApparentPartnerOfRecipient = this.getApparentPartner(pPhysicalPartner);
               
                     int style =1;
                     if(this.currentState==2)style=3;
                     //c.relayTurnToParticipantWithAlteredUserNameAndEnforcedTextColour(sender, pApparentPartnerOfRecipient.getUsername(), mct, pPhysicalPartner, style, this.getDescriptionForP(sender));
                     //c.sendLabelDisplayAndEnableToParticipantInOwnStatusWindow(pPhysicalPartner, "Status:OK", false, true);
                     
                     
                     
                     
                     c.sendArtificialTurnFromApparentOrigin(pApparentPartnerOfRecipient, pPhysicalPartner, mct.getText());
                
             
             } 
               
               
               
            
           
           
    }
    
   
    

    
   
   
    
            
            
            
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
          //c.informIsTypingToAllowedParticipants(sender);
          
         Participant pPhysicalPartner = this.getPhysicalPartner(sender);
         Participant pApparentOrigin = this.getApparentPartner(pPhysicalPartner);
         //c.informParticipantBthatParticipantAIsTyping(pApparentOrigin, pPhysicalPartner);
         
           
    }
    
    
    public void setSTime(long stime){
        this.ctc1.setSTime(stime);
        this.ctc2.setSTime(stime);
    }
    public void setFTime(long fixationtime ){
        this.ctc1.setFTime(fixationtime);
        this.ctc2.setFTime(fixationtime);
    }
     public void setBTime(long backgroundtime ){
        this.ctc1.setBTime(backgroundtime);
        this.ctc2.setBTime(backgroundtime);
    }
    
    
    
    
   
    
    public boolean requestParticipantJoinConversation(String participantID){
        if(participantID.equalsIgnoreCase("111111"))return true;
        if(participantID.equalsIgnoreCase("222222"))return true;
        if(participantID.equalsIgnoreCase("333333"))return true;
        if(participantID.equalsIgnoreCase("444444"))return true;
        
        Vector pss = (Vector)c.getParticipants().getAllParticipants();
        for(int i=0;i<pss.size();i++){
            Participant p = (Participant)pss.elementAt(i);
            //
        }
        return false;
    }
    
    
    
    
    
    
    
    
     
    
    
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        ConfidenceTaskController ctcForP = this.getTaskController(p);
        //ctcForP.participantJoinedConversation(p);
        
       
        
       
        
        
        if(p.getParticipantID().equalsIgnoreCase("111111")){
            ctc1.participantJoinedConversationSetAsA(p);
            this.p1=p;
        }
        if(p.getParticipantID().equalsIgnoreCase("222222")){
            ctc1.participantJoinedConversationSetAsB(p);
            this.p2=p;
        }
        if(p.getParticipantID().equalsIgnoreCase("333333")){
            ctc2.participantJoinedConversationSetAsA(p);
            this.p3=p;
        }
        if(p.getParticipantID().equalsIgnoreCase("444444")){
            ctc2.participantJoinedConversationSetAsB(p);
            this.p4=p;
        }
                

        if(c.getParticipants().getAllParticipants().size()>3){
           CustomDialog.showDialog("ALL 4 PARTICIPANTS HAVE LOGGED ON\n"
                   + "PRESS OK TO START THE EXPERIMENT");
            jc.startCountdown();
            ctc1.sendStartMessageToParticipants();
            ctc2.sendStartMessageToParticipants();
            
            
            //this.nextState();
        }
        
        
        
        
        
        
        
        
        
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
        super.participantRejoinedConversation(p);
         ConfidenceTaskController ctcForP = this.getTaskController(p);
        ctcForP.participantRejoinedConversation(p);
    }
    
   
   
 

    
    public Participant getPhysicalPartner(Participant p){
         if(this.currentState==0){
              if(p==p1) return p2;
              if(p==p2) return p1;
              if(p==p3) return p4;
              if(p==p4) return p3;
         }
         else if (currentState==1){
           if(this.choice==choice1 || this.choice==choice3){
                if(p==p1) return p2;
                if(p==p2) return p1;
                if(p==p3) return p4;
                if(p==p4) return p3;
           }
           else if(this.choice==choice2 || this.choice==choice4){
               if(p==p1) return p3;
               if(p==p2) return p4;
               if(p==p3) return p1;
               if(p==p4) return p2;
           }
         }
        Conversation.printWSlnLog("Main", "Serious error - couldn't find partner!");
        return null;
    }
    
   
    
    public Participant getApparentPartner(Participant p){
         if(this.currentState==0){
              if(p==p1) return p2;
              if(p==p2) return p1;
              if(p==p3) return p4;
              if(p==p4) return p3;
         }
         else if(this.currentState==1){
             if(choice==choice1 || choice == choice4){  //SAME APPARENT PARTNER AS BEFORE
                 if(p==p1) return p2;
                 if(p==p2) return p1;
                 if(p==p3) return p4;
                 if(p==p4) return p3;
             }
             else if(choice==choice2||choice==choice3){  //DIFFERENT APPARENT
                if(p==p1) return p3;
               if(p==p2) return p4;
               if(p==p3) return p1;
               if(p==p4) return p2;   
             }
         }
        Conversation.printWSlnLog("Main", "Serious error - couldn't find partner!!!");
        return null;
    }
   

   

}
