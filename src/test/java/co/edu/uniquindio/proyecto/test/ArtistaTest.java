package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.docs.Artista;
import co.edu.uniquindio.proyecto.model.vo.Contacto;
import co.edu.uniquindio.proyecto.repositories.ArtistaRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ArtistaTest {

    @Autowired
    private ArtistaRepo artistaRepo;

    @Test
    public void registrarTest(){

        List<String> referencias = new ArrayList<>();
        referencias.add("Rock al parque 2016");
        referencias.add("Naturaleza por el rock 2017");



        Artista artista = Artista.builder()
                .nombre("CURA METAL")
                .genero("Metal")
                .email("cura@gmail.com")
                .telefono("7327003")
                .build();

        Artista registro = artistaRepo.save(artista);

        assertNotNull(registro);
    }


}
