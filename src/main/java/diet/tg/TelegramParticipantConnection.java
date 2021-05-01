/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.tg;

import diet.message.Message;
import diet.server.Participant;
import diet.server.ParticipantConnection;
import java.util.Date;
import java.util.Vector;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author LX1C
 */
public class TelegramParticipantConnection extends ParticipantConnection{
    
    public TGBOT tgb;
    public TelegramIDLOGFILE telegramidlogfile;
    public long telegramID;
    public Vector<String> allLogsoFar = new Vector<String>();
    //public ParticipantConnection ParticipantID;
    private String logincode;
    
    
    

    public TelegramParticipantConnection(TGBOT tgb,long telegramID,  TelegramIDLOGFILE tidl) {
        super();
        this.tgb=tgb;
        this.telegramidlogfile = tidl;
        this.telegramID = telegramID;
        //this.logincode = logincode;
       // Conversation.printWSln("Main", "TPC Record with Telegram ID: "+telegramID + " has been established");
       
        
        
    }
    
    
    public void processIncomingMessage(Update update){
        this.telegramidlogfile.appendText(update.toString()+"\n");
        this.allLogsoFar.add(update.toString());
        
        System.err.println("HEREINCOMING1");
        
        if(super.particip!=null){
            TelegramMessageFromClient tmfc = new TelegramMessageFromClient(update, particip.getParticipantID(), particip.getUsername());
            this.particip.getConversation().getParticipants().addMessageToIncomingQueue(tmfc);    
            System.err.println("HEREINCOMING2");
        }
        
        this.timeOfLastMessage = new Date().getTime();
        this.telegramMessageCount= this.telegramMessageCount+1;    
    }
    
    
    
    
    public void setLogsSoFar(Vector<String> v){
        this.allLogsoFar=v;
    }
    
    
    public void setValidLoginCode(String logincode){
        this.logincode=logincode;
        //Conversation.printWSln("Main", "Establishing login code of TPC with TelegramID "+telegramID + " ....login code is: "+logincode);
       
    }

    public String getValidLogincode() {
        return logincode;
    }
    
    
    
    
    
    
    @Override
    public void simulateNetworkLag(boolean doLag, long extralagtime) {          
    }

    @Override
    public void dispose() {
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isConnected() {
        return true;//Once connected, telegram is in essence always connected
    }

    @Override
    public void setConnected(boolean isConnected) {
        //
    }

    @Override
    public boolean isTyping(long typingWindow) {
        return false;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
         
    }

    @Override
    public long getNumberOfChatTurnsReceivedFromClient() {
        return super.getNumberOfChatTurnsReceivedFromClient(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long getTimeOfLastTyping() {
        return 0;
    }

    @Override
    public String getTextEntryWindow() {
        return "Not available in telegram";
    }

    @Override
    public long getMostRecentLag() {
         return 0;
    }

    @Override
    public String getClientIPAddress() {
        return "tg client";
    }

    @Override
    public long getNumberOfChatMessagesProduced() {
        return super.getNumberOfChatMessagesProduced(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long getTimeOfLastChatTextSentToServer() {
        return super.getTimeOfLastChatTextSentToServer(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long getTimeOfLastMessageSentToServer() {
        return super.getTimeOfLastMessageSentToServer(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void send(Message m) {
        //super.send(m); //To change body of generated methods, choose Tools | Templates.
    }

    
    
        
    public void sendEditMessageReplyMarkup(EditMessageReplyMarkup emrp) {
        System.err.println("HEREINCOMING104");
        //super.send(m); //To change body of generated methods, choose Tools | Templates.
        try{
          this.telegramidlogfile.appendText("TOCLIENT: "+emrp.toString()+"\n");
        } catch(Exception e){
            e.printStackTrace();
        }  
        try{
            this.tgb.sendEditMessageReplyMarkup(emrp);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }    
        
        
    
    public void sendEditMessage(EditMessageText sem) {
        System.err.println("HEREINCOMING104");
        //super.send(m); //To change body of generated methods, choose Tools | Templates.
        try{
          this.telegramidlogfile.appendText("TOCLIENT: "+sem.toString()+"\n");
        } catch(Exception e){
            e.printStackTrace();
        }  
        try{
            this.tgb.sendEditMessage(sem);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    
    
    public org.telegram.telegrambots.meta.api.objects.Message send(SendMessage sm) {
        System.err.println("HEREINCOMING104");
        //super.send(m); //To change body of generated methods, choose Tools | Templates.
        try{
          this.telegramidlogfile.appendText("TOCLIENT: "+sm.toString()+"\n");
        } catch(Exception e){
            e.printStackTrace();
        }  
        try{
            return this.tgb.sendMessage(sm);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    public  void sendEditMessage(org.telegram.telegrambots.meta.api.objects.Message mOriginalMessage, EditMessageText emt) {
        System.err.println("HEREINCOMING104");
        //super.send(m); //To change body of generated methods, choose Tools | Templates.
        try{
          this.telegramidlogfile.appendText("TOCLIENT: "+emt.toString()+"\n");
        } catch(Exception e){
            e.printStackTrace();
        }  
        try{
            this.tgb.sendEditMessage(mOriginalMessage, emt);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return;
    }
    
    
    
    public  void sendPinChatMessage(PinChatMessage messageToBePinned) {
        System.err.println("HEREINCOMING104");
        //super.send(m); //To change body of generated methods, choose Tools | Templates.
        try{
          this.telegramidlogfile.appendText("TOCLIENT: "+messageToBePinned.toString()+"\n");
        } catch(Exception e){
            e.printStackTrace();
        }  
        try{
            this.tgb.sendPinChatMessage(messageToBePinned);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return;
    }
    
    
    
    
    public  org.telegram.telegrambots.meta.api.objects.Message sendPhoto(SendPhoto sp) {
        System.err.println("HEREINCOMING104");
        //super.send(m); //To change body of generated methods, choose Tools | Templates.
        try{
          this.telegramidlogfile.appendText("TOCLIENT: "+sp.toString()+"\n");
        } catch(Exception e){
            e.printStackTrace();
        }  
        try{
            return this.tgb.sendPhoto(sp);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    
     public  org.telegram.telegrambots.meta.api.objects.Message sendVoice(SendVoice sv) {
        System.err.println("HEREINCOMING104");
        //super.send(m); //To change body of generated methods, choose Tools | Templates.
        try{
          this.telegramidlogfile.appendText("TOCLIENT: "+sv.toString()+"\n");
        } catch(Exception e){
            e.printStackTrace();
        }  
        try{
            return this.tgb.sendVoice(sv);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    
    
    
    public  org.telegram.telegrambots.meta.api.objects.Message sendPoll(SendPoll sp) {
        System.err.println("HEREINCOMING104");
        //super.send(m); //To change body of generated methods, choose Tools | Templates.
        try{
          this.telegramidlogfile.appendText("TOCLIENT: "+sp.toString()+"\n");
        } catch(Exception e){
            e.printStackTrace();
        }  
        try{
            return this.tgb.sendPoll(sp);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    
    
    public void sendDeleteMessage(DeleteMessage sdm) {
        System.err.println("HEREINCOMING104");
        //super.send(m); //To change body of generated methods, choose Tools | Templates.
        try{
          this.telegramidlogfile.appendText("DELETEMESSAGE: "+sdm.toString()+"\n");
        } catch(Exception e){
            e.printStackTrace();
        }  
        try{
            this.tgb.sendDeleteMessage(sdm);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
   
    
    
    
    @Override
    public void assignToParticipant(Participant p) {
        super.assignToParticipant(p); //To change body of generated methods, choose Tools | Templates.
    }

    public String getLogincode() {
        return logincode;
    }
    
    int telegramMessageCount=0;
    public int getNumberOfTelegramMessagesSent(){
        return telegramMessageCount;
    }
    
    long timeOfLastMessage = -1;
    public long getTimeOfLastTelegrammessage(){
        return timeOfLastMessage;
    }
    
    
}

    
