package cl.duoc.MicroservicioUsuario.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.MicroservicioUsuario.Model.Usuario;
import cl.duoc.MicroservicioUsuario.Service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/Usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioservice;

    @GetMapping
    public ResponseEntity<List<Usuario>> Listar(){
        List<Usuario> usuarios = usuarioservice.BuscarTodo();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(usuarios);
        }
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Usuario> BuscarUsuario(@PathVariable Long rut){
        try {
            Usuario usuario = usuarioservice.BuscarUnUsuario(rut);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario){
        Usuario usuarioNuevo = usuarioservice.Guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminar(@PathVariable Long rut){
        try {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
    
    @PutMapping("/{rut}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long rut, @RequestBody Usuario usuario){
        try {
            Usuario usuarioEditar = usuarioservice.BuscarUnUsuario(rut);
            usuarioEditar.setRut(rut);
            usuarioEditar.setNombre(usuario.getNombre());
            usuarioEditar.setMail(usuario.getMail());
            usuarioEditar.setIdcurso(usuario.getIdcurso());

            usuarioservice.Guardar(usuarioEditar);
            return ResponseEntity.ok(usuarioEditar);

        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

}