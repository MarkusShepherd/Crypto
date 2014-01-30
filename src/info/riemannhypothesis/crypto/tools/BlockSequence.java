package info.riemannhypothesis.crypto.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class BlockSequence {

	public final int blockLength;
	private final ByteSequence[] blocks;

	public BlockSequence(int blockLength, ByteSequence bytes) {
		this.blockLength = blockLength;
		int arrayLength = (int) Math.round(1.0 * bytes.length() / blockLength);
		blocks = new ByteSequence[arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			blocks[i] = bytes.range(i * blockLength, (i + 1) * blockLength);
		}
	}

	public BlockSequence(int blockLength, InputStream stream, int streamLength)
			throws IOException {
		this.blockLength = blockLength;
		int arrayLength = (int) Math.round(1.0 * streamLength / blockLength);
		blocks = new ByteSequence[arrayLength];
		byte[] temp = new byte[blockLength];
		int read = 0;
		int i = 0;
		while ((read = stream.read(temp)) > 0) {
			blocks[i] = new ByteSequence(Arrays.copyOfRange(temp, 0, read));
			i++;
		}
	}

	public BlockSequence(int blockLength, FileInputStream stream)
			throws IOException {
		this(blockLength, stream, stream.available());
	}

	public ByteSequence[] getBlockArray() {
		return blocks;
	}

	public ByteSequence blockAt(int pos) {
		return blocks[pos];
	}

	public int length() {
		return blocks.length;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (ByteSequence bytes : blocks) {
			builder.append(bytes.toString());
		}
		return builder.toString();
	}

	public String toHexString() {
		StringBuilder builder = new StringBuilder();
		for (ByteSequence bytes : blocks) {
			builder.append(bytes.toHexString(null));
		}
		return builder.toString();
	}

}
