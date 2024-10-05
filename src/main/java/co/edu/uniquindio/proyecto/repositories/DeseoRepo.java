package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Deseo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeseoRepo extends MongoRepository<Deseo, String> {
    @Query("{ 'cuenta' : ?0 }")
    List<Deseo> findByCuenta(ObjectId cuenta);
    @Query("{ 'cuenta' : ?0, 'evento' : ?1 }")
    Optional<Deseo> findByCuentaAndEvento(ObjectId cuenta, ObjectId evento);
    boolean existsByCuentaAndEvento(ObjectId cuenta, ObjectId evento);
    void deleteByCuentaAndEvento(ObjectId cuenta, ObjectId evento);
}
