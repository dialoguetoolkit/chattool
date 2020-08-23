/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.Vector;

import diet.message.Message;
import diet.message.MessageTask;
import diet.server.QueueBlockingForRecipient;
import diet.server.conversationhistory.turn.Turn;
import diet.task.tangram2D1M.message.MessageEndOfCurTangramSet;
import diet.task.tangram2D1M.message.MessageNextTangramPlaced;
import diet.task.tangram2D1M.message.MessageTangramRevealed;

/**
 *
 * @author user
 */
public class SetupIOTangramGameMoveWriting extends Thread {
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
    String tangramSetName;
    
    public SetupIOTangramGameMoveWriting(String directory, String tangramSetName){
       super();
       this.tangramSetName=tangramSetName;
       this.start();
       
       try{
         File taskMessagesVectorFile = new File(directory+File.separator+"tangramgamemessages.v");
         File taskMessagesTextFile   = new File(directory+File.separator+"tangramgamemessages.txt");
         FileOutputStream fOutStreamv = new FileOutputStream(taskMessagesVectorFile);
         objOutTaskMessage = new ObjectOutputStream(fOutStreamv);   
         txtOutTaskMessage = new FileWriter (taskMessagesTextFile);
       }catch (Exception e){
           System.err.println("COULD NOT SETUP THE MAZEGAME OUTPUT FILES");
       }  
       
    }
    
    public void setTangramSetName(String name)
    {
        this.tangramSetName=name;
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
    
    public void saveTurn(Turn t){
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
                line = line + mt.getClass().getSimpleName() +"|";
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
            if(mt instanceof MessageTangramRevealed){
                 MessageTangramRevealed mcu = (MessageTangramRevealed)mt;
                 line = line + this.tangramSetName+"|"+mcu.getTangramName()+ "|" + mcu.getSlotID();
            }
            else if(mt instanceof MessageNextTangramPlaced){
                 MessageNextTangramPlaced ntp = (MessageNextTangramPlaced)mt;
                 line = line + this.tangramSetName+"|"+ ntp.getTangramName()+"|"+ntp.getTargetSlotID();
            }
            else if(mt instanceof MessageEndOfCurTangramSet){
                MessageEndOfCurTangramSet ets = (MessageEndOfCurTangramSet)mt;
                line = line + this.tangramSetName;
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
