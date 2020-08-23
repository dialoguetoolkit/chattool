package diet.server.ConversationController;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.Participant;




public abstract class CCCONFIDENCE extends DefaultConversationController{

    public CCCONFIDENCE(Conversation c) {
        super(c);
    }
   
    
    
    
    public String getDescriptionForP(Participant p){
        return "DYADIC";
    }
    
     public static boolean showcCONGUI() {
        return false;
    }
}
