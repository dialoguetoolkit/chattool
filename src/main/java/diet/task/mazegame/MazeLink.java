/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

import java.io.Serializable;

public class MazeLink
    implements Serializable {
  MazeSquare firstSquare;
  MazeSquare secondSquare;
  boolean isagate = false;

  public MazeLink(MazeSquare mz1, MazeSquare mz2, boolean isgate) {
    isagate = isgate;
    firstSquare = mz1;
    secondSquare = mz2;
  }

  public MazeSquare getLinkedSquare(MazeSquare ms) {
    if (ms == firstSquare) {
      return secondSquare;
    }
    else {
      return firstSquare;
    }

  }
  public void setIsAGate(boolean gateStatus){
    isagate = gateStatus;
  }

}
