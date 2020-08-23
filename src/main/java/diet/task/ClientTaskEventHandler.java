/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task;

import diet.client.ConnectionToServer;
import diet.message.MessageTask;

/**
 *
 * @author Greg
 */
public abstract class ClientTaskEventHandler extends Thread{

     public ConnectionToServer cts;
    
    public ClientTaskEventHandler(ConnectionToServer cts)
    {
       this.cts=cts;
    }
    
    public abstract void processTaskMove(MessageTask mt);
            
    public abstract void closeDown();
}
    

