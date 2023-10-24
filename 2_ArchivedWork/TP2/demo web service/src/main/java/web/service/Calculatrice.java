package web.service;

public class Calculatrice {
	
	public int add(int a, int b) {
		return a+b;
	}

	public int substract(int a, int b) {
		return a-b;
	}

	public int multiply(int a, int b) {
		return a*b;
	}

	public int divide(int a, int b) throws IllegalArgumentException {
		if (b == 0)
			throw new IllegalArgumentException("Error: division by zero");
		return a/b;
	}
}
