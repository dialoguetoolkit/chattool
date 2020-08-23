package diet.message;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;



public class Message  implements Serializable {

  private Date dateOfSending = new Date();
  private Date dateOfReceipt;
  private String email;
  private String username;

  
  public Message(String email, String username) {
      this.email=email;
      this.username=username;
  }

  public void setTimeOfReceipt() {
    dateOfReceipt = new Date();
  }
  
  public void setTimeOfSending() {
    dateOfReceipt = new Date();
  }

  public Date getTimeOfReceipt() {
    return dateOfReceipt;
  }
  public Date getTimeOfSending() {
    return dateOfSending;
  }

  public String getUsername() {
    return username;
  }

  public void overrideSetUsername(String newUserName) {
    this.username=newUserName;
  }

  public String getEmail(){
      return email;
  }
  public String getMessageClass(){
  return "Message";
}

 
  
}
