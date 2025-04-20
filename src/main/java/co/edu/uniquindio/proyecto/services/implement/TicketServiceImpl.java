package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.compraDTO.CrearCompraDTO;
import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;
import co.edu.uniquindio.proyecto.dto.ticketDTO.CrearTicketDTO;
import co.edu.uniquindio.proyecto.dto.ticketDTO.ItemTicketDTO;
import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import co.edu.uniquindio.proyecto.model.entities.Match;
import co.edu.uniquindio.proyecto.model.entities.Section;
import co.edu.uniquindio.proyecto.model.entities.Ticket;
import co.edu.uniquindio.proyecto.model.vo.Usuario;
import co.edu.uniquindio.proyecto.repositories.CuentaRepo;
import co.edu.uniquindio.proyecto.repositories.MatchRepository;
import co.edu.uniquindio.proyecto.repositories.SectionRepository;
import co.edu.uniquindio.proyecto.repositories.TicketRepository;
import co.edu.uniquindio.proyecto.services.interfaces.EmailService;
import co.edu.uniquindio.proyecto.services.interfaces.PdfGeneratorService;
import co.edu.uniquindio.proyecto.services.interfaces.TicketService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final MatchRepository matchRepository;
    private final SectionRepository sectionRepository;
    private final CuentaRepo cuentaRepository;
    private final EmailService emailService;
    private final PdfGeneratorService pdfGeneratorService;

    @Override
    public Long crearTicket(CrearTicketDTO dto) throws Exception {
        Match match = matchRepository.findById(dto.getMatchId())
                .orElseThrow(() -> new Exception("Partido no encontrado"));

        Section section = sectionRepository.findById(dto.getSectionId())
                .orElseThrow(() -> new Exception("Sección no encontrada"));

        if (section.getCapacidadRestante() <= 0) {
            throw new Exception("No hay boletas disponibles en esta sección.");
        }

        section.setCapacidadRestante(section.getCapacidadRestante() - 1);
        sectionRepository.save(section);

        Cuenta cuenta = cuentaRepository.findById(dto.getCuentaId())
                .orElseThrow(() -> new Exception("Cuenta no encontrada"));

        Usuario portador = new Usuario(
                dto.getCedulaPortador(),
                dto.getNombrePortador(),
                dto.getDireccionPortador(),
                dto.getTelefonoPortador()
        );

        Ticket ticket = Ticket.builder()
                .codigo(UUID.randomUUID().toString())
                .match(match)
                .section(section)
                .comprador(cuenta)
                .portador(portador)
                .emailPortador(dto.getEmailPortador())
                .estado(Ticket.EstadoTicket.COMPRADA)
                .fechaCompra(LocalDateTime.now())
                .build();

        Ticket ticketGuardado = ticketRepository.save(ticket);

        byte[] pdfBytes = pdfGeneratorService.generarPdfTicket(ticketGuardado);

        emailService.enviarEmailConAdjunto(
                new EmailDTO(cuenta.getEmail(), "Tu entrada para el partido de: " + dto.getNombrePortador(), "Adjunto su ticket."),
                pdfBytes,
                "ticket-" + ticketGuardado.getCodigo() + ".pdf"
        );

        emailService.enviarEmailConAdjunto(
                new EmailDTO(dto.getEmailPortador(), "Tu entrada para el partido", "Adjunto tu ticket."),
                pdfBytes,
                "ticket-" + ticketGuardado.getCodigo() + ".pdf"
        );

        return ticketGuardado.getId();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void crearCompra(CrearCompraDTO dto) throws Exception {
        Match match = matchRepository.findById(dto.getMatchId())
                .orElseThrow(() -> new Exception("Partido no encontrado"));

        Map<Long, Section> seccionesMap = new HashMap<>();

        for (CrearTicketDTO ticketDTO : dto.getTickets()) {
            Long sectionId = ticketDTO.getSectionId();
            seccionesMap.putIfAbsent(sectionId,
                    sectionRepository.findById(sectionId)
                            .orElseThrow(() -> new Exception("Sección no encontrada: ID " + sectionId)));
        }

        Map<Long, Long> conteoPorSeccion = dto.getTickets().stream()
                .collect(Collectors.groupingBy(CrearTicketDTO::getSectionId, Collectors.counting()));

        for (Map.Entry<Long, Long> entry : conteoPorSeccion.entrySet()) {
            Section s = seccionesMap.get(entry.getKey());
            if (s.getCapacidadRestante() < entry.getValue()) {
                throw new Exception("No hay suficientes boletas en la sección: " + s.getNombre());
            }
        }

        Cuenta cuenta = cuentaRepository.findById(dto.getCuentaId())
                .orElseThrow(() -> new Exception("Cuenta no encontrada"));

        for (CrearTicketDTO ticketDTO : dto.getTickets()) {
            Section s = seccionesMap.get(ticketDTO.getSectionId());
            s.setCapacidadRestante(s.getCapacidadRestante() - 1);

            Usuario portador = new Usuario(
                    ticketDTO.getCedulaPortador(),
                    ticketDTO.getNombrePortador(),
                    ticketDTO.getDireccionPortador(),
                    ticketDTO.getTelefonoPortador()
            );

            Ticket ticket = Ticket.builder()
                    .codigo(UUID.randomUUID().toString())
                    .match(match)
                    .section(s)
                    .comprador(cuenta)
                    .portador(portador)
                    .emailPortador(ticketDTO.getEmailPortador())
                    .estado(Ticket.EstadoTicket.COMPRADA)
                    .fechaCompra(LocalDateTime.now())
                    .build();

            Ticket ticketGuardado = ticketRepository.save(ticket);

            byte[] pdf = pdfGeneratorService.generarPdfTicket(ticketGuardado);

            emailService.enviarEmailConAdjunto(
                    new EmailDTO(cuenta.getEmail(), "Entrada " + ticketDTO.getNombrePortador(), "Adjunto su ticket."),
                    pdf,
                    "ticket-" + ticketGuardado.getCodigo() + ".pdf"
            );

            emailService.enviarEmailConAdjunto(
                    new EmailDTO(ticketDTO.getEmailPortador(), "Tu entrada para el partido", "Adjunto tu ticket."),
                    pdf,
                    "ticket-" + ticketGuardado.getCodigo() + ".pdf"
            );
        }

        sectionRepository.saveAll(seccionesMap.values());
    }



    @Override
    public List<ItemTicketDTO> listarTicketsPorCuenta(Long cuentaId) {
        List<Ticket> tickets = ticketRepository.findAllByComprador_Id(cuentaId);

        return tickets.stream().map(t -> ItemTicketDTO.builder()
                .id(t.getId())
                .codigo(t.getCodigo())
                .nombrePortador(t.getPortador().getNombre())
                .emailPortador(t.getEmailPortador())
                .nombreMatch(t.getMatch().getEquipoLocal() + " vs " + t.getMatch().getEquipoVisitante())
                .nombreSeccion(t.getSection().getNombre())
                .fechaCompra(t.getFechaCompra())
                .estado(t.getEstado().name())
                .build()
        ).toList();
    }

}

