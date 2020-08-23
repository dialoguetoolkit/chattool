package diet.server.ConversationController;
import diet.client.ClientInterfaceEvents.ClientInterfaceEvent;
import diet.client.ClientInterfaceEvents.ClientInterfaceEventStringPrettifier;
import diet.debug.Debug;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageClientInterfaceEvent;
import diet.message.MessageKeypressed;
import diet.message.MessageTask;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.ConnectionListener;
import diet.server.Conversation;
import diet.server.Participant;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Vector;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;




public class TurnByTurn_Dyadic extends DefaultConversationController{

    
    
    
    public static boolean showcCONGUI(){
        return true;
    } 

    
    
    public TurnByTurn_Dyadic(Conversation c) {
        super(c);
        String portNumberOfServer = ""+ConnectionListener.staticGetPortNumber();
        this.setID("Dyadic");
        this.experimentHasStarted=true;     
    }
    public TurnByTurn_Dyadic(Conversation c, long istypingtimeout) {
        super(c,istypingtimeout);
        String portNumberOfServer = ""+ConnectionListener.staticGetPortNumber();
        this.setID("Dyadic");
        this.experimentHasStarted=true;     
        
        
    }
    
    
    
    
    
     @Override
    public boolean requestParticipantJoinConversation(String participantID) {
        return true;        
    }
    
    
    @Override
    public synchronized void participantJoinedConversation(final Participant p) {
        super.participantJoinedConversation(p);
        
        
    }
    
    
    
    

    
    @Override
    public void participantRejoinedConversation(Participant p) {     
        super.participantRejoinedConversation(p);      
    }
    
    
    
   
   
   
   
   
   
   
   public synchronized void processTaskMove(MessageTask mtm, Participant origin) {            
    }
   
   
   
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct){    
          itnt.removeSpoofTypingInfoAfterThreshold(sender, new Date().getTime());
          itnt.processTurnSentByClient(sender);
          c.relayTurnToPermittedParticipants(sender, mct);
    }
    
    
    
    
    
    
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
       super.processKeyPress(sender, mkp);
        // super.processKeyPress(sender, mkp);
        //this.itnt.processDoingsByClient(sender);
         //this.itnt.addSpoofTypingInfo(sender, new Date().getTime()+1000);
         //this.itnt.addSpoofTypingInfo(sender, new Date().getTime()+2000);
       
        
    }

    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
 
           //this.itnt.processDoingsByClient(sender);
    }
    
    
    
    @Override
    public void processWYSIWYGTextRemoved(Participant sender,MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp){
          // this.itnt.processDoingsByClient(sender);
    }

    
   
    
    @Override
    public void processClientEvent(Participant origin, MessageClientInterfaceEvent mce) {
       
    }
    
   
    
   
   


    
    
   

   

}
