package diet.server.ConversationController;
import diet.attribval.AttribVal;
import diet.debug.Debug;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageTask;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Configuration;
import diet.server.ConnectionListener;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.ConversationController.ui.JInterfaceToggleButtonsFIVE;
import diet.server.ConversationController.ui.JInterfaceMenuButtonsReceiverInterface;
import diet.server.IsTypingController.IsTypingOrNotTyping;
import diet.server.Participant;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;




public class TurnByTurn_Dyadic_ArtificialIsTypingStatusIndicator extends TurnByTurn_Dyadic{

       
    String currentMode = "instructions";
    
    public static boolean showcCONGUI(){
        return true;
    } 

     boolean pAIsInterrupted = false;
     boolean pBIsInterrupted = false;
     Participant pA;
     Participant pB;
        
    
    public TurnByTurn_Dyadic_ArtificialIsTypingStatusIndicator(Conversation c) {
        super(c);
        CustomDialog.showDialog("This intervention introduces artificial \'ís typing\' signals into the interaction\nOn average every third turn is interrupted.\n");
        String portNumberOfServer = ""+ConnectionListener.staticGetPortNumber();
        this.setID("DefaultDyadicConversationSpoofInterruptions"); 
        sett.login_numberOfParticipants =2;
       
    }

    @Override
    public synchronized void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p); //To change body of generated methods, choose Tools | Templates.
        if(c.getParticipants().getAllParticipants().size()==1){
            pA=p;
        }
        else if (c.getParticipants().getAllParticipants().size()==2){
            pB=p;
        }
        
        
        
    }

  
   
   
    
    
    
    
    
    
    
    
   
    
    

    
    
    @Override
    public void participantRejoinedConversation(Participant p) {
       
        super.participantRejoinedConversation(p);
        
        
    }
    
    
    
   
   
   
   
   
   
   
   public synchronized void processTaskMove(MessageTask mtm, Participant origin) {            
    }
   
   
   
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct){    
        
        
         
          
          
         
          itnt.processTurnSentByClient(sender);       
          c.relayTurnToPermittedParticipants(sender, mct);
          
          this.pAIsInterrupted=r.nextBoolean();
          this.pBIsInterrupted=r.nextBoolean();
          
          
           
          
       
          
    }
    
    
   
    
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
         
         Vector recipients = pp.getRecipients(sender);
         if(recipients.size()==0)return;
         if(recipients.size()>1){
             Conversation.printWSln("Main", "Error - the recipient "+sender.getUsername()+" has more than one partner...");
         }
         Participant recipient = (Participant)recipients.elementAt(r.nextInt(recipients.size()));
         System.err.println("THE KEYCODE IS: "+mkp.getKeypressed().getKeycode());
         
         if(mkp.getKeypressed().getKeycode()!=KeyEvent.VK_ENTER){
             if(sender==pA&&this.pAIsInterrupted){
                 this.createFakeTyping(recipient, sender);
             }
             else if(sender==pB&&this.pBIsInterrupted){
                 this.createFakeTyping(recipient, sender);
             }
             
                
                         
         }
    }

    
    public void createFakeTyping(Participant spoofTyping, Participant victim){

        int decision = r.nextInt(10);
        if(decision == 1){
           for(long l = new Date().getTime(); l < new Date().getTime()+ r.nextInt(10000); l = l+ 900){
                itnt.addSpoofTypingInfo(spoofTyping, l);     
           }
          
            
        }
        
        
        //itnt.addSpoofTypingInfo(recipient, l);
        
       /* for(long l = new Date().getTime(); l < new Date().getTime()+ r.nextInt(5000); l = l+ 900){
                      itnt.addSpoofTypingInfo(recipient, l);
                      //System.exit(-213);
                  }   
        
        */
        
    }
    
    
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
           
           long startOfKeypressWrite = new Date().getTime();
          
           this.itnt.processDoingsByClient(sender);
           long finishOfKeypressWrite = new Date().getTime();
           
           long durationOfKeypressProcessBYITNT =  finishOfKeypressWrite-startOfKeypressWrite;
           c.convIO.saveTextToFileCreatingIfNecessary(durationOfKeypressProcessBYITNT+"", "KEYPRESSWRITE");
           
           
           
           
    }
    
    
    
    @Override
    public void processWYSIWYGTextRemoved(Participant sender,MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp){
          this.itnt.processDoingsByClient(sender);
    }
    
   
    
   
   


    
    
   

   

}
