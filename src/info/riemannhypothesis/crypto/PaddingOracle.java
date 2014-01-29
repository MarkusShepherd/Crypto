package info.riemannhypothesis.crypto;

import info.riemannhypothesis.crypto.tools.ByteSequence;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PaddingOracle {

	public static void main(String[] args) throws IOException {
		String baseURL = "http://crypto-class.appspot.com/po?er=";
		String cipher = "f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4";
		ByteSequence bytes = ByteSequence.fromHexString(cipher);
		ByteSequence plain = new ByteSequence(new byte[bytes.length()]);
		// BlockSequence blocks = new BlockSequence(16, bytes);

		for (int i = bytes.length() - 1, attLength = 1; i > 15; i--, attLength = (attLength + 1) % 16) {
			while (attLength <= 0) {
				attLength += 16;
			}
			ByteSequence attack = new ByteSequence(bytes.getByteArray().clone());
			int attPos = i - 16;
			// ByteSequence origBytes = bytes.range(attPos, attPos + attLength);
			for (int g = 0; g < 256; g++) {
				plain.setByteAt(i, (byte) g);
				for (int y = 0; y < attLength; y++) {
					byte subs = (byte) (bytes.byteAt(attPos + y) ^ g ^ attLength);
					attack.setByteAt(attPos + y, subs);
				}
				System.out.println(attack.toHexString());
				URL url = new URL(baseURL + attack.toHexString());
				if (getResponseCode(url) != 403) {
					System.out.println("Character at position " + i + ": "
							+ g);
					break;
				}
			}
		}
		System.out.println("Response: " + plain.toString());
	}

	public static int getResponseCode(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		int response = connection.getResponseCode();
		connection.disconnect();
		return response;
	}
}
