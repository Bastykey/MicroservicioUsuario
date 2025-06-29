package cl.duoc.MicroservicioUsuario.Assembler;

import cl.duoc.MicroservicioUsuario.Controller.UsuarioController;
import cl.duoc.MicroservicioUsuario.DTO.UsuarioDTO;
import cl.duoc.MicroservicioUsuario.Model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<UsuarioDTO>> {

    @Override
    public EntityModel<UsuarioDTO> toModel(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO(
                usuario.getRut(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getEdad()
        );

        return EntityModel.of(dto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                        .buscarPorRut(usuario.getRut())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                        .obtenerTodos()).withRel("usuarios"));
    }
}
