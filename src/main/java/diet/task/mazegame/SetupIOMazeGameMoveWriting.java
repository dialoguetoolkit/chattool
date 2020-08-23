/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.Vector;

import diet.message.Message;
import diet.message.MessageTask;
import diet.server.QueueBlockingForRecipient;
import diet.server.conversationhistory.turn.Turn;
import diet.task.mazegame.message.MessageChangeGateStatus;
import diet.task.mazegame.message.MessageCursorUpdate;
import diet.task.mazegame.message.MessageNextMaze_SentAsIndex;

/**
 *
 * @author user
 */
public class SetupIOMazeGameMoveWriting extends Thread {
    Vector messages = new Vector();    
    boolean conversationInProgress = true;
   
    //LinkedBlockingQueue lbq = new LinkedBlockingQueue();
    //Write own queue that has the desired features    
    QueueBlockingForRecipient qbfr = new QueueBlockingForRecipient();
    int msgsWritesSinceLastReset =0;
    File taskMessagesVectorFile;
    File taskMessagesTextFile;
    ObjectOutputStream objOutTaskMessage;
    FileWriter txtOutTaskMessage;
    
    public SetupIOMazeGameMoveWriting(String directory){
       super();
       this.start();
       
       try{
         File taskMessagesVectorFile = new File(directory+File.separator+"mazegamemessages.v");
         File taskMessagesTextFile   = new File(directory+File.separator+"mazegamemessages.txt");
         FileOutputStream fOutStreamv = new FileOutputStream(taskMessagesVectorFile);
         objOutTaskMessage = new ObjectOutputStream(fOutStreamv);   
         txtOutTaskMessage = new FileWriter (taskMessagesTextFile);
       }catch (Exception e){
           System.err.println("COULD NOT SETUP THE MAZEGAME OUTPUT FILES");
       }  
       
    }
    
    
    public void run(){
          while(conversationInProgress){
               Object o = qbfr.getNextBlocking();
               if(o instanceof Message){
                   saveMessageToFile((MessageTask)o);
                   System.out.println("SAVING MESSAGE");
               }
          }
          try{
             txtOutTaskMessage.flush();
             txtOutTaskMessage.close();
             objOutTaskMessage.flush();
             objOutTaskMessage.close();
          }catch (Exception e){
              System.err.println("ERROR SAVING MAZEGAME MESSAGE");
          }    
    }

    
    public void saveMessage(Message m){ 
       qbfr.enqueue(m) ;  
    }
    
    public void saveTurnDEPRECATED(Turn t){
       qbfr.enqueue(t) ;
        
    }
        
    public void finalize(){
         
    }    
    
    private void saveMessageToFile(MessageTask mt){
        
        
        try{
           this.objOutTaskMessage.writeObject(mt);
            this.objOutTaskMessage.flush();
            msgsWritesSinceLastReset++;
            
            String line = "";
            if(mt.getEmail()!=null){
                line = line + mt.getEmail() + "|"; 
            }
             else{
                line = line + "| |";
            }
            if(mt.getUsername()!=null){
                line = line + mt.getUsername() + "|";
            }
             else{
                line = line + "| |";
            }
            if(mt.getMessageClass()!=null){
                line = line + mt.getMessageClass() +"|";
            }
             else{
                line = line + "| |";
            }
            if(mt.getTimeOfReceipt()!=null){
                line = line + mt.getTimeOfReceipt().getTime()+"|";
            }
             else{
                line = line + "| |";
            }
            if(mt instanceof MessageCursorUpdate){
                 MessageCursorUpdate mcu = (MessageCursorUpdate)mt;
                 line = line + mcu.newPos.width+ "|" + mcu.newPos.height+ "|";
                 if(mcu.isAswitch()){
                     line = line + "is a switch"+"|";
                 }
                 else{
                     line = line + "not a switch"+"|";
                 }
            }
            else if(mt instanceof MessageChangeGateStatus){
                 MessageChangeGateStatus mcgs = (MessageChangeGateStatus)mt;
                 if(mcgs.gatesAreOpen()){
                    line = line + "open the gates"+"|"+ mcgs.getRecipient()+"|";
                 }
                 else{
                    line = line +"close the gates"+"|"+mcgs.getRecipient()+"|";   
                 }
                 
            }
            else if(mt instanceof MessageNextMaze_SentAsIndex){
                MessageNextMaze_SentAsIndex mnm = (MessageNextMaze_SentAsIndex)mt;
                line = line + "Move to maze number"+"|"+mnm.getNext()+"|";
            }
            else{
                line = line + "| |";
            
            }
            this.txtOutTaskMessage.write(line+"\n");
            this.txtOutTaskMessage.flush();
            msgsWritesSinceLastReset++;
            if(msgsWritesSinceLastReset>100){  
                 objOutTaskMessage.flush();
                 objOutTaskMessage.reset();
                 msgsWritesSinceLastReset =0;
            }         
      }      
      catch (Exception e){
            try{
               System.err.println("ERROR SAVING MESSAGE IN MAZEGAME: "+mt.getTimeOfReceipt()+mt.getUsername()+mt.getMessageClass()+"  ");
               System.out.println("Stacktrace:");
               e.printStackTrace();
            }catch (Exception e2){
                System.err.println("ERROR SAVING MESSAGE IN MAZEGAME2");
            }
        }
    }
    
    public void closeDown(){
        this.conversationInProgress=false;
    }

}
