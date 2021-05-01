/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.tg;

import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

/**
 *
 * @author LX1C
 */
public class IDCheckRecords {

    Conversation c;
    IDCheck idc;
    File directory ;
   
    
    Vector<TelegramParticipantConnection> allTelegramIDRecords = new Vector<TelegramParticipantConnection>();
    
    
    
    
    public IDCheckRecords(Conversation c,IDCheck idc) {
        this.c=c;
        this.idc=idc;
        this.directory= new File(System.getProperty("user.dir")+File.separator+ "tg");
        this.loadFromDirectory();
    }
    
    public TelegramParticipantConnection getLogFile(long userid){
       for(int i=0;i<this.allTelegramIDRecords.size();i++){
           System.err.println("LOOKING AT: "+allTelegramIDRecords.elementAt(i).telegramID+ " trying to find: "+userid);
           if(allTelegramIDRecords.elementAt(i).telegramID == userid) return allTelegramIDRecords.elementAt(i);     
       }
       return null;
    }
    
    
    public TelegramParticipantConnection createNewRecord(long userid){
         System.err.println("HERE11");
        File f = new File(directory,""+userid+".log" );
        TelegramIDLOGFILE tidlf = new TelegramIDLOGFILE(f);
        Conversation.printWSln("Main", "Creating new record "+userid);
        TelegramParticipantConnection tidr = new TelegramParticipantConnection(idc.cttgbot,   userid,tidlf);
        tidr.telegramidlogfile=tidlf;
        tidr.telegramID=userid;
        allTelegramIDRecords.add(tidr);
        return tidr;
        
    }
    
    
    
    public void addValidLoginCode(long userid, String validlogincode){
       TelegramParticipantConnection logFile = this.getLogFile(userid);
       System.err.println("HERE1A");
       if(logFile==null){
            System.err.println("HERE2A");
           logFile= createNewRecord(userid);
       }
       this.allTelegramIDRecords.add(logFile);
       logFile.setValidLoginCode(validlogincode);
       logFile.telegramidlogfile.appendText("logincode:" +validlogincode+"\n");
       Conversation.printWSln("Main", "Updated log file of TelegramID:"+userid + " with logincode: "+validlogincode);
        
        
       
    }
    
    public boolean allowLoginsAnswerGiven = false;
    public boolean allowLoginsByNewParticipantsGeneric = false;
    public boolean allowLoginsByNewParticipantsSpecific = false;
    public boolean allowLoginsByOldParticipants = false;
    public String genericLoginCode = "login";
    
    
    
    //public boolean loginAnswerGiven = false;
    //public boolean loginAnswerLoginOLD = false;
    //public boolean loginAnswerLoginNEW=false;
    
    public  void loadFromDirectory(){
       Vector<retval> potentialTelegramIDRecordsToAdd = new Vector<retval>();

       File[] allFiles = this.directory.listFiles();
        
      // Vector allLogs = new Vector();
       for (int i=0; i < allFiles.length; ++i) {
          if (allFiles[i].getName().endsWith(".log")) {
                //TelegramParticipantConnection tidr= loadRecord(allFiles[i]);
                
                retval rv = loadRecord(allFiles[i]);
                
                if(rv.logincode!=null){
                    System.err.println("Found "+allFiles[i].getName());
                    potentialTelegramIDRecordsToAdd.add(rv);    
                }
                
                
                
                //this.allTelegramIDRecords.add(tidr);     
          } else {
        
          }
        } 
       
       System.err.println("HEREAAA");
       
       //JInitializeLoginQuestion jilq = new JInitializeLoginQuestion(this);
       
      
       
       String[][] rows = new String [potentialTelegramIDRecordsToAdd.size()][4];
       for(int i=0;i<potentialTelegramIDRecordsToAdd.size();i++){
           retval rv = potentialTelegramIDRecordsToAdd.elementAt(i);
           
           String[] row = new String[]{rv.telegramid+"",rv.logincode,rv.participantID,rv.participantusername};
           rows[i]=row;
       }
       
        JDIALOGRELOGIN jilq = new JDIALOGRELOGIN(null,this, rows,new String[]{"TelegramID","Logincode" ,"ParticipantID","Username"} , true);
  
        
        
       //jilq.changeTableModel(rows, new String[]{"TelegramID","Logincode" ,"ParticipantID","Username"});
       
       /*
       System.err.println("HEREBBB");
       while(!this.allowLoginsAnswerGiven){
            try{
                wait();
            }catch(Exception e){
                e.printStackTrace();
            }
       }
       System.err.println("HERECCC");
       */
      
       //idc.allowNEWLOGINS=allowLoginsByNewParticipants;
    
       if(this.allowLoginsByOldParticipants){
           for(int i=0;i<potentialTelegramIDRecordsToAdd.size();i++){
               retval rv = potentialTelegramIDRecordsToAdd.elementAt(i);
               this.allTelegramIDRecords.add(rv.tidr);
               Conversation.printWSln("Main", "Reactivating "+rv.telegramid);
               this.rejoinConversation(rv);
           }
       }
       else{
           for(int i=0;i<potentialTelegramIDRecordsToAdd.size();i++){
               retval rv = potentialTelegramIDRecordsToAdd.elementAt(i);
               idc.addTelegramToBlockList(rv.telegramid);
               Conversation.printWSln("Main", "Adding "+rv.telegramid+" to blocked list");
           }
           
           
       }

    }
    
    public void registerResponseFromGUI(boolean allowOLD, boolean allowNEWG, boolean allowNEWS, String genericLoginCode){
        this.allowLoginsByOldParticipants=allowOLD;
        this.allowLoginsByNewParticipantsGeneric=allowNEWG;
         this.allowLoginsByNewParticipantsSpecific=allowNEWS;
        System.err.println("Logins by new participant....setting to:"+allowNEWG);
        this.allowLoginsAnswerGiven=true;
        this.genericLoginCode=genericLoginCode;
        //notifyAll();
        
    }    
    
    
public retval loadRecord(File f){
        
     Conversation.printWSln("Main", "Loading record from logfile:"+f.getName() );
     
     String idAsString = f.getName().replace(".log", "" );
     Long telegramIDAsLong = (long)-1;
     
     
     try{
       telegramIDAsLong = Long.parseLong(idAsString);
     } catch (Exception e){
         e.printStackTrace();
         CustomDialog.showDialog("There was an error processing "+idAsString+"\n"
                 + "All telegram log files should be numbers with a .log extension\n"
                 + "Unless you really know what you are doing - you should exit the program and fix the filename!");
         return null;
     }
     
     Vector<String> log = new Vector();
     
        
     try {
        
       
        BufferedReader in = new BufferedReader(
           new InputStreamReader(new FileInputStream(f), "UTF-8"));

        String str;

        while ((str = in.readLine()) != null) {
            System.out.println("Loading log of "+f.getName()+": "+str);
            log.addElement(str);
        }
                in.close();
        } 
        catch (UnsupportedEncodingException e) 
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
     catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
     }
     
     String logincode = "";
     for(int i=0;i<log.size();i++){
          String s = (String)log.elementAt(i);
          if(s.contains("logincode:")){
              logincode = s.replace("logincode:","");
              logincode = logincode.trim();
              break;
          }
     }
     
     String participantid = "";
     for(int i=0;i<log.size();i++){
          String s = (String)log.elementAt(i);
          if(s.contains("participantid:")){
              participantid = s.replace("participantid:","");
              System.err.println("HERE1000");
              participantid = participantid .trim();
              break;
          }
     }
     if(participantid.equalsIgnoreCase("")){
         //CustomDialog.showDialog("The logfile "+f.getName()+ " does n");
     }
     
     
     
     String participantusername = "";
     for(int i=0;i<log.size();i++){
          String s = (String)log.elementAt(i);
          if(s.contains("participantusername:")){
              participantusername = s.replace("participantusername:","");
              System.err.println("HERE1001");
              participantusername = participantusername.trim();
              break;
          }
     }
    
     
     
                             
     
     
     
     
     
     
     
     //tidr.telegramID=idAsLong;
     //tidr.allLogsoFar=log;
     //tidr.logincode = logincode;
     //tidr.telegramidlogfile= new TelegramIDLOGFILE(f);
     
         
      TelegramParticipantConnection tidr = new TelegramParticipantConnection(idc.cttgbot,telegramIDAsLong,new TelegramIDLOGFILE(f));
      
      
      tidr.setLogsSoFar(log);
      if(logincode!=null&&!logincode.equalsIgnoreCase("")){
            tidr.setValidLoginCode(logincode);  
            System.err.println("HERE1002");
      }
      
      
       
      if(logincode.equalsIgnoreCase("")){
          Conversation.printWSln("Main", "Loaded record from logfile:"+telegramIDAsLong+". Record created. Waiting for user to provide logincode");
          retval rv = new retval();
          rv.tidr=tidr;
          
          return rv;
      }
      if(participantid.equalsIgnoreCase("")){
          Conversation.printWSln("Main", "Checking record from logfile:"+idAsString+". Found login code: "+logincode);
          Conversation.printWSln("Main", "Checking record from logfile:"+idAsString+". Can't find participant id");
          CustomDialog.showDialog("Checking record from logfile:"+idAsString+". Can't find participant id\n"
                                 + "This could mean that the logfile is corrupted because\n"
                                 + "it has a record of successful use of a logincode "+logincode+ "\n"
                                 + "but there is no record of a participant ID.\n"
                                 + "This could happen if the server restarted while a participant was logging in.\n\n"
                                 + "Unless you know what you are doing - it is best to \n"
                                 + "(1) Close down the server\n"
                                 + "(2) Delete the logfile "+idAsString+"\n"
                                 + "(3) Ask the user to login again");
          retval rv = new retval();
          rv.tidr=tidr;
          
          return rv;
      }
      if(participantusername.equalsIgnoreCase("")){
          Conversation.printWSln("Main", "Checking record from logfile:"+idAsString+". Found login code: "+logincode);
          Conversation.printWSln("Main", "Checking record from logfile:"+idAsString+". Found participant id: "+participantid);
          Conversation.printWSln("Main", "Checking record from logfile:"+idAsString+". Can't find username");
          CustomDialog.showDialog("Loading record from logfile:"+idAsString+"\n"
                                 + "Can find valid logincode: "+logincode + "\n"
                                 + "Can find participant id:"+ participantid+"\n"
                                 + "But can't find username."
                                 + "This could mean that the logfile is corrupted because\n"
                                 + "it has a record of successful use of a logincode "+logincode+ "\n"
                                 + "There is a record of a participant ID but no username\n"
                                 + "This could happen if the server restarted while a participant was logging in.\n\n"
                                 + "Unless you know what you are doing - it is best to \n"
                                 + "(1) Close down the server\n"
                                 + "(2) Delete the logfile "+idAsString+"\n"
                                 + "(3) Ask the user to login again");
          retval rv = new retval();
          rv.telegramid=telegramIDAsLong;
          rv.tidr=tidr;
          
          return rv; 
      }
      
      
      
     String utelegramid = telegramIDAsLong+"";  
     String ulogincode=logincode+"";         
     String uparticipantid=participantid+"";        
     String uusername=participantusername+"";
     
     boolean detailsmissing=false;
     if(ulogincode.equalsIgnoreCase("")){
         ulogincode="VALUE MISSING";
         detailsmissing=true;
     }
     if(uparticipantid.equalsIgnoreCase("")){
         uparticipantid="VALUE MISSING";
         detailsmissing=true;
     }
     if(uusername.equalsIgnoreCase("")){
         uusername="VALUE MISSING";
         detailsmissing=true;
     }
     
     if(detailsmissing){
           String initial = "The logfile "+f.getName()+" is incomplete. Some information is missing.\nThe record is:\n\n";
         
           String userinformation = "Telegram ID:"+utelegramid + "\n"+  
                              "Logincode:"+ulogincode+"\n"+
                              "ParticipantID:"+uparticipantid+"\n"+
                              "Username:"+uusername+"";
           
           String endinformation = "This could happen in a couple of ways";
     
     }
     

      retval rv = new retval();
      rv.telegramid=telegramIDAsLong;
      rv.tidr=tidr;
      rv.logincode=logincode+"";    
      rv.participantID=participantid;
      rv.participantusername=participantusername;
      
      Conversation.printWSln("Main", "Loaded record from logfile: Telegram ID:"+rv.telegramid+" Logincode:"+rv.logincode+" ParticipantID:"+rv.participantID+" Username:"+rv.participantusername);
     
      
      
      return rv;
   } 
    
    
     public void rejoinConversation(retval rv){
         Participant p = c.getParticipants().findParticipantWithEmail(rv.participantID);
      if(p==null || !(p instanceof TelegramParticipant)){
          TelegramParticipant tp =new  TelegramParticipant(c, rv.participantID, rv.participantusername);
          tp.setConnection(rv.tidr);
          c.getParticipants().addNewParticipant(tp);
          c.getController().telegram_participantReJoinedConversation(tp);
          c.getCHistoryUIM().updateParticipantsListChanged(c.getParticipants().getAllParticipants());
          c.getMm().updateParticipants(c.getParticipants().getAllParticipants().size());
          System.err.println("HERE1003");
      }
      else{
          TelegramParticipant tp = (TelegramParticipant)p;
          tp.setConnection(rv.tidr);     
          c.getController().telegram_participantReJoinedConversation(tp);
          c.getCHistoryUIM().updateParticipantsListChanged(c.getParticipants().getAllParticipants());
          c.getMm().updateParticipants(c.getParticipants().getAllParticipants().size());
          System.err.println("HERE1004");
      }
     System.err.println("HERE1005");
     }


     class retval{
          public TelegramParticipantConnection tidr;
          public long telegramid;
          public String logincode;
          public String participantID;
          public String participantusername;
     }
    
    
}


