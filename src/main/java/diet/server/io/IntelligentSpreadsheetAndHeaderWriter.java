/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.io;

import diet.attribval.AttribVal;
import diet.server.ConversationController.DefaultConversationController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class IntelligentSpreadsheetAndHeaderWriter extends Thread{
 
    Vector attribvalsToSave = new Vector();
    public boolean doSaving = true;
    
    File fSPREADSHEET;
    BufferedWriter textOut;
    
    
    Vector vAttributeIDs = new Vector();
    File fHEADER;
    
    
    
    
    public IntelligentSpreadsheetAndHeaderWriter(File f){
        try{
            this.fSPREADSHEET=f; 
            //CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
            //encoder.onMalformedInput(CodingErrorAction.REPORT);
            //encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
            //textOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fSPREADSHEET),encoder));   
            
            byte[] bytesReplacementForMalformedInput = ("█").getBytes();           
            this.textOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fSPREADSHEET,true),Charset.forName("UTF-8").newEncoder().onMalformedInput(CodingErrorAction.REPLACE).replaceWith(bytesReplacementForMalformedInput).onUnmappableCharacter(CodingErrorAction.REPLACE)));
            
            
            
            
          }catch (Exception e){
              e.printStackTrace();
          }
          this.start();
    }
    
    
    
    
    
    public void appendToHeaderFile(String s){
      try{  
        fHEADER  = new File(fSPREADSHEET.getCanonicalPath()+"_HEADER.txt");
        FileWriter fooWriter = new FileWriter(fHEADER, true); // true to append                                                     
        fooWriter.write(s);
        fooWriter.close();
      } catch (Exception e){
          e.printStackTrace();
      }  
    }
    
    
    
    public synchronized void saveAttribVals(Vector additionalData){
         this.attribvalsToSave.addElement(additionalData);
         notifyAll();
    }
    
    
    
    private synchronized Vector getNext(){
       while(this.attribvalsToSave.size()==0){
           try{
              wait();
           }catch (Exception e){
               e.printStackTrace();
           }   
       }
       Vector v = (Vector)attribvalsToSave.elementAt(0);
       attribvalsToSave.remove(v);
       return v;
       
    }
    
    
    public void run(){
        savingLoop();
    }
    
    int counter =0;;
    
    public void savingLoop(){
         while(doSaving){
              Vector v = this.getNext();
             
              try{
                 
                  
                  String spreadsheetROW = processAttribVals(v).replaceAll("\n", diet.server.Configuration.outputfile_newline_replacement_character)+"\n";
                  
                  textOut.append(spreadsheetROW);
                  textOut.flush();
                
                 
                  
                          
              }catch(Exception e){
                  e.printStackTrace();
                 
                  this.reestablishFile();
              }
         }
        
    }
    
    
    private void reestablishFile(){
        boolean wasSuccessfulReestablishing = false;
        while(!wasSuccessfulReestablishing){
             
             try{
                 byte[] bytesReplacementForMalformedInput = ("█").getBytes();           
                 this.textOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fSPREADSHEET,true),Charset.forName("UTF-8").newEncoder().onMalformedInput(CodingErrorAction.REPLACE).replaceWith(bytesReplacementForMalformedInput).onUnmappableCharacter(CodingErrorAction.REPLACE)));
           
                if(fSPREADSHEET.canWrite()) wasSuccessfulReestablishing = true;
             }catch(Exception e){
                 System.err.println("Trying to re-establish file "+fSPREADSHEET.getName());
                 e.printStackTrace();
             }        
        }
        System.err.println("File system was re-established: "+fSPREADSHEET.getName());
       
    }
    
    
    
    
    public String processAttribVals(Vector v){
        
        Vector vAttribValsClone = (Vector)v.clone();
        
        String row = "";
        
        for(int i=0;i<this.vAttributeIDs.size();i++){
            String s = (String)vAttributeIDs.elementAt(i);
            boolean found =false;
            for(int j=0;j <v.size();j++){
                AttribVal av = (AttribVal)v.elementAt(j);
                if(av.getID().equalsIgnoreCase(s)){
                    found = true;
                    vAttribValsClone.remove(av);
                    row = row + av.getValAsString()   + DefaultConversationController.sett.recordeddata_CSVSeparator;
                    break;
                }
            }
            if(!found){
                row = row    + DefaultConversationController.sett.recordeddata_CSVSeparator;
            }      
        }
        
        
        for(int i=0;i<vAttribValsClone.size();i++){
             AttribVal av = (AttribVal)vAttribValsClone.elementAt(i);
             row = row + av.getValAsString()  + DefaultConversationController.sett.recordeddata_CSVSeparator;
             this.vAttributeIDs.addElement(av.id);
             this.appendToHeaderFile(av.id+DefaultConversationController.sett.recordeddata_CSVSeparator);
        }
        
        
        
        
        
        return row;
    }
    
    
    
}
