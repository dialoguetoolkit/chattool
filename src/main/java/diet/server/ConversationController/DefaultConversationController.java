package diet.server.ConversationController;
///THIS IS A MAIN CLASS
import diet.attribval.AttribVal;
import diet.client.JChatFrameMultipleWindowWithSendButtonWidthByHeight.StyledDocumentStyleSettings;
import java.util.Date;
import java.util.Random;


import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.Participant;
import diet.server.stylemanager.StyleManager;
import diet.message.*;
import diet.server.ParticipantGroups.ParticipantGroups;
import diet.server.IsTypingController.IsTypingOrNotTyping;
import diet.server.Configuration;
import diet.server.ConversationController.autointervention.autointervention;
import diet.server.io.IntelligentIO;
import diet.task.DefaultTaskController;
import diet.task.TaskControllerInterface;
import diet.textmanipulationmodules.CyclicRandomTextGenerators.CyclicRandomParticipantIDGeneratorGROOP;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import java.awt.Color;
import java.io.File;

import java.util.Vector;
import javax.swing.text.MutableAttributeSet;

/**
 * This is the main (preferably only) class that should be changed when creating
 * a new experimental design. Every message sent from the clients passes through
 * the methods provided by this class. This includes each keypress, each turn
 * typed and sent. <p>On receiving a message, this class determines what is to
 * be done with the message. In normal operation it relays the messages to the
 * other participants. However, to create interventions this behaviour can be
 * replaced with commands to modify the turn or create artificial turns.
 *
 * <p> Most of the methods of this class are called by {@link diet.server.Conversation}.
 * It is expected that the methods of this class will do necessary detection of
 * targets, transforming of turns, and on this basis call methods in {@link diet.server.Conversation}
 * to send the artificially created messages to the participants.
 *
 *
 * @author gjmills
 */
public abstract class DefaultConversationController  {
 
    public Conversation c;
    
    
    //This class detects whether or not to do an auto intervention
    public autointervention ai ;
    
    
    
    /**
     * The configuration file for the chat-tool
     */
    public static Configuration sett = new Configuration();
    
   
    /**
     * Used to generate ParticipantIDs when using the autologin function when developing
     */
    public static CyclicRandomParticipantIDGeneratorGROOP autologinParticipantIDGenerator = new CyclicRandomParticipantIDGeneratorGROOP(); 
  
  
    /**
     * This object is used to generate the "is tpying" status indicator messages - note that these can be manipulated  
     */
    public IsTypingOrNotTyping itnt; //= new IsTypingOrNotTyping(this, param_isTypingTimeOut);
   
    
    /**
     * This is used to store who speaks with whom 
     */
    public ParticipantGroups pp;
    
    
    /**
     * This is used to make sure that each person sees other participants' text with consistent colours / fonts 
     */
    public StyleManager sm = new StyleManager(this);
    
    
    /**
     * Many experiments also involve some dialogue task 
     */
    public TaskControllerInterface tc;
        
    public Random r = new Random(new Date().getTime());
     
    public boolean experimentHasStarted = false; 
    
    public Vector participantJoinedConversationButNotAssignedToGroup = new Vector();
    
    
    
    public DefaultConversationController(Conversation c){
        this.c=c;
        String parentDirectory = System.getProperty("user.dir");
        if(parentDirectory.endsWith(File.separator)){
            parentDirectory = parentDirectory + "data"+File.separator+"saved experimental data";
        }
        else{
            parentDirectory = parentDirectory + File.separator+ "data"+File.separator+"saved experimental data";
        }
            
        c.convIO = new IntelligentIO(c,parentDirectory,this.getID());
        pp = new ParticipantGroups(this);
        itnt = new IsTypingOrNotTyping(this, sett.client_TextEntryWindow_istypingtimeout);
        this.ai=new autointervention(c);
       
    }
    
    public DefaultConversationController(Conversation c, long istypingtimeout){
        this.c=c;
         String parentDirectory = System.getProperty("user.dir");
        if(parentDirectory.endsWith(File.separator)){
            parentDirectory = parentDirectory + "data"+File.separator+"saved experimental data";
        }
        else{
            parentDirectory = parentDirectory + File.separator+ "data"+File.separator+"saved experimental data";
        }
        c.convIO = new IntelligentIO(c,parentDirectory,this.getID());
        pp = new ParticipantGroups(this);
        itnt = new IsTypingOrNotTyping(this, istypingtimeout);
        this.ai=new autointervention(c);
    }
    
    /**
    * This is called after the object has been created and connected with the GUI and filesystem. 
    */
    public void  initializePostSetup(){
        
    }
    
    /**
     * 
     * @return whether the ConversationController object is displayed in the GUI.
     */
    public static boolean showcCONGUI() {
        return false;
    }

    /**
     * 
     * @return the Conversation object
     */
    public Conversation getC() {
        return c;
    }
   
    /**
     * 
     * @return the StyleManager, which is responsible for determining the fonts and colours of participants' text.
     */
    public StyleManager getStyleManager() {
        return sm;
    }

    
    
    /**
    * Called by the client when the client attempts to connect to the server
    * This is step 1 in the login process.
    */   
    public boolean requestParticipantJoinConversation(String participantID) {
        return true;
    }
    
    
            
   
    
    /**
     * Step 2 in the login process (after participant successfully entered ID and Username)
     * 
     * @return the Message containing the setup information that is sent to the client after successful logging in.
     */
   public MessageClientSetupParameters processRequestForInitialChatToolSettings(){      
               boolean alignmentIsVertical = true;
               boolean deletesPermitted =true;
               
               
               
               Color background =    new Color(sett.client_backgroundcolour_rgb[0],sett.client_backgroundcolour_rgb[1], sett.client_backgroundcolour_rgb[2]);
               Vector othersColors = new Vector();
               Color selfColor = Color.black;
               StyledDocumentStyleSettings styleddocsettings;
               int ownWindowNumber =0;
               try{
                   MutableAttributeSet masSELF =  this.getStyleManager().getStyleForSelf();  
                   styleddocsettings = new StyledDocumentStyleSettings(background, selfColor, masSELF  );
                   return new MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight("server","servername2",
                                                this.sett.client_MainWindow_width, this.sett.client_MainWindow_height,
                                                alignmentIsVertical,
                                                this.sett.client_numberOfWindows,
                                                ownWindowNumber,    //This needs to be loaded from the Permissions File //Get x,y
                                                false,
                                                true,
                                                "Setting up",
                                                true,
                                                this.sett.client_TextEntryWindow_width, this.sett.client_TextEntryWindow_height,
                                                this.sett.client_TextEntryWindow_maximumtextlength,
                                                styleddocsettings);
               }catch (Exception e){
                    Conversation.printWSln("Main", "Could not find parameters for chat tool client interface...attempting to use defaults" );
                    
                e.printStackTrace();

                }
       return null;          
    }
    
  
    /**
     * Third stage in the login process.
     * Use this method to determine what instructions the participant is sent when they login
     * Use this method to initialize aspects of the task that depend on participants logging in.
     * 
     * @param p Participant who has just logged in
     */
    public void participantJoinedConversation(Participant p){
        participantJoinedConversationButNotAssignedToGroup.add(p);
        if(participantJoinedConversationButNotAssignedToGroup.size()== Configuration.defaultGroupSize){
             pp.createNewSubdialogue(participantJoinedConversationButNotAssignedToGroup);
             itnt.addGroupWhoAreMutuallyInformedOfTyping(participantJoinedConversationButNotAssignedToGroup);           
             participantJoinedConversationButNotAssignedToGroup.removeAllElements();
       }
      
    }
    
     
    
    
    /**
     * 
     * This is invoked if a participant who has already logged "joined" the experiment logs in again. 
     * This typically happens if their computer crashes or if they restart their computer.
     * 
     * @param p the participant who logs back in.
     */
    public void participantRejoinedConversation(Participant p) {
    }
   
   
    /**
     *
     * This method is invoked by {@link diet.server.Conversation} whenever a
     * participant presses a key while typing text in their chat window. The
     * default behaviour is to inform the other participants that the
     * participant is typing.
     *
     * @param sender Participant who has pressed a key
     * @param mkp message containing the keypress information
     */
    public void processKeyPress(Participant sender, MessageKeypressed mkp) {
        if (!mkp.isENTER()){
          this.itnt.processDoingsByClient(sender);
        }
    }

    /**
     * This method is invoked by {@link diet.server.Conversation} whenever the
     * text in a participant's text entry window changes by having one or more
     * characters inserted.
     *
     * @param sender participant who inserted text
     * @param mWYSIWYGkp message containing information about the text inserted
     */
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
    }

    /**
     * This method is invoked by {@link diet.server.Conversation} whenever the
     * text in a participant's text entry window changes by having one or more
     * characters deleted. This is separate from Keypresses (a user might delete
     * a whole segment of text by highlighting the text and pressing delete
     * once).
     *
     * @param sender participant who deleted text
     * @param mWYSIWYGkp message containing information about the text deleted
     */
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
    }

    
    
    
    
    
    
   
        
    
    /**
     * This method is invoked by {@link diet.server.Conversation} whenever a
     * participant has typed a message The default behaviour is to relay the
     * message to the other participants. This is the main locus for programming
     * interventions in the chat tool.
     *
     * @param sender the participant who typed the turn
     * @param mct the message typed by the participant
     */
    public void processChatText(Participant sender, MessageChatTextFromClient mct) {
        
        
        
        if(!this.experimentHasStarted){
            c.sendInstructionToParticipant(sender, "Please wait until the experiment has started");
        }
        
        itnt.processTurnSentByClient(sender);
        if (sett.debug_allow_client_to_send_debug_commands) {
            cmnd(sender, mct.getText());
        }
    }
    
    
    /**
     * 
     * This method is invoked when the client performs a move in a task (e.g. moving position marker in the maze game, selecting a referent)
     * 
     * @param mt The taskmove
     * @param p  The participant who performed the task move.
     */
    public void processTaskMove(MessageTask mt, Participant p){
         if(tc!=null){
           tc.processTaskMove(mt, p);
        }
    }
    
    /**
     * Sends an instruction to the taskcontroller to close down.
     */
    public void closeDown(){
        if(tc!=null){
           tc.closeDown();
        }
    }
    
   

    /**
     * Invoked after the server instructs the client to display a popup using {@link diet.server.Conversation.showPopupOnClientQueryInfo}, and the client selects one of the options.
     * 
     * @param origin The participant who pressed one of the options on the popup button
     * @param mpr The message containing information about the selection.
     */
    public void processPopupResponse(Participant origin, MessagePopupResponseFromClient mpr) {
    }

    /**
     * 
     * This method receives all the updates from the clients' interfaces. This is very finegrained information about everything the participants perceived on their screens. 
     * This data is saved to "clientinterfaceevents.txt". 
     * For turn-based interventions, this information is probably too finegrained!
     * 
     * @param origin Participant who generated the interface event
     * @param mce message describing the interface event
     */
    public void processClientEvent(Participant origin, MessageClientInterfaceEvent mce){
       
        
    }
    
   
    
   
    
    
    
    
     /**
      * 
      * This method is used when saving data to turns.txt. 
      * If you want to save additional information for a participant to file, override this method.
      * 
      * @param p
      * @return 
      */
     public Vector<AttribVal> getAdditionalInformationForParticipant(Participant p){
         try{
             AttribVal av = new AttribVal("istypingtimeout",""+itnt.getIsTypingTimeout());
             Vector v = new Vector(); v.addElement(av); 
             return v;
         } catch (Exception e){
             e.printStackTrace();
         }
         
         return new Vector();
    }
    

    

    
    /**
     * 
     * This allows the experimenter to run commands on the server, e.g. when debugging.
     * Currently, the only command implemented is to kill the ParticipantConnection
     * 
     * @param p
     * @param command 
     */
    public void cmnd(Participant p, String command) {
        if (command.equalsIgnoreCase("////d")) {
            if (p != null) {
                p.getConnection().dispose();
            }
        }
    }
    
    
    /**
     * 
     * After a participant stops typing the "is typing" messages persists for an additional duration.
     * 
     * @param n number of milliseconds the "is typing" message is displayed.
     */
    public void setIsTypingTimeout(int n){
        this.itnt.setInactivityThreshold(n);
    }
    
    
    
    /**
     * 
     * Invoked when one of the participants presses buttons that are displayed underneath their stimulus, created with {@link dier.server.Conversation.showStimulusImageFromJarFile_InitializeWindow}
     * 
     * @param sender
     * @param mbfc 
     */
    public void processButtonPress(Participant sender, MessageButtonPressFromClient mbfc){
        c.saveAdditionalRowOfDataToSpreadsheetOfTurns("buttonpress", sender, mbfc.buttonname);
    }
            
    
    
    /**
     * 
     * @return the default settings of the chattool
     */       
    public Configuration getSettings(){
        return DefaultConversationController.sett;
    }
    
    String id = getClass().getSimpleName();
    
    
    /**
     * 
     * Set the ID of the experiment (stored in the turns.txt file)
     * 
     * @param s the ID of the experiment
     */
    public void setID(String s){    
        this.id=s;
    }
    
    /**
     * 
     * @return the ID of the experiment
     */
    public String getID(){
        if(id==null)return "notset";
        if(id.length()==0) return "notset";
        if(id.equalsIgnoreCase("")) return "notset";
        return id;
    }
    
    
    public void performActionCalledByTaskController(String s, DefaultTaskController tc){
        
    }
    
    
    public void telegram_participantJoinedConversation(TelegramParticipant p) {
    
    }
    
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
    
    }
    
    
   
   public void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
       
   }
                    
    public void telegram_processPollAnswerFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
    
}
    
    public  String[] telegram_getUniqueParticipantIDAndUniqueUsername(long telegramID ,String logincode){
         return new String[]{"",""};
        
    }
    
    
    public boolean telegram_startTelegramBOT(){
        return false;
    }
    
    
    public boolean processAutoIntervention(Participant sender, TelegramMessageFromClient tmfc){
        String text = tmfc.u.getMessage().getText();
        boolean hasBeenModified = ai.processText(sender, text);
        return hasBeenModified;
    }
    
    
    
    
    
}
