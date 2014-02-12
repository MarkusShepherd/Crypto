package info.riemannhypothesis.math.structure.concrete;

import info.riemannhypothesis.math.structure.Field;

import java.util.ArrayList;

public class SquareMatrix<F extends Field<F>> extends Matrix<F> {

	private final int n;
	private F det;
	private SquareMatrix<F> inverse;

	public SquareMatrix(int n, F fill) {
		super(n, n, fill);
		this.n = n;
	}

	public SquareMatrix(F[][] elements) {
		super(elements);
		if (rows() != cols()) {
			throw new IllegalArgumentException();
		}
		this.n = rows();
	}

	public SquareMatrix(ArrayList<ArrayList<F>> elements) {
		super(elements);
		if (rows() != cols()) {
			throw new IllegalArgumentException();
		}
		this.n = rows();
	}

	public int n() {
		return n;
	}

	public SquareMatrix<F> inverse() {
		if (this.inverse != null) {
			return this.inverse;
		}
		F det = det();
		if (det.isZero()) {
			return null;
		}
		F detInv = det.inverse();
		@SuppressWarnings("unchecked")
		F[][] newElements = (F[][]) new Field[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				newElements[i][j] = cofactor(j, i).multiply(detInv);
			}
		}
		this.inverse = new SquareMatrix<F>(newElements);
		return inverse;
	}

	public SquareMatrix<F> multiply(SquareMatrix<F> that) {
		return (SquareMatrix<F>) super.multiply(that);
	}

	public SquareMatrix<F> divide(SquareMatrix<F> that) {
		return this.multiply(that.inverse());
	}

	public F det() {
		if (this.det != null) {
			return this.det;
		}
		if (n == 1) {
			this.det = get(0, 0);
		} else if (n == 2) {
			this.det = get(0, 0).multiply(get(1, 1)).subtract(
					get(1, 0).multiply(get(0, 1)));
		} else {
			F value = get(0, 0).multiply(cofactor(0, 0));
			for (int i = 1; i < n; i++) {
				value = value.add(get(i, 0).multiply(cofactor(i, 0)));
			}
			this.det = value;
		}
		return this.det;
	}

	public F minor(int row, int col) {
		if (n == 1 || row < 0 || col < 0 || row >= n || col >= n) {
			throw new IllegalArgumentException();
		}
		if (n == 2) {
			return get(1 - row, 1 - col);
		}
		return ((SquareMatrix<F>) deleteRowAndColumn(row, col)).det();
	}

	public F cofactor(int row, int col) {
		return (row + col) % 2 == 0 ? minor(row, col) : minor(row, col)
				.negate();
	}

	public F trace() {
		F value = get(0, 0);
		for (int i = 1; i < n; i++) {
			value = value.add(get(i, i));
		}
		return value;
	}

	public static void main(String[] args) {
		SquareMatrix<Complex> matrix = new SquareMatrix<Complex>(
				new Complex[][] { { Complex.ONE, new Complex(2.0000) },
						{ Complex.I, Complex.PI } });
		System.out.println("Matrix: " + matrix.toString() + "; dimension: "
				+ matrix.n());
		System.out.println("Transposed: " + matrix.transpose().toString());
		System.out.println("Determinant: " + matrix.det().toString());
		System.out.println("Trace: " + matrix.trace().toString());
		System.out.println("Matrix * Matrix: "
				+ matrix.multiply(matrix).toString());
		if (!matrix.det().isZero()) {
			System.out.println("Inverse: " + matrix.inverse().toString());
			System.out.println("Matrix * Inverse: "
					+ matrix.multiply(matrix.inverse()).toString());
			System.out.println("Inverse * Matrix: "
					+ matrix.inverse().multiply(matrix).toString());
		}
	}
}
