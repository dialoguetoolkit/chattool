package diet.textmanipulationmodules.mazegame_location_description_detection;

import java.util.Vector;



public class Start {
  public Start() {

  }
  public static void main(String[] args) {
    
    PhraseFilter pf = new PhraseFilter();
    Vector v = pf.getAllClarifications("It is not there where we win the beginning of the row");
    
    String sQuick = pf.getAClarificationFastest(" there a clmn wi is");
    System.out.println(sQuick);
    if(2<5)return;
    
    for(int i=0;i<v.size();i++){
        Vector v2 = (Vector)v.elementAt(i);
        System.out.println(i+"");
        for(int j=0;j<v2.size();j++){
            String s = (String)v2.elementAt(j);
            System.out.println(s);
        }
        
        
    }
    if(2<5)return;
      
      
    Start start1 = new Start();
    TestLoader tl = new TestLoader();
    JTestFrame jt = new JTestFrame(tl.getAllTurns(),new PhraseFilter());
    jt.setVisible(true);
  }

}
