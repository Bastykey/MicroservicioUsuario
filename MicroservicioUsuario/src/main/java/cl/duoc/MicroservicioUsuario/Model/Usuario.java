package cl.duoc.MicroservicioUsuario.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    private Long rut;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String mail;
    
    @Column(nullable = false)
    private Integer idcurso;
}
