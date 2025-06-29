package cl.duoc.MicroservicioUsuario.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "USUARIO", schema = "ADMIN")
@Schema(description = "Entidad que representa a un usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Schema(description = "ID del usuario", example = "1")
    private Long id;

    @Column(name = "RUT", nullable = false, unique = true)
    @Schema(description = "RUT del usuario", example = "12.345.678-9")
    private String rut;

    @Column(name = "NOMBRE", nullable = false)
    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String nombre;

    @Column(name = "CORREO", nullable = false, unique = true)
    @Schema(description = "Correo electrónico", example = "juan@correo.com")
    private String email;

    @Column(name = "EDAD", nullable = false)
    @Schema(description = "Edad del usuario", example = "30")
    private Integer edad;

    @Column(name = "PASSWORD", nullable = false)
    @Schema(description = "Contraseña del usuario", example = "clave123")
    private String password;

    @Column(name = "FECHA_CREACION", nullable = false, updatable = false)
    @Schema(description = "Fecha en que se creó el usuario", example = "2025-06-29T13:34:25")
    private LocalDateTime fechaCreacion;

    public Usuario() {}

    public Usuario(Long id, String rut, String nombre, String email, Integer edad, String password, LocalDateTime fechaCreacion) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.password = password;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
