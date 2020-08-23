/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;


 import java.io.Serializable;
import java.util.Vector;

public class Game
    implements Serializable {
  int minsPerMaze;
  int lengthExperiment;
  Vector mazes = new Vector();
  String clientName;
  Maze currentMaze;

  public Game(Vector v) {
    lengthExperiment = 0;
    minsPerMaze = 0;
    mazes = v;
    clientName = "Not set yet";
    currentMaze = (Maze) mazes.elementAt(0);
  }

  public Game() {
    Vector v1 = new Vector();
    v1.addElement(new Maze(1));
    v1.addElement(new Maze(2));//
    //v1.addElement(new Maze(3));//
    //v1.addElement(new Maze(4));//
    mazes = v1;
    currentMaze = (Maze) v1.elementAt(0);

  }

  public Game(String n) {

    Vector v1 = new Vector();
    v1.addElement(new Maze(1));
    v1.addElement(new Maze(2));
    v1.addElement(new Maze(3));
    v1.addElement(new Maze(4));
    mazes = v1;
    currentMaze = (Maze) v1.elementAt(0);

  }

  public Maze getMazeNo(int i) {
    return (Maze) mazes.elementAt(i);
  }

  public Vector getAllMazes() {
    return mazes;
  }

  public Maze getCurrentMaze() {
    return currentMaze;
  }

  public Maze nextMaze() {
    int pos = mazes.indexOf(currentMaze);
    currentMaze = (Maze) mazes.elementAt(pos + 1);
    return currentMaze;
  }

}
