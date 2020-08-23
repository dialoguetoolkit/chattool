/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.ProceduralCommsGRID;

import diet.message.Message;
import diet.message.MessageGridTextStimuliSelectionFromClient;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.Participant;
import diet.utils.HashtableWithDefaultvalue;
import java.awt.Color;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author gj
 */
public class ProceduralCommsTaskController {
    
     DefaultConversationController cC;
     static HashtableWithDefaultvalue hwdv  = new HashtableWithDefaultvalue(0);
    
     public Participant pA;
     public Participant pB;
    
     long mostRecent = new Date().getTime();
     
     ProceduralCommsSetOfMoves pcsm  = new ProceduralCommsSetOfMoves();
     
     
     
     
     
     public TextGridStimuliProcComms pcss ;
     
     
     
     
     
     
     
     
     public ProceduralCommsTaskController(DefaultConversationController cC){
         this.cC=cC;
         this.pcss= new TextGridStimuliProcComms(this)
;     }
    
     public void startGame(Participant a, Participant b){
        //randomizeGRIDS(10,10);
        //cC.c.gridTextStimuli_Initialize(a,     10, 10, pA_texts, pA_innerMostSelection, pA_innerSelection   , 50, 50,  0) ;
     }
     
     public void processTaskMove(Message m, Participant origin){
            if(m instanceof diet.message.MessageGridTextStimuliSelectionFromClient){
                MessageGridTextStimuliSelectionFromClient mgtssfc = (MessageGridTextStimuliSelectionFromClient)m;
                String selectionName = mgtssfc.getName();
                System.err.println("Selection is "+selectionName);
                
            }
           
         
            //randomizeGRIDS(10,10);
            //cC.c.gridTextStimuli_changeTexts(origin, pA_texts, mostRecent);
            
     }
             
          
     
     
    
    
     
     
     
}
