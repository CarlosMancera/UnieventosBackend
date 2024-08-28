package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Cupon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CuponRepo extends MongoRepository<Cupon,String> {
}
