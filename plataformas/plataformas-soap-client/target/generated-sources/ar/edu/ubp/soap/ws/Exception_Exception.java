
package ar.edu.ubp.soap.ws;

import jakarta.xml.ws.WebFault;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.1
 * Generated source version: 3.0
 * 
 */
@WebFault(name = "Exception", targetNamespace = "http://ws.soap.ubp.edu.ar/")
public class Exception_Exception
    extends java.lang.Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ar.edu.ubp.soap.ws.Exception faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public Exception_Exception(String message, ar.edu.ubp.soap.ws.Exception faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param cause
     * @param faultInfo
     * @param message
     */
    public Exception_Exception(String message, ar.edu.ubp.soap.ws.Exception faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: ar.edu.ubp.soap.ws.Exception
     */
    public ar.edu.ubp.soap.ws.Exception getFaultInfo() {
        return faultInfo;
    }

}
