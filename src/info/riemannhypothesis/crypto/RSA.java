package info.riemannhypothesis.crypto;

//import info.schepke.MyBigInteger;

import java.math.BigInteger;
import java.util.Random;

public class RSA {

	private static final Random GENERATOR = new Random();
	public static final int DEFAULT_BIT_LENGTH = 1024;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		keyPair[] keys = generateKeys();
		keyPair publicKey = keys[0];
		keyPair privateKey = keys[1];

		System.out.println("n = " + publicKey.modulus.toString());
		System.out.println("e = " + publicKey.exponent.toString());
		System.out.println("d = " + privateKey.exponent.toString());

		BigInteger message = BigInteger.valueOf(42);
		BigInteger encrypted = encryptMessage(message, publicKey);
		BigInteger decrypted = decryptBigInteger(encrypted, privateKey);

		System.out.println("message = " + message.toString());
		System.out.println("encrypted = " + encrypted.toString());
		System.out.println("decrypted = " + decrypted.toString());

		String newMessage = "ß∂˚ßƒ˙≈ç^π∑´®∆˚ßabcdefghijklmnopqrstuvxyz abcdefghijklmnopqrstuvxyz abcdefghijklmnopqrstuvxyz abcdefghijklmnopqrstuvxyz abcdefghijklmnopqrstuvxyz abcdefghijklmnopqrstuvxyz abcdefghijklmnopqrstuvxyz abcdefghijklmnopqrstuvxyz abcdefghijklmnopqrstuvxyz abcdefghijklmnopqrstuvxyz abcdefghijklmnopqrstuvxyz abcdefghijklmnopqrstuvxyz";
		BigInteger[] newEncrypted = encryptMessage(newMessage, publicKey);
		String newDecrypted = decryptString(newEncrypted, privateKey);

		System.out.println("message = " + newMessage);
		System.out.println("decrypted = " + newDecrypted);
	}

	public static BigInteger[] generatePrimes() {
		return generatePrimes(DEFAULT_BIT_LENGTH);
	}

	public static BigInteger[] generatePrimes(int bitLength) {
		BigInteger[] primes = {
				BigInteger.probablePrime(bitLength, GENERATOR), 
				BigInteger.probablePrime(bitLength, GENERATOR)};

		return primes;
	}

	public static keyPair[] generateKeys() {
		return generateKeys(DEFAULT_BIT_LENGTH);
	}

	public static keyPair[] generateKeys(int bitLength) {
		keyPair[] keys = new keyPair[2];

		BigInteger[] primes = generatePrimes(bitLength);
		BigInteger p = primes[0];
		BigInteger q = primes[1];
		BigInteger n = p.multiply(q);

		BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

		BigInteger e;

		do {
			e = new BigInteger(bitLength, GENERATOR);
		} while(e.compareTo(BigInteger.ONE) <= 0 
				|| e.compareTo(phiN) >= 0 
				|| e.gcd(phiN).compareTo(BigInteger.ONE) != 0);

		BigInteger d = e.modInverse(phiN);

		keys[0] = new keyPair(n, e);
		keys[1] = new keyPair(n, d);

		return keys;
	}

	public static class keyPair {
		public final BigInteger modulus;
		public final BigInteger exponent;

		public keyPair(BigInteger modulus, BigInteger exponent) {
			this.modulus = modulus;
			this.exponent = exponent;
		}
	}

	public static BigInteger encryptMessage(BigInteger message, keyPair publicKey) {
		return message.modPow(publicKey.exponent, publicKey.modulus);
	}

	public static BigInteger[] encryptMessage(String message, keyPair publicKey) {
		int capacity = message.length() * 8 / publicKey.modulus.bitCount() + 1;
		int step = (int) Math.ceil(message.length() * 1.0 / capacity);
		
		BigInteger[] encrypted = new BigInteger[capacity];
		
		for (int i = 0; i < capacity; i ++) {
			int start = i * step;
			int end = (i + 1) * step > message.length() ? message.length() : (i + 1) * step;
			encrypted[i] = encryptMessage(padMessage(message.substring(start, end)), publicKey);
		}
		
		return encrypted;
	}

	public static BigInteger decryptBigInteger(BigInteger encrypted, keyPair privateKey) {
		return encrypted.modPow(privateKey.exponent, privateKey.modulus);
	}

	public static String decryptString(BigInteger[] encrypted, keyPair privateKey) {
		StringBuffer message = new StringBuffer();
		
		for (BigInteger part : encrypted) {
			message.append(unpadMessage(decryptBigInteger(part, privateKey)));
		}
		
		return message.toString();
	}

	public static BigInteger padMessage(String message) {
		BigInteger padded = BigInteger.ZERO;

		for (int i = 0; i < message.length(); i++) {
			int digit = (int) message.charAt(i);
			digit = (digit <= 0 || digit > 255) ? (int) '?' : digit;
			padded = padded.shiftLeft(8).add(new BigInteger(Integer.toString(digit)));
		}
		
		return padded;
	}
	
	public static String unpadMessage(BigInteger padded) {
		final BigInteger TWOFIVEFIVE = BigInteger.valueOf(255);
		StringBuffer message = new StringBuffer();

		while (padded.bitCount() > 0) {
			int value = padded.and(TWOFIVEFIVE).intValue();
			message.append((char) value);
			padded = padded.shiftRight(8);
		}

		return message.reverse().toString();
	}
}
