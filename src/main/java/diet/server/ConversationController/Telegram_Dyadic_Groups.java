/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.ConversationController.ui.JInterfaceMenuButtonsReceiverInterface;
import diet.server.ConversationController.ui.JInterfaceTwelveButtons;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import java.util.Vector;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 *
 * @author LX1C
 */
public class Telegram_Dyadic_Groups extends TelegramController implements JInterfaceMenuButtonsReceiverInterface{

    
   // CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(this,true, "tangramset01", "tangramsequence.txt" );
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crts);
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, 5000,true);
   // Participant pDirector;
   // Participant pMatcher;
    
    JInterfaceTwelveButtons jitb = new JInterfaceTwelveButtons(this,"Assign to groups","start", "pause", "", "", "", "", "", "", "");

    
    public Telegram_Dyadic_Groups(Conversation c) {
        super(c);
    }

    public Telegram_Dyadic_Groups(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
    }

    
    
   @Override
    public void performActionTriggeredByUI(String s) {
        if(s.equalsIgnoreCase("Assign to groups")){
             assignToGroups();
        }    
    }
    
    public void assignToGroups(){
        while(this.vQueued.size()>1)
        
        
        
         pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
             //this.itnt.addGroupWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants());
              
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
    }
    
    Vector<TelegramParticipant> vQueued = new Vector();
   
     public void telegram_participantJoinedConversation(TelegramParticipant p) {
          vQueued.add(p);
          Conversation.printWSln("Main", "Added "+p.getParticipantID()+ " size of queue is: "+vQueued.size());
     }
    
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
        
      // c.telegram_sendPoll(p, "This is a questionnaire","What is it?", new String[]{"option1", "option2", "option3", "option4"});
        
      // c.telegram_sendInstructionToParticipantWithForcedKeyboardButtons(p, "This is a question",  new String[]{"a", "b", "c", "d", "e", "f", "g"},3);
        
      Message m = c.telegram_sendInstructionToParticipant_MonospaceFont(p, "this is the initial message from the serverA");
      
      c.telegram_sendPinChatMessageToParticipant(p, m);
      
      c.telegram_sendEditMessageToParticipant(p, m, "This is the replacement textB");
      
      //c.deprecated_telegram_sendEditMessage(p, sm, "this is edited");
      
       if(c.getParticipants().getAllParticipants().size()==2) {
            
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
             //this.itnt.addGroupWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants());
              
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
            
        }
    }
     

    @Override
    public void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             String text=tmfc.u.getMessage().getText();
            // this.crt.processChatText(sender, text);
             if(!text.startsWith("/")){
                c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);      
             } 
      
        }
        if(this.relayPhotos && tmfc.u.hasMessage()&&  tmfc.u.getMessage().hasPhoto()){
             c.telegram_relayMessagePhotoToOtherParticipants_By_File_ID(sender, tmfc);    
        }
        if(this.relayVoice && tmfc.u.hasMessage()&& tmfc.u.getMessage().hasVoice() ){
             c.telegram_relayMessageVoiceToOtherParticipants_By_File_ID(sender, tmfc);
        }
        if(tmfc.u.hasCallbackQuery()){
            CallbackQuery cbq = tmfc.u.getCallbackQuery();
            Message  m =cbq.getMessage();
            String callbackData =   cbq.getData();
            System.err.println("callbackdata: "+callbackData);
            
           // crt.telegram_processButtonPress(sender, tmfc.u);
            
            //c.telegram_respondToCallback(sender, tmfc.u, "THIS IS THE RESPONSE");
            
            
           // String[][] nb = new String[][]{  {"a","b"}, {"c","d","e"}     };
            
            //c.telegram_sendEditMessageReplyMarkup(sender, tmfc.u,nb);
            
        }
        
        
        
    }

   

    
    
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
}
