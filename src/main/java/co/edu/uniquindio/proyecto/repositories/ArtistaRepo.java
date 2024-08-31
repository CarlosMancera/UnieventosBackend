package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Artista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaRepo extends MongoRepository<Artista,String> {
}
