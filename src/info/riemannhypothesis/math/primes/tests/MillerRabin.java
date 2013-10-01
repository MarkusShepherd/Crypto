/**
 * 
 */
package info.riemannhypothesis.math.primes.tests;

import java.math.BigInteger;
import java.util.Random;

import info.riemannhypothesis.math.MyBigInteger;

/**
 * @author ms
 *
 */
public class MillerRabin {

	static final Random GENERATOR = new Random();
	static final int DEFAULT_ACCURACY = 1000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyBigInteger n = new MyBigInteger(100, -1, GENERATOR);
		System.out.println("Testing " + n.toString() + "...");
		long start, end;
		
		System.out.println("Regular Miller-Rabin:");
		start = System.nanoTime();
		boolean primeRegMR = isPrime(n, DEFAULT_ACCURACY);
		end = System.nanoTime();
		if (primeRegMR) {
			System.out.println("PRIME!");
		}
		else {
			System.out.println("Composite.");
		}
		System.out.println("Time: " + ((end - start)/1000000000.0));
		System.out.println();

		System.out.println("Threaded Miller-Rabin:");
		start = System.nanoTime();
		MillerRabinTest test = new MillerRabinTest(n, DEFAULT_ACCURACY, 8);
		boolean primeThrMR = test.isPrime();
		end = System.nanoTime();
		if (primeThrMR) {
			System.out.println("PRIME!");
		}
		else {
			System.out.println("Composite.");
		}
		System.out.println("Time: " + ((end - start)/1000000000.0));
		System.out.println();

		System.out.println("Trivial test:");
		start = System.nanoTime();
		boolean primeTrivial = Trivial.isPrime(n);
		end = System.nanoTime();
		if (primeTrivial) {
			System.out.println("PRIME!");
		}
		else {
			System.out.println("Composite.");
		}
		System.out.println("Time: " + ((end - start)/1000000000.0));
		System.out.println();
	}

	public static boolean isPrime(MyBigInteger input) {
		return isPrime(input, DEFAULT_ACCURACY);
	}

	public static boolean isPrime(MyBigInteger input, int accuracy) {

		if (input.equals(MyBigInteger.TWO) 
				|| input.equals(MyBigInteger.THREE)
				|| input.equals(MyBigInteger.FIVE) 
				|| input.equals(MyBigInteger.SEVEN)) {
			return true;
		}

		if (input.compareTo(MyBigInteger.ONE) <= 0
				|| input.divisibleBy(MyBigInteger.TWO)
				|| input.divisibleBy(MyBigInteger.THREE)) {
			return false;
		}

		BigInteger d = input.subtract(MyBigInteger.ONE);
		BigInteger s = MyBigInteger.ZERO;
		BigInteger[] divideAndRemainder = d.divideAndRemainder(MyBigInteger.TWO);

		while (divideAndRemainder[1].equals(MyBigInteger.ZERO)) {
			d = divideAndRemainder[0];
			s = s.add(MyBigInteger.ONE);
			divideAndRemainder = d.divideAndRemainder(MyBigInteger.TWO);
		}

		for (int i = 0; i < accuracy; i++) {
			if (! millerRabinStep(input, d, s)) {
				return false;
			}
		}

		return true;

	}

	static boolean millerRabinStep(MyBigInteger input, BigInteger d, BigInteger s) {

		BigInteger a = (new BigInteger(input.bitCount() - 1, GENERATOR)).add(MyBigInteger.TWO);
		BigInteger x = a.modPow(d, input);

		if (x.equals(MyBigInteger.ONE) || x.equals(input.subtract(MyBigInteger.ONE))) {
			return true;
		}

		for (BigInteger r = MyBigInteger.ONE; r.compareTo(s) < 0; r = r.add(MyBigInteger.ONE)) {
			x = x.multiply(x).mod(input);

			if (x.equals(MyBigInteger.ONE)) {
				return false;
			}

			if (x.equals(input.subtract(MyBigInteger.ONE))) {
				return true;
			}
		}

		return false;
	}



}
