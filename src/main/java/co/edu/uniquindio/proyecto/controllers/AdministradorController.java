package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.CrearArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.EditarArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.InformacionArtistaDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.CrearCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.EditarCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.InformacionCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.ResumenCuponDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.*;
import co.edu.uniquindio.proyecto.model.enums.EstadoEvento;
import co.edu.uniquindio.proyecto.services.interfaces.ArtistaService;
import co.edu.uniquindio.proyecto.services.interfaces.CuponService;
import co.edu.uniquindio.proyecto.services.interfaces.EventoService;
import co.edu.uniquindio.proyecto.services.interfaces.ImagenesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/administrador")
public class AdministradorController {

    @Autowired
    private ArtistaService artistaService;
    @Autowired
    private CuponService cuponService;
    @Autowired
    private EventoService eventoService;
    @Autowired
    private ImagenesService imagenesService;

    @GetMapping("/listar-artista")
    public ResponseEntity<MensajeDTO<List<InformacionArtistaDTO>>> listarArtistas() throws Exception {
        List<InformacionArtistaDTO> lista = artistaService.listarArtistas();
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @GetMapping("/buscar-artista/{nombre}")
    public ResponseEntity<MensajeDTO<List<InformacionArtistaDTO>>> buscarArtistasPorNombre(@PathVariable String nombre) {
        List<InformacionArtistaDTO> lista = artistaService.buscarArtistasPorNombre(nombre);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @GetMapping("/obtener-artista/{id}")
    public ResponseEntity<MensajeDTO<InformacionArtistaDTO>> obtenerInformacionArtista(@PathVariable String id) throws Exception {
        InformacionArtistaDTO info = artistaService.obtenerInformacionArtista(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, info));
    }

    @DeleteMapping("/eliminar-artista/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarArtista(@PathVariable String id) throws Exception {
        String mensaje = artistaService.eliminarArtista(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @PutMapping("/editar-artista")
    public ResponseEntity<MensajeDTO<String>> editarArtista(@Valid @RequestBody EditarArtistaDTO artista) throws Exception {
        String mensaje = artistaService.editarArtista(artista);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @PostMapping("/crear-artista")
    public ResponseEntity<MensajeDTO<String>> crearArtista(@Valid @RequestBody CrearArtistaDTO artista) throws Exception {
        String mensaje = artistaService.crearArtista(artista);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @PostMapping("/crear-cupon")
    public ResponseEntity<MensajeDTO<String>> crearCupon(@Valid @RequestBody CrearCuponDTO cuponDTO) throws Exception {
        String mensaje = cuponService.crearCupon(cuponDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @PutMapping("/editar-cupon")
    public ResponseEntity<MensajeDTO<String>> editarCupon(@Valid @RequestBody EditarCuponDTO cuponDTO) throws Exception {
        String mensaje = cuponService.editarCupon(cuponDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @DeleteMapping("/eliminar-cupon/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarCupon(@PathVariable String id) throws Exception {
        String mensaje = cuponService.eliminarCupon(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @GetMapping("/obtener-cupon/{id}")
    public ResponseEntity<MensajeDTO<InformacionCuponDTO>> obtenerInformacionCupon(@PathVariable String id) throws Exception {
        InformacionCuponDTO info = cuponService.obtenerInformacionCupon(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, info));
    }

    @GetMapping("/listar-cupon")
    public ResponseEntity<MensajeDTO<List<ResumenCuponDTO>>> listarCupones() {
        List<ResumenCuponDTO> lista = cuponService.listarCupones();
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @GetMapping("/buscar-cupon/{codigo}")
    public ResponseEntity<MensajeDTO<List<ResumenCuponDTO>>> buscarCuponesPorCodigo(@PathVariable String codigo) {
        List<ResumenCuponDTO> lista = cuponService.buscarCuponesPorCodigo(codigo);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }


    @PostMapping("/crear-evento")
    public ResponseEntity<MensajeDTO<String>> crearEvento(@Valid @RequestBody CrearEventoDTO crearEventoDTO) throws Exception {
        String mensaje = eventoService.crearEvento(crearEventoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @PutMapping("/editar-evento")
    public ResponseEntity<MensajeDTO<String>> editarEvento(@Valid @RequestBody EditarEventoDTO editarEventoDTO) throws Exception {
        String mensaje = eventoService.editarEvento(editarEventoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @DeleteMapping("/eliminar-evento/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarEvento(@PathVariable String id) throws Exception {
        String mensaje = eventoService.eliminarEvento(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @GetMapping("/obtener-info-evento/{id}")
    public ResponseEntity<MensajeDTO<InformacionEventoDTO>> obtenerInformacionEvento(@PathVariable String id) throws Exception {
        InformacionEventoDTO info = eventoService.obtenerInformacionEvento(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, info));
    }

    @GetMapping("/listar")
    public ResponseEntity<MensajeDTO<List<ResumenEventoDTO>>> listarEventos() throws Exception {
        List<ResumenEventoDTO> lista = eventoService.listarEventos();
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @PostMapping("/filtrar")
    public ResponseEntity<MensajeDTO<List<ItemEventoDTO>>> filtrarEventos(@Valid @RequestBody FiltroEventoDTO filtroDTO) throws Exception {
        List<ItemEventoDTO> lista = eventoService.filtrarEventos(filtroDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @PutMapping("/cambiar-estado/{id}")
    public ResponseEntity<MensajeDTO<String>> cambiarEstadoEvento(@PathVariable String id, @RequestParam EstadoEvento nuevoEstado) throws Exception {
        eventoService.cambiarEstadoEvento(id, nuevoEstado);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Estado del evento cambiado exitosamente"));
    }

    @GetMapping("/buscar-evento/{nombre}")
    public ResponseEntity<MensajeDTO<List<ResumenEventoDTO>>> buscarEventosPorNombre(@PathVariable String nombre) {
        List<ResumenEventoDTO> lista = eventoService.buscarEventosPorNombre(nombre);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @PostMapping("/agregar-imagen/{idEvento}")
    public ResponseEntity<MensajeDTO<String>> agregarImagenEvento(@PathVariable String idEvento, @RequestParam("imagen") MultipartFile imagen) throws Exception {
        eventoService.agregarImagenEvento(idEvento, imagen);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Imagen agregada exitosamente"));
    }

    @PostMapping("/agregar-imagen-localidad/{idEvento}/{nombreLocalidad}")
    public ResponseEntity<MensajeDTO<String>> agregarImagenLocalidad(@PathVariable String idEvento, @PathVariable String nombreLocalidad, @RequestParam("imagen") MultipartFile imagen) throws Exception {
        eventoService.agregarImagenLocalidad(idEvento, nombreLocalidad, imagen);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Imagen de localidad agregada exitosamente"));
    }

    @GetMapping("/reporte-ventas-html/{idEvento}")
    public ResponseEntity<byte[]> generarReporteVentasHTML(@PathVariable String idEvento) throws Exception {
        byte[] reporte = eventoService.generarReporteVentasHTML(idEvento);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_ventas.html")
                .contentType(MediaType.TEXT_HTML)
                .body(reporte);
    }

    @GetMapping("/reporte-ventas-pdf/{idEvento}")
    public ResponseEntity<byte[]> generarReporteVentasPDF(@PathVariable String idEvento) throws Exception {
        byte[] reporte = eventoService.generarReporteVentasPDF(idEvento);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_ventas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(reporte);
    }

    @GetMapping("/reporte-ventas-xml/{idEvento}")
    public ResponseEntity<byte[]> generarReporteVentasXML(@PathVariable String idEvento) throws Exception {
        byte[] reporte = eventoService.generarReporteVentasXML(idEvento);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_ventas.xml")
                .contentType(MediaType.APPLICATION_XML)
                .body(reporte);
    }

    @PostMapping("/subir-imagen")
    public ResponseEntity<MensajeDTO<String>> subirImagen(@RequestParam("imagen") MultipartFile imagen) throws Exception {
        String nombreImagen = imagenesService.subirImagen(imagen);
        return ResponseEntity.ok(new MensajeDTO<>(false, nombreImagen));
    }

    @DeleteMapping("/eliminar-imagen/{nombreImagen}")
    public ResponseEntity<MensajeDTO<String>> eliminarImagen(@PathVariable String nombreImagen) throws Exception {
        imagenesService.eliminarImagen(nombreImagen);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Imagen eliminada exitosamente"));
    }
}
