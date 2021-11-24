/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.message.MessageChatTextFromClient;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import java.util.Vector;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 *
 * @author LX1C
 */
public class Telegram_Multiparty extends TelegramController{

    
   // CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(this,true, "tangramset01", "tangramsequence.txt" );
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crts);
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, 5000,true);
   // Participant pDirector;
   // Participant pMatcher;
    
    
    public Telegram_Multiparty(Conversation c) {
        super(c);
    }

    public Telegram_Multiparty(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
    }

    
    
  

   
     public void telegram_participantJoinedConversation(TelegramParticipant p) {
         
        //Each time there is a new participant, a new group is created with all members in it.
        
        
        pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
        
         
        
    }
    
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
       //Each time there is a new participant, a new group is created with all members in it.
      
          pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
        
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
