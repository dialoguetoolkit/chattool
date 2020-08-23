/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * This is the generic super class of Self/Other and Collective/dyadic tangram sequences.
 * sequence is a array of strings where each member represents who can see the tangram
 * If A director A, if B director B and if AB, both directors.
 * @author Arash
 */
public abstract class TangramSequence implements Serializable{

    public String[] sequence;
    public final int[] interventionSlots={3,6,9,11};
    private int cur;
    protected Random r=new Random(new Date().getTime());
    public TangramSequence()
    {
        cur=0;

        sequence=new String[12];
        setNewRandomSequence();
    }

    public String whichDirectors(int index)
    {
        return sequence[index];
    }

    public abstract void setNewRandomSequence();

    public String getSubsequenceAsString(int begin, int end) //exclusive of end
    {
        String result="("+sequence[begin]+")("+sequence[end-1]+")";
        return result;
    }
    public void advanceInterventionPointer()
    {
        cur=(cur+1)%interventionSlots.length;
    }
    public boolean interventionTime(int slot)
    {
        return slot==interventionSlots[cur];
    }
    public String toString()
    {
        String result="";
        for(int i=0;i<sequence.length;i++)
        {
            result+= "("+sequence[i]+")";
        }
        return result;
    }
}
