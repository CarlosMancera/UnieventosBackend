package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.entities.Carrito;
import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepo extends JpaRepository<Carrito, Long> {


    Optional<Carrito> findByCuenta(Cuenta cuenta);
}
