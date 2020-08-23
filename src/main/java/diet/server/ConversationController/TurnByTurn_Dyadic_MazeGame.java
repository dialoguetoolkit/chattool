/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.message.MessageChatTextFromClient;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.Participant;
import diet.task.mazegame.MazeGameController2WAY;
import diet.textmanipulationmodules.CyclicRandomTextGenerators.CyclicRandomDutchSelfRepairInitiation;
import diet.textmanipulationmodules.Selfrepairgeneration.SelfRepair;
import diet.utils.HashtableOfLong;
import static java.awt.SystemColor.info;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class TurnByTurn_Dyadic_MazeGame extends MazeGameConversationControllerMultipleDyads {

    //CyclicRandomDutchSelfRepairInitiation crdsr = new CyclicRandomDutchSelfRepairInitiation();
    //SelfRepair sr = new SelfRepair();
    // HashtableOfLong htol = new HashtableOfLong(0);
    
    
    public TurnByTurn_Dyadic_MazeGame(Conversation c) {
        super(c);
        this.setID( "MAZEGAME_BASELINE");
    }

    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
      super.processChatText(sender, mct);
      
                
                
    }

    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
        this.updateInfoAsItIsTyped(sender, mWYSIWYGkp.getAllTextInWindow(),false);
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
         this.updateInfoAsItIsTyped(sender, mWYSIWYGkp.getAllTextInWindow(),true);
    }
     
    
    
    
    public synchronized void updateInfoAsItIsTyped(Participant sender, String textInBoxOLD, boolean updateISREMOVE){
           try{
              //textInBox = "PRESET"+textInBoxOLD.replaceAll("\n", "").replace("\n", "").replaceAll("\r\n", "").replaceAll("\r", "")+"ENDSET";
               
               
           }catch(Exception e){e.printStackTrace();}
           
          
        
          
    }    
    
      public static boolean showcCONGUI(){
        return true;
    } 
    
    
}
