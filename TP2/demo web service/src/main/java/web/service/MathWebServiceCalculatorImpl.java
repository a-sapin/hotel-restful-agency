package web.service;

//import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface="web.service.MathWebService")
public class MathWebServiceCalculatorImpl implements MathWebService {
	
	private Calculatrice calculatrice = new Calculatrice();

	@Override
	public int add(int a, int b) {
		return calculatrice.add(a, b);
	}

	@Override
	public int substract(int a, int b) {
		return calculatrice.substract(a, b);
	}

	@Override
	public int multiply(int a, int b) {
		return calculatrice.multiply(a, b);
	}

	@Override
	public int divide(int a, int b) throws IllegalArgumentException {
		return calculatrice.divide(a, b);
	}

}
