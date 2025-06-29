
package cl.duoc.MicroservicioUsuario.Service;

import cl.duoc.MicroservicioUsuario.Model.Usuario;
import cl.duoc.MicroservicioUsuario.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // importante para inicializar los mocks
    }

    private Usuario crearUsuarioEjemplo(Long id) {
        return new Usuario(
            id,
            "12345678-9",
            "Cristiano Ronaldo",
            "cristiano@gmail.com",
            40,
            "Goat2025",
            LocalDateTime.now()
        );
    }

    @Test
    void testBuscarTodos() {
        Usuario u1 = crearUsuarioEjemplo(1L);
        Usuario u2 = crearUsuarioEjemplo(2L);
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Usuario> resultado = usuarioService.buscarTodos();

        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorRut() {
        Usuario usuario = crearUsuarioEjemplo(1L);
        when(usuarioRepository.findByRut("12345678-9")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorRut("12345678-9");

        assertTrue(resultado.isPresent());
        assertEquals("Cristiano Ronaldo", resultado.get().getNombre());
    }

    @Test
    void testGuardar() {
        Usuario usuario = crearUsuarioEjemplo(1L);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario guardado = usuarioService.guardar(usuario);

        assertNotNull(guardado);
        assertEquals("cristiano@gmail.com", guardado.getEmail());
    }

    @Test
    void testEliminarPorRut() {
        doNothing().when(usuarioRepository).deleteByRut("12345678-9");

        usuarioService.eliminarPorRut("12345678-9");

        verify(usuarioRepository, times(1)).deleteByRut("12345678-9");
    }

    @Test
    void testActualizar() {
        Usuario existente = crearUsuarioEjemplo(1L);
        Usuario nuevosDatos = new Usuario(
            1L,
            "12345678-9",
            "Cristiano Ronaldo",
            "cristiano@gmail.com",
            30,
            "Goat2025",
            existente.getFechaCreacion()
        );

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        Usuario actualizado = usuarioService.actualizar(1L, nuevosDatos);

        assertEquals("Cristiano Ronaldo", actualizado.getNombre());
        assertEquals("cristiano@gmail.com", actualizado.getEmail());
        verify(usuarioRepository).save(existente);
    }
}
