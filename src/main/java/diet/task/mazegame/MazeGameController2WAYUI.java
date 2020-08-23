/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;


import diet.gui.lookandfeel.JNimbusProgressBar;
import diet.gui.lookandfeel.JProgressBarFillPainter;
import diet.server.ConnectionListener;
import diet.server.Participant;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author user
 */
public class MazeGameController2WAYUI implements ActionListener{
    
    
    
    public MazeGameController2WAYTimer mgcT = new MazeGameController2WAYTimer(this);
    MazeGameController2WAY mgc;
    JTabbedPane jtpDirectorTabbedPane = new JTabbedPane();
    JTabbedPane jtpMatcherTabbedPane = new JTabbedPane();
    //JExperimentInProgressSuperPanel directorMF;// = new JExperimentFrameGameInProgressMaze();
    public JExperimentInProgressSuperPanel directorMF;// matcher1MF = new JMazeFrame();
    public JExperimentInProgressSuperPanel matcherMF; //matcher2MF = new JMazeFrame();
    public JFrame jf;
    JScrollPane js = new JScrollPane();
    JTextPane jtp = new JTextPane();
    JButton jbRESETPOSITION = new JButton("reset position marker to initial position");
    JButton jbUPMAZE = new JButton("  +  ");
    JButton jbDOWNMAZE = new JButton("  -  ");
    
    JButton jbTimerPLUS10 = new JButton("+ 10 sec");
    JButton jbTimerMINUS10 = new JButton("- 10 sec");
    JButton jbTimerPAUSE = new JButton("Start / Pause");
    
    
    
    JNimbusProgressBar jnp ;
    JProgressBar jProgressA;    
    JProgressBar jProgressB ;
    
    
    public MazeGameController2WAYUI(MazeGameController2WAY mgc) {
        
        this.mgc=mgc;
        jtpDirectorTabbedPane.setVisible(true);
        String portNumberOfServer = ""+ConnectionListener.staticGetPortNumber();
        jf = new JFrame("Server. Port: "+portNumberOfServer+ " Mazegame controller");
         jf.setLayout(new BorderLayout());
        jf.getContentPane().add(jtpDirectorTabbedPane,BorderLayout.WEST);
        jf.getContentPane().add(jtpMatcherTabbedPane,BorderLayout.EAST);
        //jf.getContentPane().add(jb,BorderLayout.SOUTH);
       
        JPanel jp2 = new JPanel();
        jp2.setLayout(new BorderLayout());
        jp2.add(jbRESETPOSITION,BorderLayout.CENTER);
        jp2.add(jbUPMAZE,BorderLayout.EAST);
        jp2.add(jbDOWNMAZE,BorderLayout.WEST);
        
        //jf.getContentPane().add(jp2,BorderLayout.SOUTH);
        
        JPanel jpTimerPanel = new JPanel();
        jpTimerPanel.setLayout(new BorderLayout());
        jpTimerPanel.add(this.jbTimerMINUS10, BorderLayout.WEST);
        jpTimerPanel.add(this.jbTimerPAUSE, BorderLayout.CENTER);
        jpTimerPanel.add(this.jbTimerPLUS10, BorderLayout.EAST); 
        
        
        this.jbTimerMINUS10.addActionListener(this);
        this.jbTimerPAUSE.addActionListener(this);
        this.jbTimerPLUS10.addActionListener(this);
        
        //jf.get
        
        JPanel jpCentre = new JPanel();
        jpCentre.setPreferredSize(new Dimension(200,200));
        jpCentre.setMinimumSize(new Dimension(200,200));
        
        jtpDirectorTabbedPane.setPreferredSize(new Dimension(200,200));
        jtpMatcherTabbedPane.setPreferredSize(new Dimension(200,200));
        
        //js.add(jtp);
        js.getViewport().setView(jtp);
        js.setPreferredSize(new Dimension(200,200));
        
        jtp.setBackground(Color.BLACK);
        
        jProgressA = new JProgressBar();
        jProgressA.setMaximum(100);
        jProgressA.setMinimum(0);
        jProgressA.setOrientation(SwingConstants.VERTICAL);
        
        jProgressB = new JProgressBar();
        jProgressB.setMaximum(100);
        jProgressB.setMinimum(0);
       
        jProgressB.setOrientation(SwingConstants.VERTICAL);
        
        
        JPanel jCentralPane = new JPanel();
        jCentralPane.setLayout(new BorderLayout());
        //jCentralPane.add(js,BorderLayout.CENTER);
        jCentralPane.add(jProgressA,BorderLayout.WEST);
        jCentralPane.add(jProgressB,BorderLayout.EAST);
        
        
        JPanel jCentralCentralPane = new JPanel();
        jCentralCentralPane.setLayout(new BorderLayout());
        jCentralCentralPane.add(js, BorderLayout.CENTER);
        jCentralPane.add(jpTimerPanel, BorderLayout.NORTH);
        jCentralPane.add(jp2, BorderLayout.SOUTH);
        jCentralPane.add(jCentralCentralPane,BorderLayout.CENTER);
        
        
        
        jf.getContentPane().add(jCentralPane,BorderLayout.CENTER);
        this.jbDOWNMAZE.addActionListener(this);
        this.jbUPMAZE.addActionListener(this);
        jbRESETPOSITION.addActionListener(this);    
        UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
        UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
        jnp = new JNimbusProgressBar(JProgressBar.HORIZONTAL,0,100);
        
        jnp.setIndeterminate(false);
        
       
        
        jf.getContentPane().add(jnp,BorderLayout.NORTH);
        
             
             
        jf.setAlwaysOnTop(true);
             
        
        ////Nimbus
        
        Color bgColor = Color.BLACK;
        UIDefaults defaults = new UIDefaults();
        defaults.put("TextPane[Enabled].backgroundPainter", bgColor);
        jtp.putClientProperty("Nimbus.Overrides", defaults);
        jtp.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
        jtp.setBackground(bgColor);
        
        SwingUtilities.updateComponentTreeUI(jf);
        jf.pack();
        jf.setVisible(true);
        
        
      
        
        
        
        
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(jbRESETPOSITION)){
            mgc.moveToMazeNo(mgc.getMazeNo(), "Restarting maze");
            try{
                mgc.c.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns("ExperimenterControl", "restarted maze number:"+mgc.mazeNumber+" ("+mgc.pDirector.getParticipantID()+", "+mgc.pDirector.getUsername()+")   "
                        + "("+mgc.pMatcher.getParticipantID()+", "+mgc.pMatcher.getUsername()+")");
            }catch(Exception e2){}
        }
        else if(e.getSource().equals(this.jbUPMAZE)){
            mgc.moveToMazeNo(mgc.getMazeNo()+1);
            try{
            mgc.c.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns("ExperimenterControl", "moving up to next maze number:"+mgc.mazeNumber+" ("+mgc.pDirector.getParticipantID()+", "+mgc.pDirector.getUsername()+")   "
                        + "("+mgc.pMatcher.getParticipantID()+", "+mgc.pMatcher.getUsername()+")");
            }catch(Exception e2){}
            
        }
        else if (e.getSource().equals(this.jbDOWNMAZE)){
            mgc.moveToMazeNo(mgc.getMazeNo()-1);
            try{
             mgc.c.deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns("ExperimenterControl", "moving down to next maze number:"+mgc.mazeNumber+" ("+mgc.pDirector.getParticipantID()+", "+mgc.pDirector.getUsername()+")   "
                        + "("+mgc.pMatcher.getParticipantID()+", "+mgc.pMatcher.getUsername()+")");
            }catch(Exception e2){}
            
        }
        else if (e.getSource().equals(this.jbTimerMINUS10)){
             long newvalue =  mgcT.timeOfLatestChange- 10000;
             if (newvalue<0)newvalue=0;
             mgcT.timeOfLatestChange = newvalue;
            
        }
        else if (e.getSource().equals(this.jbTimerPAUSE)){  
            if(!mgcT.doTiming){
               mgcT.startTiming();    
            }
            else{
                mgcT.pauseTiming=!mgcT.pauseTiming;
            }
        }
        else if (e.getSource().equals(this.jbTimerPLUS10)){
            mgcT.timeOfLatestChange = mgcT.timeOfLatestChange+ 10000;
        }
        
        
    }
    public void updateJProgressBar(final int value, final String text, final Color color){
        SwingUtilities.invokeLater(new Runnable(){
             public void run(){
                  
                  jnp.changeJProgressBar(color, text, value);
                  
                  
             }
        });
    }  
    
    
  
    
    public void changeProgressBarOfName(final String username, int value){
        if(value>99)value=99; if(value<0)value=0;
        if(jtpDirectorTabbedPane.getTabCount()==0)return;
        if(jtpMatcherTabbedPane.getTabCount()==0)return;
        String directorName = jtpDirectorTabbedPane.getTitleAt(0);
        String matcherName =  jtpMatcherTabbedPane.getTitleAt(0);
        if(username.equalsIgnoreCase(directorName)){
            changeJProgressBar(this.jProgressA, value );
        }
        else if(username.equalsIgnoreCase(matcherName)){
             changeJProgressBar(this.jProgressB, value );
        }
        else{
            mgc.c.printWlnLog("Main", "THERE IS AN ERROR - THE MAZE UI CAN'T FIND THE TAB FOR: "+username+"....the directorname: "+directorName+"...the matchername: "+matcherName);
            System.err.println("THERE IS AN ERROR - THE MAZE UI CAN'T FIND THE TAB FOR: "+username+"....the directorname: "+directorName+"...the matchername: "+matcherName);
        }
        
    }
    
    public void changeJProgressBar(final JProgressBar jpb, final int value){
        SwingUtilities.invokeLater(new Runnable(){ 
              public void run(){
                   jpb.setValue(value);
              }
              });
    }
    
    
    public void changeTabNamesAndMazes(final String directorTab,final Vector directorMazes, final String matcherTab, final Vector matcherMazes, final int mazeNumber){
        SwingUtilities.invokeLater(new Runnable(){ 
              public void run(){
                   jtpDirectorTabbedPane.setTitleAt(0, directorTab);
                   jtpMatcherTabbedPane.setTitleAt(0, matcherTab);
                   directorMF.setMazes(directorMazes);
                   matcherMF.setMazes(matcherMazes);
                   moveToMazeNo(mazeNumber);
              }
              });
    }
    
    
    public void initializeJTabbedPane(
            final String client1Name,final Vector client1Mazes,
            final String client2Name, final Vector client2Mazes){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
              public void run(){
                  
                  Vector matcher1MazesClone = mgc.cloneVectorOfMazes(client1Mazes);
                  Vector matcher2MazesClone = mgc.cloneVectorOfMazes(client2Mazes);
                  
                  
                  //directorMF = new JExperimentInProgressSuperPanel(directorName,directorMazesClone);
                  directorMF = new JExperimentInProgressSuperPanel(client1Name,matcher1MazesClone);
                  matcherMF = new JExperimentInProgressSuperPanel(client2Name,matcher2MazesClone);
                  
                 
                  
                  //jtp.insertTab(directorName, null, directorMF, null, 0);
                  jtpDirectorTabbedPane.insertTab(client1Name, null, directorMF, null, 0);
                  jtpMatcherTabbedPane.insertTab(client2Name, null, matcherMF, null, 0);
                  
                  jf.pack();
                  jf.repaint();
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
                  //directorMF.changeToMazeNo(i);
                  directorMF.changeToMazeNo(i);
                  matcherMF.changeToMazeNo(i);
                  
                  
               } 
            });   
        }catch (Exception e){
            System.err.println("ERROR ON SERVER UPDATING DISPLAY AND MOVING MAZES");
        }
    }   
    
   
    public void movePositionClient(Participant clientName,final Dimension newPos){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                directorMF.updateCursor(newPos);
                jtpDirectorTabbedPane.repaint();
                jtpMatcherTabbedPane.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }    
        
        
    }
    
    
    
    public void movePositionDirector(final Dimension newPos){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                directorMF.updateCursor(newPos);
                jtpDirectorTabbedPane.repaint();
                jtpMatcherTabbedPane.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }    
        
        
    }
    public void movePositionMatcher(final Dimension newPos){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                matcherMF.updateCursor(newPos);
                jtpDirectorTabbedPane.repaint();
                jtpMatcherTabbedPane.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }    
        
    }
    
    
    public void changeGateStatusOfName(final String username, final boolean open){
        String directorName = jtpDirectorTabbedPane.getTitleAt(0);
        String matcherName =  jtpMatcherTabbedPane.getTitleAt(0);
        if(username.equalsIgnoreCase(directorName)){
            changeGateStatusDirector(open);
        }
        else if(username.equalsIgnoreCase(matcherName)){
            changeGateStatusMatcher(open);
        }
        else{
            mgc.c.printWlnLog("Main", "THERE IS AN ERROR - THE MAZE UI CAN'T FIND THE TAB FOR: "+username+"....the directorname: "+directorName+"...the matchername: "+matcherName);
            System.err.println("THERE IS AN ERROR - THE MAZE UI CAN'T FIND THE TAB FOR: "+username+"....the directorname: "+directorName+"...the matchername: "+matcherName);
        }
        
    }
    
    public void changeGateStatusDirector(final boolean open){
         try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                directorMF.changeGateStatus(open);
                jtpDirectorTabbedPane.repaint();
                jtpMatcherTabbedPane.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }   
    }
    public void changeGateStatusMatcher(final boolean open){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                matcherMF.changeGateStatus(open);
                jtpDirectorTabbedPane.repaint();
                jtpMatcherTabbedPane.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }   
    }
    
    
 public void append(final String s){
        
     final StyledDocument doc = jtp.getStyledDocument();
     final SimpleAttributeSet keyWord = new SimpleAttributeSet();
     StyleConstants.setForeground(keyWord, Color.WHITE);
     //StyleConstants.setBackground(keyWord, Color.YELLOW);
     //StyleConstants.setBold(keyWord, true);
     try
        {
        
        }
     catch(Exception e) { System.out.println(e); }
     
     try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
               try
        { 
                
               doc.insertString(doc.getLength(), s+"\n", keyWord );
             
            }
     catch(Exception e) { System.out.println(e); }
            
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
         jtpDirectorTabbedPane.setVisible(false);
         jtpMatcherTabbedPane.setVisible(false);
         jtpDirectorTabbedPane=null;
         jtpMatcherTabbedPane=null;
    }
}
