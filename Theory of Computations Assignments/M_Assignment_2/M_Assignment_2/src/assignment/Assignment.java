package assignment;
import java.io.*;
import java.util.*;

public class Assignment {
	private boolean c1 = true;
	private boolean c2 = true;
	private boolean c3 = true;
	private ArrayList<String> LHS = new ArrayList<String>();
	private ArrayList<String> RHS = new ArrayList<String>();
	private String s = "";
	private String test = "";
	private int in = 0;
	private Scanner sc;
	
	public Assignment(File f) throws FileNotFoundException {
		sc = new Scanner(f);
	}
	
	public void start() {
		while(sc.hasNext()) {
			s = sc.nextLine();
			s = s.replaceAll(" ", "");
			for(int i = 0; i < s.length(); i++){
				if(s.charAt(i) == '>'){
					in = i;
				}
			}
			if(!s.contains(">")){
				break;
			}
			LHS.add(s.substring(0, in));
			RHS.add(s.substring(in + 1));
		}
		test = s;
		sc.close();
		
		for(int i =0; i < LHS.size();i++){
			System.out.println(LHS.get(i) + " -> ");
			System.out.println(RHS.get(i) +"\n");
			
		}
	}
	
	private boolean isVariable(char c){
		if(Character.isUpperCase(c))
			return true;
		return false;
	}
			
	public boolean isGrammarValid(){
		
		for(int i=0;i<LHS.size();i++){
			if(LHS.get(i).length() > 1){
					c1 = false;
			}
		}
		for(int j=0; j< RHS.size();j++){
			if(RHS.get(j).length() >= 1){
				if(isVariable(RHS.get(j).charAt(0))){
					c2 = false;
				}
			}
			else{
				c2 = false;
			}
		}
		if(c1 && c2){
			String[] pairs = new String[LHS.size()];
			for(int i=0; i < LHS.size();i++){
				pairs[i] = LHS.get(i);
				pairs[i] += RHS.get(i).charAt(0);
			}
			if(pairs.length > 1){
				for(int i=0;i< pairs.length;i++){
					for(int k=i+1; k < pairs.length;k++){
						if(pairs[i].equals(pairs[k])){
							c3 = false;
						}
					}
				}
			}
		}
		if(c1 && c2 && c3){
			System.out.println("This grammar is a valid S-Grammar");
			return true;
		}
		
		System.out.println("This grammar is NOT a valid S-Grammar");
		return false;
	}
	 
	public void testString(){
		
		Stack<Character> stack1 = new Stack<Character>();
		String result = "";
		
		String s = RHS.get(0);
		int len = s.length()-1;
		
		for(int i = s.length() - 1; i >= 0; i--){
				stack1.add(s.charAt(i));
		}
		
		String temp = "";
		
		for(int i = 0; i < test.length(); i++){
			if(stack1.isEmpty() == false){
				char c = stack1.pop();

				if(isVariable(c)){

					for(int k = 0; k < LHS.size(); k++){
						if(c == LHS.get(k).charAt(0)){

							if(RHS.get(k).charAt(0) == test.charAt(i)){

								for(int m = RHS.get(k).length()-1;m>= 0; m --){
									if(isVariable(RHS.get(k).charAt(m)))
										stack1.push(RHS.get(k).charAt(m));

								}
								temp += RHS.get(k).charAt(0);
							}
						}

					}
				}
				else{
					temp += c;

				}
				result = "";
				System.out.print(temp);
				for(int j = stack1.size() - 1; j >= 0; j--){
					System.out.print(stack1.elementAt(j)+"");
					result += stack1.elementAt(j);
				}
				System.out.print("\n");
			}

		}
		result = temp + result;
		
		if(result.equals(test)){
			System.out.print("The string is accepted by the S-Grammar \n ");
		}
		else{
			System.out.print("The string is NOT accepted by the S-Grammar \n");
		}		
		
	}
}

