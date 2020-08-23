/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.Vector;

/**
 *
 * @author LX1C
 */
public class RecoveringOldMessages {
     
    public static void main (String[] s){
           RecoveringOldMessages rom = new RecoveringOldMessages();
           rom.processVector();
     }
    
    
     public void processVector(){
         BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
         File f = new File("messages.dat");
         Vector vOBj = new Vector();
         try{
              ObjectInputStream obj = new ObjectInputStream(new FileInputStream(f));
              boolean loadobj=true;
               while(loadobj){
                  Object o = obj.readObject();
                  System.err.println(o);
               }
             // obj.close();
          
        
         }catch(Exception e){
             e.printStackTrace();
 
         }     
         

     } 

 }
