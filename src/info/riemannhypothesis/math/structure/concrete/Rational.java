/**
 * 
 */
package info.riemannhypothesis.math.structure.concrete;

import info.riemannhypothesis.math.structure.Field;
import info.riemannhypothesis.math.structure.VectorSpaceNormed;

/**
 * @author MarkusSchepke
 * 
 */
public class Rational implements Field<Rational>,
        VectorSpaceNormed<Rational, Rational>, Comparable<Rational> {

    private final long n, d;
    private final double real;

    public Rational(long numerator, long denominator) {
        if (numerator == 0) {
            n = 0;
            d = 1;
            real = 0;
            return;
        }
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator must not be zero.");
        }
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        assert denominator > 0;
        long gcd = gcd(numerator, denominator);
        if (gcd > 1) {
            numerator /= gcd;
            denominator /= gcd;
        }
        n = numerator;
        d = denominator;
        real = n / (double) d;
    }

    @Override
    public Rational negate() {
        return new Rational(-n, d);
    }

    @Override
    public Rational subtract(Rational that) {
        return new Rational(this.n * that.d - this.d * that.n, this.d * that.d);
    }

    @Override
    public boolean isZero() {
        return n == 0;
    }

    @Override
    public Rational add(Rational that) {
        return new Rational(this.n * that.d + this.d * that.n, this.d * that.d);
    }

    @Override
    public Rational multiply(Rational that) {
        return new Rational(this.n * that.n, this.d * that.d);
    }

    @Override
    public boolean isOne() {
        return n == d;
    }

    @Override
    public Rational inverse() {
        return new Rational(d, n);
    }

    @Override
    public Rational divide(Rational that) {
        return new Rational(this.n * that.d, this.d * that.n);
    }

    @Override
    public int compareTo(Rational o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double norm() {
        return Math.abs(real);
    }

    public double doubleValue() {
        return real;
    }

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void main(String... args) {
        Rational r = new Rational(0, 0);
        System.out.println("Test: " + r.n + "/" + r.d);
    }
}
