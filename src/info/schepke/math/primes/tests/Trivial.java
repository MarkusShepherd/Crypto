package info.schepke.math.primes.tests;

import info.schepke.math.MyBigInteger;

import java.math.BigInteger;
import java.util.Random;

public class Trivial {

	public static void main(String[] args) {
//		BigInteger test = BigInteger.valueOf(12345678746529723547);
		MyBigInteger test = new MyBigInteger(60, -1000, new Random());
		System.out.println("Testing " + test.toString() + "...");

		BigInteger divisor = smallestDivisor(test);

		if (divisor == null) {
			System.out.println("Invalid input.");
		} 
		else if (divisor.equals(test)) {
			System.out.println("PRIME!");
		}
		else {
			System.out.println("Composite. Smallest factor is " + divisor.toString());
		}
	}

	public static BigInteger smallestDivisor(MyBigInteger input) {
		BigInteger sqrt = input.sqrt();

		if (input.equals(MyBigInteger.TWO) 
				|| input.equals(MyBigInteger.THREE)
				|| input.equals(MyBigInteger.FIVE) 
				|| input.equals(MyBigInteger.SEVEN)) {
			return input;
		}

		if (input.compareTo(MyBigInteger.ONE) <= 0) {
			return null;
		}

		if (input.divisibleBy(MyBigInteger.TWO)) {
			return MyBigInteger.TWO;
		}

		if (input.divisibleBy(MyBigInteger.THREE)) {
			return MyBigInteger.THREE;
		}

		for (BigInteger n = MyBigInteger.SEVEN;
				n.compareTo(sqrt) <= 0;
				n = n.add(MyBigInteger.SIX)) {

			if (input.divisibleBy(n.subtract(MyBigInteger.TWO))) {
				return n.subtract(MyBigInteger.TWO);
			}

			if (input.divisibleBy(n)) {
				return n;
			}
		}

		return input;
	}
	
	public static boolean isPrime(MyBigInteger input) {
		return smallestDivisor(input) == input;
	}

}
