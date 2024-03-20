package expression.generic;

import expression.exceptions.parsingExceptions.ComputingException;
import expression.generic.genericExpression.TripleExpression;
import expression.generic.typeOperators.*;

import java.util.HashMap;
import java.util.Map;

public class GenericTabulator implements Tabulator {
    private static final Map<String, TypeOperator<? extends Number>> typeOperators = new HashMap<>(Map.of(
            "u", new IntegerOperator(),
            "i", new CheckedIntegerOperator(),
            "b", new ByteOperator(),
            "d", new DoubleOperator(),
            "bi", new BigIntegerOperator()
    ));

    public static void main(String[] args) {
        TypeOperator<? extends Number> mode = typeOperators.get(args[0].substring(1));
        printTable(mode, args[1]);
    }

    private static <T extends Number> void printTable(TypeOperator<T> mode, String arg) {
        TripleExpression<T> expr = new GenericParser<>(mode).parse(arg);
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                for (int k = -2; k <= 2; k++) {
                    System.out.println("x=" + i + " y=" + j + " z=" + k + "; res=" +
                            expr.evaluate(mode.convert(i), mode.convert(j), mode.convert(k))
                    );
                }
            }
        }
    }

    @Override
    public Object[][][] tabulate(
            String mode, String expression,
            int x1, int x2, int y1, int y2, int z1, int z2
    ) {
        return makeTable(typeOperators.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    public <T extends Number> Object[][][] makeTable(TypeOperator<T> typeOperator, String expression,
                                                     int x1, int x2, int y1, int y2, int z1, int z2
    ) {
        TripleExpression<T> expr = new GenericParser<>(typeOperator).parse(expression);
        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        table[i - x1][j - y1][k - z1] = expr.evaluate(
                                typeOperator.convert(i), typeOperator.convert(j), typeOperator.convert(k)
                        );
                    } catch (ComputingException exception) {
                        table[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return table;
    }
}
