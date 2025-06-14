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

@RestController
@RequestMapping("/api/v1/usuarios") // Ajustado a minúsculas para mayor coherencia
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
@GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.BuscarTodo();
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

    // Obtener un usuario por RUT
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

    // Eliminar un usuario
@DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String rut) {
        usuarioService.Eliminar(rut);
        return ResponseEntity.noContent().build();
    }

    // Actualizar datos de un usuario
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