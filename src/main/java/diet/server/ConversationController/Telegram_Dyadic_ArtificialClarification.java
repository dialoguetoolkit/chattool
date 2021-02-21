/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.textmanipulationmodules.CyclicRandomTextGenerators.CyclicRandomTextGenerator;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import diet.utils.HashtableOfLong;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author LX1C
 */
public class Telegram_Dyadic_ArtificialClarification extends TelegramController{

    
    //CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(this,true, null, null );
    //CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crts);
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, 5000,true);
   // Participant pDirector;
   // Participant pMatcher;
    
    
    public Telegram_Dyadic_ArtificialClarification(Conversation c) {
        super(c);
        Thread t = new Thread(){public void run(){loop();}};
        t.start();
    }

    public Telegram_Dyadic_ArtificialClarification(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
        Thread t = new Thread(){public void run(){loop();}};
        t.start();
        
    }

    
    
    
    
    
    
    

   
     public void telegram_participantJoinedConversation(TelegramParticipant p) {
        if(c.getParticipants().getAllParticipants().size()==2) {
            
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
             //this.itnt.addGroupWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants());
              
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
             
             
        }
    }
    
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
        
      // c.telegram_sendPoll(p, "This is a questionnaire","What is it?", new String[]{"option1", "option2", "option3", "option4"});
        
       // c.telegram_sendInstructionToParticipantWithForcedKeyboardButtons(p, "This is a question",  new String[]{"option1", "option2", "option3", "option4", "option5", "option6", "option7"},3);
        
       if(c.getParticipants().getAllParticipants().size()==2) {
            
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
             //this.itnt.addGroupWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants());
              
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
             
        }
    }
     

    @Override
    public synchronized void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             String text=tmfc.u.getMessage().getText();
             
             long numberOfTurns = this.htTurns.get(sender);
             numberOfTurns++;
             this.htTurns.put(sender, numberOfTurns);
             
             if(this.mode==normalconversation){
                 generateClarification(sender,text);
                  c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);    
             }
             else if(this.mode==this.waitingaftertargetbeforesending & sender==this.detectedParticipant){
                 Conversation.printWSln("Main", "The target has sent another message...");
                 this.mode=normalconversation;
                 generateClarification(sender,text);
                 c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);    
             }
             else if(this.mode==this.waitingforresponse & sender ==this.detectedParticipant){
                 Conversation.printWSln("Main", "Received response!");
                 this.messageQueue.add(tmfc);
                 this.htOriginOfMessage.put(tmfc,sender);
                 this.mode=this.emptyingqueue;
             }
             
             
             else {
                 Conversation.printWSln("Main", "Message was sent while queue was not empty. This message has been enqueued.");
                 this.messageQueue.add(tmfc);
                 this.htOriginOfMessage.put(tmfc,sender);
                 
             }
             
             
             
             
             
      
        }
        
        
        
        
    }
    
    
    
    
    int turnsElapsedBeforeClarification = CustomDialog.getInteger("How many turns need to elapse between interventions? (per participant)", 5);
    long durationToWaitAfterTargetBeforeIntervention = CustomDialog.getLong("Duration to wait after target detected, before sending clarification?", 6000);
    long durationToWaitAfterIntervention =  CustomDialog.getLong("Duration to wait after sending intervention before resuming and sending messages from the queue?", 10000);
    long durationBetweenMessagesWhenEmptyingQueueMin = CustomDialog.getLong("When emptying the queue, what is the gap between messages?", 3000);
    
            
    
    //String[] possibleTargets = new String[]{"Bob", "Riviera"};
    Vector possibleTargets = CustomDialog.loadTextFileWithExtensionToVector(System.getProperty("user.dir"), "What is the text file containing the target strings", "txt", "");
    
    //String[] whyvariants = new String[]{"why?", "sorry why?", "umm why?"};
    
    
    String[] whyvariants = (String[])CustomDialog.loadTextFileWithExtensionToVector(System.getProperty("user.dir"), "What is the text file containing all the WHY? variants", "txt", "").toArray();
    CyclicRandomTextGenerator crt = new CyclicRandomTextGenerator( new Vector<String>(Arrays.asList(whyvariants)));
    

    HashtableOfLong htTurns = new HashtableOfLong(0);
    HashtableOfLong htTurnOfLastIntervention = new HashtableOfLong(0);
    Hashtable<TelegramMessageFromClient, TelegramParticipant> htOriginOfMessage = new Hashtable();
    
    
    long timestampOfDetection = new Date().getTime();
    long timestampOfSendingWHY = new Date().getTime();
    TelegramParticipant detectedParticipant = null;
    long timestampOfMostRecentQueueSend = 0;
    
    
    
    
    
    public synchronized void generateClarification(TelegramParticipant sender, String t){
         if(this.mode!=normalconversation) {
             
             Conversation.printWSln("Main", "Turn by "+sender.getConnection().telegramID+ " can`t be clarified - not in mode 0" );
             return ;
         }
             
         
        
         long turnOfLastInterventionToParticipant = this.htTurnOfLastIntervention.get(sender);
         long turnsProducedByParticipant = this.htTurns.get(sender);
         
         long turnsSinceLastIntervention = turnsProducedByParticipant - turnOfLastInterventionToParticipant;
         if(turnsSinceLastIntervention<  turnsElapsedBeforeClarification){
             Conversation.printWSln("Main", "Turn by "+sender.getConnection().telegramID+ " can`t be clarified - Only "+turnsSinceLastIntervention+ "  turns since last intervention" );
             return ;
         }
         
         boolean containsTarget = false;
         for(int i =0; i < possibleTargets.size(); i++){
             String needle = " "+((String)possibleTargets.elementAt(i)).toUpperCase()+" ";
             
             String haystack = " "+t.toUpperCase()+ " ";
             
             //Conversation.printWSln("Main", "Haystack: "+haystack+ "    Needle: "+needle);
             
             
             if(haystack.contains(needle))containsTarget = true;
         }
         if(!containsTarget){
             Conversation.printWSln("Main", "Turn by "+sender.getConnection().telegramID+ " can`t be clarified - doesn`t contain target");
             return ;
         }
         
         this.detectedParticipant = sender;
         this.timestampOfDetection = new Date().getTime();
         
         
       
         Conversation.printWSln("Main", "Target identified. Waiting before sending Clarification");
             
         this.mode=this.waitingaftertargetbeforesending;
         
         
         
    }
    
    
    Vector<TelegramMessageFromClient> messageQueue = new Vector();
    
   
    
    public synchronized void loop(){
         while(true){
             try{
                 wait(500);
                 System.err.println("WAIT1");
                 if(mode==this.waitingaftertargetbeforesending){
                      System.err.println("WAIT1");
                      long currentTime = new Date().getTime();
                      long timeSinceDetection = currentTime - this.timestampOfDetection;
                      if(timeSinceDetection>this.durationToWaitAfterTargetBeforeIntervention){
                             String whyvariant = crt.getNext(this.detectedParticipant);
         
                             Vector v = this.pp.getRecipients(this.detectedParticipant);
                             TelegramParticipant recip = (TelegramParticipant)v.elementAt(0);
                             
                             Conversation.printWSln("Main", "Sending "+ whyvariant +  " to "+this.detectedParticipant.getConnection().telegramID);
                             c.telegram_sendArtificialTurnFromApparentOriginToPermittedParticipants(recip, whyvariant);
                             this.timestampOfSendingWHY = new Date().getTime();
                             
                             this.mode=this.waitingforresponse;
                      }
                 }
                 if(mode==this.waitingforresponse){
                       long durationElapsed = new Date().getTime()-this.timestampOfSendingWHY;                      
                       Conversation.printWSln("Main", "Waiting for response to clarification. "+durationElapsed+ " has elapsed");
                       if(durationElapsed>this.durationToWaitAfterIntervention){
                            if(messageQueue.size()==0){
                                Conversation.printWSln("Main", "Has waited for maximum of. "+this.durationToWaitAfterIntervention+ " continuing normally");
                                this.htTurnOfLastIntervention.put(detectedParticipant,this.htTurns.get(detectedParticipant));
                                this.mode=this.normalconversation;
                            }
                            else {
                                Conversation.printWSln("Main", "Has waited for maximum of. "+this.durationToWaitAfterIntervention+ " ...now emptying queue" );
                                this.mode=this.emptyingqueue;
                            }
                            
                       }
                 }
                 else if(mode==this.emptyingqueue){
                       long timeelapsedsinceLastSend = new Date().getTime()-this.timestampOfMostRecentQueueSend;
                       
                       if(timeelapsedsinceLastSend>this.durationBetweenMessagesWhenEmptyingQueueMin){
                             Conversation.printWSln("Main", "Emptying queue");
                             TelegramMessageFromClient tmfc = this.messageQueue.firstElement();
                             TelegramParticipant sender = this.htOriginOfMessage.get(tmfc);
                             c.telegram_sendArtificialTurnFromApparentOriginToPermittedParticipants(sender, tmfc.u.getMessage().getText());
                             this.timestampOfMostRecentQueueSend = new Date().getTime();
                             this.messageQueue.remove(tmfc);
                       }
                       if(this.messageQueue.size()==0){
                            Conversation.printWSln("Main", "Queue empty. Changing mode to normal");
                            this.htTurnOfLastIntervention.put(detectedParticipant,this.htTurns.get(detectedParticipant));
                            this.mode=this.normalconversation;
                       }
                 }
                 
             }catch(Exception e){
                 e.printStackTrace();
             }
             
             
              
         }
    }
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
   
   
    
    final int normalconversation = 0;
    final int waitingaftertargetbeforesending = 1;
    final int waitingforresponse = 2;
    final int emptyingqueue = 3;
    int mode = normalconversation;
    
   
    public void updateModeCRSENT(){
        if(mode==normalconversation){
            mode=waitingforresponse;
        }
        else{
            c.saveErrorLog("Trying to go from mode "+ mode+ " to "+waitingforresponse);
        }
    }
   
   
   
   
   
}







