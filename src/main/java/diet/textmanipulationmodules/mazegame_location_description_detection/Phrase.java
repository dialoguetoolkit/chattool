package diet.textmanipulationmodules.mazegame_location_description_detection;
import java.util.Vector;

public class Phrase {


   private String head ;
   private Vector tails = new Vector();




 public Phrase findPhraseWithHeader(String s){
     for(int i = 0 ; i < tails.size() ; i ++){
        Phrase p = (Phrase)tails.elementAt(i);
        if(headIsEqualTo(s)) return p;
     }
     return null;
 }


  public boolean headIsEqualTo(String s){
     if(head.equalsIgnoreCase(s))return true;
     return false;
  }


  public Phrase(String s) {
      head = s;

  }


  public void addPhrase(Vector v){

     if(v.size()==1){
        //check to see if first element of v is contained in tails
        //if it is, do nothing
        //if it isn't, create new phrase with v.elementAt(i) and add it to tails in alphabetical order
        String shead = (String)v.elementAt(0);
        Phrase p = findPhraseWithHeader(shead);
        //if(p==null){
        //   Phrase p = new
       // }


     }
     else if(v.size()>1){
       //check to see if first element of v is contained in tails
       //if it is,
            //call addPhrase method on it with the rest of the vector as argument.
       //if it isn't
             //create new Phrase with v.elementAt(i)
             //add it to tails in alphabetical order
             //call the addPhrase method on it with the rest of the vector as argument

     }

       //Vector v2 = (Vector)v.clone();
       //v2.removeElementAt(0);
       //head = (String) v.elementAt(0);
       //tail = new Phrase(v2);


  }
  public boolean isLeaf(){
    if (tails.size()==0){
      return true;
    }
    return false;
  }



}
