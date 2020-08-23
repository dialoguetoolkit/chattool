package diet.task.tangram2D1M;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arash
 */
import java.util.Random;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RandSeq {

  
    public static String nextRandomSequence()
    {
        Random r=new Random();
        r=new Random();
        Vector<String> sequences = new Vector<String>();        
        String seq="";
        
        while (true)
        {
            
            
            
            while(!sequences.isEmpty())
            {
                seq+=sequences.remove(r.nextInt(sequences.size()));
            }
            if (sequenceIsValid(seq)) break;
            else {
                //System.out.println("Sequence was invalid:"+seq);
                
                seq="";
                sequences.add("AB");
                sequences.add("AB");
                sequences.add("AB");
                sequences.add("BA");
                sequences.add("BA");
                sequences.add("BA");
            }
           
            
        }
        return seq;
    }
    private static boolean sequenceIsValid(String seq)
    {
        if (seq.length()!=12) return false;
        Pattern p=Pattern.compile("AA");
        Matcher m=p.matcher(seq);
        boolean hasAA=m.find();
        p=Pattern.compile("BB");
        m=p.matcher(seq);
        boolean hasBB=m.find();
        return hasAA&&hasBB;
    }
    public static void main(String a[])
    {
        for(int i=0;i<10;i++)
        {
            System.out.println(nextRandomSequence());
        }
    }

}
