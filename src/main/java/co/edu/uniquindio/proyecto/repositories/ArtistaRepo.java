package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.dto.artistasDTO.InformacionArtistaDTO;
import co.edu.uniquindio.proyecto.model.docs.Artista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistaRepo extends MongoRepository<Artista,String> {

    Optional<Artista> findByNombre(String nombre);
    List<Artista> findByNombreContainingIgnoreCase(String nombre);

}
