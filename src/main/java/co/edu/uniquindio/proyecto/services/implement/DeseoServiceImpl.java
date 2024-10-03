package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.deseoDTO.AgregarDeseoDTO;
import co.edu.uniquindio.proyecto.dto.deseoDTO.ResumenDeseoDTO;
import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;
import co.edu.uniquindio.proyecto.model.docs.Cuenta;
import co.edu.uniquindio.proyecto.model.docs.Deseo;
import co.edu.uniquindio.proyecto.model.docs.Evento;
import co.edu.uniquindio.proyecto.repositories.CuentaRepo;
import co.edu.uniquindio.proyecto.repositories.DeseoRepo;
import co.edu.uniquindio.proyecto.repositories.EventoRepo;
import co.edu.uniquindio.proyecto.services.interfaces.DeseoService;
import co.edu.uniquindio.proyecto.services.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
        if (deseoRepo.existsByCuentaAndEvento(deseoDTO.idCuenta(), deseoDTO.idEvento())) {
            throw new Exception("El evento ya está en la lista de deseos del usuario");
        }

        Deseo nuevoDeseo = Deseo.builder()
                .cuenta(deseoDTO.idCuenta())
                .evento(deseoDTO.idEvento())
                .recibeInfo(deseoDTO.recibeInfo())
                .build();

        Deseo deseoCreado = deseoRepo.save(nuevoDeseo);
        return deseoCreado.getId();
    }

    @Override
    @Transactional
    public void eliminarDeseo(ObjectId idCuenta, ObjectId idEvento) throws Exception {
        deseoRepo.deleteByCuentaAndEvento(idCuenta, idEvento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumenDeseoDTO> listarDeseos(ObjectId idCuenta) throws Exception {
        List<Deseo> deseos = deseoRepo.findByCuenta(idCuenta);
        return deseos.stream().map(this::mapToResumenDeseoDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumenDeseoDTO> buscarDeseos(ObjectId idCuenta, String nombreEvento) throws Exception {
        // Esta implementación requerirá una consulta personalizada o procesamiento adicional
        // ya que MongoDB no soporta directamente búsquedas en campos de documentos relacionados
        List<Deseo> deseos = deseoRepo.findByCuenta(idCuenta);
        return deseos.stream()
                .filter(d -> eventoRepo.findById(d.getEvento().toString())
                        .map(e -> e.getNombre().toLowerCase().contains(nombreEvento.toLowerCase()))
                        .orElse(false))
                .map(this::mapToResumenDeseoDTO)
                .toList();
    }

    @Override
    @Transactional
    public void enviarNotificaciones()  {
        List<Deseo> deseos = deseoRepo.findAll();
        for (Deseo deseo : deseos) {
            if (deseo.isRecibeInfo()) {
                eventoRepo.findById(deseo.getEvento().toString()).ifPresent(evento -> {
                    if (esEventoCercano(evento)) {
                        try {
                            enviarNotificacionEventoCercano(deseo, evento);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }
    }

    private ResumenDeseoDTO mapToResumenDeseoDTO(Deseo deseo) {
        Evento evento = eventoRepo.findById(deseo.getEvento().toString()).orElseThrow();
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
        Optional<Cuenta> cuentaOptional = cuentaRepo.findById(deseo.getCuenta().toString());

        if (cuentaOptional.isPresent()) {
            Cuenta cuenta = cuentaOptional.get();
            String asunto = "Evento próximo";
            String cuerpo = "El evento " + evento.getNombre() + " está próximo a realizarse.";

            EmailDTO emailDTO = new EmailDTO(cuenta.getEmail(), asunto, cuerpo);
            emailService.enviarCorreo(new EmailDTO(asunto, cuerpo, cuenta.getEmail()));
        }
    }
}