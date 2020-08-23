/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;

/**
 *
 * @author Greg
 */
public class MessageDisplayChangeWebpage extends Message{

    String id;
    String url;

    public String newtext ="";
    
    
    long lengthOfTime = -999999; //Anything negative is treated as display forever
    public boolean appendeverything = false;
    
    public MessageDisplayChangeWebpage(String id, String url) {
          super("server","server");
          this.id=id;
          this.url=url;
    }

    public MessageDisplayChangeWebpage(String id, String url, String newtext, boolean appendeverything) {
          super("server","server");
          this.id=id;
          this.url=url;
          this.newtext=newtext;
          this.appendeverything=appendeverything;
    }
     public MessageDisplayChangeWebpage(String id, String url, String newtext, long lengthOfTime) {
          super("server","server");
          this.id=id;
          this.url=url;
          this.newtext=newtext;
          this.lengthOfTime=lengthOfTime;
    }

     
   public long getLengthOfTime(){
       return this.lengthOfTime;
   }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

  
}
