package expression.exceptions.parsingExceptions;

public class IncorrectBracketSequenceException extends ParsingException {
    public IncorrectBracketSequenceException(String type) {
        super("Not enough " + type + " brackets");
    }
}
