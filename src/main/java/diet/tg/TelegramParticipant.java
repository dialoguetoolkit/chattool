/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.tg;

import diet.message.Message;
import diet.server.Conversation;
import diet.server.Participant;
import diet.server.ParticipantConnection;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

/**
 *
 * @author LX1C
 */
public class TelegramParticipant extends Participant{

    public TelegramParticipant(Conversation c, String em, String usrname) {
        super(c, em, usrname);
    }

    
   
    
    
            
    public void sendEditMessageReplyMarkup(EditMessageReplyMarkup erm){
        TelegramParticipantConnection tpc = (TelegramParticipantConnection)this.getConnection();
        System.err.println("HEREINCOMING103");
        tpc.sendEditMessageReplyMarkup(erm);
        
    }
            
            
    
    
    
    
    
    public org.telegram.telegrambots.meta.api.objects.Message sendMessage(SendMessage sm){
        TelegramParticipantConnection tpc = (TelegramParticipantConnection)this.getConnection();
        System.err.println("HEREINCOMING103");
        return tpc.send(sm);
        
    }
    
    
     public org.telegram.telegrambots.meta.api.objects.Message sendPoll(SendPoll sp){
        TelegramParticipantConnection tpc = (TelegramParticipantConnection)this.getConnection();
        
        System.err.println("HEREINCOMING103");
        return tpc.sendPoll(sp);
        
    }
    
     
      public void sendEditMessage(org.telegram.telegrambots.meta.api.objects.Message mOriginalMessage, EditMessageText emt){
        TelegramParticipantConnection tpc = (TelegramParticipantConnection)this.getConnection();
        
        System.err.println("HEREINCOMING103");
        tpc.sendEditMessage(mOriginalMessage, emt);
        
    }
     
     
     public void sendPinChatMessage(PinChatMessage messageToBePinned){
        TelegramParticipantConnection tpc = (TelegramParticipantConnection)this.getConnection();
        
        System.err.println("HEREINCOMING103");
        tpc.sendPinChatMessage(messageToBePinned);
        
    }
     
    
    public org.telegram.telegrambots.meta.api.objects.Message sendPhoto(SendPhoto sp){
        TelegramParticipantConnection tpc = (TelegramParticipantConnection)this.getConnection();
        
        System.err.println("HEREINCOMING103");
        return tpc.sendPhoto(sp);
        
    }
    
    public org.telegram.telegrambots.meta.api.objects.Message sendVoice(SendVoice sv){
        TelegramParticipantConnection tpc = (TelegramParticipantConnection)this.getConnection();
        
        System.err.println("HEREINCOMING103");
        return tpc.sendVoice(sv);
        
    }
    
    
    
    
    
    public void sendDeleteMessage(DeleteMessage sdm){
        TelegramParticipantConnection tpc = (TelegramParticipantConnection)this.getConnection();
        tpc.sendDeleteMessage(sdm);
        System.err.println("HEREINCOMING103");
        
    }
    
    
    
    
    @Override
    public long getNumberOfConnections() {
        return super.getNumberOfConnections(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeDown() {
        super.closeDown(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isConnected() {
        return super.isConnected(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTextEntryWindow() {
        return super.getTextEntryWindow(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isTyping(long typingThreshold) {
        return super.isTyping(typingThreshold); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Conversation getConversation() {
        return super.getConversation(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getParticipantID() {
        return super.getParticipantID(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendMessage(Message m) {
        //super.sendMessage(m); //To change body of generated methods, choose Tools | Templates.
        Conversation.saveErr("The chattool is trying to send a message to a telegram client as if it was a normal client");
        String canonicalclass= m.getClass().getCanonicalName();
        String classname = m.getClass().getName();
        
        
        Conversation.saveErr("The message is: "+canonicalclass+"\n"+classname+"\n"+m.toString());
        
        
    }
    
    

    @Override
    public String getUsername() {
        return super.getUsername(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TelegramParticipantConnection getConnection() {
        return (TelegramParticipantConnection)super.getConnection(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long getNumberOfChatMessagesProduced() {
        return super.getNumberOfChatMessagesProduced(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void disconnectParticipantConnection() {
        super.disconnectParticipantConnection(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setConnection(ParticipantConnection participC) {
        super.setConnection(participC); //To change body of generated methods, choose Tools | Templates.
    }
    
}
