/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.wysiwyg;

import diet.message.Message;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.WYSIWYGTube.Content.TubeFakeInsertedText;
import diet.server.Participant;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class WYSIWYGReconstructingTurnsFromDocInserts {

    DefaultConversationController cC;
    //Participant pMostRecent = null;
   //Vector currentTurn = new Vector();
    
    Hashtable htGroup_MostRecentSender = new Hashtable();
    Hashtable htGroup_CurrentTurn = new Hashtable();
    
    
    public WYSIWYGReconstructingTurnsFromDocInserts(DefaultConversationController cC) {
        this.cC=cC;
    }
    
    
    /*  processFakeText (String fakesender, String text)
    
     // checks to see if current turn is by fakesender. 
            If current turn is not by fake sender,
    
    
    
    */
    
    Participant serverP=new Participant(null,"server","server"); //This is a hack
    
    public synchronized void processFakeInsertedText(TubeFakeInsertedText wfit){
        String groupIDOfSender = cC.pp.getSubdialogueID(wfit.apparentSender);  //could be null  
        Participant group_MostRecentSender = null;
        Vector group_currentTurn = new Vector();
        
        if(groupIDOfSender!=null && !groupIDOfSender.equalsIgnoreCase("")){
            Object o = htGroup_MostRecentSender.get(groupIDOfSender);
            if(o!=null){
                group_MostRecentSender=(Participant)o;
            }
            Object o2 = htGroup_CurrentTurn.get(groupIDOfSender);
            if(o2!=null){
                group_currentTurn = (Vector)o2;
            }
        }
        
        if(groupIDOfSender!=null&& !groupIDOfSender.equalsIgnoreCase("") &&  serverP!=group_MostRecentSender && group_MostRecentSender!=null){
            this.saveToToTurnsFile(group_MostRecentSender, group_currentTurn);
            group_currentTurn = new Vector();
            
            MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp = new MessageWYSIWYGDocumentSyncFromClientInsert("server","server",wfit.text,0,0,"", false,0);
            mWYSIWYGkp.setTimeOfReceipt();
            
            group_currentTurn.addElement(mWYSIWYGkp);
            htGroup_CurrentTurn.put(groupIDOfSender, group_currentTurn);
            htGroup_MostRecentSender.put(groupIDOfSender, serverP);      
            System.err.println("WYSIWYG-RECONSTRUCTINGTURNS-TURNCHANGE");
        }
        else if (groupIDOfSender!=null&& !groupIDOfSender.equalsIgnoreCase("") ){
            
            MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp = new MessageWYSIWYGDocumentSyncFromClientInsert("server","server",wfit.text,0,0,"", false,0);
             mWYSIWYGkp.setTimeOfReceipt();
            group_currentTurn.addElement(mWYSIWYGkp);
            htGroup_CurrentTurn.put(groupIDOfSender, group_currentTurn);
            htGroup_MostRecentSender.put(groupIDOfSender, serverP);   
            System.err.println("WYSIWYG-RECONSTRUCTINGTURNS-SAMETURN");
        }
        else{
            Vector v = new Vector();
            MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp = new MessageWYSIWYGDocumentSyncFromClientInsert("server","server",wfit.text,0,0,"", false,0);
           
            v.addElement(mWYSIWYGkp);
            this.saveToToTurnsFile(serverP, v);
            System.err.println("WYSIWYG-RECONSTRUCTINGTURNS-SAVINGEACHCHARACTERSAVINGEACHCHARACTER");
        }
    }
    
    
    
    
    public synchronized void processAppendedDocumentInsert(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
        this.saveToHorizontalFile(sender, mWYSIWYGkp);  //Doesn't do anything...
        String groupIDOfSender = cC.pp.getSubdialogueID(sender);  //could be null  
        Participant group_MostRecentSender = null;
        Vector group_currentTurn = new Vector();
        
        if(groupIDOfSender!=null && !groupIDOfSender.equalsIgnoreCase("")){
            Object o = htGroup_MostRecentSender.get(groupIDOfSender);
            if(o!=null){
                group_MostRecentSender=(Participant)o;
            }
            Object o2 = htGroup_CurrentTurn.get(groupIDOfSender);
            if(o2!=null){
                group_currentTurn = (Vector)o2;
            }
        }
        
        if(groupIDOfSender!=null&& !groupIDOfSender.equalsIgnoreCase("") &&  sender!=group_MostRecentSender && group_MostRecentSender!=null){
            this.saveToToTurnsFile(group_MostRecentSender, group_currentTurn);
            group_currentTurn = new Vector();
            group_currentTurn.addElement(mWYSIWYGkp);
            htGroup_CurrentTurn.put(groupIDOfSender, group_currentTurn);
            htGroup_MostRecentSender.put(groupIDOfSender, sender);      
            System.err.println("WYSIWYG-RECONSTRUCTINGTURNS-TURNCHANGE");
        }
        else if (groupIDOfSender!=null&& !groupIDOfSender.equalsIgnoreCase("") ){
            group_currentTurn.addElement(mWYSIWYGkp);
            htGroup_CurrentTurn.put(groupIDOfSender, group_currentTurn);
            htGroup_MostRecentSender.put(groupIDOfSender, sender);   
            System.err.println("WYSIWYG-RECONSTRUCTINGTURNS-SAMETURN");
        }
        else{
            Vector v = new Vector();
            v.addElement(mWYSIWYGkp);
            this.saveToToTurnsFile(sender, v);
            System.err.println("WYSIWYG-RECONSTRUCTINGTURNS-SAVINGEACHCHARACTERSAVINGEACHCHARACTER");
        }
        
        
        
        
    }
    
    private void saveToHorizontalFile(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp ){
        // String filename = "wysiwyg_server_"+sender.getParticipantID()+"_"+sender.getUsername()+".txt";
        // cC.c.convIO.writeToTextFileCreatingIfNecessary(filename, mWYSIWYGkp.getTextTyped()+);
        //cC.c.convIO.writeToSpreadsheetFileWithAutoHeaderCreatingIfNecessary(filename, attribVals);
    }
    
    private void saveToToTurnsFile(Participant p, Vector v){
        if(v.size()<1)return;
        String text ="";
        String textWithFloorRequests ="";
        
        MessageWYSIWYGDocumentSyncFromClientInsert firstMessage = null;
        int indexOfFirstMessage =0;
        for(int i=0;i<v.size();i++){
             Message m = (Message)v.elementAt(i);
             if(m instanceof MessageWYSIWYGDocumentSyncFromClientInsert){
                 firstMessage = (MessageWYSIWYGDocumentSyncFromClientInsert)m;
                 indexOfFirstMessage=i;
                 break;
             }
  
        }
        if(firstMessage==null)return;
        
        MessageWYSIWYGDocumentSyncFromClientInsert lastMessage = null;
        for(int i=0;i<v.size();i++){
             Message m = (Message)v.elementAt(i);
             if(m instanceof MessageWYSIWYGDocumentSyncFromClientInsert){
                 MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYG = (MessageWYSIWYGDocumentSyncFromClientInsert)m;
                 text = text + mWYSIWYG.getTextTyped();
                 textWithFloorRequests = textWithFloorRequests+ mWYSIWYG.getTextTyped();     
                 lastMessage=mWYSIWYG;
             }
             
  
        }
        long clientTimeOfStart = firstMessage.getTimeOfSending().getTime();
        long clientTimeOfEnd = lastMessage.getTimeOfSending().getTime();
        long serverTimeOfReceipt = lastMessage.getTimeOfReceipt().getTime();
        String subdialogueID = cC.pp.getSubdialogueID(p);
        Vector recipientsNames=cC.pp.getRecipientsNames(p);
        
        
       // AttribVal av = new AttribVal("TextWithFloorRequests",textWithFloorRequests);
        Vector avs = this.cC.getAdditionalInformationForParticipant(p);
        // avs.addElement(av);
        
        text = text.replace("\n", "(((NEWLINE)))");
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns(subdialogueID, "WYSIWYGSERVERTURN", p.getParticipantID(), p.getUsername(), p.getUsername(), clientTimeOfStart, clientTimeOfEnd, serverTimeOfReceipt, recipientsNames,text, avs);
        
        
       // cC.c.getHistory().saveMessageRelayedToOthers(text, clientTimeOfStart, clientTimeOfEnd, serverTimeOfReceipt, text, subdialogueID, textWithFloorRequests, text, recipientsNames, true, avs, recipientsNames, recipientsNames, recipientsNames, true);
  
        String text2 = text.replace("\r", "**NEWLINE**");
        String text3 = text2.replace(System.getProperty("line.separator"), "((((LINESEPARATOR))))");
     /*   
       try{
       cC.c.getHistory().saveMessageRelayedToOthers(
        subdialogueID,
        clientTimeOfStart, 
        clientTimeOfEnd, 
        serverTimeOfReceipt, 
        p.getParticipantID(),
        p.getUsername(), 
        p.getUsername(),
        text3,
        recipientsNames, 
        false, 
        new Vector(), 
        new Vector(),
        new Vector(),
        avs, 
        false);
       }catch(Exception e){
           e.printStackTrace();
       }
       
        */
        
        
        /*
       public synchronized void saveMessageRelayedToOthers(
        String dtype,
        long timeOnClientOfStartTyping, 
        long timeOnClientOfSending, 
        long timeOnServerOfReceipt, 
        String senderID,
        String senderUsername, 
        String apparentSenderUsername,
        String text,
        Vector recipientsNames, 
        boolean wasBlocked, 
        Vector keyPresses, 
        Vector<DocChange> documentUpdates,
        Vector<ClientInterfaceEvent> clientInterfaceEvents,
        Vector<AttribVal> additionalValues, 
        boolean dummy) {
        
       */
    
    
    }
    
    
}
