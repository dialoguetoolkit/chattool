/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client.DocumentChange;



/**
 *
 * @author Greg
 */
public class DocCursor extends DocChange{

    public int cursorstart;
    public int cursorfinish;
    
    public DocCursor(String sender,String apparentSender,String recipient,int cursorstart,int cursorfinish,String elementString,int elementStart,int elementFinish){
        super(sender,apparentSender,recipient);
        this.cursorstart=cursorstart;
        this.cursorfinish=cursorfinish;
        this.elementString=elementString;
        this.elementStart=elementStart;
        this.elementFinish=elementFinish;
    }
    
    public DocCursor returnCopy(){
        DocCursor dc = new DocCursor(sender,apparentSender,recipient,cursorstart,cursorfinish,elementString,elementStart,elementFinish);
        dc.setTimestamp(timestamp);
        return dc;
    }
    
    
}
