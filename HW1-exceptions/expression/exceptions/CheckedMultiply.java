package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.CustomExpression;
import expression.exceptions.parsingExceptions.OverflowException;

public class CheckedMultiply extends AbstractBinaryOperation {
    public CheckedMultiply(CustomExpression value1, CustomExpression value2) {
        super(value1, value2);
    }

    @Override
    protected int compute(int value1, int value2) {
        if ((value1 > 0 && value2 > 0 && value1 > Integer.MAX_VALUE / value2) ||
                (value1 < 0 && value2 < 0 && value1 < Integer.MAX_VALUE / value2) ||
                (value1 > 0 && value2 < 0 && value2 < Integer.MIN_VALUE / value1) ||
                (value1 < 0 && value2 > 0 && value1 < Integer.MIN_VALUE / value2)) {
            throw new OverflowException(getOperation(), value1, value2);
        }
        return value1 * value2;
    }

    @Override
    protected String getOperation() {
        return "*";
    }
}
