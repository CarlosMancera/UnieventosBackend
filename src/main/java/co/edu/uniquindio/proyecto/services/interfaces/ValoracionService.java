package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.model.entities.Valoracion;

import java.util.List;

public interface ValoracionService {
    List<Valoracion> listarValoracionByNombre(String nombre);

    void crearValoracion(Valoracion valoracion);

    List<Valoracion> obtenerValoracionByUsuarioID(Long usuarioId);

    void eliminarValoracionByUsuarioId(Long usuarioId, Valoracion valoracion);
}
