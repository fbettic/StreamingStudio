package ar.edu.ubp.soap.app;

import ar.edu.ubp.soap.db.DatabaseConnection;
import ar.edu.ubp.soap.resources.utils.AuthUrlGenerator;
import ar.edu.ubp.soap.ws.PlataformasWS;
import jakarta.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println(
                    "Debe proporcionar el nombre de la base de datos, puerto del servidor y la url del front client.");
            System.exit(1);
        }

        String databaseName = args[0];
        String port = args[1];
        String frontUrl = args[2];

        AuthUrlGenerator.getInstance().setBaseUrl(frontUrl);

        DatabaseConnection.getInstance().setDatabaseName(databaseName);

        PlataformasWS implementor = new PlataformasWS();
        String address = "http://localhost:" + port + "/soap/plataforma";
        Endpoint.publish(address, implementor);
    }
}
