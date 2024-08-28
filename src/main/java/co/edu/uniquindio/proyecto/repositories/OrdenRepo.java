package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.OrdenCompra;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdenRepo extends MongoRepository<OrdenCompra,String> {
}
