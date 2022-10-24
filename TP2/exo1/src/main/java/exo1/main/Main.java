package exo1.main;

import java.net.MalformedURLException;
import java.net.URL;

import exo1.Calculator;
import exo1.CalculatorSoap;

public class Main {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://www.dneonline.com/calculator.asmx?WSDL");
			Calculator service = new Calculator(url);
			CalculatorSoap proxy = service.getCalculatorSoap();
			
			int a = 15;
			int b = 3;
			
			System.out.println("TEST DU CLIENT AVEC A = 15 ET B = 3");
			System.out.println(proxy.add(a, b));
			System.out.println(proxy.subtract(a, b));
			System.out.println(proxy.multiply(a, b));
			System.out.println(proxy.divide(a, b));
			
			b = 0;
			
			System.out.println("\nTEST DU CLIENT AVEC A = 15 ET B = 0");
			System.out.println(proxy.add(a, b));
			System.out.println(proxy.subtract(a, b));
			System.out.println(proxy.multiply(a, b));
			System.out.println(proxy.divide(a, b)); //should throw exception
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
