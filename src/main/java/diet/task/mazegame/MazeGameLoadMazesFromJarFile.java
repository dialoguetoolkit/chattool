/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

import diet.server.ConversationController.ui.CustomDialog;
import diet.utils.VectorToolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.util.Vector;
import javax.swing.JFileChooser;
import static org.reflections.vfs.Vfs.DefaultUrlTypes.directory;

/**
 *
 * @author GM
 */
public class MazeGameLoadMazesFromJarFile {
    
    //static Vector mazePairs = new Vector();
    //static Vector mazePairsRandomized = new Vector();
    
    //public static Vector cl1MazesRANDOMIZED = new Vector();
    //public static Vector cl2MazesRANDOMIZED = new Vector();
    
    
   public Vector setNames = new Vector();

    public MazeGameLoadMazesFromJarFile() {
        //setNames.addElement("mazecomplementary12A");
        //setNames.addElement("mazecomplementary12B");
        setNames.addElement("mazeset_9_mazes");
        setNames.addElement("mazeset_12_easy_mazes");
        //.addElement("mazeset_12_easy_mazes_reverseorder");
        setNames.addElement("mazeset_12_mazes");
        setNames.addElement("mazeset_16_mazes");
        setNames.addElement("mazeset_verysimplified_setof5");
        setNames.addElement("CUSTOM");
        
        
        
        //setNames.addElement("mazeset_verysimplified_setof11");
        
    }
    
    
    
    
    public Vector  getMazeFromJar(String name){
        //InputStream inp = ClassLoader.getSystemClassLoader().getResourceAsStream("featurestoadd.odt");  
        //InputStream inp = ClassLoader.getSystemClassLoader().getResourceAsStream(name); 
        InputStream inp = this.getClass().getResourceAsStream(name);    
        
        
    try {  
          ObjectInputStream ois = new ObjectInputStream (inp);
          Object o  = ois.readObject();
          System.err.println("READINGIN:" +o.getClass().toString());
          
          return (Vector)o;       
    } catch(Exception e){
       e.printStackTrace();
    }
       return null;
    }
    
    public String  getMazesFromJarASTEXT(String name){
        
        //Because the files are saved in a "mazegame" folder (see pom.xml)
        InputStream inp = this.getClass().getResourceAsStream("/mazegame"+ name);    
        
        
       try {  
         
        
        
        BufferedReader br = new BufferedReader(new InputStreamReader(inp, "UTF-8"));
        
        
        
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
        
        
        
        
        
        
        
        
        
        
        
        
        
    
    
    
    //public static Vector cl1Mazes = new Vector();
    //public static Vector cl2Mazes = new Vector();
    
    
     
    
     public void getSetOfFurtherSimplifiedMazesFromJarDEPRECATED(){
        
      
      
       
        //this.randomizeMazes();
    }
    
    
    //set11_16
    
    public void getSetOf14MazesFromJar(){
        Vector cl1Mazes = getMazeFromJar("/mazefourteen/set1/cl1mzes.v");
        Vector cl2Mazes = getMazeFromJar("/mazefourteen/set1/cl2mzes.v");
        
        this.initializeRandomized();
        
        //cl1MazesRANDOMIZED = cl1Mazes;
        //cl2MazesRANDOMIZED = cl2Mazes;
    }
    
    
    public void getSetOf16MazesFromJar(){
        Vector cl1Mazes = getMazeFromJar("/mazesixteen/set11_16/cl1mzes.v");
        Vector cl2Mazes = getMazeFromJar("/mazesixteen/set11_16/cl2mzes.v");
        
        this.initializeRandomized();
        
        //cl1MazesRANDOMIZED = cl1Mazes;
        //cl2MazesRANDOMIZED = cl2Mazes;
    }
    
     public void getSetOf17MazesFromJar(){
        Vector cl1Mazes = getMazeFromJar("/mazeseventeen/set1/cl1mzes.v");
        Vector cl2Mazes = getMazeFromJar("/mazeseventeen/set1/cl2mzes.v");
        
         this.initializemazes();
        
        //cl1MazesRANDOMIZED = cl1Mazes;
        //cl2MazesRANDOMIZED = cl2Mazes;
    }
     
     public void getSetOf18MazesFromJarASTEXT(){
        String cl1SMazes = getMazesFromJarASTEXT("/mazeeighteen/set1/cl1mzes.txt");
        String cl2SMazes = getMazesFromJarASTEXT("/mazeeighteen/set1/cl2mzes.txt");
        
        
        
        
        Vector cl1Mazes =  getClientMazesFromTEXT(cl1SMazes);
        Vector cl2Mazes = getClientMazesFromTEXT(cl2SMazes);
        
         this.initializemazes();
        
         //this.initializeRandomized();
         
        //cl1MazesRANDOMIZED = cl1Mazes;
        //cl2MazesRANDOMIZED = cl2Mazes;
    }
     
     
     public Vector[]  getSetOf12MazesFromJarASTEXT(){
        String cl1SMazes = getMazesFromJarASTEXT("/mazetwelve/set1/cl1mzes.txt");
        String cl2SMazes = getMazesFromJarASTEXT("/mazetwelve/set1/cl2mzes.txt");
        
        Vector cl1Mazes =  getClientMazesFromTEXT(cl1SMazes);
        Vector cl2Mazes = getClientMazesFromTEXT(cl2SMazes);
        
        Vector[] results = new Vector[2];
        results[0] = cl1Mazes;
        results[1] = cl2Mazes;
        return results;
    }
     
     
     public Vector[] getExternalMazesASTEXT(String optionname){
       
         if (optionname.equalsIgnoreCase("CUSTOM"))  return getCustomMazesAsTEXT();
         
         String cl1SMazes = getMazesFromJarASTEXT("/"+optionname+"/cl1mzes.txt");
         String cl2SMazes = getMazesFromJarASTEXT("/"+optionname+"/cl2mzes.txt");
         
         Vector cl1Mazes =  getClientMazesFromTEXT(cl1SMazes);
         Vector cl2Mazes = getClientMazesFromTEXT(cl2SMazes);
         
        Vector[] results = new Vector[2];
        results[0] = cl1Mazes;
        results[1] = cl2Mazes;
        return results;
    }
     
     
     
     
     public Vector[] getCustomMazesAsTEXT(){
         SetupIO sIO = new SetupIO();
         return sIO.loadExternalPairOfMazeVectors();
     }
     
     
             
             
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     public  Vector[] shuffleMazes(Vector[]  mPs){
       Vector cl1Mazes =  (mPs[0]);
        Vector cl2Mazes = (mPs[1]);
        Vector mazePairs = new Vector();
        
       for(int i=0;i<cl1Mazes.size();i++){
            Object o1 = cl1Mazes.elementAt(i);
            Object o2 = cl2Mazes.elementAt(i);
            MazePair mp = new MazePair(o1,o2);
            mazePairs.add(mp);      
        } 
        
        Vector mazePairsRandomized = new Vector();
        Vector cl1MazesRANDOMIZED = new Vector();
        Vector cl2MazesRANDOMIZED = new Vector();
        
        mazePairsRandomized = VectorToolkit.randomSubset(mazePairs, mazePairs.size());
        for(int i=0;i<mazePairsRandomized.size();i++){
            MazePair mp = (MazePair)mazePairsRandomized.elementAt(i);
            cl1MazesRANDOMIZED.add(mp.cl1Maze);
            cl2MazesRANDOMIZED.add(mp.cl2Maze);
        }
        
        Vector[] results = new Vector[2];
        results[0] =  cl1MazesRANDOMIZED;
        results[1] = cl2MazesRANDOMIZED;
        return results;
   
     }
     
     
     
      public Vector[] getSetOf12MazesFromJarASTEXTShuffled(){
        String cl1SMazes = getMazesFromJarASTEXT("/mazetwelve/set1/cl1mzes.txt");
        String cl2SMazes = getMazesFromJarASTEXT("/mazetwelve/set1/cl2mzes.txt");
        
        Vector cl1Mazes =  getClientMazesFromTEXT(cl1SMazes);
        Vector cl2Mazes = getClientMazesFromTEXT(cl2SMazes);
        Vector mazePairs = new Vector();
        
       for(int i=0;i<cl1Mazes.size();i++){
            Object o1 = cl1Mazes.elementAt(i);
            Object o2 = cl2Mazes.elementAt(i);
            MazePair mp = new MazePair(o1,o2);
            mazePairs.add(mp);
            
        }
        Vector mazePairsRandomized = new Vector();
        Vector cl1MazesRANDOMIZED = new Vector();
        Vector cl2MazesRANDOMIZED = new Vector();
        
        mazePairsRandomized = VectorToolkit.randomSubset(mazePairs, mazePairs.size());
        for(int i=0;i<mazePairsRandomized.size();i++){
            MazePair mp = (MazePair)mazePairsRandomized.elementAt(i);
            cl1MazesRANDOMIZED.add(mp.cl1Maze);
            cl2MazesRANDOMIZED.add(mp.cl2Maze);
        }
        
        Vector[] results = new Vector[2];
        results[0] =  cl1MazesRANDOMIZED;
        results[1] = cl2MazesRANDOMIZED;
        return results;
   
    }
     
     
     
     
     
     public void getSetOf18MazesFromJarSET3(){
        String cl1SMazes = getMazesFromJarASTEXT("/mazeeighteen/set3marleen/cl1mazes.txt");
        String cl2SMazes = getMazesFromJarASTEXT("/mazeeighteen/set3marleen/cl2mazes.txt");
        
        
        
        Vector cl1Mazes =  getClientMazesFromTEXT(cl1SMazes);
        Vector cl2Mazes = getClientMazesFromTEXT(cl2SMazes);
        
         this.initializemazes();
        
         
         
        //cl1MazesRANDOMIZED = cl1Mazes;
        //cl2MazesRANDOMIZED = cl2Mazes;
    }
    
    
     
     
     public  Vector getClientMazesFromTEXT(String txt){
         
    txt = txt.replaceAll("BEGINLink", "BEGIN Link");
    txt = txt.replaceAll("FINISHLink", "FINISH Link");
    txt = txt.replaceAll("\nLink", "Link");
    
    System.err.println("THE FILE IS: "+txt);
    //System.out.println(textFile);
    Vector mazesOfStrings = getClientMazesFromVectorOfStringsForEachMaze(txt);
    System.err.println("THE SIZE IS: "+mazesOfStrings.size());
    Vector vOfMazes = getClientMazesStep3_ConstructMazes(mazesOfStrings);
    return vOfMazes;
}
     
     
     public Vector getClientMazesStep3_ConstructMazes(Vector v){
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
    
    String id="notsetyet";
    boolean hasSetIDOnce = false;
    
     try{
        Vector mazesquares = new Vector();
        for(int i=0;i<v.size();i++){
             String s = (String)v.elementAt(i);
             if(s.startsWith("MAZE No:")){
                 if(hasSetIDOnce){
                     System.err.println("Maze setup has found multiple IDs for the same maze");
                     CustomDialog.showDialog("Maze setup has found multiple IDs for the same maze");
                 }
                 s = s.replace("MAZE No: ", "");
                 s = s.replace("MAZE No:", ""); ///In case the setup file has a space missing
                 id =""+s;
                 
                
                 
                 System.err.println("IT IS: "+id);
                
                 hasSetIDOnce = true;
                 continue;
             }
             
             
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
        
        connectLinkToSquareEASTWEST(hasLinkEAST, hasLinkWEST,false);
        connectLinkToSquareEASTWEST(hasLinkEASTGate, hasLinkWESTGate,true);
        connectLinkToSquareNORTHSOUTH(hasLinkNORTH, hasLinkSOUTH,false);
        connectLinkToSquareNORTHSOUTH(hasLinkNORTHGate, hasLinkSOUTHGate,true);
        
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
        
        ///String vs = (String)v.elementAt(0);
        ///System.err.println("The name is: "+vs);
        ///System.exit(-55);
        
        Maze mz = new Maze(0);
        mz.id= id;
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
              currentMaze.addElement(line); ////
           }
           else if(  line.startsWith("MAZE No") ||line.startsWith("Maze No")){
                
               currentMaze = new Vector();
               mazesOfStrings.addElement(currentMaze);
               currentMaze.addElement(line); ///////
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
     
    
    public void getSetOf9MazesFromJar(){
       
        String[] choices = {"01","02","03","04","05","06","07","08","09","10",
                            "11","12","13","14","15","16","17","18","19","20",
                            "21","22","23","24","25","26","27","28","29","30",
                            "31","32","33","34","35","36","37","38","39","40",
                            "41","42","43"};
        
        String title = "Choose and START!";
        String prompt = "\n"
                + "Please select a set (of 9 mazes)\n\nPressing OK will start the timer!\n\n";
        String result = CustomDialog.showComboBoxDialog(title, prompt, choices, true);
        System.err.println("RESULT:"+result);
        //System.exit(-4);
        
        //cl1Mazes = getMazeFromJar("/mazenine/set"+result+"/cl1mzes.v");
        //cl2Mazes = getMazeFromJar("/mazenine/set"+result+"/cl2mzes.v");
        
       //if(cl1Mazes==null || cl2Mazes ==null){
            
            Vector cl1Mazes = getMazeFromJar("/mazenine/set"+result+"/cl1Mazes.v");
            Vector cl2Mazes = getMazeFromJar("/mazenine/set"+result+"/cl2Mazes.v");
            
             
        //}
       // cl1Mazes = getMazeFromJar("/mazegamesetup/expmazes12/cl1mzes.v");
        //cl2Mazes = getMazeFromJar("/mazegamesetup/expmazes12/cl2mzes.v");
        
        
        
        if(cl1Mazes==null || cl2Mazes == null){
            
            CustomDialog.showDialog("For some reason the chat tool could not find the mazes in the configuration file.\n"
                    + "The chat tool server will close after you press OK.\n"
                    + "If this problem persists, please email g.j.mills@rug.nl\n");
            System.exit(-4);
        }
        Vector cl1MazesRANDOMIZED = cl1Mazes;
        Vector cl2MazesRANDOMIZED = cl2Mazes;
        System.err.println("THE SIZE IS"+cl1MazesRANDOMIZED.size());
        //System.exit(-4);
    }
    
    public void getSetOf9MazesFromJarOLD(){
        getMazesFromJar();
    }
    
    
    
    
    
    
    
    
    public  void getMazesFromJar(){
        Vector cl1Mazes = getMazeFromJar("/mazegamesetup/expmazes12/cl1mzes.v");
        Vector cl2Mazes = getMazeFromJar("/mazegamesetup/expmazes12/cl2mzes.v");
        
        
        

         // System.exit(-4)
        if(cl1Mazes==null || cl2Mazes == null){
            
            CustomDialog.showDialog("For some reason the chat tool could not find the mazes in the configuration file.\n"
                    + "The chat tool server will close after you press OK.\n"
                    + "If this problem persists, please email g.j.mills@rug.nl\n");
            System.exit(-4);
        }
        
        initializeRandomized();
    }
    
    
    
    public void initializemazes(){
      /*  for(int i=0;i<cl1Mazes.size();i++){
            Object o1 = cl1Mazes.elementAt(i);
            Object o2 = cl2Mazes.elementAt(i);
            MazePair mp = new MazePair(o1,o2);
            mazePairs.add(mp);
            
        }
         for(int i=0;i<mazePairs.size();i++){
            MazePair mp = (MazePair)mazePairs.elementAt(i);
            cl1MazesRANDOMIZED.add(mp.cl1Maze);
            cl2MazesRANDOMIZED.add(mp.cl2Maze);
        }
              */
    }
    
    
    public  void initializeRandomized(){
        /*
        for(int i=0;i<cl1Mazes.size();i++){
            Object o1 = cl1Mazes.elementAt(i);
            Object o2 = cl2Mazes.elementAt(i);
            MazePair mp = new MazePair(o1,o2);
            mazePairs.add(mp);
            
        }
        Vector mazePairsRandomized = new Vector();
        mazePairsRandomized = VectorToolkit.randomSubset(mazePairs, mazePairs.size());
        for(int i=0;i<mazePairsRandomized.size();i++){
            MazePair mp = (MazePair)mazePairsRandomized.elementAt(i);
            cl1MazesRANDOMIZED.add(mp.cl1Maze);
            cl2MazesRANDOMIZED.add(mp.cl2Maze);
        }
        */
    }
    
    
    class MazePair {
         
          public MazePair(Object m1, Object m2){
               cl1Maze = m1;
               cl2Maze = m2;
               
          }
         
          public Object cl1Maze;
          public Object cl2Maze;
     }
   
}
