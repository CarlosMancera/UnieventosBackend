package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.controllers.AdministradorController;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.CrearArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.EditarArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.InformacionArtistaDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.CrearCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.EditarCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.InformacionCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.ResumenCuponDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.CrearEventoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.EditarEventoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.FiltroEventoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.InformacionEventoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.ItemEventoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.ResumenEventoDTO;
import co.edu.uniquindio.proyecto.model.entities.Artista;
import co.edu.uniquindio.proyecto.model.enums.EstadoCupon;
import co.edu.uniquindio.proyecto.model.enums.EstadoEvento;
import co.edu.uniquindio.proyecto.model.enums.TipoCupon;
import co.edu.uniquindio.proyecto.model.enums.TipoEvento;
import co.edu.uniquindio.proyecto.services.interfaces.ArtistaService;
import co.edu.uniquindio.proyecto.services.interfaces.CuponService;
import co.edu.uniquindio.proyecto.services.interfaces.EventoService;
import co.edu.uniquindio.proyecto.services.interfaces.ImagenesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdministradorControllerTest {

    @Mock
    private ArtistaService artistaService;

    @Mock
    private CuponService cuponService;
    @Mock
    private EventoService eventoService;
    @Mock
    private ImagenesService imagenesService;


    @InjectMocks
    private AdministradorController administradorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarArtistas_deberiaRetornarListaYStatus200() throws Exception {
        List<InformacionArtistaDTO> artistasMock = Collections.emptyList();
        when(artistaService.listarArtistas()).thenReturn(artistasMock);

        ResponseEntity<MensajeDTO<List<InformacionArtistaDTO>>> response = administradorController.listarArtistas();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        MensajeDTO<List<InformacionArtistaDTO>> body = response.getBody();
        assertNotNull(body);
        assertFalse(body.error());
        assertEquals(artistasMock, body.respuesta());
    }

    @Test
    void buscarArtistasPorNombre_deberiaRetornarListaDeArtistas() {
        String nombre = "Carlos";
        List<InformacionArtistaDTO> artistasMock = Collections.singletonList(
                new InformacionArtistaDTO(1L, "Carlos", "Rock", "carlos@mail.com", "123456789", "ACTIVO")
        );

        when(artistaService.buscarArtistasPorNombre(nombre)).thenReturn(artistasMock);

        ResponseEntity<MensajeDTO<List<InformacionArtistaDTO>>> response = administradorController.buscarArtistasPorNombre(nombre);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        MensajeDTO<List<InformacionArtistaDTO>> body = response.getBody();
        assertNotNull(body);
        assertFalse(body.error());
        assertEquals(artistasMock, body.respuesta());
    }

    @Test
    void obtenerInformacionArtista_deberiaRetornarInfoArtista() throws Exception {
        String id = "1";
        InformacionArtistaDTO artistaMock = new InformacionArtistaDTO(1L, "Carlos", "Rock", "carlos@mail.com", "123456789", "ACTIVO");

        when(artistaService.obtenerInformacionArtista(1L)).thenReturn(artistaMock);

        ResponseEntity<MensajeDTO<InformacionArtistaDTO>> response = administradorController.obtenerInformacionArtista(id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        MensajeDTO<InformacionArtistaDTO> body = response.getBody();
        assertNotNull(body);
        assertFalse(body.error());
        assertEquals(artistaMock, body.respuesta());
    }


    @Test
    void eliminarArtista_deberiaRetornarMensajeExitoso() throws Exception {
        String id = "1";
        String mensajeEsperado = "Artista eliminado correctamente";

        when(artistaService.eliminarArtista(1L)).thenReturn(mensajeEsperado);

        ResponseEntity<MensajeDTO<String>> response = administradorController.eliminarArtista(id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().error());
        assertEquals(mensajeEsperado, response.getBody().respuesta());
    }

    @Test
    void editarArtistaTest() throws Exception {
        EditarArtistaDTO dto = new EditarArtistaDTO(
                1L,
                "Carlos Vives",
                "Vallenato",
                "carlos@email.com",
                "3101234567",
                "ACTIVO"
        );
        String mensajeEsperado = "Artista editado correctamente";
        when(artistaService.editarArtista(dto)).thenReturn(mensajeEsperado);

        ResponseEntity<MensajeDTO<String>> response = administradorController.editarArtista(dto);

        assertEquals(false, response.getBody().error());
        assertEquals(mensajeEsperado, response.getBody().respuesta());
    }

    @Test
    void crearArtista_deberiaRetornarMensajeExitoso() throws Exception {
        CrearArtistaDTO dto = new CrearArtistaDTO("Nombre", "Rock", "correo@mail.com", "123456789");
        String mensajeEsperado = "Artista creado correctamente";

        when(artistaService.crearArtista(dto)).thenReturn(mensajeEsperado);

        ResponseEntity<MensajeDTO<String>> response = administradorController.crearArtista(dto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().error());
        assertEquals(mensajeEsperado, response.getBody().respuesta());
    }

    @Test
    void crearCuponTest() throws Exception {
        CrearCuponDTO dto = new CrearCuponDTO(
                "CUPON2025",
                15.5,
                LocalDateTime.now().plusDays(10),
                30,
                TipoCupon.MULTIPLE
        );

        Long idEsperado = 1L;
        when(cuponService.crearCupon(dto)).thenReturn(idEsperado);

        ResponseEntity<MensajeDTO<String>> response = administradorController.crearCupon(dto);

        assertEquals(false, response.getBody().error());
        assertEquals(String.valueOf(idEsperado), response.getBody().respuesta());
    }

    @Test
    void editarCuponTest() throws Exception {
        EditarCuponDTO dto = new EditarCuponDTO(
                "1",
                "CUPONEDIT",
                10.0,
                LocalDateTime.now().plusDays(15),
                100,
                TipoCupon.UNICO
        );

        Long idEsperado = 1L;
        when(cuponService.editarCupon(dto)).thenReturn(idEsperado);

        ResponseEntity<MensajeDTO<String>> response = administradorController.editarCupon(dto);

        assertEquals(false, response.getBody().error());
        assertEquals(String.valueOf(idEsperado), response.getBody().respuesta());
    }

    @Test
    void eliminarCuponTest() throws Exception {
        Long cuponId = 5L;
        Long idEliminado = 5L;
        when(cuponService.eliminarCupon(cuponId)).thenReturn(idEliminado);

        ResponseEntity<MensajeDTO<String>> response = administradorController.eliminarCupon(cuponId);

        assertEquals(false, response.getBody().error());
        assertEquals(String.valueOf(idEliminado), response.getBody().respuesta());
    }

    @Test
    void obtenerInformacionCuponTest() throws Exception {
        Long cuponId = 1L;
        InformacionCuponDTO dto = new InformacionCuponDTO(
                "CUPON2025",
                15.5,
                LocalDateTime.now().plusDays(5),
                50,
                TipoCupon.MULTIPLE,
                EstadoCupon.ACTIVO
        );

        when(cuponService.obtenerInformacionCupon(cuponId)).thenReturn(dto);

        ResponseEntity<MensajeDTO<InformacionCuponDTO>> response = administradorController.obtenerInformacionCupon(cuponId);

        assertEquals(false, response.getBody().error());
        assertEquals(dto, response.getBody().respuesta());
    }

    @Test
    void listarCuponesTest() {
        ResumenCuponDTO resumen = new ResumenCuponDTO(
                "CUPONLISTA",
                10.0,
                LocalDateTime.now().plusDays(3),
                EstadoCupon.INACTIVO
        );

        when(cuponService.listarCupones()).thenReturn(List.of(resumen));

        ResponseEntity<MensajeDTO<List<ResumenCuponDTO>>> response = administradorController.listarCupones();

        assertEquals(false, response.getBody().error());
        assertEquals(1, response.getBody().respuesta().size());
        assertEquals("CUPONLISTA", response.getBody().respuesta().get(0).codigo());
    }

    @Test
    void buscarCuponesPorCodigoTest() {
        String codigo = "DESCUENTO10";
        ResumenCuponDTO resumen = new ResumenCuponDTO(
                codigo,
                10.0,
                LocalDateTime.now().plusDays(7),
                EstadoCupon.ACTIVO
        );

        when(cuponService.buscarCuponesPorCodigo(codigo)).thenReturn(List.of(resumen));

        ResponseEntity<MensajeDTO<List<ResumenCuponDTO>>> response = administradorController.buscarCuponesPorCodigo(codigo);

        assertEquals(false, response.getBody().error());
        assertEquals(1, response.getBody().respuesta().size());
        assertEquals(codigo, response.getBody().respuesta().get(0).codigo());
    }


    @Test
    void crearEventoTest() throws Exception {
        CrearEventoDTO dto = new CrearEventoDTO(
                "Concierto Rock",
                "1",
                "Gran concierto de rock",
                LocalDateTime.now(),
                "Calle 123",
                "Bogotá",
                TipoEvento.CONCIERTO,
                List.of()
        );
        String mensajeEsperado = "Evento creado correctamente";
        when(eventoService.crearEvento(dto)).thenReturn(mensajeEsperado);

        ResponseEntity<MensajeDTO<String>> response = administradorController.crearEvento(dto);

        assertEquals(false, response.getBody().error());
        assertEquals(mensajeEsperado, response.getBody().respuesta());
    }

    @Test
    void editarEventoTest() throws Exception {
        EditarEventoDTO editarEventoDTO = new EditarEventoDTO(
                "1", "Nuevo Nombre", new Artista(), "Nueva descripción",
                LocalDateTime.now().plusDays(1), "Calle 123", "Medellín",
                TipoEvento.CONCIERTO, List.of()
        );

        // Simulamos que el ID del evento editado es 1L
        when(eventoService.editarEvento(editarEventoDTO)).thenReturn(1L);

        ResponseEntity<MensajeDTO<String>> response = administradorController.editarEvento(editarEventoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().respuesta()); // Se espera el String "1" porque en el controller se hace String.valueOf
        assertFalse(response.getBody().error());
    }


    @Test
    void eliminarEventoTest() throws Exception {
        Long idEvento = 1L;

        when(eventoService.eliminarEvento(idEvento)).thenReturn(1L); // Retornamos un Long, no un String

        ResponseEntity<MensajeDTO<String>> response = administradorController.eliminarEvento(idEvento);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().respuesta()); // El controlador hace String.valueOf del Long
        assertFalse(response.getBody().error());
    }




    @Test
    void obtenerInformacionEventoTest() throws Exception {
        Long idEvento = 1L;
        InformacionEventoDTO info = new InformacionEventoDTO(
                idEvento, "Rock Fest", "Artista1", "Descripción", LocalDateTime.now(),
                "Dirección", "Cali", TipoEvento.CONCIERTO, EstadoEvento.ACTIVO,
                List.of(), "url_imagen"
        );
        when(eventoService.obtenerInformacionEvento(idEvento)).thenReturn(info);

        ResponseEntity<MensajeDTO<InformacionEventoDTO>> response = administradorController.obtenerInformacionEvento(idEvento);

        assertEquals(false, response.getBody().error());
        assertEquals(info, response.getBody().respuesta());
    }

    @Test
    void listarEventosTest() throws Exception {
        List<ResumenEventoDTO> eventos = List.of();
        when(eventoService.listarEventos()).thenReturn(eventos);

        ResponseEntity<MensajeDTO<List<ResumenEventoDTO>>> response = administradorController.listarEventos();

        assertEquals(false, response.getBody().error());
        assertEquals(eventos, response.getBody().respuesta());
    }

    @Test
    void filtrarEventosTest() throws Exception {
        FiltroEventoDTO filtro = new FiltroEventoDTO("Concierto", "Bogotá", TipoEvento.CONCIERTO);
        List<ItemEventoDTO> lista = List.of();
        when(eventoService.filtrarEventos(filtro)).thenReturn(lista);

        ResponseEntity<MensajeDTO<List<ItemEventoDTO>>> response = administradorController.filtrarEventos(filtro);

        assertEquals(false, response.getBody().error());
        assertEquals(lista, response.getBody().respuesta());
    }

    @Test
    void cambiarEstadoEventoTest() throws Exception {
        Long idEvento = 1L;
        EstadoEvento nuevoEstado = EstadoEvento.ACTIVO;

        doNothing().when(eventoService).cambiarEstadoEvento(idEvento, nuevoEstado);

        ResponseEntity<MensajeDTO<String>> response = administradorController.cambiarEstadoEvento(idEvento, nuevoEstado);

        assertEquals(false, response.getBody().error());
        assertEquals("Estado del evento cambiado exitosamente", response.getBody().respuesta());
    }

    @Test
    void buscarEventosPorNombreTest() {
        String nombre = "Festival";
        List<ResumenEventoDTO> eventos = List.of();
        when(eventoService.buscarEventosPorNombre(nombre)).thenReturn(eventos);

        ResponseEntity<MensajeDTO<List<ResumenEventoDTO>>> response = administradorController.buscarEventosPorNombre(nombre);

        assertEquals(false, response.getBody().error());
        assertEquals(eventos, response.getBody().respuesta());
    }

    @Test
    void agregarImagenEventoTest() throws Exception {
        Long idEvento = 1L;
        MultipartFile imagen = mock(MultipartFile.class);

        doNothing().when(eventoService).agregarImagenEvento(idEvento, imagen);

        ResponseEntity<MensajeDTO<String>> response = administradorController.agregarImagenEvento(idEvento, imagen);

        assertEquals(false, response.getBody().error());
        assertEquals("Imagen agregada exitosamente", response.getBody().respuesta());
    }



}