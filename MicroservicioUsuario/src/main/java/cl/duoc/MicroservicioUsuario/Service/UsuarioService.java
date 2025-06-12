package cl.duoc.MicroservicioUsuario.Service;

import java.util.List;

import cl.duoc.MicroservicioUsuario.Model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.MicroservicioUsuario.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository Usuariorepository;

    public List<Usuario> BuscarTodo(){
        return Usuariorepository.findAll();
    }

    public Usuario BuscarUnUsuario(Long rut){
        return Usuariorepository.findById(rut).get();
    }

    public Usuario Guardar(Usuario usuario){
        return Usuariorepository.save(usuario);
    }

    public void Eliminar(Long rut){
        Usuariorepository.deleteById(rut);
    }

}
