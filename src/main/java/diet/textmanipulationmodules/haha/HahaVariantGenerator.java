/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.textmanipulationmodules.haha;

import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author g
 */
public class HahaVariantGenerator {
    
    
    public int maxlength = 12;
    Vector<String> allVariants =  new Vector();
    Vector<String> allVariantsSORTED =  new Vector();
    
     public HahaVariantGenerator(){
          this.generateAllVariants();
          allVariantsSORTED =  sortByLength(allVariants);

     }
     
     public void generateAllVariants(){
          Vector ha = new Vector();
          Vector hha = new Vector();
          Vector hhha = new Vector();
          
          
          Vector ahah = new Vector();
          
          Vector aahah = new Vector();
          Vector ahhah = new Vector();
          
          Vector aaahah = new Vector();
          Vector ahhhah = new Vector();
          
          Vector ahaah = new Vector();
          
          Vector ahahh = new Vector();
          
         
          
          
          
          generateVVariants(ha,"ha");
          generateVVariants(hha, "hha");
          generateVVariants(hhha , "hhha");
          
          
          generateVVariants(ahah , "ahah");
          generateVVariants(aahah,  "aahah");
          generateVVariants(ahhah , "ahhah");
          
          generateVVariants(aaahah , "aaahah");
          generateVVariants(ahhhah , "ahhhah");
          
          generateVVariants(ahaah , "ahaah");
          
          generateVVariants(ahahh, "ahahh");
          
          
       
          
          allVariants.addAll(ha);
          allVariants.addAll(hha);
          allVariants.addAll(hhha);
          allVariants.addAll(ahah);
          allVariants.addAll(aahah);
          allVariants.addAll(ahhah);
          allVariants.addAll(aaahah);
          allVariants.addAll(ahhhah);
          allVariants.addAll(ahaah);
          allVariants.addAll(ahahh);
          
     }
    
    
       static Vector<Object> function_that_takes_a_b_merges(Vector<Object> Va, Vector<Object> Vb) {
          Vector<Object> merge = new Vector<Object>();
          merge.addAll(Va);
          merge.addAll(Vb);
          return merge;
       }
    
     
        public static Vector<String> sortByLength(Vector<String> v){
            Vector<String> vSorted = new Vector();
            for(int i=0;i<v.size();i++){
                
                 String s = v.elementAt(i);
                 int idx=0;
                 for(int j=0;j<vSorted.size();j++){
                     if(s.length()>=vSorted.elementAt(j).length()){
                         idx = j;
                         break;
                     }
                     else{
                         //System.err.println(s);
                     }
                 }
                 vSorted.insertElementAt(s, idx);
            }
            return vSorted;
        }
     
     
        public  void generateVVariants(Vector v, String s){
          
            
             v.addElement(" "+s+" ");
            if(s.length()>maxlength){
                //v.addElement(s);
                //System.out.print(".");
                return;
            }
            else{
               
               
                generateVVariants(v,s+"h");
                
                generateVVariants(v,s+"a");
            }
            
     }
    
        
      public String getLargestHahaContainedInString(String s){
          try{
          for(int i=0;i<this.allVariantsSORTED.size();i++){
              if(s.contains(this.allVariantsSORTED.elementAt(i))){
                  return this.allVariantsSORTED.elementAt(i);
              }
          }
          }catch(Exception e){
              e.printStackTrace();
              Conversation.saveErr(e);
          
          }
          return null;
      }
        
      
      
      public  int[] doesTextContainHaha(String originalTurn){
        try{
        int[] d = {0,0};
        String originalTurnLower = originalTurn.toLowerCase();
        
        for(int i=0; i< allVariantsSORTED.size();i++){
             int startIndex = originalTurnLower.indexOf(allVariantsSORTED.elementAt(i));
             if(startIndex>=0){
                 
                 Conversation.printWSln("Main", "Found haha! startindex:"+startIndex+ " length:"+allVariantsSORTED.elementAt(i).length());
                 d[0] = startIndex;
                 d[1] = allVariantsSORTED.elementAt(i).length();
                 return d;
             }
        }
        }catch(Exception e){
            e.printStackTrace();
           
        }
         return null;
         
         
         
        
    }
        
      
      
      
      public String filterOutHaha(String source){
          
          String source_spaceprefix_spacesuffix =  " "+source+" ";
           String source_cleaned = source.replaceAll("[^A-Za-z0-9]", " ");
           String source_cleaned_spaceprefix_space_suffix = " "+source_cleaned + " ";
           
           int[] d = this.doesTextContainHaha(source_cleaned_spaceprefix_space_suffix);
           String filtered ="";
           
           //******** HAHA ******
           //        <---->
           if(d==null)return null;
           if(d[0]==0){ //Turn starts with haha
               filtered = source_spaceprefix_spacesuffix.substring(d[1]-1).trim();
               return filtered;
           }
           else if(d[0]>0){ //Turn starts with haha
               String filteredleftpart  = source_spaceprefix_spacesuffix.substring(0,d[0]+1);
               String filteredrightpart = source_spaceprefix_spacesuffix.substring(d[0]+d[1]-1);
               filtered = (filteredleftpart +""+filteredrightpart).trim();
               return filtered;
           }
           return null;
      }
      
      
      
      
      public  void processTurnsToSeeIfTheyContainHaha(){
          File f = CustomDialog.loadFile(System.getProperty("user.dir"));     
          Vector<String> v = loadFile(f);
          Vector<String> vOut = new Vector();
          for(int i =0; i<v.size();i++){
              String s = v.elementAt(i);
              String sFiltered = this.filterOutHaha(s);
              if(sFiltered==null){
                  vOut.addElement("NON");
              }
              else if(sFiltered.length()<s.length()){
                  System.out.println("FOUND HAHA"+ s);
                  vOut.addElement("HAHA");
              }
              else if(sFiltered.length()==s.length()){
                  
              }
              else{
                  System.err.println("CRASHED ON:"+v.elementAt(i));
                  System.exit(-56);
              }
              
          }
          
          if(v.size()!=vOut.size()){
              CustomDialog.showDialog("Input and Output are NOT the same size: "+v.size()+" vs."+vOut.size());
          }
          else{
              this.saveFile(f, vOut);
          }
          
      }
      
    public void   saveFile(File originalfile, Vector<String> data){
                File nf = new File(originalfile.getAbsoluteFile()+"OUTPUT");
                System.out.println("writing to: "+nf.getAbsolutePath());
                System.out.println("writing to: "+nf.getName());

       
            try {
                OutputStreamWriter osw=
             new OutputStreamWriter(new FileOutputStream(nf), StandardCharsets.UTF_8);
  
                for(int i=0;i<data.size();i++){
                    System.out.println("WRITING:"+data.elementAt(i));
                    String s = data.elementAt(i);
                    osw.write(s+"\n");
                }
                
                osw.close();
            }catch(Exception e){
                  e.printStackTrace();
            }
    }
      
      
      
      
     public Vector<String> loadFile(File f){
           Vector<String> v= new Vector();
           InputStream ins = null; // raw byte-stream
           Reader r = null; // cooked reader
           BufferedReader br = null; // buffered for readLine()
           try {
               String s;
               ins = new FileInputStream(f);
               r = new InputStreamReader(ins, "UTF-8"); // leave charset out for default
               br = new BufferedReader(r);
               while ((s = br.readLine()) != null) {
                   System.out.println(s);
                   v.addElement(s);
               }
           }
           catch (Exception e)
           {
               System.err.println(e.getMessage()); // handle exception
           }
           finally {
               if (br != null) { try { br.close(); } catch(Throwable t) { /* ensure close happens */ } }
               if (r != null) { try { r.close(); } catch(Throwable t) { /* ensure close happens */ } }
               if (ins != null) { try { ins.close(); } catch(Throwable t) { /* ensure close happens */ } }
               
        }
        return v;
   }

    
      public static void main (String[] args){
           
           
          
          
           HahaVariantGenerator hvg = new HahaVariantGenerator();
           hvg.processTurnsToSeeIfTheyContainHaha();
           System.exit(-568);
           
           //if end index is too big..shorten it
           
           //String teststring ="123456789";
           //System.out.println(teststring.substring(0,3));
           //System.out.println(teststring.substring(3,teststring.length()));
           //haha
           //haha.
           //hah
           //thah
           //hah.that is
           //haha haha haha
           //haha this is funny
           //haha    this is funny
           // haha this is so funny
           // ha
           // hatch
           //er terte haha
           //er rerert haha  (SPACE)
           
           
           
           
           System.out.println(hvg.filterOutHaha("haha"));
           System.exit(-56);
          
           long startTime = new Date().getTime();
           
           //HahaVariantGenerator hvg = new HahaVariantGenerator();
           
           Vector<String> v = hvg.allVariantsSORTED;
          
           
           for(int i=0;i<v.size();i++){
               
                  // System.out.println(v.elementAt(i));
               
           }
           
           
           System.out.println(v.size());
           System.out.println(new Date().getTime()-startTime);
           
           startTime = new Date().getTime();
            String s = hvg.getLargestHahaContainedInString("rqerqrqerqewrqwer qer qewr qer haha");
            System.out.println(s);
            System.out.println(new Date().getTime()-startTime);
      }
      
}





/*

    String[] hahavariants = { 
       
        "haha", 
        "hahah", 
        "hahaha", 
        "hahahah", 
        "hahahaha",
        "hahahahah",
        "hahahahaha",
        
        "haaha", 
        "haahah", 
        "haahaha", 
        "haahahah", 
        "haahahaha",
        "haahahahah",
        "haahahahaha",
        
        "haahaa", 
        "haahaha", 
        "haahaaha", 
        "haahaahah", 
        "haahaahaha",
        "haahaahahah",
        "haahaahahaha",
        
        "haaahaa", 
        "haaahaaha", 
        "haaahaaha", 
        "haaahaahah", 
        "haaahaahaha",
        "haaahaahahah",
        "haaahaahahaha",
        
        "ahaha", 
        "ahahah", 
        "ahahaha", 
        "ahahahah", 
        "ahahahaha",
        "ahahahahah",
        "ahahahahaha",
        
        "aahaha", 
        "aahahah", 
        "aahahaha", 
        "aahahahah", 
        "aahahahaha",
        "aahahahahah",
        "aahahahahaha",
        
        "aaahaha", 
        "aaahahah", 
        "aaahahaha", 
        "aaahahahah", 
        "aaahahahaha",
        "aaahahahahah",
        "aaahahahahaha",
        
        "aahaha", 
        "ahahah", 
        "ahahaha", 
        "ahahahah", 
        "ahahahaha",
        "ahahahahah",
        "ahahahahaha",
        
        "hhah",
        "hhaha",
        "hhahahaa",
        "hhahhaaa",
        
        "hahaa",
        "hahahaa",
        "hahahaaa",
        "haahaa",
        "haaahaaa",
        "haahaaaa",
        "hahahaah",
        "hahahaaah",
        "hahahaaaah",
        "hahaaahahaa",
        
        "hahaah",
        "hahaaha",
        
        "hahaaheh",
        "hahaheh",
        "ahhaah",
        
        "hahha",
        "hahhaha",
        "hahahahhaa",
        "hahahha",
        "hhhahhah", 
        "aahahaahhaha",
            
        "ahhaha",
        "hahahha",
            
        "hahhhaha",
        "hahhhaha",
        "hahhhahah"
*/