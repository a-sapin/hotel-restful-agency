package web.service.client.main;

import java.net.MalformedURLException;
import java.net.URL;

import web.service.clientpackage.MathWebService;
import web.service.clientpackage.MathWebServiceImplService;

public class Main {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://localhost:8080/mathwebservice?wsdl");
			MathWebServiceImplService serviceImpl = new MathWebServiceImplService(url);
			MathWebService proxy = serviceImpl.getMathWebServiceImplPort();
			
			int a = 15;
			int b = 0;
			System.out.println(a + " + " + b + " = " + proxy.add(a, b));
			System.out.println(a + " - " + b + " = " + proxy.substract(a, b)); //minor spelling mistake
			System.out.println(a + " * " + b + " = " + proxy.multiply(a, b));
			System.out.println(a + " / " + b + " = " + proxy.divide(a, b));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
