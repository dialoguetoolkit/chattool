/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.gridstimuli;

import diet.client.ConnectionToServer;
import diet.message.MessageGridImageStimuliSelectionFromClient;
import diet.message.MessageGridTextStimuliSelectionFromClient;
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
public class JFrameGridTextStimuli extends JFrame implements ActionListener{
    ConnectionToServer cts;
    
    //Vector allPanels = new Vector();
    
    JClientTextP[][] jctps;
    
    long serverIDOfSet;
    JPanel north;
    JButton south;
    
    public JFrameGridTextStimuli(ConnectionToServer cts, long idofset,  int rrows,int ccolumns,  final String[] [] texts, Color[][] innerMostc, Color[][] innerc  ,int width, int height)  {
        this.cts=cts;
        this.serverIDOfSet=idofset;
        north = new JPanel();
        this.setLayout(new BorderLayout());
        north.setLayout(new GridLayout(rrows, ccolumns, 0, 0));
        this.getContentPane().add(north, BorderLayout.NORTH);
        
        JButton south = new JButton("Select");
        south.addActionListener(this);
        this.getContentPane().add(south, BorderLayout.SOUTH);
        
       
       
         initialize();
        
  
        this.jctps= new  JClientTextP[rrows][ccolumns];        
        for(int i=0;i< rrows;i++){
            for(int j=0;j< ccolumns;j++){
               JClientTextP jctp = new JClientTextP(this,i,j);
               jctp.setText(texts[i][j]);
               jctp.setPreferredSize(new Dimension(width,height));
              
               jctp.setSelection(innerMostc[i][j], innerc[i][j]);
               north.add(jctp);
               jctps[i][j]=jctp;
           }   
        }
        this.pack();
        //this.setResizable(false);
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
    
     
    
    
    public void changeTexts(final long serverIDD, final String[][] texts){
      SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        serverIDOfSet = serverIDD;
        for(int i=0;i<texts.length;i++){
            for(int j=0;j<texts[i].length;j++){
                 jctps[i][j].setText(texts[i][j]);
            }
        }
        repaint();
        }
      });
        
        
    }
     
    //jfgridstimuli.changeSelection(mgsstc.getServerID(),mgsstc.getSelections(),mgsstc.getColour());
         
    public void changeAllSelections(final long serverIDD, final Color[][] cInnerMost , final Color[][] cInner){
       //Selections can be integer (index) or String (content)
       SwingUtilities.invokeLater(new Runnable(){
           public void run(){
           serverIDOfSet=serverIDD;
         
           for(int i=0;i < cInnerMost.length;i++){
             for(int j=0;j<cInnerMost[i].length;j++){
                 JClientTextP jctp = jctps[i][j];
                 jctp.setSelection(cInnerMost[i][j], cInner[i][j]);
             }
           }
         }});
    }
        
         
         
         
 public void setTextSelected( JPanel jp,String text, long timeOfPress, Color innerColor, Color innerMostColor, int x, int y){
       
        int index=-1;
       
        try{
        MessageGridTextStimuliSelectionFromClient mgss = new MessageGridTextStimuliSelectionFromClient(cts.getEmail(),cts.getUsername(), this.serverIDOfSet,index, text,timeOfPress, innerColor, innerMostColor);
        cts.sendMessage(mgss);
        }catch(Exception e){
            cts.sendErrorMessage(e);
        }
    }
    
    public void actionPerformed(ActionEvent e) { 
        MessageGridTextStimuliSelectionFromClient mgss = new MessageGridTextStimuliSelectionFromClient(cts.getEmail(),cts.getUsername(), serverIDOfSet, -9999, "SELECTBUTTON",e.getWhen(), null,null);
        
        cts.sendMessage(mgss);
    }
    
}