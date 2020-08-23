/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

/**
 *
 * @author user
 */
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

public class Maze implements Serializable {
  //Vector links = new Vector();
  Vector squares = new Vector();
  MazeSquare begin;
  public MazeSquare finish;
  MazeSquare current;
  boolean gatesOpen = false;
  public String id ="";
  Random r = new Random(new Date().getTime());

public Vector getSquares(){
  return squares;
}


public boolean squareExists(Dimension d){
  for (int i = 0; i < squares.size(); i++) {
    MazeSquare sqr = (MazeSquare) squares.elementAt(i);
    if (sqr.x == d.width && sqr.y == d.height) {
        return true;
    }
  }
  return false;
}
public void toggleSwitch(Dimension d){
  for (int i = 0; i < squares.size(); i++) {
    MazeSquare sqr = (MazeSquare) squares.elementAt(i);
    if (sqr.x == d.width && sqr.y == d.height) {
        sqr.isaSwitch=!sqr.isaSwitch;
        return;
    }
  }


}


public void toggleLink(Dimension d1, Dimension d2){
  for (int i = 0; i < squares.size(); i++) {
    MazeSquare sqr = (MazeSquare) squares.elementAt(i);
    if (sqr.x == d1.width && sqr.y == d1.height) {
      for (int j = 0; j < squares.size(); j++) {
          MazeSquare sqr2 = (MazeSquare) squares.elementAt(j);
          if (sqr2.x == d2.width && sqr2.y == d2.height) {
              //are matched
              if(sqr.north!=null && sqr.north.getLinkedSquare(sqr).equals(sqr2))sqr.north.isagate=!sqr.north.isagate;
              if(sqr.east!=null && sqr.east.getLinkedSquare(sqr).equals(sqr2))sqr.east.isagate=!sqr.east.isagate;
              if(sqr.south!=null && sqr.south.getLinkedSquare(sqr).equals(sqr2))sqr.south.isagate=!sqr.south.isagate;
              if(sqr.west!=null && sqr.west.getLinkedSquare(sqr).equals(sqr2))sqr.west.isagate=!sqr.west.isagate;
              return;
          }


      }


    }

  }




}

public boolean isLinkedNOTSUREIFITWORKS(Dimension d1 , Dimension d2){

  for (int i = 0; i < squares.size(); i++) {
    MazeSquare sqr = (MazeSquare) squares.elementAt(i);
    if (sqr.x == d1.width && sqr.y == d1.height) {
      for (int j = 0; j < squares.size(); j++) {
          MazeSquare sqr2 = (MazeSquare) squares.elementAt(j);
          if (sqr2.x == d2.width && sqr2.y == d2.height) {
              //are matched
              if(sqr.north!=null && sqr.north.getLinkedSquare(sqr).equals(sqr2))return true;
              if(sqr.east!=null && sqr.east.getLinkedSquare(sqr).equals(sqr2))return true;
              if(sqr.south!=null && sqr.south.getLinkedSquare(sqr).equals(sqr2))return true;
              if(sqr.west!=null && sqr.west.getLinkedSquare(sqr).equals(sqr2))return true;
          }

      }


    }

  }
  return false;


}


public Vector getLinks(){
  Vector links = new Vector();
  for(int i=0;i<squares.size();i++){
    MazeSquare ms = (MazeSquare) squares.elementAt(i);
    if(ms.north!=null && !links.contains(ms.north))links.addElement(ms.north);
    if(ms.east!=null && !links.contains(ms.east))links.addElement(ms.east);
    if(ms.south!=null && !links.contains(ms.south))links.addElement(ms.south);
    if(ms.west!=null && !links.contains(ms.west))links.addElement(ms.west);
  }
  return links;
}


 public void changeStartPosTo(Dimension d){
   for (int i = 0; i < squares.size(); i++) {
     //System.out.println("CHANGING GATES");
     MazeSquare sqr = (MazeSquare) squares.elementAt(i);
     if (sqr.x == d.width && sqr.y == d.height) {
            begin =sqr;
     }
     sqr.x=sqr.x - d.width;
     sqr.y=sqr.y - d.height;


   }
   current = begin;
 }

 public void selectRandomStartingPosition(){
   boolean chosen = false;
   while(!chosen){
     int chooser = r.nextInt(squares.size());
     MazeSquare ms = (MazeSquare)squares.elementAt(chooser);
     if (!ms.equals(finish)){
       begin = ms;
       chosen = true;
       changeStartPosTo(new Dimension(ms.x,ms.y));
     }
   }




 }
 public void selectRandomFinishingPosition(){
  boolean chosen = false;
  while(!chosen){
    int chooser = r.nextInt(squares.size());
    MazeSquare ms = (MazeSquare)squares.elementAt(chooser);
    if (!ms.equals(begin)){
      finish = ms;
      chosen = true;
    }
  }


}



 public void changeFinishPosTo(Dimension d){
   for (int i = 0; i < squares.size(); i++) {
     MazeSquare sqr = (MazeSquare) squares.elementAt(i);
     if (sqr.x == d.width && sqr.y == d.height) {
       finish =sqr;
     }
   }
 }



 public Maze getClone(){
    Maze m = new Maze(0);
    try {
       File f = new File("swapfile");
       if(f.exists())f.delete();
       ObjectOutputStream oOut = new ObjectOutputStream(new FileOutputStream ("swapfile"));
       oOut.writeObject(this);
       oOut.flush();
       oOut.close();
       ObjectInputStream oInp = new ObjectInputStream(new FileInputStream("swapfile"));
       m = (Maze)oInp.readObject();


    } catch(Exception e) {System.out.println("cloning doesn't seem to work " + e.getMessage());}
    return m;
 }

  public String toText() {
    String text = "";
    for (int i = 0; i < squares.size(); i++) {
      MazeSquare mzsq = (MazeSquare) squares.elementAt(i);
      text = text + "[ " + mzsq.x + " " + mzsq.y + " ]";
      if(mzsq==begin)text = text + "BEGIN";
      if(mzsq==finish)text = text + "FINISH";
      if (mzsq.isaSwitch) {
        text = text + "SWITCH \n";
      }
      if (mzsq.north != null) {
        text = text + "Link North ";
        if (mzsq.north.isagate) {
          text = text + "is a gate. ";
        }
      }
      if (mzsq.east != null) {
        text = text + "Link East ";
        if (mzsq.east.isagate) {
          text = text + "is a gate. ";
        }

      }
      if (mzsq.south != null) {
        text = text + "Link South ";
        if (mzsq.south.isagate) {
          text = text + "is a gate. ";
        }

      }
      if (mzsq.west != null) {
        text = text + "Link West ";
        if (mzsq.west.isagate) {
          text = text + "is a gate. ";
        }

      }
      text = text + "\n";

    }
    //System.out.println("MAZETEXT IS : "+text);
    return text;

  }
 public MazeSquare getSquare(Dimension d){
   //Looks for it in squares.
   //f it is there, returns it.If not doesn't
   for (int i = 0; i < squares.size(); i++) {
     MazeSquare sqr = (MazeSquare) squares.elementAt(i);
     if (sqr.x == d.width && sqr.y == d.height) {
       return sqr;
     }
   }
   return null;
 }

  public void constructRandomly(int x, int y,double duplicateprob){
 //GOT TO CHECK IF IS ALREADY CONNECTED
 //GOT TO CHECK TO SEE IF GROWTH RESULTS IN MAZE BEING TOO BIG
 //GOT TO CHECK THAT WHEN YOU GROW, EVEN WHEN THE LINK IS NULL , THE NEW MAZESQUARE MIGHT ALREADY BE THERE ONLY NOT CONNECTED
  System.out.println("STARTING");
  MazeSquare temp;
  while (this.getBounds().width<=x || this.getBounds().height<=y){
    System.out.println("WHILELOOP");
    int squarechooser = r.nextInt(squares.size());
    temp=(MazeSquare)squares.elementAt(squarechooser);
    int slength = r.nextInt((int) (x+y)/2);
    MazeSquare temp1=temp;
    for(int i = 0 ; i< slength; i++){
      //try{int yz = System.in.read();} catch (Exception e){}

      int s = r.nextInt(4) ;
      if(s==0 && temp.north==null && this.getBounds().height<=y ){
        MazeSquare mz = getSquare(new Dimension(temp.x,temp.y-1));
        if(mz==null){
          temp1 = temp.growN(false, false);
          squares.addElement(temp1);
          System.out.println("ADDING NORTH : FROM " + temp.x+" "+temp.y+" to "+ temp1.x+ " "+temp1.y+" ");
        }
        else{
         double isOK = r.nextDouble();
         if(isOK<duplicateprob){
           MazeLink mzl = new MazeLink(mz,temp,false);
           mz.south=mzl;
           temp.north = mzl;
           temp1 = mz;

         }
        }

      }
     else if(s==1&&temp.east==null&& this.getBounds().width<=x){
        MazeSquare mz = getSquare(new Dimension(temp.x+1,temp.y));
        if(mz==null){
           temp1 = temp.growE(false, false);
           squares.addElement(temp1);
           System.out.println("ADDING east : FROM " + temp.x+" "+temp.y+" to "+ temp1.x+ " "+temp1.y+" ");
        }
        else{
          double isOK = r.nextDouble();
          if(isOK<duplicateprob){
           MazeLink mzl = new MazeLink(mz,temp,false);
           mz.west=mzl;
           temp.east = mzl;
           temp1 = mz;
          }
       }

      }
      else if(s==2&&temp.south==null && this.getBounds().height<=y){
        MazeSquare mz = getSquare(new Dimension(temp.x,temp.y+1));
        if(mz==null){
           temp1 = temp.growS(false, false);
           squares.addElement(temp1);
           System.out.println("ADDING south : FROM " + temp.x+" "+temp.y+" to "+ temp1.x+ " "+temp1.y+" ");
        }
        else{
          double isOK = r.nextDouble();
          if(isOK<duplicateprob){
            MazeLink mzl = new MazeLink(mz, temp, false);
            mz.north = mzl;
            temp.south = mzl;
            temp1 = mz;
          }
        }

      }
      else if(s==3&&temp.west==null && this.getBounds().width<=x){
        MazeSquare mz = getSquare(new Dimension(temp.x-1,temp.y));
     if(mz==null){
        temp1 = temp.growW(false, false);
        squares.addElement(temp1);
        System.out.println("ADDING west : FROM " + temp.x+" "+temp.y+" to "+ temp1.x+ " "+temp1.y+" ");
     }
     else{
       double isOK = r.nextDouble();
       if(isOK<duplicateprob){
        MazeLink mzl = new MazeLink(mz,temp,false);
        mz.east=mzl;
        temp.west = mzl;
        temp1 = mz;
       }
     }

   }
   System.out.println("TEMP + TEEMP1");
   temp=temp1;
      }
     }
  }

  public void construct(int n) {
    MazeSquare temp, temp1, temp2, temp3;

    if (n == 0) {

    }

    else if (n == 1) {
      temp = begin.growN(false, false);
      squares.addElement(temp);
      temp1 = temp.growN(false, false);
      squares.addElement(temp1);
      temp = temp1.growN(false, false);
      squares.addElement(temp);
      finish = temp;
      temp = temp.growN(false, false);
      squares.addElement(temp);
      temp2 = temp.growW(false, false);
      squares.addElement(temp2);
      temp = temp.growE(false, true);
      squares.addElement(temp);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);
      temp1 = temp1.growE(true, false);
      squares.addElement(temp1);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);
      temp2 = temp1.growE(true, false);
      squares.addElement(temp2);
      temp3 = temp2.growN(false, false);
      squares.addElement(temp3);
      temp1 = temp1.growS(false, false);
      squares.addElement(temp1);
      temp1 = temp1.growS(false, true);
      squares.addElement(temp1);
      temp = temp1.growW(true, false);
      squares.addElement(temp);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);

    }
    else if (n == 2) {
      temp = begin.growN(false, false);
      squares.addElement(temp);
      temp1 = temp.growN(false, false);
      finish = temp;
      squares.addElement(temp1);
      temp = temp1.growN(false, false);
      squares.addElement(temp);
      temp = temp.growN(false, false);
      squares.addElement(temp);
      temp2 = temp.growW(false, false);
      squares.addElement(temp2);
      temp = temp.growE(false, true);
      squares.addElement(temp);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);
      temp1 = temp1.growE(true, false);
      squares.addElement(temp1);
      temp2 = temp1.growE(true, false);
      squares.addElement(temp2);
      temp3 = temp2.growN(false, false);
      squares.addElement(temp3);
      temp1 = temp1.growS(true, false);
      squares.addElement(temp1);
      temp1 = temp1.growS(false, false);
      squares.addElement(temp1);
      temp = temp1.growW(true, true);
      squares.addElement(temp);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);

    }
    else if (n == 3) {

      temp = begin.growN(false, false);
      squares.addElement(temp);
      temp1 = temp.growN(false, false);
      squares.addElement(temp1);
      temp = temp1.growN(false, false);
      squares.addElement(temp);
      temp = temp.growN(false, false);
      squares.addElement(temp);
      temp2 = temp.growW(false, false);
      finish = temp2;
      squares.addElement(temp2);
      temp = temp.growE(false, true);
      squares.addElement(temp);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);
      temp1 = temp1.growE(true, false);
      squares.addElement(temp1);
      temp2 = temp1.growE(true, false);
      squares.addElement(temp2);
      temp3 = temp2.growN(false, false);
      squares.addElement(temp3);
      temp1 = temp1.growS(true, false);
      squares.addElement(temp1);
      temp1 = temp1.growS(false, false);
      squares.addElement(temp1);
      temp = temp1.growW(true, true);
      squares.addElement(temp);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);
    }
    else if (n == 4) {
      temp = begin.growN(false, false);
      squares.addElement(temp);
      temp1 = temp.growN(false, false);
      squares.addElement(temp1);
      temp = temp1.growN(false, false);
      finish = temp;
      squares.addElement(temp);
      temp = temp.growN(false, false);
      squares.addElement(temp);
      temp2 = temp.growW(false, false);
      squares.addElement(temp2);
      temp = temp.growE(false, true);
      squares.addElement(temp);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);
      temp1 = temp1.growE(true, false);
      squares.addElement(temp1);
      temp2 = temp1.growE(true, false);
      squares.addElement(temp2);
      temp3 = temp2.growN(false, false);
      squares.addElement(temp3);
      temp1 = temp1.growS(true, false);
      squares.addElement(temp1);
      temp1 = temp1.growS(false, false);
      squares.addElement(temp1);
      temp = temp1.growW(true, true);
      squares.addElement(temp);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);

    }
    else {
      temp = begin.growN(false, false);
      squares.addElement(temp);
      temp1 = temp.growN(false, false);
      squares.addElement(temp1);
      temp = temp1.growN(false, false);
      squares.addElement(temp);
      temp = temp.growN(false, false);
      squares.addElement(temp);
      finish = temp;
      temp2 = temp.growW(true, false);
      squares.addElement(temp2);
      temp = temp.growE(true, true);
      squares.addElement(temp);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);
      temp1 = temp1.growE(true, false);
      squares.addElement(temp1);
      temp1 = temp1.growE(true, false);
      squares.addElement(temp1);
      temp2 = temp1.growE(true, false);
      squares.addElement(temp2);
      temp3 = temp2.growN(false, false);
      squares.addElement(temp3);
      temp1 = temp1.growS(true, false);
      squares.addElement(temp1);
      temp1 = temp1.growS(false, false);
      squares.addElement(temp1);
      temp = temp1.growW(true, true);
      squares.addElement(temp);
      temp1 = temp1.growE(false, false);
      squares.addElement(temp1);

    }

  }

  public boolean isCompleted() {
    if (current == finish) {
      return true;
    }
    return false;

  }

  public Maze(int number) {
    MazeSquare ms = new MazeSquare(false, 0, 0);
    begin = ms;
    squares.addElement(ms);
    current = begin;
    construct(number);
  }

  public Maze(int x,int y,double duplicateprob, int gates) {
  MazeSquare ms = new MazeSquare(false, 0, 0);
  begin = ms;
  squares.addElement(ms);
  constructRandomly(x,y,duplicateprob);
  constructRandomGates(gates);
  current = begin;
}



public void constructRandomGates(int noOfGates){
   Vector links = new Vector();
   for(int i=0;i<squares.size();i++){
      MazeSquare ms = (MazeSquare) squares.elementAt(i);
      if(ms.north!=null && !links.contains(ms.north))links.addElement(ms.north);
      if(ms.east!=null && !links.contains(ms.east))links.addElement(ms.east);
      if(ms.south!=null && !links.contains(ms.south))links.addElement(ms.south);
      if(ms.west!=null && !links.contains(ms.west))links.addElement(ms.west);
      System.out.println("MAKING GATE "+links.size());
   }

   boolean finished = false;
   while(!finished){
      int seed = r.nextInt(links.size());
      MazeLink ml = (MazeLink)links.elementAt(seed);
      if(!ml.isagate){
         ml.setIsAGate(true);
         noOfGates=noOfGates-1;
         System.out.println("MAKING GATE "+noOfGates);
         if(noOfGates<=0)finished = true;
      }




   }

}


  public Dimension getBounds() {
    Dimension max = getMaxBounds();
    Dimension min = getMinBounds();
    return new Dimension(1 + max.width - min.width, 1 + max.height - min.height);

  }

  public Dimension getMaxBounds() {
    int maxHeight = 0;
    int maxWidth = 0;
    for (int i = 0; i < squares.size(); i++) {
      MazeSquare ms = (MazeSquare) squares.elementAt(i);
      if (ms.x > maxWidth) {
        maxWidth = ms.x;
      }
      if (ms.y > maxHeight) {
        maxHeight = ms.y;
      }
    }
    //System.out.println(maxHeight);
    //System.out.println(maxWidth);
    return new Dimension(maxWidth, maxHeight);
  }

  public Dimension getMinBounds() {
    int minHeight = 0;
    int minWidth = 0;
    for (int i = 0; i < squares.size(); i++) {
      MazeSquare ms = (MazeSquare) squares.elementAt(i);
      if (ms.x < minWidth) {
        minWidth = ms.x;
      }
      if (ms.y < minHeight) {
        minHeight = ms.y;
      }
    }
    //System.out.println(minHeight);
    //System.out.println(minWidth);
    return new Dimension(minWidth, minHeight);
  }

  public void moveNorth() {
    current = current.north.getLinkedSquare(current);
  }

  public void moveEast() {
    current = current.east.getLinkedSquare(current);
  }

  public void moveSouth() {
    current = current.south.getLinkedSquare(current);
  }

  public void moveWest() {
     current = current.west.getLinkedSquare(current);
  }

  public void changeGates(boolean gs) {
    gatesOpen = gs;
  }


 public boolean isASwitch(Dimension mazeBoundsOfOther,Dimension position){
   Dimension thisBounds = this.getMinBounds();
   int diffx = thisBounds.width - mazeBoundsOfOther.width ;
   int diffy = thisBounds.height - mazeBoundsOfOther.height ;
   Dimension updatedPosition = new Dimension(position.width+diffx,position.height+diffy);
   System.out.println("CONVERTED "+position.width+" ,"+position.height+" to "+updatedPosition.width+" ,"+updatedPosition.height);
   return isASwitch(updatedPosition);
 }


  public boolean isASwitch(Dimension sq) {
    System.out.println("looking for switch status of "+sq.width+ " "+sq.height);
    for (int i = 0; i < squares.size(); i++) {
      MazeSquare sqr = (MazeSquare) squares.elementAt(i);
      System.out.println("looking for switch status of "+sq.width+ " "+sq.height + "at " + " MAZESQUARE"+sqr.x+" "+sqr.y);
      if (sqr.x == sq.width && sqr.y == sq.height) {
        return sqr.isSwitch();
      }
    }
    //System.err.println("Should never get here");
    //System.exit( -1);
    System.out.println("REALLY SHOULDN'T GET HERE . SHOULDNT . BUT MIGHT IF THE RESET MAZE THING DOESNT WORK PROPERLY");
    return false;
  }

  public void moveTo(Dimension sq) {
    for (int i = 0; i < squares.size(); i++) {
      MazeSquare sqr = (MazeSquare) squares.elementAt(i);
      if (sqr.x == sq.width && sqr.y == sq.height) {
        current = sqr;
      }
    }
  }


}
