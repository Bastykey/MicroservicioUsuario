package cl.duoc.MicroservicioUsuario.Controller;

import cl.duoc.MicroservicioUsuario.DTO.UsuarioDTO;
import cl.duoc.MicroservicioUsuario.Model.Usuario;
import cl.duoc.MicroservicioUsuario.Service.UsuarioService;
import cl.duoc.MicroservicioUsuario.Assembler.UsuarioAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas a usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioAssembler usuarioAssembler;

    @Operation(summary = "Listar todos los usuarios")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.BuscarTodo();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<UsuarioDTO> dtos = usuarios.stream()
                .map(usuarioAssembler::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Buscar usuario por RUT", description = "Obtiene los datos de un usuario a partir de su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                {
                  "rut": "12345678-9",
                  "nombre": "Juan Pérez",
                  "email": "juan.perez@duoc.cl"
                }
                """))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{rut}")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable String rut) {
        return usuarioService.BuscarUnUsuario(rut)
                .map(usuarioAssembler::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Permite registrar un usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                {
                  "rut": "12345678-9",
                  "nombre": "Juan Pérez",
                  "email": "juan.perez@duoc.cl"
                }
                """))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, datos inválidos",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                {
                  "error": "Datos inválidos",
                  "message": "El RUT debe tener un formato válido"
                }
                """)))
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> guardarUsuario(@RequestBody UsuarioDTO dto) {
        Usuario usuario = usuarioAssembler.toEntity(dto);
        usuario.setFechaCreacion(new Date());
        Usuario guardado = usuarioService.Guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioAssembler.toDTO(guardado));
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente mediante su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                {
                  "rut": "12345678-9",
                  "nombre": "Ana Actualizada",
                  "email": "ana.actualizada@duoc.cl",
                  "edad": 28
                }
                """))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                {
                  "error": "Usuario no encontrado",
                  "message": "No existe un usuario con el RUT proporcionado"
                }
                """))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                {
                  "error": "Validación fallida",
                  "message": "El campo 'correo' debe tener un formato válido"
                }
                """)))
    })
    @PutMapping("/{rut}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable String rut, @RequestBody UsuarioDTO dto) {
        return usuarioService.BuscarUnUsuario(rut)
                .map(usuario -> {
                    usuario.setNombre(dto.getNombre());
                    usuario.setEmail(dto.getEmail());
                    usuario.setEdad(dto.getEdad());
                    Usuario actualizado = usuarioService.Guardar(usuario);
                    return ResponseEntity.ok(usuarioAssembler.toDTO(actualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente usando su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                {
                  "error": "Usuario no encontrado",
                  "message": "No se encontró un usuario con el RUT proporcionado"
                }
                """))),
        @ApiResponse(responseCode = "400", description = "RUT inválido",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                {
                  "error": "Solicitud inválida",
                  "message": "El RUT enviado no tiene un formato válido"
                }
                """)))
    })
    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String rut) {
        usuarioService.Eliminar(rut);
        return ResponseEntity.noContent().build();
    }
}