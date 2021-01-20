import java.util.ArrayList;


public class State implements Comparable<State>{
	ArrayList<String> rhs = new ArrayList<>();
	ArrayList<String> lhs = new ArrayList<>();
	State parent;
	public int cost;


	//String torch;
	boolean torchRHS;

	boolean goalState = false;
	int accTime;

	boolean aSearch = false;




	@Override
	public int compareTo(State s) {
		if(aSearch){

			if(this.equals(s)){
				return 0;
			}else if(this.rhs.size() + accTime > s.rhs.size() + accTime){
				return 1;
			}else{
				return -1;
			}//else

		}//if

		else{

			if(this.equals(s)){
				return 0;
			}else if(this.rhs.size() > s.rhs.size()){
				return 1;
			}else{
				return -1;
			}
		}

	}

}
