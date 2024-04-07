package ar.edu.ubp.rest.plataformasrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.edu.ubp.rest.plataformasrest.utils.AuthUrlGenerator;

@SpringBootApplication
public class PlataformasRestApplication {

	public static void main(String[] args) {
		if (args.length < 1) {
            System.err.println(
                    "Debe proporcionar la url del front client.");
            System.exit(1);
        }
		
        String frontUrl = args[0];

        AuthUrlGenerator.getInstance().setBaseUrl(frontUrl);
		SpringApplication.run(PlataformasRestApplication.class, args);
	}

}
