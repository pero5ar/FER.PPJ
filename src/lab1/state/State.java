package lab1.state;

/**
 * Created by pero5ar on 22.10.2016..
 */
public class State {
    private String definition;
    private String name;

    public State(String definition) {
        this.definition = definition;
        name = definition.substring(2);
    }

    public String getDefinition() {
        return definition;
    }

    public String getName() {
        return name;
    }
}
