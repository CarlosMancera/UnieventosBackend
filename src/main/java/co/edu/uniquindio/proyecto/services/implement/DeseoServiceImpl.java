package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.deseoDTO.AgregarDeseoDTO;
import co.edu.uniquindio.proyecto.dto.deseoDTO.ResumenDeseoDTO;
import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;
import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import co.edu.uniquindio.proyecto.model.entities.Deseo;
import co.edu.uniquindio.proyecto.model.entities.Evento;
import co.edu.uniquindio.proyecto.repositories.CuentaRepo;
import co.edu.uniquindio.proyecto.repositories.DeseoRepo;
import co.edu.uniquindio.proyecto.repositories.EventoRepo;
import co.edu.uniquindio.proyecto.services.interfaces.DeseoService;
import co.edu.uniquindio.proyecto.services.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeseoServiceImpl implements DeseoService {


    private final DeseoRepo deseoRepo;
    private final CuentaRepo cuentaRepo;
    private final EventoRepo eventoRepo;
    private final EmailService emailService;


    @Override
    @Transactional
    public String agregarDeseo(AgregarDeseoDTO deseoDTO) throws Exception {
        // Obtener las entidades Cuenta y Evento a partir de los IDs proporcionados
        Cuenta cuenta = cuentaRepo.findById(deseoDTO.idCuenta())
                .orElseThrow(() -> new Exception("No existe una cuenta con el ID: " + deseoDTO.idCuenta()));

        Evento evento = eventoRepo.findById(deseoDTO.idEvento())
                .orElseThrow(() -> new Exception("No existe un evento con el ID: " + deseoDTO.idEvento()));

        // Verificar si el deseo ya existe
        if (deseoRepo.existsByCuentaAndEvento(cuenta, evento)) {
            throw new Exception("El evento ya está en la lista de deseos del usuario");
        }

        // Crear la entidad Deseo con las referencias correctas
        Deseo nuevoDeseo = Deseo.builder()
                .cuenta(cuenta)  // Se asigna la entidad `Cuenta`, no solo su ID
                .evento(evento)  // Se asigna la entidad `Evento`, no solo su ID
                .recibeInfo(deseoDTO.recibeInfo())
                .build();

        Deseo deseoCreado = deseoRepo.save(nuevoDeseo);
        return deseoCreado.getId().toString();
    }

    @Override
    @Transactional
    public void eliminarDeseo(Long idCuenta, Long idEvento) throws Exception {
        // Obtener las entidades Cuenta y Evento a partir de los IDs proporcionados
        Cuenta cuenta = cuentaRepo.findById(idCuenta)
                .orElseThrow(() -> new Exception("No existe una cuenta con el ID: " + idCuenta));

        Evento evento = eventoRepo.findById(idEvento)
                .orElseThrow(() -> new Exception("No existe un evento con el ID: " + idEvento));

        // Ahora se pueden pasar los objetos a `deleteByCuentaAndEvento`
        deseoRepo.deleteByCuentaAndEvento(cuenta, evento);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ResumenDeseoDTO> listarDeseos(Long idCuenta) throws Exception {
        // Obtener la entidad Cuenta a partir del ID proporcionado
        Cuenta cuenta = cuentaRepo.findById(idCuenta)
                .orElseThrow(() -> new Exception("No existe una cuenta con el ID: " + idCuenta));

        // Buscar los deseos asociados a la cuenta
        List<Deseo> deseos = deseoRepo.findByCuenta(cuenta);

        // Convertir los deseos a DTOs
        return deseos.stream().map(this::mapToResumenDeseoDTO).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<ResumenDeseoDTO> buscarDeseos(Long idCuenta, String nombreEvento) throws Exception {
        // Obtener la entidad Cuenta a partir del ID proporcionado
        Cuenta cuenta = cuentaRepo.findById(idCuenta)
                .orElseThrow(() -> new Exception("No existe una cuenta con el ID: " + idCuenta));

        // Obtener los deseos asociados a la cuenta
        List<Deseo> deseos = deseoRepo.findByCuenta(cuenta);

        // Filtrar los deseos cuyos eventos contienen el nombre buscado
        return deseos.stream()
                .filter(d -> d.getEvento().getNombre().toLowerCase().contains(nombreEvento.toLowerCase()))
                .map(this::mapToResumenDeseoDTO)
                .toList();
    }
    @Override
    @Transactional
    public void enviarNotificaciones() {
        List<Deseo> deseos = deseoRepo.findAll();

        for (Deseo deseo : deseos) {
            if (deseo.isRecibeInfo()) {
                eventoRepo.findById(deseo.getEvento().getId())  // ✅ Ahora usa correctamente el ID
                        .ifPresent(evento -> {
                            if (esEventoCercano(evento)) {
                                try {
                                    enviarNotificacionEventoCercano(deseo, evento);
                                } catch (Exception e) {
                                    throw new RuntimeException("Error al enviar notificación", e);
                                }
                            }
                        });
            }
        }
    }


    private ResumenDeseoDTO mapToResumenDeseoDTO(Deseo deseo) {
        Evento evento = eventoRepo.findById(deseo.getEvento().getId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + deseo.getEvento().getId()));

        return new ResumenDeseoDTO(
                deseo.getId(),
                evento.getNombre(),
                evento.getFecha(),
                deseo.isRecibeInfo()
        );
    }

    private boolean esEventoCercano(Evento evento) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaEvento = evento.getFecha().toLocalDate();

        long diasHastaEvento = ChronoUnit.DAYS.between(fechaActual, fechaEvento);

        // Consideramos que un evento está próximo si faltan 7 días o menos
        return diasHastaEvento >= 0 && diasHastaEvento <= 7;
    }

    /*private void enviarNotificacionEventoCercano(Deseo deseo, Evento evento) {
        cuentaRepo.findById(deseo.getCuenta().toString()).ifPresent(cuenta -> {
            emailService.enviarCorreo(new EmailDTO("Evento próximo", "El evento " + evento.getNombre() + " está próximo a realizarse."cuenta.getEmail())

        });
    }*/

    private void enviarNotificacionEventoCercano(Deseo deseo, Evento evento) throws Exception {
        // Obtener la cuenta a partir del ID
        Cuenta cuenta = cuentaRepo.findById(deseo.getCuenta().getId())
                .orElseThrow(() -> new Exception("Cuenta no encontrada con ID: " + deseo.getCuenta().getId()));

        // Crear el correo
        String asunto = "Evento próximo";
        String cuerpo = "El evento " + evento.getNombre() + " está próximo a realizarse.";

        EmailDTO emailDTO = new EmailDTO(cuenta.getEmail(), asunto, cuerpo);

        // Enviar la notificación
        emailService.enviarEmail(emailDTO);
    }

}