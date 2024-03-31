package ar.edu.ubp.soap.app;

import ar.edu.ubp.soap.ws.AnunciantesWS;
import jakarta.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println(
                    "Debe proporcionar el nombre de la base de datos ademas del puerto del servidor.");
            System.exit(1);
        }

        String databaseName = args[0];
        String port = args[1];

        AnunciantesWS implementor = new AnunciantesWS(databaseName);
        String address = "http://localhost:" + port + "/soap/anunciante";
        Endpoint.publish(address, implementor);
    }
}
