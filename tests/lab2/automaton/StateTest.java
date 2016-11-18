package lab2.automaton;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by juraj on 18.11.2016..
 */
public class StateTest {

	private State stateA = new State("A");
	private State stateB = new State("B");

	private State stateCompositeAB = new State(stateA, stateB);

	public StateTest(){
	}

	@Test
	public void statesEqualBasic() throws Exception {
		assertEquals(new State("A"), stateA);
	}

	@Test
	public void statesEqualName() throws Exception {
		assertEquals("A", stateA.toString());
	}

	@Test
	public void statesEqualCompositeName() throws Exception {
		assertEquals("{A,B}", stateCompositeAB.toString());
	}

	@Test
	public void statesEqualComposite() throws Exception {
		assertEquals(new State(
				new State("A"),
				new State("B")
		), stateCompositeAB);
	}

}