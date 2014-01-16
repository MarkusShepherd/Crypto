package info.riemannhypothesis.math.crypto;

public class CryptoTools {

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

	public static byte[] hexToByteArray(String input) {
		if (input == null || input.length() == 0 || input.length() % 2 != 0) {
			return new byte[] {};
		}
		int len = input.length() / 2;
		byte[] out = new byte[len];
		for (int i = 0; i < len; i++) {
			try {
				out[i] = (byte) Integer.parseInt(
						input.substring(2 * i, 2 * i + 2), 16);
			} catch (NumberFormatException e) {
				return out;
			}
		}
		return out;
	}

	public static String charArrayToHex(char[] input) {
		return charArrayToHex(input, null);
	}

	public static String charArrayToHex(char[] input, String sep) {
		if (input == null || input.length == 0) {
			return "";
		}
		boolean insertSep = true;
		if (sep == null || sep.length() == 0) {
			insertSep = false;
		}
		StringBuilder temp = new StringBuilder(input.length);
		for (int i = 0; i < input.length; i++) {
			if (input[i] < 16) {
				temp.append('0');
			}
			temp.append(Integer.toHexString(input[i]));
			if (insertSep && i < input.length - 1) {
				temp.append(sep);
			}
		}
		return temp.toString();
	}

	public static String byteArrayToHex(byte[] input) {
		return byteArrayToHex(input, null);
	}

	public static String byteArrayToHex(byte[] input, String sep) {
		if (input == null || input.length == 0) {
			return "";
		}
		boolean insertSep = true;
		if (sep == null || sep.length() == 0) {
			insertSep = false;
		}
		StringBuilder temp = new StringBuilder(input.length);
		for (int i = 0; i < input.length; i++) {
			if (input[i] < 16) {
				temp.append('0');
			}
			temp.append(Integer.toHexString(input[i]));
			if (insertSep && i < input.length - 1) {
				temp.append(sep);
			}
		}
		return temp.toString();
	}

	public static char[] xor(char[] seq1, char[] seq2) {
		if (seq1 == null) {
			seq1 = new char[] {};
		}
		if (seq2 == null) {
			seq2 = new char[] {};
		}
		int len1 = seq1.length;
		int len2 = seq2.length;
		int len = Math.min(len1, len2);
		if (len == 0) {
			return new char[] {};
		}
		char[] out = new char[len];
		for (int i = 0; i < len; i++) {
			char c1 = i < len1 ? seq1[i] : 0;
			char c2 = i < len2 ? seq2[i] : 0;
			out[i] = (char) (c1 ^ c2);
		}
		return out;
	}

	public static byte[] xor(byte[] seq1, byte[] seq2) {
		if (seq1 == null) {
			seq1 = new byte[] {};
		}
		if (seq2 == null) {
			seq2 = new byte[] {};
		}
		int len1 = seq1.length;
		int len2 = seq2.length;
		int len = Math.min(len1, len2);
		if (len == 0) {
			return new byte[] {};
		}
		byte[] out = new byte[len];
		for (int i = 0; i < len; i++) {
			byte c1 = i < len1 ? seq1[i] : 0;
			byte c2 = i < len2 ? seq2[i] : 0;
			out[i] = (byte) (c1 ^ c2);
		}
		return out;
	}

	public static CharSequence xor(CharSequence seq1, CharSequence seq2) {
		if (seq1 == null) {
			seq1 = "";
		}
		if (seq2 == null) {
			seq2 = "";
		}
		int len1 = seq1.length();
		int len2 = seq2.length();
		int len = Math.min(len1, len2);
		if (len == 0) {
			return "";
		}
		StringBuilder out = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c1 = i < len1 ? seq1.charAt(i) : 0;
			char c2 = i < len2 ? seq2.charAt(i) : 0;
			out.append((char) (c1 ^ c2));
		}
		return out;
	}

	public static String xorHex(String seq1, String seq2) {
		return charArrayToHex(xor(hexToCharArray(seq1), hexToCharArray(seq2)));
	}
}
