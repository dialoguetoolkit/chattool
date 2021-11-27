/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.CustomizableReferentialTask;

import diet.attribval.AttribVal;
import diet.message.MessageTask;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.task.ProceduralComms.JTrialTimerActionRecipientInterface;
import diet.task.TaskControllerInterface;
import diet.tg.TelegramParticipant;
import diet.utils.HashtableWithDefaultvalue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author gj
 */
public class CustomizableReferentialTaskSettings  {

   
    DefaultConversationController cC;
    
    
    public long durationOfStimulus;// =  CustomDialog.getLong("How long should the stimuli be displayed for?", 600000);
    public long durationOfGame;// = CustomDialog.getLong("How long is a game?", 60000);
    public boolean showButtons;// = CustomDialog.getBoolean("Do you want to show buttons underneath the stimuli on the clients?", "Buttons", "No Buttons");
     
    
    
    public double correctscoreinrement = 10;//CustomDialog.getDouble("What is the score increment for correct guesses?", 10);
    public double incorrectpenalty = 5;//CustomDialog.getDouble("What is the point penalty for incorrect guesses?", 5);
    
   
    
    public String directoryname =  "";
      
    public int stimuluswidth = -1;
    public int stimulusheight=-1;   
    
    public boolean isinphysicalfolder = true;
    
    
    public Vector<String[]> vs= new Vector();
    
    //public Vector<String[]> vstimuli = new Vector();
   // public Vector<String[]> vstimuliFULL = new Vector();
    
    public Hashtable htIMAGE = new Hashtable();
    
    
    public boolean telegram = false;
    public boolean deleteStimulusAfterEachTrial =true;
    
    
    public boolean showScoreOnEachGame = true;
    public boolean showIfSelectionWasCorrrectOrIncorrect  = true;
    public boolean advanceToNextManually = true;
   
    public String filename;
    
    // option of looping around when done
    
    
    public CustomizableReferentialTaskSettings(DefaultConversationController cC, Vector<String[]> vs , String foldername, String filename){

         this.cC=cC; 
         this.vs = vs;
            
         this.directoryname=foldername;
         this.filename=filename;
       
         Dimension d =  getImageHeights();
         if(d.width==-1||d.height==-1) {
             CustomDialog.showDialog("The server can't find the images...\nTry recompiling the project!");
         }
         stimuluswidth=d.width;
         stimulusheight=d.height;
             
         
    }
 

   
    
    Dimension getImageHeights(){
        return this.getImageHeightsFILE();
    }
    
    private Dimension getImageHeightsFILE(){   
         String userdir = System.getProperty("user.dir");
         String dir = (userdir+File.separator+"experimentresources"+ File.separator+ "stimuli"+ File.separator+directoryname);
         String firstfilename = dir+File.separator+vs.elementAt(0)[0];
         System.err.println("Trying to get image heights for: "+firstfilename);
         BufferedImage bimg =null ;
         try{
             File f = new File(firstfilename);
             bimg = ImageIO.read(f);
             int width = bimg.getWidth();
             int height = bimg.getHeight();
             System.err.println("Returning size as: ("+width+","+height+")");
             return (new Dimension(width,height)); 
         }
         catch(Exception e){
             e.printStackTrace();
         }
         return new Dimension(-1,-1);
    }
    
    
    
    
    
  

    public Vector<String[]> getVstimuli() {
        // returns two vectors containing the stimuli
        Vector<String[]> v1 = new Vector();
        //Vector<String[]> v2 = new Vector();
     
         v1 = duplicateVectorOfStringArray(vs);
         //v2 = duplicateVectorOfStringArray(vs);
         //return new Vector[]{v1,v2};
         return v1;
            
       
    }

    
    
    
    public static Vector<String[]> randomizeSequence(Vector<String[]> v){
        Random r = new Random();
        Vector<String[]> vRandomized = new Vector();
        for(int i=0;i<v.size();i++){
             Object o = v.elementAt(i);
             vRandomized.insertElementAt((String[])o, r.nextInt(vRandomized.size()+1));
        }
        Vector<String[]> v1 = vRandomized;
        //Vector<String[]> v2 = (Vector<String[]>)v1.clone();
        return v1;
    }
     
    
    
    
    
    
    static public Vector<String[]> duplicateVectorOfStringArray(Vector<String[]> vSA){
        Vector<String[]> vSANEW = new Vector();
        for(int i=0;i<vSA.size();i++){
            String[] sA = vSA.elementAt(i);
            String[] sACOPY = new String[sA.length];
            for(int j=0;j<sA.length;j++){
                sACOPY[j]=sA[j]+"";
            }
            vSANEW.add(sACOPY);
        }
        return vSANEW;
    }
    
    
    
    
    
    
   
    
    
    
    
    
    private Dimension getImageHeightsJARDEPRECATED(){   
        String firstfilename = directoryname+"/"+vs.elementAt(0)[0];
        System.err.println("Trying to get image heights for: "+firstfilename);
        BufferedImage bimg =null ;
        try {   
             ClassLoader cldr = CustomizableReferentialTaskSettings.class.getClassLoader();
             URL url = cldr.getResource(firstfilename);
             bimg = ImageIO.read(url);
             int width = bimg.getWidth();
             int height = bimg.getHeight();
             return (new Dimension(width,height)); 
       } catch (IOException ex) {
            // handle exception...
           ex.printStackTrace();   
       } 
        return new Dimension(-1,-1); 
    }   
     
     
     
}
