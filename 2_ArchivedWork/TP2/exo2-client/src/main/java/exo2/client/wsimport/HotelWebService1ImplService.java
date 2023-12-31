
package exo2.client.wsimport;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "HotelWebService1ImplService", targetNamespace = "http://serveur.exo2/", wsdlLocation = "http://localhost:8080/0/consultation?wsdl")
public class HotelWebService1ImplService
    extends Service
{

    private final static URL HOTELWEBSERVICE1IMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException HOTELWEBSERVICE1IMPLSERVICE_EXCEPTION;
    private final static QName HOTELWEBSERVICE1IMPLSERVICE_QNAME = new QName("http://serveur.exo2/", "HotelWebService1ImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/0/consultation?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        HOTELWEBSERVICE1IMPLSERVICE_WSDL_LOCATION = url;
        HOTELWEBSERVICE1IMPLSERVICE_EXCEPTION = e;
    }

    public HotelWebService1ImplService() {
        super(__getWsdlLocation(), HOTELWEBSERVICE1IMPLSERVICE_QNAME);
    }

    public HotelWebService1ImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), HOTELWEBSERVICE1IMPLSERVICE_QNAME, features);
    }

    public HotelWebService1ImplService(URL wsdlLocation) {
        super(wsdlLocation, HOTELWEBSERVICE1IMPLSERVICE_QNAME);
    }

    public HotelWebService1ImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, HOTELWEBSERVICE1IMPLSERVICE_QNAME, features);
    }

    public HotelWebService1ImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HotelWebService1ImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns HotelWebService1
     */
    @WebEndpoint(name = "HotelWebService1ImplPort")
    public HotelWebService1 getHotelWebService1ImplPort() {
        return super.getPort(new QName("http://serveur.exo2/", "HotelWebService1ImplPort"), HotelWebService1.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns HotelWebService1
     */
    @WebEndpoint(name = "HotelWebService1ImplPort")
    public HotelWebService1 getHotelWebService1ImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://serveur.exo2/", "HotelWebService1ImplPort"), HotelWebService1.class, features);
    }

    private static URL __getWsdlLocation() {
        if (HOTELWEBSERVICE1IMPLSERVICE_EXCEPTION!= null) {
            throw HOTELWEBSERVICE1IMPLSERVICE_EXCEPTION;
        }
        return HOTELWEBSERVICE1IMPLSERVICE_WSDL_LOCATION;
    }

}
