import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;



public class Problem{
	ArrayList<String> people = new ArrayList<>();//Initialize array list for people
	ArrayList<Integer> time = new ArrayList<>();//Initialize array list for time
	int min;

	//ArrayList<State> states = new ArrayList<>();
	State root = new State();



	public void setRoot(){
		for (int i = 0; i < people.size(); i++) {
			root.rhs.add(people.get(i));//initially everyone is on the rhs

		}//for

		root.cost = 0;
		root.torchRHS = true;
		root.accTime = 0;
		//states.add(root);
	}

	public State moveOne(State s, String person){
		State child = new State();
		for (int i = 0; i < s.rhs.size(); i++) {
			child.rhs.add(s.rhs.get(i));
		}//copy rhs
		for (int i = 0; i < s.lhs.size(); i++) {
			child.lhs.add(s.lhs.get(i));
		}//copy rhs

		child.lhs.remove(person);//moving one person from the lhs...
		child.rhs.add(person);//...to the rhs
		child.torchRHS = true;//...setting the torch to be true on the rhs
		int pos = people.indexOf(person);
		child.cost = time.get(pos);//adding the cost of that move

		child.parent = s;//linking new state to the initial parent
		//states.add(child);//adding this unique state to the the list of state spaces



		child.accTime = s.accTime + child.cost;

		return child;

	}

	public State moveTwo(State s, String person1, String person2){
		State child = new State();
		for (int i = 0; i < s.rhs.size(); i++) {
			child.rhs.add(s.rhs.get(i));
		}
		for (int i = 0; i < s.lhs.size(); i++) {
			child.lhs.add(s.lhs.get(i));
		}


		child.rhs.remove(person1);
		child.rhs.remove(person2);//moving two selected people from rhs...

		child.lhs.add(person1);
		child.lhs.add(person2);//... to the lhs
		int cost1 = time.get(people.indexOf(person1));
		int cost2 = time.get(people.indexOf(person2));//getting both times to be compared

		if(cost1 > cost2){
			child.cost = cost1;
		}else{
			child.cost = cost2;
		}
		//simple check which person takes longer, then add that time to state cost

		child.torchRHS = false;//...setting the torch to be false on the rhs(so it's on the lhs)

		child.parent = s;//linking new state to the initial parent
		//states.add(child);//adding this unique state to the the list of state spaces

		child.accTime = s.accTime + child.cost;

		if(child.rhs.size() == 0){
			child.goalState = true;

		}

		return child;

	}

	public ArrayList<State> path(State s){
		ArrayList<State> sp = new ArrayList<>();
		State tmp = new State();
		tmp = s;
		sp.add(tmp);
		while(tmp.parent != null){
			tmp = tmp.parent;
			sp.add(tmp);
		}
		return sp;
	}

	public void printArrayList(ArrayList<State> sp){
		int step = 0;
		for (int i = sp.size() - 1 ; i >= 0; i--) {
			printState(sp.get(i), step);
			step++;
		}
	}

	public void bfs(){
		State node = root;
		State shortState = new State();
		Queue<State> frontier = new LinkedList<>();
		ArrayList<State> reached = new ArrayList<>();
		frontier.add(root);
		reached.add(root);
		boolean leafReached = false;
		boolean goalReached = false;
		int shortest;
		int numVisit = 0;

		while(!frontier.isEmpty() && !goalReached){

			node = frontier.poll();

			if(node.torchRHS){
				for (int i = 0; i < node.rhs.size() - 1; i++) {

					for (int j = i +1 ; j < node.rhs.size(); j++) {
						State s = new State();
						s = moveTwo(node, node.rhs.get(i), node.rhs.get(j));
						reached.add(s);
						frontier.add(s);
						numVisit++;
						//printState(s);

					}//for j
				}//for i
			}//if
			else if(!node.goalState){
				for (int i = 0; i < node.lhs.size(); i++) {
					State s = moveOne(node, node.lhs.get(i));
					reached.add(s);
					frontier.add(s);
					numVisit++;
					//printState(s);


				}//for i
			}//else
			else{
				leafReached = true;
				shortest = node.accTime;
				if(shortest <= min){
					shortState = node;
					goalReached = true;
				}
			}//else
		}//while
		printArrayList(path(shortState));
		System.out.println("The number of visited nodes : " + numVisit);

	}//bfs

	public void greedyBest(){
		State node = root;
		State shortState = new State();
		PriorityQueue<State> frontier = new PriorityQueue<>();
		ArrayList<State> reached = new ArrayList<>();
		frontier.add(root);
		reached.add(root);
		boolean leafReached = false;
		boolean goalReached = false;
		int shortest;
		int numVisit = 0;

		while(!frontier.isEmpty() && !goalReached){

			node = frontier.poll();

			if(node.torchRHS){
				for (int i = 0; i < node.rhs.size() - 1; i++) {

					for (int j = i +1 ; j < node.rhs.size(); j++) {
						State s = new State();
						s = moveTwo(node, node.rhs.get(i), node.rhs.get(j));
						reached.add(s);
						frontier.add(s);
						numVisit++;
						//printState(s);

					}//for j
				}//for i
			}//if
			else if(!node.goalState){
				for (int i = 0; i < node.lhs.size(); i++) {
					State s = moveOne(node, node.lhs.get(i));
					reached.add(s);
					frontier.add(s);
					numVisit++;
					//printState(s);


				}//for i
			}//else
			else{
				leafReached = true;
				shortest = node.accTime;
				if(shortest <= min){
					shortState = node;
					goalReached = true;
				}
			}//else
		}//while
		printArrayList(path(shortState));
		System.out.println("The number of visited nodes : " + numVisit);

	}//greedy

	public void aStar(){
		State node = root;
		node.aSearch = true;
		State shortState = new State();
		PriorityQueue<State> frontier = new PriorityQueue<>();
		ArrayList<State> reached = new ArrayList<>();
		frontier.add(root);
		reached.add(root);
		boolean leafReached = false;
		boolean goalReached = false;
		int shortest;
		int numVisit = 0;

		while(!frontier.isEmpty() && !goalReached){

			node = frontier.poll();

			if(node.torchRHS){
				for (int i = 0; i < node.rhs.size() - 1; i++) {

					for (int j = i +1 ; j < node.rhs.size(); j++) {
						State s = new State();
						s = moveTwo(node, node.rhs.get(i), node.rhs.get(j));
						s.aSearch = true;
						reached.add(s);
						frontier.add(s);
						numVisit++;
						//printState(s);

					}//for j
				}//for i
			}//if
			else if(!node.goalState){
				for (int i = 0; i < node.lhs.size(); i++) {
					State s = new State();
					s = moveOne(node, node.lhs.get(i));
					s.aSearch = true;
					reached.add(s);
					frontier.add(s);
					numVisit++;
					//printState(s);


				}//for i
			}//else
			else{
				leafReached = true;
				shortest = node.accTime;
				if(shortest <= min){
					shortState = node;
					goalReached = true;
				}
			}//else
		}//while
		printArrayList(path(shortState));
		System.out.println("The number of visited nodes : " + numVisit);

	}//aStar search

	public void dfs(){
		State node = root;
		State shortState = new State();
		Stack<State> frontier = new Stack<>();
		ArrayList<State> reached = new ArrayList<>();
		frontier.add(root);
		reached.add(root);
		boolean leafReached = false;
		boolean goalReached = false;
		int shortest;
		int numVisit = 0;
		while(!frontier.isEmpty() && !goalReached){
			node = frontier.pop();

			if(node.torchRHS){
				for (int i = 0; i < node.rhs.size() - 1; i++) {

					for (int j = i +1 ; j < node.rhs.size(); j++) {
						State s = new State();
						s = moveTwo(node, node.rhs.get(i), node.rhs.get(j));
						reached.add(s);
						frontier.add(s);
						numVisit++;
						//printState(s);

					}//for j
				}//for i
			}//if
			else if(!node.goalState){
				for (int i = 0; i < node.lhs.size(); i++) {
					State s = moveOne(node, node.lhs.get(i));
					reached.add(s);
					frontier.add(s);
					numVisit++;
					//printState(s);


				}//for i
			}//else
			else{
				leafReached = true;
				shortest = node.accTime;
				if(shortest <= min){
					shortState = node;
					goalReached = true;
				}
			}//else

		}//while
		printArrayList(path(shortState));
		System.out.println("The number of visited nodes : " + numVisit);
	}//dfs

	public void printState(State s){
		System.out.print("RHS: ");
		for (int i = 0; i < s.rhs.size(); i++) {
			System.out.print(s.rhs.get(i)+", ");
		}//print rhs
		System.out.println("");


		System.out.print("LHS: ");
		for (int i = 0; i < s.lhs.size(); i++) {
			System.out.print(s.lhs.get(i)+", ");
		}//print rhs
		System.out.println("");


		if(s.torchRHS){
			System.out.println("Torch on RHS");
		}else{
			System.out.println("Torch on LHS");
		}

		System.out.println("Move time : "+s.cost);
		System.out.println("Total time : "+s.accTime);

		if(s.goalState){
			System.out.println("GOAL ACHIEVED");
		}
		System.out.println("");


	}//print state

	public void printState(State s, int step){
		System.out.println("Step "+step+" : ");
		printState(s);

	}//print state


}
