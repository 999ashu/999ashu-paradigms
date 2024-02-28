package expression.generic;

public class GenericTabulator implements Tabulator {
    protected GenericTabulator() {

    }

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return new Object[0][][];
    }
}
