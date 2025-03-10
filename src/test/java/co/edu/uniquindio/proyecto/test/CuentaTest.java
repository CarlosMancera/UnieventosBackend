package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import co.edu.uniquindio.proyecto.model.enums.EstadoCuenta;
import co.edu.uniquindio.proyecto.model.enums.TipoUsuario;
import co.edu.uniquindio.proyecto.model.vo.CodigoValidacion;
import co.edu.uniquindio.proyecto.model.vo.Usuario;
import co.edu.uniquindio.proyecto.repositories.CuentaRepo;
import co.edu.uniquindio.proyecto.services.implement.CuentaServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CuentaTest {

    @Autowired
    private CuentaRepo cuentaRepo;
    @Autowired
    private CuentaServiceImpl cuentaService;

    @Test
    public void crearCuentaTest(){

       /* try{
            cuentaService.crearCuenta(new CrearCuentaDTO("999555333","Julio Jaramillo",
                    "7327003","Cra 14", "cliente1@gmail.com", "cliente1"));
        }catch (Exception e){
            e.printStackTrace();
        }


        */

//        try{
//            cuentaRepo.save(new Cuenta("admin@mail.com",TipoUsuario.ADMINISTRADOR,EstadoCuenta.ACTIVO,
//                    null,new Usuario("1094666","Administrador Alzate","Uniquindio",
//                    "3207552632"),LocalDateTime.now(),cuentaService.encriptarPassword("adminadmin"),null));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//



        //CUENTA NORMAL

       /* Cuenta cuenta = Cuenta.builder()
                .email("usuario@gmail.com")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .estadoCuenta(EstadoCuenta.ACTIVO)
                .codigoValidacionPassword(new CodigoValidacion("666", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15)))
                .password(cuentaService.encriptarPassword("useruser"))
                .codigoValidacionRegistro(new CodigoValidacion("666",LocalDateTime.now(), LocalDateTime.now().plusMinutes(15)))
                .usuario(new Usuario("1094955", "Usuario Default", "Cra 14 debajo del puente", "3201554896"))
                .build();


        Cuenta cuentaRegistro = cuentaRepo.save(cuenta);

        assertNotNull(cuentaRegistro);*/

        //CUENTA - ADMINISTRADOR

        Cuenta cuenta2 = Cuenta.builder()
                .email("admin9@gmail.com")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .estadoCuenta(EstadoCuenta.ACTIVO)
                .codigoValidacionPassword(new CodigoValidacion("666", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15)))
                .password(cuentaService.encriptarPassword("adminadmin"))
                .codigoValidacionRegistro(new CodigoValidacion("666",LocalDateTime.now(), LocalDateTime.now().plusMinutes(15)))
                .usuario(new Usuario("1094666", "Admin Default", "Cra 14 encima del puente", "320555666"))
                .build();


        Cuenta cuentaRegistro2 = cuentaRepo.save(cuenta2);

        assertNotNull(cuentaRegistro2);

    }

}
