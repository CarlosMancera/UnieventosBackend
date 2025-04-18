package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;
import co.edu.uniquindio.proyecto.dto.ticketDTO.CrearTicketDTO;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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
                new EmailDTO(dto.getEmailPortador(), "Tu entrada para el partido", "Adjunto tu ticket."),
                pdfBytes,
                "ticket-" + ticketGuardado.getCodigo() + ".pdf"
        );

        return ticketGuardado.getId();
    }
}

