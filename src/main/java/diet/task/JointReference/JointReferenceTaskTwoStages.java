/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.JointReference;

import diet.attribval.AttribVal;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.task.ProceduralComms.JTrialTimer;
import diet.task.ProceduralComms.JTrialTimerActionRecipientInterface;
import diet.utils.HashtableWithDefaultvalue;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class JointReferenceTaskTwoStages implements JTrialTimerActionRecipientInterface {

    JJPauser jjp =new JJPauser();

    DefaultConversationController cC;
    
    JTrialTimer  jtt;// = new JTrialTimer(this, 10000);
    
   // double probabOfSame= 0.5;//CustomDialog.getDouble("Enter the probability of SAME stimuli", 0, 1, 0.5);
    public Participant pA;
    public Participant pB;
    Random r = new Random();
    final long durationOfStimulus = 500000;// = CustomDialog.getLong("How long should the stimuli be displayed for?", 5000);
    //boolean blockTextEntryDuringStimulus = true;// = CustomDialog.getBoolean("Block text entry while stimulus is displayed?", "block", "do not block");
    
    String pA_Imagename;
    String pB_Imagename;
    
    boolean currentsethasbeensolved = false;
    
    public HashtableWithDefaultvalue htscoreCORRECT = new HashtableWithDefaultvalue(0);
    public HashtableWithDefaultvalue htscoreINCORRECT = new HashtableWithDefaultvalue(0);
    
     public HashtableWithDefaultvalue htPOINTS = new HashtableWithDefaultvalue((double)0);
    long gamenumber=0;
    
    double correctscoreinrement = 10;//CustomDialog.getDouble("What is the score increment for correct guesses?", 10);
    double incorrectpenalty = 5;//CustomDialog.getDouble("What is the point penalty for incorrect guesses?", 5);
    
     final  String[] buttons = {"",""};
     String displayname = "instructions";
    // String directoryname = "faceset4and5";
     
      
    //  String option="tangramlist01.txt";
      String directoryname = "tangramtask";
      
      boolean showFeedbackToDirector = false;
      
      
      boolean istrainingphase = CustomDialog.getBoolean("Training or test phase", "TRAINING", "TEST");
      
      
      //Vector loadedTraining = new Vector();
      //Vector loadedTEsting = new Vector();
      
    
    public JointReferenceTaskTwoStages(DefaultConversationController cC) {
        //durationOfStimulus = 5000;//CustomDialog.getLong("How long should the stimuli be displayed for?", 5000);
        //blockTextEntryDuringStimulus = CustomDialog.getBoolean("Block text entry while stimulus is displayed?", "block", "do not block");
        this.cC=cC;
        this.initFilenames();
        jtt = new JTrialTimer(this, 10000);
    }
    public JointReferenceTaskTwoStages(DefaultConversationController cC, long durationOfStimulus){
         //this.durationOfStimulus=durationOfStimulus;
         this.cC=cC;
         
         
         
         
         
         
          Vector<String> v = new Vector();
         v.add("A");
         v.add("B");
         v.add("C");
         
         cyclicdispenser cd = new cyclicdispenser(v);
         boolean b = false;
         while(b){
             System.out.println(cd.getNext());
         }
         
         
         
         
         
         
         
         if(this.istrainingphase){
             this.loadReferentlistTraining();
         }
         else{
             this.loadReferentlistTesting();
         } 
         
        
         
         
         
    }
    
    
    public boolean isTraining(){
        return this.istrainingphase;
    }
    
    
    private void loadReferentlistTestingOLD(){
        
      
        String dir = "" ;
        String mat="";
        int matchset =-1;
        
        dir ="D5_A_2.png";    matchset = 6;  mat = "set"+matchset+ ".png";  vpairs.add(new String[]{dir,mat});   
        dir ="D5_B_3.png";    matchset = 7; mat = "set"+matchset+ ".png";  vpairs.add(new String[]{dir,mat});
        dir= "D5_E_1.png";     matchset = 8; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
        dir ="D5_H_4.png";    matchset = 6; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
        
       
        dir = "D6_B_7.png";    matchset = 5; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
        dir = "D6_D_2.png";    matchset = 7; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
        dir = "D6_F_4.png";    matchset = 8; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
        dir = "D6_G_0.png";    matchset = 5; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
        
      
        dir = "D7_A_3.png";     matchset = 5; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
        dir = "D7_C_1.png";      matchset = 6; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
        dir = "D7_E_7.png";      matchset = 8; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
        dir = "D7_G_5.png";    matchset = 8; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
                
        
        dir = "D8_C_5.png";    matchset = 5; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
        dir = "D8_D_4.png";    matchset = 6; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat}); 
        dir = "D8_F_2.png";    matchset = 7; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
        dir = "D8_H_0.png";    matchset = 7; mat = "set"+matchset+ ".png"; vpairs.add(new String[]{dir,mat});
                
                
        //5678
        //8567        
                
    }
    
    
     private void loadReferentlistTesting(){
         boolean foundseq = false;
         while(!foundseq){
              Vector vp = this.generateReferentlistTesting();
              if(vp!=null){
                  foundseq=true;
                  vpairs=vp;
              }
              else{
                  
              }
         }
     }
     
     
     
     private Vector generateReferentlistTesting(){
        
         Vector vpairscandidate = new Vector();
         
         String dir = "" ;
        String mat="";
        int matchset =-1;
        
        
       
        SequenceOfFour sf4 = new SequenceOfFour(5,6,7,8);
       
        
        SequenceOfThree sf3 = new SequenceOfThree(6,7,8);
        dir ="D5_A_2.png";    matchset = sf3.getNext();  mat = "set"+matchset+ ".png";  vpairscandidate.add(new String[]{dir,mat});   
        dir ="D5_B_3.png";    matchset = sf3.getNext(); mat = "set"+matchset+ ".png";  vpairscandidate.add(new String[]{dir,mat});
        dir= "D5_E_1.png";    matchset = sf3.getNext(); mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
        dir ="D5_H_4.png";    matchset = sf4.getNext(matchset,5); if (matchset==-1)return null     ;mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
        
       
        sf3 = new SequenceOfThree(5,7,8);
        dir = "D6_B_7.png";    matchset = sf3.getNext(); mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
        dir = "D6_D_2.png";    matchset = sf3.getNext(); mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
        dir = "D6_F_4.png";    matchset = sf3.getNext(); mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
        dir = "D6_G_0.png";   matchset = sf4.getNext(matchset,6); if (matchset==-1)return null ;mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
        
         sf3 = new SequenceOfThree(5,6,8);
        dir = "D7_A_3.png";    matchset = sf3.getNext(); mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
        dir = "D7_C_1.png";    matchset = sf3.getNext(); mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
        dir = "D7_E_7.png";    matchset = sf3.getNext(); mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
        dir = "D7_G_5.png";    matchset = sf4.getNext(matchset,7); if (matchset==-1)return null ;mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
                
         sf3 = new SequenceOfThree(5,6,7);
        dir = "D8_C_5.png";    matchset = sf3.getNext(); mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
        dir = "D8_D_4.png";    matchset = sf3.getNext(); mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat}); 
        dir = "D8_F_2.png";    matchset = sf3.getNext(); mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
        dir = "D8_H_0.png";     matchset = sf4.getNext(matchset,8); if (matchset==-1)return null ;mat = "set"+matchset+ ".png"; vpairscandidate.add(new String[]{dir,mat});
                
                
        //5678
        //8567        
        return vpairscandidate;
    }
    
    
    
    class SequenceOfFour{
        Vector <Integer> v = new Vector();
        public SequenceOfFour(int a, int b, int c, int d){
            v.addElement(a);
            v.addElement(b);
            v.addElement(c);
            v.addElement(d);
        }
        public int getNext(int notval, int notval2){
            Vector<Integer> vv = new Vector();
            for(int i=0;i<v.size();i++){
               Integer ii= v.elementAt(i);
               if(ii!=notval && ii!=notval2){
                   vv.addElement(ii);
               }
            
            }
            if(vv.size()==0){
               // if(v.size()!=1) CustomDialog.showDialog("ERROR CODE 50");
                
                return -1;
            }
            
            int idx = r.nextInt(vv.size());
            int retval = vv.elementAt(idx);
            v.removeElementAt(idx);
            return retval;
        }
    }
    class SequenceOfThree{
        Vector <Integer> v = new Vector();
        public SequenceOfThree(int x, int y, int z){
            v.addElement(x);
            v.addElement(y);
            v.addElement(z);
        }
        public int getNext(){
            int idx = r.nextInt(v.size());
            int retval = v.elementAt(idx);
            v.removeElementAt(idx);
            return retval;
        }
        
    }
    
    
    
    
    
    //Vector<String> targetFigs;
    
    
    
    class cyclicdispenser {
        
        Vector<String> vAllFull = new Vector();
        Random r = new Random();
        String mostRecent ="";
        
        public Vector<String> itemsRemaining= new Vector();
        
        public cyclicdispenser(Vector<String> v){
            this.vAllFull = (Vector)v.clone();
        }
        
        
        
        
        public String getNext(){
            if (itemsRemaining.size()==0){
                itemsRemaining = (Vector<String>)vAllFull.clone();
                
            }
            boolean foundnew = false;
            String candidate = null;
            while(!foundnew){
                 candidate = this.itemsRemaining.elementAt(r.nextInt(itemsRemaining.size()));
                if(!candidate.equalsIgnoreCase(mostRecent)){
                    foundnew = true;
                }
            }
            mostRecent = candidate;
            itemsRemaining.remove(candidate);
            
            return candidate;
            
        }
        
    }
    
    
    
    private void loadReferentlistTraining(){
       
        
     
       
       Vector<int[]> directormatchersets = getReferentlistTraining();
       
       System.err.println("TRAININGDIRECTORMATCHERSETZIZE"+ directormatchersets.size());
       
       //System.exit(-5678);
       Vector<String> targettangrams = new Vector();targettangrams.add("A");targettangrams.add("B");targettangrams.add("C");targettangrams.add("D");targettangrams.add("E");targettangrams.add("F");targettangrams.add("G");targettangrams.add("H");
       
       cyclicdispenser figures = new cyclicdispenser(targettangrams);
       
       
       
       
       
       for(int targetFigIDX=0;targetFigIDX<8;targetFigIDX++){
            
           for(int i=0;i<5;i++){
               int directorset = directormatchersets.firstElement()[0];
               
               String target = figures.getNext();
               
               int directorsetposition = this.getPositionOfFigureInSetstart0(target, directorset);
               int matcherset = directormatchersets.firstElement()[1];
               int matchersetposition = this.getPositionOfFigureInSetstart0(target, matcherset);
               
               //int[] set = new int[] {directorset, targetFig, directorsetposition, matcherset,matchersetposition};
               
               String dfilename = "D"+directorset+"_"+target+ "_"+directorsetposition;
               
               directormatchersets.removeElementAt(0);
               
               System.out.println(dfilename+ "---matcherset: "+matcherset);
               
               String mfilename = "set"+matcherset+".png";
               String[] dirmatchpair = new String[]{dfilename+".png", mfilename  };
               
               vpairs.add(dirmatchpair);
           }
       }   
      
     // Vector vtemp = new Vector();
      //vtemp.addElement(vpairs.elementAt(0));
      //vpairs = new Vector();
      //vpairs = vtemp;
       
       this.vpairsFULL=(Vector)vpairs.clone();
       
       
       
      //System.exit(-56789);
    }
    
    
    
    public void createAllCombinations(){
        // 1,1    2,2    3,3   4,4    5,4
        // 
        
       // 1,2   2,1
       // 2,1   1,2
       
      
       //1,2   2,3  3,1
       //1,3   2,1  3,2
       
       //1,4   2,3   3,2  4,1
       //1,4   2,1   3,2  4,3
        
       //Set 1 Target A      2
       //Set 1 Target B      3
       //.....
       //Set 1 Target H
       //...
       //Set 8...Target H
       
       /*
       
       0A
       0B
       0C
       0D
       0E
       0F
       0G
       0H
       
       1A
       1B
       1C
       1D
       1E
       1F
       1G
       
       2A
       2B
       2C
       2D
       2E
       2F
       2G
       
       3A
       3B
       3C
       3D
       3E
       3F
       3G
       3H
       
       4A
       4B
       4C
       4D
       4E
       4F
       4G
       4H
       
       
       5A
       5B
       5C
       5D
       5E
       5F
       5G
       5H
       
       6A
       6B
       6C
       6D
       6E
       6F
       6G
       6H
       
       7A
       7B
       7C
       7D
       7E
       7F
       7G
       7H
       
       
       
       
       
       
       0,1
       0,2
       0,3
       0,4
       
       
       1,0
       1,2
       1,3
       1,4
       
       2,0
       2,1
       2,3
       2,4
       
       3,0
       3,1
       3,2
       3,4
       
       4,0
       4,1
       4,2
       4,3
       
        0,1
       0,2
       0,3
       0,4
       
       
       1,0
       1,2
       1,3
       1,4
       
       2,0
       2,1
       2,3
       2,4
       
       3,0
       3,1
       3,2
       3,4
       
       4,0
       4,1
       4,2
       4,3
       //cycle through each block of 20 twice - the last one of the first set is not the first one of the second set
       
       
       //blocks of 8
       //the last one of a set is never the first one of a set
       
       
       
       
       
       ///RAW
       
       
       *12345
       *12345
       
       12345
       51234
       
       12345
       45123
       
       12345
       34512
       
       12345
       23451
       
       
       
       54321
       12345
       
      *54321
      *51234
      
       54321
       45123
       
      *54321
      *34512
      
      *54321
      *23451
       
       54321
       12543
       
       54321
       31254
       
       ///Sequence is:
       
       
       
       
       12345123451234512345   
       51234451233451223451  
       
       
       
       
        51234451233451223451 
        12345123451234512345
       
       
              
     
       
       
       
      
       
      *54321
      *51234
      
       54321
       45123
       
      *54321
      *34512
      
      *54321
      *23451
       
       54321
       12543
       
       54321
       31254
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       /*
       
       */
      // See if possible to generate
       /*
       //smaple it..
       
       
       
       0,1  -> 1,2  -                   
               1,3
               1,4
               2,2
               2,3
               2,4
               3,1 
               3,2
               3,3
               3,4
               4,0
               
               4,2
               
       
       
       0,2  0,3 0,4 
       
       0,1
       
       
       
       
       
       
       
       
       
       
       
       
       
       */
    }
    
    
    
    
    private void loadReferents2019(){
        String option="6";
        InputStream inp = this.getClass().getResourceAsStream("/"+option+".txt");  
       try {    
        BufferedReader br = new BufferedReader(new InputStreamReader(inp, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            String[] splitline = line.split(" ");
            this.vpairs.addElement(splitline);
             
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        String everything = sb.toString();
       }
        catch (Exception e){
        e.printStackTrace();
    }
    
    this.vpairsFULL=(Vector)vpairs.clone();
    }
    
    
    
    private void loadListsFor2017Experiment(){
       //Vector allPairs= new Vector();
        
        String[] options = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21", "22","23","24","25",
        "26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50"};
        
       
       String option= CustomDialog.showComboBoxDialog("options", "choose set", options, true);
        
       
        
        
       InputStream inp = this.getClass().getResourceAsStream("/"+option+".txt");  
       try {    
        BufferedReader br = new BufferedReader(new InputStreamReader(inp, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            String[] splitline = line.split(" ");
            System.err.println("FIRSTPART"+splitline[0]);
            System.err.println("SECONDPART"+splitline[1]);
            this.vpairs.addElement(splitline);
             
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        String everything = sb.toString();
        
    }
       
       
    catch (Exception e){
        e.printStackTrace();
    }
    
    this.vpairsFULL=(Vector)vpairs.clone();
    
        
    
    }
    
    
    public Vector<String[]> vpairs = new Vector();
    public Vector<String[]> vpairsFULL = new Vector();
    
    
    
   // Vector<String> vs = new Vector<String>();
   // Vector<String> vs2 = new Vector<String>();
    
    private void initFilenames(){
        
   
    }
    
    public void  participantRejoined(Participant p){
        if(p==pA){
             cC.c.showStimulusImageFromJarFile_InitializeWindow(pA, 710, 400, "",false,buttons);
             cC.c.showStimulusImageEnableButtons(pA, buttons, false);
        }
        else{
              cC.c.showStimulusImageFromJarFile_InitializeWindow(pB, 710, 400, "",false, buttons);
               cC.c.showStimulusImageEnableButtons(pB, buttons, false);
        }
    }
    
    
    
    public void startTask(Participant pA, Participant pB){
       
            this.pA=pA;
            this.pB=pB;
        
        
        
        cC.c.showStimulusImageFromJarFile_InitializeWindow(pA, 710, 400, "",false, buttons);
        cC.c.showStimulusImageFromJarFile_InitializeWindow(pB, 710, 400, "",false, buttons);
       // cC.c.showStimulusImageEnableButtons(pB, buttons, false);
        //cC.c.showStimulusImageEnableButtons(pA, buttons, false);
        //doCountdowntoNextSet_DEPRECATED("Please start!","Next face in " );
        
        Thread t = new Thread(){
            public void run(){
                gameloop();
            }
        };
      
        
        t.start();
    }
    
    
    
   
  
    
     
     
     
     
     public boolean hasLoopedThroughAllFaces = false;
     private void checkIfHasLoopedThroughAllDEPRECATED(){
          
          
         if(vpairs.size()==0){
             
                      this.hasLoopedThroughAllFaces=true;
                      vpairs=(Vector)vpairsFULL.clone();
                      System.err.println("HAS LOOPED THROUGH ALL OF THE FACES!");
                      Conversation.printWSln("Main", "Has looped through all the faces");
          }
     }
     
     boolean hasdonetraining = false;
     
     
      private void checkIfHasLoopedThroughAll(){
          
          
         if(vpairs.size()==0){
                      if(this.istrainingphase&&!hasdonetraining){
                           CustomDialog.showDialog("HAS FINISHED TRAINING! CONTINUING WITH TEST SET");
                          hasdonetraining = true;
                          this.loadReferentlistTesting();
                          this.istrainingphase=false;
                          
                          Participant pASwap =pA;
                          pA=pB;
                          pB=pASwap;
                          
                          this.htscoreCORRECT.putObject(pA, 0);
                          this.htscoreCORRECT.putObject(pB, 0);
            
           
            
                          
                          
                      }
                      else if(!this.istrainingphase){
                          CustomDialog.showDialog("EXPERIMENT FINISHED!");
                      }
                     
                      //this.hasLoopedThroughAllFaces=true;
                      //vpairs=(Vector)vpairsFULL.clone();
                      System.err.println("HAS LOOPED THROUGH ALL OF THE FACES!");
                      Conversation.printWSln("Main", "Has looped through all the faces");
          }
     }
     
     
     
     private void loadNextStimulusSetSet(String directoryname){
                  checkIfHasLoopedThroughAll();   
                 
                  
                  String[] pair = this.vpairs.elementAt(0);
                  
                 // String[] pair = this.vpairs.elementAt(r.nextInt(vpairs.size()));
                  vpairs.remove(pair);
                  
                 
                      String imageName1 = directoryname+"/"+pair[0];
                      String imageName2 = directoryname+"/"+pair[1];
                      this.pA_Imagename=imageName1;   
                      this.pB_Imagename=imageName2;  
                                   
                
           Conversation.printWSln("Main", "THE FACES ARE:"  + this.pA_Imagename+"----"+this.pB_Imagename);
           
            
           System.err.println("THE FACE SETS ARE: "+this.pA_Imagename );
     }
    
    
    
     
     
     //boolean isInTransitionBetweenGames = false;
     
     
     long startOfCurrentGame = new Date().getTime();
     long durationOfGame = CustomDialog.getLong("How long is a game?", 60000);
     
     
     
     public synchronized void gameloop(){
          try{
             //this.doCountdowntoNextSet_Step1_Countdown("Your score is: ","Time till next image: ",false);
             this.doCountdowntoNextSet_Step2_LoadNextSet();
             //this.doCountdowntoNextSet_Step3_StopShowingStimulus();
             this.doCountdowntoNextSet_Step4_ShowMessageAtStartOfTrial();
          }catch(Exception e){
              e.printStackTrace();
          }   
          
          long bonustimethisgame = 0;
          startOfCurrentGame = new Date().getTime();
          while(2<5){
              //
              try{
                  
                 wait(250);
                 
                 long time1 = new Date().getTime();
                 boolean ispaused = this.jjp.isPaused();
                 while(ispaused){
                     ispaused = this.jjp.isPaused();
                     wait(500);
                     bonustimethisgame=bonustimethisgame+500;
                 }
                 
                 
                 long durationOfGameSoFar = new Date().getTime()-startOfCurrentGame;
                 long timeRemaining =  bonustimethisgame +   durationOfGame - durationOfGameSoFar;
                 if(timeRemaining<0)timeRemaining =0;
                 
                 Color progressBar = Color.red;
                 long percentage = (100*timeRemaining)/durationOfGame;
                 
                 System.err.println("durationOfGameSoFar "+durationOfGameSoFar);
                 System.err.println("timeremaining "+timeRemaining);
                 System.err.println("durationofgame "+durationOfGame);
                 
                  int timeRemainingSeconds = (int)timeRemaining/1000;
                  
                  cC.c.changeJProgressBar(pA, "CHATFRAME",  timeRemainingSeconds+"", new Color(150,150,255), (int)percentage);
                  cC.c.changeJProgressBar(pB, "CHATFRAME",  timeRemainingSeconds+"", new Color(150,150,255), (int)percentage);
                 
                 
                 if(this.currentsethasbeensolved){                    
                     this.doCountdowntoNextSet_Step1_Countdown("Your score is: ","Time till next image: ", false, false);
                     this.doCountdowntoNextSet_Step2_LoadNextSet();
                     this.startOfCurrentGame= new Date().getTime();
                      bonustimethisgame=0;
                     //this.doCountdowntoNextSet_Step3_StopShowingStimulus();
                     this.doCountdowntoNextSet_Step4_ShowMessageAtStartOfTrial();
                 }   
                 else if(timeRemaining<=0){
                        cC.c.sendInstructionToParticipant(pA, "You ran out of time!" );
                        cC.c.sendInstructionToParticipant(pB, "You ran out of time!" );
                      this.doCountdowntoNextSet_Step1_Countdown("Your score is: ","Time till next image: ",false, false);
                     this.doCountdowntoNextSet_Step2_LoadNextSet();
                     this.startOfCurrentGame= new Date().getTime();
                      bonustimethisgame=0;
                     //this.doCountdowntoNextSet_Step3_StopShowingStimulus();
                     this.doCountdowntoNextSet_Step4_ShowMessageAtStartOfTrial();
                 }
                 
                 
                 //check if game is complete
                 //if game is not complete
              }
              catch(Exception e){
                  e.printStackTrace();
              }
                             
          }
         
         
     }
     
     
     
     
     
     
     
     
     
     public void doCountdowntoNextSet_Step1_Countdown(final String  firstmessage,final  String countdownmessageprefix, boolean showTimerInChatWindow  ,boolean blocktextentry) throws Exception{
       
                
                  cC.c.sendInstructionToParticipant(pA, "Loading next set");
                  cC.c.sendInstructionToParticipant(pB, "Loading next set");
                 
                     
               
                  cC.c.showStimulusImageEnableButtons(pB, buttons, false);
                  cC.c.showStimulusImageEnableButtons(pA, buttons, false);
                  
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pA, pA_Imagename, false,1); //Making the image disappear
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pB, pB_Imagename, false, 1); //Making the image disappear
                  
                  
                  
                  if(blocktextentry){
                     cC.c.changeClientInterface_clearMainWindows(pA);
                     cC.c.changeClientInterface_clearMainWindows(pB);
                     cC.c.changeClientInterface_disableTextEntry(pA);
                     cC.c.changeClientInterface_disableTextEntry(pB);
                  } 
                  
                
                  
                  
                
                  Thread.sleep(1000);
                  
                  if(blocktextentry)   cC.c.changeClientInterface_clearMainWindows(pA);
                   if(blocktextentry) cC.c.changeClientInterface_clearMainWindows(pB);
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","" ,false, pA,pB );
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.white);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.white);
                  //cC.c.newsendInstructionToParticipant(pA, "Please " );
                   
                  //cC.c.newsendInstructionToParticipant(pB, messageprefix + "1 seconds" );
                  
                  //cC.c.showStimulusImageFromJarFile_ChangeImage(pA, "faceset1/face1.png", 4000);
                  //cC.c.showStimulusImageFromJarFile_ChangeImage(pB, "faceset1/face1.png", 4000);
                  
                 
         
     }
     
     public void pause(){
         jjp.setPaused();
     }
     
     
     public void doCountdowntoNextSet_Step2_LoadNextSet(){
         loadNextStimulusSetSet(this.directoryname);     
         gamenumber++;
         cC.c.showStimulusImageFromJarFile_ChangeImage(pA, pA_Imagename,false, durationOfStimulus);
         cC.c.showStimulusImageFromJarFile_ChangeImage(pB, pB_Imagename, false,durationOfStimulus);  
          this.jjp.addTextln("Gamenumber: "+gamenumber);
         this.jjp.addTextln(pA.getParticipantID()+","+pA.getUsername()+" "+pA_Imagename);
         this.jjp.addTextln(pB.getParticipantID()+","+pB.getUsername()+" "+pB_Imagename);
         this.jjp.addTextln("");
         currentsethasbeensolved = false;         
     }
     
     
     
     public void doCountdowntoNextSet_Step3_StopShowingStimulus() throws Exception{
                 
                  
                       try{
                            //Thread.sleep(durationOfStimulus);
                            cC.c.changeClientInterface_enableTextEntry(pA);
                            cC.c.changeClientInterface_enableTextEntry(pB);
                            
                             int pAPercentageCorrect=0;
                             int pANumberCorrect = getScoreCORRECT(pA);
                             int pANumberINCorrect = getScoreINCORRECT(pA);
                             int pBPercentageCorrect=0;
                             int pBNumberCorrect = getScoreCORRECT(pB);
                             int pBNumberINCorrect = getScoreINCORRECT(pB);
                             if(gamenumber>1){
                                 pAPercentageCorrect= (100*pANumberCorrect) / (pANumberCorrect+pANumberINCorrect);
                                 pBPercentageCorrect= (100*pBNumberCorrect) / (pBNumberCorrect+pBNumberINCorrect);
                            }
                  
                            //if(blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pA, "Your success rate is: "+ pAPercentageCorrect+   "%"  );
                            //if(blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pB, "Your success rate is: "+ pBPercentageCorrect+   "%"  );
                            
                              DecimalFormat myFormatter = new DecimalFormat("############.#");
                             String outputPA = myFormatter.format( (double)  htPOINTS.getObject(pA));
                             String outputPB = myFormatter.format( (double)  htPOINTS.getObject(pB));
                             
                            // cC.c.newsendInstructionToParticipant(pA, "Your score is: "+ outputPA );
                            // cC.c.newsendInstructionToParticipant(pB, "Your score is: "+ outputPB );
                  
                            
                         
                            //cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ outputPA ,true, pA);
                            //cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+  outputPB,true, pB);
                      
                             
                             
                             
                             
                            
                                 cC.c.showStimulusImageEnableButtons(pB, buttons, true);
                                 cC.c.showStimulusImageEnableButtons(pA, buttons, true);
                                 //if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) +"\n",true, pA );
                                 //if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) +"\n",true, pB );
                                 //if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",false, pA,pB );
                            
                           // cC.c.newsendInstructionToParticipant(pA,"enter '/s' if you saw the same face");
                           // cC.c.newsendInstructionToParticipant(pA,"enter '/d' if you saw different faces");
                           //  cC.c.newsendInstructionToParticipant(pB,"enter '/s' if you saw the same face");
                           // cC.c.newsendInstructionToParticipant(pB,"enter '/d' if you saw different faces");
                            
                            
                       }catch(Exception eee){
                           eee.printStackTrace();
                       }
                    
                  
     }
     
     
     
     
      public void doCountdowntoNextSet_Step4_ShowMessageAtStartOfTrial() throws Exception{
                 
                  
                            
                             int pAPercentageCorrect=0;
                             int pANumberCorrect = getScoreCORRECT(pA);
                             int pANumberINCorrect = getScoreINCORRECT(pA);
                             int pBPercentageCorrect=0;
                             int pBNumberCorrect = getScoreCORRECT(pB);
                             int pBNumberINCorrect = getScoreINCORRECT(pB);
                        
                            
                              DecimalFormat myFormatter = new DecimalFormat("############.#");
                             String outputPA = myFormatter.format( (double)  htPOINTS.getObject(pA));
                             String outputPB = myFormatter.format( (double)  htPOINTS.getObject(pB));
                             
                            // cC.c.newsendInstructionToParticipant(pA, "Your score is: "+ outputPA );
                            // cC.c.newsendInstructionToParticipant(pB, "Your score is: "+ outputPB );
                  
                            
                         
                            cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ outputPA ,true, pA);
                            cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+  outputPB,true, pB);
                      
                             
                             
                             
                             
                            
                                 cC.c.showStimulusImageEnableButtons(pB, buttons, false);
                                 cC.c.showStimulusImageEnableButtons(pA, buttons, false);
                                // if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) +"\n",true, pA );
                                // if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) +"\n",true, pB );
                                // if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",false, pA,pB );
                            
                             //cC.c.newsendInstructionToParticipant(pA,"enter '/s' if you saw the same face");
                            // cC.c.newsendInstructionToParticipant(pA,"enter / followed by the number");
                            //cC.c.newsendInstructionToParticipant(pB,"enter '/s' if you saw the same face");
                            //cC.c.newsendInstructionToParticipant(pB,"enter '/d' if you saw different faces");
                            
                            
                    
                  
     }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
    
    
    private synchronized void doCountdowntoNextSet_DEPRECATED(final String  firstmessage,final  String countdownmessageprefix){
        if(2<5)return;
        final String scoreA = " Score: "+(Double)this.htPOINTS.getObject(pA);
        final  String scoreB = " Score: "+(Double)this.htPOINTS.getObject(pB);
        
        Thread t = new Thread(){
             public void run(){
                 try{
                  
                  
                     
                
                  cC.c.showStimulusImageEnableButtons(pB, buttons, false);
                  cC.c.showStimulusImageEnableButtons(pA, buttons, false);
                  cC.c.changeClientInterface_clearMainWindows(pA);
                  cC.c.changeClientInterface_clearMainWindows(pB);
                  cC.c.changeClientInterface_disableTextEntry(pA);
                  cC.c.changeClientInterface_disableTextEntry(pB);
                  
                  
                  if(firstmessage!=null  && !firstmessage.equalsIgnoreCase(""))cC.c.sendInstructionToParticipant(pA, firstmessage );
                  if(firstmessage!=null && !firstmessage.equalsIgnoreCase("")) cC.c.sendInstructionToParticipant(pB, firstmessage );
                
                  if(firstmessage!=null  && !firstmessage.equalsIgnoreCase("") &&displayname!=null){
                      cC.c.textOutputWindow_ChangeText("instructions", firstmessage +scoreA+"\n" ,true, pA );
                      cC.c.textOutputWindow_ChangeText("instructions", firstmessage +scoreB+"\n",true, pB );
                  }
                 
                  
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.red);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.red);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "5 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "5 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "5 secs" +"\n" ,true, pA,pB );
                  
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.black);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.black);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "4 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "4 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "4 secs" +"\n" ,true, pA,pB );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.red);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.red);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "3 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "3 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "3 secs" +"\n" ,true, pA,pB );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.black);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.black);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "2 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "2 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "2 secs"+"\n"  ,true, pA,pB );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.red);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.red);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "1 sec" );
                  cC.c.sendInstructionToParticipant(pB,countdownmessageprefix + "1 sec" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "1 secs"+"\n"  ,true, pA,pB );
                  Thread.sleep(1000);
                  cC.c.changeClientInterface_clearMainWindows(pA);
                  cC.c.changeClientInterface_clearMainWindows(pB);
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","" ,false, pA,pB );
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.white);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.white);
                  //cC.c.newsendInstructionToParticipant(pA, "Please " );
                   
                  //cC.c.newsendInstructionToParticipant(pB, messageprefix + "1 seconds" );
                  
                  //cC.c.showStimulusImageFromJarFile_ChangeImage(pA, "faceset1/face1.png", 4000);
                  //cC.c.showStimulusImageFromJarFile_ChangeImage(pB, "faceset1/face1.png", 4000);
                  
                  loadNextStimulusSetSet(directoryname);  
                  
                  
                 
                  gamenumber++;
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pA, pA_Imagename, false,durationOfStimulus);
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pB, pB_Imagename, false, durationOfStimulus);
                  
                  
                  
                  currentsethasbeensolved = false;
                  
                  
                  int pAPercentageCorrect=0;
                  int pANumberCorrect = getScoreCORRECT(pA);
                  int pANumberINCorrect = getScoreINCORRECT(pA);
                  int pBPercentageCorrect=0;
                  int pBNumberCorrect = getScoreCORRECT(pB);
                  int pBNumberINCorrect = getScoreINCORRECT(pB);
                  try{
                  if(gamenumber>1){
                      pAPercentageCorrect= (100*pANumberCorrect) / (pANumberCorrect+pANumberINCorrect);
                      pBPercentageCorrect= (100*pBNumberCorrect) / (pBNumberCorrect+pBNumberINCorrect);
                  }
                  }catch (Exception e){
                      e.printStackTrace();
                  }
                  //if(!blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pA, "Your success rate is: "+ pAPercentageCorrect+   "%"  );
                  //if(!blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pB, "Your success rate is: "+ pBPercentageCorrect+   "%"  );
                  
                  
                  
                  
                 
                  
                 
                  //if(cC instanceof FaceComms2016_RAP_TURNBYTURNDyadic)      ((FaceComms2016_RAP_TURNBYTURNDyadic)cC).changeInterfaceDelays();
                  
                  
                  
                  
                  Thread tt = new Thread(){
                     public void run(){
                       try{
                            Thread.sleep(durationOfStimulus);
                            cC.c.changeClientInterface_enableTextEntry(pA);
                            cC.c.changeClientInterface_enableTextEntry(pB);
                            
                             int pAPercentageCorrect=0;
                             int pANumberCorrect = getScoreCORRECT(pA);
                             int pANumberINCorrect = getScoreINCORRECT(pA);
                             int pBPercentageCorrect=0;
                             int pBNumberCorrect = getScoreCORRECT(pB);
                             int pBNumberINCorrect = getScoreINCORRECT(pB);
                             if(gamenumber>1){
                                 pAPercentageCorrect= (100*pANumberCorrect) / (pANumberCorrect+pANumberINCorrect);
                                 pBPercentageCorrect= (100*pBNumberCorrect) / (pBNumberCorrect+pBNumberINCorrect);
                            }
                  
                            //if(blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pA, "Your success rate is: "+ pAPercentageCorrect+   "%"  );
                            //if(blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pB, "Your success rate is: "+ pBPercentageCorrect+   "%"  );
                            
                              DecimalFormat myFormatter = new DecimalFormat("############.#");
                             String outputPA = myFormatter.format( (double)  htPOINTS.getObject(pA));
                             String outputPB = myFormatter.format( (double)  htPOINTS.getObject(pB));
                             
                             cC.c.sendInstructionToParticipant(pA, "Your score is: "+ outputPA );
                             cC.c.sendInstructionToParticipant(pB, "Your score is: "+ outputPB );
                  
                            
                         
                            cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ outputPA ,true, pA);
                            cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+  outputPB,true, pB);
                      
                             
                             
                             
                             
                            
                                 cC.c.showStimulusImageEnableButtons(pB, buttons, true);
                                 cC.c.showStimulusImageEnableButtons(pA, buttons, true);
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) +"\n",true, pA );
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) +"\n",true, pB );
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",false, pA,pB );
                            
                            cC.c.sendInstructionToParticipant(pA,"enter '/s' if you saw the same face");
                            cC.c.sendInstructionToParticipant(pA,"enter '/d' if you saw different faces");
                            cC.c.sendInstructionToParticipant(pB,"enter '/s' if you saw the same face");
                            cC.c.sendInstructionToParticipant(pB,"enter '/d' if you saw different faces");
                            
                            
                       }catch(Exception eee){
                           eee.printStackTrace();
                       }
                     }
                  };
                  tt.start();
                  
                 }catch (Exception e){
                     e.printStackTrace();
                 }
                 
                 
                 
             }  
         };
         t.start();
    }
    
   
    //private boolean isGuessCorrec
    
    
    private boolean isSelectionCorrect(int selectionposition){
        
      
        System.err.println("ISTARGETCORRECT: paIMagename: "+pA_Imagename);
        String targetfigure = pA_Imagename.substring(this.pA_Imagename.length()-7, this.pA_Imagename.length()-6);
        System.err.println("ISTARGETCORRECT: Target figure: "+targetfigure);
        
        
          System.err.println("ISTARGETCORRECT: pbIMagename: "+pB_Imagename);
        String mset =   pB_Imagename.substring(this.pB_Imagename.length()-5, this.pB_Imagename.length()-4);
        
         System.err.println("ISTARGETCORRECT: Matcher set: "+mset);
        int matchersetnumber = Integer.parseInt(mset);
        
        int positionOfFigureInMatcherSet = this.getPositionOfFigureInSetstart1(targetfigure, matchersetnumber);
        
         System.err.println("ISTARGETCORRECT: positionoffiginmatcherset "+positionOfFigureInMatcherSet);
         
         System.err.println("ISTARGETCORRECT: selection is: "+selectionposition);
        if(positionOfFigureInMatcherSet==selectionposition) return true;
        return false;
        
        
        
       
        
    }
    
    
    public synchronized void processChatText(Participant sender, String text){
        if(!text.startsWith("/"))return;
         if(istrainingphase && sender==this.pA)return;
         if(text.length()!=2)return;
         
         if(!istrainingphase && text.endsWith("n")){
             this.currentsethasbeensolved=true;
             return;
         }
         if(text.endsWith("N")){
             this.currentsethasbeensolved=true;
             return;
         }
         else if(text.endsWith("B")){
             //this.vpairs=new Vector();
             this.currentsethasbeensolved=true;
             this.vpairsFULL = new Vector();
             this.vpairs= new Vector();
             //System.exit(-567);
         }
         
         
        if(this.currentsethasbeensolved){
            cC.c.sendInstructionToParticipant(sender,"The current set has already been solved");
            return;
            
        }
        text=text.replace("\n", "");
        text=text.replace(" ", "");
        
       
        
        if(text.startsWith("/")){
           
            int selection=-1;
            try{
                 selection = Integer.parseInt(text.charAt(1)+"");
            }catch(Exception e){
                e.printStackTrace();
                return;
            }
            
            if(isSelectionCorrect(selection)){
                 this.updateScores(true);
                  String scoreA = " Score: "+(Double)this.htPOINTS.getObject(pA);
                  String scoreB = " Score: "+(Double)this.htPOINTS.getObject(pB);
                  if(showFeedbackToDirector) cC.c.sendInstructionToParticipant(pA, "CORRECT! " + scoreA);
                 cC.c.sendInstructionToParticipant(pB, "CORRECT! " + scoreB);
                 this.currentsethasbeensolved=true;
                 //doCountdowntoNextSet_DEPRECATED("CORRECT! They are the SAME", "Next face in "  );
                
            }
            else{
                this.updateScores(false);
                 String scoreA = " Score: "+(Double)this.htPOINTS.getObject(pA);
                 String scoreB = " Score: "+(Double)this.htPOINTS.getObject(pB);
                 if(showFeedbackToDirector)cC.c.sendInstructionToParticipant(pA, "INCORRECT! " + scoreA);
                cC.c.sendInstructionToParticipant(pB, "INCORRECT! " + scoreB);
                this.currentsethasbeensolved=true;
                //doCountdowntoNextSet_DEPRECATED("INCORRECT! They are  DIFFERENT","Next face in " );
                
            }
        
        }
        else{
            cC.c.sendInstructionToParticipant(sender,"Incorrect command:");
            cC.c.sendInstructionToParticipant(sender,"Choose '/s' if you think they are the same");
            cC.c.sendInstructionToParticipant(sender,"Choose '/d' if you think they are different");        
        }
    }
    
    
    
    
    private int getRandomNumberBetween(int lower_inclusive, int upper_exclusive, int exlusive){
        int value =   lower_inclusive+ r.nextInt(upper_exclusive-lower_inclusive);
        while(value==exlusive){
             value =   lower_inclusive+ r.nextInt(upper_exclusive-lower_inclusive);
        }
        return value;
    }
    
    
    public void updateScores(boolean success){
        if(success){
            int scorepAsuccess = this.getScoreCORRECT(pA);
            this.htscoreCORRECT.putObject(pA, scorepAsuccess+1);
            
            int scorepBsuccess = this.getScoreCORRECT(pB);
            this.htscoreCORRECT.putObject(pB, scorepAsuccess+1);
            
            
            double score = (double)  this.htPOINTS.getObject(pA);
            score=score+correctscoreinrement;
            
            this.htPOINTS.putObject(pA,score);
            score = (double)  this.htPOINTS.getObject(pB);
            score=score+correctscoreinrement;
            this.htPOINTS.putObject(pB,score);
            
        }
        else{
             int scorepAsuccess = this.getScoreINCORRECT(pA);
            this.htscoreINCORRECT.putObject(pA, scorepAsuccess+1);
            
            int scorepBsuccess = this.getScoreINCORRECT(pB);
            this.htscoreINCORRECT.putObject(pB, scorepAsuccess+1);
            
            double score = (double)  this.htPOINTS.getObject(pA);
            score=score-this.incorrectpenalty;
            if(score<0)score=0;
            this.htPOINTS.putObject(pA,score);
            score = (double)  this.htPOINTS.getObject(pB);
            score=score-this.incorrectpenalty;
            if(score<0)score=0;
            this.htPOINTS.putObject(pB,score);
            
            
        }
        
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_correct", pA, ""+this.getScoreCORRECT(pA));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_correct", pB, ""+this.getScoreCORRECT(pB));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_incorrect", pA, ""+this.getScoreINCORRECT(pA));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_incorrect", pB, ""+this.getScoreINCORRECT(pB));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_points", pA, ""+(double)  this.htPOINTS.getObject(pA));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_points", pB, ""+(double)  this.htPOINTS.getObject(pB));
        
        Conversation.printWSln("Main", pA.getUsername()+" has "+(double)  this.htPOINTS.getObject(pA));
        Conversation.printWSln("Main", pB.getUsername()+" has "+(double)  this.htPOINTS.getObject(pB));
        
    }
    
   
    
    
    
    public int getScoreCORRECT(Participant p){
        return (int)this.htscoreCORRECT.getObject(p);
    }
     public int getScoreINCORRECT(Participant p){
        return (int)this.htscoreINCORRECT.getObject(p);
    }
    
    public Vector getAdditionalValues(Participant p){
        
         String stimulusself ="";
         String stimulusother ="";
         Vector avs = new Vector();
         if(pA==p){
             stimulusself=this.pA_Imagename;
             stimulusother=this.pB_Imagename;
             AttribVal av0 = new AttribVal("gamenumber", ""+this.gamenumber);
             AttribVal av1 = new AttribVal("correct", ""+this.getScoreCORRECT(pA));
             AttribVal av2 = new AttribVal("incorrect", ""+this.getScoreINCORRECT(pA));
             AttribVal av3 = new AttribVal("stimulusself",stimulusself  );
             AttribVal av4 = new AttribVal("stimulusother",stimulusself  );
             AttribVal av5 = new AttribVal("points", ""+(double)  this.htPOINTS.getObject(pA));
             AttribVal av6 =null; 
             if(hasLoopedThroughAllFaces) {
                 av6 = new AttribVal("haslooped","YES")  ;
             } 
             else {
                 av6 = new AttribVal("haslooped","NO");
             }
             avs.addElement(av0);avs.addElement(av1); avs.addElement(av2); avs.addElement(av3); avs.addElement(av4); avs.addElement(av5);avs.addElement(av6);
         }
         if(pB==p){
             stimulusself=this.pB_Imagename;
             stimulusother=this.pA_Imagename;
             AttribVal av0 = new AttribVal("gamenumber", ""+this.gamenumber);
             AttribVal av1 = new AttribVal("correct", ""+this.getScoreCORRECT(pB));
             AttribVal av2 = new AttribVal("incorrect", ""+this.getScoreINCORRECT(pB));
             AttribVal av3 = new AttribVal("stimulusself",""+stimulusself  );
             AttribVal av4 = new AttribVal("stimulusother",""+stimulusself  ); 
             AttribVal av5 = new AttribVal("points", ""+(double)  this.htPOINTS.getObject(pB));
             AttribVal av6 =null;
             if(hasLoopedThroughAllFaces) {
                 av6 = new AttribVal("haslooped","YES")  ; 
             }else {
                 av6 = new AttribVal("haslooped","NO");
             }
             avs.addElement(av0);avs.addElement(av1); avs.addElement(av2); avs.addElement(av3); avs.addElement(av4);avs.addElement(av5);avs.addElement(av6);
         }
    
         return avs;
    } 
     
     
    @Override
    public void processNotification(String nameOfEvent) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(nameOfEvent.equalsIgnoreCase("timeout")){
            this.doCountdowntoNextSet_DEPRECATED("FIRSTMESSAGE", "FIRSTMESSAGE");
        }
    }

    @Override
    public void changeClientProgressBars(int value, String text) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public int getPositionOfFigureInSetstart1(String figure, int set){
        return getPositionOfFigureInSetstart0(figure, set)+1;
     }
    
    public int getPositionOfFigureInSetstart0(String figure, int set){
         if(figure.equalsIgnoreCase("A") && set==0) return 0;
         if(figure.equalsIgnoreCase("B") && set==0) return 1;
         if(figure.equalsIgnoreCase("C") && set==0) return 2;
         if(figure.equalsIgnoreCase("D") && set==0) return 3;
         if(figure.equalsIgnoreCase("E") && set==0) return 4;
         if(figure.equalsIgnoreCase("F") && set==0) return 5;
         if(figure.equalsIgnoreCase("G") && set==0) return 6;
         if(figure.equalsIgnoreCase("H") && set==0) return 7;
         
         if(figure.equalsIgnoreCase("A") && set==1) return 1;
         if(figure.equalsIgnoreCase("B") && set==1) return 4;
         if(figure.equalsIgnoreCase("C") && set==1) return 2;
         if(figure.equalsIgnoreCase("D") && set==1) return 5;
         if(figure.equalsIgnoreCase("E") && set==1) return 0;
         if(figure.equalsIgnoreCase("F") && set==1) return 6;
         if(figure.equalsIgnoreCase("G") && set==1) return 7;
         if(figure.equalsIgnoreCase("H") && set==1) return 3;
         
         if(figure.equalsIgnoreCase("A") && set==2) return 4;
         if(figure.equalsIgnoreCase("B") && set==2) return 7;
         if(figure.equalsIgnoreCase("C") && set==2) return 0;
         if(figure.equalsIgnoreCase("D") && set==2) return 6;
         if(figure.equalsIgnoreCase("E") && set==2) return 3;
         if(figure.equalsIgnoreCase("F") && set==2) return 1;
         if(figure.equalsIgnoreCase("G") && set==2) return 2;
         if(figure.equalsIgnoreCase("H") && set==2) return 5;
         
         if(figure.equalsIgnoreCase("A") && set==3) return 6;
         if(figure.equalsIgnoreCase("B") && set==3) return 4;
         if(figure.equalsIgnoreCase("C") && set==3) return 3;
         if(figure.equalsIgnoreCase("D") && set==3) return 0;
         if(figure.equalsIgnoreCase("E") && set==3) return 5;
         if(figure.equalsIgnoreCase("F") && set==3) return 7;
         if(figure.equalsIgnoreCase("G") && set==3) return 1;
         if(figure.equalsIgnoreCase("H") && set==3) return 2;
         
         if(figure.equalsIgnoreCase("A") && set==4) return 7;
         if(figure.equalsIgnoreCase("B") && set==4) return 0;
         if(figure.equalsIgnoreCase("C") && set==4) return 4;
         if(figure.equalsIgnoreCase("D") && set==4) return 1;
         if(figure.equalsIgnoreCase("E") && set==4) return 2;
         if(figure.equalsIgnoreCase("F") && set==4) return 3;
         if(figure.equalsIgnoreCase("G") && set==4) return 5;
         if(figure.equalsIgnoreCase("H") && set==4) return 6;
         
         if(figure.equalsIgnoreCase("A") && set==5) return 2;
         if(figure.equalsIgnoreCase("B") && set==5) return 3;
         if(figure.equalsIgnoreCase("C") && set==5) return 7;
         if(figure.equalsIgnoreCase("D") && set==5) return 5;
         if(figure.equalsIgnoreCase("E") && set==5) return 1;
         if(figure.equalsIgnoreCase("F") && set==5) return 6;
         if(figure.equalsIgnoreCase("G") && set==5) return 0;
         if(figure.equalsIgnoreCase("H") && set==5) return 4;
         
         if(figure.equalsIgnoreCase("A") && set==6) return 5;
         if(figure.equalsIgnoreCase("B") && set==6) return 7;
         if(figure.equalsIgnoreCase("C") && set==6) return 6;
         if(figure.equalsIgnoreCase("D") && set==6) return 2;
         if(figure.equalsIgnoreCase("E") && set==6) return 3;
         if(figure.equalsIgnoreCase("F") && set==6) return 4;
         if(figure.equalsIgnoreCase("G") && set==6) return 0;
         if(figure.equalsIgnoreCase("H") && set==6) return 1;
         
         if(figure.equalsIgnoreCase("A") && set==7) return 3;
         if(figure.equalsIgnoreCase("B") && set==7) return 4;
         if(figure.equalsIgnoreCase("C") && set==7) return 1;
         if(figure.equalsIgnoreCase("D") && set==7) return 2;
         if(figure.equalsIgnoreCase("E") && set==7) return 7;
         if(figure.equalsIgnoreCase("F") && set==7) return 0;
         if(figure.equalsIgnoreCase("G") && set==7) return 5;
         if(figure.equalsIgnoreCase("H") && set==7) return 6;
         
         if(figure.equalsIgnoreCase("A") && set==8) return 1;
         if(figure.equalsIgnoreCase("B") && set==8) return 6;
         if(figure.equalsIgnoreCase("C") && set==8) return 5;
         if(figure.equalsIgnoreCase("D") && set==8) return 4;
         if(figure.equalsIgnoreCase("E") && set==8) return 7;
         if(figure.equalsIgnoreCase("F") && set==8) return 2;
         if(figure.equalsIgnoreCase("G") && set==8) return 3;
         if(figure.equalsIgnoreCase("H") && set==8) return 0;
         
         
         return -1;
    }
    
    
    
    public static void main (String[] args){
         System.err.println("2");
         JointReferenceTaskTwoStages jrt2st = new JointReferenceTaskTwoStages(null);
         jrt2st.loadReferentlistTraining();;
        //loadReferentlistTraining();
    }
    
    
     private static Vector<int[]> getReferentlistTraining(){
         //loop through sets 0,1,2,3,4
         //loop 
         Vector allpairs = new Vector();
        for(int i=0;i<5;i++){
            
            for(int j=0;j<5;j++){
                if(i!=j){
                    int[] pairing = new int[]{i,j};
                    allpairs.add(pairing);
                }          
            }   
        };  
        
        
        
        
        //printListOfPairs(allpairs);
        return getListsTrainingGR(allpairs);
        
      
     }
     
     
     public static Vector<int[]> getListsTrainingGR(Vector<int[]> v){
          Vector<int[]> firstpart = getRandomValidSequence(v);
          
          int[] lastpair= firstpart.lastElement();
          
          
          boolean foundsecondpart = false;
          
          Vector<int[]> secondpart = new Vector();
          while(!foundsecondpart){
             Vector<int[]>secondpartcandidate = getRandomValidSequence(v);
             if(secondpartcandidate.firstElement()[0]!=lastpair[0]&&secondpartcandidate.firstElement()[1]!=lastpair[1]){
                 foundsecondpart = true;
                 secondpart = secondpartcandidate;
                 // System.exit(-568);
             }
             else{
                 System.err.println("REJECTING");
             }
             
             
          }
          
         for(int i=0;i<secondpart.size();i++){
             
             firstpart.add(secondpart.elementAt(i));
         }
         //System.err.println(firstpart.size()); System.exit(-5678);
          
         return firstpart;
     }
     
     
     
     
    
      public static Vector <int[]> getRandomValidSequence(Vector<int[]> v){
           boolean foundseq = false;
           while(!foundseq){
               Vector posslist =  generateRandomSequence(v);
               foundseq = checkIfListIsValid(posslist);
               printListOfPairs(posslist);
               return posslist;
           }
           System.exit(-9);
           return null;
      }
      
      public static boolean checkIfListIsValid(Vector<int[]> v){
          for(int i=0;i<v.size()-1;i++){
              int [] paira = v.elementAt(i);
              int[] pairb = v.elementAt(i+1);
              if(paira[0]==pairb[0]) return false;
              if(paira[1]==pairb[1]) return false;
          }
          return true;
      }
      
      static Random rr = new Random();
      
      public static Vector<int[]> generateRandomSequence(Vector<int[]> v){
          Vector candidatelist    = new Vector();
          for(int i=0;i<v.size();i++){
               candidatelist.insertElementAt(v.elementAt(i), rr.nextInt(candidatelist.size()+1));
          }
          
          return candidatelist;
      }
     
     
     public static void getList(Vector<int[]> v){
          Vector<int[]> unused = (Vector) v.clone();
          for(int i=0;i<v.size();i++){      
            Vector<int[]> possibleNext = getPermissible(v.elementAt(i)[0],      v.elementAt(i)[1], unused );    
             printListOfPairs(possibleNext);
             while(possibleNext.size()>0){
                 
             }
             
             
             
          }
         
     }
     
     /*
  
     (0, 1)  (1, 0)                 (1, 2) (2, 0) (2, 1) (0, 2)
     
     
     (0, 1)  (1, 0)   (2, 1)  (1, 2) (2, 0)  (0, 2)
     
     
     (0, 1)  (1, 0)   (2, 1)         (1, 2) (2, 0)  (0, 2)
     
     
     (0, 1)  (1, 0)                 (1, 2) (2, 0) (2, 1) (0, 2)
     
     
     */
     
     public static Vector<int[]> getPermissible(int d, int m, Vector<int[]> unused){
         Vector<int[]> permissible = new Vector(); 
         for(int i=0;i<unused.size();i++){
              int[] pair = unused.elementAt(i);
              if(pair[0]!=d&&pair[1]!=m){
                 permissible.addElement(pair);
              }  
         }
         return permissible;
     }
     
    
     
     
     
     public static void printListOfPairs(Vector<int[]> listv){
         for(int i=0;i<listv.size();i++){
                 System.out.print("("+listv.elementAt(i)[0]+", "+ listv.elementAt(i)[1]+") ");
             }
             System.out.println("");
     }
     
     
      public static String getListOfPairsAsString(Vector<int[]> listv){
         String retval="";
         for(int i=0;i<listv.size();i++){
                 retval = retval+("("+listv.elementAt(i)[0]+", "+ listv.elementAt(i)[1]+") ");
             }
         
         return retval;
     }
     
     
}
