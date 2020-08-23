/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class IntelligentObjectFileWriter extends Thread{
 
    Vector objects = new Vector();
    public boolean doSaving = true;
    
    
    long objectWritesSinceLastReset =0;
    long objectWritesThreshold = 30;
    
    File f;
    ObjectOutputStream objectsSerializedOut;
    
    
    public IntelligentObjectFileWriter(File f){
        try{
             this.f=f;
             f.createNewFile();    
             FileOutputStream objectsOut = new FileOutputStream(f);
             objectsSerializedOut =  new ObjectOutputStream(objectsOut);
           
             
          }catch (Exception e){
              e.printStackTrace();
          }
          this.start();
    }
    
    
    public synchronized void saveObject(Object o){
         objects.addElement(o);
         notifyAll();
    }
    
    
    
    private synchronized Object getNext(){
       while(this.objects.size()==0){
           try{
              wait();
           }catch (Exception e){
               e.printStackTrace();
           }   
       }
       Object o = objects.elementAt(0);
       objects.remove(o);
       return o;
       
    }
    
    
    public void run(){
        savingLoop();
    }
    
    public void savingLoop(){
         while(doSaving){
              Object o = this.getNext();
             
              try{
                 objectsSerializedOut.writeObject(o);
                 objectsSerializedOut.flush();
                 objectWritesSinceLastReset++;
                 if(objectWritesSinceLastReset>this.objectWritesThreshold){
       
                     this.objectsSerializedOut.reset();
                     this.objectWritesSinceLastReset=0;
                 }    
                    
                          
              }catch(Exception e){
                  this.reestablishFile();
              }
         }
        
    }
    
    
    private void reestablishFile(){
        boolean wasSuccessfulReestablishing = false;
        while(!wasSuccessfulReestablishing){    
             try{
                if(!f.exists())f.createNewFile();    
                FileOutputStream objectsOut = new FileOutputStream(f);
                objectsSerializedOut =  new ObjectOutputStream(objectsOut);
                if(f.canWrite()) wasSuccessfulReestablishing = true;
             }catch(Exception e){
                 System.err.println("Trying to re-establish file "+f.getName());
                 e.printStackTrace();
             }        
        }
        System.err.println("File system was re-established: "+f.getName());
       
    }
    
    
}
