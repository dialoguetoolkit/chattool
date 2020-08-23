/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils.postprocessing;

import diet.server.ConversationController.ui.CustomDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class WYSIWYGTranscriptLayout {
    
    
    
    
    
    
    
     public void processDirectory(File directory){
         //         directory =   new File("C:\\New folder (2)\\DATAANALYSIS\\2017.05.19.FaceCommsInteractionDesignClass\\0038FaceComms2016WYSIWYGDyadic_InteractionDesignClass");//wysiwyg_cie_LLLL1_s2580861_o_s3016080.txt";
       // directory =  directory =   new File("C:\\New folder (2)\\DATAANALYSIS\\2017.05.19.FaceCommsInteractionDesignClass\\001RAWDATA\\0040PROCESSING.STEP2");//wysiwyg_cie_LLLL1_s2580861_o_s3016080.txt";
        //directory =   new File("C:\\New folder\\gd2\\2017-2018\\ExpPrag\\LABCHATTOOL\\data\\saved experimental data\\0003FaceComms2016WYSIWYGDyadic_InteractionDesignClass");//wysiwyg_cie_LLLL1_s2580861_o_s3016080.txt";
        
       // directory = new File("C:\\github3\\chattool\\data\\saved experimental data\\0490Dyadic_WYSIWYGInterface");
        
       // directory = new File("C:\\Users\\LX1C\\Desktop\\DBG");
        
       // directory = new File("C:\\New folder (3)\\DATAANALYSIS\\2019.05.FaceComms.SocialMediaClass\\0043FaceComms2016WYSIWYGDyadic_InteractionDesignClass");
       // directory = new File("C:\\New folder (3)\\DATAANALYSIS\\2019.05.FaceComms.SocialMediaClass\\0045FaceComms2016WYSIWYGDyadic_InteractionDesignClass");
       //  directory = new File("C:\\New folder (3)\\DATAANALYSIS\\2016.FACECOMMS-InteractionDesignClass\\01.RAWDATA\\0002NEEDS TO BE SET");
         
         // File directory = new File(s);
         File[] listings = directory.listFiles();
         Vector wysiwygfiles = new Vector();
         for(int i=0;i<listings.length;i++){
             if(listings[i].getName().contains("PROCwysiwyg_cie_")   && listings[i].getName().contains("_o_")){
                 wysiwygfiles.addElement(listings[i]);
                 System.err.println("ADDING "+ listings[i].getName());
             }
        }
        
        for(int i=0;i<wysiwygfiles.size();i++){
            File fOTHER = (File)wysiwygfiles.elementAt(i);
            String csvTextOTHER = getCSVText(fOTHER);
           
            String fOTHERName = fOTHER.getName();
            
            String fSelf =      fOTHERName.substring(0,fOTHERName.indexOf("_o_")+3).replace("_o_", "_s_")+".txt";
            String csvTextSELF = getCSVText(new File(directory,fSelf));
            //System.err.println(fSelf);
            System.err.println();
            
            String fWindow =      fOTHERName.substring(0,fOTHERName.indexOf("_o_")+3).replace("_o_", "_w_")+".txt";
            String csvTextWINDOW = getCSVText(new File(directory,fWindow));
            
            
            
            String output = getOutput(csvTextSELF, csvTextOTHER, csvTextWINDOW, 150);
            System.err.println(output);
            
            String filename = "TRANSCRIPT_"+fOTHER.getName().replaceAll(".txt", "")+".txt";
            File outputFile = new File(directory,filename);
            this.saveString(outputFile, output);
            
            //String filenameFULL = fOTHER.getName()+"_transcript2.txt";
            //File outputFileFULL = new File(directory,filenameFULL);
            //this.saveString(outputFileFULL, output);
            
        }
         
       
        
         
     }
     
     public void saveString(File f, String s){
          try{
             PrintWriter writer = new PrintWriter(f, "UTF-8");
             writer.write(s);
             writer.flush();
             writer.close();
           }catch (Exception e){
               e.printStackTrace();
           }
     }
     
     
     
      public  String getCSVText(File fother){
       // String other =  "C:\\New folder\\gd2\\experimentdatabackups\\2016.FACECOMMS\\2016.InteractionDesignClass\\0031NEEDS TO BE SET\\wysiwyg_cie_LLLL1_s2580861_o_s3016080.txt";
         System.err.println("getCSVText is trying to load: "+fother.getName());
        //File fother = new File(other);
         String fotherRAW ="";
         try (BufferedReader br = new BufferedReader(new FileReader(fother))) {
               String line;
               while ((line = br.readLine()) != null) {
                   fotherRAW = fotherRAW+line;                  
               }
         }catch (Exception e){
             e.printStackTrace();
         }
         String fotherREPLACED1 = fotherRAW.replace("¦¦", "¦ ¦");
         String fotherREPLACED2 = fotherREPLACED1.replace("¦¦", "¦ ¦");
         //fotherREPLACED2 = fotherREPLACED2.replace("¦¦", "¦ ¦");
         if(fotherREPLACED2.startsWith("¦")){
             fotherREPLACED2=" "+fotherREPLACED2;
         }
         //System.err.println(fotherREPLACED2);
         System.err.println("The text is:"+fotherREPLACED2);
         return fotherREPLACED2;
        
     }
     
     
      public String getOutput(String selfCSV, String otherCSV, String windowCSV, int charsperline){
          System.err.println("CHARSPERLINE: "+charsperline);
          System.err.println("running getOutput( ) on "+ selfCSV+"+++++++++" + otherCSV +"+++++++++" + windowCSV);
          selfCSV = selfCSV.replace("¦", "");
          otherCSV = otherCSV.replace("¦", "");
          windowCSV = windowCSV.replace("¦", "");
          
          System.err.println("Getitngoutput:");
          System.err.println(selfCSV);
          System.err.println(otherCSV);
          System.err.println(windowCSV);
          
          
          Vector selfLines = new Vector();
          Vector otherLines = new Vector();
          Vector windowLines = new Vector();
          
          Vector<String> currentLine = new Vector<String>();
          selfLines.add(currentLine);
    
          for(int i=0;i<selfCSV.length();i++){
               currentLine.add(""+selfCSV.charAt(i));
               System.err.println("SELFCSV "+i+ " "+selfCSV.charAt(i));
               if(currentLine.size()>=charsperline){
                   currentLine = new Vector<String>();
                   selfLines.add(currentLine);         
                   System.err.println("selfcsvcreatingnewline");
               } 
              
          }
          
          System.err.println("selfcsvlength:"+selfCSV.length());
          System.err.println("selflinessize:"+selfLines.size());
          
          
          currentLine = new Vector<String>();
          otherLines.add(currentLine);
          for(int i=0;i<otherCSV.length();i++){
               currentLine.add(""+otherCSV.charAt(i));
               if(currentLine.size()>=charsperline){
                   currentLine = new Vector<String>();
                   otherLines.add(currentLine);
                  
                   
               }   
          }
           
            System.err.println("othercsvlength:"+otherCSV.length());
          System.err.println("otherlinessize:"+otherLines.size());
          
          
          currentLine = new Vector<String>();
          windowLines.add(currentLine);
          for(int i=0;i<windowCSV.length();i++){
               currentLine.add(""+ windowCSV.charAt(i));
               if(currentLine.size()>=charsperline){
                   currentLine = new Vector<String>();
                   windowLines.add(currentLine);
                   
                   
               }   
          }
          
          
            System.err.println("selfwindowlength:"+windowCSV.length());
          System.err.println("selflinessize:"+windowLines.size());
          
          
          int numberOfLines = Math.max(selfLines.size(), otherLines.size());
          System.err.println("NUMBER OF LINES IS"+numberOfLines);
          if(selfLines.size()!= otherLines.size()){
              System.err.println("DIFFERENCE IS: "+(selfLines.size()- otherLines.size()));
          }
          
          String output = "";
          for(int i=0;i<numberOfLines;i++){
               
                if(i<otherLines.size()){
                   Vector<String> otherLine = (Vector<String>)otherLines.elementAt(i);
                   output = output+ convertVectorOfStringToString(otherLine) +"\n";
                }
                else{
                     output = output+ "\n";
                }
                if(i<selfLines.size()){
                   Vector<String> selfLine = (Vector<String>)selfLines.elementAt(i);
                   output = output + convertVectorOfStringToString(selfLine) +"\n"; 
                }
                 else{
                     output = output+ "\n";
                }
                if(i<windowLines.size()){
                   Vector<String> windowLine = (Vector<String>)windowLines.elementAt(i);
                   output = output + convertVectorOfStringToString(windowLine) +"\n"; 
                }
                 else{
                     output = output+ "\n";
                }  
             output = output +"\n\n\n\n\n";
               
              
               
          }
          
          System.err.println("get output returns: "+output);
          
          
          
          return output;
      }
      
    public static String convertVectorOfStringToString(Vector<String> vs){
        String retval="";
        for(int i=0;i<vs.size();i++){
            retval=retval+vs.elementAt(i);
        }
        return retval;
    }
     
    public static void main(String[] args){
          WYSIWYGTranscriptLayout wtl = new WYSIWYGTranscriptLayout();
          //wtl.processFile(null);
          wtl.processDirectory(null);
          
          
    }
    
    
}
