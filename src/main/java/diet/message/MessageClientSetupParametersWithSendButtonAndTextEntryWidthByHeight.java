package diet.message;
import diet.client.JChatFrameMultipleWindowWithSendButtonWidthByHeight.StyledDocumentStyleSettings;
import java.io.Serializable;


public class MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight extends MessageClientSetupParameters implements Serializable{

    private int windowType;
    private int windowOfOwnText = 0; // Check to see
    private boolean alignmentIsVertical;
    private int mainWindowWidth;
    private int mainWindowHeight;
    private int textEntryWidth;
    private int textEntryHeight;
    private boolean windowIsEnabled;
    private String statusDisplay;
    private boolean statusIsInRed;
    private boolean participantHasStatusWindow;
    private int numberOfWindows;

    private long timeOfLastScroll; // This should be in a message from client to server

    private int maxLengthOfTextEntry =0;

       StyledDocumentStyleSettings wtsdsd;

    public MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight(String servername, String servername2,  int mainWindowWidth,int mainWindowHeight,boolean alignmentIsVertical,int numberOfWindows, int windowOfOwnText,boolean windowIsEnabled, boolean participantHasStatusWindow, String statusDisplay, boolean statusIsInRed, int textEntryWidth, int textEntryHeight, int maxNumberOfChars,  StyledDocumentStyleSettings wtsdsd){
       super (servername,servername2);

       this.setWindowType(getWindowType());
       this.setNumberOfWindows(numberOfWindows);
       
       this.setWindowOfOwnText(windowOfOwnText);
       this.setWindowIsEnabled(windowIsEnabled);
       this.setStatusDisplay(statusDisplay);
       this.setStatusIsInRed(statusIsInRed);
       this.setAlignmentIsVertical(alignmentIsVertical);
       this.setParticipantHasStatusWindow(participantHasStatusWindow);

       this.mainWindowWidth=mainWindowWidth;
       this.mainWindowHeight=mainWindowHeight;
       this.textEntryWidth=textEntryWidth;
       this.textEntryHeight=textEntryHeight;
       this.maxLengthOfTextEntry= maxNumberOfChars;
       this.wtsdsd=wtsdsd;
       
    }

    public int getMaxCharLength(){
        return maxLengthOfTextEntry;
    }
    
    

    public StyledDocumentStyleSettings getWtsdsd() {
        return wtsdsd;
    }

    public int getWindowType(){
        return windowType;
    }
    public int getNoOfWindows(){
        return getNumberOfWindows();
    }
    
    
    public int getParticipantsTextWindow(){
        return getWindowOfOwnText();
    }
    public boolean getParticipantsWindowIsEnabled(){
        return isWindowIsEnabled();
    }
    public String getStatusDisplay(){
        return statusDisplay;
    }
    public boolean getStatusIsInRed(){
        return isStatusIsInRed();
    }
    public boolean getAlignmentIsVertical(){
        return this.isAlignmentIsVertical();
    }
    public boolean getParticipantHasStatusWindow(){
        return isParticipantHasStatusWindow();
    }
    public int getTextEntryWidth(){
        return this.textEntryWidth;
    }

    public int getTextEntryHeight(){
        return this.textEntryHeight;
    }

    public int getMainWindowWidth(){
        return this.mainWindowWidth;
    }

    public int getMainWindowHeight(){
        return this.mainWindowHeight;
    }

    public String getMessageClass(){
    return "ClientSetupParametersWYSIWYG";
}

    public void setWindowType(int windowType) {
        this.windowType = windowType;
    }

    public int getWindowOfOwnText() {
        return windowOfOwnText;
    }

    public void setWindowOfOwnText(int windowOfOwnText) {
        this.windowOfOwnText = windowOfOwnText;
    }

    public boolean isAlignmentIsVertical() {
        return alignmentIsVertical;
    }

    public void setAlignmentIsVertical(boolean alignmentIsVertical) {
        this.alignmentIsVertical = alignmentIsVertical;
    }

    public int getNumberOfWindows() {
        return numberOfWindows;
    }

    public void setNumberOfWindows(int numberOfWindows) {
        this.numberOfWindows = numberOfWindows;
    }

   

    

    public boolean isWindowIsEnabled() {
        return windowIsEnabled;
    }

    public void setWindowIsEnabled(boolean windowIsEnabled) {
        this.windowIsEnabled = windowIsEnabled;
    }

    public void setStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
    }

    public boolean isStatusIsInRed() {
        return statusIsInRed;
    }

    public void setStatusIsInRed(boolean statusIsInRed) {
        this.statusIsInRed = statusIsInRed;
    }

    public boolean isParticipantHasStatusWindow() {
        return participantHasStatusWindow;
    }

    public void setParticipantHasStatusWindow(boolean participantHasStatusWindow) {
        this.participantHasStatusWindow = participantHasStatusWindow;
    }

    

    public long getTimeOfLastScroll() {
        return timeOfLastScroll;
    }

    public void setTimeOfLastScroll(long timeOfLastScroll) {
        this.timeOfLastScroll = timeOfLastScroll;
    }

}
