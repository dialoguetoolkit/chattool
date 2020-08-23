/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client;

import diet.attribval.AttribVal;
import diet.server.ConversationController.ui.CustomDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author gj
 */
public class JFrameStimulusSingleImage extends JFrame implements WindowListener, ActionListener{

    ConnectionToServer cts;
    JPanelStimulusSingleImage jpssi;
    JPanel jsp = new JPanel();
    Vector jbuttons = new Vector();
    public int prefwidth ;
    public int prefheight;
    
    
    
    
    public JFrameStimulusSingleImage(ConnectionToServer cts, int width, int height, String[] buttonnames) throws HeadlessException {
        super(cts.getUsername()); 
        this.prefwidth=width;
        this.prefheight=height;
        this.addWindowListener(this);
         //this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
         this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
         
        this.getContentPane().setLayout(new BorderLayout());
        jpssi = new JPanelStimulusSingleImage(cts);
        jpssi.setPreferredSize(new Dimension(width,height)); //For the buttons
        jpssi.setMinimumSize(new Dimension(width,height));
        jpssi.setMaximumSize(new Dimension(width,height));
        //jpssi.setBackground(Color.BLACK);
       
        this.getContentPane().add(jpssi, BorderLayout.CENTER);
        this.cts = cts;
        
        
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        
        southPanel.setPreferredSize(new Dimension(width,30));
        //southPanel.setBackground(Color.white);
        //JPanel southPanelNorth = new JPanel();
        
        
        //JPanel southPanelSouth = new JPanel();
        //southPanel.setBackground(Color.red);
        //southPanelNorth.setBackground(Color.green);
        //southPanelSouth.setBackground(Color.blue);
        
        
        for(int i=0;i<buttonnames.length;i++){
           JButton jb = new JButton(buttonnames[i]);
           jb.setFocusable(false);
           
           southPanel.add(jb);    
           this.jbuttons.add(jb);
           jb.addActionListener(this);
           currentButtons.add(jb);
        }
        
        //southPanel.add(southPanelNorth,BorderLayout.NORTH);
        //southPanel.add(southPanelSouth,BorderLayout.SOUTH);
        
         southPanel.setPreferredSize(new Dimension(this.prefwidth,40));
        jcurrentbuttonpanel=southPanel;
        
        this.getContentPane().add(southPanel,BorderLayout.SOUTH);
        
        
        this.setResizable(false);
        this.setVisible(true);
        this.pack();    
    }
    
    JPanel jcurrentbuttonpanel;
    Vector<JButton> currentButtons=new Vector();
    
    
    
    
       
    private void newButtons(String[] buttonnames, boolean enabled){
        //Disconnect
        jcurrentbuttonpanel.setVisible(false);
        this.getContentPane().remove(jcurrentbuttonpanel);
        for(int i=0;i<currentButtons.size();i++){
            currentButtons.elementAt(i).removeActionListener(this);
        }
        currentButtons=new Vector();
        //Create new
        JPanel southPanel = new JPanel();
        JPanel southPanelNorth = new JPanel();
        JPanel southPanelSouth = new JPanel();   
        for(int i=0;i<buttonnames.length;i++){
           JButton jb = new JButton(buttonnames[i]);
           jb.setFocusable(false);
           jb.setEnabled(enabled);
           southPanel.add(jb);    
           this.jbuttons.add(jb);
           jb.addActionListener(this);
           currentButtons.add(jb);
        }
       
        southPanel.setPreferredSize(new Dimension(this.prefwidth,40));
        //southPanel.setBackground(Color.white);
        
        //southPanel.add(southPanelNorth,BorderLayout.NORTH);
        //southPanel.add(southPanelSouth,BorderLayout.SOUTH);
        jcurrentbuttonpanel= southPanel;
        
        //southPanel.setBackground(Color.red);
        //southPanelNorth.setBackground(Color.green);
        //southPanelSouth.setBackground(Color.blue);
        
        this.getContentPane().add(southPanel,BorderLayout.SOUTH);
        
        
        
        
    }
    
    
    public void addNewButtons(final String[] buttonnames, final boolean enable){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                newButtons( buttonnames,enable);
            }
        });
      
    }
    
    
    public void enableButtons(final String[] buttonnames, final boolean enable){
      
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                for(int i=0;i<buttonnames.length;i++){
                    String buttonname = buttonnames[i];
                    
                    for(int j=0;j<jbuttons.size();j++){
                        JButton jb = (JButton)jbuttons.elementAt(j);
                        
                        
                        if(jb.getText().equalsIgnoreCase(buttonname)){
                            jb.setEnabled(enable);
                            
                            AttribVal av1 = new AttribVal("name",buttonnames[i]);
                            String newstatus = "enable";
                            if(!enable)newstatus="disable";
                            AttribVal av2 = new AttribVal("newstatus",newstatus);
                            cts.cEventHandler.reportInterfaceEvent("button_enable", new Date().getTime(), av1,av2);
                            System.err.println("ENABLING");
                        }
                    }
                }
            }
        
        });
        
        
    }
    
    
    
    
    public void displayBackgroundColour(Color col){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                jpssi.changeImage(Color.GRAY);
            }
        });    
    }
    
    
    public void displayImage(final String jarresourcename, boolean isphysicalfile){
        
        if(jarresourcename==null)return;
        if(jarresourcename.equalsIgnoreCase(""))return;
        
       System.err.println("Trying to LOAD1 "+jarresourcename);
        BufferedImage bi=null;
        if(isphysicalfile){
            bi = loadFromDirectory(jarresourcename);
        }
        else{
            bi = loadFromJar(jarresourcename);
        }
        
        if(bi==null){
            cts.sendErrorMessage("ERROR: Could not find "+jarresourcename);
            CustomDialog.showDialog("ERROR: Could not find "+jarresourcename );
            
        }
        
        final BufferedImage finalimage = bi;
        final JFrameStimulusSingleImage jfssi = this;
        SwingUtilities.invokeLater(new Runnable(){
           public void run(){                    
                jpssi.changeImage(finalimage, jarresourcename);
            }
            
        });
    }
    
    JFrameStimulusSingleImageCountdownThread jfssictCurrent;
    
    
 
 public BufferedImage loadFromJar(String jarresourcename){
     BufferedImage image =null ;
        try {
             ClassLoader cldr = JFrameStimulusSingleImage.class.getClassLoader();
             URL url = cldr.getResource(jarresourcename);
             image = ImageIO.read(url);
              System.err.println("Trying to LOAD2 "+jarresourcename);
             //image = ImageIO.read(new File("C:\\sourceforge\\experimentresources\\gridstimuli\\stimuliset5\\A01.png"));
       } catch (IOException ex) {
            // handle exception...
           ex.printStackTrace();
           System.err.println("ERROR TRYING TO LOAD3: "+jarresourcename);
            cts.sendErrorMessage(ex);
       }
        return image;
 }
 
 
 public BufferedImage loadFromDirectory(String jarresourcename){
      String currentDirectory = System.getProperty("user.dir");
      
      jarresourcename = jarresourcename.replace("/", File.separator);
      jarresourcename = jarresourcename.replace("\\", File.separator);
      
      String filename= currentDirectory+File.separator+  "experimentresources"+ File.separator+   "stimuli"+ File.separator+ jarresourcename;
      System.err.println("loadfromdirectory: "+filename);
      try{
      BufferedImage image = ImageIO.read(new File(filename));
      return image;
      } catch (Exception e){
          cts.sendErrorMessage(e);
      }
      return null;
 }
    
    
 public void displayImage(final String resourcename, boolean  isindirectoryfinal, long duration){
        
        System.err.println("Trying to LOAD1 "+resourcename);
        BufferedImage bi=null;
        if(isindirectoryfinal){
            bi = loadFromDirectory(resourcename);
        }
        else{
            bi = loadFromJar(resourcename);
        }
        
        if(bi==null){
            cts.sendErrorMessage("ERROR: Could not find "+resourcename);
            CustomDialog.showDialog("ERROR: Could not find "+resourcename );
            
        }
        
        final BufferedImage finalimage = bi;
        final JFrameStimulusSingleImage jfssi = this;
        SwingUtilities.invokeLater(new Runnable(){
           public void run(){                    
                jpssi.changeImage(finalimage, resourcename,duration);
            }
            
        });
        
    }
    
    

    @Override
    public void windowOpened(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
     public void windowClosing(WindowEvent we) {
         String result  = CustomDialog.getString("Please enter the password to close the window\nThis is to prevent 'accidental' closing of the window by participants!\n", "");
         if(result==null)return;
         if(result.equalsIgnoreCase("closedown")){
             cts.sendErrorMessage("THE CLIENT STIMULUS WINDOW OF:"+ cts.getEmail()+","+cts.getUsername() +" WAS CLOSED DOWN! AT" +(new Date().getTime()));
             System.err.println("THE CLIENT STIMULUS WINDOW WAS CLOSED DOWN!");
             System.exit(-1112223);
             
         }
    }
     
     public void actionPerformed(ActionEvent ae){
         if(ae.getSource() instanceof JButton){
             String text = ((JButton)ae.getSource()).getText();
             long timeOfAction = ae.getWhen();
             AttribVal av = new AttribVal("text",text);
             this.cts.cEventHandler.reportInterfaceEvent("button_press", timeOfAction, av);
             this.cts.sendButtonPresed(text,timeOfAction);
         }
     }
     
     
}
