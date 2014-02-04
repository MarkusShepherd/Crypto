package info.riemannhypothesis.crypto;

import java.math.BigInteger;
import java.util.HashMap;

public class DiscreteLog {

	public static final BigInteger p = new BigInteger(
			"13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084171");
	public static final BigInteger g = new BigInteger(
			"11717829880366207009516117596335367088558084999998952205599979459063929499736583746670572176471460312928594829675428279466566527115212748467589894601965568");
	public static final BigInteger h = new BigInteger(
			"3239475104050450443565264378728065788649097520952449527834792452971981976143292558073856937958553180532878928001494706097394108577585732452307673444020333");
	public static final int B = 1048576;
	public static final int maxHashSize = B / 2;
	public static final HashMap<BigInteger, Integer> map = new HashMap<BigInteger, Integer>(
			maxHashSize);
	public static final int outputAfterLines = 10000;

	public static void main(String[] args) {
		Integer x1 = null;
		Integer x0 = null;
		BigInteger x = null;
		int offset = 0;
		// int i = 0;
		while (x == null) {
			System.out.println("Begin building hash table in range " + offset
					+ " to " + (offset + maxHashSize + 1));
			for (x1 = offset; x1 <= offset + maxHashSize + 1; x1++) {
				BigInteger value = g.modPow(BigInteger.valueOf(x1), p)
						.modInverse(p).multiply(h).mod(p);
				map.put(value, x1);
				if (x1 % outputAfterLines == 0) {
					System.out.println(x1);
				}
			}
			System.out.println("Hash table built!");

			BigInteger gB = g.modPow(BigInteger.valueOf(B), p);

			x1 = null;
			for (x0 = 0; x1 == null && x0 <= B; x0++) {
				BigInteger value = gB.modPow(BigInteger.valueOf(x0), p);
				x1 = map.get(value);
				if (x0 % outputAfterLines == 0) {
					System.out.println(x0);
				}
			}
			if (x1 != null) {
				x0--;
				x = BigInteger.valueOf(x0).multiply(BigInteger.valueOf(B))
						.add(BigInteger.valueOf(x1));
			} else {
				offset += maxHashSize;
				map.clear();
			}
		}
		BigInteger test = g.modPow(x, p);
		System.out.println("Found the solution: x = " + x);
		System.out.println("x0 = " + x0 + "; x1 = " + x1);
		System.out.println("Test: g^x = " + test + " mod " + p);
		if (test.equals(h)) {
			System.out.println("Test succeeded - values agree!");
		} else {
			System.out.println("Test fails - there must be a bug...");
		}
	}

}
