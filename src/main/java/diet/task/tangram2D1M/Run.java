package diet.task.tangram2D1M;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arash
 */
public class Run {
    public static void main(String args[])
    {
        //Matcher Frame Test
        /*
        try{
            
            MatcherTangramFrame m=new MatcherTangramFrame();
            m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            m.setVisible(true);
        }
        catch(IOException e)
        {
            System.out.println("IO Exception occured! Sorry");
        }
        
        */
        //Director Frame Test
       /*
        try{
            String seq=RandSeq.nextRandomSequence();
            System.out.println(seq);
            TangramLoader tl=new TangramLoader(System.getProperty("user.dir")+File.separator+"tangram_sets");
            Vector<Tangram> v=tl.getNextTangramSet();
            String setName=tl.getCurrentSetName();
            DirectorTangramFrame d1=new DirectorTangramFrame(seq, 'A', v,setName);
            DirectorTangramFrame d2=new DirectorTangramFrame(seq,'B', v, setName);
            d1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            d1.setVisible(true);
            d2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            d2.setVisible(true);
        }
        catch(IOException e)
        {
            System.out.println("IO Exception occured! Sorry");
        }*/
     
    }

}
