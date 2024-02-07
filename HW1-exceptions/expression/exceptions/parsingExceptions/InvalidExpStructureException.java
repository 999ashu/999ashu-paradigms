package expression.exceptions.parsingExceptions;

public class InvalidExpStructureException extends ParsingException {
    public InvalidExpStructureException() {
        super("Expression has invalid structure or symbol");
    }
}
