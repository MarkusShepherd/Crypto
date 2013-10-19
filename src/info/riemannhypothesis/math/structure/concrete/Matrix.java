package info.riemannhypothesis.math.structure.concrete;

import info.riemannhypothesis.math.structure.Field;
import info.riemannhypothesis.math.structure.Ring;
import info.riemannhypothesis.math.structure.VectorSpace;

import java.util.ArrayList;

public class Matrix<F extends Field<F>> implements VectorSpace<Matrix<F>, F>,
		Ring<Matrix<F>> {

	private final int rows, cols;
	private final ArrayList<ArrayList<F>> elements;

	private Matrix<F> transpose;
	private String string;

	public Matrix(F[][] elements) {
		rows = elements.length;
		cols = elements[0].length;
		this.elements = new ArrayList<ArrayList<F>>(rows);
		for (int i = 0; i < rows; i++) {
			ArrayList<F> temp = new ArrayList<F>(cols);
			for (int j = 0; j < cols; j++) {
				temp.add(elements[i][j]);
			}
			this.elements.add(temp);
		}
	}

	public Matrix(ArrayList<ArrayList<F>> elements) {
		rows = elements.size();
		cols = elements.get(0).size();
		/*
		 * for (ArrayList<F> row : elements) { if (row.size() != cols) { throw
		 * new IllegalArgumentException(); } }
		 */
		this.elements = elements;
	}

	public Matrix(int rows, int cols, F fill) {
		this.rows = rows;
		this.cols = cols;
		elements = new ArrayList<ArrayList<F>>(rows);
		for (int i = 0; i < rows; i++) {
			ArrayList<F> temp = new ArrayList<F>(cols);
			for (int j = 0; j < cols; j++) {
				temp.add(fill);
			}
			this.elements.add(temp);
		}
	}

	public int rows() {
		return rows;
	}

	public int cols() {
		return cols;
	}

	ArrayList<ArrayList<F>> elements() {
		return elements;
	}

	public F get(int row, int col) {
		return elements.get(row).get(col);
	}

	@Override
	public Matrix<F> negate() {
		ArrayList<ArrayList<F>> newElements = new ArrayList<ArrayList<F>>(rows);
		for (int i = 0; i < rows; i++) {
			ArrayList<F> temp = new ArrayList<F>(cols);
			for (int j = 0; j < cols; j++) {
				temp.add(elements.get(i).get(j));
			}
			newElements.add(temp);
		}
		return new Matrix<F>(newElements);
	}

	@Override
	public Matrix<F> add(Matrix<F> that) throws IllegalArgumentException {
		if (rows != that.rows() || cols != that.cols()) {
			throw new IllegalArgumentException();
		}
		ArrayList<ArrayList<F>> newElements = new ArrayList<ArrayList<F>>(rows);
		for (int i = 0; i < rows; i++) {
			ArrayList<F> temp = new ArrayList<F>(cols);
			for (int j = 0; j < cols; j++) {
				temp.add(get(i, j).add(that.get(i, j)));
			}
			newElements.add(temp);
		}
		return new Matrix<F>(newElements);
	}

	@Override
	public Matrix<F> subtract(Matrix<F> that) throws IllegalArgumentException {
		if (rows != that.rows() || cols != that.cols()) {
			throw new IllegalArgumentException();
		}
		ArrayList<ArrayList<F>> newElements = new ArrayList<ArrayList<F>>(rows);
		for (int i = 0; i < rows; i++) {
			ArrayList<F> temp = new ArrayList<F>(cols);
			for (int j = 0; j < cols; j++) {
				temp.add(get(i, j).subtract(that.get(i, j)));
			}
			newElements.add(temp);
		}
		return new Matrix<F>(newElements);
	}

	@Override
	public Matrix<F> multiply(F that) {
		ArrayList<ArrayList<F>> newElements = new ArrayList<ArrayList<F>>(rows);
		for (int i = 0; i < rows; i++) {
			ArrayList<F> temp = new ArrayList<F>(cols);
			for (int j = 0; j < cols; j++) {
				temp.add(get(i, j).multiply(that));
			}
			newElements.add(temp);
		}
		return new Matrix<F>(newElements);
	}

	@Override
	public Matrix<F> multiply(Matrix<F> that) {
		if (cols != that.rows()) {
			throw new IllegalArgumentException();
		}
		ArrayList<ArrayList<F>> newElements = new ArrayList<ArrayList<F>>(rows);
		for (int i = 0; i < rows; i++) {
			ArrayList<F> temp = new ArrayList<F>(that.cols());
			for (int j = 0; j < that.cols(); j++) {
				F value = get(i, 0).multiply(that.get(0, j));
				for (int k = 1; k < cols; k++) {
					value = value.add(this.get(i, k).multiply(that.get(k, j)));
				}
				temp.add(value);
			}
			newElements.add(temp);
		}
		if (rows == that.cols()) {
			return new SquareMatrix<F>(newElements);
		}
		return new Matrix<F>(newElements);
	}

	@Override
	public boolean isZero() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (!get(i, j).isZero()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isOne() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == j) {
					if (!get(i, j).isOne()) {
						return false;
					}
				} else {
					if (!get(i, j).isZero()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public Matrix<F> transpose() {
		if (this.transpose != null) {
			return this.transpose;
		}
		ArrayList<ArrayList<F>> newElements = new ArrayList<ArrayList<F>>(cols);
		for (int i = 0; i < cols; i++) {
			ArrayList<F> temp = new ArrayList<F>(rows);
			for (int j = 0; j < rows; j++) {
				temp.add(this.get(j, i));
			}
			newElements.add(temp);
		}
		this.transpose = new Matrix<F>(newElements);
		return this.transpose;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public String toString() {
		if (string != null && string.length() > 0) {
			return string;
		}
		StringBuilder builder = new StringBuilder("[");
		for (int i = 0; i < rows; i++) {
			builder.append("[");
			for (int j = 0; j < cols; j++) {
				builder.append(get(i, j).toString());
				if (j < cols - 1) {
					builder.append(", ");
				}
			}
			builder.append("]");
			if (i < rows - 1) {
				builder.append(", ");
			}
		}
		builder.append("]");
		string = builder.toString();
		return string;
	}

	public Matrix<F> deleteRowAndColumn(int row, int col) {
		if (rows == 1 || cols == 1 || row < 0 || col < 0 || row >= rows
				|| col >= cols) {
			throw new IllegalArgumentException();
		}
		ArrayList<ArrayList<F>> newElements = new ArrayList<ArrayList<F>>(
				rows - 1);
		for (int i = 0; i < rows; i++) {
			if (i == row) {
				continue;
			}
			ArrayList<F> temp = new ArrayList<F>(cols - 1);
			for (int j = 0; j < cols; j++) {
				if (j == col) {
					continue;
				}
				temp.add(get(i, j));
			}
			newElements.add(temp);
		}
		if (rows == cols) {
			return new SquareMatrix<F>(newElements);
		}
		return new Matrix<F>(newElements);
	}

	public static void main(String[] args) {
		Matrix<Complex> A = new Matrix<Complex>(new Complex[][] {
				{ Complex.ONE, new Complex(2), Complex.I },
				{ Complex.I, Complex.ONE, Complex.I } });
		System.out.println("A: " + A.toString() + "; dimension: " + A.rows()
				+ "x" + A.cols());
		System.out.println("Transposed: " + A.transpose().toString());
		Matrix<Complex> B = new Matrix<Complex>(new Complex[][] {
				{ Complex.ONE, new Complex(2) }, { Complex.ONE, Complex.I },
				{ Complex.PI, new Complex(3) } });
		System.out.println("B: " + B.toString() + "; dimension: " + B.rows()
				+ "x" + B.cols());
		System.out.println("Transposed: " + B.transpose().toString());
		SquareMatrix<Complex> AtB = (SquareMatrix<Complex>) A.multiply(B);
		System.out.println("A * B: " + AtB.toString());
		System.out.println("det(A * B): " + AtB.det().toString());
		SquareMatrix<Complex> BtA = (SquareMatrix<Complex>) B.multiply(A);
		System.out.println("B * A: " + BtA.toString());
		System.out.println("det(B * A): " + BtA.det().toString());
		SquareMatrix<Complex> temp = (SquareMatrix<Complex>) BtA
				.deleteRowAndColumn(0, 0);
		System.out.println("(B * A) submatrix 0, 0: " + temp.toString()
				+ "; det: " + temp.det() + "; minor: " + BtA.minor(0, 0)
				+ "; cofactor: " + BtA.cofactor(0, 0));
		temp = (SquareMatrix<Complex>) BtA.deleteRowAndColumn(1, 0);
		System.out.println("(B * A) submatrix 1, 0: " + temp.toString()
				+ "; det: " + temp.det() + "; minor: " + BtA.minor(1, 0)
				+ "; cofactor: " + BtA.cofactor(1, 0));
		temp = (SquareMatrix<Complex>) BtA.deleteRowAndColumn(2, 0);
		System.out.println("(B * A) submatrix 2, 0: " + temp.toString()
				+ "; det: " + temp.det() + "; minor: " + BtA.minor(2, 0)
				+ "; cofactor: " + BtA.cofactor(2, 0));
		temp = (SquareMatrix<Complex>) BtA.deleteRowAndColumn(1, 1);
		System.out.println("(B * A) submatrix 1, 1: " + temp.toString()
				+ "; det: " + temp.det() + "; minor: " + BtA.minor(1, 1)
				+ "; cofactor: " + BtA.cofactor(1, 1));
	}
}
