package expression.exceptions;

import expression.AbstractUnaryOperation;
import expression.CustomExpression;
import expression.exceptions.parsingExceptions.OverflowException;

public class CheckedNegate extends AbstractUnaryOperation {
    public CheckedNegate(CustomExpression value) {
        super(value);
    }

    @Override
    protected int compute(int value) {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException(getOperation(), value);
        }
        return -value;
    }

    @Override
    protected String getOperation() {
        return "-";
    }
}
