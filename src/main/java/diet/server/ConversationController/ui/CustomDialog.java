
package diet.server.ConversationController.ui;




import java.io.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.*;

/* 1.4 example used by DialogDemo.java. */
public class CustomDialog {
    
    public static int getInteger(String prompt, int defaultValue){
        boolean isInteger = false;
        while(!isInteger){
               String s = (String)JOptionPane.showInputDialog(
                    null,
                    prompt,
                    "Type in a number",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    ""+defaultValue);
                try{
                    int returnVal = Integer.parseInt(s);
                    return returnVal;
                }catch (Exception e){
                    prompt = prompt+"\nPlease make sure it's an integer";
                }
               
        }
        return -1;
        
    }
    
    public static double getDouble(String prompt, double defaultValue){
        boolean isDouble = false;
        while(!isDouble){
               String s = (String)JOptionPane.showInputDialog(
                    null,
                    prompt,
                    "Type in a number",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    ""+defaultValue);
                try{
                    double returnVal = Double.parseDouble(s);
                    return returnVal;
                }catch (Exception e){
                    prompt = prompt+"\nPlease make sure it's an integer";
                }
               
        }
        return -1;
        
        
      
        
        
    }
    
    
    
    public static double getDouble(String prompt, double min, double maxExclusive,    double defaultValue){
        boolean isPermitted = false;
        while(!isPermitted){
               String s = (String)JOptionPane.showInputDialog(
                    null,
                    prompt,
                    "Enter a number between "+min + " (incl)  and "+maxExclusive+" (excl)",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    ""+defaultValue);
                try{
                    double returnVal = Double.parseDouble(s);
                    if(returnVal<min  || returnVal >= maxExclusive){
                        
                    }
                    else{
                       return returnVal;    
                    }
                    
                    
                }catch (Exception e){
                    prompt = prompt+"\nPlease make sure it's between "+min + " (inclusive)  and "+maxExclusive+" (exclusive)";
                }
               
        }
        return -1;
        
        
      
        
        
    }
    
    
    
    
    public static boolean getBoolean(String prompt){
        String[] options = {"true","false"};
        String answer = CustomDialog.show2OptionDialog(options, prompt, "Two choices");
        if(answer.equalsIgnoreCase(options[0])) {
            System.err.println("SELECTED TRUE");
           
            return true;
        }
         System.err.println("SELECTED FALSE");
        return false;
    }
    public static boolean getBoolean(String prompt,String TRUEBUTTON, String FALSEBUTTON){
        String[] options = {TRUEBUTTON,FALSEBUTTON};
        String answer = CustomDialog.show2OptionDialog(options, prompt, "Two choices");
        if(answer.equalsIgnoreCase(options[0])) {
            System.err.println("SELECTED TRUE");
           
            return true;
        }
         System.err.println("SELECTED FALSE");
        return false;
    }
    
    
    public static boolean getBoolean(String prompt,String TRUEBUTTON, String FALSEBUTTON, String textInTitleBar){
        String[] options = {TRUEBUTTON,FALSEBUTTON};
        String answer = CustomDialog.show2OptionDialog(options, prompt, textInTitleBar);
        if(answer.equalsIgnoreCase(options[0])) {
            System.err.println("SELECTED TRUE");
           
            return true;
        }
         System.err.println("SELECTED FALSE");
        return false;
    }
    
    
    public static long getLong(String prompt, long defaultValue){
        boolean isInteger = false;
        while(!isInteger){
               String s = (String)JOptionPane.showInputDialog(
                    null,
                    prompt,
                    "Type in a number",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    ""+defaultValue);
                try{
                    long returnVal = Long.parseLong(s);
                    return returnVal;
                }catch (Exception e){
                    prompt = prompt+"\nPlease make sure it's an integer";
                }
               
        }
        return -1;
     }
        
        
        public static String getString(String prompt, String defaultValue){
        boolean isInteger = false;
        while(!isInteger){
               String s = (String)JOptionPane.showInputDialog(
                    null,
                    prompt,
                    "",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    ""+defaultValue);
                try{
                    return s;
                   
                }catch (Exception e){
                    prompt = prompt+"\nPlease make sure it's an integer";
                }
               
        }
        return "";
        
        
      
        
        
    }
    
        
        
    public static String showComboBoxDialog(String title, String prompt, Object[] possibilities, boolean forceNonNullChoice){
      
        String s = (String)JOptionPane.showInputDialog(
                    null,
                    prompt,
                    title,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "01");
        while(forceNonNullChoice&&(s==null||s.equals(""))){
             CustomDialog.showDialog("ERROR\nYou must make a choice. You can't press cancel!");
             s = (String)JOptionPane.showInputDialog(
                    null,
                    "Choose a randomized maze set:",
                    "Please choose a maze set",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "01");
        }
        
        
        return s;
       
         
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static String showComboBoxDialogOTHER(String title, String prompt, Object[] possibilities, boolean forceNonNullChoice){
      
        String s = (String)JOptionPane.showInputDialog(
                    null,
                    prompt,
                    title,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "01");
        while(forceNonNullChoice&&(s==null||s.equals(""))){
             CustomDialog.showDialog("ERROR\nYou must make a choice. You can't press cancel!");
             s = (String)JOptionPane.showInputDialog(
                    null,
                    "Choose a randomized maze set:",
                    "Please choose a maze set",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "01");
        }
        
        
        return s;
       
         
        
        
    }
    
        
    
    public static void showDialog(String message){
        JOptionPane.showMessageDialog(null, message);
    }
    
    public static String show3OptionDialog(String[] threeOptionsOnButtons, String questionprompt, String titleOfWindow){
          
          int n = JOptionPane.showOptionDialog(null, questionprompt,titleOfWindow,
                  JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                  null,
                  threeOptionsOnButtons,
                  threeOptionsOnButtons[0]);
          return threeOptionsOnButtons[n];
    }
    
    
    public static String show2OptionDialog(String[] twoOptionsOnButtons, String questionprompt, String titleOfWindow){
          
          int n = JOptionPane.showOptionDialog(null, questionprompt,titleOfWindow,
                  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                  null,
                  twoOptionsOnButtons,
                  twoOptionsOnButtons[0]);
          return twoOptionsOnButtons[n];
    }
    
    
    public static File loadFile(String directory){
      return loadFile(directory,"",null);
    }
    
    public static String loadTextFileWithExtension(String directory, String titleText, String extension, String extensionexplanation){
         File f = loadFileWithExtension(directory, titleText, extension, extensionexplanation);
         
         try{
             String textFile = "";
             
              BufferedReader br = new BufferedReader(new FileReader(f));
              String line = br.readLine();
              while (line != null) {
                   textFile= textFile+line+ "\n";
                   line = br.readLine();
              }
              
              br.close();
              System.err.println("LOADED: "+ textFile);
              return textFile;
             
             
         }
         catch(Exception e){
             e.printStackTrace();
         }
         return null;
    }
    
    public static File loadFileWithExtension(String directory, String titleText, String extension, String extensionexplanation){
        String s = System.getProperty("user.dir");
         if(directory!=null){
             s=directory;
         }
        File f = new File(s);
        JFileChooser fileChooser = new JFileChooser(f);
        fileChooser.setAcceptAllFileFilterUsed(false);
        javax.swing.filechooser.FileFilter ff = new javax.swing.filechooser.FileFilter(){
            public String getDescription() {
                 return extensionexplanation;
            }
             public boolean accept(File f) {
               if (f.isDirectory()) {
                  return true;
               }
              else {
                   return f.getName().toLowerCase().endsWith(extension);
                 }
             }    
        };
        
        JFileChooser fc = fileChooser;
        fileChooser.addChoosableFileFilter(ff);
        fc.setDialogTitle(titleText);
         
         int returnVal = fc.showDialog(null,"Load the file");
         File fname = fc.getSelectedFile();
         
         
         Vector allWords = new Vector();
         try{
              BufferedReader br = new BufferedReader(new FileReader(fname));
              String line = br.readLine();
              while (line != null) {
                   allWords.addElement(line);
                   line = br.readLine();
              }
              
              br.close();
        
         }catch(Exception e){
             e.printStackTrace();
             CustomDialog.showDialog("Could not load the file for some reason");
             //System.exit(-52);
             return null;
         }
         return  fname;
            
    }        
   
        
        
    
    public static File loadFile(String directory, String titleText, File selectedFile){
         
         String s = System.getProperty("user.dir");
         if(directory!=null){
             s=directory;
         }
         //System.err.println(s);System.exit(-5);
         File f = new File(s);
         final JFileChooser fc = new JFileChooser(f);
         fc.setDialogTitle(titleText);
         if(selectedFile!=null){
             fc.setSelectedFile(selectedFile);
         }
         
         int returnVal = fc.showDialog(null,"Load the file");
         File fname = fc.getSelectedFile();
         
         
         Vector allWords = new Vector();
         try{
              BufferedReader br = new BufferedReader(new FileReader(fname));
              String line = br.readLine();
              while (line != null) {
                   allWords.addElement(line);
                   line = br.readLine();
              }
              
              br.close();
        
         }catch(Exception e){
             e.printStackTrace();
             CustomDialog.showDialog("Could not load the file for some reason");
             System.exit(-52);
         }
         return  fname;
    }
    
    public static File loadFile(File dir){
         
         //System.err.println(s);System.exit(-5);
         final JFileChooser fc = new JFileChooser(dir);
         int returnVal = fc.showDialog(null,"Load the file");
         File fname = fc.getSelectedFile();
         Vector allWords = new Vector();
         try{
              BufferedReader br = new BufferedReader(new FileReader(fname));
              String line = br.readLine();
              while (line != null) {
                   allWords.addElement(line);
                   line = br.readLine();
              }
              
              br.close();
        
         }catch(Exception e){
             e.printStackTrace();
             CustomDialog.showDialog("Could not load the file for some reason");
             System.exit(-52);
         }
         return  fname;
    }
    
    
    
    
    
    public static void saveFile(String directory, String filename, String  contents){
        
       JFileChooser chooser = new JFileChooser();
       chooser.setCurrentDirectory(new File(directory));
       File newFile = new File(directory,filename);
       chooser.setSelectedFile(newFile);
       int retrieval = chooser.showSaveDialog(null);
        if (retrieval == JFileChooser.APPROVE_OPTION) {
        try {
            FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt");
            fw.write(contents);
            fw.close();
            
             Writer out = new BufferedWriter(new OutputStreamWriter(
               new FileOutputStream(chooser.getSelectedFile()+"_UTF8.txt"), "UTF-8"));
            out.write(contents);
            out.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    }
    
    
    public static void saveFileAsUTF8(String directory, String filename, String  contents){
        
       JFileChooser chooser = new JFileChooser();
       chooser.setCurrentDirectory(new File(directory));
       File newFile = new File(directory,filename);
       chooser.setSelectedFile(newFile);
       int retrieval = chooser.showSaveDialog(null);
        if (retrieval == JFileChooser.APPROVE_OPTION) {
        try {
            FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt");
            fw.write(contents);
            fw.close();
            
            Writer out = new BufferedWriter(new OutputStreamWriter(
               new FileOutputStream(chooser.getSelectedFile()+"_UTF8.txt"), "UTF-8"));
            out.write(contents);
            out.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    }
    
    
    
    public static File[] loadDirectories(String title, String directory){
              JFileChooser chooser;
              String choosertitle=title;
              
              chooser = new JFileChooser(); 
              chooser.setCurrentDirectory(new java.io.File(directory));
              chooser.setDialogTitle(choosertitle);           
              chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);        
              chooser.setMultiSelectionEnabled(true);
              
            
              int value = chooser.showOpenDialog(null);
              File[] fs = chooser.getSelectedFiles();
              for(int i=0;i<fs.length;i++){
                  File f = fs[i];
                  System.err.println(f.getName());
              }

            return fs;
        
    }
    
    public static void showModelessDialog(String text){
        JFrame tokenFrame = new JFrame("NOTIFICATION");
        tokenFrame.setSize(400,100);
        JLabel dialogLabel = new JLabel(text);
        tokenFrame.getContentPane().add(dialogLabel);
        tokenFrame.setVisible(true);              
        tokenFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

   //Create some component for the JDialog
       //JButton okButton = new JButton("OK");
       //JButton cancelButton = new JButton("Cancel");
       

   //Create the modeless dialog box
   //Using the JFrame tokenFrame as the parent component 
   //allows the dialog to be centered in front of the frame
      
    }
    public  static void showModeLessDialog(String text, final long milliseconds){
        final JFrame tokenFrame = new JFrame("NOTIFICATION");
        tokenFrame.setSize(800,100);
        JLabel dialogLabel = new JLabel(text);
        tokenFrame.getContentPane().add(dialogLabel);
        tokenFrame.setVisible(true);              
        tokenFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        Thread t = new Thread(){
        public void run(){
          try{
              Thread.sleep(milliseconds);
          }catch (Exception e){
              e.printStackTrace();
          }  
            
          SwingUtilities.invokeLater(new Thread(){
              public void run(){
                  tokenFrame.setVisible(false);
              }
        
          });
        }};
        t.start();
    }
    
    
}
    