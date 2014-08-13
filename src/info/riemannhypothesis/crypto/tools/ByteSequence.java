package info.riemannhypothesis.crypto.tools;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class ByteSequence implements Cloneable, Comparable<ByteSequence> {

    public static final Charset      DEFAULT_CHARSET = Charset.defaultCharset();
    public static final ByteSequence EMPTY_SEQUENCE  = new ByteSequence(
                                                             new byte[] {});

    private final byte[]             seq;

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

    public int intAt(int pos) {
        int temp = seq[pos];
        while (temp < 0) {
            temp += 256;
        }
        return temp;
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

    public ByteSequence add(byte add) {
        byte[] result = new byte[length()];

        System.arraycopy(seq, 0, result, 0, length());

        int pos = length() - 1;

        while (add != 0 && pos >= 0) {

            byte old = seq[pos];
            int temp = old + add;

            final int shift = 1 << Byte.SIZE;

            while (temp > Byte.MAX_VALUE) {
                temp -= shift;
            }
            while (temp < Byte.MIN_VALUE) {
                temp += shift;
            }

            if (old < 0 && temp >= 0) {
                add = 1;
            } else {
                add = 0;
            }

            result[pos--] = (byte) temp;
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
        return this.toNumString(16, 2, null);
    }

    public String toHexString(String sep) {
        return this.toNumString(16, 2, sep);
    }

    public String toNumString(int base) {
        return toNumString(base, charPerByte(base), null);
    }

    public String toNumString(int base, int charPerByte) {
        return toNumString(base, charPerByte, null);
    }

    public String toNumString(int base, String sep) {
        return toNumString(base, charPerByte(base), sep);
    }

    public String toNumString(int base, int charPerByte, String sep) {
        boolean insertSep = sep != null && sep.length() > 0;
        StringBuilder temp = new StringBuilder(seq.length);
        for (int i = 0; i < seq.length; i++) {
            StringBuilder c = new StringBuilder(charPerByte);
            int b = intAt(i);
            for (int d = 0; d < charPerByte; d++) {
                c.append(Integer.toString(b % base, base));
                b = b / base;
            }
            temp.append(c.reverse());
            if (insertSep && i < seq.length - 1) {
                temp.append(sep);
            }
        }
        return temp.toString();
    }

    public static ByteSequence fromNumString(String input, int base) {
        return fromNumString(input, base, charPerByte(base));
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

    public static ByteSequence fromInt(int that, int length) {
        final int l = Math.max(length, 4);
        ByteBuffer bb = ByteBuffer.allocate(l);
        bb.position(l - 4);
        return new ByteSequence(bb.putInt(that).array());
        // putInt(that).array());
    }

    public static ByteSequence fromLong(long that, int length) {
        final int l = Math.max(length, 8);
        ByteBuffer bb = ByteBuffer.allocate(l);
        bb.position(l - 8);
        return new ByteSequence(bb.putLong(that).array());
        // putInt(that).array());
    }

    @Override
    public ByteSequence clone() throws CloneNotSupportedException {
        return new ByteSequence(Arrays.copyOf(seq, seq.length));
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ByteSequence)) {
            return false;
        }
        ByteSequence that = (ByteSequence) obj;
        return Arrays.equals(this.seq, that.seq);
    }

    @Override
    public int compareTo(ByteSequence that) {
        for (int i = 0; i < Math.min(this.length(), that.length()); i++) {
            int diff = this.intAt(i) - that.intAt(i);
            if (diff != 0) {
                return diff;
            }
        }
        return this.length() - that.length();
    }

    @Override
    public int hashCode() {
        ByteBuffer bb = ByteBuffer.wrap(seq);
        int hash = 0;
        while (bb.position() <= bb.capacity() - 4) {
            hash ^= bb.getInt();
        }
        while (bb.position() <= bb.capacity() - 2) {
            hash ^= bb.getShort();
        }
        while (bb.position() <= bb.capacity() - 1) {
            hash ^= bb.get();
        }
        return hash;
    }

    private static int charPerByte(int base) {
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
        return charPerByte;
    }

    public static void main(String[] args) {
        ByteSequence bs1 = ByteSequence.fromInt(
                (int) (Math.random() * Integer.MAX_VALUE), 0);
        ByteSequence bs2 = ByteSequence.fromLong(0xFFFFFFFFFFL, 8);
        System.out.println(bs1.toNumString(2, 8, " "));
        System.out.println(bs1.toNumString(8, 8, " "));
        System.out.println(bs1.toNumString(10, 8, " "));
        System.out.println(bs1.toNumString(16, 8, " "));
        System.out.println(bs2.toNumString(2, 8, " "));
        System.out.println(bs1.hashCode());
        System.out.println(bs2.hashCode());
        System.out.println(bs2.compareTo(bs1));
        System.out.println(bs1.add((byte) -1).toNumString(2, 8, " "));
        System.out.println(bs1.add((byte) 100).toNumString(2, 8, " "));
        System.out.println(bs2.add((byte) -1).toNumString(2, 8, " "));
        System.out.println(bs2.add((byte) 100).toNumString(2, 8, " "));

    }
}
