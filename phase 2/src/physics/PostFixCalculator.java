package physics;

import org.jetbrains.annotations.NotNull;

public class PostFixCalculator {

    private static final String ADD = "+";
    private static final String SUB = "-";
    private static final String MUL = "*";
    private static final String DIV = "/";
    private static final String POW = "^";
    private static final String SQRT = "sqrt";
    private static final String SIN = "sin";
    private static final String COS = "cos";
    private static final String TAN = "tan";
    private static final String LOG = "log";
    private static final String LN = "ln";
    //</editor-fold>

    /**
     *
     * @param input String, Function that you would want to convert
     * @param coordinates Vector2d, the value that you want to calculate
     * @return Double, z value for the coordinate in a certain function
     */
    public static Double calculate(@NotNull String input, Vector2d coordinates) {
        SinglyLinkedListStack<Double> stack = new SinglyLinkedListStack<>();

        String[] inputs = input.split(" ");
        for ( int i = 0; i < inputs.length; i++) {
            switch (inputs[i]) {
                case "x":
                    String x = removeX(coordinates);
                    inputs[i] = x;

                    break;
                case "y":
                    String y = removeY(coordinates);
                    inputs[i] = y;
                    break;
                case "pi":
                    String pi = String.valueOf(Math.PI);
                    inputs[i] = pi;
                    break;
                case "e":
                    String e = String.valueOf(Math.E);
                    inputs[i] = e;
                    break;
            }
        }

        return handleCalculation(stack, inputs);
    }

    /**
     *
     * @param stack Stack of Doubles, doubles you want to review
     * @param el Array of Strings, contains al standard values (x, y, e, pi)
     * @return Double, calculated value
     */
    private static Double handleCalculation(SinglyLinkedListStack<Double> stack, String @NotNull [] el) {
        double operand1, operand2;

        for (String s : el) {
            //<editor-fold desc="Global Variables">
            if (s.equals(ADD) || s.equals(SUB) || s.equals(MUL) || s.equals(DIV) || s.equals(POW)) {

                operand2 = stack.pop();
                operand1 = stack.pop();
                switch (s) {
                    case ADD: {
                        double local = operand1 + operand2;
                        stack.push(local);
                        break;
                    }

                    case SUB: {
                        double local = operand1 - operand2;
                        stack.push(local);
                        break;
                    }

                    case MUL: {
                        double local = operand1 * operand2;
                        stack.push(local);
                        break;
                    }

                    case DIV: {
                        double local = operand1 / operand2;
                        stack.push(local);
                        break;
                    }
                    case POW: {
                        double local = Math.pow(operand1, operand2);
                        stack.push(local);
                        break;
                    }
                }
            } else if (s.equals(SQRT) || s.equals(SIN) || s.equals(COS) || s.equals(TAN) || s.equals(LOG) || s.equals(LN)) {
                operand1 = stack.pop();
                switch (s) {
                    case SQRT: {
                        double local = Math.sqrt(operand1);
                        stack.push(local);
                        break;
                    }
                    case SIN: {
                        double local = Math.sin(operand1);
                        stack.push(local);
                        break;
                    }
                    case COS: {
                        double local = Math.cos(operand1);
                        stack.push(local);
                        break;
                    }
                    case TAN: {
                        double local = Math.tan(operand1);
                        stack.push(local);
                        break;
                    }
                    case LOG: {
                        double local = Math.log10(operand1);
                        stack.push(local);
                        break;
                    }
                    case LN: {
                        double local = Math.log(operand1);
                        stack.push(local);
                        break;
                    }

                }
            } else {
                stack.push(Double.parseDouble(s));
            }
        }

        return stack.pop();
    }

    /**
     *
     * @param coordinates Vector2d
     * @return String, value of x
     */
    public static @NotNull String removeX(@NotNull Vector2d coordinates) {

        return String.valueOf(coordinates.get_x());

    }

    /**
     *
     * @param coordinates Vector2d
     * @return String, value of y
     */
    public static @NotNull String removeY(@NotNull Vector2d coordinates) {

        return String.valueOf(coordinates.get_y());

    }
    

}

