/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.tg;

import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author LX1C
 */
public class tgSTARTER {
    
   
    
    public static void main(String[] args){
        tgSTARTER tgs = new tgSTARTER();
        //tgs.loadBotNameFromFile();
    }
     public static TGBOT startBOT(Conversation c){
         return loadBotNameFromFile(c);
     }
     
     public static TGBOT loadBotNameFromFile(Conversation c){
        String userdir = System.getProperty("user.dir")+File.separator+"tg";
        System.err.println("userdir is: "+userdir);
        File f = new File(userdir, "botname.txt");
        
        String mybotusername=null;
        String mybottoken = null ;
        
        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
            StringBuilder sb = new StringBuilder();
            mybotusername = br.readLine();
            mybottoken =br.readLine();
           
            
        }catch (Exception e){
            e.printStackTrace();
        }   
       
        if(mybotusername==null){
            CustomDialog.showDialog("Could not find bot name in "+f.getAbsolutePath());
            return null;
        }
        if(mybottoken==null){
            CustomDialog.showDialog("Could not find bot token in"+f.getAbsolutePath());
            return null;
        }
      
       ApiContextInitializer.init();
       TelegramBotsApi botsApi = new TelegramBotsApi();
       TGBOT cTGBOT = new TGBOT(c,mybotusername,mybottoken);
       
       cTGBOT.loadAdminIDsFromFileAndStart();
       
       
       
        try {
            botsApi.registerBot(cTGBOT);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            CustomDialog.showDialog("Could not start the telegram bot with bot username: "+mybotusername+ "  and with bot token: "+mybottoken+"\n"
                    + "Check the file /tg/botname.txt");
        }   
        return cTGBOT;
    }
        
     
    
    
    
   
    
    
}
