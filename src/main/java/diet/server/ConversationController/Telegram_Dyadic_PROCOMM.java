/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.task.ProceduralComms.PCTaskTG;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import java.util.Hashtable;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 *
 * @author LX1C
 */
public class Telegram_Dyadic_PROCOMM extends TelegramController{

    
    //CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(this,true, null, null );
    //CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crts);
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, 5000,true);
   // Participant pDirector;
   // Participant pMatcher;
    
    
    public Telegram_Dyadic_PROCOMM(Conversation c) {
        super(c);
        
    }

    public Telegram_Dyadic_PROCOMM(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
        
        
    }

    
    
    
    
    
    
    

   
     public void telegram_participantJoinedConversation(TelegramParticipant p) {
         
         this.generatePinnedMessage(p);
         
        if(c.getParticipants().getAllParticipants().size()==2) {
            
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
             //this.itnt.addGroupWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants());
              
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
             pctg = new PCTaskTG(this,(TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(0), (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(1));
             
             
        }
    }
    
     
     
     
     
     
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
        
        this.generatePinnedMessage(p);
        
       // c.telegram_sendPoll(p, "This is a questionnaire","What is it?", new String[]{"option1", "option2", "option3", "option4"});
        
       // c.telegram_sendInstructionToParticipantWithForcedKeyboardButtons(p, "This is a question",  new String[]{"option1", "option2", "option3", "option4", "option5", "option6", "option7"},3);
        
       if(c.getParticipants().getAllParticipants().size()==2) {
            
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
             //this.itnt.addGroupWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants());
              
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
              pctg = new PCTaskTG(this,(TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(0), (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(1));
             
        }
    }
     

    @Override
    public synchronized void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             String text = tmfc.u.getMessage().getText();
             if(text.startsWith("/menu")){
                 generatePinnedMessage(sender);
             }
             else{
                 c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);   
                 this.pctg.evaluate(sender, text);
                 
                 
             }
             
             
             
        }          
        else{    
             c.telegram_sendInstructionToParticipant(sender, "Please only send text");
        }
    
    }
    
    
    
    
     PCTaskTG pctg;
     public Hashtable htPinnedMessages = new Hashtable();
     public Hashtable<TelegramParticipant,String> htMostRecentPinnedText = new Hashtable();
    
    
    public void generatePinnedMessage(TelegramParticipant p){ 
        Message m = c.telegram_sendInstructionToParticipant(p, "Please do not close this message. You will need it in the task");
        c.telegram_sendPinChatMessageToParticipant(p, m);
        htPinnedMessages.put(p, m);      
    }
    
     public void changePinnedMessage(TelegramParticipant p,String text){
          String mostRecent = this.htMostRecentPinnedText.get(p);
          if(mostRecent!=null){
              if(mostRecent.equalsIgnoreCase(text))return;
          }
         
         
           org.telegram.telegrambots.meta.api.objects.Message m = (org.telegram.telegrambots.meta.api.objects.Message)this.htPinnedMessages.get(p);
           if(m!=null){
               c.telegram_sendEditMessageToParticipant(p, m, text);
               htMostRecentPinnedText.put(p,text);
           }
     }
    
    
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
   
   
    
   
   
   
   
   
   
}







