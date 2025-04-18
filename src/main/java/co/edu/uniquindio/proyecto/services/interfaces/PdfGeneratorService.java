package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.model.entities.Ticket;

import java.io.IOException;

public interface PdfGeneratorService {
    byte[] generarPdfTicket(Ticket ticket) throws IOException;
}

