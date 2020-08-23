/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.wysiwyg;

import diet.server.ConversationController.DefaultWYSIWYGConversationControllerInterface;
import diet.server.Participant;
import diet.utils.HashtableWithDefaultvalue;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class WYSIWYGMultiTrackManager {

    DefaultWYSIWYGConversationControllerInterface cC;
    boolean toggleAllOnSameTrack = true;
    
    
    public WYSIWYGMultiTrackManager(diet.server.ConversationController.DefaultWYSIWYGConversationControllerInterface cC) {
       this.cC=cC;
    }
    
    public WYSIWYGMultiTrackManager(diet.server.ConversationController.DefaultWYSIWYGConversationControllerInterface cC, boolean setAllOnSameTrack) {
       this.cC=cC;
       this.toggleAllOnSameTrack=setAllOnSameTrack;
    }
    
    public void setAllOnSameTrack(boolean allOnSameTrack){
       this.toggleAllOnSameTrack=allOnSameTrack;
        //System.exit(-56);
    }
    
    
    
    public int getTrackInWhichSendersTextIsDisplayedOnRecepientsScreen(Participant sender, Participant recipient){
        
         if(this.toggleAllOnSameTrack) return 0;
         
         Vector allSenders = cC.pp.getSenders(recipient);
         int index = allSenders.indexOf(sender);
         if(index==-1)index=0;
         index++;
         return index;
    }
    

     
}
