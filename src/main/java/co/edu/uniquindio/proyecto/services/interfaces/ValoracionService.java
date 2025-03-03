package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.model.docs.Valoracion;

import java.util.List;

public interface ValoracionService {
    List<Valoracion> listarValoracionByNombre(String nombre);

    void crearValoracion(Valoracion valoracion);

    List<Valoracion> obtenerValoracionByUsuarioID(String usuarioId);

    void eliminarValoracionByUsuarioId(String usuarioId, Valoracion valoracion);
}
