package info.riemannhypothesis.math.crypto;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesExample {

	public static void main(String[] args) throws Exception {
		String key = "36f18357be4dbd77f050515c73fcf9f2";
		byte[] bkey = new byte[] {0x14, 0x0b, 0x41, (byte) 0xb2, 0x2a, 0x29, (byte) 0xbe, (byte) 0xb4, 0x06, 0x1b, (byte) 0xda, 0x66, (byte) 0xb6, 0x74, 0x7e, 0x14};
		System.out.println(CryptoTools.byteArrayToHex(bkey, " "));
		System.out.println(CryptoTools.byteArrayToHex(CryptoTools.hexToByteArray(key), " "));
		String ct = "770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451";
		System.out.println(String.valueOf(CryptoTools.hexToCharArray(CryptoTools.byteArrayToHex(decrypt(CryptoTools.hexToByteArray(key), CryptoTools.hexToByteArray(ct))))));
	}

	public static byte[] encrypt(byte[] key, byte[] strToEncrypt) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			final byte[] encryptedString = cipher.doFinal(strToEncrypt);
			return encryptedString;
		} catch (Exception e) {
			// log.error("Error while encrypting", e);
		}
		return null;

	}

	public static byte[] decrypt(byte[] key, byte[] strToDecrypt) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5PADDING");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(
					Arrays.copyOfRange(strToDecrypt, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
			final byte[] decryptedString = cipher.doFinal(Arrays.copyOfRange(
					strToDecrypt, 16, strToDecrypt.length));
			//cipher.init(Cipher.DECRYPT_MODE, secretKey);
			//final byte[] decryptedString = cipher.doFinal(strToDecrypt);
			return decryptedString;
		} catch (Exception e) {
			// log.error("Error while decrypting", e);
			throw e;
		}
	}

}
