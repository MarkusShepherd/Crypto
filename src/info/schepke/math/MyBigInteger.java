/**
 * 
 */
package info.schepke.math;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author ms
 *
 */
@SuppressWarnings("serial")
public class MyBigInteger extends BigInteger {

	public static final BigInteger M_ONE = BigInteger.valueOf(-1);
	public static final BigInteger ZERO = BigInteger.valueOf(0);
	public static final BigInteger ONE = BigInteger.valueOf(1);
	public static final BigInteger TWO = BigInteger.valueOf(2);
	public static final BigInteger THREE = BigInteger.valueOf(3);
	public static final BigInteger FOUR = BigInteger.valueOf(4);
	public static final BigInteger FIVE = BigInteger.valueOf(5);
	public static final BigInteger SIX = BigInteger.valueOf(6);
	public static final BigInteger SEVEN = BigInteger.valueOf(7);
	public static final BigInteger EIGHT = BigInteger.valueOf(8);
	public static final BigInteger NINE = BigInteger.valueOf(9);
	public static final BigInteger TEN = BigInteger.valueOf(10);

	
	public MyBigInteger(BigInteger bigInteger) {
		super(bigInteger.toByteArray());
	}

	/**
	 * @param arg0
	 */
	public MyBigInteger(byte[] arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public MyBigInteger(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MyBigInteger(int arg0, byte[] arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MyBigInteger(String arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MyBigInteger(int arg0, Random arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public MyBigInteger(int arg0, int arg1, Random arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	public BigInteger sqrt() {
		BigInteger a = ONE;
		BigInteger b = this.shiftRight(5).add(EIGHT);
		while(b.compareTo(a) >= 0) {
			BigInteger mid = a.add(b).shiftRight(1);
			if (mid.multiply(mid).compareTo(this) > 0) {
				b = mid.subtract(ONE);
			}
			else {
				a = mid.add(ONE);
			}
		}
		
		return a.subtract(ONE);
	}
	
	public boolean divisibleBy(BigInteger divisor) {
		return this.remainder(divisor).equals(ZERO);
	}
	
	public BigInteger smallestDivisor() {
		if (this.equals(ZERO) 
				|| this.equals(ONE) 
				|| this.equals(M_ONE)
				|| this.isProbablePrime(100)) {
			return this;
		}
		
		if (this.divisibleBy(TWO)) {
			return TWO;
		}
		
		if (this.divisibleBy(THREE)) {
			return THREE;
		}

		BigInteger sqrt = this.sqrt();

		for (BigInteger n = SEVEN;
				n.compareTo(sqrt) <= 0;
				n = n.add(MyBigInteger.SIX)) {

			if (this.divisibleBy(n.subtract(TWO))) {
				return n.subtract(TWO);
			}

			if (this.divisibleBy(n)) {
				return n;
			}
		}
		
		return this;
	}

}
