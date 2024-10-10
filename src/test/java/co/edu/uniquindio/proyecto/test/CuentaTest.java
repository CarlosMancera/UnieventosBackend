package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.docs.Cuenta;
import co.edu.uniquindio.proyecto.model.enums.EstadoCuenta;
import co.edu.uniquindio.proyecto.model.enums.TipoUsuario;
import co.edu.uniquindio.proyecto.model.vo.CodigoValidacion;
import co.edu.uniquindio.proyecto.repositories.CuentaRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CuentaTest {

    @Autowired
    private CuentaRepo cuentaRepo;

    @Test
    public void crearCuentaTest(){

        Cuenta cuenta = Cuenta.builder()
                .email("usuario@gmail.com")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .estadoCuenta(EstadoCuenta.ACTIVO)
                .codigoValidacionPassword(new CodigoValidacion("666", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15)))
                .password("666")
                .codigoValidacionRegistro(new CodigoValidacion("666",LocalDateTime.now(), LocalDateTime.now().plusMinutes(15)))
                .build();

        List<LocalDateTime> historialConexion = new ArrayList<>();
        historialConexion.add(LocalDateTime.now());

        cuenta.setHistorialConexion(historialConexion);

        Cuenta cuentaRegistro = cuentaRepo.save(cuenta);

        assertNotNull(cuentaRegistro);

    }

}
