package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.docs.Valoracion;
import co.edu.uniquindio.proyecto.repositories.ValoracionRepo;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ValoracionTest {

    @Autowired
    private ValoracionRepo valoracionRepo;

    @Test
    public void registrarTest(){

        Valoracion valoracion = Valoracion.builder()
                .evento(new ObjectId("67085b8a76fafb320689e149"))
                .usuario(new ObjectId("670854d36e334608369450c8"))
                .puntuacion(5)
                .comentario("Muy bueno, la cerveza y la banda")
                .fecha(LocalDateTime.now())
                .build();

        Valoracion valoracionRegistro = valoracionRepo.save(valoracion);

        assertNotNull(valoracionRegistro);

    }

}
