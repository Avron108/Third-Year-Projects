package assignment;
import java.io.*;
import java.util.*;

public class RunAssignment {
			
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select an input file(1-6): ");
		int i = sc.nextInt();
		while(i < 1 && i > 6) {
			System.out.println("Enter a valid integer from 1 to 6: ");
			i = sc.nextInt();
		}
		
		File f = new File("input" + i + ".txt");
		Assignment a = new Assignment(f);
		
		a.start();
		
		if(a.isGrammarValid()){
			a.testString();
		}
	}

}
