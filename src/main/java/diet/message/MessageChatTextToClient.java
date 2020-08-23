package diet.message;
import diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker;
import java.io.Serializable;
import javax.swing.text.MutableAttributeSet;

public class MessageChatTextToClient extends Message implements Serializable {

  private String text;
  private int windowNumber;
  private int type;
  private boolean prefixUsername;
  private Object styleattributes;
  private String idToConfirm;
  private boolean clearWindowFirst;
  //private String typeToConfirm = ClientInterfaceEventTracker.normalturn;
  
  static long allIDSSOFAR =0;
  
  
  public MessageChatTextToClient(String idToConfirm,   String email,String username, String t, int windowNumber, boolean prefixUsername, MutableAttributeSet styleattributes) {
    super(email,username);
    setText(t);
    this.setWindowNumber(windowNumber);
    this.setPrefixUsername(prefixUsername);
    this.styleattributes=styleattributes;
    this.idToConfirm=idToConfirm;
  }

 
  
  
  public String getMessageID(){
      return this.idToConfirm;
  }
  
  
  public Object getStylenumber(){
      return this.styleattributes;
  }

  public String getText() {
    return text;
  }

  public int getWindowNo(){
      return getWindowNumber();
  }
  public boolean prefixUsername(){
    return isPrefixUsername();
}
  

  public String getMessageClass(){
      return "ChatTextToClient";
  }

    public void setText(String text) {
        this.text = text;
    }

    public int getWindowNumber() {
        return windowNumber;
    }

    public void setWindowNumber(int windowNumber) {
        this.windowNumber = windowNumber;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public boolean isPrefixUsername() {
        return prefixUsername;
    }

    public void setPrefixUsername(boolean prefixUsername) {
        this.prefixUsername = prefixUsername;
    }

}
