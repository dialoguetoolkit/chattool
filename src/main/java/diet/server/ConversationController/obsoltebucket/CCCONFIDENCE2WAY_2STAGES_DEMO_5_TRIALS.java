package diet.server.ConversationController.obsoltebucket;
import diet.server.ConversationController.CCCONFIDENCE;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;

import diet.server.Conversation;

import diet.server.Participant;
//import diet.task.collabMinitaskProceduralComms.JSDM_4WAYConversationControllerINTERFACE;
import diet.task.stimuliset.ConfidenceTaskController;
import diet.task.stimuliset.ConfidenceTaskControllerSequenceSet;
import java.util.Vector;




public class CCCONFIDENCE2WAY_2STAGES_DEMO_5_TRIALS extends CCCONFIDENCE {

    public CCCONFIDENCE2WAY_2STAGES_DEMO_5_TRIALS(Conversation c) {
        super(c);
    }
    
    
    
    
    ConfidenceTaskControllerSequenceSet ctcss= new ConfidenceTaskControllerSequenceSet();
    ConfidenceTaskController ctc = new ConfidenceTaskController(this,ctcss.ctcseq1,"ctc","",5,100);
     
    public static boolean showcCONGUI() {
        return false;
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
