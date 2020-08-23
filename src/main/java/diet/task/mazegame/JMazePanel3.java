/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

/**
 *
 * @author user
 */
//import diet.task.mazegame.client.*;
//import diet.task.mazegame.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.util.Vector;
import javax.swing.BorderFactory;

import javax.swing.JPanel;



public class JMazePanel3 extends JPanel{

  public Maze maz;
  int widthIncrement;
  int heightIncrement;
  int currentWidth;
  int currentHeight;
  int counter = 0;
   boolean firstPainntinghasOccurred = false;
  JMazeFrame jmf;
  Dimension cursorPos = new Dimension(0,0);
  Color mazeFigureColor = Color.WHITE;
  Color mazeBackgroundColor = new Color(240,240,240);
  
  public JMazePanel3(JMazeFrame jm) {
    try {
      jbInit();
      jmf = jm;
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    
    
    
    
    maz = new Maze(0);
    this.setOpaque(true);
    this.setBackground(this.mazeBackgroundColor);
    this.setBackground(Color.yellow);
    this.validate();
    this.reCalculateWidthsandSizes();
    System.out.println(this.getBounds().getHeight());
  }

  protected void paintSquare(Graphics g, MazeSquare mz, Dimension position,
                             Vector linksAllreadyDrawn) {

    //g.drawRect(position.width,position.height,widthIncrement,heightIncrement);
    int x0 = position.width;
    int y0 = position.height;
    if (mz == maz.begin) {
      Color oldColor = g.getColor();
      //g.setColor(Color.green);
     // g.fillRect( (int) x0 + (widthIncrement * 1 / 8),
       //          (int) y0 + (heightIncrement * 1 / 8),
       //          (int) widthIncrement * 3 / 4, (int) heightIncrement * 3 / 4);
      g.setColor(oldColor);
      Font oldFont = g.getFont();
      //g.drawString("start",(int)x0+(widthIncrement*2/8),(int)y0+(heightIncrement*7/8));

    }

    if (mz == maz.finish) {
      Color oldColor = g.getColor();
      
       
      g.setColor(this.mazeFigureColor);
      g.fillRect( (int) x0 + (widthIncrement * 1 / 8),
                 (int) y0 + (heightIncrement * 1 / 8),
                 (int)1+ widthIncrement * 3 / 4, (int)1+ heightIncrement * 3 / 4);
      g.setColor(oldColor);
      
      
      g.setColor(Color.red);
      //g.fillRect( (int) x0 + (widthIncrement * 1 / 8),
      //           (int) y0 + (heightIncrement * 1 / 8),
      //           (int) widthIncrement * 3 / 4, (int) heightIncrement * 3 / 4);

      Font oldFont = g.getFont();
      g.setColor(Color.red);
      g.setFont(new java.awt.Font("Dialog", 1, (int)heightIncrement*6/8));
      g.drawString("X",(int)x0+widthIncrement/4,(int)y0+heightIncrement*6/8);
      g.setFont(oldFont);
      g.setColor(oldColor);

//      g.fillPolygon();



    }

    else if (mz.isaSwitch) {
      Color oldColor = g.getColor();
      g.setColor(Color.lightGray);
      g.fillRect( (int) x0 + (widthIncrement * 1 / 8),
                 (int) y0 + (heightIncrement * 1 / 8),
                 (int)1+ widthIncrement * 3 / 4, (int)1+ heightIncrement * 3 / 4);
      g.setColor(oldColor);

    }
    
    else{
        Color oldColor = g.getColor();
      g.setColor(this.mazeFigureColor);
      g.fillRect( (int) x0 + (widthIncrement * 1 / 8),
                 (int) y0 + (heightIncrement * 1 / 8),
                 (int) widthIncrement * 3 / 4, (int) heightIncrement * 3 / 4);
      g.setColor(oldColor);
    }
    

    g.drawLine( (int) x0 + (widthIncrement * 1 / 8),
               (int) y0 + (heightIncrement * 1 / 8),
               (int) x0 + (widthIncrement * 3 / 8),
               (int) y0 + (heightIncrement * 1 / 8));
    g.drawLine( (int) x0 + (widthIncrement * 1 / 8),
               (int) y0 + (heightIncrement * 1 / 8),
               (int) x0 + (widthIncrement * 1 / 8),
               (int) y0 + (heightIncrement * 3 / 8));
    g.drawLine( (int) x0 + (widthIncrement * 7 / 8),
               (int) y0 + (heightIncrement * 1 / 8),
               (int) x0 + (widthIncrement * 5 / 8),
               (int) y0 + (heightIncrement * 1 / 8));
    g.drawLine( (int) x0 + (widthIncrement * 7 / 8),
               (int) y0 + (heightIncrement * 1 / 8),
               (int) x0 + (widthIncrement * 7 / 8),
               (int) y0 + (heightIncrement * 3 / 8));
    g.drawLine( (int) x0 + (widthIncrement * 1 / 8),
               (int) y0 + (heightIncrement * 7 / 8),
               (int) x0 + (widthIncrement * 1 / 8),
               (int) y0 + (heightIncrement * 5 / 8));
    g.drawLine( (int) x0 + (widthIncrement * 1 / 8),
               (int) y0 + (heightIncrement * 7 / 8),
               (int) x0 + (widthIncrement * 3 / 8),
               (int) y0 + (heightIncrement * 7 / 8));
    g.drawLine( (int) x0 + (widthIncrement * 7 / 8),
               (int) y0 + (heightIncrement * 7 / 8),
               (int) x0 + (widthIncrement * 7 / 8),
               (int) y0 + (heightIncrement * 5 / 8));
    g.drawLine( (int) x0 + (widthIncrement * 7 / 8),
               (int) y0 + (heightIncrement * 7 / 8),
               (int) x0 + (widthIncrement * 5 / 8),
               (int) y0 + (heightIncrement * 7 / 8));

    if (mz.north != null) {
        
        
      ///NEW COMMANDS
       Color oldColor = g.getColor();
       g.setColor(this.mazeFigureColor);
       int width = (widthIncrement * 2)/8;  int height =(heightIncrement * 1)/8;
       g.fillRect((int) x0 + (widthIncrement * 3 / 8), (int) y0, width, height);
       g.setColor(oldColor);
       /////NEW COMMANDS
      g.drawLine( (int) x0 + (widthIncrement * 3 / 8),
                 (int) y0 + (heightIncrement * 1 / 8),
                 (int) x0 + (widthIncrement * 3 / 8), y0);
      g.drawLine( (int) x0 + (widthIncrement * 5 / 8),
                 (int) y0 + (heightIncrement * 1 / 8),
                 (int) x0 + (widthIncrement * 5 / 8), y0);
      
     
     
      
    }
    else {
      g.drawLine( (int) x0 + (widthIncrement * 3 / 8),
                 (int) y0 + (heightIncrement * 1 / 8),
                 (int) x0 + (widthIncrement * 5 / 8),
                 (int) y0 + (heightIncrement * 1 / 8));
    }
    if (mz.east != null) {
        
      ///NEW COMMANDS
       Color oldColor = g.getColor();
       g.setColor(this.mazeFigureColor);
       int height = (heightIncrement * 2)/8;  int width = (widthIncrement * 1)/8;
       g.fillRect((int) x0 + (widthIncrement * 7 / 8), (int) y0 + (heightIncrement *3)/8, width+2, height);
       g.setColor(oldColor);
       /////NEW COMMANDS  
        
        
      g.drawLine( (int) x0 + (widthIncrement * 7 / 8),
                 (int) y0 + (heightIncrement * 3 / 8),
                 (int) x0 + widthIncrement, (int) y0 + (heightIncrement * 3 / 8));
      g.drawLine( (int) x0 + (widthIncrement * 7 / 8),
                 (int) y0 + (heightIncrement * 5 / 8),
                 (int) x0 + widthIncrement, (int) y0 + (heightIncrement * 5 / 8));
    }
    else {
      g.drawLine( (int) x0 + (widthIncrement * 7 / 8),
                 (int) y0 + (heightIncrement * 3 / 8),
                 (int) x0 + (widthIncrement * 7 / 8),
                 (int) y0 + (heightIncrement * 5 / 8));
    }
    if (mz.south != null) {
      ///NEW COMMANDS
       Color oldColor = g.getColor();
       g.setColor(this.mazeFigureColor);
       int width = (widthIncrement * 2)/8;  int height =(heightIncrement * 1)/8;
       g.fillRect((int) x0 + (widthIncrement * 3 / 8), (int) -1+y0 + heightIncrement * 7/8, width, height+2);
       g.setColor(oldColor);
       /////NEW COMMANDS
       
      g.drawLine( (int) x0 + (widthIncrement * 3 / 8),
                 (int) y0 + (heightIncrement * 7 / 8),
                 (int) x0 + (widthIncrement * 3 / 8),
                 (int) y0 + (heightIncrement));
      g.drawLine( (int) x0 + (widthIncrement * 5 / 8),
                 (int) y0 + (heightIncrement * 7 / 8),
                 (int) x0 + (widthIncrement * 5 / 8),
                 (int) y0 + (heightIncrement));
      
      
       
    }
    else {
      g.drawLine( (int) x0 + (widthIncrement * 3 / 8),
                 (int) y0 + (heightIncrement * 7 / 8),
                 (int) x0 + (widthIncrement * 5 / 8),
                 (int) y0 + (heightIncrement * 7 / 8));
    }
    if (mz.west != null) {
        
      ///NEW COMMANDS
       Color oldColor = g.getColor();
       g.setColor(this.mazeFigureColor);
        
       int height = (heightIncrement * 2)/8;  int width = (widthIncrement * 1)/8;
       g.fillRect((int) x0 , (int) y0 + (heightIncrement *3)/8, width, height);
       g.setColor(oldColor);
       /////NEW COMMANDS  
        
      g.drawLine( (int) x0 + (widthIncrement * 1 / 8),
                 (int) y0 + (heightIncrement * 3 / 8), (int) x0,
                 (int) y0 + (heightIncrement * 3 / 8));
      g.drawLine( (int) x0 + (widthIncrement * 1 / 8),
                 (int) y0 + (heightIncrement * 5 / 8), (int) x0,
                 (int) y0 + (heightIncrement * 5 / 8));
    }
    else {
      g.drawLine( (int) x0 + (widthIncrement * 1 / 8),
                 (int) y0 + (heightIncrement * 3 / 8),
                 (int) x0 + (widthIncrement * 1 / 8),
                 (int) y0 + (heightIncrement * 5 / 8));
    }

    if (mz.north != null && !linksAllreadyDrawn.contains(mz.north)) {
      linksAllreadyDrawn.addElement(mz.north);
      paintSquare(g, mz.north.getLinkedSquare(mz),
                  new Dimension(position.width,
                                position.height - heightIncrement),
                  linksAllreadyDrawn);
      if (mz.north.isagate) {
        if (!maz.gatesOpen) {
          g.fillRect( (int) x0 + (widthIncrement * 2 / 8), y0 - 3,
                     (int) widthIncrement * 4 / 8, 6);
        }
        else {
          g.fillRect( (int) x0 + (widthIncrement * 2 / 8), y0 - 3,
                     (int) widthIncrement * 1 / 8, 6);
          g.fillRect( (int) x0 + (widthIncrement * 5 / 8), y0 - 3,
                     (int) widthIncrement * 1 / 8, 6);
          Color oldColor = g.getColor();
          g.setColor(Color.white);
          g.fillRect( (int) x0 + (widthIncrement * 3 / 8), y0 - 3,
                     (int) widthIncrement * 2 / 8, 6);
          g.setColor(oldColor);

        }
      }
    }
    if (mz.east != null && !linksAllreadyDrawn.contains(mz.east)) {
      linksAllreadyDrawn.addElement(mz.east);
      paintSquare(g, mz.east.getLinkedSquare(mz),
                  new Dimension(position.width + widthIncrement,
                                position.height), linksAllreadyDrawn);
      if (mz.east.isagate) {
        if (!maz.gatesOpen) {
          g.fillRect( (int) x0 + widthIncrement - 3,
                     (int) y0 + (heightIncrement * 2 / 8), (int) 6,
                     (int) (heightIncrement * 4 / 8));
        }
        else {
          g.fillRect( (int) x0 + widthIncrement - 3,
                     (int) y0 + (heightIncrement * 2 / 8), (int) 6,
                     (int) (heightIncrement * 1 / 8));
          g.fillRect( (int) x0 + widthIncrement - 3,
                     (int) y0 + (heightIncrement * 5 / 8), (int) 6,
                     (int) (heightIncrement * 1 / 8));
          Color oldColor = g.getColor();
          g.setColor(Color.white);
          g.fillRect( (int) x0 + widthIncrement - 3,
                     (int) y0 + (heightIncrement * 3 / 8), 6,
                     (heightIncrement * 2 / 8));
          g.setColor(oldColor);

        }
      }
    }
    if (mz.south != null && !linksAllreadyDrawn.contains(mz.south)) {
      linksAllreadyDrawn.addElement(mz.south);
      paintSquare(g, mz.south.getLinkedSquare(mz),
                  new Dimension(position.width,
                                position.height + heightIncrement),
                  linksAllreadyDrawn);
      if (mz.south.isagate) {
        if (!maz.gatesOpen) {
          g.fillRect( (int) x0 + (widthIncrement * 2 / 8),
                     (int) y0 + heightIncrement - 3,
                     (int) widthIncrement * 4 / 8, 6);
        }
        else {
          g.fillRect( (int) x0 + (widthIncrement * 2 / 8),
                     (int) y0 + heightIncrement - 3,
                     (int) widthIncrement * 1 / 8, 6);
          g.fillRect( (int) x0 + (widthIncrement * 5 / 8),
                     (int) y0 + heightIncrement - 3,
                     (int) widthIncrement * 1 / 8, 6);
          Color oldColor = g.getColor();
          g.setColor(Color.white);
          g.fillRect( (int) x0 + (widthIncrement * 3 / 8),
                     y0 - 3 + heightIncrement, (int) widthIncrement * 2 / 8, 6);
          g.setColor(oldColor);

        }
      }
    }
    if (mz.west != null && !linksAllreadyDrawn.contains(mz.west)) {
      linksAllreadyDrawn.addElement(mz.west);
      paintSquare(g, mz.west.getLinkedSquare(mz),
                  new Dimension(position.width - widthIncrement,
                                position.height), linksAllreadyDrawn);
      if (mz.west.isagate) {
        if (!maz.gatesOpen) {
          g.fillRect( (int) x0 - 3, (int) y0 + (heightIncrement * 2 / 8),
                     (int) 6, (int) (heightIncrement * 4 / 8));
        }
        else {
          g.fillRect( (int) x0 - 3, (int) y0 + (heightIncrement * 2 / 8),
                     (int) 6, (int) (heightIncrement * 1 / 8));
          g.fillRect( (int) x0 - 3, (int) y0 + (heightIncrement * 5 / 8),
                     (int) 6, (int) (heightIncrement * 1 / 8));
          Color oldColor = g.getColor();
          g.setColor(Color.white);
          g.fillRect( (int) x0 - 3, (int) y0 + (heightIncrement * 3 / 8), 6,
                     (heightIncrement * 2 / 8));
          g.setColor(oldColor);

        }
      }
    }
  }

  
  public void displayMazeDebugINFO(){
      System.err.println("CURRENTX"+maz.current.x);
      System.err.println("CURRENTY"+maz.current.y);
         
      if(maz.current.east==null)System.err.println("CURRENT-EAST IS NULL");
      if(maz.current.south==null)System.err.println("CURRENT-SOUTH IS NULL");
      if(maz.current.west==null)System.err.println("CURRENT-WEST IS NULL");
      if(maz.current.north==null)System.err.println("CURRENT-NORTH IS NULL");
      
      System.err.println("GOALX: "+maz.finish.x);
      System.err.println("GOALY: "+maz.finish.y);
      
      if(maz.finish.east==null)System.err.println("FINISH: EAST IS NULL");
      if(maz.finish.south==null)System.err.println("FINISH: SOUTH IS NULL");
      if(maz.finish.west==null)System.err.println("FINISH: WEST IS NULL");
      if(maz.finish.north==null)System.err.println("FINISH: NORTH IS NULL");
      
      
      System.err.println("BEGINX: "+maz.begin.x);
      System.err.println("GOALY: "+maz.begin.y);
      
      if(maz.begin.east==null)System.err.println("BEGIN: EAST IS NULL");
      if(maz.begin.south==null)System.err.println("BEGIN: SOUTH IS NULL");
      if(maz.begin.west==null)System.err.println("BEGIN: WEST IS NULL");
      if(maz.begin.north==null)System.err.println("BEGIN: NORTH IS NULL");
      
  }
  
  public void moveCursor(int n) {
    System.err.println("JMAZEPANEL3 - THERE SHOULD BE A SUBSEQUENT MOVE COMMAND");
    displayMazeDebugINFO();
    boolean moved = false;
    if (n == 37&& maz.current.west!=null) {
      System.err.println("JMAZEPANEL3 - MOVE CURSOR - WEST");
      if ( (maz.current.west.isagate & maz.gatesOpen) || !maz.current.west.isagate) {
           maz.moveWest();
          
           moved = true;
         }
    }
    else if (n == 38 && maz.current.north!=null) {
      System.err.println("JMAZEPANEL3 - MOVE CURSOR - NORTH");
      if ( (maz.current.north.isagate & maz.gatesOpen) || !maz.current.north.isagate) {
        maz.moveNorth();
        moved = true;
      }
    }
    else if (n == 39 && maz.current.east!=null) {
      System.err.println("JMAZEPANEL3 - MOVE CURSOR - EAST");
      if ( (maz.current.east.isagate & maz.gatesOpen) || !maz.current.east.isagate) {
        maz.moveEast();
        moved = true;
      }
    }
    else if (n == 40 && maz.current.south!=null) {
      System.err.println("JMAZEPANEL3 - MOVE CURSOR - SOUTH");
      if ( (maz.current.south.isagate & maz.gatesOpen) || !maz.current.south.isagate) {
        maz.moveSouth();
        moved = true;
      }
    }
    if (moved) {
       System.err.println("JMAZEPANEL3 - IT DID MOVE");
      jmf.cms.sendCursorPos(new Dimension(maz.current.x, maz.current.y));
      moveCursorTo(new Dimension(maz.current.x,maz.current.y));
      this.paintComponent(this.getGraphics());
    }
  }

  public void moveCursorTo(Dimension d){
       maz.moveTo(d);
       int oldWidth = currentWidth;
       int oldHeight = currentHeight;
       Dimension oldPos = new Dimension(cursorPos);
       Insets insets = getInsets();
       currentWidth = getWidth() - insets.left - insets.right;
       currentHeight = getHeight() - insets.top - insets.bottom;
       Dimension sizeDims = maz.getBounds();
       widthIncrement = (int) (currentWidth / sizeDims.width);
       heightIncrement = (int) (currentHeight / sizeDims.height);
       Dimension startPos = new Dimension(maz.getMinBounds().width * ( -widthIncrement),
                                    maz.getMinBounds().height * ( -heightIncrement));

       cursorPos = new Dimension( (int) startPos.width + widthIncrement / 2,(int) startPos.height + heightIncrement / 2);
       cursorPos = new Dimension (cursorPos.width+widthIncrement*d.width,cursorPos.height+heightIncrement*d.height);
       System.out.println("JMAZEPANEL3 MOVED CURSOR!" );

    }


  public Rectangle cursorToRect(Dimension c) {
    return new Rectangle(c.width, c.height, (int) widthIncrement / 6,
                         (int) heightIncrement / 6);

  }

  protected void paintCursor(Graphics g) {
    this.reCalculateWidthsandSizes();
    g.fillOval((int)cursorPos.width -widthIncrement/8, (int)cursorPos.height-heightIncrement/8, (int) widthIncrement / 4,
               (int) heightIncrement / 4);

  }

  public void resetCursorToBegin() {
    Insets insets = getInsets();
    currentWidth = getWidth() - insets.left - insets.right;
    currentHeight = getHeight() - insets.top - insets.bottom;
    Dimension sizeDims = maz.getBounds();
    widthIncrement = (int) (currentWidth / sizeDims.width);
    heightIncrement = (int) (currentHeight / sizeDims.height);
    Dimension startPos = new Dimension(maz.getMinBounds().width * ( -widthIncrement),
                             maz.getMinBounds().height * ( -heightIncrement));
    cursorPos = new Dimension( (int) startPos.width + widthIncrement / 2,
                              (int) startPos.height + heightIncrement / 2);

  }

  protected void paintComponent(Graphics g) {
    //System.out.println("painting" +counter);
    counter++;

    g.setColor(Color.black);
    Insets insets = getInsets();

    currentWidth = getWidth() - insets.left - insets.right;
    currentHeight = getHeight() - insets.top - insets.bottom;
    
    int pixelborder =0;
    try{
        if(jmf.jfgp.showBorder) pixelborder = jmf.jfgp.pixelborder;
        
    }catch(Exception e){ }
    
    g.setColor(this.mazeBackgroundColor);
   // g.fillRect(0, 0, currentWidth, currentHeight);
     g.fillRect(pixelborder, pixelborder, currentWidth- (2*pixelborder), currentHeight-(2*pixelborder));
    
    
    g.setColor(Color.black);
    Dimension sizeDims = maz.getBounds();
    widthIncrement = (int) (currentWidth / sizeDims.width);
    heightIncrement = (int) (currentHeight / sizeDims.height);
    //System.out.println("SizeDim"+ sizeDims.width);

    Dimension startPos = new Dimension(maz.getMinBounds().width * ( -widthIncrement),
                             maz.getMinBounds().height * ( -heightIncrement));
    //g.drawRect(startPos.width,startPos.height,widthIncrement,heightIncrement);
    //System.out.println("StartPos "+startPos.width+" "+startPos.height);
    if (!firstPainntinghasOccurred) {
      cursorPos = new Dimension( (int) startPos.width + widthIncrement / 2,
                                (int) startPos.height + heightIncrement / 2);
      firstPainntinghasOccurred = true;
    }

    paintSquare(g, maz.begin, startPos, new Vector());
    paintCursor(g);

    if (jmf.cms == null) {
      Color oldColor = g.getColor();
      g.setColor(Color.white);
      g.fillRect(0, 0, currentWidth, currentHeight);
      g.setColor(Color.black);
      g.drawString("Setting up ", (int) currentWidth / 2,
                   (int) currentHeight / 2);
      g.setColor(oldColor);
    }
    else if (jmf.cms.getDisplayMessage() != null) {
      Color oldColor = g.getColor();
      g.setColor(Color.white);
      g.fillRect(0, 0, currentWidth, currentHeight);
      g.setColor(Color.black);
      g.drawString(jmf.cms.getDisplayMessage(), (int) currentWidth / 2,
                   (int) currentHeight / 2);
      g.setColor(oldColor);
    }

  }


  public JMazePanel3() {
    maz = new Maze(0);
    this.setOpaque(true);
    this.setBackground(Color.white);
    this.validate();
    this.reCalculateWidthsandSizes();
    System.out.println(this.getBounds().getHeight());
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.addComponentListener(new JMazePanel3_this_componentAdapter(this));
  }

  void this_componentResized(ComponentEvent e) {
    reCalculateWidthsandSizes();
  }

  public void reCalculateWidthsandSizes() {
    int oldWidth = currentWidth;
    int oldHeight = currentHeight;
    Insets insets = getInsets();
    currentWidth = getWidth() - insets.left - insets.right;
    currentHeight = getHeight() - insets.top - insets.bottom;
    Dimension sizeDims = maz.getBounds();
    widthIncrement = (int) (currentWidth / sizeDims.width);
    heightIncrement = (int) (currentHeight / sizeDims.height);
    Dimension startPos = new Dimension(maz.getMinBounds().width * ( -widthIncrement),
                             maz.getMinBounds().height * ( -heightIncrement));
    System.out.println("StartPos "+startPos.width+" "+startPos.height);
    if (oldWidth != 0 & oldHeight != 0) {
      cursorPos.width = (int) cursorPos.width * currentWidth / oldWidth;
      cursorPos.height = (int) cursorPos.height * currentHeight / oldHeight;
    }

  }
  public void changeMaze(Maze m){
    maz = m;
  }

}

class JMazePanel3_this_componentAdapter extends java.awt.event.ComponentAdapter {
  JMazePanel3 adaptee;

  JMazePanel3_this_componentAdapter(JMazePanel3 adaptee) {
    this.adaptee = adaptee;
  }

  public void componentResized(ComponentEvent e) {
    adaptee.this_componentResized(e);
  }
}

