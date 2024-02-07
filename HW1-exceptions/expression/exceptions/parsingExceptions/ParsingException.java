package expression.exceptions.parsingExceptions;

public abstract class ParsingException extends RuntimeException {
    public ParsingException(String message) {
        super(message);
    }
}
