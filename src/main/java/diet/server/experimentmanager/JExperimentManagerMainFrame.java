/*
 * JExperimentManagerMainFrame.java
 *
 * Created on 07 January 2008, 12:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.experimentmanager;
import diet.server.experimentmanager.ui.JEMStarter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.*;


/**
 *
 * @author user
 */
public class JExperimentManagerMainFrame extends JFrame{
    
    /** Creates a new instance of JExperimentManagerMainFrame */
    
    JLeftTabbedPanel jltp;
    

   
    JSplitPane jsplitpaneTree_Panels_LEFT_RIGHT;
    JSplitPane jsplitpaneDebugOutput_TOP_BOTTOM;
    //JSplitPane jsplitpaneRightLevel;
    JScrollPane leftScrollPane;
    JPanel rightTopPanel;
    JOutputPanels jos;
    
    EMUI expmanUI;
    
    JTabbedPane jtb = new JTabbedPane();
    
    MenuBarHandler mbh;
    
    public JStatusBar jsb  = new JStatusBar(this);
    
    public JExperimentManagerMainFrame(EMUI expmanUI,String s, String header) {
        super(header);
        this.expmanUI=expmanUI;
        jltp = new JLeftTabbedPanel(expmanUI);
        
                 
       
        
        JClassBrowser jcb = new JClassBrowser(this);
        
        this.jltp.displayPanelAddingIfNecessary("Setup & Templates",jcb);
        
        
        
        jos= new JOutputPanels();
        displayTextOutputInBottomTextarea("Main", "The chat server is now listening for connections on port: "+this.expmanUI.expmanager.getPortNumberListening()+ "\n");
       
        
        jsplitpaneDebugOutput_TOP_BOTTOM =new JSplitPane(JSplitPane.VERTICAL_SPLIT,jltp, jos);
        
       
        
        
        
        this.setLayout(new BorderLayout());
        this.getContentPane().add(jsplitpaneDebugOutput_TOP_BOTTOM,BorderLayout.CENTER);
       
        mbh = new MenuBarHandler(this);
        mbh.buildMenu();
        
        
         this.pack();
        // System.exit(-1234510);
        this.validate();
        jsplitpaneDebugOutput_TOP_BOTTOM.setDividerLocation(jsplitpaneDebugOutput_TOP_BOTTOM.getHeight()-250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.validate();
        this.setVisible(true);
        
        this.getContentPane().add(jsb,BorderLayout.SOUTH);
    
       
    
    }
    
 
    
    public void displayTextOutputInBottomTextarea(String stitle, String text){
        jos.outputTextCreatingPanelIfNecessary(stitle,text);
    }
    
   
    
    
    
    
    public void setTopRightPanelParameterFileDeprecated(File f){
       // JParameterSetupPanel jpsp = new JParameterSetupPanel(f);
       // this.rightTopPanel.add(jpsp);
        rightTopPanel.validate();
        rightTopPanel.repaint();
    }
    
    
    public void addConvIOtoLeftTabbedPane(String tabName,JTabbedPane jtp){
        this.jltp.displayPanelAddingIfNecessary(tabName,jtp);     
       
    }
    
    public void removeConvIOFromLeftTabbedPane(JComponent jc){
        this.jltp.removePanel(jc);
    }
    
     public EMUI getExpmanUI() {
        return expmanUI;
    }

    
    
   public JLeftTabbedPanel getJltp() {
        return jltp;
    }
    
    
    public void dispose(){
         JEMStarter.pullThePlug();
         
         super.dispose();
    }
}
