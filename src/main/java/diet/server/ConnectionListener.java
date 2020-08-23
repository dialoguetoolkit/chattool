package diet.server;

import diet.message.MessageClientCloseDown;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Vector;
import javax.swing.JOptionPane;


/**
 * There is a single ConnectionListener object per server. It listens on a specified port
 * for connections from the clients. On receiving a connection request, it instantiates a 
 * new {@link ParticipantConnection} object.
 * @author user
 */
public class ConnectionListener extends Thread {

    private ExperimentManager expM;
    ServerSocket serverSocket;
    int portNumber;
    boolean listening=false;

    boolean experimentScriptHasBeenStarted = false;
    
    public static ConnectionListener thisConnectionListener;


    public ConnectionListener(ExperimentManager experimentM, int portNumber) {
        expM = experimentM;
        serverSocket = null;
        
        this.portNumber = portNumber;
       try{
           serverSocket = new ServerSocket(portNumber);
           thisConnectionListener = this;
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println("Could not listen on port: "+portNumber);
            
     
             
              String errorMsg = "The following error occurred:\n\n"+
                       e.toString()+"\n\n"+
                      
                      "Could not listen on port: "+portNumber+"\nThis could be because\n(1)Another copy of the server is already running\n(2)The firewall doesn't allow incoming connections";
               
             
               JOptionPane.showMessageDialog(null, errorMsg, "Error",  JOptionPane.PLAIN_MESSAGE);
               System.out.println(e.getStackTrace());
            
            
            
            System.exit(-123);
        }
        this.start();
       
    }

/**
 * Loop which listens for new connections on the specified port number. On establishing a new connection
 * it instantiates a new ObjectInputStream and ObjectOutputStream which it then associates with a 
 * ParticipantConnection object.
 * @author user
 */
    
    public void startListeningOLD(){
        if(listening)return;
        start();
    }
    
    public synchronized void scriptHasBeenStarted(){
        if(experimentScriptHasBeenStarted)return;
        experimentScriptHasBeenStarted=true;
        for(int i=0;i<enqueuedSockets.size();i++){
             Socket s2 = (Socket)enqueuedSockets.elementAt(i);
             activateSocket(s2);
             
             enqueuedSockets.remove(s2);
        }
        
    }
    
    
    Vector enqueuedSockets = new Vector();
    
    
   public synchronized void activateSocket(Socket s){
        try{
                Socket s2=s;
                
                
                
                ObjectInputStream in;
                ObjectOutputStream out;
                System.out.println("Server has received connection request");
                out = new ObjectOutputStream(s2.getOutputStream());
                in = new ObjectInputStream(s2.getInputStream());
                System.out.println("Server has created input and output streams");
                ParticipantConnection participconnection= new ParticipantConnection(in,out,expM);
                participconnection.start();

           }catch (Exception e){
                System.err.println("ConnectionListener: socket problem");
                System.err.println(e.getMessage());
                System.err.println(e.getStackTrace());
                //System.exit(-1);
           }
   }
    
    
    public synchronized int processIncomingConnectionPriorToScriptStart(Socket s2){
        SocketAddress s2a = s2.getRemoteSocketAddress();
        s2a.toString();
        
        Object[] options = {"(1) Allow client to connect and start Default script",
                     "(2) Allow client to connect",
                     "(3) Kill the client"};
      int n = JOptionPane.showOptionDialog(null,
      "A client is trying to connect to the server even though a script hasn't been started yet on the server\n\n"
              + "The ID of the client is: "+s2a.toString()+"\n\n"
              + "There are 3 options:\n\n"
              + "(1) Allow client to connect AND start the default script:\n"
              + "       Choose this option if you would like to run the chat tool without manipulating the dialogue in any way.\n"
              + "       This option will start a script that will allow you to connect as many clients as you want.\n"
              + "       If all you want to do is use the chat tool to record conversational data without performing interventions, choose this\n"
              + "\n"+""
              + "(2) Allow client to connect BUT choose the experimental script manually:\n"
              + "       Choose this if you have started the client software and you know which script you want to run.\n"
              + "       Choose this if you were in the middle of an experiment and it crashed and would like to resume the experiment (if you programmed crash recovery).\n"
              + "\n"
              + "(3) Kill the client:\n"
              + "       If you're not sure which machine is sending these connection requests, or if you thought you had shutdown / reset the clients\n"
              + "       but they're inexplicably sending connection requests, choose this option to 'kill' the client\n"+
                "       The option below, 'KILL client' will attempt to send a message to the client program to exit/shutdown\n"
              + "       Unless you know what you're doing, it's probably best to restart the server after selecting 'KILL CLIENT'\n"
              + "       If this doesn't solve the problem - in other words if you have restarted the server and keep getting this message,\n"
              + "       you will have to look at each computer on your network to find which client is still trying to connect to the server, and shut it down manually\n"
              + "       In windows, use the 'Task Manager' to kill the process on the client.\n"
              + "       On Mac or Linux, use the kill command to kill the process on the client."
              + "\n\n\n"
              + "Sorry for this unwieldy solution - it has to be this difficult because this is to stop the participants from closing the clients down themselves.",
               
      "A client is trying to connect to the server",
      JOptionPane.YES_NO_CANCEL_OPTION,
      JOptionPane.QUESTION_MESSAGE,
      null,
      options,
      options[2]);
      return n;  
      
    }
    
    
    public void run(){
        listening = true;
        while (listening) {
            try{
                Socket s2 =serverSocket.accept();
                if(experimentScriptHasBeenStarted){
                   this.activateSocket(s2);    
                }
                else{
                    int option = this.processIncomingConnectionPriorToScriptStart(s2);
                    if(option ==0){
                        this.enqueuedSockets.add(s2);
                        this.expM.createAndActivateNewExperiment("CCMESSENGERNORMAL");
                    }
                    else if(option ==0){
                        this.enqueuedSockets.add(s2);
                    }
                    else if(option ==2){
                       ObjectInputStream in;
                       ObjectOutputStream out;
                       System.out.println("Server has received connection request");
                       try{
                           out = new ObjectOutputStream(s2.getOutputStream());
                           in = new ObjectInputStream(s2.getInputStream());
                           MessageClientCloseDown mccd = new MessageClientCloseDown("server","server");
                           out.writeObject(mccd);
                           out.flush();
                           out.close();
                  
                }catch(Exception e){
                    e.printStackTrace();
                }  
             }
                    
                    
                    this.enqueuedSockets.add(s2);
                }
                

           }catch (Exception e){
                System.err.println("ConnectionListener: socket problem");
                System.err.println(e.getMessage());
                System.err.println(e.getStackTrace());
                //System.exit(-1);
           }
        }
    }

    
    public int getPortNumber(){
        return this.portNumber;
    }

    
    public static int staticGetPortNumber(){
        return thisConnectionListener.getPortNumber();
    }
    
}
