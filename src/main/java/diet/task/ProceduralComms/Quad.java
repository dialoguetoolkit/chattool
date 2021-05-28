/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.ProceduralComms;

import diet.server.Conversation;
import diet.server.ConversationController.Telegram_Dyadic_PROCOMM;
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
    
      public Quad(Telegram_Dyadic_PROCOMM cC,TelegramParticipant p1, TelegramParticipant p2, TelegramParticipant p3,TelegramParticipant p4){
          this.cC=cC;
          this.p1=p1;
          this.p2=p2;
          this.p3=p3;
          this.p4=p4;
          initialConnection();
          constructUI();
      }
    
      public boolean isParticipantInQuad(TelegramParticipant tp){
          if(tp==p1)return true;
          if(tp==p2)return true;
          if(tp==p3)return true;
          if(tp==p4)return true;
          return false;
      }
      
      public void evaluate (TelegramParticipant sender, TelegramMessageFromClient tmfc) {
           if(pctg1.pA==sender || pctg1.pB==sender) pctg1.evaluate(sender, tmfc);
           else if (pctg2.pA==sender || pctg2.pB==sender) pctg2.evaluate(sender, tmfc);
           else{
               Conversation.saveErr("This shouldn`t happen! Can`t find participant to evaluate");
           }
      }
      
      
      
      
      
      public JPanel getUI(){
          return jpui;
      }
     
      private void initialConnection(){
           System.err.println("Creating PCTG");
           pctg1 = new PCTaskTG(cC,p1,p2,true,true,false);
           pctg2 = new PCTaskTG(cC,p3,p4,false,false,false);
           
      }
      
      private JPanel constructUI(){
          if(SwingUtilities.isEventDispatchThread()) {
                 JPanel jp = new JPanel();
                 jp.setLayout(new BorderLayout());
                 jp.add(pctg1.getUI(), BorderLayout.WEST);
                 jp.add(pctg2.getUI(), BorderLayout.EAST);
                 jpui=jp;
                 return jpui;
          }
          try{
          JPanel retval = null;
          SwingUtilities.invokeAndWait(new Runnable(){
               public void run(){
                   JPanel jp = new JPanel();
                    jp.setLayout(new BorderLayout());
                    jp.add(pctg1.getUI(), BorderLayout.WEST);
                    jp.add(pctg2.getUI(), BorderLayout.EAST);
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
                    jp.add(pctg1.getUI(), BorderLayout.WEST);
                    jp.add(pctg2.getUI(), BorderLayout.EAST);
                    jpui=jp;
          }
          
          return jpui;
      }
      
      public void startExperiment(){
          
      }
    
}
