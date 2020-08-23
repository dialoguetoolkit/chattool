package diet.message;
import java.io.Serializable;

public class MessagePopup extends Message implements Serializable {

    private String popupIDNeededForFeedBackToServer ="";
    private String question;
    private String[] options;
    private String title;
    int selection;
   

    public MessagePopup(String popupIDNeededForFeedBackBackToServer, String senderID, String username, String question, String[] options, String title, int selection) {
        super(senderID,username);
        this.popupIDNeededForFeedBackToServer=popupIDNeededForFeedBackBackToServer;
        this.question = question;
        this.options=options;
        this.title = title;
        this.selection = selection;
    }
    
    public String getID(){
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

    
    
}
