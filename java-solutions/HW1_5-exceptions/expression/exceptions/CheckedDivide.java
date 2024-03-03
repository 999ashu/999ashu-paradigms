package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.CustomExpression;
import expression.exceptions.parsingExceptions.DivisionByZeroException;
import expression.exceptions.parsingExceptions.OverflowException;

public class CheckedDivide extends AbstractBinaryOperation {
    public CheckedDivide(CustomExpression value1, CustomExpression value2) {
        super(value1, value2);
    }

    @Override
    protected int compute(int value1, int value2) {
        if (value2 == 0) {
            throw new DivisionByZeroException(value1);
        } else if (value1 == Integer.MIN_VALUE && value2 == -1) {
            throw new OverflowException(getOperation(), value1, value2);
        }
        return value1 / value2;
    }

    @Override
    protected String getOperation() {
        return "/";
    }
}
