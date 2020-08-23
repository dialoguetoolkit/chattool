package diet.server.conversationhistory;

import diet.attribval.AttribVal;
import diet.client.ClientInterfaceEvents.ClientInterfaceEvent;
import diet.client.DocumentChange.DocChange;
import diet.message.Keypress;
import java.io.Serializable;
import java.util.Vector;


import diet.server.Conversation;
import diet.server.ConversationUIManager;
import diet.server.conversation.ui.JGroupsConversationHistories;
import diet.server.conversationhistory.turn.ArtificialTurnProducedByServer;
import diet.server.conversationhistory.turn.DataToBeSaved;
import diet.server.conversationhistory.turn.Turn;
import diet.server.conversationhistory.turn.NormalTurnProducedByParticipant;
import diet.server.conversationhistory.turn.NormallTurnProducedByParticipantInterceptedByServer;
import diet.server.io.IntelligentIO;
import diet.utils.VectorHashtable;
import java.util.Date;
//import edu.stanford.nlp.trees.Tree;

/**
 * This and other classes of this package store all the turns from the
 * conversation. Each turn is indexed by its and origin and recipient(s). On
 * saving a turn, all the words are stored in a lexicon, which is also
 * associated with origin/recipients.
 * 
 * <p>
 * The data stored in ConversationHistory abstracts the data from individual
 * messages to Turns. It is designed to function without being associated with a
 * {@link diet.server.Conversation} object, in order to be able to replay saved
 * turns.
 * 
 * 
 * @author user
 */
public class ConversationHistory implements Serializable {

	private Vector turns = new Vector();
	private Vector contiguousTurns = new Vector();
	// PartOfSpeechLexicon plex = new PartOfSpeechLexicon();
	private Vector interventions = new Vector();
	
	
	private Vector conversants = new Vector();
	private ConversationUIManager convUIManager;
	private String conversationName;
	private long startTime = new Date().getTime();
	private boolean setStartTimeOnFirstMessage = true;
	private IntelligentIO convIO;
	private Conversation c;
        
        private VectorHashtable  vGroups = new VectorHashtable();
        
        JGroupsConversationHistories jgch;
	

	public ConversationHistory(Conversation c, String nameOfConversation,
			IntelligentIO cIO) {
		this.convIO = cIO;

		this.conversationName = nameOfConversation;
		this.c = c;
		

	}

	public void setConversationUIManager(ConversationUIManager ci) {
		this.convUIManager = ci;
                
	}

	/**
	 * Sets the start time of the conversation. This is so that the first turn
	 * in the conversation will have a turn onset of 0 millisecs.
	 * 
	 * @param startTime
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTimeOnFirstMessage(boolean setStartFirstMessage) {
		this.setStartTimeOnFirstMessage = setStartFirstMessage;
	}

	



        
       
       

	











       

	/**
	 * 
	 * This saves text from the conversation, associating it with an origin and
	 * recipients, as well as timing data. In addition, this method will append
	 * the single to a contiguous turn (if appropriate) and store the individual
	 * words associated with the turn.
	 * 
	 * <p>
	 * This method when called from the Conversation class must always be called
	 * AFTER sending the message to the participants. This is because the
	 * participant.send() method carries out the timestamping.
	 * 
	 * @param onset
	 * @param enter
	 * @param senderUsername
	 * @param apparentSenderUsername
	 * @param text
	 * @param recipientsNames
	 * @param wasBlocked
	 * @param keyPresses
	 * @param documentUpdates
	 * @param turnNumber
	 */
	public synchronized void saveMessageRelayedToOthers(String dtype,long timeOnClientOfStartTyping, long timeOnClientOfSending, long timeOnServerOfReceipt, String senderID, String senderUsername, String apparentSenderUsername, String text,
			Vector recipientsNames, boolean wasBlocked, Vector keyPresses, Vector<DocChange> documentUpdates, Vector<ClientInterfaceEvent> clientInterfaceEvents, Vector<AttribVal> additionalValues, boolean dummy) {
            
                
        	Conversant sender = getConversantAddingIfNecessary(senderUsername);
		Conversant apparentSender = getConversantAddingIfNecessary(apparentSenderUsername);
		Vector recipients = getConversantsAddingIfNecessary(recipientsNames);

		// ----------------------------------------------------------------------------

		NormalTurnProducedByParticipant tbp = new NormalTurnProducedByParticipant(this, dtype, timeOnClientOfStartTyping,timeOnClientOfSending, timeOnServerOfReceipt, senderID, sender, apparentSender, text,
				recipients, wasBlocked, keyPresses, documentUpdates, clientInterfaceEvents, additionalValues);

                
                this.vGroups.put(dtype, tbp);
                
		sender.addTurnProduced(tbp);
		for (int i = 0; i < recipients.size(); i++) {
			Conversant c2 = (Conversant) conversants.elementAt(i);
			c2.addTurnReceived(tbp);
		}
		turns.addElement(tbp);// Has to be after updating contiguous turns

		if (this.convUIManager != null)
			convUIManager.updateUIWithTurnInfo(tbp);
		this.convIO.saveTurn(tbp);

	}

        
        
        public synchronized void saveInterceptedNonRelayedMessage(String dtype,long timeOnClientOfStartTyping, long timeOnClientOfSending, long timeOnServerOfReceipt, String senderID, String senderUsername, String apparentSenderUsername, String text,
			Vector recipientsNames, boolean wasBlocked, Vector keyPresses, Vector<DocChange> documentUpdates, Vector<ClientInterfaceEvent> clientInterfaceEvents, Vector<AttribVal> additionalValues, boolean dummy) {
            
                
        	Conversant sender = getConversantAddingIfNecessary(senderUsername);
		Conversant apparentSender = getConversantAddingIfNecessary(apparentSenderUsername);
                recipientsNames.addElement("server");
		Vector recipients = getConversantsAddingIfNecessary(recipientsNames);

		// ----------------------------------------------------------------------------

		diet.server.conversationhistory.turn.NormallTurnProducedByParticipantInterceptedByServer tbp = new NormallTurnProducedByParticipantInterceptedByServer(this, dtype, timeOnClientOfStartTyping,timeOnClientOfSending, timeOnServerOfReceipt, senderID, sender, apparentSender, text,
				recipients, wasBlocked, keyPresses, documentUpdates, clientInterfaceEvents, additionalValues);

                this.vGroups.put(dtype, tbp);
              
		sender.addTurnProduced(tbp);
		for (int i = 0; i < recipients.size(); i++) {
			Conversant c2 = (Conversant) conversants.elementAt(i);
			c2.addTurnReceived(tbp);
		}
		turns.addElement(tbp);// Has to be after updating contiguous turns
		if (this.convUIManager != null)
			convUIManager.updateUIWithTurnInfo(tbp);
		this.convIO.saveTurn(tbp);

	}

        
	

        
        
        public synchronized void saveArtificialMessageCreatedByServer(String subdialogue, long timeOnServerOfSending , String apparentSenderUsername, String text,
			Vector recipientsNames, Vector<AttribVal> additionalValues, boolean dumfmy) {
            
                //mct.getStartOfTypingOnClient(),  mct.getTimeOfSending().getTime(),mct.getTimeOfReceipt().getTime()
            
        	Conversant sender = getConversantAddingIfNecessary("server");
		Conversant apparentSender = getConversantAddingIfNecessary(apparentSenderUsername);
		Vector recipients = getConversantsAddingIfNecessary(recipientsNames);
		int recipConversantsSize = recipients.size();

		
		

		// ----------------------------------------------------------------------------

		ArtificialTurnProducedByServer   atpbs = new ArtificialTurnProducedByServer(this, subdialogue,  timeOnServerOfSending, sender, "server",  apparentSender, text,
                recipients, additionalValues);
                
              
		sender.addTurnProduced(atpbs);
                
                this.vGroups.put(subdialogue, atpbs);
                
		for (int i = 0; i < recipients.size(); i++) {
			Conversant c2 = (Conversant) conversants.elementAt(i);
			c2.addTurnReceived(atpbs);
		}
		turns.addElement(atpbs);// Has to be after updating contiguous turns
		if (this.convUIManager != null)
			convUIManager.updateUIWithTurnInfo(atpbs);
		this.convIO.saveTurn(atpbs);

	}
        
        
        
            
        
        
        
        public synchronized void saveDataAsRowInSpreadsheetOfTurns(String subdialogueID, String dataType,       long timeOnClientOfStartTyping,  long timeOnClientOfSending, long timeOnServerOfReceipt, 
                                                                   String senderID,      String senderUsername, String apparentSenderUsername,   String text,                Vector recipientsNames, 
                                                                   boolean wasBlocked,   Vector<Keypress> keyPresses,     Vector<DocChange> documentUpdates, Vector<ClientInterfaceEvent> clientinterfaceEvents         ,Vector<AttribVal> additionalValues,     boolean dummgy) {
            
                      
            
		// ----------------------------------------------------------------------------

                Conversant sender = getConversantAddingIfNecessary("server");
		Conversant apparentSender = getConversantAddingIfNecessary(apparentSenderUsername);
		Vector recipients = getConversantsAddingIfNecessary(recipientsNames);
            
                
                
                //System.err.println("SL03");
                Turn t = new DataToBeSaved(this, subdialogueID, dataType, timeOnClientOfStartTyping,timeOnClientOfSending, timeOnServerOfReceipt, senderID, senderUsername, apparentSenderUsername, text,
				recipients, wasBlocked, keyPresses, documentUpdates,clientinterfaceEvents , additionalValues);

                this.vGroups.put(subdialogueID, t);
                
                //System.err.println("SL04");
                
                if (this.convUIManager != null)
			convUIManager.updateUIWithTurnInfo(t);
                
                this.convIO.saveTurn(t);
                
	}
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
       
        
	

	
	private Vector getConversantsAddingIfNecessary(Vector usernames) {
		Vector v = new Vector();
		for (int i = 0; i < usernames.size(); i++) {
			String username = (String) usernames.elementAt(i);
			v.addElement(getConversantAddingIfNecessary(username));
		}
		return v;
	}

	/**
	 * Searches for conversant. If conversant not found, creates and returns a
	 * new Conversant with specified username
	 * 
	 * @param username
	 *            username to search for
	 * @return conversant with username
	 */
	private Conversant getConversantAddingIfNecessary(String username) {
		for (int i = 0; i < conversants.size(); i++) {
			Conversant conv = (Conversant) conversants.elementAt(i);
			if (conv.getUsername().equalsIgnoreCase(username)) {
				return conv;
			}
		}
		System.out.println("Adding new conversant with username " + username);
		Conversant c2 = new Conversant(username);
		conversants.addElement(c2);

		return c2;
	}

	

	

	

	/**
	 * Returns all the turns of the conversation
	 * 
	 * @return Vector containing all turns of conversation
	 */
	public Vector getTurns() {
		return this.turns;
	}

	public Vector getContiguousTurns() {
		return contiguousTurns;
	}

	public Vector getConversants() {
		return conversants;
	}

	public String getConversationName() {
		return this.conversationName;
	}

	private Conversant getConversant(String s) {
		for (int i = 0; i < conversants.size(); i++) {
			Conversant c2 = (Conversant) conversants.elementAt(i);
			if (c2.getUsername().equals(s))
				return c2;
		}
		return null;
	}

	

	

	
	

	

	

	

	
	
	

	

	public void closeDown() {
		try {
			turns = null;
			contiguousTurns = null;
			
		} catch (Exception e) {

		}
	}

	







        

        public Conversation getConversation(){
            return c;
        }




}
