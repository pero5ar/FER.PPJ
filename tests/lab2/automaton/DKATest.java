package lab2.automaton;

import lab2.models.DoubleMap;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by juraj on 18.11.2016..
 */
public class DKATest {
	@Test
	public void fromNKA() throws Exception {

		DoubleMap<Set<State>, String, Set<State>> prijelazi = new DoubleMap<>();
		NKA nka = new NKA(
				new State("a").getSet(),
				stringsToStateSet("a", "b", "c", "d", "e"),
				prijelazi
		);

		// prijelazi
		// stanje a
		prijelazi.put(new State("a").getSet(), "0", stringsToStateSet("a", "b", "c", "d", "e"));
		prijelazi.put(new State("a").getSet(), "1", stringsToStateSet("d", "e"));
		// stanje b
		prijelazi.put(new State("b").getSet(), "0", stringsToStateSet("c"));
		prijelazi.put(new State("b").getSet(), "1", stringsToStateSet("e"));
		// stanje c
		prijelazi.put(new State("c").getSet(), "0", new HashSet<>());
		prijelazi.put(new State("c").getSet(), "1", stringsToStateSet("b"));
		// stanje d
		prijelazi.put(new State("d").getSet(), "0", stringsToStateSet("e"));
		prijelazi.put(new State("d").getSet(), "1", new HashSet<>());
		// stanje e
		prijelazi.put(new State("e").getSet(), "0", new HashSet<>());
		prijelazi.put(new State("e").getSet(), "1", new HashSet<>());

		DKA dka = DKA.fromNKA(nka);

		Set<State> expectedStatesSet = new HashSet<>();
		DoubleMap<Set<State>, String, Set<State>> expectedPrijelazi = new DoubleMap<>();
		DKA expectedDKA = new DKA(
				new State("a").getSet(),
				expectedStatesSet,
				expectedPrijelazi
		);

		// stanje a
		expectedPrijelazi.put(new State("a").getSet(), "0", stringsToStateSet("a", "b", "c", "d", "e"));
		expectedPrijelazi.put(new State("a").getSet(), "1", stringsToStateSet("d", "e"));
		// stanje b
		expectedPrijelazi.put(new State("b").getSet(), "0", stringsToStateSet("c"));
		expectedPrijelazi.put(new State("b").getSet(), "1", stringsToStateSet("e"));
		// stanje c
		expectedPrijelazi.put(new State("c").getSet(), "0", new HashSet<>());
		expectedPrijelazi.put(new State("c").getSet(), "1", stringsToStateSet("b"));
		// stanje d
		expectedPrijelazi.put(new State("d").getSet(), "0", stringsToStateSet("e"));
		expectedPrijelazi.put(new State("d").getSet(), "1", new HashSet<>());
		// stanje e
		expectedPrijelazi.put(new State("e").getSet(), "0", new HashSet<>());
		expectedPrijelazi.put(new State("e").getSet(), "1", new HashSet<>());

		// stanje {a,b,c,d,e}
		expectedPrijelazi.put(stringsToStateSet("a","b","c","d","e"), "0", stringsToStateSet("a","b","c","d","e"));
		expectedPrijelazi.put(stringsToStateSet("a","b","c","d","e"), "1", stringsToStateSet("b","d","e"));
		// stanje {d,e}
		expectedPrijelazi.put(stringsToStateSet("d","e"), "0", stringsToStateSet("e"));
		expectedPrijelazi.put(stringsToStateSet("d","e"), "1", stringsToStateSet());
		// stanje {b,d,e}
		expectedPrijelazi.put(stringsToStateSet("b","d","e"), "0", stringsToStateSet("c","e"));
		expectedPrijelazi.put(stringsToStateSet("b","d","e"), "1", stringsToStateSet("e"));
		// stanje {d,e}
		expectedPrijelazi.put(stringsToStateSet("c","e"), "0", stringsToStateSet());
		expectedPrijelazi.put(stringsToStateSet("c","e"), "1", stringsToStateSet("b"));

		assertEquals(expectedDKA, dka);

	}

	private Set<State> stringsToStateSet(String...states){
		Set<State> set = new HashSet<>();

		for(String s : states){
			set.add(new State(s));
		}

		return set;
	}

}