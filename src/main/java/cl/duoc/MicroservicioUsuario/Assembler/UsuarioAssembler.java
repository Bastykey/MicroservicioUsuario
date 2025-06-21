package cl.duoc.MicroservicioUsuario.Assembler;

import cl.duoc.MicroservicioUsuario.DTO.UsuarioDTO;
import cl.duoc.MicroservicioUsuario.Model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAssembler {

    public UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setRut(usuario.getRut());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setEdad(usuario.getEdad()); 
        return dto;
    }

    public Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setRut(dto.getRut());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setEdad(dto.getEdad()); 
        return usuario;
    }
}