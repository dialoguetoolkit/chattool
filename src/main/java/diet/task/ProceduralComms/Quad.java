/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.ProceduralComms;

import diet.server.Conversation;
import diet.server.ConversationController.Telegram_Dyadic_PROCOMM;
import diet.server.ConversationController.ui.CustomDialog;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author g
 */
public class Quad {
    
      Telegram_Dyadic_PROCOMM cC;
      TelegramParticipant p1;
      TelegramParticipant p2;
      TelegramParticipant p3;
      TelegramParticipant p4;
      PCTaskTG pctg1;// = new PCTaskTG(cC,p1,p2,true,true,false);
      PCTaskTG pctg2;// = new PCTaskTG(cC,p3,p4,true,true,false);
      JPanel jpui;
      int swapState = 0;
      
    
      public Quad(Telegram_Dyadic_PROCOMM cC,TelegramParticipant p1, TelegramParticipant p2, TelegramParticipant p3,TelegramParticipant p4){
          this.cC=cC;
          this.p1=p1;
          this.p2=p2;
          this.p3=p3;
          this.p4=p4;
          startPRACTICE();
      }
    
      public int getSwapState(){
          return this.swapState;
      }
      
      
      public boolean isParticipantInQuad(TelegramParticipant tp){
          if(tp==p1)return true;
          if(tp==p2)return true;
          if(tp==p3)return true;
          if(tp==p4)return true;
          return false;
      }
      
      public synchronized void evaluate (TelegramParticipant sender, TelegramMessageFromClient tmfc) {
          if(pctg1!=null){
              if(pctg1.pA==sender || pctg1.pB==sender) {
                  pctg1.evaluate(sender, tmfc);
                  return;
              }
          }
          if(pctg2!=null){
              if(pctg2.pA==sender || pctg2.pB==sender) {
                  pctg2.evaluate(sender, tmfc);
                  return;
              }
          }
          Conversation.saveErr("This shouldn`t happen! Can`t find participant to evaluate");
          
         
      }
      
      
      public synchronized void swapWITHIN(){
           if(pctg1==null){
               CustomDialog.showDialog("Cannot do swap WITHIN of quad because pctg1 is NULL");
               return;
           }
           if(pctg2==null){
               CustomDialog.showDialog("Cannot do swap WITHIN of quad because pctg2 is NULL");
               return;
           }
          
          
            if(pctg1!=null && pctg1.ispracticestage){
                CustomDialog.showDialog("Cannot do swap WITHIN of quad because one of the pairs is still in practice mode. Please start the experiment first!");
                return;
            }
            if(pctg2!=null && pctg2.ispracticestage){
                CustomDialog.showDialog("Cannot do swap WITHIN of quad because one of the pairs is still in practice mode. Please start the experiment first!");
                return;
            }
          
            this.pctg1.kill();
            this.pctg2.kill();
          
            swapState = swapState+1;
            String partnername = "p"+(swapState+2);
            
            if(swapState % 2 ==1){
                   pctg1 = new PCTaskTG(cC,p1,p3,partnername,false,true,true,false);
                   pctg2 = new PCTaskTG(cC,p2,p4,partnername,false,true,true,false);        
            }
            else{
                   pctg1 = new PCTaskTG(cC,p1,p2,partnername,false,true,true,false);
                   pctg2 = new PCTaskTG(cC,p3,p4,partnername,false,true,true,false);      
            }
            
            constructUI();
      }
      
          public synchronized TelegramParticipant[] swapBETWEEN(){
           if(pctg1!=null && pctg1.ispracticestage){
                CustomDialog.showDialog("Cannot do swap BETWEEN of quad because one of the pairs is still in practice mode. Please start the experiment first!");
                return null;
            }
            if(pctg2!=null && pctg2.ispracticestage){
                CustomDialog.showDialog("Cannot do swap BETWEEN of quad because one of the pairs is still in practice mode. Please start the experiment first!");
                return null;
            }
          
            System.err.println("swapbetween1-STARTING KILL");
           
            this.pctg1.kill();
            
             System.err.println("swapbetween1-ENDING KILL");
             
            System.err.println("swapbetween2-STARTING KILL");
            this.pctg2.kill();
            System.err.println("swapbetween2-ENDING KILL");
           
            swapState = swapState+1;
            String partnername = "p"+(swapState+2);
            
            pctg1 = new PCTaskTG(cC,p1,p4,partnername,false,true,true,false);
           
            pctg2=null;
            
            
          
      
            TelegramParticipant tp2 = p2;
            TelegramParticipant tp3 = p3;
            
            this.p2=null;
            this.p3=null;
            
            constructUI();
            
            
           
            return new TelegramParticipant[]{tp2,tp3};
            
      }
      
      
      
      
      public synchronized void startPRACTICE(){
             String partnername = "p"+(swapState+2);
             pctg1 = new PCTaskTG(cC,p1,p2,partnername,true,false,false,false);
             pctg2 = new PCTaskTG(cC,p3,p4,partnername,true,false,false,false);
             constructUI();
      }
      
      
      public synchronized void startEXPERIMENT(){
             String partnername = "p"+(swapState+2);
             pctg1 = new PCTaskTG(cC,p1,p2,partnername,false,false,false,false);
             pctg2 = new PCTaskTG(cC,p3,p4,partnername,false,false,false,false);
             constructUI();
      }
      
      public synchronized void startTIMER(){
          if(pctg1!=null)pctg1.startTimer();
          if(pctg2!=null)pctg2.startTimer();
      }
       public synchronized void pauseTIMER(){
          if(pctg1!=null)pctg1.pauseTimer();
          if(pctg2!=null)pctg2.pauseTimer();
      }
      
      
      public JPanel getUI(){
          return jpui;
      }
      
      
     
     
      
      private JPanel constructUI(){
          if(SwingUtilities.isEventDispatchThread()) {
                 JPanel jp = new JPanel();
                 jp.setLayout(new BorderLayout());
                 if(pctg1!=null)jp.add(pctg1.getUI(), BorderLayout.WEST);
                 if(pctg2!=null)jp.add(pctg2.getUI(), BorderLayout.EAST);
                 jpui=jp;
                 return jpui;
          }
          try{
          JPanel retval = null;
          SwingUtilities.invokeAndWait(new Runnable(){
               public void run(){
                   JPanel jp = new JPanel();
                    jp.setLayout(new BorderLayout());
                    if(pctg1!=null)jp.add(pctg1.getUI(), BorderLayout.WEST);
                    if(pctg2!=null)jp.add(pctg2.getUI(), BorderLayout.EAST);
                    jpui=jp;
                   
               }
          });
          }catch(Exception e){
              e.printStackTrace();
              Conversation.saveErr(e);
          }
          if(jpui==null){
              Conversation.saveErr("Trying to salvage Quad UI");
              JPanel jp = new JPanel();
                    jp.setLayout(new BorderLayout());
                    if(pctg1!=null)jp.add(pctg1.getUI(), BorderLayout.WEST);
                    if(pctg2!=null)jp.add(pctg2.getUI(), BorderLayout.EAST);
                    jpui=jp;
          }
          
          return jpui;
      }
      
      
    
      
      public PCTaskTG getPCTaskTGForParticipant(TelegramParticipant tp){
         if(pctg1!=null){
             if(pctg1.pA==tp) return pctg1;
             if(pctg1.pB==tp) return pctg1;
         }
         if(pctg2!=null){
             if(pctg2.pA==tp) return pctg2;
             if(pctg2.pB==tp) return pctg2;
         }
         return null;
       }
      
       public TelegramParticipant[] getAllParticipants(){
           TelegramParticipant[] tps = new TelegramParticipant[]{p1,p2,p3,p4};
           return tps;
       }
       public void kill(){
           try{
               pctg1.kill();
           }catch (Exception e){
               e.printStackTrace();
           }
            try{
               pctg2.kill();
           }catch (Exception e){
               e.printStackTrace();
           }
       }
      
}
