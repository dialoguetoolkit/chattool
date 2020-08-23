/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import javax.imageio.ImageIO;

/**
 *
 * @author LX1C
 */
public class IntelligentBufferedImagesSaver extends Thread{
    
    Vector<bif> filesToSave = new Vector<bif>();

    public IntelligentBufferedImagesSaver() {
        this.start();
    }
    
    
    
    
    
    public void run(){
        while(2<5){
            bif b = this.getNext();
            try{
                

              File outputfile = new File(b.fn);
              ImageIO.write(b.bi, "png", outputfile);


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
    
    
    public synchronized void add(BufferedImage bi,String fn){
        bif b= new bif();
        b.bi=bi;
        b.fn=fn;
        filesToSave.add(b);
        notifyAll();
    }
    
    class bif{
        BufferedImage bi;
        String fn;
    }
    
    
    
}
