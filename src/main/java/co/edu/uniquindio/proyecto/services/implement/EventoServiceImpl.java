package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.model.vo.Localidad;
import co.edu.uniquindio.proyecto.dto.eventoDTO.*;
import co.edu.uniquindio.proyecto.model.docs.Evento;
import co.edu.uniquindio.proyecto.model.enums.EstadoEvento;
import co.edu.uniquindio.proyecto.repositories.EventoRepo;
import co.edu.uniquindio.proyecto.services.interfaces.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

//METODOS POR REVISAR

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {

    private final EventoRepo eventoRepo;
    private final ArchivoService archivoService;

    @Override
    @Transactional
    public String crearEvento(CrearEventoDTO eventoDTO) throws Exception {
        Evento nuevoEvento = new Evento();
        nuevoEvento.setNombre(eventoDTO.nombre());
        nuevoEvento.setArtista(eventoDTO.artista());
        nuevoEvento.setDescripcion(eventoDTO.descripcion());
        nuevoEvento.setFecha(eventoDTO.fecha());
        nuevoEvento.setDireccion(eventoDTO.direccion());
        nuevoEvento.setCiudad(eventoDTO.ciudad());
        nuevoEvento.setTipoEvento(eventoDTO.tipoEvento());
        nuevoEvento.setEstado(EstadoEvento.ACTIVO);

        for (LocalidadDTO localidadDTO : eventoDTO.localidades()) {
            Localidad localidad = new Localidad();
            localidad.setNombre(localidadDTO.nombre());
            localidad.setPrecio(localidadDTO.precio());
            localidad.setCapacidad(localidadDTO.capacidad());
            nuevoEvento.getLocalidades().add(localidad);
        }

        Evento eventoCreado = eventoRepo.save(nuevoEvento);
        return eventoCreado.getId();
    }

    @Override
    @Transactional
    public String editarEvento(EditarEventoDTO eventoDTO) throws Exception {
        Evento evento = getEvento(eventoDTO.id());

        evento.setNombre(eventoDTO.nombre());
        evento.setArtista(eventoDTO.artista());
        evento.setDescripcion(eventoDTO.descripcion());
        evento.setFecha(eventoDTO.fecha());
        evento.setDireccion(eventoDTO.direccion());
        evento.setCiudad(eventoDTO.ciudad());
        evento.setTipoEvento(eventoDTO.tipoEvento());

        evento.getLocalidades().clear();
        for (LocalidadDTO localidadDTO : eventoDTO.localidades()) {
            Localidad localidad = new Localidad();
            localidad.setNombre(localidadDTO.nombre());
            localidad.setPrecio(localidadDTO.precio());
            localidad.setCapacidad(localidadDTO.capacidad());
            evento.getLocalidades().add(localidad);
        }

        eventoRepo.save(evento);
        return evento.getId();
    }

    @Override
    @Transactional
    public String eliminarEvento(String id) throws Exception {
        Evento evento = getEvento(id);
        evento.setEstado(EstadoEvento.INACTIVO);
        eventoRepo.save(evento);
        return id;
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionEventoDTO obtenerInformacionEvento(String id) throws Exception {
        Evento evento = getEvento(id);
        return mapToInformacionEventoDTO(evento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumenEventoDTO> listarEventos() {
        List<Evento> eventos = eventoRepo.findAll();
        return eventos.stream()
                .map(this::mapToResumenEventoDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumenEventoDTO> buscarEventosPorNombre(String nombre) {
        List<Evento> eventos = eventoRepo.findByNombreContainingIgnoreCase(nombre);
        return eventos.stream()
                .map(this::mapToResumenEventoDTO)
                .toList();
    }

    @Override
    @Transactional
    public void agregarImagenEvento(String idEvento, MultipartFile imagen) throws Exception {
        Evento evento = getEvento(idEvento);
        String rutaImagen = archivoService.subirImagen(imagen);
        evento.getImagenes().add(rutaImagen);
        eventoRepo.save(evento);
    }

    @Override
    @Transactional
    public void agregarImagenLocalidad(String idEvento, String nombreLocalidad, MultipartFile imagen) throws Exception {
        Evento evento = getEvento(idEvento);
        Optional<Localidad> optionalLocalidad = evento.getLocalidades().stream()
                .filter(l -> l.getNombre().equals(nombreLocalidad))
                .findFirst();

        if (optionalLocalidad.isEmpty()) {
            throw new Exception("No se encontró la localidad " + nombreLocalidad);
        }

        Localidad localidad = optionalLocalidad.get();
        String rutaImagen = archivoService.subirImagen(imagen);
        localidad.setImagen(rutaImagen);
        eventoRepo.save(evento);
    }

    @Override
    public byte[] generarReporteVentasHTML(String idEvento) throws Exception {
        // Implementación para generar el reporte HTML
        return new byte[0];
    }

    @Override
    public byte[] generarReporteVentasPDF(String idEvento) throws Exception {
        // Implementación para generar el reporte PDF
        return new byte[0];
    }

    private InformacionEventoDTO mapToInformacionEventoDTO(Evento evento) {
        return new InformacionEventoDTO(
                evento.getId(),
                evento.getNombre(),
                evento.getArtista(),
                evento.getDescripcion(),
                evento.getFecha(),
                evento.getDireccion(),
                evento.getCiudad(),
                evento.getTipoEvento(),
                evento.getEstado(),
                evento.getLocalidades().stream().map(this::mapToLocalidadDTO).toList(),
                evento.getImagenes()
        );
    }

    private ResumenEventoDTO mapToResumenEventoDTO(Evento evento) {
        return new ResumenEventoDTO(
                evento.getId(),
                evento.getNombre(),
                evento.getFecha(),
                evento.getDireccion(),
                evento.getLocalidades().stream().mapToDouble(Localidad::getPrecio).min().orElse(0),
                evento.getLocalidades().stream().mapToInt(Localidad::getCapacidad).sum(),
                evento.getTipoEvento(),
                evento.getEstado()
        );
    }

    private LocalidadDTO mapToLocalidadDTO(Localidad localidad) {
        return new LocalidadDTO(
                localidad.getNombre(),
                localidad.getPrecio(),
                localidad.getCapacidad(),
                localidad.getImagen()
        );
    }

    private Evento getEvento(String id) throws Exception {
        Optional<Evento> optionalEvento = eventoRepo.findById(id);
        if (optionalEvento.isEmpty()) {
            throw new Exception("No se encontró el evento con el id " + id);
        }
        return optionalEvento.get();
        }
}