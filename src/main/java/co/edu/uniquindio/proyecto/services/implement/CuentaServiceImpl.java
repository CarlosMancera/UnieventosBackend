package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.config.JWTUtils;
import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.CrearCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.EditarCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.InformacionCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.ItemCuentaDTO;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor                                    //Esta anotación sustituye al constructor
public class CuentaServiceImpl implements CuentaService {   //con la inicialización del atributo cuentaRepo (que es una interface)

    private final CuentaRepo cuentaRepo;
    private final JWTUtils jwtUtils;


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
                generarCodigoRecuperacion(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15L)
        ));

        Cuenta cuentaCreada = cuentaRepo.save(nuevaCuenta);

        //TODO Enviar un email al usuario con el código de validación

        return cuentaCreada.getId().toString();                         //Por ahora digamos que está bien
    }                                                                   //retornar retornar el ID



    @Override
    public String editarCuenta(EditarCuentaDTO cuenta) throws Exception {

                                                                            //Buscamos la cuenta del
        Optional<Cuenta> optionalCuenta = getCuenta(cuenta.id());           //usuario que se quiere actualizar

                                                                            //Obtenemos la cuenta del
        Cuenta cuentaModificada = optionalCuenta.get();                     //usuario a modificar y actualizamos sus datos
        cuentaModificada.getUsuario().setNombreCompleto( cuenta.nombre() );
        cuentaModificada.getUsuario().setTelefono( cuenta.telefono() );
        cuentaModificada.getUsuario().setDireccion( cuenta.direccion() );
        cuentaModificada.setPassword( cuenta.password() );

                                                                        //Como el objeto cuenta ya tiene
        cuentaRepo.save(cuentaModificada);                              //un id, el save() no crea un nuevo
                                                                        //registro sino que actualiza el que ya existe

        return cuentaModificada.getId();
    }

    @Override
    public String eliminarCuenta(String id) throws Exception {

                                                            //Buscamos la cuenta del usuario
        Optional<Cuenta> optionalCuenta = getCuenta(id);    //que se quiere eliminar

                                                            //Obtenemos la cuenta del usuario que
        Cuenta cuenta = optionalCuenta.get();               //se quiere eliminar y le asignamos el estado eliminado
        cuenta.setEstadoCuenta(EstadoCuenta.ELIMINADO);

                                                            //Como el objeto cuenta ya tiene un id,
        cuentaRepo.save(cuenta);                            //el save() no crea un nuevo registro sino que actualiza el que ya existe


        return cuenta.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception {


        Optional<Cuenta> optionalCuenta = getCuenta(id);            //Buscamos la cuenta del usuario que se quiere obtener


        Cuenta cuenta = optionalCuenta.get();                   //Obtenemos la cuenta del usuario


        return new InformacionCuentaDTO(                        //Retornamos la información de la cuenta del usuario
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

        Optional<Cuenta> optionalCuenta = cuentaRepo.findByEmail(correo);

        if (optionalCuenta.isEmpty()) {
            throw new Exception("No existe una cuenta con el correo " + correo);
        }

        Cuenta cuenta = optionalCuenta.get();

        // Generar un código de recuperación único
        String codigoRecuperacion = generarCodigoRecuperacion(); // Implementar este método

        // Actualizar la cuenta con el nuevo código de recuperación
        cuenta.setCodigoValidacionPassword(new CodigoValidacion(
                codigoRecuperacion,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15L) // El código expira en 1 hora

        ));

        cuentaRepo.save(cuenta);

        // TODO: Enviar un email al usuario con el código de recuperación

        return "Se ha enviado un código de recuperación a su correo electrónico";
    }

    @Override
    public String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception {
        Optional<Cuenta> optionalCuenta = cuentaRepo.findByEmail(cambiarPasswordDTO.correo());

        if (optionalCuenta.isEmpty()) {
            throw new Exception("No existe una cuenta con el correo " + cambiarPasswordDTO.correo());
        }

        Cuenta cuenta = optionalCuenta.get();

        // Verificar que el código de recuperación sea válido y no haya expirado
        if (cuenta.getCodigoValidacionPassword() == null ||
                !cuenta.getCodigoValidacionPassword().getCodigo().equals(cambiarPasswordDTO.codigoVerificacion()) ||
                cuenta.getCodigoValidacionPassword().getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new Exception("El código de recuperación es inválido o ha expirado");
        }

        // Cambiar la contraseña
        cuenta.setPassword(encriptarPassword(cambiarPasswordDTO.passwordNuevaT()));

        // Eliminar el código de recuperación
        cuenta.setCodigoValidacionPassword(null);

        cuentaRepo.save(cuenta);

        return "La contraseña ha sido cambiada exitosamente";
    }

    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {

        Optional<Cuenta> cuentaOpcional = cuentaRepo.findByEmail(loginDTO.email());

        if(cuentaOpcional.isEmpty()) {
            throw new Exception("No existe una cuenta con ese correo electrónico");
        }

        Cuenta cuenta = cuentaOpcional.get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if( !passwordEncoder.matches(loginDTO.password(), cuenta.getPassword()) ) {
            throw new Exception("La contraseña es incorrecta");
        }


        Map<String, Object> map = construirClaims(cuenta);
        return new TokenDTO( jwtUtils.generarToken(cuenta.getEmail(), map) );
    }


    @Override
    public List<ItemCuentaDTO> listarCuentas() {

        List<Cuenta> cuentas = cuentaRepo.findAll();                //Obtenemos todas las cuentas de los
                                                                    //usuarios de la base de datos

        List<ItemCuentaDTO> items = new ArrayList<>();              //Creamos una lista de DTOs


        for (Cuenta cuenta : cuentas) {                          //Recorremos la lista de cuentas y por cada uno
            items.add( new ItemCuentaDTO(                        //creamos un DTO y lo agregamos a la lista
                    cuenta.getId(),
                    cuenta.getUsuario().getNombreCompleto(),
                    cuenta.getEmail(),
                    cuenta.getUsuario().getTelefono()
            ));
        }

        return items;
    }




    //------------------------MÉTODOS AUXILIARES----------------------------

    private boolean existeCedula(@NotBlank @Length(max = 10) String cedula) {

        return cuentaRepo.findByCedula(cedula).isPresent();

    }

    private boolean existeEmail(@NotBlank @Length(max = 40) @Email String correo) {

        return cuentaRepo.findByEmail(correo).isPresent();

    }

    private Optional<Cuenta> getCuenta(String id) throws Exception {

        Optional<Cuenta> optionalCuenta = cuentaRepo.findById(id);                 //Buscamos la cuenta del usuario que se quiere obtener

        if(optionalCuenta.isEmpty()){
            throw new Exception("No se encontró el usuario con el id "+id);        //Si no se encontró la cuenta, lanzamos una excepción
        }

        return optionalCuenta;

    }

    // Método auxiliar para generar un código de recuperación
    private String generarCodigoRecuperacion() {
        // Implementar la lógica para generar un código único
        // Por ejemplo, puedes usar UUID o algún otro método de generación de códigos únicos
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    private String encriptarPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode( password );
    }

    private Map<String, Object> construirClaims(Cuenta cuenta) {
        return Map.of(
                "rol", cuenta.getTipoUsuario(),
                "nombre", cuenta.getUsuario().getNombreCompleto(),
                "id", cuenta.getId()
        );
    }


}
