************************
Programming the chattool  
************************



The chat tool is designed so that all information from the chat clients passes through a ``diet.server.ConversationController`` object.  The ``ConversationController`` sits in the middle between all the participants. 

All turns, keypresses, GUI events, keyboard events and task-information (e.g. game states in a joint referential task) are sent to the ConversationController object.  

When the ``ConversationController`` object receives a turn  from one of the clients, it automatically relays the turn to the other participants in the conversation.

It also automatically takes care of saving the data (i.e. all keypresses), and ensures, e.g. that the participants receive "is typing" notifications.


Think of the ConversationController object as a switchboard operator that controls the wiring of the switchboard. The sockets of the switchboard (for patching incoming and outgoing connections) are located in the ``diet.server.Conversation.java`` object.  

In 99% of experimental designs, you should only have to modify code in your custom subclass of ```diet.server.ConversationController``` which controls the interaction by calling code in ```diet.server.Conversation```


Workflow when programming the chattool
######################################

The standard approach to creating a new experiment is to:

1. Create a subclass of ``diet.server.ConversationController.DefaultConversationController``, e.g. ``MynewConversationController``

2. Make sure that MyNewConversationController.showCCOnGUI() returns true. (this will display your ConversationController class in the main GUI of the chattool.

3. Check that it runs by (select "Run" in netbeans) and then, in the launcher window of the chattool, select "MyNewConversationController" and press "Demo on local machine: Auto login". This should start everything, including the clients. 

4. Customize the ConversationController object, e.g.

   * modify ``processChatText(...)`` This method specifies what happens when a participant sends a message and it is received by the server. The default behaviour is to simply relay the message to all other participants. This method can be modified to, e.g. selectively block (shadowban) messages, or to transform messages (e.g. replace or add text), or to send entirely artificial turns. There is a large set of methods in ``diet.server.Conversation" for doing this, such as ``diet.server.Conversation.sendArtificialTurn(...)````
   * modify ```participantJoinedConversation(...)``` to specify what happens when a participant logs in (e.g. you could make it so that the servers sends some instructions to the participant, using e.g.  ```diet.server.Conversation.sendInstructionToParticipant(...)```
 
5. Test the setup locally (run as demo) 


6. Once the setup works properly, pilot the experiment on separate computers.
   

The login process of how participants connect to the server:
############################################################

When a ConversationController object has been started and is waiting for a connection from a client, 

When a client connects to the server, 

1. The server requests the participant ID from the participant.
2. This participantID is routed to ``requestParticipantJoinedConversation(String id)`` in the ConversationController object. If the ID is ok, the method returns "true". The default behaviour of the chattool is to accept all IDs - but you can put a whitelist of participant IDs here. You also might want to block participants logging in once the group size reaches a maximum.
3. If the participantID is deemed OK, the server requests the participant username from the client.
4. The client displays a "Please enter your username" . 
5. When the server receives the username from the client, it creates a new ``diet.server.Participant`` object for that Participant. This ``Participant`` object is then passed to the ConversationController in the ``participantJoinedConversation(Participant p)`` method. 
6. The default behaviour of ``participantJoinedConversation(Participant p)`` is to assign participants to groups of size 2. (You can change this value in ``Configuration.defaultGroupSize``) and to ensure that participants who are assigned to the same group see each other's typing notifications. (N.B These two functionalities have to be kept separate to allow "spoof" messages and "spoof" typing notifications). You can see the code that does this in the superclass ``DefaultConversationController. participantJoinedConversation(Participant p)`` ::
   
      participantJoinedConversationButNotAssignedToGroup.add(p);
        if(participantJoinedConversationButNotAssignedToGroup.size()== Configuration.defaultGroupSize){
             pp.createNewSubdialogue(participantJoinedConversationButNotAssignedToGroup);             itnt.addGroupWhoAreMutuallyInformedOfTyping(participantJoinedConversationButNotAssignedToGroup);           
             participantJoinedConversationButNotAssignedToGroup.removeAllElements();
      }
   
7. If you want to send a custom message to the participant when they login, you can edit your ConversationController's ``participantJoinedConversation()`` method::
    
	 public synchronized void participantJoinedConversation(final Participant p) {
        super.participantJoinedConversation(p);  //Keep this in your code - to assign participants to groups and ensure they see each other's typing notification.
		
		cC.sendInstructionToParticipant( p, "Hello and welcome to the experiment!")
         
    }



   
   
   
How to change the properties of chat interfaces 
###############################################

There are two ways of customizing the client interfaces:

The easiest way is to customize  ``DefaultConversationController.participantJoinedConversation(...)`` method so that whenever a participant logs in, the server immediately sends instructions to the client. For example::

   This is code
   so is this code 



You can edit diet.server.Configuration and then recompile the code.

2. You can edit the ``DefaultConversationController.participantJoinedConversation(...)`` method so that whenever a participant logs in, the server immediately sends instructions to the client. There is a whole set of 







   