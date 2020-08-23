/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.stimuliset;

import diet.server.ConversationController.ui.CustomDialog;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class ConfidenceTaskControllerSequence {
     
     int[] positionInHexagon;
     int[] patchContrast ;
     int[] patchIsInFirstOrSecond;   
    
      public int nextIDX =0;
      
      public ConfidenceTaskControllerSequence(String[] positions, String[] patch_Contrast, String[] patchIsIn1or2){
           if(positions.length!=patch_Contrast.length ||positions.length!=patchIsIn1or2.length){
               CustomDialog.showDialog("WRONG LENGTH OF TASKCONTROLLER SEQUENCE");
               System.exit(-4);
           }
           
           positionInHexagon = new int[positions.length];
           patchContrast = new int[patch_Contrast.length];
           patchIsInFirstOrSecond = new int [patchIsIn1or2.length];
           
           try{
           for(int i=0;i<positionInHexagon.length;i++){
               positionInHexagon[i]= Integer.parseInt(positions[i]);
               patchContrast[i]= Integer.parseInt(patch_Contrast[i]);
               patchIsInFirstOrSecond[i]=Integer.parseInt(patchIsIn1or2[i]);
           }
           }catch (Exception e){
               e.printStackTrace();
               CustomDialog.showDialog("SOME ERROR PROCESSING THE FILE: "+e.getMessage());
               System.exit(-5);
           }
           
           
           
      }
     
      public int[] getPositionInHexagon_PatchContrast_PatchIsIn1or2(){
          
          int[] value = {positionInHexagon[nextIDX], patchContrast[nextIDX],patchIsInFirstOrSecond[nextIDX]};
          nextIDX++;
          if(nextIDX>=positionInHexagon.length){
              nextIDX=0;
          }     
          return value;
      }
      
}
