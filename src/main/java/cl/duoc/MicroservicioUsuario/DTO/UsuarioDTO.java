package cl.duoc.MicroservicioUsuario.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Este DTO Representa los datos de un usuario")
public class UsuarioDTO {

    @Schema(description = "RUT del usuario", example = "12.345.678-9", required = true)
    @NotBlank(message = "El RUT no puede estar vacío")
    private String rut;

    @Schema(description = "Nombre completo del usuario", example = "Alan Brito", required = true)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "Ejemplo1@correo.cl")
    @Email(message = "El correo debe tener un formato válido")
    private String email;

    @Schema(description = "Edad del usuario", example = "28")
    private Integer edad;
}