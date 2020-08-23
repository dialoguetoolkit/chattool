/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

/**
 *
 * @author Arash
 */
import java.util.LinkedList;
import java.util.Queue;
public class TangramUtteranceSyncQueue {
    
    Queue<TangramUtterance> q;
    
    public TangramUtteranceSyncQueue()
    {
        q=new LinkedList<TangramUtterance>();
        
    }
    
    
    public synchronized TangramUtterance poll()
    {
        return q.poll();
    }
    public synchronized void offer(TangramUtterance u)
    {
        q.offer(u);
    }
    public synchronized TangramUtterance peek()
    {
        return q.peek();
    }
    public boolean isEmpty()
    {
        return q.isEmpty();
    }
}
    
    


