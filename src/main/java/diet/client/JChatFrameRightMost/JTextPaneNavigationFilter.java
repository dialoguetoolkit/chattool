/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client.JChatFrameRightMost;

import diet.client.JChatFrameMultipleWindowWithSendButtonWidthByHeight.JChatFrameMultipleWindowsWithSendButtonWidthByHeight;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.NavigationFilter;
import javax.swing.text.Position;

/**
 *
 * @author gj
 */
public class JTextPaneNavigationFilter extends NavigationFilter{

    JTextPane jtp;
    JChatFrameMultipleWindowsWithSendButtonWidthByHeight jcfmpwwsbwbh;
    
    
    public JTextPaneNavigationFilter(JTextPane jtp, JChatFrameMultipleWindowsWithSendButtonWidthByHeight jcfmpwwsbwb ) {
       this.jtp=jtp;
       jcfmpwwsbwbh=jcfmpwwsbwb;
       
    }

    @Override
    public int getNextVisualPositionFrom(JTextComponent jtc, int i, Position.Bias bias, int i1, Position.Bias[] biass) throws BadLocationException {
        
      try{
        System.err.println(jtc.getText()+"...getting next visual position...jtplength"+jtc.getText().length()+" i:"+i+ "....i1"+i1);
        return jtp.getDocument().getLength();
      }catch(Exception e){
          e.printStackTrace();
          jcfmpwwsbwbh.getClientEventHandler().getCts().sendErrorMessage(e);
          jcfmpwwsbwbh.getClientEventHandler().getCts().sendErrorMessage("GUI PROBLEM!");
      }          
      return super.getNextVisualPositionFrom(jtc, i, bias, i1, biass);
        
        //return super.getNextVisualPositionFrom(jtc, i, bias, i1, biass); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public void moveDot(FilterBypass fb, int i, Position.Bias bias) {
      try{
        System.err.println("MOVING THE DOT...to: "+i);
        this.setDot(fb, i, bias); //To change body of generated methods, choose Tools | Templates.
      }catch (Exception e){
          jcfmpwwsbwbh.getClientEventHandler().getCts().sendErrorMessage(e);
          jcfmpwwsbwbh.getClientEventHandler().getCts().sendErrorMessage("GUI PROBLEM2...bypassing");
          super.moveDot(fb,i,bias);
      } 
      
    }

    @Override
    public void setDot(FilterBypass fb, int i, Position.Bias bias) {
        System.err.println("SETTING THE DOT");
         try{
        
             super.setDot(fb, jtp.getDocument().getLength(), Position.Bias.Forward); //To change body of generated methods, choose Tools | Templates.
              }
         catch (Exception e){
           jcfmpwwsbwbh.getClientEventHandler().getCts().sendErrorMessage(e);
           jcfmpwwsbwbh.getClientEventHandler().getCts().sendErrorMessage("GUI PROBLEM3...bypassing");
           setDot(fb, i,  bias) ;
      } 
    }
    
    
    
    
    
}
