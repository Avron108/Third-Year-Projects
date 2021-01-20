package assignment;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class RunAssignment{
	
    public static boolean isMemberOf(String re ,String input){

        try {
            RegExp.setNextStateNum(0);
            RegExp r = (new RegExp2AST(re)).convert();
            r.decompile();
            Nfa n = r.makeNfa();
            SubsetConstruction sub = new SubsetConstruction(n);
            Dfa dfa = sub.makeDfa();
            int[][] transTable = dfa.getTransTable();
            int symbolIndex;
            int nextState =1;
            for (int i = 0; i <input.length() ; i++) {
                symbolIndex= (int)input.charAt(i);
                nextState = transTable[nextState][symbolIndex];
            }
            if(transTable[nextState][0]==-1)
                return true;

        }catch (ParseException  ex) {
            
        }catch (IOException e){
            System.err.println(e);
        }

        return false;
    }

    public void readFile()throws IOException{
        
        File filename = new File("TestData.txt");
        Scanner fileScan = new Scanner(filename);
        String re = "";
        boolean regExpr = true;
        int regExrNum = 1;
        
         while (fileScan.hasNextLine()){
            String line = fileScan.nextLine();
            if(regExpr){
                System.out.println();
                System.out.println("_______________ Regular expression Number : "+regExrNum+" _____________");
                re = line;
                System.out.println("CONVERTING REGULAR EXPRESSION :"+re+" to DFA");
                System.out.println();
                regExpr = false;
                regExrNum++;
            }else{
                if(line.equals("//")){
                    regExpr = true;
                }else{
                    if(line.isEmpty() && re.charAt(re.length()-1) == '*')
                        System.out.println("  : is a member of the Language");
                    else if(line.isEmpty() && re.charAt(re.length()-1) != '*')
                        System.out.println("  : is not a member of the Language");
                    else {
                        boolean member = isMemberOf(re,line);
                        System.out.println(line+(member ?" : is a member of the Language":" : is not a member of the Language"));
                    } 
                }
            }
        }
        fileScan.close();
    }

    public static void main(String[] args)throws IOException {
		RunAssignment obj = new RunAssignment();
	    obj.readFile();
	}
}