package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import co.edu.uniquindio.proyecto.model.entities.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepo extends JpaRepository<OrdenCompra, Long> {

    int countByCliente(Cuenta cliente);

}
