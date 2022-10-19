package web.service;

import javax.jws.WebService;

@WebService(endpointInterface="web.service.MathWebService")
public class MathWebServiceImpl implements MathWebService {

	@Override
	public int add(int a, int b) {
		return a+b;
	}

	@Override
	public int substract(int a, int b) {
		return a-b;
	}

	@Override
	public int multiply(int a, int b) {
		return a*b;
	}

	@Override
	public int divide(int a, int b) throws IllegalArgumentException {
		if (b == 0)
			throw new IllegalArgumentException("Error: division by zero");
		return a/b;
	}

}
