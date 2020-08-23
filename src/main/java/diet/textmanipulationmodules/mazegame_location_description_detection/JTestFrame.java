package diet.textmanipulationmodules.mazegame_location_description_detection;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;


public class JTestFrame extends JFrame {
  JScrollPane jScrollPane1 = new JScrollPane();
  JList jList1;// = new JList();
  PhraseFilter pr;
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JTextField jTextField2 = new JTextField();
  JTextField jTextField1 = new JTextField();
  JTextField jTextField3 = new JTextField();
  JTextField jTextField4 = new JTextField();
  JTextField jTextField1a = new JTextField();
  JTextField jTextField1b = new JTextField();
  JTextField jTextField2b = new JTextField();
  JTextField jTextField2a = new JTextField();
  JTextField jTextField3b = new JTextField();
  JTextField jTextField3a = new JTextField();
  JTextField jTextField4b = new JTextField();
  JTextField jTextField4a = new JTextField();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  JButton jButton4 = new JButton();
  JButton jButtonsave1 = new JButton();
  JButton jButtonsave2 = new JButton();
  JButton jButtonsave3 = new JButton();
  JButton jButtonsave4 = new JButton();
  JButton jButton5 = new JButton();
  JButton jButtonblock1 = new JButton();
  JButton jButtonblock2 = new JButton();
  JButton jButtonblock3 = new JButton();
  JButton jButtonblock4 = new JButton();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JTextField jTextFieldpermitted = new JTextField();
  JLabel jLabel9 = new JLabel();
  JTextField jTextField5 = new JTextField();
  JButton jButton6 = new JButton();


  public JTestFrame(Vector v,PhraseFilter pr1) throws HeadlessException {
    try {
      pr=pr1;
      jList1 = new JList(v);
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public JTestFrame(GraphicsConfiguration p0) {
    super(p0);
  }

  public JTestFrame(String p0) throws HeadlessException {
    super(p0);
  }

  public JTestFrame(String p0, GraphicsConfiguration p1) {
    super(p0, p1);
  }
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
     jList1.addPropertyChangeListener(new JTestFrame_jList1_propertyChangeAdapter(this));
     jList1.addListSelectionListener(new JTestFrame_jList1_listSelectionAdapter(this));
     jList1.setMaximumSize(new Dimension(32767, 32767));
     jList1.setPreferredSize(new Dimension(32767, 32767));
     jList1.setRequestFocusEnabled(true);
     jList1.setSelectedIndex(0);
     jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
     jScrollPane1.setMaximumSize(new Dimension(32767, 32767));
     jScrollPane1.setMinimumSize(new Dimension(0, 0));
     jScrollPane1.setOpaque(true);
     jScrollPane1.setPreferredSize(new Dimension(500, 500));
     jScrollPane1.setRequestFocusEnabled(true);
     jScrollPane1.setToolTipText("");
     jScrollPane1.setVerifyInputWhenFocusTarget(true);
     jPanel1.setLayout(null);
     jPanel1.setMinimumSize(new Dimension(20, 900));
     jPanel1.setPreferredSize(new Dimension(20, 900));
     jPanel1.setRequestFocusEnabled(true);
     jTextField2.setMinimumSize(new Dimension(11, 60));
     jTextField2.setOpaque(true);
     jTextField2.setPreferredSize(new Dimension(62, 70));
     jTextField2.setToolTipText("");
     jTextField2.setText("");
     jTextField2.setBounds(new Rectangle(33, 647, 272, 27));
     jTextField1.setMinimumSize(new Dimension(50, 50));
     jTextField1.setPreferredSize(new Dimension(62, 50));
     jTextField1.setRequestFocusEnabled(true);
     jTextField1.setText("");
     jTextField1.setBounds(new Rectangle(33, 602, 272, 27));
     jTextField3.setText("");
     jTextField3.setBounds(new Rectangle(33, 692, 272, 27));
     jTextField4.setToolTipText("");
     jTextField4.setText("");
     jTextField4.setBounds(new Rectangle(33, 737, 272, 27));
     jTextField1a.setText("");
     jTextField1a.setBounds(new Rectangle(412, 602, 81, 27));
     jTextField1b.setBounds(new Rectangle(516, 602, 81, 27));
     jTextField1b.setToolTipText("");
     jTextField1b.setText("");
     jTextField2b.setToolTipText("");
     jTextField2b.setText("");
     jTextField2b.setBounds(new Rectangle(516, 647, 81, 27));
     jTextField2a.setText("");
     jTextField2a.setBounds(new Rectangle(412, 647, 81, 27));
     jTextField3b.setToolTipText("");
     jTextField3b.setText("");
     jTextField3b.setBounds(new Rectangle(516, 692, 81, 27));
     jTextField3a.setText("");
     jTextField3a.setBounds(new Rectangle(412, 692, 81, 27));
     jTextField4b.setToolTipText("");
     jTextField4b.setText("");
     jTextField4b.setBounds(new Rectangle(516, 737, 81, 27));
     jTextField4a.setText("");
     jTextField4a.setBounds(new Rectangle(412, 737, 81, 27));
     jButton1.setBounds(new Rectangle(608, 602, 77, 27));
     jButton1.setMaximumSize(new Dimension(55, 19));
     jButton1.setMargin(new Insets(0, 0, 0, 0));
     jButton1.setText("new/syn");
     jButton1.addActionListener(new JTestFrame_jButton1_actionAdapter(this));
     jButton2.setBounds(new Rectangle(608, 647, 78, 27));
     jButton2.setMargin(new Insets(0, 0, 0, 0));
     jButton2.setText("new/syn");
     jButton2.addActionListener(new JTestFrame_jButton2_actionAdapter(this));
     jButton3.setBounds(new Rectangle(608, 692, 79, 27));
     jButton3.setMaximumSize(new Dimension(53, 19));
     jButton3.setMargin(new Insets(0, 0, 0, 0));
     jButton3.setText("newword");
     jButton3.addActionListener(new JTestFrame_jButton3_actionAdapter(this));
     jButton4.setBounds(new Rectangle(608, 737, 80, 27));
     jButton4.setText("newword");
     jButton4.addActionListener(new JTestFrame_jButton4_actionAdapter(this));
     jButtonsave1.setBounds(new Rectangle(6, 602, 21, 26));
     jButtonsave1.setText("jButton5");
     jButtonsave1.addActionListener(new JTestFrame_jButtonsave1_actionAdapter(this));
     jButtonsave2.setText("jButton5");
     jButtonsave2.addActionListener(new JTestFrame_jButtonsave2_actionAdapter(this));
     jButtonsave2.setBounds(new Rectangle(6, 647, 21, 26));
     jButtonsave3.setText("jButton5");
     jButtonsave3.addActionListener(new JTestFrame_jButtonsave3_actionAdapter(this));
     jButtonsave3.setBounds(new Rectangle(6, 692, 21, 26));
     jButtonsave4.setText("jButton5");
     jButtonsave4.addActionListener(new JTestFrame_jButtonsave4_actionAdapter(this));
     jButtonsave4.setBounds(new Rectangle(6, 737, 21, 26));
     jButton5.setBounds(new Rectangle(612, 790, 90, 32));
     jButton5.setText("reset");
     jButton5.addActionListener(new JTestFrame_jButton5_actionAdapter(this));
     jButtonblock1.setBounds(new Rectangle(375, 602, 27, 27));
     jButtonblock1.setText("block");
     jButtonblock1.addActionListener(new JTestFrame_jButtonblock1_actionAdapter(this));
     jButtonblock2.setText("block");
     jButtonblock2.addActionListener(new JTestFrame_jButtonblock2_actionAdapter(this));
     jButtonblock2.setBounds(new Rectangle(375, 647, 27, 27));
     jButtonblock3.setText("block");
     jButtonblock3.addActionListener(new JTestFrame_jButtonblock3_actionAdapter(this));
     jButtonblock3.setBounds(new Rectangle(375, 692, 27, 27));
     jButtonblock4.setText("block");
     jButtonblock4.addActionListener(new JTestFrame_jButtonblock4_actionAdapter(this));
     jButtonblock4.setBounds(new Rectangle(375, 739, 27, 27));
     jLabel1.setText("BLOCK");
     jLabel1.setBounds(new Rectangle(362, 573, 45, 24));
     jLabel2.setText("SYNONYM / NEW");
     jLabel2.setBounds(new Rectangle(413, 578, 94, 15));
     jLabel3.setText("SYNONYM");
     jLabel3.setBounds(new Rectangle(524, 580, 66, 15));
     jLabel4.setToolTipText("");
     jLabel4.setText("SAVE");
     jLabel4.setBounds(new Rectangle(6, 579, 49, 18));
     jLabel5.setText("ABSTRACT");
     jLabel5.setBounds(new Rectangle(308, 605, 65, 25));
     jLabel6.setBounds(new Rectangle(308, 649, 65, 25));
     jLabel6.setText("FIGURAL");
     jLabel7.setText("Coordinate");
     jLabel7.setBounds(new Rectangle(311, 695, 60, 23));
     jLabel8.setToolTipText("");
     jLabel8.setText("CR");
     jLabel8.setBounds(new Rectangle(315, 739, 39, 27));
     jTextFieldpermitted.setText("");
     jTextFieldpermitted.setBounds(new Rectangle(12, 793, 574, 27));
     jLabel9.setText("jLabel9");
    jLabel9.setBounds(new Rectangle(304, 847, 73, 30));
    jTextField5.setText("jTextField5");
    jTextField5.setBounds(new Rectangle(28, 848, 183, 27));
    jButton6.setBounds(new Rectangle(225, 851, 49, 27));
    jButton6.setText("jButton6");
    jButton6.addActionListener(new JTestFrame_jButton6_actionAdapter(this));
    this.getContentPane().add(jScrollPane1,  BorderLayout.NORTH);
     this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
     jPanel1.add(jTextField1, null);
     jPanel1.add(jTextField2, null);
     jPanel1.add(jTextField3, null);
     jPanel1.add(jTextField4, null);
     jScrollPane1.getViewport().add(jList1, null);
     jPanel1.add(jTextField2a, null);
     jPanel1.add(jTextField3a, null);
     jPanel1.add(jTextField4a, null);
     jPanel1.add(jButton1, null);
     jPanel1.add(jButton2, null);
     jPanel1.add(jButton3, null);
     jPanel1.add(jButton4, null);
     jPanel1.add(jTextField1a, null);
     jPanel1.add(jTextField4b, null);
     jPanel1.add(jTextField1b, null);
     jPanel1.add(jTextField2b, null);
     jPanel1.add(jTextField3b, null);
     jPanel1.add(jButtonsave1, null);
     jPanel1.add(jButtonsave2, null);
     jPanel1.add(jButtonsave3, null);
     jPanel1.add(jButtonsave4, null);
     jPanel1.add(jButton5, null);
     jPanel1.add(jButtonblock4, null);
     jPanel1.add(jButtonblock3, null);
     jPanel1.add(jButtonblock2, null);
     jPanel1.add(jButtonblock1, null);
     jPanel1.add(jLabel1, null);
     jPanel1.add(jLabel2, null);
     jPanel1.add(jLabel3, null);
     jPanel1.add(jLabel4, null);
     jPanel1.add(jLabel5, null);
     jPanel1.add(jLabel6, null);
     jPanel1.add(jLabel7, null);
     jPanel1.add(jLabel8, null);
     jPanel1.add(jTextFieldpermitted, null);
    jPanel1.add(jTextField5, null);
    jPanel1.add(jButton6, null);
    jPanel1.add(jLabel9, null);


  }

  void jList1_propertyChange(PropertyChangeEvent e) {

  }

  void jList1_valueChanged(ListSelectionEvent e) {
       //jTextField1.setText(c.getClarificationFragment((String)jList1.getSelectedValue()));
       Vector v = pr.getAllClarifications((String)jList1.getSelectedValue());
       Vector v1 = (Vector)v.elementAt(0);
       Vector v2 = (Vector)v.elementAt(1);
       Vector v3 = (Vector)v.elementAt(2);
       Vector v4 = (Vector)v.elementAt(3);
       Vector v5 = (Vector)v.elementAt(4);

       jTextField4.setText(pr.turnVectorToString(v4));
       jTextField3.setText(pr.turnVectorToString(v3));
       jTextField2.setText(pr.turnVectorToString(v2));
       jTextField1.setText(pr.turnVectorToString(v1));
       jTextFieldpermitted.setText(pr.turnVectorToString(v5));

       this.jLabel9.setText(pr.getAClarificationFastest((String)jList1.getSelectedValue()));
       pr.jumbleAllVectors();
  }

  void jButtonsave1_actionPerformed(ActionEvent e) {
      pr.pl.saveFrags("a");
  }

  void jButtonsave2_actionPerformed(ActionEvent e) {
      pr.pl.saveFrags("f");
  }

  void jButtonsave3_actionPerformed(ActionEvent e) {
      pr.pl.saveFrags("acr");
  }

  void jButtonsave4_actionPerformed(ActionEvent e) {
      pr.pl.saveFrags("ac");
  }

  void jButton2_actionPerformed(ActionEvent e) {
    try{
     String n = this.jTextField2a.getText();
     String n2 = this.jTextField2b.getText();
     if(n.length()!=0&n2.length()!=0){
        pr.pl.addSynonymFigural(n,n2);
        System.out.println("adding synonym");
        System.out.println("LENGTH "+n2.length());
     }
     else if(n.length()!=0){
        pr.pl.addFragmentFigural(n);
        System.out.println("adding fragment");
     }

    } catch(Exception e2){System.out.println("Cant add synonym");}

  }

  void jButton1_actionPerformed(ActionEvent e) {
    try{
        String n = this.jTextField1a.getText();
        String n2 = this.jTextField1b.getText();
        if(n.length()!=0&n2.length()!=0){
           pr.pl.addSynonymAbstract(n,n2);
           System.out.println("adding synonym");
        }
        else if(n.length()!=0){
           pr.pl.addFragmentAbstract(n);
           System.out.println("adding fragment");
        }
       } catch(Exception e2){System.out.println("Cant add synonym");}
    }


  void jButton4_actionPerformed(ActionEvent e) {
        String n = this.jTextField3a.getText();
        if(n.length()!=0){
            pr.pl.addFragmentAbstract_CR(n);
            System.out.println("adding fragment");
        }


  }

  void jButton3_actionPerformed(ActionEvent e) {
    String n = this.jTextField4a.getText();
    if(n.length()!=0){
        pr.pl.addFragmentAbstract_Coord(n);
         System.out.println("adding fragment");
    }


  }

  void jButton5_actionPerformed(ActionEvent e) {
       pr.pl.loadRawPhrases();
  }

  void jButtonblock1_actionPerformed(ActionEvent e) {

  }

  void jButtonblock2_actionPerformed(ActionEvent e) {

  }

  void jButtonblock3_actionPerformed(ActionEvent e) {

  }

  void jButtonblock4_actionPerformed(ActionEvent e) {

  }

  void jButton6_actionPerformed(ActionEvent e) {
    Vector v = pr.getAllClarifications((String)this.jTextField5.getText());
     Vector v1 = (Vector)v.elementAt(0);
     Vector v2 = (Vector)v.elementAt(1);
     Vector v3 = (Vector)v.elementAt(2);
     Vector v4 = (Vector)v.elementAt(3);
     Vector v5 = (Vector)v.elementAt(4);

     //jTextField4.setText(pr.turnVectorToString(v4));
     //jTextField3.setText(pr.turnVectorToString(v3));
     //jTextField2.setText(pr.turnVectorToString(v2));
     //jTextField1.setText(pr.turnVectorToString(v1));
     //jTextFieldpermitted.setText(pr.turnVectorToString(v5));

     this.jLabel9.setText(pr.getAClarificationFastest((String)this.jTextField5.getText()));
     //pr.jumbleAllVectors();

  }








}

class JTestFrame_jList1_propertyChangeAdapter implements java.beans.PropertyChangeListener {
  JTestFrame adaptee;

  JTestFrame_jList1_propertyChangeAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void propertyChange(PropertyChangeEvent e) {
    adaptee.jList1_propertyChange(e);
  }
}

class JTestFrame_jList1_listSelectionAdapter implements javax.swing.event.ListSelectionListener {
  JTestFrame adaptee;

  JTestFrame_jList1_listSelectionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void valueChanged(ListSelectionEvent e) {
    adaptee.jList1_valueChanged(e);
  }
}

class JTestFrame_jButtonsave1_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButtonsave1_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonsave1_actionPerformed(e);
  }
}

class JTestFrame_jButtonsave2_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButtonsave2_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonsave2_actionPerformed(e);
  }
}

class JTestFrame_jButtonsave3_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButtonsave3_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonsave3_actionPerformed(e);
  }
}

class JTestFrame_jButtonsave4_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButtonsave4_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonsave4_actionPerformed(e);
  }
}

class JTestFrame_jButton2_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButton2_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton2_actionPerformed(e);
  }
}

class JTestFrame_jButton1_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButton1_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class JTestFrame_jButton3_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButton3_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton3_actionPerformed(e);
  }
}

class JTestFrame_jButton4_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButton4_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton4_actionPerformed(e);
  }
}

class JTestFrame_jButton5_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButton5_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton5_actionPerformed(e);
  }
}

class JTestFrame_jButtonblock1_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButtonblock1_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonblock1_actionPerformed(e);
  }
}

class JTestFrame_jButtonblock2_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButtonblock2_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonblock2_actionPerformed(e);
  }
}

class JTestFrame_jButtonblock3_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButtonblock3_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonblock3_actionPerformed(e);
  }
}

class JTestFrame_jButtonblock4_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButtonblock4_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonblock4_actionPerformed(e);
  }
}

class JTestFrame_jButton6_actionAdapter implements java.awt.event.ActionListener {
  JTestFrame adaptee;

  JTestFrame_jButton6_actionAdapter(JTestFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton6_actionPerformed(e);
  }
}
