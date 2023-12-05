//package Java.InfixToPostfix;

import java.util.*;

class Conversion {

    private String infix;

    Conversion(String infix) {
        this.infix = infix;
    }

    public String getPostfix() {
        StringBuilder result = new StringBuilder();
        Stack<Character> operator = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            StringBuilder multiDigit = new StringBuilder();
            char c = infix.charAt(i);

            if (Character.isDigit(c)) {
                while (i < infix.length() && Character.isDigit(infix.charAt(i))) {
                    multiDigit.append(infix.charAt(i));
                    i++;
                }
                result.append(multiDigit).append(" ");
                i--;
            } else if (c == '(') {
                operator.push(c);
            } else if (c == ')') {
                while (operator.peek() != '(') {
                    result.append(operator.pop()).append(" ");
                }
                operator.pop();
            } else {
                if (c == '-' && Character.isDigit(infix.charAt(i + 1))) {
                    multiDigit.append(c);
                    i++;
                    while (i < infix.length() && Character.isDigit(infix.charAt(i))) {
                        multiDigit.append(infix.charAt(i));
                        i++;
                        if (i == infix.length()) {
                            break;
                        }
                    }
                    result.append(multiDigit).append(" ");
                    i--;
                } else if (operator.isEmpty() || operator.peek() == '(') {
                    operator.push(c);
                } else {
                    while (!operator.isEmpty() && checkPrecedence(c) <= checkPrecedence(operator.peek())) {
                        result.append(operator.pop()).append(" ");
                    }
                    operator.push(c);
                }
            }
        }

        while (!operator.isEmpty()) {
            result.append(operator.pop()).append(" ");
        }

        return result.toString();
    }

    public double evalValue(String expression) {
        Stack<String> operands = new Stack<>();
        String[] operators = expression.split(" ");
        double value = 0;
        int ctr = 1;

        for (int i = 0; i < operators.length; i++) {
            if (isOperator(operators[i]) == true) {
                double operand2 = Double.parseDouble(operands.pop());
                double operand1 = Double.parseDouble(operands.pop());

                switch (operators[i]) {
                    case "+":
                        value = operand1 + operand2;
                        System.out.println(ctr + ".) " + operand1 + " + " + operand2);
                        ctr++;
                        break;
                    case "-":
                        value = operand1 - operand2;
                        System.out.println(ctr + ".) " + operand1 + " - " + operand2);
                        ctr++;
                        break;
                    case "*":
                        value = operand1 * operand2;
                        System.out.println(ctr + ".) " + operand1 + " * " + operand2);
                        ctr++;
                        break;
                    case "/":
                        try {
                            value = operand1 / operand2;
                            System.out.println(ctr + ".) " + operand1 + " / " + operand2);
                            ctr++;
                        } catch (Exception e) {
                            System.out.println("Cannot divide by 0!");
                        }
                        break;
                    case "%":
                        value = operand1 % operand2;
                        System.out.println(ctr + ".) " + operand1 + " % " + operand2);
                        ctr++;
                        break;
                    case "^":
                        value = Math.pow(operand1, operand2);
                        System.out.println(ctr + ".) " + operand1 + " ^ " + operand2);
                        ctr++;
                        break;
                }
                operands.push(String.valueOf(value));
            } else {
                operands.push(operators[i]);
            }
        }

        return value;
    }

    public static int checkPrecedence(char c) {
        if (c == '+' || c == '-')
            return 1;
        if (c == '*' || c == '/' || c == '%')
            return 2;
        if (c == '^')
            return 3;
        return 0;
    }

    public static boolean isOperator(String c) {
        String[] operators = { "+", "-", "*", "/", "%", "^" };
        for (String operator : operators) {
            if (c.equals(operator)) {
                return true;
            }
        }
        return false;
    }
}

class InfixConversion {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // (2 - 4 / 4 ) * (8 / 2 - 3)
        // (6 - 4 + 2) / 1 * (10/5+(4*1))

        // String infix = "(-5+-1)*1";
        // System.out.printf("Enter equation: %s\n", infix);

        System.out.println("Please use \"-1 + -1\" or \"-1 - -1\" fornegativeintegers");
        System.out.print("Enter equation: ");
        String infix = scan.nextLine();

        infix = infix.replaceAll(" ", "");

        if (checkParenthesis(infix) == true) {
            Conversion convert = new Conversion(infix);
            String result = convert.getPostfix();

            System.out.println("Postfix: " + result);
            System.out.println("Value: " + convert.evalValue(result));
        } else {
            System.out.println("Missing Parenthesis!");
        }

        scan.close();
    }

    private static boolean checkParenthesis(String infix) {
        int parenthesisCnt = 0;

        for (int i = 0; i < infix.length(); i++) {
            if (infix.charAt(i) == '(' || infix.charAt(i) == ')') {
                parenthesisCnt++;
            }
        }

        return parenthesisCnt % 2 == 0;
    }
}