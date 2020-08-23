package diet.task.tangram2D1M;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arash
 */

public class ClientTangramMessageOutgoing {
    
    //setID is name of the tangram set
   
    public static void tangramClicked(String tangramID, String setID)
    {
        System.out.println("Tangram "+tangramID+" clicked from tangram set "+setID);
    }
    
    //-1 if released outside all slots
    public static void tangramReleased(String tangramID, String setID, int droppedOnSlotID, boolean successful) 
    {
        if (successful)
        {
            System.out.println();
        }
        else
        {
            System.out.println();
        }
    }
    

}
