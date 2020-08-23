/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M.message;

/**
 *
 * @author Arash
 */
import java.io.Serializable;
import java.util.Vector;

import diet.message.MessageTask;
import diet.task.tangram2D1M.SerialTangram;

public class MessageNewTangrams extends MessageTask implements Serializable{
    
    public Vector<SerialTangram> tangrams;
    public String tangramSetName;
    public String nameOfRecipient;
 
    public MessageNewTangrams(String email, String userName, Vector<SerialTangram> tangrams, String tangramSetName, String name)
    {
        super(email,userName);
        this.tangrams=tangrams;
        this.tangramSetName=tangramSetName;
        this.nameOfRecipient=name;
    }
    

}
