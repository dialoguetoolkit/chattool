/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.tg;

import diet.server.Conversation;
import diet.utils.VectorHashtable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import javax.imageio.ImageIO;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author LX1C
 */
public class TGASYNCMEDIAGETTER extends Thread{
    
    
    Conversation c;
    TGBOT tg;

    Vector<Update> v = new Vector<Update>();
    diet.utils.VectorHashtable ht_sender_updates= new VectorHashtable();
    Hashtable ht_update_sender= new Hashtable();
    
    diet.utils.VectorHashtable ht_sender_images= new VectorHashtable();
    
    Hashtable ht_update_file_id= new Hashtable();
    
    public TGASYNCMEDIAGETTER(Conversation c, TGBOT tg) {
        this.c=c;
        this.tg = tg;
        this.start();
    }
       
    public void run(){
        while(2<5){
            Update u = this.getNext();
            System.err.println("TGUTILSPHOTOSAVER retrieving image");
            if(u.hasMessage() && u.getMessage().hasPhoto()){
                getPhotoFromUpdate(u);
            }
            if(u.hasMessage() && u.getMessage().hasVoice()){
                getVoiceFromUpdate(u);
            }
            
            
            
            
           
            
        }   
    }
    
    public synchronized void addItem(TelegramParticipant sender, Update u){
        v.add(u);
        ht_sender_updates.put(sender, u);
        ht_update_sender.put(u, sender);
        this.notifyAll();
    }
    
    
    public synchronized Update getNext(){
        while(v.size()==0){
            try{
              wait();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        Update u = v.elementAt(0);
        v.remove(u);
        return u;
        
    }
    
    
    
    public void getPhotoFromUpdate(Update update){
      if (update.hasMessage() && update.getMessage().hasPhoto()) {
           PhotoSize ps = getPhoto(update);
           
           String file_id =   ps.getFileId();
           ht_update_file_id.put(update, file_id);
           
           
           
           String filepath= getFilePath(ps);
           java.io.File p = this.downloadFileByFilePath(filepath);
           BufferedImage img = null;
            try {
                 img = ImageIO.read(p);
                 
                 TelegramParticipant tp = (TelegramParticipant)ht_update_sender.get(update);
                 long time = new Date().getTime();
                 
                 String id = (String)this.ht_update_file_id.get(update);
                 if(id==null)id="";
                 
                 String filename = time+"_"+tp.getParticipantID()+"_"+id+".png";
                 
                 c.convIO.saveBufferedImage(img, filename);
                 
                 System.err.println("IMGHEIGHT:"+img.getHeight());      
             }   catch (Exception e) {
                 e.printStackTrace();
                 Conversation.saveErr(e);
            }
      }
      
  }
  
  
    
    
    
  public void getVoiceFromUpdate(Update update){
      if (update.hasMessage() && update.getMessage().hasVoice()) {
          
        String uniquefileid = update.getMessage().getVoice().getFileUniqueId(); 
        
        String fileid = update.getMessage().getVoice().getFileId(); 
        
        ht_update_file_id.put(update, fileid);
           
        System.err.println("file: "+fileid+ " unique: "+uniquefileid);
           
           
        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(fileid);
        try {         
             System.err.println("VOICE: getFileMethod:"+getFileMethod.toString());
            // We execute the method using AbsSender::execute method.
            org.telegram.telegrambots.meta.api.objects.File file =   tg.execute(getFileMethod);
            // We now have the file_path
            
            String filepath = "";
            
            if(file == null){
                System.err.println("VOICE: FILE==NULL");
            }
            else{
                System.err.println("VOICE:file.getFilePath: "+file.getFilePath().toString());
                filepath = file.getFilePath().toString();
                java.io.File p = this.downloadFileByFilePath(filepath);
                
                TelegramParticipant tp = (TelegramParticipant)ht_update_sender.get(update);
                 long time = new Date().getTime();
                 
                 String id = (String)this.ht_update_file_id.get(update);
                 if(id==null)id="";
                 
                 String filename = time+"_"+tp.getParticipantID()+"_"+id+".ogg";
                 
                 c.convIO.saveBufferedFile(p, filename);
                 
                 System.err.println("SAVING VOICE");                 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
           
      }
      
     }
  }
    
    
    
    
    
    
    
  private PhotoSize getPhoto(Update update) {
    // Check that the update contains a message and the message has a photo
    if (update.hasMessage() && update.getMessage().hasPhoto()) {
        // When receiving a photo, you usually get different sizes of it
        List<PhotoSize> photos = update.getMessage().getPhoto();

        // We fetch the bigger photo
        return photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
    }

    // Return null if not found
    return null;
}
    
 private String getFilePath(PhotoSize photo) {
    Objects.requireNonNull(photo);

    if (photo.hasFilePath()) { // If the file_path is already present, we are done!
        System.err.println("PHOT:IT HAS A FILE PATH: "+photo.getFilePath().toString());
        return photo.getFilePath();
    } else { // If not, let find it
        // We create a GetFile method and set the file_id from the photo
        System.err.println("PHOT:IT DOESN'T HAVE A FILE PATH: ");
        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(photo.getFileId());
        try {
            
            System.err.println("PHOT:IT DOESN'T HAVE A FILE PATH: getFileMethod:"+getFileMethod.toString());
            // We execute the method using AbsSender::execute method.
            org.telegram.telegrambots.meta.api.objects.File file =   tg.execute(getFileMethod);
            // We now have the file_path
            if(file == null){
                System.err.println("PHOT: FILE==NULL");
            }
            else{
                System.err.println("PHOT:file.getFilePath: "+file.getFilePath().toString());
            }
            
            return file.getFilePath();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    return null; // Just in case
}
    
 private java.io.File downloadFileByFilePath(String filePath) {
    try {
        // Download the file calling AbsSender::downloadFile method
        return tg.downloadFile(filePath);
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }

    return null;
}   
 
 
 
}

    
    
    
    
    
    
    
    

