package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.model.entities.Ticket;
import co.edu.uniquindio.proyecto.services.interfaces.PdfGeneratorService;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    @Override
    public byte[] generarPdfTicket(Ticket ticket) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("TICKET DE ENTRADA").setBold().setFontSize(18));
        document.add(new Paragraph("Código: " + ticket.getCodigo()));
        document.add(new Paragraph("Evento: " + ticket.getMatch().getEquipoLocal() + " vs " + ticket.getMatch().getEquipoVisitante()));
        document.add(new Paragraph("Fecha: " + ticket.getMatch().getFechaHora()));
        document.add(new Paragraph("Sección: " + ticket.getSection().getNombre()));
        document.add(new Paragraph("Portador: " + ticket.getPortador().getNombre()));
        document.add(new Paragraph("Cédula: " + ticket.getPortador().getCedula()));

        document.close();
        return baos.toByteArray();
    }
}
