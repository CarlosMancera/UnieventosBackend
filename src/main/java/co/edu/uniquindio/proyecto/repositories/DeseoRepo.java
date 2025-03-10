package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import co.edu.uniquindio.proyecto.model.entities.Deseo;
import co.edu.uniquindio.proyecto.model.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeseoRepo extends JpaRepository<Deseo, Long> {

    List<Deseo> findByCuenta(Cuenta cuenta);

    Optional<Deseo> findByCuentaAndEvento(Cuenta cuenta, Evento evento);

    boolean existsByCuentaAndEvento(Cuenta cuenta, Evento evento);

    void deleteByCuentaAndEvento(Cuenta cuenta, Evento evento);
}
