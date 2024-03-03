package expression.exceptions;

import expression.*;
import expression.exceptions.parsingExceptions.*;
import expression.parser.BaseParser;
import expression.parser.CharSource;
import expression.parser.StringSource;
import expression.parser.TripleParser;

import java.util.*;

public class ExpressionParser implements TripleParser, ListParser {
    private Set<String> vars;

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
        private int tokens = 1;
        private final Map<Character, Character> parens = new HashMap<>(Map.of(
                '(', ')',
                '[', ']',
                '{', '}'
        ));

        private ExpParser(CharSource source) {
            super(source);
        }

        private CustomExpression checkParsing() {
            CustomExpression result = parseExpression();
            if (eof()) {
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
                skipWhitespace();
                CustomExpression argument = parseExpression();
                skipWhitespace();
                if (test(parens.get(parenthesis))) {
                    take();
                } else if (eof()) {
                    throw new IncorrectBracketSequenceException("closing");
                } else {
                    throw new UnexpectedSymbolException(String.valueOf(take()));
                }
                tokens++;
                return argument;
            } else if (between('x', 'z')) {
                tokens++;
                return new Variable(String.valueOf(take()));
            } else if (take('$')) {
                String var = getVarName();
                if (vars.contains("$" + var)) {
                    tokens++;
                    return new Variable(Integer.parseInt(var));
                } else {
                    throw new UnexpectedSymbolException("$");
                }
            } else if (between('0', '9')) {
                tokens++;
                return new Const(getNumber(false));
            } else if (take('-')) {
                if (between('0', '9')) {
                    tokens++;
                    return new Const(getNumber(true));
                } else {
                    return new CheckedNegate(parseAtom());
                }
            } else if (lookup() == 't' || lookup() == 'l') {
                if (take('t') && take('0')) {
                    if (eof()) {
                        throw new InvalidTokenException(String.valueOf(tokens));
                    } else if (!(test('(') || Character.isWhitespace(lookup()))) {
                        throw new InvalidExpStructureException();
                    }
                    return new Tzero(parseAtom());
                } else if (take('l') && take('0')) {
                    if (eof()) {
                        throw new InvalidTokenException(String.valueOf(tokens));
                    } else if (!(test('(') || Character.isWhitespace(lookup()))) {
                        throw new InvalidExpStructureException();
                    }
                    return new Lzero(parseAtom());
                } else {
                    throw new UnexpectedSymbolException(String.valueOf(take()));
                }
            } else if (eof() || take('*') || take('/') ||
                    take('+') || take('-')) {
                if (tokens == 1 && eof()) {
                    throw new InvalidTokenException("any");
                } else {
                    throw new InvalidTokenException(String.valueOf(tokens));
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
            StringBuilder var = new StringBuilder();
            while (between('0', '9')) {
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
