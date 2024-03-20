package expression.generic.typeOperators;

import expression.exceptions.parsingExceptions.DivisionByZeroException;

public class ByteOperator implements TypeOperator<Byte> {
    @Override
    public Byte convert(int value) {
        return (byte) value;
    }

    @Override
    public Byte parseNumber(String value) {
        return Byte.parseByte(value);
    }

    @Override
    public Byte add(Byte value1, Byte value2) {
        return (byte) (value1 + value2);
    }

    @Override
    public Byte subtract(Byte value1, Byte value2) {
        return (byte) (value1 - value2);
    }

    @Override
    public Byte multiply(Byte value1, Byte value2) {
        return (byte) (value1 * value2);
    }

    @Override
    public Byte divide(Byte value1, Byte value2) {
        if (value2 == 0) {
            throw new DivisionByZeroException(String.valueOf(value1));
        }
        return (byte) (value1 / value2);
    }

    @Override
    public Byte negate(Byte value) {
        return multiply(value, (byte) -1);
    }
}
