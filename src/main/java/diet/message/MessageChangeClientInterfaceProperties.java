package diet.message;

import java.io.Serializable;


public class MessageChangeClientInterfaceProperties extends Message implements Serializable {

    private int windowNumber;
   
    
    
   
    
    
    private int interfaceproperties;
    private Object value = null;
    private Object value2 = null;
    private Object value3 = null;
    private Object value4 = null;
    
    private String uniqueID;
    
    
    
    
    public MessageChangeClientInterfaceProperties(String uniqueIDGeneratedByServer,  int interfaceproperties) {
        super("server", "server");
        this.interfaceproperties=interfaceproperties;
        this.uniqueID=uniqueIDGeneratedByServer;
        System.err.println("Creating message with interfaceproperty: "+interfaceproperties);
    }
    
    
    public MessageChangeClientInterfaceProperties(String uniqueIDGeneratedByServer, int interfaceproperties, Object value) {
        super("server", "server");
        this.interfaceproperties=interfaceproperties;
        this.value=value;
        this.uniqueID=uniqueIDGeneratedByServer;
        System.err.println("Creating message with interfaceproperty: "+interfaceproperties);
    }
    
    
    public MessageChangeClientInterfaceProperties(String uniqueIDGeneratedByServer, int interfaceproperties, Object value, Object value2) {
        super("server", "server");
        this.interfaceproperties=interfaceproperties;
        this.value=value;
        this.value2=value2;
        this.uniqueID=uniqueIDGeneratedByServer;
        System.err.println("Creating message with interfaceproperty: "+interfaceproperties);
    }

     public MessageChangeClientInterfaceProperties(String uniqueIDGeneratedByServer, int interfaceproperties, Object value, Object value2, Object value3) {
        super("server", "server");
        this.interfaceproperties=interfaceproperties;
        this.value=value;
        this.value2=value2;
        this.value3=value3;
        this.uniqueID=uniqueIDGeneratedByServer;
        System.err.println("Creating message with interfaceproperty: "+interfaceproperties);
    }


     public MessageChangeClientInterfaceProperties(String uniqueIDGeneratedByServer, int interfaceproperties, Object value, Object value2, Object value3, Object value4) {
        super("server", "server");
        this.interfaceproperties=interfaceproperties;
        this.value=value;
        this.value2=value2;
        this.value3=value3;
        this.value4=value4;
        this.uniqueID=uniqueIDGeneratedByServer;
        System.err.println("Creating message with interfaceproperty: "+interfaceproperties);
    }

     
     
    public int getWindowNumber(){
        return windowNumber;
    }

    public int getInterfaceproperties() {
        return interfaceproperties;
    }

    //public Color getNewBackgroundColour()

    public Object getValue(){
        return value;
    }
    public Object getValue2(){
        return value2;
    }
    public Object getValue3(){
        return value3;
    }

    public Object getValue4(){
        return value4;
    }
    
    
    public String getUniqueIDGeneratedByServer(){
        return this.uniqueID;
    }
    
}
