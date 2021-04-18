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
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 *
 * @author LX1C
 */
public class Telegram_dyadic_AskFor_Language_NL_EN_REMOVEHAHA extends TelegramController implements JInterfaceMenuButtonsReceiverInterface{

    
   // CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(this,true, "tangramset01", "tangramsequence.txt" );
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crts);
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, 5000,true);
   // Participant pDirector;
   // Participant pMatcher;
    
    
    
    JInterfaceTwelveButtons jitb = new JInterfaceTwelveButtons(this,"Assign to groups","start", "pause", "", "", "", "", "","","");

    /**
     * Creates new form JInterfaceFiveButtons
     */
    
    //JInterfaceMenuButtonsReceiverInterface
    
    
    
    
    
    public Telegram_dyadic_AskFor_Language_NL_EN_REMOVEHAHA(Conversation c) {
        super(c);
        sortTargetList();
    }

    public Telegram_dyadic_AskFor_Language_NL_EN_REMOVEHAHA(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
        sortTargetList();
    }

    
    
    Vector<TelegramParticipant> vQueuedNL = new Vector();
    Vector<TelegramParticipant> vQueuedOTHER = new Vector();
    
   
     public synchronized void telegram_participantJoinedConversation(TelegramParticipant p) {
         //Synchronized because of manual group assignment by experimenter
         
        // if(this.experimentHasStarted=false){
             if(p.getConnection().getLogincode().toUpperCase().endsWith("NL")){
                 Conversation.printWSln("Main", "Queuing "+p.getConnection().telegramID + " to NL");
                 vQueuedNL.add(p);
             }
             else{
                 Conversation.printWSln("Main", "Queuing "+p.getConnection().telegramID + " to OTHER");
                 vQueuedOTHER.add(p);
             }
            Conversation.printWSln("Main", "Current queue size: NL QUEUE: "+vQueuedNL.size()+" OTHER QUEUE: "+this.vQueuedOTHER.size());
        // }
    }

    @Override
    public void performActionTriggeredByUI(String s) {
        if(s.equalsIgnoreCase("Assign to groups")){
             assignQueueParticipantsAndStart();
        }    
    }
     
     
    public synchronized void assignQueueParticipantsAndStart(){
        if(vQueuedNL.size() +  vQueuedOTHER.size() <2){
            CustomDialog.showDialog("Can't start with NL: "+vQueuedNL.size()+ "    OTHER: "+this.vQueuedOTHER.size());
            return;
        }
        
         if(vQueuedNL.size()  % 2 ==1 ){
               //CustomDialog.showDialog();
               boolean proceed = CustomDialog.getBoolean("There is an uneven ("+ this.vQueuedNL.size() +") number of NL pairs!", "continue", "cancel");
               if(!proceed)return;
         }
         if(vQueuedOTHER.size()  % 2 ==1 ){
               //CustomDialog.showDialog();
               boolean proceed = CustomDialog.getBoolean("There is an uneven ("+ this.vQueuedOTHER.size() +")number of OTHER pairs!", "continue", "cancel");
               if(!proceed)return;
         }
         
         
         
         
         while(this.vQueuedNL.size()>1){
             TelegramParticipant tp1 = this.vQueuedNL.elementAt(0);
             TelegramParticipant tp2 = this.vQueuedNL.elementAt(1);
             pp.createNewSubdialogue(tp1,tp2); 
             Conversation.printWSln("Main", "NL: Created group of "+tp1.getConnection().getValidLogincode()+ " and "+tp2.getConnection().getValidLogincode());
             c.telegram_sendInstructionToParticipant_MonospaceFont(tp1, "Please start!");
             c.telegram_sendInstructionToParticipant_MonospaceFont(tp2, "Please start!");
             this.vQueuedNL.remove(tp1);
             this.vQueuedNL.remove(tp2);      
         }
         
         while(this.vQueuedOTHER.size()>1){
             TelegramParticipant tp1 = this.vQueuedOTHER.elementAt(0);
             TelegramParticipant tp2 = this.vQueuedOTHER.elementAt(1);
             pp.createNewSubdialogue(tp1,tp2); 
             Conversation.printWSln("Main", "OTHER: Created group of "+tp1.getConnection().getValidLogincode()+ " and "+tp2.getConnection().getValidLogincode());
             c.telegram_sendInstructionToParticipant_MonospaceFont(tp1, "Please start!");
             c.telegram_sendInstructionToParticipant_MonospaceFont(tp2, "Please start!");
             this.vQueuedOTHER.remove(tp1);
             this.vQueuedOTHER.remove(tp2);      
         }
         
         
         
         
         if(vQueuedNL.size() ==0 &&  vQueuedOTHER.size() ==0   ){
             CustomDialog.showDialog("All participants have been paired!");
         }
         else if(vQueuedNL.size() ==1 &&  vQueuedOTHER.size() ==0   ){
             CustomDialog.showDialog("One NL participant remaining");
         }
         else if(vQueuedNL.size() ==0 &&  vQueuedOTHER.size() ==1   ){
             CustomDialog.showDialog("One OTHER participant remaining");
         }
         else if (vQueuedNL.size() ==1 &&  vQueuedOTHER.size() ==1   ){
             //CustomDialog.showDialog("There is one mixed pair possible");
             boolean option = CustomDialog.getBoolean("There is one of both types left over. Assign to mixed group?", "assign to mixed", "wait for others to login");
             if(option){
                 TelegramParticipant tp1 = this.vQueuedNL.elementAt(0);
                 TelegramParticipant tp2 = this.vQueuedOTHER.elementAt(0);     
                 pp.createNewSubdialogue(tp1,tp2); 
                  Conversation.printWSln("Main", "MIXED: Created group of "+tp1.getConnection().getValidLogincode()+ " and "+tp2.getConnection().getValidLogincode());
            
                 this.vQueuedNL.remove(tp1);
                 this.vQueuedOTHER.remove(tp2);    
                 
             }
             
         }
         else{
             CustomDialog.showDialog("This shouldn't happen: "+this.vQueuedNL.size()+ ": "+ this.vQueuedOTHER.size());
         }
         
         
         
        
         
         
         
    } 
     
     
     
     
     
    
     
     
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
        
         vQueuedOTHER.add(p);
       
    }
     

    @Override
    public void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             String text=tmfc.u.getMessage().getText();
            // this.crt.processChatText(sender, text);
             if(!text.startsWith("/")){
                  this.processMessageFromClient(sender, tmfc);
                 
                  // c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);      
                //  c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);
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
       
        }
        
        
        
    }
     //has to be organized from longest to shortest
     //"what about ha"
   
    String[] hahavariants = { 
       
        "haha", 
        "hahah", 
        "hahaha", 
        "hahahah", 
        "hahahaha",
        "hahahahah",
        "hahahahaha",
        
        "haaha", 
        "haahah", 
        "haahaha", 
        "haahahah", 
        "haahahaha",
        "haahahahah",
        "haahahahaha",
        
        "haahaa", 
        "haahaha", 
        "haahaaha", 
        "haahaahah", 
        "haahaahaha",
        "haahaahahah",
        "haahaahahaha",
        
        "haaahaa", 
        "haaahaaha", 
        "haaahaaha", 
        "haaahaahah", 
        "haaahaahaha",
        "haaahaahahah",
        "haaahaahahaha",
        
        "ahaha", 
        "ahahah", 
        "ahahaha", 
        "ahahahah", 
        "ahahahaha",
        "ahahahahah",
        "ahahahahaha",
        
        "aahaha", 
        "aahahah", 
        "aahahaha", 
        "aahahahah", 
        "aahahahaha",
        "aahahahahah",
        "aahahahahaha",
        
        "aaahaha", 
        "aaahahah", 
        "aaahahaha", 
        "aaahahahah", 
        "aaahahahaha",
        "aaahahahahah",
        "aaahahahahaha",
        
        "aahaha", 
        "ahahah", 
        "ahahaha", 
        "ahahahah", 
        "ahahahaha",
        "ahahahahah",
        "ahahahahaha",
        
        "hhah",
        "hhaha",
        "hhahahaa",
        "hhahhaaa",
        
        "hahaa",
        "hahahaa",
        "hahahaaa",
        "haahaa",
        "haaahaaa",
        "haahaaaa",
        "hahahaah",
        "hahahaaah",
        "hahahaaaah",
        "hahaaahahaa",
        
        "hahaah",
        "hahaaha",
        
        "hahaaheh",
        "hahaheh",
        "ahhaah",
        
        "hahha",
        "hahhaha",
        "hahahahhaa",
        "hahahha",
        "hhhahhah", 
        "aahahaahhaha",
            
        "ahhaha",
        "hahahha"
   
    
    };
    
    Vector<String> vHahaSorted = new Vector();
    
    public void sortTargetList(){
        List<String> haha = Arrays.asList(hahavariants);
        List hahalistsorted = sortStringListByLength(haha);
        vHahaSorted = new Vector(hahalistsorted);
        
    }
    
    private static List sortStringListByLength(List<String> list) {
        System.out.println("-- sorting list of string --");
        Collections.sort(list, Comparator.comparing(String::length));
        list.forEach(System.out::println);
        return list;
    }
    
    public  int[] doesTextContainHaha(String originalTurn){
        int[] d = {0,0};
        String originalTurnLower = originalTurn.toLowerCase();
        
        for(int i=vHahaSorted.size()-1;i>=0;i--){
             int startIndex = originalTurnLower.indexOf(vHahaSorted.elementAt(i));
             if(startIndex>=0){
                 c.printWln("Main", "Found haha! startindex:"+startIndex+ " length:"+vHahaSorted.elementAt(i).length());
                 d[0] = startIndex;
                 d[1] = vHahaSorted.elementAt(i).length();
                 return d;
             }
        }
        return null;
        
    }
    
    
    public void processMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc){
   
        try{
          String textFromSender = tmfc.u.getMessage().getText();
          
          String newText = ""+textFromSender;
          int[] d = this.doesTextContainHaha(textFromSender);
          if(d==null){
              c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);
          }
          else{
               if(d[0]==0){
                   newText = textFromSender.substring(d[1]);
               }
               else{
                   newText = textFromSender.substring(0,d[0])  + textFromSender.substring(d[0]+d[1]);
                  
               }
               if(newText.replace(" ", "").length()==0){
                   
               }
               else{
                   c.telegram_sendArtificialTurnFromApparentOriginToPermittedParticipants(sender, newText);
               }
                   
                
          }
        }catch(Exception e){
            e.printStackTrace();
            Conversation.saveErr(e);
           
            c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);
             Conversation.printWSln("Main", "Error processing turn "+tmfc.u.getMessage().getText());
        }
          
          
          
    }
    
    
    
    
   
    
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
}
