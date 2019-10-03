package eg.edu.alexu.csd.datastructure.stack.cs05;

/**
 *
 * @author Ahmed Ali
 *
 */
public class Test {

	/**
	 *
	 * @param args
	 * array
	 */
	public static void main(final String[] args) {
		Evaluator ev = new Evaluator();
		String exp = "(1 + 2) + (3 * 4)";
		System.out.println(ev.infixToPostfix(exp));
		System.out.println(ev.infixToPostfix(exp));
		String expev = "";
		expev = ev.infixToPostfix(exp);
		System.out.println(expev);
		System.out.println(ev.evaluate(expev));
	}

}
