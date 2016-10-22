package lab1.state;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by pero5ar on 22.10.2016..
 */
public class StateStorage {
    private Collection<State> states;

    public StateStorage() {
        states = new HashSet<State>();
    }

    public void add(String state) {
        states.add(new State(state));
    }

    public void add(Collection<String> states) {

    }
}
