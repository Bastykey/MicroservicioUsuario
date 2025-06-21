package cl.duoc.MicroservicioUsuario.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
                return new OpenAPI()
                .info(new Info()
                .title("API de Usuarios - DUOC")
                .version("1.1")
                .description("Documentación del Microservicio de Usuarios con Swagger - Springdoc")
                .contact(new Contact()
                .name("Equipo Stackwise DUOC")
                .email("ba.necul@duocuc.cl")
                .url("https://www.duoc.cl"))
                .license(new License()
                .name("Todos los derechos reservados")
                .url("ttp://localhost:8082/swagger-ui/index.html")));
    } 
    
}
