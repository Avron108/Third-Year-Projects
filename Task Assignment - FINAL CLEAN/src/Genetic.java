import java.util.ArrayList;


public class Genetic {
	ArrayList<ArrayList<Integer>> table = new ArrayList<>();//stores all the initial tasks
	ArrayList<ArrayList<Integer>> population = new ArrayList<>();
	ArrayList<Integer> chromoFitness = new ArrayList<>();//stores the total efficiency of each chromosome the the population
	ArrayList<Integer> fittestArr = new ArrayList<>();
	int fittestTotal = 0;
	
	ArrayList<ArrayList<Integer>> tournamentArr = new ArrayList<>();
	ArrayList<Integer> tournamentFitness = new ArrayList<>();
	
	
	
	public ArrayList<Integer> createChromosome(){
		ArrayList<Integer> chromo = new ArrayList<>();
		for (int i = 0; i < table.get(0).size(); i++) {
			int rand;
			do {
				rand = (int)(Math.random() * table.size()) +0 ;
			} while (chromo.contains(rand));
			chromo.add(rand);
		}//for
		
		return chromo;
	}//createChromosome
	
	public void createPopulation(int num){
		for (int i = 0; i < num; i++) {
			ArrayList<Integer> tmp = createChromosome();
			population.add(tmp);
			addFit(tmp);
		}//for
		findMaxFittest();
	}//createPopulation
	
	public int getFit(ArrayList<Integer> tmp){
		int total = 0;
		
		for (int i = 0; i < tmp.size(); i++) {
			total+= table.get(tmp.get(i)).get(i);
			
		}//for
		
		return total;
	}
	
	public void addFit(ArrayList<Integer> tmp){
		chromoFitness.add(getFit(tmp));
	}//addEff
	
	
	
	public void findMaxFittest(){
		for (int i = 0; i < table.get(0).size(); i++) {
			int singleBest = 0;
			fittestArr.add(null);
			for (int j = 0; j < table.size(); j++) {
				int tmp = table.get(j).get(i);
				if(tmp >= singleBest){
					singleBest = tmp;
					fittestArr.set(i, j);
				}//if
			}//for j
			fittestTotal += singleBest;
		}//for i
	}//findBest
	
	public void crossover(int pos){
		
		int rand = (int)(Math.random() * population.get(0).size()) +1 ; //selecting how many of the parent 1 must be in the child
		
		ArrayList<Integer> child = new ArrayList<>();
		for (int i = 0; i < rand; i++) {
			child.add(population.get(pos).get(i));
		}//for
		
		do {
			rand = (int)(Math.random() * population.size()) +0;
		} while (rand == pos);
		
		
		int j = 0;
		while(child.size() < population.get(rand).size()){
			if (!child.contains(population.get(rand).get(j))) {
				child.add(population.get(rand).get(j));
			}//if
			j++;
		}//while
		
		
		if(getFit(child) > chromoFitness.get(pos)  && getFit(child) > chromoFitness.get(rand)){
			
			if(rand > pos){
				population.remove(rand);
				chromoFitness.remove(rand);
				population.remove(pos);
				chromoFitness.remove(pos);
					}//remove the one lower in the list first
			else{
				population.remove(pos);
				chromoFitness.remove(pos);
				population.remove(rand);
				chromoFitness.remove(rand);
				}//else
		
			population.add(child);
			addFit(child);
			
		}//create child only if it has better fitness than each of its parents
	
	}//crossover
	
	public void mutation(int pos){
	
		int rand = (int)(Math.random() * population.get(0).size()) +1 ;
		
		ArrayList<Integer> child = new ArrayList<>();
		
		for (int i = 0; i < population.get(pos).size(); i++) {
			child.add(population.get(pos).get(i));
		}//for copying all from parent to child
		
		
		for (int i = 0; i < rand; i++) {
			int rand1 = (int)(Math.random() * population.get(pos).size()) +0;
			int rand2;
			do {
				rand2 = (int)(Math.random() * population.get(pos).size()) +0;
			} while (rand1 == rand2);
			int tmp = child.get(rand1);
			child.set(rand1, child.get(rand2));
			child.set(rand2, tmp);
			
			
		}//for doing swaps for random spots. We do this 'rand' times.
		
		
		if(getFit(child) > chromoFitness.get(pos)  ){
			population.add(child);
			addFit(child);
			chromoFitness.remove(pos);
			population.remove(pos);
			
		}//do this only if the child has better fitness than the father
	
	}//mutation
	
	public void modification(){
		int randC, randM;
		for (int i = 0; i < population.size(); i++) {
			randM = (int)(Math.random() * 100) + 0;
			randC = (int)(Math.random() * 100) + 0;
			
			if(randM < 10){
				mutation(i);
				
			}//mutation
			
			
			if(randC < 95){
				crossover(i);
				
			}//crossover
			
			
		}
	}//modification
	

	public void tournament(){
		int best = 0;
		int pos = 0;
		for (int i = 0; i < population.size(); i++) {
			if(chromoFitness.get(i) > best){
				best = chromoFitness.get(i);
				pos = i;
			}//if
		}//for
		
		
		
		tournamentArr.add(population.get(pos));
		tournamentFitness.add(best);
		
		population.remove(pos);
		chromoFitness.remove(pos);
		
		
	}//tournament
	
	public void printFinal(ArrayList<Integer> elite, int fitness){
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		System.out.println("BEST TASK ASSIGNMENT FOUND");
		System.out.println("====================================");
		
		for (int i = 0; i < elite.size(); i++) {
			int taskNum = i + 1;
			System.out.println("Task "+taskNum+" : "+letters.charAt(elite.get(i)));
			
		}//for
		System.out.println("Total fitness : "+fitness );
		
	}//printFinal
	
	public void termination(){
		
		int best = 0;
		ArrayList<Integer> elite = new ArrayList<>();
		
		for (int j = 0; j < 20; j++) {
			createPopulation(10000);
			while(tournamentArr.size()<100){
				modification();
				tournament();
			}//while
			
			for (int i = 0; i < tournamentFitness.size(); i++) {
				if(tournamentFitness.get(i) > best){
					best = tournamentFitness.get(i);
					elite = tournamentArr.get(i);
				}//if
				
			}//for i
		}//for j - repeat to do many times and get a better solution
		
		
		
		
		
		
		printFinal(elite, best);
		
		
	}//termination
	
}
