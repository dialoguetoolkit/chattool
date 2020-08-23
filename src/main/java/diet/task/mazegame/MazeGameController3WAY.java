/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

import java.awt.Dimension;
import java.io.File;
import java.util.Random;
import java.util.Vector;

import diet.message.MessageTask;

import diet.server.Conversation;
import diet.server.Participant;
import diet.task.DefaultTaskController;
import diet.task.mazegame.message.MessageChangeGateStatus;
import diet.task.mazegame.message.MessageCursorUpdate;
import diet.task.mazegame.message.MessageNewMazeGame;
import diet.task.mazegame.message.MessageNextMaze_SentAsIndex;

/**
 *
 * @author user
 * This class should actually be part of diet.task.mazegame.server
 * However, due to legacy support it has to be in this package as it uses non-public fields
 * of other classes in this package.
 */
public class MazeGameController3WAY extends DefaultTaskController{

    Conversation c;
    SetupIO sIO;
    SetupIOMazeGameMoveWriting sIOWriting;
    MazeGameController3WAYUI mgcUI;
    Participant pDirector;
    Participant pMatcher1;
    Participant pMatcher2;
    
    Vector pDirectorMazes = new Vector();
    Vector pMatcher1Mazes = new Vector();
    Vector pMatcher2Mazes = new Vector();
    
    MazeWrap pDirectorMaze, pMatcher1Maze, pMatcher2Maze;
    Dimension pDirectorPosition, pMatcher1Position, pMatcher2Position;
    
    int mazeNumber =0;
    
    
    
    public MazeGameController3WAY(Conversation c){
        this.c=c;
        //sIO = new SetupIO("C:\\svndiet\\chattool\\mazegame",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
       sIO = new SetupIO(System.getProperty("user.dir")+File.separator+"mazegame"+File.separator+"mazegamesetup",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
         sIOWriting = new SetupIOMazeGameMoveWriting(c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        mgcUI = new MazeGameController3WAYUI(this);
        
        
        
    }
    
    
    
    public void participantJoinedConversation(Participant pJoined){
        //If the program gets to the end of this method 
        //The director and matchers should have been established
        Vector v = (Vector)c.getParticipants().getAllActiveParticipants().clone();
        v = (Vector)c.getParticipants().getAllParticipants().clone();
        if(v.size()!=3) {
            System.err.println("THERE SHOULD BE THREE PARTICIPANTS...THERE ARE "+v.size());
            return;
        }
        for(int i=0;i<v.size();i++){
            Participant p = (Participant) v.elementAt(i);
            String pEmail = p.getParticipantID();
            System.err.println("THE EMAIL IS: "+pEmail);
            System.err.println("THE USERNAME IS: "+p.getUsername());
            if(pEmail.startsWith("direct") || pEmail.startsWith("Direct")||pEmail.equalsIgnoreCase("director")){
                pDirector = p;
                v.remove(p);
            }       
        }
        if(pDirector==null){
            System.err.println("ERROR.....COULD NOT FIND DIRECTOR");
            System.exit(-123456789);
        }
        
        pMatcher1 = (Participant) v.elementAt(0);
        pMatcher2 = (Participant) v.elementAt(1);
        loadAndSendMazesToClients();
        sIO.saveClientMazesFromSetupNo_DEPRECATED_NEEDSTOBEREWRITTEN(pDirectorMazes, pMatcher1Mazes, pMatcher2Mazes, "");
    }
    
   
    
    
    public void loadAndSendMazesToClients(){
        try{
          System.err.println("MESSAGE0");
          //Check to see if the experiment is actually re-starting a previous one
          //If so, need to load the old mazes
         
             //System.exit(-12312324); 
           loadRandomMazesFromFile();
             
          System.err.println("MESSAGE1");
          initializeMazesToFirstMaze();
          
          System.err.println("MESSAGE2");
          sendMazesToClients();
          System.err.println("MESSAGE3");
          mgcUI.initializeJTabbedPane(pDirector.getUsername(),this.pDirectorMazes,
                  pMatcher1.getUsername(),pMatcher1Mazes,pMatcher2.getUsername(),pMatcher2Mazes);
        }catch(Exception e){
            System.err.println(e.getMessage().toString());
            System.out.println("STACKTRACE");
            e.printStackTrace();
            System.exit(-123423);
        }  
    }
    
    public void loadRandomMazesFromFile(){
        System.err.println("HEREA");
        Vector v = this.sIO.getRandomPairOfMazeVectors();
        System.err.println("HEREB");
        Vector client1Mazes = (Vector)v.elementAt(0);
        System.err.println("HEREC");
        Vector client2Mazes = (Vector)v.elementAt(1);
        System.err.println("HERED");
        Random r = new Random();
        System.err.println("HEREE");
        int i = r.nextInt(2);
        System.err.println("HEREF");
        if(i==1){
            System.err.println("HEREG1");
            this.pDirectorMazes=(Vector)v.elementAt(0);
            System.err.println("HEREG2");
            this.pMatcher1Mazes=(Vector)v.elementAt(1);
            System.err.println("HEREG3");
            this.pMatcher2Mazes=cloneVectorOfMazes(pMatcher1Mazes);
            System.err.println("HEREG4");
            //for(int i=0;i<pMatcher1Mazes.s)
            
        }
        else{
            this.pDirectorMazes=(Vector)v.elementAt(1);
            System.err.println("HEREI1");
            this.pMatcher1Mazes=(Vector)v.elementAt(0);
            System.err.println("HEREI2");
            this.pMatcher2Mazes=cloneVectorOfMazes(pMatcher1Mazes);
            System.err.println("HEREI3");
        }
        
        
    }
    
    public Vector cloneVectorOfMazes(Vector v){
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
        pMatcher2Maze = new MazeWrap((Maze) pMatcher2Mazes.elementAt(0));
        mazeNumber=0;
        pDirectorPosition = new Dimension(0,0);
        pMatcher1Position = new Dimension(0,0);
        pMatcher2Position = new Dimension(0,0);
    }
    
    public void sendMazesToClients(){
         Game gDirector = new Game(pDirectorMazes);
         Game gMatcher1 = new Game(pMatcher1Mazes);
         Game gMatcher2 = new Game(pMatcher2Mazes);
         //expIOW.saveMazes(client1mazes, client2mazes, client3mazes);
         c.sendTaskMoveToParticipant(pDirector, new MessageNewMazeGame("server","server",gDirector));
         c.sendTaskMoveToParticipant(pMatcher1, new MessageNewMazeGame("server","server",gMatcher1));
         c.sendTaskMoveToParticipant(pMatcher2, new MessageNewMazeGame("server","server",gMatcher2));

    }
   
    
    synchronized public void processTaskMove(MessageTask mt, Participant origin){
        //System.exit(-2345);
     try{   
        if(origin==pDirector){
          if (mt instanceof MessageCursorUpdate){
             System.out.println("Server detects client1 sending cursor update");
             
             MessageCursorUpdate mcu = (MessageCursorUpdate)mt;
             this.pDirectorMaze.getMaze().moveTo(mcu.newPos);
             this.mgcUI.movePositionDirector(mcu.newPos);
             if(pMatcher1Maze.getMaze().isASwitch(pDirectorMaze.getMaze().getMinBounds(),mcu.newPos)){
                 mcu.setASwitch(true);
             }
             else{
                 mcu.setASwitch(false);
             }
             if(pMatcher1Maze.getMaze().isASwitch(pDirectorMaze.getMaze().getMinBounds(),mcu.newPos)){
                System.out.println("Server detects client1 as being on client 2 and 3 switch");
                MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",true,pMatcher1.getUsername()+"|"+pMatcher2.getUsername());
                //mscg.setRecipient(pMatcher1.getUsername()+"|"+pMatcher2.getUsername());
                //mcu.setASwitch(true);
                c.sendTaskMoveToParticipant(pMatcher1,mscg);
                c.sendTaskMoveToParticipant(pMatcher2,mscg);
                this.mgcUI.changeGateStatusMatcher1(true);
                this.mgcUI.changeGateStatusMatcher2(true);
                mcu.setASwitch(true);
                this.sIOWriting.saveMessage(mcu);
                this.sIOWriting.saveMessage(mscg);
                }
                else {
                    System.out.println("Server detects client1 as NOT being on switch");
                    MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",false,pMatcher1.getUsername()+"|"+pMatcher2.getUsername());
                    //mscg.setRecipient(pMatcher1.getUsername()+"|"+pMatcher2.getUsername());
                    //mcu.setASwitch(false);
                    c.sendTaskMoveToParticipant(pMatcher1,mscg);
                    c.sendTaskMoveToParticipant(pMatcher2,mscg);
                    this.mgcUI.changeGateStatusMatcher1(false);
                    this.mgcUI.changeGateStatusMatcher2(false);
                    mcu.setASwitch(false);
                    this.sIOWriting.saveMessage(mcu);
                    this.sIOWriting.saveMessage(mscg);

                }
             
           }

          else {
            System.out.println("experiment in progress , was expecting username or cursor");
            System.out.println("don't know: " + mt.getClass().toString());
            }   

        //messages.addElement(m);
        ////expIOW.saveMessage(m);

       }
        else if(origin == pMatcher1 ) {
       if (mt instanceof MessageCursorUpdate){
           System.out.println("Server detects client2 sending cursor update");
           MessageCursorUpdate mcu = (MessageCursorUpdate)mt;
           pMatcher1Maze.getMaze().moveTo(mcu.newPos);
           this.mgcUI.movePositionMatcher1(mcu.newPos);
           if(pDirectorMaze.getMaze().isASwitch(pMatcher1Maze.getMaze().getMinBounds(),mcu.newPos)){
                 mcu.setASwitch(true);
             }
             else{
                 mcu.setASwitch(false);
           }
           //MazeSquare current3=((Maze)client3Maze).getCurrent();
           if(pDirectorMaze.getMaze().isASwitch(pMatcher1Maze.getMaze().getMinBounds(),mcu.newPos)
                   &&pDirectorMaze.getMaze().isASwitch(pMatcher2Maze.getMaze().getMinBounds(), pMatcher2Maze.getCurrent())
                   ){
              //
               
              System.out.println("Server detects client2 and client3 as being on client1's switch");
              MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",true,pDirector.getUsername());
              c.sendTaskMoveToParticipant(pDirector,mscg);
              this.mgcUI.changeGateStatusDirector(true);    
              //mcu.setASwitch(true);
              this.sIOWriting.saveMessage(mcu);
              this.sIOWriting.saveMessage(mscg);
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
       }

      else {
          System.out.println("experiment in progress2 , was expecting username or cursor update");
          System.out.println("don't know: " + mt.getClass().toString());
      }
      
      //expIOW.saveMessage(m);

   }
        else if(origin==pMatcher2) {
        //Toolkit.getDefaultToolkit().beep();  
       if (mt instanceof MessageCursorUpdate){
           System.out.println("Server detects client3 sending cursor update");
           MessageCursorUpdate mcu = (MessageCursorUpdate)mt;
           pMatcher2Maze.getMaze().moveTo(mcu.newPos);
           this.mgcUI.movePositionMatcher2(mcu.newPos);
           //MazeSquare current2=((Maze)client2Maze).getCurrent();
           if(pDirectorMaze.getMaze().isASwitch(pMatcher2Maze.getMaze().getMinBounds(),mcu.newPos)){
                 mcu.setASwitch(true);
             }
             else{
                mcu.setASwitch(false);
           }
           if(pMatcher1Maze==pMatcher2Maze){
               System.exit(-421342415);
           }
           if(pDirectorMaze.getMaze().isASwitch(pMatcher2Maze.getMaze().getMinBounds(),mcu.newPos)
                   && pDirectorMaze.getMaze().isASwitch(pMatcher1Maze.getMaze().getMinBounds(), pMatcher1Maze.getCurrent())
                   ){
              System.out.println("Server detects client3 and client2 as being on client1sswitch");
              MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",true,pDirector.getUsername());
              c.sendTaskMoveToParticipant(pDirector,mscg);
              this.mgcUI.changeGateStatusDirector(true);
              //mcu.setASwitch(true);
              this.sIOWriting.saveMessage(mcu);
              this.sIOWriting.saveMessage(mscg);
          }
          else {
            System.out.println("Server detects client2 or client3 as NOT being on switch");
            MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",false,pDirector.getUsername());
            c.sendTaskMoveToParticipant(pDirector,mscg);
            this.mgcUI.changeGateStatusDirector(false);
            //mcu.setASwitch(false);
            this.sIOWriting.saveMessage(mcu);
            this.sIOWriting.saveMessage(mscg);
          }
       }

      else {
          System.out.println("experiment in progress2 , was expecting username or cursor update");
          System.out.println("don't know: " + mt.getClass().toString());
      }
      
     // expIOW.saveMessage(m);

   }
   if(pDirectorMaze.getMaze().isCompleted()&&pMatcher1Maze.getMaze().isCompleted()&&pMatcher2Maze.getMaze().isCompleted()){
      System.out.println("Maze number"+ mazeNumber+ " completed");
      moveToNextMaze();
      
        

   }
     }catch (Exception e){
         System.out.println("STACKTRACE");
         System.err.println("ERROR IN MAZEGAME TASKMOVE HANDLER");
         e.printStackTrace();
     }      

    }
  
 synchronized public boolean moveToMazeNo(int mazeNo){
   if (mazeNumber>=pDirectorMazes.size()-1 ||mazeNumber>=pMatcher1Mazes.size()-1
              ||mazeNumber>=pMatcher2Mazes.size()-1) 
      {
       System.out.println("MoveToMazeNo returning false "+mazeNumber);
       return false;
   }

   pDirectorMaze = new MazeWrap((Maze) pDirectorMazes.elementAt(mazeNo));
   pMatcher1Maze = new MazeWrap((Maze) pMatcher1Mazes.elementAt(mazeNo));
   pMatcher2Maze = new MazeWrap((Maze) pMatcher2Mazes.elementAt(mazeNo));
   mazeNumber=mazeNo;
   pDirectorPosition = new Dimension(0,0);
   pMatcher1Position = new Dimension(0,0);
   pMatcher2Position = new Dimension(0,0);
   System.out.println("move to next maze EXPERIMENT IS TRUE");
   MessageNextMaze_SentAsIndex mnm= new MessageNextMaze_SentAsIndex("server","server",mazeNo, "Moving to maze "+mazeNumber);
   c.sendTaskMoveToParticipant(pDirector,mnm);
   c.sendTaskMoveToParticipant(pMatcher1,mnm);
   c.sendTaskMoveToParticipant(pMatcher2,mnm);
   this.mgcUI.moveToMazeNo(mazeNo);
   this.sIOWriting.saveMessage(mnm);
   return true;
} 
    
    
  public boolean moveToNextMaze(){
   if (mazeNumber>=pDirectorMazes.size()-1 ||mazeNumber>=pMatcher1Mazes.size()-1
              ||mazeNumber>=pMatcher2Mazes.size()-1) 
      {
       System.out.println("MoveToNextMaze returning false "+mazeNumber);
       return false;
   }

   pDirectorMaze = new MazeWrap((Maze) pDirectorMazes.elementAt(pDirectorMazes.indexOf(pDirectorMaze.getMaze())+1));
   pMatcher1Maze = new MazeWrap((Maze) pMatcher1Mazes.elementAt(pMatcher1Mazes.indexOf(pMatcher1Maze.getMaze())+1));
   pMatcher2Maze = new MazeWrap((Maze) pMatcher2Mazes.elementAt(pMatcher2Mazes.indexOf(pMatcher2Maze.getMaze())+1));
   mazeNumber++;
   pDirectorPosition = new Dimension(0,0);
   pMatcher1Position = new Dimension(0,0);
   pMatcher2Position = new Dimension(0,0);
    System.out.println("move to next maze EXPERIMENT IS TRUE");
    MessageNextMaze_SentAsIndex mnm= new MessageNextMaze_SentAsIndex("server","server",mazeNumber, "Moving to maze "+mazeNumber);
   c.sendTaskMoveToParticipant(pDirector,mnm);
   c.sendTaskMoveToParticipant(pMatcher1,mnm);
   c.sendTaskMoveToParticipant(pMatcher2,mnm);
   this.mgcUI.moveToMazeNo(mazeNumber);
   this.sIOWriting.saveMessage(mnm);
   return true;
}

   public String getDirectorOrMatcher(Participant p){
       if(p==pDirector)return "D";
       if(p==pMatcher1)return "M1";
       if(p==pMatcher2)return "M2";
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
}
