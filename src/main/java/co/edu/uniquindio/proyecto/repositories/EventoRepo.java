package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventoRepo extends MongoRepository<Evento,String> {
}
