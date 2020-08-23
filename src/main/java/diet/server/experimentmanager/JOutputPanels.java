/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.experimentmanager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import org.wonderly.swing.tabs.CloseableTabbedPane;
import org.wonderly.swing.tabs.TabCloseEvent;
import org.wonderly.swing.tabs.TabCloseListener;



/**
 *
 * @author user
 */
public class JOutputPanels extends JPanel {

    BorderLayout bl = new BorderLayout();
    CloseableTabbedPane jtp;
    JLabel header = new JLabel("");

    public JOutputPanels() {
        super();
        this.setLayout(bl);
        //this.add(header,BorderLayout.NORTH);

        jtp = new CloseableTabbedPane();
        jtp.addTabCloseListener(new TabCloseListener() {

            public void tabClosed(TabCloseEvent tce) {
                int i = tce.getClosedTab();
                System.out.println("IT IS " + i);
                jtp.removeTabAt(i);
            }
        });

        //new CloseButton();
        ////TabbedPanel tabbedPanel = new TabbedPanel();
        ////TitledTab tab1 = new TitledTab("Templates & Settings", null, new JTextArea(),null,15);
        ////TitledTab tab2 = new TitledTab("Experiment WOT:FRAG", null, new JTextArea(), null,15);
        ////TitledTab tab3 = new TitledTab("Experiment Shortest common turn", null, new JTextArea(), null,15);
        ////tabbedPanel.addTab(tab1);
        ////tabbedPanel.addTab(tab2);
        ////tabbedPanel.addTab(tab3);
  
        
        
        /*TabbedPanelTitledTabTheme[] themes = new TabbedPanelTitledTabTheme[]{new DefaultTheme(), new LookAndFeelTheme(), new ClassicTheme(), new BlueHighlightTheme(), new SmallFlatTheme(), new GradientTheme(),
                                                                               new GradientTheme(true, true), new ShapedGradientTheme(), new ShapedGradientTheme(
                                                                                   0f,
                                                                                   0f,
                                                                                   new FixedColorProvider(
                                                                                       new Color(150, 150, 150)),
                                                                                   null) {
                                                                                 public String getName() {
                                                                                   return super.getName() +
                                                                                          " Flat with no Slopes";
                                                                                 }
                                                                               }};
        
        */
        ////TabbedPanelTitledTabTheme theme = themes[8];
    
        ////TitledTabProperties titledTabProperties = new TitledTabProperties();
        ////titledTabProperties.getNormalProperties().setDirection(Direction.RIGHT);
        ////titledTabProperties.addSuperObject(theme.getTitledTabProperties());
    
 
        ////tab1.getProperties().addSuperObject(titledTabProperties);
        ////tab2.getProperties().addSuperObject(titledTabProperties);
        ////tab3.getProperties().addSuperObject(titledTabProperties);
        ////tabbedPanel.getProperties().addSuperObject(theme.getTabbedPanelProperties());
        
        ////TabbedPanelProperties properties = tabbedPanel.getProperties();
        ////properties.setTabAreaOrientation(Direction.DOWN);
        
        //Object o = new SimpleTabbedPanelExample();
        
        //// JButton jb = new JButton("X");
        //// jb.setForeground(Color.red);
        //// tab1.setHighlightedStateTitleComponent(jb);//createCloseTabButton(tab1));
        
        /////JLeftTabbedPanel jltp = new JLeftTabbedPanel();
        //TitledTab tab4 = new TitledTab("Experiment Shortest common turn", null, new JTextArea(), null,15);
        ////jltp.displayPanelAddingIfNecessary(new JButton());
        ////jltp.displayPanelAddingIfNecessary(new JButton());
        //jltp.addTab(tab4);        
       
        //jltp.displayPanelAddingIfNecessary();
        //jtp.addTab("Component2", jltp);
               
        //properties.getTabAreaComponentsProperties().setStretchEnabled(true);
        //jtp.addTab("Component", tabbedPanel);

        jtp.setBorder(javax.swing.BorderFactory.createTitledBorder("Output"));


        jtp.setTabPlacement(JTabbedPane.LEFT);
        this.add(jtp, BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(900, 200));
        outputTextCreatingPanelIfNecessary("Main", "The clients will need to know the internet address of the server.\nBelow should be a list of all "
                + "internet addresses that are associated with the server.\n"+showIPAddress());
        //outputTextCreatingPanelIfNecessary("Main2", "Initializing2....");
    }

    private String showIPAddress(){
        String ipaddresses = "";
        try{
            
            String ip;
    try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            // filters out 127.0.0.1 and inactive interfaces
            if (iface.isLoopback() || !iface.isUp())
                continue;

            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while(addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                ip = addr.getHostAddress();
                if(this.containsCharacter(ip)){
                    //ip = "";
                }
                else{
                     ipaddresses = ipaddresses+ip+"     ("+ iface.getDisplayName() + ")" +"\n";
                }
                
               
                System.out.println(iface.getDisplayName() + " " + ip);
                
            }
        }
    } catch (SocketException e) {
        throw new RuntimeException(e);
    }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return ipaddresses+ "If none of the above internet addresses work, please check your firewall settings and/or use ipconfig (Windows) or ifconfig (apple)\n"
                ;
    }
    
    private boolean containsCharacter(String s){
        for(int i=0;i<s.length();i++){
            Character c = s.charAt(i);
            if(Character.isAlphabetic(c))return true;
        }
        return false;
    }
    
    
    
    synchronized public void outputTextCreatingPanelIfNecessary(final String panelName, final String text) {
      SwingUtilities.invokeLater(new Runnable(){
        public void run(){    
      
        
        JOutput jo=null;
        for (int i = 0; i < jtp.getTabCount(); i++) {
            String s = jtp.getTitleAt(i);
            //System.out.println("-------------------------LOOKING FOR PANEL "+panelName+" : FOUND "+s);
            if (panelName.equalsIgnoreCase(s)) {
                jo=(JOutput)jtp.getComponentAt(i);
                break;   
            }  
        }
        if(jo==null){
                jo = new JOutput();
                jtp.addTab(panelName, jo);
                jo.appendText(text);
        }
        else{
                jo.appendText(text);
        }
        }});         
    }

    private synchronized void createNewOutputPanelAndAppendText(final String name, final String text) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JOutput jo = new JOutput();
                jtp.addTab(name, jo);
                //System.out.println("-------------------ADDING PANELNAME "+name);
                //jtp.setBackgroundAt(jtp.getTabCount()-1, Color.WHITE);
                //jtp.setBackground(Color.WHITE);
                jo.appendText(text);

            }
        });
    }
}
