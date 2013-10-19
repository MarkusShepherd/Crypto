package info.riemannhypothesis.math.structure.concrete;

import info.riemannhypothesis.math.structure.Field;
import info.riemannhypothesis.math.structure.VectorSpaceNormed;

public class Real implements Field<Real>, VectorSpaceNormed<Real, Real>,
		Comparable<Real> {

	public final static Real ZERO = new Real(0);
	public final static Real ONE = new Real(1);
	public final static Real PI = new Real(Math.PI);

	private final double re;

	public Real(double re) {
		this.re = re;
	}

	public double re() {
		return re;
	}

	@Override
	public Real negate() {
		return new Real(-re);
	}

	@Override
	public Real subtract(Real that) {
		return new Real(re - that.re());
	}

	@Override
	public boolean isZero() {
		return equals(ZERO);
	}

	@Override
	public Real add(Real that) {
		return new Real(re + that.re());
	}

	@Override
	public Real multiply(Real that) {
		return new Real(re * that.re());
	}

	@Override
	public boolean isOne() {
		return equals(ONE);
	}

	@Override
	public Real inverse() {
		return new Real(1 / re);
	}

	@Override
	public Real divide(Real that) {
		return new Real(re / that.re());
	}

	@Override
	public double norm() {
		return abs();
	}

	public double abs() {
		return Math.abs(re);
	}

	@Override
	public int compareTo(Real that) {
		// return (int) (this.re - that.re);
		if (re < that.re()) {
			return -1;
		}
		if (re > that.re()) {
			return 1;
		}
		return 0;
	}

}
