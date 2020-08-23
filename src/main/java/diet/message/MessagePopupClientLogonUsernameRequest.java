package diet.message;

import java.io.Serializable;

public class MessagePopupClientLogonUsernameRequest extends Message implements Serializable {

    private String promptText;
    private String optionText;
    private boolean allowAlternatives = true;

    public MessagePopupClientLogonUsernameRequest(String promptText, String optionText) {
        super("server","server");
        this.setPromptText(promptText);
        this.setOptionText(optionText);
    }
     public MessagePopupClientLogonUsernameRequest(String promptText, String optionText,boolean allowAlternatives) {
        super("server","server");
        this.setPromptText(promptText);
        this.setOptionText(optionText);
        this.allowAlternatives=allowAlternatives;
    }

    public String getPrompt(){
        return getPromptText();
    }

    public String getOptionText(){
        return optionText;
    }
    public String getMessageClass(){
       return "PopupUsername";
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

    public boolean getAllowAlternatives(){
        return this.allowAlternatives;
    }
}
