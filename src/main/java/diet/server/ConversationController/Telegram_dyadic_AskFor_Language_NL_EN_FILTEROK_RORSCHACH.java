   
         ///have an option of pairing them differently, e.g. simply take the first element and put it at the end. This will pair them differently
         ///Also need control condition 
     


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
import diet.server.Participant;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTask;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTaskSettings;
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
public class Telegram_dyadic_AskFor_Language_NL_EN_FILTEROK_RORSCHACH extends TelegramController implements JInterfaceMenuButtonsReceiverInterface{

    
   // CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(this,true, "rorschachset02" ,"rorschachsequence01.txt");
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crts);
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, 5000,true);
    
    
    CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(this,true, "rorschachset01", "rorschachlongersequence.txt" );
   
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, 5000,true);
   // Participant pDirector;
   // Participant pMatcher;
    
    
    
    JInterfaceTwelveButtons jitb = new JInterfaceTwelveButtons(this,"Assign to groups","start", "pause", "", "", "", "", "","","50% control. 50% experimental");

    
    boolean hasStarted = false;
    
    /**
     * Creates new form JInterfaceFiveButtons
     */
    
    //JInterfaceMenuButtonsReceiverInterface
    
    
    
    
    
    public Telegram_dyadic_AskFor_Language_NL_EN_FILTEROK_RORSCHACH(Conversation c) {
        super(c);
    }

    public Telegram_dyadic_AskFor_Language_NL_EN_FILTEROK_RORSCHACH(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
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
        
        String[] options = new String[]{"Forgotten", "Remembered"};
        
        
        
        String response = CustomDialog.show2OptionDialog(options, "Did you remember to specify the interventions?", "Did you remember to specify the interventions?");
        if (response.equalsIgnoreCase(options[0]))return;
       
        
        if(s.equalsIgnoreCase("Assign to groups")){
             assignQueueParticipantsAndStart(false);
             this.hasStarted=true;
        }
        if(s.equalsIgnoreCase("50% control. 50% experimental")){
            assignQueueParticipantsAndStart(true);
            this.hasStarted=true;
                    
        }
    }
     
    Hashtable htTasks = new Hashtable();
    
    
     int createdSubdialogue = 0;
    
     public synchronized void assignQueueParticipantsAndStart(boolean doHalfControls){
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
         
         
         ///have an option of pairing them differently, e.g. simply take the first element and put it at the end. This will pair them differently
         ///Also need control condition 
         
         
         while(this.vQueuedNL.size()>1){
             TelegramParticipant tp1 = this.vQueuedNL.elementAt(0);
             TelegramParticipant tp2 = this.vQueuedNL.elementAt(1);
             
             this.createNewPair(tp1, tp2);
                
             
             this.vQueuedNL.remove(tp1);
             this.vQueuedNL.remove(tp2);   
             
             
             
             
              
             
                
         }
         
         while(this.vQueuedOTHER.size()>1){
             TelegramParticipant tp1 = this.vQueuedOTHER.elementAt(0);
             TelegramParticipant tp2 = this.vQueuedOTHER.elementAt(1);
             
             
              this.createNewPair(tp1, tp2);
             
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
               
                 this.createNewPair(tp1, tp2);
                  
                 this.vQueuedNL.remove(tp1);
                 this.vQueuedOTHER.remove(tp2);    
                 
             }
             
         }
         else{
             CustomDialog.showDialog("This shouldn't happen: "+this.vQueuedNL.size()+ ": "+ this.vQueuedOTHER.size());
         }

    }
    
    Vector experimentalTasks = new Vector();
    
    public void createNewPair(TelegramParticipant tp1, TelegramParticipant tp2){
         createdSubdialogue=createdSubdialogue+1;
         String name = "";
         if(createdSubdialogue %2 ==1){
             name = "experimental"+createdSubdialogue;
             pp.createNewSubdialogue(name,tp1,tp2); 
             Conversation.printWSln("Main", "Created new pair"+tp1.getConnection().getValidLogincode()+ " and "+tp2.getConnection().getValidLogincode());
             c.telegram_sendInstructionToParticipant_MonospaceFont(tp1, "Please start!");
             c.telegram_sendInstructionToParticipant_MonospaceFont(tp2, "Please start!");
             CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crts);
             this.htTasks.put(tp1, crt);
             this.htTasks.put(tp2, crt);
             crt.startTask(tp1, tp2);
             this.experimentalTasks.add(crt);
             
             
        
         }
         else{
             name = "control"+createdSubdialogue;
             pp.createNewSubdialogue(name,tp1,tp2); 
             Conversation.printWSln("Main", "Created new pair"+tp1.getConnection().getValidLogincode()+ " and "+tp2.getConnection().getValidLogincode());
             c.telegram_sendInstructionToParticipant_MonospaceFont(tp1, "Please start!");
             c.telegram_sendInstructionToParticipant_MonospaceFont(tp2, "Please start!");
             CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crts);
             this.htTasks.put(tp1, crt);
             this.htTasks.put(tp2, crt);
             crt.startTask(tp1, tp2);
        
         }
        
        
    
    
           
           
            
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
     
   
     
     
     
     
    
     
     
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
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
    public void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
        if(this.hasStarted==false) {
            Conversation.printWSln("Main", sender.getParticipantID()+ " sent a message. The server blocked it because experiment hasn`t started.");
            return;
        }
        
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             String text=tmfc.u.getMessage().getText();
            // this.crt.processChatText(sender, text);
                  
                 
                  CustomizableReferentialTask crt =  (CustomizableReferentialTask)  this.htTasks.get(sender);
                  if(crt ==null){
                      c.saveErrorLog("Error! Cannot find the Referential task for the participant "+sender.getParticipantID());
                  }
                  else{
                      crt.processChatText(sender, text);
                      
                  }
                  c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);
      
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
     
   
    
   // Vector<TelegramParticipant> recipientOfNaturalHahaAndSenderOfFakeHaha = new Vector();
   // Hashtable htTimeOfReceiptOfNaturalHahaBySenderOfFakeHaha =  new  Hashtable();
    
   // Vector fakeSendersOfHaha  = new Vector();
    
    
    

    @Override
    public boolean processAutoIntervention(Participant sender, TelegramMessageFromClient tmfc) {
        
        CustomizableReferentialTask crtn =  (CustomizableReferentialTask)  this.htTasks.get(sender);
        boolean didIntervention = false;
        if(this.experimentalTasks.contains(crtn)){
            Conversation.printWSln("Main", "Received text from a dyad that is experimental group");
            didIntervention = super.processAutoIntervention(sender, tmfc); 
        }
        else{
            Conversation.printWSln("Main", "Received text from a dyad that is CONTROL group");
        }
        
        
        
        if(!didIntervention){
            Conversation.printWSln("Main", "Did not process intervention");
        }
        else{
               Conversation.printWSln("Main", "Did process intervention");
                CustomizableReferentialTask crt =  (CustomizableReferentialTask)  this.htTasks.get(sender);
                  if(crt ==null){
                      c.saveErrorLog("Error! Cannot find the Referential task for the participant "+sender.getParticipantID());
                  }
                  else{
                      try{
                      String text=tmfc.u.getMessage().getText();
                      crt.processChatText(sender, text + "EDITED");
                      }catch(Exception e){Conversation.saveErr(e);}
                      
                  }
        }
            
        
        
        
        
        return didIntervention;
        
    }
    
    
    
    
    
    
    //Hashtable<TelegramParticipant,Integer> htNumberOfInterventions = new Hashtable();
    Hashtable<TelegramParticipant,Integer> htTurnOfTurns = new Hashtable();
    //Hashtable<TelegramParticipant,Integer> htTurnOfLastIntervention = new Hashtable();
   
    
    

    
    
   
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
}
