package info.riemannhypothesis.crypto.tools;

public class BlockSequence {

	public final int blockLength;
	private final ByteSequence[] blocks;

	public BlockSequence(int blockLength, ByteSequence bytes) {
		this.blockLength = blockLength;
		int arrayLength = (int) Math.round(1.0 * bytes.length() / blockLength);
		blocks = new ByteSequence[arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			blocks[i] = bytes.range(i * blockLength, (i+i) * blockLength);
		}
	}

	public ByteSequence blockAt(int pos) {
		return blocks[pos];
	}
}
