package info.riemannhypothesis.crypto.tools;

public abstract class BlockCipher extends PseudoRandomPermutation {

	private final ByteSequence key;

	public BlockCipher(int keyLength, ByteSequence key) {
		super(keyLength, key.length());
		this.key = key;
	}

	public final ByteSequence decrypt(ByteSequence output) {
		return decrypt(key, output);
	}

	public final ByteSequence encrypt(ByteSequence input) {
		return encrypt(key, input);
	}

}
