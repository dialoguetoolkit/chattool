/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.client;

import diet.task.stimuliset.SerializableImage;
import diet.utils.StringOperations;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author sre
 */
public class JFrameSubliminalStimuli extends JFrame{
    
    
    
    //feedback correct / wrong / indecision   (same length of time)
    
    
    
    
    
    JPanelSubliminalStimuli jpss;// = new JPanelSubliminalStimuli();
    Hashtable htStimuli = new Hashtable();
    ConnectionToServer cts;
    
    
    public JFrameSubliminalStimuli(ConnectionToServer cts,int width,int height){
        this.cts=cts;
        jpss = new JPanelSubliminalStimuli();
        jpss.setPreferredSize(new Dimension(width,height));
         //this.setPreferredSize(new Dimension(800,600));
         this.getContentPane().add(jpss);
         this.pack();
         this.setVisible(true);
    }
    
    
    public void addStimuliSet(Vector v){
         
        Graphics g = this.jpss.getGraphics();
         for(int i=0;i<v.size();i++){
            SerializableImage si = (SerializableImage)v.elementAt(i) ;
            BufferedImage bi = si.getImage();
            //g.drawImage(bi, 0, 0, null);
            htStimuli.put(si.getName(), bi);  
         }
         
         
    }
    
    
    //Fixation1
    //Stimulus1
    //Blankscreen1
    //Fixation2
    //Stimulus2
    //Blankscreen2time
    
    boolean isDisplayingStimuli = false;
    
    
   
    
    
    public   void displayStimulus(final long fixation1time, final long stimulus1time
                               ,final long blankscreen1time
                               ,final long fixation2time, final long stimulus2time
                               ,final long blankscreen2time,
                               String stimulus1ID, String stimulus2ID ){
        
        try{
            
            
            
             
            final BufferedImage stimulusbackground  = (BufferedImage)this.htStimuli.get("stimulusbackground");
            final BufferedImage biFixation1 = (BufferedImage)this.htStimuli.get("fixation");
            final BufferedImage biFixation2 = (BufferedImage)this.htStimuli.get("fixation");
            final BufferedImage biStimulus1 = (BufferedImage)this.htStimuli.get(stimulus1ID);
            final BufferedImage biStimulus2 = (BufferedImage)this.htStimuli.get(stimulus2ID);
            final BufferedImage biIndividualQuestion = (BufferedImage)this.htStimuli.get("individualquestion");             
            
            final Graphics g = this.jpss.getGraphics();
            //g.drawImage(background, 0, 0, null);
            
            
            Thread t = new Thread(){
            
            public void run(){
            
            long actualFixation1Time = -9999;
            long actualStimulus1Time = -9999;
            long actualFixation2Time = -9999;
            long actualStimulus2Time = -9999;
            long actualQuestionTime  = -9999;
                
                //fixation1time + stimulus1time + blankscreen1time + fixation2time + stimulus2time +blankscreen2time
                
            //try{Thread.sleep(individualinstructionstime);}catch (Exception e){e.printStackTrace();}
            jpss.displayImage(biFixation1);
            actualFixation1Time = new Date().getTime();
            
            //this.displayText("HERE1..bifixation1", Color.black, null,100, 100);
            try{Thread.sleep(fixation1time);}catch (Exception e){e.printStackTrace();}
            
            actualStimulus1Time= new Date().getTime();
            jpss.displayImage(biStimulus1);
            //this.displayText("HERE2...bistimulus1", Color.black, null,120, 120);
            try{Thread.sleep(stimulus1time);}catch (Exception e){e.printStackTrace();}
            
            
            
            jpss.displayImage(stimulusbackground);
            //this.displayText("HERE2...bistimulus1", Color.black, null,120, 120);
            try{Thread.sleep(blankscreen1time);}catch (Exception e){e.printStackTrace();}
            
            actualFixation2Time = new Date().getTime();
            jpss.displayImage(biFixation2);
            //this.displayText("HERE3...bifixation2", Color.black, null,140, 140);
            try{Thread.sleep(fixation2time);}catch (Exception e){e.printStackTrace();}
            
            actualStimulus2Time = new Date().getTime();
            jpss.displayImage(biStimulus2);
            //this.displayText("HERE4....biStimulus2", Color.black, null,160, 160);
            try{Thread.sleep(stimulus2time);}catch (Exception e){e.printStackTrace();}
            
            jpss.displayImage(stimulusbackground);
            //this.displayText("HERE2...bistimulus1", Color.black, null,120, 120);
            try{Thread.sleep(blankscreen2time);}catch (Exception e){e.printStackTrace();}
            
             actualQuestionTime = new Date().getTime();
             jpss.displayImage(biIndividualQuestion);
             
            
             
             
             
             
            //cts.sendClientEvent("INDIVIDUALSTIMULUS "+ actualFixation1Time+"_"+actualStimulus1Time+"_"+actualFixation2Time+"_"+actualStimulus2Time+"_"+actualQuestionTime);
            
            
            cts.cEventHandler.ciet.deprecated_addClientEvent("JFRAMESUBLIMINALSTIMULI", -999999 ,"actiontype"  ,"INDIVIDUALSTIMULUS "+ actualFixation1Time+"_"+actualStimulus1Time+"_"+actualFixation2Time+"_"+actualStimulus2Time+"_"+actualQuestionTime);
            
            
            
            }
            };
            t.start();
             
             
             
         }catch (Exception e){
             e.printStackTrace();
         }
            
               
    }
    
    
    private BufferedImage loadLocalFile(String localFileName){
        try{
            String s = System.getProperty("user.dir");
            //File fil = new File(s+File.separatorChar+"stimuli"+File.separatorChar+"faces"+File.separatorChar+"stimuli10");
            
            String[] fileName = StringOperations.splitOnlyText(localFileName);
            String newLocalfileName =s;
            newLocalfileName = newLocalfileName+fileName[0];
            for(int i=1;i<fileName.length-1;i++){
                newLocalfileName = newLocalfileName+File.separatorChar+fileName[i];
                System.err.println("NEWLOCALFILENAME "+fileName[i]+"........"+newLocalfileName);    
            }
            
            newLocalfileName=newLocalfileName+".png";
            File f = new File(newLocalfileName);
            System.err.println("TRYING TO LOAD: SSSS"+s+"........"+f.getName());
            BufferedImage bi = ImageIO.read(f);
            //SerializableImage si = new SerializableImage(bi,fileName[fileName.length-1]);
            return bi;
        }catch(Exception e){
            e.printStackTrace();
           
        }
        return null;
        
    } 
    
    
     
    
    
    
    
    
    
    
    
    
    public void displayText(final String text,final Color textColour,final String nameOfPanel,final int positionX,final int positionY, long lengthOfTime){
       try{ 
          BufferedImage background =null;
         if(nameOfPanel!=null ){
             
             if(!nameOfPanel.equalsIgnoreCase("")){
                 background = (BufferedImage)this.htStimuli.get(nameOfPanel);
                 if(background==null){
                     background = this.loadLocalFile(nameOfPanel);
                 }
                 
             }
         } 
         final BufferedImage backgroundfinal = background;
                long timeOfDisplayText = new Date().getTime();
                jpss.displayText(text, textColour, backgroundfinal, positionX, positionY);
                cts.cEventHandler.ciet.deprecated_addClientEvent("JFRAMESUBLIMINALSTIMULI", -99999    ,"actiontype", "DISPLAYEDBUFFEREDIMAGE "+text+"_"+nameOfPanel+"_"+timeOfDisplayText+"_"+lengthOfTime);
         
         
         
         if(lengthOfTime>0){Thread.sleep(lengthOfTime);}
         System.err.println(lengthOfTime+".......");
         //System.exit(-5);
       }catch (Exception e){
           e.printStackTrace();
       }
       
    }
    
    
    
    
    
     
     
    
    public void displayTextOLD(String text,Color textColour,String nameOfPanel,int positionX,int positionY, long lengthOfTime){
       try{ 
         Graphics g = this.jpss.getGraphics();
         if(nameOfPanel!=null ){
             
             if(!nameOfPanel.equalsIgnoreCase("")){
                 BufferedImage background = (BufferedImage)this.htStimuli.get(nameOfPanel);
                 
                 g.drawImage(background, 0, 0, null);
             }
         }  
        
         Graphics2D g2d = (Graphics2D)g;
         int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
         int fontSize = (int)Math.round(25.0 * screenRes / 72.0);
         Font font = new Font("Arial", Font.PLAIN, fontSize);   
         g2d.setFont(font);
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g.setColor(textColour);
         g.drawString(text, 100, 100);
         Thread.sleep(lengthOfTime);
         System.err.println(lengthOfTime+".......");
         //System.exit(-5);
       }catch (Exception e){
           e.printStackTrace();
       }
       
    }
    
    
    
    
   
               
   
    
    
    
    
    
    
    
    
    
}
