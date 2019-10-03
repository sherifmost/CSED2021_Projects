package eg.edu.alexu.csd.datastructure.stack.cs05;

import eg.edu.alexu.csd.datastructure.stack.IExpressionEvaluator;


/**
 *
 * @author Ahmed Ali
 *
 */
public class Evaluator implements IExpressionEvaluator {

	/**
	 * @author Ahmed Ali
	 */
	 final int fe = 48;
	@Override
	/**
	 * @author Ahmed Ali
	 */
	public String infixToPostfix(final String expression) {
		if (expression == null) {
			throw new RuntimeException();
		}
	    check(expression);
		MyStack stack = new MyStack();
		StringBuilder postfix = new StringBuilder("");
		int i = 0;
		char[] exp = expression.toCharArray();

		for (i = 0; i < exp.length; i++) {
			if (Character.isLetterOrDigit(exp[i])) {
				postfix.append(exp[i]);
				postfix.append(" ");
			} else if (exp[i] == '*' || exp[i] == '/') {
				if (!stack.isEmpty()) {
					if (stack.peek() == (Object) '*'
							|| stack.peek() == (Object) '/') {
						if (stack.peek() != (Object) '(') {
							postfix.append(stack.pop());
							postfix.append(" ");
						}
						stack.push(exp[i]);
					} else if (stack.peek() == (Object) '+'
							|| stack.peek() == (Object) '-') {
						stack.push(exp[i]);
					} else {
						stack.push(exp[i]);
					}
				} else {
					stack.push(exp[i]);
				}
			} else if (exp[i] == '+' || exp[i] == '-') {
				if (!stack.isEmpty()) {
					while (!stack.isEmpty()) {
						if (stack.peek() == (Object) '(') {
							break;
						}

						postfix.append(stack.pop());
						postfix.append(" ");
					}
					/*
					 * if (!stack.isEmpty()) { stack.pop(); }
					 */
				}
				// System.out.println(stack.peek() + "" +exp[i]);
				stack.push(exp[i]);
				// System.out.println(stack.peek());
			} else if (exp[i] == '(') {
				stack.push(exp[i]);
			} else if (exp[i] == ')') {
				while (!stack.isEmpty() && stack.peek() != (Object) '(') {
					// if (stack.peek() == (Object) '(') {
					postfix.append(stack.pop());
					 //postfix.append(" ");
					// break;
					// }
					// postfix.append(stack.pop());
				}
				if (!stack.isEmpty()) {
					stack.pop();
				}

			}
		}
		while (!stack.isEmpty()) {
			postfix.append(stack.pop());
			if (stack.size() > 1) {
				postfix.append(" ");
			}
		}
		return postfix.toString();
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public int evaluate(final String expression) {
		if (expression == null) {
			throw new RuntimeException();
		}
		check(expression);
		MyStack stack = new MyStack();
		// System.out.println(expression);
		int number1 = 0;
		int number2 = 0;
		int result = 0;
		char[] exp = expression.toCharArray();
		int i = 0;
		for (i = 0; i < exp.length; i++) {
			if (Character.isLetterOrDigit(exp[i])) {
				// System.out.println(exp[i]);
				// System.out.println((int)exp[i]-48);
				stack.push((int) exp[i] - fe);
				// System.out.println((int)stack.peek());
			} else if (exp[i] == '+' || exp[i] == '-'
					|| exp[i] == '*' || exp[i] == '/') {
				// System.out.println(stack.peek());
				number1 = (int) stack.pop();
				// System.out.println(number1);
				// System.out.println(stack.peek());
				number2 = (int) stack.pop();
				result = doMath(number1, number2, exp[i]);
				stack.push(result);
			}
		}
		return result;
	}

	/**
	 *
	 * @param first
	 * first
	 * @param second
	 * second
	 * @param op
	 * op
	 * @return
	 * return
	 */
	public int doMath(final int first, final int second, final char op) {

		switch (op) {
		case '+':
			return first + second;
		case '-':
			return first - second;
		case '*':
			return first * second;
		case '/':
			return first / second;
		default:
			return 0;
		}
	}
	/**
	 *
	 * @param expression
	 * exp
	 */
	public void check(final String expression) {
		char[] exp = expression.toCharArray();
		int i = 0;
		if (exp[0] == '+' || exp[0] == '-'
				|| exp[0] == '/' || exp[0] == '*') {
			throw new RuntimeException();
		}
		int good = 0;
		for (i = 0; i < exp.length; i++) {
			if (Character.isLetterOrDigit(exp[i])) {
				good++;
			} else if (exp[i] == '+' || exp[i] == '-'
					|| exp[i] == '*' || exp[i] == '/') {
				good--;
			}
		}
		if (good != 1) {
			throw new RuntimeException();
		}
	}
}
