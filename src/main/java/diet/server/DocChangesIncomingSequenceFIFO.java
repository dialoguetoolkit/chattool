/*
 * DocumentChangesIncomingSequence.java
 *
 * Created on 23 October 2007, 23:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server;


import java.io.Serializable;
import java.util.Vector;

import diet.client.DocumentChange.DocInsert;
import diet.client.DocumentChange.DocRemove;

/**
 * Each Participant has an instance of this queue which records all the changes made to the text in their window
 * during the most recent turn. It is continuously updated. It records the entire process involved in
 * constructing the turn, consequently it contains information about all the characters deleted.
 * 
 * @author user
 */
public class DocChangesIncomingSequenceFIFO implements Serializable {
    

    private StringBuilder sequence = new StringBuilder(); 
    private Vector allInsertsAndRemoves = new Vector();
    private long startTime;
   
    public DocChangesIncomingSequenceFIFO(long startTime) {
       this.startTime = startTime;
    }
    
    /**
     * Adds an insert to the Vector of inserts/removes associated with a participant
     * @param s text that is inserted
     * @param offsetFrmRight offset of insert
     * @param timestamp timestamp of insert
     */
    public void insert(String s,int offsetFrmRight,long timestamp){
        try{
          int offsetFrmLeft = sequence.length()-offsetFrmRight;
          if(offsetFrmLeft<0)offsetFrmLeft=0;
          if(offsetFrmLeft>sequence.length())offsetFrmLeft=sequence.length();
          
          //System.err.println("-----------INSERTING "+s+" with "+s.length());
          
          sequence.insert(offsetFrmLeft,s);
          
          //System.err.println("-----------SHOULD BE SAME LENGTH AS "+sequence.length());
          
        }catch(Exception e){
            System.err.println("Error in DocumentChangeSequenceOffsetFromRight Inserting "+s+" offsetFrmRight: "+offsetFrmRight);
        }  
          DocInsert insert = new DocInsert(offsetFrmRight,s,null);
          System.err.println("---------------------------------------------------ADDING DOCINSERT1: ("+s+")");
          insert.setTimestamp(timestamp);
          allInsertsAndRemoves.addElement(insert);
    }
    
    
    
    /**
     * 
     * Adds an insert to the Vector of inserts/removes associated with a participant
     * @param DocInsert
     * @param timestamp timestamp of insert
     * @param insert
     */
    public void insert(DocInsert insert,long timestamp){
        System.err.println("---------------------------------------------------ADDING DOCINSERT2");
        insert.setTimestamp(timestamp);
        allInsertsAndRemoves.addElement(insert);
        try{
          int offsetFrmLeft = sequence.length()-insert.getOffs();
          if(offsetFrmLeft<0)offsetFrmLeft=0;
          if(offsetFrmLeft>sequence.length())offsetFrmLeft=sequence.length();
        }catch (Exception e){
            System.err.println("Error in DocumentChangeSequenceOffsetFromRight Inserting "+insert.getStr()+" offsetFrmRight: "+insert.getOffs());
        }  
        sequence.insert(sequence.length()-insert.getOffs(),insert.getStr());
    }
    
   
    /**
     * 
     * 
     * Adds a remove DocChange to the Vector of inserts/removes associated with a participant
     * 
     * @param remove
     * @param timestamp
     */
    public void remove(DocRemove remove,long timestamp){
      remove.setTimestamp(timestamp);  
      allInsertsAndRemoves.addElement(remove);
      try{
          int offsetFrmLeft = sequence.length()-remove.getOffs();
          if(offsetFrmLeft<0)offsetFrmLeft=0;
          if(offsetFrmLeft>sequence.length())offsetFrmLeft=sequence.length();
          int len = remove.getLen();
          if(offsetFrmLeft+len>sequence.length())len = sequence.length()-offsetFrmLeft;
          
          sequence.replace(offsetFrmLeft,offsetFrmLeft+remove.getLen(),"");
      }    
      catch (Exception e){
            System.err.println("Error in DocumentChangeSequenceOffsetFromRight Removing offsetFrmRight: "+remove.getOffs()+" "+remove.getLen());
        }          
    }
    /**
     * Adds a remove DocChange to the Vector of inserts/removes associated with a participant
     * @param offsetFrmRight
     * @param length
     * @param timestamp
     */
    public void remove(int offsetFrmRight,int length, long timestamp){
        DocRemove remove = new DocRemove(offsetFrmRight,length);
        remove.setTimestamp(timestamp);
        allInsertsAndRemoves.addElement(remove);
        try{
          int offsetFrmLeft = sequence.length()-remove.getOffs();
          if(offsetFrmLeft<0)offsetFrmLeft=0;
          if(offsetFrmLeft>sequence.length())offsetFrmLeft=sequence.length();
          int len = remove.getLen();
          if(offsetFrmLeft+len>sequence.length())len = sequence.length()-offsetFrmLeft;  
          //sequence.replace(sequence.length()-offsetFrmRight,sequence.length()-offsetFrmRight+length,"");
          sequence.replace(offsetFrmLeft,offsetFrmLeft+remove.getLen(),"");
        }catch (Exception e){
            System.err.println("Error in DocumentChangeSequenceOffsetFromRight Removing offsetFrmRight2: "+remove.getOffs()+" "+remove.getLen());
        }   
           
    }
   
    /**
     * 
     * @return a String representation of the turn (a reconstruction of the turn construction)
     */
    public String getParsedText(){
        return sequence.toString();
    }
    
    public long getStartTime(){
        return this.startTime;
    }
    public long getEndOfTyping(){
        
        if(allInsertsAndRemoves.size()==0)return this.startTime;
        Object o = allInsertsAndRemoves.lastElement();
        if( o instanceof DocRemove){
            DocRemove dir = (DocRemove)o;
            return dir.getTimestamp();
        }
        else {
            DocInsert dins = (DocInsert)o;
            return dins.getTimestamp();
        }            
    }
    public Vector getAllInsertsAndRemoves(){
        return allInsertsAndRemoves;
    }
    
    /**
     * Calculates the numbers of characters deleted during the construction of the turn
     * @param v
     * @return
     */
    static public int getTotalCharsDeleted(Vector v){
        //if(Debug.verboseOUTPUT)System.out.println("GETTING TOTAL CHARS DELETED..........................................Vsize() is"+v.size());
        int charsDeleted =0;
        for(int i=0;i<v.size();i++){
            Object o = v.elementAt(i);
            System.out.println("--"+i+":");
            if(o instanceof DocRemove){
                DocRemove docIR = (DocRemove)o;
                charsDeleted = charsDeleted+docIR.getLen();
                //System.out.println("GETTING TOTAL CHARS DELETED FOR: "+"---"+i+":"+docIR.getOffs()+":"+docIR.getLen());
                System.out.println("--DOCREMOVE"+i+":"+docIR.getOffs()+":"+docIR.getLen());
                
            }
            
        }
        //if(Debug.verboseOUTPUT)System.out.println("----");
        return charsDeleted;
    }
    /**
     * Calculates the number of characters inserted during the construction of the turn
     * @param v
     * @return
     */
    static public int getTotalCharsInsertedOutsideTurnTerminal(Vector v){
         int charsInserted =0;
        for(int i=0;i<v.size();i++){
            Object o = v.elementAt(i);
            if(o instanceof DocInsert){
                DocInsert docINS = (DocInsert)o;
                if(docINS.getOffs()>0){
                    charsInserted = charsInserted+docINS.getStr().length();
                    //if(Debug.verboseOUTPUT)System.out.println("---------------------------------------------------FOUND DOCINSERT1: ("+docINS.getStr()+") has length:"+docINS.getStr().length());
                }
            }
        }
        //if(Debug.verboseOUTPUT)System.out.println("------------------------------------------RETURNING DOCINSERT1 WITH LENGTH: "+charsInserted);
        return charsInserted;
    }
    
    
    /**
     * Calculates the DDels measurement which multiplies the number of deletes by their position in the string
     * @param v
     * @return
     */
    static public int getDelsScore(Vector v){
        int charsEditedSum=0;
        for(int i=0;i<v.size();i++){
            Object o = v.elementAt(i);      
            if(o instanceof DocRemove){
                DocRemove docIR = (DocRemove)o;
                charsEditedSum = charsEditedSum + docIR.getLen()*docIR.getOffs();
            }
        }
        return charsEditedSum;
    }
    
    /**
     * Calculates the DIns measurement which multiplies the number of inserts by their position in the string
     * @param v
     * @return
     */
    static public int getInsScore(Vector v){
        int charsEditedSum=0;
        for(int i=0;i<v.size();i++){
            Object o = v.elementAt(i);
            if(o instanceof DocInsert){
                DocInsert docINS = (DocInsert)o;
                if(docINS.getOffs()>0)charsEditedSum = charsEditedSum + docINS.getStr().length()*docINS.getOffs();
            }
        }
        return charsEditedSum;
    }    
    
    /**
     * Calculates the sum of DIns and DDels
     * @param v
     * @return
     */
    static public int getEditScore(Vector v){
        int charsEditedSum =0;
        for(int i=0;i<v.size();i++){
            Object o = v.elementAt(i);
            if(o instanceof DocInsert){
                DocInsert docINS = (DocInsert)o;
                if(docINS.getOffs()>0)charsEditedSum = charsEditedSum + docINS.getStr().length()*docINS.getOffs();
            }
            else if(o instanceof DocRemove){
                DocRemove docIR = (DocRemove)o;
                charsEditedSum = charsEditedSum + docIR.getLen()*docIR.getOffs();
            }
        }
        return charsEditedSum;  
    }
}