package physics;

import java.util.*;

public class ShuntingYard {

            private enum Operator
            {
                ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4), POWER(5), SQRT(6), SINE( 7), COSINE(8),
                TANGENT(9), LOG(10), LN(11);
                final int precedence;
                Operator(int p) { precedence = p; }
            }

            private static Map<String, Operator> ops = new HashMap<String, Operator>() {{
                put("+", Operator.ADD);
                put("-", Operator.SUBTRACT);
                put("*", Operator.MULTIPLY);
                put("/", Operator.DIVIDE);
                put("^", Operator.POWER);
                put("sin", Operator.SINE);
                put("cos", Operator.COSINE);
                put("tan", Operator.TANGENT);
                put("log", Operator.LOG);
                put("ln", Operator.LN);
                put("sqrt", Operator.SQRT);

            }};

            private static boolean isHigerPrec(String op, String sub)
            {
                return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
            }

            public static String postfix(String infix) {
                StringBuilder output = new StringBuilder();
                Deque<String> stack = new LinkedList<>();

                for (String token : infix.split("\\s")) {
                    // operator
                    if (ops.containsKey(token)) {
                        while (!stack.isEmpty() && isHigerPrec(token, stack.peek()))
                            output.append(stack.pop()).append(' ');
                        stack.push(token);

                        // left parenthesis
                    } else if (token.equals("(")) {
                        stack.push(token);

                        // right parenthesis
                    } else if (token.equals(")")) {
                        while (!stack.peek().equals("("))
                            output.append(stack.pop()).append(' ');
                        stack.pop();

                        // digit                    
                    }else {
                        output.append(token).append(' ');
                    }
                }

                while (!stack.isEmpty())
                    output.append(stack.pop()).append(' ');

                return output.toString();
            }


    //public static void main(String[] args) {
     //   String function = "log ( sin ( sqrt ( x ^ 3 ) ) ) + y";
     //   String post = postfix(function);
     //   System.out.print(post);
    //}
}