//package Java.InfixToPostfix;

import java.util.*;

class Conversion {
    Conversion(String oldInfix) {
        String infix = oldInfix;
    }

    public String getPostfix(String infix) {
        String result = "";
        char c;
        Stack<Character> operator = new Stack<>();

        for (int i = 0; i < infix.length(); ++i) {
            c = infix.charAt(i);

            if (Character.isDigit(c) || Character.isLetter(c)) {
                result += c;
            } else if (c == '(') {
                operator.push(c);
            } else if (c == ')') {
                while (operator.peek() != '(') {
                    result += operator.pop();
                }
                operator.pop();
            } else {
                while (!operator.isEmpty() && !(operator.peek() == '(')
                        && checkPrecedence(c) <= checkPrecedence(operator.peek())) {
                    result += operator.pop();
                }
                operator.push(c);
            }
        }

        while (!operator.isEmpty()) {
            result += operator.pop();
        }

        return result;
    }

    public String getPrefix(String infix) {
        char c;
        Stack<Character> operators = new Stack<>();
        Stack<String> operands = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            c = infix.charAt(i);

            if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.empty() && operators.peek() != '(') {
                    String operand1 = operands.peek();
                    operands.pop();
                    String operand2 = operands.peek();
                    operands.pop();
                    char operator = operators.peek();
                    operators.pop();
                    String temp = operator + operand2 + operand1;
                    operands.push(temp);
                }
                operators.pop();
            } else if (!isOperator(c)) {
                operands.push(c + "");
            } else {
                while (!operators.empty() &&
                        checkPrecedence(c) <= checkPrecedence(operators.peek())) {

                    String operand1 = operands.peek();
                    operands.pop();
                    String operand2 = operands.peek();
                    operands.pop();
                    char operator = operators.peek();
                    operators.pop();
                    String temp = operator + operand2 + operand1;
                    operands.push(temp);
                }
                operators.push(c);
            }
        }

        while (!operators.empty()) {
            String operand1 = operands.peek();
            operands.pop();
            String operand2 = operands.peek();
            operands.pop();
            char operator = operators.peek();
            operators.pop();
            String temp = operator + operand2 + operand1;
            operands.push(temp);
        }

        return operands.peek();
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

    public static boolean isOperator(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/') {
            return true;
        }

        return false;
    }
}

class InfixConversion {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // (2 - 4 / 4 ) * (8 / 2 - 3)
        // (6 - 4 + 2) / 1 * (10/5+(4*1))
        // x+y*z/w+u

        System.out.print("Enter equation: ");
        String infix = scan.nextLine();

        infix = infix.replaceAll(" ", "");

        if (checkParenthesis(infix) == true) {
            Conversion convert = new Conversion(infix);

            System.out.println("Postfix: " + convert.getPostfix(infix));
            System.out.println("Prefix: " + convert.getPrefix(infix));
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
