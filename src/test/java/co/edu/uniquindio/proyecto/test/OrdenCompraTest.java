package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.docs.OrdenCompra;
import co.edu.uniquindio.proyecto.model.vo.DetalleOrden;
import co.edu.uniquindio.proyecto.model.vo.Pago;
import co.edu.uniquindio.proyecto.repositories.OrdenRepo;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrdenCompraTest {

    @Autowired
    private OrdenRepo ordenRepo;

    @Test
    public void registrarTest(){



        List<DetalleOrden> items = new ArrayList<>();
        items.add(new DetalleOrden("Platea", 5,new ObjectId("66d655cb6cab961e099b1fc4"),10000));

        OrdenCompra ordenCompra = OrdenCompra.builder()
                .codigo("666")
                .cliente(new ObjectId("66d651c3054efe107386b4a8"))
                .items(items)
                .cupon(null)
                .total(50000)
                .fecha(LocalDateTime.now())
                .pago(new Pago("666","efectivo","pagado",50000,LocalDateTime.now()))
                .build();

        OrdenCompra ordenRegistro = ordenRepo.save(ordenCompra);

        assertNotNull(ordenRegistro);

    }

}
