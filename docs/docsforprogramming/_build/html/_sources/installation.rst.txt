**************************************
Downloading and installing the toolkit    
**************************************
                                          
The toolkit is currently hosted on github at https://github.com/dialoguetoolkit/chattool

The toolkit is stored as a netbeans project (http://netbeans.org). You can of course use your own IDE, or run it from the command-line. However, it is easiest to run and script using netbeans. If you use netbeans, it should work "out-of-the-box" - there should be no need to play around with linking libraries and directory paths.



Starting the server in Netbeans
###############################

To start the server, in netbeans select "Run"


Starting the server on the command-line
#######################################

To start the server, use ``java -jar "chattool.jar SERVER``

The server will listen for incoming connections on port 20000

To chage the port number, use ``java -jar "chattool.jar SERVER %PORTNUMBER%``

where ``%PORTNUMBER%`` is the portnumber



Starting the server, clients, and automatically logging the clients in
######################################################################

This becomes essential once you start programming interventions. Using these command-line arguments you can start the server, specify how many clients you want to start, and automatically log the clients in. This means you can test any interventions you program with a single click. Use the following syntax:

``java -jar "chatool.jar" nogui_ccname_autologin CONVERSATIONCONTROLLERNAME``

For example:

``java -jar "chatool.jar" nogui_ccname_autologin DyadicTurnByTurnInterface``

his will automatically start the chattool, load the ConversationController object, initialize the clients and log them in.

The default number of clients to start is 2 (which is specified in ``diet.Configuration.defaultGroupSize`` 


