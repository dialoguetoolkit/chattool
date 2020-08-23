/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.postprocessing.collatingdata;

import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.CustomDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class CollatingData {
    
    
        //String header = "";
    
        static String separator = DefaultConversationController.sett.recordeddata_CSVSeparator;
    
        
       
        
        
        public CollatingData(){
            
             boolean doPrefixBasedSearch = CustomDialog.getBoolean("Do you want to do prefix-based search?","PREFIX","turns.txt");
             if(doPrefixBasedSearch){
                  String directory = System.getProperty("user.dir");
              //String directory = System.getProperty("user.dir")+File.separator+"data"+File.separator+"Saved experimental data";
              File[] fs = CustomDialog.loadDirectories("What are the directories containing the experiments you want to collate?", directory);
              String[] contents = new String[fs.length];
              contents[0] = processDirectoryPREFIXSEARCH(fs[0],true,"turns");  
              for(int i=1;i<fs.length;i++){
                   contents[i] = processDirectoryPREFIXSEARCH(fs[i],false,"turns"); 
                   System.err.println("PROCESSEDDIRECTORY "+i);
              }
              System.err.println("COLLATINGA");
              String output ="";
              for(int i=0;i<contents.length;i++){
                   //System.out.print(contents[i]);
                   System.err.println("COLLATINGINTOONEBIGOUTPUT"+i);
                   output=output+contents[i];
              }
              String outputFilename = "collated"+new Date().getTime()+".txt";
              CustomDialog.saveFile(directory, outputFilename, output);
              Conversation.printWSln("Main","DONE!");
                  
              return;
             }
            
            
             boolean doOLDCollation =CustomDialog.getBoolean("Do you want to use the old format or the new format ", "OLD", "NEW");
             String prefixOfTurnsFile = "";
             prefixOfTurnsFile = CustomDialog.getString("What is the prefix", "");
             
             separator = CustomDialog.show2OptionDialog(new String[]{"|","¦"},"What do you want to use as the CSV separator?", "What do you want to use as the CSV separator?");
             
             
             
             if(!doOLDCollation){
                   //File f  = CustomDialog.loadFile(System.getProperty("user.dir"));
              String directory = System.getProperty("user.dir");
              //String directory = System.getProperty("user.dir")+File.separator+"data"+File.separator+"Saved experimental data";
              File[] fs = CustomDialog.loadDirectories("What are the directories containing the experiments you want to collate?", directory);
              String[] contents = new String[fs.length];
              contents[0] = processDirectoryNEW(fs[0],true);  
              for(int i=1;i<fs.length;i++){
                   contents[i] = processDirectoryNEW(fs[i],false); 
                   System.err.println("PROCESSEDDIRECTORY "+i);
              }
              System.err.println("COLLATINGA");
              String output ="";
              for(int i=0;i<contents.length;i++){
                   //System.out.print(contents[i]);
                   System.err.println("COLLATINGINTOONEBIGOUTPUT"+i);
                   output=output+contents[i];
              }
              String outputFilename = "collated"+new Date().getTime()+".txt";
              CustomDialog.saveFile(directory, outputFilename, output);
              Conversation.printWSln("Main","DONE!");
                  
              return;
              }
             

             
             
             if(doOLDCollation){
                   //File f  = CustomDialog.loadFile(System.getProperty("user.dir"));
              String directory = System.getProperty("user.dir");
              //String directory = System.getProperty("user.dir")+File.separator+"data"+File.separator+"Saved experimental data";
              File[] fs = CustomDialog.loadDirectories("What are the directories containing the experiments you want to collate?", directory);
              String[] contents = new String[fs.length];
              contents[0] = processDirectoryOLD(fs[0],true);  
              for(int i=1;i<fs.length;i++){
                   contents[i] = processDirectoryOLD(fs[i],false); 
                   System.err.println("PROCESSEDDIRECTORY "+i);
              }
              System.err.println("COLLATINGA");
              String output ="";
              for(int i=0;i<contents.length;i++){
                   //System.out.print(contents[i]);
                   System.err.println("COLLATINGINTOONEBIGOUTPUT"+i);
                   output=output+contents[i];
              }
              String outputFilename = "collated"+new Date().getTime()+".txt";
              CustomDialog.saveFile(directory, outputFilename, output);
              Conversation.printWSln("Main","DONE!");
                  
              return;
              }


              //File f  = CustomDialog.loadFile(System.getProperty("user.dir"));
              String directory = System.getProperty("user.dir");
              //String directory = System.getProperty("user.dir")+File.separator+"data"+File.separator+"Saved experimental data";
              File[] fs = CustomDialog.loadDirectories("What are the directories containing the experiments you want to collate?", directory);
              String[] contents = new String[fs.length];
              contents[0] = processDirectorySELFREPAIR(fs[0],true);  
              String headerTemplate = loadHeader(fs[0]);
              
              
              for(int i=1;i<fs.length;i++){
                   contents[i] = processDirectorySELFREPAIR(fs[i],true); 
                   String headerN = loadHeader(fs[i]);
                   if(!headerN.equalsIgnoreCase(headerTemplate)){
                       CustomDialog.showDialog("The two headers are not the same!");
                       return;
                   }
                   
                   System.err.println("PROCESSEDDIRECTORY "+i);
              }
              System.err.println("COLLATINGA");
              String output ="";
              for(int i=0;i<contents.length;i++){
                   //System.out.print(contents[i]);
                   System.err.println("COLLATINGINTOONEBIGOUTPUT"+i);
                   output=output+contents[i];
              }
              String outputFilename = "collated"+new Date().getTime()+".txt";
              CustomDialog.saveFile(directory, outputFilename, output);
               Conversation.printWSln("Main","DONE!");
              
        }
             
        public static String loadHeader(File directoryF){
             File headerFile = new File(directoryF,"turnasattribvals.txt_HEADER.txt");
             String header ="";
             
              String line = null;
             try{
                BufferedReader br = new BufferedReader(new FileReader(headerFile));
                line = br.readLine();
                header = ""+line;
                while (line != null) {
                      line =  br.readLine();
                      header = header + line;
                      
                } 
                br.close();
                
                return header;
         }catch(Exception e){
               e.printStackTrace();
               CustomDialog.showDialog("Could not load header"+ directoryF.getName()+  "for some reason: "+e.getMessage());
               System.exit(-52);
         }
             
             
             return null;
             
             
        }
        
        
        
        
        
        
        public static String processDirectorySELFREPAIR(File directoryFile,boolean includeheader){
             
             System.err.println("PROCESSING:"+directoryFile);
             Conversation.printWSln("Main","PROCESSING: "+directoryFile);
             File turns = new File(directoryFile,"turnasattribvals.txt");
             if(!turns.exists()){
                  CustomDialog.showDialog("There is no turnsattribvals.txt file in: "+directoryFile);
             }
             Vector v = new Vector();
             String line = null;
             try{
                BufferedReader br = new BufferedReader(new FileReader(turns));
                line = br.readLine();
                //if(line==null) throw new Exception();
                //line="Directory"+"|"+line +"\n";
                //line="Directory"+"|"+line +"\n";
                
                line = directoryFile.getName()+ separator +line.replace("\n", "") +"\n";
                
                if(includeheader)v.addElement(line);
                long linecounter =0;
                String previousLINE = ""+line;
                while (line != null) {
                      line = br.readLine();
                      if(line!=null){
                    
                           if(line.contains("data")) {
                               if(line.contains("COMPLETED0")) continue;
                               if(line.contains("COMPLETED1")) continue;
                               if(line.contains("COMPLETED2")) continue;
                               if(line.contains("COMPLETED3")) continue;
                               if(line.contains("COMPLETED4")) continue;
                               if(line.contains("COMPLETED5")) continue;
                               if(line.contains("COMPLETED6")) continue;
                               if(line.contains("COMPLETED7")) continue;
                               if(line.contains("COMPLETED8")) continue;
                               if(line.contains("COMPLETED9")) continue;
                               if(line.contains("INCOMPLETE0")) continue;
                               if(line.contains("INCOMPLETE1")) continue;
                               if(line.contains("INCOMPLETE2")) continue;
                               if(line.contains("INCOMPLETE3")) continue;
                               if(line.contains("INCOMPLETE4")) continue;
                               if(line.contains("INCOMPLETE5")) continue;
                               if(line.contains("INCOMPLETE6")) continue;
                               if(line.contains("INCOMPLETE7")) continue;
                               if(line.contains("INCOMPLETE8")) continue;
                               if(line.contains("INCOMPLETE9")) continue;
                               
                               
                               
                           }
                           if(line.contains("mazenumber:11")&&line.contains("previousmazenumber: 10") &&line.contains("mazetotalnumberofgamemoves:0")&&
                                   line.contains("TimeOfBothOnGoal: -1") &&line.contains("participanttotalnumberofgamemoves:0")&&line.contains("participanttotalnumberofswitchtraversals:0")  ){
                               //System.err.println("SKIP1 ");
                               continue;
                           }
                           if(line.contains("|server|server|")&&line.contains("Congratulations! Next maze in")){
                                continue;
                           }
                           if(line.contains("|server|server|")&&line.contains("Please start")){
                                continue;
                           }
                            if(line.contains("|server|server|")&&line.contains("Out of time. Next maze in 5 secs")){                              
                                continue;
                           }
                           if(line.contains("|server|server|")&&line.contains("Out of time. Next maze in 4 secs")){                              
                                continue;
                           }
                           if(line.contains("|server|server|")&&line.contains("Out of time. Next maze in 3 secs")){   
                                continue;
                           }
                           if(line.contains("|server|server|")&&line.contains("Out of time. Next maze in 2 secs")){      
                                continue;
                           }
                           if(line.contains("|server|server|")&&line.contains("Out of time. Next maze in 1 secs")){      
                                continue;
                           }
                           if(line.contains("|server|server|")&&line.contains("Out of time. Next maze in 0 secs")){      
                                continue;
                           }
                           if(line.contains("interceptedturn") && line.contains("NOTRELAYED")){
                               if(  previousLINE.contains("eeh") ||
                                    previousLINE.contains("eehm")||
                                    previousLINE.contains("eh")||
                                    previousLINE.contains("eh ik bedoel")||
                                    previousLINE.contains("ehm")||
                                    previousLINE.contains("euh")||
                                    previousLINE.contains("euhm")||
                                    previousLINE.contains("ik bedoel")||
                                    previousLINE.contains("of eeh")||
                                    previousLINE.contains("of eehm")|| 
                                    previousLINE.contains("of eh")||
                                    previousLINE.contains("of ehm")|| 
                                    previousLINE.contains("of euh")||
                                    previousLINE.contains("of euhm")||
                                    previousLINE.contains("of uh")||
                                    previousLINE.contains("of uhm")|| 
                                    previousLINE.contains("of uuh")||
                                    previousLINE.contains("of uuhm")|| 
                                    previousLINE.contains("uh")||
                                    previousLINE.contains("uhm")||
                                    previousLINE.contains("uuh")||
                                    previousLINE.contains("uuhm")){
                                         int idx =   previousLINE.indexOf("normalturn");
                                         String previousLINEFIRSTHALF = previousLINE.substring(0, idx);
                                         String subdialogueID = previousLINEFIRSTHALF.substring(idx-2,idx-1);      
                                         if(!(previousLINEFIRSTHALF.charAt(idx-3)=='|')){
                                             System.err.println("EXIT:"+previousLINEFIRSTHALF);
                                             System.exit(-9);
                                         }
                                         
                                         try{
                                              int subdialogueIDint = Integer.parseInt(subdialogueID);
                                              line = line.replace("NOTRELAYED", ""+subdialogueID);
                                              
                                         }
                                         catch (Exception e){
                                              e.printStackTrace();
                                              System.err.println("(1)"+line);
                                              System.err.println("(2)"+previousLINE);
                                              System.err.println("(3)"+previousLINEFIRSTHALF);
                                              System.err.println("CANT:"+subdialogueID);
                                              System.exit(-5);
                                         }
                                         
                               }
                                   
                              
                           }
                           
                           
                          
                            v.addElement( directoryFile.getName()+ separator +line.replace("\n", "") +"\n");
                            
                            previousLINE = line;
                            
                           //}
                      }
                         
                      //System.err.println("LINE_"+directoryFile+line);
                      
                      linecounter++;
                      if(linecounter % 1000 ==0){
                          System.err.println(linecounter);
                      }
                } 
                br.close();
                System.err.println("FINISHED PROCESSING "+directoryFile.getName());
        
         }catch(Exception e){
               e.printStackTrace();
               CustomDialog.showDialog("Could not load "+ directoryFile.getName()+  "for some reason: "+e.getMessage());
               System.exit(-52);
         }
             System.err.println("END1 "+directoryFile.getName());
             String retvalue="";
             
             for(int i=0;i<v.size();i++){
                 if(i % 2000 ==0){
                          System.err.println("ENDB "+directoryFile.getName()+" "+i);
                 
                 }
                 String s = (String)v.elementAt(i);
                 retvalue = retvalue+s;
             }
             
            
              System.err.println("RETURNING:"+directoryFile);
             return retvalue;
        }
        
        
        
        
        
        
        
        
        public static String processDirectoryNormal(File directoryFile,boolean includeheader){
             
             System.err.println("PROCESSING:"+directoryFile);
             Conversation.printWSln("Main","PROCESSING: "+directoryFile);
             File turns = new File(directoryFile,"turnasattribvals.txt");
             if(!turns.exists()){
                  CustomDialog.showDialog("There is no turnsattribvals.txt file in: "+directoryFile);
             }
             Vector v = new Vector();
             String line = null;
             try{
                BufferedReader br = new BufferedReader(new FileReader(turns));
                line = br.readLine();
                if(line==null) throw new Exception();
                line="Directory"+separator+line +"\n";
                if(includeheader)v.addElement(line);
                long linecounter =0;
                while (line != null) {
                      line = br.readLine();
                      if(line!=null){
                           //if(!line.contains("KEYPRESSS")){
                                v.addElement( directoryFile.getName()+ separator +line.replace("\n", "") +"\n");
                           //}
                      }
                         
                      //System.err.println("LINE_"+directoryFile+line);
                      
                      linecounter++;
                      if(linecounter % 1000 ==0){
                          System.err.println(linecounter);
                      }
                } 
                br.close();
                System.err.println("FINISHED PROCESSING "+directoryFile.getName());
        
         }catch(Exception e){
               e.printStackTrace();
               CustomDialog.showDialog("Could not load "+ directoryFile.getName()+  "for some reason: "+e.getMessage());
               System.exit(-52);
         }
             System.err.println("END1 "+directoryFile.getName());
             String retvalue="";
             
             for(int i=0;i<v.size();i++){
                 if(i % 2000 ==0){
                          System.err.println("ENDB "+directoryFile.getName()+" "+i);
                 
                 }
                 String s = (String)v.elementAt(i);
                 retvalue = retvalue+s;
             }
             
            
              System.err.println("RETURNING:"+directoryFile);
             return retvalue;
        }
      
        
        
        
        
        
        
        
        
        
        //THis is the previous version
        /*public CollatingData(){
              //File f  = CustomDialog.loadFile(System.getProperty("user.dir"));
              String directory = System.getProperty("user.dir");
              //String directory = System.getProperty("user.dir")+File.separator+"data"+File.separator+"Saved experimental data";
              File[] fs = CustomDialog.loadDirectories("What are the directories containing the experiments you want to collate?", directory);
              String[] contents = new String[fs.length];
              contents[0] = processDirectory(fs[0],true);  
              for(int i=1;i<fs.length;i++){
                   contents[i] = processDirectory(fs[i],false); 
                   System.err.println("PROCESSEDDIRECTORY "+i);
              }
              System.err.println("COLLATINGA");
              String output ="";
              for(int i=0;i<contents.length;i++){
                   //System.out.print(contents[i]);
                   System.err.println("COLLATINGINTOONEBIGOUTPUT"+i);
                   output=output+contents[i];
              }
              String outputFilename = "collated"+new Date().getTime()+".txt";
              CustomDialog.saveFile(directory, outputFilename, output);
               Conversation.printWSln("Main","DONE!");
              
        }
        
        */
        
        
        
         public static String processDirectoryPREFIXSEARCH(File directoryFile,boolean includeheader, String prefix){
         System.err.println("PROCESSING:"+directoryFile);
             Conversation.printWSln("Main","PROCESSING: "+directoryFile);
             
             File turns = null;
             File[] listF = directoryFile.listFiles();
             for(int i=0;i<listF.length;i++){
                 String s = listF[i].getName();
                 if(s.startsWith("turns")){
                      turns = listF[i];
                      break;
                 }
             }
             
             
             //File turns = new File(directoryFile,"turns.txt");
             
             if(turns==null||!turns.exists()){
                  CustomDialog.showDialog("There is no turnsXYZ.txt file in: "+directoryFile);
             }
             Vector v = new Vector();
             String line = null;
             try{
                 
                 BufferedReader br = new BufferedReader(
		   new InputStreamReader(
                      new FileInputStream(turns), "UTF8"));
		        
                 
                 
                //BufferedReader br = new BufferedReader(new FileReader(turns));
                line = br.readLine();
                if(line==null) throw new Exception();
                line="Directory"+separator+line +"\n";
                if(includeheader)v.addElement(line);
                long linecounter =0;
                while (line != null) {
                      line = br.readLine();
                      if(line!=null){
                           //if(!line.contains("KEYPRESSS")){
                                v.addElement( directoryFile.getName()+ separator + turns.getName() +separator+    line.replace("\n", "") +"\n");
                           //}
                      }
                         
                      //System.err.println("LINE_"+directoryFile+line);
                      
                      linecounter++;
                      if(linecounter % 1000 ==0){
                          System.err.println(linecounter);
                      }
                } 
                br.close();
                System.err.println("FINISHED PROCESSING "+directoryFile.getName());
        
         }catch(Exception e){
               e.printStackTrace();
               CustomDialog.showDialog("Could not load "+ directoryFile.getName()+  "for some reason: "+e.getMessage());
               System.exit(-52);
         }
             System.err.println("END1 "+directoryFile.getName());
             String retvalue="";
             
             for(int i=0;i<v.size();i++){
                 if(i % 2000 ==0){
                          System.err.println("ENDB "+directoryFile.getName()+" "+i);
                 
                 }
                 String s = (String)v.elementAt(i);
                 retvalue = retvalue+s;
             }
             
            
              System.err.println("RETURNING:"+directoryFile);
             return retvalue;
        }
        
        
        
        
        
        
        
        
         public static String processDirectoryNEW(File directoryFile,boolean includeheader){
             
             System.err.println("PROCESSING:"+directoryFile);
             Conversation.printWSln("Main","PROCESSING: "+directoryFile);
             File turns = new File(directoryFile,"turnasattribvals.txt");
             if(!turns.exists()){
                  CustomDialog.showDialog("There is no turnsattribvals.txt file in: "+directoryFile);
             }
             Vector v = new Vector();
             String line = null;
             try{
                 
                 BufferedReader br = new BufferedReader(
		   new InputStreamReader(
                      new FileInputStream(turns), "UTF8"));
		        
                 
                 
                //BufferedReader br = new BufferedReader(new FileReader(turns));
                line = br.readLine();
                if(line==null) throw new Exception();
                line="Directory"+separator+line +"\n";
                if(includeheader)v.addElement(line);
                long linecounter =0;
                while (line != null) {
                      line = br.readLine();
                      if(line!=null){
                           //if(!line.contains("KEYPRESSS")){
                                v.addElement( directoryFile.getName()+ separator +line.replace("\n", "") +"\n");
                           //}
                      }
                         
                      //System.err.println("LINE_"+directoryFile+line);
                      
                      linecounter++;
                      if(linecounter % 1000 ==0){
                          System.err.println(linecounter);
                      }
                } 
                br.close();
                System.err.println("FINISHED PROCESSING "+directoryFile.getName());
        
         }catch(Exception e){
               e.printStackTrace();
               CustomDialog.showDialog("Could not load "+ directoryFile.getName()+  "for some reason: "+e.getMessage());
               System.exit(-52);
         }
             System.err.println("END1 "+directoryFile.getName());
             String retvalue="";
             
             for(int i=0;i<v.size();i++){
                 if(i % 2000 ==0){
                          System.err.println("ENDB "+directoryFile.getName()+" "+i);
                 
                 }
                 String s = (String)v.elementAt(i);
                 retvalue = retvalue+s;
             }
             
            
              System.err.println("RETURNING:"+directoryFile);
             return retvalue;
        }
        
        
        
        public static String processDirectoryOLD(File directoryFile,boolean includeheader){
             
             System.err.println("PROCESSING:"+directoryFile);
             Conversation.printWSln("Main","PROCESSING: "+directoryFile);
             File turns = new File(directoryFile,"turns.txt");
             if(!turns.exists()){
                  CustomDialog.showDialog("There is no turns.txt file in: "+directoryFile);
             }
             Vector v = new Vector();
             String line = null;
             try{
                 
                 BufferedReader br = new BufferedReader(
		   new InputStreamReader(
                      new FileInputStream(turns), "UTF8"));
		        
                 
                 
                //BufferedReader br = new BufferedReader(new FileReader(turns));
                line = br.readLine();
                if(line==null) throw new Exception();
                line="Directory"+separator+line +"\n";
                if(includeheader)v.addElement(line);
                long linecounter =0;
                while (line != null) {
                      line = br.readLine();
                      if(line!=null){
                           //if(!line.contains("KEYPRESSS")){
                                v.addElement( directoryFile.getName()+ separator +line.replace("\n", "") +"\n");
                           //}
                      }
                         
                      //System.err.println("LINE_"+directoryFile+line);
                      
                      linecounter++;
                      if(linecounter % 1000 ==0){
                          System.err.println(linecounter);
                      }
                } 
                br.close();
                System.err.println("FINISHED PROCESSING "+directoryFile.getName());
        
         }catch(Exception e){
               e.printStackTrace();
               CustomDialog.showDialog("Could not load "+ directoryFile.getName()+  "for some reason: "+e.getMessage());
               System.exit(-52);
         }
             System.err.println("END1 "+directoryFile.getName());
             String retvalue="";
             
             for(int i=0;i<v.size();i++){
                 if(i % 2000 ==0){
                          System.err.println("ENDB "+directoryFile.getName()+" "+i);
                 
                 }
                 String s = (String)v.elementAt(i);
                 retvalue = retvalue+s;
             }
             
            
              System.err.println("RETURNING:"+directoryFile);
             return retvalue;
        }
        
        
    
       public static void main(String[] args){
          
          // CollatingData cd = new CollatingData();
       }
}
