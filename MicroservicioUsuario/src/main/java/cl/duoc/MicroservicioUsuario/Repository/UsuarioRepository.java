package cl.duoc.MicroservicioUsuario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.MicroservicioUsuario.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
