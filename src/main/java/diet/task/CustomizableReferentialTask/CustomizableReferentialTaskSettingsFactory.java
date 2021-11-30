/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.CustomizableReferentialTask;

import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.CustomDialog;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Vector;
import javax.imageio.ImageIO;

/**
 *
 * @author g
 */
public class CustomizableReferentialTaskSettingsFactory {

   DefaultConversationController cC;
    
    
    public long durationOfStimulus;// =  CustomDialog.getLong("How long should the stimuli be displayed for?", 600000);
    public long durationOfGame;// = CustomDialog.getLong("How long is a game?", 60000);
    public boolean showButtons;// = CustomDialog.getBoolean("Do you want to show buttons underneath the stimuli on the clients?", "Buttons", "No Buttons");
      
    public double correctscoreinrement = 10;//CustomDialog.getDouble("What is the score increment for correct guesses?", 10);
    public double incorrectpenalty = 5;//CustomDialog.getDouble("What is the point penalty for incorrect guesses?", 5);
       
    //public String directoryname =  "tangramset01";//"tangramset02directortraining";
      
    public int stimuluswidth = -1;
    public int stimulusheight=-1;   
    
    public boolean isinphysicalfolder = true;
    
    //public Vector<String[]> vstimuli = new Vector();
    //public Vector<String[]> vstimuliFULL = new Vector();
    
    public Hashtable htIMAGE = new Hashtable();
    
    
    public boolean telegram = false;
    public boolean deleteStimulusAfterEachTrial =true;
    
    
    public boolean showScoreOnEachGame = true;
    public boolean showIfSelectionWasCorrrectOrIncorrect  = true;
    public boolean advanceToNextManually = true;
   
    public boolean randomizeSequence = false;
    
    String stimulisettingsType = "singlesorted";   //
    
    Vector<CustomizableReferentialTaskSettings> vcrts = new Vector();
    
    boolean isSingleSet = true;
    boolean loopAutomaticallyWhenMultipleSetsAreAllUsed = true;

    
    
    public CustomizableReferentialTaskSettingsFactory(DefaultConversationController cC, boolean telegram, String folder, String filename) {
        this.cC = cC;
        this.telegram = telegram;
        String fn =    System.getProperty("user.dir") +   File.separator        +  "experimentresources"+File.separator+"stimuli" +  File.separator+folder + File.separator+ filename;
        File seqF = new File(fn);
        if(seqF.exists()){
                  this.loadStimuli_SingleSet(seqF);
        }
        else{
                  CustomDialog.showDialog("Can't find the file:\n\n"
                     + folder+"\n\n"
                     + "in the folder:\n\n"
                     + filename +"\n\n"
                     + "The system is looking in: \n\n"+fn+ "\n\n\n"
                     +  "You might be able to find it manually:");
             
                  loadStimuli_SingleSet(null);
               }
        }
            
        

    
       
    
    
       
    
    
    
    
    
    
    
    
    public CustomizableReferentialTaskSettingsFactory(DefaultConversationController cC, boolean telegram) {
        this.cC = cC;
        this.telegram = telegram;
        String[] options = {"Single set", "Multiple sets"};
        stimulisettingsType = CustomDialog.show2OptionDialog(options, "What kind of stimuli set(s) do you want to use?", "Stimuli");
        
        if(stimulisettingsType.equalsIgnoreCase(options[0])){
            this.isSingleSet=true;
            this.loadStimuli_SingleSet(null);
           
            
        }
        else if (stimulisettingsType.equalsIgnoreCase(options[1])){
            this.isSingleSet=false;
            this.loadStimuli_MultipleSet();
            
        }
        
    }
    
    
     public void loadStimuli_MultipleSet(){
          
           String userdir = System.getProperty("user.dir");
           String directory = (userdir+File.separator+"experimentresources"+ File.separator+ "stimuli");
           CustomDialog.showDialog("In the next step you need to select a text file\nthat contains a list of stimuli sets");
           
           File f = CustomDialog.loadFileWithExtension(directory, "Select the text file containing the list of stimuli sets", "txt", "txt");
           
           Vector<String> fileNames = new Vector();
           //String textFile = "";
           try{
             BufferedReader br = new BufferedReader(new FileReader(f));
             String line = br.readLine();
             while (line != null) {
                  fileNames.addElement(line);    
                  line = br.readLine();       
             }  
             br.close();
           }
           catch(Exception e){
               e.printStackTrace();
               Conversation.saveErr(e);
               CustomDialog.showDialog("There was an error loading the file containing the list of stimuli sets.\nTo prevent the experiment starting with missing stimuli, the program will exit.");
               System.exit(-55);
           }
           
           
           Vector <CustomizableReferentialTaskSettings> vc = new Vector();
           
           for (int i=0;i<fileNames.size();i++){
               System.err.println("HEREAA-loading "+i);
               String s = fileNames.elementAt(i);
               File fStimuli = new File(f.getParentFile(),s);
               
               if(!fStimuli.exists()){
                   CustomDialog.showDialog("Error!\n\n"+s+ "\n\ndoes not exist\n\nPerhaps there is a typo?\n\nProgram will close.");
                   System.exit(-55);
               }
                    Conversation.printWSln("Main", "loading the list of stimuli from "+s);
                    Vector<String[]> vs = this.loadFromFile(fStimuli);
                    Conversation.printWSln("Main", "checking that the stimuli in "+s+ " exist");
                    CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(cC, vs, f.getParentFile().getName(),s);
                    vc.add(crts);
                   
                    
              
               
               
               
               
           }
           this.askBooleanQuestions();
           this.loopAutomaticallyWhenMultipleSetsAreAllUsed = CustomDialog.getBoolean("When all sets are used, do you want to\nloop and use them again or prompt user what to do?", "loop and use again", "prompt user what to do");
           Conversation.printWSln("Main", "Now making sure that each list has images that can be found");
           
           
           for(int i=0;i < vc.size() ; i++ ){
               CustomizableReferentialTaskSettings crts = vc.elementAt(i);
               crts.durationOfGame = this.durationOfGame;
               crts.durationOfStimulus =  this.durationOfStimulus;
               
               crts.showButtons = this.showButtons;
               crts.deleteStimulusAfterEachTrial = this.deleteStimulusAfterEachTrial;
               crts.showScoreOnEachGame = this.showScoreOnEachGame;
               crts.showIfSelectionWasCorrrectOrIncorrect = this.showIfSelectionWasCorrrectOrIncorrect;
               crts.advanceToNextManually =   this.advanceToNextManually;
               crts.telegram=this.telegram;
               this.vcrts.add(crts);
           }
         
     }
    
   
     
     
     
            
            
    
    
    public void loadStimuli_SingleSet(File f){
        
        if(f!=null && !f.exists()){
            CustomDialog.showDialog("Could not find the file containing the list of stimuli.\nPlease choose manually.");
            f = this.getScriptFile();
        }
        
        else if (f==null){
            f = this.getScriptFile();
        }
        if(f==null){
            CustomDialog.showDialog("Could not load the file containing the list of stimuli! Exiting");System.exit(-45);
        }
        
        Vector<String[]> vstimuli = this.loadFromFile(f);
        
        this.askBooleanQuestions();
        
        if(this.randomizeSequence){
            vstimuli = CustomizableReferentialTaskSettings.randomizeSequence(vstimuli);
            Conversation.printWSln("Main", "Randomizing the order of the stimuli");
        }
        
        
        CustomizableReferentialTaskSettings crts = new CustomizableReferentialTaskSettings(cC, vstimuli, f.getParentFile().getName(),f.getName());
        //crts.directoryname = f.getParent();
        //System.err.println("The name is "+f.getName());
        //System.err.println("The path is "+f.getPath());
        //System.err.println("The parent is "+f.getParent());
        
        crts.durationOfGame = this.durationOfGame;
        crts.durationOfStimulus =  this.durationOfStimulus;
        crts.showButtons = this.showButtons;
        crts.deleteStimulusAfterEachTrial = this.deleteStimulusAfterEachTrial;
        crts.showScoreOnEachGame = this.showScoreOnEachGame;
        crts.showIfSelectionWasCorrrectOrIncorrect = this.showIfSelectionWasCorrrectOrIncorrect;
        crts.advanceToNextManually =   this.advanceToNextManually;
        crts.telegram=this.telegram;
        this.vcrts.add(crts);
    }
    
    
    
    Vector<String[]> usedSets = new Vector();
    Hashtable htNames = new Hashtable();
    
    int indexOfNextFree =0;
    public CustomizableReferentialTaskSettings getNextCustomizableReferentialTaskSettings(){
        if(this.isSingleSet) return this.vcrts.firstElement();
        
        if(indexOfNextFree>=this.vcrts.size()){
            if(this.loopAutomaticallyWhenMultipleSetsAreAllUsed){
                Conversation.printWSln("Main", "looping round and reusing stimuli sets");
                this.indexOfNextFree=0;
            }
            else{
                CustomDialog.showDialog("This is an alert that all the stimuli sets have been used.\n"
                        + "Press OK to reuse the stimuli sets.");
                this.indexOfNextFree=0;
            }
            
           
            
        }

        CustomizableReferentialTaskSettings crts = this.vcrts.elementAt(indexOfNextFree);
        Conversation.printWSln("Main", "Using stimuli set "+crts.filename);
        indexOfNextFree++;
        return crts;
    }
    
    
    
    
    
    
    
    public void askBooleanQuestions(){
          durationOfGame = CustomDialog.getLong("How long is a game? (seconds)\n\nNote: To prevent games from timing out ", 600000);
          if(this.isSingleSet)this.randomizeSequence = CustomDialog.getBoolean("Do you want the sequence of stimuli to be in original order or ranomized?", "original order", "randomized");
          durationOfStimulus =  CustomDialog.getLong("How long should the stimuli be displayed for?", 6000000);
          showButtons = CustomDialog.getBoolean("Do you want to show buttons underneath the stimuli on the clients?", "Buttons", "No Buttons");
          if(telegram) this.deleteStimulusAfterEachTrial = CustomDialog.getBoolean("Do you want to delete the stimuli from the telegram window after each trial?", "Delete", "Keep");
         
          showScoreOnEachGame = CustomDialog.getBoolean("Do you want to show the score after each trial?", "Show", "Don`t show");
          showIfSelectionWasCorrrectOrIncorrect = CustomDialog.getBoolean("Do you want to show if the previous trial was correct / incorrect?", "Show", "Don`t show");
          advanceToNextManually =   !CustomDialog.getBoolean("Do you want participants to advance automatically\n or do participants have to type /NEXT to advance to next stimuli?", "Automatic advancement", "Manual advancement by typing /NEXT");
          this.telegram=telegram;
          
    }
    
    
    
    
    
    
    private File getScriptFile(){
        String userdir = System.getProperty("user.dir");
       String directory = (userdir+File.separator+"experimentresources"+ File.separator+ "stimuli");
       
       CustomDialog.showDialog("Important:\n\n(1) The stimuli must be in a subdirectory of /experimentresources/stimuli.\n"
               + "(2) The file containing the stimuli sequence must be in the same subdirectory as the stimuli.\n"
               + "\n(See usermanual for more details!)");
       File stimulisequence = CustomDialog.loadFileWithExtension(directory, "Choose the text file containing the sequences of stimuli. \nIt must be in the same directory as the stimuli", "txt", "Text file containing the list of stimuli and correct answers");
       return stimulisequence;
    }
    
    
    
    
     private Vector<String[]>    loadFromFile(File stimulisequence){
       
      
       
        String foldername = stimulisequence.getParentFile().getName();
        
       // stimulisequence.get
       
       System.err.println("Looking in "+foldername + " for script file called "+stimulisequence);
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
            CustomDialog.showDialog("There was an error loading in the stimuli list! Probably best to exit program and start again\n");
                 
        }
      
        //this.vstimuli=(Vector<String[]>)data.clone();
        //this.vstimuliFULL=(Vector<String[]>)data.clone();
        try{
           boolean stimuliFilesExist = this.checkStimuliFilesExist(data,foldername);
           if(!stimuliFilesExist){
               CustomDialog.showDialog("Error loading the stimuli - it could be that there is empty space at the end of the script containing the stimuli names! \n"
                       + "The program will exit to make sure the experiment doesn`t start with missing stimuli!");
               System.exit(-5);
           }
        }catch(Exception e){
            e.printStackTrace();
            Conversation.saveErr(e);
            CustomDialog.showDialog("Error loading the stimuli - it could be that there is empty space at the end of the script containing the stimuli names! \n"
                       + "The program will exit to make sure the experiment doesn`t start with missing stimuli!");
            System.exit(-5);
  
        }
        return data;
   }
  
    
   private boolean checkStimuliFilesExist(Vector<String[]> vstimuli, String directoryname){
       String userdir = System.getProperty("user.dir");
       String dir = (userdir+File.separator+"experimentresources"+ File.separator+ "stimuli"+ File.separator+directoryname);
       
       
       for(int i=0;i<vstimuli.size();i++){
           File f1 = new File(dir, (String)vstimuli.elementAt(i)[0]);
           if(!f1.exists()){
               CustomDialog.showDialog("Can't find file:\n\n"+f1.getAbsolutePath()+"\n\nCheck and fix the stimuli sequence before running the experiment!" );
               return false;
           }
           File f2 = new File(dir, (String)vstimuli.elementAt(i)[1]);
           if(!f2.exists()){
               CustomDialog.showDialog("Can't find file:\n\n"+f2.getAbsolutePath()+"\n\nCheck and fix the stimuli sequence before running the experiment!" );
               return false;
           }
           
           try{
                BufferedImage image1 = ImageIO.read(f1);
                BufferedImage image2 = ImageIO.read(f2);
                this.htIMAGE.put(vstimuli.elementAt(i)[0],image1);
                this.htIMAGE.put(vstimuli.elementAt(i)[1],image2);
      
          } catch (Exception e){
               e.printStackTrace();
               Conversation.saveErr(e);
               CustomDialog.showDialog("Error loading the file - it could be that there is empty space at the end of the script:");
           }
           
           
       }
       return true;
   }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
