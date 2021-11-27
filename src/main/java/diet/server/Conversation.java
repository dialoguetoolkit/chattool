/*
 * 
 * 
 *     Please, whatever you do, don't delete or rewrite any of the existing code in this class....
 * 
 *     But please DO ADD whatever you need!
 * 
 * 
 
 * 
 */
package diet.server;

import diet.attribval.AttribVal;
import diet.client.ClientInterfaceEvents.ClientInterfaceEvent;
import diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker;
import diet.client.DocumentChange.DocChange;
import java.io.File;
import java.util.Date;
import java.util.Vector;
import diet.message.*;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.TelegramController;
import diet.server.ConversationController.autointervention.autointervention;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.conversationhistory.ConversationHistory;
import diet.server.experimentmanager.EMUI;
import diet.server.io.IntelligentIO;
import diet.tg.TGBOT;
import diet.tg.TGMESSAGEDELETER;
import diet.tg.TGASYNCMEDIAGETTER;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import diet.tg.tgSTARTER;
import java.awt.Color;
import static java.awt.SystemColor.text;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.text.MutableAttributeSet;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


/**
 * This is the main server class. Each experiment has an associated Conversation object. The Conversation object
 * acts as the intermediary between the clients. It constantly checks for incoming messages, and relays them to a
 * ConversationController object that determines whether the message should be transformed.
 *
 * The Conversation object contains methods that should be used to send messages to clients.
 *
 * @author gjmills
 */


public class Conversation extends Thread{

    private ConversationHistory cH;
    private DefaultConversationController cC;
    private ConversationUIManager cHistoryUIM;
    private Participants ps;
    private boolean conversationIsActive = true;
    private boolean conversationThreadHasTerminated=false;
    private DocChangesIncoming turnsconstructed = new DocChangesIncoming(this); 
    public IntelligentIO convIO;
  
    private ExperimentManager expManager;
    
    static Conversation statC; 

    Metrics mm;   //For calculating metrics in the chat
    
    ///public autointervention ai ;
    
    
    public TGBOT tgb ;
    public TGASYNCMEDIAGETTER tgups ;
    public TGMESSAGEDELETER tmdel;
    
     public Conversation(ExperimentManager expM,String nameOfDefaultConversationController){
         this.expManager=expM;
         mm = new Metrics(this);
         ///ai=new autointervention(this);
         statC=this;
         try{  
              this.doBasicSetup(nameOfDefaultConversationController);
         }catch (Exception e){
              e.printStackTrace();
                      //try{ 
                       //Class c = Class.forName( "diet.server.ConversationController.obsoltebucket."+nameOfDefaultConversationController);
                       //this.doBasicSetup(nameOfDefaultConversationController);
                      //}catch(Exception ee){
                      //    ee.printStackTrace();
                      //     
                      //} 
         }      
     }
         
 
  
  
    /**
     *
     * This method retrieves the individual settings for the Conversation by extracting
     * the {@link diet.parameters.Parameter} objects from {@link diet.parameters.ExperimentSettings}.
     * It instantiates the correct {@link ConversationController} using dynamic class loading if necessary, {@link TaskController} (if any), ensures the data will be saved
     * in a particular directory.
     *
     * <p>After initializing the components, it starts the ConversationController Thread.
     *
     * <p>Editing this method has to be done carefully. The sequence of the objects that are initialized is very
     * important as there are many complex interdependencies.
     * 
     * @param nameOfConversationController The string used to identify the experiment
     *
     */
    private void doBasicSetup(String nameOfConversationController){
        //Editing this method has to be done carefully
        //The sequence of the objects that are initialized
        //Is very important..

        try{
            String cCType = nameOfConversationController.trim();
            System.err.println("THE TRIMMED NAME IS: "+cCType);
            Class c = null;
            if(cCType.contains("diet.server.ConversationController")){
               c=Class.forName(cCType);
            }
            else{
               c = Class.forName( "diet.server.ConversationController."+cCType);
            }
            //DefaultConversationController dcc = (DefaultConversationController)c.newInstance();
            
            Class[] intArgsClass = new Class[] {Conversation.class};
           
            if(c==null){
                System.err.println("IN CONVERSATION - DOBASICSETUP COULD NOT FIND THE CLASS!");
            }
            else{
                System.err.println("IN CONVERSATION - DOBASICSETUP COULD FIND THE CLASS!");
            }
           
            Constructor cons = c.getConstructor(intArgsClass);
            
            if(cons==null){
                System.err.println("CONS IS NULL");
            }
            else{
              
                System.err.println("CONS IS NOT NULL");
            }
            
           
            DefaultConversationController dcc = (DefaultConversationController)cons.newInstance(this);
            
            
            cC=dcc;
                      
           
            
            
            
           }catch(Exception e){
                  System.err.println("COULD NOT FIND AND DYNAMICALLY LOAD CONVERSATION CONTROLLER...trying to load"+nameOfConversationController);
                  System.err.println("THIS ERROR MESSAGE USUALLY MEANS THAT THERE WAS AN ERROR IN THE CONVERSATIONCONTROLLER OBJECT WHEN IT WAS BEING INITIALIZED");
                    
                    
                  e.printStackTrace();
                  InvocationTargetException ite = (InvocationTargetException)e;
                  ite.getTargetException().printStackTrace();
                  if(this.expManager.emui!=null){
                    this.expManager.emui.print("Main","Could not dynamically load "+nameOfConversationController);
                    this.expManager.emui.print("Main", "THIS ERROR MESSAGE USUALLY MEANS THAT THERE WAS AN ERROR IN THE CONVERSATIONCONTROLLER OBJECT WHEN IT WAS BEING INITIALIZED");
                    e.printStackTrace();
                      
                    e.getCause();
                    e.getCause().printStackTrace();

                  }else{
                      //System.err.println("Could not dynamically load "+nameOfConversationController);
                      e.printStackTrace();
                  }
           }
         
        cH= new ConversationHistory(this,  cC.getID(), convIO);
        cHistoryUIM = new ConversationUIManager(cH,this);
        cH.setConversationUIManager(cHistoryUIM);
        ps = new Participants(this);  
        if(cC instanceof diet.server.ConversationController.TelegramController){
            this.expManager.connectUIWithExperimentManager(this,cHistoryUIM, true);
        }
        else{
            this.expManager.connectUIWithExperimentManager(this,cHistoryUIM,false);
        }
        
        
        cC.initializePostSetup();
        
        if(cC.telegram_startTelegramBOT()){
            //tgb = new TGBOT(this);
            tmdel = new  TGMESSAGEDELETER (this);
           
           
            tgb=tgSTARTER.startBOT(this);
             tgups = new TGASYNCMEDIAGETTER (this,tgb);
            
            
        }
    }

    
    
    
    
    
    /**
     * Returns the collection of tables that are associated with this Conversation
     * @return ConversationUIManager
     */
    public ConversationUIManager getCHistoryUIM() {
        return cHistoryUIM;
    }


    /**
     * When a client connects to the server when logging on, it checks that the participant ID is OK
     * @param participantID The ID entered by the participant on the client
     * @return permission to login
     */
    public synchronized boolean requestPermissionForNewParticipantToBeAddedToConversation(String participantID){
        return cC.requestParticipantJoinConversation(participantID);
    }




    /**
     * Adds participant to the Conversation and sends the participant a MessageClientSetupParameters message with
     * the necessary window and client settings.
     *
     * @param p Participant to be added to the Conversation
     */
    public synchronized boolean addNewParticipant(Participant p){
        
          Vector participants = ps.getAllParticipants();
        
          for(int i=0;i<participants.size();i++){
              Participant pAlreadyLoggedIn = (Participant)participants.elementAt(i);
              if(p.getParticipantID().equals(pAlreadyLoggedIn.getParticipantID())){
                    String messageToDisplay = "There is an error: A participant with a duplicate ParticipantID is logging in:\n"+
                            pAlreadyLoggedIn.getParticipantID()+", "+pAlreadyLoggedIn.getUsername()+" is already logged in\n"+
                            p.getParticipantID()+", "+p.getUsername()+" is about to log in\n"
                            + "THIS SHOULD NOT HAPPEN! PLEASE CHECK THE INSTRUCTIONS GIVEN TO THE PARTICIPANTS!!!\n"
                            + "IF YOU HAVE JUST STARTED AN EXPERIMENT, YOU MUST RESTART THE SERVER AND CLIENTS!!!! DO NOT CONTINUE!\n"
                            + "IF YOU ARE IN THE MIDDLE OF RUNNING AN EXPERIMENT, YOU SHOULD PROBABLY PRESS OK AND HOPE FOR THE BEST!";
                    
                            
                     this.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns("ERROR", messageToDisplay);
                    CustomDialog.showDialog(messageToDisplay);
                   
              }
          }
          
      try{
        ps.addNewParticipant(p);
        int ownWindowNumber = 0;
        MessageClientSetupParameters mcsp =cC.processRequestForInitialChatToolSettings();
        mcsp.setNewEmailAndUsername(p.getParticipantID(),p.getUsername());
        p.sendMessage(mcsp);
        System.out.println("added new participant "+p.getParticipantID());
        if(this.expManager.emui!=null){
           this.expManager.emui.println("Main", "Participant with email: "+p.getParticipantID()+" and with username: "+p.getUsername()+" has logged in to "+cC.getID()+" there are "+ps.getAllParticipants().size());
        }
      }catch (Exception e){
         System.err.println("Problem adding new participant");
      }
     
      cC.participantJoinedConversation(p);
      
     this.cHistoryUIM.updateParticipantsListChanged(this.getParticipants().getAllParticipants());
     this.mm.updateParticipants(this.getParticipants().getAllParticipants().size());
      
      return true;
      }

    
    /**
     * 
     * @return the Metrics object that counts various metrics for the server GUI
     */
    public Metrics getMm() {
        return mm;
    }

    
    
    
    
    
    /**
     * This method still needs to be implemented and verified.
     * @param p
     */
    public void reactivateParticipant(Participant p){

       try{
        Conversation.printWSln("Main", "LOGGINGA");
        int ownWindowNumber = 0;
        Conversation.printWSln("Main", "LOGGINGB");
        MessageClientSetupParameters mcsp = cC.processRequestForInitialChatToolSettings();
        Conversation.printWSln("Main", "LOGGINGC");
        mcsp.setNewEmailAndUsername(p.getParticipantID(),p.getUsername());
        Conversation.printWSln("Main", "LOGGINGD");
        p.sendMessage(mcsp);
        Conversation.printWSln("Main", "LOGGINGE");
        cC.participantRejoinedConversation(p);
        CustomDialog.showModelessDialog("Participant ID: "+p.getParticipantID()+" USERNAME"+p.getUsername()+" was reconnected.");
        
        Conversation.printWSln("Main", "LOGGINGF");
        Conversation.printWSln("Main", "Participant "+p.getParticipantID()+" reactivated ");
      }catch (Exception e){
          System.err.println("Problem reactivating participant");
          e.printStackTrace();
      }
   }

    
    
    

    long timeOfLastEvent=-9999;
    
    
    /**
     * Saves the name of an event to a log file "debuggingevents.txt"
     * @param eventName The name of the event
     */
    public void debug_SaveEvent(String eventName){
        String description ="";
        if(timeOfLastEvent==-9999) {
            description = eventName + new Date().getTime()+"\n";
            timeOfLastEvent= new Date().getTime();
        }
        else{
            long currentTime = new Date().getTime();
            long timeSinceLast = currentTime - timeOfLastEvent;
            description = timeSinceLast + ": "+eventName+": "+currentTime+"\n";
            timeOfLastEvent=currentTime;
        }  
        convIO.saveTextToFileCreatingIfNecessary(description, "debuggingevents.txt");
    }


     private Message mostRecentMessageReceived = null;

    /**
     * Loop that polls {@link Participants} for any incoming messages. On receiving a message it calls the corresponding
     * methods in {@link diet.server.ConversationController}.
     *
     */
    @Override
    public void run(){
        System.out.println("Starting conversationcontroller");
          System.err.println("HEREINCOMING3");
        
        while (isConversationActive()){
          try{
               System.err.println("HEREINCOMING4");
             if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("1");
             mm.registerLoadingNextMessage(mostRecentMessageReceived);
             Message m = (Message) ps.getNextMessage();
             mostRecentMessageReceived =m;
             mm.registerIncomingMessage(mostRecentMessageReceived);
             
             if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("2");
             
              System.err.println("HEREINCOMING5");
             
             if (m!=null){
               if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("3"+m.getClass().toString());
               cHistoryUIM.updateControlPanel(m);
               if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("4");
               convIO.saveMessage(m);
               if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("5");
               //if(m instanceof diet.message.MessageClientInterfaceEvent) 
              
                System.err.println("TRYING TO FIND PARTICIPANT:"+m.getEmail());
               Participant origin = ps.findParticipantWithEmail(m.getEmail());
              
               
               
                if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("6");
              
                 
                if(m instanceof MessageButtonPressFromClient){
                    MessageButtonPressFromClient mbpfc = (MessageButtonPressFromClient)m; 
                    cC.processButtonPress(origin, mbpfc);
                } 
                 
                
                if (m instanceof MessageChatTextFromClient) {
                    if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("7");
                    MessageChatTextFromClient msctfc = (MessageChatTextFromClient)m;
                    try{
                      cC.processChatText(origin,msctfc);
                    }catch(Exception e){
                         printWln("Main","There was an error processing chattext");
                         printWln("Main",e.getMessage());
                         e.printStackTrace();
                         this.saveErrorLog("Error processing chattext");
                         convIO.saveErrorLog(e);  
                    }
                    
                    if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("8"); 
              
                    if(m instanceof MessageChatTextFromClient && diet.debug.Debug.debugtimers ){    
                         ((MessageChatTextFromClient)m).saveTime("serverConversation.hasBeenProcessedByConversationController");
                     }
                    
                    if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("9"); 

                    if(!msctfc.hasBeenRelayedByServer){
                        String prefix = "NOTRELAYED";
                        Vector vAdditionalValues = cC.getAdditionalInformationForParticipant(origin);
                         
                        String subdialogueID =  cC.pp.getSubdialogueID(origin);
                         
                        this.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(origin, msctfc, subdialogueID, vAdditionalValues);
                         
                      
                         
                         if(m instanceof MessageChatTextFromClient && diet.debug.Debug.debugtimers ){    
                           ((MessageChatTextFromClient)m).saveTime("serverConversation.hasBeenSavedBecauseNotrelayed");
                         }
                    
                    }
                    if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("10"); 
                    
                    if(diet.debug.Debug.debugtimers){
                        Vector allTimesSaved = msctfc.allTimes;
                        String textToSave = "------------\n";
                        textToSave = textToSave + msctfc.getEmail()+":"+msctfc.getUsername()+"\n"+ msctfc.getText()+"\n";
                        for(int i=0;i<allTimesSaved.size();i++){
                            AttribVal av = (AttribVal)allTimesSaved.elementAt(i);
                            
                            String diffval="";
                            if(i>0) {
                                long prevvalue = (long)((AttribVal)allTimesSaved.elementAt(i-1)).value;
                                long difference = (long)av.value - prevvalue;
                                diffval = difference+"";
                            }  
                            textToSave  =   textToSave+ diffval+":  "+ av.id +": "+ av.getValAsString()+"\n";
                        }     
                        this.convIO.saveTextToFileCreatingIfNecessary(textToSave,"debugoutputA");
                        long prevvalue = (long)((AttribVal)allTimesSaved.elementAt(allTimesSaved.size()-1)).value;
                        long difference = (long)new Date().getTime() - prevvalue;
                        String diffv = ""+difference;
                        
                        
                        
                        textToSave = textToSave + diffv+ "SecondSave: "+new Date().getTime()+"\n";
                        this.convIO.saveTextToFileCreatingIfNecessary(textToSave,"debugoutputB");
                    }
                    
                    
                    
                    if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("11"); 
                    
                }
                else if (m instanceof MessageKeypressed){
                    if(DefaultConversationController.sett.debug_debugTime)this.debug_SaveEvent("12"); 
                    MessageKeypressed mkp = (MessageKeypressed)m;
                    String txtEntered = mkp.getContentsOfTextEntryWindow();
                    if(txtEntered!=null){
                        if(txtEntered.length()>0){
                           char txtEnteredLastChar = txtEntered.charAt(txtEntered.length()-1);
                        }
                    }
                    System.out.println(origin.getParticipantID());
                    System.out.println(mkp.getKeypressed());
                    this.saveClientKeypressToFile(origin, mkp);
                    cC.processKeyPress(origin,mkp);

                }
                else if (m instanceof MessageWYSIWYGDocumentSyncFromClientInsert){
                    
                    MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp = (MessageWYSIWYGDocumentSyncFromClientInsert)m;
                    getDocChangesIncoming().addInsert(origin,mWYSIWYGkp.getTextToAppendToWindow(),mWYSIWYGkp.getOffset(),mWYSIWYGkp.getTimeOfReceipt().getTime());
                    cC.processWYSIWYGTextInserted(origin,mWYSIWYGkp);
                    try{
                        String textToappendtoWindow =  mWYSIWYGkp.getTextToAppendToWindow();
                        
                    }catch(Exception e){
                        e.printStackTrace();
                        //System.exit(-456);
                    }
                    this.cHistoryUIM.updateChatToolTextEntryFieldsUI(origin.getParticipantID(), origin.getUsername(),mWYSIWYGkp.getAllTextInWindow() );
                }
                else if (m instanceof MessageWYSIWYGDocumentSyncFromClientRemove){
                    MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp = (MessageWYSIWYGDocumentSyncFromClientRemove)m;
                    getDocChangesIncoming().addRemove(origin,mWYSIWYGkp.getOffset(),mWYSIWYGkp.getLength(),mWYSIWYGkp.getTimeOfReceipt().getTime());
                    cC.processWYSIWYGTextRemoved(origin,mWYSIWYGkp);
                    this.cHistoryUIM.updateChatToolTextEntryFieldsUI(origin.getParticipantID(), origin.getUsername(),mWYSIWYGkp.getAllTextInWindow() );
                }
 
                else if (m instanceof MessageErrorFromClient){
                    MessageErrorFromClient mefc = (MessageErrorFromClient)m;
                    this.printWln("Main", "ERROR IN CLIENT: "+m.getEmail()+" "+m.getUsername()+"\n"+mefc.getErrorMessage());
                    System.err.println("ERROR IN CLIENT: "+m.getEmail()+" "+m.getUsername()+"\n"+mefc.getErrorMessage());
                    Throwable t = mefc.getThrowable();
                    this.saveErrorLog("ERROR IN CLIENT: "+m.getEmail()+" "+m.getUsername()+"\n"+mefc.getErrorMessage());
                }
                else if(m instanceof MessageClientInterfaceEvent){
                    MessageClientInterfaceEvent mcie = (MessageClientInterfaceEvent)m;
                    try{
                    cC.processClientEvent(origin,mcie);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    convIO.saveClientEvent(mcie);
                    String eventtype = mcie.getClientInterfaceEvent().getType();
                    if(eventtype.equalsIgnoreCase("stimulusimage_change_confirm")){
                       long timeOfDisplayOnClient =  mcie.getClientInterfaceEvent().getClientTimeOfDisplay();
                       String name = (String)mcie.getClientInterfaceEvent().getValue("name");
                       this.saveAdditionalRowOfDataProducedOnCLIENTToSpreadsheetOfTurns( timeOfDisplayOnClient,"stimulusimage_change_confirm"  , origin, name);     
                    }
                }
                else if (m instanceof MessagePopupResponseFromClient){
                    try{
                       MessagePopupResponseFromClient mpr = (MessagePopupResponseFromClient)m;
                       String[] options = mpr.getOptions();
                       String question = mpr.getQuestion();
                       String title = mpr.getTitle();
                       String optionsFLATTENED = "";
                       for(int l=0;l<options.length;l++){
                           optionsFLATTENED = optionsFLATTENED+options[l];
                       }
                       String participantID = "not_yet_set";
                       String username = "not_yet_set";
                       participantID = mpr.getEmail();
                       username = mpr.getUsername();//origin.getUsername();
                        
                       String popupID=mpr.getPopupID();
                        
                       String s4 = ""+mpr.getTimeOfReceipt().getTime();
                       String s5 = ""+mpr.getTimeOfReceipt();
                       String s7 = optionsFLATTENED;
                       
                       String timeOnClientOfShowing = "(TimeOnClientOfShowing:"+mpr.timeOnClientOfDisplay+")";
                       String timeOnClientOfChoice = "(TimeOnClientOfSelecting:"+mpr.timeOfChoice+")";
                       
                     
                       
                       
                       String textToSave = (title+"/"+question+"/"+optionsFLATTENED).replaceAll("\n","(NEWLINE)")+ timeOnClientOfShowing+timeOnClientOfChoice+"_"+mpr.getSelection()+"__"+mpr.getSelectedValue();
                       
                       this.saveAdditionalRowOfDataToSpreadsheetOfTurns("popupreceived_"+popupID, origin, textToSave);
                       
                       cC.processPopupResponse(origin, (MessagePopupResponseFromClient)m);
                        
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    
                }
                
                
                
                
                else if(m instanceof MessageTask){
                   cC.processTaskMove((MessageTask)m, origin);
                }
                else if (m instanceof TelegramMessageFromClient){
                   System.err.println("HEREINCOMING6");
                   TelegramMessageFromClient tmfc =  (TelegramMessageFromClient)m;
                   TelegramParticipant tcp = (TelegramParticipant)origin;
                   
                   try{
                       String group = cC.pp.getSubdialogueID(origin);
                       if(group==null ){
                           group="";
                       }        
                       long dateOfReceipt =  ((TelegramMessageFromClient)m).timeOfCreation;
                       String text = ((TelegramMessageFromClient)m).u.toString();
                       convIO.saveTelegramIO(group, origin.getParticipantID(), origin.getUsername(),"from",dateOfReceipt , text);
                   }catch(Exception e){
                       e.printStackTrace();
                   }
                   
                   if(tmfc.u.hasMessage()&&  tmfc.u.getMessage().hasPhoto()){
                       System.err.println("HEREINCOMING - ADDING IMAGE");
                       tgups.addItem(tcp,tmfc.u);
                   }
                   else if(tmfc.u.hasMessage()&&  tmfc.u.getMessage().hasVoice()){
                       System.err.println("HEREINCOMING - ADDING VOICE");
                       tgups.addItem(tcp,tmfc.u);
                   }
                   
                   boolean hasBeenModifiedByAutoIntervention = false;
                   if(tmfc.u.hasMessage()&&tmfc.u.getMessage().hasText()){
                        
                        hasBeenModifiedByAutoIntervention =  cC.processAutoIntervention(origin,tmfc);
              
                   }
                   
                   if(!hasBeenModifiedByAutoIntervention)cC.telegram_processTelegramMessageFromClient(tcp,tmfc);
                   
                   if(tmfc.u.hasPollAnswer()){
                       cC.telegram_processPollAnswerFromClient(tcp,tmfc);
                   }
                   
                   if(!tmfc.hasBeenRelayed){
                        boolean nonRelayedHasBeenSaved=false;
                        if(tmfc.u.hasMessage() && tmfc.u.getMessage().hasText()){
                              long senderTelegramLogin = tmfc.u.getMessage().getChatId();
                              String subdialogueID =  cC.pp.getSubdialogueID(origin);
                              if(subdialogueID==null)subdialogueID=""; 
                               
                             
                              
                              //Vector vAdditionalValues = cC.getAdditionalInformationForParticipant(origin);
                              
                               Vector addVals = cC.getAdditionalInformationForParticipant(origin);
                              
                             
  
                               
                               
                               
                               
                              
                              AttribVal av1 = new AttribVal("telegramtype","text");
                              addVals.add(av1);
            
                              AttribVal av2 = new AttribVal("telegramrawdata",tmfc.u.toString());
                              addVals.add(av2);
                              
                               Vector vAddValsFromMessage = tmfc.getAttribVals();
                               addVals.addAll(vAddValsFromMessage);
                              
                              
                              cH.saveInterceptedNonRelayedMessage(subdialogueID, tmfc.u.getMessage().getDate(), tmfc.u.getMessage().getDate(),
                                      tmfc.timeOfCreation, 
                                      origin.getParticipantID(),
                                      origin.getUsername(), 
                                      origin.getUsername(), 
                                      tmfc.u.getMessage().getText(), 
                                      new Vector(),  false, new Vector(), new Vector(),new Vector(),
                                      addVals,false);
                              nonRelayedHasBeenSaved=true;
                        
                        }
                        if(tmfc.u.hasMessage() && tmfc.u.getMessage().hasPhoto()){
                              long senderTelegramLogin = tmfc.u.getMessage().getChatId();
                              String subdialogueID =  cC.pp.getSubdialogueID(origin);
                              if(subdialogueID==null)subdialogueID=""; 
                               
                              
                              
                              Vector addVals = cC.getAdditionalInformationForParticipant(origin);
                              AttribVal av1 = new AttribVal("telegramtype","photo");
                              addVals.add(av1);
            
                              String caption = tmfc.u.getMessage().getCaption();
                              if(caption==null)caption="";
                              AttribVal av2 = new AttribVal("caption", caption);
                              addVals.add(av2);
             
                              AttribVal av3 = new AttribVal("telegramrawdata",tmfc.u.toString());
                              addVals.add(av3);
                              
                              Vector vAddValsFromMessage = tmfc.getAttribVals();
                              addVals.addAll(vAddValsFromMessage);
                              
                              
                              String text = tmfc.u.getMessage().getText();
                              if(text==null)text="";
                              
                              cH.saveInterceptedNonRelayedMessage(subdialogueID, tmfc.u.getMessage().getDate(), tmfc.u.getMessage().getDate(),
                                      tmfc.timeOfCreation, 
                                      origin.getParticipantID(),
                                      origin.getUsername(), 
                                      "", 
                                      text, 
                                      new Vector(),  false, new Vector(), new Vector(),new Vector(),
                                      addVals,false);
                               nonRelayedHasBeenSaved=true;
                        
                        }
                        if(tmfc.u.hasMessage() && tmfc.u.getMessage().hasVoice()){
                              long senderTelegramLogin = tmfc.u.getMessage().getChatId();
                              String subdialogueID =  cC.pp.getSubdialogueID(origin);
                              if(subdialogueID==null)subdialogueID=""; 
                               
                              
                              Vector addVals = cC.getAdditionalInformationForParticipant(origin);
                              AttribVal av1 = new AttribVal("telegramtype","voice");
                              addVals.add(av1);
            
                              AttribVal av2 = new AttribVal("telegramrawdata",tmfc.u.toString());
                              addVals.add(av2);
                              
                              String text = tmfc.u.getMessage().getText();
                              if(text==null)text="";
                              
                              Vector vAddValsFromMessage = tmfc.getAttribVals();
                               addVals.addAll(vAddValsFromMessage);
                              
                              cH.saveInterceptedNonRelayedMessage(subdialogueID, tmfc.u.getMessage().getDate(), tmfc.u.getMessage().getDate(),
                                      tmfc.timeOfCreation, 
                                      origin.getParticipantID(),
                                      origin.getUsername(), 
                                      "", 
                                      text, 
                                      new Vector(),  false, new Vector(), new Vector(),new Vector(),
                                      addVals,false);
                               nonRelayedHasBeenSaved=true;
                        
                        }
                        if(tmfc.u.hasCallbackQuery()){
                              long senderTelegramLogin = ((TelegramParticipant)origin).getConnection().telegramID;
                              String subdialogueID =  cC.pp.getSubdialogueID(origin);
                              if(subdialogueID==null)subdialogueID=""; 
                               
                              
                              Vector addVals = cC.getAdditionalInformationForParticipant(origin);
                              AttribVal av1 = new AttribVal("telegramtype","callback");
                              addVals.add(av1);
            
                              AttribVal av2 = new AttribVal("telegramrawdata",tmfc.u.toString());
                              addVals.add(av2);
                              
                                                       
                                                      
                            
                              CallbackQuery cbq = tmfc.u.getCallbackQuery();
                              org.telegram.telegrambots.meta.api.objects.Message  mcbq =cbq.getMessage();
                              String callbackData =   cbq.getData();
                              
                              String text="";
                              try{ text=cbq.getMessage().getText();} catch(Exception e){e.printStackTrace(); }
                              if(text==null ) text=""; 
                            
                              Vector vAddValsFromMessage = tmfc.getAttribVals();
                              addVals.addAll(vAddValsFromMessage);
                              
                              cH.saveInterceptedNonRelayedMessage(subdialogueID, cbq.getMessage().getDate(), cbq.getMessage().getDate(),
                                      tmfc.timeOfCreation, 
                                      origin.getParticipantID(),
                                      origin.getUsername(), 
                                      "", 
                                      text, 
                                      new Vector(),  false, new Vector(), new Vector(),new Vector(),
                                      addVals,false);
                               nonRelayedHasBeenSaved=true;
                               System.err.println("callbackdata: "+callbackData);
                        }    
                        
                        if(tmfc.u.hasPollAnswer()){
                              long senderTelegramLogin = tmfc.u.getMessage().getChatId();
                              String subdialogueID =  cC.pp.getSubdialogueID(origin);
                              if(subdialogueID==null)subdialogueID=""; 
                               
                              
                              Vector addVals = cC.getAdditionalInformationForParticipant(origin);
                              AttribVal av1 = new AttribVal("telegramtype","pollanswer");
                              addVals.add(av1);
            
                              AttribVal av2 = new AttribVal("telegramrawdata",tmfc.u.toString());
                              addVals.add(av2);
                              
                              String text = tmfc.u.getPollAnswer().toString();
                              if(text==null)text="";
                 
                              long dtlong = new Date().getTime();
                              
                              Vector vAddValsFromMessage = tmfc.getAttribVals();
                              addVals.addAll(vAddValsFromMessage);
                              
                              cH.saveInterceptedNonRelayedMessage(subdialogueID, tmfc.timeOfCreation, tmfc.timeOfCreation,
                                      tmfc.timeOfCreation, 
                                      origin.getParticipantID(),
                                      origin.getUsername(), 
                                      "", 
                                      text, 
                                      new Vector(),  false, new Vector(), new Vector(),new Vector(),
                                      addVals,false);
                               nonRelayedHasBeenSaved=true;
                        
                        }
                        
                        
                        
                        if( !nonRelayedHasBeenSaved){
                              long senderTelegramLogin = ((TelegramParticipant)origin).getConnection().telegramID;
                              String subdialogueID =  cC.pp.getSubdialogueID(origin);
                              if(subdialogueID==null)subdialogueID=""; 
                               
                              
                              Vector addVals = cC.getAdditionalInformationForParticipant(origin);
                              AttribVal av1 = new AttribVal("telegramtype","nonsupported");
                              addVals.add(av1);
            
                              AttribVal av2 = new AttribVal("telegramrawdata",tmfc.u.toString());
                              addVals.add(av2);
                              
                              Vector vAddValsFromMessage = tmfc.getAttribVals();
                              addVals.addAll(vAddValsFromMessage);
                              
                             
                              
                              cH.saveInterceptedNonRelayedMessage(subdialogueID, tmfc.u.getMessage().getDate(), tmfc.u.getMessage().getDate(),
                                      tmfc.timeOfCreation, 
                                      origin.getParticipantID(),
                                      origin.getUsername(), 
                                      "", 
                                      "", 
                                      new Vector(),  false, new Vector(), new Vector(),new Vector(),
                                      addVals,false);
                               nonRelayedHasBeenSaved=true;
                        }
                        
                        
                        
                        
                       
                        
                       
                   }
                   
                   
                   /*
                   if(tmfc.hasBeenRelayedByServer){
                        String prefix = "NOTRELAYED";
                        Vector vAdditionalValues = cC.getAdditionalInformationForParticipant(origin);
                         
                        String subdialogueID =  cC.pp.getSubdialogueID(origin);
                         
                        this.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(origin, msctfc, subdialogueID, vAdditionalValues);
                         
                      
                         
                         if(m instanceof MessageChatTextFromClient && diet.debug.Debug.debugtimers ){    
                           ((MessageChatTextFromClient)m).saveTime("serverConversation.hasBeenSavedBecauseNotrelayed");
                         }
                    
                    }
                   */
                   
                   
                   
                   
                    
               }
                 System.err.println("HEREINCOMING8");
                
         }
           System.err.println("HEREINCOMING9");

          if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE){System.out.println("MCT44");System.out.flush();}
          }catch (Exception e){
               System.err.println(cC.getID()+ ": ERROR SOMEWHERE IN THE CONVERSATION CONTROLLER");
               printWln("Main","There is an ERROR in the Conversation Controller");
               printWln("Main","Check the ERRORLOG.TXT file in the directory containing");
               printWln("Main","the saved experimental data");
               printWln("Main",e.getMessage());
                e.printStackTrace();
               convIO.saveErrorLog(e);
              
          }
       if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE){System.out.println("MCT45");System.out.flush();}
      }
         if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE){System.out.println("MCT46");System.out.flush();}
          System.err.println("THREAD TERMINATED");
          System.err.flush();
          conversationThreadHasTerminated=true;
          
          
         
    }




    
    
    /**
     * Sends instructions from the server to the client. The message is displayed in the conversation history window.
     * This ensures that the message is coloured differently:  messages sent by the participants (the default colour is red).
     * It also ensures that the message is stored in the correct format in the turns.txt datafile.
     * 
     * @param recipient The participant who receives the instruction message
     * @param text The text to be displayed in the conversation history window
     * @param additionalValues A vector (can be empty) where you can add attribute/value pairs to be added to "turns.txt"
     * 
     */
    public void sendInstructionToParticipant( Participant recipient, String text, Vector<AttribVal> additionalValues){  
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        MutableAttributeSet style = cC.getStyleManager().getStyleForInstructionMessages(recipient);
        int windowNo = cC.getStyleManager().getWindowNumberInWhichParticipantBReceivesTextFromServer_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(recipient);
        MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),"server","",text,windowNo,false,style);
        Vector recipientNames = new Vector();
        recipientNames.addElement(recipient.getUsername());
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mctc);
        cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(),  "server",text, recipientNames, additionalValues, false);
    }
    
    
    
    /**
     * Sends instructions from the server to the client. The message is displayed in the conversation history window.
     * This ensures that the message is coloured differently:  messages sent by the participants (the default colour is red).
     * It also ensures that the message is stored in the correct format in the turns.txt datafile.
     * 
     * @param recipient The participant who receives the instruction message
     * @param text The text to be displayed in the conversation history window
     *
     * 
     */
    public void sendInstructionToParticipant( Participant recipient, String text){  
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
         MutableAttributeSet style = cC.getStyleManager().getStyleForInstructionMessages(recipient);
        int windowNo = cC.getStyleManager().getWindowNumberInWhichParticipantBReceivesTextFromServer_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(recipient); 
        MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),"server","",text,windowNo,false,style);
        Vector recipientNames = new Vector();
        recipientNames.addElement(recipient.getUsername());
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mctc);
        cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), "server",text, recipientNames, new Vector(), false);
    }
   
    
    /**
    * Sends instructions from the server to multiple clients. The message is displayed in the conversation history window.
    * This ensures that the message is coloured differently:  messages sent by the participants (the default colour is red).
    * It also ensures that the message is stored in the correct format in the turns.txt datafile.
    * 
    * @param recipients The participants who receive the instruction message
    * @param text The text to be displayed in the conversation history window
    * 
    */  
    public void sendInstructionToMultipleParticipants(Vector recipients, String text){
        for(int i=0;i<recipients.size();i++){
             Participant pRecipient = (Participant )recipients.elementAt(i);
             sendInstructionToParticipant(pRecipient, text);
        }
    }
    

    /**
     * Displays a fake turn in the conversation history window of the participant, which appears to come from another participant.
     *      * 
     * @param apparentOrigin The participant who the turn appears to originate from
     * @param recipient The participant who receives the turn
     * @param text The text of the turn
     * 
     */
    public void sendArtificialTurnFromApparentOrigin( Participant apparentOrigin, Participant recipient, String text){  
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        MutableAttributeSet style = cC.getStyleManager().getStyleForRecipient(apparentOrigin, recipient);
        int windowNo = cC.getStyleManager().getWindowNumberInWhichParticipantBReceivesTextFromParticipantA_Deprecated_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(apparentOrigin, recipient);
        MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),"server",apparentOrigin.getUsername(),text,windowNo,true,style);
        Vector recipientNames = new Vector();
        recipientNames.addElement(recipient.getUsername());
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mctc);
        
        Vector additionalInfo = new Vector();
        try{
           additionalInfo = cC.getAdditionalInformationForParticipant(apparentOrigin);
           if(additionalInfo==null)additionalInfo = new Vector();
        }catch(Exception e){
            e.printStackTrace();
        }
           
        cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(),   apparentOrigin.getUsername(),text, recipientNames, additionalInfo, false);
    }
    
    
    /**
    * Displays a fake turn in the conversation history window of the recipient, which appears to come from another participant. 
    * This method is useful if you want to spoof the username of a participant who isn't actually participating in the conversation.
    * 
    *      * 
    * @param apparentOriginUsername The username of the participant who the turn appears to originate from. 
    * @param recipient The participant who receives the turn
    * @param text The text of the turn
    * 
    */
    public void sendArtificialTurnFromApparentOrigin( String apparentOriginUsername, Participant recipient, String text){  
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        MutableAttributeSet style = cC.getStyleManager().getStyleFoOTHER1();
        int windowNo = 0;
        MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),"server",apparentOriginUsername,text,windowNo,true,style);
        Vector recipientNames = new Vector();
        recipientNames.addElement(recipient.getUsername());
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mctc);
        
        Vector additionalInfo = new Vector();
        try{
           additionalInfo = cC.getAdditionalInformationForParticipant(null);
           if(additionalInfo==null)additionalInfo = new Vector();
        }catch(Exception e){
            e.printStackTrace();
             if(additionalInfo==null)additionalInfo = new Vector();
        }
           
        cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(),   apparentOriginUsername,text, recipientNames, additionalInfo, false);
    }
    
    
    
    
    
    
   
    /**
     * Displays a fake turn in the conversation history window of the participant, which appears to come from another participant.
     *      * 
     * @param apparentOrigin The participant who the turn appears to originate from
     * @param recipient The participant who receives the turn
     * @param text The text of the turn
     * @param additionalValues A vector (can be empty) where you can add attribute/value pairs to be added to "turns.txt"
     * 
     */
     public void sendArtificialTurnFromApparentOrigin( Participant apparentOrigin, Participant recipient, String text, int windowNo, Vector<AttribVal> additionalValues){  
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        MutableAttributeSet style = cC.getStyleManager().getStyleForRecipient(apparentOrigin, recipient);     
        MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),"server",apparentOrigin.getUsername(),text,windowNo,true,style);
        mctc.setPrefixUsername(false);
        
        Vector recipientNames = new Vector();
        recipientNames.addElement(recipient.getUsername());
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mctc);
        if(additionalValues == null)additionalValues = new Vector();
        String textWithNoEnters = text.replace("\n", DefaultConversationController.sett.recordeddata_newlinestring).replace("\r", DefaultConversationController.sett.recordeddata_newlinestring);
        
        
        
        //String textWithNoEnters ="-------------";
        
        cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(),   apparentOrigin.getUsername(),textWithNoEnters, recipientNames, additionalValues, false);
    }
    
    
    /*
     * Deprecated method that is still included for 
     *      * 
     * @param apparentOrigin The participant who the turn appears to originate from
     * @param recipient The participant who receives the turn
     * @param text The text of the turn
     * @param additionalValues A vector (can be empty) where you can add attribute/value pairs to be added to "turns.txt"
     * 
     */ 
     public void deprecatedSendArtificialTurnFromApparentOriginToRecipient( String apparentOriginUsername, Participant recipient, boolean prefixUsername, String text,  String subdialogueID, MutableAttributeSet style, int windowNo, Vector<AttribVal> additionalValues){  
        MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),"server",apparentOriginUsername,text,windowNo,true,style);
        Vector recipientNames = new Vector();
        recipientNames.addElement(recipient.getUsername());
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mctc);
        
        
        Vector additionalInfo = new Vector();
        try{
           Participant pApparentOriginUsername = ps.findParticipantWithUsername(apparentOriginUsername);
           if(pApparentOriginUsername!=null){
               additionalInfo = cC.getAdditionalInformationForParticipant(pApparentOriginUsername);           
           }
           if(additionalInfo==null)additionalInfo = new Vector();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), apparentOriginUsername,text, recipientNames, additionalInfo, false);
    }
     
     
   
   public void ddeprecatedSendArtificialTurnFromApparentOriginToMultipleRecipients(String apparentOriginUsername, String subdialogueID ,Vector recipients, String text,   MutableAttributeSet style, int windowNo, Vector<AttribVal> additionalValues){  
        MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),"server",apparentOriginUsername,text,windowNo,true,style);
        Vector recipientNames = new Vector();
        for(int i=0;i<recipients.size();i++){
            Participant recipient = (Participant)recipients.elementAt(i);
            recipientNames.addElement(recipient.getUsername());
            ps.sendMessageToParticipant(recipient,mctc);        
        }
        
        Vector additionalInfo = new Vector();
        try{
           Participant pApparentOriginUsername = ps.findParticipantWithUsername(apparentOriginUsername);
           if(pApparentOriginUsername!=null){
               additionalInfo = cC.getAdditionalInformationForParticipant(pApparentOriginUsername);           
           }
           if(additionalInfo==null)additionalInfo = new Vector();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), apparentOriginUsername,text, recipientNames, additionalInfo, false);
    }

   
   
   /**
     * Displays a fake turn in the conversation history window of multiple participants.
     *      * 
     * @param apparentOrigin The participant who the turn appears to originate from
     * @param usernames The usernames of the participants who receive the text
     * @param text The text of the fake turn
     * 
     */
     public void sendArtificialTurnFromApparentOriginToParticipantUsernames(Participant apparentOrigin, Vector<String> usernames, String text ){   

        Vector additionalInfo = new Vector();
        try{
           additionalInfo = cC.getAdditionalInformationForParticipant(apparentOrigin); 
           if(additionalInfo==null)additionalInfo = new Vector();
        }catch(Exception e){
            e.printStackTrace();
        }
       
       
       
        for(int i=0;i<usernames.size();i++){
            Participant recipient = this.getParticipants().findParticipantWithUsername((String) usernames.elementAt(i));
            String subdialogueID = cC.pp.getSubdialogueID(recipient);
            MutableAttributeSet style = cC.getStyleManager().getStyleForRecipient(apparentOrigin, recipient);
            int windowNo = cC.getStyleManager().getWindowNumberInWhichParticipantBReceivesTextFromParticipantA_Deprecated_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(apparentOrigin, recipient);
            MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),"server",apparentOrigin.getUsername(),text,windowNo,true,style); 
             Vector recipientName = new Vector();
            recipientName.addElement(recipient.getUsername());
            ps.sendMessageToParticipant(recipient,mctc);        
            cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), apparentOrigin.getUsername(),text, recipientName, additionalInfo, false);
   
            }
         }
   
     
     
     
     public void sendArtificialTurnFromApparentOriginToPermittedParticipants(Participant apparentSender,  String text){
          Vector<Participant> vP = cC.pp.getRecipients(apparentSender);
            Vector<String>  vID = new Vector();
            
            for(int i=0;i<vP.size();i++){
                vID.add(vP.elementAt(i).getParticipantID());
            }
            sendArtificialTurnFromApparentOriginToParticipantID(apparentSender, vID,  text);
     }
     
     
     
     public void sendArtificialTurnFromApparentOriginToParticipantID(Participant apparentSender, Vector<String> vid, String text){
         
        Vector additionalInfo = new Vector();
        try{
           additionalInfo = cC.getAdditionalInformationForParticipant(apparentSender); 
           if(additionalInfo==null)additionalInfo = new Vector();
        }catch(Exception e){
            e.printStackTrace();
        }
       
       
       
        for(int i=0;i<vid.size();i++){
            Participant recipient = this.getParticipants().findParticipantWithEmail((String) vid.elementAt(i));
            String subdialogueID = cC.pp.getSubdialogueID(recipient);
            MutableAttributeSet style = cC.getStyleManager().getStyleForRecipient(apparentSender, recipient);
            int windowNo = cC.getStyleManager().getWindowNumberInWhichParticipantBReceivesTextFromParticipantA_Deprecated_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(apparentSender, recipient);
            MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),"server",apparentSender.getUsername(),text,windowNo,true,style); 
             Vector recipientName = new Vector();
            recipientName.addElement(recipient.getUsername());
            ps.sendMessageToParticipant(recipient,mctc);        
            cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), apparentSender.getUsername(),text, recipientName, additionalInfo, false);
   
            }
         }
        
     
     
     
     
     
   /**
     * Displays a fake turn in the conversation history window of multiple participants.
     *      * 
     * @param apparentOrigin The participant who the turn appears to originate from
     * @param usernames The participants who receive the fake turn
     * @param text The text of the fake turn
     * 
     */
   public void sendArtificialTurnFromApparentOriginToParticipants(Participant apparentOrigin, Vector<Participant> recipients, String text   ){   
       
       
        Vector additionalInfo = new Vector();
        try{
           additionalInfo = cC.getAdditionalInformationForParticipant(apparentOrigin); 
           if(additionalInfo==null)additionalInfo = new Vector();
        }catch(Exception e){
            e.printStackTrace();
        }
       
       
       
        for(int i=0;i<recipients.size();i++){
            Participant recipient = recipients.elementAt(i);
            String subdialogueID = cC.pp.getSubdialogueID(recipient);
            MutableAttributeSet style = cC.getStyleManager().getStyleForRecipient(apparentOrigin, recipient);
            int windowNo = cC.getStyleManager().getWindowNumberInWhichParticipantBReceivesTextFromParticipantA_Deprecated_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(apparentOrigin, recipient);
            MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),"server",apparentOrigin.getUsername(),text,windowNo,true,style); 
             Vector recipientName = new Vector();
            recipientName.addElement(recipient.getUsername());
            ps.sendMessageToParticipant(recipient,mctc);        
            cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), apparentOrigin.getUsername(),text, recipientName, additionalInfo, false);
   
            }
         }
   
   
   
   
   
   
   
   
     
    /**
     * Deprecated - included because some older interventions still use this method.
     */
     public void deprecated_sendArtificialTurnToRecipient(Participant recipient,String text, int windowNo){
         MutableAttributeSet style = cC.getStyleManager().getStyleForInstructionMessages(recipient);
         deprecatedSendArtificialTurnFromApparentOriginToRecipient("", recipient, false,text, "", style,windowNo, new Vector() );
         
     }
    
     /**
     * Deprecated - included because some older interventions still use this method.
     */
     public void deprecatedsendArtificialTurnToRecipientWithEnforcedTextColour(Participant recipient,String text, int windowNo,  MutableAttributeSet style, String cvsPREFIX){
          deprecatedsendArtificialTurnToRecipientWithEnforcedTextColour(recipient, text, windowNo, cvsPREFIX,style);
     }

     /**
     * Deprecated - included because some older interventions still use this method.
     */
    public void deprecatedsendArtificialTurnToRecipientWithEnforcedTextColour(Participant recipient,String text, int windowNo, String prefix,  MutableAttributeSet style ){
        deprecatedSendArtificialTurnFromApparentOriginToRecipient("", recipient, false,text, "", style,windowNo, new Vector() );
     }    
     
    /**
     * Deprecated - included because some older interventions still use this method.
     */
    public void deprecated_sendArtificialTurnToRecipient(Participant recipient,String text, int windowNo, String prefixFORCSVSpreadsheetIDENTIFIER ){
        MutableAttributeSet style = cC.getStyleManager().getStyleForInstructionMessages(recipient);
        deprecatedSendArtificialTurnFromApparentOriginToRecipient("", recipient, false,text, "", style,windowNo, new Vector() );   
    }
    
    /**
     * Deprecated - included because some older interventions still use this method.
     */
     public void deprecated_sendArtificialMazeGameTurnFromApparentOriginToRecipientWithEnforcedTextColour(String apparentOriginUsername, Participant recipient,String text, int windowNo, String prefixFORCSVSpreadsheetIDENTIFIER,  MutableAttributeSet style ){
         deprecatedSendArtificialTurnFromApparentOriginToRecipient("", recipient, false,text, "", style,windowNo, new Vector() );   
     }
    
    /**
     * Deprecated - included because some older interventions still use this method.
     */
    public void deprecated_sendArtificialTurnToRecipient(Participant recipient,String text, int windowNo,  MutableAttributeSet style, String prefixFORCSVSpreadsheetIDENTIFIER ){
         deprecatedSendArtificialTurnFromApparentOriginToRecipient("", recipient, false,text, "", style,windowNo, new Vector() );   
    }
    
   
    
    /**
     *  
     * Shows a popup message on the client. The response is sent back to the {@link diet.server.DefaultConversationController.processPopupResponse(Participant origin, MessagePopupResponseFromClient mpr)}
     * 
     * @param popupID Your code should provide a unique ID so that when the response is received, it is clear what it is answering.
     * @param question The text displayed in the popup
     * @param options A list of options that the participant can select
     * @param title This will be displayed on the titlebar of the popup window
     * @param selection This is the default selected value
     */
    public void showPopupOnClientQueryInfo(String popupID,Participant recipient, String question, String[] options, String title, int selection){
        MessagePopup mp = new MessagePopup (popupID,"server", "server", question, options, title, selection);
        ps.sendMessageToParticipant(recipient,mp);
        
        String questionCleaned = question.replaceAll("\n", "(NEWLINE)");
        String titleCleaned = title.replaceAll("\n", "(NEWLINE)");
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        
        this.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(subdialogueID, "popupsent", recipient.getParticipantID(), recipient.getUsername(), new Date().getTime() ,new Date().getTime(), new Date().getTime(),titleCleaned+"-"+questionCleaned, new Vector());
        //this.saveDataToFile(title, title, question, selection, selection, title, null);
    }
    
    
    
    /**
     * Deprecated - included because some older interventions still use this method.
     */
    public void deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(Participant recipient, String subdialogueID, String data){
        this.saveAdditionalRowOfDataToSpreadsheetOfTurns(subdialogueID,"data"   , "Server", "server", recipient.getParticipantID()+"."+recipient.getUsername(),new Date().getTime() ,new Date().getTime(), new Date().getTime(), new Vector(), data, new Vector());
 
        //this.saveDataToFile(title, title, question, selection, selection, title, null);
    }
    
    /**
     * Deprecated - included because some older interventions still use this method.
     */
    public void deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(Participant sender,String recipientName , long timeCREATEDONCLIENT, String subdialogueID, String data){
        //String recipient
        this.saveAdditionalRowOfDataToSpreadsheetOfTurns(subdialogueID, "data",  sender.getParticipantID(), sender.getUsername(), recipientName, timeCREATEDONCLIENT,new Date().getTime(), new Date().getTime(), new Vector()  ,data,  new Vector());
 
       
    }
    
    /**
     * Deprecated - included because some older interventions still use this method.
     */
    public void deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(String subDialogueID, String data){    
        long timeOfCreationOnServer = new Date().getTime();
        Conversation.this.saveAdditionalRowOfDataToSpreadsheetOfTurns(subDialogueID, "data", "server", "server", "server",timeOfCreationOnServer ,timeOfCreationOnServer, timeOfCreationOnServer, new Vector(),data, new Vector());
        
    }
    
    /**
     * Deprecated - included because some older interventions still use this method.
     */
     public void deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(String subdialogue, String datatype, String senderID, String senderUsername, long timeOfCreationOnClient,long timeOfSendOnClient, long timeOfRELAYONSERVER, String text, Vector additionalData){
        try{
            Conversation.this.saveAdditionalRowOfDataToSpreadsheetOfTurns( subdialogue, datatype,  senderID, senderUsername, "", timeOfCreationOnClient , timeOfSendOnClient, timeOfRELAYONSERVER, new Vector(), text, additionalData);
        
         }catch (Exception e){
            Conversation.printWSln("Main", "Error saving data");
        }
    }
    
    
    /**
     * Saves data to the "turns.txt" datafile. 
     * 
     * If your code calls this directly - feel free to customize the fields how you see fit. If you do this, it is best to ensure you create your own unique value for datatype
     * 
     * @param subdialogueID The identifier of the subdialogue 
     * @param datatype The type of data, e.g. "normalturn", "interceptedturn", "data"
     * @param senderID The ID of the participant who generated the turn/data (can also be "server" for interventions or for data being saved)
     * @param senderUsername The username of the participant who generated the turn/data (can also be "server" for interventions or for data being saved)
     * @param apparentSenderUsername This field is only relevant for fake turns, where the apparent sender is different from the actual sender.
     * @param timeOfCreationOnClient The time on the client when the data was created (Not relevant for data that is created on server)
     * @param timeOfSendOnClient The time on the client when the data was sent (Not relevant for data that is created on server)
     * @param timeOfRELAYONSERVER The time on the server when the data was received/relayed from one participant to the other.
     * @param recipientsNames The participants who received this data
     * @param text The data (Save your data to this value)
     * @param additionalData A list of attribute/value pairs that will also be saved to the file as separate columns
     * 
     */
    public void saveAdditionalRowOfDataToSpreadsheetOfTurns(String subdialogueID, String datatype,  String senderID, String senderUsername, String apparentSenderUsername,
            long timeOfCreationOnClient ,long timeOfSendOnClient, long timeOfRELAYONSERVER, Vector recipientsNames, String text, Vector<AttribVal> additionalData){
        try{
           //System.err.println("SL02");
           String text1 = text.replace("\r","((NEWLINE))");
           String text2 = text1.replace(System.getProperty("line.separator"), "((((NEWLINE))))");
           
           cH.saveDataAsRowInSpreadsheetOfTurns(subdialogueID, datatype,timeOfCreationOnClient ,timeOfSendOnClient, timeOfRELAYONSERVER ,senderID, senderUsername, apparentSenderUsername,text2.replaceAll("\n", "(NEWLINE)"),recipientsNames,false,new Vector<Keypress>(), new Vector<DocChange>()  ,new Vector<ClientInterfaceEvent>() , additionalData, false);
             }catch (Exception e){
                e.printStackTrace();
        }
    }
    
    
    
    
    public void saveAdditionalRowOfDataToSpreadsheetOfTurns(String subdialogueID, String datatype,  String text){
        try{
            String senderID = "server";
             String senderUsername = "server";
             String apparentSenderUsername = "server";
             long timeOfCreationOnClient =  new Date().getTime();
             long timeOfSendOnClient = new Date().getTime();
             long timeOfRELAYONSERVER = new Date().getTime();
             Vector recipientsNames = new Vector();
             Vector<AttribVal> additionalData = new Vector();
            
           //System.err.println("SL02");
           String text1 = text.replace("\r","((NEWLINE))");
           String text2 = text1.replace(System.getProperty("line.separator"), "((((NEWLINE))))");
           
           cH.saveDataAsRowInSpreadsheetOfTurns(subdialogueID, datatype,timeOfCreationOnClient ,timeOfSendOnClient, timeOfRELAYONSERVER ,senderID, senderUsername, apparentSenderUsername,text2.replaceAll("\n", "(NEWLINE)"),recipientsNames,false,new Vector<Keypress>(), new Vector<DocChange>()  ,new Vector<ClientInterfaceEvent>() , additionalData, false);
             }catch (Exception e){
                e.printStackTrace();
        }
    }
    
    
    
    
    
    
   /*
    *
    * Convenience function - do not use unless you know what you are doing!
    */
    private void saveAdditionalRowOfDataProducedOnCLIENTToSpreadsheetOfTurns(long timeOnClient, String datatype,  Participant p, String value){
        try{
           String subDialogueID = cC.pp.getSubdialogueID(p);
           long timeOfCreationOnClient = timeOnClient; 
           long timeOfSendOnClient = timeOfCreationOnClient;
           long timeOfRELAYONSERVER = new Date().getTime();
           String senderID = p.getParticipantID();
           String senderUsername = p.getUsername();
           String recipient = p.getUsername();
           String text = value;
           
           Vector<AttribVal> additionalInfo = new Vector();
           try{
             additionalInfo = cC.getAdditionalInformationForParticipant(p);
             if(additionalInfo==null)additionalInfo = new Vector();
           }catch(Exception e){
              e.printStackTrace();
           }
           
           
           
           cH.saveDataAsRowInSpreadsheetOfTurns(subDialogueID, datatype,timeOfCreationOnClient ,timeOfSendOnClient, timeOfRELAYONSERVER ,senderID, senderUsername, recipient,text.replaceAll("\n", "(NEWLINE)"),new Vector(),false,new Vector<Keypress>(), new Vector<DocChange>()  ,new Vector<ClientInterfaceEvent>() , additionalInfo, false);
           }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    
     
    
    /**
     * Saves data to the "turns.txt" datafile. 
     * 
     *  
     * @param datatype The type of data, e.g. "normalturn", "interceptedturn", "data"
     * @param p The participant associated with the data
     * @param value The data (Save your data to this value)
     * 
     */    
    public void saveAdditionalRowOfDataToSpreadsheetOfTurns(String datatype,  Participant p, String value){
        if(value==null)value ="";
        saveAdditionalRowOfDataToSpreadsheetOfTurns(datatype,  p,  value, new Vector());
    }
    
    
    /**
     * Saves data to the "turns.txt" datafile. 
     * 
     *  
     * @param datatype The type of data, e.g. "normalturn", "interceptedturn", "data"
     * @param p The participant associated with the data
     * @param value The data (Save your data to this value)
     * @param additionalAttribVals A list of attribute/value pairs that will also be saved to the file as separate columns
     * 
     */    
    public void saveAdditionalRowOfDataToSpreadsheetOfTurns(String datatype,  Participant p, String value, Vector additionalAttribVals){
        try{
           if (value==null)value="";
           String subDialogueID = "notset";
           if(p!=null) subDialogueID =  cC.pp.getSubdialogueID(p);
           if(subDialogueID==null)subDialogueID="NULL";
          
           long currentTIME = new Date().getTime(); 
           long timeOfSendOnClient = currentTIME;
           long timeOfRELAYONSERVER = currentTIME;
           String senderID = p.getParticipantID();
           String senderUsername = p.getUsername();
           String recipient = p.getUsername();
           String text = value;
           
           Vector additionalInfo = new Vector();
           additionalInfo.addElement(""); 
           try{
             additionalInfo = cC.getAdditionalInformationForParticipant(p);
             if(additionalInfo==null)additionalInfo = new Vector();
           }catch(Exception e){
              e.printStackTrace();
           }
           if(additionalAttribVals!=null & additionalAttribVals.size()>0){
               additionalInfo.addAll(additionalAttribVals);
           }
           
           cH.saveDataAsRowInSpreadsheetOfTurns(subDialogueID, datatype,currentTIME ,timeOfSendOnClient, timeOfRELAYONSERVER ,senderID, senderUsername, recipient,text.replaceAll("\n", "(NEWLINE)"),new Vector(),false,new Vector<Keypress>(), new Vector<DocChange>()  ,new Vector<ClientInterfaceEvent>() , additionalInfo, false);
           }catch (Exception e){
            e.printStackTrace();
        }
    }
   

    /**
     * Saves keypress data to the "keyspressed.txt" datafile. 
     * 
     * This is handled automatically - your code shouldn't have to call/modify this.
     *  
     * @param sender The participant who generated the keypress
     * @param mkp the keypress information
     * 
     */  
    public void saveClientKeypressToFile(Participant sender, MessageKeypressed mkp){
        String subDialogueID = cC.pp.getSubdialogueID(sender);
        if (subDialogueID ==null) subDialogueID = "";
        this.saveClientKeypressToFile(sender, mkp, subDialogueID);
    }

    /**
     * Saves keypress data to the "keyspressed.txt" datafile. 
     * 
     * This is handled automatically - your code shouldn't have to call/modify this.
     *  
     * @param sender The participant who generated the keypress
     * @param mkp The keypress information
     * @param subdialogueID The subdialogue ID of the participant
     * @param additionalAttribVals A list of attribute/value pairs that will also be saved to the file as separate columns
     * 
     */  
    public void saveClientKeypressToFile(Participant sender, MessageKeypressed mkp,String subDialogueID ){
        convIO.saveClientKeypressFromClient(mkp, subDialogueID);
    }

    
       
    
   /**
    *
    * Called by ConversationController to send a turn it has received, unmodified, to the other participants
    * If the ConversationController object modifies the turn in any way, e.g.adding,deleting,substituting characters, or manipulating the timing, use one of the sendArtificialTurn methods.
    *
    * @param sender The participant who created the turn
    * @param recipients The participants who will receive the turn
    * @param mct The original message produced by the sender
    * @param windowNumber The window number on the client in which the message will be displayed. This is only relevant for the multiple window version of the  Turn By Turn Interface. The default window number is 0.
    * @param subdialogueID The subdialogue ID of the participant
    * 
    */ 
      public void relayTurnToMultipleParticipants(Participant sender,Vector<Participant> recipients, MessageChatTextFromClient mct,   int windowNumber, String subdialogueID, Vector<AttribVal> additionalValues){
        mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        
       
        
        Vector pUsernames = new Vector();
        for(int i=0;i<recipients.size();i++){
            Participant recipient = (Participant)recipients.elementAt(i);
            pUsernames.addElement(recipient.getUsername());  
             MutableAttributeSet style = cC.getStyleManager().getStyleForRecipient(recipient, sender);
            MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),sender.getParticipantID(),sender.getUsername(),mct.getText(),windowNumber,true,style);
            ps.sendMessageToParticipant(recipient, mctc);
        }
        cH.saveMessageRelayedToOthers(subdialogueID,mct.getStartOfTypingOnClient(),  mct.getTimeOfSending().getTime(),mct.getTimeOfReceipt().getTime(),sender.getParticipantID(), sender.getUsername(),sender.getUsername(),mct.getText(),pUsernames,false,mct.getKeypresses(),ds.getAllInsertsAndRemoves(),mct.getClientInterfaceEvents(),    additionalValues,false);
      }
      
      
    /**
    *
    * Called by ConversationController to send a turn it has received, unmodified, to the other participants
    * If the ConversationController object modifies the turn in any way, e.g.adding,deleting,substituting characters, or manipulating the timing, use one of the sendArtificialTurn methods.
    * This method retrieves the SubdialogueID from the ConversationController object.
    * 
    * @param sender The participant who created the turn
    * @param recipients The participants who will receive the turn
    * @param mct The original message produced by the sender
    * @param additionalAttribVals A list of attribute/value pairs that will also be saved to the file as separate columns
    * 
    */   
     public void relayTurnToMultipleParticipants(Participant sender,Vector recipients, MessageChatTextFromClient mct, Vector<AttribVal> additionalValues){
        mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        
      
        
        String subdialogueID = cC.pp.getSubdialogueID(sender);
        Vector pUsernames = new Vector();
        for(int i=0;i<recipients.size();i++){
            Participant recipient = (Participant)recipients.elementAt(i);
            int windowNumber = cC.sm.getWindowNumberInWhichParticipantBReceivesTextFromParticipantA_Deprecated_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(sender, recipient);
            pUsernames.addElement(recipient.getUsername());  
            MutableAttributeSet style = cC.getStyleManager().getStyleForRecipient(recipient, sender);
            MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),sender.getParticipantID(),sender.getUsername(),mct.getText(),windowNumber,true,style);
            ps.sendMessageToParticipant(recipient, mctc);
        }
        cH.saveMessageRelayedToOthers(subdialogueID,mct.getStartOfTypingOnClient(),  mct.getTimeOfSending().getTime(),mct.getTimeOfReceipt().getTime(),sender.getParticipantID(), sender.getUsername(),sender.getUsername(),mct.getText(),pUsernames,false,mct.getKeypresses(),ds.getAllInsertsAndRemoves(),   mct.getClientInterfaceEvents(), additionalValues,false);
      }
      
     /**
    *
    * Called by ConversationController to send a turn it has received, unmodified, to the other participants
    * If the ConversationController object modifies the turn in any way, e.g.adding,deleting,substituting characters, or manipulating the timing, use one of the sendArtificialTurn methods.
    * This method retrieves the SubdialogueID from the ConversationController object 
    * This method retrieves the recipients from the ConversationController object
    * 
    * @param sender The participant who created the turn
    * @param recipients The participants who will receive the turn
    * @param mct The original message produced by the sender
    * 
    * 
    */  
      public void relayTurnToPermittedParticipants(Participant sender, MessageChatTextFromClient mct, Vector<AttribVal> additionalValues){
        mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());   
        String subdialogueID = cC.pp.getSubdialogueID(sender);
        Vector pUsernames = new Vector();
        Vector recipients = cC.pp.getRecipients(sender);
        for(int i=0;i<recipients.size();i++){
            Participant recipient = (Participant)recipients.elementAt(i);
            int windowNumber = cC.sm.getWindowNumberInWhichParticipantBReceivesTextFromParticipantA_Deprecated_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(sender, recipient);
            pUsernames.addElement(recipient.getUsername());  
            MutableAttributeSet style = cC.getStyleManager().getStyleForRecipient(recipient, sender);
            MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),sender.getParticipantID(),sender.getUsername(),mct.getText(),windowNumber,true,style);
             if( diet.debug.Debug.debugtimers ) mct.saveTime("ServerConversation.newrelayTurnToPermittedParticipantsSENDING:"+recipient.getParticipantID()+","+recipient.getUsername());
              
            ps.sendMessageToParticipant(recipient, mctc);
            if( diet.debug.Debug.debugtimers ) mct.saveTime("ServerConversation.newrelayTurnToPermittedParticipantsSENT:"+recipient.getParticipantID()+","+recipient.getUsername());
            
        }
        cH.saveMessageRelayedToOthers(subdialogueID,mct.getStartOfTypingOnClient(),  mct.getTimeOfSending().getTime(),mct.getTimeOfReceipt().getTime(),sender.getParticipantID(), sender.getUsername(),sender.getUsername(),mct.getText(),pUsernames,false,mct.getKeypresses(),ds.getAllInsertsAndRemoves(), mct.getClientInterfaceEvents(), additionalValues,false);
        if( diet.debug.Debug.debugtimers ) mct.saveTime("ServerConversation.newrelayTurnToPermittedParticipantsSAVED:");
            
      }
      
      
    /**
    *
    * Called by ConversationController to send a turn it has received, unmodified, to the other participants
    * If the ConversationController object modifies the turn in any way, e.g.adding,deleting,substituting characters, or manipulating the timing, use one of the sendArtificialTurn methods.
    * This method retrieves the SubdialogueID from the ConversationController object 
    * This method retrieves the recipients from the ConversationController object
    * 
    * @param sender The participant who created the turn
    * @param mct The original message produced by the sender
    * 
    * 
    */   
     public void relayTurnToPermittedParticipants(Participant sender, MessageChatTextFromClient mct){
        mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        
        Vector additionalInfo = new Vector();
        try{
           additionalInfo = cC.getAdditionalInformationForParticipant(sender);
           if(additionalInfo==null)additionalInfo = new Vector();
        }catch(Exception e){
            e.printStackTrace();
        }
        String subdialogueID = cC.pp.getSubdialogueID(sender);
        Vector pUsernames = new Vector();
        Vector recipients = cC.pp.getRecipients(sender);
        for(int i=0;i<recipients.size();i++){
            Participant recipient = (Participant)recipients.elementAt(i);
            int windowNumber = cC.sm.getWindowNumberInWhichParticipantBReceivesTextFromParticipantA_Deprecated_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(sender, recipient);
            pUsernames.addElement(recipient.getUsername());  
            MutableAttributeSet style = cC.getStyleManager().getStyleForRecipient(recipient, sender);
            MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),sender.getParticipantID(),sender.getUsername(),mct.getText(),windowNumber,true,style);
            ps.sendMessageToParticipant(recipient, mctc);
        }
        cH.saveMessageRelayedToOthers(subdialogueID,mct.getStartOfTypingOnClient(),  mct.getTimeOfSending().getTime(),mct.getTimeOfReceipt().getTime(),sender.getParticipantID(), sender.getUsername(),sender.getUsername(),mct.getText(),pUsernames,false,mct.getKeypresses(),ds.getAllInsertsAndRemoves(), mct.getClientInterfaceEvents(),  additionalInfo,false);
      }
      
      
     
      
     /**
      * 
      * Removes all messages that are in the queue of outgoing delayed messages. These are the messages that were sent with 
      * 
      */ 
     public void removeAllDelayedMessages(){
         this.ps.removeAllDelayedMessages();
     }
      
      
      long dbgcounterA=0;
     
      
    /**
    *
    * Called by ConversationController to send a delayed turn
    * This method retrieves the SubdialogueID from the ConversationController object 
    * This method retrieves the recipients from the ConversationController object
    * 
    * IMPORTANT: This information is saved in the turns.txt file when the instruction is made, not when it is actually sent!
    * If you want to see the exact moment when it was received, look at the TextAsformulatedTIMING column in "turns.txt". It will be displayed there when it is received.
    * The exact moment it is received is also saved in the "clientinterfaceevents.txt"   
    * Note that this method saves an additional attribute/value pair that records the delay.    * 
    * 
    * @param sender The participant who created the turn
    * @param long the delay (in milliseconds) when the turn is sent.
    * @param text The message to be sent
    * 
    * 
    */   
     public void sendArtificialDelayedTurnToPermittedParticipants(Participant sender, String text, long delay){
        
        //DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        
        Vector additionalInfo = new Vector();
        try{
           additionalInfo = cC.getAdditionalInformationForParticipant(sender);
           if(additionalInfo==null)additionalInfo = new Vector();
           additionalInfo = (Vector) additionalInfo.clone();
        }catch(Exception e){
            e.printStackTrace();
        }
        String subdialogueID = cC.pp.getSubdialogueID(sender);
        Vector pUsernames = new Vector();
        Vector recipients = cC.pp.getRecipients(sender);
        AttribVal av2 = new AttribVal("actualdelay",delay);
        additionalInfo.addElement(av2);
        for(int i=0;i<recipients.size();i++){
            Participant recipient = (Participant)recipients.elementAt(i);
            int windowNumber = cC.sm.getWindowNumberInWhichParticipantBReceivesTextFromParticipantA_Deprecated_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(sender, recipient);
            pUsernames.addElement(recipient.getUsername());  
            MutableAttributeSet style = cC.getStyleManager().getStyleForRecipient(recipient, sender);
            MessageChatTextToClient mctc = new MessageChatTextToClient(generateNextClientInterfaceEventIDForClientDisplayConfirm(),sender.getParticipantID(),sender.getUsername(),text,windowNumber,true,style);
            ps.sendDelayedMessage(mctc, recipient, delay);   
            Vector uniquerecipient = new Vector(); uniquerecipient.add(recipient.getUsername());
            cH.saveArtificialMessageCreatedByServer(subdialogueID, new Date().getTime(),   sender.getUsername(),text, uniquerecipient, additionalInfo, false);
       
        }
        dbgcounterA++;
        //cH.saveMessageRelayedToOthers(subdialogueID,mct.getStartOfTypingOnClient(),  mct.getTimeOfSending().getTime(),mct.getTimeOfReceipt().getTime(),sender.getParticipantID(), sender.getUsername(),sender.getUsername(),mct.getText(),pUsernames,false,mct.getKeypresses(),ds.getAllInsertsAndRemoves(), mct.getClientInterfaceEvents(),  additionalInfo,false);
      }
      
      
      
      
      
      
      
      
    
    
   
    
    
    
    
    
   
    
    
    
    
    

    
    
  
    
    
     
    

    
   

    
     /**
      * Clears the turn formulation box in the Turn By Turn interface
      * 
      * @param recipient The participant whose box gets cleared
      */ 
     public void changeClientInterface_clearTextEntryField(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.clearTextEntryField);
           ps.sendMessageToParticipant(recipient, mccip); 
    }
    /**
     * Clears the conversation history window in the Turn By Turn interface
     * 
     * @param recipient The participant whose conversation history is cleared
     */
    public void changeClientInterface_clearMainWindows(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.clearMainTextWindows);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    
    /**
     * In the multiple window version of the Turn By Turn Interface, this clears all windows apart from window 0 (which is typically the window where the participant's own turns are displayed)
     * 
     * @param recipient The participant whose windows are cleared
     */
    public void changeClientInterface_clearMainWindowsExceptWindow0(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.clearAllWindowsExceptWindow0);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    
    /**
     * 
     * Enables the text entry box in the Turn By Turn interface
     * @param recipient 
     */
    public void changeClientInterface_enableTextEntry(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.enableTextEntry);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    
    /**
     * 
     * Specifies the maximum number of characters that can be typed in the turn formulation box. This is useful, as it prevents partcipants from typing long monologues.
     * 
     * @param recipient whose text entry box is modified.
     */
    public void changeClientInterface_setMaxTextEntryCharLength(Participant recipient, int charlength){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.setCharLengthLimit, charlength);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    
    /**
     * 
     * Disables the text entry box in the Turn By Turn interface. Prevents the participant from viewing/typing text.
     * @param recipient 
     */
    public void changeClientInterface_disableTextEntry(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.disableTextEntry);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    
    
    
    /**
     * 
     * Enables the conversation history in the Turn By Turn interface.
     * @param recipient 
     */
    public void changeClientInterface_enableConversationHistory(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.enableTextPane);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    
    /**
     * 
     * Disables the conversation history window in the Turn By Turn interface. Prevents the participant from viewing the conversation history
     * @param recipient 
     */
     public void changeClientInterface_disableConversationHistory(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.disableTextPane);
           System.err.println("BLOCKING CONVERSATION HISTORY! (3A)");
           ps.sendMessageToParticipant(recipient, mccip);
           System.err.println("BLOCKING CONVERSATION HISTORY! (3B)");
    }
     
     /**
     * 
     * Prevents the participant from scrolling the conversation history window.
     * @param recipient 
     */
    public void changeClientInterface_disableScrolling(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.disableScrolling);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    
    /**
     * 
     * Allows the participant to scroll the conversation history window.
     * @param recipient 
     */
    public void changeClientInterface_enableScrolling(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.enableScrolling);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    
    /**
     * Changes the background colour of the interface. This can be useful to e.g. flash the interface to signal a task state.
     * 
     * @param recipient
     * @param newColor 
     */
    public void changeClientInterface_backgroundColour(Participant recipient, Color newColor){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.changeScreenBackgroundColour, newColor);
           ps.sendMessageToParticipant(recipient, mccip);
            //System.exit(-44444678);
    }
    
    /**
     * 
     * To test/debug the chattool, this sends instructions to the client to generate random keypresses. 
     * 
     * @param recipient  
     * @param nameOfDebugScenario
     * @param duration How long the keypresses should be generated for
     */
    public void changeClientInterface_doRobotDebug(Participant recipient,  String nameOfDebugScenario, long duration){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.dodebugrobot, nameOfDebugScenario, duration);
           ps.sendMessageToParticipant(recipient, mccip);
            //System.exit(-44444678);
    }
    
    
    /**
     * Instructs the client's operating system to open a browser window and load a webpage.
     * 
     * Important - test this before relying on it in an experiment, as it is controlling code outside of java, on the local machine.
     * 
     * @param pRecipient
     * @param webpage 
     */
    public void openClientBrowserToWebpage(Participant pRecipient, String webpage){
        MessageOpenClientBrowserWebpage mocbw=new MessageOpenClientBrowserWebpage(webpage);
        
        ps.sendMessageToParticipant(pRecipient, mocbw);
        Conversation.this.saveAdditionalRowOfDataToSpreadsheetOfTurns("openbrowser",  pRecipient, webpage);
        
    }
    
    
   
    
    /**
     * This allows participants to delete their turns in the text formulation window.
     * 
     * @param recipient 
     */
    public void changeClientInterface_EnableDeletes(Participant recipient){
         MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.enableDeletes);
          ps.sendMessageToParticipant(recipient, mccip);
    }
    
    /**
     * This prevents participants from deleting their turns in the text formulation window.
     * 
     * @param recipient 
     */
     public void changeClientInterface_DisableDeletes(Participant recipient){
         MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.disableDeletes);
          ps.sendMessageToParticipant(recipient, mccip);
    }
            
    /**
     * 
     * This is specific to the maze game. This command allows status messages to be displayed superimposed over the mazes in the maze game window.
     * 
     * @param recipient  
     * @param text Text to be displayed
     * @param lengthOfTime duration of message
     */
    public void changeClientInterface_DisplayTextInMazeGameWindow(Participant recipient,String text, long lengthOfTime){
         MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.changeMazeWindow, text, lengthOfTime);
          ps.sendMessageToParticipant(recipient, mccip);
    }
    
    /**
     * 
     * Change the border of the client's chatwindow- can be useful for notifications
     * 
     * @param recipient  
     * @param width Width in pixels of the border
     * @param colour new colour of the border
     */
    public void changeClientInterface_changeBorderOnChatFrame(Participant recipient,int width, Color colour){
         MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.changeBorderOfChatFrame, width, colour);
         ps.sendMessageToParticipant(recipient, mccip);
         
    }
    
    
    /**
     * This is specific to the maze game. 
     * Change the border of the client's maze game window - can be useful for notifications
     * 
     * @param recipient  
     * @param width Width in pixels of the border
     * @param colour new colour of the border
     */
    public void changeClientInterface_changeBorderOnMazeFrame(Participant recipient,int width, Color colour){
         MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(generateNextClientInterfaceEventIDForClientDisplayConfirm(),ClientInterfaceEventTracker.changeBorderOfMazeFrame, width, colour);
         ps.sendMessageToParticipant(recipient, mccip);
         
    }
    
    
    
    
    
    /*
    *
    * Called by the server GUI when the experimenter presses the button to generate fake typing behaviour
    */
    public void typingactivity_GenerateFakeTyping(Participant p){
        cC.itnt.addSpoofTypingInfo(p, new Date().getTime());
    }
    
    
            
   /**
    *
    * Generates a unique ID for instructions that are sent to the clients. These IDs are shown in "clientinterfaceevents.txt"
    */
   public String generateNextClientInterfaceEventIDForClientDisplayConfirm(){
       this.currentDisplayableID = currentDisplayableID +1;
       return currentDisplayableID+"";  
   }
   long currentDisplayableID =0; 
    
    
     
     
    /**
      *
      * Saves the turn that is not being relayed to the log files. It also resets the queue of incoming messages associated with the participant.
      * There is an additional 
      *
      * @param p
      * @param turnNotRelayed
      * @param prefixFORCSVSpreadsheetIDENTIFIER
      * 
      */
     private void setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(Participant p,MessageChatTextFromClient turnNotRelayed, String subdialogueID, Vector<AttribVal> additionalValues){
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(p,new Date().getTime());
        
        cH.saveInterceptedNonRelayedMessage(subdialogueID, turnNotRelayed.getStartOfTypingOnClient(), turnNotRelayed.getTimeOfSending().getTime(), turnNotRelayed.getTimeOfReceipt().getTime(), 
                p.getParticipantID(), p.getUsername(), "", turnNotRelayed.getText(), new Vector(), turnNotRelayed.isHasBeenBlocked(), turnNotRelayed.getKeypresses(), ds.getAllInsertsAndRemoves(),turnNotRelayed.getClientInterfaceEvents() ,additionalValues,false);
			
            
     } 
     
    
    

    
    /**
     * 
     * Sends sets of images to the clients, to be displayed during the experiment. These images are used for the Confidence Expression task.
     * This method works, but is somewhat overkill. Instead of sending serialized images via the network connection, it is much quicker to save the images in the jar file
     * 
     * 
     * @param recipient The participant who receives the images
     * @param serializedImages The set of serialized images
     * @param width The width of the window that will be displayed on the client
     * @param height The height of the window that will be displayed on the client
     * @param additionalInfo Additional information that is saved to the "turns.txt" file (This way of saving additional info is deprecated - use attribute/value pairs)
     */
     public void subliminalstimuliset_SendSet(Participant recipient, Vector serializedImages, int width,int height, String prefixFORCSVSpreadsheet){
         MessageSubliminalStimuliSendSetToClient  mssstc = new MessageSubliminalStimuliSendSetToClient(serializedImages, width, height);
         ps.sendMessageToParticipant(recipient, mssstc);     
         this.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(recipient, prefixFORCSVSpreadsheet+"STIMULISET:SENDING", "");
     }
    
     
     
  
     
     /**
      * 
      * Commands to display stimuli in the Confidence task.
      * 
      * @param recipient the participant who sees the images
      * @param fixation1time how long a fixation cross is displayed before the main stimulus
      * @param stimulus1time how long the first stimulus is shown for 
      * @param blankscreen1time how long a bland screen is shown before showing the second fixation cross
      * @param fixation2time how long the fixation cross is displayed before the second stimulus
      * @param stimulus2time how long the second stimulus is displayed for
      * @param blankscreen2time how long a blankscreen is displayed before the participant is asked for a choice
      * @param stimulus1ID the image name of the first stimulus
      * @param stimulus2ID The image name of the second stimulus
      * @param prefixFORCSVSpreadsheet Additional information that is saved to the "turns.txt" file (This way of saving additional info is deprecated - use attribute/value pairs)
      */
     public void subliminalstimuliset_displaySet(Participant recipient, long fixation1time, long stimulus1time
                               ,long blankscreen1time
                               ,long fixation2time, long stimulus2time
                               ,long blankscreen2time,
                               String stimulus1ID, String stimulus2ID, String prefixFORCSVSpreadsheet ){
         
         MessageSubliminalStimuliChangeImage  mssci  = new MessageSubliminalStimuliChangeImage(fixation1time,stimulus1time
                               ,blankscreen1time
                               ,fixation2time,  stimulus2time
                               ,blankscreen2time,
                               stimulus1ID,  stimulus2ID);
         ps.sendMessageToParticipant(recipient, mssci);  
          this.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(recipient, prefixFORCSVSpreadsheet+"STIMULISET:Display new set",
                  " fixationtime:"+fixation1time+
                  " stimulus1time:"+stimulus1time+
                  "blankscreen1time:"+blankscreen1time+
                  " fixation2time:"+fixation2time+
                  " stimulus2time:"+stimulus2time+
                  "blankscreen2time:"+blankscreen2time+
                  " stimulus1ID:"+stimulus1ID+
                  " stimulus2ID:"+stimulus2ID);
     }
        
     
     
     /**
      * 
      * Displays text in the stimulus window
      * 
      * @param recipient Participant who receives the text
      * @param text Text displayed in the window
      * @param panelName The image name that is displayed in the window
      * @param textColour The colour of the text
      * @param positionX The x position of the text in the window
      * @param positionY The y position of the text in the window
      * @param lengthOfTime How long the text is displayed for 
      * @param prefixFORCSVSpreadsheet Additional information that is saved to the "turns.txt" file (This way of saving additional info is deprecated - use attribute/value pairs)
      */
     public void subliminalstimuliset_displayText(Participant recipient, String text, String panelName, Color textColour, int positionX, int positionY, long lengthOfTime, String prefixFORCSVSpreadsheet ){
         
          MessageSubliminalStimuliDisplayText mssdt= new MessageSubliminalStimuliDisplayText(text,textColour,panelName,positionX,positionY, lengthOfTime);
          ps.sendMessageToParticipant(recipient, mssdt); 
          
          this.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(recipient, prefixFORCSVSpreadsheet+"STIMULISET: Send text",text+" ("+positionX+","+positionY+")");
     }
     
         
    
    


     /**
      * Sends task move (e.g. move in the maze game) to a participant.
      * @param p
      * @param mt
      */
     public void sendTaskMoveToParticipant(Participant p, MessageTask mt){
        ps.sendMessageToParticipant(p, mt);

    }

     


    


    

     /**
      * Returns the location on the local file system where the data from the experiment is saved
      * @return File representing the enclosing folder/directory containing the data from the experiment
      */
     public File getDirectoryNameContainingAllSavedExperimentalData(){
        return convIO.getFileNameContainingConversationData();
    }

     
    /**
     *  
     * @return the conversation history of the experiment This is a representation of all turns/messages by the server to the clients.
     */
    public ConversationHistory getHistory(){
        return cH;
    }
    
    /**
     * 
     * @return the active conversation controller object 
     */
    public DefaultConversationController getController(){
        return cC;
    }
    
    
   
    /**
     * 
     * @return all participants currently logged in
     */
    public Vector<Participant> getAllParticipantsAsList(){
        return this.ps.getAllParticipants();
    }

    /**
     * 
     * @return the Participants objects that contains (1) all participants logged in (2) methods for sending messages to participants
     */
    public Participants getParticipants(){
        return this.ps;
    }
    
    /**
     * 
     * @return the number of participants who are currently logged in
     */
    public int getNoOfParticipants(){
        return this.getParticipants().getAllParticipants().size();
    }
    
    
    
    private DocChangesIncoming getDocChangesIncoming(){
        return this.turnsconstructed;
    }
    
    public DocChangesIncomingSequenceFIFO getTurnBeingConstructed(Participant p){
        return this.turnsconstructed.getTurnBeingConstructed(p);
    }
   
    
    
    
    /**
     * 
     * @return the object responsible for saving files to the experiment folder
     */
    public IntelligentIO getConvIO(){
        return convIO;
    }

    
   

    
    
    /**
     * 
     * Sends a kill command to close down participants
     * 
     * @param p the participant to be closed
     */
    public void killClientOnRemoteMachine(Participant p){
        ps.killClient(p);
    }
    
    
    
    
    
    /**
     * Attempts to close down the Conversation and associated resources.
     * @param sendCloseToClients
     */
    public void closeDown(boolean sendCloseToClients){
        try{
            this.conversationIsActive=false;
            
           
                cC.closeDown();
            
            this.cHistoryUIM.closedown();
            this.cH.closeDown();
           
            this.ps.closeDown(sendCloseToClients);
            //this.ps.stop();
            this.ps = null;

            
            
            //this.conversationIsActive=false;
            this.convIO.shutThreadDownAndCloseFiles();
        } catch (Exception e){

        }
    }



    /**
     * 
     * Used to show the stimulus on a client's interface.. This is the most straightforward.
     * The file needs to be saved  in the jar file (netbeans does this automatically for folders in /experimentresources/
     * When showing stimuli, this should be used to display the first stimulus
     * 
     * @param p The participant who sees the image
     * @param width The width of the stimulus window
     * @param height The height of the stimulus window
     * @param imagename The name of the image 
     * @param isindirectory Whether the image is in the jar file OR is a physical file.
     * @param buttonnames A list of buttonnames that appear underneath the stimulus window.
     */    
    public void showStimulusImageFromJarFile_InitializeWindow(Participant p, int width, int height, String imagename, boolean isindirectory, String[] buttonnames){
         diet.message.MessageStimulusImageDisplayNewJFrame msidjf = new MessageStimulusImageDisplayNewJFrame( width, height, imagename, isindirectory,  buttonnames );
         ps.sendMessageToParticipant(p,  msidjf);
         String allButtonnames="Buttons: ";
         for(int i=0;i<buttonnames.length;i++){
              allButtonnames = allButtonnames+ "{"+buttonnames[i]+"}";
         }
         Conversation.this.saveAdditionalRowOfDataToSpreadsheetOfTurns("stimulusimage_initialize",  p, "Width:"+width+" Height:"+height+ " Filename:"+imagename);
    }
    
    
    /**
     * 
     * Used to change the stimulus on a client's interface.  
     * To initialize the window on the chat client use {@link showStimulusImageFromJarFile_InitializeWindow} for the first stimulus.
     * The file needs to be saved  in the jar file (netbeans does this automatically for folders in /experimentresources/
     * When showing stimuli, this should be used to display the first stimulus
     * 
     * @param p The participant who sees the image
     * @param imagename The name of the image (should be in the jar file) 
     * @param isindirectory Whether the image is in the jar file OR is a physical file.
     * @param durationmsecs Duration of the stimulus.
     */    
    public void showStimulusImageFromJarFile_ChangeImage(Participant p,String imagename, boolean isindirectory,long durationmsecs){
         diet.message.MessageStimulusImageChangeImage msidjf = new MessageStimulusImageChangeImage( imagename,isindirectory ,durationmsecs );
         ps.sendMessageToParticipant(p,  msidjf);
         Conversation.this.saveAdditionalRowOfDataToSpreadsheetOfTurns("stimulusimage_change_instruction",  p, "Filename:"+imagename+ " Duration:"+durationmsecs);
    }
    
    
    /**
     * 
     * Enables/disables the buttons underneath the stimulus. 
     * This is useful, e.g. for preventing participants for making a choice at particular moments in the experiment.
     * 
     * @param p the participant already displaying a stimulus window
     * @param buttonnames the names of the buttons to enable/disable
     * @param enable whether the button(s) are enabled/disabled
     */
    public void showStimulusImageEnableButtons(Participant p,String[] buttonnames, boolean enable){
         diet.message.MessageStimulusImageEnableButtons msieb = new MessageStimulusImageEnableButtons( buttonnames, enable );
         ps.sendMessageToParticipant(p,  msieb);
         String allButtonnames="Buttons: ";
         for(int i=0;i<buttonnames.length;i++){
             allButtonnames = allButtonnames+ "{"+buttonnames[i]+"}";
         }
         
         String type="enable";
         if(!enable) {
             type = "disable";
         }
         
         Conversation.this.saveAdditionalRowOfDataToSpreadsheetOfTurns("stimulusimage_buttons_"+type,  p,allButtonnames );
    }
    
    
       /**
     * 
     * Enables/disables the buttons underneath the stimulus. 
     * This is useful, e.g. for preventing participants for making a choice at particular moments in the experiment.
     * 
     * @param p the participant already displaying a stimulus window
     * @param buttonnames the names of the buttons to enable/disable
     * @param enable whether the button(s) are enabled/disabled
     */
    public void showStimulusImageReplaceWithNewButtons(Participant p,String[] buttonnames, boolean enable){
         diet.message.MessageStimulusImageReplaceWithNewButtons msirwnb = new MessageStimulusImageReplaceWithNewButtons( buttonnames, enable );
         ps.sendMessageToParticipant(p,  msirwnb);
         String allButtonnames="Buttons: ";
         for(int i=0;i<buttonnames.length;i++){
             allButtonnames = allButtonnames+ "{"+buttonnames[i]+"}";
         }
         
         String type="enable";
         if(!enable) {
             type = "disable";
         }
         
         Conversation.this.saveAdditionalRowOfDataToSpreadsheetOfTurns("stimulusimage_buttons_"+type,  p,allButtonnames );
    }
    
      
    
    
    
     
    
    /**
     * 
     * Creates a simple text window where instructions can be displayed
     * 
     * @param p Participant who receives the instructions
     * @param id an ID given to the instructions
     * @param header Text displayed at the top of the window (this is somewhat unwieldy, but it does work...)
     * @param width width of the window
     * @param height height of the window
     * @param vScrollBar whether a scrollbar is activated
     * @param displayCOURIERFONT  whether the message is displayed in monospace font
     */
    public void textOutputWindow_Initialize(Participant p, String id, String header, String url, int width, int height,boolean vScrollBar,boolean displayCOURIERFONT){
        //url is ignored
        ps.displayNEWWebpage(p, id, header, "", width, height, vScrollBar,displayCOURIERFONT);
        
    }
    
    
      
    

   /**
    * Displays an image in the text window on the client from a url.
    * This is not recommended - it is much better to include images in the jar file, using the showStimulusImageEnableButtons(..) method . 
    * They load much quicker, and there isn't the uncertainty of relying on webservers.
    * 
    * @param p Participant who sees the image
    * @param id an ID given to the stimulus
    * @param imageurl the url of the image.
    */
    public void textOutputWindow_displayImage_DEPRECATED(Participant p, String id,  String imageurl){
        //imageaddressOnServerWebserver needs to include a backslash as first character.
        String url = "<html><img src="+imageurl+"'></img>";   
        ps.changeWebpage(p, id, url);
    }

   
    /**
     * 
     * Changes the text in the text output window on the clients
     * 
     * @param id an ID to identify the type of message that is displayed
     * @param newtext The text that is displayed
     * @param append whether to append or replace the text that is currently being displayed
     * @param prtss list of Participants who receive the instructions.
     */
    public void textOutputWindow_ChangeText( String id, String newtext, boolean append,  Participant...prtss){
        for(Participant recipient: prtss){
            ps.changeWebpage(recipient, id, "", newtext,append);
            try{
                 String subdialogueID = cC.pp.getSubdialogueID(recipient);
                 Vector recipientNames = new Vector();
                 recipientNames.addElement(recipient.getUsername());
                 
                 String lineToSave = "TEXTOUTPUTWINDOW:"+" ID:"+id+" TEXT:"+newtext;
                 lineToSave=lineToSave.replace("\n", "((NEWLINE))").replace("\r", "((NEWLINE))");
                 
                 cH.saveArtificialMessageCreatedByServer(subdialogueID, new Date().getTime(), "server",lineToSave, recipientNames, new Vector(), false);
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        
        
    }
 
    
     
     /**
      * 
      * Closes the window
      * 
      * @param p  participant that receives the instruction
      * @param id the window that is to be closed.
      */
     public void textOutputWindow_CloseWindow(Participant p, String id){
        ps.closeWebpageWindow(p, id);
    }
    
    
    
    
    
    
   
    /**
     * 
     * Creates a progress bar on the client
     * 
     * @param p Participant where the progress bar is created
     * @param id Where the Progress bar should be displayed: "CHATFRAME"= under the chat window  "instructions"=under the window created with {@link textOutputWindow_Initialize}
     * @param text The text displayed in the progress bar
     * @param foreCol The foreground colour of the progressbar
     * @param value  A value between 0 and 100 representing how complete the progressbar is displayed.
     */
    public void changeJProgressBar(Participant p,String id, String text, Color foreCol, int value){
        ps.changeJProgressBar(p, id, text, foreCol, value);
    }
    
    
    
    /**
     * 
     * Creates a progress bar on all clients.
     * 
     *      * @param id Where the Progress bar should be displayed: "CHATFRAME"= under the chat window  "instructions"=under the window created with {@link textOutputWindow_Initialize}
     * @param text The text displayed in the progress bar
     * @param foreCol The foreground colour of the progressbar
     * @param value  A value between 0 and 100 representing how complete the progressbar is displayed.
     */
    public void changeJProgressBarsOfAllParticipants(String id, String text, Color foreCol,int value){
        if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE){System.out.println("SINH1");;System.out.flush();}
        Vector v = ps.getAllParticipants();
         if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE){ System.out.println("SINH2");;System.out.flush();}
        for(int i=0;i<v.size();i++){
            
            Participant p = (Participant)v.elementAt(i);
            if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE){ System.out.println("SINH4 "+p.getUsername());;System.out.flush();}
             ps.changeJProgressBar(p, id, text, foreCol, value);
             if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE){System.out.println("SINH5 "+p.getUsername());System.out.flush();}
        }
        if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE){System.out.println("SINH6 ");System.out.flush();}
    }

    
    


    /**
     * 
     * @return the ExperimentManager which is responsible for logging in of clients.
     */
    public ExperimentManager getExpManager() {
        return expManager;
    }

    /**
     * 
     * Prints text to the GUI in one of the status windows 
     * 
     * @param windowName The name of the window where text is displayed. If the window doesn't exist, it is created.
     * @param text The text that is appended to the window.
     */
    public void printWln(String windowName, String text){
        EMUI em = this.getExpManager().getEMUI();
        if(em!=null){
            em.println(windowName, text);
        }
        else{
            System.err.println("PRINTWLN EM IS NULL"+windowName+" "+text);
        }
        convIO.saveWindowTextToLog(windowName, text);
    }

    
    /**
     * 
     * Prints text to the GUI in one of the status windows 
     * 
     * @param windowName The name of the window where text is displayed. If the window doesn't exist, it is created.
     * @param text The text that is appended to the window.
     */
    public static void printWSln(String windowName, String text){
        System.err.println("PRINTWSLN: "+windowName+": "+text);
        if(statC!=null){
           statC.printWln(windowName, text);
           
        }
        else{
           System.err.println("PRINTWSLN: Can't print to: "+windowName+" because Conversation is null: ");
        }
    }
    
    /**
     * 
     * Prints text to the GUI in one of the status windows and saves it to the log file in the experiment directory
     * 
     * @param windowName The name of the window where text is displayed. If the window doesn't exist, it is created.
     * @param text The text that is appended to the window.
     */
    public static void printWSlnLog(String windowName, String text)
    {
    	if(statC!=null)
    		statC.printWlnLog(windowName, text);
                statC.convIO.saveWindowTextToLog(windowName, text);
    }
    
    /**
     * 
     * Prints text to the GUI in one of the status windows and saves it to the log file in the experiment directory
     * 
     * @param windowName The name of the window where text is displayed. If the window doesn't exist, it is created.
     * @param text The text that is appended to the window.
     */
    public void printWlnLog(String windowName, String text)
    {
    	EMUI em = this.getExpManager().getEMUI();
        if(em!=null){
            em.println(windowName, text);
        }
        this.convIO.saveWindowTextToLog(windowName, text);


    }

    
    /**
     * Saves errors (Throwables) to the experiment folder
     * 
     * @param t error to be saved to 
     */
    public static void saveErr(Throwable t){
        try{
           t.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        if(statC!=null){
           statC.saveErrorLog(t);
        }
    }

    /**
     * Saves errors to the experiment folder
     * 
     * @param s string describing the error
     */
    public static void saveErr(String s){
        if(statC!=null){
           statC.saveErrorLog(s);
        }
    }

    /**
     * Saves errors (throwables) to the experiment folder
     * 
     * @param t error to be saved to 
     */
    private void saveErrorLog(Throwable t){
        System.err.println(cC.getID()+ ": ERROR SOMEWHERE IN THE CONVERSATION CONTROLLER");
        printWln("Main","There is an ERROR in the Conversation Controller");
        printWln("Main","Check the ERRORLOG.TXT file in the directory containing");
        printWln("Main","the saved experimental data");
        printWln("Main",t.getMessage());
        getConvIO().saveErrorLog(t);
    }
    
    /**
     * Saves errors to the experiment folder
     * 
     * @param s string describing the error
     */
    public void saveErrorLog(String s){
        System.err.println(cC.getID()+ ": ERROR SOMEWHERE IN THE CONVERSATION CONTROLLER");
        printWln("Main","There is an ERROR in the Conversation Controller");
        printWln("Main","Check the ERRORLOG.TXT file in the directory containing");
        printWln("Main","the saved experimental data");
        printWln("Main",s);
        getConvIO().saveErrorLog(s);
    }


    /**
     * 
     * @return whether the conversation is executing the main loop of checking for incoming messages.
     */
    public boolean isConversationActive() {
        return conversationIsActive;
    }

    
    

 
     public String[] telegram_processLoginCode(long telegramid,String code){
         return  cC.telegram_getUniqueParticipantIDAndUniqueUsername(telegramid, code);
     }
    
     
     public void deprecated_telegram_relayMessageTextToOtherParticipantsOLD(TelegramParticipant sender, diet.tg.TelegramMessageFromClient tmfc){
           System.err.println("HEREINCOMING101");
         
           
          String text = tmfc.u.getMessage().getText();
          long senderTelegramLogin = tmfc.u.getMessage().getChatId();
          Vector recipients = cC.pp.getRecipients(sender);
          
            System.err.println("HEREINCOMING101A. there are " + recipients.size()+ " recipients");
          
          for(int i=0;i<recipients.size();i++){
              try{
              TelegramParticipant recipient = (TelegramParticipant)recipients.elementAt(i);
              long recipientID = recipient.getConnection().telegramID;
              
              SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(recipientID)
                .setText(text);
                recipient.sendMessage(message);
                 System.err.println("HEREINCOMING102");
                 
                 String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , recipient.toString());
                 
                 
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }   
                
          }    
            this.getCHistoryUIM().updateParticipantsListChanged(this.getParticipants().getAllParticipants());
     
     }
     
     
     public void telegram_relayMessageTextToOtherParticipants(TelegramParticipant sender, diet.tg.TelegramMessageFromClient tmfc){
           System.err.println("HEREINCOMING101");
         
           
          String text = tmfc.u.getMessage().getText();
          long senderTelegramLogin = tmfc.u.getMessage().getChatId();
          Vector recipients = cC.pp.getRecipients(sender);
          
            System.err.println("HEREINCOMING101A. there are " + recipients.size()+ " recipients");
          
          String chgroup="";  
            
          Vector<String> vRecipientsUsernames = new Vector();
          
          for(int i=0;i<recipients.size();i++){
              try{
              TelegramParticipant recipient = (TelegramParticipant)recipients.elementAt(i);
              
              vRecipientsUsernames.add(recipient.getUsername());
              
              long recipientID = recipient.getConnection().telegramID;
              
              SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                       .setChatId(recipientID);
              message = message.disableNotification();
              message.enableHtml(true);
              message.setText("<b>"+sender.getUsername()+": </b>"+text);
              
              
              
              recipient.sendMessage(message);
              
              String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               
               
               
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , message.toString());
              
              
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }   
                
          }    
          
            String group =cC.pp.getSubdialogueID(sender);
            if(group==null)group="";
                  
           
           //public synchronized void saveMessageRelayedToOthers(String dtype,long timeOnClientOfStartTyping, long timeOnClientOfSending, long timeOnServerOfReceipt, String senderID, String senderUsername, String apparentSenderUsername, String text,
	//	 	Vector recipientsNames, boolean wasBlocked, Vector keyPresses, Vector<DocChange> documentUpdates, Vector<ClientInterfaceEvent> clientInterfaceEvents, Vector<AttribVal> additionalValues, boolean dummy) {
        
            Vector addVals = cC.getAdditionalInformationForParticipant(sender);
             AttribVal av1 = new AttribVal("telegramtype","text");
             addVals.add(av1);
            
             AttribVal av2 = new AttribVal("telegramrawdata",tmfc.u.toString());
             addVals.add(av2);
             
             AttribVal av3 = new AttribVal("telegramid",senderTelegramLogin);
             addVals.add(av3);
             
             
             
            cH.saveMessageRelayedToOthers(group,  tmfc.u.getMessage().getDate() ,  tmfc.u.getMessage().getDate()  , tmfc.timeOfCreation,sender.getParticipantID(), sender.getUsername(),sender.getUsername(),text,vRecipientsUsernames,false,new Vector(),new Vector(), new Vector(), addVals,false);
       
           
            
            this.getCHistoryUIM().updateParticipantsListChanged(this.getParticipants().getAllParticipants());
            tmfc.hasBeenRelayed=true;
     
     }
     
     
     
      public org.telegram.telegrambots.meta.api.objects.Message telegram_sendInstructionToParticipant_(TelegramParticipant recipient, String markdown){
         
         org.telegram.telegrambots.meta.api.objects.Message retMessage = null;
         
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        
        long recipientID = recipient.getConnection().telegramID;
            
             try{
                SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                       .setChatId(recipientID)
                       .setText(markdown);
                 message = message.disableNotification();
                 message.enableMarkdown(true);
                 
                 message.setText(markdown);  
                 org.telegram.telegrambots.meta.api.objects.Message m = recipient.sendMessage(message);
                
                String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , message.toString());
                
               Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
             Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
             AttribVal av1 = new AttribVal("telegramtype","instructiontoparticipant");
             additionalValues.add(av1);
             AttribVal av2 = new AttribVal("telegramrawdata",message.toString());
             additionalValues.add(av2);     
              AttribVal av3 = new AttribVal("telegramid",recipient.getConnection().telegramID );
                   additionalValues.add(av3);
             
              cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server",markdown, recipientsNames, additionalValues, false);
               
               
               
               
                 System.err.println("HEREINCOMING102");
                 retMessage=m;
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }    
             if(retMessage!=null) return retMessage;
             return null;
        //cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), "server",text, recipientNames, new Vector(), false);
     }
     
     
      
     
     
     
     public org.telegram.telegrambots.meta.api.objects.Message telegram_sendInstructionToParticipant_HTML(TelegramParticipant recipient, String html){
         
         org.telegram.telegrambots.meta.api.objects.Message retMessage = null;
         
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        
        long recipientID = recipient.getConnection().telegramID;
            
             try{
                SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                       .setChatId(recipientID)
                       .setText(html);
                 message = message.disableNotification();
                 message.enableHtml(true);
                 
                 message.setText(html);  
                 org.telegram.telegrambots.meta.api.objects.Message m = recipient.sendMessage(message);
                
                String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , message.toString());
                
               Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
             Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
             AttribVal av1 = new AttribVal("telegramtype","instructiontoparticipant");
             additionalValues.add(av1);
             AttribVal av2 = new AttribVal("telegramrawdata",message.toString());
             additionalValues.add(av2);     
              AttribVal av3 = new AttribVal("telegramid",recipient.getConnection().telegramID );
                   additionalValues.add(av3);
             
              cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server",html, recipientsNames, additionalValues, false);
               
               
               
               
                 System.err.println("HEREINCOMING102");
                 retMessage=m;
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }    
             if(retMessage!=null) return retMessage;
             return null;
        //cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), "server",text, recipientNames, new Vector(), false);
     }
     
     
     
     
     public org.telegram.telegrambots.meta.api.objects.Message telegram_sendInstructionToParticipant_MonospaceFont(TelegramParticipant recipient, String text){
         
         org.telegram.telegrambots.meta.api.objects.Message retMessage = null;
         
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        
        long recipientID = recipient.getConnection().telegramID;
            
             try{
                SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                       .setChatId(recipientID)
                       .setText(text);
                 message = message.disableNotification();
                 message.enableHtml(true);
                 
                 message.setText("<code>"+text+"</code>");  
                 org.telegram.telegrambots.meta.api.objects.Message m = recipient.sendMessage(message);
                
                String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , message.toString());
                
               Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
             Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
             AttribVal av1 = new AttribVal("telegramtype","instructiontoparticipant");
             additionalValues.add(av1);
             AttribVal av2 = new AttribVal("telegramrawdata",message.toString());
             additionalValues.add(av2);     
              AttribVal av3 = new AttribVal("telegramid",recipient.getConnection().telegramID );
                   additionalValues.add(av3);
             
              cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server",text, recipientsNames, additionalValues, false);
               
               
               
               
                 System.err.println("HEREINCOMING102");
                 retMessage=m;
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }    
             if(retMessage!=null) return retMessage;
             return null;
        //cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), "server",text, recipientNames, new Vector(), false);
     }
     
     
     
     
     
     public org.telegram.telegrambots.meta.api.objects.Message telegram_sendInstructionToParticipant_Markdownv2(TelegramParticipant recipient, String markdownv2){
         
         org.telegram.telegrambots.meta.api.objects.Message retMessage = null;
         
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        
        long recipientID = recipient.getConnection().telegramID;
            
             try{
                SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                       .setChatId(recipientID)
                       .setText(markdownv2);
                 message = message.disableNotification();
                 message.enableMarkdownV2(conversationIsActive);
                 message.setText("<code>"+markdownv2+"</code>");  
                 org.telegram.telegrambots.meta.api.objects.Message m = recipient.sendMessage(message);
                
                String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , message.toString());
                
               Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
             Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
             AttribVal av1 = new AttribVal("telegramtype","instructiontoparticipant");
             additionalValues.add(av1);
             AttribVal av2 = new AttribVal("telegramrawdata",message.toString());
             additionalValues.add(av2);     
              AttribVal av3 = new AttribVal("telegramid",recipient.getConnection().telegramID );
                   additionalValues.add(av3);
             
              cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server",markdownv2, recipientsNames, additionalValues, false);
               
               
               
               
                 System.err.println("HEREINCOMING102");
                 retMessage=m;
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }    
             if(retMessage!=null) return retMessage;
             return null;
        //cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), "server",text, recipientNames, new Vector(), false);
     }
     
      public void telegram_sendInstructionToParticipantWithForcedKeyboardButtonsDEPRECATED(TelegramParticipant recipient, String text, String[] buttons, int columns){
        try{  
          Vector<Vector<String>> rows=new Vector();
          Vector<String> currentRow = new Vector();
          
          for(int i=0;i<buttons.length;i++){
               //currentRow.add(new KeyboardButton(buttons[i]));
               if(currentRow.size()>columns){
                   rows.add(currentRow);
                   currentRow=new Vector<String>();
               }
          }
          
          String[][] rowA = new String[rows.size()][];
          for(int i=0;i<rows.size();i++){
               
               Vector<String> vs = rows.elementAt(i);
               String[] row = new String[ vs.size()  ];
               for(int j=0;j<vs.size();j++){
                   String button = vs.elementAt(j);
                   row[j]=button;
               }
               rowA[i]=row;
               
               
               
          }
          telegram_sendInstructionToParticipantWithForcedKeyboardButtons(recipient,  text, rowA);
          
     
        }catch(Exception e){
            e.printStackTrace();
            Conversation.saveErr(e);
        } 
          
        
          
      }
     
  
      
      
       public void telegram_sendInstructionToParticipantWithForcedKeyboardButtons(TelegramParticipant recipient, Vector<String> buttonnames, String messageText){
            String subdialogueID = cC.pp.getSubdialogueID(recipient);
        
        long recipientID = recipient.getConnection().telegramID;
            
        try{
                SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                       .setChatId(recipientID).setText(messageText);
                 message = message.disableNotification();
                 message.enableHtml(false);
                 //message.setText("<code>"+text+"</code>");  
                 
                
                 
               ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
               // Create the keyboard (list of keyboard rows)
               List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                for(int i=0;i<buttonnames.size();i++){
                    row.add(new KeyboardButton(buttonnames.elementAt(i)));
                    System.err.println("ADDINGKEYBOARD:"+buttonnames.elementAt(i));
                }
                
                
                
               
                keyboard.add(row);
                  
               keyboardMarkup.setKeyboard(keyboard);
                keyboardMarkup.setOneTimeKeyboard(false);

                message.setReplyMarkup(keyboardMarkup);
          
                keyboardMarkup.setResizeKeyboard(true);
               
                 
               recipient.sendMessage(message);
               
               String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , message.toString());
               
               Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
               Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
               AttribVal av1 = new AttribVal("telegramtype","instructiontoparticipantwithforcedkeyboard");
               additionalValues.add(av1);
               AttribVal av2 = new AttribVal("telegramrawdata",message.toString());
               additionalValues.add(av2);     
               AttribVal av3 = new AttribVal("telegramid",recipient.getConnection().telegramID );
               additionalValues.add(av3);
             
              // cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server","", recipientsNames, additionalValues, false);
               
               
               
               System.err.println("HEREINCOMING102SENT + text");
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }    
       
       }
      
     
     
     public void telegram_sendInstructionToParticipantWithForcedKeyboardButtons(TelegramParticipant recipient, String text, String[][] buttongrid){
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        
        long recipientID = recipient.getConnection().telegramID;
            
        try{
                SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                       .setChatId(recipientID)
                       .setText(text);
                 message = message.disableNotification();
                 message.enableHtml(true);
                 message.setText("<code>"+text+"</code>");  
                 
                
                 
               ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
               // Create the keyboard (list of keyboard rows)
               List<KeyboardRow> keyboard = new ArrayList<>();
               for(int i=0;i<buttongrid.length;i++){
                   KeyboardRow row = new KeyboardRow();
                   for(int j=0;j<buttongrid[i].length;j++){
                      row.add(buttongrid[i][j]);
                   }
                   keyboard.add(row);
               }      
               keyboardMarkup.setKeyboard(keyboard);
                keyboardMarkup.setOneTimeKeyboard(false);

                message.setReplyMarkup(keyboardMarkup);
          
               
                 
               recipient.sendMessage(message);
               
               String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , message.toString());
               
               Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
               Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
               AttribVal av1 = new AttribVal("telegramtype","instructiontoparticipantwithforcedkeyboard");
               additionalValues.add(av1);
               AttribVal av2 = new AttribVal("telegramrawdata",message.toString());
               additionalValues.add(av2);     
               AttribVal av3 = new AttribVal("telegramid",recipient.getConnection().telegramID );
               additionalValues.add(av3);
             
               cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server",text, recipientsNames, additionalValues, false);
               
               
               
               System.err.println("HEREINCOMING102SENT + text");
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }    
        //cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), "server",text, recipientNames, new Vector(), false);
     }
     
     
     
     
     
     
     
     
     
     public void telegram_sendURL(TelegramParticipant recipient, String text){
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        
        long recipientID = recipient.getConnection().telegramID;
            
             try{
                SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                       .setChatId(recipientID)
                       .setText(text);
                 message = message.disableNotification();
                 message.enableHtml(true);
                 message.setText("<a href=\""+text+"\">"+text+ "</a>");  
                recipient.sendMessage(message);
                
                String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"from",new Date().getTime() , message.toString());
               Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
                     Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
                     AttribVal av1 = new AttribVal("telegramtype","sendurl");
                     additionalValues.add(av1);
                     AttribVal av2 = new AttribVal("telegramrawdata",message.toString());
                     additionalValues.add(av2);     
                     AttribVal av3 = new AttribVal("telegramid",recipient.getConnection().telegramID );
                     additionalValues.add(av3);
             
                     cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server",text, recipientsNames, additionalValues, false);
               
               
               
                
                 System.err.println("HEREINCOMING102");
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }   
       
        //cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), "server",text, recipientNames, new Vector(), false);
   
     }
     
     
      public void telegram_sendArtificialTurnFromApparentOriginToPermittedParticipants(TelegramParticipant apparentSender,  String text){
            Vector<Participant> vP = cC.pp.getRecipients(apparentSender);
            Vector<String>  vID = new Vector();
            
            for(int i=0;i<vP.size();i++){
                vID.add(vP.elementAt(i).getParticipantID());
            }
            telegram_sendArtificialTurnFromApparentOriginToParticipantID(apparentSender, vID,  text);
      }
     
      
      
       
      public void telegram_sendArtificialTurnFromApparentOriginToParticipant(String apparentSenderString, TelegramParticipant recip, String text){
             Vector<String>  vID = new Vector();
             vID.add(recip.getParticipantID());
             
            telegram_sendArtificialTurnFromApparentOriginToParticipantID(apparentSenderString, vID,  text);
       }
      
      
      
        private void telegram_sendArtificialTurnFromApparentOriginToParticipantID(String apparentSenderString, Vector<String> vid, String text){
         
        Vector additionalInfo = new Vector();
         
 
        for(int i=0;i<vid.size();i++){
            TelegramParticipant recipient = (TelegramParticipant)this.getParticipants().findParticipantWithEmail((String) vid.elementAt(i));
            
             try{
             
              long recipientID = recipient.getConnection().telegramID;
              
              SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                       .setChatId(recipientID);
              message = message.disableNotification();
              message.enableHtml(true);
              message.setText("<b>"+apparentSenderString+": </b>"+text);
              recipient.sendMessage(message);
              
              String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"from",new Date().getTime() , message.toString());
               
               Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
               Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
               
               
                   
                   AttribVal av1 = new AttribVal("telegramtype","artificialturn");
                   additionalValues.add(av1);
                   AttribVal av2 = new AttribVal("telegramrawdata",message.toString());
                   additionalValues.add(av2);     
                   
               
              
       
               cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(), apparentSenderString, text, recipientsNames, additionalValues, false);
               
               
              
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }   
            
            
            
     
            //cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), apparentSender.getUsername(),text, recipientName, additionalInfo, false);
   
            }
         }
 
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
       public void telegram_sendArtificialTurnFromApparentOriginToParticipant(TelegramParticipant apparentSender, TelegramParticipant recip, String text){
             Vector<String>  vID = new Vector();
             vID.add(recip.getParticipantID());
             
            telegram_sendArtificialTurnFromApparentOriginToParticipantID(apparentSender, vID,  text, 0);
       }
      
      
       public void telegram_sendArtificialTurnFromApparentOriginToParticipantID(TelegramParticipant apparentSender, Vector<String> vid, String text){
            telegram_sendArtificialTurnFromApparentOriginToParticipantID(apparentSender, vid,  text, 0);
       }
        
      private void telegram_sendArtificialTurnFromApparentOriginToParticipantID(TelegramParticipant apparentSender, Vector<String> vid, String text, long delaySinceInvoked){
         
        Vector additionalInfo = new Vector();
        try{
           additionalInfo = cC.getAdditionalInformationForParticipant(apparentSender); 
           if(additionalInfo==null)additionalInfo = new Vector();
        }catch(Exception e){
            e.printStackTrace();
        }
 
        for(int i=0;i<vid.size();i++){
            TelegramParticipant recipient = (TelegramParticipant)this.getParticipants().findParticipantWithEmail((String) vid.elementAt(i));
            
             try{
             
              long recipientID = recipient.getConnection().telegramID;
              
              SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                       .setChatId(recipientID);
              message = message.disableNotification();
              message.enableHtml(true);
              message.setText("<b>"+apparentSender.getUsername()+": </b>"+text);
              recipient.sendMessage(message);
              
              String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"from",new Date().getTime() , message.toString());
               
               Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
               Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
               if(delaySinceInvoked>0){
                   AttribVal av1 = new AttribVal("telegramtype","artificialturndelayed"+delaySinceInvoked);
                   additionalValues.add(av1);
                   AttribVal av2 = new AttribVal("telegramrawdata",message.toString());
                   additionalValues.add(av2);     
                   AttribVal av3 = new AttribVal("telegramid",apparentSender.getConnection().telegramID );
                   additionalValues.add(av3);
               }
               else{
                   
                   AttribVal av1 = new AttribVal("telegramtype","artificialturn");
                   additionalValues.add(av1);
                   AttribVal av2 = new AttribVal("telegramrawdata",message.toString());
                   additionalValues.add(av2);     
                    AttribVal av3 = new AttribVal("telegramid",apparentSender.getConnection().telegramID );
                   additionalValues.add(av3);
               
               }
       
               cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(), apparentSender.getUsername(), text, recipientsNames, additionalValues, false);
               
               
              
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }   
            
            
            
     
            //cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), apparentSender.getUsername(),text, recipientName, additionalInfo, false);
   
            }
         }
        
     
     
     
      public org.telegram.telegrambots.meta.api.objects.Message telegram_sendPhoto_By_File_ID(TelegramParticipant recipient, String file_id){
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        
        long recipientID = recipient.getConnection().telegramID;
            
             try{
                SendPhoto msg = new SendPhoto()
                    .setChatId(recipientID)
                    .setPhoto(file_id);
                 msg = msg.disableNotification();
                  
                 try{
                     String group =cC.pp.getSubdialogueID(recipient);
                     if(group==null)group="";
                     convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , msg.toString());
                     
                     Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
                     Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
                     AttribVal av1 = new AttribVal("telegramtype","sendphoto");
                     additionalValues.add(av1);
                     AttribVal av2 = new AttribVal("telegramrawdata",msg.toString());
                     additionalValues.add(av2);     
                     AttribVal av3 = new AttribVal("telegramid",recipient.getConnection().telegramID );
                     additionalValues.add(av3);
             
                     cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server","", recipientsNames, additionalValues, false);
               
                     
                     
                     
                 }catch(Exception e){
                     e.printStackTrace();
                 }
                 
                 return recipient.sendPhoto(msg);
                 //System.err.println("HEREINCOMING102");
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }   
       
        //cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), "server",text, recipientNames, new Vector(), false);
        return null;
     }
      
     public void telegram_relayMessagePhotoToOtherParticipants_By_File_ID(TelegramParticipant sender, diet.tg.TelegramMessageFromClient tmfc){
           System.err.println("HEREINCOMING101");
         
           
            List<PhotoSize> photos = tmfc.u.getMessage().getPhoto();
            // Get largest photo's file_id
            String file_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();      
            
          String text = tmfc.u.getMessage().getText();
          if(text==null) text="";
          
          long senderTelegramLogin = tmfc.u.getMessage().getChatId();
          Vector recipients = cC.pp.getRecipients(sender);
          
          System.err.println("HEREINCOMING101A. there are " + recipients.size()+ " recipients");
           Vector<String> vRecipientsUsernames = new Vector();
          for(int i=0;i<recipients.size();i++){
              try{
                 TelegramParticipant recipient = (TelegramParticipant)recipients.elementAt(i);
                 vRecipientsUsernames.add(recipient.getUsername());
                 long recipientID = recipient.getConnection().telegramID;
                 SendPhoto msg = new SendPhoto()
                    .setChatId(recipientID)
                    .setPhoto(file_id);
                 msg = msg.disableNotification();
                 
                 
                 
                  
                recipient.sendPhoto(msg);
                
                String group =cC.pp.getSubdialogueID(recipient);
                if(group==null)group="";
                convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , msg.toString());
                
                 
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }   
                
          }    
           // this.getCHistoryUIM().updateParticipantsListChanged(this.getParticipants().getAllParticipants());
           String group =cC.pp.getSubdialogueID(sender);
            if(group==null)group="";
                  
            
           //public synchronized void saveMessageRelayedToOthers(String dtype,long timeOnClientOfStartTyping, long timeOnClientOfSending, long timeOnServerOfReceipt, String senderID, String senderUsername, String apparentSenderUsername, String text,
	//	 	Vector recipientsNames, boolean wasBlocked, Vector keyPresses, Vector<DocChange> documentUpdates, Vector<ClientInterfaceEvent> clientInterfaceEvents, Vector<AttribVal> additionalValues, boolean dummy) {
        
             Vector addVals = cC.getAdditionalInformationForParticipant(sender);
            
             AttribVal av1 = new AttribVal("telegramtype","photo");
             addVals.add(av1);
            
             String caption = tmfc.u.getMessage().getCaption();
             if(caption==null)caption="";
             AttribVal av2 = new AttribVal("caption", caption);
             addVals.add(av2);
             
             AttribVal av3 = new AttribVal("telegramrawdata",tmfc.u.toString());
             addVals.add(av3);
             
             AttribVal av4 = new AttribVal("telegramid",senderTelegramLogin);
             addVals.add(av3);
             
             Vector vAddValsFromMessage = tmfc.getAttribVals();
             addVals.addAll(vAddValsFromMessage);
             
            cH.saveMessageRelayedToOthers(group,  tmfc.u.getMessage().getDate() ,  tmfc.u.getMessage().getDate()  , tmfc.timeOfCreation,sender.getParticipantID(), sender.getUsername(),sender.getUsername(),text,vRecipientsUsernames,false,new Vector(),new Vector(), new Vector(), addVals,false);
       
            tmfc.hasBeenRelayed=true;
           
     }
    
     
public void telegram_relayMessageVoiceToOtherParticipants_By_File_ID(TelegramParticipant sender, diet.tg.TelegramMessageFromClient tmfc){
           System.err.println("HEREINCOMING101");
         
           
           String fileid = tmfc.u.getMessage().getVoice().getFileId();
           String fileuniqueid = tmfc.u.getMessage().getVoice().getFileUniqueId();
           
           
           // List<PhotoSize> photos = tmfc.u.getMessage().getPhoto();
            // Get largest photo's file_id
           /* String file_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();      
           */ 
          String text = tmfc.u.getMessage().getText();
          long senderTelegramLogin = tmfc.u.getMessage().getChatId();
          Vector recipients = cC.pp.getRecipients(sender);
          String sendergroup = cC.pp.getSubdialogueID(sender);
          
          System.err.println("HEREINCOMING101A. there are " + recipients.size()+ " recipients");
          
          Vector<String> vRecipientsUsernames = new Vector();
          
              
             
          for(int i=0;i<recipients.size();i++){
              try{
                 TelegramParticipant recipient = (TelegramParticipant)recipients.elementAt(i);
                 long recipientID = recipient.getConnection().telegramID;
                 vRecipientsUsernames.add(recipient.getUsername());            
            
                 /*SendPhoto msg = new SendPhoto()
                    .setChatId(recipientID)
                    .setPhoto(file_id)
                    .setCaption("Photo");
                 msg = msg.disableNotification();
                 */
                 
                 SendVoice msg = new SendVoice().setChatId(recipientID).setVoice(fileid);
                 
                 
                recipient.sendVoice(msg);
                
                String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , msg.toString());
                
                
                 
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }   
                
          }    
          
           Vector addVals = cC.getAdditionalInformationForParticipant(sender);
            
             AttribVal av1 = new AttribVal("telegramtype","voice");
             addVals.add(av1);
            
            
             
             AttribVal av2 = new AttribVal("telegramrawdata",tmfc.u.toString());
             addVals.add(av2);
             
             
             AttribVal av3 = new AttribVal("telegramid",senderTelegramLogin);
             addVals.add(av3);
             
             Vector vAddValsFromMessage = tmfc.getAttribVals();
             addVals.addAll(vAddValsFromMessage);
             
             cH.saveMessageRelayedToOthers(sendergroup,  tmfc.u.getMessage().getDate() ,  tmfc.u.getMessage().getDate()  , tmfc.timeOfCreation,sender.getParticipantID(), sender.getUsername(),sender.getUsername(),text,vRecipientsUsernames,false,new Vector(),new Vector(), new Vector(), addVals,false);
       
          
             tmfc.hasBeenRelayed=true;
          
           // this.getCHistoryUIM().updateParticipantsListChanged(this.getParticipants().getAllParticipants());
     }
     
     
     
     
     
     public org.telegram.telegrambots.meta.api.objects.Message telegram_sendPhoto_By_File_DeleteAfter(TelegramParticipant recipient, File f, long displayDuration, String[][] buttons, String[][] actions){
         org.telegram.telegrambots.meta.api.objects.Message m = telegram_sendPhoto_By_File(recipient, f, buttons, actions);
         
         if(this.tmdel==null) {
             CustomDialog.showModeLessDialog("TMDEL IS NULL", 100000);
             Conversation.saveErr("TMDEL IS NULL!");
             System.err.println("TMDEL IS NULL");
         }
         
         if(m!=null){
             if(m==null)System.err.println("M IS NULL");
             if(tmdel==null)System.err.println("TMDEL IS NULL");
             this.tmdel.add(recipient, m.getMessageId(), displayDuration);    
         }
         else{
             CustomDialog.showModeLessDialog(f.getName()+ "Cant get message ", 100000);
         }
         return m;
         
     }
     
     public void sendInlineKeyBoard(){
         
     }
     
     
     public void telegram_deleteMessageNow(TelegramParticipant recipient, int messageID){
          System.err.println("TELEGRAMDELETEMESSAGENOW: "+recipient+" "+messageID);
          this.tmdel.add(recipient, messageID, 1);    
     }
     
     
     
     
     
     
     
     public org.telegram.telegrambots.meta.api.objects.Message telegram_sendPhoto_By_File(TelegramParticipant recipient, File f, String[][] buttons, String[][] actions){
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
      //  CustomDialog.showDialog("142:SENDING STIMULU0");
        
        long recipientID = recipient.getConnection().telegramID;
            
             try{
  
                SendPhoto msg = new SendPhoto()
                    .setChatId(recipientID)
                    .setCaption("").setPhoto(f);
                 msg = msg.disableNotification();
                
                
                
                if(buttons!=null&&actions!=null){
                
                List<List<InlineKeyboardButton>> keybb = new ArrayList<>();
                 
                 for(int i=0;i<buttons.length;i++){
                     String[] buttonrow = buttons[i];
                     String[] actionrow = actions[i];
                     
                     List<InlineKeyboardButton> keyb = new ArrayList<>();
                     for(int j=0;j<buttonrow.length;j++){
                         String bname = buttonrow[j];
                         String baction = actionrow[j];
                         InlineKeyboardButton ikb = new InlineKeyboardButton();
                         ikb.setCallbackData(baction);
                         ikb.setText(bname);
                         keyb.add(ikb);
                     }
                     keybb.add(keyb);
                 }
                  
                InlineKeyboardMarkup im = new InlineKeyboardMarkup(); 
                im.setKeyboard(keybb);
                msg = msg.setReplyMarkup(im);
                 
                }
                
                try{
                     String group =cC.pp.getSubdialogueID(recipient);
                     if(group==null)group="";
                     convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , msg.toString());
                     
                      Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
                      Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
                      AttribVal av1 = new AttribVal("telegramtype","sendphotobyfile");
                      additionalValues.add(av1);
                      AttribVal av2 = new AttribVal("telegramrawdata",msg.toString());
                      additionalValues.add(av2);     
                      AttribVal av3 = new AttribVal("telegramid",recipient.getConnection().telegramID );
                      additionalValues.add(av3);
             
                      cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server","", recipientsNames, additionalValues, false);
                      
                      
                     
                 }catch(Exception e){
                     e.printStackTrace();
                 }
                
               // CustomDialog.showDialog("142:SENDING STIMULUA");
                return recipient.sendPhoto(msg);
                 //System.err.println("HEREINCOMING102");
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }   
         return null;
        //cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), "server",text, recipientNames, new Vector(), false);
   
     }
     
     
     
     public void telegram_DeleteMessage_DoNotCallThisMethodDirectlyFromController(TelegramParticipant recipient, int message_id , long timeSinceDeleteRequested){
         //  String subdialogueID = cC.pp.getSubdialogueID(recipient);
        
        long recipientID = recipient.getConnection().telegramID;
         
         
          try{
              DeleteMessage dm = new DeleteMessage().setChatId(recipientID).setMessageId(message_id);
              System.err.println("CREATING DELETEMESSAGE: "+recipientID+" "+message_id);
              
               String group =cC.pp.getSubdialogueID(recipient);
               if(group==null)group="";
              
               recipient.sendDeleteMessage(dm);
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , dm.toString());
               
               Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
               Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
               
               if(timeSinceDeleteRequested>0){
                    AttribVal av1 = new AttribVal("telegramtype","delayeddeletemessage"+timeSinceDeleteRequested);
                    additionalValues.add(av1);
                    AttribVal av2 = new AttribVal("telegramrawdata",dm.toString());
                    additionalValues.add(av2);     
                    AttribVal av3 = new AttribVal("telegramid",recipientID);
                    additionalValues.add(av3);
                    
               }
               else{
                   AttribVal av1 = new AttribVal("telegramtype","deletemessage");
                    additionalValues.add(av1);
                    AttribVal av2 = new AttribVal("telegramrawdata",dm.toString());
                    additionalValues.add(av2);
                    AttribVal av3 = new AttribVal("telegramid",recipientID);
                    additionalValues.add(av3);
               }
              
               cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(), "server", "", recipientsNames, additionalValues, false);
               
          }
          catch(Exception e){
              e.printStackTrace();
          }
          
           
      
     }
     
     
     
         
    public void telegram_sendEditMessageToParticipant(TelegramParticipant recipient, org.telegram.telegrambots.meta.api.objects.Message mOriginalMessage, String replacementText){
              
        String subdialogueID = cC.pp.getSubdialogueID(recipient);  
        long recipientID = recipient.getConnection().telegramID;
        
        String originalText ="";
        if(replacementText==null) replacementText ="";
        
        
        try{
            originalText = mOriginalMessage.getText();
            if(originalText==null)originalText="";
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
             try{
                 
                Integer messageID = mOriginalMessage.getMessageId() ;
                Long messagechatid = mOriginalMessage.getChatId();
                EditMessageText emt = new EditMessageText();
            
           
                emt.enableHtml(true);
                emt.setText("<code>"+replacementText+"</code>");
                emt.setChatId(messagechatid);
                emt.setMessageId(messageID);  
                              
                recipient.sendEditMessage(mOriginalMessage, emt);
                
                String group =cC.pp.getSubdialogueID(recipient);
                
               if(group==null)group="";
               convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , emt.toString());
                
               Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
             Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
             AttribVal av1 = new AttribVal("telegramtype","editmessage");
             additionalValues.add(av1);
              AttribVal av2 = new AttribVal("telegramrawdata_original",mOriginalMessage.toString());    
             additionalValues.add(av2);   
             AttribVal av3 = new AttribVal("telegramrawdata_replacement",emt.toString());    
             additionalValues.add(av3);     
             AttribVal av4 = new AttribVal("originaltext", mOriginalMessage.getText());
             additionalValues.add(av4);
         
             AttribVal av5 = new AttribVal("replacementtext", mOriginalMessage.getText());
             additionalValues.add(av5);
             
             AttribVal av6 = new AttribVal("telegramid",recipient.getConnection().telegramID );
             additionalValues.add(av6);
            
            
 
             cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server",  "REPLACED: "+originalText+" WITH: "    +replacementText, recipientsNames, additionalValues, false);
               
             System.err.println("HEREINCOMING102");
                
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }    
           
     }
    
    
     public void telegram_sendPinChatMessageToParticipant(TelegramParticipant recipient, org.telegram.telegrambots.meta.api.objects.Message messageToBePinned){
              
        String subdialogueID = cC.pp.getSubdialogueID(recipient);  
        long recipientID = recipient.getConnection().telegramID;
        String messageToBePinnedText ="";
            
             try{
                 
                PinChatMessage pcm = new PinChatMessage() ;
                pcm = pcm.setMessageId(messageToBePinned.getMessageId());
                pcm = pcm.setChatId(messageToBePinned.getChatId());
                
                String group =cC.pp.getSubdialogueID(recipient);
                
                
                recipient.sendPinChatMessage(pcm);
                
                if(group==null)group="";
                convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , pcm.toString());
                
                Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
                Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
             AttribVal av1 = new AttribVal("telegramtype","pinchatmessage");
             additionalValues.add(av1);
              AttribVal av2 = new AttribVal("telegramrawdata",pcm.toString());    
             additionalValues.add(av2);   
             
             try{
                 messageToBePinnedText = messageToBePinned.getText();
                 if(messageToBePinnedText==null)messageToBePinnedText ="";
             }catch (Exception e){
                 e.printStackTrace();
             }
             
             
              AttribVal av3 = new AttribVal("telegrammessagetobepinnedtext",  messageToBePinnedText);
             additionalValues.add(av3);
             
             AttribVal av4 = new AttribVal("telegramid",recipient.getConnection().telegramID );
             additionalValues.add(av4);
            
             
 
             cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server",  "Pinned: "+messageToBePinnedText, recipientsNames, additionalValues, false);
               
             System.err.println("HEREINCOMING102");
                
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }    
           
     }
    
    
    
    
    
     
     
     
         
    public void telegram_sendEditMessageReplyMarkup(TelegramParticipant recipient, Update u, String[][] buttons, String[][] actions){
         CallbackQuery cbq = u.getCallbackQuery();
        // Integer messageID = u.getMessage().getMessageId();
         org.telegram.telegrambots.meta.api.objects.Message  m =cbq.getMessage();
         Integer messageID=m.getMessageId();
         String callbackData =   cbq.getData();
         System.err.println("callbackdata: "+callbackData);   
         String inlineMessageID = cbq.getInlineMessageId();
         Long messagechatid = m.getChatId();
         telegram_sendEditMessageReplyMarkup(recipient, messageID, messagechatid,inlineMessageID,messagechatid,buttons,actions );
    } 
     
     
     
     
     
     
     
     public void telegram_sendEditMessageReplyMarkup(TelegramParticipant recipient, Integer messageID,Long messagechatid, String inlineMessageID, Long chatID  ,   String[][] buttons, String[][] actions){
         try{

            EditMessageReplyMarkup emrm = new EditMessageReplyMarkup();
            if(inlineMessageID!=null)emrm = emrm.setInlineMessageId(inlineMessageID);
            if(messagechatid!=null)emrm = emrm.setChatId(messagechatid);
            emrm.setMessageId(messageID);
                 
             
             
             if(buttons!=null&&actions!=null){
                
                List<List<InlineKeyboardButton>> keybb = new ArrayList<>();
                 
                 for(int i=0;i<buttons.length;i++){
                     String[] buttonrow = buttons[i];
                     String[] actionrow = actions[i];
                     
                     List<InlineKeyboardButton> keyb = new ArrayList<>();
                     for(int j=0;j<buttonrow.length;j++){
                         String bname = buttonrow[j];
                         String baction = actionrow[j];
                         InlineKeyboardButton ikb = new InlineKeyboardButton();
                         ikb.setCallbackData(baction);
                         ikb.setText(bname);
                         keyb.add(ikb);
                     }
                     keybb.add(keyb);
                 }
                  
                InlineKeyboardMarkup im = new InlineKeyboardMarkup(); 
                im.setKeyboard(keybb);
                emrm = emrm.setReplyMarkup(im);
                 
                }
             else{
               InlineKeyboardMarkup im = new InlineKeyboardMarkup(); 
                emrm = emrm.setReplyMarkup(im);
             }
             
             
                 
            recipient.sendEditMessageReplyMarkup(emrm);
            
            String group =cC.pp.getSubdialogueID(recipient);
            if(group==null)group="";
            convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"from",new Date().getTime() , emrm.toString());
           
             Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
             Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
             AttribVal av1 = new AttribVal("telegramtype","editmessagemarkup");
             additionalValues.add(av1);
             AttribVal av2 = new AttribVal("telegramrawdata",emrm.toString());
             additionalValues.add(av2);     
              AttribVal av3 = new AttribVal("telegramid",recipient.getConnection().telegramID );
              additionalValues.add(av3);
              cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server","", recipientsNames, additionalValues, false);
               
            
            
            
                  
              }catch (Exception e){
                  e.printStackTrace();
              }
    }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     public org.telegram.telegrambots.meta.api.objects.Message telegram_sendPoll(TelegramParticipant recipient, String explanation, String question, String[] options, boolean allowMultipleSelections){
        String subdialogueID = cC.pp.getSubdialogueID(recipient);
        
        long recipientID = recipient.getConnection().telegramID;
            
             try{
                SendPoll sp = new SendPoll();
                sp = sp.setQuestion(question);
                sp = sp.setOptions( Arrays.asList(options));
                sp = sp.setExplanation(explanation);
                sp = sp.setAnonymous(true);
                sp =sp.setAllowMultipleAnswers(allowMultipleSelections);
                
                
               
                sp=sp.setChatId(recipientID);
                sp=sp.disableNotification();
                
                           
                try{
                     String group =cC.pp.getSubdialogueID(recipient);
                     if(group==null)group="";
                     convIO.saveTelegramIO(group, recipient.getParticipantID(), recipient.getUsername(),"to",new Date().getTime() , sp.toString());
                     
                     Vector<String> recipientsNames = new Vector(); recipientsNames.add(recipient.getUsername());
                     Vector additionalValues = cC.getAdditionalInformationForParticipant(recipient);
              
                   
                     AttribVal av1 = new AttribVal("telegramtype","sendpoll");
                     additionalValues.add(av1);
                     AttribVal av2 = new AttribVal("telegramrawdata",sp.toString());
                     additionalValues.add(av2);     
                     AttribVal av3 = new AttribVal("telegramid",recipient.getConnection().telegramID );
                     additionalValues.add(av3);
             
                     cH.saveArtificialMessageCreatedByServer(group, new Date().getTime(),  "server",sp.toString(), recipientsNames, additionalValues, false);
               
                     
                     
                 }catch(Exception e){
                     e.printStackTrace();
                 }  
                
                 return recipient.sendPoll(sp);
                 //System.err.println("HEREINCOMING102");
              } catch (Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
                  
              }   
       
        //cH.saveArtificialMessageCreatedByServer(subdialogueID, mctc.getTimeOfSending().getTime(), "server",text, recipientNames, new Vector(), false);
        return null;
     }
     
     public boolean isInTelegramMode(){
         if(this.cC==null) return false;
         if(this.cC instanceof TelegramController) return true;
         return false;
     }    
     
     
     
     
     
     public void telegram_sendDelayedArtificialTurnFromApparentOriginToPermittedParticipants(TelegramParticipant apparentSender,  String text, long delay){
            
            System.err.println("telegram_sendDelayedArtificialTurnFromApparentOriginToPermittedParticipants(A): "+text);
            Vector<Participant> vP = cC.pp.getRecipients(apparentSender);
            Vector<String>  vID = new Vector();
            
            for(int i=0;i<vP.size();i++){
                vID.add(vP.elementAt(i).getParticipantID());
            }
            
            delayedaction ds = new delayedaction(){
                 public void codetorun(){
                       System.err.println("telegram_sendDelayedArtificialTurnFromApparentOriginToPermittedParticipants(B): "+text);
                       telegram_sendArtificialTurnFromApparentOriginToParticipantID(apparentSender, vID,  text, delay);                
                 }
            };
            ds.timeOfSend=new Date().getTime()+delay;
            del.add(ds);
            
            
      }
     
     Delayer del = new Delayer();
     
     private class Delayer extends Thread{

       Vector<delayedaction> das = new Vector();
         
        public Delayer() {
            this.start();
        }
        
        public synchronized void add(delayedaction da){
            das.add(da);
            System.err.println("DELAYED ACTION ADDING: Time till send = "+(new Date().getTime()-da.timeOfSend));
            notifyAll();
        }
        
        
        public synchronized void run(){
            while(2<5){
                try{
                     while(das.size()==0){
                         wait();
                     }
                     long currentTime = new Date().getTime();
                     Vector<delayedaction> dasRUNNOW = new Vector();
                     long timeTillNextEvent = 999999999999999L;
                     
                     for(int i=0;i<das.size();i++){
                         if(das.elementAt(i).timeOfSend<currentTime){
                             dasRUNNOW.add(das.elementAt(i));
                              System.err.println("DELAYER: CURRENT TIME IS "+currentTime);
                              System.err.println("DELAYER: TIME OF SEND IS:"+das.elementAt(i).timeOfSend);
                             
                             
                              System.err.println("DELAYER: ADDING ELEMENT ");
                         }
                         else{
                             long timeTillItActivates = das.elementAt(i).timeOfSend-currentTime;
                             if(timeTillItActivates<0){
                                 System.err.println("DELAYER: Need to reset time from "+timeTillItActivates + "to 0");
                                 timeTillItActivates =0;
                             }
                             if(timeTillItActivates < timeTillNextEvent) timeTillNextEvent = timeTillItActivates;
                         }
                         
                     }
                     
                     for(int i=0;i<dasRUNNOW.size();i++){
                        delayedaction da = das.elementAt(i);
                        System.err.println("DELAYER: RUNNING" );
                        das.remove(da);
                        da.codetorun();
                        System.err.println("DELAYER: FINISHED RUNNING" );
                     }
                     
                     if(das.size()>0){
                          System.err.println("DELAYER WILL NOW SLEEP FOR: "+timeTillNextEvent);
                          wait(timeTillNextEvent+1);
                        
                     }
                     
                     
                    
                }catch(Exception e){
                    e.printStackTrace();
                    Conversation.saveErr(e);
                }
            }
             
            
        }
        
         
     }
     
     public autointervention getAI(){
         return cC.ai;
     }
     
     
     private class delayedaction{
          long timeOfSend = new Date().getTime();
          
          public void codetorun(){
              
          }
         
     }
     
     
         
     }
     
     
     
