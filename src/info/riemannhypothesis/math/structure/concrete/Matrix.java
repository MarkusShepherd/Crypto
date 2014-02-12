package info.riemannhypothesis.math.structure.concrete;

import info.riemannhypothesis.math.structure.Field;
import info.riemannhypothesis.math.structure.Ring;
import info.riemannhypothesis.math.structure.VectorSpace;

import java.util.ArrayList;

public class Matrix<F extends Field<F>> implements VectorSpace<Matrix<F>, F>,
		Ring<Matrix<F>> {

	private final int rows, cols;
	private final F[][] elements;

	private Matrix<F> transpose;
	private String string;

	public Matrix(F[][] elements) {
		rows = elements.length;
		cols = elements[0].length;
		this.elements = elements;
	}

	@SuppressWarnings("unchecked")
	public Matrix(ArrayList<ArrayList<F>> elements) {
		rows = elements.size();
		cols = elements.get(0).size();
		this.elements = (F[][]) new Field[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.elements[i][j] = elements.get(i).get(j);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Matrix(int rows, int cols, F fill) {
		this.rows = rows;
		this.cols = cols;
		elements = (F[][]) new Field[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				elements[i][j] = fill;
			}
		}
	}

	public int rows() {
		return rows;
	}

	public int cols() {
		return cols;
	}

	F[][] elements() {
		return elements;
	}

	public F get(int row, int col) {
		return elements[row][col];
	}

	@Override
	public Matrix<F> negate() {
		@SuppressWarnings("unchecked")
		F[][] newElements = (F[][]) new Field[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				newElements[i][j] = elements[i][j].negate();
			}
		}
		return new Matrix<F>(newElements);
	}

	@Override
	public Matrix<F> add(Matrix<F> that) throws IllegalArgumentException {
		if (rows != that.rows() || cols != that.cols()) {
			throw new IllegalArgumentException();
		}
		@SuppressWarnings("unchecked")
		F[][] newElements = (F[][]) new Field[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				newElements[i][j] = this.get(i, j).add(that.get(i, j));
			}
		}
		return new Matrix<F>(newElements);
	}

	@Override
	public Matrix<F> subtract(Matrix<F> that) throws IllegalArgumentException {
		if (this.rows() != that.rows() || this.cols() != that.cols()) {
			throw new IllegalArgumentException();
		}
		@SuppressWarnings("unchecked")
		F[][] newElements = (F[][]) new Field[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				newElements[i][j] = this.get(i, j).subtract(that.get(i, j));
			}
		}
		return new Matrix<F>(newElements);
	}

	@Override
	public Matrix<F> multiply(F that) {
		@SuppressWarnings("unchecked")
		F[][] newElements = (F[][]) new Field[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				newElements[i][j] = this.get(i, j).multiply(that);
			}
		}
		return new Matrix<F>(newElements);
	}

	@Override
	public Matrix<F> multiply(Matrix<F> that) {
		if (this.cols() != that.rows()) {
			throw new IllegalArgumentException();
		}
		@SuppressWarnings("unchecked")
		F[][] newElements = (F[][]) new Field[this.rows()][that.cols()];
		for (int i = 0; i < this.rows(); i++) {
			for (int j = 0; j < that.cols(); j++) {
				F value = this.get(i, 0).multiply(that.get(0, j));
				for (int k = 1; k < this.cols(); k++) {
					value = value.add(this.get(i, k).multiply(that.get(k, j)));
				}
				newElements[i][j] = value;
			}
		}
		if (this.rows() == that.cols()) {
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
		@SuppressWarnings("unchecked")
		F[][] newElements = (F[][]) new Field[this.cols()][this.rows()];
		for (int i = 0; i < this.cols(); i++) {
			for (int j = 0; j < this.rows(); j++) {
				newElements[i][j] = this.get(j, i);
			}
		}
		this.transpose = new Matrix<F>(newElements);
		return this.transpose;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Matrix<?>)) {
			return false;
		}
		Matrix<?> that = (Matrix<?>) obj;
		for (int i = 0; i < this.cols(); i++) {
			for (int j = 0; j < this.rows(); j++) {
				if (!this.get(i, j).equals(that.get(i, j))) {
					return false;
				}
			}
		}
		return true;
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
		@SuppressWarnings("unchecked")
		F[][] newElements = (F[][]) new Field[this.rows()-1][this.cols()-1];
		for (int i = 0; i < rows; i++) {
			if (i == row) {
				continue;
			}
			int pi = i > row ? i-1 : i;
			for (int j = 0; j < cols; j++) {
				if (j == col) {
					continue;
				}
				int pj = j > col ? j-1 : j;
				newElements[pi][pj] = this.get(i, j);
			}
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
