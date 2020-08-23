package diet.textmanipulationmodules.mazegame_location_description_detection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

public class PhraseLibrary {
    private Vector rawPhrases_Figural = new Vector();
    private Vector rawPhrases_Abstract = new Vector();
    private Vector rawPhrases_Abstract_Coordinates = new Vector();
    private Vector rawPhrases_Abstract_CR = new Vector();
    private Vector jumblePhrases_Figural = new Vector();
    private Vector jumblePhrases_Abstract = new Vector();
    private Vector jumblePhrases_Abstract_Coordinates = new Vector();
    private Vector jumblePhrases_Abstract_CR = new Vector();
    // private Vector


    Vector wots = new Vector();
    Vector acknowledgements = new Vector();
    File inputFileFigural ;
    File inputFileAbstract;
    File inputFileAbstract_Coordinates;
    File inputFileAbstract_CR;
    Random r = new Random();
    String  prefixPath = System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"categ";










  public PhraseLibrary() {
      prefixPath = System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"categ"+File.separator;
      loadRawPhrases();
     //jumblePhrases_Figural =makeCopyOfVector(rawPhrases_Figural);
     //jumblePhrases_Abstract = makeCopyOfVector(rawPhrases_Abstract);
     //jumblePhrases_Abstract_Coordinates =  makeCopyOfVector(rawPhrases_Abstract_Coordinates);
     //jumblePhrases_Abstract_CR = makeCopyOfVector(rawPhrases_Abstract_CR);

     //sort through figural , no duplicates

  }
  public Vector makeCopyOfVector(Vector v){
      Vector v2 = new Vector();
      for(int i = 0 ; i< v.size(); i++){
          String s = (String)v.elementAt(i);
          String s2 = new String(s);
          v2.addElement(s2);
      }
      return v2;
  }

  public Vector jumbleVector(Vector v){

    Vector v1 = makeCopyOfVector(v);
    Vector v2 = new Vector();
    while (v1.size()>0){
         int r2 = r.nextInt(v1.size());
         Object o = v1.elementAt(r2);
         v2.addElement(o);
         v1.remove(o);
        // System.out.println("SIZE OF V1 is "+v1.size());
       }
    return v2;
  }

  public void jumbleAllVectors(){
  //System.out.println("STARTING JUMBLING WITH ABSTRACT:  SIZE IS "+jumblePhrases_Abstract.size());
      this.jumblePhrases_Abstract = jumbleVector(this.jumblePhrases_Abstract);
      this.jumblePhrases_Figural = jumbleVector(this.jumblePhrases_Figural);
      this.jumblePhrases_Abstract_CR = jumbleVector(this.jumblePhrases_Abstract_CR);
      this.jumblePhrases_Abstract_Coordinates = jumbleVector(this.jumblePhrases_Abstract_Coordinates);
  //System.out.println("EXITED JUMBLING WITH ABSTRACT:  SIZE IS "+jumblePhrases_Abstract.size());
  }


  private boolean vectorContains(Vector v,String s){
      for (int i = 0; i < v.size(); i++) {
        //System.out.println("STRING: "+s);
          String s2 = (String) v.elementAt(i);
          if (s2.equalsIgnoreCase(s))return true;
      }
      return false;

  }

   public void loadRawPhrases(){
      try{
        wots = new Vector();
        acknowledgements = new Vector();
        wots.addElement("What ");
        wots.addElement("what ");
        wots.addElement("wot ");
        wots.addElement("ehh ");
        wots.addElement("huh ");
        wots.addElement("sorry ");
        wots.addElement("sory ");
        wots.addElement("where ");
        wots.addElement("sorry what ");
        wots.addElement("sorry where ");
        wots.addElement("err ");
        wots.addElement("uhh ");
        acknowledgements.addElement("OK");
        acknowledgements.addElement("ok");
        acknowledgements.addElement("ok right");
       acknowledgements.addElement("k right");
        acknowledgements.addElement("oh ok");
        acknowledgements.addElement("oh right");
        acknowledgements.addElement("thnks");
        acknowledgements.addElement("thanks");
        acknowledgements.addElement("got u");
        acknowledgements.addElement("k");

        this.rawPhrases_Abstract = new Vector();
        this.rawPhrases_Abstract_Coordinates=new Vector();
        this.rawPhrases_Abstract_CR = new Vector();
        this.rawPhrases_Figural=new Vector();
        inputFileFigural = new File(prefixPath+"okfrags_Figural.csv");
        inputFileAbstract = new File(prefixPath+"okfrags_Abstract.csv");
        inputFileAbstract_Coordinates = new File(prefixPath+"okfrags_Abstract_Coordinates.csv");
        inputFileAbstract_CR = new File(prefixPath+"okfrags_Abstract_CR.csv");
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
             if(!vectorContains(rawPhrases_Figural,r)&!r.equalsIgnoreCase("right")&!r.endsWith(".")&!r.endsWith(",")&!r.endsWith(",")&!r.endsWith("?")&!r.endsWith("!")){
               rawPhrases_Figural.addElement(r);
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
                 if(!vectorContains(rawPhrases_Abstract,r)){
                     rawPhrases_Abstract.addElement(r);
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
               if(!vectorContains(rawPhrases_Abstract_Coordinates,r)){
                   rawPhrases_Abstract_Coordinates.addElement(r);
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
                 if(!vectorContains(rawPhrases_Abstract_CR,r)){

                     rawPhrases_Abstract_CR.addElement(r);
                 }
               }
           }

           inFigural3.close();
           inAbstract3.close();
           inAbstract_Coordinates3.close();
           inAbstract_CR3.close();
     } catch (Exception e){
        System.out.println("Exception");
        e.printStackTrace();
    }
    jumblePhrases_Figural =makeCopyOfVector(rawPhrases_Figural);
    jumblePhrases_Abstract = makeCopyOfVector(rawPhrases_Abstract);
    jumblePhrases_Abstract_Coordinates =  makeCopyOfVector(rawPhrases_Abstract_Coordinates);
    jumblePhrases_Abstract_CR = makeCopyOfVector(rawPhrases_Abstract_CR);

   }


   public void addSynonymFigural(String s,String s2){
     Vector newphrases = new Vector();
     for(int i = 0;i< this.rawPhrases_Figural.size();i++){
         String wrd =(String)this.rawPhrases_Figural.elementAt(i);
         newphrases.addElement(wrd);
         int startIndex = wrd.toLowerCase().indexOf(s);
         if(startIndex>-1){
            System.out.println("adding "+wrd);
            String newStr = new String(wrd.replaceAll(s,s2));
            System.out.println("Added: " +newStr);
            if(!vectorContains(rawPhrases_Figural,newStr)){
              newphrases.addElement(newStr);
            }
         }
     }
     this.rawPhrases_Figural = newphrases;
   }

   public void addSynonymAbstract(String s,String s2){
     Vector newphrases = new Vector();
     for(int i = 0;i< this.rawPhrases_Abstract.size();i++){
         String wrd =(String)this.rawPhrases_Abstract.elementAt(i);
         newphrases.addElement(wrd);
         int startIndex = wrd.toLowerCase().indexOf(s);
         if(startIndex>-1){
            System.out.println("adding "+wrd);
            String newStr = new String(wrd.replaceAll(s,s2));
            System.out.println("Added: " +newStr);
            if(!vectorContains(rawPhrases_Abstract,newStr)){
              newphrases.addElement(newStr);
            }
         }
     }
     this.rawPhrases_Abstract = newphrases;
   }

  public void addFragmentAbstract(String s){
    if(!vectorContains(rawPhrases_Abstract,s)){
      this.rawPhrases_Abstract.addElement(s);
    }
  }
  public void addFragmentFigural(String s){
    if(!vectorContains(rawPhrases_Figural,s)){
      this.rawPhrases_Figural.addElement(s);
    }
  }
  public void addFragmentAbstract_CR(String s){
   if(!vectorContains(rawPhrases_Abstract_CR,s)){
     this.rawPhrases_Abstract_CR.addElement(s);
   }
}
 public void addFragmentAbstract_Coord(String s){
   if(!vectorContains(rawPhrases_Abstract_Coordinates,s)){
     this.rawPhrases_Abstract_Coordinates.addElement(s);
   }
}



   public void saveFrags(String n){
     try {
         if(n.equalsIgnoreCase("f")){
         inputFileFigural = new File("okfrags_Figural2.csv");
         FileOutputStream outFigural = new FileOutputStream(inputFileFigural);
         PrintWriter outFigural2 = new PrintWriter(outFigural);
         for (int i = 0; i < rawPhrases_Figural.size(); i++) {
           System.out.println("Writing +"+i);
           String s = (String) rawPhrases_Figural.elementAt(i);
           outFigural2.println(s);
         }
         outFigural2.flush();
         outFigural2.close();
       }
       else if (n.equalsIgnoreCase("a")){
         inputFileAbstract = new File("okfrags_Abstract2.csv");
         FileOutputStream outAbstract = new FileOutputStream(inputFileAbstract);
         PrintWriter outAbstract2 = new PrintWriter(outAbstract);
         for (int i = 0; i < rawPhrases_Abstract.size(); i++) {
           System.out.println("Writing +"+i);
           String s = (String) rawPhrases_Abstract.elementAt(i);
           outAbstract2.println(s);
         }
         outAbstract2.flush();
         outAbstract2.close();
       }
       else if(n.equalsIgnoreCase("ac")){
            inputFileAbstract_Coordinates = new File("okfrags_Abstract_Coordinates2.csv");
            FileOutputStream outAbstract_Coordinates = new FileOutputStream(inputFileAbstract_Coordinates);
            PrintWriter outAbstract_Coordinates2 = new PrintWriter(outAbstract_Coordinates);
         for(int i = 0 ; i< rawPhrases_Abstract_Coordinates.size();i++){
           System.out.println("Writing +"+i);
           String s = (String) rawPhrases_Abstract_Coordinates.elementAt(i);
           outAbstract_Coordinates2.println(s);
         }
         outAbstract_Coordinates2.flush();
         outAbstract_Coordinates2.close();
       }
       else if(n.equalsIgnoreCase("acr")){
         inputFileAbstract_CR = new File("okfrags_Abstract_CR2.csv");
         FileOutputStream outAbstract_CR = new FileOutputStream(inputFileAbstract_CR);
         PrintWriter outAbstract_CR2 = new PrintWriter(outAbstract_CR);

         for (int i = 0; i < rawPhrases_Abstract_CR.size(); i++) {
           System.out.println("Writing +"+i);
           String s = (String) rawPhrases_Abstract_CR.elementAt(i);
           outAbstract_CR2.println(s);
         }
         outAbstract_CR2.flush();
         outAbstract_CR2.close();

       }
     }catch(Exception e){
        System.out.println("Exception writing");
        e.printStackTrace();
     }

   }



   public Vector decomposeFiguralFrags(String s){
     Vector frags = new Vector();
     StringTokenizer stz = new StringTokenizer(s," ,.?!");
     while(stz.hasMoreTokens()){
       frags.addElement(stz.nextToken());
     }
      return frags;
   }
   public Vector decomposeAbstractFragsOLD(String s){
       Vector frags = new Vector();
       StringTokenizer stz = new StringTokenizer(s," ,.?!");
       while(stz.hasMoreTokens()){
         frags.addElement(stz.nextToken());
       }
        return frags;
     }

     public Vector decomposeAbstractFrags(String s){
         Vector frags = new Vector();
         if(s.indexOf("1 st") >= 0 ){
             System.out.println("CHANGEDREPLACED " +s);
             frags.addElement("1 st");
             s=s.replaceAll("1 st"," ");
             System.out.println("CHANGED TO " +s);
         }
         else if(s.indexOf("2 nd") >= 0 ){
                      System.out.println("CHANGEDREPLACED " +s);
                      frags.addElement("2 nd");
                      s=s.replaceAll("2 nd"," ");
                      System.out.println("CHANGED TO " +s);
         }
         else if(s.indexOf("3 rd") >= 0 ){
                      System.out.println("CHANGEDREPLACED " +s);
                      frags.addElement("3 rd");
                      s=s.replaceAll("3 rd"," ");
                      System.out.println("CHANGED TO " +s);
         }
         else if(s.indexOf("4 th") >= 0 ){
                      System.out.println("CHANGEDREPLACED " +s);
                      frags.addElement("4 th");
                      s=s.replaceAll("4 th"," ");
                      System.out.println("CHANGED TO " +s);
         }
         else if(s.indexOf("5 th") >= 0 ){
                      System.out.println("CHANGEDREPLACED " +s);
                      frags.addElement("5 th");
                      s=s.replaceAll("5 th"," ");
                      System.out.println("CHANGED TO " +s);
         }
         else if(s.indexOf("6 th") >= 0 ){
                      System.out.println("CHANGEDREPLACED " +s);
                      frags.addElement("6 th");
                      s=s.replaceAll("6 th"," ");
                      System.out.println("CHANGED TO " +s);
         }
         else if(s.indexOf("7 th") >= 0 ){
                      System.out.println("CHANGEDREPLACED " +s);
                      frags.addElement("7 th");
                      s=s.replaceAll("7 th"," ");
                      System.out.println("CHANGED TO " +s);
         }
         else if(s.indexOf("8 th") >= 0 ){
                      System.out.println("CHANGEDREPLACED " +s);
                      frags.addElement("8 th");
                      s=s.replaceAll("8 th"," ");
                      System.out.println("CHANGED TO " +s);
         }
         else if(s.indexOf("9 nd") >= 0 ){
                      System.out.println("CHANGEDREPLACED " +s);
                      frags.addElement("9 th");
                      s=s.replaceAll("9 th"," ");
                      System.out.println("CHANGED TO " +s);
         }
         else if(s.indexOf("10 th") >= 0 ){
                      System.out.println("CHANGEDREPLACED " +s);
                      frags.addElement("10 th");
                      s=s.replaceAll("10 th"," ");
                      System.out.println("CHANGED TO " +s);
         }






         StringTokenizer stz = new StringTokenizer(s," ,.?!");
         while(stz.hasMoreTokens()){
           String token = stz.nextToken();
           if(token.indexOf("1st")>=0 | token.indexOf("2nd")>=0 | token.indexOf("3rd")>=0 | token.indexOf("4th")>=0 |
              token.indexOf("5th")>=0 | token.indexOf("6th")>=0 | token.indexOf("7th")>=0 | token.indexOf("8th")>=0 |
              token.indexOf("9th")>=0 | token.indexOf("10th")>=0 |
              token.indexOf("1 st")>=0 | token.indexOf("2 nd")>=0 | token.indexOf("3 rd")>=0 | token.indexOf("4 th")>=0 |
              token.indexOf("5 th")>=0 | token.indexOf("6 th")>=0 | token.indexOf("7 th")>=0 | token.indexOf("8 th")>=0 |
              token.indexOf("9 th")>=0 | token.indexOf("10 th")>=0) {
              frags.addElement(token);
           }
           else {
              StringTokenizer stz0123456789 = new StringTokenizer(token,"0123456789",true);
              while(stz0123456789.hasMoreTokens()){
                 String stz0123456789token = stz0123456789.nextToken();
                 frags.addElement(stz0123456789token);
              }

           }

         }
          return frags;
       }





     public Vector decomposeAbstractFrags_Coord(String s){
          Vector decomposedCommaSpace = new Vector();
          StringTokenizer stz = new StringTokenizer(s," ,*.xX?!()[]{}<>/");
          while(stz.hasMoreTokens()){
            decomposedCommaSpace.addElement(stz.nextToken());
          }
          Vector frags = new Vector();
          for(int i = 0 ; i < decomposedCommaSpace.size();i++){
             String stok = (String)decomposedCommaSpace.elementAt(i);
             StringTokenizer stztok = new StringTokenizer(stok,"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",true);
             while(stztok.hasMoreTokens()){
                  frags.addElement(stztok.nextToken());
             }
           }
            return frags;
        }
     public Vector decomposeAbstractFrags_CR(String s){
          Vector decomposedCommaSpace = new Vector();
          StringTokenizer stz = new StringTokenizer(s," ,.?!()[]{}<>");
          while(stz.hasMoreTokens()){
            decomposedCommaSpace.addElement(stz.nextToken());
          }
          Vector frags = new Vector();
          for(int i = 0 ; i < decomposedCommaSpace.size();i++){
             String stok = (String)decomposedCommaSpace.elementAt(i);
             StringTokenizer stztok = new StringTokenizer(stok,"crCR0123456789",true);
             while(stztok.hasMoreTokens()){
                  frags.addElement(stztok.nextToken());
             }
           }
            return frags;
        }

   public Vector findStringsWithPunctuationInString(Vector v,String s,String additionaltokens){
     s=" "+s+" ";
     String tokens =" ?.,:;"+additionaltokens;
     Vector fragments = new Vector();
     for(int i = 0 ;i<v.size();i++){
          String sphr = (String)v.elementAt(i);
          for(int j =0;j<tokens.length();j++){
             for(int k =0;k<tokens.length();k++){
               String r = tokens.substring(j,j+1);
               String r2 = tokens.substring(k,k+1);
               int startIndex = s.toLowerCase().indexOf((r2+sphr+r).toLowerCase());
               if (startIndex>-1){
                     fragments.addElement(s.substring(startIndex,startIndex+sphr.length()+2));
               }
             }

          }
     }
     return fragments;
   }
   public String findStringWithPunctuationInString(String sokFrag,String s,String additionaltokens){
     s=" "+s+" ";
     String tokens =" ?.,:;"+additionaltokens;
     for(int i = 0 ; i < tokens.length();i++){
         for(int j = 0 ; j< tokens.length();j++){
             String r = tokens.substring(i,i+1);
             String r2 = tokens.substring(j,j+1);
             int startIndex = s.toLowerCase().indexOf((r2+sokFrag+r).toLowerCase());
             if(startIndex>-1){
                return s.substring(startIndex,startIndex+sokFrag.length()+2);
             }
         }

     }
     return null;
   }



   public Vector getWots(){
      return wots;
   }
   public Vector getAcknowledgements(){
     return acknowledgements;
   }


   public Vector getClarificationFragmentFigural(String s){
      Vector fragments = findStringsWithPunctuationInString(rawPhrases_Figural,s,"()[]");
      Vector singleWordFragments = new Vector();
      String response ="";
      for(int i =0; i<fragments.size();i++){
         String sfrg = (String) fragments.elementAt(i);
         Vector vfrags = decomposeFiguralFrags(sfrg);
         for(int j=0;j<vfrags.size();j++){
               String vfrag = (String) vfrags.elementAt(j);
               if(!vectorContains(singleWordFragments,vfrag)){
                    singleWordFragments.addElement(vfrag);
                    response = response +"|"+vfrag;
               }
         }
     }
      return singleWordFragments;

   }
public Vector getClarificationFragmentAbstract_CR(String s){
   Vector fragments = findStringsWithPunctuationInString(rawPhrases_Abstract_CR,s,"");
   Vector singleWordFragments = new Vector();
   String response ="";
   for(int i =0; i<fragments.size();i++){
      String sfrg = (String) fragments.elementAt(i);
      Vector vfrags = decomposeAbstractFrags_CR(sfrg);
      for(int j=0;j<vfrags.size();j++){
           String vfrag = (String) vfrags.elementAt(j);
           if(!vectorContains(singleWordFragments,vfrag)){
                 singleWordFragments.addElement(vfrag);
                response = response +"|"+vfrag;
            }
      }
   }
   return singleWordFragments;
 }


public Vector getClarificationFragmentAbstract_Coord(String s){
   Vector fragments = findStringsWithPunctuationInString(rawPhrases_Abstract_Coordinates,s,"");
   Vector singleWordFragments = new Vector();
   String response ="";
   for(int i =0; i<fragments.size();i++){
    String sfrg = (String) fragments.elementAt(i);
    Vector vfrags = decomposeAbstractFrags_Coord(sfrg);
    for(int j=0;j<vfrags.size();j++){
         String vfrag = (String) vfrags.elementAt(j);
         if(!vectorContains(singleWordFragments,vfrag)){
               singleWordFragments.addElement(vfrag);
              response = response +"|"+vfrag;
          }
    }
 }
 return singleWordFragments;
}



  public Vector getClarificationFragmentAbstract(String s){
     Vector fragments = findStringsWithPunctuationInString(rawPhrases_Abstract,s,"[]'");
     Vector singleWordFragments = new Vector();
     String response ="";
     for(int i =0; i<fragments.size();i++){
        String sfrg = (String) fragments.elementAt(i);
        Vector vfrags = decomposeAbstractFrags(sfrg);
        for(int j=0;j<vfrags.size();j++){
             String vfrag = (String) vfrags.elementAt(j);
             if(!vectorContains(singleWordFragments,vfrag)){
                   singleWordFragments.addElement(vfrag);
                  response = response +"|"+vfrag;
              }
        }
     }
     return singleWordFragments;
   }


   public String getFirstPermittedClarificationFragmentAbstract(String s,Vector nonPhrases){
     //for each permitted fragment :
        //see if it is contained in the chattext
        //If it is contained in the chattext
             //decompose that fragment into potential clarifications
             //Filter all of them so that there are no nonpermitted ones
             //Select one randomly

  for(int i = 0 ; i < this.jumblePhrases_Abstract.size();i++){
      String sokfrag = (String) this.jumblePhrases_Abstract.elementAt(i);
      String spotentialfrag = this.findStringWithPunctuationInString(sokfrag,s,"[]'");
      if(spotentialfrag!=null){
           Vector vfrags = decomposeAbstractFrags(spotentialfrag);
           Vector vfragsPermitted = removeElementsFromVector(vfrags,nonPhrases);
           if (vfragsPermitted.size()!=0){
              int r2 = r.nextInt(vfragsPermitted.size());
              return (String)vfragsPermitted.elementAt(r2);
           }
      }

  }



  return null;

   }

   public String getFirstPermittedClarificationFragmentFigural(String s,Vector nonPhrases){
       //for each permitted fragment :
             //see if it is contained in the chattext
             //If it is contained in the chattext
                  //decompose that fragment into potential clarifications
                  //Filter all of them so that there are no nonpermitted ones
                  //Select one randomly

       for(int i = 0 ; i < this.jumblePhrases_Figural.size();i++){
           String sokfrag = (String) this.jumblePhrases_Figural.elementAt(i);
           String spotentialfrag = this.findStringWithPunctuationInString(sokfrag,s,"()[]");
            if(spotentialfrag!=null){
                Vector vfrags = decomposeFiguralFrags(spotentialfrag);
                Vector vfragsPermitted = removeElementsFromVector(vfrags,nonPhrases);
                if (vfragsPermitted.size()!=0){
                   int r2 = r.nextInt(vfragsPermitted.size());
                   return (String)vfragsPermitted.elementAt(r2);
                }
           }

       }



       return null;
   }
   public String getFirstPermittedClarificationFragmentAbstract_CR(String s,Vector nonPhrases){
        //for each permitted fragment :
        //see if it is contained in the chattext
        //If it is contained in the chattext
             //decompose that fragment into potential clarifications
             //Filter all of them so that there are no nonpermitted ones
             //Select one randomly

  for(int i = 0 ; i < this.jumblePhrases_Abstract_CR.size();i++){
      String sokfrag = (String) this.jumblePhrases_Abstract_CR.elementAt(i);
      String spotentialfrag = this.findStringWithPunctuationInString(sokfrag,s,"()[]");
       if(spotentialfrag!=null){
            Vector vfrags = decomposeAbstractFrags_CR(spotentialfrag);
           Vector vfragsPermitted = removeElementsFromVector(vfrags,nonPhrases);
           if (vfragsPermitted.size()!=0){
              int r2 = r.nextInt(vfragsPermitted.size());
              return (String)vfragsPermitted.elementAt(r2);
           }
      }

  }



  return null;

   }
   public String getFirstPermittedClarificationFragmentAbstract_Coord(String s,Vector nonPhrases){
     //for each permitted fragment :
    //see if it is contained in the chattext
    //If it is contained in the chattext
         //decompose that fragment into potential clarifications
         //Filter all of them so that there are no nonpermitted ones
         //Select one randomly

for(int i = 0 ; i < this.jumblePhrases_Abstract_Coordinates.size();i++){
  String sokfrag = (String) this.jumblePhrases_Abstract_Coordinates.elementAt(i);
  String spotentialfrag = this.findStringWithPunctuationInString(sokfrag,s,"");
   if(spotentialfrag!=null){
       Vector vfrags = decomposeAbstractFrags_Coord(spotentialfrag);
       Vector vfragsPermitted = removeElementsFromVector(vfrags,nonPhrases);
       if (vfragsPermitted.size()!=0){
          int r2 = r.nextInt(vfragsPermitted.size());
          return (String)vfragsPermitted.elementAt(r2);
       }
  }

}



return null;

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



}
