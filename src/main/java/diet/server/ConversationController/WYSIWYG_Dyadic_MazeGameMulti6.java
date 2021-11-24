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
public class WYSIWYG_Dyadic_MazeGameMulti6 extends WYSIWYG_Dyadic implements  JInterfaceMenuButtonsReceiverInterface{

    
    MazeGameController2WAY mgc1, mgc2,mgc3,mgc4,mgc5,mgc6;
    Participant p01,p02,p03,p04,p05,p06,p07,p08,p09,p10,p11,p12;
    
    JInterfaceSinglePressButtonFIVE jispb;
    
    boolean tbtFIRST = CustomDialog.getBoolean("OPTION", "TBT then WYSIWYG", "WYSIWYG then TBT");
    
    
    public WYSIWYG_Dyadic_MazeGameMulti6(Conversation c) {
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

    public WYSIWYG_Dyadic_MazeGameMulti6(Conversation c, int numberOfTracks, int durationOfTimeout) {
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
    public boolean requestParticipantJoinConversation(String participantID) {
        Conversation.printWSln("Main", "Request to join conversation from "+participantID);
        return true;
        
    }

    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
        super.processWYSIWYGTextInserted(sender, mWYSIWYGkp); //To change body of generated methods, choose Tools | Templates.
    }

    public MazeGameController2WAY getControllerForParticipant(Participant p){
        if(p==this.p01 ) return this.mgc1; 
        if(p==this.p02 ) return this.mgc1; 
        
        if(p==this.p03 ) return this.mgc2; 
        if(p==this.p04 ) return this.mgc2;
        
        if(p==this.p05 ) return this.mgc3; 
        if(p==this.p06 ) return this.mgc3;
        
        if(p==this.p07 ) return this.mgc4;
        if(p==this.p08 ) return this.mgc4; 
        
        if(p==this.p09 ) return this.mgc5;
        if(p==this.p10 ) return this.mgc5;
        
        if(p==this.p11 ) return this.mgc6;
        if(p==this.p12 ) return this.mgc6; 
        
        return null;
    }
    
    
    
    
    public synchronized void processTaskMove(MessageTask mtm, Participant origin) {
        
         MazeGameController2WAY mgc = this.getControllerForParticipant(origin);
         if(mgc==null){
             Conversation.saveErr("Mazegamecontroller is null ");
             return;
         }
        
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
        if(c.getParticipants().getAllParticipants().size()==1) this.p01=p;
        if(c.getParticipants().getAllParticipants().size()==2) this.p02=p;
        if(c.getParticipants().getAllParticipants().size()==3) this.p03=p;
        if(c.getParticipants().getAllParticipants().size()==4) this.p04=p;
        if(c.getParticipants().getAllParticipants().size()==5) this.p05=p;
        if(c.getParticipants().getAllParticipants().size()==6) this.p06=p; 
        if(c.getParticipants().getAllParticipants().size()==7) this.p07=p;
        if(c.getParticipants().getAllParticipants().size()==8) this.p08=p;
        if(c.getParticipants().getAllParticipants().size()==9) this.p09=p;
        if(c.getParticipants().getAllParticipants().size()==10) this.p10=p;
        if(c.getParticipants().getAllParticipants().size()==11) this.p11=p;
        if(c.getParticipants().getAllParticipants().size()==12) this.p12=p;
        
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
            
            super.changeClientInterfaceToTurnByTurn(p01);
            super.changeClientInterfaceToTurnByTurn(p02);
            super.changeClientInterfaceToTurnByTurn(p03);
            super.changeClientInterfaceToTurnByTurn(p04);
            super.changeClientInterfaceToTurnByTurn(p05);
            super.changeClientInterfaceToTurnByTurn(p06);
            super.changeClientInterfaceToTurnByTurn(p07);
            super.changeClientInterfaceToTurnByTurn(p08);
            super.changeClientInterfaceToTurnByTurn(p09);
            super.changeClientInterfaceToTurnByTurn(p10);
            super.changeClientInterfaceToTurnByTurn(p11);
            super.changeClientInterfaceToTurnByTurn(p12);
                                
        }
        
        if(s.equalsIgnoreCase("wysiwyg")){
          if(singleTrackOption){
             this.changeClientInterfaceToRightJustified(p01,800,100,durationOfTextFadeout, 2,1);
             this.changeClientInterfaceToRightJustified(p02,800,100,durationOfTextFadeout, 2,1);
             this.changeClientInterfaceToRightJustified(p03,800,100,durationOfTextFadeout, 2,1);
             this.changeClientInterfaceToRightJustified(p04,800,100,durationOfTextFadeout, 2,1);
             this.changeClientInterfaceToRightJustified(p05,800,100,durationOfTextFadeout, 2,1);
             this.changeClientInterfaceToRightJustified(p06,800,100,durationOfTextFadeout, 2,1);
             this.changeClientInterfaceToRightJustified(p07,800,100,durationOfTextFadeout, 2,1);
             this.changeClientInterfaceToRightJustified(p08,800,100,durationOfTextFadeout, 2,1);
             this.changeClientInterfaceToRightJustified(p09,800,100,durationOfTextFadeout, 2,1);
             this.changeClientInterfaceToRightJustified(p10,800,100,durationOfTextFadeout, 2,1);
             this.changeClientInterfaceToRightJustified(p11,800,100,durationOfTextFadeout, 2,1);
             this.changeClientInterfaceToRightJustified(p12,800,100,durationOfTextFadeout, 2,1);
          }
          else{
            this.changeClientInterfaceToRightJustified(p01,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(p02,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(p03,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(p04,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(p05,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(p06,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(p07,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(p08,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(p09,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(p10,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(p11,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
            this.changeClientInterfaceToRightJustified(p12,800,100,durationOfTextFadeout, 2,sett.defaultGroupSize);
          }
        }     
        if(p01!=null)c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p01,s);
        if(p02!=null)c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p02,s); 
        if(p03!=null)c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p03,s);
        if(p04!=null)c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p04,s);
        if(p05!=null)c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p05,s);
        if(p06!=null)c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p06,s);
        if(p07!=null)c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p07,s);
        if(p08!=null)c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p08,s);
        if(p09!=null)c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p09,s);
        if(p10!=null)c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p10,s);
        if(p11!=null)c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p11,s);
        if(p12!=null) c.saveAdditionalRowOfDataToSpreadsheetOfTurns("actiontriggeredbyui", p12,s);
    }
    
    
    
    public void startexperimentChooseSet(){   
        MazeGameLoadMazesFromJarFile mglmfj = new MazeGameLoadMazesFromJarFile();
       
        Object[] options = mglmfj.setNames.toArray();
        String option = (String)CustomDialog.showComboBoxDialog("Load maze", "choose:", options, true);
        boolean doShuffle = CustomDialog.getBoolean("Do you want to randomize the order of the mazes?", "randomize", "use original order");
      
        Vector[] bothmazes = mglmfj.getExternalMazesASTEXT(option);
        if(doShuffle) bothmazes = mglmfj.shuffleMazes(bothmazes);
        
        if(p01==null){Conversation.printWSln("Main", "Can`t start because p01 is not logged in"); return;} 
        if(p02==null){Conversation.printWSln("Main", "Can`t start because p02 is not logged in"); return;} 
        if(p03==null){Conversation.printWSln("Main", "Can`t start because p03 is not logged in"); return;}
        if(p04==null){Conversation.printWSln("Main", "Can`t start because p04 is not logged in"); return;}
        if(p05==null){Conversation.printWSln("Main", "Can`t start because p05 is not logged in"); return;} 
        if(p06==null){Conversation.printWSln("Main", "Can`t start because p06 is not logged in"); return;} 
        if(p07==null){Conversation.printWSln("Main", "Can`t start because p07 is not logged in"); return;} 
        if(p08==null){Conversation.printWSln("Main", "Can`t start because p08 is not logged in"); return;} 
        if(p09==null){Conversation.printWSln("Main", "Can`t start because p09 is not logged in"); return;} 
        if(p10==null){Conversation.printWSln("Main", "Can`t start because p10 is not logged in"); return;} 
        if(p11==null){Conversation.printWSln("Main", "Can`t start because p11 is not logged in"); return;} 
        if(p12==null){Conversation.printWSln("Main", "Can`t start because p12 is not logged in"); return;} 
        
        mgc1 = new MazeGameController2WAY(c,bothmazes[0],bothmazes[1]);
        mgc1.startNewMazeGame(p01, p02);  
        
        bothmazes = mglmfj.getExternalMazesASTEXT(option);
        if(doShuffle) bothmazes = mglmfj.shuffleMazes(bothmazes);
        mgc2 = new MazeGameController2WAY(c,bothmazes[0],bothmazes[1]);
        mgc2.startNewMazeGame(p03, p04);  
        
        bothmazes = mglmfj.getExternalMazesASTEXT(option);
        if(doShuffle) bothmazes = mglmfj.shuffleMazes(bothmazes);
        mgc3 = new MazeGameController2WAY(c,bothmazes[0],bothmazes[1]);
        mgc3.startNewMazeGame(p05, p06);  
        
        bothmazes = mglmfj.getExternalMazesASTEXT(option);
        if(doShuffle) bothmazes = mglmfj.shuffleMazes(bothmazes);
        mgc4 = new MazeGameController2WAY(c,bothmazes[0],bothmazes[1]);
        mgc4.startNewMazeGame(p07, p08);  
        
        bothmazes = mglmfj.getExternalMazesASTEXT(option);
        if(doShuffle) bothmazes = mglmfj.shuffleMazes(bothmazes);
        mgc5 = new MazeGameController2WAY(c,bothmazes[0],bothmazes[1]);
        mgc5.startNewMazeGame(p09, p10);  
        
        bothmazes = mglmfj.getExternalMazesASTEXT(option);
        if(doShuffle) bothmazes = mglmfj.shuffleMazes(bothmazes);
        mgc6 = new MazeGameController2WAY(c,bothmazes[0],bothmazes[1]);
        mgc6.startNewMazeGame(p11, p12);  
        
        pp.createNewSubdialogue(p01,p02);
        pp.createNewSubdialogue(p03,p04);
        pp.createNewSubdialogue(p05,p06);
        pp.createNewSubdialogue(p07,p08);
        pp.createNewSubdialogue(p09,p10); 
        pp.createNewSubdialogue(p11,p12);
       
        
        
        itnt.addPairWhoAreMutuallyInformedOfTyping(p01, p02);
        itnt.addPairWhoAreMutuallyInformedOfTyping(p03, p04);
        itnt.addPairWhoAreMutuallyInformedOfTyping(p05, p06);
        itnt.addPairWhoAreMutuallyInformedOfTyping(p07, p08);
        itnt.addPairWhoAreMutuallyInformedOfTyping(p09, p10);
        itnt.addPairWhoAreMutuallyInformedOfTyping(p11, p12);
        
        
        
        
        
        
        
        
        Vector recipients = new Vector();
        recipients.addElement(p01); recipients.addElement(p02); recipients.addElement(p03); recipients.addElement(p04); recipients.addElement(p05); recipients.addElement(p06);
        recipients.addElement(p07); recipients.addElement(p08); recipients.addElement(p09); recipients.addElement(p10); recipients.addElement(p11); recipients.addElement(p12);
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
         
         MazeGameController2WAY mgc = this.getControllerForParticipant(p);
         if(mgc==null){
             Conversation.saveErr("AdditionalVector: Mazegamecontroller is null ");
             return new Vector();
         }
         
         
         
         
         
         
         if(mgc == null){
             return new Vector();
         }
         
         
         Vector additionalData = mgc.getAdditionalData(p);  
         if(additionalData==null) additionalData = new Vector();
         
         return additionalData;
    }
    
    
    public void performActionCalledByTaskController(String s, MazeGameController2WAY mgtc){
         System.err.println("PACBTC"+ s);
         
         if(this.tbtFIRST){
             if(s.equalsIgnoreCase("10")){
                 this.changeClientInterfaceToRightJustified(mgtc.pDirector,800,100,durationOfTextFadeout, 2,2);
                 this.changeClientInterfaceToRightJustified(mgtc.pMatcher,800,100,durationOfTextFadeout, 2,2);
                 CustomDialog.showModeLessDialog("Interface has changed to WYSIWYG. \nPerhaps explain to participants where to type", 10000);
             }
         }
         else if (!this.tbtFIRST){
             if(s.equalsIgnoreCase("10")){
                 super.changeClientInterfaceToTurnByTurn(mgtc.pDirector);
                 super.changeClientInterfaceToTurnByTurn(mgtc.pMatcher);
                 CustomDialog.showModeLessDialog("Interface has changed to TURN BY TURN. \nPerhaps explain to participants where to type", 10000);
             }
         }
         
    }
    
    
    
}
