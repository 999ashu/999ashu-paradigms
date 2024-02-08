package expression;

public class lzero extends AbstractUnaryOperation{
    public lzero(CustomExpression value) {
        super(value);
    }

    @Override
    protected int compute(int value) {
        if (value == 0) {
            return 32;
        }
        return 32 - Integer.toBinaryString(value).length();
    }

    @Override
    protected String getOperation() {
        return "l0";
    }
}
