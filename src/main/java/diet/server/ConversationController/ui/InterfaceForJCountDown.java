/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController.ui;

/**
 *
 * @author sre
 */
public interface InterfaceForJCountDown {
    
    public void performUITriggeredEvent(String eventname, int value);
    
    public void performUIJProgressBarDisplayOnClient(int percentage, String text);
    
            
           
}
