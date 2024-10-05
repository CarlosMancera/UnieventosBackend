package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Carrito;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepo extends MongoRepository<Carrito, String> {
    @Query("{ 'idUsuario' : ?0 }")
    Optional<Carrito> findByIdUsuario (ObjectId id);
    @Query("{ 'idCuenta' : ?0 }")
    Optional<Carrito> findByCuenta( ObjectId idCuenta);
}
