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
public class Pair {
    
      Telegram_Dyadic_PROCOMM cC;
      TelegramParticipant pA;
      TelegramParticipant pB;
      
      PCTaskTG pctg;// = new PCTaskTG(cC,p1,p2,true,true,false);
     
      JPanel jpui;
     
      int swapState =0;
      
    
      public Pair(Telegram_Dyadic_PROCOMM cC,TelegramParticipant tpA,TelegramParticipant tpB, int swapState, boolean startPractice){
          this.cC=cC;
          this.swapState=swapState;
          this.pA=tpA;
          this.pB=tpB;
          if(startPractice){
              startPRACTICE();
          }
          else{
              startEXPERIMENT();
              startTIMER();
          }
      }
    
      public boolean isParticipantInPair(TelegramParticipant tp){
          if(tp==pA)return true;
          if(tp==pB)return true;
         
          return false;
      }
      
      public synchronized void evaluate (TelegramParticipant sender, TelegramMessageFromClient tmfc) {
          if(pctg!=null){
              if(pctg.pA==sender || pctg.pB==sender) {
                  pctg.evaluate(sender, tmfc);
                  return;
              }
          }
          
          Conversation.saveErr("This shouldn`t happen! Can`t find participant to evaluate"); 
      }
      
      
      public synchronized void swapWITHIN(){
   
            swapState=swapState+1;
            String partnername = "p"+(swapState);
            pctg = new PCTaskTG(cC,pA,pB,partnername,false,true,true,false);
           
            
            constructUI();
      }
      
        
      
      
      
      
      public synchronized void startPRACTICE(){
             String partnername = "p"+swapState;
             pctg = new PCTaskTG(cC,pA,pB,partnername,true,false,false,false);
             
             constructUI();
      }
      
      
      public synchronized void startEXPERIMENT(){
             String partnername = "p"+swapState;
             pctg = new PCTaskTG(cC,pA,pB,partnername,false,false,false,false);
           
             constructUI();
      }
      
      public synchronized void startTIMER(){
          if(pctg!=null)pctg.startTimer();
         
      }
       public synchronized void pauseTIMER(){
          if(pctg!=null)pctg.pauseTimer();
          
      }
      
      
      public JPanel getUI(){
          return jpui;
      }
      
      
     
     
      
      private JPanel constructUI(){
          if(SwingUtilities.isEventDispatchThread()) {
                 JPanel jp = new JPanel();
                 jp.setLayout(new BorderLayout());
                 if(pctg!=null)jp.add(pctg.getUI(), BorderLayout.WEST);
                
                 jpui=jp;
                 return jpui;
          }
          try{
          JPanel retval = null;
          SwingUtilities.invokeAndWait(new Runnable(){
               public void run(){
                   JPanel jp = new JPanel();
                    jp.setLayout(new BorderLayout());
                    if(pctg!=null)jp.add(pctg.getUI(), BorderLayout.WEST);
                    
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
                    if(pctg!=null)jp.add(pctg.getUI(), BorderLayout.WEST);
                    
                    jpui=jp;
          }
          
          return jpui;
      }
      
      
    
      
     
      
      
}
