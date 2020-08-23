/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController.ui;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import java.net.URL;



/**
 *
 * @author gj
 */
public class OpenInBrowser  {
    
    public OpenInBrowser() {
    }
    
    
    
    public static String openInBrowser(String url)
{
   
    try
        {
            URI uri = new URL(url).toURI();
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
                desktop.browse(uri);
        }
    catch (Exception e)
        {
          e.printStackTrace();
          System.err.println("THE URL WAS: "+url);
        }
    //System.exit(-5);
    return "";
}
}
