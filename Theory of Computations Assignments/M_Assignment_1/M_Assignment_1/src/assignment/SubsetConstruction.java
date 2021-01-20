package assignment;

import java.util.*;

public class SubsetConstruction {

    Nfa n;
    LinkedHashSet<HashSet<NfaState>> statesDFA;
    int[][] dTrans;
    
    public SubsetConstruction(Nfa n){
        this.n = n;
        statesDFA = new LinkedHashSet<HashSet<NfaState>>();
        dTrans = new int[Dfa.MAX_STATES][Dfa.SIGMA_UPPER];
    }

    int getIndex(LinkedHashSet<HashSet<NfaState>> dfaStates, HashSet<NfaState> states){
        int index=1;
        for (HashSet<NfaState> el:dfaStates) {
            if(el.containsAll(states)){
                return index;
            }
            index++;
        }
        return -1;
    }

    public Dfa  makeDfa(){
        HashSet<NfaState> T, V;
        HashSet<NfaState> s = new HashSet<NfaState>();
        Queue<HashSet<NfaState>> q = new LinkedList<HashSet<NfaState>>();
        
        s.add(n.getStart());
        V = e_closure(s);
        q.add(V);
        statesDFA.add(V);
        int r;
        
        while(!q.isEmpty()){
            T = q.remove();
            r = getIndex(statesDFA, T);
            
            for(int i = Dfa.SIGMA_LOWER; i  < Dfa.SIGMA_UPPER; i++){
                char a = (char)i;
                HashSet<NfaState> move = move(T,a);
                V = e_closure(move);
                
                if(!V.isEmpty()){
                    if(getIndex(statesDFA,V) == -1){
                        q.add(V);
                        statesDFA.add(V);
                        dTrans[r][i]= statesDFA.size();
                        
                        for ( NfaState v: V) {
                            if(v.getSymbol()==NfaState.ACCEPT){
                                dTrans[statesDFA.size()][0]= -1;
                                break;
                            }
                        }
                    }
                    else{
                         dTrans[r][i] = getIndex(statesDFA, V);
                    }
                }
            }
        }
        
        ArrayList<HashSet<NfaState>> states = new ArrayList<HashSet<NfaState>>();
        states.addAll(statesDFA);
        return new Dfa(dTrans, states, states.size());
    }
    
    HashSet<NfaState> e_closure(HashSet<NfaState> T){
        HashSet<NfaState> eClosure = T;
        Stack<NfaState> stack = new Stack<NfaState>();
        
        for (NfaState s : T) {
            stack.push(s);
        }
        
        while(!stack.empty()){
            NfaState t = stack.pop();
            if(t.getSymbol() == NfaState.EPSILON){
                NfaState next1 = t.getNext1();
                NfaState next2 = t.getNext2();
                
                if(next1!= null){
                    eClosure.add(next1);
                    if(!stack.contains(next1)){
                        stack.push(next1);
                    }
                }
                
                if(next2!= null){
                    eClosure.add(next2);
                    if(!stack.contains(next2)){
                        stack.push(next2);
                    }
                }
            }
        }
        return eClosure;
    }
    
    HashSet<NfaState> move(HashSet<NfaState> T, char x){
        HashSet<NfaState> move = new HashSet<NfaState>();
        
        for (NfaState s : T) {
            NfaState next1 = s.getNext1();
            NfaState next2 = s.getNext2();
            
            if(s.getSymbol() == x){
                if(next1!= null && !move.contains(next1)){
                    move.add(next1);
                }
                if(next2!=null && !move.contains(next2)){
                    move.add(next2);
                }
            }
        }
        return move;
    }

}
