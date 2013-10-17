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
		ArrayList<ArrayList<F>> newElements = new ArrayList<ArrayList<F>>(n);
		for (int i = 0; i < n; i++) {
			ArrayList<F> temp = new ArrayList<F>(n);
			for (int j = 0; j < n; j++) {
				temp.add(cofactor(j, i).multiply(detInv));
			}
			newElements.add(temp);
		}
		this.inverse = new SquareMatrix<F>(newElements);
		return inverse;
	}

	public SquareMatrix<F> multiply(SquareMatrix<F> that) {
		return new SquareMatrix<F>(this.multiply(that).elements());
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
		return deleteRowAndColumn(row, col).det();
	}

	public SquareMatrix<F> deleteRowAndColumn(int row, int col) {
		if (n == 1 || row < 0 || col < 0 || row >= n || col >= n) {
			throw new IllegalArgumentException();
		}
		ArrayList<ArrayList<F>> newElements = new ArrayList<ArrayList<F>>(n - 1);
		for (int i = 0; i < n; i++) {
			if (i == row) {
				continue;
			}
			ArrayList<F> temp = new ArrayList<F>(n - 1);
			for (int j = 0; j < n; j++) {
				if (j == col) {
					continue;
				}
				temp.add(get(i, j));
			}
			newElements.add(temp);
		}
		return new SquareMatrix<F>(newElements);
	}

	public F cofactor(int row, int col) {
		return row + col % 2 == 0 ? minor(row, col) : minor(row, col).negate();
	}

	public F trace() {
		F value = get(0, 0);
		for (int i = 1; i < n; i++) {
			value = value.add(get(i, i));
		}
		return value;
	}
}
