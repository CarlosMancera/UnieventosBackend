package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepo extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByEmail(String email);

    Optional<Cuenta> findByUsuario_Cedula(String cedula);

    Optional<Cuenta> findByEmailAndPassword(String email, String password);



}
