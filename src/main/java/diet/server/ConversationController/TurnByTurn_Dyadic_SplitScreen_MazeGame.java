/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.attribval.AttribVal;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.ConversationController.ui.JInterfaceSinglePressButtonFIVE;
import diet.server.Participant;
import diet.task.mazegame.MazeGameController2WAY;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class TurnByTurn_Dyadic_SplitScreen_MazeGame extends MazeGameConversationControllerMultipleDyads{

    boolean evenMazesAreSplitScreen = true;
    
    
    public TurnByTurn_Dyadic_SplitScreen_MazeGame(Conversation c) {
        super(c);
        this.setID("DP2015SPLITSCREEN");
        super.sett.client_numberOfWindows=2;
        
    }

    @Override
    public void startexperiment(boolean shuffle) {
        evenMazesAreSplitScreen = CustomDialog.getBoolean("Do you want even or odd mazes to be splitscreen?", "even", "odd");
        
        
        super.startexperiment(shuffle); 
     
        
    }

   
    
    
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct){    
          if(!this.experimentHasStarted){
              c.sendInstructionToParticipant(sender, "Please wait for the experiment to start");
              return;
          }
          
          this.itnt.processTurnSentByClient(sender);
          MazeGameController2WAY mgcNEW = this.getMazeGameController(sender);
          Vector additionalData = mgcNEW.getAdditionalData(sender);
          if(doSplitScreen(sender)){
              additionalData.addElement(new AttribVal("splitscreen","split"));
              c.relayTurnToPermittedParticipants(sender, mct,additionalData);        
              mgcNEW.appendToUI(sender.getUsername()+": "+mct.getText()); 
          }
          else{
             additionalData.addElement(new AttribVal("splitscreen","normal"));
             c.relayTurnToPermittedParticipants(sender, mct,additionalData);        
             mgcNEW.appendToUI(sender.getUsername()+": "+mct.getText()); 
             Participant pRecip = (Participant)pp.getRecipients(sender).elementAt(0);
             c.changeClientInterface_clearMainWindowsExceptWindow0(pRecip);
             
          }
              
    }
    
    

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
       if(!this.experimentHasStarted)return;
        if(doSplitScreen(sender) && this.experimentHasStarted){
            Participant recipient = (Participant)pp.getRecipients(sender).elementAt(0);
            Vector additionalValues = this.getAdditionalInformationForParticipant(sender);
            c.changeClientInterface_clearMainWindowsExceptWindow0(recipient);
            c.sendArtificialTurnFromApparentOrigin(    sender, recipient, mWYSIWYGkp.getAllTextInWindow()  , 1, additionalValues);
        }
        
        
    }

    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
        if(!this.experimentHasStarted)return;
        if(doSplitScreen(sender) && this.experimentHasStarted){
            System.err.println("THE TEXT TYPED IS: "+mWYSIWYGkp.getTextTyped());
            Participant recipient = (Participant)pp.getRecipients(sender).elementAt(0);
            Vector additionalValues = this.getAdditionalInformationForParticipant(sender);
            c.changeClientInterface_clearMainWindowsExceptWindow0(recipient);
            c.sendArtificialTurnFromApparentOrigin(sender, recipient, mWYSIWYGkp.getAllTextInWindow()  , 1, additionalValues);
        }
    }
    
    
    public boolean doSplitScreen(Participant p){
        MazeGameController2WAY mgc = this.getMazeGameController(p);
        int mazeno = mgc.getMazeNo();
        if(mazeno % 2 == 0){
            if(this.evenMazesAreSplitScreen) return true;            
            return false;
        }
        else {
            if(this.evenMazesAreSplitScreen) return false;   
            return true;
        }
        
    }
    
      public static boolean showcCONGUI(){
        return false;
    } 
    
}
