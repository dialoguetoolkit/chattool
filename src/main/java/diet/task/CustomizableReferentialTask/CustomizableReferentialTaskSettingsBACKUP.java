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
public class CustomizableReferentialTaskSettingsBACKUP  {

   
    DefaultConversationController cC;
    
    
    public long durationOfStimulus;// =  CustomDialog.getLong("How long should the stimuli be displayed for?", 600000);
    public long durationOfGame;// = CustomDialog.getLong("How long is a game?", 60000);
    public boolean showButtons;// = CustomDialog.getBoolean("Do you want to show buttons underneath the stimuli on the clients?", "Buttons", "No Buttons");
     
    
    
    public double correctscoreinrement = 10;//CustomDialog.getDouble("What is the score increment for correct guesses?", 10);
    public double incorrectpenalty = 5;//CustomDialog.getDouble("What is the point penalty for incorrect guesses?", 5);
    
   
    
    public String directoryname =  "tangramset01";//"tangramset02directortraining";
      
    public int stimuluswidth = -1;
    public int stimulusheight=-1;   
    
    public boolean isinphysicalfolder = true;
    
    public Vector<String[]> vstimuli = new Vector();
    public Vector<String[]> vstimuliFULL = new Vector();
    
    public Hashtable htIMAGE = new Hashtable();
    
    
    public boolean telegram = false;
    public boolean deleteStimulusAfterEachTrial =true;
    
    
    public boolean showScoreOnEachGame = true;
    public boolean showIfSelectionWasCorrrectOrIncorrect  = true;
    public boolean advanceToNextManually = true;
   
    public boolean randomizeSequence = false;
    
    // option of looping around when done
    
    
    public CustomizableReferentialTaskSettingsBACKUP(DefaultConversationController cC,  boolean telegram,  String stimulisubfolderCANBENULL, String sequencefilenameCANBENULL){
         //this.durationOfStimulus=durationOfStimulus;    
         //if(2<5)System.exit()
         
         this.cC=cC; 
         
         if(stimulisubfolderCANBENULL!=null  && sequencefilenameCANBENULL!=null){
              String fn =    System.getProperty("user.dir") +   File.separator        +  "experimentresources"+File.separator+"stimuli" +  File.separator+stimulisubfolderCANBENULL + File.separator+ sequencefilenameCANBENULL;
              File seqF = new File(fn);
              if(seqF.exists()){
                  loadFromFile(seqF);
              }
              else{
                  CustomDialog.showDialog("Can't find the file:\n\n"
                     + sequencefilenameCANBENULL+"\n\n"
                     + "in the folder:\n\n"
                     + stimulisubfolderCANBENULL +"\n\n"
                     + "The system is looking in: \n\n"+fn+ "\n\n\n"
                     +  "You might be able to find it manually:");
             
                  loadFromFile(null);
               }
         }
         else{
              loadFromFile(null);
         }
         
        
         
         
        
         
         
         
         
           randomizeSequence = CustomDialog.getBoolean("Do you want to randomize the order of the stimuli?", "randomize", "keep order");
          if(randomizeSequence){
               Random r = new Random();
               
               Vector<String[]> vRandomized = new Vector();
               for(int i=0;i<this.vstimuli.size();i++){
                   Object o = this.vstimuli.elementAt(i);
                   vRandomized.insertElementAt((String[])o, r.nextInt(vRandomized.size()+1));
               }
               this.vstimuli=vRandomized;
               this.vstimuliFULL=(Vector<String[]>)vRandomized.clone();
               
               
          }
          
         
         
         
          durationOfGame = CustomDialog.getLong("How long is a game?", 60000);
          durationOfStimulus =  CustomDialog.getLong("How long should the stimuli be displayed for?", 600000);
          showButtons = CustomDialog.getBoolean("Do you want to show buttons underneath the stimuli on the clients?", "Buttons", "No Buttons");
     
         
         
         
         this.telegram=telegram;
         
         //loadStimuliList();
         Dimension d =  getImageHeights();
         if(d.width==-1||d.height==-1) {
             CustomDialog.showDialog("The server can't find the images in the JAR file\nTry recompiling the project!");
         }
         stimuluswidth=d.width;
         stimulusheight=d.height;
         
         final CustomizableReferentialTaskSettingsBACKUP crtthis = this;
         
         if(telegram) this.deleteStimulusAfterEachTrial = CustomDialog.getBoolean("Do you want to delete the stimuli from the telegram window after each trial?", "Delete", "Keep");
         
        
         
         
        showScoreOnEachGame = CustomDialog.getBoolean("Do you want to show the score after each trial?", "Show", "Don`t show");
        showIfSelectionWasCorrrectOrIncorrect = CustomDialog.getBoolean("Do you want to show if the previous trial was correct / incorrect?", "Show", "Don`t show");

        advanceToNextManually =   !CustomDialog.getBoolean("Do you want participants to advance automatically\n or do participants have to type /NEXT to advance to next stimuli?", "Automatic advancement", "Manual advancement by typing /NEXT");
        
         
         
    }
 
    
    
   private void loadFromFile(File stimulisequence){
       
       if(stimulisequence==null || !stimulisequence.exists()){
       String userdir = System.getProperty("user.dir");
       String directory = (userdir+File.separator+"experimentresources"+ File.separator+ "stimuli");
       
       CustomDialog.showDialog("Important:\n\n(1) The stimuli must be in a subdirectory of /experimentresources/stimuli.\n"
               + "(2) The file containing the stimuli sequence must be in the same subdirectory as the stimuli.\n"
               + "\n(See usermanual for more details!)");
       stimulisequence = CustomDialog.loadFileWithExtension(directory, "Choose the text file containing the sequences of stimuli. \nIt must be in the same directory as the stimuli", "txt", "Text file containing the list of stimuli and correct answers");

       }
       
        String foldername = stimulisequence.getParentFile().getName();
       this.directoryname=foldername;
       System.err.println("Looking in "+directoryname);
       if(stimulisequence==null)CustomDialog.showDialog("Couldn't find the file!");
       Vector data = new Vector();
        try{
            
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(stimulisequence), "UTF-8"));
            //BufferedReader br = new BufferedReader(new FileReader(stimulisequence)); 
            String st;
            String header = br.readLine();
            while ((st = br.readLine()) != null){ 
                 System.out.println(st);
                 
                 //st= st.replace(" ", "");
                 String[] row =  st.split("¦");
                 data.addElement(row);
                 System.out.println("Split to:");
                 for(int i =0;i<row.length;i++){
                     System.out.print(row[i]+ "|");
                     if(i<4) row[i]=row[i].replace(" ", "");
                 }
                 System.out.println("");
                  
                 
                 
            } 
        }catch(Exception e){
            e.printStackTrace();
        }
      
        this.vstimuli=(Vector<String[]>)data.clone();
        this.vstimuliFULL=(Vector<String[]>)data.clone();
        try{
           this.checkStimuliFilesExist();
        }catch(Exception e){
            e.printStackTrace();
            Conversation.saveErr(e);
            CustomDialog.showDialog("Error loading the stimuli - it could be that there is empty space at the end of the script containing the stimuli names! Please check and restart!");
        }
   }
  
    
   private void checkStimuliFilesExist(){
       String userdir = System.getProperty("user.dir");
       String dir = (userdir+File.separator+"experimentresources"+ File.separator+ "stimuli"+ File.separator+directoryname);
       
       
       for(int i=0;i<this.vstimuliFULL.size();i++){
           File f1 = new File(dir, (String)vstimuliFULL.elementAt(i)[0]);
           if(!f1.exists()){
               CustomDialog.showDialog("Can't find file:\n\n"+f1.getAbsolutePath()+"\n\nCheck and fix the stimuli sequence before running the experiment!" );
           }
           File f2 = new File(dir, (String)vstimuliFULL.elementAt(i)[1]);
           if(!f2.exists()){
               CustomDialog.showDialog("Can't find file:\n\n"+f2.getAbsolutePath()+"\n\nCheck and fix the stimuli sequence before running the experiment!" );
           }
           
           try{
                BufferedImage image1 = ImageIO.read(f1);
                BufferedImage image2 = ImageIO.read(f2);
                this.htIMAGE.put(vstimuliFULL.elementAt(i)[0],image1);
                this.htIMAGE.put(vstimuliFULL.elementAt(i)[1],image2);
      
          } catch (Exception e){
               e.printStackTrace();
               Conversation.saveErr(e);
               CustomDialog.showDialog("Error loading the file - it could be that there is empty space at the end of the script:");
           }
           
           
       }
   }
   
      
   
    
    Dimension getImageHeights(){
        return this.getImageHeightsFILE();
    }
    
    private Dimension getImageHeightsFILE(){   
         String userdir = System.getProperty("user.dir");
         String dir = (userdir+File.separator+"experimentresources"+ File.separator+ "stimuli"+ File.separator+directoryname);
         String firstfilename = dir+"/"+vstimuliFULL.elementAt(0)[0];
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
    
    
    private Dimension getImageHeightsJAR(){   
        String firstfilename = directoryname+"/"+vstimuliFULL.elementAt(0)[0];
        System.err.println("Trying to get image heights for: "+firstfilename);
        BufferedImage bimg =null ;
        try {   
             ClassLoader cldr = CustomizableReferentialTaskSettingsBACKUP.class.getClassLoader();
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
    
    
  

    public Vector<String[]>[] getVstimuli() {
        // returns two vectors containing the stimuli
        Vector<String[]> v1 = new Vector();
        Vector<String[]> v2 = new Vector();
        
        if(this.randomizeSequence){
               Vector<String[]> vrnd = randomizeSequence(vstimuli);
               v1 = duplicateVectorOfStringArray(vrnd);
               v2 = duplicateVectorOfStringArray(vrnd);
               return new Vector[]{v1,v2};
        }
        else{
             v1 = duplicateVectorOfStringArray(vstimuli);
             v2 = duplicateVectorOfStringArray(vstimuli);
             return new Vector[]{v1,v2};
        }
            
       
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
    
    
    
    
    
    
     
     
     
     
}
