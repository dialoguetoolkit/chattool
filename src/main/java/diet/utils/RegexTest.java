package diet.utils;

import java.util.Scanner;

public class RegexTest {

	public static void main(String args[]) {
		
		/*
		while (true) {
			Scanner in = new Scanner(System.in);
			in.useDelimiter("FF");

			System.out.print("Enter string: ");
			
			String s = in.next();
			System.out.println("String entered is:("+s+")");
			System.out.print("Enter Regex: ");
			in = new Scanner(System.in);
			in.useDelimiter("FF");
			String regex = in.next();
			System.out.println("Regex is:("+regex+")");
			if (s.matches(regex))
				System.out.println("\nMATCHED!!" + regex + " "+ s);
			else
				System.out.println("\nNOT MATCHED!!"+ regex + " "+ s);
		}*/
		
		String a="a|b|bc|kj|||";
		System.out.println(a.split("\\|",-1).length);
	}
}