/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class IntelligentThrowableWriter extends Thread{
 
    Vector throwables = new Vector();
    public boolean doSaving = true;
    
    File f;
    PrintWriter pwErrorfos;
    
    public IntelligentThrowableWriter(File f){
        try{
            this.f=f;  
            f.createNewFile();
            FileOutputStream fosErrorLog = new FileOutputStream(f,true);
            pwErrorfos = new PrintWriter(fosErrorLog);
           
             
          }catch (Exception e){
              e.printStackTrace();
          }
          this.start();
    }
    
    
    public synchronized void saveThrowable(Throwable t){
         throwables.addElement(t);
         notifyAll();
    }
    
    
    
    private synchronized Throwable getNext(){
       while(this.throwables.size()==0){
           try{
              wait();
           }catch (Exception e){
               e.printStackTrace();
           }   
       }
       Throwable t = (Throwable)throwables.elementAt(0);
       throwables.remove(t);
       return t;
       
    }
    
    
    public void run(){
        savingLoop();
    }
    
    public void savingLoop(){
         while(doSaving){
              Throwable t = this.getNext();
              try{
                 t.printStackTrace(pwErrorfos);
                 pwErrorfos.flush();
                  
                          
              }catch(Exception e){
                  this.reestablishFile();
              }
         }
        
    }
    
    
    private void reestablishFile(){
        boolean wasSuccessfulReestablishing = false;
        while(!wasSuccessfulReestablishing){
             
             try{
                FileOutputStream fosErrorLog = new FileOutputStream(f,true);
                pwErrorfos = new PrintWriter(fosErrorLog);
                if(f.canWrite()) wasSuccessfulReestablishing = true;
             }catch(Exception e){
                 System.err.println("Trying to re-establish file "+f.getName());
                 e.printStackTrace();
             }        
        }
        System.err.println("File system was re-established: "+f.getName());
       
    }
    
    
}
