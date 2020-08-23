package diet.message;

import java.io.Serializable;

public class MessagePopupClientLogonEmailRequest extends Message implements Serializable {

    private String promptText;
    private String optionText;
    private int minLength;

    public MessagePopupClientLogonEmailRequest(String promptText, String optionText,int minLength) {
        super("server","server");
        this.setPromptText(promptText);
        this.setOptionText(optionText);
        this.minLength=minLength;
    }

    public String getPrompt(){
        return getPromptText();
    }

    public String getOptionText(){
        return optionText;
    }
    public String getMessageClass(){
    return "PopupEmailRequest";
}

    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
    public int getMinLength(){
        return this.minLength;
    }
    
}
