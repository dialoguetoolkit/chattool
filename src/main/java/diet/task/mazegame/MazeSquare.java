/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

/**
 *
 * @author user
 */
import java.io.Serializable;

public class MazeSquare
    implements Serializable {
  public MazeLink north;
  public MazeLink east;
  public MazeLink south;
  public MazeLink west;
  public int x = 0;
  public int y = 0;

  boolean isaSwitch;

  public MazeSquare(boolean isSwitch, int xpos, int ypos) {
    isaSwitch = isSwitch;
    x = xpos;
    y = ypos;
  }

  public boolean isSwitch() {
    if(isaSwitch)System.out.println("MazeSquare says it is a switch");
    if(!isaSwitch)System.out.println("MazeSquare says it is NOT a switch");
    return isaSwitch;
  }

  public MazeSquare growN(boolean isGate, boolean growthIsASwitch) {
    MazeSquare newone = new MazeSquare(growthIsASwitch, this.x, this.y - 1);
    MazeLink ml = new MazeLink(this, newone, isGate);
    this.north = ml;
    newone.south = ml;
    return newone;
  }

  public MazeSquare growE(boolean isGate, boolean growthIsASwitch) {
    MazeSquare newone = new MazeSquare(growthIsASwitch, this.x + 1, this.y);
    MazeLink ml = new MazeLink(this, newone, isGate);
    this.east = ml;
    newone.west = ml;
    return newone;
  }

  public MazeSquare growS(boolean isGate, boolean growthIsASwitch) {
    MazeSquare newone = new MazeSquare(growthIsASwitch, this.x, this.y + 1);
    MazeLink ml = new MazeLink(this, newone, isGate);
    this.south = ml;
    newone.north = ml;
    return newone;
  }

  public MazeSquare growW(boolean isGate, boolean growthIsASwitch) {
    MazeSquare newone = new MazeSquare(growthIsASwitch, this.x - 1, this.y);
    MazeLink ml = new MazeLink(this, newone, isGate);
    this.west = ml;
    newone.east = ml;
    return newone;
  }

  public void connectN(MazeSquare newone, boolean isGate, boolean growthIsASwitch) {
    MazeLink ml = new MazeLink(this, newone, isGate);
    this.north = ml;
    newone.south = ml;
  }

  public void connectE(MazeSquare newone,boolean isGate, boolean growthIsASwitch) {
    MazeLink ml = new MazeLink(this, newone, isGate);
    this.east = ml;
    newone.west = ml;
  }

  public void connectS(MazeSquare newone,boolean isGate, boolean growthIsASwitch) {
    MazeLink ml = new MazeLink(this, newone, isGate);
    this.south = ml;
    newone.north = ml;
  }

  public void connectW(MazeSquare newone,boolean isGate, boolean growthIsASwitch) {
    MazeLink ml = new MazeLink(this, newone, isGate);
    this.west = ml;
    newone.east = ml;
   }
   public String toString(){
      return ""+this.x+" , "+this.y;
   }


}
