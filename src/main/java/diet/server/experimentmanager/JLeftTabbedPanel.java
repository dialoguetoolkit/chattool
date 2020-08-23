/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.experimentmanager;

import java.awt.Color;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.theme.DefaultTheme;
import net.infonode.tabbedpanel.theme.ShapedGradientTheme;
import net.infonode.tabbedpanel.theme.TabbedPanelTitledTabTheme;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;
import net.infonode.util.Direction;
import diet.server.ConversationUIManager;

/**
 *
 * @author user
 */
public class JLeftTabbedPanel extends TabbedPanel{
    
    
    private Hashtable allComponentsTabs = new Hashtable();
    EMUI em;
    private int fontSize = 15;
    //TabbedPanelTitledTabTheme[] themes = new TabbedPanelTitledTabTheme[]{new DefaultTheme(), new LookAndFeelTheme(), new ClassicTheme(), new BlueHighlightTheme(), new SmallFlatTheme(), new GradientTheme(),
    //                                                                           new GradientTheme(true, true), new ShapedGradientTheme(), new ShapedGradientTheme(
    //                                                                               0f,
    //                                                                               0f,
    //                                                                               new FixedColorProvider(
    //                                                                                   new Color(150, 150, 150)),
    //                                                                               null) {
    //                                                                             public String getName() {
    //                                                                               return super.getName() +
    //                                                                                      " Flat with no Slopes";
    //                                                                             }
    //                                                                           }};
    
    TabbedPanelTitledTabTheme theme;
    //

    TitledTabProperties titledTabProperties = new TitledTabProperties();
       
    
    
    public JLeftTabbedPanel (EMUI em){       
        super();
        try{
           theme = new ShapedGradientTheme(0f, 0f, 
            new FixedColorProvider(new Color(150, 150, 150)), null) {
                 public String getName() {return super.getName() + " Flat with no Slopes";}};
        }catch(Exception e){
            theme = new DefaultTheme();
        }         
        if (theme==null)theme = new DefaultTheme();
        titledTabProperties.getNormalProperties().setDirection(Direction.UP);
        titledTabProperties.addSuperObject(theme.getTitledTabProperties());
        super.getProperties().addSuperObject(theme.getTabbedPanelProperties());
        
        TabbedPanelProperties properties = super.getProperties();
        properties.setTabAreaOrientation(Direction.LEFT);
        this.em = em;
       
   
    }
    public void displayPanelAddingIfNecessaryDeprecated(ConversationUIManager cUI){
        TitledTabWithFontSettings tab = new TitledTabWithFontSettings(cUI.getConvID(),null,new JPanel(),null,fontSize);
        tab.getProperties().addSuperObject(titledTabProperties);
         super.addTab(tab);     
    }
    
    public void removePanel(final JComponent jc){
       final TabbedPanel superTabbedPanel = this; 
        
       SwingUtilities.invokeLater(new Runnable(){
           public void run(){
             try{
                TitledTabWithFontSettings tt = (TitledTabWithFontSettings)allComponentsTabs.get(jc);
                if(tt!=null){
                   superTabbedPanel.removeTab(tt);
                   allComponentsTabs.remove(tt);
                   
                }
                else{
                    
                   
                }
             }catch(Exception e){
                System.err.println("ERROR CLOSING TAB");
                
       }  
           }
       }); 
       
    }
    
    
   public void displayPanelAddingIfNecessary(String s,JComponent jp){
        
        if(super.getTabCount()>0){
          JLeftTabbedPanelCloseButton jltpcb = new JLeftTabbedPanelCloseButton(em,jp);
          TitledTabWithFontSettings tab = new TitledTabWithFontSettings(s,null,jp,jltpcb,fontSize);//null,fontSize);
          tab.getProperties().addSuperObject(titledTabProperties);
          super.addTab(tab);
          this.allComponentsTabs.put(jp, tab);
          super.setSelectedTab(tab);
        }
        else {
          TitledTabWithFontSettings tab = new TitledTabWithFontSettings(s,null,jp,null,fontSize);//null,fontSize);
          tab.getProperties().addSuperObject(titledTabProperties);
          super.addTab(tab);
          super.setSelectedTab(tab);
          
        }
       
                
    }
   
   public void displayPanelAddingIfNecessary(JComponent jc){
        TitledTabWithFontSettings tab = new TitledTabWithFontSettings("Conversation",null,new JPanel(),null,fontSize);
         tab.getProperties().addSuperObject(titledTabProperties);
         super.addTab(tab);     
    }             
        
 

}
