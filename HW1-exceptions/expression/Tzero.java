package expression;

public class Tzero extends AbstractUnaryOperation{
    public Tzero(CustomExpression value) {
        super(value);
    }

    @Override
    protected int compute(int value) {
        return Integer.numberOfTrailingZeros(value);
    }

    @Override
    protected String getOperation() {
        return "t0";
    }
}
