/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.stylemanager;


import diet.server.ConversationController.DefaultConversationController;
import diet.server.Participant;
import java.awt.Color;
import java.util.Vector;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Greg
 */
public class StyleManager {

     
     DefaultConversationController cC;
     
     
     public SimpleAttributeSet defaultFONTSETTINGSOTHER1 = new SimpleAttributeSet();
     public SimpleAttributeSet defaultFONTSETTINGSOTHER2 = new SimpleAttributeSet();
     public SimpleAttributeSet defaultFONTSETTINGSSELF = new SimpleAttributeSet();
     public SimpleAttributeSet defaultFONTSETTINGSSERVER = new SimpleAttributeSet();

     
    
     
     
     public StyleManager(DefaultConversationController cC){
         
         //These should all be initialized from the ConversationController
         
         this.cC=cC;
       
         StyleConstants.setFontFamily(defaultFONTSETTINGSSELF, cC.sett.client_font_SELF_fontfamily);
         StyleConstants.setFontSize(defaultFONTSETTINGSSELF, cC.sett.client_font_SELF_size);
         StyleConstants.setBold(defaultFONTSETTINGSSELF, cC.sett.client_font_SELF_isbold);
         StyleConstants.setItalic(defaultFONTSETTINGSSELF, cC.sett.client_font_SELF_isitalic);
         StyleConstants.setForeground(defaultFONTSETTINGSSELF,    new Color(  cC.sett.client_font_SELF_foregroundcolour_rgb[0],cC.sett.client_font_SELF_foregroundcolour_rgb[1],cC.sett.client_font_SELF_foregroundcolour_rgb[2]));
        
         
         
         StyleConstants.setFontFamily(defaultFONTSETTINGSOTHER1, cC.sett.client_font_OTHER1_fontfamily);
         StyleConstants.setFontSize(defaultFONTSETTINGSOTHER1, cC.sett.client_font_OTHER1_size);
         StyleConstants.setBold(defaultFONTSETTINGSOTHER1, cC.sett.client_font_OTHER1_isbold);
         StyleConstants.setItalic(defaultFONTSETTINGSOTHER1, cC.sett.client_font_OTHER1_isitalic);
         StyleConstants.setForeground(defaultFONTSETTINGSOTHER1,     new Color(      cC.sett.client_font_OTHER1_foregroundcolour_rgb[0],cC.sett.client_font_OTHER1_foregroundcolour_rgb[1],cC.sett.client_font_OTHER1_foregroundcolour_rgb[2]   ));
         
         
         StyleConstants.setFontFamily(defaultFONTSETTINGSOTHER2, cC.sett.client_font_OTHER2_fontfamily);
         StyleConstants.setFontSize(defaultFONTSETTINGSOTHER2, cC.sett.client_font_OTHER2_size);
         StyleConstants.setBold(defaultFONTSETTINGSOTHER2, cC.sett.client_font_OTHER2_isbold);
         StyleConstants.setItalic(defaultFONTSETTINGSOTHER2, cC.sett.client_font_OTHER2_isitalic);
         StyleConstants.setForeground(defaultFONTSETTINGSOTHER2,     new Color(      cC.sett.client_font_OTHER2_foregroundcolour_rgb[0],cC.sett.client_font_OTHER2_foregroundcolour_rgb[1],cC.sett.client_font_OTHER2_foregroundcolour_rgb[2]   ));
         
       
         
         StyleConstants.setFontFamily(defaultFONTSETTINGSSERVER, cC.sett.client_font_SERVER_fontfamily);
         StyleConstants.setFontSize(defaultFONTSETTINGSSERVER, cC.sett.client_font_SERVER_fontsize);
         StyleConstants.setBold(defaultFONTSETTINGSSERVER, cC.sett.client_font_SERVER_isbold);
         StyleConstants.setItalic(defaultFONTSETTINGSSERVER, cC.sett.client_font_SERVER_isitalic);
         StyleConstants.setForeground(defaultFONTSETTINGSSERVER, new Color(   cC.sett.client_font_SERVER_foregroundcolour_rgb[0], cC.sett.client_font_SERVER_foregroundcolour_rgb[1], cC.sett.client_font_SERVER_foregroundcolour_rgb[2]   )  );
     }
    
    
    
     
     
     public MutableAttributeSet getStyleForRecipient(Participant sender, Participant recipient){
         //first tries to see if they have been set
        
         ///If the values haven't been set by the experimenter then it figures out a random setting
         Vector senders = cC.pp.getSenders(recipient);
         int indexOfSender = senders.indexOf(sender);
         if(indexOfSender==0) return this.defaultFONTSETTINGSOTHER1;
         if(indexOfSender==1) return this.defaultFONTSETTINGSOTHER2;
         return this.defaultFONTSETTINGSOTHER2;    
     }
     
     public MutableAttributeSet getStyleFoOTHER1(){
         return defaultFONTSETTINGSOTHER1;
     }
     
     public MutableAttributeSet getStyleFoOTHER2(){
         return defaultFONTSETTINGSOTHER1;
     }
     
     
     
     public MutableAttributeSet getStyleForInstructionMessages(Participant recipient){
         return this.defaultFONTSETTINGSSERVER;
     }
     
     public MutableAttributeSet getDefaultStyleForInstructionMessages(){
         return this.defaultFONTSETTINGSSERVER;
     }
     
     public MutableAttributeSet  getStyleForSelf(){
         return this.defaultFONTSETTINGSSELF;
     }
     public int getWindowNumberInWhichParticipantBReceivesTextFromParticipantA_Deprecated_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(Participant senderA, Participant recipientB){
         return 0;
     }
      public int getWindowNumberInWhichParticipantBReceivesTextFromServer_SHOULDBEMOVEDTOATRACKORWINDOWMANAGER(Participant recipientB){
         return 0;
     }
      
     
     
    
     
     
     
     
     
     
     
     
     
     
     
     
     
     
  
     
     
}
