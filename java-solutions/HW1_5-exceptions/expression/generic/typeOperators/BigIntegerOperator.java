package expression.generic.typeOperators;

import expression.exceptions.parsingExceptions.DivisionByZeroException;

import java.math.BigInteger;

public class BigIntegerOperator implements TypeOperator<BigInteger> {
    @Override
    public BigInteger convert(int value) {
        return BigInteger.valueOf(value);
    }

    @Override
    public BigInteger parseNumber(String value) {
        return new BigInteger(value);
    }

    @Override
    public BigInteger add(BigInteger value1, BigInteger value2) {
        return value1.add(value2);
    }

    @Override
    public BigInteger subtract(BigInteger value1, BigInteger value2) {
        return value1.subtract(value2);
    }

    @Override
    public BigInteger multiply(BigInteger value1, BigInteger value2) {
        return value1.multiply(value2);
    }

    @Override
    public BigInteger divide(BigInteger value1, BigInteger value2) {
        if (value2.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException(String.valueOf(value1));
        }
        return value1.divide(value2);
    }

    @Override
    public BigInteger negate(BigInteger value) {
        return multiply(value, BigInteger.valueOf(-1));
    }
}
