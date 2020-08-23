package diet.textmanipulationmodules.mazegame_location_description_detection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;


public class TestLoader {
  Vector linesOfText  = new Vector();




  public TestLoader() {
   try{
    File inputFile = new File("rawturns.csv");
    FileInputStream in = new FileInputStream(inputFile);
    InputStreamReader in2 = new InputStreamReader(in);
    BufferedReader in3= new BufferedReader (in2);
    boolean readon = true;
    for(int i =0 ; i < 20000 ; i++){
       linesOfText.addElement(in3.readLine());
    }

   } catch (Exception e){
       System.out.println("Exception");
   }


  }
  public Vector getAllTurns(){
    return linesOfText;
  }


}
