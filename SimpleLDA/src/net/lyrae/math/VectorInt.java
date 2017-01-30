package net.lyrae.math;

public class VectorInt {
	private int[] vector;
	
	public VectorInt(int size) {
		vector = new int[size];
	}
	
	public int size() {
		return vector.length;
	}
	
	public void set(int index, int value) {
		vector[index] = value;
	}
	
	public int get(int index) {
		return vector[index];
	}
	
	public int sum() {
		int sum = 0;
		for (int i : vector)
			sum += i;	
		return sum;
	}
	
	public int dot(VectorInt lVector) 
		throws Exception
	{
		if (size() != lVector.size())
			throw new Exception("The vectors have different size");
		
		int product = 0;
		for (int i = 0; i < size(); ++i)
			product += get(i)*lVector.get(i);	
		return product;
	}
	
	public VectorInt add(VectorInt lVector) 
		throws Exception
	{
		if (size() != lVector.size())
			throw new Exception("The vectors have different size");
		
		VectorInt result = new VectorInt(size());
		for (int i = 0; i < size(); ++i)
			result.set(i, get(i) + lVector.get(i));	
		return result;
	}
	
	public VectorInt sub(VectorInt lVector) 
			throws Exception
	{
		if (size() != lVector.size())
			throw new Exception("The vectors have different size");
		
		VectorInt result = new VectorInt(size());
		for (int i = 0; i < size(); ++i)
			result.set(i, get(i) - lVector.get(i));	
		return result;
	}
	
	public VectorInt mult(VectorInt lVector) 
			throws Exception
	{
		if (size() != lVector.size())
			throw new Exception("The vectors have different size");
		
		VectorInt result = new VectorInt(size());
		for (int i = 0; i < size(); ++i)
			result.set(i, get(i) * lVector.get(i));	
		return result;
	}
	
	public VectorInt scaleAdd(int scalar) 
			throws Exception
	{
		VectorInt result = new VectorInt(size());
		for (int i = 0; i < size(); ++i)
			result.set(i, scalar+get(i));
		return result;
	}
	
	public VectorInt scaleSub(int scalar) 
			throws Exception
	{
		VectorInt result = new VectorInt(size());
		for (int i = 0; i < size(); ++i)
			result.set(i, get(i)-scalar);
		return result;
	}
	
	public VectorInt scaleMult(int scalar) 
			throws Exception
	{
		VectorInt result = new VectorInt(size());
		for (int i = 0; i < size(); ++i)
			result.set(i, scalar*get(i));
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		
		out.append("[ ");
		for (int i = 0; i < vector.length-1; ++i) {
			out.append(vector[i]);
			out.append(", ");
		}
		
		out.append(vector[vector.length-1]);
		out.append(" ]");
		
		return out.toString();
	}
}
