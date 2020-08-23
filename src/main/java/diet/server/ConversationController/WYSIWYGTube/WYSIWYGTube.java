/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController.WYSIWYGTube;

import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.server.ConversationController.WYSIWYG_Dyadic_Artificial_Turn;
import diet.server.Participant;

/**
 *
 * @author LX1C
 */
public interface WYSIWYGTube {
    
    
   //public void (Dyadic_WYSIWYGInterface_Manipulation);
   public void add(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGInsert);
}
