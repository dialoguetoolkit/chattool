package diet.message;
import java.io.Serializable;


public class MessageClientSetupSuccessful extends Message implements Serializable{

    public MessageClientSetupSuccessful(String email, String username) {
       super(email,username);
   }

   public String getMessageClass(){
       return "ClientSetupSuccessful";
   }


}
