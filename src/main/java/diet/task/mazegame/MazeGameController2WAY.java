/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

import diet.attribval.AttribVal;
import diet.debug.Debug;
import java.awt.Dimension;
import java.io.File;
import java.util.Vector;

import diet.message.MessageTask;

import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.task.DefaultTaskController;
import diet.task.mazegame.message.*;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author user
 * This class should actually be part of diet.task.mazegame.server
 * However, due to legacy support it has to be in this package as it uses non-public fields
 * of other classes in this package.
 */
public class MazeGameController2WAY extends DefaultTaskController{

    
    
    
    
    Conversation c;
    SetupIO sIO;
    SetupIOMazeGameMoveWriting sIOWriting;
    MazeGameController2WAYUI mgcUI;
    public Participant pDirector;
    public Participant pMatcher;
    
    
    Vector pDirectorMazes = new Vector();
    Vector pMatcher1Mazes = new Vector();
    
    
    MazeWrap pDirectorMaze, pMatcher1Maze;//, pMatcher2Maze;
    //Dimension pDirectorPosition, pMatcher1Position;//, pMatcher2Position;
    
    
    
    static long timeParticipantMustStayStill = -1;
    
    
    int mazeNumber =0;
    int prevMazeNumber =-1;
    
    
    int moveNo=0;
    
    int participant1MoveNo=0;
    int participant2MoveNo=0;

    boolean participant1IsOnSwitch;
    boolean participant2IsOnSwitch;

    int participant1NoOfTimesOnSwitch=0;
    int participant2NoOfTimesOnSwitch=0;
    
    long startOfCurrentMaze = new Date().getTime();
    long timeOfJointGoal = -1;
   
    
    
   
    
    
    MazeGameIsOnSwitchCountdownController mgioswcDirector;
    MazeGameIsOnSwitchCountdownController mgioswcMatcher;
    
    private void resetCounters(){
        moveNo=0;
        participant1MoveNo=0;
        participant2MoveNo=0;
        participant1NoOfTimesOnSwitch=0;
        participant2NoOfTimesOnSwitch=0;
        this.startOfCurrentMaze=new Date().getTime();
        this.timeOfJointGoal=-1;
         
    
    }
    
    
    public void saveDataForMaze(){
         //c.saveDataToConversationHistory(pDirector, pMatcher.getUsername(), new Date().getTime(), "MAZEINFO", "AbsMoveno:$"+participant1MoveNo+);
         
        String dataToSave ="";
        long timeOfExitFromMaze = new Date().getTime();
        try{
            String prevMazeNumberAsString ="FIRSTMAZE";
            if(this.prevMazeNumber>-1){
                prevMazeNumberAsString =""+prevMazeNumber;
            }
            
            
         dataToSave = dataToSave + 
                 
                 " mazenumber:"+ this.mazeNumber+
                 " previousmazenumber: "+prevMazeNumberAsString +
                 " mazestarttime:" + this.startOfCurrentMaze+
                 " mazestoptime:" + timeOfExitFromMaze;
                 
         if(this.isCompleted()){
             dataToSave = dataToSave+ " mazeiscompleted:" +"COMPLETED";
         } 
         else {
             dataToSave = dataToSave+ " mazeiscompleted:" +"INCOMPLETE";
         } 
         dataToSave = dataToSave+ " mazetotalnumberofgamemoves:"+moveNo;
         
         dataToSave = dataToSave+ " TimeOfBothOnGoal: "+ this.timeOfJointGoal;
         
         String dataToSaveDirector = dataToSave + " participanttotalnumberofgamemoves:"+participant1MoveNo +
                                     " participanttotalnumberofswitchtraversals:"+participant1NoOfTimesOnSwitch +
                                     " partnerid: "+pMatcher.getParticipantID()+
                                     " partnerusername: "+pMatcher.getUsername();
         
         String dataToSaveMatcher = dataToSave + " participanttotalnumberofgamemoves:"+participant2MoveNo +
                                     " participanttotalnumberofswitchtraversals:"+participant2NoOfTimesOnSwitch +
                                     " partnerid: "+pDirector.getParticipantID()+
                                     " partnerusername: "+pDirector.getUsername();
         
         
         
         
        String subdialogueID = "";
       try{
           subdialogueID = this.c.getController().pp.getSubdialogueID(pDirector);
       }catch (Exception ee){
           ee.printStackTrace();
       }
       
         
         
         
         c.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(pDirector, "", new Date().getTime(), subdialogueID, dataToSaveDirector);
         c.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(pMatcher, "", new Date().getTime(), subdialogueID, dataToSaveMatcher);
         
         }catch (Exception e){
             e.printStackTrace();
             c.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns("DATASAVING", "ERRORSAVING" +mazeNumber);
         }
         
     }
    
    
    public void saveDataForMazeNEWv4(){
        String status ="";
        if(this.isCompleted()){
            status="COMPLETED";
        }
        else{
            status="INCOMPLETE";
        }
        c.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(c.getController().pp.getSubdialogueID(pDirector), "DATA", pDirector.getParticipantID(), pDirector.getUsername(),  this.timeOfJointGoal, this.timeOfJointGoal, this.timeOfJointGoal, status +getMazeNo(), new Vector());
        c.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(c.getController().pp.getSubdialogueID(pMatcher), "DATA", pMatcher.getParticipantID(), pMatcher.getUsername(),  this.timeOfJointGoal, this.timeOfJointGoal, this.timeOfJointGoal, status +getMazeNo(), new Vector());
        
        
    }
    
   
    
    
    
    public void saveDataForMazev5(){
         //c.saveDataToConversationHistory(pDirector, pMatcher.getUsername(), new Date().getTime(), "MAZEINFO", "AbsMoveno:$"+participant1MoveNo+);
         
        //String dataToSave ="";
        long timeOfExitFromMaze = new Date().getTime();
        try{
            String prevMazeNumberAsString ="FIRSTMAZE";
            if(this.prevMazeNumber>-1){
                prevMazeNumberAsString =""+prevMazeNumber;
            }
            
            
         
          
                 AttribVal av1 = new AttribVal("mazenumber",this.mazeNumber );
                 AttribVal av2 = new AttribVal("previousmazenumber",prevMazeNumberAsString );
                 AttribVal av3 = new AttribVal("mazestarttime",this.startOfCurrentMaze);
                 AttribVal av4 = new AttribVal("mazestoptime",timeOfExitFromMaze);
                 AttribVal av5 = new AttribVal("mazeiscompleted","INCOMPLETE");
                 if(this.isCompleted()){
                     av5 = new AttribVal("mazeiscompleted","COMPLETED");
                 }
                 AttribVal av6 = new AttribVal("mazetotalnumberofgamemoves",moveNo);
                 AttribVal av7 = new AttribVal("timeofbothongoal",this.timeOfJointGoal);
                 
                 
                
                 AttribVal av8D = new AttribVal("participant1moveno", participant1MoveNo);
                 AttribVal av9D = new AttribVal("participanttotalnumberofswitchtraversals", participant1NoOfTimesOnSwitch);
                 AttribVal av10D = new AttribVal("mazepartnerid", pMatcher.getParticipantID());
                 AttribVal av11D = new AttribVal("mazepartnerusername", pMatcher.getUsername());
                 
                 String directorMaze = this.pDirectorMaze.m.toText().replace("\n", ";").replace("\r", ";");
                 String matcherMaze = this.pMatcher1Maze.m.toText().replace("\n", ";").replace("\r", ";");
                 
                 AttribVal av12D = new AttribVal("participant1maze", directorMaze);
                 AttribVal av13D = new AttribVal("participant2maze", matcherMaze);
         
                 Vector vToSaveDirector = new Vector();
                 vToSaveDirector.addElement(av1);  vToSaveDirector.addElement(av2);   vToSaveDirector.addElement(av3);   vToSaveDirector.addElement(av4);
                 vToSaveDirector.addElement(av5);  vToSaveDirector.addElement(av6);   vToSaveDirector.addElement(av7);   
                 
                 vToSaveDirector.addElement(av8D); vToSaveDirector.addElement(av9D);  vToSaveDirector.addElement(av10D);   vToSaveDirector.addElement(av11D); 
                 
                 vToSaveDirector.addElement(av12D); 
                 vToSaveDirector.addElement(av13D);       
                
                 
                 
                                
                  
                 AttribVal av8M = new AttribVal("participant1moveno", participant2MoveNo);
                 AttribVal av9M = new AttribVal("participanttotalnumberofswitchtraversals", participant2NoOfTimesOnSwitch);
                 AttribVal av10M = new AttribVal("mazepartnerid", pDirector.getParticipantID());
                 AttribVal av11M = new AttribVal("mazepartnerusername", pDirector.getUsername());
                 AttribVal av12M = new AttribVal("participant1maze", matcherMaze);
                 AttribVal av13M = new AttribVal("participant2maze", directorMaze);
                 
                
                 Vector vToSaveMatcher = new Vector();
                 vToSaveMatcher.addElement(av1);  vToSaveMatcher.addElement(av2);   vToSaveMatcher.addElement(av3);   vToSaveMatcher.addElement(av4);
                 vToSaveMatcher.addElement(av5);  vToSaveMatcher.addElement(av6);   vToSaveMatcher.addElement(av7);  
                 
                 vToSaveMatcher.addElement(av8M); vToSaveMatcher.addElement(av9M);  vToSaveMatcher.addElement(av10M);   vToSaveMatcher.addElement(av11M); 
                 
                 
                 vToSaveMatcher.addElement(av12M); 
                 vToSaveMatcher.addElement(av13M); 
                 
                 c.saveAdditionalRowOfDataToSpreadsheetOfTurns("mazeinfov5", pDirector, "", vToSaveDirector);
                 c.saveAdditionalRowOfDataToSpreadsheetOfTurns("mazeinfov5", pMatcher, "", vToSaveMatcher);
         
         
                 
         
         
         
         }catch (Exception e){
             e.printStackTrace();
             c.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns("DATASAVING", "ERRORSAVINGv5" +mazeNumber);
         }
         
     }
    
    
    
    
    
    
    
    
    
    
    public MazeGameController2WAY(Conversation c, File p1Mazes, File p2Mazes){
         CustomDialog.showModelessDialog("Important: You must press START when starting the experiment!\n");
       
         //ObjectInputStream oi = new ObjectInputStream
        try{
        ObjectInputStream p1Oi = new ObjectInputStream(new FileInputStream(p1Mazes));
        ObjectInputStream p2Oi = new ObjectInputStream(new FileInputStream(p2Mazes));
        
        Vector p1Mzs = (Vector)p1Oi.readObject();
        Vector p2Mzs = (Vector)p2Oi.readObject();
        
        this.c=c;
        
        if(timeParticipantMustStayStill==-1){
            MazeGameController2WAY.timeParticipantMustStayStill=CustomDialog.getLong("This is a new option for the maze game...\n"
            + "How long should a participant be stationary on a switch before it opens (msecs)\n"
            + "(This is to prevent participants from trying out each maze location withut communicating)\n", 2500);
        }
        
        
        this.pDirectorMazes=p1Mzs;
        this.pMatcher1Mazes=p2Mzs;
        //sIO = new SetupIO("C:\\svndiet\\chattool\\mazegame",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIO = new SetupIO(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+File.separator+"mazegame"+File.separator+"mazegamesetup",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIOWriting = new SetupIOMazeGameMoveWriting(c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        mgcUI = new MazeGameController2WAYUI(this);    
        
        
        
        }catch (Exception e){
            e.printStackTrace();
            Conversation.printWSln("Main", "Error loading mazes");
        }
        
        
        
    }
    
    public MazeGameController2WAY(Conversation c, Vector p1Mazes, Vector p2Mazes, long timeToStayStill){
        CustomDialog.showModelessDialog("Important: You must press START when starting the experiment!\n");
     
        timeParticipantMustStayStill=timeToStayStill;
          this.c=c;
          this.pDirectorMazes=p1Mazes;
        this.pMatcher1Mazes=p2Mazes;
        //sIO = new SetupIO("C:\\svndiet\\chattool\\mazegame",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIO = new SetupIO(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+File.separator+"mazegame"+File.separator+"mazegamesetup",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIOWriting = new SetupIOMazeGameMoveWriting(c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        mgcUI = new MazeGameController2WAYUI(this);    
          
    }
    
    public MazeGameController2WAY(Conversation c, Vector p1Mazes, Vector p2Mazes){
        CustomDialog.showModelessDialog("Important: You must press START when starting the experiment!\n");
        this.c=c;
        if(timeParticipantMustStayStill==-1){
            MazeGameController2WAY.timeParticipantMustStayStill=CustomDialog.getLong("This is a new option for the maze game...\n"
            + "How long should a participant be stationary on a switch before it opens (msecs)\n"
            + "(This is to prevent participants from trying out each maze location withut communicating)\n", 2500);
        }
        this.pDirectorMazes=p1Mazes;
        this.pMatcher1Mazes=p2Mazes;
        //sIO = new SetupIO("C:\\svndiet\\chattool\\mazegame",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIO = new SetupIO(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+File.separator+"mazegame"+File.separator+"mazegamesetup",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIOWriting = new SetupIOMazeGameMoveWriting(c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        mgcUI = new MazeGameController2WAYUI(this);    
    }
    
    
    
    
     
    
    
    
    public MazeGameController2WAYUI getUI(){
        return this.mgcUI;
    }
    
    public void updateJProgressBar(final int value, final String text, final Color color){
        mgcUI.updateJProgressBar(value, text, color);
    }
    
    public Vector getP1Mazes_Cloned(){
        return cloneVectorOfMazes(this.pDirectorMazes);
    }
    public Vector getP2Mazes_Cloned(){
        
        return cloneVectorOfMazes(this.pMatcher1Mazes);
    }
    
    
         
    
    public void startNewMazeGame(Participant a, Participant b){
        Conversation.printWSln("Main", "Mazegame controller is sending mazes to both clients "+a.getUsername()+" and also to "+b.getUsername());
        pDirector = a;
        pMatcher=b;
        if(mgioswcDirector!=null)mgioswcDirector.abort();       
        if(mgioswcMatcher!=null)mgioswcMatcher.abort();
        this.mgioswcDirector = new MazeGameIsOnSwitchCountdownController(this,pDirector,pMatcher, timeParticipantMustStayStill);
        this.mgioswcMatcher = new MazeGameIsOnSwitchCountdownController(this,pMatcher, pDirector, timeParticipantMustStayStill);
        
        
        this.startOfCurrentMaze = new Date().getTime();
        this.sendMazesToClients();
        sIO.saveClientMazesOfTwoClientsByName(a.getUsername(), b.getUsername(),       pDirectorMazes, pMatcher1Mazes);
    }
    
    
    
    
    
    
    public void appendToUI(String chattext){
        this.mgcUI.append(chattext);
    }
    
    
    
  
    
    
    
   
    
   
    
   
    
    
   
    
   
    
    public static synchronized Vector cloneVectorOfMazes(Vector v){
            Vector v2 = new Vector();
            for(int i=0;i<v.size();i++){
                Maze m = (Maze)v.elementAt(i);
                Maze m2 = m.getClone();
                v2.addElement(m2); 
            }
            return v2;
    }
    
    public void initializeMazesToFirstMaze(){
        pDirectorMaze = new MazeWrap((Maze) pDirectorMazes.elementAt(0));
        pMatcher1Maze = new MazeWrap((Maze) pMatcher1Mazes.elementAt(0));
        mazeNumber=0;
        //pDirectorPosition = new Dimension(0,0);
        //pMatcher1Position = new Dimension(0,0);
    }
    
    public void sendMazesToClients(){
         initializeMazesToFirstMaze();
          mgcUI.initializeJTabbedPane(pDirector.getUsername(),pDirectorMazes,pMatcher.getUsername(),this.pMatcher1Mazes);//,pMatcher2.getUsername(),pMatcher2Mazes);
      
         Game gDirector = new Game(pDirectorMazes);
         Game gMatcher1 = new Game(pMatcher1Mazes);
         
         //expIOW.saveMazes(client1mazes, client2mazes, client3mazes);
         c.sendTaskMoveToParticipant(pDirector, new MessageNewMazeGame("server","server",gDirector));
         c.sendTaskMoveToParticipant(pMatcher, new MessageNewMazeGame("server","server",gMatcher1));
    }
   
    
    public void reconnectParticipant(Participant pReloggingIn){
        if(pReloggingIn==pDirector){
             Game gDirector = new Game(pDirectorMazes);
             c.sendTaskMoveToParticipant(pDirector, new MessageNewMazeGame("server","server",gDirector));
             c.sendInstructionToParticipant(pDirector, "Please continue");
             c.sendInstructionToParticipant(pMatcher, "Network error. Your partner has logged back in. Resetting to the start of the maze");
             
             if(Debug.debugmazegamereconnect)  c.sendInstructionToParticipant(pReloggingIn, "(2a) You are the director....and the director ID IS:"+pDirector.getParticipantID()+"....."+pDirector.getUsername());
       
             
            
        }
        else if(pReloggingIn==pMatcher){
             Game gMatcher1 = new Game(pMatcher1Mazes);
             
             
             c.sendTaskMoveToParticipant(pMatcher, new MessageNewMazeGame("server","server",gMatcher1));
             c.sendInstructionToParticipant(pMatcher, "Please continue");
             c.sendInstructionToParticipant(pDirector, "Network error. Your partner has logged back in. Resetting to the start of the maze");
              if(Debug.debugmazegamereconnect)  c.sendInstructionToParticipant(pReloggingIn, "(2b) You are the matcher....and the director ID IS:"+pDirector.getParticipantID()+"....."+pDirector.getUsername());
       
        }
        //this.moveToMazeNo(this.mazeNumber);
        appendToUI("PARTICIPANT: "+pReloggingIn.getUsername()+" IS LOGGING BACK IN. CHECK NETWORK CABLES!");
        
        /* if(Debug.debugmazegamereconnect) {
             try{
                 System.err.println("5");
                 Thread.sleep(1000);
                  System.err.println("4");
                 Thread.sleep(1000);
                 System.err.println("3");
                 Thread.sleep(1000);
                  System.err.println("2");
                 Thread.sleep(1000);
                  System.err.println("1");
                 Thread.sleep(1000);
                  System.err.println("0");
                 Thread.sleep(1000);
                 
             }catch(Exception e){
                 e.printStackTrace();R
             }
          
         }*/
        this.moveToMazeNo(this.mazeNumber);
       
        
        
         
       
        
    }
    
    
    
    
    
    synchronized public boolean isAonOthersSwitch(String a){
        if(this.pDirector.getUsername().equalsIgnoreCase(a)){
            //is participant 1 on participant 2s switch?
            return this.participant1IsOnSwitch;
        }
        else if(this.pMatcher.getUsername().equalsIgnoreCase(a)){
            return this.participant2IsOnSwitch;
        }
        else{
            Conversation.printWSln("Main", "Error in working out whether A is on Other's switch because "+a+" is not (1) "+pDirector.getUsername()+" or(2)"+pMatcher.getUsername());
            return false;
        }
    }

    synchronized public boolean isOtherSOnAswitch(String a){
        try{
            if(this.pDirector.getUsername().equalsIgnoreCase(a)){
                //is participant 1 on participant 2s switch?
                return this.participant2IsOnSwitch;
            }
            else if(this.pMatcher.getUsername().equalsIgnoreCase(a)){
                return this.participant1IsOnSwitch;
            }
            else{
                Conversation.printWSln("Main", "Error in working out whether Other is On A's switch because "+a+" is not (1) "+pDirector.getUsername()+" or(2)"+pMatcher.getUsername());
           
                return false;
            }
        }catch (Exception e){
             Conversation.printWSln("Main", "Error in working out whether Other is On A's switch");
             return false;
        }
    }


    synchronized public int getSwitchTraversalCount(String participantname){
      try{
        if(this.pDirector.getUsername().equalsIgnoreCase(participantname)){
            //is participant 1 on participant 2s switch?
            return this.participant1NoOfTimesOnSwitch;
        }
        else if(this.pMatcher.getUsername().equalsIgnoreCase(participantname)){
            return this.participant2NoOfTimesOnSwitch;
        }
        else{
            Conversation.printWSln("Main", "Error in working out traversal count (1) "+participantname);
            return -1;
        }
        }
      catch (Exception e){
            Conversation.printWSln("Main", "Error in working out traversal count (2) "+e.getMessage());
            return -1;
      }
    }

synchronized public int getOthersSwitchTraversalCount(String participantname){
      try{
        if(this.pDirector.getUsername().equalsIgnoreCase(participantname)){
            //is participant 1 on participant 2s switch?
            return this.participant2NoOfTimesOnSwitch;
        }
        else if(this.pMatcher.getUsername().equalsIgnoreCase(participantname)){
            return this.participant1NoOfTimesOnSwitch;
        }
        else{
            Conversation.printWSln("Main", "Error in working out switch traversal");
            return -1;
        }
        }
      catch (Exception e){
            Conversation.printWSln("Main", "Error in working out switch traversal");
            return -1;
      }
    }

    public int getOthersMoveNo(String participantname){
        try{
            if(this.pDirector.getUsername().equalsIgnoreCase(participantname)){
                //is participant 1 on participant 2s switch?
                return this.participant2MoveNo;
             }
             else if(this.pMatcher.getUsername().equalsIgnoreCase(participantname)){
                return this.participant1MoveNo;
        }
        else{
            Conversation.printWSln("Main", "Error in working out Others Move No");
            return -1;
            }
      }
      catch (Exception e){
            Conversation.printWSln("Main", "Error in working out Others Move No");
            return -1;

    }
    }

    private Hashtable htTimeOutInfo = new Hashtable();
    
    
    
    public synchronized boolean isTimedOut(int mazeNo){
        try{
            Object o = this.htTimeOutInfo.get(mazeNo);
            if(o==null)return false;
            return (boolean)o;
        }
        catch(Exception e){
            e.printStackTrace();
            
        }
        return false;
    }
    public synchronized boolean setTimedOut(int mazeNo){
        if(this.isCompleted())return false;
        htTimeOutInfo.put(mazeNo, true);
        return true;
    }
    
    
    public synchronized boolean processMazeMove(MessageTask mt,Participant origin, boolean automaticallyGoTONextMaze){
      if(this.isCompleted())return true;
      //if(this.isTimedOut( this.mazeNumber) ) return true;
      
       if (mt instanceof MessageCursorUpdate){
             MessageCursorUpdate mcu = (MessageCursorUpdate)mt;
             if( mcu.getMazeNo() != this.getMazeNo()){
                  Conversation.printWSln("Main", "Possible network lag and/or error. Experiment is receiving maze cursor updates from "+origin.getUsername()+"...from maze number "+mcu.getMazeNo()+"..."+"but the current maze is: "+this.getMazeNo());
          
             }
       }      
      
      
      
      
      
      
      try{   
        if(origin==pDirector){
          if (mt instanceof MessageCursorUpdate){
             MessageCursorUpdate mcu = (MessageCursorUpdate)mt;
             
              
              
             System.out.println("Server detects client1 sending cursor update");
             this.mgioswcDirector.updateIsOffSwitch();
             
             this.pDirectorMaze.getMaze().moveTo(mcu.newPos);
             this.mgcUI.movePositionDirector(mcu.newPos);
             
             if(pMatcher1Maze.getMaze().isASwitch(pDirectorMaze.getMaze().getMinBounds(),mcu.newPos)){
                 mcu.setASwitch(true);
                 System.out.println("DETECTING SWITCH..");
             }
             else{
                 mcu.setASwitch(false);
                 System.out.println("DETECTING NOT SWITCH..");
             }
             if(pMatcher1Maze.getMaze().isASwitch(pDirectorMaze.getMaze().getMinBounds(),mcu.newPos)){ 
                this.mgioswcDirector.updateIsOnSwitch();
                mcu.setASwitch(true);
                this.sIOWriting.saveMessage(mcu);
                this.participant1IsOnSwitch=true;
                this.participant1NoOfTimesOnSwitch++;
                
            }
            else {
                 System.out.println("Server detects client1 as NOT being on switch");
                 MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",false,pMatcher.getUsername());
                 c.sendTaskMoveToParticipant(pMatcher,mscg);
                 this.mgcUI.changeGateStatusMatcher(false);
                 mcu.setASwitch(false);
                 this.sIOWriting.saveMessage(mcu);
                 this.sIOWriting.saveMessage(mscg);

             }
             try{
                  c.saveAdditionalRowOfDataToSpreadsheetOfTurns("mazemove", origin, "");
              }catch(Exception e){
                 e.printStackTrace();
              }
             
           }

          else {
            System.out.println("experiment in progress , was expecting username or cursor");
            System.out.println("don't know: " + mt.getClass().toString());
            }   

        //messages.addElement(m);
        ////expIOW.saveMessage(m);

       }
     else if(origin == pMatcher ) {
       if (mt instanceof MessageCursorUpdate){
           System.out.println("Server detects client2 sending cursor update");
           this.mgioswcMatcher.updateIsOffSwitch();
           MessageCursorUpdate mcu = (MessageCursorUpdate)mt;
           pMatcher1Maze.getMaze().moveTo(mcu.newPos);
           this.mgcUI.movePositionMatcher(mcu.newPos);
           if(pDirectorMaze.getMaze().isASwitch(pMatcher1Maze.getMaze().getMinBounds(),mcu.newPos)){
                 mcu.setASwitch(true);
                 System.out.println("DETECTING SWITCH..B");
             }
             else{
                 mcu.setASwitch(false);
                  System.out.println("DETECTING NOTSWITCH..NOTB");
           }
           //MazeSquare current3=((Maze)client3Maze).getCurrent();
           if(pDirectorMaze.getMaze().isASwitch(pMatcher1Maze.getMaze().getMinBounds(),mcu.newPos)){
             
              //
              /*
              System.out.println("Server detects client2 and client3 as being on client1's switch");
              MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",true,pDirector.getUsername());
              c.sendTaskMoveToParticipant(pDirector,mscg);
              this.mgcUI.changeGateStatusDirector(true);    
              //mcu.setASwitch(true);
              this.sIOWriting.saveMessage(mcu);
              this.sIOWriting.saveMessage(mscg);

              
              */
               this.mgioswcMatcher.updateIsOnSwitch();
               mcu.setASwitch(true);
               this.sIOWriting.saveMessage(mcu);
               this.participant2IsOnSwitch=true;
               this.participant2NoOfTimesOnSwitch++; 

          }
          else {
            System.out.println("Server detects client2 or client3 as NOT being on  cl1's switch");
            MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",false,pDirector.getUsername());
            c.sendTaskMoveToParticipant(pDirector,mscg);
            this.mgcUI.changeGateStatusDirector(false);
            
            //mcu.setASwitch(false);
            this.sIOWriting.saveMessage(mcu);
            this.sIOWriting.saveMessage(mscg);
          }
          try{
              c.saveAdditionalRowOfDataToSpreadsheetOfTurns("mazemove", origin, "");
              }catch(Exception e){
              e.printStackTrace();
          }
       }

      else {
          System.out.println("experiment in progress2 , was expecting username or cursor update");
          System.out.println("don't know: " + mt.getClass().toString());
      }
      
      //expIOW.saveMessage(m);

   }
     
   moveNo++;
     if(origin==this.pDirector){
         participant1MoveNo++;
     }
     else if(origin == this.pMatcher){
         participant2MoveNo++;
     }     
        
     
   if(pDirectorMaze.getMaze().isCompleted() && pMatcher1Maze.getMaze().isCompleted()){
       if(timeOfJointGoal<0)this.timeOfJointGoal = new Date().getTime();
       
       String subdialogueID = "";
       try{
           subdialogueID = this.c.getController().pp.getSubdialogueID(pDirector);
       }catch (Exception ee){
           ee.printStackTrace();
       }
       c.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(subdialogueID, "MAZECOMPLETE:"+ "("+this.mazeNumber+")"+ 
               " ("+pDirector.getParticipantID()+","+ pDirector.getUsername()+
               " ("+pMatcher.getParticipantID()+ ", "+pMatcher.getUsername()+timeOfJointGoal);
       
       
        
   }  
        
   ///if(automaticallyGoTONextMaze&&  pDirectorMaze.getMaze().isCompleted()&&pMatcher1Maze.getMaze().isCompleted()){
   ///   System.out.println("Maze number"+ mazeNumber+ " completed");
   ///   moveToNextMaze();
      
    

   ///}
     }catch (Exception e){
         System.out.println("STACKTRACE");
         System.err.println("ERROR IN MAZEGAME TASKMOVE HANDLER");
         e.printStackTrace();
     }      

      
      
     notifyAll();
     return (pDirectorMaze.getMaze().isCompleted()&&pMatcher1Maze.getMaze().isCompleted());
           
    }
    
    
    synchronized public boolean isCompleted(){
        return pDirectorMaze.getMaze().isCompleted()&&pMatcher1Maze.getMaze().isCompleted();
    }
    

    synchronized public void processTaskMove(MessageTask mt, Participant origin){
       this.processMazeMove(mt, origin, true);
  
    }
    
    
  public void displayMessage(String s){
      MessageWaitForOthersToCatchUp mwfotcu = new  MessageWaitForOthersToCatchUp("server","server",s,Color.red);
      c.sendTaskMoveToParticipant(pDirector,mwfotcu);
      c.sendTaskMoveToParticipant(pMatcher,mwfotcu);
  }  
    
   
  
  
  
 synchronized public boolean moveToMazeNo(int newMazeNo){
     try{
     return moveToMazeNo(newMazeNo, "Moving to maze "+newMazeNo);
     }catch (Exception e){
         Conversation.printWSln("Main", "COULD NOT MOVE TO MAZENO "+newMazeNo);
         return false;
     }
 } 
  
 synchronized public boolean moveToMazeNo(int mazeNo,String text){
   text = text + "!";
   if(mgioswcDirector!=null)mgioswcDirector.updateIsOffSwitch();
   if(mgioswcMatcher!=null)mgioswcMatcher.updateIsOffSwitch();
   if(mazeNo<0)mazeNo=0;
   if(mazeNo>=pDirectorMazes.size())mazeNo=pDirectorMazes.size()-1;
   //
   resetCounters();
   if (mazeNumber>=pDirectorMazes.size()-1 ||mazeNumber>=pMatcher1Mazes.size()-1) 
      {
       System.out.println("MoveToMazeNo returning false "+mazeNumber);
       Conversation.printWSln("Main", "Experimenter attempted to go to next or previous maze that doesn't exist...so doing nothing");
       return false;
   }
   resetCounters();
   pDirectorMaze = new MazeWrap((Maze) pDirectorMazes.elementAt(mazeNo));
   pMatcher1Maze = new MazeWrap((Maze) pMatcher1Mazes.elementAt(mazeNo));
   prevMazeNumber=mazeNumber;
   mazeNumber=mazeNo;
   
   //pDirectorPosition = new Dimension(0,0);
   //pMatcher1Position = new Dimension(0,0);
   System.out.println("move to maze number");
   //MessageNextMaze_SentAsIndex mnm= new MessageNextMaze_SentAsIndex("server","server",mazeNo,"Moving to maze "+mazeNo);
   MessageNextMaze_SentAsIndex mnm= new MessageNextMaze_SentAsIndex("server","server",mazeNo, text);
   this.mgcUI.moveToMazeNo(mazeNo);
   c.sendTaskMoveToParticipant(pDirector,mnm);
   c.sendTaskMoveToParticipant(pMatcher,mnm);
   
   this.sIOWriting.saveMessage(mnm);
   
   return true;
} 
  
 

    
 public boolean moveToNextMaze(){
     return this.moveToNextMaze("");
 }
 
   
 
 
  public boolean moveToNextMaze(String displayMessage){
   //this.saveDataForMaze();
   resetCounters();
   if(mgioswcDirector!=null)mgioswcDirector.updateIsOffSwitch();
   if(mgioswcMatcher!=null)mgioswcMatcher.updateIsOffSwitch();
    if (mazeNumber>=pDirectorMazes.size()-1 ||mazeNumber>=pMatcher1Mazes.size()-1) 
      {
       System.out.println("MoveToNextMaze returning false "+mazeNumber);
       return false;
   }
   
   pDirectorMaze = new MazeWrap((Maze) pDirectorMazes.elementAt(pDirectorMazes.indexOf(pDirectorMaze.getMaze())+1));
   pMatcher1Maze = new MazeWrap((Maze) pMatcher1Mazes.elementAt(pMatcher1Mazes.indexOf(pMatcher1Maze.getMaze())+1));
   prevMazeNumber=mazeNumber;
   mazeNumber++;
   //pDirectorPosition = new Dimension(0,0);
   //pMatcher1Position = new Dimension(0,0);
    System.out.println("move to next maze EXPERIMENT IS TRUE");
   MessageNextMaze_SentAsIndex mnm= new MessageNextMaze_SentAsIndex("server","server",mazeNumber, displayMessage);
   c.sendTaskMoveToParticipant(pDirector,mnm);
   c.sendTaskMoveToParticipant(pMatcher,mnm);
   this.mgcUI.moveToMazeNo(mazeNumber);
   this.sIOWriting.saveMessage(mnm);
    
   this.c.getController().performActionCalledByTaskController(""+mazeNumber);
   
   return true;
}

   public String getDirectorOrMatcher(Participant p){
       if(p==pDirector)return "D";
       if(p==pMatcher)return "M1";
       return "";
   }
    
    
    @Override
    public void closeDown(){
        try{
          this.sIOWriting.closeDown();
        }catch(Exception e){
            System.err.println("ERROR CLOSING DOWN MAZEGAME CONTROLLER");
        }  
    }
    
    public int getMazeNo(){
        return this.mazeNumber;
    }
    public int getMoveNo(){
        return this.moveNo;
    }
    public int getParticipantsMoveNo(Participant p){
        if(p==pDirector){
            return this.participant1MoveNo;
        }
        else if(p==pMatcher){
            return this.participant2MoveNo;
        }
        return -9999999;
    }
    
    public Vector getAdditionalData(Participant p){
        Vector v = new Vector();
        AttribVal av1 = new AttribVal("mazeno",this.mazeNumber);
        v.addElement(av1);
        
        if(p==this.pDirector){
              AttribVal av2 = new AttribVal("mazeID",this.pDirectorMaze.m.id);
              v.addElement(av2);
              
              Dimension pDirectorPosition = this.pDirectorMaze.getCurrent();
              
              if (pDirectorPosition!=null){
                  AttribVal av3 = new AttribVal("currentposx", pDirectorPosition.width );
                  AttribVal av4 = new AttribVal("currentposy", pDirectorPosition.height );
                  v.addElement(av3);  
                  v.addElement(av4);
                 
                  if(this.participant1IsOnSwitch){
                      v.addElement(new AttribVal("isonothersswitch","onswitch"));
                  }
                  else{
                      v.addElement(new AttribVal("isonothersswitch","not"));
                  }   
              }
              
             
              
             ;
           
        }
        if(p==this.pMatcher){
            AttribVal av2 = new AttribVal("mazeID",this.pMatcher1Maze.m.id);
            v.addElement(av2);
            
            Dimension pMatcher1Position = this.pMatcher1Maze.getCurrent();
            
            if (pMatcher1Position!=null){
                  AttribVal av3 = new AttribVal("currentposx", pMatcher1Position.width );
                  AttribVal av4 = new AttribVal("currentposy", pMatcher1Position.height );
                  v.addElement(av3);  
                  v.addElement(av4);
                 
                  if(this.participant2IsOnSwitch){
                      v.addElement(new AttribVal("isonothersswitch","onswitch"));
                  }
                  else{
                      v.addElement(new AttribVal("isonothersswitch","not"));
                  }   
              }
        }
        
        
        return v;
        
    }
    
    
}
