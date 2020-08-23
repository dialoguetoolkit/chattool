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
public class FaceComms2016_RAP_WYSIWYGDyadic  extends DefaultWYSIWYGConversationControllerInterface   {

    
    
   JInterfaceTenButtons jisp; 
   FaceCommsTaskControllerDyadic fctc;
    
    
    public FaceComms2016_RAP_WYSIWYGDyadic(Conversation c) {
        super(c, 2, 3000);
        this.setID("rapwysiwyg"); 
        fctc = new FaceCommsTaskControllerDyadic (this, 5000);
        
        
        sett.login_numberOfParticipants=2;
        sett.client_MainWindow_width=800;
       
    }
    
    

    
    
    @Override
    public synchronized void participantJoinedConversation(final Participant p) {
        this.changeClientInterfaceToRightJustified(p,800,100,durationOfTextFadeout, 2,2);
        c.textOutputWindow_Initialize(p, "instructions", "instructions", "", 500, 500, false, true);
        if(c.getParticipants().getAllParticipants().size()==2){
            Participant pA = (Participant)c.getParticipants().getAllParticipants().elementAt(0);
            Participant pB = (Participant)c.getParticipants().getAllParticipants().elementAt(1);
            pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
            this.itnt.setWhoSeesEachOthersTyping( pp);
            CustomDialog.showDialog("PRESS OK TO START THE EXPERIMENT!");
            fctc.startTask(pA, pB);
        }
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
        this.changeClientInterfaceToRightJustified(p,800,100,durationOfTextFadeout, 2,2);
        c.textOutputWindow_Initialize(p, "instructions", "instructions", "", 500, 500, false, true);
        super.participantRejoinedConversation(p); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    
     
    
    
    
    
    
    
    
   

    
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
      
       if(fctc==null){Conversation.printWSln("Main", "ERROR COULD NOT FIND TASKCONTROLLER FOR: "+sender.getUsername());}
       if(fctc!=null)fctc.processChatText(sender, mct.getText());        
        
    }

   
    
        
    

    @Override
    public void processButtonPress(Participant sender, MessageButtonPressFromClient mbfc) {
        //System.err.println("NAME: "+mbfc.buttonname);
        //System.err.println("SUBSTRING: "+mbfc.buttonname.substring(0, 1));
        
        //System.exit(-56);
        super.processButtonPress(sender, mbfc);
        
       if(fctc==null){Conversation.printWSln("Main", "ERROR COULD NOT FIND TASKCONTROLLER FOR: "+sender.getUsername());}
       if(fctc!=null) fctc.processChatText(sender, "/"+mbfc.buttonname.substring(0,1));
       
    }
    
    
   
    
    

    @Override
    public Vector getAdditionalInformationForParticipant(Participant p) {
        //return super.getAdditionalInformationForParticipant(p); //To change body of generated methods, choose Tools | Templates.
        
       
        
        Vector fctcadditionalvalues = new Vector();
       
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
                 
                 if(fctc==null){Conversation.printWSln("Main", "ERROR COULD NOT FIND TASKCONTROLLER FOR: "+sender.getUsername());}
                 if(fctc!=null) fctc.processChatText(sender, "/s");
                //System.exit(-5);
            }
            else if(mWYSIWYGkp.getTextTyped().contains("d")){
                
             
               if(fctc==null){Conversation.printWSln("Main", "ERROR COULD NOT FIND TASKCONTROLLER FOR: "+sender.getUsername());}
               if(fctc!=null)  fctc.processChatText(sender, "/d");
            }
        }
                
    }            
                
    
    
        public static boolean showcCONGUI(){
        return false;
    }     
       
        
       
    }
    
       
    
    
    
  
    
    


