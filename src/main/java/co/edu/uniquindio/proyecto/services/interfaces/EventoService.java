package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.FiltroDTO;
import co.edu.uniquindio.proyecto.dto.InformacionEventoDTO;
import co.edu.uniquindio.proyecto.dto.ItemEventoDTO;

import java.util.List;

public interface EventoService {

    String crearEvento();

    String editarEvento();

    String eliminarEvento();

    InformacionEventoDTO obtenerInformacionEvento (String id);

    List<ItemEventoDTO> filtrarEventos(FiltroDTO filtroDTO);

    List<ItemEventoDTO> obtenerEventos();

    //TODO

}
