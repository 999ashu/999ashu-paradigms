package expression.parser;

import expression.exceptions.parsingExceptions.UnexpectedSymbolException;

public class BaseParser {
    private static final char END = '\0';
    private final CharSource source;
    private char ch;

    protected BaseParser(final CharSource source) {
        this.source = source;
        take();
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected void expect(final char expected) {
        if (test(expected)) {
            take();
        } else {
            throw new UnexpectedSymbolException(String.valueOf(take()));
        }
    }

    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected char lookup() {
        return ch;
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            ch = source.hasNext() ? source.next() : END;
        }
    }

    protected boolean eof() {
        return ch == END;
    }
}
