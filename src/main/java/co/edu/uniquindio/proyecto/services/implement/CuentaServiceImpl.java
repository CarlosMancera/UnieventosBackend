package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.docs.Cuenta;
import co.edu.uniquindio.proyecto.model.enums.EstadoCuenta;
import co.edu.uniquindio.proyecto.model.enums.TipoUsuario;
import co.edu.uniquindio.proyecto.model.vo.CodigoValidacion;
import co.edu.uniquindio.proyecto.model.vo.Usuario;
import co.edu.uniquindio.proyecto.repositories.CuentaRepo;
import co.edu.uniquindio.proyecto.services.interfaces.CuentaService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor //Esta anotación sustituye al constructor con la inicialización del atributo cuentaRepo (que es una interface)
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepo cuentaRepo;


    @Override
    public String crearCuenta(CrearCuentaDTO cuenta) throws Exception {

        if(existeEmail(cuenta.correo())){
            throw new Exception("Ya existe una cuenta con el correo " + cuenta.correo());
        }

        if(existeCedula(cuenta.cedula())){

            throw new Exception("La cedula ya se encuentra registrada");

        }

        //Invocación al método de generación

        Cuenta nuevaCuenta = new Cuenta();
        nuevaCuenta.setEmail(cuenta.correo());
        nuevaCuenta.setPassword(cuenta.password());
        nuevaCuenta.setTipoUsuario(TipoUsuario.CLIENTE);
        nuevaCuenta.setFechaRegistro(LocalDateTime.now());
        nuevaCuenta.setUsuario(new Usuario(cuenta.cedula(), cuenta.nombre(),
                cuenta.direccion(),cuenta.telefono()));
        nuevaCuenta.setEstadoCuenta(EstadoCuenta.INACTIVO);
        nuevaCuenta.setCodigoValidacionRegistro(new CodigoValidacion(
                "HA678B",                                                     // Crear método para generar ese código (maybe REGEX)
                LocalDateTime.now()                                           //Antes de la toma de datos del DTO
        ));

        Cuenta cuentaCreada = cuentaRepo.save(nuevaCuenta);

        //TODO Enviar un email al usuario con el código de validación

        return cuentaCreada.getId().toString();   //Por ahora digamos que está bien retornar retornar el ID
    }

    private boolean existeCedula(@NotBlank @Length(max = 10) String cedula) {

        return cuentaRepo.findByCedula(cedula).isPresent();

    }

    private boolean existeEmail(@NotBlank @Length(max = 40) @Email String correo) {

        return cuentaRepo.findByEmail(correo).isPresent();

    }

    @Override
    public String editarCuenta(EditarCuentaDTO cuenta) throws Exception {

        //Buscamos la cuenta del usuario que se quiere actualizar
        Optional<Cuenta> optionalCuenta = cuentaRepo.findById(cuenta.id());


        //Si no se encontró la cuenta del usuario, lanzamos una excepción
        if(optionalCuenta.isEmpty()){
            throw new Exception("No existe un usuario con el id dado");
        }


        //Obtenemos la cuenta del usuario a modificar y actualizamos sus datos
        Cuenta cuentaModificada = optionalCuenta.get();
        cuentaModificada.getUsuario().setNombreCompleto( cuenta.nombre() );
        cuentaModificada.getUsuario().setTelefono( cuenta.telefono() );
        cuentaModificada.getUsuario().setDireccion( cuenta.direccion() );
        cuentaModificada.setPassword( cuenta.password() );


        //Como el objeto cuenta ya tiene un id, el save() no crea un nuevo registro sino que actualiza el que ya existe
        cuentaRepo.save(cuentaModificada);


        return cuentaModificada.getId();
    }

    @Override
    public String eliminarCuenta(String id) throws Exception {

        //Buscamos la cuenta del usuario que se quiere eliminar
        Optional<Cuenta> optionalCuenta = cuentaRepo.findById(id);


        //Si no se encontró la cuenta, lanzamos una excepción
        if(optionalCuenta.isEmpty()){
            throw new Exception("No se encontró el usuario con el id "+id);
        }


        //Obtenemos la cuenta del usuario que se quiere eliminar y le asignamos el estado eliminado
        Cuenta cuenta = optionalCuenta.get();
        cuenta.setEstadoCuenta(EstadoCuenta.ELIMINADO);


        //Como el objeto cuenta ya tiene un id, el save() no crea un nuevo registro sino que actualiza el que ya existe
        cuentaRepo.save(cuenta);


        return cuenta.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception {

        //Buscamos la cuenta del usuario que se quiere obtener
        Optional<Cuenta> optionalCuenta = cuentaRepo.findById(id);


        //Si no se encontró la cuenta, lanzamos una excepción
        if(optionalCuenta.isEmpty()){
            throw new Exception("No se encontró el usuario con el id "+id);
        }


        //Obtenemos la cuenta del usuario
        Cuenta cuenta = optionalCuenta.get();


        //Retornamos la información de la cuenta del usuario
        return new InformacionCuentaDTO(
                cuenta.getId(),
                cuenta.getUsuario().getCedula(),
                cuenta.getUsuario().getNombreCompleto(),
                cuenta.getUsuario().getTelefono(),
                cuenta.getUsuario().getDireccion(),
                cuenta.getEmail()
        );

    }

    @Override
    public String enviarCodigoRecuperacionPassword(String correo) throws Exception {
        return "";
    }

    @Override
    public String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception {
        return "";
    }

    @Override
    public String iniciarSesion(LoginDTO loginDTO) throws Exception {
        return "";
    }
}
