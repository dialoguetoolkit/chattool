package diet.message;
import diet.serverclientconsistencycheck.ServerClientConsistency;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.SocketAddress;

public class MessageClientLogon extends Message implements Serializable {

    double scc;
    InetAddress ina;
    int inPort; 
    InetAddress local_ina; 
    int local_port; 
    SocketAddress socketAddress;
    
    
    public MessageClientLogon(String email, String username,double scc, InetAddress ina, int inPort, InetAddress local_ina, int local_port,  SocketAddress socketAddress) {
        super(email, username);
        this.scc=scc;
        this.ina=ina;
        this.inPort = inPort;
        this.local_ina = local_ina;
        this.local_port = local_port; 
        this.socketAddress = socketAddress;
        printInfo();
    }

    
    public String getMessageClass(){
        return "MessageClientLogon";
    }

    public double getServerClientConsistencyID(){
        return scc;
    }

   

    public InetAddress getIna() {
        return ina;
    }

    public int getInPort() {
        return inPort;
    }

    public InetAddress getLocal_ina() {
        return local_ina;
    }

    public int getLocal_port() {
        return local_port;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
    
    public void printInfo(){
        System.err.println("INTERNET INFO-----------------------------");
        System.err.println(ina.getCanonicalHostName());
        System.err.println(ina.getHostAddress());
        System.err.println(this.inPort);
        System.err.println(this.local_ina.getCanonicalHostName());
        System.err.println(this.local_ina.getHostAddress());
        System.err.println(this.local_port);
        System.err.println(this.socketAddress);
         System.err.println("INTERNET INFO-------------------------");
    }
    
    
    
    
}
