package diet.utils.postprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Vector;

import diet.server.Conversation;

public class TurnsCollatorLengthFragDist extends TurnsCollaterPostAnalysis {
	
	@Override
	String getExtraColumnNames() {
		
		return "|WordsLength|CharsLength|FragAntecedentDistance";
	}

	@Override
	String getExtraColumns(Vector<String> previousTurns) {
		
		
		String curTurn=previousTurns.lastElement();
		//System.out.println("last element:"+curTurn);
		
		
		String[] values=curTurn.split("\\|", -1);
		String text=values[7];
		//System.out.println("text is:"+text);
		String[] words=text.split("\\s+");
		String result="|";
		result+=words.length+"|";
		result+=text.length()+"|";
		String prevTurn;
		String prevText;
		if (previousTurns.size()>=2)
		{
			prevTurn=previousTurns.elementAt(previousTurns.size()-2);
			prevText=prevTurn.split("\\|",-1)[7];
		}
		else
		{
			result+="N/A";
			return result;
		}
		if (values[1].equalsIgnoreCase("server")&&text.endsWith("?"))
		{
			String fragLower=text.substring(0, text.length()-1).toLowerCase();
		
			String antecedentCorrected=this.fixSpelling(prevText);
			String antecedentLower=antecedentCorrected.toLowerCase();
			//System.out.println("antecedent lower:"+antecedentLower);
			//System.out.println("fragment lower:"+fragLower);
			int distance=antecedentLower.length()-antecedentLower.lastIndexOf(fragLower);
			//System.out.println("index of frag"+fragLower);
			result+=distance;
		}
		else
			result+="N/A";
			
		
		
		return result;
	}
	public TurnsCollatorLengthFragDist(String expDataDir, String outputDir) throws IOException
	{
		super(expDataDir, outputDir);
		this.loadMisspellings();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			TurnsCollatorLengthFragDist tc=
				new TurnsCollatorLengthFragDist(
						"D:\\UniWork\\Experiments(data and writing)\\Within_Between_End_of_turn_ConstituentProbeCR\\data_raw_OtherEndOfTurn",
						"D:\\UniWork\\Experiments(data and writing)\\Within_Between_End_of_turn_ConstituentProbeCR\\data_analysis_OtherEndOfTurn");
			tc.analyseAndCollate();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
	
	protected String fixSpelling(String turnSoFar) {

		String split[] = turnSoFar.split(" ");
		String result = "";
		for (int i = 0; i < split.length; i++) {

			String w = split[i];

			String lowerCase = w.toLowerCase();
			// Conversation.printWSln("Misspellings",
			// "Checking Word For Misspelling: "+w);
			if (misspellings.containsKey(lowerCase)) {
				// Conversation.printWSln("Misspellings",
				// "Found misspelling: "+w);
				String replacement = misspellings.get(lowerCase);
				// Conversation.printWSln("Misspellings",
				// "Replacing: "+replacement);
				result += replacement + " ";
			} else
				result += w + " ";
		}
		if (turnSoFar.endsWith(" "))
			return result;
		else
			return result.trim();
	}
	TreeMap<String, String> misspellings = new TreeMap<String, String>();
	public synchronized void loadMisspellings() throws IOException {
		misspellings = new TreeMap<String, String>();
		Conversation.printWSln("Main", "loading misspellings");
		BufferedReader in = new BufferedReader(new FileReader(
				this.misspellingsFile));
		String line;
		while ((line = in.readLine()) != null) {
			String[] halves = getHalves(line);
			misspellings.put(halves[0], halves[1]);
		}

	}
	private String[] getHalves(String whole) {
		String[] result = new String[2];
		for (int i = 0; i < whole.length(); i++) {
			if (whole.charAt(i) == ' ') {
				result[0] = whole.substring(0, i);
				result[1] = whole.substring(i + 1, whole.length());
				return result;
			}
		}
		return null;
	}

	File misspellingsFile = new File(System.getProperty("user.dir")+File.separator+"experimentresources"
			+ File.separator + "fragmentFilters" + File.separator
			+ "misspellings.txt");

}
