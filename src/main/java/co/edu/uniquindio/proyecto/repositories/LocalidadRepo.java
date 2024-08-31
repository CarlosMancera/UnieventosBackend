package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.vo.Localidad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalidadRepo extends MongoRepository<Localidad,String> {
}
