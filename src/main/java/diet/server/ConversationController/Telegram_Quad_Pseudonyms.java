/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
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
public class Telegram_Quad_Pseudonyms extends TelegramController{

    
   // CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(this,true, "tangramset01", "tangramsequence.txt" );
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crts);
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, 5000,true);
   // Participant pDirector;
   // Participant pMatcher;
    
    Vector vnames = new Vector();
    //Vector vnamesunassigned = new Vector();
    
    
    
    public Telegram_Quad_Pseudonyms(Conversation c) {
        super(c);
        vnames = new Vector(){}; vnames.addElement("Pluto"); vnames.addElement("Neptune"); vnames.addElement("Saturn"); vnames.addElement("Jupiter");
    }

    public Telegram_Quad_Pseudonyms(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
         vnames = new Vector(){}; vnames.addElement("Pluto"); vnames.addElement("Neptune"); vnames.addElement("Saturn"); vnames.addElement("Jupiter");
       
    }

    
    
  

   
    
     

       
  
     
     
     public String getNextFreeUsername(){
         // String randomname = (String)vnames.elementAt(r.nextInt(vnames.size()));
          
          
          //vnames.remove(randomname);
          
          
          String nextname = (String)vnames.elementAt(0);
          vnames.remove(nextname);
        
          return nextname;  
     }
     
     
     Hashtable htNames = new Hashtable();
     
     public String getPseudonym(TelegramParticipant tp){
         String s = (String) this.htNames.get(tp);
         if(s==null){
             Conversation.printWSln("Main", "Error - "+tp.getParticipantID()+ " doesn`t have a pseudonym");
             return "Moon";
         }
         return s;
     }
    
   
     
      public  final String[] telegram_getUniqueParticipantIDAndUniqueUsername(long telegramID ,String logincodeattempt){
         Conversation.printWSln("Main", "TelegramController receiving login request from "+ telegramID + " with "+ logincodeattempt  );
         if(logincodeattempt.equalsIgnoreCase(this.c.tgb.idc.idcIO.genericLoginCode)){  // This reference needs to be fixed / simplified
               
               String[] unique = c.getParticipants().pang.generateNextIDUsername_Format_PREFIXPLUSNUMBER("id","p"); 
               unique[1]=this.getNextFreeUsername();
               
               
               
               
               Conversation.printWSln("Main", "TelegramController returning unique ID and Username for "+ telegramID +": ParticipantID:"+unique[0]+".  Username:"+unique[1] );
               
               return unique;
               
               //return c.getParticipants().pang.generateNextIDUsername_Format_PREFIXPLUSNUMBER("id","p");     
         }
         else{
           Conversation.printWSln("Main", "TelegramController preventing access of "+ telegramID + " to ConversationController"  );
               
             return new String[]{"",""};
         }
      }
     
      
      
     public void telegram_participantJoinedConversation(TelegramParticipant p) {
         
        //Each time there is a new participant, a new group is created with all members in it.
        
        if(c.getParticipants().getAllParticipants().size()==2) startExperiment();
        
         
        
    }
     
     
     
     
     
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
       //Each time there is a new participant, a new group is created with all members in it.
      
        
        if(c.getParticipants().getAllParticipants().size()==2) startExperiment();
             
        
    }
     
    public void startExperiment(){
        
        this.experimentHasStarted=true;
        
        Vector v = c.getParticipants().getAllParticipants();
        
        for(int i=0;i<v.size();i++){
            TelegramParticipant tp = (TelegramParticipant)v.elementAt(i);
            c.telegram_sendInstructionToParticipant_MonospaceFont(tp, "Everyone is now logged in!");
            c.telegram_sendInstructionToParticipant_MonospaceFont(tp, "You are in group number "+1);
            c.telegram_sendInstructionToParticipant_MonospaceFont(tp, "Your assigned name in the chat is "+tp.getUsername());
            
        }
    }
    
    
    

    @Override
    public void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
        if(!super.experimentHasStarted){
            c.telegram_sendInstructionToParticipant_MonospaceFont(sender, "Please wait for other participants to login");
            return;
        }
        
        
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
