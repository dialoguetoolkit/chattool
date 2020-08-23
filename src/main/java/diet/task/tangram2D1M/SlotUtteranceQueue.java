/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

/**
 *
 * @author Arash
 */

//this class assumes that slotutterances are offered in order. So e.g. an utterance beloging 
//to slot 4 does is not offered before an utterance from slot 3.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
public class SlotUtteranceQueue extends Thread{
    
    List<SlotUtterance> q;
    
    int curIndex=0;
    public SlotUtteranceQueue()
    {
        q=Collections.synchronizedList(new ArrayList<SlotUtterance>());
        
    }
    
    
    public synchronized List<SlotUtterance> getUtterancesFromSlot(int slotUD)
    {
        List<SlotUtterance> result=new ArrayList<SlotUtterance>();
        synchronized(q){
        for(SlotUtterance u:q)
        {
            if (u.slotUD>slotUD) return result;
            if (u.slotUD==slotUD) result.add(u);
        }}
        return result;
    }
    public synchronized void offer(SlotUtterance u)
    {
        int slotUD=u.slotUD;
        String utt=u.utterance;
        String[] sentences=utt.split("[.!?]");
        synchronized(q)
        {
            for(String sentence:sentences)
            {
            
                if (sentence.length()>0) q.add(new SlotUtterance(sentence, slotUD));
            }
        }
        if (slotUD>3) removeOldUpToSlotIndex(slotUD-3);
        
        
    }
    
    
    public synchronized void removeOldUpToSlotIndex(int i)
    {
        
        
        synchronized(q){
            ListIterator<SlotUtterance> j=q.listIterator();
            while(j.hasNext())
            {
                SlotUtterance u=j.next();
                if (u.slotUD<i) 
                {
                    if (q.indexOf(u)<curIndex)
                    {
                        curIndex--;
                    }
                
                    
                    j.remove();
                }
            }
        }
        
        System.out.println("removeOld called. curIndex is "+curIndex+" size is: "+q.size());
    }
    public synchronized SlotUtterance peek()
    {
        
        if (q.isEmpty()) return null;
        SlotUtterance u=q.get(curIndex);
        advanceIndex();
        System.out.println("peeked. curIndex is "+curIndex+" size is: "+q.size());
        return u;
    }
    public synchronized void advanceIndex()
    {
        if (q.isEmpty()) curIndex=0;
        else curIndex=(curIndex+1)%q.size();
    }
    //should always be called right after peek() with the same returned slotutterance from peek
    public synchronized void remove(SlotUtterance t)
    {
        int indexRemoved; 
    
        if (q.contains(t))
        {
            indexRemoved=q.indexOf(t);
        }
        else return;
        if (indexRemoved<curIndex)
        {
            curIndex--;
        }    
        q.remove(t);
        //System.out.println("remove caleed. curIndex is "+curIndex+" size is: "+q.size());
    }
    
    public boolean isEmpty()
    {
        
        return q.isEmpty();
    }
    
    
   
}
    
    


