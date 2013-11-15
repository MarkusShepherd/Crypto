package info.riemannhypothesis.math.crypto;

public class OTP {

	public static void main(String[] args) {
		String m1s = "attack at dawn";
		char[] m1c = m1s.toCharArray();
		String c1s = "09e1c5f70a65ac519458e7e53f36";
		char[] c1c = hexToCharArray(c1s);
		char[] key = minus(c1c, m1c);
		String m2s = "attack at dusk";
		char[] m2c = m2s.toCharArray();
		char[] c2c = plus(m2c, key);
		System.out.println(charArrayToHex(m1c));
		System.out.println(charArrayToHex(c1c));
		System.out.println(charArrayToHex(key));
		System.out.println(charArrayToHex(c2c));
	}

	public static char[] hexToCharArray(String input) {
		if (input == null || input.length() == 0 || input.length() % 2 != 0) {
			return new char[] {};
		}
		int len = input.length() / 2;
		char[] out = new char[len];
		for (int i = 0; i < len; i++) {
			try {
				out[i] = (char) Integer.parseInt(
						input.substring(2 * i, 2 * i + 2), 16);
			} catch (NumberFormatException e) {
				return out;
			}
		}
		return out;
	}

	public static String charArrayToHex(char[] input) {
		if (input == null || input.length == 0) {
			return "";
		}
		StringBuilder temp = new StringBuilder(input.length);
		for (char c : input) {
			if (c < 16) {
				temp.append('0');
			}
			temp.append(Integer.toHexString(c));
		}
		return temp.toString();
	}

	public static char[] plus(char[] seq1, char[] seq2) {
		if (seq1 == null) {
			seq1 = new char[] {};
		}
		if (seq2 == null) {
			seq2 = new char[] {};
		}
		int len1 = seq1.length;
		int len2 = seq2.length;
		int len = Math.max(len1, len2);
		if (len == 0) {
			return new char[] {};
		}
		int mod = 256;
		char[] out = new char[len];
		for (int i = 0; i < len; i++) {
			char c1 = i < len1 ? seq1[i] : 0;
			char c2 = i < len2 ? seq2[i] : 0;
			int c = (c1 + c2) % mod;
			while (c < 0) {
				c += mod;
			}
			out[i] = (char) c;
		}
		return out;
	}

	public static char[] minus(char[] seq1, char[] seq2) {
		if (seq1 == null) {
			seq1 = new char[] {};
		}
		if (seq2 == null) {
			seq2 = new char[] {};
		}
		int len1 = seq1.length;
		int len2 = seq2.length;
		int len = Math.max(len1, len2);
		if (len == 0) {
			return new char[] {};
		}
		int mod = 256;
		char[] out = new char[len];
		for (int i = 0; i < len; i++) {
			char c1 = i < len1 ? seq1[i] : 0;
			char c2 = i < len2 ? seq2[i] : 0;
			int c = (c1 - c2) % mod;
			while (c < 0) {
				c += mod;
			}
			out[i] = (char) c;
		}
		return out;
	}
}
