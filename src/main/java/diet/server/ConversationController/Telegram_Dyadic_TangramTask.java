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
import diet.server.ParticipantGroups.ParticipantGroup;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTask;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTaskSettings;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTaskSettingsFactory;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import java.util.Hashtable;
import java.util.Vector;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 *
 * @author LX1C
 */
public class Telegram_Dyadic_TangramTask extends TelegramController{

    
    
    CustomizableReferentialTaskSettingsFactory crtsf = new CustomizableReferentialTaskSettingsFactory(this, true, "tangramset01" ,"tangramsequence.txt");
    CustomizableReferentialTaskSettings crts = crtsf.getNextCustomizableReferentialTaskSettings();
   
    
    //CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(this,true, "tangramset01", "tangramsequence.txt" );
   
    Hashtable ht_pg_crt = new Hashtable();
    Hashtable ht_p_crt = new Hashtable();

// CustomizableReferentialTask crt = new CustomizableReferentialTask(this, 5000,true);
   // Participant pDirector;
   // Participant pMatcher;
    
    
    public Telegram_Dyadic_TangramTask(Conversation c) {
        super(c);
    }

    public Telegram_Dyadic_TangramTask(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
    }

    
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
        
            
        
  
    }
    
    public CustomizableReferentialTask getTaskForParticipant(Participant p){
       return (CustomizableReferentialTask)this.ht_p_crt.get(p);
    }
    
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
       
    }
    
    Vector<TelegramParticipant> vQueued = new Vector();

   
     public void telegram_participantJoinedConversation(TelegramParticipant p) {
         
         if(vQueued.size()==0){
             vQueued.add(p);
             return;
         }
         else if(vQueued.size()==1){
             ParticipantGroup pg = pp.createNewSubdialogue(vQueued.elementAt(0),p);
             this.experimentHasStarted=true;
             CustomizableReferentialTask crt = new CustomizableReferentialTask(this,crts);
             
             this.ht_pg_crt.put(pg,crt);
             this.ht_p_crt.put(vQueued.elementAt(0), crt);
             this.ht_p_crt.put(p, crt);
             crt.startTask(vQueued.elementAt(0), p);
             vQueued.removeAllElements();
             
         }  
       
    }
    
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
        
      if(vQueued.size()==0){
             vQueued.add(p);
             return;
         }
         else if(vQueued.size()==1){
             ParticipantGroup pg = pp.createNewSubdialogue(vQueued.elementAt(0),p);
             this.experimentHasStarted=true;
             CustomizableReferentialTask crt = new CustomizableReferentialTask(this,crts);
             
             this.ht_pg_crt.put(pg,crt);
             this.ht_p_crt.put(vQueued.elementAt(0), crt);
             this.ht_p_crt.put(p, crt);
             crt.startTask(vQueued.elementAt(0), p);
             vQueued.removeAllElements();
             
         }  
    }
     

    @Override
    public void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             String text=tmfc.u.getMessage().getText();
             
              CustomizableReferentialTask crt = this.getTaskForParticipant(sender);
             if(crt!=null)crt.processChatText(sender, text);
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
            
            CustomizableReferentialTask crt = this.getTaskForParticipant(sender);
             if(crt!=null)crt.telegram_processButtonPress(sender, tmfc.u);
            
            //c.telegram_respondToCallback(sender, tmfc.u, "THIS IS THE RESPONSE");
            
            
           // String[][] nb = new String[][]{  {"a","b"}, {"c","d","e"}     };
            
            //c.telegram_sendEditMessageReplyMarkup(sender, tmfc.u,nb);
            
        }
        
        
        
    }

   

    
    
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
}
