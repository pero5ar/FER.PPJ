package lab1.models;


import java.util.LinkedHashSet;
import java.util.Set;
import java.io.Serializable;

/**
 * Created by pero5ar on 23.10.2016..
 *
 */
public class RegEx implements Serializable {
    private final char EPSILON = '$';
    private final char KLEEN ='*';

    private String definition;
    private Set<String> options;

	public RegEx(String definition) {
		this.definition = definition;
        options = generateOptions(definition);
	}

    /**
     * Generates possible options from regex expression.
     * ESCAPE CHARACTER, EPSILON CLOSURE AND KLEEN STAR STILL UNRESOLVED.
     *
     * TODO: logika ne radi za duple zagrade... i pitaj boga za šta još
     *
     *
     * TODO: NAPIŠI PONOVNO PO ALGORITMU U ZADATKU LABOSA
     *
     *
     * @param expression    regex expression
     * @return
     */
    private Set<String> generateOptions(String expression) {
        char[] expressionAsArray = expression.toCharArray();
        Set<String> localOptions = new LinkedHashSet<>();

        int optionIndex = 0;
        int subExpressionIndex = -1;

        boolean escape = false;
        int bracketCounter = 0;

        Set<String> activeOptions = new LinkedHashSet<>();
        fillIfEmpty(activeOptions);

        for (int i = 0; i < expressionAsArray.length; i++) {
            char currentChar = expressionAsArray[i];

            if (!escape && (currentChar == '\\')) {
                escape = true;
                continue;
            }

            /**
             * resolve bracket counters
             */
            if (!escape) {
                if (currentChar == '(') bracketCounter++;
                if (currentChar == ')') bracketCounter--;
            }

            /*
             * close bracket
             */
            if ((subExpressionIndex > -1) && (currentChar == ')') && (bracketCounter == 0)) {
                Set<String> activeOptionsCopy = new LinkedHashSet<>(activeOptions);
                activeOptions.clear();
                for (String option : activeOptionsCopy) {
                    for (String subOption : generateOptions(expression.substring(subExpressionIndex, i))) {
                        activeOptions.add(option + subOption);
                    }
                }
                fillIfEmpty(activeOptions);
                subExpressionIndex = -1;
                optionIndex = i+1;
                continue;
            }

            /*
             * open bracket
             */
            if (!escape && (currentChar == '(')) {
                subExpressionIndex = i+1;
                Set<String> activeOptionsCopy = new LinkedHashSet<>(activeOptions);
                activeOptions.clear();
                for (String option : activeOptionsCopy) {
                    activeOptions.add(option + expression.substring(optionIndex,i));
                }
                fillIfEmpty(activeOptions);
                optionIndex = i+1;
                continue;
            }

            /*
             * delimiter
             */
            if (!escape && !(subExpressionIndex > -1) && (currentChar == '|') && (bracketCounter == 0)) {
                Set<String> activeOptionsCopy = new LinkedHashSet<>(activeOptions);
                activeOptions.clear();
                for (String option : activeOptionsCopy) {
                    activeOptions.add(option + expression.substring(optionIndex,i));
                }
                optionIndex = i+1;
                localOptions.addAll(activeOptions);
                activeOptions = new LinkedHashSet<>();
                fillIfEmpty(activeOptions);
                continue;
            }

            escape = false;
        }

        /*
         * end of expression option
         */
        for (String option : activeOptions) {
            localOptions.add(option + expression.substring(optionIndex,expressionAsArray.length));
        }
        localOptions.remove("");

        return localOptions;
    }

    /**
     * Fill empty set with a single "" string
     *
     * @param set
     */
    private void fillIfEmpty(Set<String> set) {
        if (set.isEmpty()) {
            set.add("");
        }
    }

    /**
     * Checks if the input string is defined by this RegEx.
     *
     * @param str
     * @return
     */
    public boolean startsWith(String str) {
        return false;
    }

    /**
     * returns result after initialization - for testing purposes only
     * @return
     */
    public Set<String> getOptions() {
        return options;
    }
}
