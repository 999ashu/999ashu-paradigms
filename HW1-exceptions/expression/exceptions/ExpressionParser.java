package expression.exceptions;

import expression.*;
import expression.parser.*;
import expression.exceptions.parsingExceptions.*;

public class ExpressionParser implements TripleParser {
    public CustomExpression parse(String expression) {
        return new ExpParser(new StringSource(expression)).checkParsing();
    }

    private static final class ExpParser extends BaseParser {
        boolean closing = true;
        int arguments = 1;

        private ExpParser(CharSource source) {
            super(source);
        }

        private CustomExpression checkParsing() {
            CustomExpression result = parseExpression();
            if (!closing) {
                throw new IncorrectBracketSequenceException("closing");
            } else if (eof()) {
                return result;
            } else if (take(')')) {
                throw new IncorrectBracketSequenceException("opening");
            } else {
                throw new InvalidExpStructureException();
            }
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
                closing = false;
                skipWhitespace();
                CustomExpression argument = parseExpression();
                skipWhitespace();
                if (test(')')) {
                    closing = true;
                    take();
                }
                arguments++;
                return argument;
            } else if (between('x', 'z')) {
                arguments++;
                return new Variable(String.valueOf(take()));
            } else if (take('-')) {
                if (between('0', '9')) {
                    arguments++;
                    return new Const(getNumber(true));
                } else {
                    return new CheckedNegate(parseAtom());
                }
            } else if (between('0', '9')) {
                arguments++;
                return new Const(getNumber(false));
            } else if (eof() || take('*') || take('/') ||
                    take('+') || take('-')) {
                if (arguments == 1 && eof()) {
                    throw new InvalidArgumentException("any");
                } else {
                    throw new InvalidArgumentException(String.valueOf(arguments));
                }
            } else if (test(')')) {
                throw new EmptyParenthesisException();
            } else {
                throw new UnexpectedSymbolException(String.valueOf(take()));
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
                case "+" -> new CheckedAdd(argument1, argument2);
                case "-" -> new CheckedSubtract(argument1, argument2);
                case "*" -> new CheckedMultiply(argument1, argument2);
                case "/" -> new CheckedDivide(argument1, argument2);
                default -> throw new UnexpectedSymbolException(operator);
            };
        }
    }
}