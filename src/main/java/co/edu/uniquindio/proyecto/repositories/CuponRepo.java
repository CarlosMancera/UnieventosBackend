package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.entities.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuponRepo extends JpaRepository<Cupon, Long> {

    Optional<Cupon> findByCodigo(String codigo);

    List<Cupon> findByCodigoContainingIgnoreCase(String codigo);

}
