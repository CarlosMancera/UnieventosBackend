package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.model.docs.Valoracion;
import co.edu.uniquindio.proyecto.repositories.ValoracionRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ValoracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ValoracionServiceImpl implements ValoracionService {
    private ValoracionRepo valoracionRepo;

    public ValoracionServiceImpl(ValoracionRepo valoracionRepo){
        this.valoracionRepo=valoracionRepo;
    }

    @Override
    public List<Valoracion> listarValoracionByNombre(String nombre) {
        List<Valoracion> listarValoraciones =valoracionRepo.listarValoracionByUsuario(nombre);
        return listarValoraciones;
    }

    @Override
    public void crearValoracion(Valoracion valoracion) {
        valoracionRepo.save(valoracion);
    }

    @Override
    public List<Valoracion> obtenerValoracionByUsuarioID(String usuarioId) {
        List<Valoracion> listaValoraciones =valoracionRepo.listarValoracionByUsuario(usuarioId);
        return listaValoraciones;
    }

    @Override
    public void eliminarValoracionByUsuarioId(String usuarioId, Valoracion valoracion) {
        valoracionRepo.eliminarValoracionByUsuarioId(usuarioId,valoracion.getId()); //Query en el repo
    }
}
