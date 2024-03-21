package expression.generic.typeOperators;

import expression.exceptions.parsingExceptions.DivisionByZeroException;

public class IntegerOperator implements TypeOperator<Integer> {
    @Override
    public Integer convert(int value) {
        return value;
    }

    @Override
    public Integer parseNumber(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public Integer add(Integer value1, Integer value2) {
        return value1 + value2;
    }

    @Override
    public Integer subtract(Integer value1, Integer value2) {
        return value1 - value2;
    }

    @Override
    public Integer multiply(Integer value1, Integer value2) {
        return value1 * value2;
    }

    @Override
    public Integer divide(Integer value1, Integer value2) {
        if (value2 == 0) {
            throw new DivisionByZeroException(String.valueOf(value1));
        }
        return value1 / value2;
    }

    @Override
    public Integer negate(Integer value) {
        return multiply(value, -1);
    }
}
