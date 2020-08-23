/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.io;

import diet.server.io.IntelligentBufferedImagesSaver.bif;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author LX1C
 */
public class IntelligentBufferedFilesSaver extends Thread{
    
    Vector<bif> filesToSave = new Vector<bif>();

    public IntelligentBufferedFilesSaver() {
        this.start();
    }
    
    
    
    
    
    public void run(){
        while(2<5){
            bif b = this.getNext();
            try{
                

              File outputfile = new File(b.fn);
              
              
              
              
            
 
                 FileInputStream fin = new FileInputStream(b.f);
 
 
                FileOutputStream fout = new FileOutputStream(new File(b.fn));
 
                byte[] byt = new byte[1024];
                int noOfBytes = 0;
 
                System.out.println("Copying file using streams "+ b.fn);
 
                //read bytes from source file and write to destination file
                 while( (noOfBytes = fin.read(byt)) != -1 ) {
                     fout.write(byt, 0, noOfBytes);
                 }
                 System.err.println("File copied!" + b.fn);
 
                  //close the streams
                 fin.close();
                fout.close(); 
 


            }catch(Exception e){
                e.printStackTrace();
            }
            
            
        }
        
    }
    
    public synchronized bif getNext(){
        
         while(filesToSave.size()==0){
             try{
               wait();
             } catch(Exception e){
                 e.printStackTrace();
             }  
         }
         bif retval= filesToSave.elementAt(0);
         filesToSave.remove(retval);
         return retval;
    }
    
    
    public synchronized void add(File f,String fn){
        bif b= new bif();
        b.f=f;
        b.fn=fn;
        filesToSave.add(b);
        notifyAll();
    }
    
    class bif{
        File f;
        String fn;
    }
    
    
    
}
