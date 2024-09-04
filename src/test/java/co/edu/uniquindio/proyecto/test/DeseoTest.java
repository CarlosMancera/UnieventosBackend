package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.docs.Deseo;
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
                .cuenta(new ObjectId("66d651c3054efe107386b4a8"))
                .evento(new ObjectId("66d655cb6cab961e099b1fc4"))
                .recibeInfo(true)
                .build();

        Deseo deseoRegistro = deseoRepo.save(deseo);

        assertNotNull(deseoRegistro);

    }


}
