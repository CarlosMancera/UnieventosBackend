package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Valoracion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValoracionRepo extends MongoRepository<Valoracion,String> {
}
