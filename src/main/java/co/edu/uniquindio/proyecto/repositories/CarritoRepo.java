package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Carrito;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepo extends MongoRepository<Carrito, String> {

    Optional<Carrito> findByCuenta ();
}
