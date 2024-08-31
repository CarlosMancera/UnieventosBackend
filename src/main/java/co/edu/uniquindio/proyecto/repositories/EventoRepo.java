package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepo extends MongoRepository<Evento,String> {
}
