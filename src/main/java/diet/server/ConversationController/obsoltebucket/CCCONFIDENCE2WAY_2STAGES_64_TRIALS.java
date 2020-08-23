package diet.server.ConversationController.obsoltebucket;
import diet.server.ConversationController.CCCONFIDENCE;
import diet.client.ClientInterfaceEvents.ClientInterfaceEvent;
import diet.message.*;
import diet.server.Conversation;

import diet.server.Participant;
//import diet.task.collabMinitaskProceduralComms.JSDM_4WAYConversationControllerINTERFACE;
import diet.task.stimuliset.ConfidenceTaskController;
import diet.task.stimuliset.ConfidenceTaskControllerSequenceSet;




public class CCCONFIDENCE2WAY_2STAGES_64_TRIALS extends CCCONFIDENCE {

    public CCCONFIDENCE2WAY_2STAGES_64_TRIALS(Conversation c) {
        super(c);
    }
   
    
    
    
    ConfidenceTaskControllerSequenceSet ctcss= new ConfidenceTaskControllerSequenceSet();
    ConfidenceTaskController ctc = new ConfidenceTaskController(this,ctcss.ctcseq1,"ctc","",64,100);
     
    public static boolean showcCONGUI() {
        return false;
    }
    
    

    
     public ConfidenceTaskController getTaskController(Participant p){
           return ctc;
     }
    
    
    
    
    
    @Override
    public void processClientEvent(Participant origin, MessageClientInterfaceEvent mcie) {
        super.processClientEvent(origin, mcie);
        ClientInterfaceEvent cie = mcie.getClientInterfaceEvent();
        String eventtype = cie.getType();
        if(!eventtype.equalsIgnoreCase("JFRAMESUBLIMINALSTIMULI")){
            return;
        }  
        
        String s = (String)cie.getAttribVal("actiontype").getVal();
        
        
       
      
        
        
        
        if(s.startsWith("INDIVIDUALSTIMULUS")){
            ConfidenceTaskController pctc = this.getTaskController(origin);
            pctc.updateWithClientEvent_IndividualStimulusInfo(origin, s);
        }
        else if (s.contains("jointquestionresponder")){
             ConfidenceTaskController pctc = this.getTaskController(origin);
             pctc.updateWithClientEvent_JointStimulusInfo(origin, s);
        }
        else if (s.contains("jointquestionnonresponder")){
            ConfidenceTaskController pctc = this.getTaskController(origin);
            pctc.updateWithClientEvent_JointStimulusInfo(origin, s);
        }
        
        
        
        
        
        
        
        
    }
    
    
   
   
    
  
    
    
    
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
       super.processChatText(sender, mct);
           
           
          
         
          
       
                   
    }
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
        //c.informIsTypingToAllowedParticipants(sender);//,mkp);
        c.saveClientKeypressToFile(sender, mkp);

    }

    
   
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
         
       
    }
    
    
    public void setSTime(long stime){
        this.ctc.setSTime(stime);
    }
    public void setFTime(long fixationtime ){
        this.ctc.setFTime(fixationtime);
    }
     public void setBTime(long backgroundtime ){
        this.ctc.setBTime(backgroundtime);
    }
    
    
    
    
    
    @Override
    public void processWYSIWYGTextRemoved(Participant sender,MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp){
        //c.relayWYSIWYGTextRemovedToAllowedParticipants(sender,mWYSIWYGkp);
        //turnBeingConstructed.remove(mWYSIWYGkp.getOffset(),mWYSIWYGkp.getLength(),mWYSIWYGkp.getTimeStamp().getTime()); 
        //chOut.addMessage(sender,mWYSIWYGkp);
    }
    
   

    @Override
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        ctc.participantJoinedConversation(p);
        if(c.getParticipants().getAllParticipants().size()>1)ctc.sendStartMessageToParticipants();
        //System.exit(-4); 
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
        super.participantRejoinedConversation(p);
        ctc.participantRejoinedConversation(p);
    }
    
   
   

    public void reset(){
        this.ctc.resetToStart();
    }
    
    
   

   

}
