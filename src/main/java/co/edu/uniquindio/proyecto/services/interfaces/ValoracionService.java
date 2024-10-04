package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.model.docs.Valoracion;

import java.util.List;

public interface ValoracionService {
    List<Valoracion> listarValoracionByNombre(String nombre) throws Exception;

    void crearValoracion(Valoracion valoracion) throws Exception;

    List<Valoracion> obtenerValoracionByUsuarioID(String usuarioId) throws Exception;

    void eliminarValoracionByUsuarioId(String usuarioId, Valoracion valoracion) throws Exception;
}
