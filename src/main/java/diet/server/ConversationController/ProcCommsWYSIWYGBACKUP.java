/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.attribval.AttribVal;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.DefaultWYSIWYGConversationControllerInterface;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.task.ProceduralComms.PCTask;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class ProcCommsWYSIWYGBACKUP extends DefaultWYSIWYGConversationControllerInterface {

   PCTask pct;
   long durationFadeout= 90000000;// = CustomDialog.getLong("What is the fadeout?", 90000000);
  
   
   boolean useNormalInterface = true;//CustomDialog.getBoolean("use normal interface?", "normal", "wysiwyg");
   
   
    public ProcCommsWYSIWYGBACKUP(Conversation c) {
        super(c, 2, 2500);
       // this.wysiwygtm.setAllOnSameTrack(false);
        //DefaultConversationController.sett.client_MainWindow_height= 800;
        //DefaultConversationController.sett.client_font_OTHER1_size=8;
        //DefaultConversationController.sett.client_font_OTHER2_size=8;
        //DefaultConversationController.sett.client_font_SELF_size=8;
        // DefaultConversationController.sett.client_font_SERVER_fontsize=8;
         
        
            durationFadeout = CustomDialog.getLong("What is the fadeout?", 90000000);
        
        
    }

    @Override
    public synchronized void participantJoinedConversation(Participant p) {
         // super.participantJoinedConversation(p); //To change body of generated methods, choose Tools | Templates.
         
        
          if(this.useNormalInterface){
              
          }
          else{
               durationFadeout = CustomDialog.getLong("What is the fadeout?", 90000000);
               this.changeClientInterfaceToRightJustified(p,800,100,durationFadeout, 2,2);
          }
          
        
        try{
            Thread.sleep(1000);
        }catch(Exception e){e.printStackTrace();}
        
        this.changeClientInterfaceCharacterWhitelist(p, "");
        c.changeClientInterface_setMaxTextEntryCharLength(p, 1);
        
        c.textOutputWindow_Initialize(p, "instructions", "instructions", "", 500, 600, false, true);
        if(c.getParticipants().getAllParticipants().size()==2 ){
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
             pct=new PCTask(this,c.getParticipants().getAllParticipants().elementAt(0), c.getParticipants().getAllParticipants().elementAt(1));
             this.changeClientInterfaceCharacterWhitelist(pct.pA, pct.pAWhitelist + pct.sharedWhitelist+pct.allowedMetaChars);
             this.changeClientInterfaceCharacterWhitelist(pct.pB, pct.pBWhitelist+  pct.sharedWhitelist+pct.allowedMetaChars);
             
        }
        
    }
    
    
    
    

    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
        if(this.useNormalInterface) return;
        if(pct!=null  && !   pct.allowedMetaChars.contains( mWYSIWYGkp.getTextTyped()))  pct.evaluate(sender, mWYSIWYGkp.getTextToAppendToWindow());
        super.processWYSIWYGTextInserted(sender, mWYSIWYGkp); //To change body of generated methods, choose Tools | Templates.     
    }

    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
        super.processChatText(sender, mct); //To change body of generated methods, choose Tools | Templates.    
        c.relayTurnToPermittedParticipants(sender, mct);
        
        
         if(pct!=null  && !   pct.allowedMetaChars.contains( mct.getText())) {
             pct.evaluate(sender, mct.getText());
             System.err.println("--TEXT:"+mct.getText().replace("\n", ""));
             //System.exit(-234);
         }
        
    
        
        
    }

    @Override
    public Vector<AttribVal> getAdditionalInformationForParticipant(Participant p) {
        Vector superGetAdditionalInformation = super.getAdditionalInformationForParticipant(p); //To change body of generated methods, choose Tools | Templates.
        Vector gameAdditionlInformation =  this.pct.getAdditionalValues(p);
        superGetAdditionalInformation.addAll(gameAdditionlInformation);
        return superGetAdditionalInformation;
    }

    
    
    
      
       public static boolean showcCONGUI(){
        return true;
    }     
    
    
    
    
}
