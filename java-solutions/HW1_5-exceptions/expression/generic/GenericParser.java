package expression.generic;

import expression.generic.genericExpression.*;
import expression.exceptions.parsingExceptions.InvalidExpStructureException;
import expression.generic.typeOperators.TypeOperator;
import expression.parser.*;

public class GenericParser<T extends Number> {
    private final TypeOperator<T> typeOperator;

    public GenericParser(TypeOperator<T> typeOperator) {
        this.typeOperator = typeOperator;
    }

    public TripleExpression<T> parse(String expression) {
        return new Parser(new StringSource(expression)).parseExpression();
    }

    private final class Parser extends BaseParser {
        private Parser(CharSource source) {
            super(source);
        }

        private TripleExpression<T> parseExpression() {
            skipWhitespace();
            TripleExpression<T> argument = parseTerm();
            while (true) {
                skipWhitespace();
                if (test('+') || test('-')) {
                    argument = makeExpression(String.valueOf(take()), argument, parseTerm());
                } else {
                    return argument;
                }
            }
        }

        private TripleExpression<T> parseTerm() {
            skipWhitespace();
            TripleExpression<T> argument = parseAtom();
            while (true) {
                skipWhitespace();
                if (test('*') || test('/')) {
                    argument = makeExpression(String.valueOf(take()), argument, parseAtom());
                } else {
                    return argument;
                }
            }
        }

        private TripleExpression<T> parseAtom() {
            skipWhitespace();
            if (take('(')) {
                skipWhitespace();
                TripleExpression<T> argument = parseExpression();
                skipWhitespace();
                expect(')');
                return argument;
            } else if (between('x', 'z')) {
                return new Variable<>(String.valueOf(take()));
            } else if (take('-')) {
                if (between('0', '9')) {
                    return new Const<>(typeOperator.parseNumber(getNumber(true)));
                } else {
                    return new Negate<>(parseAtom(), typeOperator);
                }
            } else {
                return new Const<>(typeOperator.parseNumber(getNumber(false)));
            }
        }

        private String getNumber(boolean negative) {
            StringBuilder number = negative ? new StringBuilder().append('-') : new StringBuilder();
            do {
                number.append(take());
            } while (between('0', '9'));
            return number.toString();
        }

        private TripleExpression<T> makeExpression(String operator, TripleExpression<T> argument1, TripleExpression<T> argument2) {
            return switch (operator) {
                case "+" -> new Add<>(argument1, argument2, typeOperator);
                case "-" -> new Subtract<>(argument1, argument2, typeOperator);
                case "*" -> new Multiply<>(argument1, argument2, typeOperator);
                case "/" -> new Divide<>(argument1, argument2, typeOperator);
                default -> throw new InvalidExpStructureException();
            };
        }
    }
}
