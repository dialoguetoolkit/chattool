/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.tg;

//import diet.message.Message;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.Vector;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author LX1C
 */
public class TGBOT  extends TelegramLongPollingBot{
    
    
    Conversation c;
    public IDCheck idc;
    
    String mybotusername ="";
    String mybottoken ="";
    
    Vector<String> adminIDS = new Vector();
    
    TelegramBotLogFile tbl = new TelegramBotLogFile();
    
    Vector<String> blockedIDS = new Vector();

    public TGBOT(Conversation c,String mybotusername, String mybottoken) {
       this.c=c;
       this.mybotusername = mybotusername;
       this.mybottoken = mybottoken;
       idc = new IDCheck(c,this);
       System.err.println("TGBOT/c/m/m");
       
       String s= System.getProperty("user.dir");
       
       if(     !(this.mybottoken==null)&&
               !this.mybottoken.equalsIgnoreCase("")&&
                !(this.mybotusername==null)&&
               !this.mybotusername.equalsIgnoreCase("")){
                     this.startBOT();
               Conversation.printWSln("Main", "Starting telegram bot:"+mybotusername +"\nUse the Telegram app to connect to this bot.");
       }
       
       
       
    }
    
    public TGBOT(Conversation c) {
       this.c=c;
       this.loadBotNameFromFile();
       if(     !(this.mybottoken==null)&&
               !this.mybottoken.equalsIgnoreCase("")&&
                !(this.mybotusername==null)&&
               !this.mybotusername.equalsIgnoreCase("")){
                     this.startBOT();
               Conversation.printWSln("Main", "Starting telegram bot with bot username:"+mybotusername +" bot token: "+ this.mybottoken );
       }
       else{
           Conversation.printWSln("Main", "Not starting telegram bot. No bot settings found");
           CustomDialog.showDialog("Can't find the settings for the telegram bot\n"
                   + "You need \n"
                   + "(1) a folder in the chattool directory called /tg/\n"
                   + "(2) a file /tg/botname.txt which contains two lines\n"
                   + "    The first line the contains the name of the bot\n"
                   + "    The second line contains the \"bot token\n"
                   + "\n\n"
                   + "To get the botname and token you first have to register using a Telegram account\n"
                   + "You can do this on the website. (https://core.telegram.org/bots)\n\n"
                   + "It takes a couple of minutes, and doesn't require programming!");
       }
       System.err.println("INITTGBOT");
       this.loadAdminIDsFromFileAndStart();
       startAdminNotificationsLoop();
       idc = new IDCheck(c,this);
    }
    
    
    
    
    
    
    public void loadBotNameFromFile(){
        String userdir = System.getProperty("user.dir")+File.separator+"tg";
        System.err.println("userdir is: "+userdir);
        File f = new File(userdir, "botname.txt");
        
        
        
        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
            //StringBuilder sb = new StringBuilder();
            mybotusername = br.readLine();
            mybottoken =br.readLine();
           
            Conversation.printWSln("Main", "Loaded bot name: "+mybotusername);
            Conversation.printWSln("Main", "Loaded bot token: "+mybottoken);
            
            
        }catch (Exception e){
            e.printStackTrace();
        }   
       
        if(mybotusername==null){
            CustomDialog.showDialog("Could not find bot name in "+f.getAbsolutePath());
            return;
        }
        if(mybottoken==null){
            CustomDialog.showDialog("Could not find bot token in"+f.getAbsolutePath());
        } 
    }
    
    public void loadAdminIDsFromFileAndStart(){
        System.err.println("LOADADMINIDsFromFile");
        String userdir = System.getProperty("user.dir")+File.separator+"tg";
        System.err.println("userdir is: "+userdir);
        File f = new File(userdir, "adminids.txt");
        System.err.println("Loading adminids");
        if(!f.exists())return;
        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
           String line="";
            while((line=br.readLine())!=null){
               this.adminIDS.add(line);
               System.err.println("Adding: "+line);
               Conversation.printWSln("Main", "adminids.txt: Adding "+line+ " to list of admins who receive notifications from the server");
           } 
        System.err.println("Finished loading adminids");    
        startAdminNotificationsLoop();
           
            
        }catch (Exception e){
            e.printStackTrace();
        }   
    }
    
    
    
    
    
    public void startBOT(){
       ApiContextInitializer.init();
       TelegramBotsApi botsApi = new TelegramBotsApi();
    
        try {
            botsApi.registerBot(this);
            System.err.println("STARTING THE BOT");
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.err.println("STARTING THE BOT - ERROR");
            CustomDialog.showDialog("Could not start the telegram bot with bot username: "+mybotusername+ "  and with bot token: "+mybottoken+"\n"
                    + "Check the file /tg/botname.txt");
        }    
    }
    
    
    
    
    
    
    
    
    
    
    

    

   
    public String getBotUsername() {
        // TODO
       // return "A4329Bot";
       return this.mybotusername;
    }

    @Override
    public String getBotToken() {
        // TODO
       // return "367313563:AAGVBpZElDgaOgvuEciSK_8na0xObfPI5Fc";
       return this.mybottoken;
    }
    
    
    
    public String oneTimeString = "";
    
    public void addAdminID(Long chatid, String turn){
        if(oneTimeString==null | oneTimeString.equalsIgnoreCase(""))return;
       
        
        
        String id = ""+chatid;
        boolean foundID=false;
        for(int i = 0;i<this.adminIDS.size();i++){
            if(id.equalsIgnoreCase(adminIDS.elementAt(i))){
                foundID=true;
            }
        }
        if(foundID){
            CustomDialog.showModelessDialog(id + " tried to register as admin. But is already registered");
            this.sendMessage(chatid, "You are already registered as admin");
            return;
        }
        
        String turnfiltered = turn.replace("/registeradminaccnt ", "");
        if(turnfiltered.equalsIgnoreCase(oneTimeString)){
              boolean succ = addToListOfAdmins(chatid);
              if(succ){
                   this.sendMessage(chatid, "PIN IS VALID. ADDED TO LIST OF ADMINS");     
              }
              else{
                   this.sendMessage(chatid, "PIN IS VALID. BUT COULDN'T SAVE FILE. TRY UPDATING IT MANUALLY");
              }
              
             
             
              this.oneTimeString="";
        }
        else{
              this.sendMessage(chatid, "WRONG PIN ENTERED\n You will need to create a new PIN and try again");
        }
        
          
        
    }
    
    
    public boolean addToListOfAdmins(Long l){
         String s = System.getProperty("user.dir");
         String tgdirectory = s+File.separator+ "tg";
         File fdir = new File(tgdirectory);
        
         File f = new File(fdir,"adminids.txt");
                
                try{
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true), "UTF-8"));
                    out.write(l+"\n");
                    out.close();
                    Conversation.printWSln("Main",  "\nUpdated file: "+f.getName()+ " with adminsettings\n");
                    this.adminIDS.add(l+"");
                    return true;
                } catch (Exception e){
                    e.printStackTrace();
                    Conversation.printWSln("Main",   "\nCould not create and write to file botname.txt\n");
                    Conversation.saveErr(e);
                } 
                return false;
    }
    
    
    @Override
public void onUpdateReceived(Update update) {
 try{    
    // We check if the update has a message and the message has text
    
    this.tbl.saveFrom(update.toString());
    System.err.println(update.toString());
    System.err.println("UPDATE RECEIVED");
    
    //Testing for blocked
    if(!update.hasMessage() && !update.hasCallbackQuery()&&!update.hasPollAnswer()){
         this.sendMessage(update.getMessage().getChatId(), "Message format not supported!");
         Conversation.printWSln("Main", "Message was received that had no message and no callback. This is not serious, it just means that a participant tried to use some functionality on the Telegram client that isn't supported by the server" );
         return;
    }
   
    
    if (update.hasMessage() ) {
         Long telegramID= update.getMessage().getChatId();
         if(idc.isIDOK(telegramID)){        
              //this.sendMessage(update.getMessage().getChatId(), "You are not blocked - please contact the experimenter!");
         }
         else{
              this.sendMessage(update.getMessage().getChatId(), "You are blocked - please contact the experimenter!");
              return;
         }
    }
    else if (update.hasCallbackQuery()) {
         String str_telegramID= update.getCallbackQuery().getId();
         
         Long telegramID = -1l;
         telegramID = Long.parseLong(str_telegramID);
         
         if(telegramID>0&&    idc.isIDOK(telegramID)){           
         }
         else{
              this.sendMessage(update.getMessage().getChatId(), "Experiment is currently closed - please contact the experimenter to activate!");
              return;
         }
    }
    
    
    
    
    //Testing for admin message
    if (update.hasMessage() ) {
        
        System.err.println("UPDATE RECEIVED HAS MSG");
        
        
        if(update.getMessage().hasText()){
           String msg1 = update.getMessage().getText();
           if(msg1.startsWith("/registeradminaccnt")){
                addAdminID(update.getMessage().getChatId(), msg1);
           }   
        }
         
        
        
        System.err.println(update.getMessage());
        
        boolean isADMINID = false;
        
        String id = update.getMessage().getChatId()+"";
        for(int i = 0;i<this.adminIDS.size();i++){
            if(id.equalsIgnoreCase(adminIDS.elementAt(i))){
                isADMINID = true;
            }
        }
        if(isADMINID && update.getMessage().hasText()){
            String msg = update.getMessage().getText();
            if(msg.equalsIgnoreCase("/exit")){
                 this.sendMessage(update.getMessage().getChatId(), "ADMIN COMMAND EXITING");
                 if(2<5)System.exit(5);
                 this.sendMessage(update.getMessage().getChatId(), "If you can read this - it hasn't stopped!");
                 return;
            }
            else if(msg.startsWith("/listp")){
                 Vector<Participant> v = c.getParticipants().getAllParticipants();
                 String output ="";
                 for(int i=0;i<v.size();i++){
                     Participant p = v.elementAt(i);
                     output = output + p.getParticipantID()+ ","+p.getUsername()+ "\n";
                 }
                  this.sendMessage(update.getMessage().getChatId(), "ADMIN:\nList of participants:\n\n"+output);
                  return;
            }
            else if(msg.startsWith("/sendmsg")){
                 this.sendMessage(update.getMessage().getChatId(), "ADMIN COMMAND SENDMSG");
                 msg = msg.replace("/sendmsg", "");
                 msg = msg.trim();
                 String[] msgseq = msg.split(" ");
                
                 String recipientID = msgseq[0];
                 String text = msgseq[1];
                 
                 this.sendMessage(update.getMessage().getChatId(), "ADMIN. SENDMSG RECIPIENT IS: "+recipientID);
                 this.sendMessage(update.getMessage().getChatId(), "ADMIN. SENDMSG TEXT IS: "+text);
                 
                 try{
                    TelegramParticipant tp = (TelegramParticipant)c.getParticipants().findParticipantWithEmail(recipientID);
                    if(tp==null){
                         this.sendMessage(update.getMessage().getChatId(), "ADMIN. SENDMSG: CAN'T FIND RECIPIENT");
                         
                     }
                     else{
                         c.telegram_sendInstructionToParticipant_MonospaceFont(tp, text);
                     }
                 }catch(Exception e){
                     e.printStackTrace();
                 }
                 return;  
            }
            
            
        } 
    }   
        
       
    
    if(!update.hasMessage()){
        System.err.println("UPDATE DOESN'T HAVE MESSAGE");
    }
    
    
    
    if (update.hasMessage() ) {
        System.err.println(update.getMessage());
        Long userid = update.getMessage().getChatId();
        
        if(!idc.idcIO.allowLoginsByNewParticipantsGeneric && !idc.idcIO.allowLoginsByNewParticipantsSpecific){
            
            System.err.println("Logins by new participant not allowed");
            
             SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText("It is not possible to login. Experiment is currently closed to new logins. Please contact the experimenter");
            sendMessage(message);
            Conversation.printWSln("Main", "Prevented a new login from telegram id: "+userid);
            return;
        }
        
        
        
        
        boolean telegramidisok = false;
        try{
             System.err.println("PROCESSINGTELEGRAMMESSAGE1");
             telegramidisok = idc.processTelegramMessage(update);
             
        }catch(Exception e){
            e.printStackTrace();
        }
        
               
        
        if(!telegramidisok){
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText("What is your login code?");
            sendMessage(message);
        }
      }  
    else if (update.hasCallbackQuery()){
          System.err.println("Processing incoming callback");
          boolean telegramidisok = false;
          try{
              System.err.println("Callback01");
              telegramidisok = idc.processTelegramCallback(update);
               System.err.println("Callback02");
          }
          catch(Exception e){
              e.printStackTrace();
          }
          if(!telegramidisok){
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText("What is your login code?");
            sendMessage(message);
        }
    }
    else if (update.hasPollAnswer()){
          System.err.println("Processing incoming poll answer");
          boolean telegramidisok = false;
          try{
              System.err.println("Callback01");
              telegramidisok = idc.processTelegramPollAnswer(update);
               System.err.println("Callback02");
          }
          catch(Exception e){
              e.printStackTrace();
          }
          if(!telegramidisok){
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText("What is your login code?");
            sendMessage(message);
        }
    }
    
    
    
    
 } catch(Exception e){
     e.printStackTrace();
 }
  }      
        
   public synchronized void sendEditMessage(EditMessageText emt){
      try {
         execute(emt);
      }
      catch(TelegramApiException e) {
            e.printStackTrace();
        }
       this.tbl.saveTo(emt.toString());
   }      
        
   public synchronized void sendEditMessageReplyMarkup(EditMessageReplyMarkup emrp){
       try {
                
            execute(emrp); // Call method to send the message
            
            
            
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        this.tbl.saveTo(emrp.toString());
       
      //return null;
   }
  
    private synchronized void sendAdminMessage(long telegramID, String text){
       SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(telegramID)
                .setText(text);
            sendMessage(message);
            
            this.tbl.saveTo(message.toString());
   } 

    
   public synchronized void sendMessage(long telegramID, String text){
       SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(telegramID)
                .setText(text);
            sendMessage(message);
            this.tbl.saveTo(message.toString());
   } 
   
   
   public synchronized void sendEditMessage(Message mOriginalMessage, EditMessageText emt){
       try{
            
            /*Integer messageID = mOriginalMessage.getMessageId() ;
            Long messagechatid = mOriginalMessage.getChatId();
            EditMessageText emt = new EditMessageText();
            
           
            emt.enableHtml(true);
            emt.setText("<code>"+replacementText+"</code>");
            emt.setChatId(messagechatid);
            emt.setMessageId(messageID); 

           */

            this.tbl.saveTo(emt.toString());
            this.execute(emt);
       }
       catch(Exception e){
           e.printStackTrace();
       }
           
   }
   
   
   public synchronized void sendPinChatMessage(PinChatMessage pcm){
       try{
                 
                  
                  this.execute(pcm);      
                  this.tbl.saveTo(pcm.toString());
                  System.err.println("Trying to pin the message");
               //this.sendChatAction(sca);
            }catch(Exception e){
                e.printStackTrace();
            }
   }
  
    
  public synchronized Message sendMessage(SendMessage message){
      try {
            this.tbl.saveTo(message.toString());           
            Message m = execute(message); 
            return m; // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
      
      return null;
  }
  
  public synchronized Message sendPhoto(SendPhoto message){
      try {
            this.tbl.saveTo(message.toString());
            return execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
      
      return null;
  }
  
  public synchronized void sendDeleteMessage(DeleteMessage message){
      try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
      this.tbl.saveTo(message.toString());
      
      
  }
  
  
  
  public  synchronized Message  sendVoice(SendVoice message){
      try {
            return execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
       return null;
      
      
  }
  
  
   public synchronized Message sendPoll(SendPoll sp){
      try {
            this.tbl.saveTo(sp.toString());
            return execute(sp); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
      
      return null;
  
  }
  
   
  
      public synchronized void sendTypingNotification(Long telegramID){
         SendChatAction sca = new SendChatAction();
            ActionType at = ActionType.TYPING;
            sca.setChatId(telegramID);
            sca.setAction(at);
            try{
                 this.execute(sca);
               //this.sendChatAction(sca);
            }catch(Exception e){
                e.printStackTrace();
            }
            this.tbl.saveTo(sca.toString());
    }
      
      
      
     
      
  
      
     private void startAdminNotificationsLoop(){
         Thread t = new Thread(){
            public void run(){
                doAdminNotificationsLoop();
            }   
         };
         t.start();
     }
      
  
     private void doAdminNotificationsLoop(){
          while(2<5){
              try{
                  
                  for(int i=0;i<this.adminIDS.size();i++){
                      String ID = adminIDS.elementAt(i);
                      Long IDLONG = Long.parseLong(ID);
                      Thread.sleep(200);
                      sendTypingNotification(IDLONG);
                      System.err.println("TGB: SENDING TYPING NOTIFICATION TO: "+ID);
                  }
                  System.err.println("TGB: SLEEPING FOR "+20000);
                  Thread.sleep(20000);
                  System.err.println("TGB: WAKING");
                  
              }catch(Exception e){
                  e.printStackTrace();
              }
          }
     }
  
    
}
    
    




