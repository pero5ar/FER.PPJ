package lab2.models;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by JJ on 19.11.2016..
 */
public class StateSet extends HashSet<String> {

	/**
	 * Broj u DKA
	 */
	protected String stateName;

	public void setStateName(String stateName){
		this.stateName = stateName;
	}

	public String getStateName(){
		return stateName;
	}

	public StateSet(){
		super();
	}


	public StateSet(Collection<String> collection){
		super(collection);
	}
}
