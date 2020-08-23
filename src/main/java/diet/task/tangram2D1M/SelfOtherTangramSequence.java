/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

import java.util.Vector;

/**
 *
 * @author Arash
 */
public class SelfOtherTangramSequence extends TangramSequence{

    public SelfOtherTangramSequence()
    {
        super();
    }

    public void setNewRandomSequence()
    {
        Vector<String> interventions=new Vector<String>();
        interventions.add("AB");interventions.add("BA");interventions.add("BB");interventions.add("AA");
        
        String seq="            ";
        int i=0;
        while(!interventions.isEmpty())
        {
            String insert=interventions.remove(r.nextInt(interventions.size()));
            //System.out.println("inserting"+insert);
            seq=insertAt(seq, insert, interventionSlots[i]-2);
            i++;

        }
        System.out.println(seq);
        //filling empty spaces:
        for(int j=0;j<interventionSlots.length;j++)
        {
            int empty=interventionSlots[j]-3;
            if (seq.charAt(empty)==' ')
            {
                if (empty>0)
                {
                    if (seq.charAt(empty-1)=='A'&&seq.charAt(empty+1)=='A')
                        seq=insertAt(seq, "B",empty);
                    else if (seq.charAt(empty-1)=='B'&&seq.charAt(empty+1)=='B')
                        seq=insertAt(seq, "A",empty);
                    else
                    {
                        if (seq.substring(empty+1,empty+3).equalsIgnoreCase("AA")
                                ||
                                seq.substring(empty-2,empty).equalsIgnoreCase("AA"))

                            seq=insertAt(seq, "B", empty);

                        else if (seq.substring(empty+1,empty+3).equalsIgnoreCase("BB")
                                ||
                                seq.substring(empty-2,empty).equalsIgnoreCase("BB"))

                            seq=insertAt(seq, "A", empty);



                    }
                }
                else
                {
                    if (seq.charAt(empty+1)=='A')
                        seq=insertAt(seq, "B",empty);
                    else if (seq.charAt(empty+1)=='B')
                        seq=insertAt(seq, "A",empty);

                }




            }
            else continue;
        }
        if (seq.charAt(11)==' ')
        {
            if (seq.charAt(10)=='B') seq=insertAt(seq, "A", 11);
            else seq=insertAt(seq, "B", 11);
        }
        //converting to string array
        System.out.println(seq);
        this.sequence=convertToArray(seq);
    }
    private String[] convertToArray(String seq)
    {
        if (seq.length()!=12) return null;
        //System.out.println(seq);
        String[] result=new String[12];
        for(int i=0;i<seq.length();i++) result[i]=seq.substring(i, i+1);
        //System.out.println("last char is"+result[11]+"ll");
        return result;
    }

    private static String insertAt(String s, String insert, int index)
    {
        if (index>s.length()) return s;
        String result="";
        char[] charSeq=s.toCharArray();
        boolean inserted=false;
        for(int i=0;i<charSeq.length;)
        {
            if (i<index||inserted)
            {
                result+=charSeq[i];
                i++;
            }
            else
            {
                result+=insert;
                i+=insert.length();
                inserted=true;

            }
        }
        return result;

        
    }
    public static void main(String args[])
    {
        SelfOtherTangramSequence s=new SelfOtherTangramSequence();
        for(int i=0;i<=30; i++) s.setNewRandomSequence();
        //insertAt("     ", "AB", 2);


    }



}
