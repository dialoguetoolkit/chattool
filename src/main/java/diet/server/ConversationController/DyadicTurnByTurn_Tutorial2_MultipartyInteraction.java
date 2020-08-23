package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageClientInterfaceEvent;
import diet.message.MessageKeypressed;
import diet.message.MessageTask;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.ConnectionListener;
import diet.server.Conversation;
import diet.server.Participant;
import java.util.Date;
import java.util.Random;
import java.util.Vector;




public class DyadicTurnByTurn_Tutorial2_MultipartyInteraction extends DefaultConversationController{

    
    
    
    public static boolean showcCONGUI(){
        return false;
    } 

    
    
    public DyadicTurnByTurn_Tutorial2_MultipartyInteraction(Conversation c) {
        super(c);
        String portNumberOfServer = ""+ConnectionListener.staticGetPortNumber();
        this.setID("Dyadic");
        this.experimentHasStarted=true;     
        sett.login_numberOfParticipants = 3;
    }
    public DyadicTurnByTurn_Tutorial2_MultipartyInteraction(Conversation c, long istypingtimeout) {
        super(c,istypingtimeout);
        String portNumberOfServer = ""+ConnectionListener.staticGetPortNumber();
        this.setID("Dyadic");
        this.experimentHasStarted=true;
        sett.login_numberOfParticipants = 3;    
    }
    
    
    
    
    
     @Override
    public boolean requestParticipantJoinConversation(String participantID) {
        return true;        
    }
    
Participant pA,pB,pC;
    
public synchronized void participantJoinedConversation(final Participant p) {
       // super.participantJoinedConversation(p);
       Vector<Participant> allP = c.getAllParticipantsAsList();   
       
       if(c.getNoOfParticipants()==8){
           pp.createNewSubdialogue(allP.elementAt(0), allP.elementAt(1));
           pp.createNewSubdialogue(allP.elementAt(2), allP.elementAt(3));
           pp.createNewSubdialogue(allP.elementAt(4), allP.elementAt(5));
           pp.createNewSubdialogue(allP.elementAt(6), allP.elementAt(7));
           
           itnt.addPairWhoAreMutuallyInformedOfTyping(allP.elementAt(0), allP.elementAt(1));
           itnt.addPairWhoAreMutuallyInformedOfTyping(allP.elementAt(2), allP.elementAt(3));
           itnt.addPairWhoAreMutuallyInformedOfTyping(allP.elementAt(4), allP.elementAt(5));
           itnt.addPairWhoAreMutuallyInformedOfTyping(allP.elementAt(6), allP.elementAt(7));
       }
       else{
           c.sendInstructionToParticipant(p,"Please wait for other participants to login");
       }  
}
    
    

    
    @Override
    public void participantRejoinedConversation(Participant p) {     
        
    }
    
   
   
   public synchronized void processTaskMove(MessageTask mtm, Participant origin) {            
    }
   
   
   
    
    long msgcount=0; 
     
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct){  
       msgcount++;
       if(msgcount==10){
           Vector<Participant> allP = c.getAllParticipantsAsList();   
           pp.createNewSubdialogue(allP.elementAt(0), allP.elementAt(2));
           pp.createNewSubdialogue(allP.elementAt(1), allP.elementAt(3));
           pp.createNewSubdialogue(allP.elementAt(4), allP.elementAt(6));
           pp.createNewSubdialogue(allP.elementAt(5), allP.elementAt(7));
           
           itnt.addPairWhoAreMutuallyInformedOfTyping(allP.elementAt(0), allP.elementAt(2));
           itnt.addPairWhoAreMutuallyInformedOfTyping(allP.elementAt(1), allP.elementAt(3));
           itnt.addPairWhoAreMutuallyInformedOfTyping(allP.elementAt(4), allP.elementAt(6));
           itnt.addPairWhoAreMutuallyInformedOfTyping(allP.elementAt(5), allP.elementAt(7));
       }
    }
    
    
    Random r = new Random();
    public synchronized void processChatText3(Participant sender, MessageChatTextFromClient mct){    
          itnt.processTurnSentByClient(sender);
          
          if(r.nextBoolean()){
               String turn = mct.getText() + " :)" ;
               Vector recipients = pp.getRecipients(sender);
               
               c.sendArtificialTurnFromApparentOriginToParticipants(sender, recipients, turn);
          }
          else{
               c.relayTurnToPermittedParticipants(sender, mct);     
          }      
    }
    
    
    
    
    
    
    
    
    //@Override
    public void processKeyPress3(Participant sender,MessageKeypressed mkp){
         //super.processKeyPress(sender, mkp);
        
        
         if(sender==pB ){
              itnt.addSpoofTypingInfo(pA, 0, new Participant[]{pB});
              //itnt.addSpoofTypingInfo(pB, 0, new Participant[]{pC});          
         }
            
    }

    
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
         //super.processKeyPress(sender, mkp);
        
         itnt.addSpoofTypingInfo(pA, new Date().getTime(), new Participant[]{pB});
         itnt.addSpoofTypingInfo(pB, new Date().getTime(), new Participant[]{pC});
         itnt.addSpoofTypingInfo(pC, new Date().getTime(), new Participant[]{pA});
         
         //itnt.addGroupWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants());
         
         if(2<5) return;
         
         if( sender ==pA){
              //itnt.processTurnSentByClient(sender);
              //super.processKeyPress(sender, mkp);
             itnt.addSpoofTypingInfo(pA, 0, new Participant[]{pB});
             itnt.addSpoofTypingInfo(pA, 0, new Participant[]{pC});
             return;
         }
        else if(sender==pB ){
              itnt.addSpoofTypingInfo(pC, 0, new Participant[]{pA});
              itnt.addSpoofTypingInfo(pB, 0, new Participant[]{pC});
              
         }
        else if(sender==pC ){
              itnt.addSpoofTypingInfo(pB, 0, new Participant[]{pA,});
              itnt.addSpoofTypingInfo(pC, 0, new Participant[]{pB});              
         }  
         
         
         
         
        
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
