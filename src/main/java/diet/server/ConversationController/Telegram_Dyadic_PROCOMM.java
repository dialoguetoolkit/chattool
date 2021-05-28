/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.attribval.AttribVal;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.ConversationController.ui.JInterfaceMenuButtonsReceiverInterface;
import diet.server.ConversationController.ui.JInterfaceTenButtons;
import diet.server.Participant;
import diet.task.ProceduralComms.PCTaskTG;
import diet.task.ProceduralComms.Quad;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 *
 * @author LX1C
 */
public class Telegram_Dyadic_PROCOMM extends TelegramController implements JInterfaceMenuButtonsReceiverInterface{

   //
   //Needs to sort through IDs numerically and then assign them
    
    JInterfaceTenButtons jitb = new JInterfaceTenButtons (this, "practice", "experiment", "swap","always send explanation","never send explanation","send explanation once","20","","","");
    
    public Telegram_Dyadic_PROCOMM(Conversation c) {
        super(c);
        
    }

    public Telegram_Dyadic_PROCOMM(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
        
        
    }

    @Override
    public void performActionTriggeredByUI(String s) {
      
        
        if(s.equalsIgnoreCase("swap")){
             
             pctg.kill();
            try{Thread.sleep(5000);}catch(Exception e){e.printStackTrace();}

            // pctg = new PCTaskTG(this,(TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(0), (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(1),true,true,false);
            
            
        }
        else if (s.equalsIgnoreCase("send explanation once")){
            pctg.sendInstructionsOnce();
        }
        
        
        
    }

    
    
    
     Quad q;
    
    
    

   
     public void telegram_participantJoinedConversation(TelegramParticipant p) {
              
        this.generatePinnedMessage(p);
         
        if(c.getParticipants().getAllParticipants().size()==4) {
            
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
               
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
             q = new Quad(this,(TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(0), (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(1),
                     (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(2), (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(3));
             
             JPanel jpui = q.getUI();
             JFrame jf = new JFrame();
             jf.getContentPane().add(jpui);
             jf.pack();
             jf.setVisible(true);
             
        }
       
    }
    
     
     
     
     
     
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
        
        this.generatePinnedMessage(p);
               
       
       if(c.getParticipants().getAllParticipants().size()==4) {
            
           //  pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
               
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
             q = new Quad(this,(TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(0), (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(1),
                     (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(2), (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(3));
             
             JPanel jpui = q.getUI();
             JFrame jf = new JFrame();
             jf.getContentPane().add(jpui);
             jf.pack();
             jf.setVisible(true);
             
        }
       
       
    }
     

    @Override
    public synchronized void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             String text = tmfc.u.getMessage().getText();
             
             
             
             if(text.startsWith("/menu")){
                 generatePinnedMessage(sender);
             }
             else{
                 System.err.println("Attempting to acquire lock on pctasktg");
                 
                 if(q.isParticipantInQuad(sender)) q.evaluate(sender, tmfc);
                 //if(this.pctg!=null) this.pctg.evaluate(sender, tmfc);
                
                 
             }
             
             
        }
        else{
        try{
            Message pm = tmfc.u.getMessage().getPinnedMessage();
            if(pm!=null&&pm.hasText()){
                
            }else{
                 c.telegram_sendInstructionToParticipant_MonospaceFont(sender, "Please only send text");
            }
            
        }catch(Exception e){
               c.telegram_sendInstructionToParticipant_MonospaceFont(sender, "Please only send text");   
               e.printStackTrace();
               Conversation.saveErr(e);
        }
        }        
          

        
    
    }
    
    
    
    
     PCTaskTG pctg;
     public Hashtable htPinnedMessages = new Hashtable();
     public Hashtable<TelegramParticipant,String> htMostRecentPinnedText = new Hashtable();
    
    
    public void generatePinnedMessage(TelegramParticipant p){ 
        if(p==null){
            Conversation.saveErr("Trying to generate pinned message for null participant");
            return;
        }
        Message m = c.telegram_sendInstructionToParticipant_MonospaceFont(p, "Please do not close this message. You will need it in the task");
        c.telegram_sendPinChatMessageToParticipant(p, m);
        htPinnedMessages.put(p, m);      
    }
    
    
    
    
    public void changePinnedMessage(TelegramParticipant p,String text){
        System.err.println("CHANGEPINNEDMESSAGE1"+text);
         if(p==null){
            Conversation.saveErr("Trying to change pinned message for null participant. "+text);
            return;
        }
          String mostRecent = this.htMostRecentPinnedText.get(p);
          System.err.println("CHANGEPINNEDMESSAGE2"+text);

          if(mostRecent!=null ){
              System.err.println("CHANGEPINNEDMESSAGE3"+text);
              if(mostRecent.equals(text))return;
              System.err.println("CHANGEPINNEDMESSAGE4"+text);
              if(mostRecent.equalsIgnoreCase(text)){
                    System.err.println("CHANGEPINNEDMESSAGE5"+text);
                    org.telegram.telegrambots.meta.api.objects.Message m = (org.telegram.telegrambots.meta.api.objects.Message)this.htPinnedMessages.get(p);
                    if(m!=null){
                        System.err.println("CHANGEPINNEDMESSAGE6"+text);
                        c.telegram_sendEditMessageToParticipant(p, m, "processing move");
                        htMostRecentPinnedText.put(p,"--------------------");
                    }

              }
          }
         
           System.err.println("CHANGEPINNEDMESSAGE7"+text);
           org.telegram.telegrambots.meta.api.objects.Message m = (org.telegram.telegrambots.meta.api.objects.Message)this.htPinnedMessages.get(p);
           if(m!=null){
               System.err.println("CHANGEPINNEDMESSAGE8"+text);
               c.telegram_sendEditMessageToParticipant(p, m, text);
               htMostRecentPinnedText.put(p,text);
           }
           System.err.println("CHANGEPINNEDMESSAGE9"+text);
     }
    
    
    
     public void changePinnedMessageOLD(TelegramParticipant p,String text){
          String mostRecent = this.htMostRecentPinnedText.get(p);
          if(mostRecent!=null){
              if(mostRecent.equalsIgnoreCase(text))return;
          }
         
         
           org.telegram.telegrambots.meta.api.objects.Message m = (org.telegram.telegrambots.meta.api.objects.Message)this.htPinnedMessages.get(p);
           if(m!=null){
               c.telegram_sendEditMessageToParticipant(p, m, text);
               htMostRecentPinnedText.put(p,text);
           }
     }

    @Override
    public Vector<AttribVal> getAdditionalInformationForParticipant(Participant p) {
        
        if(this.pctg!=null) return this.pctg.getAdditionalValues(p);
        return super.getAdditionalInformationForParticipant(p);
    }
    
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
   
   
    
   
   
   
   
   
   
}







