/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.experimentmanager;

import diet.client.ClientEventHandler;
import diet.client.ConnectionToServer;
import diet.client.JChatFrameMultipleWindowWithSendButtonWidthByHeight.JChatFrameMultipleWindowsWithSendButtonWidthByHeight;
import diet.client.JChatFrameMultipleWindowWithSendButtonWidthByHeight.StyledDocumentStyleSettings;
import diet.message.Message;
import diet.message.MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gj
 */
public class JReplayTableModel extends AbstractTableModel{

    
    Vector allMessages = new Vector();
    Vector userEmails = new Vector(); 
    Vector userEmailUsernames = new Vector();
    Hashtable ht = new Hashtable();
    
   
    
    
    
    
    public JReplayTableModel(){
          this.loadMessagesFromFile("c:\\t");
          this.findAllTheSenders();
          this.loadAllTheUserInterfaces();
    }
    
    
    public JReplayTableModel(String filename){
        
    }
    public JReplayTableModel(int someothertest){
        
    }
    
    
    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return String.class;
    }

    @Override
    public String getColumnName(int i) {
        return (String)this.userEmailUsernames.elementAt(i);
        
    }

    @Override
    public int getRowCount() {
        return this.allMessages.size();
    }

    @Override
    public int getColumnCount() {
        return userEmails.size();
    }
    
    public Object getValueAt(int x,int y){
         Object o = allMessages.elementAt(x);
         if(o instanceof diet.message.MessageChatTextToClient){
             return "CHATTEXT\nCHATTEXT";
         }
         return "";
    }
    
    
    
    
    int deugcount=0;
    int debugthreshold = 5;
    
    
    public void loadMessagesFromFile(String directory){
         
          try{
               FileInputStream fi;
               String fileToLoad =directory+File.separator+"messages.dat";
               fi = new FileInputStream(new File(fileToLoad));
               ObjectInputStream oInp = new ObjectInputStream(fi);
               allMessages = this.loadFileBlocking(oInp, 500) ;
               //System.out.println("SIZE IS"+vMazeGameMessages.size());
               oInp.close();
               fi.close();
         }catch(Exception e){
             System.out.println("can't load in mazegamemessages "+e.getMessage());
             e.printStackTrace();
 
         }
        
    }
    
    
    
    
    
    final public Vector loadFileBlocking(final ObjectInputStream oInp,  long timeoutForLastObject){
        final Vector fileLoaded = new Vector();
      
                 boolean loadRun = true;
                 int i =0;
                 while(loadRun){
                      try{
                         if(i>60)loadRun=false;
                         setTimeOfLastObjectLoaded();
                         Object o = oInp.readObject();
                         i++;
                         fileLoaded.add(o);
                         if(i%10000==0)System.err.println("LOADING ");
                      }catch (Exception e){
                          e.printStackTrace();
                          System.out.println("LOADING HAS TIMED OUT VIA AN ERROR AT"+i);
                                  
                          loadRun=false;
                          
                      }  
                 } 
        return fileLoaded;
    
        
}
    long timeOfLastLoaded = new Date().getTime();
    final private synchronized void setTimeOfLastObjectLoaded(){
         timeOfLastLoaded = new Date().getTime();
     }
     final private synchronized long getTimeSinceLastObjectLoaded(){
         return new Date().getTime()-timeOfLastLoaded;
     }
     
    
  
    
     
    public void findAllTheSenders(){
        for(int i=0;i<this.allMessages.size();i++){
            Message m = (Message)allMessages.elementAt(i);
            if(m instanceof diet.message.MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight){
                 userInterfaceSettings.addElement(m);
            }
            //System.out.println(m.getClass().toString());
            
            String email = m.getEmail();
            String username = m.getUsername();
            boolean found=false;
            for(int j=0;j<userEmails.size();j++){
                String s = (String)userEmails.elementAt(j);
                if(s.equalsIgnoreCase(email)){
                    found=true;
                    break;
                }
            }
            if(!found){
                if(!email.equalsIgnoreCase("server")){
                   userEmails.addElement(email);
                   userEmailUsernames.addElement(email+" / "+username);
                   ht.put(email, username);
                }
            }  
        }
    } 
     
     Vector userInterfaceSettings = new Vector();
     
     Hashtable useruserInterfaces = new Hashtable();
     
     public void loadAllTheUserInterfaces(){
          
          
          
     }
     
    public void showUI(int column, boolean show){
         String email = (String)this.userEmails.elementAt(column);
         JFrame jf = (JFrame)this.useruserInterfaces.get(email);
         jf.setVisible(show);
    }
     
     
     
     
}    