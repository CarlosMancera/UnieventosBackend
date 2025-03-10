package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.entities.Deseo;
import co.edu.uniquindio.proyecto.repositories.DeseoRepo;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DeseoTest {

    @Autowired
    private DeseoRepo deseoRepo;

    @Test
    public void registrarTest(){

        Deseo deseo = Deseo.builder()
                .cuenta(new ObjectId("670854d36e334608369450c8"))
                .evento(new ObjectId("67085b8a76fafb320689e149"))
                .recibeInfo(true)
                .build();

        Deseo deseoRegistro = deseoRepo.save(deseo);

        assertNotNull(deseoRegistro);

    }


}
