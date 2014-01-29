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

		for (int orgPos = bytes.length() - 1, attLength = 1; orgPos > 15; orgPos--, attLength = (attLength + 1) % 16) {
			while (attLength <= 0) {
				attLength += 16;
			}
			ByteSequence attack = new ByteSequence(bytes.getByteArray().clone());
			int attPos = orgPos - 16;
			// ByteSequence origBytes = bytes.range(attPos, attPos + attLength);
			for (int guess = 0; guess < 256; guess++) {
				//if (guess == attLength) {
				//	continue;
				//}
				plain.setByteAt(orgPos, (byte) guess);
				for (int i = 0; i < attLength; i++) {
					byte subs = (byte) (bytes.byteAt(attPos + i) ^ guess ^ attLength);
					attack.setByteAt(attPos + i, subs);
				}
				System.out.println("orgPos: " + orgPos + "; attLength: "
						+ attLength + "; attPos: " + attPos + "; guess: "
						+ guess + "; attack: " + attack.toHexString());
				URL url = new URL(baseURL + attack.toHexString());
				if (getResponseCode(url) == 404) {
					System.out.println("Character at position " + orgPos + ": "
							+ guess);
					break;
				}
			}
		}
		System.out.println("Response: " + plain.toHexString());
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
