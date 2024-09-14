package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.docs.Artista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistaRepo extends MongoRepository<Artista,String> {

    List<Artista> findByNombreContainingIgnoreCase(String nombre);

    Optional<Artista> findByNombre(String nombre);


    List<Artista> findByGenerosContainingIgnoreCase(String genero);

    List<Artista> findByTipo(TipoArtista tipo);

    List<Artista> findByEstado(EstadoArtista estado);

    boolean existsByNombre(String nombre);

    List<Artista> findByTarifaBetween(double minTarifa, double maxTarifa);
}
