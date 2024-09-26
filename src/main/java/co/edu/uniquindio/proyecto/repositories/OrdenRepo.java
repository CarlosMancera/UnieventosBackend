package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.OrdenCompra;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepo extends MongoRepository<OrdenCompra,String> {

    //   List<Cupon> findByUsuariosUsadosContaining(String idUsuario);

    int countByCuenta (String idUsuario);
}
