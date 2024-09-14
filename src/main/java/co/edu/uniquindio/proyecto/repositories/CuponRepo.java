package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Cupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuponRepo extends MongoRepository<Cupon,String> {
    Optional<Cupon> findByCodigo(String codigo);
    List<Cupon> findByCodigoContainingIgnoreCase(String codigo);
    // List<Cupon> findByUsuariosUsadosContaining(String idUsuario);
    boolean estaVigente(String idCupon, String idUsuario) throws Exception;
}
