package info.schepke.math.primes;

import info.schepke.math.MyBigInteger;

import java.math.BigInteger;

public class EuclidPrimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BigInteger[] primes = euclidPrimes(20);
		for (BigInteger prime : primes) {
			System.out.println(prime.toString());
		}
	}

	public static BigInteger nthEuclidPrime(BigInteger n) {
		if (n.compareTo(MyBigInteger.ZERO) < 0) {
			return null;
		}

		BigInteger product = MyBigInteger.ONE;
		BigInteger prime = MyBigInteger.ONE;

		for (BigInteger i = MyBigInteger.ZERO;
				i.compareTo(n) <= 0;
				i = i.add(MyBigInteger.ONE)) {
			product = product.multiply(prime);
			prime = new MyBigInteger(product.add(MyBigInteger.ONE)).smallestDivisor();
		}

		return prime;
	}

	public static BigInteger[] euclidPrimes(int n) {
		if (n <= 0) {
			return null;
		}

		BigInteger product = MyBigInteger.ONE;
		BigInteger prime = MyBigInteger.ONE;
		BigInteger[] primes = new BigInteger[n+1];

		for (int i = 0;
				i <= n;
				i++) {
			product = product.multiply(prime);
			prime = new MyBigInteger(product.add(MyBigInteger.ONE)).smallestDivisor();
			primes[i] = prime;
			System.out.println("No " + i + ": " + prime.toString());
		}

		return primes;
	}
}
