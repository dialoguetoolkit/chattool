/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils.postprocessing;

import diet.message.MessageClientInterfaceEvent;
import diet.server.ConversationController.ui.CustomDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class WYSIWYGTranscriptLayoutFromClientEvents {
 
     // HashtableWithDefaultvalue htEvents = new HashtableWithDefaultvalue(new Vector());
      
    
    public void callFromInterface(){
        File[] d = CustomDialog.loadDirectories("Choose the folder(s) containing the WYSIWYG chat data you want to turn into a transcript", System.getProperty("user.dir"));
        for(int i=0;i<d.length;i++){
            loadClientInterfaceEventsFromFile(d[i]);
        }
        
        
        
    }
    
    
    
       public Vector loadClientInterfaceEventsFromFile(File f){
           //File directory = new File("C:\\New folder\\gd2\\experimentdatabackups\\2016.FACECOMMS\\2016.InteractionDesignClass\\0023NEEDS TO BE SET\\");
           
           //File directory = new File("C:\\New folder (2)\\DATAANALYSIS\\2017.05.19.FaceCommsInteractionDesignClass\\0038FaceComms2016WYSIWYGDyadic_InteractionDesignClass");
           
          //File directory = new File("C:\\New folder (2)\\DATAANALYSIS\\2017.05.19.FaceCommsInteractionDesignClass\\001RAWDATA\\0040PROCESSING.STEP1");
           
         // File directory = new File("C:\\New folder (3)\\DATAANALYSIS\\2019.05.FaceComms.SocialMediaClass\\0055FaceComms2016WYSIWYGDyadic_InteractionDesignClass");
          // File directory =  new File("C:\\New folder (3)\\DATAANALYSIS\\2017.05.19.FaceCommsInteractionDesignClass\\001RAWDATA\\0041FaceComms2016WYSIWYGDyadic_InteractionDesignClass");
         //File directory = new File("C:\\github3\\chattool\\data\\saved experimental data\\0490Dyadic_WYSIWYGInterface");
         
        // directory = new File("C:\\Users\\LX1C\\Desktop\\DBG");
        
        File directory = f;
          
           WYSIWYGReconstructingTurnsFromClientEvents1 wrt1 = new WYSIWYGReconstructingTurnsFromClientEvents1(directory);
           WYSIWYGTranscriptLayout wtl = new WYSIWYGTranscriptLayout();
           
           
           try{
              FileInputStream fin = new FileInputStream(new File(directory,"clientinterfaceeventsserialized.obj"));
              ObjectInputStream ois = new ObjectInputStream(fin);
              boolean doLoop = true;
              while(doLoop){
                
                  Object o=ois.readObject();
                  if(o==null){
                      doLoop=false;
                      break;
                  }
                  MessageClientInterfaceEvent mcie = ( MessageClientInterfaceEvent)o;
                 // System.err.println(o.getClass().getName());
                  
                  String sID = mcie.getEmail();   
                  String sUsername = mcie.getUsername();
                  
                  //This does a lot of heavy-lifting. It creates a whole new set, prefixed with PROC
                  wrt1.processClientEvent(sID,sUsername, mcie);
                 
                  
                  
              }
              
           }catch(Exception e){
               //e.printStackTrace();
           }
          wtl.processDirectory(directory);
          CustomDialog.showDialog("Generated transcript for "+directory.getName()+" Press OK to continue");
          return null;
       }
    
       
       
       
    
       public static void main(String[] args){
            WYSIWYGTranscriptLayoutFromClientEvents wtl = new WYSIWYGTranscriptLayoutFromClientEvents ();
            wtl.loadClientInterfaceEventsFromFile(null);
       }
    
}
