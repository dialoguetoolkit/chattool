package diet.textmanipulationmodules.mazegame_location_description_detection;
import java.util.Vector;


public class TextTurnFragment {
  String text ="";
  boolean _1_1 = false;
  Vector _1_1FoundPreps = new Vector();
  String _2_1RightmostText;

  public TextTurnFragment(String t) {
    text =t;

  }




   public void _1_1StoreFoundPreposition(String s){
    System.out.println("adding");
    _1_1FoundPreps.addElement(s);
  }

  public String _2_1GetRightMostText(){
    if(_1_1FoundPreps.size()==0)return "Empty";
    String s = (String) _1_1FoundPreps.elementAt(0);
    int rightmostIndex = text.lastIndexOf(s);
    //System.out.println("WORD: "+s+" "+rightmostIndex+" "+s.length());
    _2_1RightmostText = text.substring(rightmostIndex+s.length(),text.length());
    return _2_1RightmostText;

 }


}
