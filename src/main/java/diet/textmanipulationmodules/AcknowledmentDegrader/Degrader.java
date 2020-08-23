/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.textmanipulationmodules.AcknowledmentDegrader;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Arash
 */


public class Degrader {
    
    
    String mainDirectory;
    Vector<String> degradings=new Vector<String>();
    Vector<String> regexs=new Vector<String>();
    Random r=new Random(new Date().getTime());
    
  
     public Degrader(String directory) 
    {
      try{  
        r=new Random(new Date().getTime());
        mainDirectory=directory;
        BufferedReader reader=new BufferedReader(new FileReader(directory+"\\"+"degradings.txt"));
        String cur;
        while((cur=reader.readLine())!=null)
        {
            degradings.add(cur);
        }
        reader=new BufferedReader(new FileReader(directory+"\\"+"regexs.txt"));
        while((cur=reader.readLine())!=null)
        {
            regexs.add(cur);
        }
      }catch(Exception e){
          System.out.println("ERROR LOADING DEGRADING");
          System.exit(-12343215);
      } 
        
    }
    
    private String chooseRandomDegrading()
    {
        int n=degradings.size();
        
        return degradings.elementAt(Math.abs(r.nextInt())%n);
        
    }
    public void reloadFiles() throws IOException
    {
        r=new Random(new Date().getTime());
        BufferedReader reader=new BufferedReader(new FileReader(mainDirectory+"\\"+"degradings.txt"));
        String cur;
        while((cur=reader.readLine())!=null)
        {
            degradings.add(cur);
        }
        reader=new BufferedReader(new FileReader(mainDirectory+"\\"+"regexs.txt"));
        while((cur=reader.readLine())!=null)
        {
            regexs.add(cur);
        }
        
        
    }
    
    
    //Gives the same turn back if there's no cue in the turn.
    public String degrade(String turn1)
    {
        
        
        String turn=turn1+" ";
        String degrading=chooseRandomDegrading();   
        Iterator<String> i=regexs.iterator();
        while(i.hasNext())
        {
            String regex=i.next();
        
            Pattern p=Pattern.compile(regex);
            Matcher matcher=p.matcher(turn);
            
            if (matcher.find())
            {
                String groundingCue=matcher.group();
                System.out.println("matching group is"+groundingCue);
                if (groundingCue.length()==0) continue;
                if (groundingCue.charAt(groundingCue.length()-1)=='?')
                {
                    continue;
                }
            
                int start=matcher.start();
                int end=matcher.end();
                if (start==0&&end==turn.length())
                {
                    
                    return degrading;
                    //stand-alone
                    
                }
                else if(start==0)
                {
                    if (!groundingCue.matches("[\\.\\s!]*[rR][iI][gG][hH][tT][\\.!?\\s]*"))
                    {
                        
                        
                    
                    return degrading+" "+turn.substring(end, turn.length());
                    //turn initial
                    }
                    else continue;
                }
                    
                   
                                   
            }
        }
        return null;
        
        
    }
    public static void main(String args[])
    {
        try{
            Degrader d=new Degrader("D:\\UniWork\\Experiments");
            File out=new File("D:\\UniWork\\Experiments\\replacements.txt");
            out.createNewFile();
            BufferedWriter writer=new BufferedWriter(new FileWriter(out));
            BufferedReader in=new BufferedReader(new FileReader("D:\\UniWork\\Experiments\\degradingstab.txt"));
            String cur;
            while((cur=in.readLine())!=null)
            {
               String result=d.degrade(cur);
               if (result!=null){
               writer.write(result, 0, result.length());
               writer.newLine();
               writer.flush();}
               else
               {
                   writer.write(cur, 0, cur.length());
               writer.newLine();
               writer.flush();
                   
               }
            }
            writer.close();
          
            
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        
        
    }

}
