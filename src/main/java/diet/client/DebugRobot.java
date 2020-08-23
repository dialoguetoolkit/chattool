/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.AWTException;

public class DebugRobot extends Thread{

  private Robot robot;

  public DebugRobot()  {
      try{
        this.robot = new Robot();
      }catch (Exception e){
          e.printStackTrace();
      }
  }
  
  boolean doFakeActivity = false;
  
  long fakeactioncount =0;
  
  public void startFakeKeyboardActivity(){
      this.doFakeActivity = true;
      Thread t = new Thread(){
          public void run(){
              while(doFakeActivity){
                  type("This is fake text ");
                  fakeactioncount=fakeactioncount+1;
                  int keyCode = KeyEvent.VK_ENTER   ; // the A key
                  robot.keyPress(keyCode);
                  robot.keyRelease(keyCode);
                  
                   
                  
                  System.err.println("WAKING UP");
                  try{
                      Thread.sleep(400);
                  }catch(Exception e){
                      e.printStackTrace();
                  }
              }
          }
      };
      t.start();
  }
  
  public void stopFakeKeyboardActivity(){
      this.doFakeActivity=false;
  }
  
  
  

  public DebugRobot(Robot robot) {
      this.robot = robot;
  }

  public void type(CharSequence characters) {
      int length = characters.length();
      for (int i = 0; i < length; i++) {
          char character = characters.charAt(i);
          type(character);
      }
  }

  public void type(char character) {
      switch (character) {
      
      case 'a': doType(KeyEvent.VK_A); break;
      case 'b': doType(KeyEvent.VK_B); break;
      case 'c': doType(KeyEvent.VK_C); break;
      case 'd': doType(KeyEvent.VK_D); break;
      case 'e': doType(KeyEvent.VK_E); break;
      case 'f': doType(KeyEvent.VK_F); break;
      case 'g': doType(KeyEvent.VK_G); break;
      case 'h': doType(KeyEvent.VK_H); break;
      case 'i': doType(KeyEvent.VK_I); break;
      case 'j': doType(KeyEvent.VK_J); break;
      case 'k': doType(KeyEvent.VK_K); break;
      case 'l': doType(KeyEvent.VK_L); break;
      case 'm': doType(KeyEvent.VK_M); break;
      case 'n': doType(KeyEvent.VK_N); break;
      case 'o': doType(KeyEvent.VK_O); break;
      case 'p': doType(KeyEvent.VK_P); break;
      case 'q': doType(KeyEvent.VK_Q); break;
      case 'r': doType(KeyEvent.VK_R); break;
      case 's': doType(KeyEvent.VK_S); break;
      case 't': doType(KeyEvent.VK_T); break;
      case 'u': doType(KeyEvent.VK_U); break;
      case 'v': doType(KeyEvent.VK_V); break;
      case 'w': doType(KeyEvent.VK_W); break;
      case 'x': doType(KeyEvent.VK_X); break;
      case 'y': doType(KeyEvent.VK_Y); break;
      case 'z': doType(KeyEvent.VK_Z); break;
      case 'A': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_A); break;
      case 'B': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_B); break;
      case 'C': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_C); break;
      case 'D': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_D); break;
      case 'E': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_E); break;
      case 'F': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_F); break;
      case 'G': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_G); break;
      case 'H': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_H); break;
      case 'I': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_I); break;
      case 'J': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_J); break;
      case 'K': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_K); break;
      case 'L': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_L); break;
      case 'M': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_M); break;
      case 'N': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_N); break;
      case 'O': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_O); break;
      case 'P': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_P); break;
      case 'Q': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Q); break;
      case 'R': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_R); break;
      case 'S': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_S); break;
      case 'T': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_T); break;
      case 'U': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_U); break;
      case 'V': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_V); break;
      case 'W': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_W); break;
      case 'X': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_X); break;
      case 'Y': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Y); break;
      case 'Z': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Z); break;
      case '!': doType(KeyEvent.VK_EXCLAMATION_MARK); break;
      case ' ': doType(KeyEvent.VK_SPACE); break;   
      default:
          throw new IllegalArgumentException("Cannot type character " + character);
      }
  }

  private void doType(int... keyCodes) {
      doType(keyCodes, 0, keyCodes.length);
  }

  private void doType(int[] keyCodes, int offset, int length) {
      if (length == 0) {
          return;
      }

      robot.keyPress(keyCodes[offset]);
      doType(keyCodes, offset + 1, length - 1);
      robot.keyRelease(keyCodes[offset]);
  }

 }