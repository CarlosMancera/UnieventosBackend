package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.docs.Cupon;
import co.edu.uniquindio.proyecto.model.enums.EstadoCupon;
import co.edu.uniquindio.proyecto.model.enums.TipoCupon;
import co.edu.uniquindio.proyecto.repositories.CuponRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CuponTest {

    @Autowired
    private CuponRepo cuponRepo;

    @Test
    public void registrarTest(){

        Cupon cupon = Cupon.builder()
                .codigo("This is halloween")
                .descuento(0.3)
                .fechaVencimiento(LocalDateTime.of(2024,10,31,23,59))
                .tipoCupon(TipoCupon.MULTIPLE)
                .estado(EstadoCupon.ACTIVO)
                .esEspecial(true)
                .limiteUso(50)
                .cantidadUsados(25)
                .build();

        Cupon cuponRegistro = cuponRepo.save(cupon);

        assertNotNull(cuponRegistro);

    }
}
