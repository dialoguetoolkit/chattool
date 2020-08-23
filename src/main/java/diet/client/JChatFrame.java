package diet.client;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import diet.client.DocumentChange.DocInsert;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.experimentmanager.ui.JEMStarter;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;


public class JChatFrame extends JFrame implements WindowListener{

    private boolean textEntryAndSendEnabled = true;
    private Vector textComponents = new Vector();
    private ClientEventHandler clEventHandler;

    //public static final int "Formulate and revise then send" = 0;
    //static public final int WYSIWYG_MULTIPLE_SEPARATE =1;
    //static public final int WYSIWYG_SINGLE =2;
    //static public final int WYSIWYG_SINGLE_KEYPRESS_INTERCEPTED =3;
    
    /**
     * abstract description of all chat tool window instances.
     * 
     * @param clevh
     * @throws java.awt.HeadlessException
     */
    public JChatFrame(ClientEventHandler clevh) throws HeadlessException {
        super("Chat client: "+clevh.getCts().getUsername());
        clEventHandler = clevh;
         this.addWindowListener(this);
         this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
         
         
         
         //TitledBorder titled = new TitledBorder("the");
         //titled.setTitleColor(Color.red);
         //getRootPane().setBorder(titled);    
         
        //getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.LIGHT_GRAY));
         
         
         
        
         //this.se
    }

   
    

    public void setEditable(boolean editable){
        System.err.println("Error with setEditable..should use subclass");
    
    }
    
    
    
    


    public void appendWithCaretCheck(String msgFROMSERVERID, String prefix  ,boolean showPrefix,  String text, int windownumber, Object attributeset){
        System.err.println("Error with appendText..should use subclass");
    }


    
    /**
     *  
     * @return the text entered in the text entry window of the chat window
     */
    public String getTextEnteredInField(){
        return new String("Error in JChatFrame, getTextEnteredInField should be overriden");
    }

    /**
     * Sets the text entered in the text entry window
     * @param s
     */
    public void clearTextEntryField(boolean changeIsInitiatedByClient){
        System.err.println("Error Set TextEntryField should be subclassed");
    }
    
    

    public ClientEventHandler getClientEventHandler(){
        return this.clEventHandler;
    }
    
    

    /**
     * Adds String to the WYSIWYG chat window at the specified position
     * @param windowNumber
     * @param replacementText
     * @param offset
     * @param length
     */
    public void wYSIWYGUpdateDocumentInsert(int windowNumber, String replacementText,int offset,int length){
             System.err.println("Error...setting WYSIWYG Text in superclass");
    }
    
    
    
    
    
    
     public void wYSIWYGUpdateDocumentAppend(int wysiwygWindowNumber, String appendedText, AttributeSet as, String usernameOther) {
          System.err.println("NOT IMPLEMENTED YET");
    }
    
    
     public void  wysiwyg_ChangeFloorStatus(int newStatus, boolean deletePendingInserts, long serverID){
        System.err.println("NOT IMPLEMENTED YET2");
        //System.exit(456);
    }
    
    
    
    
    
    /**
     * Removes String fromo the WYSIWYG chat window at the specified position
     * @param windowNumber
     * @param offset
     * @param length
     */
    public void wYSIWYGUpdateDocumentRemove(int windowNumber,int offset,int length){
             System.err.println("Error...setting WYSIWYG Text in superclass");
    }
    
    /**
     * Changes the position of the cursor and the selected text in the WYSIWYG chat window
     * @param windowNumber
     * @param startPos
     * @param finishPos
     */
    public void wYSIWYGChangeCursorAndSelection(int windowNumber,int startPos,int finishPos){
                 System.err.println("Error...changing WYSIWYG Cursor in superclass");
    }

    /**
     * Determines whether the display of cursor position and text highlighting is permitted in chat window
     * @param windowNumber
     * @param cursorIsDisplayed
     * @param selectionIsDisplayed
     */
    public void wYSIWYGsetCursorAndSelectionAreDisplayedWYSIWYG(int windowNumber, boolean cursorIsDisplayed, boolean selectionIsDisplayed){
        System.err.println("Error...setting WYSIWYG Cursor in superclass");
    }

    /**
     * Relays whether request for conversational floor has been permitted / client has lost floor
     * (e.g. through timeout determined by server) in order to block / allow text entry in the chat window / 
     * @param status
     * @param msg
     */
    public void wWYSIWYGChangeInterceptStatus(int status, String msg){
        System.err.println("Error...setting intercept status in superclass");
    }

    
   
    
    

    

    /**
     * Relays whether request for conversational floor has been permitted / client has lost floor
     * (e.g. through timeout determined by server) in order to block / allow text entry in the chat window / 
     * @param status
     * @param msg
     */
    
    
      public void setTextEntrytext(String s){
          
      }
    
    
    public void changeJProgressBar(final String text,final Color colorForeground, int value){
        
    }
      
      
    
    
    public String getContentsOfWindow(int windownumber){
        return "ERROR: THIS NEEDS TO BE IMPLEMENTED IN SUBCLASS";
    }
    
      
    
    /**
     * Returns the window number of the window in which the participant's own text is displayed
     * @return window number of participant's text
     */
    public int getParticipantsOwnWindow(){
       return 0;
     }

    public JChatFrame(GraphicsConfiguration gc) {
        super(gc);
        this.addWindowListener(this);
    }

    public JChatFrame(String title) throws HeadlessException {
        super(title);
        this.addWindowListener(this);
    }

    public JChatFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
         this.addWindowListener(this);
    }
    
    public void changeInterfaceProperties(String uniqueIDGeneratedByServer, int newInterfaceproperties, Object...value){
        System.err.println("NEEDS TO BE IMPLEMENTED IN SUPERCLASS");
    }
    
    
    
    
    public void closeDown(){
        this.clEventHandler=null;
        JEMStarter.pullThePlug();
        super.dispose();
    }
    
    public void dispose(){
         
       
    }

    @Override
    public void windowOpened(WindowEvent we) {
        
    }

    @Override
    public void windowClosing(WindowEvent we) {
         String result  = CustomDialog.getString("Please enter the password to close the window\nThis is to prevent 'accidental' closing of the window by participants!\n", "");
         if(result==null)return;
         if(result.equalsIgnoreCase("closedown")){
             this.clEventHandler.getCts().sendErrorMessage("THE CLIENT WAS CLOSED DOWN! AT" +(new Date().getTime()));
             System.err.println("THE CLIENT WAS CLOSED DOWN!");
             System.exit(-1112223);
             
         }
    }

    @Override
    public void windowClosed(WindowEvent we) {
        
    }

    @Override
    public void windowIconified(WindowEvent we) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        
    }

    @Override
    public void windowActivated(WindowEvent we) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        
    }
    
    
    
}
