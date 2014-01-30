package info.riemannhypothesis.crypto;

import info.riemannhypothesis.crypto.tools.BlockSequence;
import info.riemannhypothesis.crypto.tools.ByteSequence;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PaddingOracle {

	public static final String baseURL = "http://crypto-class.appspot.com/po?er=";

	public static void main(String[] args) throws IOException {

		String cipher = "f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4";
		final byte blockLength = 16;
		ByteSequence bytes = ByteSequence.fromHexString(cipher);
		ByteSequence plain = ByteSequence.EMPTY_SEQUENCE;
		BlockSequence blocks = new BlockSequence(blockLength, bytes);

		for (int i = 1; i < blocks.length(); i++) {
			ByteSequence iv = blocks.blockAt(i - 1);
			ByteSequence block = blocks.blockAt(i);
			boolean lastBlock = (i == blocks.length() - 1);
			try {
				ByteSequence plainBlock = decryptBlock(iv, block);
				if (lastBlock) {
					byte paddingLength = plainBlock
							.byteAt(plainBlock.length() - 1);
					if (paddingLength < 1 || paddingLength > blockLength) {
						throw new Exception("Incorrect padding");
					}
					for (int pos = 1; pos < paddingLength; pos++) {
						if (plainBlock.byteAt(plainBlock.length() - 1 - pos) != paddingLength) {
							throw new Exception("Incorrect padding");
						}
					}
					plainBlock = plainBlock.range(0, plainBlock.length()
							- paddingLength);
				}
				System.out.println("The decryption of block " + i + " is: "
						+ plainBlock.toString());
				plain = plain.append(plainBlock);
			} catch (Exception e) {
				System.out.print("There was an error: " + e.getMessage());
				e.printStackTrace();
			}
		}
		System.out.println("Plaintext: " + plain.toString());
		System.out.println("Plaintext (hex): " + plain.toHexString());
	}

	public static int getResponseCode(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		int response = connection.getResponseCode();
		connection.disconnect();
		return response;
	}

	public static ByteSequence decryptBlock(ByteSequence iv, ByteSequence cipher)
			throws CloneNotSupportedException, IOException {
		ByteSequence plain = new ByteSequence(new byte[cipher.length()]);
		boolean wrongGuess = false;
		byte avoidGuess = 0;
		for (int pos = cipher.length() - 1, pad = 1; pos >= 0; pos--, pad++) {
			ByteSequence attack = iv.append(cipher);
			boolean foundGuess = false;
			for (int guess = 0; guess < 256; guess++) {
				if (wrongGuess && guess == avoidGuess) {
					System.out.println("Skipped; pos: " + pos + "; pad: " + pad
							+ "; guess: " + guess);
					continue;
				}
				byte subs = (byte) (iv.byteAt(pos) ^ guess ^ pad);
				attack.setByteAt(pos, subs);
				for (int i = 1; i < pad; i++) {
					subs = (byte) (iv.byteAt(pos + i) ^ plain.byteAt(pos + i) ^ pad);
					attack.setByteAt(pos + i, subs);
				}
				URL url = new URL(baseURL + attack.toHexString());
				int response = getResponseCode(url);
				System.out.println("pos: " + pos + "; pad: " + pad
						+ "; guess: " + guess + "; attack: "
						+ attack.toHexString() + "; reponse: " + response);
				if (response == 404) {
					System.out.println("Character at position " + pos + ": "
							+ guess);
					plain.setByteAt(pos, (byte) guess);
					wrongGuess = false;
					foundGuess = true;
					break;
				}
			}
			if (!foundGuess) {
				System.out
						.println("Found no matching guess, will go back one step; pos: "
								+ pos + "; pad: " + pad);
				wrongGuess = true;
				avoidGuess = plain.byteAt(pos + 1);
				pos += 2;
				pad -= 2;
			}
		}
		return plain;
	}
}
