package expression;

import expression.exceptions.ExpressionParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TripleExpression exp = new Divide(
                new Multiply(
                        new Multiply(
                                new Multiply(
                                        new Multiply(
                                                new Multiply(
                                                        new Const(1_000_000),
                                                        new Variable("x")),
                                                new Variable("x")),
                                        new Variable("x")),
                                new Variable("x")),
                        new Variable("x")),
                new Subtract(new Variable("x"), new Const(1)));
        System.out.println("Before parse: " + exp);
        ExpressionParser parser = new ExpressionParser();
        System.out.println(parser.parse("x + 1"));
        exp = parser.parse(exp.toString());
        System.out.println("After parse: " + exp);
        System.out.println("===========================================================");
        for (int x = 0; x <= 10; x++) {
            try {
                System.out.println(exp.evaluate(x, 0, 0));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}