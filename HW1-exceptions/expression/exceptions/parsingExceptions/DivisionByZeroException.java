package expression.exceptions.parsingExceptions;

public class DivisionByZeroException extends ComputingException {
    public DivisionByZeroException(int value) {
        super("Tried to divide " + value + " by 0");
    }
}
