/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server;

import com.esotericsoftware.yamlbeans.YamlWriter;
import java.awt.Color;
import java.io.FileWriter;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author gj
 */
public class Configuration implements Cloneable{
    public static String recordeddata_mazegameCloseGates = "\u24d2";
    public static String recordeddata_startOfIsTypingByOther = "\u2286";
    public static String recordeddata_mazegameOpenGates = "\u24de";
    public static String recordeddata_endOfIsTypingByOther = "\u2287";
    
    public static int defaultGroupSize = 2; //i.e dyads
    
    public Configuration(){
        
    }
    
   public String wysiwyglicense = "Please note that this interface is released under a more restrictive license - pse contact author prior to reimplementing / publishing" ;
        
    
    public String startup_start_as_CLIENT_or_SERVER_or_CHOICE = "CHOICE";
    public int server_port_number_to_listen_for_clients = 20000;
    public int client_port_number_to_connect_to = 20000;
    public String client_ip_address_of_server = "localhost";
   // public String 
    
    
     //Below are some debug parameters - they are not essential.
    public int login_minimumlength_of_participantID = 4; 
    public int login_numberOfParticipants = 2;
    public boolean login_autologin = false;
     
    public boolean debug_debugMESSAGEBLOCKAGE = false; //true;//false;//true;//true;//false;//true;
    public boolean debug_allow_client_to_send_debug_commands = true;
    
    //public static CyclicRandomParticipantIDGeneratorGROOP autologinParticipantIDGenerator = new CyclicRandomParticipantIDGeneratorGROOP(); 
    public boolean debug_debugTime= true;
    public long debug_logging_of_OverrunTime_threshold = 500;  //time in milliseconds
    public long debug_logging_of_OverrunPINGPONG = 1000;  
    
    
    /*
         Below are experiment parameters. It is (strongly!) advisable not to edit them here, but instead override in a subclass
         These parameters are intended primarily for initializing the experiment, not for changing the chat tool behaviour while the experiment is running
         There is no guarantee that changing these parameters during an experiment will have the desired effect! Please write custom code instead.
    
    */
    //public String param_experimentID = "NEEDSTOBESET";
   
   
    
    
    public int client_MainWindow_width = 600;
    public int client_MainWindow_height = 350;
    public int client_TextEntryWindow_width =300;
    public int client_TextEntryWindow_height = 150;
    public int client_TextEntryWindow_maximumtextlength = 500;
    public int client_TextEntryWindow_istypingtimeout = 500;
    public boolean client_TextEntryWindow_istypingstatus_use_senders_textstyle = true;
    
    public int client_numberOfWindows = 1;
    
    
    //These settings below are the default - however they might be overriden by custom implementations of the StlyeManager
    
    //public Color client_backgroundcolour = Color.white;
    public int[] client_backgroundcolour_rgb = {255,255,255};
    
    //How text from other participants appears by default:
    public String client_font_OTHER1_fontfamily = "Monospace";
    public int client_font_OTHER1_size = 22;
    public boolean client_font_OTHER1_isbold = false;
    public boolean client_font_OTHER1_isitalic = false;
    //public Color client_font_OTHER_foregroundcolour = Color.BLUE;
    public int[] client_font_OTHER1_foregroundcolour_rgb = {0,0,255};
    
    
     public String client_font_OTHER2_fontfamily = "Monospace";
    public int client_font_OTHER2_size = 22;
    public boolean client_font_OTHER2_isbold = false;
    public boolean client_font_OTHER2_isitalic = false;
    //public Color client_font_OTHER_foregroundcolour = Color.GREEN;
    public int[] client_font_OTHER2_foregroundcolour_rgb = {0,125,0};
    
    
    
    //How own text appears:
    public String client_font_SELF_fontfamily = "Monospace";
    public int client_font_SELF_size = 22;
    public boolean client_font_SELF_isbold = false;
    public boolean client_font_SELF_isitalic = false;
   // public Color client_font_SELF_foregroundcolour = Color.BLACK;
    public int[] client_font_SELF_foregroundcolour_rgb = {0,0,0};
    
    //How text from the server (i.e. instructions) appears:
    public String client_font_SERVER_fontfamily = "Monospace";
    public int client_font_SERVER_fontsize = 22;
    public boolean client_font_SERVER_isbold = false;
    public boolean client_font_SERVER_isitalic = false;
    //public Color client_font_SERVER_foregroundcolour = Color.RED;
    public int[] client_font_SERVER_foregroundcolour_rgb = {255,0,0};
    
    
    
    //Settings for transcript
    
    
    
    
    //Settings for interventions:
    public String interventions_defaultSpoofErrorMessage = "Network error - please wait";
    
    
    //Settings for output to CSV spreadsheet
    public  String recordeddata_CSVSeparator = "¦";
    public  String recordeddata_newlinestring = "((((((NEWLINE))))))";
    
    
    
    public  String debug_fakeTypingByClientSTARTTrigger = "/startdebug"; //This is used by the client to generate fake "is typing events"
    public  String debug_fakeTypingByClientSTOPTrigger = "/stopdebug"; //This is used by the client to generate fake "is typing events"
    
    
    ///Be very! careful modifying this. If it is modified without checking all its uses - can lead to crash!
    static public  String[] recordeddata_mainoutputfileheader_HEADER = {"ExperimentID", "ServerTimestampOfSavingToFile", "GroupID", "Turntype", "SernderID", "SenderUsername", "ApparentSender", "Text", 
         "Recipient(s)", "NoOfDocumentDeletes", "NoOfKeypressDeletes", "ClientTimestampONSET", "ClientTimestampENTER", "ServerTimestampOfReceiptAndOrSending","TextAsformulatedTIMING","TextAsFormulatedLOGSPACE"};
         
    
    //Below are settings for the ExperimentManager GUI
    public String server_mainwindow_instructionToClient = "Please wait for further instructions. Thankyou!";
    public String server_mainwindow_websiteURL = "http://www.qualtrics.com";
    
    static public String about_tcontact = "Call on skype: millsgjm";
    static public String about_econtact = "g (dot) j (dot) mills (at) rug (dot) nl";
    static public String about_econtactname = "Gregory Mills";
    
    
    //Below is versioning information
    static public double about_softwareversion = 5.2;
    static final public String about_info = 
    "This is the 5th incarnation of the chat tool. It grew out of the ROSSINI project (P.G.T. Healey, Queen Mary University)\n"
    + "It was used by Matt Purver for his thesis, was rebuilt for G. Mills's thesis, and then programmed as a stand-alone toolkit\n"
    + "as part of the DiET project (EPSRC). It has been modified and extended as part of the DynDial project (Kings College & QMUL)\n"
    + "and also as part of ERIS (FP7 EU Project, G.J Mills, Stanford and University of Edinburgh).\n\n"+

     "Contributors to the design, coding and documentation of the chat tool include:\n"
            + "Pat Healey, Jonathan Ginzburg, James King, Matt Purver, Arash Eshghi, Chris Howes and Eleni Gregoromichelaki."+
            
            "\n\nIf you would like to know more, please email:\n"+about_econtactname+"\n"+about_econtact+"\n";
    
    
    
   
    static public String telegram_generic_login = "login";
    
    
    static final public String outputfile_newline_replacement_character =    "↲";
    static final public String outputfile_unsupported_character = "█"; 
    
    //static final public String outputfile_newline_replacement_character =  ""+  '\u8626';       //"↲";
    //static final public String outputfile_unsupported_character = ""+ '\u9608';      //"█"; 
    
    static final public String a = ""+ '\u9608';
    
    //end of paramater settings.
    
    public void saveSettings(){
         Yaml yaml = new Yaml();
         Configuration ss = new Configuration();
         String output =  yaml.dump(ss);
         
    }
    
    public void loadSettings(){
        
    }
    
    
    public static void main(String[] args){
       try{ 

        System.out.println(Configuration.outputfile_newline_replacement_character);         
        System.out.println(Configuration.outputfile_unsupported_character);         

        
         Configuration ss = new Configuration();
         //YamlWriter writer = new YamlWriter(new FileWriter("output3.yml"));
        // writer.write(ss);  
        // writer.close();
           
           Yaml yaml = new Yaml();
           String output = yaml.dump(ss);
           System.out.println(output);
           
           Yaml yaml2  = new Yaml();
           Configuration ss2 = yaml2.loadAs(output, Configuration.class);
           System.err.println(ss2.client_MainWindow_height);
           
           
       }catch (Exception e){
           e.printStackTrace();
       }  
    }
    
    
    
}
