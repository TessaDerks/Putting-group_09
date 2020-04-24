package physics;

import java.util.*;

public class PostFixCalculator {

    private static Double operand1, operand2;
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

    public static Double calculate(String input, Vector2d coordinates) {
        SinglyLinkedListStack<Double> stack = new SinglyLinkedListStack<>();

        String[] inputs = input.split(" ");
        for ( int i = 0; i < inputs.length; i++) {
         if(inputs[i].equals("x")) {
            String x = removeX(coordinates);
            inputs[i] = x;
         
        } else if (inputs[i].equals("y")) {
            String y = removeY(coordinates);
            inputs[i] = y;
        } else if (inputs[i].equals("pi")) {
            String pi = String.valueOf(Math.PI);
            inputs[i] = pi;
        } else if (inputs[i].equals("e")) {
            String e = String.valueOf(Math.E);
            inputs[i] = e;
        }
        }

        return handleCalculation(stack, inputs);
    }

    private static Double handleCalculation(SinglyLinkedListStack<Double> stack, String[] el) {

        for(int i = 0; i < el.length; i++) {
            if( el[i].equals(ADD) || el[i].equals(SUB) || el[i].equals(MUL) || el[i].equals(DIV) || el[i].equals(POW) ) {

                operand2 = stack.pop();
                operand1 = stack.pop();
                switch(el[i]) {
                    case ADD: {
                        Double local = operand1 + operand2;
                        stack.push(local);
                        break;
                    }

                    case SUB: {
                        Double local = operand1 - operand2;
                        stack.push(local);
                        break;
                    }

                    case MUL: {
                        Double local = operand1 * operand2;
                        stack.push(local);
                        break;
                    }

                    case DIV: {
                        Double local = operand1 / operand2;
                        stack.push(local);
                        break;
                    }
                    case POW: {
                        Double local = Math.pow(operand1, operand2);
                        stack.push(local);
                        break;
                    }
                }
            }
                    else if (el[i].equals(SQRT) || el[i].equals(SIN) || el[i].equals(COS) || el[i].equals(TAN) || el[i].equals(LOG) || el[i].equals(LN) ) {
                        operand1 = stack.pop();
                        switch(el[i]) {
                        case SQRT: {
                        Double local = Math.sqrt(operand1);
                        stack.push(local);
                        break;
                        }
                        case SIN: {
                            Double local = Math.sin(operand1);
                            stack.push(local);
                            break;
                        }
                        case COS: {
                            Double local = Math.cos(operand1);
                            stack.push(local);
                            break;
                        }
                        case TAN: {
                            Double local = Math.tan(operand1);
                            stack.push(local);
                            break;
                        }
                        case LOG: {
                            Double local = Math.log10(operand1);
                            stack.push(local);
                            break;
                        }
                        case LN: {
                            Double local = Math.log(operand1);
                            stack.push(local);
                            break;
                        }

                    }
                }
             else {
                stack.push(Double.parseDouble(el[i]));
            }
        }

        return stack.pop();
    }
    public static String removeX(Vector2d coordinates) {

        return String.valueOf(coordinates.get_x());

    }
    public static String removeY(Vector2d coordinates) {

        return String.valueOf(coordinates.get_y());

    }
    

}

