package diet.message;
import java.io.Serializable;

public class MessagePopupResponseFromClient extends Message implements Serializable {

    private String popupIDNeededForFeedBackToServer ="";
    private String question;
    private String[] options;
    private String title;
    int selection;
    public long timeOnClientOfDisplay;
    public long timeOfChoice;

    public MessagePopupResponseFromClient(String popupIDNeededForFeedBackBackToServer, String senderID, String username, String question, String[] options, String title, int selection, long timeOnClientOfDisplay, long timeOnClientTimeOfChoice) {
        super(senderID,username);
        this.popupIDNeededForFeedBackToServer=popupIDNeededForFeedBackBackToServer;
        this.question = question;
        this.options=options;
        this.title = title;
        this.selection = selection;
        this.timeOnClientOfDisplay=timeOnClientOfDisplay;
        this.timeOfChoice=timeOnClientTimeOfChoice;
    }
    
    public String getPopupID(){
        return popupIDNeededForFeedBackToServer;
    }

    public String[] getOptions() {
        return options;
    }

    public String getQuestion() {
        return question;
    }

    public int getSelection() {
        return selection;
    }

    public String getTitle() {
        return title;
    }

    public String getSelectedValue(){
        try{
            return this.options[selection];
        }   catch(Exception e){
            e.printStackTrace();
            return "ERROR:"+e.getMessage();
        }
        
    }
    
    
}
