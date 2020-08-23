/*
 * DocInsert.java
 *
 * Created on 21 October 2007, 21:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.client.DocumentChange;


import java.io.Serializable;
import java.util.Date;

import javax.swing.text.AttributeSet;

/**
 * Records the offset (from beginning or from end of document) of any text inserted
 * @author user
 */
public class DocInsert extends DocChange implements Serializable{
    
    
    public String str;
    public Object a;//attribute set
    
    
    static public DocInsert createEmpty(){
        DocInsert di = new DocInsert("null","null","null",0,"",null,"null",0,0,0);
        return di;
    }
    
    public DocInsert(String sender, String apparentSender,String recipient,int offs, String str, Object a,String elementString,int elementStart,int elementFinish, int docSize){
      super(sender,apparentSender,recipient);
      this.offs=offs;
      this.str=str;
      this.a=a;
      this.elementString=elementString;
      this.elementStart=elementStart;
      this.elementFinish=elementFinish;
      this.elementString=elementString;
      
        this.docSize=docSize;
    
    }
    
    
    public DocInsert(String sender, String apparentSender,String recipient,int offs, String str, AttributeSet a,int rowNumber,int positionWithinRow){
        this(sender, apparentSender,recipient,offs, str,  a);
        this.rowNumber=rowNumber;
        this.positionWithinRowFromRight=positionWithinRow;
    }
    
    public DocInsert(String sender, String apparentSender,String recipient,int offs, String str, Object a) {
      super(sender,apparentSender,recipient);
      this.offs=offs;
      this.str=str;
      this.a=a;
    }
    
    
    public DocInsert(int offs, String str, Object attributeSetORStyle) {
      this.offs=offs;
      this.str=str;
      this.a=attributeSetORStyle;
      timestamp = new Date().getTime();
    }
    /**
     * Returns the offset associated with the insert operation
     * @return Returns the offset associated with the insert operation
     */
    
    public DocInsert returnCopy(){
        DocInsert di = new DocInsert(sender, apparentSender,recipient,offs, str, a,elementString,elementStart,elementFinish,docSize);
        di.timestamp=timestamp;
        return di;
    }
    
    public String getStr(){
        return str;
    }
    public Object getAttrSet(){
        return a;
    }
     
}
