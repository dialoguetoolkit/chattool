/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.stimuliset;

import diet.server.ConversationController.ui.CustomDialog;
import diet.utils.VectorToolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author sre
 */
public class ConfidenceTaskControllerSequenceSet {
    
    
      public ConfidenceTaskControllerSequence ctcseq1;
      public ConfidenceTaskControllerSequence ctcseq2; 
      public ConfidenceTaskControllerSequence ctcseq3; 
      public ConfidenceTaskControllerSequence ctcseq4; 
      public ConfidenceTaskControllerSequence ctcseq5; 
    
      public ConfidenceTaskControllerSequenceSet(File f){
          processFile(f);
      }
      
      
      
      public ConfidenceTaskControllerSequenceSet(){
          CustomDialog.showDialog("The next menu will ask for the file containing the randomized sequences..");
          File f = loadFile();
          processFile(f);
      }
      
      
      public static File loadFile(){
           String s = System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence";        
           File f = CustomDialog.loadFile(s);
           return f;
      }
      public  void processFile(File f){
          try{
            BufferedReader in = new BufferedReader(new FileReader(f));
            String sCurrentLine;
            String[] linesOfFile = new String[24];
            int line =0;
            while ((sCurrentLine = in.readLine()) != null) {
				System.err.println(sCurrentLine);
                                linesOfFile[line]=sCurrentLine;
                                line++;
	    }
            System.err.println(line);
            if(line!=21){
                CustomDialog.showDialog("ERROR: there should be 21 lines in the file...only "+line+" have been detected");
                System.exit(-5);
            }
            
           
            String[] set1_Position =  linesOfFile[1].split("\t+");
            String[] set1_Contrast = linesOfFile[2].split("\t+");
            String[] set1_FirstOrSecond = linesOfFile[3].split("\t+");       
            
            String[] set2_Position =  linesOfFile[5].split("\t+");
            String[] set2_Contrast = linesOfFile[6].split("\t+");
            String[] set2_FirstOrSecond = linesOfFile[7].split("\t+");     
            
            
            String[] set3_Position =  linesOfFile[9].split("\t+");
            String[] set3_Contrast = linesOfFile[10].split("\t+");
            String[] set3_FirstOrSecond = linesOfFile[11].split("\t+");   
            
            String[] set4_Position =  linesOfFile[13].split("\t+");
            String[] set4_Contrast = linesOfFile[14].split("\t+");
            String[] set4_FirstOrSecond = linesOfFile[15].split("\t+");   
            
            String[] set5_Position =  linesOfFile[17].split("\t+");
            String[] set5_Contrast = linesOfFile[18].split("\t+");
            String[] set5_FirstOrSecond = linesOfFile[19].split("\t+");   
            
            
            System.err.println(set1_Position.length);
            System.err.println(set1_Contrast.length);
            System.err.println(set1_FirstOrSecond.length);
            
            ctcseq1 = new ConfidenceTaskControllerSequence(set1_Position,set1_Contrast,set1_FirstOrSecond);
            ctcseq2 = new ConfidenceTaskControllerSequence(set2_Position,set2_Contrast,set2_FirstOrSecond);
            ctcseq3 = new ConfidenceTaskControllerSequence(set3_Position,set3_Contrast,set3_FirstOrSecond);
            ctcseq4 = new ConfidenceTaskControllerSequence(set4_Position,set4_Contrast,set4_FirstOrSecond);
            ctcseq5 = new ConfidenceTaskControllerSequence(set5_Position,set5_Contrast,set5_FirstOrSecond);
            
          }catch(Exception e){
              e.printStackTrace();
              CustomDialog.showDialog("ERROR: "+e.getMessage());
              System.exit(-5);
          }
          
      }
      public static void main(String[] args){
           //File f =loadFile();
           File f = new File(System.getProperty("user.dir"));
           f  = new File(f,"randomizedsequence.txt");
         
           ConfidenceTaskControllerSequenceSet cs = new ConfidenceTaskControllerSequenceSet();
      }
      
}
