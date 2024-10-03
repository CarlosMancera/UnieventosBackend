package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Evento;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface EventoRepo extends MongoRepository<Evento,String> {

    List<Evento> findByNombreContainingIgnoreCase(String nombre);
    Optional<Evento> findById(ObjectId id);

}
