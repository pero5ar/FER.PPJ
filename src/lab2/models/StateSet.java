package lab2.models;

import lab2.automaton.State;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by JJ on 19.11.2016..
 */
public class StateSet extends HashSet<State> {
	/**
	 * Broj u DKA
	 */
	protected String nazivStanja;

	public void setNazivStanja(String nazivStanja){
		this.nazivStanja = nazivStanja;
	}

	public String getNazivStanja(){
		return nazivStanja;
	}

	public StateSet(){
		super();
	}

	public StateSet(Collection<State> collection){
		super(collection);
	}
}
