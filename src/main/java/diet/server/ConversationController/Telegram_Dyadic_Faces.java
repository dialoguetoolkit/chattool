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
import diet.task.CustomizableReferentialTask.CustomizableReferentialTask;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTaskSettings;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTaskSettingsFactory;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 *
 * @author LX1C
 */
public class Telegram_Dyadic_Faces extends TelegramController{

    
    
    CustomizableReferentialTaskSettingsFactory crtsf = new CustomizableReferentialTaskSettingsFactory(this, true, "rorschachset02" ,"rorschachsequence01.txt");
    CustomizableReferentialTaskSettings crts = crtsf.getNextCustomizableReferentialTaskSettings();
    
    
    //CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(this,true, "rorschachset02" ,"rorschachsequence01.txt");
    CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crts);
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, 5000,true);
   // Participant pDirector;
   // Participant pMatcher;
    
    
    public Telegram_Dyadic_Faces(Conversation c) {
        super(c);
    }

    public Telegram_Dyadic_Faces(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
    }

    
    

   
     public void telegram_participantJoinedConversation(TelegramParticipant p) {
        if(c.getParticipants().getAllParticipants().size()==2) {
            
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
              
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
             crt.startTask((Participant)c.getParticipants().getAllOtherParticipants(p).elementAt(0), p);
        }
    }
    
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
        
        
       if(c.getParticipants().getAllParticipants().size()==2) {
            
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
             //this.itnt.addGroupWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants());
              
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
             crt.startTask((Participant)c.getParticipants().getAllOtherParticipants(p).elementAt(0), p);
        }
    }
     

    @Override
    public void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             String text=tmfc.u.getMessage().getText();
             this.crt.processChatText(sender, text);
             if(!text.startsWith("/")){
                c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);      
             } 
      
        }
        if(this.relayPhotos && tmfc.u.hasMessage()&&  tmfc.u.getMessage().hasPhoto()){
             c.telegram_relayMessagePhotoToOtherParticipants_By_File_ID(sender, tmfc);    
        }
        
        if(tmfc.u.hasCallbackQuery()){
            CallbackQuery cbq = tmfc.u.getCallbackQuery();
            Message  m =cbq.getMessage();
            String callbackData =   cbq.getData();
            System.err.println("callbackdata: "+callbackData);
            
            crt.telegram_processButtonPress(sender, tmfc.u);
            
            //c.telegram_respondToCallback(sender, tmfc.u, "THIS IS THE RESPONSE");
            
            
           // String[][] nb = new String[][]{  {"a","b"}, {"c","d","e"}     };
            
            //c.telegram_sendEditMessageReplyMarkup(sender, tmfc.u,nb);
            
        }
        
        
        
    }

   

    
    
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
}
