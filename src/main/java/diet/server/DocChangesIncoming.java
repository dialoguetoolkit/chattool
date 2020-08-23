/*
 * DocChangesIncoming.java
 *
 * Created on 24 October 2007, 20:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server;

import java.io.Serializable;
import java.util.Hashtable;


/**
 * The collection of DocChanges associated with the turn currently being constructed by each participant.
 * As this must also cope with WYSIWYG interfaces, this class assumes that the turn boundaries will be
 * determined by the ConversationController in conjunction with {@link Conversation}, e.g. the 
 * default operation demarcates a turn boundary whenever a client presses ("ENTER"/"RETURN"/"SEND")
 * 
 * @author user
 */
public class DocChangesIncoming implements Serializable{
    
    private Conversation c;
    private Hashtable turnsBeingConstructed = new Hashtable();
    
    /**
     * Creates a new instance of DocChangesIncoming
     */
    public DocChangesIncoming(Conversation c) {
       this.c = c;
    }
         
    /**
     * Returns all the inserts/removes of the most recent turn currently being constructed.
     * @param p Participant 
     * @return null | Vector containing all the inserts/removes of the current turn under construction
     */
    public DocChangesIncomingSequenceFIFO getTurnBeingConstructed(Participant p){
        try{
          return (DocChangesIncomingSequenceFIFO)turnsBeingConstructed.get(p);
        }catch (Exception e){
            return null;
        }   
    }
    
    /**
     * Adds an insert to the Vector of inserts/removes associated with a participant
     * @param p Participant
     * @param txt text that is inserted
     * @param offsetFrmRight offset of insert
     * @param timestamp timestamp of insert
     */
    public synchronized void addInsert(Participant p, String txt,int offsetFrmRight, long timestamp){
        System.out.println(p.getUsername());
         if (!turnsBeingConstructed.containsKey(p))createNewTurn(p,timestamp);
        ((DocChangesIncomingSequenceFIFO)turnsBeingConstructed.get(p)).insert(txt,offsetFrmRight,timestamp);
    }
    
    /**
     * Adds a remove DocChange to the Vector of inserts/removes associated with a participant
     * @param p Participant
     * @param offsetFrmRight offset of the update 
     * @param length length (in characters) of update
     * @param timestamp time (millisecs) of formulation
     */
    public synchronized void addRemove(Participant p, int offsetFrmRight, int length, long timestamp){
        if (!turnsBeingConstructed.containsKey(p))createNewTurn(p,timestamp);
        ((DocChangesIncomingSequenceFIFO)turnsBeingConstructed.get(p)).remove(offsetFrmRight,length,timestamp);
        
    }
    /**
     * This is invoked when the ConversationController determines that the client has finished typing a turn.
     * It clears the turn being constructed, returning it.
     * 
     * 
     * @param p Participant who has finished typing a turn
     * @param timestamp timestamp of end of old turn
     * @return complete turn former turn under construction
     */
    public synchronized DocChangesIncomingSequenceFIFO setNewTurnAndReturnPreviousTurn(Participant p,long timestamp){
        if (!turnsBeingConstructed.containsKey(p))createNewTurn(p,timestamp);
         DocChangesIncomingSequenceFIFO returnValue = ((DocChangesIncomingSequenceFIFO)turnsBeingConstructed.get(p));
         createNewTurn(p,timestamp);
         return returnValue;
    }
    
    /**
     * Clears the current turn being constructed for a participant, creating a new empty one
     * @param p Participant
     * @param timestamp timestamp of end of old turn
     */
    private void createNewTurn(Participant p, long timestamp){
        turnsBeingConstructed.remove(p);
        DocChangesIncomingSequenceFIFO ds = new DocChangesIncomingSequenceFIFO(timestamp);
        turnsBeingConstructed.put(p,ds);        
    }
    
    
}
