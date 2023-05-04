package es.mdef.gestionPreguntas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionPreguntasApplication {
	
	public static final Logger log = LoggerFactory.getLogger(GestionPreguntasApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GestionPreguntasApplication.class, args);
	}

}
