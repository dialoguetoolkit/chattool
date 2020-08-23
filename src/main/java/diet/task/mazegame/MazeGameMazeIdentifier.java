/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.mazegame;

import diet.task.mazegame.message.MessageCursorUpdate;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class MazeGameMazeIdentifier {
    
      Vector directorMazesReference;
      Vector matcherMazesReference;
    
      public MazeGameMazeIdentifier(String referenceMazeDirector, String referenceMazeMatcher){
           directorMazesReference=loadMazes(referenceMazeDirector);
           matcherMazesReference=loadMazes(referenceMazeMatcher);
      }
      
      
      public Vector loadMazes(String s){
           Vector v = new Vector();
          String fileToLoad = "unset";
          try{
               FileInputStream fi;
             
               fi = new FileInputStream(new File(s));
               ObjectInputStream oInp = new ObjectInputStream(fi);
               Object o = oInp.readObject();//v = (Vector)oInp.readObject();
               v = (Vector)o;
               oInp.close();
               fi.close();
         }catch(Exception e){
             System.out.println("can't load in setup mazes "+e.getMessage()+"the name is: "+s);
             e.printStackTrace();
 
         }
         return v;
       
         
      }
      
      public static void main(String[] args){
           //String refMazesDirector = "C:\\sourceforge\\mazegamesetupswithbackup\\set4_16\\cl1mzes.v";
           //String refMazesMatcher = "C:\\sourceforge\\mazegamesetupswithbackup\\set4_16\\cl2mzes.v";
          
           //MazeGameMazeIdentifier mgmi = new MazeGameMazeIdentifier(refMazesDirector,refMazesMatcher);
           
           //String comparison = "C:\\sourceforge\\mazegamesetupswithbackup\\set5_16\\cl2mzes.v";
           //mgmi.compareMazeSet(comparison);
      }
      
      public boolean areMazesTheSame(Maze a, Maze b){
          for(int i=0;i<a.squares.size();i++){
               MazeSquare msA = (MazeSquare)a.squares.elementAt(i);
               MazeSquare msB = (MazeSquare)b.squares.elementAt(i);
               if(msA.x==msB.x && msA.y==msB.y  && msA.isaSwitch==msB.isaSwitch ){
                   
               }
               else{
                   return false;
               }
                       
          }
          return true;
      }
      
      
      public String getMazeID(Maze mCompare){
         
                 for(int j=0;j<this.directorMazesReference.size();j++){
                      Maze mCompare2 = (Maze)directorMazesReference.elementAt(j);
                      boolean areTheySame = this.areMazesTheSame(mCompare, mCompare2);
                      if(areTheySame){
                           System.out.println("A"+j);
                            return ("A"+j);
                      }
                 }
                 for(int j=0;j<this.matcherMazesReference.size();j++){
                      Maze mCompare2 = (Maze)matcherMazesReference.elementAt(j);
                      boolean areTheySame = this.areMazesTheSame(mCompare, mCompare2);
                      if(areTheySame){
                           System.out.println("B"+j);
                           return ("B"+j);
                      }
                 }
                 System.exit(-24234234);
                 return null;
      }
      
      
      
      
      public Vector compareMazeSet(String mazeToCompareFilename){
          
          Vector ids = new Vector();
          
          Vector mazes = this.loadMazes(mazeToCompareFilename);
             for(int i=0;i<mazes.size();i++){
                  
                 Maze mCompare = (Maze)mazes.elementAt(i);
                 for(int j=0;j<this.directorMazesReference.size();j++){
                      Maze mCompare2 = (Maze)directorMazesReference.elementAt(j);
                      boolean areTheySame = this.areMazesTheSame(mCompare, mCompare2);
                      if(areTheySame){
                           System.out.println("A"+j);
                            ids.addElement("A"+j);
                      }
                 }
                 for(int j=0;j<this.matcherMazesReference.size();j++){
                      Maze mCompare2 = (Maze)matcherMazesReference.elementAt(j);
                      boolean areTheySame = this.areMazesTheSame(mCompare, mCompare2);
                      if(areTheySame){
                           System.out.println("B"+j);
                           ids.addElement("B"+j);
                      }
                 }
             }
             
             return ids;
      }
              
      
      
      
      
      
      public boolean isParticipantDirector(String participantID, Vector mazegamemessages){
         boolean hasEncounteredERRORWITHDirector = false;
         boolean hasEncounteredERRORWITHMatcher = false;
          
          
         for(int i=0;i<mazegamemessages.size();i++){
             Object o = mazegamemessages.elementAt(i);
             String nameOfClass = o.getClass().getName();
             
             if(nameOfClass.equalsIgnoreCase("diet.task.mazegame.message.MessageCursorUpdate")){            
                  MessageCursorUpdate mcu = (MessageCursorUpdate)o;
                  String senderID = mcu.getEmail();
                  String senderUsernName = mcu.getUsername();
                  
                  if(senderID.equals(participantID)){
                        System.err.println("TRYING TO IDENTIFY MAZES - LOOKING FOR ERROR"+participantID);
                        int mazeNumber = mcu.getMazeNo();
                        Maze directorMaze = (Maze)directorMazesReference.elementAt(mazeNumber);
                        Maze matcherMaze =  (Maze)matcherMazesReference.elementAt(mazeNumber);
                        Dimension newPos = new Dimension(mcu.newPos.width,mcu.newPos.height);    
                        directorMaze.moveTo(newPos);
                        matcherMaze.moveTo(newPos);
                        if(directorMaze.getSquare(newPos)==null){
                             //System.err.println("DIRECTORERROR: "+mazeNumber);
                             hasEncounteredERRORWITHDirector=true;
                        }
                        else if(matcherMaze.getSquare(newPos)==null){
                             //System.err.println("MATCHERERROR: "+mazeNumber);
                             hasEncounteredERRORWITHMatcher=true;
                    }
                        
                  }
                  
                  
                  
                 
                 
                  
                  
                 
             }    
         }
         
         if(hasEncounteredERRORWITHDirector& hasEncounteredERRORWITHMatcher ) System.exit(-55501);
         if(!hasEncounteredERRORWITHDirector&! hasEncounteredERRORWITHMatcher ){
             System.err.println("NO ERROR"+participantID);
             System.exit(-55602);
         }
         return !hasEncounteredERRORWITHDirector;
         
      }    
         
      
      
      
}
