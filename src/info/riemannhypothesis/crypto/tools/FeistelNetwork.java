package info.riemannhypothesis.crypto.tools;

/**
 * @author Markus Schepke
 * @date 20 Aug 2014
 */
public class FeistelNetwork {

    private final Function<ByteSequence, ByteSequence>[] functions;

    public FeistelNetwork(Function<ByteSequence, ByteSequence>[] functions) {
        this.functions = functions;
    }

    public ByteSequence encrypt(ByteSequence input) {
        if ((input.length() % 2) != 0) {
            throw new IllegalArgumentException();
        }

        ByteSequence l = input.range(0, input.length() / 2);
        ByteSequence r = input.range(input.length() / 2, input.length());

        for (int i = 0; i < functions.length; i++) {
            Function<ByteSequence, ByteSequence> f = functions[i];
            ByteSequence newL = r;
            ByteSequence newR = f.apply(r).xor(l);
            l = newL;
            r = newR;
        }

        return l.append(r);
    }

    public ByteSequence decrypt(ByteSequence output) {
        if ((output.length() % 2) != 0) {
            throw new IllegalArgumentException();
        }

        ByteSequence l = output.range(0, output.length() / 2);
        ByteSequence r = output.range(output.length() / 2, output.length());

        for (int i = functions.length - 1; i >= 0; i--) {
            Function<ByteSequence, ByteSequence> f = functions[i];
            ByteSequence newL = f.apply(l).xor(r);
            ByteSequence newR = l;
            l = newL;
            r = newR;
        }

        return l.append(r);
    }

    public static void main(String[] args) {
        Function<ByteSequence, ByteSequence> f1 = new Function<ByteSequence, ByteSequence>() {
            @Override
            public ByteSequence apply(ByteSequence arg) {
                return arg.xor(new ByteSequence("HorrayTheWitchIsDead!"));
            }
        };
        Function<ByteSequence, ByteSequence> f2 = new Function<ByteSequence, ByteSequence>() {
            @Override
            public ByteSequence apply(ByteSequence arg) {
                return arg.xor(new ByteSequence("###JustATest...###"));
            }
        };
        Function<ByteSequence, ByteSequence> f3 = new Function<ByteSequence, ByteSequence>() {
            @Override
            public ByteSequence apply(ByteSequence arg) {
                return arg.xor(new ByteSequence(
                        "!@£$%^&*()1234989sjhvba alurfi aufasdf"));
            }
        };
        @SuppressWarnings("unchecked")
        final FeistelNetwork feistelNetwork = new FeistelNetwork(
                (Function<ByteSequence, ByteSequence>[]) new Function<?,?>[] { f1, f2, f3 });

        ByteSequence m = new ByteSequence("Secret message!!");
        ByteSequence c = feistelNetwork.encrypt(m);
        ByteSequence d = feistelNetwork.decrypt(c);

        System.out.println(m.toNumString(2, " "));
        System.out.println(c.toNumString(2, " "));
        System.out.println(d.toNumString(2, " "));
    }
}
