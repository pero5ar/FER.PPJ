package lab1.models;


import java.io.Serializable;

/**
 * Created by pero5ar on 23.10.2016..
 */
public class RegEx implements Serializable {
	private String definition;

	public RegEx(String definition) {
		this.definition = definition;
	}

	public boolean startsWith(String str) {
		return false;
	}
}
