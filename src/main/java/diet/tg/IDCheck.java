/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.tg;

import diet.server.Conversation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author LX1C
 */
public class IDCheck {

    Conversation c;
    TGBOT cttgbot;
    public IDCheckRecords idcIO;
    
    public Vector<Long> blockedTelegramIDs = new Vector();
    
    //public boolean allowNEWLOGINS = false;
    
    public IDCheck(Conversation c, TGBOT cttgbot) {
        this.c=c;
        this.cttgbot=cttgbot;
        this.idcIO=new IDCheckRecords(c,this);
        this.loadBlockedIDS();
    }
    
     public void loadBlockedIDS(){
        System.err.println("LOADBLOCKEDIDsFromFile");
        String userdir = System.getProperty("user.dir")+File.separator+"tg";
        System.err.println("userdir is: "+userdir);
        File f = new File(userdir, "blockedusers.txt");
        System.err.println("Loading blocked users");
        if(!f.exists())return;
        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
           String line="";
            while((line=br.readLine())!=null){
               //this.adminIDS.add(line);
               try{
                  Long l = Long.parseLong(line);
                  this.blockedTelegramIDs.add(l);
               }catch(Exception e){
                   e.printStackTrace();
                   Conversation.printWSln("Main", "There was some error in blockedusers.txt with the line: "+line );
                   Conversation.saveErr(e);
               }
                  
               System.err.println("Adding: "+line + " to blocked list");
               Conversation.printWSln("Main", "blockedusers.txt: Adding "+line+ " to list of blocked users");
           } 
        System.err.println("Finished loading adminids");    
        
           
            
        }catch (Exception e){
            e.printStackTrace();
        }   
    }
    
    
    
    
    public void addTelegramToBlockList(Long telegramID){
        blockedTelegramIDs.add(telegramID);
        System.err.println("Adding "+telegramID+ " to blocked list");
    }
    
    public boolean isIDOK(long telegramID){
        System.err.println("Checking if "+telegramID + " is blocked");
        for(int i=0;i<this.blockedTelegramIDs.size(); i++){
            Long tgbID = blockedTelegramIDs.elementAt(i);
            System.err.println("Checking if "+telegramID + " is blocked ....found "+ tgbID);
            if(tgbID==telegramID) {
                Conversation.printWSln("Main", "Blocked "+telegramID+ " from logging in...is on blocklist");
                System.err.println("Checking if "+telegramID + " is blocked .... it should be blocked!");
                return false;
            }
           
        }
        return true;
    }
    
    
    public boolean isUserRegistered(long telegramid){
        TelegramParticipantConnection tidr = idcIO.getLogFile(telegramid);
        if(tidr==null){
            System.err.println("HEREA");
            return false;
        }
        if(tidr.getValidLogincode()!=null&&!tidr.getValidLogincode().equalsIgnoreCase("")){
             System.err.println("HEREB");
            return true;
        }
         System.err.println("HEREC");
        return false;
    }
    
    public void registerUser(long telegramid, String logincode){
       this.idcIO.addValidLoginCode(telegramid, logincode);
    }
    
    
    
   public boolean hasUserLoggedInBefore(long telegramid){
       TelegramParticipantConnection tidr = idcIO.getLogFile(telegramid);
        if(tidr==null){
            System.err.println("HEREA");
            return false;
        }
        return true;
   } 
    
    
    
    public boolean processTelegramMessage(Update update){
        TelegramParticipantConnection telegramparticipconnection= null;
        
        telegramparticipconnection= this.idcIO.getLogFile(update.getMessage().getChatId());
        if(telegramparticipconnection ==null){
             //First time logged in, need to create it.
             telegramparticipconnection= this.idcIO.createNewRecord(update.getMessage().getChatId());
        }
        //Now save to log file and to Vector in memory

        //Now check if user is registered:
        
         boolean isUserRegistered = this.isUserRegistered(update.getMessage().getChatId());
         System.err.println("HERE100");
         
         if(!isUserRegistered){
              System.err.println("HERE101");
              String[] pid_username = c.telegram_processLoginCode(update.getMessage().getChatId(), update.getMessage().getText());
              String participantID = pid_username[0];
              String username = pid_username[1];
              if(participantID==null||participantID.equalsIgnoreCase("")|| 
                      username==null || username.equalsIgnoreCase("")){
                  
                  System.err.println("HERE102");
                  return false;
              }
              else{
                  this.registerUser(update.getMessage().getChatId(), update.getMessage().getText());
                  sendMessageAndSaveToLog(update.getMessage().getChatId(),"Please wait for others to login!");
                  System.err.println("HERE103");
                  
                  TelegramParticipant tpc= new TelegramParticipant(c, participantID, username);
                  
                  telegramparticipconnection.telegramidlogfile.appendText("participantid:"+participantID+"\n");
                  telegramparticipconnection.telegramidlogfile.appendText("participantusername:"+username+"\n");
                  
                  
                  tpc.setConnection(telegramparticipconnection);
                 // telegramparticipconnection.assignToParticipant(tpc);
                  c.getParticipants().addNewParticipant(tpc);
                  c.getController().telegram_participantJoinedConversation(tpc);
                  c.getCHistoryUIM().updateParticipantsListChanged(c.getParticipants().getAllParticipants());
                  c.getMm().updateParticipants(c.getParticipants().getAllParticipants().size());
     
    
                  
                  
                  return true;
              }
             
         }
        System.err.println("HERE105");
        
        try{
         //sendMessageAndSaveToLog(update.getMessage().getChatId(),"HELLO!");
         telegramparticipconnection.processIncomingMessage(update);
        } catch (Exception e){
            e.printStackTrace();
        }
         
         
        //Normal chat text sent during a game
        
        //Is a login code when registering (that is valid)
        
        //Is a login code when registering (that is invalid)
       return true;
    }
     
    
    
    
    
    
    
    
    
    public boolean processTelegramCallback(Update update){
        TelegramParticipantConnection telegramparticipconnection= null;
        
        long id = update.getCallbackQuery().getFrom().getId();
   
        telegramparticipconnection= this.idcIO.getLogFile(id);
        if(telegramparticipconnection ==null){
             //First time logged in, need to create it.
             telegramparticipconnection= this.idcIO.createNewRecord(update.getMessage().getChatId());
        }
        //Now save to log file and to Vector in memory

        //Now check if user is registered:
        
         boolean isUserRegistered = this.isUserRegistered(id);
         System.err.println("HERE100");
         
         if(!isUserRegistered){
              System.err.println("HERE101");
              String[] pid_username = c.telegram_processLoginCode(id, update.getMessage().getText());
              String participantID = pid_username[0];
              String username = pid_username[1];
              if(participantID==null||participantID.equalsIgnoreCase("")|| 
                      username==null || username.equalsIgnoreCase("")){
                  
                  System.err.println("HERE102");
                  return false;
              }
              else{
                  this.registerUser(id, update.getMessage().getText());
                  sendMessageAndSaveToLog(id,"Please wait for others to login!");
                  System.err.println("HERE103");
                  
                  TelegramParticipant tpc= new TelegramParticipant(c, participantID, username);
                  
                  telegramparticipconnection.telegramidlogfile.appendText("participantid:"+participantID+"\n");
                  telegramparticipconnection.telegramidlogfile.appendText("participantusername:"+username+"\n");
                  
                  
                  tpc.setConnection(telegramparticipconnection);
                 // telegramparticipconnection.assignToParticipant(tpc);
                  c.getParticipants().addNewParticipant(tpc);
                  c.getController().telegram_participantJoinedConversation(tpc);
                  c.getCHistoryUIM().updateParticipantsListChanged(c.getParticipants().getAllParticipants());
                  c.getMm().updateParticipants(c.getParticipants().getAllParticipants().size());
     
    
                  
                  
                  return true;
              }
             
         }
        System.err.println("HERE105");
        
        try{
         /// sendMessageAndSaveToLog(id,"HELLO!");
          System.err.println("Callback011");
         telegramparticipconnection.processIncomingMessage(update);
         System.err.println("Callback012");
        } catch (Exception e){
            e.printStackTrace();
        }
         
         
        //Normal chat text sent during a game
        
        //Is a login code when registering (that is valid)
        
        //Is a login code when registering (that is invalid)
       return true;
    }
     
    
    
    public boolean processTelegramPollAnswer(Update update){
        TelegramParticipantConnection telegramparticipconnection= null;
        
        long id = update.getCallbackQuery().getFrom().getId();
   
        telegramparticipconnection= this.idcIO.getLogFile(id);
        if(telegramparticipconnection ==null){
             //First time logged in, need to create it.
             telegramparticipconnection= this.idcIO.createNewRecord(update.getMessage().getChatId());
        }
        //Now save to log file and to Vector in memory

        //Now check if user is registered:
        
         boolean isUserRegistered = this.isUserRegistered(id);
         if(!isUserRegistered) return false;
         
         System.err.println("HERE100");
         
        
        
        try{
         /// sendMessageAndSaveToLog(id,"HELLO!");
          System.err.println("Callback011");
         telegramparticipconnection.processIncomingMessage(update);
         System.err.println("Callback012");
        } catch (Exception e){
            e.printStackTrace();
        }
         
         
        //Normal chat text sent during a game
        
        //Is a login code when registering (that is valid)
        
        //Is a login code when registering (that is invalid)
       return true;
    }
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void sendMessageAndSaveToLog(long telegramID, String msg){
       try{ 
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
               message .setChatId(""+telegramID);
               message.setText(msg);
             this.cttgbot.sendMessage(message);
            
        TelegramParticipantConnection telegramIDRecord= this.idcIO.getLogFile(telegramID);
        telegramIDRecord.telegramidlogfile.appendText(msg+ "\n");
         
            
            
       } catch(Exception e){
           e.printStackTrace();
       }    
    }
    
    
   
    
    
     
}
