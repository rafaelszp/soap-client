package com.example.soapclient.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.w3schools.webservices.TempConvertSoap;

import static org.junit.Assert.*;


@RunWith(Arquillian.class)
public class WebserviceTest {

	static Logger logger = Logger.getLogger(WebserviceTest.class.getName());
	
	static String WEBSERVICE_URL = "http://www.w3schools.com/webservices/tempconvert.asmx?WSDL";
	
	TempConvertSoap tempConvertSoap;
	
	@Deployment
    public static WebArchive createDeployment() {
        WebArchive war =  ShrinkWrap.create(WebArchive.class,"soap-client.war")
        		.addPackage(TempConvertSoap.class.getPackage())
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        
        logger.info(war.toString(true));
        return war;
    }
	
	@Before
	public void setup() throws MalformedURLException{
		URL wsdlDocumentLocation = new URL(WEBSERVICE_URL);
	    String namespaceURI = "http://www.w3schools.com/webservices/";
	    String servicePart = "TempConvert";
	    String portName = "TempConvertSoap";
	    QName serviceQN = new QName(namespaceURI, servicePart);
	    QName portQN = new QName(namespaceURI, portName);
	 
	    // Creates a service instance
	    Service service = Service.create(wsdlDocumentLocation, serviceQN);
	    tempConvertSoap = service.getPort(portQN, TempConvertSoap.class);
		
	}
	
	
	@Test
	public void deveConverterDeCelsiusParaFahrenheit(){
		
		String celsius = "30";
		String fah = tempConvertSoap.celsiusToFahrenheit(celsius);
		
		assertEquals("86",fah);
		logger.info(String.format("Celsius %s => Fahrenheit: %s",celsius,fah));
	}
	
	@Test
	public void deveConverterDeFahrenheitParaCelsius(){
		
		String fah = "86";
		String celsius = tempConvertSoap.fahrenheitToCelsius(fah);
		
		assertEquals("30",celsius);
		logger.info(String.format("Fahrenheit %s => Celsius: %s",fah,celsius));
	}

}
