package info.riemannhypothesis.crypto;

import info.riemannhypothesis.crypto.tools.ByteSequence;

import java.math.BigInteger;

public class Factor {

    public static void main(String[] args) {
        BigInteger N1 = new BigInteger(
                "179769313486231590772930519078902473361797697894230657273430081157732675805505620686985379449212982959585501387537164015710139858647833778606925583497541085196591615128057575940752635007475935288710823649949940771895617054361149474865046711015101563940680527540071584560878577663743040086340742855278549092581");
        BigInteger p = factor(N1);
        BigInteger q = N1.divide(p);
        BigInteger M = p.subtract(BigInteger.ONE).multiply(
                q.subtract(BigInteger.ONE));
        System.out.println(p);

        BigInteger N2 = new BigInteger(
                "648455842808071669662824265346772278726343720706976263060439070378797308618081116462714015276061417569195587321840254520655424906719892428844841839353281972988531310511738648965962582821502504990264452100885281673303711142296421027840289307657458645233683357077834689715838646088239640236866252211790085787877");
        System.out.println(factor(N2));

        BigInteger N3 = new BigInteger(
                "720062263747350425279564435525583738338084451473999841826653057981916355690188337790423408664187663938485175264994017897083524079135686877441155132015188279331812309091996246361896836573643119174094961348524639707885238799396839230364676670221627018353299443241192173812729276147530748597302192751375739387929");
        System.out.println(factor2(N3));

        BigInteger c = new BigInteger(
                "22096451867410381776306561134883418017410069787892831071731839143676135600120538004282329650473509424343946219751512256465839967942889460764542040581564748988013734864120452325229320176487916666402997509188729971690526083222067771600019329260870009579993724077458967773697817571267229951148662959627934791540");
        BigInteger e = BigInteger.valueOf(65537);
        BigInteger d = e.modInverse(M);
        BigInteger plain = c.modPow(d, N1);
        String hex = plain.toString(16);
        String unpadded = hex.substring(hex.indexOf("00"));
        // System.out.println(plain);
        // System.out.println(plain.toString(16));
        ByteSequence sol = ByteSequence.fromHexString(unpadded);
        System.out.println(sol.toString());
    }

    public static BigInteger factor(BigInteger N) {
        BigInteger A = sqrt(N);
        BigInteger diff = N.subtract(A.multiply(A));
        while (diff.compareTo(BigInteger.ZERO) > 0) {
            A = A.add(BigInteger.ONE);
            diff = N.subtract(A.multiply(A));
        }
        BigInteger p, q;
        do {
            BigInteger x = sqrt(A.multiply(A).subtract(N));
            p = A.subtract(x);
            q = A.add(x);
            A = A.add(BigInteger.ONE);
        } while (!N.subtract(p.multiply(q)).equals(BigInteger.ZERO));
        return p;
    }

    public static BigInteger factor2(BigInteger N) {
        BigInteger SixN = N.multiply(BigInteger.valueOf(6));
        BigInteger TwentyfourN = SixN.multiply(BigInteger.valueOf(4));
        BigInteger TwoA = sqrt(TwentyfourN);
        BigInteger diff = TwentyfourN.subtract(TwoA.multiply(TwoA));
        while (diff.compareTo(BigInteger.ZERO) > 0) {
            TwoA = TwoA.add(BigInteger.ONE);
            diff = TwentyfourN.subtract(TwoA.multiply(TwoA));
        }
        BigInteger p, q;
        do {
            BigInteger twox = sqrt(TwoA.multiply(TwoA).subtract(TwentyfourN));
            BigInteger sixp = TwoA.subtract(twox);
            BigInteger fourq = TwoA.add(twox);
            p = sixp.divide(BigInteger.valueOf(6));
            q = fourq.divide(BigInteger.valueOf(4));
            if (N.subtract(p.multiply(q)).equals(BigInteger.ZERO)) {
                break;
            }
            sixp = TwoA.add(twox);
            fourq = TwoA.subtract(twox);
            p = sixp.divide(BigInteger.valueOf(6));
            q = fourq.divide(BigInteger.valueOf(4));
            TwoA = TwoA.add(BigInteger.ONE);
        } while (!N.subtract(p.multiply(q)).equals(BigInteger.ZERO));
        if (p.compareTo(q) < 0) {
            return p;
        }
        return q;
    }

    public static BigInteger sqrt(BigInteger x) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = x.shiftRight(5).add(BigInteger.valueOf(8));
        while (b.compareTo(a) >= 0) {
            BigInteger mid = a.add(b).shiftRight(1);
            if (mid.multiply(mid).compareTo(x) > 0) {
                b = mid.subtract(BigInteger.ONE);
            } else {
                a = mid.add(BigInteger.ONE);
            }
        }
        return a.subtract(BigInteger.ONE);
    }

}
