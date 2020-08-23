/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils.postprocessing;

import diet.message.MessageClientInterfaceEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class Deserializer {
    
    public void deserialize(){
         File f = new File("C:\\New folder (2)\\DATAANALYSIS\\2016.PROCCOMMMETALANGUAGE\\00ORIGINALFOLDER\\analysis\\2016.METALANGUAGE\\1.FIRSTBATCH-YvsN\\0035ProcCommsWYSIWYG.USEME.Y2\\messages.obj");
         String fname = "C:\\New folder (2)\\DATAANALYSIS\\2016.PROCCOMMMETALANGUAGE\\00ORIGINALFOLDER\\analysis\\2016.METALANGUAGE\\1.FIRSTBATCH-YvsN\\0035ProcCommsWYSIWYG.USEME.Y2\\messages.obj";
         try{
            FileInputStream fis = new FileInputStream(fname);
            ObjectInputStream ois = new ObjectInputStream(fis);
            long counter = 0;
            
            Vector vv = new Vector();
            while(2<5){
                Object oo=  ois.readObject();
                vv.addElement(oo);
                oo=  ois.readObject();
                counter = counter+1;
                System.err.println(counter);
                System.err.println(oo.getClass().getName());
                if (oo instanceof MessageClientInterfaceEvent){
                     MessageClientInterfaceEvent mcieoo = (MessageClientInterfaceEvent)oo;
                    // mcieoo.
                }
                
            }
            
            
         } catch (Exception e){
             e.printStackTrace();
         }
         System.exit(-98);
    }
    
    
}
