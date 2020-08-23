package diet.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//takes text file of turns/sentences (one per line) and marks those turns which contain an Acknowledgement
//produces pipe (|) seperated values file. 2 values per line, the turn and 0 or 1 for whether the turn contains an ack

public class AckMarker {
	
	Vector<Pattern> ackRegexes=new Vector<Pattern>();
	public AckMarker(String regexFile) throws IOException
	{
		this.loadRegexes(regexFile);
	}
	public void loadRegexes(String regexFile) throws IOException
	{
		BufferedReader in=new BufferedReader(new FileReader(new File(regexFile)));
		String line;
		while((line=in.readLine())!=null)
		{
			ackRegexes.add(Pattern.compile(line));
		}
	}
	
	public void processFile(String fileName, String outputFileName) throws IOException
	{
		BufferedReader in=new BufferedReader(new FileReader(new File(fileName)));
		BufferedWriter out=new BufferedWriter(new FileWriter(new File(outputFileName)));
		String line1;
		while((line1=in.readLine())!=null)
		{
			System.out.println("processing line:"+line1);
			out.write(line1+"|");
			String line=" "+line1+" ";
			boolean broken=false;
			for(Pattern p:this.ackRegexes)
			{
				Matcher m=p.matcher(line);
				if (m.find())
				{
					out.write("1", 0,1);
					out.newLine();
					out.flush();
					broken=true;
					break;
				}
				
			}
			if (!broken)
			{
				out.write("0", 0,1);
				out.newLine();
				out.flush();
				
			}
		}
		out.close();
		in.close();
		
	}
	public static void main(String args[])
	{         
		try
		{
			AckMarker am=new AckMarker(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"degradings"+File.separator+"ackRegexes.txt");
			am.processFile("D:\\UniWork\\chrisAcks\\chrisTurns.txt", "D:\\UniWork\\chrisAcks\\chrisTurnsOutput.txt");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}

}
