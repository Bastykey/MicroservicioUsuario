package cl.duoc.MicroservicioUsuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"cl.duoc.MicroservicioUsuario", "cl.duoc.MicroservicioUsuario.Config"})
public class MicroservicioUsuarioApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicroservicioUsuarioApplication.class, args);
	}
}
