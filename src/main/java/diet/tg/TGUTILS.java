/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.tg;




import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;




/**
 *
 * @author LX1C
 */
public class TGUTILS {
    
  TGBOT tgb;  
    
  public TGUTILS(TGBOT tgb){
      this.tgb=tgb;
  }  
    
  
  

  
  
  
  
  
  
  public java.io.File getPhotoFromUpdate(Update update){
      if (update.hasMessage() && update.getMessage().hasPhoto()) {
           PhotoSize ps = getPhoto(update);
           String filepath= getFilePath(ps);
           java.io.File p = this.downloadPhotoByFilePath(filepath);
      }
      return null;
  }
  
  
    
  private PhotoSize getPhoto(Update update) {
    // Check that the update contains a message and the message has a photo
    if (update.hasMessage() && update.getMessage().hasPhoto()) {
        // When receiving a photo, you usually get different sizes of it
        List<PhotoSize> photos = update.getMessage().getPhoto();

        // We fetch the bigger photo
        return photos.stream()
                .max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
    }

    // Return null if not found
    return null;
}
    
 private String getFilePath(PhotoSize photo) {
    Objects.requireNonNull(photo);

    if (photo.hasFilePath()) { // If the file_path is already present, we are done!
        return photo.getFilePath();
    } else { // If not, let find it
        // We create a GetFile method and set the file_id from the photo
        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(photo.getFileId());
        try {
            // We execute the method using AbsSender::execute method.
            File file =   tgb.execute(getFileMethod);
            // We now have the file_path
            
            
            return file.getFilePath();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    return null; // Just in case
}
    
 private java.io.File downloadPhotoByFilePath(String filePath) {
    try {
        // Download the file calling AbsSender::downloadFile method
        return tgb.downloadFile(filePath);
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }

    return null;
}   
 
 
 
}
