package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Valoracion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValoracionRepo extends MongoRepository<Valoracion,String> {
    @Query("{ 'usuario' : ?0 }")
    List<Valoracion> listarValoracionByUsuario(String usuario);

    void deleteByUsuario(String usuario);  //OJO CON ESTE MÉTODO
    void eliminarValoracionByUsuarioId (String usuarioId,String idValoracion);


}
