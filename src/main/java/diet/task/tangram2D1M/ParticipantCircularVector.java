/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

import java.util.Vector;

import diet.server.Participant;

/**
 *
 * @author Arash
 */
public class ParticipantCircularVector {
    
    Vector<Participant> participants;
    int capacity;
    public ParticipantCircularVector(int n)
    {
        participants=new Vector<Participant>();
        capacity=n;
    }
    
    public synchronized void addToTalkSeq(Participant p)
    {
        if (participants.size()<this.capacity)
        {
            participants.add(p);
        }
        else
        {
            participants.add(p);
            participants.remove(0);
        }
        
    }
    public synchronized Participant lastSpeaker()
    {
        return participants.lastElement();
    }
    public synchronized Participant penultimateSpeaker()
    {
        Participant last=participants.lastElement();
        for(int i=participants.size()-2;i>=0;i--)
        {
            if (participants.elementAt(i)!=last)
            {
                return participants.elementAt(i);
            }
        }
        return last;
    }

}
