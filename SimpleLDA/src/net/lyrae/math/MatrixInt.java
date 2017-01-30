package net.lyrae.math;

public class MatrixInt {
	private VectorInt[] matrix;
	
	public MatrixInt(int rows, int columns) {
		matrix = new VectorInt[rows];
		for (int i = 0; i < numRows(); ++i)
			matrix[i] = new VectorInt(columns);
	}
	
	public int numRows() {
		return matrix.length;
	}
	
	public int numColumns() {
		return matrix[0].size();
	}
	
	public void set(int row, int column, int value) {
		matrix[row].set(column, value);
	}
	
	public int get(int row, int column) {
		return matrix[row].get(column);
	}
	
	public int sum() {
		int sum = 0;
		for (VectorInt row : matrix)
			sum += row.sum();
		return sum;
	}
	
	public int rowSum(int row) {
		return matrix[row].sum();
	}
	
	public int columnSum(int column) {
		int sum = 0;
		for (VectorInt row : matrix)
			sum += row.get(column);	
		return sum;
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		
		out.append("[ ");
		out.append(matrix[0].toString());
		for (int i = 1; i < matrix.length; ++i) {
			out.append(System.lineSeparator());
			out.append("  ");
			out.append(matrix[i].toString());
		}
		out.append(" ]");
		
		return out.toString();
	}
}
