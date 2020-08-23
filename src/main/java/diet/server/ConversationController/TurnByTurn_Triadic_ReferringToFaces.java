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
import diet.server.Participant;
import diet.task.FaceComms.FaceCommsTaskControllerDyadic;
import diet.task.FaceComms.FaceCommsTaskControllerTriadic;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class TurnByTurn_Triadic_ReferringToFaces extends DefaultConversationController{

    
    FaceCommsTaskControllerTriadic fctct;
   
    Participant pA;
    Participant pB;
    Participant pCExcluded;
    
   
    
    
    public TurnByTurn_Triadic_ReferringToFaces(Conversation c) {
        super(c);
        sett.client_MainWindow_width=800;
        sett.login_numberOfParticipants=3;
        fctct = new FaceCommsTaskControllerTriadic (this);
        
        
    }

    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
       
              
        
    }

    @Override
    public synchronized void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        c.textOutputWindow_Initialize(p, "instructions", "instructions", "", 500, 500, false, true);
        
         if(c.getParticipants().getAllParticipants().size()==3){
            pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
           
            int choiceOfGroup =r.nextInt(3);
            if(choiceOfGroup==0){
                pCExcluded = (Participant)c.getParticipants().getAllParticipants().elementAt(0);
                pA = (Participant)c.getParticipants().getAllParticipants().elementAt(1);
                pB = (Participant)c.getParticipants().getAllParticipants().elementAt(2);
            }
            else if(choiceOfGroup==1){
                pCExcluded = (Participant)c.getParticipants().getAllParticipants().elementAt(1);
                pA = (Participant)c.getParticipants().getAllParticipants().elementAt(0);
                pB = (Participant)c.getParticipants().getAllParticipants().elementAt(2);
            }
            else if(choiceOfGroup==2){
                pCExcluded = (Participant)c.getParticipants().getAllParticipants().elementAt(2);
                pA = (Participant)c.getParticipants().getAllParticipants().elementAt(0);
                pB = (Participant)c.getParticipants().getAllParticipants().elementAt(1);
                
            }
            CustomDialog.showDialog("START EXPERIMENT");
            this.experimentHasStarted=true;
            fctct.startTask(pA, pB, pCExcluded);    
        }
        
       
        
        
        
        
        
        
        
    }
    
    
    
    
    

    @Override
    public Vector getAdditionalInformationForParticipant(Participant p) {
        Vector fctcadditionalvalues = this.fctct.getAdditionalValues(p);
        if(p==this.pCExcluded){
            AttribVal av1 = new AttribVal("ROLE","PC");
            fctcadditionalvalues.addElement(av1);
        }
        else if (p==this.pA){
            AttribVal av1 = new AttribVal("ROLE","PA");
             fctcadditionalvalues.addElement(av1);
        }
        else if (p==this.pB){
            AttribVal av1 = new AttribVal("ROLE","PB");
            fctcadditionalvalues.addElement(av1);
        }
        else{
             AttribVal av1 = new AttribVal("ROLE","OTHER");
             fctcadditionalvalues.addElement(av1);
        }
        fctcadditionalvalues.addAll(super.getAdditionalInformationForParticipant(p));  
        return fctcadditionalvalues;
    }

    @Override
    public void processKeyPress(Participant sender, MessageKeypressed mkp) {
         super.processKeyPress(sender, mkp); //To change body of generated methods, choose Tools | Templates.
         
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
        itnt.processDoingsByClient(sender);
       
       
         
        
    }

    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
      
    }
  
   

    
     @Override
    public void processButtonPress(Participant sender, MessageButtonPressFromClient mbfc) {
       // this.fctc.processChatText(sender, "/"+mbfc.buttonname.substring(1));
    }
    
     public boolean performManipulation(){
        if(this.fctct.getTrialNo()==0)return true;
        if(this.fctct.getTrialNo() % 20 !=0) return true;
        return false;
    }  
    
     
     
       public static boolean showcCONGUI(){
        return true;
    }     
       
    
}
