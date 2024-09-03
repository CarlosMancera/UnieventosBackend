package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.*;

import java.util.List;

public interface EventoService {

    String crearEvento(CrearEventoDTO crearEventoDTO);

    String editarEvento(EditarEventoDTO editarEventoDTO);

    String eliminarEvento();

    InformacionEventoDTO obtenerInformacionEvento (String id);

    List<ItemEventoDTO> filtrarEventos(FiltroDTO filtroDTO);

    List<ItemEventoDTO> obtenerEventos();

    //TODO

}
