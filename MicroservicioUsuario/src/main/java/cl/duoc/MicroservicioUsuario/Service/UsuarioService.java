package cl.duoc.MicroservicioUsuario.Service;

import java.util.List;
import java.util.Optional;

import cl.duoc.MicroservicioUsuario.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.MicroservicioUsuario.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todos los usuarios
    public List<Usuario> BuscarTodo() {
        return usuarioRepository.findAll();
    }

    // Buscar un usuario por RUT con manejo seguro de errores
    public Optional<Usuario> BuscarUnUsuario(String rut) {
        return usuarioRepository.findById(rut);
    }

    // Guardar un nuevo usuario en la base de datos
    public Usuario Guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Eliminar un usuario por RUT
    public void Eliminar(String rut) {
        usuarioRepository.deleteAll();
    }
}