package lab2.automaton;

import lab2.models.StateSet;

import java.util.HashSet;
import java.util.Set;

public class State {
	protected String name;
//	protected boolean composite = false;
//	protected Set<State> states = null;

	public State(String name){
		this.name = name;
	}

//	public State(State[] states){
//		if (states.length == 1){
//			this.name = states[0].name;
//			return;
//		}
//
//		//should resolve composite states to singular
//		this.states = new HashSet<>();
//
//		for(State s : states){
//			this.states.add(s);
//		}
//		this.composite = true;
//
//		//name = {A,B,C,D}
//		name = '{' + String.join(",", getAllSingularStatesNames()) + '}';
//	}

	public String getName(){
		return name;
	}

	public String toString(){
		return name;
	}

//	public boolean isComposite(){
//		return composite;
//	}
//
//	public Set<State> getAllSingularStates(){
//		Set<State> singularStates = new HashSet<>();
//
//		if (!composite){
//			singularStates.add(this);
//			return singularStates;
//		}
//
//		states.forEach(s -> {
//			if (s.isComposite()){
//				singularStates.addAll(s.getAllSingularStates());
//			}else{
//				singularStates.add(s);
//			}
//		});
//
//		return singularStates;
//	}

//	public Set<String> getAllSingularStatesNames(){
//		Set<String> singularStatesNames = new HashSet<>();
//
//		states.forEach(s -> {
//			if (s.isComposite()){
//				singularStatesNames.addAll(s.getAllSingularStatesNames());
//			}else{
//				singularStatesNames.add(s.name);
//			}
//		});
//
//		return singularStatesNames;
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o) return true;
//		if (o == null || getClass() != o.getClass()) return false;
//
//		State state = (State) o;
//
//		if (!name.equals(state.name)) return false;
//		return states != null ? states.equals(state.states) : state.states == null;
//
//	}
//
//	@Override
//	public int hashCode() {
//		int result = name.hashCode();
//		result = 31 * result + (states != null ? states.hashCode() : 0);
//		return result;
//	}

	public StateSet getSet(){
		StateSet set = new StateSet();
		set.add(this);
		return set;
	}

	public static String setToString(StateSet set){
		Set<String> stringSet = new HashSet<>();
		set.forEach(s -> stringSet.add(s.toString()));

		return '{' + String.join(",", stringSet) + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		State state = (State) o;

		return name.equals(state.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
