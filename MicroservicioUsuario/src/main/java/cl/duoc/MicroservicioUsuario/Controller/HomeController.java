package cl.duoc.MicroservicioUsuario.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "¡Bienvenido a la API de Usuarios!, Wena profe daniel, Ayudenos D:";
    }
}



