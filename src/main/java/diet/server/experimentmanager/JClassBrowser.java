package diet.server.experimentmanager;

import diet.server.ConversationController.DefaultConversationController;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sre
 */
public class JClassBrowser extends javax.swing.JPanel  {

    
    JExperimentManagerMainFrame jemmf;
    JList publicJLIST1;
    
    
    public static Vector useNEWMETHOD(){
        
        
        
       List<String> subclasses = new FastClasspathScanner("diet.server.ConversationController").scan().
               getNamesOfSubclassesOf("diet.server.ConversationController.DefaultConversationController");
       
    // No need to add any MatchProcessors, just create a new scanner and then call
    // .scan() to parse the class hierarchy of all classfiles on the classpath.
    
              
              
    // Get the names of all subclasses of Widget on the classpath,
    // again without calling the classloader:
            
               
       
       for(int i=0;i<subclasses.size();i++){
           System.err.println(subclasses.get(i));
       }
       
       Vector v = new Vector();
       v.addAll(subclasses);
       Vector vNEW = new Vector();
       for(int i=0;i<v.size();i++){
           String s = (String)v.elementAt(i);
           try{
              Class t = Class.forName(s);
              vNEW.addElement(t);
           }catch (Exception e){
               e.printStackTrace();
           }
           
       }
       
       return vNEW;
    }

    //Yes this is very hacky.
    
    public void valueChanged(ListSelectionEvent arg0) {
         
        
        
         String s = (String)jList1.getSelectedValue();
         s=s.toUpperCase();
         System.err.println("UPPERCASE: "+s);
         
         
         
         if(s.contains("TELEGRAM")){
             jButton4.setEnabled(false);
             jButton5.setEnabled(false);
             jButton6.setEnabled(false); 
             
             //jButton4.setVisible(false);
             //jButton5.setVisible(false);
             //jButton6.setVisible(false);
             
             System.err.println("BUTTONSDISABLING");
         }
         else{
             jButton4.setEnabled(true);
             jButton5.setEnabled(true);
             jButton6.setEnabled(true); 
              System.err.println("BUTTONSENABLING");
         }
         this.validate();
         this.repaint();
          
    }
    
    
    
    
    
    
    
    public static Vector  getConversationControllers(){
           Vector v = new Vector();
           final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
           // add include filters which matches all the classes (or use your own)
           provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

          // get matching classes defined in the package
          final Set<BeanDefinition> classes = provider.findCandidateComponents("diet.server.ConversationController");

         // this is how you can load the class type from BeanDefinition instance
         for (BeanDefinition bean: classes) {
              try{
               Class<?> clazz = Class.forName(bean.getBeanClassName());
                Method[] ms = clazz.getMethods();
                for(int j=0;j<ms.length;j++){
                     if(ms[j].getName().equalsIgnoreCase("showcCONGUI")){
                          Method mSHOWONGUI =ms[j];
                          boolean show = (Boolean)mSHOWONGUI.invoke(null);
                          if(show){
                              String className = clazz.getSimpleName();
                              if(!className.equalsIgnoreCase("DefaultConversationController")){
                                  System.out.println("......"+className);
                                  v.addElement(className);
                              }
                          }
                          
                     }
                }
                
                
                
               }catch(Exception e){
                   e.printStackTrace();
               }
              } 
           return v;
     }
    
    
    
    public static Vector getConversationControllersOLD(){
    
        //System.out.println("THE NAME OF THE JAR IS: "+getJarFileName());
        //CustomDialog.showDialog("TEMPORARY MESSAGE");
        
         System.out.println("(AB) ");
        Vector allSubClasses = useNEWMETHOD();
         System.out.println("(AC) "+allSubClasses.size());
        
                
        Vector listOfAvailableConversationControllers = new Vector();
        
        for(int i=0;i<allSubClasses.size();i++){
            Class c = (Class)allSubClasses.get(i);
            try{
               //Class t = Class.forName(name.substring(0, name.length()-6));
               Method[] ms = c.getMethods();
               Method mSHOWONGUI = null;
               for(int j=0;j<ms.length;j++){
                   //System.err.println(ms[j].getName());
                   if(ms[j].getName().equalsIgnoreCase("showcCONGUI")){
                       mSHOWONGUI=ms[j];
                       //System.err.println("FOUND");
                       System.err.println("(AD)"+c.getName());
                       
                       boolean show = (Boolean)mSHOWONGUI.invoke(null);
                       if(show){
                            String className = c.getSimpleName();
                            if(!className.equalsIgnoreCase("DefaultConversationController")){
                                listOfAvailableConversationControllers.addElement(className);
                                System.out.println("......"+className);
                            }
                            
                           
                            System.out.println("......"+c.getSimpleName());
                            
                            //String name = t.
                       }
                       break;
                   }
               }
               
               
               
              
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        
        
     
       
        return listOfAvailableConversationControllers;
    }
    
    
    
    
    
    
    public JClassBrowser( JExperimentManagerMainFrame jemmf){
        
        initComponents();
        publicJLIST1 = jList1;
        this.jemmf=jemmf;
        
        //ClassLoad cl = new ClassLoad();
        System.err.println("(AA) loading conversation controllers");
        Vector v = this.getConversationControllers();
       // Vector v =useNEWMETHOD();
        
        System.err.println("(AAA) size"+v.size());
        
        
        
        jList1.removeAll();
        jList1.setListData(v);
        this.repaint();
        jList1.repaint();
        jList1.addMouseListener(new JClassPopupMenu(this));
        
        SwingUtilities.updateComponentTreeUI(this);
        this.setVisible(true);
        this.jList1.setSelectedIndex(0);
        
        //jList1.addListSelectionListener(this);
        
    }
    
    
    
    /**
     * Creates new form JClassBrowser
     */
    public JClassBrowser() {
        initComponents();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneBOTTOM = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(950, 500));
        setPreferredSize(new java.awt.Dimension(1000, 500));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        jScrollPaneBOTTOM.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneBOTTOM.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jScrollPaneBOTTOM.setOpaque(false);
        jScrollPaneBOTTOM.setPreferredSize(new java.awt.Dimension(350, 400));

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList1MouseReleased(evt);
            }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPaneBOTTOM.setViewportView(jList1);

        add(jScrollPaneBOTTOM);

        jPanel1.setMaximumSize(new java.awt.Dimension(230, 4000));
        jPanel1.setPreferredSize(new java.awt.Dimension(210, 10));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Run clients on local machine:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton4.setText("Manual login");
        jButton4.setToolTipText("<html>This starts the experimental script and also immediately starts clients on the local machine. <br>\nUse this for trying out / testing / debugging a script</html>");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setText("Auto login");
        jButton5.setToolTipText("<html>This option <br>\n(1) starts the experimental script <br>\n(2) starts the clients on the local machine <br>\n(3) attempts to log the clients in automatically - you don't need to enter the participant ID or the Username <br>\n<br>\nUse this option if you are sick of entering the participant ID while testing / debugging<br>\n</html>");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setText("Additional Client");
        jButton6.setToolTipText("<html>\nThis option starts an additional client on the local machine that attempts to login to the experimental script<br>\n</html>\n");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Experiment:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("START");
        jButton1.setToolTipText("<html>Starts the experimental script<br>and waits for clients to connect</html>");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 374, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseReleased
        // TODO add your handling code here:
        if(evt.getButton() == MouseEvent.BUTTON2 || evt.getButton() == MouseEvent.BUTTON3){
             
        }
        jButton4.setEnabled(false);
        jButton5.setEnabled(false);
        jButton6.setEnabled(false); 
             
        
    }//GEN-LAST:event_jList1MouseReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       //Auto-login
       String oSelected = (String)publicJLIST1.getSelectedValue();
        if(oSelected==null)return;
        DefaultConversationController.sett.login_autologin=true;
        jemmf.expmanUI.runExperimentLocally(oSelected);
      
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //Manual login
        String oSelected = (String)publicJLIST1.getSelectedValue();
        if(oSelected==null)return;
        jemmf.expmanUI.runExperimentLocally(oSelected);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String oSelected = (String)publicJLIST1.getSelectedValue();
        if(oSelected==null)return;
        DefaultConversationController.sett.login_autologin=false;
        jemmf.expmanUI.createClientsLocally(1, this.jemmf.expmanUI.portNumber);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //START
        String oSelected = (String)publicJLIST1.getSelectedValue();
        if(oSelected==null)return;
        jemmf.expmanUI.runExperiment(oSelected);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
        String oSelected = (String)publicJLIST1.getSelectedValue();
        if(oSelected==null){
            jButton1.setEnabled(false);
            jButton4.setEnabled(false);
            jButton5.setEnabled(false);
            jButton6.setEnabled(false);
             jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Run clients on local machine:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), Color.gray)); // NOI18N

            
            
        }
        else if((oSelected.toUpperCase()).contains("TELEGRAM")){
           
            jButton4.setEnabled(false);
            jButton5.setEnabled(false);
            jButton6.setEnabled(false);
            jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Run clients on local machine:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), Color.gray)); // NOI18N

        }
        
        
        else{
            jButton1.setEnabled(true);
            jButton4.setEnabled(true);
            jButton5.setEnabled(true);
            jButton6.setEnabled(true);
            jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Run clients on local machine:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), Color.BLACK)); // NOI18N

        }
        
    }//GEN-LAST:event_jList1ValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPaneBOTTOM;
    // End of variables declaration//GEN-END:variables





public class JClassPopupMenu extends JPopupMenu implements MouseListener, ActionListener{

   
    
    JClassBrowser jcb;
    
    public JClassPopupMenu(JClassBrowser jcb){
        this.jcb=jcb;
    } 
            
    
    
    
     public void actionPerformed(ActionEvent e) {
        //System.out.println(e.getSource().getClass().toString());
        System.out.println(((JMenuItem)e.getSource()).getText());
        String choice = (((JMenuItem)e.getSource()).getText());
        if(choice.equalsIgnoreCase("Run as new experiment")){
            String oSelected = (String)publicJLIST1.getSelectedValue();
            if(oSelected==null)return;
            jcb.jemmf.expmanUI.runExperiment(oSelected);
        }
        else if(choice.equalsIgnoreCase("Run locally as demo")){
            String oSelected = (String)publicJLIST1.getSelectedValue();
            if(oSelected==null)return;
            jcb.jemmf.expmanUI.runExperimentLocally(oSelected);
        }
        
        //jemmf.expmanUI.expmanager.createAndActivateNewExperiment(((JMenuItem)e.getSource()).getText());
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("Not supported yet1.");
    }

    public void mouseEntered(MouseEvent e) {
        System.out.println("Not supported yet2.");
    }

    public void mouseExited(MouseEvent e) {
        System.out.println("Not supported yet3.");
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("Not supported yet4.");
         if(e.isPopupTrigger()){
            maybeShowPopup(e);
        }
    }

    public void mouseReleased(MouseEvent e) {      
        if(e.isPopupTrigger()){
            maybeShowPopup(e);
        }
       
        
        
    }

    public void maybeShowPopup(MouseEvent e){
        //System.exit(-3);
        Object o = e.getSource();
        if(o instanceof JList){
            JList jl = (JList)o;
            Object oSelected = (String)jl.getSelectedValue();
            if(oSelected==null)return;
            
                       
            JPopupMenu popup = new JPopupMenu();
            JMenuItem menuItem = new JMenuItem("Run as new experiment");
                       
            menuItem.addActionListener(this);
            popup.add(menuItem);
            menuItem = new JMenuItem("Run locally as demo");
            menuItem.addActionListener(this);
            popup.add(menuItem);
             if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                       e.getX(), e.getY());
        }
            
        }
    }
    
}











}



