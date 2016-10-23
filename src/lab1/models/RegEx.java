package lab1.models;


import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.io.Serializable;

/**
 * Created by pero5ar on 23.10.2016..
 */
public class RegEx implements Serializable {
    private final char EPSILON = '$';
    private final char KLEEN ='*';

    private String definition;
    private Set<String> options;

	public RegEx(String definition) {
		this.definition = definition;
	}

    /**
     * Generates possible options from regex expression.
     * ESCAPE CHARACTER, EPSILON CLOSURE AND KLEEN STAR STILL UNRESOLVED.
     *
     * @param expression    regex expression
     * @return
     */
    private Set<String> generateOptions(String expression) {
        char[] expressionAsArray = expression.toCharArray();
        Set<String> localOptions = new LinkedHashSet<>();

        int optionIndex = 0;

        String subExpression = "";
        int subExpressionIndex = -1;

        boolean escape = false;

        Set<String> activeOptions;

        for (int i = 0; i < expressionAsArray.length; i++) {
            char currentChar = expressionAsArray[i];

            if (i == 0) {
                activeOptions = new LinkedHashSet<>();
                activeOptions.add("");
            }

            if (!escape && (currentChar == '\\')) {
                escape = true;
                continue;
            }

            /*
             * close bracket
             */
            if ((subExpressionIndex > -1) && (currentChar == ')')) {
                Set<String> activeOptionsCopy = new LinkedHashSet<>(activeOptions);
                activeOptions.clear();
                for (String option : activeOptionsCopy) {
                    for (String subOption : generateOptions(expression.substring(subExpressionIndex, i))) {
                        activeOptions.add(option + subOption);
                    }
                }
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
                optionIndex = i+1;
                continue;
            }

            /*
             * delimiter
             */
            if (!escape && (subExpressionIndex > -1) && (currentChar == '|')) {
                Set<String> activeOptionsCopy = new LinkedHashSet<>(activeOptions);
                activeOptions.clear();
                for (String option : activeOptionsCopy) {
                    activeOptions.add(option + expression.substring(optionIndex,i));
                }
                optionIndex = i+1;
                localOptions.addAll(activeOptions);
                continue;
            }

            escape = false;
        }

        return localOptions;
    }


    public boolean startsWith(String str) {
        return false;
    }
}
