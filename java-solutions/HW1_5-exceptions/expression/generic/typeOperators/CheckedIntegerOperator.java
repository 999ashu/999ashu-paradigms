package expression.generic.typeOperators;

import expression.exceptions.parsingExceptions.*;

public class CheckedIntegerOperator implements TypeOperator<Integer> {
    @Override
    public Integer convert(int value) {
        return value;
    }

    @Override
    public Integer parseNumber(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public Integer add(Integer value1, Integer value2) throws ComputingException {
        if ((value2 > 0 && value1 > Integer.MAX_VALUE - value2) ||
                (value2 < 0 && value1 < Integer.MIN_VALUE - value2)) {
            throw new OverflowException("+", value1, value2);
        }
        return value1 + value2;
    }

    @Override
    public Integer subtract(Integer value1, Integer value2) throws ComputingException {
        if ((value2 < 0 && value1 > Integer.MAX_VALUE + value2) ||
                (value2 > 0 && value1 < Integer.MIN_VALUE + value2)) {
            throw new OverflowException("-", value1, value2);
        }
        return value1 - value2;
    }

    @Override
    public Integer multiply(Integer value1, Integer value2) throws ComputingException {
        if ((value1 > 0 && value2 > 0 && value1 > Integer.MAX_VALUE / value2) ||
                (value1 < 0 && value2 < 0 && value1 < Integer.MAX_VALUE / value2) ||
                (value1 > 0 && value2 < 0 && value2 < Integer.MIN_VALUE / value1) ||
                (value1 < 0 && value2 > 0 && value1 < Integer.MIN_VALUE / value2)) {
            throw new OverflowException("*", value1, value2);
        }
        return value1 * value2;
    }

    @Override
    public Integer divide(Integer value1, Integer value2) throws ComputingException {
        if (value2 == 0) {
            throw new DivisionByZeroException(String.valueOf(value2));
        } else if (value1 == Integer.MIN_VALUE && value2 == -1) {
            throw new OverflowException("/", value1, value2);
        }
        return value1 / value2;
    }

    @Override
    public Integer negate(Integer value) throws ComputingException {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException("-", value);
        }
        return multiply(value, -1);
    }
}
