package co.edu.uco.victusresidencias.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"co.edu.uco.victusresidencias.controller"})
public class VictusresidenciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(VictusresidenciasApplication.class, args);
	}

}
