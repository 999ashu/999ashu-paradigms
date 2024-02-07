package expression.exceptions.parsingExceptions;

public class DivisionByZeroException extends ParsingException {
    public DivisionByZeroException(int value) {
        super("Tried to divide " + value + " by 0");
    }
}
