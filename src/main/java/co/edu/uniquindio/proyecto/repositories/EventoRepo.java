package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.entities.Evento;
import co.edu.uniquindio.proyecto.model.enums.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepo extends JpaRepository<Evento, Long> {

    List<Evento> findByNombreContainingIgnoreCase(String nombre);

    Optional<Evento> findById(Long id);

    List<Evento> findByNombreAndCiudadAndTipoEvento(String nombre, String ciudad, TipoEvento tipoEvento);

}
