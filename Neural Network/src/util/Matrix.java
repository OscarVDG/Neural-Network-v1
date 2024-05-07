package util;

import java.util.Arrays;

public class Matrix {

	double[][] data;
	int rows, cols;

	// vector version
	public Matrix(Matrix m, boolean copy) {
		this(m.rows, m.cols);
		
		if (copy) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					data[i][j] = m.data[i][j];
				}
			}
		}
		
	}
	
	public Matrix(int rows_) {
		this(rows_, 1);
	}

	public Matrix(int rows_, int cols_) {
		rows = rows_;
		cols = cols_;

		data = new double[rows_][cols_];
	}

	public static Matrix add(Matrix m1, Matrix m2) {
		if (m1.cols != m2.cols || m1.rows != m2.rows) {
			System.out.println("Shape Mismatch");
			return null;
		}

		Matrix out = new Matrix(m1.rows, m1.cols);

		for (int i = 0; i < m1.rows; i++) {
			for (int j = 0; j < m1.cols; j++) {
				out.data[i][j] = m1.data[i][j] + m2.data[i][j];
			}
		}

		return out;

	}

	public static Matrix subtract(Matrix m1, Matrix m2) {
		if (m1.cols != m2.cols || m1.rows != m2.rows) {
			System.out.println("Shape Mismatch");
			return null;
		}

		Matrix out = new Matrix(m1.rows, m1.cols);

		for (int i = 0; i < m1.rows; i++) {
			for (int j = 0; j < m1.cols; j++) {
				out.data[i][j] = m1.data[i][j] - m2.data[i][j];
			}
		}

		return out;

	}

	public static Matrix multiply(Matrix m1, Matrix m2) {

		if (m1.cols != m2.rows) {
			System.out.println("Shape Mismatch");
			return null;
		}

		Matrix out = new Matrix(m1.rows, m2.cols);

		for (int i = 0; i < out.rows; i++) {
			for (int j = 0; j < out.cols; j++) {

				double sum = 0;

				for (int k = 0; k < m1.cols; k++) {
					sum += m1.data[i][k] * m2.data[k][j];
				}

				out.data[i][j] = sum;

			}
		}

		return out;

	}

	public static Matrix arrayToMatrix(int[] in) {
		Matrix out = new Matrix(in.length, 1);

		for (int i = 0; i < in.length; i++) {
			out.data[i][0] = in[i];
		}

		return out;
	}

	public static Matrix arrayToMatrix(double[] in) {

		Matrix out = new Matrix(in.length, 1);

		for (int i = 0; i < in.length; i++) {
			out.data[i][0] = in[i];
		}

		return out;

	}

	// Non statics

	public Matrix scale(double scalar) {

		Matrix out = new Matrix(rows, cols);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				out.data[i][j] = data[i][j] * scalar;
			}
		}

		return out;
	}

	public Matrix rectify() {

		Matrix out = new Matrix(rows, cols);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				out.data[i][j] = Math.max(0.01 * data[i][j], data[i][j]);
			}
		}

		return out;

	}

	// This only cares about if the neurons are negative or positive
	// Designed to take in the final output in a layer, a, but should work on z as well
	//Apply to the derivative
	public Matrix dRect(Matrix a) {

		if (cols != a.cols || rows != a.rows) {
			System.out.println("Shape Mismatch");
			return null;
		}

		Matrix out = new Matrix(rows, cols);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				if (a.data[i][j] < 0) {
					out.data[i][j] = data[i][j] * 0.01;
				} else {
					out.data[i][j] = data[i][j];
				}
			}
		}
		
		return out;
	}

	public Matrix transpose() {

		Matrix out = new Matrix(cols, rows);

		// Notice for loops are other way around
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				out.data[i][j] = data[j][i];
			}
		}

		return out;

	}
	
	public Matrix fill(int in) {
		
		Matrix out = new Matrix(rows, cols);
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				out.data[i][j] = in;
			}
		}
		
		return out;
	}
	
	// Returns the sum of the squares of the components, essentially the magnitude
	// squared
	public double getSquareSum() {

		if (cols != 1) {
			System.out.println("Matrix must be vector");
			return 0.0;
		}

		double sqMag = 0.0;

		for (int i = 0; i < rows; i++) {

			sqMag += data[i][0] * data[i][0];

		}

		return sqMag;

	}
	
	public void randomize(double min, double max) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				data[i][j] = (Math.random() * (max - min)) + min;
				
			}
		}
	}
	
	public void floor() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = Math.floor(data[i][j]);
			}
		}
	}

	public void print() {
		System.out.println(Arrays.deepToString(data));
	}

	public double[] toArray() {
		if (cols != 1) {
			System.out.println("Matrix must be vector");
			return null;
		}

		double[] out = new double[rows];
		for (int i = 0; i < rows; i++) {
			out[i] = data[i][0];
		}

		return out;

	}
}
