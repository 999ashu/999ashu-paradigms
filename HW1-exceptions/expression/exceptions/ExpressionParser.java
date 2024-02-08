package expression.exceptions;

import expression.*;
import expression.parser.*;
import expression.exceptions.parsingExceptions.*;

import java.util.*;

public class ExpressionParser implements TripleParser, ListParser {
    Set<String> vars;

    @Override
    public CustomExpression parse(String expression) {
        return new ExpParser(new StringSource(expression)).checkParsing();
    }

    @Override
    public ListExpression parse(String expression, List<String> variables) {
        vars = new LinkedHashSet<>(variables);
        return new ExpParser(new StringSource(expression)).checkParsing();
    }

    private final class ExpParser extends BaseParser {
        boolean closing = true;
        int arguments = 1;
        Map<Character, Character> parens = new HashMap<>(Map.of(
                '(', ')',
                '[', ']',
                '{', '}'
        ));

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
            if (parens.containsKey(lookup())) {
                char parenthesis = take();
                closing = false;
                skipWhitespace();
                CustomExpression argument = parseExpression();
                skipWhitespace();
                if (test(parens.get(parenthesis))) {
                    closing = true;
                    take();
                }
                arguments++;
                return argument;
            } else if (between('x', 'z')) {
                arguments++;
                return new Variable(String.valueOf(take()));
            } else if (take('$')) {
                String var = getVarName();
                if (vars.contains(var)) {
                    return new Variable(var);
                } else {
                    throw new UnexpectedSymbolException(String.valueOf(var.charAt(0)));
                }
            } else if (lookup() == 't' || lookup() == 'l') {
                if (take('t') && take('0') && (lookup() == '(' || Character.isWhitespace(lookup()))) {
                    return new tzero(parseAtom());
                } else if (take('l') && take('0') && (lookup() == '(' || Character.isWhitespace(lookup()))) {
                    return new lzero(parseAtom());
                } else {
                    throw new UnexpectedSymbolException(String.valueOf(take()));
                }
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

        private String getVarName() {
            StringBuilder var = new StringBuilder().append("$");
            while (Character.isDigit(lookup())) {
                if (eof()) {
                    break;
                }
                var.append(take());
            }
            return var.toString();
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