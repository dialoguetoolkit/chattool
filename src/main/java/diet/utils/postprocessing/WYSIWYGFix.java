/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils.postprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class WYSIWYGFix {
    
     public WYSIWYGFix(){
        // collateTurns();
          System.err.println("STARTING");
         

         //this.fix1try();  //THIS IS THE FIRST STAGE! IT HAS TO BE DONE FIRST...THEN YOU HAVE TO RESORT THE OUTPUT FROM IT BY FOLDER//DYAD//TIMESTAMP....THEN DO THE COLLATION (SEE BELOW)
         
         
          
          
          this.collateTurns();
         System.err.println("FINISHED");
         System.exit(-5);
     }
     
     
      public void collateTurns(){
        //Remember this needs to be sorted by dialogueID then by time...before it will work..  
         //Remember it also has to be in UTF8 
          
      // File f = new File("C:\\New folder (2)\\DATAANALYSIS\\2016.FACECOMMS-InteractionDesignClass\\02.COLLATING\\NEWCOLLATINGcollated1485024300688.005.TEXTONLY.CSV");
       //File f = new File("pass1output2.csv");
       
       File f = new File ("C:\\New folder (2)\\DATAANALYSIS\\2016.FACECOMMS-RAP-LOTTE-GUANGHAO\\001\\WYSIWYG\\WYSIWTGcollated1485652258759.txt_UTF8.002.csv");
       
       
       Vector vvContig = new Vector();
         // vvContig.addElement(new Vector());
       try{
           System.out.println("HERE2");
           BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
           String line;
           String previousLine;
           String[] previousLineSplit=null;
           
         
           
           while((line=br.readLine())!=null){
             
               String[] currentlinesplit = line.split("¦");
               //System.err.println(linesplit[0]);
               //System.err.println(linesplit[2]);
               System.err.println(currentlinesplit[0]);
               boolean currentIsContiguous = false;
               if(previousLineSplit!=null&&currentlinesplit[2].equalsIgnoreCase(previousLineSplit[2])){
                   //It is same dyad
                   if(previousLineSplit!=null && currentlinesplit[4].equalsIgnoreCase("normalturn") && previousLineSplit[4].equalsIgnoreCase("normalturn")){
                       //It is a "normal turn"
                       if(previousLineSplit!=null&&previousLineSplit[5].equalsIgnoreCase(currentlinesplit[5])&&!currentlinesplit[5].equalsIgnoreCase("server")){
                           ///It is from someone with same ID
                           if(previousLineSplit!=null&&previousLineSplit[6].equalsIgnoreCase(currentlinesplit[6])){
                               //It is from someone with same username
                               currentIsContiguous = true;
                              // System.err.println("CONTIGISTRUE!");
                               //System.out.println("CONTIG");
                           }
                       }
                       
                   }
               }
               if(currentIsContiguous){
                  ((Vector) vvContig.lastElement()).addElement(currentlinesplit);
               }
               else{
                   vvContig.addElement(new Vector());
                   ((Vector) vvContig.lastElement()).addElement(currentlinesplit);
               }
               
               ////////////
               previousLine = line;
               previousLineSplit = previousLine.split("¦");
           }
           
       }catch(Exception e){
               e.printStackTrace();
        }
         
       Vector outputROWS = new Vector();
       
       for(int i=0;i<vvContig.size();i++){
            Vector v= (Vector)vvContig.elementAt(i);
            if(v.size()==1){
                String[] firstrow = (String[]) v.elementAt(0);
                outputROWS.addElement(firstrow);
            }
            else if (v.size()==0){
                System.err.println(i);
                System.exit(-56);
            }
            else{
                 String[] firstrow = (String[]) v.elementAt(0);
                 for(int j=1;j<v.size();j++){
                       String[] nextrow = (String[])v.elementAt(j);
                       firstrow[1]=nextrow[1];
                       firstrow[8]=firstrow[8]+nextrow[8];
                       firstrow[13]=nextrow[13];
                       firstrow[14]=nextrow[14];
                       
                }
                 outputROWS.addElement(firstrow);
            }
            
           
           // System.err.println("HERE");
       }
        
         try {
             Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("concat"+ new Date().getTime()  +".csv"), "UTF-8"));
             //Writer out2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("pass1output.csv"), "UTF-8"));
             for(int i=0;i<outputROWS.size();i++){
                 String[] row = (String[])outputROWS.elementAt(i);
                 String rowconcat = "";
                 for(int j=0;j<row.length;j++){
                     if(j==0){
                         rowconcat = row [0] + "¦";
                     }
                     else{
                         rowconcat=rowconcat+  row[j] + "¦";
                     }
                     
                    
                 }
                   out.write(rowconcat+"\n");
                   //out2.write(rowconcat+"\n");
             }
             
            out.flush();
           //out2.flush();
           out.close();
           //out2.close();
             
        } catch (Exception e) {
           
         }
       
       
       }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
    
     public void collateTurnsOLD(){
       //File f = new File("C:\\New folder (2)\\DATAANALYSIS\\2016.FACECOMMS-InteractionDesignClass\\02.COLLATING\\NEWCOLLATINGcollated1485024300688.005.TEXTONLY.CSV");
       File f = new File("pass1output.csv");
       
       try{
           BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
           String line;
           String previousLine;
           String[] previousLineSplit=null;
           
           String[] currentcontiguous = null;
          
           
           while((line=br.readLine())!=null){
             
               String[] currentlinesplit = line.split("¦");
               //System.err.println(linesplit[0]);
               //System.err.println(linesplit[2]);
              
               boolean currentIsContiguous = false;
               if(previousLineSplit!=null&&currentlinesplit[2].equalsIgnoreCase(previousLineSplit[2])){
                   //It is same dyad
                   if(previousLineSplit!=null && currentlinesplit[4].equalsIgnoreCase("normalturn") && previousLineSplit[4].equalsIgnoreCase("normalturn")){
                       //It is a "normal turn"
                       if(previousLineSplit!=null&&previousLineSplit[5].equalsIgnoreCase(currentlinesplit[5])&&!currentlinesplit[5].equalsIgnoreCase("server")){
                           ///It is from someone with same ID
                           if(previousLineSplit!=null&&previousLineSplit[6].equalsIgnoreCase(currentlinesplit[6])){
                               //It is from someone with same username
                               currentIsContiguous = true;
                               //System.out.println("CONTIG");
                           }
                       }
                       
                   }
               }
               if(currentIsContiguous){
                   if(currentcontiguous ==null){
                       currentcontiguous = previousLineSplit;
                   }
                   currentcontiguous[8] = currentcontiguous[8]+"+++"+currentlinesplit[8];
               }
               else if(!currentIsContiguous){
                   if(currentcontiguous!=null){
                       System.err.println(currentcontiguous[8]);
                   }
                   
                   //System.err.println(linesplit[8]);
                   currentcontiguous=null;
               }
               
               
               
               ////////////
               previousLine = line;
               previousLineSplit = previousLine.split("¦");
           }
           
       }catch(Exception e){
               e.printStackTrace();
        }
           
       }
     
     
      public void fixPASS1(){  ///Need to do this...and then sort them and collate
          //go through spreadsheet from bottom to top (reversed)
          //Inside each experiment....
          //For each turn....
          ///     The correcct participantID is the participantID of the immediately preceding turn by another speaker
          //      The correct recipient is the recipient of the immediately preceding turn by another speaker
          //      The correct DyadNo is the dyadNo of the immediately preceding turn by another speaker
          
          
      }
    
      
    
      public void fix1try(){
       File f = new File("C:\\New folder (2)\\DATAANALYSIS\\2016.FACECOMMS-InteractionDesignClass\\02.COLLATING\\NEWCOLLATINGcollated1485024300688.006.ARANGEDBYTIME.csv");
       try{
           BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
           String line;
           String previousLine;
           String[] previousLineSplit=null;
           
           String[] currentcontiguous = null;
          
        String finalString ="";
        long counter =0;
        System.err.println("SARTINGWHILELOOP");
        Vector v01RAWSTRINGS = new Vector(); 
        
        Vector v02OUT = new Vector();
        
        while((line=br.readLine())!=null){
            v01RAWSTRINGS.addElement(line); 
        }
           
        for(int i=v01RAWSTRINGS.size()-1;i>=0;i--){
             String currentROW = (String) v01RAWSTRINGS.elementAt(i);
            // System.err.println(currentROW);
             String[]  currentROWSplit = currentROW.split("¦");
             v02OUT.insertElementAt(currentROWSplit, 0);
             
             
             if(currentROWSplit[4].equalsIgnoreCase("normalturn") &&! currentROWSplit[5].equalsIgnoreCase("server")){
                currentROWSplit[2] = "NOTSET";
                currentROWSplit[5] = "NOTSET";
                currentROWSplit[6] = "NOTSET";
                currentROWSplit[7] = "NOTSET";
                currentROWSplit[9] = "NOTSET";
                currentROWSplit[15] = "NOTSET";
                boolean foundprevious = false;
                for(int j=i-1;j>=0;j--){
                    String earlierROW = (String)v01RAWSTRINGS.elementAt(j);
                    String[] earlierROWSplit = earlierROW.split("¦");   
                    if(earlierROWSplit[0].equalsIgnoreCase(currentROWSplit[0])  ){
                         //It is the same experiment folder
                         if(earlierROWSplit[4].equalsIgnoreCase("normalturn") &&! earlierROWSplit[5].equalsIgnoreCase("server")){
                             //The preceding turn isn't from the server...and is text generated by a participant
                              currentROWSplit[2] = earlierROWSplit[2];
                              currentROWSplit[5] = earlierROWSplit[5];
                              currentROWSplit[6] = earlierROWSplit[6];
                              currentROWSplit[7] = earlierROWSplit[7];
                              currentROWSplit[9] = earlierROWSplit[9];
                              currentROWSplit[15] = earlierROWSplit[15];
                              currentROWSplit[16] = earlierROWSplit[16];
                              break;
                              
                         }
                        
                        //It is the same experiment set...
                       
                    }
                    
                }
             }  
               
           }
           
           
        
         Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("outfilename"+ new Date().getTime()  +".csv"), "UTF-8"));
         Writer out2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("pass1output.csv"), "UTF-8"));
         try {
             for(int i=0;i<v02OUT.size();i++){
                 String[] row = (String[])v02OUT.elementAt(i);
                 String rowconcat = "";
                 for(int j=0;j<row.length;j++){
                     if(j==0){
                         rowconcat = row [0] + "¦";
                     }
                     else{
                         rowconcat=rowconcat+  row[j] + "¦";
                     }
                     
                    
                 }
                   out.write(rowconcat+"\n");
                   out2.write(rowconcat+"\n");
             }
             
            
             
        } finally {
           out.flush();
           out2.flush();
           out.close();
           out2.close();
         }
           
           
       }catch(Exception e){
               e.printStackTrace();
        }
        
       
      
       
       
       
       }
      
      
      
      
      
      
}
