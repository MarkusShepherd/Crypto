package info.riemannhypothesis.crypto.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA implements HashFunction {

	private final MessageDigest messageDigest;

	public SHA() throws NoSuchAlgorithmException {
		this(256);
	}

	public SHA(int method) throws NoSuchAlgorithmException {
		switch (method) {
		case 1:
			messageDigest = MessageDigest.getInstance("SHA-1");
			break;
		case 384:
			messageDigest = MessageDigest.getInstance("SHA-384");
			break;
		case 512:
			messageDigest = MessageDigest.getInstance("SHA-512");
			break;
		default:
			messageDigest = MessageDigest.getInstance("SHA-256");
			break;
		}
	}

	@Override
	public ByteSequence hash(ByteSequence input) {
		messageDigest.reset();
		messageDigest.update(input.getByteArray());
		return new ByteSequence(messageDigest.digest());
	}

}
