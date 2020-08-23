package diet.server;

import java.util.Vector;


import diet.server.ConversationController.DefaultConversationController;
import diet.server.experimentmanager.EMUI;
import javax.swing.SwingUtilities;

public class ExperimentManager extends Thread {

	String expDataDirectory = "";
	// Conversation c;// = new Conversation("FRAG");
	private Vector vNames = new Vector();
	private Vector vAllActiveNames = new Vector();
	ConnectionListener cl;
	EMUI emui;
	//ExperimentSettings generalSett;

	GroupAssignment ga = new GroupAssignment();
	public Vector allConversations = new Vector();

	public EMUI getEMUI() {
		return emui;
	}

	boolean newExperiment;

	// public Vector activeRunningExperiments = new Vector();
	// public Vector experimentsWaitingForLogins = new Vector();

	public ExperimentManager(EMUI em, int portNo) {
		this.emui = em;
		
		
                cl = new ConnectionListener(this, portNo);    
               // ConnectionListenerWebSocket clws = new ConnectionListenerWebSocket(this,200001);
		
		///cl.start();
		setDefaultParticipantNames();
	}

	

        public void startListeningForIncomingConnectionsFromClients(){
            cl.start();
        }
        
        
	

        public synchronized boolean isParticipantNameAlreadyUsed(String name){
            return ga.isParticipantNameAlreadyUsed(name);
        }


	public synchronized boolean activateParticipant(ParticipantConnection participConnection, String participantID,String username) {
		// Called by participantconnection during verification of login details
		// And then adds itself to the conversation
		boolean succ = this.ga.assignParticipantToConversation(allConversations, participantID, username, participConnection);
		if(!succ)return false;
                this.vAllActiveNames.addElement(username);
                return true;
	}


        public synchronized boolean reactivateParticipant(ParticipantConnection participConnection, Participant p) {
            return ga.reConnectParticipant(participConnection,p);
        }

	private String nextFreeName() {
		for (int i = 0; i < vNames.size(); i++) {
			String s = (String) vNames.elementAt(i);
			if (!vAllActiveNames.contains(s)) {
			    vAllActiveNames.addElement(s);
                            return s;
			}
		}
		return "";
	}

	public synchronized void startParticipantandJoinConversationDEPRECATED(
			ParticipantConnection participC, String email, String username) {

	}

	public void setDefaultParticipantNames() {
		vNames.addElement("Participant1");
		vNames.addElement("Participant2");
		vNames.addElement("Participant3");
		vNames.addElement("Participant4");
		vNames.addElement("Participant5");
		vNames.addElement("Participant6");
		vNames.addElement("Participant7");
		vNames.addElement("Participant8");
		vNames.addElement("Participant9");
		vNames.addElement("Participant10");
                vNames.addElement("Participant11");
                vNames.addElement("Participant12");
                vNames.addElement("Participant13");
                vNames.addElement("Participant14");
                vNames.addElement("Participant15");
                vNames.addElement("Participant16");
                vNames.addElement("Participant17");
                vNames.addElement("Participant18");
                vNames.addElement("Participant19");
                vNames.addElement("Participant21");
                vNames.addElement("Participant22");
                vNames.addElement("Participant23");
                vNames.addElement("Participant24");
                vNames.addElement("Participant25");
                vNames.addElement("Participant26");
                vNames.addElement("Participant27");
                vNames.addElement("Participant28");
                vNames.addElement("Participant29");
                vNames.addElement("Participant30");
                vNames.addElement("Participant31");
                vNames.addElement("Participant32");
	}

        
        public static boolean stringIsOnlyLettersOrNumbers(String s){
            for(int i=0;i<s.length();i++){
                char ch = s.charAt(i);
                if(!Character.isLetterOrDigit(ch))return false;
            }
            return true;
        }
        
        
	public synchronized Object findPrevioususernameOrParticipantFromPreviousLoginsAndCheckParticipantIDISOK(String participantID) {
		//Returns a Participant (reactivating a prior login)
                //          String (suggested name of participant)
                //          Boolean (whether true or false, indicates invalid login)
               
                boolean isStringOK =     stringIsOnlyLettersOrNumbers(participantID); if(!isStringOK) return false;
            
                boolean isParticipantIDOK = ga.isParticipantIDOK(allConversations, participantID); 
                if(!isParticipantIDOK){
                    return false;
                }//else{System.exit(-5);}
                Participant p = ga.findUsernameForParticipantID(participantID);
                if(DefaultConversationController.sett.login_autologin) {
                    //return nextFreeName();
                    return participantID;
                }
                if(p!=null)return p;
                return "";
	}

         //Need to return a Conversation object so that the calling class can start the right number of clients
        public Conversation createAndActivateNewExperiment(String cCName){
            vAllActiveNames.removeAllElements();
		Conversation c = new Conversation(this, cCName);
		c.start();
		allConversations.addElement(c);
                cl.scriptHasBeenStarted();
                 return c;
        }
        
        
        
        
       

	

	public void connectUIWithExperimentManager(Conversation c,ConversationUIManager convUI, boolean telegramInterface) {
            
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    if (emui != null) {
                        if(telegramInterface){
                            emui.displayConversationUITelegram(c, convUI);
                        }
                        else{
			    emui.displayConversationUI(c, convUI);
                        }
		}
                }
            
            }  );
		
	}

	

	public int getMinParticipantIDLength() {
		return DefaultConversationController.sett.login_minimumlength_of_participantID;
	}

        public int getPortNumberListening(){
            if(cl==null)return -9999;
            return cl.getPortNumber();
        }
        
        
}
