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
import diet.task.CustomizableReferentialTask.CustomizableReferentialTask;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTaskSettings;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTaskSettingsFactory;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 *
 * @author LX1C
 */
public class Telegram2024_dyadic_AskFor_Language_NL_EN_ADDHAHA_RORSCHACH extends TelegramController implements JInterfaceMenuButtonsReceiverInterface{

    
    CustomizableReferentialTaskSettingsFactory crtsf = new CustomizableReferentialTaskSettingsFactory(this, true, "rorschachset01", "rorschachlongersequence.txt");
    //CustomizableReferentialTaskSettings crts = crtsf.getNextCustomizableReferentialTaskSettings();
    
    
    JInterfaceTwelveButtons jitb = new JInterfaceTwelveButtons(this,"Assign to groups","start", "pause", "", "", "", "", "","","");

    Random r = new Random();
    
    
    
    
    public Telegram2024_dyadic_AskFor_Language_NL_EN_ADDHAHA_RORSCHACH(Conversation c) {
        super(c);
    }

    public Telegram2024_dyadic_AskFor_Language_NL_EN_ADDHAHA_RORSCHACH(Conversation c, long istypingtimeout) {
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
        if(s.equalsIgnoreCase("Assign to groups")){
             assignQueueParticipantsAndStart();
        }    
    }
     
     
    
    
   // public Vector<TelegramParticipant> vQueued = new Vector();
    Hashtable htTasks = new Hashtable();
    public Vector<CustomizableReferentialTask> experimentalTasks = new Vector();
    
    
 
   
    
    
    
    
    
    
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
             CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crtsf.getNextCustomizableReferentialTaskSettings());
             
             crt.startTask(tp1, tp2);
             this.htTasks.put(tp1, crt);
             this.htTasks.put(tp2, crt);
             this.experimentalTasks.add(crt);
             
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
             CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crtsf.getNextCustomizableReferentialTaskSettings());
             
             crt.startTask(tp1, tp2);
             this.htTasks.put(tp1, crt);
             this.htTasks.put(tp2, crt);
             this.experimentalTasks.add(crt);
             
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
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             String text=tmfc.u.getMessage().getText();
             //if(diet.debug.Debug.debugDuplicate) System.err.println("DDP02: "+tmfc.u.getMessage().getText());
             this.processMessageFromClient(sender, tmfc);    
        }
        if(this.relayPhotos && tmfc.u.hasMessage()&&  tmfc.u.getMessage().hasPhoto()){
             //c.telegram_relayMessagePhotoToOtherParticipants_By_File_ID(sender, tmfc);   
             System.err.println("RELAYING1A");
        }
        if(this.relayVoice && tmfc.u.hasMessage()&& tmfc.u.getMessage().hasVoice() ){
             //c.telegram_relayMessageVoiceToOtherParticipants_By_File_ID(sender, tmfc);
             System.err.println("RELAYING1B");
        }
        if(tmfc.u.hasCallbackQuery()){
            CallbackQuery cbq = tmfc.u.getCallbackQuery();
            Message  m =cbq.getMessage();
            String callbackData =   cbq.getData();
            System.err.println("callbackdata: "+callbackData);
            System.err.println("RELAYING1C");
       
        }
        
        
        
    }
     
    Hashtable<TelegramParticipant,Integer> htTurnOfTurns = new Hashtable();
    
    public void processMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc){
         //if(diet.debug.Debug.debugDuplicate) System.err.println("DDP01: "+tmfc.u.getMessage().getText());
        
        
         
        
        
         CustomizableReferentialTask crt =  (CustomizableReferentialTask)  this.htTasks.get(sender);
         if(crt ==null){
             //c.saveErrorLog("Noncritical error! Cannot find the Referential task for the participant "+sender.getParticipantID()+ " this is probably because the experiment hasn`t started yet!");
             Conversation.printWSln("Main", sender.getUsername() + " sent text before the experiment has started");
             return;
         }
        
        if(!tmfc.u.hasMessage()){
            return;
        }
        if(!tmfc.u.getMessage().hasText()){
            return;
        }
        
  
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText() ){         
             String text=tmfc.u.getMessage().getText();
             if(text.startsWith("/")){
                 crt.processChatText(sender, text);
             }    
        }
        
        
        
          Integer numberOfTurns = this.htTurnOfTurns.get(sender);
          if(numberOfTurns==null)numberOfTurns=0;
          numberOfTurns++;
          htTurnOfTurns.put(sender, numberOfTurns);  
        
        
        
          String textFromSender = tmfc.u.getMessage().getText();
          boolean containsHaha = doesTextContainHaha(textFromSender);
          
          boolean domanipulation=false;
          
          if ((numberOfTurns + 5) % 15 ==0 && !containsHaha ) {
              domanipulation = true;
              domanipulation = r.nextBoolean();
          }
          else{
              Conversation.printWSln("Main", "Not generating haha for: "+sender.getParticipantID()+ ": "+(numberOfTurns + 5) % 15);
          }
          
          
         
         
         if(domanipulation){
              String newMessage = this.transformTurnAddHaha(textFromSender);   
              c.telegram_sendArtificialTurnFromApparentOriginToPermittedParticipants(sender, newMessage);
              Conversation.printWSln("Main", "Doing the manipulation! Sending from "+sender.getUsername());
             // System.err.println("CC.SENDING THE ARTIFICIAL MESSAGE "+cccount+"."+newMessage);
              cccount++;
              //this.fakeSendersOfHaha.add(sender);
         }
         else{
              Vector  tps=  this.pp.getRecipients(sender);
              if(tps==null||tps.size()==0) return;
              TelegramParticipant tpr = (TelegramParticipant)tps.elementAt(0);
              c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);
              System.err.println("CC.RELAYING THE MESSAGE "+cccount+"."+tmfc.u.getMessage().getText());
              cccount++;
         }
        
        
        
        
        

    }
    
    int cccount=1;
    
    
    
   
    
    

    
     public boolean doesTextContainHaha(String originalturn){
         String originalturnUPPER = originalturn.toUpperCase();
        String sp_UPCASE_sp = " "+originalturnUPPER+ " ";
        
        
        //if turn contains a HAHA then abort
        if((" "+sp_UPCASE_sp+" "  ) .contains(" HA ")){
            return true;
        }
        else if((" "+sp_UPCASE_sp+" "  ) .contains(" HAH ")){
            return true;
        }
        else if((" "+sp_UPCASE_sp+" "  ) .contains(" HAAH")){
            return true;
        }
        else if((" "+sp_UPCASE_sp+" "  ) .contains("HAHA")){
            return true;
        }
         
         return false;
     };
     
     public String transformTurnAddHaha(String originalturn){
       String laughtertext = "haha";
       try{  
         boolean prepend = r.nextBoolean();
         
         
         if(prepend){           
             if(originalturn.length()>1 &&   Character.isUpperCase(originalturn.charAt(0)) &    !Character.isUpperCase(originalturn.charAt(1))){
                 
                 return  (laughtertext.charAt(0)+"").toUpperCase()   +   laughtertext.substring(1) + " "+(originalturn.charAt(0)+"").toLowerCase()   +  "" + originalturn.substring(1);    
             }
             //System.exit(-34);  
             
             return laughtertext+" "+originalturn;
        }
        else{
             //strip the end of the turn of all spaces
             // if it is punctuation mark then do not add it
             // add one single space
             
             return originalturn + " "+laughtertext;
        }
       }catch (Exception e){
          e.printStackTrace();
          Conversation.saveErr(e);
           
       }
       
           if(r.nextBoolean()){
               return  originalturn + " "  + laughtertext;
           }
           else{
               return laughtertext + " "+originalturn;
           }
                
     }
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
}
