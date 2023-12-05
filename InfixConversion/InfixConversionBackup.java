//package Java.InfixToPostfix;

import java.util.*;

class InfixConversion {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // (2 - 4 / 4 ) * (8 / 2 - 3)
        // (6 - 4 + 2) / 1 * (10/5+(4*1))

        String infix = "10 / (3 + 2)";
        System.out.println("Enter equation: " + infix);
        // String infix = scan.nextLine();

        infix = infix.replaceAll(" ", "");

        if (checkParenthesis(infix) == true) {
            Conversion convert = new Conversion(infix);

            System.out.println("Postfix: " + convert.getPostfix());
            System.out.println("Value: " + convert.evalValue(convert.getPostfix()));
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

class Conversion {
    Conversion(String infix) {
        this.infix = infix;
    }

    String infix;

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
                if (operator.isEmpty() || operator.peek() == '(') {
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
        ArrayList<String> solution = new ArrayList<>();
        ArrayList<String> operand = new ArrayList<>();
        String[] operators = expression.split(" ");
        double value = 0;

        for (int i = 0; i < operators.length; i++) {
            solution.add(operators[i]);
            if (isOperator(operators[i]) == true) {
                double operand2 = Double.parseDouble(operands.pop());
                double operand1 = Double.parseDouble(operands.pop());

                switch (operators[i]) {
                    case "+":
                        value = operand1 + operand2;
                        System.out.println(operand1 + " + " + operand2);
                        break;
                    case "-":
                        value = operand1 - operand2;
                        System.out.println(operand1 + " - " + operand2);
                        break;
                    case "*":
                        value = operand1 * operand2;
                        System.out.println(operand1 + " * " + operand2);
                        break;
                    case "/":
                        try {
                            value = operand1 / operand2;
                            System.out.println(operand1 + " / " + operand2);
                        } catch (Exception e) {
                            System.out.println("Cannot divide by 0!");
                        }
                        break;
                    case "%":
                        value = operand1 % operand2;
                        System.out.println(operand1 + " % " + operand2);
                        break;
                    case "^":
                        value = Math.pow(operand1, operand2);
                        System.out.println(operand1 + " ^ " + operand2);
                        break;
                }
                operands.push(String.valueOf(value));
            } else {
                operands.push(operators[i]);
                operand.add(operators[i]);
            }
            // System.out.println(i + " " + solution.toString());
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