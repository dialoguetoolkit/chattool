/*
 * DocChange.java
 *
 * Created on 26 October 2007, 20:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.client.DocumentChange;
import java.io.Serializable;
import java.util.Date;

/**
 * Represents changes made to the underlying "Document" of the chat window. It is agnostic as to whether
 * the changes are represented w.r.t the offset from the first character of the document or from the last character.
 * See each chat tool window implementation to verify which convention is used. 
 * 
 * @author user
 */
public class DocChange implements Serializable{
    
    /** Creates a new instance of DocChange */
    
    public String sender="";
    final public String apparentSender="DEPRECATED";
    public String recipient;
    
    long timestamp;
    public int offs;
    
    
    public String elementString="";
    public int elementStart=-9999;
    public int elementFinish=-99999;
    
    public int rowNumber;
    public int positionWithinRowFromRight;
    public int docSize=-99999999;

    public long timeStampOfSend = -999999999;
   
     
    
    public boolean equals(Object o)
    {
    	if (o instanceof DocChange)
    	{
    		DocChange dc=(DocChange)o;
    		return dc.getOffs()==this.getOffs()&&dc.getTimeStampOfSend()==this.getTimeStampOfSend();
    	}
    	else return false;
    }
    public DocChange(String sender,String apparentSender,String recipient){
        timestamp=new Date().getTime();
        
        this.sender=sender;        
        this.recipient = recipient;
    }
    public DocChange(){
        
        timestamp = new Date().getTime();
    }
    
    public DocChange returnCopy(){
        DocChange dc = new DocChange(sender,apparentSender,recipient);
        dc.timestamp=this.timestamp;
        dc.offs=offs;
        dc.docSize=docSize;
        dc.elementFinish=elementFinish;
        dc.elementStart=elementStart;
        dc.elementString=elementString;
        
        return dc;
    }
    
    public String getApparentSenderDeprecated() {
        return apparentSender;
    }

    public void setApparentSenderDeprecated(String apparentSender) {
        //this.apparentSender = apparentSender;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    
    
     public int getOffs() {
        return offs;
    }

    public int getPosFromRightDeprecated() {
        return this.positionWithinRowFromRight;
    }

    public void setPosFromRight(int posFromRight) {
        positionWithinRowFromRight=posFromRight;
    }
    
    public String getRecipient() {
        return recipient;
    }

    public int getRowFromTOP() {
        return rowNumber;
    }
    public void setRowFromTOP(int rowFromTop) {
        this.rowNumber=rowFromTop;
    }
    
   
      
    
    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }
    public long getTimestamp(){
        return timestamp;
    }

    public void setTimeStampOfSend(long timeStampOfSend) {
        this.timeStampOfSend = timeStampOfSend;
    }

    public long getTimeStampOfSend() {
        return timeStampOfSend;
    }
    
    
}
