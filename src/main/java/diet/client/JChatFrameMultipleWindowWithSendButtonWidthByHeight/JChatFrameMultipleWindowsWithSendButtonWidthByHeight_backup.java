package diet.client.JChatFrameMultipleWindowWithSendButtonWidthByHeight;

import diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker;
import diet.attribval.AttribVal;
import diet.client.ClientEventHandler;
import diet.client.DebugRobot;
import diet.client.JChatFrame;
import diet.client.JFrameGlassPane;
import diet.client.JChatFrameRightMost.JTextPaneNavigationFilter;
import diet.gui.lookandfeel.JProgressBarFillPainter;
import diet.gui.lookandfeel.NimbusLookAndFeelUtilities;
import diet.server.ConversationController.DefaultConversationController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.text.*;
import javax.swing.text.AttributeSet;

/**
 * Chat window interface with separate text entry area. Text is only sent when participant presses
 * "ENTER"/"RETURN"/"SEND". Can be configured with single window (similar to existing commercial chat
 * tools) or with multiple windows.
 * @author user
 */
public class JChatFrameMultipleWindowsWithSendButtonWidthByHeight_backup extends JChatFrame {

    
    
    boolean displaySENDButton = false;
    
    JPanel cards = new JPanel(new CardLayout()) ;
    //JPanel card2 = new JPanel();
    
    
    JProgressBar jp ;
    JCollectionOfChatWindows jccw;
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JButton jSENDButton = new JButton();
    JPanel jPanel2 = new JPanel();
    JScrollPane jTextEntryScrollPane = new JScrollPane();
    //JTextPane jTextEntryPane = new JChatFrameMultipleWindowsWithSendButtonWidthByHeightTextEntryPane();  //new JTextPane();
    JTextPane jTextEntryPane = new JTextPane();
   

    JLabel jLabeldisplay = new JLabel(" ");
    int participantsOwnWindowForTextEntry;
    InputDocumentListener jtpDocumentListener;

    JPanel jContentPanel = new JPanel();
       
    JFrameGlassPane jfgp = new JFrameGlassPane(0);
    DebugRobot dr = new DebugRobot(); 
    CardLayout cl = new CardLayout();;
    
    public int maxcharlength = 90;
    
    JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument jcfd= new JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument(null);
    StyledDocumentStyleSettings styles;
    MutableAttributeSet selfSTYLE;
    

    public JChatFrameMultipleWindowsWithSendButtonWidthByHeight_backup(ClientEventHandler clevh, int numberOfWindows, int mainWindowWidth, int mainWindowHeight,boolean isVertical,boolean hasStatusWindow,int windowOfOwnText, int textEntryWidth, int textEntryHeight, int maxCharlength, StyledDocumentStyleSettings styles) {
        super (clevh);
       
        selfSTYLE = styles.masSELF;
        changeInterfaceProperties("initialsetup", ClientInterfaceEventTracker.changeTextStyleOfTextFormulationWindow, selfSTYLE,null );
        
                
               
        
        
       
        //setUndecorated(true);
        try {
         cards = new JPanel (new CardLayout());
        this.jContentPanel.setLayout(borderLayout1);
        jccw = new JCollectionOfChatWindows(numberOfWindows,mainWindowWidth,mainWindowHeight,isVertical,hasStatusWindow,windowOfOwnText, styles);
        cards.add(jccw,"jccw");
        
        
        JPanel jp = new JPanel();
        cards.add(jp,"grey");
        
        CardLayout cl = (CardLayout)(cards.getLayout());
        //cl.show(cards, "grey");
        
        
        
        this.jContentPanel.add(cards,BorderLayout.CENTER);
        jSENDButton.setHorizontalTextPosition(SwingConstants.CENTER);
       // this.wstyles = styles;
        //jPanel1.setPreferredSize(new Dimension(50, 200));
        jPanel1.setLayout(borderLayout2);

        this.jContentPanel.add(jPanel1, java.awt.BorderLayout.SOUTH);
         if(this.displaySENDButton)jPanel1.add(jPanel2,BorderLayout.EAST);

        
        this.maxcharlength=maxCharlength;
        this.participantsOwnWindowForTextEntry = windowOfOwnText;

        if(this.displaySENDButton)jPanel2.add(jSENDButton, java.awt.BorderLayout.EAST);
        jSENDButton.setPreferredSize(new Dimension(73,64));
        jSENDButton.setText("SEND");
        jSENDButton.addActionListener(new JChatFrameSENDButtonActionListener());
        jSENDButton.setMargin(new Insets(0,0,0,0));
 
        //this.getContentPane().add(jb, BorderLayout.EAST);
       

         jTextEntryPane.setBackground(styles.getBackgroundColor());
        
         NimbusLookAndFeelUtilities.changeTextPaneBackground(jTextEntryPane, styles.getBackgroundColor());
         
         
         
         JTextPaneNavigationFilter jtpnf = new JTextPaneNavigationFilter(jTextEntryPane, null);
         jTextEntryPane.setNavigationFilter(jtpnf);
         
         //styles.setStyles(jcfd,this.jTextEntryPane);
         jTextEntryPane.setFocusable(true);
         jTextEntryPane.setEditable(true);
         //.setFocusable(false);
         jTextEntryPane.setBackground(styles.getBackgroundColor());
         jTextEntryPane.getCaret().setVisible(true);
         jTextEntryPane.setCaretColor(styles.getCaretColor());
         //jTextEntryPane.
          jTextEntryPane.setEditorKit(new WrapEditorKit());

         jTextEntryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         jTextEntryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         // jTextEntryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
         //jTextEntryPane
         //jTextEntryScrollPane.getViewport().add(jTextEntryPane);
         jTextEntryScrollPane.getViewport().setPreferredSize(new Dimension(textEntryWidth,textEntryHeight));
         jTextEntryScrollPane.setPreferredSize(new Dimension(textEntryWidth,textEntryHeight));
         //jccw.setPreferredSize(new Dimension(mainWindowWidth,mainWindowHeight));
         
        jTextEntryPane.setDocument(jcfd);
        
        

        jtpDocumentListener = new InputDocumentListener(jTextEntryPane);
        jTextEntryPane.getDocument().addDocumentListener(jtpDocumentListener);

        jTextEntryScrollPane.getViewport().add(jTextEntryPane);
        //jTextEntryPane.setFont(null);
        jTextEntryPane.addKeyListener(new JChatFrameKeyEventListener());

        
       
        
        jPanel1.add(jTextEntryScrollPane, BorderLayout.CENTER);
       
        
        
        jContentPanel.setBorder(BorderFactory.createLineBorder(jContentPanel.getBackground(),10));
        
        this.getContentPane().add(jContentPanel);
        
        
        //jfgp.setBackground(Color.green);
        jfgp.setOpaque(false);
        this.setGlassPane(jfgp);
        jfgp.setVisible(true);
      
        
        
        
        
        
        
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        this.setResizable(true);
        try{
            this.validate();
        }catch (Exception e){e.printStackTrace();}
        //this.revalidate();
        try{
           this.pack();
       
         }catch (Exception e){e.printStackTrace();}
        
        this.setLocationRelativeTo(null);
        }catch (Exception ex){
            System.err.println("COULD NOT SET UP CHAT INTERFACE");
            ex.printStackTrace();
        }
      
        //this.setStatusBarText("THIS IS THE TEXT", null);
        
        
       // setStatusBarText_EnableDisable("<html><b><u>T</u>wo</b><br>lines</html>", null, false);
      
        
    }

    
    
    
    public MutableAttributeSet getSelfStyle(){
        return this.selfSTYLE;
    }

    
    public void testTForDebugTrigger(String s){
        
         if(s.startsWith(DefaultConversationController.sett.debug_fakeTypingByClientSTARTTrigger))  dr.startFakeKeyboardActivity();
         else if(s.startsWith(DefaultConversationController.sett.debug_fakeTypingByClientSTOPTrigger)) dr.stopFakeKeyboardActivity();
            //System.exit(-234);
    }
    
    
 public Color jprogressbarcolor;   
    
public void changeJProgressBar(final String text,final Color colorForeground, int value){
    final JFrame jf2 = this;
    
    if(value<0)value =0;
        if(value>100)value=100;
        final int valCorr=value;
        SwingUtilities.invokeLater(new Runnable(){
           public void run(){
              try{

                if(jp==null){
                     UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
                     UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
                     jp = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
                    
                     jp.setUI(new BasicProgressBarUI() {
                          protected Color getSelectionBackground() { return Color.black; }
                          protected Color getSelectionForeground() { return Color.black; }
                      });
                      jp.setPreferredSize(new Dimension(jPanel2.getWidth(),25));
                      jp.setIndeterminate(false);
                      jPanel1.add(jp,BorderLayout.SOUTH);
                      jf2.pack();
                }
                

             UIDefaults defaults = new UIDefaults();
             defaults.put("ProgressBar.foreground", Color.BLACK);
             if(colorForeground!=jprogressbarcolor){
                  defaults.put("ProgressBar[Enabled].foregroundPainter",new JProgressBarFillPainter(colorForeground));    
                  jprogressbarcolor = colorForeground;
                  jp.putClientProperty("Nimbus.Overrides", defaults);
                  jp.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
             }        
               
                jp.setForeground(colorForeground);
                jp.setValue(valCorr);
                jp.setString(text);
                jp.setStringPainted(true);
                 SwingUtilities.updateComponentTreeUI(jp);
            }catch (Exception e){
                    System.err.println("\n1"                            + "\n2"                            + "\n3"                            + "\n4"                            + "\n5"                            + "\n6"                            + "\n7"                            + "\n8"                            + "\n9"                            + "\n10"
                            + "\n11Error displaying progressBar in CHATFRAME");
                    e.printStackTrace();

               }
           }
       });

    }    

    
  Color oldActiveTextEntryBackground = null;
  Color oldActiveTextEntryForeground = null;
    
  
 public void changeInterfaceProperties(final String uniqueIDGeneratedByServer, final int newInterfaceproperties, final Object value1, final Object value2){
      
       final JChatFrameMultipleWindowsWithSendButtonWidthByHeight_backup jf = this;
       
       final AttribVal avServerId = new AttribVal("serverid",uniqueIDGeneratedByServer);
       
       SwingUtilities.invokeLater( new Runnable(){ public void run(){ 
                boolean performPack=true;
                System.out.println("SWINGUTILITIES");
          
                
                if (newInterfaceproperties==ClientInterfaceEventTracker.clearTextEntryField){
                    jf.clearTextEntryField(false);
                    
                     
                     
                     
                     
                     
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.clearMainTextWindows){
                     jccw.clearAllTextWindows();
                }     
                else if (newInterfaceproperties==ClientInterfaceEventTracker.clearAllWindowsExceptWindow0){
                     //jccw.clearAllTextWindows();
                     int numberOfWindows = jccw.getTextPanes().size();
                     for(int i=1;i<numberOfWindows;i++){
                          jccw.cleartextWindow(i);
                     }
                     
                     
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.disableTextEntry){    
                    if(jTextEntryPane.isEnabled()){
                          oldActiveTextEntryBackground = jTextEntryPane.getBackground();
                          oldActiveTextEntryForeground = jTextEntryPane.getForeground();    
                     }
                     jTextEntryPane.setEnabled(false);
                     jSENDButton.setEnabled(false);
                     jTextEntryPane.setBackground(Color.GRAY);
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.enableTextEntry){        
                     if(oldActiveTextEntryBackground!=null){
                         jTextEntryPane.setBackground(oldActiveTextEntryBackground);
                     }
                     if(oldActiveTextEntryForeground!=null){
                         jTextEntryPane.setForeground(oldActiveTextEntryForeground);
                     }
                    
                     jTextEntryPane.setEnabled(true);
                     jSENDButton.setEnabled(true);
                     //jTextEntryPane.setBackground(Color.red);
                     jTextEntryPane.setFocusable(true);
                     jTextEntryPane.requestFocusInWindow();
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.enableTextPane){
                     CardLayout cl = (CardLayout)(cards.getLayout());
                     cl.show(cards, "jccw"); 
                     
                }
                 else if (newInterfaceproperties==ClientInterfaceEventTracker.disableTextPane){
                     CardLayout cl = (CardLayout)(cards.getLayout());
                     cl.show(cards, "grey"); 
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.enableScrolling){
                    jccw.setEnableScrollBars(true,jf);
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.disableScrolling){
                    jccw.setEnableScrollBars(false,jf);
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.changeScreenBackgroundColour){
                    jccw.changeWindowBackgroundColour(value1);
                   
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.changeTextStyleOfTextFormulationWindow){
                     MutableAttributeSet mas = (MutableAttributeSet)value1;
                      boolean textIsBold = (boolean)mas.getAttribute(StyleConstants.Bold);
                      String fontFamily = (String)mas.getAttribute(StyleConstants.FontFamily);
                      int fontSize = (int)mas.getAttribute(StyleConstants.FontSize);                     
                      Font fnt = new Font(fontFamily, Font.PLAIN, fontSize);                   
                      jf.jTextEntryPane.setFont(fnt);
                }
                
                
                else if (newInterfaceproperties==ClientInterfaceEventTracker.changeBorderOfChatFrame){
                    performPack=false;
                    long timeOfDisplay = new Date().getTime();        
                    jf.jfgp.changeBorder((int)value1, (Color)value2);
                    
                    AttribVal av1 = new AttribVal("width", (int)value1);
                    AttribVal av2 = new AttribVal("colour", (Color)value2);
                  
                    getClientEventHandler().reportInterfaceEvent(ClientInterfaceEventTracker.changeborder, timeOfDisplay ,av1,av2, avServerId);
                }   
                else if (newInterfaceproperties==ClientInterfaceEventTracker.displayTextInStatusBar){    
                    long timeOfDisplay = new Date().getTime();
                    jf.setStatusBarText((String)value1, (MutableAttributeSet)value2);
                    AttribVal av1 = new AttribVal("text",(String)value1);
                     getClientEventHandler().reportInterfaceEvent(ClientInterfaceEventTracker.changestatuslabel, timeOfDisplay ,av1, avServerId);
                    
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.displaytextInStatusBarANDDisableTextentry){
                    long timeOfDisplay = new Date().getTime();
                    jf.setStatusBarText_EnableDisable((String)value1, (MutableAttributeSet)value2, false);
                    AttribVal av1 = new AttribVal("text",(String)value1);
                    getClientEventHandler().reportInterfaceEvent(ClientInterfaceEventTracker.changestatuslabel_disabletextentry, timeOfDisplay ,av1, avServerId);
                    
                    
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.displaytextInStatusBarANDEnableTextentry){
                     long timeOfDisplay = new Date().getTime();
                    jf.setStatusBarText_EnableDisable((String)value1, (MutableAttributeSet)value2, true);
                    AttribVal av1 = new AttribVal("text",(String)value1);
                    getClientEventHandler().reportInterfaceEvent(ClientInterfaceEventTracker.changestatuslabel_enabletextentry, timeOfDisplay ,av1, avServerId);
                    
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.enableDeletes){
                    JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument jcfd = ( JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument)jTextEntryPane.getDocument();
                  
                    jcfd.setPermitDeletes(true);
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.disableDeletes){
                    JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument jcfd = ( JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument)jTextEntryPane.getDocument();
                  
                    jcfd.setPermitDeletes(false);
                }
                    
                 
                 
                
                //if(performPack) jf.pack();
                
                
        } });
       
       
 }

   
    

    

    
 private void setStatusBarText_EnableDisable(String newText,  MutableAttributeSet mas, boolean setWindowEnabled){  
     if(mas!=null){
            boolean textIsBold = (boolean)mas.getAttribute(StyleConstants.Bold);
            String fontFamily = (String)mas.getAttribute(StyleConstants.FontFamily);
            int fontSize = (int)mas.getAttribute(StyleConstants.FontSize);
            Color foreground = (Color)mas.getAttribute(StyleConstants.Foreground);    
            Font fnt = new Font(fontFamily, Font.PLAIN, fontSize);
            Vector jLabels = jccw.getJLabels();
            for(int i=0;i<jLabels.size();i++){
                 JLabel jl = (JLabel)jLabels.elementAt(i);
                 jl.setFont(fnt);
                 jl.setForeground(foreground);
                 jl.setText(newText);
            }
     }
     else { 
            Vector jLabels = jccw.getJLabels();
            for(int i=0;i<jLabels.size();i++){
                 JLabel jl = (JLabel)jLabels.elementAt(i);
                 jl.setText(newText);
            }
     }
     
     this.jTextEntryPane.setEnabled(setWindowEnabled);
     this.jTextEntryPane.setEditable(setWindowEnabled);
     this.jTextEntryPane.setFocusable(setWindowEnabled);
     if(setWindowEnabled)this.jTextEntryPane.requestFocus();
     
 }

 
  private void setStatusBarText(String newText,  MutableAttributeSet mas){  
     if(mas!=null){
            boolean textIsBold = (boolean)mas.getAttribute(StyleConstants.Bold);
            String fontFamily = (String)mas.getAttribute(StyleConstants.FontFamily);
            int fontSize = (int)mas.getAttribute(StyleConstants.FontSize);
            Color foreground = (Color)mas.getAttribute(StyleConstants.Foreground);    
            Font fnt = new Font(fontFamily, Font.PLAIN, fontSize);
            Vector jLabels = jccw.getJLabels();
            for(int i=0;i<jLabels.size();i++){
                 JLabel jl = (JLabel)jLabels.elementAt(i);
                 jl.setFont(fnt);
                 jl.setForeground(foreground);
                 jl.setText(newText);
            }
     }
     else { 
            Vector jLabels = jccw.getJLabels();
            for(int i=0;i<jLabels.size();i++){
                 JLabel jl = (JLabel)jLabels.elementAt(i);
                 jl.setText(newText);
            }
     }
 }
 
 
 
 
 


 public void appendWithCaretCheck(String idTOCONFIRMTOSERVER,  String prefix,  boolean showPrefix, String text, int windowNumber, Object attributeset) {
     Vector jTextPanes = jccw.getTextPanes();
     Vector scrollPanes = jccw.getScrollPanes();
     if (windowNumber >= scrollPanes.size())return;
     JTextPane jtp = (JTextPane)jTextPanes.elementAt(windowNumber);
     JScrollPane jsp = (JScrollPane)scrollPanes.elementAt(windowNumber);
    
     this.testTForDebugTrigger(text);
     
     if(attributeset instanceof String){
         String attributesetAsString = (String)attributeset;
         if(attributesetAsString.equalsIgnoreCase("SELF")){
               SwingUtilities.invokeLater(new DoAppendTextWithCaretCheck(this, jtp, jsp, windowNumber, idTOCONFIRMTOSERVER,prefix, showPrefix, text,selfSTYLE, true));  
         }
     }
     else{
         SwingUtilities.invokeLater(new DoAppendTextWithCaretCheck(this, jtp, jsp,windowNumber, idTOCONFIRMTOSERVER,prefix, showPrefix, text,attributeset, false)); 
     }
    
     
     
 }

 
   
     public String getContentsOfWindow(int windownumber){
           JTextPane jtp = (JTextPane)jccw.getTextPanes().elementAt(windownumber);
           int offSet = 0;
           int end = 0;
           try{
                JViewport viewPort = (JViewport) jtp.getParent();
                offSet = jtp.viewToModel(viewPort.getViewRect().getLocation());
                int x = (int)viewPort.getViewRect().getWidth();
                int y = (int)viewPort.getViewRect().getLocation().getY() + (int)viewPort.getVisibleRect().getHeight();
                if (y > jtp.getHeight()){
                     y = jtp.getHeight();
                }
                Point endPoint = new Point(x,y);
                end = jtp.viewToModel(endPoint);
               
                return (jtp.getText(offSet,end - offSet));
           }
           catch (Exception e){
                e.printStackTrace();
           }
           return "-";
      }
   
 
 public String getContentsOfWindowOLD(int windownumber){
    //THIS MUST BE ON THE SWING THREAD!
   String results ="";
   try{  
      JTextPane jtp = (JTextPane)jccw.getTextPanes().elementAt(windownumber);
      JScrollPane jsp = (JScrollPane)jccw.getScrollPanes().elementAt(windownumber);
      JViewport viewport = jsp.getViewport();
      Point startPoint = viewport.getViewPosition();
      Dimension size = viewport.getExtentSize();
      Point endPoint = new Point(startPoint.x + size.width, startPoint.y + size.height);
      int start = jtp.viewToModel( startPoint );
      int end = jtp.viewToModel( endPoint );
      results = jtp.getText(start, end - start);
   } catch(Exception e){
       e.printStackTrace();
   }  
   return results;
}

 
 
 
 

    @Override
  public void clearTextEntryField(boolean changeIsInitiatedByClient){
           //SwingUtilities.invokeLater(new DoSetTextEntryField(jTextEntryPane,s));
           
      
           
           if(changeIsInitiatedByClient){
                final JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument   jcfdc = (JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument ) this.jTextEntryPane.getDocument();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                         try{
                         jcfdc.removeDocumentListener(jtpDocumentListener);
                         
                         jcfdc.remove(0, jcfdc.getLength(), true);
                         
                         jcfdc.addDocumentListener(jtpDocumentListener);
                         
                         }catch (Exception e){
                             e.printStackTrace();
                         }
                    }
                });
           }
           else{
                //hasn't needed to be implemented yet
           }
           
   }

    @Override
   public int getParticipantsOwnWindow(){
       return this.participantsOwnWindowForTextEntry;
   }

    @Override
public String getTextEnteredInField(){
      return jTextEntryPane.getText();
    }

public  void sendButtonPressed(ActionEvent e) {
      getClientEventHandler().sendButtonPressedDEPRECATED();
    }

    void keyPressed(KeyEvent e) {
      getClientEventHandler().keyPressFilter(e);
    }

    void keyReleased(KeyEvent e) {
      this.getClientEventHandler().keyReleaseFilter(e);
  }





    class JCollectionOfChatWindows extends JPanel{
        private Vector scrollPanes = new Vector();
        private Vector jLabels = new Vector();
        private Vector jTextPanes = new Vector();
        BoxLayout blyout;
        String labelSpacePadding = "                                         ";
        boolean hasStatusWindow;
        int windowOfParticipant=0;
        Color enabledColor;
        
      
        
        
        
        public JCollectionOfChatWindows(int numberOfWindows,int mainWindowWidth,int mainWindowHeight,boolean isVertical,boolean hasStatusWindow,int windowOfParticipant, StyledDocumentStyleSettings styles){
            super();
            if (isVertical){
                 blyout= new BoxLayout(this, BoxLayout.Y_AXIS);
            }
            else {
                blyout= new BoxLayout(this, BoxLayout.X_AXIS);
                labelSpacePadding="";
            }
            this.hasStatusWindow=hasStatusWindow;
            this.setLayout(blyout);
            this.windowOfParticipant=windowOfParticipant;

            


            for(int i=0;i<numberOfWindows;i++){
                JTextPane jTextPane = new JTextPane();
                jTextPane.setEditable(false);
                jTextPane.setFocusable(false);
                jTextPane.setBackground(styles.getBackgroundColor());
                
                 NimbusLookAndFeelUtilities.changeTextPaneBackground(jTextPane, styles.getBackgroundColor());
         
                
                jTextPane.getCaret().setVisible(false);
                jTextPane.setCaretColor(styles.getCaretColor());
                jTextPane.setEditorKit(new WrapEditorKit());
                
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.getViewport().add(jTextPane);
                //scrollPane.getViewport().setPreferredSize(new Dimension(mainWindowWidth,mainWindowHeight));
                scrollPane.setPreferredSize(new Dimension(mainWindowWidth,mainWindowHeight));
                //scrollPane.setMaximumSize(new Dimension(mainWindowWidth,mainWindowHeight));
                //scrollPane.getViewport().setMaximumSize(new Dimension(mainWindowWidth,mainWindowHeight));




                JLabel jlt = new JLabel(""+labelSpacePadding);
                jlt.setFont(new java.awt.Font("Monospace", 0, 22));
                
                //styles.masSELF.
                
                jTextPanes.addElement(jTextPane);
                scrollPanes.addElement(scrollPane);
                this.add(scrollPane);
                if(hasStatusWindow){
                    jLabels.addElement(jlt);
                    this.add(jlt);
                }
                
                
            }

            
        }

  public void setEnableScrollBars(final boolean enable, final JFrame jf){
       
       final JCollectionOfChatWindows jccw = this;
       
      SwingUtilities.invokeLater(new Runnable(){
          
         
          
          public void run(){
               for(int i=0;i<scrollPanes.size();i++){
                 JScrollPane scrollPane = (JScrollPane)scrollPanes.elementAt(i);
                 scrollPane.getVerticalScrollBar().setEnabled(enable);
                 scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                 scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);     
                 scrollPane.repaint();
                 try{
                    scrollPane.validate();
                 }catch (Exception e){e.printStackTrace();}
              }
              jccw.repaint();
              
            
              try{
                    jf.validate();
                    jf.pack();
                 }catch (Exception e){e.printStackTrace();}
              
              
              
              SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    //jf.revalidate();
                    try{
                        jf.validate();
                    }catch (Exception e){e.printStackTrace();}
                    jf.pack();
                    //jf.revalidate();
                }  
              });
              
              
              
              
              
        }
      }
      );
      
      
      
      
      
      
      
  }
        
  
  
  
  
  
  
  
         public void changeWindowBackgroundColour(Object value){
            try{
                for(int i=0;i<this.jTextPanes.size();i++){
                    JTextPane jtp = (JTextPane)jTextPanes.elementAt(i);
                   jtp.setBackground((Color)value);
                    //jtp.setBackground(Color.RED);
                    jtp.repaint();
                    //System.exit(-2345);
                    
                    //Nimbus look and feel settings
                    UIDefaults defaults = new UIDefaults();
                    NimbusLookAndFeelUtilities.changeTextPaneBackground(jtp, (Color)value);
         
                    //Nimbus look and feel settings
                    
                    
                    
                }
                
                
                
            }catch(Exception e){
                e.printStackTrace();
            }
            // System.exit(-444440);
        }
        
        
        
        
         
        
        
        
        
        
  public Vector getTextPanes(){
      return jTextPanes;
  }

  public Vector getScrollPanes(){
      return scrollPanes;
  }
  public Vector getJLabels(){
      return jLabels;
  }
  public void setEnablePane(boolean enable){
      
      JTextPane jtp = (JTextPane)jTextPanes.elementAt(windowOfParticipant);
      if(!enable & jtp.isEnabled()){
          this.enabledColor =  jtp.getBackground();
          jtp.setBackground(Color.DARK_GRAY);
          jtp.setEnabled(false);
          
          
      }
      if(enable & !jtp.isEnabled()){
          jtp.setBackground(enabledColor);
          jtp.setEnabled(true);
          
      }
      
  }
  public void cleartextWindow(int windowno){
      JTextPane jtp = (JTextPane)jTextPanes.elementAt(windowno);
      jtp.setText("");
  }

  public void clearAllTextWindows(){
      for(int i=0;i<jTextPanes.size();i++){
          JTextPane jtp = (JTextPane)jTextPanes.elementAt(i);
          jtp.setText("");
      }
  }
  

    }

   class JChatFrameKeyEventListener extends java.awt.event.KeyAdapter {

   JChatFrameKeyEventListener() {
   }

   public void keyPressed(KeyEvent e) {
     getClientEventHandler().keyPressFilter(e);
     System.out.println("EVENTLISTENER DETERMINES KEYPRESSED");
   }

   public void keyReleased(KeyEvent e) {
     getClientEventHandler().keyReleaseFilter(e);
     System.out.println("EVENTLISTENER DETERMINES KEYRELEASED");
   }
}

class JChatFrameSENDButtonActionListener implements java.awt.event.ActionListener {

 JChatFrameSENDButtonActionListener() {
 }

 public void actionPerformed(ActionEvent e) {
     System.out.println("EVENTLISTENER DETERMINES SEND BUTTON PRESSED");
     //getClientEventsendButtonPressedDEPRECATEDtonPressed();
 }
}









public class DoScrolling implements Runnable{
      JScrollPane jsp;

      public DoScrolling(JScrollPane jsp){
          this.jsp = jsp;
      }
      public void run(){
          jsp.validate();
          jsp.repaint();
          jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
          //jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMinimum());
          jsp.validate();
          jsp.repaint();
      }
  }


  

  public class DoAppendTextWithCaretCheck implements Runnable{
          JChatFrameMultipleWindowsWithSendButtonWidthByHeight_backup jcfmwwsbw;
          JTextPane jtp;
          String text;
          String prefix;
          String idToConfirm="";
          JScrollPane jsp;
          Object attrset;
          boolean isbyself=false;
          boolean showPrefix = false;
          int windowNo;

          

           public DoAppendTextWithCaretCheck(JChatFrameMultipleWindowsWithSendButtonWidthByHeight_backup jcfmwwsbw, JTextPane jtp, JScrollPane jsp, int windowNumber, String idTOCONFIRM, String prefix, boolean showPrefix, String text, Object attributeset, boolean bySelf){
              this.jtp =jtp;
              this.jsp = jsp;
              this.text=text;
              this.attrset=attributeset;
              this.idToConfirm=idTOCONFIRM;
              this.prefix=prefix;
              if(this.prefix==null)prefix="";
              if(this.text==null)text="";
              this.isbyself=bySelf;
              this.jcfmwwsbw=jcfmwwsbw;
              this.showPrefix=showPrefix;
              this.windowNo=windowNumber;
          }
          public void run(){
              String textDisplayed ="";  
              long timeDisplayed = new Date().getTime();
              
              try{
                if(attrset instanceof String){
                    String attrsetAsString = (String)attrset;
                    jtp.getStyledDocument().insertString(jtp.getDocument().getLength(), text, jtp.getStyledDocument().getStyle((String)attrset));
                    textDisplayed =text;
                }
                else if (attrset instanceof MutableAttributeSet){
                    MutableAttributeSet as = ((MutableAttributeSet)attrset);
                    boolean textIsBold = (boolean)as.getAttribute(StyleConstants.Bold);  
                    if(this.showPrefix&&this.prefix.length()>0){
                        StyleConstants.setBold(as, true);     
                        jtp.getStyledDocument().insertString(jtp.getDocument().getLength(), prefix, (AttributeSet)as);
                        StyleConstants.setBold(as, textIsBold);
                        textDisplayed = prefix;
                    }
                    jtp.getStyledDocument().insertString(jtp.getDocument().getLength(), text+"\n", (AttributeSet)attrset);
                    textDisplayed = textDisplayed+text;
                    
                }
                else{
                    jtp.getStyledDocument().insertString(jtp.getDocument().getLength(), prefix+text+ " THERE IS AN ERROR IN THE SCRIPT SPECIFYING THE FONT/STYLE OF THE TEXT"+"\n", null);
                    textDisplayed = prefix+text+" THERE IS AN ERROR IN THE SCRIPT SPECIFYING THE FONT/STYLE OF THE TEXT";
                }
                
                
              AttribVal av1 = new AttribVal("text",textDisplayed);   
              AttribVal av2 = new AttribVal("serverid",idToConfirm);  
              AttribVal av3 = new AttribVal("windowno",""+windowNo);  
             
             
              
              if(isbyself){
                   jcfmwwsbw.getClientEventHandler().reportInterfaceEvent(diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker.chatwindow_appendbyself_clearingtextentry, timeDisplayed,  av1);     
              }
              else{
                   jcfmwwsbw.getClientEventHandler().reportInterfaceEvent(diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker.chatwindow_appendbyotherparticipant, timeDisplayed  ,av1, av2, av3);      
              }
             
                 //System.exit(-999);
                
                }catch (Exception e){
                    System.err.println("Error inserting string to interface");
                    e.printStackTrace();
                   
                }
              SwingUtilities.invokeLater(new DoScrolling(jsp));
          }
    }











class InputDocumentListener implements DocumentListener{
    JTextPane jTextPaneSource;

   public InputDocumentListener(JTextPane jTextPaneSource){
       this.jTextPaneSource = jTextPaneSource;
   }

    public void insertUpdate(DocumentEvent e) {
        
        updateInsert(e);
    }
    public void removeUpdate(DocumentEvent e) {
      updateRemove(e);
    }
    public void changedUpdate(DocumentEvent e) {    
    }
    
    
    public void updateInsert(DocumentEvent e) {
      int offset = e.getOffset();
      int length = e.getLength();   
      try{  
          int documentlength = jTextEntryPane.getDocument().getLength();
          int insrtIndex = (documentlength-length)-offset;  //The length of document is increased by length on insertion   
          //System.err.println("DocLength: "+documentlength+" "+". Offset: "+offset+". Length: "+length+". Text: "+jTextAreaSource.getText().substring(offset,offset+length)+". InsertIndex: "+insrtIndex);
          
          try{
             getClientEventHandler().textEntryDocumentHasChangedInsert(jTextPaneSource.getText().substring(offset,offset+length),insrtIndex,length,jTextPaneSource.getText());
          }catch (Exception eee){
              getClientEventHandler().textEntryDocumentHasChangedInsert("",insrtIndex,length,jTextPaneSource.getText());
              getClientEventHandler().getCts().sendErrorMessage("ERROR INSIDE CHAT FRAME WITH INDICES");
          }   
      }catch(Error e2){System.err.println("OFFSET ERROR "+offset+" , "+jTextPaneSource.getText().length()+" "+jTextPaneSource.getText()+" "+e2.getStackTrace());
           getClientEventHandler().getCts().sendErrorMessage(e2.getMessage()+" ERROR INSIDE THE JCHATFRAME");
      }     
   }
    
   public void updateRemove(DocumentEvent e){
       int offset = e.getOffset();
       int length = e.getLength();
       
       int documentlength = jTextEntryPane.getDocument().getLength();
       int insrtIndex = (documentlength+length)-offset; // The length of document is decreased by length on deletion
       //System.err.println("REMOVE: DocLength: "+documentlength+" "+". Offset: "+offset+". Length: "+length+". InsertIndex: "+insrtIndex);
       
       String textToAdd ="";
       try{
           textToAdd=jTextPaneSource.getText();
       }catch(Exception ee){
            getClientEventHandler().getCts().sendErrorMessage(ee.getMessage()+" ERROR INSIDE THE JCHATFRAME (REMOVE1)");
       }
       
       try{
            getClientEventHandler().wYSIWYGDocumentHasChangedRemove(insrtIndex,length,textToAdd);
            System.err.println("DELETINGDELETINGDOCUMENTDELETINGDOCUMENT");
            
       }catch(Exception e2){
           System.err.println("ERROR ATTEMPTING TO CAPTURE A DELETE");
            getClientEventHandler().getCts().sendErrorMessage(e2.getMessage()+" ERROR INSIDE THE JCHATFRAME (REMOVE1)");
       }
       
       
   } 
    
    
 }



   public void closeDown(){

       this.jTextEntryPane.setEnabled(false);
       this.jLabeldisplay.setEnabled(false);
       this.jTextEntryScrollPane.setEnabled(false);
       this.setVisible(false);
       super.dispose();
   }



   
  class WrapEditorKit extends StyledEditorKit {
        ViewFactory defaultFactory=new WrapColumnFactory();
        public ViewFactory getViewFactory() {
            return defaultFactory;
        }

    }

    class WrapColumnFactory implements ViewFactory {
        public View create(javax.swing.text.Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new WrapLabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new BoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }

            // default to text display
            return new LabelView(elem);
        }
    }

    class WrapLabelView extends LabelView {
        public WrapLabelView(javax.swing.text.Element elem) {
            super(elem);
        }

        public float getMinimumSpan(int axis) {
            switch (axis) {
                case View.X_AXIS:
                    return 0;
                case View.Y_AXIS:
                    return super.getMinimumSpan(axis);
                default:
                    throw new IllegalArgumentException("Invalid axis: " + axis);
            }
        }

    }
   
   
     
    
    
    
}


