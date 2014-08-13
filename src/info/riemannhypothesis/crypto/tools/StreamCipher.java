package info.riemannhypothesis.crypto.tools;

/**
 * @author Markus Schepke
 * @date 13 Aug 2014
 */
public class StreamCipher implements Cipher {

    private final PseudoRandomGenerator prg;

    public StreamCipher(PseudoRandomGenerator prg) {
        this.prg = prg;
    }

    @Override
    public ByteSequence encrypt(ByteSequence seed, ByteSequence input) {
        ByteSequence key = prg.random(seed);
        if (input.length() > key.length()) {
            throw new IllegalArgumentException(
                    "Input length is longer than key");
        }
        return input.xor(key);
    }

    @Override
    public ByteSequence decrypt(ByteSequence seed, ByteSequence output) {
        return encrypt(seed, output);
    }

}
