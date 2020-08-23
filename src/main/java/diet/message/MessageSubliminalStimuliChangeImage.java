/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;

import java.util.Vector;

/**
 *
 * @author Greg
 */
public class MessageSubliminalStimuliChangeImage extends Message{

    
     
     
    long fixation1time;
    long stimulus1time;
    long blankscreen1time;
    long fixation2time;
    long stimulus2time;
    long blankscreen2time;
    String stimulus1ID;
    String stimulus2ID;
     
     
     
 
    
    public MessageSubliminalStimuliChangeImage(long fixation1time, long stimulus1time
                               ,long blankscreen1time
                               ,long fixation2time, long stimulus2time
                               ,long blankscreen2time,
                               String stimulus1ID, String stimulus2ID ){
        super("server","server");
        
        this.fixation1time=fixation1time;
        this.blankscreen1time=blankscreen1time;
        this.fixation2time=fixation2time;
        this.stimulus1time=stimulus1time;
        this.stimulus2time=stimulus2time;
        this.blankscreen2time=blankscreen2time;
        this.stimulus1ID=stimulus1ID;
        this.stimulus2ID=stimulus2ID;     
    }

    public long getFixation1time() {
        return fixation1time;
    }

    public long getFixation2time() {
        return fixation2time;
    }

    public long getBlankscreen1time() {
        return this.blankscreen1time;
    }
    public long getBlankscreen2time() {
        return this.blankscreen2time;
    }

    public String getStimulus1ID() {
        return stimulus1ID;
    }

    public long getStimulus1time() {
        return stimulus1time;
    }

    public String getStimulus2ID() {
        return stimulus2ID;
    }

    public long getStimulus2time() {
        return stimulus2time;
    }
    
    
  

   
}
