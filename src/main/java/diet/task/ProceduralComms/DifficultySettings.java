/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.ProceduralComms;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author g
 */
public class DifficultySettings {
    
     Random r = new Random();
     Hashtable ht = new Hashtable();
     Vector<Vector<String>> allPossibleSequences = new Vector();

    
    
    public static void main(String[] args){
        DifficultySettings ds = new DifficultySettings();
    }
   
     
     
    public DifficultySettings() {
        initialize();
    }
    
 
    
     
     
     public void calculateDifficulty( int nONLYOtherBothShared_MSS, int nONLYOtherNotShared_MSN, int nONLYSelf_DS, int nANDSame_DMS, int nANDDifferent_DMD){
             
     }
    
     
     public void initialize(){
          generateAllVariants(new Vector<String>(),"");
           
        for(int i=0;i<this.allPossibleSequences.size();i++){
            Vector vseq = (Vector)allPossibleSequences.elementAt(i);
            calculateDifficulty(vseq);
            
            
        }
       
        
        String prettyp = this.prettyPrintAll();
        System.err.println(prettyp);
     }
    
     
     
     
     
     public void generateAllVariants(Vector<String> vs, String appendText){
        if(vs.size()==5)return;
       
        Vector<String> vsclone=      (Vector<String>)vs.clone();
        if(!appendText.equalsIgnoreCase(""))vsclone.add(appendText);
        
        generateAllVariants(vsclone, "MS");
        generateAllVariants(vsclone, "DS");
        generateAllVariants(vsclone, "DMS");
       
        
       
        if(isPermissible(vsclone)){
            allPossibleSequences.add(vsclone);
            //calculateDifficulty(vsclone);
           // printV(vsclone);
        }
   }
    

     public  void calculateDifficulty( Vector<String> vs){
        int switchcount=0;
        int singlenotescount=0;
        int simnotescount=0;
       
        
        
        for(int i=0;i<vs.size();i++){
               
               if(i>0&&!vs.elementAt(i).equalsIgnoreCase(vs.elementAt(i-1)))switchcount++; 
               if(vs.elementAt(i).equalsIgnoreCase("DS")||vs.elementAt(i).equalsIgnoreCase("MS")   ) singlenotescount++;
               if(vs.elementAt(i).equalsIgnoreCase("DMS")  )simnotescount++;
                 
        }
        
        int difficulty =   Math.min(switchcount, PCTaskTG.difficultysettings_maxSwitchCost) +  singlenotescount*PCTaskTG.difficulty_settings_singleNotesCoef +  simnotescount*PCTaskTG.difficultysettings_simulNotesCoef  ;
        difficulty=difficulty-1;
        if(difficulty<0)difficulty=0;
        
        
        Object o = ht.get(difficulty);
        if(o==null){
            Vector vvv = new Vector();
            vvv.addElement(vs);
            ht.put(difficulty, vvv);
        }
        else{
            Vector vvv = (Vector)o;
            vvv.addElement(vs);
            ht.put(difficulty, vvv);
        }
        //System.err.println(switchcount+singlenotescount+simnotescount+encnotescount);           
    }
     
     
     
     


public  boolean isPermissible(Vector<String> vs){
        for(int i=0;i<vs.size();i++){
            if(!vs.elementAt(i).equalsIgnoreCase("DS")){
                //System.err.println("PERMISSIBLE:"+vs.elementAt(i)+".");
                return true;
            }
        }
        //System.err.println("NOTPERMISSIBLE");
        return false;
    }


     public String prettyPrintAll(){
                
        
        String text ="SimultaneousNotes Coefficient: "+PCTaskTG.difficultysettings_simulNotesCoef+"\n"
                + "SingleNotes Coefficient: "+  PCTaskTG.difficulty_settings_singleNotesCoef+"\n"
               ;
        
        
        Set keyset = ht.keySet();
        Iterator  sit = keyset.iterator();
        System.err.println("Going through keyset" );
        while(sit.hasNext()){
            Integer key = (int)sit.next();
            text = text+ ("\nDifficulty level: "+key+"");
            Vector v =(Vector) ht.get(key);
            text = text + (" has "+v.size()+ ".\n");
            for(int i=0;i<v.size();i++){
                Vector vv =(Vector) v.elementAt(i);
                for(int j=0;j<vv.size();j++){
                    Object o = vv.elementAt(j);
                    text = text + " "+(String)o;
                    //System.err.print(o+ " ");
                }
               text = text +"\n"; 
               //System.err.println("");
            }
            
            
        }
        return text;
    }
     
     public  void printV(Vector vs){
        for(int i=0;i<vs.size();i++){
               System.err.print(vs.elementAt(i)+",");
           }
           System.err.println("");
           return;
    }
     
     

     public Vector returnSequenceForLevelOrLower(int targetLevel){
          System.err.println("Trying to find sequence for level "+targetLevel);
          Vector v = this.returnSequenceForLevel(targetLevel);
          while(v==null || v.size()==0){
              targetLevel=targetLevel-1;
              System.err.println("Loop: Trying to find sequence for level "+targetLevel);

              v = this.returnSequenceForLevel(targetLevel);
          }
          this.printV(v);
          return v;
     }
     
     
     
     private Vector returnSequenceForLevel(int targetlevel){
        
        for(int i =targetlevel;i>=0;i--){
               Vector v =(Vector) ht.get(i);
               if(v!=null){
                   int idx = r.nextInt(v.size());
                   
                   return (Vector)v.elementAt(idx);
               }
               else{
                   //System.err.println("Generating for targetlevel: "+targetlevel+ " could not find target...looking for decrement "+i);
               }
               
        }
        System.err.println("Returning empty vector...this shouldn't happen");
        return new Vector();
       
        
    }

}