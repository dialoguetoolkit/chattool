/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;

/**
 *
 * @author Greg
 */
public class MessageDisplayCloseWindow extends Message {

    String id;
    
    public MessageDisplayCloseWindow(String id){
        super("server","server");
        this.id=id;
               
    }
    public String getID(){
        return id;
    }
    
}
