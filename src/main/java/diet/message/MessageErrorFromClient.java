package diet.message;
import java.io.Serializable;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MessageErrorFromClient extends Message implements Serializable{

    String errorMessage;
    Throwable t;
    
    public MessageErrorFromClient(String textDescribingError, String email, String username) {
        super(email, username);
        this.errorMessage=textDescribingError;
    }

    public MessageErrorFromClient(String textDescribingError, Throwable t,String email, String username) {
        super(email, username);
        this.errorMessage=textDescribingError;
        this.t=t;
    }
    
    public String getMessageClass(){
    return "ErrorFromCLient";
}

   public String getErrorMessage(){
       return errorMessage;
   }
   public Throwable getThrowable(){
       return t;
   }
   
}
