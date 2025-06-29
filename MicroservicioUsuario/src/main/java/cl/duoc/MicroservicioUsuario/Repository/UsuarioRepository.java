package cl.duoc.MicroservicioUsuario.Repository;

import cl.duoc.MicroservicioUsuario.Model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Optional<Usuario> findByRut(String rut);
    void deleteByRut(String rut);
    
 
}