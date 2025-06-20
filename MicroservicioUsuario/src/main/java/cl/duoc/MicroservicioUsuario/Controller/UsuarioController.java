package cl.duoc.MicroservicioUsuario.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.MicroservicioUsuario.Model.Usuario;
import cl.duoc.MicroservicioUsuario.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuarios", description = "Operaciones relacionadas a usuarios")
@RestController
@RequestMapping("/api/v1/usuarios") 
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


@Operation(summary = "Crear un nuevo usuario", description = "Permite registrar un usuario en el sistema")
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
        content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = """
                {
                  "rut": "12345678-9",
                  "nombre": "Juan Pérez",
                  "correo": "juan.perez@duoc.cl"
                }
                """
            )
        )
    ),
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, datos inválidos",
        content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = """
                {
                  "error": "Datos inválidos",
                  "message": "El RUT debe tener un formato válido"
                }
                """
            )
        )
    )
})


@GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.BuscarTodo();
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

@Operation(summary = "Buscar usuario por RUT", description = "Obtiene los datos de un usuario a partir de su RUT")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente", 
        content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = """
                {
                  "rut": "12345678-9",
                  "nombre": "Juan Pérez",
                  "correo": "juan.perez@duoc.cl"
                }
                """
            ))),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
})

@GetMapping("/{rut}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable String rut) {
        return usuarioService.BuscarUnUsuario(rut)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

@PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
        usuario.setFechaCreacion(new java.util.Date()); // Se asigna fecha de creación automáticamente
        Usuario usuarioNuevo = usuarioService.Guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
        
    }
@Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente usando su RUT")
@ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el RUT especificado",
        content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                name = "Usuario no encontrado",
                value = """
                {
                  "error": "Usuario no encontrado",
                  "message": "No se encontró un usuario con el RUT proporcionado"
                }
                """
            )
        )
    ),
    @ApiResponse(responseCode = "400", description = "RUT inválido",
        content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                name = "RUT inválido",
                value = """
                {
                  "error": "Solicitud inválida",
                  "message": "El RUT enviado no tiene un formato válido"
                }
                """
            )
        )
    )
})
@DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String rut) {
        usuarioService.Eliminar(rut);
        return ResponseEntity.noContent().build();
    }

@Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente mediante su RUT")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
        content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                name = "Respuesta exitosa",
                value = """
                {
                  "rut": "12345678-9",
                  "nombre": "Ana Actualizada",
                  "correo": "ana.actualizada@duoc.cl",
                  "edad": 28
                }
                """
            )
        )
    ),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el RUT especificado",
        content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                name = "Usuario no encontrado",
                value = """
                {
                  "error": "Usuario no encontrado",
                  "message": "No existe un usuario con el RUT proporcionado"
                }
                """
            )
        )
    ),
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud",
        content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                name = "Datos inválidos",
                value = """
                {
                  "error": "Validación fallida",
                  "message": "El campo 'correo' debe tener un formato válido"
                }
                """
            )
        )
    )
})
@PutMapping("/{rut}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable String rut, @RequestBody Usuario usuario) {
        return usuarioService.BuscarUnUsuario(rut)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombre(usuario.getNombre());
                    usuarioExistente.setEmail(usuario.getEmail());
                    usuarioExistente.setEdad(usuario.getEdad());
                    usuarioService.Guardar(usuarioExistente);
                    return ResponseEntity.ok(usuarioExistente);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}