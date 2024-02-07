package expression.exceptions.parsingExceptions;

public class OverflowException extends ParsingException {
    public OverflowException(String operator, int value1, int value2) {
        super("Operation of '" + operator + "' is overflown: (" + value1 + " " + operator + " " + value2 + ")");
    }

    public OverflowException(String operator, int value) {
        super("Operation of '" + operator + "' is overflown: " + operator + "(" + value + ")");
    }
}
