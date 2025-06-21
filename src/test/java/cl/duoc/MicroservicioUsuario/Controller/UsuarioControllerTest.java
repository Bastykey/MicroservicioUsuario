
package cl.duoc.MicroservicioUsuario.Controller;

import cl.duoc.MicroservicioUsuario.Assembler.UsuarioAssembler;
import cl.duoc.MicroservicioUsuario.DTO.UsuarioDTO;
import cl.duoc.MicroservicioUsuario.Model.Usuario;
import cl.duoc.MicroservicioUsuario.Service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioAssembler usuarioAssembler;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarUsuarios_deberiaListarUsuariosComoDtosCuandoExis() {
        Usuario usuario = new Usuario();
        usuario.setRut("12345678-9");
        usuario.setNombre("Juan");
        usuario.setEmail("juan@duoc.cl");
        usuario.setEdad(25);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setRut("12345678-9");
        dto.setNombre("Juan");
        dto.setEmail("juan@duoc.cl");
        dto.setEdad(25);

        when(usuarioService.BuscarTodo()).thenReturn(List.of(usuario));
        when(usuarioAssembler.toDTO(usuario)).thenReturn(dto);

        ResponseEntity<List<UsuarioDTO>> respuesta = usuarioController.listarUsuarios();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("Juan", respuesta.getBody().get(0).getNombre());

        verify(usuarioService).BuscarTodo();
        verify(usuarioAssembler).toDTO(usuario);
    }

    @Test
    void buscarUsuario_deberiaDevolverUnUsuarioDtoCuandoExistePorRut() {
        String rut = "12345678-9";
        Usuario usuario = new Usuario();
        usuario.setRut(rut);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setRut(rut);

        when(usuarioService.BuscarUnUsuario(rut)).thenReturn(Optional.of(usuario));
        when(usuarioAssembler.toDTO(usuario)).thenReturn(dto);

        ResponseEntity<UsuarioDTO> respuesta = usuarioController.buscarUsuario(rut);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(rut, respuesta.getBody().getRut());

        verify(usuarioService).BuscarUnUsuario(rut);
        verify(usuarioAssembler).toDTO(usuario);
    }

    @Test
    void buscarUsuario_noExiste_deberiaRetornar404() {
        String rut = "00000000-0";

        when(usuarioService.BuscarUnUsuario(rut)).thenReturn(Optional.empty());

        ResponseEntity<UsuarioDTO> respuesta = usuarioController.buscarUsuario(rut);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());

        verify(usuarioService).BuscarUnUsuario(rut);
        verify(usuarioAssembler, never()).toDTO(any());
    }

    @Test
    void guardarUsuario_deberiaRetornar404SiNoSeEncuentraElUsuarioPorRut() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setRut("12345678-9");
        dto.setNombre("Juan");
        dto.setEmail("juan@duoc.cl");

        Usuario usuario = new Usuario();
        usuario.setRut("12345678-9");
        usuario.setNombre("Juan");
        usuario.setEmail("juan@duoc.cl");

        when(usuarioAssembler.toEntity(dto)).thenReturn(usuario);
        when(usuarioService.Guardar(any(Usuario.class))).thenReturn(usuario);
        when(usuarioAssembler.toDTO(usuario)).thenReturn(dto);

        ResponseEntity<UsuarioDTO> respuesta = usuarioController.guardarUsuario(dto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals("Juan", respuesta.getBody().getNombre());

        verify(usuarioAssembler).toEntity(dto);
        verify(usuarioService).Guardar(usuario);
        verify(usuarioAssembler).toDTO(usuario);
    }

    @Test
    void actualizarUsuario_existente_deberiaActualizarYRetornarDTO() {
        String rut = "12345678-9";

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Nombre Actualizado");
        dto.setEmail("nuevo@duoc.cl");
        dto.setEdad(30);

        Usuario existente = new Usuario();
        existente.setRut(rut);
        existente.setNombre("Antiguo");
        existente.setEmail("viejo@duoc.cl");
        existente.setEdad(20);

        when(usuarioService.BuscarUnUsuario(rut)).thenReturn(Optional.of(existente));
        when(usuarioService.Guardar(existente)).thenReturn(existente);
        when(usuarioAssembler.toDTO(existente)).thenReturn(dto);

        ResponseEntity<UsuarioDTO> respuesta = usuarioController.actualizarUsuario(rut, dto);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Nombre Actualizado", respuesta.getBody().getNombre());

        verify(usuarioService).BuscarUnUsuario(rut);
        verify(usuarioService).Guardar(existente);
        verify(usuarioAssembler).toDTO(existente);
    }

    @Test
    void actualizarUsuario_deberiaRetornar404CuandoUsuarioNoExisteParaActualizar() {
        String rut = "99999999-9";
        UsuarioDTO dto = new UsuarioDTO();

        when(usuarioService.BuscarUnUsuario(rut)).thenReturn(Optional.empty());

        ResponseEntity<UsuarioDTO> respuesta = usuarioController.actualizarUsuario(rut, dto);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());

        verify(usuarioService).BuscarUnUsuario(rut);
        verify(usuarioService, never()).Guardar(any());
        verify(usuarioAssembler, never()).toDTO(any());
    }

    @Test
    void eliminarUsuario_deberiaEliminarUsuarioYRetornarNoContentt() {
        String rut = "12345678-9";

        
        ResponseEntity<Void> respuesta = usuarioController.eliminarUsuario(rut);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(usuarioService).Eliminar(rut);
    }
}

//NOTA: SE DEBEN IMPLEMENTAR NOMBRES DESCRIBIENDO EL COMPARTAMIENTO DE CADA TEST.