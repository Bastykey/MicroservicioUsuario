/*package cl.duoc.MicroservicioUsuario.Client;

import cl.duoc.MicroservicioUsuario.DTO.VentaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class VentaClient {

    private final WebClient webClient;

    public VentaClient(@Value("${servicio.ventas.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<VentaDTO> obtenerVentasPorRut(String rutUsuario) {
        return webClient.get()
                .uri("/api/v1/ventas/usuario/{rut}", rutUsuario)
                .retrieve()
                .bodyToFlux(VentaDTO.class);
    }
}
    /* */