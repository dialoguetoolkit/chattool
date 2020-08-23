/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client.ClientInterfaceEvents;

import diet.server.ConversationController.DefaultConversationController;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.json.Json;
import javax.json.JsonObjectBuilder;



/**
 *
 * @author gj
 */
public class ClientInterfaceEventStringPrettifier {
    
    public static JsonObjectBuilder getVerboseJSONDescription(ClientInterfaceEvent cie){
        
        
         JsonObjectBuilder cieJSON = Json.createObjectBuilder();
         try{ 
         
         cieJSON.add("type", cie.type);
         cieJSON.add("time", cie.clientTimeOfDisplay);
         cieJSON.add("id", cie.serverUniqueID);
           
        for(int i=0;i<cie.arguments.length;i++){
            Object aval = cie.arguments[i].getVal();
            if(aval instanceof Integer){
                 cieJSON.add(cie.arguments[i].id, (Integer)cie.arguments[i].getVal());
            }else if(aval instanceof String){
                  cieJSON.add(cie.arguments[i].id, (String)cie.arguments[i].getVal());
            }
            else if (aval instanceof Double){
                  cieJSON.add(cie.arguments[i].id, (Double)cie.arguments[i].getVal());
            }
            else if (aval instanceof Long){
                  cieJSON.add(cie.arguments[i].id, (Long)cie.arguments[i].getVal());
            }
            else if (aval instanceof Boolean){
                  cieJSON.add(cie.arguments[i].id, (Boolean)cie.arguments[i].getVal());
            }
            else{
                  cieJSON.add(cie.arguments[i].id, cie.arguments[i].getValAsString());
            }
            
            
           
            
        }   
            
         }catch (Exception e){
             return cieJSON;
         }
         return cieJSON;
        
    }
    
    
    
    
    
    public static String getTextFormulationProcessRepresentation(Vector clientinterfaceevents){
        
        String sValue = "";
        try{
        
        long startTime = -999999999;
        if(clientinterfaceevents.size()>0){
            ClientInterfaceEvent cie0 =(ClientInterfaceEvent) clientinterfaceevents.elementAt(0);
            startTime = cie0.getClientTimeOfDisplay();
        }
        else{
            return "";
        }
        
     
        
        
       
        long previousDisplayTime = -99999999;
        boolean hasfoundfirstevent = false;
        
        
        
        
        for(int i=0;i<clientinterfaceevents.size();i++){
            
            
            ClientInterfaceEvent cie =(ClientInterfaceEvent) clientinterfaceevents.elementAt(i);
           
             
            if(cie.getType().equalsIgnoreCase("tefr")){
                long timesinceprevious =0;
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                
                
                int length = (int) cie.getValue("length");
                
                if(length!=0){
                    sValue =  sValue + ""+timesinceprevious+")"    + '\u02FF'+(String)cie.getValue("text");
                }
            }
            else if(cie.getType().equalsIgnoreCase("termve")){
                long timesinceprevious =0;
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                int length = (int) cie.getValue("length");
                if(length!=0){
                    sValue = sValue + " "+superscript(""+timesinceprevious +"") + '\u02FF'+(String)cie.getValue("textbeingdeleted");
                }
            }
            else if(cie.getType().equalsIgnoreCase("tefi")){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                
                
                sValue = sValue + " "+superscript(""+timesinceprevious +"")+cie.getValue("text");
            }
             else if(cie.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                
                
                sValue = sValue + " "+superscript(""+timesinceprevious +"")+cie.getValue("text");
            }
             else if(cie.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                
                
                sValue = sValue + " "+superscript(""+timesinceprevious +"")+cie.getValue("text");
            }
            
            
            
             else if(cie.getType().equalsIgnoreCase("chwappbyo")){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
               
                
                sValue = sValue + " "+superscript(""+timesinceprevious +"")  + '\u3010'+cie.getValue("text")+ '\u3011';
            }
              else if(cie.type.equalsIgnoreCase("stimulusimage_change_confirm")){
                long timesinceprevious =0;
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                hasfoundfirstevent = true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                sValue = sValue + " "+ superscript(""+timesinceprevious +"") +"[["+ cie.getValue("name") +"]]";
                
            }
             
             
             
             else if(cie.isStartOfIsTyping()){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                sValue = sValue + " "+superscript(""+timesinceprevious +"")  + DefaultConversationController.sett.recordeddata_startOfIsTypingByOther;
            }
             else if(cie.isEndOfIsTyping()){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                sValue = sValue + " "+superscript(""+timesinceprevious +"")  + DefaultConversationController.sett.recordeddata_endOfIsTypingByOther;
            }
            else if(cie.type.equalsIgnoreCase(ClientInterfaceEventTracker.mazegameOpenGates)){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                sValue = sValue + " "+superscript(""+timesinceprevious +"")  + DefaultConversationController.sett.recordeddata_mazegameOpenGates;
            }
            else if(cie.type.equalsIgnoreCase(ClientInterfaceEventTracker.mazegameCloseGates)){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                sValue = sValue + " "+superscript(""+timesinceprevious +"")  + DefaultConversationController.sett.recordeddata_mazegameCloseGates;
            }
           
            
            
            else{
                System.err.println("PRETTIFYING:"+cie.getType());
            }
            
            
            
           
           // sValue =  cie.type+","+    sValue + "---"+timesinceprevious;
        
        }
        
        
        }catch (Exception e){
            e.printStackTrace();
            sValue="ERROR"+sValue+"ERROR";
        }
        return sValue.replace("\n", "ENTER");
    }
    
    
    
    public static String getTextFormulationProcessRepresentationLOGARHYTHMICSPACE(Vector clientinterfaceevents){
       
      
        
        String sValue = "";
        try{
        
        long startTime = -999999999;
        if(clientinterfaceevents.size()>0){
            ClientInterfaceEvent cie0 =(ClientInterfaceEvent) clientinterfaceevents.elementAt(0);
            startTime = cie0.getClientTimeOfDisplay();
        }
        else{
            return "";
        }
        
     
        
        
       
        long previousDisplayTime = -99999999;
        boolean hasfoundfirstevent = false;
        
        
        
        
        for(int i=0;i<clientinterfaceevents.size();i++){
            
            
            ClientInterfaceEvent cie =(ClientInterfaceEvent) clientinterfaceevents.elementAt(i);
           
           
             
            if(cie.getType().equalsIgnoreCase("tefr")){
                long timesinceprevious =0;
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                
                
                int length = (int) cie.getValue("length");
                
                if(length!=0){
                    sValue =  sValue + ""+timesinceprevious+")"    + '\u02FF'+((String)cie.getValue("text"));
                }
            }
            else if(cie.getType().equalsIgnoreCase("termve")){
                long timesinceprevious =0;
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                int length = (int) cie.getValue("length");
                
                
                if(length!=0){
                    sValue = sValue + createLogBlankspace(timesinceprevious)  + '\u02FF'+((String)cie.getValue("textbeingdeleted"));
                }
            }
            else if(cie.getType().equalsIgnoreCase("tefi")){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                
                
                sValue = sValue + createLogBlankspace(timesinceprevious) +((String)cie.getValue("text"))  ;
            }
            else if(cie.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyself)){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                
                
                sValue = sValue + createLogBlankspace(timesinceprevious) +((String)cie.getValue("text"));
            }
            else if(cie.getType().equalsIgnoreCase(ClientInterfaceEventTracker.wysiwyg_appendbyother)){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                
                
                sValue = sValue + createLogBlankspace(timesinceprevious) +((String)cie.getValue("text"));
            }
            
            
            
            
            
            
            
            
            
             else if(cie.getType().equalsIgnoreCase("chwappbyo")){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
               
                
                sValue = sValue + createLogBlankspace(timesinceprevious)  + '\u3010'+cie.getValue("text")+ '\u3011';
            }
            else if(cie.type.equalsIgnoreCase("stimulusimage_change_confirm")){
                long timesinceprevious =0;
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                hasfoundfirstevent = true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                sValue = sValue +createLogBlankspace(timesinceprevious) +"[["+ cie.getValue("name") +"]]";
                
            }
             
             
             
             else if(cie.isStartOfIsTyping()){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                sValue = sValue + createLogBlankspace(timesinceprevious)  + DefaultConversationController.sett.recordeddata_startOfIsTypingByOther;
            }
             else if(cie.isEndOfIsTyping()){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                sValue = sValue + createLogBlankspace(timesinceprevious)  + DefaultConversationController.sett.recordeddata_endOfIsTypingByOther;
            }
            else if(cie.type.equalsIgnoreCase(ClientInterfaceEventTracker.mazegameOpenGates)){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                sValue = sValue + createLogBlankspace(timesinceprevious)   + DefaultConversationController.sett.recordeddata_mazegameOpenGates;
            }
            else if(cie.type.equalsIgnoreCase(ClientInterfaceEventTracker.mazegameCloseGates)){
                long timesinceprevious =0;
               
                if(hasfoundfirstevent){
                     timesinceprevious = cie.getClientTimeOfDisplay()-previousDisplayTime;
                }
                 hasfoundfirstevent=true;
                previousDisplayTime= cie.getClientTimeOfDisplay();
                sValue = sValue + createLogBlankspace(timesinceprevious)   + DefaultConversationController.sett.recordeddata_mazegameCloseGates;
            }
            
            else{
                System.err.println("PRETTIFYING:"+cie.getType());
            }
            
            
            
           
           // sValue =  cie.type+","+    sValue + "---"+timesinceprevious;
        
        }
        
        
        }catch (Exception e){
            e.printStackTrace();
            sValue="ERROR"+sValue+"ERROR";
        }
        return sValue.replace("\n", "");
    }
    
    
    public static String createLogBlankspace(long milliseconds){
        double seconds = milliseconds/1000;
        if(seconds <1) return "";
        String returnvalue =""+'\u2585';
        int durationlog= (int)       (Math.log(seconds)/Math.log(2));
        for(int i=0;i<durationlog;i++){
            returnvalue= returnvalue+'\u2585';
        }
        System.err.println("RETURNVALUE: "+returnvalue+ durationlog);
        //System.exit(-5);
        return returnvalue;
        
    }
    
    
    
  public static String subscript(String str) {
    str = str.replaceAll("0", ""+'\u2080');
    str = str.replaceAll("1", ""+'\u2081');
    str = str.replaceAll("2", ""+'\u2082');
    str = str.replaceAll("3", ""+'\u2083');
    str = str.replaceAll("4", ""+'\u2084');
    str = str.replaceAll("5", ""+'\u2085');
    str = str.replaceAll("6", ""+'\u2086');
    str = str.replaceAll("7", ""+'\u2087');
    str = str.replaceAll("8", ""+'\u2088');
    str = str.replaceAll("9", ""+'\u2089');
    return str;
 }
   
  public static String superscript(String str) {
    str = str.replaceAll("0", "⁰");
    str = str.replaceAll("1", "¹");
    str = str.replaceAll("2", "²");
    str = str.replaceAll("3", "³");
    str = str.replaceAll("4", "⁴");
    str = str.replaceAll("5", "⁵");
    str = str.replaceAll("6", "⁶");
    str = str.replaceAll("7", "⁷");
    str = str.replaceAll("8", "⁸");
    str = str.replaceAll("9", "⁹");         
    return str;
}
    
    
    public static String convertToDeletedString(String s){
        
        if(2<5) return   Charset.forName("UTF-8").encode(   "a" + "\u0300").toString();
        String newstring ="";
        for(int i=0;i<s.length();i++){
           char c = s.charAt(i);
           
          
           
           newstring = newstring + '\u02FF' + c;
       }
       return newstring;
    }
    
    
    //public static String 
     //    »
    //Ð
    
    //U+02FF	˿	cb bf	MODIFIER LETTER LOW LEFT ARROW
    //U+0354	͔	cd 94	COMBINING LEFT ARROWHEAD BELOW           
}
