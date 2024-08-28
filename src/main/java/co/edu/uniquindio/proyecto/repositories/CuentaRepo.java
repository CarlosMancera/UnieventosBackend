package co.edu.uniquindio.proyecto.repositories;
import co.edu.uniquindio.proyecto.model.docs.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CuentaRepo extends MongoRepository<Cuenta,String> {
}
