package diet.message;

import java.io.Serializable;


public class MessageOpenClientBrowserWebpage extends Message implements Serializable {

    
    String webpage;
    
    public MessageOpenClientBrowserWebpage(String wpage) {
        super("server", "server");
        this.webpage=wpage;
    }
    
    
    public String getWebaddress(){
        return webpage;
    }
}

    
    