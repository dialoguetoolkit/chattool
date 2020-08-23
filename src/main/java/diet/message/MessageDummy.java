package diet.message;
import java.io.Serializable;

public class MessageDummy extends Message implements Serializable {


    public MessageDummy(String email, String username) {
        super(email, username);

    }

    public String getMessageClass(){
        return "MessageDummy";
    }


}
