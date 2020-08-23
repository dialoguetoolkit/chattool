/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Date;

/**
 *
 * @author gj
 */
public class DebugRobotNew extends Thread{
    
    Robot r;
    
    public DebugRobotNew(){
        try{
            r = new Robot();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
  
    public void run(){
        int keyCode = KeyEvent.getExtendedKeyCodeForChar('a');
    }
    
    
    public static void startDebug(Object value, Object value2){
         scenario1((long)value2);
    }
    
    
    public static void scenario1(long duration){
        
        final long timeToStop = new Date().getTime()+duration;
        Thread t = new Thread(){
            public void run(){
                try{
                    System.err.println("Robot - starting");
                    Robot r = new Robot();
                    System.err.println("Robot - starting 2");
                    while(new Date().getTime()<timeToStop){
                         type("hello", r);
                    }
                    
                    
                }catch (Exception e){
                    e.printStackTrace();
                }
                 System.err.println("Robot - starting 3");
            }
        };
        t.start();
    }
    
    
    
    
  private static void type(String s, Robot r)
  {
    byte[] bytes = s.getBytes();
    for (byte b : bytes)
    {
      int code = b;
      // keycode only handles [A-Z] (which is ASCII decimal [65-90])
      if (code > 96 && code < 123) code = code - 32;
      r.delay(25);
      r.keyPress(code);
      r.keyRelease(code);
    }
  }
    
    private void type(int i)
  {
    r.delay(25);
    r.keyPress(i);
    r.keyRelease(i);
  }
    
    
}
