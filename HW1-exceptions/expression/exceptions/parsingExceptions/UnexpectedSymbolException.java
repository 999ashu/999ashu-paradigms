package expression.exceptions.parsingExceptions;

public class UnexpectedSymbolException extends ParsingException {
    public UnexpectedSymbolException(String symbol) {
        super("An unexpected character received: '" + symbol + "'");
    }
}
