/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.stimuliset;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;

/**
 *
 * @author sre
 */
public class SerializableImage implements Serializable {
    
      byte[] imageInByte;
       
      int width = 0;
      int height =0;
      
      String name ="";
      
      
      
      
      public SerializableImage(BufferedImage bi, String s, String notused){
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         	
         try{
            ImageIO.write(bi, "png", baos);
	    baos.flush();
	    imageInByte = baos.toByteArray();
	    baos.close();
            this.width=bi.getWidth();
            this.height=bi.getHeight();
         }catch (Exception e){
             e.printStackTrace();
         }
      }
    
      
      
      
      
      int[] pixels;

 public SerializableImage(BufferedImage bi, String name){
   this.name = name;
   width = bi.getWidth();
   height = bi.getHeight();
   pixels = new int[width * height];
   int[] tmp=bi.getRGB(0,0,width,height,pixels,0,width);
   pixels=tmp;
}

public BufferedImage getImage() {
   BufferedImage bi = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
   bi.setRGB(0,0,width,height,pixels,0,width);
return bi;
}


      
      
      
      
      
      
      
     
      
      
      public BufferedImage getBufferedImage(){
          try{
             InputStream in = new ByteArrayInputStream(imageInByte);
	     BufferedImage bImageFromConvert = ImageIO.read(in);
             //bImageFromConvert.getR
             return bImageFromConvert;
          }catch (Exception e){
              e.printStackTrace();
          }  
          return null;
      }
      
      
      
      
      
      public BufferedImage produceRenderedImage(){
            DataBuffer dBuffer = new DataBufferByte(imageInByte, width * height);
            WritableRaster wr = Raster.createInterleavedRaster(dBuffer,width,height,width,1,new int[]{0},null);
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorModel cm = new ComponentColorModel(cs,false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
            BufferedImage bi = new BufferedImage(cm, wr, false, null);
           return bi;
      }
      
      
      
      
      public String getName(){
          return this.name;
      }
      
}
    

