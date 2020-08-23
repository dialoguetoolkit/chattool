package diet.message;
import java.io.Serializable;

public class MessageDummyFromServerToShowIsStillAlive extends Message implements Serializable {


    public MessageDummyFromServerToShowIsStillAlive() {
        super("server", "server");

    }

    public String getMessageClass(){
        return "MessageDummy";
    }


}
