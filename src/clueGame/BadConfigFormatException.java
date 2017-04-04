package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception{
	public BadConfigFormatException(){
		super();
		System.out.println("Error, the configuration file is not properly formated.");
	}
	public BadConfigFormatException(String b) throws FileNotFoundException{
		super();
		System.out.println("Error, the configuration file:" + b + " is not properly formated.");
		PrintWriter print = new PrintWriter("logfile.txt");
		print.println("Error, the configuration file:" + b + " is not properly formated.");
		print.close();
	}
}
