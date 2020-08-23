/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils.postprocessing;

import diet.attribval.AttribVal;
import diet.client.ClientInterfaceEvents.ClientInterfaceEvent;
import diet.client.ClientInterfaceEvents.ClientInterfaceEventStringPrettifier;
import diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker;
import diet.message.MessageClientInterfaceEvent;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.Participant;
import diet.server.io.IntelligentSpreadsheetAndHeaderWriter;
import diet.server.io.IntelligentTextFileWriter;
import diet.utils.HashtableWithDefaultvalue;
import java.io.File;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class WYSIWYGReconstructingTurnsFromClientEvents1 {

    //DefaultConversationController cC;
    HashtableWithDefaultvalue htwdv = new HashtableWithDefaultvalue(new Vector());  
    File directory;
    
    public WYSIWYGReconstructingTurnsFromClientEvents1(File directory) {
        //this.cC=cC;
        this.directory=directory;
    }
    
    int counter =0;
    int savecounter=0;
    public synchronized void processClientEvent(String senderID, String senderUsername, MessageClientInterfaceEvent mcieCURRENT){
        
       
       
        if(!(mcieCURRENT.getClientInterfaceEvent().getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself) || mcieCURRENT.getClientInterfaceEvent().getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother))){
            //System.err.println("RETURNING BECAUSE NONCHTEXT)");
            return;
        }
        try{
           saveAppendedStringLTRForParticipant(directory, senderID, senderUsername, mcieCURRENT);
        }catch(Exception e){
            e.printStackTrace();
        }
        // Debug.showDebug2("\n"); Debug.showDebug2("\n");
        // diet.debug.Debug.showDebug2(sender.getUsername()+"\n");
         //diet.debug.Debug.showDebug2(mcieCURRENT.getClientInterfaceEvent().getAllAttribValuesAsStringForDebug());
        
        
        ClientInterfaceEvent cieCURRENT = mcieCURRENT.getClientInterfaceEvent();        
        Vector interfaceEvents = (Vector)this.htwdv.getObject(senderID);
        ClientInterfaceEvent ciePREVIOUS = null;
       
        
        //System.err.println("+++++++++++++++++++++++++++++++++++++++");
        //System.err.println("RECEIVING: "+cieCURRENT.getAllAttribValuesAsStringForDebug());
        
         //System.err.println("+++++++++++++++++++++++++++++++++++++++"+counter);
        
        //Debug.showDebug2("looking for previous by:"+ sender.getUsername() +" \n");
        
        
        for(int ii= interfaceEvents.size()-1;ii>=0;ii--){
             MessageClientInterfaceEvent mciee = (MessageClientInterfaceEvent)interfaceEvents.elementAt(ii);
             if(mciee.getClientInterfaceEvent().getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)||  mciee.getClientInterfaceEvent().getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother) ){
                 ciePREVIOUS = mciee.getClientInterfaceEvent();
               //  Debug.showDebug2("found previous by:"+ mciee.getUsername() +" \n");
               //  Debug.showDebug2(ciePREVIOUS.getAllAttribValuesAsStringForDebug());
                 break;
             }     
        }
        if(ciePREVIOUS==null){
            ((Vector)this.htwdv.getObject(senderID)).addElement(mcieCURRENT);
            return;
        }
        else if(ciePREVIOUS.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)){
            if(cieCURRENT.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)){
                ((Vector)this.htwdv.getObject(senderID)).addElement(mcieCURRENT);
                return;
            }
             else if(cieCURRENT.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
               // System.err.println("+++++++++++++++SAVING1"+"+++++"+cieCURRENT.getType()+"+++++++"+cieCURRENT);
                //Debug.showDebug2("SAVING1 for user: "+sender.getUsername() +"\n");
                 this.saveDataForParticipant(senderID,interfaceEvents, true);
                 Vector vNEW = new Vector();
                 vNEW.addElement(mcieCURRENT);
                 this.htwdv.putObject(senderID, vNEW);
                 return;
             }
        }
        else if(ciePREVIOUS.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
            if(cieCURRENT.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
                ((Vector)this.htwdv.getObject(senderID)).addElement(mcieCURRENT);
                return;
            }
             else if(cieCURRENT.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)){
                //System.err.println("+++++++++++++++SAVING2");
                // Debug.showDebug2("SAVING2 for user: "+sender.getUsername() +"\n");
                 this.saveDataForParticipant(senderID,interfaceEvents, false);
                 Vector vNEW = new Vector();
                 vNEW.addElement(mcieCURRENT);
                 this.htwdv.putObject(senderID, vNEW);
                 return;
             }
        }
        
        
        
        
   
       
    }
    
    boolean selfHasAlreadySavedClientEvent = false;
    HashtableWithDefaultvalue htOthersAlreadySaved = new HashtableWithDefaultvalue(new Vector());
    HashtableWithDefaultvalue htNumberOfEventsRecorded = new HashtableWithDefaultvalue((long)0);
    HashtableWithDefaultvalue htMostRecentEvent = new HashtableWithDefaultvalue(new Date().getTime());
    
    
    private void saveAppendedStringLTRForParticipant(File directory, String senderID, String senderUsername, MessageClientInterfaceEvent mcieCURRENT){
            String filenameSELF =  "POSTPROCwysiwyg_cie_"+senderID+"_"+senderUsername+"_s_.txt";
            long numberOfEventsRecorded = (long)this.htNumberOfEventsRecorded.getObject( senderID);
            if(!this.selfHasAlreadySavedClientEvent){
                String prepend ="";
                for(int i=0;i<numberOfEventsRecorded;i++){
                    prepend = prepend +DefaultConversationController.sett.recordeddata_CSVSeparator;
                }
                this.writeToTextFileCreatingIfNecessary(directory,filenameSELF, prepend);
                this.selfHasAlreadySavedClientEvent=true;
            }  
        
        if( mcieCURRENT.getClientInterfaceEvent().getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)){        
            String avText = (String)mcieCURRENT.getClientInterfaceEvent().getAttribVal("text").getVal();
             this.writeToTextFileCreatingIfNecessary(directory,filenameSELF, avText+DefaultConversationController.sett.recordeddata_CSVSeparator);
            
            //updating the other files 
            Vector thoseAlreadySaved = (Vector)htOthersAlreadySaved.getObject(senderID); 
            for(int i=0;i<thoseAlreadySaved.size();i++){
                String other = (String)thoseAlreadySaved.elementAt(i);
                String filenameOTHER = "POSTPROCwysiwyg_cie_"+senderID+"_"+senderUsername+"_o_"+other+".txt"; 
                 this.writeToTextFileCreatingIfNecessary(directory,filenameOTHER, DefaultConversationController.sett.recordeddata_CSVSeparator);
            }
                
      }
       else if  ( mcieCURRENT.getClientInterfaceEvent().getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
                
                this.writeToTextFileCreatingIfNecessary(directory,filenameSELF, DefaultConversationController.sett.recordeddata_CSVSeparator);
                String avText = (String)mcieCURRENT.getClientInterfaceEvent().getAttribVal("text").getVal();
                String otherUsername = (String)mcieCURRENT.getClientInterfaceEvent().getAttribVal("otherusername").getVal();
                
                
                Vector thoseAlreadySaved = (Vector)htOthersAlreadySaved.getObject(senderID); 
                boolean found = false;
                for(int i=0;i<thoseAlreadySaved.size();i++){
                    
                    String other = (String)thoseAlreadySaved.elementAt(i);
                    String filenameOTHER = "POSTPROCwysiwyg_cie_"+senderID+"_"+senderUsername+"_o_"+other+".txt"; 
                    if(otherUsername.equalsIgnoreCase(other)){
                         this.writeToTextFileCreatingIfNecessary(directory,filenameOTHER, avText+DefaultConversationController.sett.recordeddata_CSVSeparator);
                        found=true;
                    }
                    else{
                         this.writeToTextFileCreatingIfNecessary(directory,filenameOTHER, DefaultConversationController.sett.recordeddata_CSVSeparator);    
                       
                    }
                    
                }
                if(!found){
                    
                    String prepend ="";
                    for(int i=0;i<numberOfEventsRecorded;i++){
                        prepend = prepend +DefaultConversationController.sett.recordeddata_CSVSeparator;
                    }
                    String filenameOTHER = "POSTPROCwysiwyg_cie_"+senderID+"_"+senderUsername+"_o_"+otherUsername+".txt";
                     this.writeToTextFileCreatingIfNecessary(directory,filenameOTHER, prepend);
                     this.writeToTextFileCreatingIfNecessary(directory,filenameOTHER, avText+DefaultConversationController.sett.recordeddata_CSVSeparator);
                    thoseAlreadySaved.addElement(otherUsername);
                    this.htOthersAlreadySaved.putObject(senderID, thoseAlreadySaved);
                }
        //         long noOfEvents = (long) htNumberOfEventsRecorded.getObject(sender); noOfEvents++;htNumberOfEventsRecorded.putObject(sender, (long)noOfEvents);    
        
       
       }
        long noOfEvents = (long) htNumberOfEventsRecorded.getObject(senderID); 
        noOfEvents++;
        htNumberOfEventsRecorded.putObject(senderID, (long)noOfEvents);
        
        
        long mostRecentEvent = (long)this.htMostRecentEvent.getObject(senderID);
        String filenameTIME = "POSTPROCwysiwyg_cie_"+senderID+"_"+senderUsername+"_t_"+".txt";
        if(noOfEvents==1){
             this.writeToTextFileCreatingIfNecessary(directory,filenameTIME, DefaultConversationController.sett.recordeddata_CSVSeparator);
        }
        long timeSincePrevious = mcieCURRENT.getClientInterfaceEvent().getClientTimeOfDisplay()-mostRecentEvent;
         this.writeToTextFileCreatingIfNecessary(directory,filenameTIME,   timeSincePrevious+DefaultConversationController.sett.recordeddata_CSVSeparator);
        htMostRecentEvent.putObject(senderID, mcieCURRENT.getClientInterfaceEvent().getClientTimeOfDisplay());
       
        String filenameWINDOW = "POSTPROCwysiwyg_cie_"+senderID+"_"+senderUsername+"_w_"+".txt";
        if(noOfEvents==1){
             this.writeToTextFileCreatingIfNecessary(directory,filenameTIME, DefaultConversationController.sett.recordeddata_CSVSeparator);
        }
        String windownnumber = (String)mcieCURRENT.getClientInterfaceEvent().getValue("windowno");
        String windowchar = "";
        if(windownnumber.equalsIgnoreCase("1")){
            windowchar="-";
        }
        else if (windownnumber.equalsIgnoreCase("0")){
            windowchar="_";
        }
        else{
            windowchar = "?";
        }
        this.writeToTextFileCreatingIfNecessary(directory,filenameWINDOW,   windowchar+DefaultConversationController.sett.recordeddata_CSVSeparator);
        htMostRecentEvent.putObject(senderID, mcieCURRENT.getClientInterfaceEvent().getClientTimeOfDisplay());
          
       
    }
            
            
    
    
    
    private void saveDataForParticipant(Object o, Vector v, boolean turnproducedbyself){
      
    }
    
    
    
    
    Hashtable htTextFiles = new Hashtable();
     Hashtable htSpreadsheetFiles = new Hashtable();
    
     public void writeToTextFileCreatingIfNecessary(File directory, String filename, String text){         
             IntelligentTextFileWriter itfw = (IntelligentTextFileWriter) this.htTextFiles.get(filename);
             if(itfw==null){
                 itfw = new IntelligentTextFileWriter(new File(directory,filename));
                 this.htTextFiles.put(filename, itfw);
             }          
             itfw.appendText(text);
    }
    
      public void writeToSpreadsheetFileWithAutoHeaderCreatingIfNecessary(File directory, String filename, Vector additionalValues){         
             IntelligentSpreadsheetAndHeaderWriter isfw = (IntelligentSpreadsheetAndHeaderWriter) this.htSpreadsheetFiles.get(filename);
             if(isfw==null){
                 isfw = new IntelligentSpreadsheetAndHeaderWriter(new File(directory,filename));
                 this.htSpreadsheetFiles.put(filename, isfw);
             }          
             isfw. saveAttribVals(additionalValues);
    }
    
   
    
}
