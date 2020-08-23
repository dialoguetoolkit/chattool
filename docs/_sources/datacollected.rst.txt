******************************
Data collected by the chattool  
******************************

All data collected by the chattool is stored in a subfolder of /data/saved experimental data.

The folder name is has a prefix that increments for each new experiment. The second part of the folder name is the name of the ConversationController object.

All `` .obj`` files contain serialized java objects. They can be deserialized
All ``.txt`` and ``.csv`` files are coded in UTF8, use "¦" as a separator, and do not use a special string delimiter.
 


Data collected by the turn-by-turn interface
############################################

The chat interface consists of three main components. The top part shows a conversation history window. The lower half contains a turn-formulation window. In between the two is a status bar that can display status messages, typically "Participant X is typing".

The chat-tool detects records any changes of these three componenents, and sends a record of them to the server. Crucially, some of these changes of these three components are due to the individual participant using the client, whereas others 


In order to capture all important data about what participants are doing (and what information they are presented with) while interacting with each other, each chat client captures three types of data.


Keypress data
*************

The moment a physical key is pressed. This includes "special" keys, e.g. "delete", "shift", etc. When a physical key is pressed (even before it is released), the client sends a ``diet.Message.MessageKeypressed`` message to the server, where it is routed to ```diet.server.ConversationController.DefaultConversatioController.processKeyPressed(...)```. Ordinarily ```processKeypressed()``` then informs the other participants that the participant who pressed a key "is typing".  This information is saved in a dedicated column in ``turns.txt`` and also in ``turnattribvals.txt`` (See below). Any keypress that is detected while the main JFrame chatwindow is in focus will be sent to the server.




Turn-formulation window data
****************************


At a slightly higher level of abstraction than keypresses, the chattool records any editing in the turn-formulation window, while the participant is typing a turn. The chattool records for each edit, the position of the string where the editing was done, whether it was an insert/replace/delete, and where relevant what the new character inserted was. This information is sent to the server in a ``diet.Message.MessageWYSIWYGDocumentSyncFromClientInsert`` message, and is then routed to the ``diet.server.ConversationController.DefaultConversationController.processWYSIWYGTextInserted(..)`` method. This information is typically identical to the information from keypresses (See above). However, in some cases, e.g. when a participant deletes text by selecting, say 20 characters and pressing delete - this will be recorded as a single keypress delete, but as 20 turn-formulation window deletes. N.B. To prevent participants from deleting text by selecting it, this can be disabled by invoking ``diet.server.changeClientInterface_DisbleDeletes( ..)`` in your conversationcontroller object when a participant logs in.

Also, the turn-formulation window records window-specific events - e.g. when text-entry is disabled/enabled. This functionality is useful, e.g. to prevent the user from typing while attending to a stimulus.  See, e.g. ``Conversation.changeClientInterface_disableTextEntry(..)``

This information is saved to ``clientinterfacevents.txt`` in the experiment directory (See section below)
 


Conversation history window
***************************

The top part of the chat window displays the conversation history. In normal operation, the client appends turns to this chat window. Turns can be of three types:

* When the client receives *a turn* that was produced by another participant, it sends a ```diet.Message.MessageClientInterfaceEvent``` to the server containing the text and timestamp when the turn was displayed.
  
* When the client receives *text that was produced by the server*, e.g. game instructions, in the conversation history window. When this happens, the client sends a ```diet.Message.MessageClientInterfaceEvent``` to the server containing the text and timestamp when the turn was displayed.
  
* When the participant has typed a turn privately in the turn formulation window and presses ENTER to send it to the other participants, the text in the turnformulation window is cleared, and the turn is appended to the conversation history.   

In much the same way that it is possible to disable/enable textentry, the script can be programmed to remotely enable/disable the conversationhistory window. This is useful if you don't want participants to pay attention to their immediately preceding conversation. See the method ``diet.server.changeClientInterface_enableConversationHistory()``

Everything that is displayed in the conversation history window is saved to ``clientinterfacevents.txt`` in the experiment directory (See section below). it is also saved to "turns.txt" and "turnsattribvals" in the column **TextAsformulatedTIMING**. 
   

Status messages
***************

There is a status bar that separates the turn formulation window and the conversation history window. This status can display a single line of text. Typically the message that is displayed is a typing status message, e.g. "Participant X is typing". These status messages are saved to `clientinterfacevents.txt`` in the experiment directory (See section below). it is also saved to "turns.txt" and "turnsattribvals" in the column **TextAsformulatedTIMING**. 

   
   
   
Data collected by the WYSIWYG Interface
#######################################

See the usermanual for a detailed description of the data collected by the WYSIWYG Interface 




The experiment folder
#####################


clientinterfaceevents.txt:
**************************

    This is a UTF8 CSV file with "¦" as separator. The headers of this datafile are stored in a separate file ``clientinterfaceevents.txt_HEADER.txt``. This contains a list of clientinterfaceevents received from all clients. Not all of the columns are relevant for all intervention types. Currently, they are:
	
	**SenderID**  The Participant ID of the sender using the client.
	
	**username**  The username of the participant using the client.
	
	**eventtype** A short string describing the event type.  See ``diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker`` for a list of events that are recorded. For example
	
	   * tefi  = Text Entry Field Insert String 
	   * tefr  = Text Entry Field Remove String 
	   * chwappbyo = Chat window appended by other participant
	   * changestatuslabel = status label changed (e.g. "Participant X is typing")
	   * + other types of event.
	   
	**debugcounter** A parameter/counter that was useful for debugging. It counts the number of events received.

	**offset** This measure is only relevant for turn formulation. It records the position in the string where any edit was made. 
	
	**text** This measure is only relevant for turn formulation. It records the new character(s) added to the turn being formulated.
	
	***finalstring** This measure is only relevant for turn formulation. It shows what the contents are of the turnformulation window after the edit.
	
	**length** This measure is only relevant for turn formulation. When a participant deletes a substring of their turn, it records the length of that substring.
	
	**serverid**  When the server sends an instruction to the clients to display something on the interface (e.g. "is typing" / "is not typing" or a participant's turn), it assigns a unique ID to each message that is sent to the client. Currently this is an integer that is incremented with each message. **it** is suffixed if it is an "is typing" message. **nt** is suffixed if it is an instruction to stop showing the "is typing" message. So, for example:
	
	   * 4it  = 4th message from server, instructing client to display "Participant X is typing"
	   * 5it  = 5th message from server, instructing client to display "Participant X is not typing"
	   * 6   =  6th message from server instructing client to display text in the conversation history window (either a turn from another client or an instruction from the server)
	
	**windownumber** The Turn By Turn interface has a variant which gives each participant multiple windows. These windows can be used variously to display separate conversation history windows for each conversational partner (in multiparty interaction) or to display dynamically the contents of other participants turn-formulation windows. See, e.g. the ``diet.server.ConversationController`` template  ``Dyadic_SingleOrSplitScreenInterface`` which can be started from the main GUI.
	
	
	
clientinterfaceevents.txt_HEADER.txt   
************************************

This contains the headers for ``clientinterfaceevents.txt`` (see above).
It might seem strange separating the headers like this! The reason for this departure from convention is to make it possible to have an openended set of columns that can be determined at run-time.


clientinterfaceeventsserialized.obj    
***********************************
This contains a list of all the MessageClientInterfaceEvent``` objects that were received from the clients. The messages are serialized using javas serialization operations.

N.B. It is highly advisable to make sure that for each experiment you run, you save a version of the jar file with the experiment data. The reason is that when deserializing objects from this file, it is essential that the class definitions in the file are absolutely identical to the class definitions of the code doing the deserialization.


debugging*
**********

There are a few files with "debugging" or "debug" in the filename. You can ignore them. They contain text that can be useful for debugging how messages pass through the chattool. 


messages.obj
************

This contains all messages that were sent and received by the server. This file contains all communication from- and to- the clients. 

N.B. It is highly advisable to make sure that for each experiment you run, you save a version of the jar file with the experiment data. The reason is that when deserializing objects from this file, it is essential that the class definitions in the file are absolutely identical to the class definitions of the code doing the deserialization.

textentrykeypresses.txt
***********************

This contains a list of all the keypresses by the clients as they formulate their turns.. Note that these are not necessarily the same as the clientinterfaceevents. 

For example, if a participant types an uppercase character by pressing and holding SHIFT before typing the character, this is two keypresses, but only one edit of the turnformulation window. The columns from right to left are:

* The name of ConversationController object 
* The name of the subdialogue (see ``diet.server.ParticipantPartnering.Subdialogue``)
* The Participant ID
* The Username of the participant
* The keycode of the keypress (see java documentation - this is a unique integer)
* The time when the message was sent by the client
* The time when the message was received by the server
* The contents of the text entry window *before* the keypress


turnsattribvals.txt 
*******************

This contains the same information as turns.txt. (The only difference between turns.txt and turnsattribvals is that the code for saving ``turnsattribvals`` has an intermediate step that involves representing the information as attribute-value pairs. In a future update these will be saved in a JSON file)

Important: The “turn headers” (i.e. the names of each column) are stored in a separate file called “turnsasattribvals.txt_HEADER.txt”.

The columns are


* **ExperimentID** An identifier of the type of experiment (This is automatically generated by the ConversationController object)

* **ServerTimestampOfSavingToFile** This is the timestamp, recorded on the server, when the row of data was saved to the CSV file

* **SubdialogueID** This identifies the "subdialogue" in the interaction. This is only relevant for experiments which involve multiparty interactions where multiple groups speak with each other simultaneously. Each group is assigned a separate subdialogueID.

* **Turntype** This identifies what kind of data is stored in that particular role in the CSV file. Turns produced by participants are saved as "NormalTurn". There are other types of data, e.g. "servermessage" - which are messages that were sent to the clients from the server.

* **SenderID** This is the Participant ID of the Participant who sent the message.

* **SenderUsername** This is the username of the Participant.

* **ApparentSender** This is who the participant appears to be to the recipient of the message. This is only relevant for turns that are spoofed. For example, if Participant C receives a message that was created by Participant A, but appears to be sent from Participant B. (This shows who the recipient thinks sent the message)

* **Text** This is the text that was sent.

* **Recipient(s)** The participant(s) that received the message.

* **NoOfDocumentDeletes** This is the number of characters that were deleted in the text formulation window. Usually this is the same as NoOfKeypressDeletes (below) - but some people select a large chunk of text and delete or replace it with other text - this captures these deletions.

* **NoOfKeypressDeletes** This is the number of times the participant pressed the physical Delete key on the keyboard while formulating the turn.

* **ClientTimestampONSET** This is the time (in msecs) of the first keypress, recorded on each client.

* **ClientTimestampENTER** This is the time (in msecs), also recorded on the client, when the participant pressed ENTER and sent the message.


 
* **ServerTimestampOfReceiptAndOrSending** This is the time (in msecs), recorded on the server, when the message from the client was received on the server.

* | **TextAsformulatedTIMING** This shows, character by character, how a turn was produced.  In order to display this information a simple notation is used that is both human-readable and can also be easily parsed by a computer script. Each keypress is prefixed with superscript representing the time that has elapsed since the previous keypress. Backspace keyspresses are represented with a left-pointing arrow. It also records whether the participant produced their message while the other was typing or not. This notation is explained below:
  |  
  | Suppose a participant types and sends “Hello” , this could yield:
  |
  | H ¹¹²e  ⁸⁹l ¹⁸²l ³⁴⁸o  ⁶⁸²¹ENTER
  |
  | This shows that “e” was typed 112 ms after the “H”. the first “l”was typed 89ms after the “e”. The second “l” was typed 182ms after the first “l”, the “o” was typed 348 msec after the “l”. Finally, it shows that the turn was sent (i.e. Enter was pressed) 6821 msecs after the “o” was typed. 
  |
  | Each backspace is recorded as a left-pointing arrow "←". Suppose a participant types "dig" and then presses backspace twice, and then edits the turn to "dog". This could yield:
  |
  | ⁰d ¹¹⁰o ¹⁸⁹g ¹⁸²←  ³⁴⁸←  ¹⁴⁸i ²⁸⁹g
  |
  | 
  | The format also records the "is typing" notifications that are displayed on the participant's screen. This makes it possible to determine whether participants start/continue/stop typing when they see their partner start/stop typing. Suppose Participant A is in the middle of typing the turn "this shape is red". Halfway through, while typing "shape" , Participant B starts typing, which displays an "Participant B is typing" notification in Participant A's status bar. This could yield:
  |
  | ⁰T ¹¹⁰h ²⁸⁹i ¹⁸²s   ³²    ³⁴⁸s  ⁸⁰h ¹¹⁰a ¹⁰⊆ ²⁸⁹p ¹⁸²e   ⁵⁸⁰⁰  ⊇ ⁶⁰⁰i  ⁵⁰⁰s  ⁴⁹⁰  ³⁰⁰r ²⁸⁹e ¹⁰⁰d  ³⁰⁰ENTER 
  | 
  | The start of an “is typing” notification is represented with “⊆” and the end of the “is typing” notification is represented with "⊇". Notice also, that from this representation you can see that the participant received the “is typing” notification just before typing "p". You can also see that after finishing typing “shape”, Participant A paused. 5.8 seconds later, Participant A received a notification that Participant B had stopped typing. Then 600 msecs later, Participant A resumed, typing "i"
  |
  |
  |
  | This format also records the moment when turns appear in the conversation history window. Because text-chat is asynchronous, this means that at any point, while a participant is formulating a turn, it can happen that the other person sends a turn. This is recorded by the chat-tool. Suppose Participant A is typing  “The yellow circle”, but half-way through typing “yellow”, Participant B sends their turn “now?”. This could yield: 
  | 
  | ⁰T ¹¹⁰h ²⁸⁹e  ¹⁸²   ³⁴⁸y  ⁸⁰⁰e ¹¹⁰l    ³⁰⁷【Participant1: now?】²⁸⁹l ¹⁸²o ⁵⁸w  ⁶⁰⁰  ⁵⁰⁰c  ⁴⁹⁰i  ³⁰⁰r ²⁸⁹c ¹⁰⁰l  ³⁰⁰e
  | 
  | This shows that 307 milliseconds after the participant typing the turn typed the second “l” of “yellow”, the participant received the turn “now?” from Participant1.


* **TextAsFormulatedLOGSPACE** This prettifies the output of TextAsFormulatedTIMING. It removes the superscript numerals and replaces them with spaces (calculated logarithmically)- e.g. a gap of 100ms is one space. A gap of 1second is two spaces, a gap of 10 seconds is three spaces, a gap of 100 seconds is four spaces, etc.

* **istypingtimeout** The parameter that determines how long the "is typing" indicator when a participant presses a key.


 



