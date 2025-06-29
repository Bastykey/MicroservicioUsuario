package cl.duoc.MicroservicioUsuario.Controller;

import cl.duoc.MicroservicioUsuario.Model.Usuario;
import cl.duoc.MicroservicioUsuario.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones CRUD con documentación Swagger y HATEOAS")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usuario.class)))
    @GetMapping
    public CollectionModel<EntityModel<Usuario>> obtenerTodos() {
        List<EntityModel<Usuario>> usuarios = usuarioService.buscarTodos().stream()
                .map(usuario -> EntityModel.of(usuario,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                                .buscarPorRut(usuario.getRut())).withRel("ver_usuario")))
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                        .obtenerTodos()).withSelfRel());
    }

    @Operation(summary = "Buscar usuario por RUT", description = "Retorna los datos de un usuario específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/rut/{rut}")
    public ResponseEntity<EntityModel<Usuario>> buscarPorRut(@PathVariable String rut) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorRut(rut);
        return usuarioOpt.map(usuario -> {
            EntityModel<Usuario> model = EntityModel.of(usuario,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                            .buscarPorRut(rut)).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                            .obtenerTodos()).withRel("todos_usuarios"));
            return ResponseEntity.ok(model);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Agrega un nuevo usuario a la base de datos.")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.guardar(usuario);
        EntityModel<Usuario> model = EntityModel.of(nuevo,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                        .buscarPorRut(nuevo.getRut())).withRel("ver_usuario"));
        return ResponseEntity.status(201).body(model);
    }

    @Operation(summary = "Actualizar usuario por ID", description = "Actualiza los datos de un usuario existente.")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario datos) {
        Usuario actualizado = usuarioService.actualizar(id, datos);
        EntityModel<Usuario> model = EntityModel.of(actualizado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                        .buscarPorRut(actualizado.getRut())).withRel("ver_usuario"));
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Eliminar usuario por RUT", description = "Elimina un usuario de la base de datos.")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente")
    @DeleteMapping("/rut/{rut}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String rut) {
        usuarioService.eliminarPorRut(rut);
        return ResponseEntity.noContent().build();
    }
}