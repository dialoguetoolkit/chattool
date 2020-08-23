/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.gridstimuli;

import diet.attribval.AttribVal;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author sre
 */
public class JClientTextP extends JPanel implements MouseListener{
    
    JFrameGridTextStimuli jfgts;
   
    int borderpixels = 10;
    
    Color innerMostColor = Color.WHITE;
    Color innerColor = Color.WHITE;
    
    int w;
    int h;
    
    JLabel jl ;
    JScrollPane jsp;
    
    int xpos;
    int ypos;
    
    
    public JClientTextP(JFrameGridTextStimuli jfgts, int xpos, int ypos){
        this.jfgts=jfgts;
        this.setBackground(Color.WHITE);
            
        jl = new JLabel();
        jsp = new JScrollPane();
        
      
        
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
        this.addMouseListener(this);
        this.setSelection(Color.WHITE, Color.WHITE);
        this.setLayout(new BorderLayout());
        this.add(jl,BorderLayout.CENTER);
        
        
        this.xpos=xpos;
        this.ypos=ypos;
        
    }
    
    
    
    
    public void setText(String text){   
        
        String oldVal = jl.getText();
        long timeOfSetting = new Date().getTime();
        this.jl.setText(text);
        long timeOfHavingSet = new Date().getTime();
        
        
        long timeOfDrawingText =(timeOfHavingSet+timeOfSetting)/2;
        
        Vector currentState = this.getCurrentState();
        AttribVal av4 = new AttribVal("newtext",text);
        currentState.addElement(av4);
        
        this.jfgts.cts.cEventHandler.ciet.addClientEvent("gridtextsettext", timeOfDrawingText, currentState);
        
        
        
    }
    
    public String getText(){
        return jl.getText();
    }
    
    
    
    public Vector getCurrentState(){
        AttribVal av1 = new AttribVal("x",xpos);
        AttribVal av2 = new AttribVal("y",ypos);
        AttribVal av3 = new AttribVal("text",jl.getText());
        AttribVal av4 = new AttribVal("innermostc",this.innerMostColor.toString());
        AttribVal av5 = new AttribVal("innerc",this.innerColor.toString());
        Vector currentState= new Vector();
        currentState.addElement(av1); currentState.addElement(av2); currentState.addElement(av3); currentState.addElement(av4); currentState.addElement(av5);
        return currentState;
        
    }
    
    
    
    private void drawText(Graphics g){
        // Color oldColor = g.getColor();
             //g.setColor(Color.red);
            //
              //System.exit(-56);
              //Graphics2D g2d = (Graphics2D)g;
         //int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
         //int fontSize = (int)Math.round(25.0 * screenRes / 72.0);
         //Font font = new Font("Arial", Font.PLAIN, fontSize);   
         //g2d.setFont(font);
         //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         //g.setColor(textColour);
         //g.drawString(text, positionX, positionY);
    }
    
    
    public void setPreferredSize(Dimension d){
        super.setPreferredSize(new Dimension(d.width+borderpixels+borderpixels,d.height+borderpixels+borderpixels));
    }
    
    public void paint(Graphics g){
      super.paint(g);
     
      try{
      
        
        // drawText(g);
        
       }catch (Exception e){
           e.printStackTrace();
       }
      //System.err.println("PAINT1");
      //g.d
  }

    
    public void setSelection(Color innermostColor, Color innerColor){
       //System.exit(-55);
      // setBorder(new javax.swing.border.LineBorder(c, borderpixels, false));
       //Border margin1 = new javax.swing.border.LineBorder(Color.RED,2 , false);
       //Border margin2 = new javax.swing.border.LineBorder(Color.GREEN,10, false);
       //this.setBorder(new CompoundBorder(margin1, margin2));
       // setBorder(new javax.swing.border.LineBorder(c, borderpixels, false));
        Border innermost = new javax.swing.border.LineBorder(innermostColor, (borderpixels/2)-1, false);
        Border outer = new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, false);
        Border inner = new javax.swing.border.LineBorder(innerColor, ((borderpixels)/2)-1, false);
        //this.setBorder(new CompoundBorder(margin1, margin2));
       
        this.innerColor=innerColor;
        this.innerMostColor=innerMostColor;
        
        CompoundBorder cb = new CompoundBorder(inner,innermost);
        CompoundBorder cb2 = new CompoundBorder(outer,cb);
        
        
        this.setBorder(cb);
        
        
       
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
         System.err.println("MOuSEPRESSED");
         jfgts.setTextSelected(this, jl.getText(), new Date().getTime(), this.innerMostColor, this.innerColor  ,w,h );
         
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    
    
    
}
