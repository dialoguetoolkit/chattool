/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

/**
 *
 * @author Arash
 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;
public abstract class TangramPanel extends JPanel{
    
    protected Vector<Slot> components=new Vector<Slot>();
    protected String curTangramSetName;
    protected Vector<Tangram> curTangrams;
    protected TangramFrame containingFrame;
    
    public TangramPanel(TangramFrame containingFrame, Vector<SerialTangram> v, String setName)
    {
        curTangramSetName=setName;
        curTangrams=getTangramsFromSerialTangrams((Vector<SerialTangram>)v.clone());
        this.containingFrame=containingFrame;
    }
    
    protected Vector<Tangram> getTangramsFromSerialTangrams(Vector<SerialTangram> v)
    {
        Vector<Tangram> result=new Vector<Tangram>();
        for(int i=0;i<v.size();i++)
        {
            result.add(new Tangram(v.elementAt(i)));
        }
        return result;
    }
    
    protected void paintComponents(Graphics2D g2)
    {       
        Iterator<Slot> i=components.iterator();
        //System.out.println("there are "+ components.size());
        while(i.hasNext())
        {
            Slot cur=i.next();
            cur.draw(g2, 'c');
        }
        
    
       
    }
    protected abstract void loadNext(Vector<SerialTangram> v, String setName, TangramSequence sequence, char whichDirector);
    
    @Override
    protected void paintComponent(Graphics g)
    {
        //System.out.println("this is called");
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        paintComponents(g2);
        
        
        
    }
    protected abstract void updateStatusTangramPlaced(int id, boolean directorMoveOn);
    protected abstract void updateStatusTangramRevealed(int slotID);
    
    

}
