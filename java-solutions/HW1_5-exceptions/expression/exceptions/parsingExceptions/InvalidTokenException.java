package expression.exceptions.parsingExceptions;

public class InvalidTokenException extends ParsingException {
    public InvalidTokenException(String position) {
        super("Not received " + position + " token");
    }
}
