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

import diet.task.tangram2D1M.SerialTangram;
public class MessageNewTangramsMatcher extends MessageNewTangrams implements Serializable{
    
    public MessageNewTangramsMatcher(String email, String userName, Vector<SerialTangram> v, String setName, String name)
    {
        super(email, userName, v, setName, name);
    }

}
