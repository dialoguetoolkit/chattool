/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;


import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import diet.message.MessageTask;
import diet.server.Conversation;
import diet.server.Participant;
import diet.task.DefaultTaskController;
import diet.task.tangram2D1M.message.MessageEndOfCurTangramSet;
import diet.task.tangram2D1M.message.MessageEndOfGame;
import diet.task.tangram2D1M.message.MessageNewTangramsDirectorA;
import diet.task.tangram2D1M.message.MessageNewTangramsDirectorB;
import diet.task.tangram2D1M.message.MessageNewTangramsMatcher;
import diet.task.tangram2D1M.message.MessageNextTangramPlaced;
import diet.task.tangram2D1M.message.MessageTangramRevealed;
/**
 *
 * @author Arash
 */
public class TangramGameController extends DefaultTaskController{
    
    Conversation c;
    TangramLoader tangramLoader;
    SetupIOTangramGameMoveWriting sIOWriting;
    TangramGameServerUI tgsUI;
    Participant pDirectorA;
    Participant pDirectorB;
    Participant pMatcher;
    
    Vector<SerialTangram> curTangrams; 
    
    String tangramSequenceType;
    String curTangramSetName;
    TangramSequence curSequence;
    int curSlot=0;
    
    public TangramGameController(Conversation c, String experimentID){
        this.c=c;
        
        //sIO = new SetupIO("C:\\svndiet\\chattool\\mazegame",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        tangramLoader = new TangramLoader(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"tangram_sets");//,c.getDirectoryNameContainingAllSavedExperimentalData().toString()
        sIOWriting = new SetupIOTangramGameMoveWriting(c.getDirectoryNameContainingAllSavedExperimentalData().toString(), curTangramSetName);
        tgsUI = new TangramGameServerUI();
        if (experimentID.indexOf("SelfOther")>0)
        {
            this.tangramSequenceType="selfother";
        }
        else
        {
            this.tangramSequenceType="collectiveDyadic";
            System.out.println(experimentID+" doesn't match "+"selfother");
        }

        
        
        
    }
    public TangramSequence getCurSequence()
    {
        return curSequence;
    }
    
    public void initialize(){
        //This is called once max. number of participants has been reached.
    }
    
    public void participantJoinedConversation(Participant pJoined){
         //If the program gets to the end of this method 
        //The directors and matcher should have been established
        //Vector v = (Vector)c.getParticipants().getAllActiveParticipants().clone();
        Vector v = (Vector)c.getParticipants().getAllParticipants().clone();
        if(v.size()!=3) {
            System.err.println("THERE SHOULD BE THREE PARTICIPANTS...THERE ARE "+v.size());
            return;
        }
        for(int i=0;i<v.size();i++){
            Participant p = (Participant) v.elementAt(i);
            String pEmail = p.getParticipantID();
            System.err.println("THE EMAIL IS: "+pEmail);
            System.err.println("THE USERNAME IS: "+p.getUsername());
            if(pEmail.startsWith("match") || pEmail.startsWith("Match")||pEmail.equalsIgnoreCase("matcher")){
                pMatcher = p;
                v.remove(p);
            }       
        }
        if(pMatcher==null){
            System.err.println("ERROR.....COULD NOT FIND MATCHER");
            System.exit(-123456789);
        }
        
        pDirectorA = (Participant) v.elementAt(0);
        pDirectorB = (Participant) v.elementAt(1);
        if (!loadAndSendTangramsToClients()) 
        {
            System.out.println("no tangram sets");
            System.exit(1);
        }
        
    }
    
    public void setCurTangramSetName(String name)
    {
        this.curTangramSetName=name;
        this.sIOWriting.setTangramSetName(name);
    }
    
    private Vector<Tangram> getSameOrder(Vector<SerialTangram> v, Vector<Tangram> v2)
    {
        Vector<Tangram> result=new Vector<Tangram>();
        for(SerialTangram sTangram:v)
        {
            for(Tangram tangram:v2)
            {
                if (tangram.name.equals(sTangram.name))
                {
                    
                
                    result.add(tangram);
                    break;
                }
            }
        }
        return result;
    }
    
    
    private boolean loadAndSendTangramsToClients()
    {
        try{
            //loading

            if (this.tangramSequenceType.equalsIgnoreCase("selfother"))
            {
                this.curSequence=new SelfOtherTangramSequence();
                System.out.println("setting new selfother sequence");
            }

            else
            {
                this.curSequence=new CollectiveDyadicTangramSequence();////CHANGED
                System.out.print("setting new collectivedyadic sequence");
            }


            System.out.println("load and send tangrams called");
            Vector[] tangrams=tangramLoader.getNextTangramSet();
            System.out.println("tangram vector has "+tangrams.length);
            if (tangrams==null)
            {
                System.out.println("No tangrams");
                        return false;
            }
            Vector<SerialTangram> v=(Vector<SerialTangram>)tangrams[0];
           
            System.out.println("there are "+v.size()+"tangrams");
            Vector<SerialTangram> vRandomOrder=(Vector<SerialTangram>)v.clone();
            Vector<Tangram> serverTangramsDirector=(Vector<Tangram>)tangrams[1];
            Collections.shuffle(vRandomOrder);
            Vector<Tangram> serverTangramsMatcher=getSameOrder(vRandomOrder, serverTangramsDirector);
            
            this.curTangrams=v;
            this.curTangramSetName=tangramLoader.getCurrentSetName();
            this.sIOWriting.setTangramSetName(this.curTangramSetName);
            
            //sending
            
            tgsUI.initializeJTabbedPane(pMatcher.getUsername(), pDirectorA.getUsername()
                    , pDirectorB.getUsername(), curSequence, serverTangramsMatcher, serverTangramsDirector);
            MessageNewTangramsMatcher mntm=new MessageNewTangramsMatcher("server", "server", vRandomOrder, curTangramSetName, pMatcher.getUsername());
            MessageNewTangramsDirectorA mnta=new MessageNewTangramsDirectorA("server","server", curTangrams,curTangramSetName,curSequence, pDirectorA.getUsername());
            MessageNewTangramsDirectorB mntb=new MessageNewTangramsDirectorB("server","server", curTangrams,curTangramSetName,curSequence, pDirectorB.getUsername());
            System.out.println("sending new tangrams");
            c.sendTaskMoveToParticipant(pDirectorA, mnta);
            c.sendTaskMoveToParticipant(pDirectorB, mntb);
            c.sendTaskMoveToParticipant(pMatcher, mntm);
            this.curSlot=0;
            return true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return false;
        
    }
    
    synchronized public void processTaskMove(MessageTask mt, Participant origin){
        
        if (origin==this.pMatcher)
        {
            if (mt instanceof MessageNextTangramPlaced)
            {
                MessageNextTangramPlaced m=(MessageNextTangramPlaced)mt;
              
                int source=m.getSourceSlotID();
                int target=m.getTargetSlotID();
                int activeDropSlot=m.getActiveDropSlot();
                boolean directorMoveOn=m.directorMoveOn();
                if (activeDropSlot==curSlot&&directorMoveOn) 
                {
                    
                    curSlot=(activeDropSlot+1)%12;
                    System.out.println("curslot changed to:"+curSlot);
                }
                c.sendTaskMoveToParticipant(pDirectorA, mt);
                c.sendTaskMoveToParticipant(pDirectorB, mt);
                this.tgsUI.updateStatusTangramPlaced(source, target, directorMoveOn);
                this.sIOWriting.saveMessage((MessageNextTangramPlaced)mt);
                
            }
            else if (mt instanceof MessageEndOfCurTangramSet)
            {
                System.out.println("Recieved MessageEndOFCurTangramSet");
                this.sIOWriting.saveMessage((MessageEndOfCurTangramSet)mt);
                if (!loadAndSendTangramsToClients())
                {
                    MessageEndOfGame meg=new MessageEndOfGame("server", "server");
                    
                    this.sIOWriting.saveMessage(meg);
                    c.sendTaskMoveToParticipant(pDirectorA, meg);
                    c.sendTaskMoveToParticipant(pDirectorB, meg);
                    c.sendTaskMoveToParticipant(pMatcher, meg);
                }               
                
            }
        }
        
        else if(origin==pDirectorA){
            if (mt instanceof MessageTangramRevealed)
            {
                //curSlot=((MessageTangramRevealed)mt).getSlotID();
                MessageTangramRevealed m=(MessageTangramRevealed)mt;
                this.tgsUI.updateStatusTangramRevealed(m.getSlotID());
                this.sIOWriting.saveMessage((MessageTangramRevealed)mt);
                c.sendTaskMoveToParticipant(pMatcher, mt);
                c.sendTaskMoveToParticipant(pDirectorB, mt);
            }
            
        }
        else if(origin==pDirectorB)
        {
            if (mt instanceof MessageTangramRevealed)
            {
                //curSlot=((MessageTangramRevealed)mt).getSlotID();
                MessageTangramRevealed m=(MessageTangramRevealed)mt;
                this.tgsUI.updateStatusTangramRevealed(m.getSlotID());
                this.sIOWriting.saveMessage((MessageTangramRevealed)mt);
                c.sendTaskMoveToParticipant(pMatcher, mt);
                c.sendTaskMoveToParticipant(pDirectorA, mt);
            }

        }
        
     
    }
    public int getCurSlotID()
    {
        return curSlot;
    }
    
    /**
     * This method is called from Conversation.closeDown() Any threads instantiated in a custom
     * TaskController object should be stoppable using this method.
     */
    public void closeDown(){
         try{
          this.sIOWriting.closeDown();
        }catch(Exception e){
            e.printStackTrace();
        }  
        
    }
    

}
