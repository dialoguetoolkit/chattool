package diet.task.tangram2D1M;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arash
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.imageio.ImageIO;

public class TangramLoader {
    
    
    private String tangramDir;
    
    public TangramLoader(String tangramDir)
    {
        this.tangramDir=tangramDir;
        previousSetIDs=new Vector<String>();
    }
    
    private Vector<String> previousSetIDs=new Vector<String>();
    
    //precondition is that the server and client directories have the exact same tangram set names
    //and tangram names except that the server images are half the size are halved
    
    public Vector[] getNextTangramSet() throws IOException
    {

        Vector[] result=new Vector[2];

        File tangramClientRootDirectory=new File(tangramDir+File.separator+"Client");
        File tangramServerRootDirectory=new File(tangramDir+File.separator+"Server");
        String[] tangramSetIDs=tangramClientRootDirectory.list();

        System.out.println("listing dir"+tangramSetIDs[0]);
        //Arrays.sort(files);
        Vector<SerialTangram> resultClient=new Vector<SerialTangram>();
        Vector<Tangram> resultServer=new Vector<Tangram>();
        for(int i=0;i<tangramSetIDs.length;i++)
        {
            if (tangramSetIDs[i].equalsIgnoreCase(".svn")) continue;
            File curClientDir=new File(tangramClientRootDirectory+File.separator+tangramSetIDs[i]);
            File curServerDir=new File(tangramServerRootDirectory+File.separator+tangramSetIDs[i]);
            if (!curClientDir.isDirectory()) continue;
            if (previousSetIDs.contains(curClientDir.getName())) continue;
            String[] files=curClientDir.list();
            Arrays.sort(files);
            for(int j=0;j<files.length;j++)
            {
                String curName=files[j];
            
           
                if (curName.substring(curName.length()-4,curName.length()).equalsIgnoreCase(".jpg"))
                {
                    BufferedImage curClientImage=ImageIO.read(new File(curClientDir.getAbsolutePath()+File.separator+curName));
                    BufferedImage curServerImage=ImageIO.read(new File(curServerDir.getAbsolutePath()+File.separator+curName));
                    int w=curClientImage.getWidth(null);
                    int h=curClientImage.getHeight(null);
                    int[] imageData=curClientImage.getRGB(0, 0, w, h, new int[w*h], 0, w);
                    SerialTangram curClientTangram=new SerialTangram(curName.substring(0,curName.length()-4),imageData,w,h);
                    Tangram curServerTangram=new Tangram(curName.substring(0,curName.length()-4), curServerImage);
                    resultClient.add(curClientTangram);
                    resultServer.add(curServerTangram);
                    System.out.println("loading Client image "+curName);
                    curClientImage.flush();
                    curServerImage.flush();
                            
                }
            
            }
            result[0]=resultClient;
            result[1]=resultServer;
            previousSetIDs.add(curClientDir.getName());
            break;
        }
        if (result[0]==null) {System.out.println("returning null");return null;}
        else 
        {
           
            return result;
        }
            
       
    }
    //to get the current set name this method should be called before getNextTangramSet is called again
    public String getCurrentSetName()
    {
        return previousSetIDs.lastElement();
    }
    

}
