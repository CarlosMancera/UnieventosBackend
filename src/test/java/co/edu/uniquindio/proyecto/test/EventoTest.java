package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.docs.Evento;
import co.edu.uniquindio.proyecto.model.enums.EstadoEvento;
import co.edu.uniquindio.proyecto.model.enums.TipoEvento;
import co.edu.uniquindio.proyecto.model.vo.Localidad;
import co.edu.uniquindio.proyecto.repositories.EventoRepo;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EventoTest {

    @Autowired
    private EventoRepo eventoRepo;

    @Test
    public void registrarEventoTest(){

        String imagen = "imagen1.com";
        List<Localidad> localidades = new ArrayList<>();
        localidades.add(new Localidad("Platea",10000,500,300,true,"De las mejorcitas","imagenLocal1.com"));
        localidades.add(new Localidad("Palco",5000,600,400,true,"De las mejorcitas","imagenLocal2.com"));
        ObjectId artista = new ObjectId("670859e75d9e140c90a3aae0");


        Evento evento = Evento.builder()
                .nombre("Rock in Torta")
                .direccion("Cra 15, Uniquindio")
                .ciudad("Armenia")
                .descripcion("Un evento de rock dentro de la universidad del Quindío")
                .tipoEvento(TipoEvento.CONCIERTO)
                .imagen(imagen)
                .fecha(LocalDateTime.now().plusMonths(12))
                .localidades(localidades)
                .estadoEvento(EstadoEvento.ACTIVO)
                .artista(artista)
                .build();

        Evento eventoRegistro = eventoRepo.save(evento);

        assertNotNull(eventoRegistro);



    }

}

