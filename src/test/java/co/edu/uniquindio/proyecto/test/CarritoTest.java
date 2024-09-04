package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.docs.Carrito;
import co.edu.uniquindio.proyecto.model.vo.DetalleCarrito;
import co.edu.uniquindio.proyecto.repositories.CarritoRepo;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CarritoTest {

    @Autowired
    private CarritoRepo carritoRepo;

    @Test
    public void registrarTest(){

        List<DetalleCarrito> items = new ArrayList<>();
        items.add(new DetalleCarrito("Platea", 5, new ObjectId("66d655cb6cab961e099b1fc4")));

        Carrito carrito = Carrito.builder()
                .fecha(LocalDateTime.now())
                .items(items)
                .idUsuario(new ObjectId("66d651c3054efe107386b4a8"))
                .build();

        Carrito carritoRegistro = carritoRepo.save(carrito);

        assertNotNull(carritoRegistro);

    }


}
