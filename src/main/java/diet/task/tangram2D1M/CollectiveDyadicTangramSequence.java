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
public class CollectiveDyadicTangramSequence extends TangramSequence{

    public CollectiveDyadicTangramSequence()
    {
        super();

    }

    public void setNewRandomSequence()
    {


        String[] seq=new String[12];
        String[] collectiveDyadic={"AB",getRandomDirector()};
        String[] dyadicCollective={getRandomDirector(), "AB"};
        String[] dyadicDyadic={getRandomDirector(), getRandomDirector()};
        String[] collectiveCollective={"AB","AB"};
        Vector<String[]> v=new Vector<String[]>();
        v.add(collectiveDyadic);
        v.add(collectiveCollective);
        v.add(dyadicDyadic);
        v.add(dyadicCollective);

        for(int i=0;i<this.interventionSlots.length;i++)
        {
            seq=insertAt(this.interventionSlots[i]-2, seq, v.remove(r.nextInt(v.size())));
        }

        for(int i=0;i<seq.length;i++)
        {
            if (seq[i]==null)
            {
                if (i!=0&&i!=seq.length-1)
                {
                    if (seq[i+1].equals("A")||seq[i-1].equals("A")) seq[i]="B";
                    else if (seq[i+1].equals("B")||seq[i-1].equals("B")) seq[i]="A";
                    else seq[i]=getRandomDirector();


                }
                else if (i==0)
                {
                    seq[i]=getRandomDirector();
                }
                else
                {
                    if (seq[i-1].equals("A")) seq[i]="B";
                    else if (seq[i-1].equals("B")) seq[i]="A";
                    else seq[i]=getRandomDirector();
                }

            }
        }
        this.sequence=seq;
        //System.out.println(this);


    }

    public String getRandomDirector()
    {
        if (r.nextInt(2)==0) return "A";
        else return "B";
    }

    private static String[] insertAt(int index, String[] a, String[] b)
    {
        String[] result=new String[a.length];


        if (b.length+index>a.length)
        {
            System.out.println("invalid insertion");
            return null;
        }

        for (int i=0;i<a.length;i++)
        {
            if (i<index||i>=index+b.length)
            {
                result[i]=a[i];
            }
            else
            {
                result[i]=b[i-index];
            }
        }
        return result;
    }
    public static void main(String args[])
    {
        
        CollectiveDyadicTangramSequence s=new CollectiveDyadicTangramSequence();
        for(int i=0;i<=10; i++)
        {
            s.setNewRandomSequence();
            System.out.println(s);
        }/*
        String[] a={"AB", "AB", "A", "B", "NN"};
        String[] b={"C", "MM"};
        String[] result=insertAt(1, a , b);
        for (int i=0;i<result.length;i++)
        {
            System.out.print(result[i]+ " ");
        }*/
    }



}
