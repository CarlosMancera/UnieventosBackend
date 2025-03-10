package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.entities.Artista;
import co.edu.uniquindio.proyecto.model.enums.EstadoArtista;
import co.edu.uniquindio.proyecto.repositories.ArtistaRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ArtistaTest {

    @Autowired
    private ArtistaRepo artistaRepo;

    @Test
    public void registrarTest(){

        Artista artista = Artista.builder()
                .nombre("CURA METAL")
                .genero("Metal")
                .email("cura@gmail.com")
                .telefono("7327003")
                .estado(EstadoArtista.DISPONIBLE)
                .build();

        Artista registro = artistaRepo.save(artista);

        assertNotNull(registro);
    }


}
