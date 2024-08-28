package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepo extends MongoRepository<Usuario,String> {
}
