package expression.parser;

import expression.*;
import expression.exceptions.parsingExceptions.InvalidExpStructureException;

public class ExpressionParser implements TripleParser {
    public TripleExpression parse(String expression) {
        return new Parser(new StringSource(expression)).parseExpression();
    }

    private static final class Parser extends BaseParser {
        private Parser(CharSource source) {
            super(source);
        }

        private CustomExpression parseExpression() {
            skipWhitespace();
            CustomExpression argument = parseTerm();
            while (true) {
                skipWhitespace();
                if (test('+') || test('-')) {
                    argument = makeExpression(String.valueOf(take()), argument, parseTerm());
                } else {
                    return argument;
                }
            }
        }

        private CustomExpression parseTerm() {
            skipWhitespace();
            CustomExpression argument = parseAtom();
            while (true) {
                skipWhitespace();
                if (test('*') || test('/')) {
                    argument = makeExpression(String.valueOf(take()), argument, parseAtom());
                } else {
                    return argument;
                }
            }
        }

        private CustomExpression parseAtom() {
            skipWhitespace();
            if (take('(')) {
                skipWhitespace();
                CustomExpression argument = parseExpression();
                skipWhitespace();
                expect(')');
                return argument;
            } else if (between('x', 'z')) {
                return new Variable(String.valueOf(take()));
            } else if (take('-')) {
                if (between('0', '9')) {
                    return new Const(getNumber(true));
                } else {
                    return new Negate(parseAtom());
                }
            } else {
                return new Const(getNumber(false));
            }
        }

        private int getNumber(boolean negative) {
            StringBuilder number = negative ? new StringBuilder().append('-') : new StringBuilder();
            do {
                number.append(take());
            } while (between('0', '9'));
            return Integer.parseInt(number.toString());
        }

        private CustomExpression makeExpression(String operator, CustomExpression argument1, CustomExpression argument2) {
            return switch (operator) {
                case "+" -> new Add(argument1, argument2);
                case "-" -> new Subtract(argument1, argument2);
                case "*" -> new Multiply(argument1, argument2);
                case "/" -> new Divide(argument1, argument2);
                default -> throw new InvalidExpStructureException();
            };
        }
    }
}
