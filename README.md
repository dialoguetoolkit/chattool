# About

This is the new (August 2020) version of the chattool.

Some of the new features are:

- **Telegram** Participants can use the Telegram messenger app to connect to the chattool server
- **Remote admin** Experimenters can monitor experiments relotely, using Telegram messenger
- **WYSIWYG** The chattool includes a "What You See Is What You Get, ie. character by character interface which is useful for programming incremental interventions


# Documentation

The programming documentation is at:   https://dialoguetoolkit.github.io/docs/

There is also a [usermanual](docs/usermanual/usermanual.pdf) on how to use the software (non-progrmamming)


# Getting started

The project should run "out of the box" in netbeans.

The project contains a pom.xml file that contains all the dependencies

## Starting the server

To start the server, use ```java -jar "chattool.jar SERVER``` 

The server will listen for incoming connections on port 20000

To change this, use ```java -jar "chattool.jar SERVER  %PORTNUMBER%```  where %PORTNUMBER% is the portnumber


## Starting the client

To start a client from the commandline you need to specify the IP address and port number to connect to

```java -jar "chattool.jar" CLIENT localhost 20000```

It is also possible to start clients (locally) from the GUI by selecting the button "Additional Client" 



## Speeding up testing cycle of conversation controller objects

The process of starting the server and clients and then logging in is quite tedious when developing. To get round this you can start Conversation Controller objects, along with the required number of clients by using the following syntax:

```
javac -jar "chatool.jar" nogui_ccname_autologin CONVERSATIONCONTROLLERNAME
```

For example:

```java
javac -jar "chatool.jar" nogui_ccname_autologin DyadicTurnByTurnInterface
```

This will automatically start the chattool, load the ConversationController object, initialize the clients and log them in.

The default number of clients to start is 2 (which is specified in ```Configuration.defaultGroupSize```

These can be saved in netbeans project configurations


