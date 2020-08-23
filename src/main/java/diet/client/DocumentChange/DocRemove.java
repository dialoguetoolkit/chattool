/*
 * DocumentInterceptingInsert.java
 *
 * Created on 21 October 2007, 21:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.client.DocumentChange;

import java.io.Serializable;

/**
 * Records the offset (from beginning or from end of document) of any text deleted
 * @author user
 */
public class DocRemove extends DocChange implements Serializable{
    
    
    public int len;
    
    
    public DocRemove(String sender,String apparentSender,String recipient,int offs,int len,String elementString,int elementStart,int elementFinish,int docSize){
        super(sender,apparentSender,recipient);
        this.offs=offs;
        this.len=len;
        this.elementString=elementString;
        this.elementStart=elementStart;
        this.elementFinish=elementFinish;
        this.docSize=docSize;
    }
    
    
    //public DocRemove(String sender,String apparentSender,String recipient,int offs,int len,int rowNumber,int positionWithinRow){
    //    this(sender,apparentSender,recipient,offs,len);
    //    this.rowNumber=rowNumber;
    //    this.positionWithinRowFromRight=positionWithinRow;
    //}
    
    public DocRemove(String sender,String apparentSender,String recipient,int offs,int len){
        super(sender,apparentSender,recipient);
        this.offs=offs;
        this.len=len;
    }
    
    
    public DocRemove(int offs, int len) {
      super();
      this.offs=offs;
      this.len=len;
        
    }
    
    public DocRemove returnCopy(){
        DocRemove dr = new DocRemove(sender,apparentSender,recipient,offs,len,elementString,elementStart,elementFinish,docSize);
        dr.timestamp=timestamp;
        return dr;
    }
    
    /**
     * Returns the offset associated with the insert operation
     * @return Returns the offset associated with the insert operation
     */
    public int getOffs(){
        return offs;
    }
    public int getLen(){
        return len;
    }
   
    
}
