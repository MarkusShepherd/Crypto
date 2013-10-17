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
			this.elements.add(temp);
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
			this.elements.add(temp);
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
			this.elements.add(temp);
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
			this.elements.add(temp);
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
			this.elements.add(temp);
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
			this.elements.add(temp);
		}
		this.transpose = new Matrix<F>(newElements);
		return this.transpose;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}
