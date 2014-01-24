package info.riemannhypothesis.crypto.tools;

import java.security.NoSuchAlgorithmException;

public class VideoHash implements HashFunction {

	private SHA sha;

	public VideoHash() {
		try {
			sha = new SHA(256);
		} catch (NoSuchAlgorithmException e) {
			sha = null;
		}
	}

	@Override
	public ByteSequence hash(ByteSequence input) {
		return hash(1024, input);
	}

	public ByteSequence hash(int blockLength, ByteSequence input) {
		return hash(new BlockSequence(blockLength, input));
	}

	public ByteSequence hash(BlockSequence blocks) {
		ByteSequence hash = sha.hash(blocks.blockAt(blocks.length() - 1));
		for (int i = blocks.length() - 2; i >= 0; i--) {
			ByteSequence temp = blocks.blockAt(i).append(hash);
			hash = sha.hash(temp);
		}
		return hash;
	}

}
