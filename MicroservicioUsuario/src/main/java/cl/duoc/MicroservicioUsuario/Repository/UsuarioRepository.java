package cl.duoc.MicroservicioUsuario.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.MicroservicioUsuario.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{

    Optional<Usuario> findById(String rut);

}
