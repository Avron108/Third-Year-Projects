import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scIn = new Scanner(System.in);
		System.out.println("Enter the path of text file");
		String in = scIn.next();
		File file = new File(in);
		Genetic g = new Genetic();
		
		try {
			Scanner sc = new Scanner(file);
			System.out.println("File found");
			
			
			int c = 0;
			while(sc.hasNext()){
				ArrayList<Integer> ints = new ArrayList<>();
				c++;
				String line = sc.nextLine();
				Scanner lineInt = new Scanner(line);
				while(lineInt.hasNext()){
					ints.add(lineInt.nextInt());
				}//Inner while
				
				g.table.add(ints);
			}//while
			g.termination();
			
		}//try
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}//catch
		
		
		//C:\\Users\\Avron\\Documents\\tasks.txt
		
	}//main

}
