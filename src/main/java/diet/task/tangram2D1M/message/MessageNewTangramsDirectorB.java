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
import diet.task.tangram2D1M.TangramSequence;

public class MessageNewTangramsDirectorB extends MessageNewTangrams implements Serializable{
    
    public TangramSequence sequence;//sequence determining directors' complementary target tangrams
    
    public MessageNewTangramsDirectorB(String email, String userName, Vector<SerialTangram> v, String setName, TangramSequence sequence,String name)
    {
        super(email,userName,v,setName,name);
        this.sequence=sequence;
    }

}
