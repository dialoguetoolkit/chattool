/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class MazeGameController3WAYUI {
    MazeGameController3WAY mgc;
    JTabbedPane jtp = new JTabbedPane();
    JExperimentInProgressSuperPanel directorMF;// = new JExperimentFrameGameInProgressMaze();
    JExperimentInProgressSuperPanel matcher1MF;// matcher1MF = new JMazeFrame();
    JExperimentInProgressSuperPanel matcher2MF; //matcher2MF = new JMazeFrame();
    
    public MazeGameController3WAYUI(MazeGameController3WAY mgc){
        this.mgc=mgc;
        jtp.setVisible(true);
        JFrame jf = new JFrame();
        jf.getContentPane().add(jtp);
        jf.pack();
        jf.setVisible(true);
        
    }
    
    public void initializeJTabbedPane(final String directorName, final Vector directorMazes,
            final String matcher1Name,final Vector matcher1Mazes,
            final String matcher2Name, final Vector matcher2Mazes){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
              public void run(){
                  Vector directorMazesClone = mgc.cloneVectorOfMazes(directorMazes);
                  Vector matcher1MazesClone = mgc.cloneVectorOfMazes(matcher1Mazes);
                  Vector matcher2MazesClone = mgc.cloneVectorOfMazes(matcher2Mazes);
                  
                  
                  directorMF = new JExperimentInProgressSuperPanel(directorName,directorMazesClone);
                  matcher1MF = new JExperimentInProgressSuperPanel(matcher1Name,matcher1MazesClone);
                  matcher2MF = new JExperimentInProgressSuperPanel(matcher2Name,matcher2MazesClone);
                  jtp.insertTab(directorName, null, directorMF, null, 0);
                  jtp.insertTab(matcher1Name, null, matcher1MF, null, 1);
                  jtp.insertTab(matcher2Name, null, matcher2MF, null, 2);
              }    
           }); 
        }catch(Exception e){
            System.err.println("COULD NOT INITIALIZE TABBED PANE OF MAZES ON SERVER SIDE");
        }    
    }    
    
    public void moveToMazeNo(final int i){
        try{
            SwingUtilities.invokeLater(new Runnable(){
               public void run(){
                  directorMF.changeToMazeNo(i);
                  matcher1MF.changeToMazeNo(i);
                  matcher2MF.changeToMazeNo(i);
               } 
            });   
        }catch (Exception e){
            System.err.println("ERROR ON SERVER UPDATING DISPLAY AND MOVING MAZES");
        }
    }   
    
    public void movePositionDirector(final Dimension newPos){  
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
              public void run(){
                directorMF.updateCursor(newPos);
                jtp.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }    
    }
    public void movePositionMatcher1(final Dimension newPos){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                matcher1MF.updateCursor(newPos);
                jtp.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }    
        
        
    }
    public void movePositionMatcher2(final Dimension newPos){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                matcher2MF.updateCursor(newPos);
                jtp.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }    
        
    }
    public void changeGateStatusDirector(final boolean open){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                directorMF.changeGateStatus(open);
                jtp.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }   
        
    }
    public void changeGateStatusMatcher1(final boolean open){
         try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                matcher1MF.changeGateStatus(open);
                jtp.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }   
    }
    public void changeGateStatusMatcher2(final boolean open){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                matcher2MF.changeGateStatus(open);
                jtp.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }   
    }
    
    /**
     * 
     */
    public void closeDown(){
         jtp.setVisible(false);
         jtp=null;
    }
}
