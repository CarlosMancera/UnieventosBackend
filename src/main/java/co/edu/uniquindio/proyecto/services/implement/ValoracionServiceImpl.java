package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import co.edu.uniquindio.proyecto.model.entities.Valoracion;
import co.edu.uniquindio.proyecto.repositories.ValoracionRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ValoracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Cuenta cuenta=new Cuenta();
        List<Valoracion> listarValoraciones =valoracionRepo.findByUsuario(cuenta);
        return listarValoraciones;
    }

    @Override
    public void crearValoracion(Valoracion valoracion) {
        valoracionRepo.save(valoracion);
    }

    @Override
    public List<Valoracion> obtenerValoracionByUsuarioID(Long usuarioId) {
        Cuenta cuenta=new Cuenta();
        List<Valoracion> listaValoraciones =valoracionRepo.findByUsuario(cuenta);
        return listaValoraciones;
    }

    @Override
    public void eliminarValoracionByUsuarioId(Long usuarioId, Valoracion valoracion) {
        Cuenta cuenta=new Cuenta();
        valoracionRepo.deleteByUsuarioAndId(cuenta,valoracion.getId());
    }
}
