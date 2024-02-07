package expression.exceptions.parsingExceptions;

public class InvalidArgumentException extends ParsingException {
    public InvalidArgumentException(String position) {
        super("Not received " + position + " argument");
    }
}
