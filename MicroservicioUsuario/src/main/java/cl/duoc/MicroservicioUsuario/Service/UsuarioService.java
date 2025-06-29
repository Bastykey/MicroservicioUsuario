package cl.duoc.MicroservicioUsuario.Service;

import cl.duoc.MicroservicioUsuario.Model.Usuario;
import cl.duoc.MicroservicioUsuario.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorRut(String rut) {
        return usuarioRepository.findByRut(rut);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminarPorRut(String rut) {
        usuarioRepository.deleteByRut(rut);
    }

    
    public Usuario actualizar(Long id, Usuario datosActualizados) {
        Usuario existente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        existente.setNombre(datosActualizados.getNombre());
        existente.setRut(datosActualizados.getRut());
        existente.setEmail(datosActualizados.getEmail());
        existente.setEdad(datosActualizados.getEdad());
        existente.setPassword(datosActualizados.getPassword());

        return usuarioRepository.save(existente);
    }
}