package expression;

public class Lzero extends AbstractUnaryOperation{
    public Lzero(CustomExpression value) {
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
