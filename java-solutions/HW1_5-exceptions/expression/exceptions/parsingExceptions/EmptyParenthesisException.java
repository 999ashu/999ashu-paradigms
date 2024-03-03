package expression.exceptions.parsingExceptions;

public class EmptyParenthesisException extends ParsingException{
    public EmptyParenthesisException() {
        super("Received empty brackets");
    }
}
