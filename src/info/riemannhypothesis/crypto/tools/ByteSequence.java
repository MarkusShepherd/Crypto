package info.riemannhypothesis.crypto.tools;

import java.nio.charset.Charset;
import java.util.Arrays;

public class ByteSequence implements Cloneable {

	public static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
	public static final ByteSequence EMPTY_SEQUENCE = new ByteSequence(
			new byte[] {});

	private final byte[] seq;

	public ByteSequence(byte[] seq) {
		this.seq = seq;
	}

	public ByteSequence(String str) {
		this.seq = str.getBytes(DEFAULT_CHARSET);
	}

	public ByteSequence(String str, Charset charset) {
		this.seq = str.getBytes(charset);
	}

	public byte[] getByteArray() {
		return seq;
	}

	public byte byteAt(int pos) {
		return this.seq[pos];
	}

	public int length() {
		return this.seq.length;
	}

	public ByteSequence prepend(byte that) {
		byte[] temp = new byte[this.length() + 1];
		temp[0] = that;
		System.arraycopy(this.getByteArray(), 0, temp, 1, this.length());
		return new ByteSequence(temp);
	}

	public ByteSequence prepend(byte[] bytes) {
		return this.prepend(new ByteSequence(bytes));
	}

	public ByteSequence prepend(ByteSequence that) {
		byte[] temp = new byte[that.length() + this.length()];
		System.arraycopy(that.getByteArray(), 0, temp, 0, that.length());
		System.arraycopy(this.getByteArray(), 0, temp, that.length(),
				this.length());
		return new ByteSequence(temp);
	}

	// TODO insert function

	public ByteSequence append(byte that) {
		byte[] temp = new byte[this.length() + 1];
		System.arraycopy(this.getByteArray(), 0, temp, 0, this.length());
		temp[this.length()] = that;
		return new ByteSequence(temp);
	}

	public ByteSequence append(byte[] bytes) {
		return this.append(new ByteSequence(bytes));
	}

	public ByteSequence append(ByteSequence that) {
		byte[] temp = new byte[this.length() + that.length()];
		System.arraycopy(this.getByteArray(), 0, temp, 0, this.length());
		System.arraycopy(that.getByteArray(), 0, temp, this.length(),
				that.length());
		return new ByteSequence(temp);
	}

	public void setByteAt(int pos, byte subs) {
		seq[pos] = subs;
	}

	public ByteSequence xor(byte that) {
		int len = Math.min(this.length(), 1);
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[i] = (byte) (this.byteAt(i) ^ that);
		}
		return new ByteSequence(result);
	}

	public ByteSequence xor(ByteSequence that) {
		int len = Math.min(this.length(), that.length());
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[i] = (byte) (this.byteAt(i) ^ that.byteAt(i));
		}
		return new ByteSequence(result);
	}

	public ByteSequence range(int from, int to) {
		return new ByteSequence(Arrays.copyOfRange(seq, from, to));
	}

	@Override
	public String toString() {
		return new String(seq, DEFAULT_CHARSET);
	}

	public String toString(Charset charset) {
		return new String(seq, charset);
	}

	public String toHexString() {
		return this.toHexString(null);
	}

	public String toHexString(String sep) {
		boolean insertSep = true;
		if (sep == null || sep.length() == 0) {
			insertSep = false;
		}
		StringBuilder temp = new StringBuilder(seq.length);
		for (int i = 0; i < seq.length; i++) {
			int b = seq[i];
			while (b < 0) {
				b += 256;
			}
			if (b < 16) {
				temp.append('0');
			}
			temp.append(Integer.toHexString(b));
			if (insertSep && i < seq.length - 1) {
				temp.append(sep);
			}
		}
		return temp.toString();
	}

	public static ByteSequence fromNumString(String input, int base) {
		int charPerByte = 2;
		if (base <= 2) {
			charPerByte = 8;
		} else if (base <= 3) {
			charPerByte = 6;
		} else if (base <= 6) {
			charPerByte = 4;
		} else if (base <= 15) {
			charPerByte = 3;
		}
		return fromNumString(input, base, charPerByte);
	}

	public static ByteSequence fromNumString(String input, int base,
			int charPerByte) {
		input = input.replaceAll("\\s+", "");
		if (input.length() == 0 || input.length() % charPerByte != 0) {
			return EMPTY_SEQUENCE;
		}
		int len = input.length() / charPerByte;
		byte[] out = new byte[len];
		for (int i = 0; i < len; i++) {
			try {
				int temp = Integer.parseInt(
						input.substring(charPerByte * i, charPerByte * i
								+ charPerByte), base);
				while (temp > 127) {
					temp -= 256;
				}
				out[i] = (byte) temp;
			} catch (NumberFormatException e) {
				return new ByteSequence(out);
			}
		}
		return new ByteSequence(out);
	}

	public static ByteSequence fromHexString(String input) {
		return fromNumString(input, 16, 2);
	}

	@Override
	public ByteSequence clone() throws CloneNotSupportedException {
		return new ByteSequence(Arrays.copyOf(seq, seq.length));
	}
}
