/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.textmanipulationmodules.haha;

import diet.server.Conversation;
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
          
            
             v.addElement(s);
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
        return null;
        
    }
        
    
      public static void main (String[] args){
           
          
          
           long startTime = new Date().getTime();
           
           HahaVariantGenerator hvg = new HahaVariantGenerator();
           
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