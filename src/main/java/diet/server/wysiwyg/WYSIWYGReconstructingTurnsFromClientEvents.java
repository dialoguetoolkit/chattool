/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.wysiwyg;

import diet.attribval.AttribVal;
import diet.client.ClientInterfaceEvents.ClientInterfaceEvent;
import diet.client.ClientInterfaceEvents.ClientInterfaceEventStringPrettifier;
import diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker;
import diet.message.MessageClientInterfaceEvent;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.Participant;
import diet.utils.HashtableWithDefaultvalue;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class WYSIWYGReconstructingTurnsFromClientEvents {

    DefaultConversationController cC;
    HashtableWithDefaultvalue htwdv = new HashtableWithDefaultvalue(new Vector());  
    
    
    public WYSIWYGReconstructingTurnsFromClientEvents(DefaultConversationController cC) {
        this.cC=cC;
    }
    
    int counter =0;
    int savecounter=0;
    public synchronized void processClientEvent(Participant sender, MessageClientInterfaceEvent mcieCURRENT){
        
       
       
        if(!(mcieCURRENT.getClientInterfaceEvent().getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself) || mcieCURRENT.getClientInterfaceEvent().getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother))){
            //System.err.println("RETURNING BECAUSE NONCHTEXT)");
            return;
        }
        try{
           saveAppendedStringLTRForParticipant(sender,  mcieCURRENT);
        }catch(Exception e){
            e.printStackTrace();
        }
        // Debug.showDebug2("\n"); Debug.showDebug2("\n");
        // diet.debug.Debug.showDebug2(sender.getUsername()+"\n");
         //diet.debug.Debug.showDebug2(mcieCURRENT.getClientInterfaceEvent().getAllAttribValuesAsStringForDebug());
        
        
        ClientInterfaceEvent cieCURRENT = mcieCURRENT.getClientInterfaceEvent();        
        Vector interfaceEvents = (Vector)this.htwdv.getObject(sender);
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
            ((Vector)this.htwdv.getObject(sender)).addElement(mcieCURRENT);
            return;
        }
        else if(ciePREVIOUS.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)){
            if(cieCURRENT.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)){
                ((Vector)this.htwdv.getObject(sender)).addElement(mcieCURRENT);
                return;
            }
             else if(cieCURRENT.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
               // System.err.println("+++++++++++++++SAVING1"+"+++++"+cieCURRENT.getType()+"+++++++"+cieCURRENT);
                //Debug.showDebug2("SAVING1 for user: "+sender.getUsername() +"\n");
                 this.saveDataForParticipant(sender,interfaceEvents, true);
                 Vector vNEW = new Vector();
                 vNEW.addElement(mcieCURRENT);
                 this.htwdv.putObject(sender, vNEW);
                 return;
             }
        }
        else if(ciePREVIOUS.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
            if(cieCURRENT.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
                ((Vector)this.htwdv.getObject(sender)).addElement(mcieCURRENT);
                return;
            }
             else if(cieCURRENT.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)){
                //System.err.println("+++++++++++++++SAVING2");
                // Debug.showDebug2("SAVING2 for user: "+sender.getUsername() +"\n");
                 this.saveDataForParticipant(sender,interfaceEvents, false);
                 Vector vNEW = new Vector();
                 vNEW.addElement(mcieCURRENT);
                 this.htwdv.putObject(sender, vNEW);
                 return;
             }
        }
        
        
        
        
   
       
    }
    
    boolean selfHasAlreadySavedClientEvent = false;
    HashtableWithDefaultvalue htOthersAlreadySaved = new HashtableWithDefaultvalue(new Vector());
    HashtableWithDefaultvalue htNumberOfEventsRecorded = new HashtableWithDefaultvalue((long)0);
    HashtableWithDefaultvalue htMostRecentEvent = new HashtableWithDefaultvalue(new Date().getTime());
    
    
    private void saveAppendedStringLTRForParticipant(Participant sender, MessageClientInterfaceEvent mcieCURRENT){
            String filenameSELF =  "wysiwyg_cie_"+sender.getParticipantID()+"_"+sender.getUsername()+"_s_.txt";
            long numberOfEventsRecorded = (long)this.htNumberOfEventsRecorded.getObject(sender);
            if(!this.selfHasAlreadySavedClientEvent){  // this doesn't make sense...it is looking ut up for self...but who is self? Doesn't this duplicate functions from below?
                
                System.err.println("CREATING_CIE_"+sender.getUsername()+"..."+mcieCURRENT.getClientInterfaceEvent().getType() );
                String prepend ="";
                for(int i=0;i<numberOfEventsRecorded;i++){
                    prepend = prepend +DefaultConversationController.sett.recordeddata_CSVSeparator;
                }
                cC.c.convIO.writeToTextFileCreatingIfNecessary(filenameSELF, prepend);
                this.selfHasAlreadySavedClientEvent=true;
            }  
        
        if( mcieCURRENT.getClientInterfaceEvent().getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)){        
            String avText = (String)mcieCURRENT.getClientInterfaceEvent().getAttribVal("text").getVal();
            cC.c.convIO.writeToTextFileCreatingIfNecessary(filenameSELF, avText+DefaultConversationController.sett.recordeddata_CSVSeparator);
            
            //updating the other files 
            Vector thoseAlreadySaved = (Vector)htOthersAlreadySaved.getObject(sender); 
            for(int i=0;i<thoseAlreadySaved.size();i++){
                String other = (String)thoseAlreadySaved.elementAt(i);
                String filenameOTHER = "wysiwyg_cie_"+sender.getParticipantID()+"_"+sender.getUsername()+"_o_"+other+".txt"; 
                cC.c.convIO.writeToTextFileCreatingIfNecessary(filenameOTHER, DefaultConversationController.sett.recordeddata_CSVSeparator);
            }
                
      }
       else if  ( mcieCURRENT.getClientInterfaceEvent().getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
                
                cC.c.convIO.writeToTextFileCreatingIfNecessary(filenameSELF, DefaultConversationController.sett.recordeddata_CSVSeparator);
                String avText = (String)mcieCURRENT.getClientInterfaceEvent().getAttribVal("text").getVal();
                String otherUsername = (String)mcieCURRENT.getClientInterfaceEvent().getAttribVal("otherusername").getVal();
                
                
                Vector thoseAlreadySaved = (Vector)htOthersAlreadySaved.getObject(sender); 
                boolean found = false;
                for(int i=0;i<thoseAlreadySaved.size();i++){
                    
                    String other = (String)thoseAlreadySaved.elementAt(i);
                    String filenameOTHER = "wysiwyg_cie_"+sender.getParticipantID()+"_"+sender.getUsername()+"_o_"+other+".txt"; 
                    if(otherUsername.equalsIgnoreCase(other)){
                        cC.c.convIO.writeToTextFileCreatingIfNecessary(filenameOTHER, avText+DefaultConversationController.sett.recordeddata_CSVSeparator);
                        found=true;
                    }
                    else{
                        cC.c.convIO.writeToTextFileCreatingIfNecessary(filenameOTHER, DefaultConversationController.sett.recordeddata_CSVSeparator);    
                       
                    }
                    
                }
                if(!found){
                    
                    String prepend ="";
                    for(int i=0;i<numberOfEventsRecorded;i++){
                        prepend = prepend +DefaultConversationController.sett.recordeddata_CSVSeparator;
                    }
                    String filenameOTHER = "wysiwyg_cie_"+sender.getParticipantID()+"_"+sender.getUsername()+"_o_"+otherUsername+".txt";
                    cC.c.convIO.writeToTextFileCreatingIfNecessary(filenameOTHER, prepend);
                    cC.c.convIO.writeToTextFileCreatingIfNecessary(filenameOTHER, avText+DefaultConversationController.sett.recordeddata_CSVSeparator);
                    thoseAlreadySaved.addElement(otherUsername);
                    this.htOthersAlreadySaved.putObject(sender, thoseAlreadySaved);
                }
        //         long noOfEvents = (long) htNumberOfEventsRecorded.getObject(sender); noOfEvents++;htNumberOfEventsRecorded.putObject(sender, (long)noOfEvents);    
        
       
       }
        long noOfEvents = (long) htNumberOfEventsRecorded.getObject(sender); 
        noOfEvents++;
        htNumberOfEventsRecorded.putObject(sender, (long)noOfEvents);
        
        
        long mostRecentEvent = (long)this.htMostRecentEvent.getObject(sender);
        String filenameTIME = "wysiwyg_cie_"+sender.getParticipantID()+"_"+sender.getUsername()+"_t_"+".txt";
        if(noOfEvents==1){
            cC.c.convIO.writeToTextFileCreatingIfNecessary(filenameTIME, DefaultConversationController.sett.recordeddata_CSVSeparator);
        }
        long timeSincePrevious = mcieCURRENT.getClientInterfaceEvent().getClientTimeOfDisplay()-mostRecentEvent;
        cC.c.convIO.writeToTextFileCreatingIfNecessary(filenameTIME,   timeSincePrevious+DefaultConversationController.sett.recordeddata_CSVSeparator);
        htMostRecentEvent.putObject(sender, mcieCURRENT.getClientInterfaceEvent().getClientTimeOfDisplay());
      
          
       
    }
            
            
    
    
    
    private void saveDataForParticipant(Participant p, Vector v, boolean turnproducedbyself){
        if(v.size()==0)return;
        savecounter++;
        //System.err.println("++++++++++++++++++++SAVECOUNTER"+this.savecounter);
        
        
        MessageClientInterfaceEvent mcieFIRST = (MessageClientInterfaceEvent)v.elementAt(0);
        MessageClientInterfaceEvent mcieLAST = (MessageClientInterfaceEvent)v.lastElement();
        String textAppended = "";
        
        MessageClientInterfaceEvent mcieFIRSTAPPEND = null;
        MessageClientInterfaceEvent mcieLASTAPPEND = null;
        
        Vector vClientInterfaceEvents = new Vector();
        for(int i=0;i<v.size();i++){
            MessageClientInterfaceEvent mcie = (MessageClientInterfaceEvent)v.elementAt(i);
            ClientInterfaceEvent cie = mcie.getClientInterfaceEvent();
            vClientInterfaceEvents.addElement(cie);
            if(cie.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself) ||  cie.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
                if(mcieFIRSTAPPEND==null ) mcieFIRSTAPPEND=mcie;             
                mcieLASTAPPEND = mcie;
                AttribVal avText = mcie.getClientInterfaceEvent().getAttribVal("text");
                textAppended = textAppended+ avText.getValAsString();
            }
             if(cie.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)) {
                  if(!turnproducedbyself) cC.c.saveErrorLog("A turn produced by other ....apparently contains text produced by self - this is a bug! Participant is: "+p.getParticipantID());
             }
            if ( cie.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
                if(turnproducedbyself) cC.c.saveErrorLog("A turn produced by self ....apparently contains text produced by other - this is a bug! Participant is: "+p.getParticipantID());
            }
            
            
        }
        AttribVal av0 = new AttribVal("SubdialogueID", cC.pp.getSubdialogueID(p));
        AttribVal av1 = null;
        if(turnproducedbyself) av1 = new AttribVal("TurnType","SELF");
        else av1 = new AttribVal("TurnType","OTHER");
    
        AttribVal av2 = new AttribVal("TimestampOnClient_FirstEvent", mcieFIRST.getClientInterfaceEvent().getClientTimeOfDisplay() );
        AttribVal av3 = new AttribVal("TimestampOnClient_LastEvent", mcieLAST.getClientInterfaceEvent().getClientTimeOfDisplay() );
        AttribVal av4 = new AttribVal("TimestampOnServer_ReceiptLastEvent", mcieLAST.getTimeOfReceipt().getTime() );
        AttribVal av5 = new AttribVal("TimestampOnClient_FirstAppend",mcieFIRSTAPPEND.getClientInterfaceEvent().getClientTimeOfDisplay() );
        AttribVal av6 = new AttribVal("TimestampOnClient_LastAppend", mcieLASTAPPEND.getClientInterfaceEvent().getClientTimeOfDisplay() );
        AttribVal av7 = new AttribVal("TimestampOnServer_ReceiptLastAppend", mcieLASTAPPEND.getTimeOfSending().getTime() );
        
        AttribVal av8 = new AttribVal("Text", textAppended );
        AttribVal av9 = new AttribVal("Recipients", cC.pp.getRecipientsAsString(p));
        
        
        AttribVal av10 = new AttribVal("PrettifierTiming", ClientInterfaceEventStringPrettifier.getTextFormulationProcessRepresentation(vClientInterfaceEvents));
        AttribVal av11 = new AttribVal("PrettifierLogtiming", ClientInterfaceEventStringPrettifier.getTextFormulationProcessRepresentationLOGARHYTHMICSPACE(vClientInterfaceEvents));
        
        
        Vector attribVals = new Vector();
        attribVals.addElement(av0);
        attribVals.addElement(av1);
        attribVals.addElement(av2);
        attribVals.addElement(av3);
        attribVals.addElement(av4);
        attribVals.addElement(av5);
        attribVals.addElement(av6);
        attribVals.addElement(av7);
        attribVals.addElement(av8);
        attribVals.addElement(av9);
        attribVals.addElement(av10);
        attribVals.addElement(av11);
        
        Vector avADD = cC.getAdditionalInformationForParticipant(p);
        for(int i=0;i<avADD.size();i++){
            attribVals.addElement(avADD.elementAt(i));
            AttribVal av = (AttribVal) avADD.elementAt(i); 
        }
        
        String filename = "wysiwyg_cie_turn_"+p.getParticipantID()+"_"+p.getUsername()+".txt";
        
        
        
        cC.c.convIO.writeToSpreadsheetFileWithAutoHeaderCreatingIfNecessary(filename, attribVals);
    }
    
    
    
   
    
}
