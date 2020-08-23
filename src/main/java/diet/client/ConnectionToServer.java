package diet.client;

import diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker;
import diet.client.JChatFrameMultipleWindowWithSendButtonWidthByHeight.JChatFrameMultipleWindowsWithSendButtonWidthByHeight;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

import diet.task.mazegame.ClientMazeGameComms;
import diet.message.*;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.OpenInBrowser;
import diet.server.experimentmanager.ui.JEMStarter;
import diet.serverclientconsistencycheck.ServerClientConsistency;
import diet.task.ClientTaskEventHandler;
import diet.task.mazegame.message.MessageNewMazeGame;
import diet.task.gridstimuli.JFrameGridImagesStimuli;
import diet.task.gridstimuli.JFrameGridTextStimuli;

import diet.task.tangram2D1M.ClientTangramGameComms;
import diet.task.tangram2D1M.message.MessageNewTangrams;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Date;
import java.util.Random;
import javax.swing.SwingUtilities;

/**
 * This deals with low-level network communication with server. It exchanges messages between server and client, 
 * unpacking messages from the server and calling associated methods in {@link ClientEventHandler}. Events from the
 * chat tool interface are assembled into messages and sent to the server.
 *  
 * @author user
 */
public class ConnectionToServer extends Thread {

    public ClientEventHandler cEventHandler;
    public ClientTaskEventHandler cteh;
    public String serverName;
    private Socket cSocket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private String email;
    private String forcedParticipantID;
    private boolean forceParticipantID = false;
    private String username;
    boolean retryconnection =true;
    private int portNumber =4444;
    private WebpageAndImageDisplay wiad = new WebpageAndImageDisplay(this);
    private JFrameSubliminalStimuli jfsubliminalstimuli;
    private JFrameGridImagesStimuli  jfgridimagestimuli;
    private JFrameGridTextStimuli  jfgridtextstimuli;
    private JFrameStimulusSingleImage jfssi;
   
    int turnsBetweenResets = 30;
    int counter =0;
    JChatFrame jf;
    ConnectionToServerOutgoingQueue ctsq;
    
    
    
    //MessageChatTextToClient mctImmediatePriorByOther = null;
    
    
    public ConnectionToServer(String serverName) {
        super();
        this.serverName = serverName;
        this.ctsq = new ConnectionToServerOutgoingQueue(this);
    }


    public ConnectionToServer(String serverName,int portNumber, String participantID ) {
        super();
        this.forcedParticipantID=participantID;
        this.forceParticipantID=true;
        this.portNumber = portNumber;
        this.serverName = serverName;
        this.ctsq = new ConnectionToServerOutgoingQueue(this);
    }




    public ConnectionToServer(String serverName,int portNumber) {
        super();
        this.portNumber = portNumber;
        this.serverName = serverName;
        this.ctsq = new ConnectionToServerOutgoingQueue(this);
    }

    /**
     * Attempts to set up network connection with server. On establishing a connection it prompts the participant 
     * for an email address and also a username.
     *
     * Return false if user decides to cancel logging in
     *
     * @throws java.lang.Exception
     */
    private boolean loginToServerAndInitializeChattool() throws Exception{
        String emailrequest = "";
        String usernamerequest= "";
        boolean loggedin = false;
        System.out.println("ClientSetup4b1");
        
        
        InetAddress ina = this.cSocket.getInetAddress();
        int inPort = this.cSocket.getPort();
        InetAddress local_ina = this.cSocket.getInetAddress();
        int local_port = this.cSocket.getLocalPort();
        SocketAddress socketAddress = this.cSocket.getLocalSocketAddress();
        
       
         
        while (!loggedin){
           System.out.println("ClientSetup4b2");
           Object o = in.readObject();
           System.out.println("ClientSetup4b2A");
           System.out.println("THE CLASS IS:"+o.getClass().toString());
           System.out.println("ClientSetup4b2B");
            Message m = (Message)o;
            if(o instanceof  MessageClientCloseDown){
                System.out.println("CLIENT RECEIVED INSTRUCTION TO CLOSEDOWN");
                System.exit(-5555365);    
            }
            
            
            System.out.println("ClientSetup4b3");
            System.out.println("Not logged in yet2"+usernamerequest+" "+emailrequest);
             if (m instanceof MessagePopupClientLogonEmailRequest && this.forceParticipantID){
                 if(this.forcedParticipantID==null||this.forcedParticipantID.length()==0){
                     JOptionPane.showMessageDialog(null, "For some reason the program can't\n"
                             + "find your ID so we can't log you in\n"
                             + "Please contact the experimenter directly\n"
                             + "If you wish to take part\n");
                     System.exit(-1);
                 }
                 emailrequest=this.forcedParticipantID;
                 out.writeObject(new MessageClientLogon(emailrequest,"",ServerClientConsistency.getVersionID(),  ina, inPort,  local_ina, local_port,  socketAddress));
             }

            else if(m instanceof MessagePopupClientLogonEmailRequest){
                System.out.println("ClientSetup4b4A");
                 MessagePopupClientLogonEmailRequest m2 = (MessagePopupClientLogonEmailRequest)m;
                 if(DefaultConversationController.sett.login_autologin){
                     emailrequest=DefaultConversationController.autologinParticipantIDGenerator.getNextNonDeleting();
                     //emailrequest = ""+new Random().nextInt(10000000);
                     usernamerequest=usernamerequest+emailrequest;
                     System.err.println(usernamerequest+"...."+emailrequest);
                 }else{                 
                     emailrequest =  (String) JOptionPane.showInputDialog(m2.getPrompt()+"Must be at least "+m2.getMinLength()+" characters",m2.getOptionText());
                 }
                 if(emailrequest==null)return false;
                 while(emailrequest.length()<m2.getMinLength()){
                     emailrequest =  (String) JOptionPane.showInputDialog(m2.getPrompt()+"Must be at least "+m2.getMinLength()+" characters",m2.getOptionText());
                     if(emailrequest==null)return false;
                 }
                 System.out.println("Sending EmailRequest");
                 out.writeObject(new MessageClientLogon(emailrequest,"",ServerClientConsistency.getVersionID(),  ina, inPort,  local_ina, local_port,  socketAddress));
            }
            else if (m instanceof MessagePopupClientLogonUsernameRequest){
                System.out.println("ClientSetup4b4B");
               MessagePopupClientLogonUsernameRequest m2 = (MessagePopupClientLogonUsernameRequest)m;
                if(DefaultConversationController.sett.login_autologin){
                    usernamerequest = m2.getOptionText();
                    if(m2.getOptionText()==null||usernamerequest.length()==0){
                        System.exit(-62);
                    }
                    out.writeObject(new MessageClientLogon(emailrequest,usernamerequest,ServerClientConsistency.getVersionID(),  ina, inPort,  local_ina, local_port,  socketAddress));
                }
                
                else if(m2.getAllowAlternatives()) {
                     System.out.println("ClientSetup4b4B1");
                     usernamerequest =  (String) JOptionPane.showInputDialog(m2.getPrompt(),m2.getOptionText());
                     if(usernamerequest==null)return false;
                     out.writeObject(new MessageClientLogon(emailrequest,usernamerequest,ServerClientConsistency.getVersionID(),  ina, inPort,  local_ina, local_port,  socketAddress));
                     System.out.println("Sending UsernameRequest:"+usernamerequest);
                     
                }
                else{
                    System.out.println("ClientSetup4b4_B2");
                    usernamerequest = m2.getOptionText();
                    Object[] options = {"Log me in again NOW", "Cancel"};
                    int n = JOptionPane.showOptionDialog(null, "It looks as if you were logged on before\n\n"
                            + "USERNAME: "+m2.getOptionText() +"\n"
                            + "\n\n"
                            + "Do you want to log back in again and continue?\n"
                            + "(Any old windows from previous login will stop working\n",
                            "Previous login detected",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,  options[0]); //default button title
                    if(n==1)return false;
                    else{
                        System.out.println("ClientSetup4b4B21");
                        out.writeObject(new MessageClientLogon(emailrequest,usernamerequest,ServerClientConsistency.getVersionID(),  ina, inPort,  local_ina, local_port,  socketAddress));
                    }


                }
            }
            else if (
                    m instanceof MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight
                    || m instanceof MessageClientSetupParametersWithSendButtonAndTextEntryOneTurnAtATime)
                   {
               // This IF section should be moved to the initialsetupofchattool() method 
               System.out.println("Logged  in...Received client setup parameters");
                MessageClientSetupParameters mcsp = (MessageClientSetupParameters)m;
                if(mcsp.getNewEmail()!=null){
                    email = mcsp.getNewEmail();
                }
                else{
                    email = emailrequest;
                }
                if(mcsp.getNewUsername()!=null){
                    username = mcsp.getNewUsername();
                }
                else{
                    username = usernamerequest;
                }      
                loggedin=true;
                this.cEventHandler = new ClientEventHandler();
                cEventHandler.setConnectionToServer(this);
                //EMUI.println(getUsername(),"Chat tool initialized...");
                initialsetupOfChatTool(m);

            }
            else if (m instanceof MessagePopup){
               System.out.println("ClientSetup4b4B32POPUP");
              
              // final JChatFrame parentComponent = this.cEventHandler.getChatFrame();  
               boolean blockIncomingMessages = false;
            
                
                final MessagePopup mp = (MessagePopup)m;
                final String[] options = mp.getOptions();
                final String question = mp.getQuestion();
                final String title = mp.getTitle();
                final String emailcopy = email; 
                final String usernamecopy = username; 
                final int selection = mp.getSelection();
                 
               
                           long timeOfDisplay = new Date().getTime();
                           String selected;
                           if(options.length>=selection||selection <0){
                                selected = null;
                           }
                           else{
                               selected = options[selection];
                           }   
                           selected =null;
                           
                           int n = JOptionPane.showOptionDialog(null, question, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,5);  
                           long timeOfResponse = new Date().getTime();
                           String popupID = mp.getID();
                           MessagePopupResponseFromClient mpRETURN = new MessagePopupResponseFromClient (popupID,emailcopy, usernamecopy, question, options, title, n,timeOfDisplay,timeOfResponse);
                           sendMessage(mpRETURN);
                
            
                    
                  
          }   
             
             
        }
        
        return true;
    }

    /**
     * Determines which chat tool window is to be used and sets the chat window accordingly
     * @param m
     */
    public void initialsetupOfChatTool(final Message m){
        
     
        
        
      System.err.println(m.toString());
     
     
        if(m instanceof MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight)
        {
           
            final MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight mcspwbyh = (MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight)m;


            JChatFrameMultipleWindowsWithSendButtonWidthByHeight jcfsw;
            jcfsw=null;
            
            try{
                 
                       jcfsw = new JChatFrameMultipleWindowsWithSendButtonWidthByHeight(cEventHandler,mcspwbyh.getNoOfWindows(),mcspwbyh.getMainWindowWidth(),mcspwbyh.getMainWindowHeight(),mcspwbyh.getAlignmentIsVertical(),mcspwbyh.getParticipantHasStatusWindow(),mcspwbyh.getParticipantsTextWindow(),mcspwbyh.getTextEntryWidth(),mcspwbyh.getTextEntryHeight(), mcspwbyh.getMaxCharLength(), mcspwbyh.getWtsdsd());
                       final JChatFrameMultipleWindowsWithSendButtonWidthByHeight jcfsw2 = jcfsw;
                        SwingUtilities.invokeLater(new Runnable(){public void run(){
                     try{    
                      jcfsw2.setResizable(false);
                      }catch (Exception e){
                     e.printStackTrace();   
                }}});
                
                 //jcfsw = new JChatFrameMultipleWindowsWithSendButtonWidthByHeight(cEventHandler,mcspwbyh.getNoOfWindows(),mcspwbyh.getMainWindowWidth(),500,mcspwbyh.getAlignmentIsVertical(),mcspwbyh.getParticipantHasStatusWindow(),mcspwbyh.getParticipantsTextWindow(),mcspwbyh.getTextEntryWidth(),mcspwbyh.getTextEntryHeight(), mcspwbyh.getWtsdsd());


            }catch (Exception e){
                e.printStackTrace();
                System.exit(06);
            }
           
            cEventHandler.setChatFrame(jcfsw);
            

           
            
            
            
        
        
        }
       
           }
    
 
    long cntINCOMING =0;
    Random r = new Random();


    public void eat3000Messages(){
        for(int j=0;j<250;j++){
             try {
                 System.err.println("READING OBJECTS");System.err.flush();
                 Object o =  in.readObject();
                 

             }catch (Exception e){
                 e.printStackTrace();
             }
        }
        
    }

    /**
     * This is the main method of the client which constantly checks for incoming messages from the server.
     * On receiving a message, it extracts the information and calls the appropriate methods in {@link ClientEventHandler}
     * When changing this method, ensure that it retains the capacity to capture exceptions and send them to the server
     * @throws java.lang.Exception
     */
    public void checkForIncomingMessagesLoop() throws Exception{
        boolean gameInProgress = true;
        while (gameInProgress) {
          try{
          //System.out.println("checkingForincomingMessages");
          
          
          
         
             Message  m = (Message) in.readObject();
          
          
         
          
          cntINCOMING++;
          //System.err.println("________________________________________________"+cntINCOMING);
          if (m instanceof MessageChatTextToClient) {
            
            // System.out.println("Appending");
             MessageChatTextToClient mct = (MessageChatTextToClient) m;
             
            
            cEventHandler.appendWithCaretCheck(mct.getMessageID(),      mct.getUsername(), mct.isPrefixUsername()   ,  mct.getText(),mct.getWindowNo(),mct.getStylenumber());
             
            
             //System.out.println("Appending: "+mct.getWindowNo()+mct.getText());
          }
          
           else if (m instanceof MessageChangeClientInterfaceProperties){
              //System.out.println("Receiving MessageStatusLabelDisplayAndBlocked");
              MessageChangeClientInterfaceProperties mccip = (MessageChangeClientInterfaceProperties)m;
              cEventHandler.changeInterfaceProperties(mccip.getUniqueIDGeneratedByServer(), mccip.getInterfaceproperties(),mccip.getValue(),mccip.getValue2(),mccip.getValue3(), mccip.getValue4());
              // System.exit(-444442);
              //cEventHandler.setLabel(msld.getWindowNumber(),msld.getTextToDisplay(),msld.isLabelRed());
              
             
              
          }
           
          else if (m instanceof diet.message.MessageWYSIWYGAppendText){
              //System.out.println("Receiving MessageStatusLabelDisplayAndBlocked");
              MessageWYSIWYGAppendText mwysiwygat = ( MessageWYSIWYGAppendText)m;
              this.cEventHandler.wYSIWYGUpdateDocumentAppend( mwysiwygat.getWindowNumber(), mwysiwygat.getText(), mwysiwygat.getMas(), mwysiwygat.getOriginUsername());
               
             // Interfaceproperties(),mccip.getValue(),mccip.getValue2());
              // System.exit(-444442);
              //cEventHandler.setLabel(msld.getWindowNumber(),msld.getTextToDisplay(),msld.isLabelRed());
              
          }
                   
                   
          
          
          
          
             
          else if(m instanceof MessageTask){
              System.err.println(this.email+" receiving MessageTask "+m.getClass().toString());
              
              if(m instanceof MessageNewMazeGame&&cteh==null){
                 cteh = new ClientMazeGameComms(this,(MessageNewMazeGame)m);
              }
              else if(m instanceof MessageNewTangrams && cteh ==null){
                  System.out.println("client tangram game comms intitialise");
                  cteh = new ClientTangramGameComms(this,(MessageNewTangrams)m);
              }
              
              else if (m instanceof MessageGridImageStimuliSelectionToClient ){
                  MessageGridImageStimuliSelectionToClient mgsstc = (MessageGridImageStimuliSelectionToClient )m;
                  jfgridimagestimuli.changeSelection(mgsstc.getServerID(),mgsstc.getSelections(),mgsstc.getInnerMostColour(),mgsstc.getInnerColour());
              }
               else if (m instanceof MessageGridTextStimuliSelectionToClient ){
                  MessageGridTextStimuliSelectionToClient mgistc = (MessageGridTextStimuliSelectionToClient )m;
                  jfgridtextstimuli.changeAllSelections(mgistc.getServerID(),mgistc.getInnerMostSColors(), mgistc.getInnerColors());
              }
              
               
               
              else{
                  MessageTask mt = (MessageTask)m;
                  try{
                         cteh.processTaskMove(mt);
                  }catch (Exception e){
                         this.sendErrorMessage(e.getMessage());
                  }
              }
              
          }
          else if (m instanceof MessageDisplayNEWWebpage){
              //System.exit(-05);
              MessageDisplayNEWWebpage mdw = (MessageDisplayNEWWebpage)m;
              try{
                  String tx = mdw.getUrl();
                  String tx2 = tx.replaceAll("%%SERVERIPADDRESS%%", this.serverName);
                  System.err.println("TRYING TO DISPLAY-"+tx+"-----------"+tx2);
                  
                  

                  this.wiad.displayNEWWebpage(mdw.getId(), mdw.getHeader(), tx2, mdw.getWidth(), mdw.getHeight(), mdw.isVScrollBar(),mdw.isJProgressBar(), mdw.isForceCourier());
                  //this.wiad.changeJProgressBar(mdw.getId(), "THE TXT IS:" +tx2+"----ANDALSO-"+tx+"----", Color.red, 59);
              }catch(Exception e){
                  e.printStackTrace();
                  //this.sendErrorMessage("ERRORDISPLAYINGNEWWEBPAGE", 0, false, new Vector(),null);
                  this.sendErrorMessage("ERRORDISPLAYINGNEWWEBPAGE");
                  //System.exit(-123456);    
              }
          }
          else if (m instanceof MessageDisplayChangeWebpage){
              MessageDisplayChangeWebpage mdcw = (MessageDisplayChangeWebpage)m;
              try{
                  String tx = mdcw.getUrl();
                  String tx2 = tx.replaceAll("%%SERVERIPADDRESS%%", this.serverName);
                  System.err.println("TRYING TO DISPLAY-"+tx+"-----------"+tx2);
                  //this.wiad.changeJProgressBar(mdcw.getId(), "THE TXT IS:" +tx2+"----ANDALSO-"+tx+"----", Color.black, 59);
                  
                  this.wiad.changeWebpage(mdcw.getId(), tx2, mdcw.newtext,mdcw.getLengthOfTime(),mdcw.appendeverything);
                  
                  
              }catch(Exception e){
                  e.printStackTrace();
              }              
          }
          else if (m instanceof MessageDisplayChangeJProgressBar){
              MessageDisplayChangeJProgressBar mdcpb = (MessageDisplayChangeJProgressBar)m;
              try{
                  String id = mdcpb.getId();
                  if(id.equalsIgnoreCase("CHATFRAME")){
                      this.cEventHandler.getChatFrame().changeJProgressBar(mdcpb.gettext(), mdcpb.getColor(), mdcpb.getValue());
                  }
                  else{
                      this.wiad.changeJProgressBar(mdcpb.getId(), mdcpb.gettext(), mdcpb.getColor(), mdcpb.getValue());
                  }
                  
              }catch(Exception e){
                  e.printStackTrace();
              }
          }
          
          else if (m instanceof MessagePing){
               MessagePing  mpi =(MessagePing)m;
               MessagePong mpo= new MessagePong(this.email,this.username,mpi.timeOnServerOfC,new Date().getTime());
               this.sendMessage(mpo);
          }
          
          
           else if (m instanceof MessageGridImagesStimuliSendSetToClient){
              MessageGridImagesStimuliSendSetToClient mgsstc = (MessageGridImagesStimuliSendSetToClient)m;
              
              if(jfgridimagestimuli==null){
                  jfgridimagestimuli = new JFrameGridImagesStimuli(this,mgsstc.getROWS(),mgsstc.getCOLUMNS(), mgsstc.getWidthHeight());
              }
              if(mgsstc.getSerializableImages()!=null ){
                  if(mgsstc.getSerializableImages().size()>0)  jfgridimagestimuli.setStimuliSet(mgsstc.getServerID(),mgsstc.getSerializableImages());
              }      
          }
           else if (m instanceof MessageGridImagesStimuliChangeImages){
              MessageGridImagesStimuliChangeImages mgsci = (MessageGridImagesStimuliChangeImages)m;
              jfgridimagestimuli.changeImages(mgsci.getServerID(),mgsci.getImageNames());
             
          }
           
           
           else if (m instanceof MessageGridTextStimuliInitialize){
               final ConnectionToServer ctss = this;
               final MessageGridTextStimuliInitialize mgtsi = (MessageGridTextStimuliInitialize)m;  
               SwingUtilities.invokeLater(new Runnable(){
                  public void run(){
                      jfgridtextstimuli = new JFrameGridTextStimuli(ctss,mgtsi.getServerID(),mgtsi.getROWS(),mgtsi.getCOLUMNS(),mgtsi.getTexts(),mgtsi.getInnermostC(),mgtsi.getInnerC()  ,  mgtsi.getWidth(),mgtsi.getHeight());
          
                  }
               });
                }
           
           
          else if (m instanceof MessageGridTextStimuliChangeTexts){
               MessageGridTextStimuliChangeTexts mgtsct = ( MessageGridTextStimuliChangeTexts)m;
               if(jfgridtextstimuli!=null){
                   //System.exit(-54);
                   jfgridtextstimuli.changeTexts(cntINCOMING, mgtsct.getTexts());
               }
               
          }
           
           
          
          
          else if (m instanceof MessageSubliminalStimuliSendSetToClient){
              MessageSubliminalStimuliSendSetToClient mssstc = (MessageSubliminalStimuliSendSetToClient)m;
              
              if(jfsubliminalstimuli==null){
                  jfsubliminalstimuli = new JFrameSubliminalStimuli(this,mssstc.getWidth(),mssstc.getHeight());
              }
              jfsubliminalstimuli.addStimuliSet(mssstc.getSerializableImages());
              //System.exit(-5);
          }
          else if (m instanceof MessageSubliminalStimuliChangeImage){
              MessageSubliminalStimuliChangeImage mssci = (MessageSubliminalStimuliChangeImage)m;
              
              if(jfsubliminalstimuli!=null){
                  jfsubliminalstimuli.displayStimulus(                   
                  mssci.getFixation1time(), mssci.getStimulus1time(),
                                mssci.getBlankscreen1time(),                                
                                mssci.getFixation2time(), mssci.getStimulus2time()
                               ,mssci.getBlankscreen1time(),
                               mssci.getStimulus1ID(),
                                mssci.getStimulus2ID());
                  
                  
                  
              } 
          }
          else if (m instanceof MessageSubliminalStimuliDisplayText){
              
              MessageSubliminalStimuliDisplayText mssdt = (MessageSubliminalStimuliDisplayText)m;
              
              if(jfsubliminalstimuli!=null){
                  jfsubliminalstimuli.displayText(mssdt.getText(),mssdt.getColour(),mssdt.getNameOfBackgroundPanel(),mssdt.getPositionX(),mssdt.getPositionY(), mssdt.getLengthOfTime());
              }
              
          }
          else if (m instanceof MessageStimulusImageDisplayNewJFrame){
              final ConnectionToServer ctsForStatic = this;
              final Message mForStatic= m;
              
              if(this.jfssi==null){ 
                    final MessageStimulusImageDisplayNewJFrame msidj = (MessageStimulusImageDisplayNewJFrame)mForStatic;
                    SwingUtilities.invokeLater(new Runnable(){
                       public void run(){
                           jfssi = new JFrameStimulusSingleImage(ctsForStatic, msidj.width, msidj.height, msidj.buttonsUnderneath);
                           String name = msidj.nameOfImage;
                           boolean isinexperimentresources = msidj.isinexperimentresources;
                           jfssi.displayImage(name, isinexperimentresources);
                       }
                    });
                    
              }
              
          }
          else if (m instanceof diet.message.MessageStimulusImageChangeImage){
              final MessageStimulusImageChangeImage msiciForStatic = (MessageStimulusImageChangeImage)m;
              final String name = msiciForStatic.nameOfImage;
              final long duration = msiciForStatic.duration;
              final boolean isindirectory = msiciForStatic.isindirectory;
              SwingUtilities.invokeLater(new Runnable(){
                       public void run(){
                          if(jfssi!=null)jfssi.displayImage(name, isindirectory,duration);
                         
                       }
              });         
          }
          else if (m instanceof diet.message.MessageStimulusImageEnableButtons){
              final MessageStimulusImageEnableButtons msicebForStatic = (MessageStimulusImageEnableButtons)m;
              //String name = msiciForStatic.nameOfImage;
              //long duration = msiciForStatic.duration;
              SwingUtilities.invokeLater(new Runnable(){
                       public void run(){
                          if(jfssi!=null)jfssi.enableButtons(msicebForStatic.buttonnames, msicebForStatic.enable);
                       }
              });         
          }
          else if (m instanceof diet.message.MessageStimulusImageReplaceWithNewButtons){
              final MessageStimulusImageReplaceWithNewButtons msirwnb = (MessageStimulusImageReplaceWithNewButtons)m;
              //String name = msiciForStatic.nameOfImage;
              //long duration = msiciForStatic.duration;
              SwingUtilities.invokeLater(new Runnable(){
                       public void run(){
                          if(jfssi!=null)jfssi.addNewButtons(msirwnb.buttonnames, msirwnb.enable);
                       }
              });         
          }
          
          
           
          

          else if (m instanceof MessageDisplayCloseWindow){
              MessageDisplayCloseWindow mdcw = (MessageDisplayCloseWindow)m;
              wiad.destroyWebpage(mdcw.getID());
          }
          
         
          else if (m instanceof MessagePopup){
            
            final JChatFrame parentComponent = this.cEventHandler.getChatFrame();  
            boolean blockIncomingMessages = false;
            if(blockIncomingMessages){
            
            }
            else{
                System.out.println("ClientSetup4b4B33");
                final MessagePopup mp = (MessagePopup)m;
                final String[] options = mp.getOptions();
                final String question = mp.getQuestion();
                final String title = mp.getTitle();
                final String emailcopy = email; 
                final String usernamecopy = username; 
                final int selection = mp.getSelection();
                 
                Runnable rr = new Runnable(){
                    public void run(){
                           long timeOfDisplay = new Date().getTime();
                           String selected;
                           if(options.length>=selection||selection <0){
                                selected = null;
                           }
                           else{
                               selected = options[selection];
                           }   
                           selected =null;
                           
                           int n = JOptionPane.showOptionDialog(parentComponent, question, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,5);  
                           long timeOfResponse = new Date().getTime();
                           String popupID = mp.getID();
                           MessagePopupResponseFromClient mpRETURN = new MessagePopupResponseFromClient (popupID,emailcopy, usernamecopy, question, options, title, n,timeOfDisplay,timeOfResponse);
                           sendMessage(mpRETURN);
                    }
                };
                SwingUtilities.invokeLater(rr);
            }
                    
                  
          }  
          else if (m instanceof MessageOpenClientBrowserWebpage){
             try{ 
               String webaddress = ((MessageOpenClientBrowserWebpage)m).getWebaddress();
               OpenInBrowser.openInBrowser(webaddress);
             } catch(Exception e){
                 this.sendErrorMessage(e);
             }
               
          }
          
          else if(m instanceof MessageClientCloseDown){
              try{
                  System.out.println("CLIENT IS TRYING TO CLOSE DOWN");
               
                  gameInProgress=false;
                  System.out.println("CLIENT IS TRYING TO CLOSE DOWN1");
                  if(cteh!=null)cteh.closeDown();
                  System.out.println("CLIENT IS TRYING TO CLOSE DOWN2");
                  this.retryconnection=false;
                  System.out.println("CLIENT IS TRYING TO CLOSE DOWN3");
                  this.cEventHandler.closeDown();
                  System.err.println("CLIENT IS TRYING TO CLOSE DOWN4");
                  this.stop();
                  System.err.println("CLIENT IS TRYING TO CLOSE DOWN5");
                  this.cEventHandler=null;
                  System.err.println("CLIENT IS TRYING TO CLOSE DOWN6");
                  if(this.wiad!=null){
                      wiad.destroyAllWebpages();
                      wiad.displays = null;
                      wiad = null;
                  }
                  System.err.println("CLIENT IS TRYING TO CLOSE DOWN7");
                  out.close();
                  System.err.println("CLIENT IS TRYING TO CLOSE DOWN9");
                  in.close();
                  System.err.println("CLIENT IS TRYING TO CLOSE DOWN10");
                  this.cSocket.close();
                  System.err.println("CLIENT IS TRYING TO CLOSE DOWN11");
                  JEMStarter.pullThePlug();
                  
              }catch(Exception e){
                   this.stop();
                   System.gc();
              }
             
                  System.err.println("Shut down by server...");
                  System.exit(-1);
              
              
          }
          }catch (Exception e){
              this.cEventHandler.appendWithCaretCheck("", "NETWORK ERROR", true,"Trying to reconnect....please don't type anything", 0, "SELF");
              e.printStackTrace();
              String possiblyNullusername = "not yet set";
              String possiblyNullemail = "Not yet set";
              if(this.email!=null)possiblyNullemail = this.email;
              if(this.username!=null)possiblyNullusername = this.username;
              diet.message.MessageErrorFromClient mefc= new MessageErrorFromClient(e.getMessage(),possiblyNullemail,possiblyNullusername);
              System.err.println("Some error in the client chat tool");
              boolean clientISCONNECTED = this.sendDummyMessageToTestConnection_getConnectionISOK();
              this.sendMessage(mefc);     
              gameInProgress=false;
             
             
          }
       }
                 

    }


    
    /**
     * Initializes the client before calling checkForIncomingMessagesLoop()
     */
    public void run(){

      
        
      System.out.println("ClientSetup0...");
      retryconnection = true;

       while (retryconnection){
           
           try{
                System.out.println("ClientSetup1");
                cSocket = new Socket(serverName, this.portNumber);
                System.out.println("ClientSetup2");
                in = new ObjectInputStream(cSocket.getInputStream());
                System.out.println("ClientSetup3");
                out = new ObjectOutputStream(cSocket.getOutputStream());
                System.out.println("ClientSetup4--");
                //EMUI.println("HELLO","ObjectInput and ObjectOutput streams created");
                retryconnection = false;
                System.out.println("ClientSetup5");
            }
             catch (Exception e){
                  e.printStackTrace();
                  System.err.println("Trying to connect");
                  String[] options = {"Reconnect","Cancel"};
                  int n2 =  JOptionPaneTimeOut.showTimeoutDialog(null, "\nThere was some connectivity problem"
                          + "trying to connect to "+serverName
                          + "...it could be\n\n"
               + "- Your internet connection isn't working\n"
               + "- Your firewall doesn't allow outgoing connections\n"
               + "- An error on the server\n"
               + "- The server isn't switched on yet\n\n"

               + "Trying to reconnect every 5 seconds\n\n"
                       + "If this goes on for too long, please choose Cancel"
                       + " and then try and log in again", "Disconnected", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,options, options[0]);
               if(n2==1) {
                   System.exit(-5);
                   //JEMStarter.pullThePlug();
                   return;
               }
               System.err.println(n2);
            }
          }

          try{
                System.out.println("ClientSetup4b");
                
               
                SwingUtilities.invokeAndWait(new Runnable(){public void run(){
                     try{    
                      boolean loginSuccessful = loginToServerAndInitializeChattool(); 
                      }catch (Exception e){
                     e.printStackTrace();   
                }
                }
                });
                
                
                
                
                
                //if(!loginSuccessful)
                
                System.out.println("ClientSetup5");
                while(this!=null){
                  if(SwingUtilities.isEventDispatchThread()){
                      System.err.println("This is running on the event dispatch thread");
                      System.exit(-5222);
                  }
                  checkForAnyLocalJavaVersionErrors();
                  
                  checkForIncomingMessagesLoop();
                  this.cEventHandler.appendWithCaretCheck("", "NETWORK ERROR(2B)", true,"Trying to reconnect....please don't type anything", 0, "SELF");
                  this.tryToReconnectAutomatically();
                }
                System.out.println("ClientSetup62");
                
               }catch (Exception e){
               try{ 
                   e.printStackTrace();
                   System.out.println("ClientSetup7");
                    //retryconnection=false;
                    out.close();
                    in.close();
                    cSocket.close();
                    System.out.println("ClientSetup8");
                    
              }catch(Exception e2){
                 e2.printStackTrace();
                 System.out.println("ClientSetup9");
              }

              





            
        }

        System.err.println("EXITED FROM CLIENT!!");
     
    }

    public void checkForAnyLocalJavaVersionErrors(){
        String version = System.getProperty("java.version");
        System.out.println("VERSION OF JAVA IS: "+version);
        if(version.startsWith("1.1")||version.startsWith("1.2")||version.startsWith("1.3")||version.startsWith("1.4")
                ||version.startsWith("1.5")||version.startsWith("1.6")){
             this.sendErrorMessage(email+": "+username+" is using java version: "+version+"\n");
        }
        if(version.startsWith("1.8")){
           this.sendErrorMessage(email+": "+username+" is using java version: "+version+"\n");
        } 
        //this.sendErrorMessage(email+": "+username+" THERE IS NOTING TO WORRY ABOUT"+"\n");
    }
    
    public void tryToReconnectAutomatically(){
        this.cEventHandler.appendWithCaretCheck("","NETWORK ERRORTRYINGTORECONNECT", true,"Trying to reconnect....please don't type anything", 0, "SELF");
       boolean hasreconnected = false;

       while (!hasreconnected){
           
           try{
               
                Thread.sleep(1000);
                System.out.println("REestablishClientSetup1");
                 cSocket = new Socket(serverName, this.portNumber);
                System.out.println("ReestablishClientSetup2");
                
                
                in = new ObjectInputStream(cSocket.getInputStream());
                System.out.println("REestablishClientSetup3");
                out = new ObjectOutputStream(cSocket.getOutputStream());
                System.out.println("ReestablishClientSetup4--");
                //EMUI.println("HELLO","ObjectInput and ObjectOutput streams created");
                
                System.out.println("ReestablishClientSetup5");
                 this.cEventHandler.appendWithCaretCheck("","MANAGED TO RECONNECT", true,"RECONNECTED TO SERVER.STEP1", 0, "SELF");
                 boolean loginSuccessful = reEstablishloginToServerAndInitializeChattool();
                 this.cEventHandler.appendWithCaretCheck("","MANAGED TO RECONNECT FULLY", true,"PLEASE CONTINUE", 0, "SELF");
                 hasreconnected = true;
            }
             catch (Exception e){
                  e.printStackTrace();
                  
             }
            
       }
        
        
    }
    
    
    
     private boolean reEstablishloginToServerAndInitializeChattool() throws Exception{
       
         
         InetAddress ina = this.cSocket.getInetAddress();
        int inPort = this.cSocket.getPort();
        InetAddress local_ina = this.cSocket.getInetAddress();
        int local_port = this.cSocket.getLocalPort();
        SocketAddress socketAddress = this.cSocket.getLocalSocketAddress();
         
         
        boolean loggedin = false;
         System.out.println("REESTABLISHClientSetup4b1");
        while (!loggedin){
           System.out.println("REESTABLISHClientSetup4b2");
            Message m = (Message)in.readObject();
            if(m instanceof  MessageClientCloseDown){
                System.out.println("CLIENT RECEIVED INSTRUCTION TO CLOSEDOWN");
                System.exit(-5555366);    
            }
            
            
            System.out.println("REESTABLISHClientSetup4b3");
            System.out.println("REESTABLISHNot logged in yet2");
             if (m instanceof MessagePopupClientLogonEmailRequest && this.forceParticipantID){
                 System.out.println("REESTABLISHNot logged in yet3A");
                 out.writeObject(new MessageClientLogon(email,"",ServerClientConsistency.getVersionID(),  ina, inPort,  local_ina, local_port,  socketAddress));
             }

            else if(m instanceof MessagePopupClientLogonEmailRequest){
                System.out.println("REESTABLISHClientSetup4b4A");
                 MessagePopupClientLogonEmailRequest m2 = (MessagePopupClientLogonEmailRequest)m;
                 
                 System.out.println("REESTABLISHSending EmailRequest");
                 out.writeObject(new MessageClientLogon(email,"",ServerClientConsistency.getVersionID(),  ina, inPort,  local_ina, local_port,  socketAddress));
            }
            else if (m instanceof MessagePopupClientLogonUsernameRequest){
                System.out.println("ClientSetup4b4B");
               MessagePopupClientLogonUsernameRequest m2 = (MessagePopupClientLogonUsernameRequest)m;
                        System.out.println("REESTABLISHSending UsernameRequest");
                        out.writeObject(new MessageClientLogon(email,username,ServerClientConsistency.getVersionID(),  ina, inPort,  local_ina, local_port,  socketAddress));;
                

                
            }
            else if (
                     m instanceof MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight
                    || m instanceof MessageClientSetupParametersWithSendButtonAndTextEntryOneTurnAtATime){
               // This IF section should be moved to the initialsetupofchattool() method 
               System.out.println("Logged  in...Received client setup parameters");
                MessageClientSetupParameters mcsp = (MessageClientSetupParameters)m;
                if(mcsp.getNewEmail()!=null){
                    email = mcsp.getNewEmail();
                }
               
                loggedin=true;
                reestablishInitialsetupOfChatTool(m);

            }
        }
        
        return true;
    }
    
    public void  reestablishInitialsetupOfChatTool(Message m){
       
      System.err.println(m.toString());
     
      
        if(m instanceof MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight)
        {
           
            MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight mcspwbyh = (MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight)m;


            
            //cEventHandler.setLabelAndTextEntryEnabled(mcspwbyh.getParticipantsTextWindow(),mcspwbyh.getStatusDisplay(),mcspwbyh.getStatusIsInRed(),mcspwbyh.getParticipantsWindowIsEnabled());
           
            MessageClientSetupSuccessful mcs = new MessageClientSetupSuccessful(email,username);
            //Needs to be set up by server
           // cEventHandler.setLabelAndTextEntryEnabled(mcspwbyh.getParticipantsTextWindow(),"Normal text",false,true);
        }
        

        
        
        
        
        
    }
    
    
    
    public void sendMessage(Message m){
        this.ctsq.addMessage(m);
    }
    


    /**
     * Sends an already constructed message to the server
     * Should only be called by CTSQ
     * @param m
     */
    public void sendMessageFromOutgoingQueue(Message m){
        try{
            out.writeObject(m);       
            out.flush();
            counter++;
            
          if(counter>turnsBetweenResets){
              out.reset();
              counter =0;
          }          
        }catch(Exception e){
          e.printStackTrace();
          System.err.println("Error sending "+e.getStackTrace()+" ");
          
          
          try{
                    this.cEventHandler.appendWithCaretCheck("","NETWORK ERROR", true,"Error sending...trying to reset...please don't type anything...the screen might freeze for a bit", 0, "SELF");
                   System.out.println("ERROR SENDINGMESSAGE_RESETTING1");
                    //retryconnection=false;
                   try{
                    out.close();
                   }catch (Exception e3){
                       e3.printStackTrace();
                   }
                     System.out.println("ERROR SENDINGMESSAGE_RESETTING2");
                     try{
                       in.close();
                     } catch (Exception e4){
                         e4.printStackTrace();
                     } 
                       
                     System.out.println("ERROR SENDINGMESSAGE_RESETTING3");
                    // cSocket.close();
                     System.out.println("ERROR SENDINGMESSAGE_RESETTING4");
                    
              }catch(Exception e2){
                 e2.printStackTrace();
                 System.out.println("ERROR SENDINGMESSAGE_RESETTING6");
              }
          
          
          
          
           //if(Debug.showErrorsForMacIntegration)System.exit(-1);
        }
    }


    public boolean isCONNECTED(){

        if(cSocket.isInputShutdown())return false;
        if(cSocket.isOutputShutdown())return false;
        if(!cSocket.isConnected()) return false;
        if(cSocket.isClosed()) return false;
        return true;
    }


    /**
     * Sends chat text and associated timing information to the server
     * @param text
     * @param startOfTyping
     * @param currentTurnBeingConstructedHasBeenBlocked
     * @param keyPresses All the keypresses associated with the construction of this turn
     */
    public synchronized void sendChatText(String text, long startOfTyping, boolean currentTurnBeingConstructedHasBeenBlocked,Vector keyPresses, Vector clientInterfaceEvents) {
       // priorChatText = null;
        // Debug.printDBG("CLIENTSENDINGCHATTEXTFROM "+this.username+"\n");  
        //if(Debug.debugBLOCKAGE&&text.equalsIgnoreCase("-"))this.eat3000Messages();
      // EMUI.println(getUsername(),"sendchattext: " + text);
     //if(Debug.debugPICTURELOADING)text = "DEBUGGING"+text;
     
      MessageChatTextFromClient msct = new MessageChatTextFromClient(email,username, text,startOfTyping,currentTurnBeingConstructedHasBeenBlocked,keyPresses,clientInterfaceEvents);
      if(diet.debug.Debug.debugtimers){
          msct.saveTime("client.sending");
      }
      
      sendMessage(msct);
     
     
   }

//
    /**
     * Sends chat text and offset information to the server
     * 
     * @param textAtEndOfString
     * @param offset
     */
    public void sendWYSIWYGDocumentHasChangedInsert(String textAtEndOfString,int offset, String contentsOfWindow, boolean controlPressed, long timeout){
       try{
      // EMUI.println(getUsername(),"SendingWYSIWYG1---------------------------------------------------------------");
       MessageWYSIWYGDocumentSyncFromClientInsert mwysfc = new MessageWYSIWYGDocumentSyncFromClientInsert(email,username,textAtEndOfString,offset,0, contentsOfWindow,  controlPressed, timeout );
       //System.out.println("SendingWYSIWYG2");
       sendMessage(mwysfc);
       }catch (Exception e){
        //   EMUI.println(getUsername(),"ERROR SENDING WYSIWYG");
       }
   }

    public boolean sendDummyMessageToTestConnection_getConnectionISOK(){
        MessageDummy md = new MessageDummy(this.email,this.username);
        try{
             out.writeObject(md);
             out.flush();
             counter++;
          if(counter>turnsBetweenResets){
              out.reset();
              counter =0;
          }
        }catch(Exception e){
          e.printStackTrace();
          System.err.println("DUMMY MESSAGE NOT SENT "+e.getStackTrace()+" ");
          return false;
        }
        return true;
    }
    
    
    /**
     * Sends offset and length of text removed from WYSIWYG chat window interface
     * @param offset
     * @param length
     */
    public void sendWYSIWYGDocumentHasChangedRemove(int offset,int length, String textInWindow, String[] priorTurnByOther){
          try{
          MessageWYSIWYGDocumentSyncFromClientRemove mwysfc = new MessageWYSIWYGDocumentSyncFromClientRemove(email,username,offset,length, textInWindow, priorTurnByOther);
          sendMessage(mwysfc);
           //System.out.println("WYSIWYGIN CLEVENTHANDLER2");
          }catch (Exception e){
             this.sendErrorMessage(e);
          }
    }
    
    /**
     * Sends offset and length of chat text inserted in WYSIWG chat window interface
     * @param text
     * @param offset
     * @param length
     */
    public void sendWYSIWYGDocumentHasChangedInsert(String textTyped,int offset,int length, String textInWindow){
          try{
          MessageWYSIWYGDocumentSyncFromClientInsert mwysfc = new MessageWYSIWYGDocumentSyncFromClientInsert(email,username,textTyped,offset,length, textInWindow, false, -1);
          sendMessage(mwysfc); 
          }catch (Exception e){
               this.sendErrorMessage(e);
          }
    }
    
    
   
    
    
    
    
    
   
 
    /**
     * Sends message to server that participant has pressed a key
     * @param kp
     * @param contents
     */
    public void sendClientIsTyping(Keypress kp, String contents){
         System.out.println("SENDING KEYPRESSED");
         MessageKeypressed mkp = new MessageKeypressed(email,username,kp, contents);
         sendMessage(mkp);
    }

    public void sendButtonPresed(String buttonname,long timeOfPress){
        MessageButtonPressFromClient mpbc= new MessageButtonPressFromClient(this.email,this.username,buttonname,timeOfPress);
        sendMessage(mpbc);
    }
    
    
    
    public void sendErrorMessage(Exception e){
      try{
        e.printStackTrace();
        StackTraceElement[] a = e.getStackTrace();
        String error = "";
        for(int i=0;i<a.length;i++){
            StackTraceElement ste = a[i];
            if(ste!=null){
                error = error+ste.toString();
            }
        }   
        String possiblyNullemail = this.email+"";
        String possiblyNullusername = this.username+"";       
        diet.message.MessageErrorFromClient mefc= new MessageErrorFromClient("This is the error message FROM CLIENT: "+e.getMessage()+"\n "+error,e,possiblyNullemail,possiblyNullusername);
        sendMessage(mefc);
      }catch(Exception ee){
          sendErrorMessage("SENDING THE ERROR MESSAGE ACTUALLY LED TO A CRASH!");
      } 
    }
    
    public void sendErrorMessage(String s){
        try{
          String possiblyNullemail = this.email+"";
          String possiblyNullusername = this.username+"";   
          diet.message.MessageErrorFromClient mefc= new MessageErrorFromClient("Error message FROM CLIENT: "+s,null,possiblyNullemail,possiblyNullusername);
          sendMessage(mefc);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
   
    
   
    
    
    /**
     * Returns username associated with client
     * @return user name
     */
    public String getUsername() {
        return username;
    }
    /**
     * Returns String email of client
     * @return email
     */
    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
}
