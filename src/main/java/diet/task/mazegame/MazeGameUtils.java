/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class MazeGameUtils {
      
    Vector referenceSetA = new Vector() ;
    Vector referenceSetB = new Vector() ;
    
    public static void main(String[] args) throws Exception{
        MazeGameUtils mgu = new MazeGameUtils();
        mgu.establishReferencesets();
        Vector v = mgu.iterateThroughDirectories();
        for(int i=0;i<v.size();i++){
            String s = (String)v.elementAt(i);
            System.err.println(s);
        }
        
        System.err.println("HERE6");
        
        
    }
    
    public Vector iterateThroughDirectories() throws Exception{
         Vector values = new Vector();
        
        
         File f = new File("C:\\New folder\\UWA\\2014\\Maze Game Logs\\Maze Game Logs");
         File[] subdirectories = f.listFiles();
         System.err.println("HERE1");
         for(int i=0;i<subdirectories.length;i++){
             System.err.println("HERE2");
             if(subdirectories[i].isDirectory()){
                 System.err.println("HERE3");
                 File[] subsubdirectories = subdirectories[i].listFiles();
                 for(int j=0;j<subsubdirectories.length;j++){
                      System.err.println("HERE4");
                      if(subsubdirectories[j].isDirectory()){
                           System.err.println("HERE5");
                           File pA = new File(subsubdirectories[j],"cl1Mazes.txt");
                           File pB = new File(subsubdirectories[j],"cl2Mazes.txt");
                           if(!pA.exists()){
                               //System.exit(-234);
                           }
                          
                           
                           
                           Vector vpa = readMazeFileAsStrings(pA.getCanonicalPath());
                           
                           printOutmazefile(vpa);
                           
                           
                           
                           Vector vpb = readMazeFileAsStrings(pB.getCanonicalPath());
                           
                           for(int k=0;k<vpa.size();k++){
                              
                               
                               
                               String amaze = (String)vpa.elementAt(k);
                               
                               String amazerefID = this.getRefID(amaze);
                               
                               String   sOUTPUT = subdirectories[i].getName() +"|"+subsubdirectories[j].getName() +"|"+   k+"|"+ "AAAAAA" + "|"+ amazerefID+"|"+"A"+ "\n";
                               sOUTPUT = sOUTPUT+ subdirectories[i].getName() +"|"+subsubdirectories[j].getName() +"|"+k+"|"+ "BBBBBB" + "|"+ amazerefID+"|"+"B"+ "\n";
                               sOUTPUT = sOUTPUT+ subdirectories[i].getName() +"|"+subsubdirectories[j].getName() +"|"+k+"|"+ "CCCCCC" + "|"+ amazerefID+"|"+"A"+ "\n";
                               sOUTPUT = sOUTPUT+ subdirectories[i].getName() +"|"+subsubdirectories[j].getName() +"|"+k+"|"+ "DDDDDD" + "|"+ amazerefID+"|"+"B"+ "\n";
                               sOUTPUT = sOUTPUT+ subdirectories[i].getName() +"|"+subsubdirectories[j].getName() +"|"+k+"|"+ "EEEEEE" + "|"+ amazerefID+"|"+"A"+ "\n";
                               sOUTPUT = sOUTPUT+ subdirectories[i].getName() +"|"+subsubdirectories[j].getName() +"|"+k+"|"+ "FFFFFF" + "|"+ amazerefID+"|"+"B"+ "\n";
                               sOUTPUT = sOUTPUT+ subdirectories[i].getName() +"|"+subsubdirectories[j].getName() +"|"+k+"|"+ "GGGGGG" + "|"+ amazerefID+"|"+"A"+ "\n";
                               sOUTPUT = sOUTPUT+ subdirectories[i].getName() +"|"+subsubdirectories[j].getName() +"|"+k+"|"+ "HHHHHH" + "|"+ amazerefID+"|"+"B"+ "\n";
                                
                                
                                
                                values.addElement(sOUTPUT);
                               
                               String bmaze = (String)vpb.elementAt(k);
                               String bmazerefID = this.getRefID(amaze);
                               if(!amazerefID.equalsIgnoreCase(bmazerefID)){
                                   System.exit(-324234);
                               }
                               
                           }
                           
                           
                      }
                }
             }
         }
         return values;
    }
    
    
    
    
    public String getRefID(String maze){
        boolean foundfirst = false;
        String refID ="";
        
        for(int i =0;i<this.referenceSetA.size();i++){
            String s = (String)referenceSetA.elementAt(i);
            if(s.equalsIgnoreCase(maze)){
                if(foundfirst){
                    System.err.println("FOUND DUPLICATEA" + i);
                    System.exit(-5);
                }
                System.err.println("FOUND: "+s);
                foundfirst = true;
                refID = ""+i;
            }
            else{
                System.err.println("NOT DUPLICATE");
            }
        }
        for(int i =0;i<this.referenceSetB.size();i++){
            String s = (String)referenceSetB.elementAt(i);
            if(s.equalsIgnoreCase(maze)){
                if(foundfirst){
                    System.err.println("FOUND DUPLICATEB"+i);
                    System.exit(-7);
                }
                foundfirst = true;
                refID = ""+i;
            }
        }
        if(!foundfirst){
            System.err.println("Couldn't find");
                    System.exit(-6);
        }
        return refID;
    }
    
    
    
    
    public void establishReferencesets(){
        referenceSetA = readMazeFileAsStrings("C:\\New folder\\UWA\\2014\\Maze Game Logs\\Maze Game Logs\\POD 1 DATA\\0002CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS\\cl1Mazes.txt");
        referenceSetB = readMazeFileAsStrings("C:\\New folder\\UWA\\2014\\Maze Game Logs\\Maze Game Logs\\POD 1 DATA\\0002CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS\\cl2Mazes.txt");
    }
    
    
    
    public static void printOutmazefile(Vector v){
        for(int i=0;i<v.size();i++){
            String s = (String)v.elementAt(i);
            System.err.println("-----------------------------------------------");
            System.err.println(s);
        }
    }
    
    
    
    
    public static Vector readMazeFileAsStringsOLD(String filename) {
    try {
        Vector mazesets= new Vector();
        String currentmazeset = "";
        
        BufferedReader br = new BufferedReader(new FileReader(filename)); 
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        System.err.println("LOADING1");
        while (line != null) {
            
            System.err.println("LOADING2 "+line);
            if(line.startsWith("MAZE N")){
               currentmazeset = "";
               mazesets.addElement(currentmazeset);
               //currentmazeset.add(line);
               System.err.println("LOADING3");
            }
            else{
                currentmazeset = currentmazeset+line +"..";
                
            }    
           
            line = br.readLine();
         }
        return mazesets;
    
    }
    catch (Exception e){
        e.printStackTrace();
    }   
    return new Vector();

}
    
    
    
  public static Vector readMazeFileAsStrings(String filename) {
     Vector retval = new Vector();
    
        Vector setv = readMazeFile(filename);
        for(int i=0;i<setv.size();i++){
            String maze = "";
            Vector setvv = (Vector)setv.elementAt(i);
            for(int j=0;j<setvv.size();j++){
                String s = (String)setvv.elementAt(j);
                maze = maze + s;
            }
            retval.addElement(maze);
        }
        return retval;
       

}
    
    
    public static Vector readMazeFile(String filename) {
    try {
        Vector mazesets= new Vector();
        Vector currentmazeset = new Vector();
        
        BufferedReader br = new BufferedReader(new FileReader(filename)); 
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        System.err.println("LOADING1");
        while (line != null) {
            System.err.println("LOADING2 "+line);
            if(line.startsWith("MAZE N")){
               currentmazeset = new Vector();
               mazesets.addElement(currentmazeset);
               //currentmazeset.add(line);
               System.err.println("LOADING3");
            }
            else{
                currentmazeset.add(line);
            }    
           
            line = br.readLine();
         }
        return mazesets;
    
    }
    catch (Exception e){
        e.printStackTrace();
    }   
    return new Vector();

}
    
    
    
    public String readFile(String filename) {
    try {
        BufferedReader br = new BufferedReader(new FileReader(filename)); 
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
           sb.append(line);
           sb.append(System.lineSeparator());
            line = br.readLine();
         }
         String everything = sb.toString();
         return everything;
    
    }
    catch (Exception e){
        e.printStackTrace();
    }   
    return "";
}
}