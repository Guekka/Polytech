public class InputParser {
    private final static String OR = "+";
    private final static String NOT = "~";

    String input;
    long varCount;

    public InputParser(String input) {
        this.input = input;
        calculateVariableCount();
    }

    private void calculateVariableCount() {
        varCount = input.chars().filter(Character::isAlphabetic).distinct().count();
    }


    public long variableCount() {
        return varCount;
    }
}
