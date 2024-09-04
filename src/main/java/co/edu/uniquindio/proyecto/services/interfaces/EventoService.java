package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.*;

import java.util.List;

public interface EventoService {

    String crearEvento(CrearEventoDTO crearEventoDTO) throws Exception;

    String editarEvento(EditarEventoDTO editarEventoDTO) throws Exception;

    String eliminarEvento(String id) throws Exception;

    InformacionEventoDTO obtenerInformacionEvento (String id) throws Exception;

    List<ItemEventoDTO> listarEventos() throws Exception;

    List<ItemEventoDTO> filtrarEventos(FiltroEventoDTO filtroDTO) throws Exception;

    //TODO

}
