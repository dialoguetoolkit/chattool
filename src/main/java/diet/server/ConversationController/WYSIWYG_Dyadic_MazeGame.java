/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.message.MessageChatTextFromClient;
import diet.message.MessageTask;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.ConversationController.ui.JInterfaceMenuButtonsReceiverInterface;
import diet.server.ConversationController.ui.JInterfaceSinglePressButtonFIVE;
import diet.server.Participant;
import diet.task.mazegame.MazeGameController2WAY;
import diet.task.mazegame.MazeGameLoadMazesFromJarFile;
import diet.task.mazegame.message.MessageCursorUpdate;
import java.util.Vector;

/**
 *
 * @author LX1C
 */
public class WYSIWYG_Dyadic_MazeGame extends WYSIWYG_Dyadic implements  JInterfaceMenuButtonsReceiverInterface{

    
    MazeGameController2WAY mgc;
    Participant pA,pB;
    
    JInterfaceSinglePressButtonFIVE jispb;
    
    boolean tbtFIRST = CustomDialog.getBoolean("OPTION", "TBT then WYSIWYG", "WYSIWYG then TBT");
    
    
    public WYSIWYG_Dyadic_MazeGame(Conversation c) {
        super(c, 2, 3000);
        jispb = new JInterfaceSinglePressButtonFIVE("Choose :",  this, "start", "wysiwyg", "wysiwyg","tbt","tbt");
        //this.tbtFIRST=false;//r.nextBoolean();
        if(tbtFIRST){
            CustomDialog.showDialog("This experiment will have the Turn by turn interface for games 0-9 \n"
                    + "On game 10 it will swap to the Character by character What You See Is What You Get Interface"); 
        }
        else{
              CustomDialog.showDialog("This experiment will have the Character by character What You See Is What You Get Interface for games 0-9 \n"
                    + "On game 10 it will swap to the Turn by turn interface."); 
        }
                
       
    }

    public WYSIWYG_Dyadic_MazeGame(Conversation c, int numberOfTracks, int durationOfTimeout) {
        super(c, numberOfTracks, durationOfTimeout);
        jispb = new JInterfaceSinglePressButtonFIVE("Choose:",  this, "start", "", "wysiwyg","","turnbyturn");
        //this.tbtFIRST=false;//r.nextBoolean();
        if(tbtFIRST){
            CustomDialog.showDialog("This experiment will have the Turn by turn interface for games 0-9 \n"
                    + "On game 10 it will swap to the Character by character What You See Is What You Get Interface"); 
        }
        else{
              CustomDialog.showDialog("This experiment will have the Character by character What You See Is What You Get Interface for games 0-9 \n"
                    + "On game 10 it will swap to the Turn by turn interface."); 
        }
        
    }

    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
        super.processWYSIWYGTextInserted(sender, mWYSIWYGkp); //To change body of generated methods, choose Tools | Templates.
    }

    
    public synchronized void processTaskMove(MessageTask mtm, Participant origin) {
        
        
         boolean mazeIsCompleted = mgc.isCompleted();
         if(mazeIsCompleted){
             Conversation.printWSln("Main", "Possible network lag and/or error. Experiment is receiving task moves from "+origin.getUsername()+"..."+"the current maze is: "+mgc.getMazeNo());  
             return;
         }
         if( mtm instanceof MessageCursorUpdate){
             MessageCursorUpdate mcu = (MessageCursorUpdate)mtm;
             if(mcu.getMazeNo() != mgc.getMazeNo()){
                  Conversation.printWSln("Main", "Possible network lag and/or error. Experiment is receiving maze cursor updates from "+origin.getUsername()+"...from maze number "+mcu.getMazeNo()+"..."+"but the current maze is: "+mgc.getMazeNo());
                  return;
             }
         }

         mazeIsCompleted = mgc.processMazeMove(mtm, origin,false);
         
         
    }
    
    
    
    
    
    
    @Override
    public synchronized void participantJoinedConversation(Participant p) {
        if(c.getParticipants().getAllParticipants().size()==1) this.pA=p;
        if(c.getParticipants().getAllParticipants().size()==2) this.pB=p;
        
        //super.participantJoinedConversation(p); //To change body of generated methods, choose Tools | Templates.
        
        if(this.tbtFIRST){
           //Don't need to do anything    
        }
        else{
            this.changeClientInterfaceToRightJustified(p,800,100,durationOfTextFadeout, 2,2);
            //this.changeClientInterfaceToRightJustified(pB,800,100,durationOfTextFadeout, 2,2);
        }
        
        
        
        
    }
    
    @Override
    public void performActionTriggeredByUI(String s) {
        if(s.equalsIgnoreCase("START"))startexperimentChooseSet();
        
        
        if(s.equalsIgnoreCase("tbt")){
            
            super.changeClientInterfaceToTurnByTurn(pA);
            super.changeClientInterfaceToTurnByTurn(pB);
        }
        
        if(s.equalsIgnoreCase("wysiwyg")){
          if(singleTrackOption){
             this.changeClientInterfaceToRightJustified(pA,800,100,durationOfTextFadeout, 2,1);
              this.changeClientInterfaceToRightJustified(pB,800,100,durationOfTextFadeout, 2,1);
          }
          else{
            this.changeClientInterfaceToRightJustified(pA,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(pB,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
          }
        }     
        c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", pA,s);
        c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", pB,s);
    }
    
    
    
    public void startexperimentChooseSet(){   
        MazeGameLoadMazesFromJarFile mglmfj = new MazeGameLoadMazesFromJarFile();
       
        Object[] options = mglmfj.setNames.toArray();
        String option = (String)CustomDialog.showComboBoxDialog("Load maze", "choose:", options, true);
        boolean doShuffle = CustomDialog.getBoolean("Do you want to randomize the order of the mazes?", "randomize", "use original order");
      
        Vector[] bothmazes = mglmfj.getExternalMazesASTEXT(option);
        if(doShuffle) bothmazes = mglmfj.shuffleMazes(bothmazes);
        mgc = new MazeGameController2WAY(c,bothmazes[0],bothmazes[1]);
        mgc.startNewMazeGame(pA, pB);  
        
        pp.createNewSubdialogue(pA,pB);
        itnt.addPairWhoAreMutuallyInformedOfTyping(pA, pB);
        Vector recipients = new Vector();
        recipients.addElement(pA); recipients.addElement(pB);
        c.sendInstructionToMultipleParticipants(recipients, "Please start!");
        
        this.experimentHasStarted=true;
        
      }

    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
         itnt.processTurnSentByClient(sender);
         c.relayTurnToPermittedParticipants(sender, mct);
    }
    
    
    
    
    
    
     
    @Override
    public Vector getAdditionalInformationForParticipant(Participant p) {
         if(!this.experimentHasStarted){
            return new Vector();
         }
         if(mgc == null){
             return new Vector();
         }
         
         
         Vector additionalData = mgc.getAdditionalData(p);  
         if(additionalData==null) additionalData = new Vector();
         
         return additionalData;
    }
    
    
    public void performActionCalledByTaskController(String s){
         System.err.println("PACBTC"+ s);
         
         if(this.tbtFIRST){
             if(s.equalsIgnoreCase("10")){
                 this.changeClientInterfaceToRightJustified(pA,800,100,durationOfTextFadeout, 2,2);
                 this.changeClientInterfaceToRightJustified(pB,800,100,durationOfTextFadeout, 2,2);
                 CustomDialog.showModeLessDialog("Interface has changed to WYSIWYG. \nPerhaps explain to participants where to type", 10000);
             }
         }
         else if (!this.tbtFIRST){
             if(s.equalsIgnoreCase("10")){
                 super.changeClientInterfaceToTurnByTurn(pA);
                 super.changeClientInterfaceToTurnByTurn(pB);
                 CustomDialog.showModeLessDialog("Interface has changed to TURN BY TURN. \nPerhaps explain to participants where to type", 10000);
             }
         }
         
    }
    
    
    
}
