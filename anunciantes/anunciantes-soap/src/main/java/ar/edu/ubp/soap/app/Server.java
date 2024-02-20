package ar.edu.ubp.soap.app;

import ar.edu.ubp.soap.ws.AnunciantesWS;
import jakarta.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        AnunciantesWS implementor = new AnunciantesWS();
        String address = "http://localhost:8880/anunciante";
        Endpoint.publish(address, implementor);                
    }
}
