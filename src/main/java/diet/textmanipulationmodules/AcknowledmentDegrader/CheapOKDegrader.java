/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.textmanipulationmodules.AcknowledmentDegrader;

import diet.server.Conversation;

/**
 *
 * @author gj
 */
public class CheapOKDegrader {
    public static boolean doesStringHaveOK(String s){
        String haystack = " "+s+" ";
        if(haystack.indexOf(" ok ")>-1) return true;
        if(haystack.indexOf(" okay ")>-1) return true;
        if(haystack.indexOf(" oke ")>-1) return true;
        if(haystack.indexOf(" oky ")>-1) return true;
        if(haystack.indexOf(" k ")>-1) return true;
        if(haystack.indexOf(" kk ")>-1) return true;
        if(haystack.indexOf(" kay ")>-1) return true;
        if(haystack.indexOf(" mkay ")>-1) return true;
        
        if(haystack.indexOf(" OK ")>-1) return true;
        if(haystack.indexOf(" OKAY ")>-1) return true;
        if(haystack.indexOf(" OKE ")>-1) return true;
        if(haystack.indexOf(" OKY ")>-1) return true;
        if(haystack.indexOf(" K ")>-1) return true;
        if(haystack.indexOf(" KK ")>-1) return true;
        if(haystack.indexOf(" KAY ")>-1) return true;
        if(haystack.indexOf(" MKAY ")>-1) return true;
        
        
        if(haystack.indexOf(" ok.")>-1) return true;
        if(haystack.indexOf(" okay.")>-1) return true;
        if(haystack.indexOf(" oke.")>-1) return true;
        if(haystack.indexOf(" oky.")>-1) return true;
        if(haystack.indexOf(" k.")>-1) return true;
        if(haystack.indexOf(" kk.")>-1) return true;
        if(haystack.indexOf(" kay.")>-1) return true;
        if(haystack.indexOf(" mkay.")>-1) return true;
        
        if(haystack.indexOf(" OK.")>-1) return true;
        if(haystack.indexOf(" OKAY.")>-1) return true;
        if(haystack.indexOf(" OKE.")>-1) return true;
        if(haystack.indexOf(" OKY.")>-1) return true;
        if(haystack.indexOf(" K.")>-1) return true;
        if(haystack.indexOf(" KK.")>-1) return true;
        if(haystack.indexOf(" KAY.")>-1) return true;
        if(haystack.indexOf(" MKAY.")>-1) return true;
        
         if(haystack.indexOf(" ok?")>-1) return true;
        if(haystack.indexOf(" okay?")>-1) return true;
        if(haystack.indexOf(" oke?")>-1) return true;
        if(haystack.indexOf(" oky?")>-1) return true;
        if(haystack.indexOf(" k?")>-1) return true;
        if(haystack.indexOf(" kk?")>-1) return true;
        if(haystack.indexOf(" kay?")>-1) return true;
        if(haystack.indexOf(" mkay?")>-1) return true;
        
        if(haystack.indexOf(" OK?")>-1) return true;
        if(haystack.indexOf(" OKAY?")>-1) return true;
        if(haystack.indexOf(" OKE?")>-1) return true;
        if(haystack.indexOf(" OKY?")>-1) return true;
        if(haystack.indexOf(" K?")>-1) return true;
        if(haystack.indexOf(" KK?")>-1) return true;
        if(haystack.indexOf(" KAY?")>-1) return true;
        if(haystack.indexOf(" MKAY?")>-1) return true;
        
         if(haystack.indexOf(" ok!")>-1) return true;
        if(haystack.indexOf(" okay!")>-1) return true;
        if(haystack.indexOf(" oke!")>-1) return true;
        if(haystack.indexOf(" oky!")>-1) return true;
        if(haystack.indexOf(" k!")>-1) return true;
        if(haystack.indexOf(" kk!")>-1) return true;
        if(haystack.indexOf(" kay!")>-1) return true;
        if(haystack.indexOf(" mkay!")>-1) return true;
        
        if(haystack.indexOf(" OK!")>-1) return true;
        if(haystack.indexOf(" OKAY!")>-1) return true;
        if(haystack.indexOf(" OKE!")>-1) return true;
        if(haystack.indexOf(" OKY!")>-1) return true;
        if(haystack.indexOf(" K!")>-1) return true;
        if(haystack.indexOf(" KK!")>-1) return true;
        if(haystack.indexOf(" KAY!")>-1) return true;
        if(haystack.indexOf(" MKAY!")>-1) return true;
        
          if(haystack.indexOf(" ok,")>-1) return true;
        if(haystack.indexOf(" okay,")>-1) return true;
        if(haystack.indexOf(" oke,")>-1) return true;
        if(haystack.indexOf(" oky,")>-1) return true;
        if(haystack.indexOf(" k,")>-1) return true;
        if(haystack.indexOf(" kk,")>-1) return true;
        if(haystack.indexOf(" kay,")>-1) return true;
        if(haystack.indexOf(" mkay,")>-1) return true;
        
        if(haystack.indexOf(" OK,")>-1) return true;
        if(haystack.indexOf(" OKAY,")>-1) return true;
        if(haystack.indexOf(" OKE,")>-1) return true;
        if(haystack.indexOf(" OKY,")>-1) return true;
        if(haystack.indexOf(" K,")>-1) return true;
        if(haystack.indexOf(" KK,")>-1) return true;
        if(haystack.indexOf(" KAY,")>-1) return true;
        if(haystack.indexOf(" MKAY,")>-1) return true;
        
        
        
        System.err.println("NO_OK");
        Conversation.printWSln("Main", "false");
        return false;   
  
    }
    
    public static String removeOKString(String s){
        String padded = " "+s+" ";
        padded = padded.replace(" ok ", " ");
        padded = padded.replace(" okay ", " ");
        padded = padded.replace(" oke ", " ");
        padded = padded.replace(" oky ", " ");
        padded = padded.replace(" k ", " ");
        padded = padded.replace(" kk ", " ");
        padded = padded.replace(" kay ", " ");
        padded = padded.replace(" mkay ", " ");
        
        padded = padded.replace(" OK ", " ");
        padded = padded.replace(" OKAY ", " ");
        padded = padded.replace(" OKE ", " ");
        padded = padded.replace(" OKY ", " ");
        padded = padded.replace(" K ", " ");
        padded = padded.replace(" KK ", " ");
        padded = padded.replace(" KAY ", " ");
        
        
        padded = padded.replace(" ok.", " ");
        padded = padded.replace(" okay.", " ");
        padded = padded.replace(" oke.", " ");
        padded = padded.replace(" oky.", " ");
        padded = padded.replace(" k.", " ");
        padded = padded.replace(" kk.", " ");
        padded = padded.replace(" kay.", " ");
        padded = padded.replace(" mkay.", " ");
        
        padded = padded.replace(" OK.", " ");
        padded = padded.replace(" OKAY.", " ");
        padded = padded.replace(" OKE.", " ");
        padded = padded.replace(" OKY.", " ");
        padded = padded.replace(" K.", " ");
        padded = padded.replace(" KK.", " ");
        padded = padded.replace(" KAY.", " ");
        
        
        padded = padded.replace(" ok,", " ");
        padded = padded.replace(" okay,", " ");
        padded = padded.replace(" oke,", " ");
        padded = padded.replace(" oky,", " ");
        padded = padded.replace(" k,", " ");
        padded = padded.replace(" kk,", " ");
        padded = padded.replace(" kay,", " ");
        padded = padded.replace(" mkay,", " ");
        
        padded = padded.replace(" OK,", " ");
        padded = padded.replace(" OKAY,", " ");
        padded = padded.replace(" OKE,", " ");
        padded = padded.replace(" OKY,", " ");
        padded = padded.replace(" K,", " ");
        padded = padded.replace(" KK,", " ");
        padded = padded.replace(" KAY,", " ");
        
        
        padded = padded.replace(" ok?", " ");
        padded = padded.replace(" okay?", " ");
        padded = padded.replace(" oke?", " ");
        padded = padded.replace(" oky?", " ");
        padded = padded.replace(" k?", " ");
        padded = padded.replace(" kk?", " ");
        padded = padded.replace(" kay?", " ");
        padded = padded.replace(" mkay?", " ");
        
        padded = padded.replace(" OK?", " ");
        padded = padded.replace(" OKAY?", " ");
        padded = padded.replace(" OKE?", " ");
        padded = padded.replace(" OKY?", " ");
        padded = padded.replace(" K?", " ");
        padded = padded.replace(" KK?", " ");
        padded = padded.replace(" KAY?", " ");
        
        padded = padded.replace(" ok!", " ");
        padded = padded.replace(" okay!", " ");
        padded = padded.replace(" oke!", " ");
        padded = padded.replace(" oky!", " ");
        padded = padded.replace(" k!", " ");
        padded = padded.replace(" kk!", " ");
        padded = padded.replace(" kay!", " ");
        padded = padded.replace(" mkay!", " ");
        
        padded = padded.replace(" OK!", " ");
        padded = padded.replace(" OKAY!", " ");
        padded = padded.replace(" OKE!", " ");
        padded = padded.replace(" OKY!", " ");
        padded = padded.replace(" K!", " ");
        padded = padded.replace(" KK!", " ");
        padded = padded.replace(" KAY!", " ");
        
        
       
        
        System.err.println("MODIFYING PADDED "+padded);
        //System.exit(-4);
        return padded;
    }
    
    
}
