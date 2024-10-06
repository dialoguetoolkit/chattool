/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.tg;

import diet.server.Conversation;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author LX1C
 */
public class TGMESSAGEDELETER extends Thread{
    
    Conversation c;
    Vector<idtimes> v = new Vector();

    public TGMESSAGEDELETER(Conversation c) {
        this.c = c;
        this.start();
    }
            
    public synchronized void run(){
        while(2<5){
            long mingap = 10000;
            Vector<idtimes> thoseToDelete = new Vector();
            
            for(int i=0;i<v.size();i++){
               idtimes idt = (idtimes)v.elementAt(i);
               
               //System.err.println("IDT2");
               if(idt.time<=new Date().getTime()){
                   //System.err.println("IDT2B");
                   thoseToDelete.add(idt);
               }
               else{
                   //System.err.println("IDT3");
                   long gap = (long)idt.time- new Date().getTime();
                   //System.err.println("GAP IS: "+gap);
                   if(gap<0)gap=0;
                   if(gap<mingap) mingap = gap;
               }
            }
   
            for(int i=0;i<thoseToDelete.size();i++){
                c.telegram_DeleteMessage_DoNotCallThisMethodDirectlyFromController(thoseToDelete.elementAt(i).tp, thoseToDelete.elementAt(i).message_id, thoseToDelete.elementAt(i).time);
                v.remove(thoseToDelete.elementAt(i));
                System.err.println("DELETING MESSAGES");
            }
        
            try{  
                if(v.size()==0){
                    wait();
                }
                else{
                    //System.err.println("Waiting for "+mingap);
                    wait(mingap+1);    
                }
                
                
                
            } catch(Exception e){
                e.printStackTrace();
            } 
        }
    } 
    
    public synchronized void add(TelegramParticipant tp,int msgid,   long durationToWait){
        
        idtimes idt = new idtimes();
         idt.tp=tp;
         idt.message_id=msgid;
         idt.time= new Date().getTime()+ durationToWait;
        
         
         for(int i=0;i<this.v.size();i++){
             idtimes idtv = v.elementAt(i);
             if(msgid==idtv.message_id){
                 //There is a match
                 if(idt.time<idtv.time){
                     System.err.println("Deleting - Duplicate Updating: "+ msgid+" from"+ idtv.time       +" to "+ idt.time+ " because it is sooner");
                     idtv.time=idt.time;
                     return;
                 }
                 else{
                     System.err.println("Deleting - Duplicate Don't need to instruct to delete msgid: "+ msgid+ " "+ idt.time+ " because will already be deleted at "+ idtv.time);
                     return;
                 }
             }
         }
        
        
         System.err.println("Deleting - adding: "+ idt.message_id +" "+ idt.time);
         v.add(idt);
         notifyAll();
    }
    
    
    
    
    
    
    
    
    class idtimes{
        TelegramParticipant tp;
        long time;
        int message_id;
    }
    
}
