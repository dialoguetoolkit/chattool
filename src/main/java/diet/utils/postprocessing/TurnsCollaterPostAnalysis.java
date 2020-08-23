package diet.utils.postprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/**
 * @author arash
 * 
 * This is an abstract class which should be sub-classed detailing the getExtraColumns and getExtraColumnNames methods.
 * This class will generally collate all the turn.txt files found in the given experiment directories. The 2 abstract methods should
 * be overridden in order to provide experiment-specific post-analyses of each turn, returning a pipe-separated string. The result of calling
 * analyseAndCollate() will then produce a concatenation of all the turns.txt files, plus these additional columns, coded for each turn. . . .   
 * 
 *
 */
public abstract class TurnsCollaterPostAnalysis {
	
	File expDataDir;
	File outputDir;
	public TurnsCollaterPostAnalysis(String expDataDir, String outputDir) throws IOException
	{
		this.expDataDir=new File(expDataDir);
		this.outputDir=new File(outputDir);
		if (!this.expDataDir.exists()||!this.outputDir.exists())
		{
			throw new IOException("Nonexistant experiment folder or nonexistant output Folder");
		}
	}
	
	public void analyseAndCollate() throws IOException
	{
		BufferedWriter out=new BufferedWriter(new FileWriter(this.outputDir.getAbsolutePath()+File.separator+"data_output_of_"+this.getClass().getName()+"3.csv"));
		
		File[] dirList=this.expDataDir.listFiles();
		boolean columnHeadersProcessed=false;
		int columnCount=0;
		for(File expDir:dirList)
		{
			
			if (expDir.isDirectory())
			{
				
				File turnsFile=new File(this.expDataDir+File.separator+expDir.getName()+File.separator+"turns.txt");
				System.out.println("Processing exp with id:"+expDir.getName());
				if (!turnsFile.exists())
				{
					System.err.println(expDir.getName()+" folder skipped, no turns.txt file");
					continue;
				}
					
				BufferedReader in=new BufferedReader(new FileReader(turnsFile));
				String line;
				boolean isFirstLineOfFile=true;
				Vector<String> linesSoFar=new Vector<String>();
				while((line=in.readLine())!=null)
				{
					if(!columnHeadersProcessed)
					{
						String headers=line+this.getExtraColumnNames();
						String headerValues[]=headers.split("\\|", -1);
						columnCount=headerValues.length;
						
						out.write("ExpID|"+headers);
						out.newLine();
						out.flush();
						columnHeadersProcessed=true;
						continue;
					}
					else if (isFirstLineOfFile) {
						isFirstLineOfFile=false;
						continue;
					}
					linesSoFar.add(line);
					String[] values=line.split("\\|",-1);
					String extraColumns=getExtraColumns(linesSoFar);
					//System.out.println("extra columns:"+extraColumns);
					String[] extraValues=extraColumns.split("\\|",-1);
					if (values.length+extraValues.length-1!=columnCount)
					{
						System.err.println("Bad Row in "+expDir.getName()+". Not the right number of values:");
						System.err.println("Number of values in row:"+((int)(values.length+extraValues.length)));
						System.err.println("Number of initial headers:"+columnCount);
						System.err.println(line+"\n");
					
					}
					
					
					out.write(expDir.getName()+"|"+line+extraColumns);
					out.newLine();
					out.flush();				
				}
			}
			
			
		}
		
	}
	
	/**
	 * These should return pipe-separated values. The string returned should start with the pipe(|).
	 * They should return the empty string if the class is only to collate the turns.txt files, without
	 * adding extra columns.
	 * NOTE: the previous turns passed to the method will not include the exp id. So all indeces-1!
	 * @param previousTurns
	 * @return pipe-separated extra values for each row
	 */
	abstract String getExtraColumns(Vector<String> previousTurns);
	
	abstract String getExtraColumnNames();
	

}
