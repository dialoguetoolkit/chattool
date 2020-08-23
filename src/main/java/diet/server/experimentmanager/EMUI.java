/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.experimentmanager;

import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;


import diet.client.ConnectionToServer;

import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationUIManager;
import diet.server.ExperimentManager;

/**
 *
 * @author user
 */
public class EMUI {

   
    //int portNo = 4444;
    JExperimentManagerMainFrame jmf;

   
    ExperimentManager expmanager;
   
    Hashtable eachConversationsDynamicTextDisplay = new Hashtable();
    Hashtable eachTabbedPanesConversation = new Hashtable();
    static JExperimentManagerMainFrame jmfStatic;
    
    int portNumber = -444;
    
    //have option for switching off saving each keypress (sluggish)
            
    //switch off saving each message that is sent
    
    //save in mazeinfo
            
            
    /**
     * Main class
     */
    public EMUI(final int portNumber){
        final EMUI thisEMUI = this;
         this.portNumber = portNumber;
        //File generalSettingsFile = new File(System.getProperty("user.dir")+File.separator+"data"+File.separator+"General settings.xml");
        //Vector v =SavedExperimentsAndSettingsFile.readParameterObjects(generalSettingsFile); 
        //ExperimentSettings generalSett = new ExperimentSettings(v);
          
          
        
        
       
        
        expmanager = new ExperimentManager(this,portNumber);
        try{
          SwingUtilities.invokeLater(new Runnable(){
              public void run(){
                  jmf = new JExperimentManagerMainFrame(thisEMUI,System.getProperty("user.dir")+File.separator+"data","DiET: Server. Port no. "+""+portNumber);
                  
              }
          });  
          
        }catch (Exception e){
            System.out.println("ERROR SETTING UP JEXPERIMENTMANAGERMAINFRAME");
        }
        jmfStatic = jmf;
              //ExperimentSettings expSett = getExperimentSettings();
              //expmanager.createAndActivateNewExperiment(expSett);
              //int n = (Integer)expSett.getV("Number of participants per conversation");
              //createClientsLocally(n,4444);
              //EMUI.println("NEW WINDOW", "HELLO");
        
    }    
    
    
    
    
    
    public Vector getCurrentlyRunningExperiments(){
        return new Vector();
    }
    
    
    
   
    
    
    public void runExperiment(final String nameOfConversationController){ 
        Thread r = new Thread(){
           public void run(){           
              ////expSett.changeParameterValue("Experiment Data Folder",System.getProperty("user.dir")+"\\data\\Saved experimental data");
              ////expSett.changeParameterValue("Experiment Data Folder",System.getProperty("user.dir")+File.separator+"data"+File.separator+"Saved experimental data");
              expmanager.createAndActivateNewExperiment(nameOfConversationController);
             
           }
        };  
         r.start();
    }   
    
    public void runExperimentLocally(final String nameOfConversationController){ 
        Thread r = new Thread(){
           public void run(){           
              ////expSett.changeParameterValue("Experiment Data Folder",System.getProperty("user.dir")+"\\data\\Saved experimental data");
              ////expSett.changeParameterValue("Experiment Data Folder",System.getProperty("user.dir")+File.separator+"data"+File.separator+"Saved experimental data");
              Conversation c = expmanager.createAndActivateNewExperiment(nameOfConversationController);
              int n = (Integer)c.getController().sett.login_numberOfParticipants;
              createClientsLocally(n,portNumber);
           }
        };  
         r.start();
    }   
    
        
  
       
    
    
    
      
    
    
     public void displayConversationUITelegram(Conversation c, ConversationUIManager convUI){
        
        JTabbedPane jtp = (JTabbedPane)convUI.createConversationTabbedPaneTelegram();
        JFrame jc = convUI.createClientTextEntryFields();
        jc.setVisible(false);
        this.eachConversationsDynamicTextDisplay.put(c,jc);
        this.jmf.addConvIOtoLeftTabbedPane(c.getController().getID(),jtp);
        if(c==null)System.exit(-12341234);
        if(jtp==null)System.exit(-12341235);
        this.eachTabbedPanesConversation.put(jtp,c);
    }
    
    
    
    public void displayConversationUI(Conversation c, ConversationUIManager convUI){
        
        JTabbedPane jtp = (JTabbedPane)convUI.createConversationTabbedPane();
        JFrame jc = convUI.createClientTextEntryFields();
        this.eachConversationsDynamicTextDisplay.put(c,jc);
        this.jmf.addConvIOtoLeftTabbedPane(c.getController().getID(),jtp);
        if(c==null)System.exit(-12341234);
        if(jtp==null)System.exit(-12341235);
        this.eachTabbedPanesConversation.put(jtp,c);
    }
    
      public void stopAllExperimentThreads(JComponent jc,boolean closeWindowOnServer,boolean closeClientsDown){
        System.err.println("STOP THE EXPERIMENT: " +jc.getClass().toString());
        Conversation c = (Conversation)this.eachTabbedPanesConversation.get(jc);
        if(c==null){
            System.exit(-123456);
        }
        this.expmanager.allConversations.remove(c);
        c.closeDown(closeClientsDown);
        if(closeWindowOnServer){
            this.eachTabbedPanesConversation.remove(jc);
            this.jmf.removeConvIOFromLeftTabbedPane(jc);
            
            //ADD IN CODE REMOVING THE TABLES FROM THE WINDOW
        }
        
    }
    
    public void toggleScrolling(boolean doScrolling){
        doScrollingOfConversations = doScrolling;
    }
    
    
    
    public void displayONOFFDynamicTextDisplayOfConversationCorrespondingToTabbedPane(JComponent tabbedPane){
        Conversation c = (Conversation)this.eachTabbedPanesConversation.get(tabbedPane);
        JFrame jf = (JFrame)this.eachConversationsDynamicTextDisplay.get(c);
        jf.setVisible(!jf.isVisible());
       
    }
    
    public static boolean doScrollingOfConversations = true;
  
    
    
    
    
    
    
    
   
    
    
    
    
    public void createClientsLocally(int n, int portNumber){
        for(int i=0;i<n;i++){
            ConnectionToServer cts = new ConnectionToServer("localhost",portNumber);
            cts.start();
        }
    }
    
    public  void println(String windowName,String s){
        if(jmf!=null){
            jmf.displayTextOutputInBottomTextarea(windowName, s+"\n");
        } 
         else{
            System.out.println("Client "+windowName+": "+s);
        }
    }
    
    public  void print(String windowName,String s){
        if(jmf!=null){
            jmf.displayTextOutputInBottomTextarea(windowName, s);
        }
        else{
            System.out.print("Client "+windowName+": "+s);
        }
    }
     public JExperimentManagerMainFrame getJMF() {
        return jmf;
    }
    
}
