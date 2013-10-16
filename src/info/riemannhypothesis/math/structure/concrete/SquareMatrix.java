package info.riemannhypothesis.math.structure.concrete;

import info.riemannhypothesis.math.structure.Field;

public class SquareMatrix<F extends Field<F>> extends Matrix<F> {

	public SquareMatrix(int n, F fill) {
		super(n, n, fill);
		// TODO Auto-generated constructor stub
	}

	public SquareMatrix<F> inverse() {
		// TODO Auto-generated method stub
		return null;
	}

	public SquareMatrix<F> divide(SquareMatrix<F> that) {
		// TODO Auto-generated method stub
		return null;
	}

	public F det() {
		// TODO Auto-generated method stub
		return null;
	}
}
