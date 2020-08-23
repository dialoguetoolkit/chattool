/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client.ClientInterfaceEvents;

import diet.client.ClientInterfaceEvents.ClientInterfaceEvent;
import diet.attribval.AttribVal;
import diet.client.ClientEventHandler;
import diet.message.MessageClientInterfaceEvent;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class ClientInterfaceEventTracker  {
    final public static int changeScreenBackgroundColour = 1;
    final public static int displaytextInStatusBarANDDisableTextentry = 2;
    final public static int changeBorderOfMazeFrame = 3;
    final public static int changeMazeWindow = 4;
    final public static int disableScrolling = 5;
    final public static int changeTextStyleOfTextFormulationWindow = 6; //
    final public static int disableTextPane = 7;
    final public static int changeBorderOfChatFrame = 8;
    final public static int displaytextInStatusBarANDEnableTextentry = 9;
    final public static int enableTextEntry = 10;
    final public static int clearAllWindowsExceptWindow0 = 11;
    final public static int displayTextInStatusBar = 12;
    final public static int clearMainTextWindows = 13;
    final public static int enableScrolling = 14;
    final public static int disableTextEntry = 15;
    final public static int enableTextPane = 16;
    final public static int clearTextEntryField = 17;
    final public static int enableDeletes = 20;
    final public static int disableDeletes = 21;
    
    final public static int changetoCBYC_SingleTrack =22;
    final public static int changetoCBYC_MultipleTrack =23;
    final public static int changetoTBT = 230;
   
    
    
    
    final public static int dodebugrobot = 24;
    final public static int setCharWhitelist =30; 
    final public static int setCharLengthLimit = 31;
    
    
    //These aren't quite the same as those above - the codes above are instructions. The number codes below are IDs
    public final static int normalturn = 50; //I don't think this is used - but it might be used as a number elsewhere - keep this code here so that 50, 51, 52 are reserved
    public final static int splitscreenAddText = 51; //I don't think this is used - but it might be used as a number elsewhere - keep this code here so that 50, 51, 52 are reserved
    public final static int splitscreenDeleteText = 52; //I don't think this is used - but it might be used as a number elsewhere - keep this code here so that 50, 51, 52 are reserved
    
    
    Vector documentEvents = new Vector();
    boolean trackEvents = true;
    ClientEventHandler ceh;
    
    
    public final static String textentryfield_insertstring = "tefi";
    public final static String textentryfield_removestring = "termve";
    public final static String textentryfield_replacestring = "tefr";
    public final static String chatwindow_appendbyotherparticipant = "chwappbyo";
    public final static String chatwindow_appendbyself_clearingtextentry = "chwappbyselfclearingte";
    public final static String changeborder = "changeborder";
    public final static String changestatuslabel = "changelabel";
    public final static String changestatuslabel_disabletextentry = "changelabel_disablete";
    public final static String changestatuslabel_enabletextentry = "changelabel_enablete";
    
    
    
    ///This is at another level of abstraction - depending on the experiment, different signals of "is typing" might be used.
    public final static String istypingByOtherSTART = "istypstart";
    public final static String istypingByOtherFINISH = "istypfinish";

    public final static String mazegameOpenGates = "mgog";
    public final static String mazegameCloseGates = "mgcg";
    
    final public static String wysiwyg_appendbyself ="wywiwyg_appendbyself";
    final public static String wysiwyg_appendbyother ="wywiwyg_appendbyother";
    
    
    
    public ClientInterfaceEventTracker(ClientEventHandler ceh) {
       this.ceh=ceh;
    
    }
    
    
    
     public void deprecated_addClientEvent(String type, long timeOfDisplay, String attribname ,   String attribval){
        //AttribVal av1 = new AttribVal(attribname,attribval);
        //ClientInterfaceEvent cie = new ClientInterfaceEvent(ceh.getCts().getEmail(),ceh.getCts().getUsername()  ,type, timeOfDisplay  ,av1);
        //needs to be moved to 
    }
    
    
      public void addClientEvent(String type, long timeofclientdisplay,  Vector attribvals){
        
          
        ClientInterfaceEvent cie = new ClientInterfaceEvent(ceh.getCts().getEmail(),ceh.getCts().getUsername(), type, timeofclientdisplay,  attribvals);
         MessageClientInterfaceEvent mcie = new MessageClientInterfaceEvent(ceh.getCts().getEmail(), ceh.getCts().getUsername(),(ClientInterfaceEvent)cie);
         ceh.getCts().sendMessage(mcie);
    }
     
     
    
    public  ClientInterfaceEvent addClientEvent(String type, long timeofclientdisplay,  AttribVal...attribvalues){
        ClientInterfaceEvent cie = new ClientInterfaceEvent(ceh.getCts().getEmail(),ceh.getCts().getUsername(), type, timeofclientdisplay,  attribvalues);
        MessageClientInterfaceEvent mcie = new MessageClientInterfaceEvent(ceh.getCts().getEmail(), ceh.getCts().getUsername(),(ClientInterfaceEvent)cie);
        ceh.getCts().sendMessage(mcie);
        
        return cie;
    }
    

    
}
/*




   











*/