package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.eventoDTO.*;
import co.edu.uniquindio.proyecto.model.enums.EstadoEvento;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventoService {

    String crearEvento(CrearEventoDTO crearEventoDTO) throws Exception;

    Long editarEvento(EditarEventoDTO editarEventoDTO) throws Exception;

    Long eliminarEvento(Long id) throws Exception;

    InformacionEventoDTO obtenerInformacionEvento(Long id) throws Exception;

    List<ResumenEventoDTO> listarEventos() throws Exception;

    List<ItemEventoDTO> filtrarEventos(FiltroEventoDTO filtroDTO) throws Exception;

    void cambiarEstadoEvento(Long id, EstadoEvento nuevoEstado) throws Exception;

    List<ResumenEventoDTO> buscarEventosPorNombre(String nombre);

    void agregarImagenEvento(Long idEvento, MultipartFile imagen) throws Exception;

    void agregarImagenLocalidad(Long idEvento, String nombreLocalidad, MultipartFile imagen) throws Exception;

    byte[] generarReporteVentasHTML(String idEvento) throws Exception;

    byte[] generarReporteVentasPDF(Long idEvento) throws Exception;

    byte[] generarReporteVentasXML(Long idEvento) throws Exception;





    //TODO

}
