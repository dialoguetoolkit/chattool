package diet.server;
import diet.message.Message;
import diet.server.ConversationController.DefaultConversationController;

/**
 * Abstraction of the participant. Handles both incoming and outgoing messages
 * @author user
 */
public class Participant {

    private String participantID="UNSET";
    private String username = "UNSET2";

    private Conversation c;
    private ParticipantConnection participantConnection;
    
    
    private long numberOfConnections =0;

    public Participant(Conversation c,String em, String usrname) {
        super();
        this.c = c;
        this.participantID = em;
        this.username=usrname;
    }

    /**
     * Associates Participant with ParticipantConnection
     * @param participC
     */
    public void setConnection(ParticipantConnection participC){
        participantConnection = participC;
        participC.assignToParticipant(this);
        this.numberOfConnections=numberOfConnections+1;
    }

    public void disconnectParticipantConnection(){
        this.participantConnection=null;
    }
    
    
    public long getNumberOfChatMessagesProduced(){
        if(participantConnection==null)return 0;
        return this.participantConnection.getNumberOfChatMessagesProduced();
    }
    
    
    

    /**
     *
     * @return ParticipantConnection of Participant
     */
    public ParticipantConnection getConnection(){
        return participantConnection;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username){
        this.username=username;
    }

    public String getUsername(){
        return username;
    }
    /**
     * sends message to participant. Saves message to log file.
     * @param m
     */
     public void sendMessage(Message m){
        
        if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE)System.out.println("SINP "+getUsername());
        if(participantConnection!=null)participantConnection.send(m);
        if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE)System.out.println("SINQ"+getUsername());
       // c.getConvIO().saveMessage2FORDEBUG(m);
        c.mm.registerSendingMessage(m);

    }

    /**
     * Returns next incoming message from Participant's chat tool
     * @return next incoming message from Participant's chat tool
     */

    public String getParticipantID(){
        return participantID;
    }
    public Conversation getConversation(){
        return c;
    }

    /**
     * Verifies whether participant has typed within a particular time threshold
     * @param typingThreshold threshold in milliseconds
     * @return whether participant has typed
     */
    public boolean isTyping(long typingThreshold){
        if(this.participantConnection==null)return false;
        return participantConnection.isTyping(typingThreshold);
    }

     /**
     * Returns the content of the participant's text entry window (before they presss ENTER or SEND)
     * 
     * @return whether participant has typed
     */
    public String getTextEntryWindow(){
        if(this.participantConnection!=null)return this.participantConnection.getTextEntryWindow();
        return"";
    }


    
    
    /**
     *
     * @return whether participant is associated with ParticipantConnection that can send and receive messages
     */
    public boolean isConnected(){
        if (this.participantConnection==null){
            return false;
        }
        else return this.participantConnection.isConnected();
     }
    /**
     * Attempts to close down the ParticipantConnection
     *
     */
    public void closeDown(){
       try{
           participantConnection.setConnected(false);
           participantConnection.stop();
           this.c= null;
       }catch(Exception e){

       }

   }
    public long getNumberOfConnections(){
       return this.numberOfConnections;   
   }
    
    
}
