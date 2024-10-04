package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Cupon;
import co.edu.uniquindio.proyecto.model.docs.OrdenCompra;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenRepo extends MongoRepository<OrdenCompra,String> {
    @Query("{ 'usuariosUsados' : ?0 }")
    List<Cupon> findByUsuariosUsadosContaining(String idUsuario);
    @Query(value = "{ 'cuenta' : ?0 }", count = true)
    int countByCuenta (String idUsuario);

    Optional<OrdenCompra> findById (ObjectId id);
}
