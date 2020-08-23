/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;


import diet.server.ConversationController.ui.CustomDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.Random;
import java.util.Vector;
import javax.swing.JFileChooser;

public class SetupIO {
    
   public String mainmazegamelibrary;
   public String experimentmazegamedirectory; 
   String parentDirectory;
   File vectorOfMazeMoves;
   File vectorOfMazeMovesTxt;

   public SetupIO(String parentdirect) {
      parentDirectory = parentdirect;
      mainmazegamelibrary = System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"mazegame";
  }
  
   public static void main(String[] args){
       SetupIO sIO = new SetupIO(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"mazegame");
       sIO.doingSetup=true;
       JSetupFrame jsf = new JSetupFrame(sIO);
       
       
       
       //System.out.println(f.getParentFile().list());
       
   }
   
  public SetupIO(){
      //used for convenience
  }
   
   
  public SetupIO(String mainmazegamelibrary,String experimentmazegamedirectory){
      this.mainmazegamelibrary=mainmazegamelibrary;
      this.experimentmazegamedirectory=experimentmazegamedirectory;
  }
  
  
  
  public Vector[] loadExternalPairOfMazeVectors(){
      String directory = System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"mazegame";
      
      
      //directory = System.getProperty("user.dir");
      final JFileChooser fc = new JFileChooser(directory);
      fc.setCurrentDirectory(new File(directory));
      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnVal = fc.showOpenDialog(null);
      File mazePairDir = fc.getSelectedFile();
         
         try{
           directory = mazePairDir.getCanonicalPath();
         }catch(Exception ee){
             ee.printStackTrace();
         }
    
      Vector[] pairOfMazeVectors = new Vector[2];
      Vector cl1Mzes = getClient1MazesFromNameASTEXT(directory,false);
      Vector cl2Mzes = getClient2MazesFromNameASTEXT(directory,false);
     // System.err.println("THESIZEIS:"+cl1Mzes.size());
     //  System.err.println("THESIZEIS2:"+cl2Mzes.size());
     // System.exit(-5);
       
      pairOfMazeVectors[0]=cl1Mzes;
      pairOfMazeVectors[1]=cl2Mzes;
      return pairOfMazeVectors;
  }
  
  
  
  
  public Vector getRandomPairOfMazeVectors(){
      System.err.println("HERE0");
      Vector pairOfMazeVectors = new Vector();
      System.err.println("HERE1");
      Vector v = getAllMazeConfigsFromLibrary();
      System.err.println("HERE2 "+v.size());
      Random r = new Random();
      System.err.println("HERE3");
      int i = r.nextInt(v.size());
      System.err.println("HERE4");
      String s = (String)v.elementAt(i);
      System.err.println("HERE5");
      Vector cl1Mazes = this.getClient1MazesFromNameASTEXT(s, true);
      System.err.println("HERE6");
      Vector cl2Mazes = this.getClient2MazesFromNameASTEXT(s,true);
      System.err.println("HERE7");
      pairOfMazeVectors.addElement(cl1Mazes);
      System.err.println("HERE8");
      pairOfMazeVectors.addElement(cl2Mazes);
      System.err.println("HERE9");
      return pairOfMazeVectors;
  }
  
  
  public Vector getAllMazeConfigsFromLibrary(){
       Vector v = new Vector();
      try {
        System.err.println("step1")  ;
        File setupdir = new File(mainmazegamelibrary);
        System.err.println("step2")  ;
        System.out.println("Looking for mazes in "+setupdir.getAbsolutePath());
         File[] setupDirListing = setupdir.listFiles();
         for(int i=0;i<setupDirListing.length;i++){
               if( setupDirListing[i].isDirectory() && !setupDirListing[i].isHidden()){
                   v.addElement(setupDirListing[i].getCanonicalPath());
                   System.err.println("LISTING CONTENTS "+setupDirListing[i].getCanonicalPath());
               }
               
         }
      }catch(Exception e){
          System.out.println("Could not read directory to get list of configs "+e.getMessage());
      }    
      return v;
  }

  boolean doingSetup = false;
  

  
  public String getClient1MazesStep1_LoadFileASTEXT(String directoryname, boolean useExperimentResourcesMazeGame){
    try {
        BufferedReader br=null;
         if(useExperimentResourcesMazeGame){
          File f = new File(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"cl1mzes.txt");
          if(f.exists()){
              br = new BufferedReader(new FileReader(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"cl1mzes.txt"));    
          }
          else{
              br = new BufferedReader(new FileReader(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"client1m.txt"));
          }
        }
        else{
          File f = new File(directoryname+File.separator+"cl1mzes.txt");
          if(f.exists()){
              br = new BufferedReader( new FileReader( directoryname+File.separator+"cl1mzes.txt"));     
          }
          else{
               br = new BufferedReader( new FileReader( directoryname+File.separator+"client1m.txt"));     
          }
          
        }
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        String everything = sb.toString();
        return everything;
    }
    catch (Exception e){
        e.printStackTrace();
    }
    if(2<5)System.exit(-5);
   
    return null;
}
  
  
  //for /R %% IN (client2m.txt) DO ren "%x" cl2Mazes.txt
  //for /R %% IN (client1m.txt) DO ren "%x" cl1Mazes.txt

  public String getClient2MazesStep1_LoadFileASTEXT(String directoryname, boolean useExperimentResourcesMazeGame){
    try {
        BufferedReader br=null;
        if(useExperimentResourcesMazeGame){
          File f = new File(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"cl2mzes.txt");
          if(f.exists()){
              br = new BufferedReader(new FileReader(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"cl2mzes.txt"));    
          }
          else{
              br = new BufferedReader(new FileReader(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"client2m.txt"));
          }
        }
        else{
          File f = new File(directoryname+File.separator+"cl2mzes.txt");
          if(f.exists()){
              br = new BufferedReader( new FileReader( directoryname+File.separator+"cl2mzes.txt"));     
          }
          else{
               br = new BufferedReader( new FileReader( directoryname+File.separator+"client2m.txt"));     
          }
          
        }
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        String everything = sb.toString();
        return everything;
    }
    catch (Exception e){
        e.printStackTrace();
    }
    if(2<5)System.exit(-5);
   
    return null;
}
  
  
public Vector getClientMazesFromVectorOfStringsForEachMaze(String s){
     Vector mazesOfStrings = new Vector();
    
     BufferedReader reader = new BufferedReader(new StringReader(s));
     try{
        String line = reader.readLine();
        Vector currentMaze = new Vector();
        mazesOfStrings.addElement(currentMaze);
        
        while(line!=null){
           
           if((line.startsWith("MAZE No")||(line.startsWith("Maze No")))& mazesOfStrings.size()==1 & currentMaze.size()==0  ){
               //Do nothing
           }
           else if(  line.startsWith("MAZE No") ||line.startsWith("Maze No")){
                
               currentMaze = new Vector();
               mazesOfStrings.addElement(currentMaze);
           }     
           else{
               currentMaze.addElement(line);
               
           }
           
           line = reader.readLine();
        }
        return mazesOfStrings;
     }
     catch(Exception e){
         e.printStackTrace();
     }
     return null;
     
     
}  

public Vector getClient1MazesStep3_ConstructMazes(Vector v){
     Vector allMazes = new Vector();
     for(int i=0;i<v.size();i++){
         Vector vMazeText = (Vector)v.elementAt(i);
         Maze m = this.constructMazeFromText(vMazeText);
         allMazes.addElement(m);
     }
     return allMazes;
}

public Maze constructMazeFromText(Vector v){
    Vector hasLinkSOUTH = new Vector();
    Vector hasLinkNORTH = new Vector();
    Vector hasLinkEAST = new Vector();
    Vector hasLinkWEST = new Vector();
    Vector hasLinkSOUTHGate = new Vector();
    Vector hasLinkNORTHGate = new Vector();
    Vector hasLinkEASTGate = new Vector();
    Vector hasLinkWESTGate = new Vector();
    
    MazeSquare start = null;
    MazeSquare finish = null;
    
     try{
        Vector mazesquares = new Vector();
        for(int i=0;i<v.size();i++){
             String s = (String)v.elementAt(i);
             s = s.replace("[ ", "");
             s = s.replace("] ", "");
             s = s.replace("Link", "");
             s = s.replace(" is a gate.", "Gate");
             s = s.replace("NORTH", "North");
             s = s.replace("north", "North");
             s = s.replace("SOUTH", "South");
             s = s.replace("south", "South");
             s = s.replace("EAST", "East");
             s = s.replace("east", "East");
             s = s.replace("west", "West");
             s = s.replace("WEST", "West");
             
             
             
             System.out.println("---"+s);
             boolean isASwitch = false; if(s.contains("SWITCH"))isASwitch=true;
             String[] row = s.split("\\s+");             
             Integer mazesquareX = Integer.parseInt(row[0]);
             Integer mazesquareY = Integer.parseInt(row[1]);
             MazeSquare ms = new MazeSquare(isASwitch,mazesquareX,mazesquareY);
             if(s.contains("NorthGate")){
                 hasLinkNORTHGate.addElement(ms);
             }
             else if(s.contains("North") || s.contains("NORTH") || s.contains("north")){
                  hasLinkNORTH.addElement(ms);
             }
             if(s.contains("EastGate")){
                 hasLinkEASTGate.addElement(ms);
             }
             else if(s.contains("East") ||s.contains("EAST") ||s.contains("east")){
                  hasLinkEAST.addElement(ms);
             }
             if(s.contains("SouthGate")){
                hasLinkSOUTHGate.addElement(ms);
             }
             else if(s.contains("South")|| s.contains("SOUTH")|| s.contains("south")){
                 hasLinkSOUTH.addElement(ms);
             }
              if(s.contains("WestGate")){
                 hasLinkWESTGate.addElement(ms);
             }
             else if(s.contains("West") || s.contains("WEST") || s.contains("west")    ){
                 hasLinkWEST.addElement(ms);
                 System.err.println("ADDING TO WEST ("+ms.x+","+ms.y+")........"+s);
             }
             mazesquares.addElement(ms);
              
             if(s.contains("BEGIN") | s.contains("Begin") | s.contains("begin")){
                 if(start!=null){
                     System.err.println("ERROR - FOUND MULTIPLE STARTS");
                     CustomDialog.showDialog("ERROR - FOUND MULTIPLE STARTS");
                     System.exit(-5);
                 }
                 else{
                     start = ms;
                 }
             }
             if(s.contains("FINISH")){
                if(finish!=null){
                     System.err.println("ERROR - FOUND MULTIPLE GOAL SQUARES");
                     CustomDialog.showDialog("ERROR - FOUND MULTIPLE GOAL SQUARES");
                     System.exit(-5);
                 }
                 else{
                     finish = ms;
                 }
             } 
             
             
        }
        
        this.connectLinkToSquareEASTWEST(hasLinkEAST, hasLinkWEST,false);
        this.connectLinkToSquareEASTWEST(hasLinkEASTGate, hasLinkWESTGate,true);
        this.connectLinkToSquareNORTHSOUTH(hasLinkNORTH, hasLinkSOUTH,false);
        this.connectLinkToSquareNORTHSOUTH(hasLinkNORTHGate, hasLinkSOUTHGate,true);
        
        if(start==null){
             System.err.println("ERROR - COULD NOT FIND START");
             CustomDialog.showDialog("ERROR - COULD NOT FIND START");
             System.exit(-5);
        }
        if(finish==null){
             System.err.println("ERROR - COULD NOT FIND FINISH");
             CustomDialog.showDialog("ERROR - COULD NOT FIND FINISH");
             System.exit(-5);
        }
        
        Maze mz = new Maze(0);
        mz.begin=start;
        mz.finish=finish;
        mz.squares=mazesquares;
        mz.current=mz.begin;
        return mz;
        
        
     }
     catch(Exception e){
         e.printStackTrace();
     }
     return null;
}

  public void connectLinkToSquareEASTWEST(Vector e, Vector w, boolean isGate){
     
      
     System.err.println("SIZEIS "+e.size()+" "+w.size());
     if(e.size()!=w.size()){
         System.err.println("VECTORS NOT THE SAME SIZE!");
         System.err.println("ERROR: UNEQUAL CONNECTIONS...THERE ARE: "+e.size() + " squares with a connection on the EAST edge vs. "+w.size()+" objects with a connection on the SOUTH edge");
         CustomDialog.showDialog("ERROR: UNEQUAL CONNECTIONS...THERE ARE: "+e.size() + " squares with a connection on the EAST edge vs. "+w.size()+" objects with a connection on the SOUTH edge");
         
         System.exit(-5);
     }
     if(e.size()==0)return;
      for(int i=0;i<e.size();i++){
         MazeSquare msE = (MazeSquare)e.elementAt(i);
         System.err.println("IDENTIFIED msE: ("+msE.x+","+msE.y+")");
         int needleX = msE.x+1;
         System.err.println("TRYING TO FIND: ("+(msE.x+1)+","+msE.y+")");
         int needleY = msE.y;
         boolean found = false;
         for(int j=0;j<w.size();j++){
              MazeSquare msW = (MazeSquare)w.elementAt(j);
              System.err.println("     SCANNING THROUGH - MSW("+msW.x+","+msW.y+")");
              if(msW.x==needleX && msW.y==needleY){
                 
                  found = true;
                  w.remove(msW);
                  MazeLink ml = new MazeLink(msE,msW,isGate);
                  msE.east=ml;
                  msW.west=ml;
                  System.err.println("     FOUND CONNECTION!- MSW("+msW.x+","+msW.y+")");;
                  break;                  
              }
         }

         if(!found){
             String errormessage = "The algorithm is processing data (it could be either Client1 or Client 2 data).\n"
                     + "The algorithm is trying to connect the square ("+msE.x+","+msE.y+") to a square that is to its EAST.\n"
                     + "This square should be ("+(needleX)+","+needleY+") and also have a WEST connection ...but can't find it in the data file.\n"
                     + "This error could also be caused by duplicates";
             System.err.println(errormessage);
             CustomDialog.showDialog(errormessage);
             
             System.exit(-5);
         } 
     }  
     if(w.size()!=0){
          System.err.println("DID NOT FIND EVERYTHING - EXITING!");
          System.exit(-5);
     }
     
     
  }
  
  
   public void connectLinkToSquareNORTHSOUTH(Vector n, Vector s, boolean isGate){
     System.err.println("SIZE IS "+n.size()+" "+s.size());
     if(n.size()!=s.size()){
         System.err.println("VECTORS NOT THE SAME SIZE...THERE ARE: "+n.size() + "squares with a connection on the NORTH edge vs. "+s.size()+" objects with a connection on the SOUTH edge");
         System.err.println("UNEQUAL CONNECTIONS...THERE ARE: "+n.size() + " squares with a connection on the NORTH edge vs. "+s.size()+" objects with a connection on the SOUTH edge");
         CustomDialog.showDialog("UNEQUAL CONNECTIONS...THERE ARE: "+n.size() + " squares with a connection on the NORTH edge vs. "+s.size()+" objects with a connection on the SOUTH edge");
         System.exit(-5);
     }
      if(n.size()==0)return;
      for(int i=0;i<n.size();i++){
         MazeSquare msN = (MazeSquare)n.elementAt(i);
         int needleX = msN.x;
         int needleY = msN.y-1;
         boolean found = false;
         for(int j=0;j<s.size();j++){
              MazeSquare msS = (MazeSquare)s.elementAt(j);
              if(msS.x==needleX && msS.y==needleY){
                  found = true;
                  s.remove(msS);
                  MazeLink ml = new MazeLink(msN,msS,isGate);
                  msN.north=ml;
                  msS.south=ml;
                  System.err.println("FOUND CONNECTIONNORTHSOUTH!");
                  break;                  
              }
         }

         if(!found){
             String errormessage = "The algorithm is processing data (it could be either Client1 or Client 2 data).\n"
                     + "The algorithm is trying to connect the square ("+msN.x+","+msN.y+") to a square that is to its SOUTH.\n"
                     + "This square should be: ("+(needleX)+","+needleY+") and have a NORTH connection...but it can't be found in the data"+"\n"
                     + "This error could also be caused by duplicates";
             System.err.println(errormessage);
             CustomDialog.showDialog(errormessage);
             System.exit(-5);
         } 
     }  
     if(s.size()!=0){
          System.err.println("DID NOT FIND EVERYTHING - EXITING!");
          System.exit(-5);
     }
     
     
  }
  
  
public  Vector getClient1MazesFromNameASTEXT(String directoryname, boolean useExperimentResourcesMazeGame){
    String textFile = this.getClient1MazesStep1_LoadFileASTEXT(directoryname, useExperimentResourcesMazeGame);
    textFile = textFile.replaceAll("BEGINLink", "BEGIN Link");
    textFile = textFile.replaceAll("FINISHLink", "FINISH Link");
    textFile = textFile.replaceAll("\nLink", "Link");
    
    System.err.println("THE FILE IS: "+textFile);
    //System.out.println(textFile);
    Vector mazesOfStrings = getClientMazesFromVectorOfStringsForEachMaze(textFile);
    System.err.println("THE SIZE IS: "+mazesOfStrings.size());
    Vector vOfMazes = getClient1MazesStep3_ConstructMazes(mazesOfStrings);
    return vOfMazes;
}
  
  
public Vector getClient1MazesFromSetupNameOLL(String directoryname){
  Vector v = new Vector();
  String fileToLoad = "unset";
try{
   FileInputStream fi;
   fileToLoad =directoryname+File.separator+"cl1mzes.v";
   if(doingSetup){
       fi = new FileInputStream(new File(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"cl1mzes.v"));
   }
   else{
       fi = new FileInputStream(new File(directoryname+File.separator+"cl1mzes.v"));
   }
   
   
   
   System.out.println("client1a");
   ObjectInputStream oInp = new ObjectInputStream(fi);
   System.out.println("client1b "+directoryname+File.separator+"cl1mzes.v");
   Object o = oInp.readObject();//v = (Vector)oInp.readObject();
   v = (Vector)o;
   System.out.println("client1c");
   System.out.println("IS OF CLASS TYPE: "+o.getClass().toString());
   System.out.println("client1d");
   oInp.close();
   fi.close();

}catch(Exception e){
 System.out.println("can't load in setup mazes1 "+e.getMessage()+"the name is: "+fileToLoad);
 e.printStackTrace();
 
}
return v;
}

public Vector getClient2MazesFromNameASTEXT(String directoryname, boolean useExperimentResourcesMazeGame){
    String textFile = this.getClient2MazesStep1_LoadFileASTEXT(directoryname, useExperimentResourcesMazeGame);
    textFile = textFile.replaceAll("BEGINLink", "BEGIN Link");
    textFile = textFile.replaceAll("FINISHLink", "FINISH Link");
    textFile = textFile.replaceAll("\nLink", "Link");
    //System.out.println(textFile);
    Vector mazesOfStrings = getClientMazesFromVectorOfStringsForEachMaze(textFile);
    Vector vOfMazes = getClient1MazesStep3_ConstructMazes(mazesOfStrings);
    return vOfMazes;
}










 public Vector getClient2MazesFromSetupOLL(String directoryname, boolean  useExperimentResourcesMazeGame){
  Vector v = new Vector();
   try{ String fileToLoad =directoryname+File.separator+"cl2mzes.v";
   //FileInputStream fi = new FileInputStream(new File(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"cl2mzes.v"));
   
   FileInputStream fi;// = new FileInputStream(new File(directoryname+File.separator+"cl2mzes.v"));
   if(useExperimentResourcesMazeGame){
       fi = new FileInputStream(new File(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"cl2mzes.v"));
   }
   else{
       fi = new FileInputStream(new File(directoryname+File.separator+"cl2mzes.v"));
   } 
   
   
  ObjectInputStream oInp = new ObjectInputStream(fi);
   v = (Vector)oInp.readObject();
   oInp.close();
   fi.close();

}catch(Exception e){
System.out.println("can't load in setup mazes2 "+e.getMessage());
}
return v;

 }
public void saveClientMazesOfTwoClientsByName(String cl1Name, String cl2Name, Vector cl1Mazes, Vector cl2Mazes ){
 try{   
   File directory = new File(this.experimentmazegamedirectory);
   if(!directory.exists())directory.mkdirs();
   File cl1MazesFile = new File(directory+File.separator+ cl1Name+ "_cl1m.v" ); 
   File cl2MazesFile = new File(directory+File.separator+ cl2Name+"_cl2m.v" );
   FileOutputStream fv1Cl1Out = new FileOutputStream(cl1MazesFile);
   FileOutputStream fv2Cl2Out = new FileOutputStream(cl2MazesFile);
   ObjectOutputStream cl1out = new ObjectOutputStream(fv1Cl1Out);
   ObjectOutputStream cl2out = new ObjectOutputStream(fv2Cl2Out);
   cl1out.writeObject(cl1Mazes);
   cl2out.writeObject(cl2Mazes); 
   
   File mazes1TextFile = new File(directory+File.separator+ cl1Name+"_cl1m.txt");
   File mazes2TextFile = new File(directory+File.separator+cl2Name+"_cl2m.txt");
   FileWriter mazes1 = new FileWriter (mazes1TextFile);
   FileWriter mazes2 = new FileWriter (mazes2TextFile);
   
   
    for(int i=0;i<cl1Mazes.size();i++){
       Maze m1 = (Maze)cl1Mazes.elementAt(i);
       Maze m2 = (Maze)cl2Mazes.elementAt(i);
       mazes1.write("MAZE No: " +  i+"\n"+m1.toText());
       mazes2.write("MAZE No: " +  i+"\n"+m2.toText());     
   }
   cl1out.flush();
   cl2out.flush();
   mazes1.flush();
   mazes2.flush();
   cl1out.close();
   cl2out.close();   
   mazes1.close();
   mazes2.close();
   
 } catch (Exception e){
    System.out.println("Error saving mazes of both clients in setup " +e.getMessage());
    e.printStackTrace();
 }
   
} 
 






public Vector getAllMazesFromExperimentDirectory2WAY(String s){
    Vector vAllMazes = new Vector();
    try{
       FileInputStream fiDirector = new FileInputStream(s+File.separator+"cl1Mazes.v");
       FileInputStream fiMatcher1 = new FileInputStream(s+File.separator+"cl2Mazes.v");
       
       
       ObjectInputStream oInpDirector = new ObjectInputStream(fiDirector);
       ObjectInputStream oInpMatcher1 = new ObjectInputStream(fiMatcher1);
       
       
       Vector vDirectorMazes = (Vector)oInpDirector.readObject();
       Vector vMatcher1Mazes = (Vector)oInpMatcher1.readObject();
       
       
       oInpDirector.close();
       oInpMatcher1.close();
       
       
       vAllMazes.addElement(vDirectorMazes);
       vAllMazes.addElement(vMatcher1Mazes);
       
        
       
    }catch(Exception e){
       System.err.println("COULD NOT LOAD EXPERIMENT MAZES: ");
       System.out.println("STACKTRACE:");
       e.printStackTrace();
    }
    
    return vAllMazes;
}
 
 
 
 
 
 public void saveClientMazesFromSetupNo(Vector v1,Vector v2, String description){
   try{
     File directory = new File(description+File.separator);
     directory.mkdirs();
     File client1MazesFile = new File(description+File.separator+"cl1mzes"+".v");
     File client2MazesFile = new File(description+File.separator+"cl2mzes"+".v");
     FileOutputStream fv1 = new FileOutputStream(client1MazesFile);
     FileOutputStream fv2 = new FileOutputStream(client2MazesFile);
     ObjectOutputStream v1out = new ObjectOutputStream(fv1);
     ObjectOutputStream v2out = new ObjectOutputStream(fv2);
  v1out.writeObject(v1);
  v2out.writeObject(v2);

  File mazes1TextFile = new File(description+File.separator+"cl1Mazes"+".txt");
  File mazes2TextFile = new File(description+File.separator+"cl2Mazes"+".txt");
  FileWriter mazes1 = new FileWriter (mazes1TextFile);
  FileWriter mazes2 = new FileWriter (mazes2TextFile);

  for(int i=0;i<v1.size();i++){
      Maze m1 = (Maze)v1.elementAt(i);
      Maze m2 = (Maze)v2.elementAt(i);
      mazes1.write("MAZE No: " +  i+"\n"+m1.toText());
      mazes2.write("MAZE No: " +  i+"\n"+m2.toText());
      ///mazes1.write("MAZE No: " +  i+"\n"+m1.toText());
      ///mazes2.write("MAZE No: " +  i+"\n"+m2.toText());
  }
  v1out.flush();
  v2out.flush();
  mazes1.flush();
  mazes2.flush();
  v1out.close();
  v2out.close();
  mazes1.close();
  mazes2.close();


 } catch (Exception e){
    System.out.println("Error saving mazes of both clients in setup..." +e.getMessage());
    CustomDialog.showDialog("Error saving mazes of both clients in setup... "+e.getMessage());
    e.printStackTrace();
 }



}

 
 
 
 public Vector getListOfMazeConfigs(){
  Vector v = new Vector();
  try {
    //File setupdir = new File(parentDirectory+"/setup/");
    File setupdir = new File(this.mainmazegamelibrary);
    File[] setupDirListing = setupdir.listFiles();
    for(int i=0;i<setupDirListing.length;i++){
       if( setupDirListing[i].isDirectory()) v.addElement(setupDirListing[i].getName());
    }
  }catch (Exception e){ System.out.println("Could not read directory to get list of configs "+e.getMessage());

  }
  return v;
}

 
 
 
public void saveClientMazesFromSetupNo_DEPRECATED_NEEDSTOBEREWRITTEN(Vector vDirector,Vector vMatcher1, Vector vMatcher2,String description){
  try{
   File directory = new File(this.experimentmazegamedirectory);
   if(!directory.exists())directory.mkdirs();
   File directorMazesFile = new File(directory+File.separator+"directorMazes.v"); 
   File matcher1MazesFile = new File(directory+File.separator+"matcher1Mazes.v"); 
   File matcher2MazesFile = new File(directory+File.separator+"matcher2Mazes.v"); 
   FileOutputStream fv1DirectorOut = new FileOutputStream(directorMazesFile);
   FileOutputStream fv2Matcher1Out = new FileOutputStream(matcher1MazesFile);
   FileOutputStream fv3Matcher2Out = new FileOutputStream(matcher2MazesFile);
   ObjectOutputStream fdout = new ObjectOutputStream(fv1DirectorOut);
   ObjectOutputStream fm1out = new ObjectOutputStream(fv2Matcher1Out);
   ObjectOutputStream fm2out = new ObjectOutputStream(fv3Matcher2Out);
   fdout.writeObject(vDirector);
   fm1out.writeObject(vMatcher1); 
   fm2out.writeObject(vMatcher2);
   
   File mazes1TextFile = new File(directory+File.separator+"directorMazes.txt");
   File mazes2TextFile = new File(directory+File.separator+"matcher1Mazes.txt");
   File mazes3TextFile = new File(directory+File.separator+"matcher2Mazes.txt");
   FileWriter mazes1 = new FileWriter (mazes1TextFile);
   FileWriter mazes2 = new FileWriter (mazes2TextFile);
   FileWriter mazes3 = new FileWriter (mazes2TextFile);
       
   for(int i=0;i<vDirector.size();i++){
       Maze m1 = (Maze)vDirector.elementAt(i);
       Maze m2 = (Maze)vMatcher1.elementAt(i);
       Maze m3 = (Maze)vMatcher2.elementAt(i);
       mazes1.write("MAZE No: " +  i+"\n"+m1.toText());
       mazes2.write("MAZE No: " +  i+"\n"+m2.toText());
       mazes3.write("MAZE No: " +  i+"\n"+m3.toText());
   }
       
   fdout.flush();
   fm1out.flush();
   fm2out.flush();

   mazes1.flush();
   mazes2.flush();
   mazes3.flush();
   
   fdout.close();
   fm1out.close();
   fm2out.close();
   
   mazes1.close();
   mazes2.close();
   mazes3.close();

 } catch (Exception e){
    System.out.println("Error saving mazes of both clients in setup " +e.getMessage());
 }



}


public Vector getAllMazesFromExperimentDirectory3WAY(String s){
    Vector vAllMazes = new Vector();
    try{
       FileInputStream fiDirector = new FileInputStream(s+File.separator+"directorMazes.v");
       FileInputStream fiMatcher1 = new FileInputStream(s+File.separator+"matcher1Mazes.v");
       FileInputStream fiMatcher2 = new FileInputStream(s+File.separator+"matcher2Mazes.v");
       
       ObjectInputStream oInpDirector = new ObjectInputStream(fiDirector);
       ObjectInputStream oInpMatcher1 = new ObjectInputStream(fiMatcher1);
       ObjectInputStream oInpMatcher2 = new ObjectInputStream(fiMatcher2);
       
       Vector vDirectorMazes = (Vector)oInpDirector.readObject();
       Vector vMatcher1Mazes = (Vector)oInpMatcher1.readObject();
       Vector vMatcher2Mazes = (Vector)oInpMatcher2.readObject();
       
       oInpDirector.close();
       oInpMatcher1.close();
       oInpMatcher2.close();
       
       vAllMazes.addElement(vDirectorMazes);
       vAllMazes.addElement(vMatcher1Mazes);
       vAllMazes.addElement(vMatcher2Mazes);
        
       
    }catch(Exception e){
       System.err.println("COULD NOT LOAD EXPERIMENT MAZES: ");
       System.out.println("STACKTRACE:");
       e.printStackTrace();
    }
    
    return vAllMazes;
}




 



}





