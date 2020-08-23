package diet.utils.postprocessing;

import java.io.IOException;
import java.util.Vector;



public class TurnsCollaterOnly extends TurnsCollaterPostAnalysis {

    public TurnsCollaterOnly(String expDataDir, String outputDir)
	    throws IOException {
	super(expDataDir, outputDir);
	
    }

    @Override
    String getExtraColumnNames() {
	
	return "";
    }

    @Override
    String getExtraColumns(Vector<String> previousTurns) {
	
	return "";
    }
    
    public static void main(String a[])
    {
	try {
		TurnsCollaterOnly tc=
			new TurnsCollaterOnly(
					"C:/Users/Chris/Desktop/ExperimentalData/Saved Experimental Data/",
					"C:/Users/Chris/Desktop/ExperimentalData/");
		tc.analyseAndCollate();
		
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    }
    
    

}
