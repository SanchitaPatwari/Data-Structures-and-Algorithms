package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	private static Node reverse(Node ptr) {
		Node prev= null;
		Node current= ptr;
		Node next= null;
		while(current!=null) {
			next=current.next;
			current.next=prev;
			prev=current;
			current=next;
		}
		ptr=prev;
		return ptr;
				
	}
	public static Node add(Node poly1, Node poly2) 
	{
		Node poly10= poly1;
		Node poly20= poly2;
		Node sum=null;
		while(poly10!=null || poly20!=null)
		{
			if (poly10==null) 
			{
				while(poly20 != null) {
					sum= new Node(poly20.term.coeff,poly20.term.degree,sum);
					poly20=poly20.next;
				}
				Node temp= reverse(sum);
				return temp;
			}
			
			if (poly20==null) 
			{
				while (poly10 != null) {
					sum= new Node(poly10.term.coeff,poly10.term.degree,sum);
					poly10=poly10.next;
				}
				Node temp= reverse(sum);
				return temp;
			}
			if (poly10.term.degree>poly20.term.degree)
			{
				sum= new Node(poly20.term.coeff,poly20.term.degree,sum);
				poly20=poly20.next;
			}
			else if(poly20.term.degree>poly10.term.degree)
			{
				sum= new Node(poly10.term.coeff,poly10.term.degree,sum);
				poly10=poly10.next;
			}	
			else if(poly10.term.degree==poly20.term.degree) 
			{
				if(poly10.term.coeff+poly20.term.coeff==0)
				{
					poly10=poly10.next;
					poly20=poly20.next;
					continue;
				}
				else 
				{
					sum= new Node(poly10.term.coeff+poly20.term.coeff,poly10.term.degree, sum);
					poly10=poly10.next;
					poly20=poly20.next;
							
				}
			}
			
		}
	  Node temp= reverse(sum);
	  return temp;
	}  
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node poly01= poly1;
		Node poly02= poly2;
		Node product1=null;
		Node result = null;
		Node product2=null;
		if (poly1 == null ||poly2 == null) {
			return null;
		}
		
		while(poly01!=null) {
			poly02 = poly2;
			product2 = null;
			while (poly02!=null) {
				product2 =new Node(poly01.term.coeff*poly02.term.coeff,poly01.term.degree+poly02.term.degree,product2);
				poly02=poly02.next;
		
			}
			result = add(reverse(product2), product1);
			product1 = result;
			
			poly01=poly01.next;
		}
		
		
		return result;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		Node poly1=poly;
		float sum = 0.0f;
		while (poly1!=null) {
			sum+=(float)(Math.pow(x, poly1.term.degree)*poly1.term.coeff);
			poly1=poly1.next;
		}
		
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
