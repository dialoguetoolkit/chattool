/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.gridstimuli;

import diet.client.ConnectionToServer;
import diet.message.MessageGridTextStimuliSelectionFromClient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author gj
 */
public class JGridStimuli2016 extends JFrame{
    
      ConnectionToServer cts;
      JGridButton[][] jps;
      long serverIDOfSet;
      int gridrows;
      int gridcolumns;
      
      
    
       public JGridStimuli2016(int gridrows, int gridcolumns, int w){
           super("stimuli");
           //this.setVisible(true);
           JPanel north = new JPanel();
           
           this.setLayout(new BorderLayout());
           north.setLayout(new GridLayout(gridrows, gridcolumns, 0, 0));
           this.getContentPane().add(north, BorderLayout.NORTH);
        
           
           JButton south = new JButton("Select");
         
           this.getContentPane().add(south, BorderLayout.SOUTH);
           
           jps = new JGridButton[gridrows][gridcolumns];
           
           for(int i=0;i<gridrows;i++){
                for(int j=0;j<gridcolumns;j++){
                   JGridButton jgb  = new JGridButton(this, "", i,j);
                   jgb.setPreferredSize(new Dimension(w,w));
                   jgb.setMaximumSize(new Dimension(w,w));
                    jgb.setMinimumSize(new Dimension(w,w));
                   north.add(jgb);
                   jps[i][j]=jgb;     
                   
               }   
           }
           
           
           this.setResizable(false);
           this.setImageOfJButton(0, 0, "/gridstimuli/stimuliset5/A01.png");
           this.setImageOfJButton(0, 1, "/gridstimuli/stimuliset5/A02.png");
           this.setImageOfJButton(0, 2, "/gridstimuli/stimuliset5/A03.png");
           this.setImageOfJButton(0, 3, "/gridstimuli/stimuliset5/A04.png");
           this.setImageOfJButton(0, 4, "/gridstimuli/stimuliset5/A05.png");
           this.setImageOfJButton(0, 5, "/gridstimuli/stimuliset5/A06.png");
           this.setImageOfJButton(1, 0, "/gridstimuli/stimuliset5/A07.png");
           this.pack();
           this.setVisible(true);
       }
       
       
   
       public void setTextOfJButton(final int x,  final int y,final String name){
        if(x <0)return;
        if(x > jps.length)return;
        if(y>jps[x].length)return;
           
         SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                
                try{
                      
                      JButton jb =  jps[x][y];
                      jb.setIcon(null);          
                      jb.setText(name);
                }catch(Exception e){
                      e.printStackTrace();
                }
            }
        });
           
    }   
    
        public void setImageOfJButton(final int x,  final int y,final String filenameINJAR){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                
                try{
                      URL imageURL = getClass().getResource(filenameINJAR);
                      ImageIcon iicon = new ImageIcon(imageURL);
                      JButton jb =  jps[x][y];
                      jb.setIcon(iicon);          
                      ImageIcon ii = new ImageIcon(imageURL);
                      jb.setIcon(new ImageIcon(ii.getImage().getScaledInstance(jps[x][y].getWidth(), jps[x][y].getHeight(),java.awt.Image.SCALE_SMOOTH)));
                }catch(Exception e){
                      
                }
            }
        });
           
    }   
       
       
    
       
       
   public void setTexts(final long serverIDD, String[] texts){
     
        
    }
       
   
     public static void main(String[] s){
         
         SwingUtilities.invokeLater(new Runnable(){
             public void run(){
                  JGridStimuli2016   jgs = new JGridStimuli2016(4,5, 200);         
             }
         });
        
     }
      
       
       
        public void setTextSelected( JPanel jp,String text, long timeOfPress, Color innerColor, Color innerMostColor){
         //send serverID
            int index=-1;
           
            try{
                MessageGridTextStimuliSelectionFromClient mgss = new MessageGridTextStimuliSelectionFromClient(cts.getEmail(),cts.getUsername(), this.serverIDOfSet,index, text,timeOfPress, innerColor, innerMostColor);
                cts.sendMessage(mgss);
        }catch(Exception e){
            cts.sendErrorMessage(e);
        }
    }
    
    
}
