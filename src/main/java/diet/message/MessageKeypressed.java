package diet.message;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Vector;

public class MessageKeypressed extends Message implements Serializable{

    private Keypress keypressed;
    private String contentsOfTextEntryWindow;
    boolean keypressIsEnter = false;

    public MessageKeypressed(String email,String username, Keypress k,Vector v) {
        super(email,username);
        setKeypressed(k);
        
    }
    public MessageKeypressed(String email,String username, Keypress k, String contentsOfWindow) {
        super(email,username);
        setKeypressed(k);
        setContentsOfTextEntryWindow(contentsOfWindow);
    }
     public Keypress getKeypressed(){
          return keypressed;
     }
     public String getMessageClass(){
         return "Keypressed";
     }
     public String getContentsOfTextEntryWindow(){
         return contentsOfTextEntryWindow;
     }

    private void setKeypressed(Keypress keypressed) {
        this.keypressed = keypressed;
        if (keypressed.getKeycode()==  KeyEvent.VK_ENTER) keypressIsEnter = true;
        
    }

    public void setContentsOfTextEntryWindow(String contentsOfTextEntryWindow) {
        this.contentsOfTextEntryWindow = contentsOfTextEntryWindow;
    }
    
    public boolean isENTER(){
        return this.keypressIsEnter;
    }

}
