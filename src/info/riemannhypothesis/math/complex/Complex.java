package info.riemannhypothesis.math.complex;

public class Complex {

	public final static Complex ZERO = new Complex(0, 0);
	public final static Complex ONE = new Complex(1, 0);
	public final static Complex I = new Complex(0, 1);
	public final static Complex PI = new Complex(Math.PI, 0);

	private final double re, im;

	public Complex(org.jscience.mathematics.number.Complex z) {
		this(z.getReal(), z.getImaginary());
	}

	public Complex(double re) {
		this(re, 0);
	}

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public double re() {
		return re;
	}

	public double im() {
		return im;
	}

	public double abs() {
		return Math.sqrt(re * re + im * im);
	}

	public double absSq() {
		return re * re + im * im;
	}

	public double arg() {
		return Math.atan2(im, re);
	}

	public Complex negate() {
		return new Complex(-re, -im);
	}

	public Complex conjugate() {
		return new Complex(re, -im);
	}

	public Complex add(double x) {
		return new Complex(re + x, im);
	}

	public Complex add(Complex z) {
		return new Complex(re + z.re(), im + z.im());
	}

	public Complex subtract(double x) {
		return new Complex(re - x);
	}

	public Complex subtract(Complex z) {
		return new Complex(re - z.re(), im - z.im());
	}

	public Complex multiply(double x) {
		return new Complex(re * x, im * x);
	}

	public Complex multiply(Complex z) {
		return new Complex(re * z.re() - im * z.im(), im * z.re() + re * z.im());
	}

	public Complex divide(double x) {
		return new Complex(re / x, im / x);
	}

	public Complex divide(Complex z) {
		double absSq = z.absSq();
		return new Complex((re * z.re() + im * z.im()) / absSq,
				(im * z.re() - re * z.im()) / absSq);
	}

	public Complex inverse() {
		double absSq = this.absSq();
		return new Complex(re / absSq, -im / absSq);
	}

	public Complex exp() {
		return exp(this);
	}

	public static Complex exp(Complex z) {
		if (ZERO.equals(z)) {
			return ONE;
		}
		double x = z.re(), y = z.im();
		double expRe = Math.exp(x);
		return new Complex(expRe * Math.cos(y), expRe * Math.sin(y));
	}

	public Complex log() {
		return log(this);
	}

	public static Complex log(Complex z) {
		return new Complex(Math.log(z.abs()), z.arg());
	}

	public Complex pow(Complex exponent) {
		return exp(exponent.multiply(log()));
	}

	public static Complex pow(Complex base, Complex exponent) {
		return exp(exponent.multiply(log(base)));
	}

	public Complex sin() {
		return sin(this);
	}

	public static Complex sin(Complex z) {
		if (ZERO.equals(z)) {
			return ZERO;
		}
		double x = z.re(), y = z.im();
		return new Complex(Math.sin(x) * Math.cosh(y), Math.cos(x)
				* Math.sinh(y));
	}

	public Complex cos() {
		return cos(this);
	}

	public static Complex cos(Complex z) {
		if (ZERO.equals(z)) {
			return ONE;
		}
		double x = z.re(), y = z.im();
		return new Complex(Math.cos(x) * Math.cosh(y), -Math.sin(x)
				* Math.sinh(y));
	}

	public org.jscience.mathematics.number.Complex toJSComplex() {
		return org.jscience.mathematics.number.Complex.valueOf(re, im);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Complex)) {
			return false;
		}
		Complex compare = (Complex) obj;
		return re == compare.re() && im == compare.im();
	}

	@Override
	public String toString() {
		return "" + re + " + " + im + "i";
	}

	public static void main(String[] arg) {
		//Complex z = I.multiply(-0.034234);
		//Complex exp = exp(z.multiply(PI));
		System.out.println("Result: " + I.pow(I));
	}
}
