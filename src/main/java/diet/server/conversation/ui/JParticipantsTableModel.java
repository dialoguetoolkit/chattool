/*
 * JTurnsListTableModel.java
 *
 * Created on 06 October 2007, 16:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.conversation.ui;

import diet.server.Conversation;
import diet.server.Participant;
import diet.server.ParticipantConnection;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import diet.server.conversationhistory.ConversationHistory;
import diet.tg.TelegramParticipant;
import diet.tg.TelegramParticipantConnection;
import java.util.Date;
import java.util.Vector;
import javax.swing.SwingUtilities;

public class JParticipantsTableModel extends AbstractTableModel {

  private ConversationHistory cH;
  private Conversation c;
  
  TableModel thisTableModel;
  JTable jt;
  UIUpdatingThread uit;
  boolean telegram = false;
  
  
  public JParticipantsTableModel(JTable jt,ConversationHistory cH, boolean telegram) {
     super();
     this.telegram=telegram;
     this.cH=cH;
     this.c=cH.getConversation();
     
     this.jt = jt;
     this.thisTableModel = this;
     uit = new UIUpdatingThread();
     uit.start();
  }
        
    

  public boolean isCellEditable(int x, int y){
    return false;
  }
  
  
   public String getColumnNameTelegram(int column){
       if(column==0){
         return "Participant ID";
     }
     else if(column==1){
         return "Username";
     }
     else if(column ==2){
         return "Telegram ID";
     }
     else if(column ==3){
         return "Login code";
     }
     else if (column==4){
         return "Participant Group";
     }
     else if(column ==5){
         return "No. of messages sent";
     }
     else if(column ==6){
         return "Time since last message";
     }
    else{
         return " ";
     }
   }
  
   
   public Object getValueAtTelegram(int x, int y){
      
    
    try{  
        Participant p = (Participant)c.getParticipants().getAllParticipants().elementAt(x);
        ParticipantConnection pc = p.getConnection();
        TelegramParticipant tp;
        TelegramParticipantConnection tpc;
        tp=null;
        tpc=null;
        try{
            tp = (TelegramParticipant)p;
            tpc = (TelegramParticipantConnection)pc;
        }catch(Exception e){
            e.printStackTrace();
            Conversation.saveErr(e);
        }
        
        //System.err.println("UI: THE SIZE OF PARTICIPANT IS: "+c.getParticipants().getAllParticipants().size());
        
        if(y==0){
            return p.getParticipantID();
        }
        else if(y==1){
            return p.getUsername();
        }
        else if (y==2){
            if(tpc!=null){
                return tpc.telegramID;
            }
            return "";
        }
        else if (y==3){
            if(tpc!=null){
                return tpc.getLogincode();
            }
            return "";
        }
        else if (y==4){
            return c.getController().pp.getSubdialogueID(p);
        }
        else if (y==5){
            return  tpc.getNumberOfTelegramMessagesSent();
        }
        else if (y==6){
             long timeOfLastMsg = tpc.getTimeOfLastTelegrammessage();
             if(timeOfLastMsg==-1)return "not sent yet";
             
             long timeElapsed = new Date().getTime()-timeOfLastMsg;
             long timeElapsedSec = timeElapsed/((long)1000);
             return ""+ timeElapsedSec;
        }
        else{
            return "" ;
                  
        }
        
    }catch (Exception e){
        e.printStackTrace();
        return "UI ERROR";
    }
  }
   
   
   public String getColumnName(int column){
       if(telegram) return getColumnNameTelegram(column);
       return getColumnNameNonTelegram(column);
   }

   public String getColumnNameNonTelegram(int column){
     if(column==0){
         return "Participant ID";
     }
     else if(column==1){
         return "Username";
     }
     else if (column==2){
         return "Participant Group";
     }
     else if(column ==3){
         return "IP Address";
     }
     else if(column ==4){
         return "No. of (re)connections";
     }
     else if(column ==5){
         return "No. of chat messages sent";
     }
     else if(column ==6){
         return "Time since last activity";
     }
     else if(column ==7){
         return "Time since last turn sent";
     }
     else if(column ==8){
         return "Server-client-server roundtrip (msecs)";
     }
     
    else{
         return " ";
     }
   }

   
   
  public Object getValueAt(int x, int y){
      if(telegram)return this.getValueAtTelegram(x, y);
      return this.getValueAtNonTelegram(x, y);
  }
   
   
  public Object getValueAtNonTelegram(int x, int y){
      
    
    try{  
        Participant p = (Participant)c.getParticipants().getAllParticipants().elementAt(x);
        ParticipantConnection pc = p.getConnection();
        
        //System.err.println("UI: THE SIZE OF PARTICIPANT IS: "+c.getParticipants().getAllParticipants().size());
        
        if(y==0){
            return p.getParticipantID();
        }
        if(y==1){
            return p.getUsername();
        }
        if (y==2){
            return c.getController().pp.getSubdialogueID(p);
        }
        else if (y==3){
            return pc.getClientIPAddress();
        }
        else if (y==4){
            return p.getNumberOfConnections();
        }
        else if (y==5){
            return pc.getNumberOfChatMessagesProduced();
        }
        else if (y==6){
             if(pc.isConnected())  return   (((new Date().getTime()- pc.getTimeOfLastMessageSentToServer()))/1000) +" secs";
             return "Not fully connected yet";
        }
        else if (y==7){
             if(pc.getNumberOfChatTurnsReceivedFromClient()>0) return   (((new Date().getTime()- pc.getTimeOfLastChatTextSentToServer()))/1000) +" secs";
             return "Hasn't sent any chat text yet";
        }
        else if (y==8){
             if(pc.isConnected()){
                 return ""+pc.getMostRecentLag(); 
             }
             return "NOT RECEIVED YET";
        }
        else{
            return "" ;
                  
        }
    
    
   
    }catch (Exception e){
        e.printStackTrace();
        return "UI ERROR";
    }
  }

  public Class getColumnClass(int column) {
                 return Object.class;         
      
              
  }
  
  public int getRowCount(){
    try{
     return c.getParticipants().getAllParticipants().size();
    }
    catch (Exception e){
        e.printStackTrace();
    }
    return 0;
  }
  public int getColumnCount(){
      if(telegram)return 7;
      return 9;
  }

  public void updateData(){
       fireTableDataChanged();
       System.err.println("UPDATING THE DTATA2");
       Vector v = c.getParticipants().getAllParticipants();
       for(int i=0;i<v.size();i++){
           Participant p = (Participant)v.elementAt(i);
           System.err.println("SETTING THE DATA2: "+p.getUsername()+" "+p.getNumberOfChatMessagesProduced());
       }
       
       
       
      
  }

  public void updateRowDataForSingleParticipant(Participant p){
      final int index = c.getParticipants().getAllParticipants().indexOf(p); 
      SwingUtilities.invokeLater(new Runnable(){
      public void run(){
            fireTableRowsUpdated(index, index);  
            
         }
      });
      
      
  }
  
  
    private void doLoopUpdatingTimeSinceLastKeypress(){
        while(2<5){
            try{
                Thread.sleep(1000);
                
                SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    for(int i=0;i<c.getParticipants().getAllParticipants().size();i++){
                    fireTableCellUpdated(i, 6);
                    fireTableCellUpdated(i, 7);
                }  
                //System.err.println("UPDATING UI");
               }
               });        
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
  
    
    private class UIUpdatingThread extends Thread{
        
        public void run(){
            while(2<5){
            try{
                Thread.sleep(1000);
                
                SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    for(int i=0;i<c.getParticipants().getAllParticipants().size();i++){
                    fireTableCellUpdated(i, 6);
                    fireTableCellUpdated(i, 7);
                }  
                //System.err.println("UPDATING UI");
               }
               });        
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        }
        
    }
    
    
  
}