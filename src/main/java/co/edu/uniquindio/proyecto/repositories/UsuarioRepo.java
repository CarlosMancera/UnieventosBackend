package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.vo.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepo extends MongoRepository<Usuario,String> {
}
