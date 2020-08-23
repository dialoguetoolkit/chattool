/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.attribval.AttribVal;
import diet.server.Configuration;
import diet.server.Conversation;
import diet.server.Participant;
import diet.task.TaskControllerInterface;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;

/**
 *
 * @author LX1C
 */
public abstract class TelegramController extends DefaultConversationController{
    
    int telegramIDcounter;
   
   // public TGUTILSPHOTOSAVER tgups= new TGUTILSPHOTOSAVER(this, c.tgb);
    
    public boolean relayPhotos = true;
    public boolean relayVoice = true;
    
    //public String logincode = "login";
    public Vector<String> permittedParticipantIDS = new Vector();
    
    
    public TelegramController(Conversation c) {
        super(c);
        this.loadPermittedParticipantIDS();
    }

    public TelegramController(Conversation c, long istypingtimeout) {
        super(c, 0);
        this.loadPermittedParticipantIDS();
    }
    
    

    public void setTc(TaskControllerInterface tc) {
        this.tc = tc;
    }

     @Override
   public boolean telegram_startTelegramBOT(){
        return true;
    } 

    @Override
    public void participantJoinedConversation(Participant p) {
       
    }
    

    public void telegram_participantJoinedConversation(TelegramParticipant p) {
        System.err.println("HERE1000A");
        participantJoinedConversationButNotAssignedToGroup.add(p);
        if(participantJoinedConversationButNotAssignedToGroup.size()== Configuration.defaultGroupSize){
             pp.createNewSubdialogue(participantJoinedConversationButNotAssignedToGroup);
             //itnt.addGroupWhoAreMutuallyInformedOfTyping(participantJoinedConversationButNotAssignedToGroup); no "is typing" information possible here           
             participantJoinedConversationButNotAssignedToGroup.removeAllElements();
       }
    }
    
    
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
        System.err.println("THE SIZE OF PARTICIPANTS IS: "+c.getParticipants().getAllParticipants().size());
        
        System.err.println("HERE1000B");
        participantJoinedConversationButNotAssignedToGroup.add(p);
        if(participantJoinedConversationButNotAssignedToGroup.size()== Configuration.defaultGroupSize){
             System.err.println("HERE1000B2");
             pp.createNewSubdialogue(participantJoinedConversationButNotAssignedToGroup);
             //itnt.addGroupWhoAreMutuallyInformedOfTyping(participantJoinedConversationButNotAssignedToGroup); no "is typing" information possible here           
             String name1 = ((TelegramParticipant)participantJoinedConversationButNotAssignedToGroup.elementAt(0)).getParticipantID()+"."+((TelegramParticipant)participantJoinedConversationButNotAssignedToGroup.elementAt(0)).getUsername();
             String name2 = ((TelegramParticipant)participantJoinedConversationButNotAssignedToGroup.elementAt(1)).getParticipantID()+"."+((TelegramParticipant)participantJoinedConversationButNotAssignedToGroup.elementAt(1)).getUsername();
             System.err.println("HERE1000B2A: "+name1);
             System.err.println("HERE1000B2B: "+name2);
             participantJoinedConversationButNotAssignedToGroup.removeAllElements();
            
       } 
    }
    
    
    
    public  final String[] telegram_getUniqueParticipantIDAndUniqueUsername(long telegramID ,String logincodeattempt){
         Conversation.printWSln("Main", "TelegramController receiving login request from "+ telegramID + " with "+ logincodeattempt  );
         if(logincodeattempt.equalsIgnoreCase(this.c.tgb.idc.idcIO.genericLoginCode)){  // This reference needs to be fixed / simplified
               
               String[] unique = c.getParticipants().pang.generateNextIDUsername_Format_PREFIXPLUSNUMBER("id","p"); 
               Conversation.printWSln("Main", "TelegramController returning unique ID and Username for "+ telegramID +": ParticipantID:"+unique[0]+".  Username:"+unique[1] );
               
               return unique;
               
               //return c.getParticipants().pang.generateNextIDUsername_Format_PREFIXPLUSNUMBER("id","p");     
         }
         else if(this.c.tgb.idc.idcIO.allowLoginsByNewParticipantsSpecific){  // This reference needs to be fixed / simplified
             System.err.println("Permitted participant IDs...looking");
             for(int i=0;i<this.permittedParticipantIDS.size();i++){
                 System.err.println("Permitted participant IDs..."+this.permittedParticipantIDS.elementAt(i));
                 if (this.permittedParticipantIDS.elementAt(i).equalsIgnoreCase(logincodeattempt)){
                     System.err.println("Permitted participant IDs...found");
                     String[] unique = c.getParticipants().pang.generateNextIDUsername_Format_PARTICIPANTIDPLUSNUMBER(logincodeattempt, "p");
                     return unique;
                 }   
             }
             System.err.println("Permitted participant IDs...not found");
             Conversation.printWSln("Main", "TelegramController preventing access of "+ telegramID + " to ConversationController"  );  
             return new String[]{"",""};

         }
         
         else{
             
             Conversation.printWSln("Main", "TelegramController preventing access of "+ telegramID + " to ConversationController"  );
               
             return new String[]{"",""};
         }
    }
    
    
    
    
    
    @Override
    public void telegram_processTelegramMessageFromClient(TelegramParticipant sender,TelegramMessageFromClient tmfc) {
        System.err.println("HEREINCOMING100");
        
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);     
        }
        if(this.relayPhotos && tmfc.u.hasMessage()&&  tmfc.u.getMessage().hasPhoto()){
             c.telegram_relayMessagePhotoToOtherParticipants_By_File_ID(sender, tmfc);
             
        }
        if(this.relayVoice && tmfc.u.hasMessage()&& tmfc.u.getMessage().hasVoice() ){
            c.telegram_relayMessageVoiceToOtherParticipants_By_File_ID(sender, tmfc);
        }
        
        
        //c.sendPhoto_By_File_DeleteAfter(sender, new File("C:\\chattoolTG\\chattoolTG\\experimentresources\\stimuli\\rorschachset01\\r01.png"), 3000);
        
        
        
        
        
        //c.telegram_DeleteMessage(sender, m.getMessageId());
    }
    
    
    
    public static boolean showcCONGUI(){
        return false;
    }

    @Override
    public Vector<AttribVal> getAdditionalInformationForParticipant(Participant p) {
        return new Vector(); //Do not call superclass - it has "is typing" timeout
    }
  
   
    
    private void loadPermittedParticipantIDS(){
        try{
            String path = System.getProperty("user.dir");
            path = path + File.separator + "experimentresources" + File.separator + "permittedparticipantids" + File.separator + "permittedparticipantids.txt";
            File f = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(f)); 
  
             String st; 
             System.out.println("Loading permitted participant IDs"); 
             while ((st = br.readLine()) != null) {
                 System.out.println("Loading permitted participant ID: "+st); 
                 this.permittedParticipantIDS.add(st);
             } 
            
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}    
