/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.message.MessageChatTextFromClient;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.Participant;
import diet.task.mazegame.MazeGameController2WAY;
import diet.textmanipulationmodules.CyclicRandomTextGenerators.CyclicRandomEnglishDemoSelfRepairInitiation;
import diet.textmanipulationmodules.Selfrepairgeneration.SelfRepair;
import diet.utils.HashtableOfLong;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class TurnByTurn_Dyadic_MazeGame_ArtificialSelfRepairs extends MazeGameConversationControllerMultipleDyads {

    //CyclicRandomDutchSelfRepairInitiation crdsr = new CyclicRandomDutchSelfRepairInitiation();
      CyclicRandomEnglishDemoSelfRepairInitiation crdsr = new CyclicRandomEnglishDemoSelfRepairInitiation();
    //EnglishDemoSelfRepairInitiation
    SelfRepair sr = new SelfRepair();
     HashtableOfLong htol = new HashtableOfLong(0);
    
    
    public TurnByTurn_Dyadic_MazeGame_ArtificialSelfRepairs(Conversation c) {
        super(c);
        this.setID("DP2015_SELFREPAIR");
    }

    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
      
        if(!this.experimentHasStarted){
              c.sendInstructionToParticipant(sender, "Please wait for the experiment to start");
              return;
        }
        
        String[] splittext = mct.getText().split(" ");
        String output = "";
        for(int i=0;i<splittext.length&&splittext.length>0;i++){
            String val = splittext[i];
            if(!val.equalsIgnoreCase("ok")){
                output = output + val + " ";    
            }       
        }
        mct.setText(output);
        if(splittext[0].equalsIgnoreCase("ok")&& splittext.length==1){
            mct.setText("hmmm...");
        }
        
        
       
        MazeGameController2WAY mgccc = this.getMazeGameController(sender);
        Participant pRecipient = (Participant)this.pp.getRecipients(sender).elementAt(0);
        
        long mostRecentIntervention = htol.get(sender);
        long currentTurnNo = sender.getNumberOfChatMessagesProduced();
        if(currentTurnNo-mostRecentIntervention < 0){  //Originally it was 2
                  
                   System.err.println("Not even trying to look for intervention - 5 turns haven't elapsed yet");
                   Conversation.printWSln("Main", "Not triggering delete because delete was in previous turn");
                   this.itnt.processTurnSentByClient(sender);
                   MazeGameController2WAY mgcNEW = this.getMazeGameController(sender);
                   Vector additionalData = mgcNEW.getAdditionalData(sender);  
                   c.relayTurnToPermittedParticipants(sender, mct,additionalData);        
                   mgcNEW.appendToUI(sender.getUsername()+": "+mct.getText());      
                   sr.setNewTurn(sender);
                   return;
        }
         String[] modifiedTurn = sr.getFirstPart_SecondPart_SummaryWithErrorCheck(sender);
         if(modifiedTurn[0]==null||modifiedTurn[1]==null){
               System.err.println("Couldnt generate delete...so is relaying");
               Conversation.printWSln("Main", "Couldnt generate delete...so is relaying");
               this.itnt.processTurnSentByClient(sender);
               MazeGameController2WAY mgcNEW = this.getMazeGameController(sender);
               Vector additionalData = mgcNEW.getAdditionalData(sender);  
               c.relayTurnToPermittedParticipants(sender, mct,additionalData);        
               mgcNEW.appendToUI(sender.getUsername()+": "+mct.getText());      
               sr.setNewTurn(sender);
               return;
               
           }
           else{
              String repairInitiation = crdsr.getNext(sender);
              String fullChangedTurn = modifiedTurn[0]+" "+repairInitiation+" "+ modifiedTurn[1];
             /// c.sendArtificialMazeGameTurnFromApparentOriginToRecipientWithEnforcedTextColour(sender.getUsername(), pRecipient, fullChangedTurn, 0, info, 1);
              
             this.itnt.processTurnSentByClient(sender);
             MazeGameController2WAY mgcNEW = this.getMazeGameController(sender);
             Vector additionalData = mgcNEW.getAdditionalData(sender);  
                 
             c.sendArtificialTurnFromApparentOrigin(sender, pRecipient, fullChangedTurn);
               
             mgcNEW.appendToUI("*"+sender.getUsername()+": "+mct.getText());
              mgcNEW.appendToUI("**"+sender.getUsername()+": "+fullChangedTurn);
             sr.setNewTurn(sender);
              
              
              
              
              itnt.processTurnSentByClient(sender);
              mgccc.appendToUI("FAKEREPAIR: "+sender.getUsername()+": "+mct.getText());
              sr.setNewTurn(sender);
              this.htol.put(sender, sender.getNumberOfChatMessagesProduced());
              return;
                   
           }
                
                
                
                
    }

    @Override
    public synchronized void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p); //To change body of generated methods, choose Tools | Templates.
        c.sendInstructionToParticipant(p, "Demo setup - 3 scenarios");
    }
    
    

    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
        this.updateInfoAsItIsTyped(sender, mWYSIWYGkp.getAllTextInWindow(),false);
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
         this.updateInfoAsItIsTyped(sender, mWYSIWYGkp.getAllTextInWindow(),true);
    }
     
    
    
    
    public synchronized void updateInfoAsItIsTyped(Participant sender, String textInBoxOLD, boolean updateISREMOVE){
           String textInBox ="";
           try{
              //textInBox = "PRESET"+textInBoxOLD.replaceAll("\n", "").replace("\n", "").replaceAll("\r\n", "").replaceAll("\r", "")+"ENDSET";
               textInBox = textInBoxOLD.replaceAll("\n", "").replace("\n", "").replaceAll("\r\n", "").replaceAll("\r", "");
               this.sr.addRevision(sender, textInBox);
               
               
           }catch(Exception e){e.printStackTrace();}
           
          
        
          
    }    

      public static boolean showcCONGUI(){
        return true;
    }     
}
