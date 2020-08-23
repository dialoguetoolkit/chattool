/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

import java.awt.Dimension;
/**
 *
 * @author Arash
 */
public class MazeWrap {
    
    Maze m;
    public MazeWrap(Maze m)
    {
        this.m=m;
    }
    public Dimension getCurrent()
    {
        return new Dimension(m.current.x, m.current.y);
        
    }
    public Maze getMaze()
    {
        return m;
    }
    

}
