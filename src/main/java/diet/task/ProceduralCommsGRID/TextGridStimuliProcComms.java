/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.ProceduralCommsGRID;

import diet.server.Participant;
import java.awt.Color;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class TextGridStimuliProcComms {
    
    
    
     Participant p1;
     String[][] p1_texts;
     Color[][] p1_innerSelection;
     Color[][] p1_innerMostSelection;
     
     Participant p2;
     String[][]p2_texts;
     Color[][] p2_innerSelection;
     Color[][] p2_innerMostSelection;
     
    ProceduralCommsTaskController pctc;
    
    public TextGridStimuliProcComms(ProceduralCommsTaskController pctc){
        this.pctc=pctc;
        
    }
    
    
    Random r =new Random();
    public void randomizeGRIDS(int x, int y){
        p1_texts = new String[x][y]; 
        p1_innerSelection = new Color[x][y]; 
        p1_innerMostSelection = new Color[x][y];
        for(int i=0;i<x;i++){
              for(int j=0;j<y;j++){
                 p1_innerSelection[i][j]= new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)); //Color.red;
                 p1_innerMostSelection[i][j] =new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
                 p1_texts[i][j] = ""+r.nextInt(60);
              }   
         } 
    }
    
    
    public void initializeScreensNewSet(Participant p1, Participant p2, String names){
        
    }
    
    public void selectWordOnScreen(Participant p, String word){
        
    }
    
}
