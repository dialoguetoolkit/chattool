/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client.JChatFrameRightMost;

import diet.attribval.AttribVal;
import diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker;
import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.Vector;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author gj
 */
public class JTextPaneRightJustifiedDocument extends DefaultStyledDocument {
    
    
   String whitelist = null;
   
    
    
    int maximumLength = 24;
    JTextPaneRightJustified jtprj;
    Object stateHolding = new Object();
    
    int state =0;
    final int nofloor =0;
   
    final int hasfloor =2;
    Vector vPendingInserts = new Vector();
    
    int fontSize = 40;

    public JTextPaneRightJustifiedDocument( JTextPaneRightJustified jtprj, int state) {
         this.jtprj =   jtprj;
         this.state=state;
         //jcfrms = new JChatFrameRightMostState(this);
         StyleContext context = new StyleContext();
         StyledDocument document = new DefaultStyledDocument(context);
         Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
         StyleConstants.setAlignment(style, StyleConstants.ALIGN_RIGHT);
         StyleConstants.setFontSize(style,fontSize);
         style.addAttribute("created", new Date().getTime());
         style.addAttribute("originalforeground", StyleConstants.getForeground(style));
         this.setLogicalStyle(0, style);
        
       
    }
    
    
   
    
    
              
         
    
    
    
    
    public void insertStringFromOther(final String str, final Color otherColor, final String usernameOther) {
         final JTextPaneRightJustifiedDocument thisjtdoc = this;
        SwingUtilities.invokeLater(
                new Runnable(){
                   public void run(){
                        try{
                          thisjtdoc.insertString_PresupposesOnSwingThread(str, otherColor);
                           reportInterfaceEventProducedByOther(str, new Date().getTime(), usernameOther);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                      }       
                })
        ;
        
    }   
    
    
    
    public void insertString_PresupposesOnSwingThread(final String str, final Color otherColor) {
                        SimpleAttributeSet newAttrs = new SimpleAttributeSet();
                        StyleConstants.setForeground( newAttrs, otherColor );
                        StyleConstants.setBackground( newAttrs, Color.WHITE );
                        StyleConstants.setFontSize(newAttrs, fontSize );
                        StyleConstants.setFontFamily(newAttrs, Font.MONOSPACED );
                        StyleConstants.setAlignment(newAttrs, StyleConstants.ALIGN_RIGHT);
                        newAttrs.addAttribute("created", new Date().getTime());
                        newAttrs.addAttribute("originalforeground", StyleConstants.getForeground(newAttrs));
       
                       int characterstodelete=0;
                       if(super.getLength()>maximumLength){
                             characterstodelete = super.getLength()-maximumLength;
                          try{
                             synchronized(this){  
                                 super.remove(0, characterstodelete);
                             }
                             System.err.println("REMOVEDTEXT");
                          }catch(Exception e){
                              e.printStackTrace();
                          }  
                       }
                        String str2 = str.replace("\n", ".");
                         try{
                           synchronized(this){
                               super.insertString(super.getLength(), str, newAttrs);
                           }
                         }catch(Exception e){
                             e.printStackTrace();
                         }
                        
    
    }
    
    
    
    
    
    
    public void insertString(int offs, String str, AttributeSet as) throws BadLocationException {

        if(whitelist!=null){
              if(!whitelist.contains(str)){
                   
                  return;
              }
              else{
                 
              }
              
        }
        
        
         System.err.println("A1 + whitelist:"+whitelist);
        
        if(str.equalsIgnoreCase("\n"))return;
        synchronized(stateHolding){
             System.err.println("A2");
            if(this.state==this.nofloor ){
                 System.err.println("A3");
                Object[] pendingInsert = new Object[]{offs,str,as};   
                 System.err.println("A4");
                vPendingInserts.addElement(pendingInsert);
                 System.err.println("A5");
                
                 
                 System.err.println("A6");
                stateHolding.notify();
                 System.err.println("A7");
                return ;
             }
            else{
               
                this.insertString_PresupposesOnSwingThread(str, Color.BLACK);
                 this.reportInterfaceEventProducedBySelf(str, new Date().getTime()); 
                 String textInWindow = "ERROR"; 
                       try {
                           textInWindow = getText(0, this.getLength());
                       } catch (Exception e){e.printStackTrace();}    
                       jtprj.cleh.wYSIWYGDocumentHasChangedInsert(str, offs,textInWindow , jtprj.isControlPressed, jtprj.fadeouttime);    
                 
                 
               // this.jtprj.cleh.wYSIWYGDocumentHasChanged(str, offs, jtprj.isControlPressed);    //DocInserts are recorded from rightmost (this needs to be fixed in future version)
                
             // need to fix document inserts...make it so that they are from left
                
                stateHolding.notify();    
            }
            
            
        }
       ;
        
       
    }

    
    
    
    public void reportInterfaceEventProducedBySelf( String text, long timeDisplayed){
              AttribVal av1 = new AttribVal("text",text);   
              AttribVal av2 =   new AttribVal("fadeouttime",   jtprj.fadeouttime);
              AttribVal av3 = new AttribVal("windowno",""+this.jtprj.windowNo); 
              this.jtprj.cleh.reportInterfaceEvent(diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker.wysiwyg_appendbyself, timeDisplayed,  av1, av2, av3);     
 
    }
    public void reportInterfaceEventProducedByOther(String text, long timeDisplayed, String usernameOfOther){
              AttribVal av1 = new AttribVal("text",text);   
              AttribVal av2 =   new AttribVal("fadeouttime",   jtprj.fadeouttime);
              AttribVal av3 = new AttribVal("windowno",""+this.jtprj.windowNo);     
              AttribVal av4 = new AttribVal("otherusername",""+usernameOfOther);     
              this.jtprj.cleh.reportInterfaceEvent(diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker.wysiwyg_appendbyother, timeDisplayed  ,av1,  av2, av3, av4);      
        
       
    }
    
    
    
    
    
    
    @Override
    public void remove(int i, int i1) throws BadLocationException {
        //super.remove(i, i1); //To change body of generated methods, choose Tools | Templates.
       
        //System.exit(-56);
    }
    
    
    
    @Override
    public void replace(int i, int i1, String string, AttributeSet as) throws BadLocationException {
        
        if(i!=this.getLength())return;
        
        SimpleAttributeSet newAttrs = new SimpleAttributeSet();
        StyleConstants.setForeground( newAttrs, StyleConstants.getForeground(as) );
        
        
        StyleConstants.setBackground( newAttrs, Color.WHITE );
         StyleConstants.setForeground( newAttrs, Color.BLACK );
        StyleConstants.setFontSize(newAttrs, fontSize );
        StyleConstants.setFontFamily(newAttrs, StyleConstants.getFontFamily(as) );
        
        StyleConstants.setAlignment(newAttrs, StyleConstants.ALIGN_RIGHT);
        newAttrs.addAttribute("created", new Date().getTime());
        newAttrs.addAttribute("originalforeground", StyleConstants.getForeground(newAttrs));
    
        
        super.replace( i, i1, string, newAttrs); //To change body of generated methods, choose Tools | Templates.
        
        
        //super.replace(i, i1, string, as); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void setWhitelist(String s){
        this.whitelist=s;
    }
    
}
