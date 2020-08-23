/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client.ClientInterfaceEvents;


import diet.attribval.AttribVal;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class ClientInterfaceEvent implements Serializable{
    
    
    
    String type;
    String serverUniqueID ="";
    AttribVal[] arguments;
    long clientTimeOfDisplay;
    public String participantID;
    public String participantUsername;
    
    
    
    public ClientInterfaceEvent(String participantID, String participantUsername, String type, long clientTimeOfDisplay, AttribVal...args){
        this.type=type;
        this.arguments=args;
        this.clientTimeOfDisplay=clientTimeOfDisplay;
        this.participantID = participantID;  
        this.participantUsername = participantUsername;  
        //System.err.println(getAllAttribValuesAsStringForDebug());       
    }
    
      public ClientInterfaceEvent(String participantID, String participantUsername, String type, long clientTimeOfDisplay, Vector attribvals){
        this.type=type;
        AttribVal[] avs = new AttribVal[attribvals.size()];
        for(int i=0;i<attribvals.size();i++){
            AttribVal av = (AttribVal )attribvals.elementAt(i);
            avs[i]=av;
        }
        this.arguments=avs;
        this.clientTimeOfDisplay=clientTimeOfDisplay;
        this.participantID = participantID;  
        this.participantUsername = participantUsername;  
        //System.err.println(getAllAttribValuesAsStringForDebug());       
    }

    

    public long getClientTimeOfDisplay() {
        return clientTimeOfDisplay;
    }
    
    
    
    
    public String getType(){
        return type;
    }
    
    public AttribVal[] getAttribVals(){
        return arguments;
    }
    
    public String getAllAttribValuesAsStringForDebug(){
        String allAttribVals = "Attribvals----------------------------------------------------"+"\n";
        allAttribVals = allAttribVals + type+"\n";
        for(int i=0;i<this.arguments.length;i++){
            allAttribVals = allAttribVals + arguments[i].getID()+", "+arguments[i].getVal() +"\n";
        }
        allAttribVals = allAttribVals+ "---------------------------------------------------------------"+"\n";
        
        return allAttribVals;
    }
    
    
    
    public AttribVal getAttribVal(String attribname){
        for(int i=0;i<arguments.length;i++){
            if(arguments[i].id.equalsIgnoreCase(attribname))return arguments[i];
        }
        return null;
    }
    
    
    
    public boolean isStartOfIsTyping(){
        //System.err.println("TYPEIS: "+ClientInterfaceEventTracker.changestatuslabel);
        //System.exit(03);
        if(this.type.equalsIgnoreCase(ClientInterfaceEventTracker.changestatuslabel) ){
            
             AttribVal av = this.getAttribVal("serverid");
             //System.err.println("ENDSWITH: "+av.getValAsString());
             //System.exit(-234);
             if(    ((String)av.value ).endsWith("it") ){
                
                 return true;
             }
             return false;
        } 
        return false;
    }
    public boolean isEndOfIsTyping(){
        if(this.type.equalsIgnoreCase(ClientInterfaceEventTracker.changestatuslabel) ){
             AttribVal av = this.getAttribVal("serverid");
             if(    ((String)av.value ).endsWith("nt") ) return true;
             return false;
        } 
        return false;
    }    
    
    
    public Object getValue(String attribname){
        for(int i=0;i<this.arguments.length;i++){
            if(this.arguments[i].getID().equals(attribname)){
                return arguments[i].getVal();
            }
        }
        return null;
    }
    
    
    
}
