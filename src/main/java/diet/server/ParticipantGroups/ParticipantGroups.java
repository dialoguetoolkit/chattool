


package diet.server.ParticipantGroups;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.Participant;
import diet.server.ParticipantGroups.ui.JPanelCurrentGroups;
import diet.server.ParticipantGroups.ui.JPanelCurrentGroupsAndConfiguration;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;





public class ParticipantGroups {
    
   
    Hashtable ht_Participant_Group = new Hashtable();
    Hashtable ht_String_Group = new Hashtable();
    
    public Conversation c;
    public DefaultConversationController cC;
    
    long subdialogueCOUNTING=0;
    
    public ParticipantGroups(DefaultConversationController cC) {
        this.cC=cC;
        this.c=cC.getC();
    }   
    
    
    public String getSubdialogueID(Participant p){
        try{
           Object o =  this.ht_Participant_Group.get(p);
           if(o==null)return null;
           ParticipantGroup sd = (ParticipantGroup)o;        
           return sd.getID();
        
        }catch (Exception e){
            e.printStackTrace();
        }
        return "NOTSET";
    }
    
    
    //Note that in this case, getSenders and getRecipients return the same...but it needn't always be the case
    public Vector<Participant> getSenders(Participant recipient){
        ParticipantGroup sd = (ParticipantGroup)this.ht_Participant_Group.get(recipient);
        if(sd==null)return new Vector();
        return sd.getAllOtherParticipants(recipient);
    }
    
    
    
    public Vector<Participant> getRecipients(Participant p){
         try{
           ParticipantGroup sd = (ParticipantGroup)this.ht_Participant_Group.get(p);
           return sd.getAllOtherParticipants(p);
        
        }catch (Exception e){
           // e.printStackTrace();
            System.err.println("THER ARE NO RECIPIENTS FOR "+p.getUsername());
        }
        return new Vector();
    }
    
    
    public Vector getRecipientsNames(Participant p){
        Vector recipients = this.getRecipients(p);
        Vector names = new Vector();
        
        for(int i=0;i<recipients.size();i++){
            Participant pRecip = (Participant)recipients.elementAt(i);
            names.addElement(pRecip.getParticipantID()+","+pRecip.getUsername());
        }
        return names;
    }
    
    public String getRecipientsAsString(Participant p){
        Vector recipients = this.getRecipients(p);
        String names ="";
        for(int i=0;i<recipients.size();i++){
            Participant pRecip = (Participant)recipients.elementAt(i);
            names = names + pRecip.getParticipantID()+","+pRecip.getUsername()+";";
        }
        return names;
    }
    
    
    
    
    public ParticipantGroup createNewSubdialogue(Vector vparticipants){
        Vector vparticipantsclone = (Vector)vparticipants.clone();
        //String id =  ""+subdialogueCOUNTING;
        //subdialogueCOUNTING++;
        return this.createNewSubdialogue("" , vparticipantsclone);
    }
    
   
    public ParticipantGroup createNewSubdialogue(Participant...ps){
        Vector v = new Vector(Arrays.asList(ps));
        //String id =  ""+subdialogueCOUNTING;
        //subdialogueCOUNTING++;
        return this.createNewSubdialogue("", v);
    }
    
    public ParticipantGroup createNewSubdialogue(String subdialogueID, Participant...ps){
        Vector v = new Vector(Arrays.asList(ps));
        //String id =  ""+subdialogueCOUNTING;
        //subdialogueCOUNTING++;
        return this.createNewSubdialogue(subdialogueID,v);
    }
    
    
    
    public ParticipantGroup createNewSubdialogue(String id,Vector<Participant> vvps){
         Vector vparticipantsclone = (Vector)vvps.clone();
        ///First need to remove them from all other subdialogues
        id =  id+"("+subdialogueCOUNTING+")";
        subdialogueCOUNTING++;
        
        
        
        for(int i=0;i<vparticipantsclone.size();i++){
            Participant p = (Participant)vparticipantsclone.elementAt(i);
            ParticipantGroup sd = (ParticipantGroup)this.ht_Participant_Group.get(p);
            if(sd!=null){
               sd.vps.remove(p);
               sd.id=sd.id+"_removed("+p.getParticipantID()+","+p.getUsername()+"";
            }
            
            
        }
        
        ParticipantGroup sd = new ParticipantGroup(this, id,vparticipantsclone);
        for(int i=0;i<vparticipantsclone.size();i++){
            Participant p = (Participant)vparticipantsclone.elementAt(i);
            this.ht_Participant_Group.put(p, sd);
        }
        this.ht_String_Group.put(id, sd);        
        
        Conversation.printWSln("Main", "---------------------------------------------------------");
        Conversation.printWSln("Main", "Created new subdialogue called: "+id +" with the following participants:");
        System.err.println( "Created new subdialogue called: "+id +" with the following participants:");
        Vector newParticipants = sd.getParticipants();
        for(int i=0;i<newParticipants.size();i++){
            Participant p = (Participant)newParticipants.elementAt(i);
            Conversation.printWSln("Main", p.getParticipantID()+" " +p.getUsername());
            
            System.err.println("Adding: " +p.getParticipantID()+" " +p.getUsername());
        }
        c.getCHistoryUIM().updateParticipantPartneringHasChanged();
        
        return sd;
    }
    
    
    
    
    
    public Vector getRecipientsSettings_DEPRECATEDFOROLDCOMPATIBILITY(Participant p){
        //Returns Vector with first element vector of Participants, 2nd element is vector of Participants' names'
        //Default seetting is that all other participants are enabled.
        Vector participants = c.getParticipants().getAllParticipants(); 
        Vector vRecipients = new Vector();
        Vector vRecipientsEmails = new Vector();
        Vector vRecipientsUsernames = new Vector();
        Vector vRecipientsWindowNumbers = new Vector();
        Vector v = new Vector();
        int pIndex = participants.indexOf(p);
        for(int i=0;i<participants.size();i++){
            //System.err.println("Getting permission for "+p.getUsername());
            Participant p2 = (Participant)participants.elementAt(i);
            if(p!=p2){//Doesn't need to be able to send to self'
                
                        vRecipients.addElement(p2);
                        vRecipientsEmails.addElement(p2.getParticipantID());
                        vRecipientsUsernames.addElement(p2.getUsername());
                        vRecipientsWindowNumbers.addElement(0);
    
            }
        }
        v.addElement(vRecipients);
        v.addElement(vRecipientsEmails);
        v.addElement(vRecipientsUsernames);
        v.addElement(vRecipientsWindowNumbers);                
        return v;
     }     
        
    
    
   
    
    
  public JPanelCurrentGroupsAndConfiguration getJPanel(){
     // return new JPanelCurrentGroups(this);
     return new JPanelCurrentGroupsAndConfiguration(this);
  }
    
    
  public Vector<Participant> getParticipants(String groupID){
      System.err.println("TRYING TO GET "+groupID);
      
     /* Object[] s = ht_Participant_Group.keySet().toArray();
      System.err.println("SET: THE KEYS ARE: ");
      for(int i=0;i<s.length;i++){
          String sk = (String)s[i];
          System.err.println("KEY: "+sk);
          ParticipantGroup pg = (ParticipantGroup)this.ht_Participant_Group.get(sk);
          System.err.println(pg.getParticipants().size());
          
      }
      System.err.println("SET: DONE");
      */
      ParticipantGroup pg = (ParticipantGroup) this.ht_String_Group.get(groupID);
      Vector retval = pg.getParticipants();
      if(retval==null) return new Vector();
      return retval;
     
  }
    

}
