/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController.autointervention;

import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.tg.TelegramParticipant;
import java.io.File;
import java.util.Vector;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author LX1C
 */
public class autointervention {
    
    Conversation c;
    
    Vector<interventionblock> vib = new Vector();
    Vector<interventionmodify> vim = new Vector();
     Vector<interventiondelay> vid = new Vector();

    
    public autointervention(Conversation c) {
        this.c = c;
       // this.loadFromFile();
    }
    
    public Vector<interventionblock> getBlockRules(){
        return vib;
    }
     public Vector<interventionmodify> getModifyRules(){
        return vim;
    }
     public Vector<interventiondelay> getDelayRules(){
        return vid;
    }
    
    
    
    
    
    public synchronized void loadFromFile(){
        
        
        
        String path = System.getProperty("user.dir")+ File.separator+"experimentresources"+File.separator+"interventionsauto";
        
        
       // File f = new File(path,"makehappy.xlsx");
        
        File f = CustomDialog.loadFileWithExtension(path, "Select excel file containing interventions", ".xlsx", "excel file");
        if(f==null){
            CustomDialog.showDialog("The file you selected doesn`t exist! Please try again");
            return;
        }
        
        if(!f.canRead()||!f.canWrite()){
            CustomDialog.showDialog("The file you have just selected is still open in another application. Perhaps it is open in Excel or LibreOffice? Please close that application and try again.");
            return;
        }
        Conversation.printWSln("Main", "Loading intervention rules from the spreadsheet. This might take a few seconds....please wait for confirmation that it has loaded!");
        
        this.vib =new Vector();
        this.vim=new Vector();
        this.vid=new Vector();
        try{
            
            
          Workbook workbook = WorkbookFactory.create(f,"",true);
          System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
          
         System.out.println("Retrieving Sheets using for-each loop");
          for(Sheet sheet: workbook) {
               String sheetname = sheet.getSheetName();
                System.out.println("=> " + sheet.getSheetName());
               if(sheetname.equalsIgnoreCase("block")){
                    this.loadBlockRules(sheet);
               }
               else if(sheetname.equalsIgnoreCase("modify")){
                    this.loadModifyRules(sheet);
               }
               else if(sheetname.equalsIgnoreCase("delay")){
                    this.loadDelayRules(sheet);
               }
              
            
          }
          
          
        }catch(Exception e){
            e.printStackTrace();
        }
         Conversation.printWSln("Main", "Finished loading the intervention rules from the spreadsheet");
    }    
    
    
    public void loadBlockRules(Sheet sheet){
        System.err.println("Loading blocking rules: ");
        DataFormatter dataFormatter = new DataFormatter();
               
        for (int i=7;i<1500;i++) {
            
            Row row = sheet.getRow(i);
            
            String interventionid = dataFormatter.formatCellValue(row.getCell(1));
            String participantid = dataFormatter.formatCellValue(row.getCell(3));
            String targetposcriteria = dataFormatter.formatCellValue(row.getCell(5));
            String targetnegcriteria = dataFormatter.formatCellValue(row.getCell(7));
            String msgToSender = dataFormatter.formatCellValue(row.getCell(9));
            
            System.err.print("ROW: "+i+ " interventionid:"+interventionid+" targetposcriteria:"+targetposcriteria);
            
            
            
            if(!interventionid.equalsIgnoreCase("") && !targetposcriteria.equalsIgnoreCase("")){
                interventionblock ib = new interventionblock(interventionid,participantid,targetposcriteria,targetnegcriteria,msgToSender);
                this.vib.add(ib);
                System.err.println("Adding rule: "+interventionid);
            }          
        }
        
    }
    
    
    
     public void loadModifyRules(Sheet sheet){
        System.err.println("Loading modify rules: ");
        DataFormatter dataFormatter = new DataFormatter();
               
        for (int i=7;i<1500;i++) {
            
            Row row = sheet.getRow(i);
            
            String interventionid = dataFormatter.formatCellValue(row.getCell(1));
            String participantid = dataFormatter.formatCellValue(row.getCell(3));
            String targetposcriteria = dataFormatter.formatCellValue(row.getCell(5));
            String targetnegcriteria = dataFormatter.formatCellValue(row.getCell(7));
            String stringToBeReplaced = dataFormatter.formatCellValue(row.getCell(9));
            String stringReplacement = dataFormatter.formatCellValue(row.getCell(11));
            
            
            System.err.print("ROWMODIFY: "+i+ " interventionid:"+interventionid+" targetposcriteria:"+targetposcriteria);
            
            
            try{
            if(!interventionid.equalsIgnoreCase("") && !targetposcriteria.equalsIgnoreCase("")){
                interventionmodify im = new interventionmodify(interventionid,participantid,targetposcriteria,targetnegcriteria,stringToBeReplaced, stringReplacement);
                this.vim.add(im);
                System.err.println("Adding rule: "+ im.toString());
            }
            }catch(Exception e){
                e.printStackTrace();
                Conversation.saveErr(e);
                CustomDialog.showDialog("There was an error loading intervention: "+interventionid+ " \n" 
                        + participantid+ "\n"
                        + targetposcriteria +"\n"
                        + targetnegcriteria + "\n"
                        + stringToBeReplaced + "\n"
                        + stringReplacement +"\n\n\n"
                        + "The error is most likely to be a typo in one of the regular expressions\n\n\n"
                                + "The java system error is:\n\n"
                                + e.getMessage());
            }
        }
        
    }
    
    
     
     public void loadDelayRules(Sheet sheet){
        System.err.println("Loading delay rules: ");
        DataFormatter dataFormatter = new DataFormatter();
               
        for (int i=7;i<1500;i++) {
            
            Row row = sheet.getRow(i);
            
            String interventionid = dataFormatter.formatCellValue(row.getCell(1));
            String participantid = dataFormatter.formatCellValue(row.getCell(3));
            String targetposcriteria = dataFormatter.formatCellValue(row.getCell(5));
            String targetnegcriteria = dataFormatter.formatCellValue(row.getCell(7));
            String delaystr= dataFormatter.formatCellValue(row.getCell(9));
            
    
            System.err.print("ROWDELAY: "+i+ " interventionid:"+interventionid+" targetposcriteria:"+targetposcriteria);
            
            
            
            if(!interventionid.equalsIgnoreCase("") && !targetposcriteria.equalsIgnoreCase("")){
                interventiondelay id = new interventiondelay(interventionid,participantid,targetposcriteria,targetnegcriteria, delaystr);
                this.vid.add(id);
                System.err.println("Adding rule: "+ id.toString());
            }          
        }
        
    }
    
     
     
    
    
    
    
    
    
    public boolean processText(Participant p, String textOfTurns){
          boolean doBlock = this.processText_doBlock( p,  textOfTurns);
          if(doBlock)return doBlock;
          
          boolean doModify = this.processText_doModify(p, textOfTurns);
          if(doModify) return doModify;
          
          long doDelay = this.processText_doDelay(p, textOfTurns);
          if(doDelay>0){
                 if(c.isInTelegramMode()){
                       c.telegram_sendDelayedArtificialTurnFromApparentOriginToPermittedParticipants((TelegramParticipant)p,  textOfTurns, doDelay);
                       
                 }      
                  else{
                       c.sendArtificialDelayedTurnToPermittedParticipants(p, textOfTurns, doDelay);
                 } 
                  
          }                
          return false;
    }
    
    
    
    
    public boolean processText_doBlock(Participant p, String textOfTurn){
         boolean performedBlock = false;
         for(int i=0;i<this.vib.size();i++){
             interventionblock ib = vib.elementAt(i);
             boolean doBlock = ib.blockTurn(p.getParticipantID(), textOfTurn);
             if(doBlock){
                 String response = ib.getResponse();
                 if(!response.equalsIgnoreCase("")){
                      if(c.isInTelegramMode()){
                           c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)p, response);
                      }
                      else{
                          c.sendInstructionToParticipant(p, textOfTurn);
                      }
                      c.saveAdditionalRowOfDataToSpreadsheetOfTurns( "autointervention", p, textOfTurn+":"+ib.toString());
                 }
                 return true;
             } 
         }
         return false;
         
    }
    
    
    
     public boolean processText_doModify(Participant p, String textOfTurn){
         
      try{   
         String sv1 = ""+textOfTurn;
         String sv2 = this.getModifiedString(p, sv1);
         System.err.println("Here98getmod-----"+sv1+"-----------"+sv2);
         while(!sv1.equals(sv2)){
             sv1 = ""+sv2;
             sv2=this.getModifiedString(p, sv1);
             System.err.println("Here99getmod-----"+sv1+"-----------"+sv2);
         }
          System.err.println("Here100getmod-----"+sv1+"-----------"+sv2);
         if(!textOfTurn.equals(sv2)) {
             System.err.println("Here101getmod");
              if(c.isInTelegramMode()){   
                          System.err.println("Here102getmod");
                         long dDly = processText_doDelay(p, sv2);
                         if(dDly >0){
                              System.err.println("AUTOINTERVENTION DELAY IS MORE THAN ZERO");
                             c.telegram_sendDelayedArtificialTurnFromApparentOriginToPermittedParticipants((TelegramParticipant)p,  sv2, dDly);
                             
                         }
                         else{
                              c.telegram_sendArtificialTurnFromApparentOriginToPermittedParticipants((TelegramParticipant)p,  sv2);    
                         }
       }
                     else{
                         System.err.println("AUTOINTERVENTION: DOMODIFYF"); 
                         long doDelay = this.processText_doDelay(p, sv2);
                         if(doDelay>0){
                             c.sendArtificialDelayedTurnToPermittedParticipants(p, sv2, doDelay);
                             
                         }
                         else{
                              c.sendArtificialTurnFromApparentOriginToPermittedParticipants(p,  sv2);
                         }
                     }
                     Conversation.printWSln("Main", "OK Detected! CHANGING: "+textOfTurn+" TO: "+sv2);
                     return true;
         }
         return false;
         
      }catch(Exception e){
          e.printStackTrace();
          Conversation.saveErr(e);
          return false;
      }
     }
    
     public String getModifiedString(Participant p,String textOriginal){
         System.err.println("Here1getmod");
         for(int i=0;i<this.vim.size();i++){
             System.err.println("Here2getmod");
             interventionmodify im = vim.elementAt(i);
             boolean doModify = im.modifyTurn(p.getParticipantID(), textOriginal);
             if(doModify){
                 System.err.println("Here3getmod");
                 c.saveAdditionalRowOfDataToSpreadsheetOfTurns( "autointervention", p, im.toString());
                 return im.getModifiedText(textOriginal);
             }
                  
     }
      System.err.println("Here4getmod "+textOriginal);
      return textOriginal;
     }
    
    public boolean processText_doModifyOLD(Participant p, String textOfTurn){
        
         boolean performedModify = false;
         for(int i=0;i<this.vim.size();i++){
             
             interventionmodify im = vim.elementAt(i);
             boolean doModify = im.modifyTurn(p.getParticipantID(), textOfTurn);
             if(doModify){
                 
                 String newTurn = im.getModifiedText(textOfTurn);
                 if(newTurn.equals(textOfTurn)){
                     
                     //Don't need to do intervention - they are the same.
                 }
                 else{
                     if(c.isInTelegramMode()){                        
                         long dDly = processText_doDelay(p, textOfTurn);
                         if(dDly >0){
                              System.err.println("AUTOINTERVENTION DELAY IS MORE THAN ZERO");
                             c.telegram_sendDelayedArtificialTurnFromApparentOriginToPermittedParticipants((TelegramParticipant)p,  newTurn, dDly);
                         }
                         else{
                              c.telegram_sendArtificialTurnFromApparentOriginToPermittedParticipants((TelegramParticipant)p,  newTurn);    
                         }
       }
                     else{
                         System.err.println("AUTOINTERVENTION: DOMODIFYF"); 
                         long doDelay = this.processText_doDelay(p, textOfTurn);
                         if(doDelay>0){
                             c.sendArtificialDelayedTurnToPermittedParticipants(p, newTurn, doDelay);
                             
                         }
                         else{
                              c.sendArtificialTurnFromApparentOriginToPermittedParticipants(p,  newTurn);
                         }
                     }
                     c.saveAdditionalRowOfDataToSpreadsheetOfTurns( "autointervention", p, textOfTurn+":"+im.toString());
                     return true;
                 }
             }    
         }
         return false;
    }
    
    
    
    public Long processText_doDelay(Participant p, String textOfTurn){
        
      try{  
         System.err.println("AUTOINTERVENTION: DODELAYAA");
         boolean performDelay = false;
         for(int i=0;i<this.vid.size();i++){
             System.err.println("AUTOINTERVENTION: DODELAYAB");
             interventiondelay id = vid.elementAt(i);
              System.err.println("AUTOINTERVENTION: DODELAYAC"+id.toString());
             boolean doDelay =  id.delayTurn(textOfTurn, textOfTurn)  ;
             if(doDelay){
                 c.saveAdditionalRowOfDataToSpreadsheetOfTurns( "autointervention", p, textOfTurn+":"+ id.toString());
                 return id.delay;
             }    
         }
         return (long)0;
      }catch (Exception e){
          e.printStackTrace();
          
      }   
      return (long)0;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args){
         autointervention au = new autointervention(null);
         au.loadFromFile();
         
         
         boolean process = au.processText_doModify(new Participant(null,"a","b"), "This is text OK");
         //System.out.println(process);
         
    }
    
    
}
