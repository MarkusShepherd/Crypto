package info.riemannhypothesis.math.structure.concrete;

import info.riemannhypothesis.math.structure.Field;
import info.riemannhypothesis.math.structure.Ring;
import info.riemannhypothesis.math.structure.VectorSpace;

import java.util.ArrayList;

public class Matrix<F extends Field<F>> implements VectorSpace<Matrix<F>, F>,
		Ring<Matrix<F>> {

	private final int rows, cols;
	private final ArrayList<ArrayList<F>> elements;

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

	@Override
	public Matrix<F> negate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<F> add(Matrix<F> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<F> subtract(Matrix<F> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<F> multiply(Matrix<F> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<F> multiply(F that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isZero() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOne() {
		// TODO Auto-generated method stub
		return false;
	}

}
