package info.schepke.math.primes.tests;

import info.schepke.math.MyBigInteger;

import java.math.BigInteger;

public class LucasLehmer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Lucas-Lehmer test:");
		
		for (int e = 3217; e < 10000; e+= 2) {
			if (isMersennePrime(e)) {
				System.out.print("Testing M" + e + "... ");
				System.out.println("PRIME!");
			}
			else {
//				System.out.print("Composite...");
			}
		}
	}
	
	public static boolean isMersennePrime(int exponent) {
		if (exponent < 2 || ! Trivial.isPrime(new MyBigInteger(Integer.toString(exponent)))) {
			return false;
		}
		
		if (exponent == 2) {
			return true;
		}
		
		BigInteger M = mersenneNumber(exponent);
		BigInteger s = BigInteger.valueOf(4);

//		System.out.println();
//		System.out.println(M.toString());
//		System.out.println(s.toString());
		
		for (int i = 0; i < exponent - 2; i++) {
			s = s.multiply(s).subtract(BigInteger.valueOf(2)).mod(M);
//			System.out.println(s.toString());
		}
		
		return s.equals(BigInteger.ZERO);
	}

	public static BigInteger mersenneNumber(int exponent) {
//		byte[] digits = new byte[exponent];
//		for (int i = 0; i < digits.length; i++) {
//			digits[i] = 1;
//		}
		return (BigInteger.valueOf(2)).pow(exponent).subtract(BigInteger.ONE);
	}
}
