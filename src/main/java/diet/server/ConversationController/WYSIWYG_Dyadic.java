/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.server.Conversation;

/**
 *
 * @author LX1C
 */
public class WYSIWYG_Dyadic extends DefaultWYSIWYGConversationControllerInterface {

    public WYSIWYG_Dyadic(Conversation c) {
        super(c);
    }

    public WYSIWYG_Dyadic(Conversation c, int numberOfTracks, int durationOfTimeout) {
        super(c, numberOfTracks, durationOfTimeout);
    }
    
    public static boolean showcCONGUI() {
        return true;
    }
}
