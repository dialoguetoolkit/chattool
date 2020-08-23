/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client.DocumentChange;
import java.util.Date;

import javax.swing.text.AttributeSet;
/**
 *
 * @author Greg
 */
public class DocReplace extends DocChange{

    int offs;
    int len;
    String str;
    Object a;

    public DocReplace(String sender,String apparentSender,String recipient,int offs,int len,String s,Object attributeSetORStyle){
        super(sender,apparentSender,recipient);
        this.offs=offs;
        this.len=len;
        this.a=attributeSetORStyle;
    }
    
    
    
    public Object getAttr() {
        return a;
    }

    public int getLen() {
        return len;
    }

    public int getOffs() {
        return offs;
    }

    public String getStr() {
        return str;
    }
    
    public DocReplace(int offs, int len, String str, AttributeSet a) {
      this.offs=offs;
      this.str=str;
      this.a=a;
      timestamp = new Date().getTime();
    }
    
}
