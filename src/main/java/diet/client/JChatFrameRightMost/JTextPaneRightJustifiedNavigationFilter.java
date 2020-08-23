/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client.JChatFrameRightMost;

import diet.client.*;
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
public class JTextPaneRightJustifiedNavigationFilter extends NavigationFilter{

    JTextPane jtp;
    JChatFrame jcf;
    
    
    public JTextPaneRightJustifiedNavigationFilter(JTextPane jtp, JChatFrame jcf ) {
       this.jtp=jtp;
       this.jcf=jcf;
    }

    @Override
    public int getNextVisualPositionFrom(JTextComponent jtc, int i, Position.Bias bias, int i1, Position.Bias[] biass) throws BadLocationException {
        
      try{
        System.err.println(jtc.getText()+"...getting next visual position...jtplength"+jtc.getText().length()+" i:"+i+ "....i1"+i1);
        return jtp.getDocument().getLength();
      }catch(Exception e){
          try{  
           if(jcf!=null)jcf.getClientEventHandler().getCts().sendErrorMessage(e);
            if(jcf!=null)jcf.getClientEventHandler().getCts().sendErrorMessage("GUI PROBLEM1...bypassing");
           }catch(Exception ee){
               ee.printStackTrace();
           }
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
          try{  
            if(jcf!=null)jcf.getClientEventHandler().getCts().sendErrorMessage(e);
            if(jcf!=null)jcf.getClientEventHandler().getCts().sendErrorMessage("GUI PROBLEM2...bypassing");
           }catch(Exception ee){
               ee.printStackTrace();
           }
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
           try{  
            if(jcf!=null)jcf.getClientEventHandler().getCts().sendErrorMessage(e);
            if(jcf!=null)jcf.getClientEventHandler().getCts().sendErrorMessage("GUI PROBLEM3...bypassing");
           }catch(Exception ee){
               ee.printStackTrace();
           }
           setDot(fb, i,  bias) ;
      } 
    }
    
    
    
    
    
}
