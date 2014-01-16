package info.riemannhypothesis.math.crypto;

public class Week2 {

	public static void main(String[] args) {
		String m1, m2;
		m1 = "e86d2de2";
		m2 = "1792d21d";
		System.out.println(CryptoTools.xorHex(m1, m2));
	}

}
