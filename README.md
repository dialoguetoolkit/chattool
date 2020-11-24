# About

This is the new (November 2020) version of the chattool.

Some of the new features are:

- **Telegram** Participants can use the Telegram messenger app to connect to the chattool server
- **Remote admin** Experimenters can monitor experiments relotely, using Telegram messenger
- **WYSIWYG** The chattool includes a "What You See Is What You Get, ie. character by character interface which is useful for programming incremental interventions


# Documentation

There is a [usermanual](docs/usermanual/usermanual.pdf) on how to use the software (non-programming)

The webpage of the chattool is at: https://dialoguetoolkit.github.io/chattool/

The programming documentation is at:   https://dialoguetoolkit.github.io/docs/

If you have any questions, please email g.j.mills@rug.nl

# Getting started

The project should run "out of the box" in netbeans (as a Maven project).

The project contains a pom.xml file that contains a list of all the dependencies.

## Starting the server

To start the server, use ```java -jar "chattool.jar SERVER``` 

The server will listen for incoming connections on port 20000

To change this, use ```java -jar "chattool.jar SERVER  %PORTNUMBER%```  where %PORTNUMBER% is the portnumber

it is advisable to read through the "quick start" sections in the user manual before programming the chattool!


## Starting the client 

### Telegram clients

Please follow the instructions in the [usermanual](docs/usermanual/usermanual.pdf)

### Non-telegram clients

To start a client from the commandline you need to specify the IP address and port number to connect to

```java -jar "chattool.jar" CLIENT localhost 20000```

It is also possible to start clients (locally) from the GUI by selecting the button "Additional Client" 



