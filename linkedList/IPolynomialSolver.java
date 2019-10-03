package eg.edu.alexu.csd.datastructure.linkedList;

public interface IPolynomialSolver {
	/**
	 * * Set polynomial terms (coefficients & exponents) * @param poly * name of the
	 * polynomial * @param terms * array of [coefficients][exponents]
	 */
	void setPolynomial(char poly, int[][] terms);

	/**
	 * * Print the polynomial in ordered human readable representation * @param poly
	 * * name of the polynomial * @return polynomial in the form like 27x^2+x-1
	 */
	String print(char poly);

	/** * Clear the polynomial * @param poly * name of the polynomial */
	void clearPolynomial(char poly);

	/**
	 * * Evaluate the polynomial * @param poly * name of the polynomial * @param the
	 * * polynomial constant value Prof. Dr. Khaled Nagi 4 Eng. Ahmed Hamdy
	 * Alexandria University Faculty of Engineering Computer and Systems Engineering
	 * Department CS122: Data Structures I Assigned: Saturday, March 10th, 2018 Due:
	 * Friday, March 23rd, 2018
	 * 
	 * @return the value of the polynomial
	 */
	float evaluatePolynomial(char poly, float value);

	/**
	 * * Add two polynomials * @param poly1 * first polynomial * @param poly2 *
	 * second polynomial * @return the result polynomial
	 */
	int[][] add(char poly1, char poly2);

	/**
	 * * Subtract two polynomials * @param poly1 * first polynomial * @param poly2 *
	 * second polynomial * @return the result polynomial
	 */
	int[][] subtract(char poly1, char poly2);

	/**
	 * * Multiply two polynomials * @param poly1 * first polynomial * @param poly2 *
	 * second polynomial * @return the result polynomial
	 */
	int[][] multiply(char poly1, char poly2);
}