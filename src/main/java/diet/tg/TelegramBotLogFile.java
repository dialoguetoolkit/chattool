/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.tg;

import diet.server.Configuration;
import diet.server.Conversation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class TelegramBotLogFile extends Thread{
 
    Vector strings = new Vector();
    public boolean doSaving = true;
    
    File f;
    BufferedWriter textOut;
    
    
    
    
    public TelegramBotLogFile(){
        try{
            String s  = System.getProperty("user.dir") + File.separator + "tg" + File.separator + "telegrambotio.txt";
            File f = new File(s);
            
            this.f=f; 
            //CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
            //encoder.onMalformedInput(CodingErrorAction.REPORT);
            //encoder.onUnmappableCharacter(CodingErrorAction.REPORT);

            //textOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true),encoder));
           
             byte[] bytesReplacementForMalformedInput = Configuration.outputfile_unsupported_character.getBytes();              
             this.textOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true),Charset.forName("UTF-8").newEncoder().onMalformedInput(CodingErrorAction.REPLACE).replaceWith(bytesReplacementForMalformedInput).onUnmappableCharacter(CodingErrorAction.REPLACE)));
           
            
             
          }catch (Exception e){
              e.printStackTrace();
              Conversation.saveErr(e);

          }
          this.start();
    }
    
    
    public synchronized void saveFrom(String s){
        String line = "" +new Date().getTime() + "|" + "FROM"+ "|"+ s;
        this.appendText(line+"\n");
    }
    
    public synchronized void saveTo(String s){
        String line = "" +new Date().getTime() + "|" + "TO"+ "|"+ s;
        this.appendText(line+"\n");
    }
    
    
    public synchronized void appendText(String s){
         strings.addElement(s);
         notifyAll();
    }
    
    
    
    private synchronized String getNext(){
       while(this.strings.size()==0){
           try{
              wait();
           }catch (Exception e){
               e.printStackTrace();
           }   
       }
       String s = (String)strings.elementAt(0);
       strings.remove(s);
       return s;
       
    }
    
    
    public void run(){
        savingLoop();
    }
    
    int counter =0;;
    
    public void savingLoop(){
         while(doSaving){
              String s = this.getNext();
             
              try{
                  textOut.append(s);
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
                 byte[] bytesReplacementForMalformedInput = Configuration.outputfile_unsupported_character.getBytes();               
                 this.textOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true),Charset.forName("UTF-8").newEncoder().onMalformedInput(CodingErrorAction.REPLACE).replaceWith(bytesReplacementForMalformedInput).onUnmappableCharacter(CodingErrorAction.REPLACE)));
           
                if(f.canWrite()) wasSuccessfulReestablishing = true;
             }catch(Exception e){
                 System.err.println("Trying to re-establish file "+f.getName());
                 e.printStackTrace();
                 Conversation.saveErr(e);

             }        
        }
        System.err.println("File system was re-established: "+f.getName());
       
    }
    
    
    
    public static void main(String[] args){
      //  IntelligentTextFileWriter itfw  = new IntelligentTextFileWriter(new File("debugoutput.txt"));
        
        
    }
    
     //static int debugCounter =0;
     //static IntelligentTextFileWriter itfw  = new IntelligentTextFileWriter(new File("debugoutput.txt"));
    
    
    
}
