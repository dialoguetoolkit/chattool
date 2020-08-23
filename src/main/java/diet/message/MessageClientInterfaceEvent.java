package diet.message;

import diet.client.ClientInterfaceEvents.ClientInterfaceEvent;
import java.io.Serializable;
import java.util.Date;

import diet.client.DocumentChange.DocChange;

public class MessageClientInterfaceEvent extends Message implements Serializable{

    
    ClientInterfaceEvent ce;
    
    
    
    public MessageClientInterfaceEvent(String email, String username, ClientInterfaceEvent ce) {
        super(email, username);
        this.ce=ce;
    }
 
   
    
   
   public ClientInterfaceEvent getClientInterfaceEvent(){
       return ce;
   }
    
   
   
    

}
