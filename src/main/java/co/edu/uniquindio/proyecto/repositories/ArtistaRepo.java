package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.Artista;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtistaRepo extends MongoRepository<Artista,String> {
}
