/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.FaceComms;

import diet.attribval.AttribVal;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.FaceComms2016_RAP_TURNBYTURNDyadic;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.utils.HashtableWithDefaultvalue;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author gj
 */
public interface FaceTaskControllerInterface {
    
     
    
   
    
    
    
    public void startTask(Participant pA, Participant pB);
    
    public void processChatText(Participant sender, String text);
    
    public void updateScores(boolean success);
    
    public int getScoreCORRECT(Participant p);
    
     public int getScoreINCORRECT(Participant p);
    
    public Vector getAdditionalValues(Participant p);
     
     


    
    
}
