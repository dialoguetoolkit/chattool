/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.ProceduralComms;

/**
 *
 * @author gj
 */
public interface JTrialTimerActionRecipientInterface {
    public void processNotification(String nameOfEvent);
    public void changeClientProgressBars(int value, String text);
    
}
