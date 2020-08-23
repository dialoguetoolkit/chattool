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
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 *
 * @author gj
 */
public class WYSIWYTranscriptLayoutHorizontalizer {
    
   // String directory = "C:\\testing\\";
    
     String directory = "C:\\github3\\chattool\\data\\saved experimental data\\0489Dyadic_WYSIWYGInterface";
        
    
    
    public static void main(String[] args){
        WYSIWYTranscriptLayoutHorizontalizer wtlh = new WYSIWYTranscriptLayoutHorizontalizer();
        wtlh.convertDirectory();
    }
    
    
    
    public void convertDirectory(){
        File fdir = new File(directory);
        File[] allFiles = fdir.listFiles();
        for(int i=0;i<allFiles.length;i++){
            File f = allFiles[i];
            System.out.println("HERE"+allFiles[i].toString());
            if(f.getName().endsWith("txt")){
                 String[] conversion = doConversion(f.getPath());
                 String outfilename =f.getPath()+".horiz.csv";
                 createCSV(outfilename,conversion[0], conversion[1]);   
                 
            }
        }
    }
    
    
    
    
    public void createCSV(String outfilename,String person1, String person2){
        System.out.println("LENGTH:"+person1.length()+person2.length());
       // System.out.println(person2);
        //System.exit(-9);
        if(person1.length()!=person2.length()){
            System.err.println("ERROR NOT THE SAME SIZE");
            System.exit(-10);
        }
        if(person1.length()>16000) {
            System.err.println("ERROR TOO BI");
            System.exit(-11);
        }
        
        String person1CSV="";
        String person2CSV="";
        for(int i=0;i<person1.length();i++){
            person1CSV = person1CSV+person1.charAt(i)+"¦";
            person2CSV = person2CSV+person2.charAt(i)+"¦";
        }
        
        
        String outputtext = person1CSV+"\n"+person2CSV;
        
        try {
    //create a temporary file
    

    BufferedWriter writer = new BufferedWriter(new FileWriter(outfilename));
    writer.write (outputtext);

    //Close writer
    writer.close();
} catch(Exception e) {
    e.printStackTrace();
}
        
        
    }
    
    
    public String[] doConversion(String filename){
      int lineNo=0;
      String person1 ="";
      String person2="";
        
        
      try{
        File infile = new File(filename);
        FileInputStream fis = new FileInputStream(infile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        System.err.println("");
	while ((line = br.readLine()) != null) {
		//System.out.println(line);
                if(lineNo % 8 ==0 ){
                    person1=person1+line;
                   // person2 = person2+createEmptySpace(line.length());
                }
                else if((lineNo)%8==1){
                   // person1 = person1+createEmptySpace(line.length());
                    person2=person2+line;
                    
                }
                if(line.length()>0){
                   // System.err.print(lineNo+","+lineNo);
                }
                
                
               lineNo=lineNo+1;
	}
        System.out.println(person2);
	br.close();
        
        
        }catch(Exception e){
            e.printStackTrace();
        }
       return new String[] {person1,person2};
    }
    
    public String createEmptySpace(int length){
        System.out.print(length+",");
        String retvalue="";
        for(int i=0;i<length;i++){
            retvalue=retvalue+" ";
        }
        return retvalue;
    }
    
}
