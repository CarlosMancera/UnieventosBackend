package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import co.edu.uniquindio.proyecto.model.entities.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValoracionRepo extends JpaRepository<Valoracion, Long> {

    List<Valoracion> findByUsuario(Cuenta usuario);

    void deleteByUsuario(Cuenta usuario);

    void deleteByUsuarioAndId(Cuenta usuario, Long id);

}
