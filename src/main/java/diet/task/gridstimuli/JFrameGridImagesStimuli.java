/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.gridstimuli;

import diet.client.ConnectionToServer;
import diet.message.MessageGridImageStimuliSelectionFromClient;
import diet.server.ConversationController.ui.CustomDialog;
import diet.task.stimuliset.SerializableImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author sre
 */
public class JFrameGridImagesStimuli extends JFrame implements ActionListener{
    ConnectionToServer cts;
    
    Vector allPanels = new Vector();
    Hashtable htStimuli = new Hashtable();
    
    
    long serverIDOfSet;
    JPanel north;
    JButton south;
    
    public JFrameGridImagesStimuli(ConnectionToServer cts,   int rows,int columns, int widthheight)  {
        this.cts=cts;
        north = new JPanel();
        this.setLayout(new BorderLayout());
        north.setLayout(new GridLayout(rows, columns, 0, 0));
        this.getContentPane().add(north, BorderLayout.NORTH);
        
        JButton south = new JButton("Select");
        south.addActionListener(this);
        this.getContentPane().add(south, BorderLayout.SOUTH);
        
      
        
        
       
        
        for(int i=0;i<rows*columns;i++){
           JClientTangramP jcp = new JClientTangramP(this);
           jcp.setPreferredSize(new Dimension(widthheight,widthheight));
           north.add(jcp);
           allPanels.add(jcp);
        }
        initialize();
        
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public void initialize(){
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                //frame.dispose();
                String password = CustomDialog.getString("To prevent accidental closing, this is password protected.\n"
                        + "Please enter the password to close the window\n","");
                if(password.equalsIgnoreCase("d")){
                    dispose();
                    //System.exit(-4);
                }
            }
        });
       

    
   }
    
     
     public void setStimuliSet(final long serverIDD, final Vector v){
         
         SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                serverIDOfSet = serverIDD;
                for(int i=0;i<v.size();i++){
                SerializableImage si = (SerializableImage)v.elementAt(i) ;
                htStimuli.put(si.getName(), si);  
           }
         }});
         
         
     }
    
    public void changeImages(final long serverIDD, final Vector imageNames){
      SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        
        serverIDOfSet = serverIDD;
        for(int i=0;i<imageNames.size();i++){
            String imageName= (String)imageNames.elementAt(i);
            SerializableImage si = (SerializableImage)htStimuli.get(imageName);
            if(si==null){
                cts.sendErrorMessage("PROBLEM. COULD NOT FIND IMAGE CALLED "+imageName);
                
            }
            else{
                 JClientTangramP jcp = (JClientTangramP)allPanels.elementAt(i);
                 jcp.setImage(si);
            }
        }
        repaint();
        }
      });
        
        
    }
     
    //jfgridstimuli.changeSelection(mgsstc.getServerID(),mgsstc.getSelections(),mgsstc.getColour());
         
    public void changeSelection(final long serverIDD, final Vector selections, final Color cInnermost, final Color cInner){
      SwingUtilities.invokeLater(new Runnable(){
         public void run(){
         serverIDOfSet=serverIDD;
         
        
         for(int i=0;i<selections.size();i++){
             String s = (String)selections.elementAt(i);
             // System.err.println("A---------"+s+"  "+allPanels.size());
             for(int j=0;j<allPanels.size();j++){
                 JClientTangramP jctp = (JClientTangramP)allPanels.elementAt(j);
                 //System.err.println("-----"+jctp.getName()+"-----"+s);
                 if(jctp.si!=null&&jctp.si.getName().equalsIgnoreCase(s)){
                     jctp.setSelection(cInnermost,cInner);
                 }
             }
         }
          repaint();
        }
      });
      // System.exit(-5);
       
    }
    
    
    public void setImageSelected( JPanel jp,SerializableImage si, long timeOfPress){
         //send serverID
        int index=-1;
        index = this.allPanels.indexOf(jp);
        try{
        MessageGridImageStimuliSelectionFromClient mgss = new MessageGridImageStimuliSelectionFromClient(cts.getEmail(),cts.getUsername(), this.serverIDOfSet,index, si.getName(),timeOfPress);
        cts.sendMessage(mgss);
        }catch(Exception e){
            cts.sendErrorMessage(e);
        }
    }
    
    public void actionPerformed(ActionEvent e) { 
        MessageGridImageStimuliSelectionFromClient mgss = new MessageGridImageStimuliSelectionFromClient(cts.getEmail(),cts.getUsername(), serverIDOfSet, -1, "Select",e.getWhen());
        cts.sendMessage(mgss);
    }
    
    
}
