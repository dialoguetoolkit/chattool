package diet.textmanipulationmodules.mazegame_location_description_detection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Vector;



public class PhraseFilter {
  PhraseLibrary pl = new PhraseLibrary();
  Vector nonPhrases_Figural = new Vector();
  Vector nonPhrases_Abstract = new Vector();
  Vector nonPhrases_Abstract_Coordinates = new Vector();
  Vector nonPhrases_Abstract_CR = new Vector();
  File inputFileFigural ;
  File inputFileAbstract;
  File inputFileAbstract_Coordinates;
  File inputFileAbstract_CR;
  Random r = new Random();
  String prefixPath =  System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"categ"+File.separator;

  public PhraseFilter() {
      
      loadRawPhrases();
     
  }


public void jumbleAllVectors(){
  pl.jumbleAllVectors();
}

  

  public void loadRawPhrases(){
   try{
     this.nonPhrases_Abstract = new Vector();
     this.nonPhrases_Abstract_Coordinates=new Vector();
     this.nonPhrases_Abstract_CR = new Vector();
     this.nonPhrases_Figural=new Vector();
     inputFileFigural = new File(prefixPath+"nonfrags_Figural.csv");
     inputFileAbstract = new File(prefixPath+"nonfrags_Abstract.csv");
     inputFileAbstract_Coordinates = new File(prefixPath+"nonfrags_Abstract_Coordinates.csv");
     inputFileAbstract_CR = new File(prefixPath+"nonfrags_Abstract_CR.csv");
     FileInputStream inFigural = new FileInputStream(inputFileFigural);
     FileInputStream inAbstract = new FileInputStream(inputFileAbstract);
     FileInputStream inAbstract_Coordinates = new FileInputStream(inputFileAbstract_Coordinates);
     FileInputStream inAbstract_CR = new FileInputStream(inputFileAbstract_CR);
     InputStreamReader inFigural2 = new InputStreamReader(inFigural);
     InputStreamReader inAbstract2 = new InputStreamReader(inAbstract);
     InputStreamReader inAbstract_Coordinates2 = new InputStreamReader(inAbstract_Coordinates);
     InputStreamReader inAbstract_CR2 = new InputStreamReader(inAbstract_CR);
     BufferedReader inFigural3= new BufferedReader (inFigural2);

     BufferedReader inAbstract3= new BufferedReader (inAbstract2);
     BufferedReader inAbstract_Coordinates3= new BufferedReader (inAbstract_Coordinates2);
     BufferedReader inAbstract_CR3= new BufferedReader (inAbstract_CR2);
     boolean readon = true;

     //Reead in figural
     while (readon){
       String r = inFigural3.readLine();
       if(r==null){
         readon =false;
       }
       else{
          if(!vectorContains(nonPhrases_Figural,r)){
            nonPhrases_Figural.addElement(r);
          }
       }
     }
     //Read in abstract
     readon = true;
         while (readon){
         String r = inAbstract3.readLine();
            if(r==null){
               readon =false;
            }
            else{
              if(!vectorContains(nonPhrases_Abstract,r)){
                  nonPhrases_Abstract.addElement(r);
              }
            }
        }
         //Read in abstract Coordinates
       readon = true;
       while (readon){
       String r = inAbstract_Coordinates3.readLine();
          if(r==null){
             readon =false;
          }
          else{
            if(!vectorContains(nonPhrases_Abstract_Coordinates,r)){
                nonPhrases_Abstract_Coordinates.addElement(r);
            }
          }
      }
      //Read in abstract CR
      readon = true;
         while (readon){
         String r = inAbstract_CR3.readLine();
            if(r==null){
               readon =false;
            }
            else{
              if(!vectorContains(nonPhrases_Abstract_CR,r)){
                  nonPhrases_Abstract_CR.addElement(r);
              }
            }
        }

        inFigural3.close();
        inAbstract3.close();
        inAbstract_Coordinates3.close();
        inAbstract_CR3.close();
  } catch (Exception e){
     System.out.println("Exception loading in nonfrags");
     e.printStackTrace();
 }
}


  private boolean vectorContains(Vector v,String s){
     for (int i = 0; i < v.size(); i++) {
       //System.out.println("STRING: "+s);
         String s2 = (String) v.elementAt(i);
         if (s2.equalsIgnoreCase(s))return true;
     }
     return false;

 }

  public String turnVectorToString(Vector v){
     String response = "";
     for (int i = 0 ; i < v.size();i++){
        String s = (String)v.elementAt(i);
        response = response+"|"+s;
     }
     return response;
  }


 public Vector removeElementsFromVector(Vector v,Vector v2){

     Vector cleanup = new Vector();
     for(int i =0; i < v.size();i++){
        String s2 = (String)v.elementAt(i);
        //System.out.println("tryingToFind "+s2);
        boolean found = false;
        for(int j=0;j<v2.size();j++){
           String sv2 = (String)v2.elementAt(j);
           //System.out.println("Trying to find  "+s2+" in "+sv2);
           if(s2.equalsIgnoreCase(sv2))found =true;
        }
        if(!found){
            cleanup.addElement(s2);
            //System.out.println("adding "+s2);
        }
        else{
            System.out.println("FOUND"+s2);
        }

     }
     return cleanup;

 }
  public Vector mergeVectorsNoDuplicates(Vector v,Vector v2){
    Vector response = new Vector();
    for(int i=0;i<v2.size();i++){
       response.addElement(v2.elementAt(i));
    }
    for(int i =0;i<v.size();i++){
       String s = (String)v.elementAt(i);
       if(!vectorContains(v2,s)){
          response.addElement(s);
       }
    }
    return response;
  }


  public Vector getAllClarifications(String s){
      Vector response = new Vector();
      Vector v1 = this.getPermittedClarificationFragmentAbstract(s);
      Vector v2 = this.getPermittedClarificationFragmentFigural(s);
      Vector v3 = this.getPermittedClarificationFragmentAbstract_Coord(s);
      Vector v4 = this.getPermittedClarificationFragmentAbstract_CR(s);
      Vector allPotentialClarifications = mergeVectorsNoDuplicates(v1,mergeVectorsNoDuplicates(v2,mergeVectorsNoDuplicates(v3,v4)));
      response.addElement(v1);
      response.addElement(v2);
      response.addElement(v3);
      response.addElement(v4);
      response.addElement(allPotentialClarifications);
      response.addElement(pl.getWots());
      response.addElement(pl.getAcknowledgements());

      return response;
  }



   public String getAClarificationFastest(String s){
      boolean lookedAtAbstract = false;
      boolean lookedAtFigural = false;
      boolean lookedAtAbstract_CR = false;
      boolean lookedAtAbstract_Coord = false;
      boolean foundFragment = false;
      String s2 = null;

      while( !lookedAtAbstract | ! lookedAtFigural | ! lookedAtAbstract_CR | ! lookedAtAbstract_Coord){
           int lookAt = r.nextInt(4);
           System.out.println("GETALL  random No is "+lookAt);
           if(lookAt==0&!lookedAtAbstract){
               s2 = pl.getFirstPermittedClarificationFragmentAbstract(s,this.nonPhrases_Abstract);
               lookedAtAbstract = true;
               System.out.println("looking at ABSTRACT "+ s2);
               if(s2!=null) return s2;
           }
           else if(lookAt ==1 & !lookedAtFigural){
                s2 = pl.getFirstPermittedClarificationFragmentFigural(s,this.nonPhrases_Figural);
                System.out.println("looking at FIGURAL "+ s2);
                lookedAtFigural = true;
                if(s2!=null) return s2;
           }
           else if(lookAt ==2 & !lookedAtAbstract_CR){
                s2 = pl.getFirstPermittedClarificationFragmentAbstract_CR(s,this.nonPhrases_Abstract_CR);
                System.out.println("looking at ABSTRACT_CR "+ s2);
                lookedAtAbstract_CR = true;
                if(s2!=null) return s2;
           }
           else if (lookAt ==3 & !lookedAtAbstract_Coord){
                s2 = pl.getFirstPermittedClarificationFragmentAbstract_Coord(s,this.nonPhrases_Abstract_Coordinates);
                System.out.println("looking at ABSTRACT_COORD "+ s2);
                lookedAtAbstract_Coord = true;
                if(s2!=null) return s2;
           }
      }
      System.out.println("RETURNING2 TO LABEL "+ s2);
      return s2;
   }



  public Vector getAllWots(){
     return pl.getWots();
  }

  public Vector getAllAcknowledgements(){
     return pl.getAcknowledgements();
  }

  public String getRandomWot(){
     Vector v = pl.getWots();
     int r2 = r.nextInt(v.size());
     return (String)v.elementAt(r2);
  }
  public String getRandomAcknowledgement(){
     Vector v = pl.getAcknowledgements();
     int r2 = r.nextInt(v.size());
     return (String)v.elementAt(r2);
  }


  public Vector getPermittedClarificationFragmentFigural(String s){
      Vector v = pl.getClarificationFragmentFigural(s);
      return removeElementsFromVector(v,this.nonPhrases_Figural);
  }

  public Vector getPermittedClarificationFragmentAbstract(String s){
      Vector v = pl.getClarificationFragmentAbstract(s);
      return removeElementsFromVector(v,this.nonPhrases_Abstract);
  }

  public Vector getPermittedClarificationFragmentAbstract_CR(String s){
      Vector v = pl.getClarificationFragmentAbstract_CR(s);
      return removeElementsFromVector(v,this.nonPhrases_Abstract_CR);
  }

  public Vector getPermittedClarificationFragmentAbstract_Coord(String s){
      Vector v = pl.getClarificationFragmentAbstract_Coord(s);
      return removeElementsFromVector(v,this.nonPhrases_Abstract_Coordinates);
  }

}
