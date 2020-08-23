package diet.message;
import java.io.Serializable;

/**
 * Containing class and associated methods for keyevents from the chat tool
 * @author user
 */
public class Keypress implements Serializable{

    private int keycode;
    private long time;

    public Keypress(int keyc, long time) {
        super();
        setKeycode(keyc);
    }
    /**
     * 
     * @return true if keypress is a delete
     */
    public boolean isDel(){
       if(getKeycode()==127||getKeycode()==8){
          return true;
       }
       return false;
    }
    /**
     * 
     * @return keycode of keyevent
     */
    public int getKeycode(){
        return keycode;
    }

    /**
     * 
     * Sets the keycode
     * 
     * @param keycode keycode
     */
    public void setKeycode(int keycode) {
        this.keycode = keycode;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
