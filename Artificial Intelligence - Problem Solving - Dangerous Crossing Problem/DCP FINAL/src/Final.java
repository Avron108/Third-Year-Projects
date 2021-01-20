import java.util.ArrayList;
import java.util.Scanner;


public class Final {


	public static void main(String[] args) {



		Scanner sc = new Scanner(System.in);//For input


		System.out.println("Enter the number of people initially on the RHS");//Initially enter the num of people
		int num = sc.nextInt();//store the value



		Problem p = new Problem();


		for (int i = 0; i < num; i++) {
			System.out.println("Enter a name of person ");
			String name = sc.next();
			p.people.add(name);

			System.out.println("Enter the time of "+name);
			int t = sc.nextInt();
			p.time.add(t);


		}//for loop to get the names and time for each person

		System.out.println("Enter the minimum time for everyone to get across");//Initially enter the minimum time
		p.min = sc.nextInt();//store the value

		p.setRoot();

		System.out.println("Enter the number correspending to a search: 1-Breadth First search, 2-Depth First Search, 3-Greedy Best First, 4-A*Search");
		int search = sc.nextInt();
		if(search == 1){
			p.bfs();
		}
		if(search == 2){
			p.dfs();
		}
		if(search == 3){
			p.greedyBest();
		}
		if(search == 4){
			p.aStar();
		}
		/*
		State one = p.moveTwo(p.root, "q", "w");
		State two = p.moveOne(one,"w");
		State three = p.moveTwo(two, "e", "r");
		State four = p.moveOne(three,"r");
		State five = p.moveTwo(four, "t", "w");
		State six = p.moveOne(five,"t");
		State seven = p.moveTwo(six, "t","r");
		p.printArrayList(p.path(seven));
		
		*/









	}

}
