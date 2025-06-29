
package cl.duoc.MicroservicioUsuario.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String rut;
    private String nombre;
    private String email;
    private int edad;
}