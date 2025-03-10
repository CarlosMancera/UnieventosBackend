package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.entities.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistaRepo extends JpaRepository<Artista, Long> {

    Optional<Artista> findByNombre(String nombre);

    List<Artista> findByNombreContainingIgnoreCase(String nombre);

}
