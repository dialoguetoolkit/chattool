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
public class WYSIWYGTranscriptLayoutFromClientEvents1 {
 
     // HashtableWithDefaultvalue htEvents = new HashtableWithDefaultvalue(new Vector());
      
    
       public Vector loadClientInterfaceEventsFromFile(File f){
           //File directory = new File("C:\\New folder\\gd2\\experimentdatabackups\\2016.FACECOMMS\\2016.InteractionDesignClass\\0023NEEDS TO BE SET\\");
           
           //File directory = new File("C:\\New folder (2)\\DATAANALYSIS\\2017.05.19.FaceCommsInteractionDesignClass\\0038FaceComms2016WYSIWYGDyadic_InteractionDesignClass");
           
          //File directory = new File("C:\\New folder (2)\\DATAANALYSIS\\2017.05.19.FaceCommsInteractionDesignClass\\001RAWDATA\\0040PROCESSING.STEP1");
           
         // File directory = new File("C:\\New folder (3)\\DATAANALYSIS\\2019.05.FaceComms.SocialMediaClass\\0055FaceComms2016WYSIWYGDyadic_InteractionDesignClass");
          // File directory =  new File("C:\\New folder (3)\\DATAANALYSIS\\2017.05.19.FaceCommsInteractionDesignClass\\001RAWDATA\\0041FaceComms2016WYSIWYGDyadic_InteractionDesignClass");
         File directory = new File("C:\\github3\\chattool\\data\\saved experimental data\\0490Dyadic_WYSIWYGInterface");
          
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
                  wrt1.processClientEvent(sID,sUsername, mcie);
                 
                  
                  
              }
              
           }catch(Exception e){
               e.printStackTrace();
           }
          wtl.processDirectory(directory);
          System.exit(-56);
          return null;
       }
    
       
       
       
    
       public static void main(String[] args){
            WYSIWYGTranscriptLayoutFromClientEvents1 wtl = new WYSIWYGTranscriptLayoutFromClientEvents1 ();
            wtl.loadClientInterfaceEventsFromFile(null);
       }
    
}
