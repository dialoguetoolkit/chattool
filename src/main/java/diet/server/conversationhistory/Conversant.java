package diet.server.conversationhistory;

import java.io.Serializable;
import java.util.Vector;

import diet.server.conversationhistory.turn.Turn;

/**
 * This is the representation of all the participant. It is 
 * @author user
 */
public class Conversant implements Serializable{
    

    private String username;
    private transient Vector turnsProduced = new Vector();
    private transient Vector turnsReceived = new Vector();
    private transient Vector wordsUsed = new Vector();
    private transient Vector wordsReceived = new Vector();

    public Vector getWordsReceived() {
        return wordsReceived;
    }

    public Vector getWordsUsed() {
        return wordsUsed;
    }
    
    
    
    public Conversant(String username) {
       this.username = username;    
    }
    
    public String getUsername(){
        return username;
    }
    
    
    
    public void addTurnProduced(Turn t){
        turnsProduced.addElement(t);
    }
    public void addTurnReceived(Turn t){
        
        turnsReceived.addElement(t);
        
    }
    

    
     
    

     
    
     public Vector getTurnsProduced(){
         return this.turnsProduced;
     }
     
     public Vector getTurnsReceived(){
         return this.turnsReceived;
     }
    
    
}
