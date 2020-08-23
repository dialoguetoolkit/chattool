/*
 * EMTester.java
 *
 * Created on 05 January 2008, 19:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.experimentmanager;


import javax.swing.UIManager;


import diet.client.ConnectionToServer;
import diet.gui.lookandfeel.JNimbusProgressBar;
import diet.gui.lookandfeel.JProgressBarFillPainter;



import diet.server.Conversation;
import diet.server.ConversationController.ClassLoader.ClassLoad;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.ExperimentManager;
import diet.server.demomode.DemoModeChecker;
import diet.server.experimentmanager.ui.JEMStarter111;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

/**
 *
 * @author user
 */
public class EMStarter {
    
    /**
     * Creates a new instance of EMTester
     */
    public EMStarter() {
       
        
    }
    
    
 /*    static public void beanshellTester(){
     try{  
       Interpreter i = new Interpreter();  // Construct an interpreter
       i.set("foo", 5);                    // Set variables
       i.set("date", new Date() ); 

        Date date = (Date)i.get("date");    // retrieve a variable

       // Eval a statement and get the result
       i.eval("bar = foo*10");      
       i.eval("classBrowser()");
       System.out.println( i.get("bar") );
       ClassBrowser cb= new ClassBrowser(i.getClassManager());
       JConsole jc = new JConsole();
       JFrame jf = new JFrame();
       jf.getContentPane().add(cb);
       jf.getContentPane().add(jc);
       jf.pack();
       jf.setVisible(true);
       
      }catch(Exception e){
          System.out.println(e.getMessage().toString());
          //System.exit(-2);
      }
      
      
      
  
      
        
    } */
    
    
    
    public static String[] args;

    public static void startNOGUI(String f){

    }

    //public static int portNumberFOREMSTARTERGUI = 20000;
    
    public static void startClient(String ipAddress, String portNumber){
        try{
              
               Integer i = Integer.parseInt(portNumber);
               ConnectionToServer cts = new ConnectionToServer(ipAddress,i);
               cts.start();
           }
           catch(Exception e){
               System.err.println("Could not start client");
               e.printStackTrace();
           }       
           return;
    }
    
    
    public static void startServer(int portNumber){
       
        
        
        DemoModeChecker dmc = new DemoModeChecker();
         try {
            
             //System.setProperty("apple.laf.useScreenMenuBar", "true");
             //System.setProperty("com.apple.mrj.application.apple.menu.about.name", "DiET Chattool");
             ////UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       
            
            ////System.setProperty("apple.laf.useScreenMenuBar", "true");
            /////System.setProperty("com.apple.mrj.application.apple.menu.about.name", "DiET Chattool");
            //// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //  UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());

             try {
                //// UIManager.setLookAndFeel(
                //// UIManager.getCrossPlatformLookAndFeelClassName());
             } catch (Exception e) { }


         } catch (Exception e) { 
             System.exit(-1);
         } 
       EMUI emn= new EMUI(portNumber);
    }          
    
    
    
    public static void mainb (String [] args2){
        JEMStarter111 jem = new JEMStarter111();
        jem.setVisible(true);
         
            
        
    }
    
    
    public static void checkVersion(){
        if(2<5)return;
        String version = System.getProperty("java.version");
        System.out.println("VERSION OF JAVA IS: "+version);
        if(version.startsWith("1.1")||version.startsWith("1.2")||version.startsWith("1.3")||version.startsWith("1.4")
                ||version.startsWith("1.5")||version.startsWith("1.6")){
            CustomDialog.showDialog("This program requires at least java version 1.7.\n"
                    + "Your computer says your java version is: "+version +"\n"
                    + "Please close this progam and install the latest version of java.\n"
                    + "You can proceed with running the chat tool, but unless you know\n"
                    + "what you're doing, you will almost certainly get runtime errors\n");
        }
        if(version.startsWith("1.8")){
            CustomDialog.showDialog("WARNING!\nThis program is being run with java 1.8\n"
                    
                    + "Currently Java 1.8 is unstable and leads to nondeterministic behaviour\n"
                    + "Please make sure you downgrade to java 1.7");
        }
    }
    static File chattooljar = null;
    
    public static void testJARLOADER(){
         String s = System.getProperty("user.dir");
         File fDIR = new File(s);
         System.out.println(fDIR.getName());
         File fCHATTOOL = new File(fDIR,"chattool.jar");
         if(fCHATTOOL.exists()){
             chattooljar = fCHATTOOL;
             return;
         }
         else{
             System.err.println("Can't find "+fCHATTOOL.getAbsoluteFile());
         }
         fCHATTOOL = new File(fDIR,"chattoolprogram.jar");
         if(fCHATTOOL.exists()){
             chattooljar = fCHATTOOL;
             return;
         }
         else{
             System.err.println("Can't find "+fCHATTOOL.getAbsoluteFile());
         }
         fCHATTOOL = new File(fDIR+File.separator+"dist"+File.separator+"chattool.jar");
         if(fCHATTOOL.exists()){
             chattooljar = fCHATTOOL;
             return;
         }
         else{
             System.err.println("Can't find "+fCHATTOOL.getAbsoluteFile());
             CustomDialog.showDialog("For some reason, the chattool can't identify which folder it is being run from.\n"
                     + "This might mean that you cannot load some of the default mazegame setups.\n"
                     + "If this problem persists, please email g.j.mills@rug.nl with a description of the problem.");
             
         }
         //try to find "chattool.jar" 
         //if it can't...try to find chattoolprogram.jar
         //if it can't try and look in subdirectory //dist
         //if it can't then please show error....please email saying what the directory is and it will be fixed.
         
         
         //System.exit(-5);
         
    }
    
    
    static boolean runningAsEXE = false;
    
     public static void checkName(String[] args2){
        if(args2==null) return;
        if(args2.length==0)return;
        if(args2[0]==null)return;
        if(args2[0].equalsIgnoreCase("RUNNINGASEXE")){
            System.out.println("DETECTED THAT IS RUNNING AS .EXE");
            runningAsEXE = true; 
            
        }
         
     }
    
     
     public static void setLookAndFeelOLD(){
          try { 
          // UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
           
          // UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
           NimbusLookAndFeel laf = new NimbusLookAndFeel();
          UIManager.setLookAndFeel(laf);
          UIDefaults defaults = laf.getDefaults();
          ColorUIResource backgroundUI = new ColorUIResource(0x494949);
          
          // backgroundUI = new ColorUIResource(0xFFFFFF);
          
          ColorUIResource textUI = new ColorUIResource(0xFFFAFA);
          ColorUIResource controlBackgroundUI = new ColorUIResource(0x5F5F4D);
          
          
          
          ColorUIResource infoBackgroundUI = new ColorUIResource(0x2f5cb4);
          ColorUIResource infoUI = new ColorUIResource(0x2f5cb4);
          ColorUIResource lightBackgroundUI = new ColorUIResource(0x5D5D5B);
          ColorUIResource focusUI = new ColorUIResource(0x39698a);

          UIManager.put("control", backgroundUI);
          UIManager.put("text", textUI);
          //UIManager.put("nimbusLightBackground", lightBackgroundUI);
          //UIManager.put("info", infoUI);
          //UIManager.put("nimbusInfoBlue", infoBackgroundUI);
          //UIManager.put("nimbusBase", controlBackgroundUI);
              //UIManager.put("nimbusBlueGrey", controlBackgroundUI);
              //UIManager.put("nimbusFocus", focusUI);
              UIManager.put("List.cellRenderer.background", textUI);
          
              
              
             
UIManager.setLookAndFeel(laf);

defaults.put("List[Selected].textForeground", laf.getDerivedColor("nimbusLightBackground", 0.0f, 0.0f, 0.0f, 0, false));
defaults.put("List[Selected].textBackground",  laf.getDerivedColor("nimbusSelectionBackground", 0.0f, 0.0f, 0.0f, 0, false));
defaults.put("List[Disabled+Selected].textBackground", laf.getDerivedColor("nimbusSelectionBackground", 0.0f, 0.0f, 0.0f, 0, false));
defaults.put("List[Disabled].textForeground",  laf.getDerivedColor("nimbusDisabledText", 0.0f, 0.0f, 0.0f, 0, false));
defaults.put("List:\"List.cellRenderer\"[Disabled].background", laf.getDerivedColor("nimbusSelectionBackground", 0.0f, 0.0f, 0.0f, 0, false));
              
              
              
        } 
        catch (Exception e) {
        }
       
          
         
          
          
     } 
     
     
     
     public static void debugJProgressBarOLD(){
       SwingUtilities.invokeLater(new Runnable(){
           public void run(){
               JFrame jf = new JFrame("JPROGRESSBAR");
               jf.setAlwaysOnTop(true);
         
              JProgressBar jpb = new JProgressBar();
              jpb.setString("this is a jporgress bar");
             UIDefaults defaults = new UIDefaults();
             defaults.put("ProgressBar.foreground", Color.black);
             defaults.put("ProgressBar[Enabled].foregroundPainter",new JProgressBarFillPainter(Color.GREEN));
             jpb.putClientProperty("Nimbus.Overrides", defaults);
             jpb.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
       
             
             
        
            SwingUtilities.updateComponentTreeUI(jpb);
            
            jpb.setForeground(Color.green);
            
            jpb.setUI(new BasicProgressBarUI() {
               protected Color getSelectionBackground() { return Color.black; }
               protected Color getSelectionForeground() { return Color.black; }
            });
            
            
                jpb.setValue(20);
         jpb.setStringPainted(true);
         jf.getContentPane().add(jpb);
         jf.pack();
         jf.setVisible(true);   
                
        
         
         
           }
       });
         
         
     }
     
     
     
      public static void debugJProgressBar(){
       SwingUtilities.invokeLater(new Runnable(){
           public void run(){
             JFrame jf = new JFrame("JPROGRESSBAR");
             jf.setAlwaysOnTop(true);
             
             JNimbusProgressBar jnpb = new JNimbusProgressBar();
             jnpb.changeJProgressBar(Color.red, "this is text", 60);
             
               // diet.gui.lookandfeel.NimbusLookAndFeelUtilities.changeJProgressBar(jnpb, Color.green, "THIS IS TEXT", 20);
                jf.getContentPane().add(jnpb);
                jf.pack();
                jf.setVisible(true);   
                
        
         
         
           }
       });
         
         
     }
     
     
     
     
      public static void setLookAndFeel(){
          
          try {
              for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                  if ("Nimbus".equals(info.getName())) {
                      UIManager.setLookAndFeel(info.getClassName());
                      break;
                  }
              }
              ColorUIResource colorResource = new ColorUIResource(Color.blue);
              UIManager.put("nimbusOrange",colorResource);
          } catch (Exception e) {
    
          }
   
         // debugJProgressBar();
   
          
     } 
     
     public static Vector  getClasses(){
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
                           
     
     
     
     
     
     
     
     public static void testClasses(){
         EMStarter em = new EMStarter();
         em.getClasses();
         
         
         
     }
     
     
     public static void main (String [] args2){
       //  testClasses();
       System.err.println("HERE");
  
      setLookAndFeel();
      System.out.println(UIManager.getLookAndFeel().getName());
      checkVersion();
      checkName(args2);
      if(runningAsEXE) args2 =  new String[]{""}; ///This is a hack 
        
      String userdir = System.getProperty("user.dir");
      System.out.println("USERDIR: "+userdir);
      
        
      try{
        if(userdir.indexOf("dist")>-1 ){
            File f = new File(userdir);
           
            File f2 = f.getParentFile();
            System.setProperty("user.dir", f2.getCanonicalPath());
            System.out.println("SETTING USER DIR TO: "+f2.getCanonicalPath());
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!runningAsEXE)ClassLoad.fixUSERDIR(); ////This is to fix a bug in Java - sometimes when starting a JARFILE, Java doesn't identify the correct user directory
        
         if (args2 == null || args2.length==0 || args2[0] ==null ||args2[0] =="" || args2[0] ==null ) {
           
             System.err.println("Checkpoint1a");
             try{
              System.err.println("Checkpoint1b");
             String sDirectory = System.getProperty("user.dir");
             System.err.println("Checkpoint1");
             File serverADDRESS = new File(sDirectory, "ip_address_of_server.txt") ;
             System.err.println("Checkpoint2");
             boolean doAutomaticStart = false;
             if(serverADDRESS.exists()){
                 String[] options  = {"AUTOCONNECT CLIENT TO SERVER", "MANUAL SETUP"};
                 String response = CustomDialog.show2OptionDialog(options, "There is a file called:\nip_address_of_server.txt that contains autoconnection settings", "Please choose:");
                 if(response.equals("AUTOCONNECT CLIENT TO SERVER"))doAutomaticStart=true;   
             }
             if(doAutomaticStart){
                    System.err.println("Checkpoint2b");
                 BufferedReader in = new BufferedReader(new FileReader(serverADDRESS));
                 String descline = in.readLine();
                 String serveraddrline = in.readLine().replace("SERVER IP ADDRESS:", "");
                 String serverportline = in.readLine().replace("SERVER PORT:", "");
                 String autoStartClient = in.readLine().replace("START CLIENT AUTOMATICALLY:", "");
                 
                 
                  System.err.println("Checkpoint2c");
                 if(autoStartClient.contains("YES")||autoStartClient.contains("yes")){
                    System.err.println("Checkpoint3a");
                    CustomDialog.showModeLessDialog("The client is connecting automatically to: "+serveraddrline+",  port number: "+serverportline+"\n", 4000);    
                    Integer ii= Integer.parseInt(serverportline);
                    ConnectionToServer cts = new ConnectionToServer(serveraddrline,ii);
                    cts.start(); 
                    
                 }
                 else{
                       System.err.println("Checkpoint3b");
                     Integer serverportlineINT = Integer.parseInt(serverportline);             
                     JEMStarter111 jem =new JEMStarter111(serveraddrline,serverportlineINT);              
                     jem.setVisible(true);    
                 }
                 
             }
             else{
                   System.err.println("Checkpoint4a");
                  JEMStarter111 jem = new JEMStarter111();
                  jem.setVisible(true);
             }
             
           } catch (Exception e){
               e.printStackTrace();
               CustomDialog.showDialog("ERROR: "+e.getMessage());
               JEMStarter111 jem = new JEMStarter111();
               jem.setVisible(true);
           }
          
           //System.exit(-324324);
         }
         else{
             processArgs(args2);
         }
    }   
    
    public static void processArgs (String [] args2){
      args = args2;
      if(args.length>1&&(args[0].equalsIgnoreCase("nogui")||args[0].equalsIgnoreCase("nogui_autologin")) ||args[0].equalsIgnoreCase("nogui_ccname_autologin") ||args[0].equalsIgnoreCase("nogui_ccname")||args[0].equalsIgnoreCase("nogui_telegram") ||args[0].equalsIgnoreCase("nogui_ccname_noclients")                     ){
            DemoModeChecker dmc = new DemoModeChecker();
            if(args[0].equalsIgnoreCase("nogui_autologin")||args[0].equalsIgnoreCase("nogui_ccname_autologin")){
                 DefaultConversationController.sett.login_autologin = true;
             }
             String s = args[1];
             for(int i=2;i<args.length;i++){
                  s=s+" ";
                  s = s+args[i];
            }        
            String separatorChar = File.separator;
   
            ExperimentManager em = new ExperimentManager(null,20000);   
            
            System.err.println("About to start!");
            // System.err.println("The CCNAME IS:"+args[1]);
            int numberOfClients = 0;
            if(args[0].equalsIgnoreCase("nogui_ccname_noclients")){
                 Conversation c =em.createAndActivateNewExperiment(args[1]);
                 System.err.println("Starting without clients");
                 
                 //numberOfClients = c.getController().sett.login_numberOfParticipants;
            }
            
            
            
            if(args[0].equalsIgnoreCase("nogui_ccname")||args[0].equalsIgnoreCase("nogui_ccname_autologin")){
                 Conversation c =em.createAndActivateNewExperiment(args[1]);
                 System.err.println("Starting"+args[1]);
                 
                 numberOfClients = c.getController().sett.login_numberOfParticipants;
                 System.err.println("THE NUMBER OF CLIENTS IS: "+numberOfClients);
            }
            else{
                //no longer necessary
            }
             
            
          
            for (int i = 0; i < numberOfClients; i++) {
	         ConnectionToServer cts = new ConnectionToServer("localhost",20000);
		 cts.start();
                 System.err.println("A)STARTING CLIENT "+i);
            }

        
    }


    if(args.length>=2&&args[0].equalsIgnoreCase("client")){
           try{
               //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");;
               Integer i = Integer.parseInt(args[2]);
               ConnectionToServer cts = new ConnectionToServer(args[1],i);
               cts.start();
               System.err.println("B))STARTING CLIENT "+i);
           }
           catch(Exception e){
               System.err.println("Could not start client");
               e.printStackTrace();
           }       
           return;
    }
    
    DemoModeChecker dmc = new DemoModeChecker();
    
    if(args[0].startsWith("SERVER")){

        try {
            
             //System.setProperty("apple.laf.useScreenMenuBar", "true");
             //System.setProperty("com.apple.mrj.application.apple.menu.about.name", "DiET Chattool");
             ////UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       
            
            //System.setProperty("apple.laf.useScreenMenuBar", "true");
            //System.setProperty("com.apple.mrj.application.apple.menu.about.name", "DiET Chattool");
            //// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            ////  UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());

             try {
                 //UIManager.setLookAndFeel(
                 //UIManager.getCrossPlatformLookAndFeelClassName());
             } catch (Exception e) { }


         } catch (Exception e) { 
             System.exit(-1);
         } 
       EMUI emn= new EMUI(20000);
    }          
 }
    
    
    
        
        
    
    
    
}
