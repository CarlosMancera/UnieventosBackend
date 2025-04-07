package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.eventoDTO.*;
import co.edu.uniquindio.proyecto.model.entities.Artista;
import co.edu.uniquindio.proyecto.model.entities.Evento;
import co.edu.uniquindio.proyecto.model.entities.Localidad;
import co.edu.uniquindio.proyecto.model.enums.EstadoEvento;
import co.edu.uniquindio.proyecto.repositories.EventoRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ArtistaService;
import co.edu.uniquindio.proyecto.services.interfaces.EventoService;
import co.edu.uniquindio.proyecto.services.interfaces.ImagenesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {

    private final EventoRepo eventoRepo;
    private final ImagenesService archivoService;
    private final ArtistaService artistaRepo;

    @Override
    @Transactional
    public String crearEvento(CrearEventoDTO eventoDTO) throws Exception {
        if (eventoDTO.artista() == null || eventoDTO.artista().isBlank()) {
            throw new Exception("El ID del artista no puede ser nulo o vacío.");
        }

        Long artistaId;
        try {
            artistaId = Long.valueOf(eventoDTO.artista());
        } catch (NumberFormatException e) {
            throw new Exception("El ID del artista debe ser un número válido.");
        }

        Artista artista = artistaRepo.findById(artistaId);

        if (eventoDTO.localidades() == null || eventoDTO.localidades().isEmpty()) {
            throw new Exception("El evento debe tener al menos una localidad.");
        }

        List<Localidad> localidades = eventoDTO.localidades().stream()
                .map(localidadDTO -> Localidad.builder()
                        .nombre(localidadDTO.nombre())
                        .precio(localidadDTO.precio())
                        .capacidad(localidadDTO.capacidad())
                        .descripcion(localidadDTO.descripcion())
                        .imagen(localidadDTO.imagen())
                        .entradasVendidas(0)
                        .disponibilidad(true)
                        .build())
                .toList();

        Evento nuevoEvento = Evento.builder()
                .nombre(eventoDTO.nombre())
                .descripcion(eventoDTO.descripcion())
                .direccion(eventoDTO.direccion())
                .ciudad(eventoDTO.ciudad())
                .fecha(eventoDTO.fecha())
                .tipoEvento(eventoDTO.tipoEvento())
                .estadoEvento(EstadoEvento.ACTIVO)
                .artista(artista)
                .localidades(localidades)
                .imagenPortada(eventoDTO.imagenPortada())
                .imagenLocalidades(eventoDTO.imagenLocalidades())
                .build();

        Evento eventoGuardado = eventoRepo.save(nuevoEvento);
        return eventoGuardado.getId().toString();
    }



    @Override
    @Transactional
    public Long editarEvento(EditarEventoDTO eventoDTO) throws Exception {

        Evento evento = getEvento(1L);

        evento.setNombre(eventoDTO.nombre());
        evento.setArtista(eventoDTO.artista());
        evento.setDescripcion(eventoDTO.descripcion());
        evento.setFecha(eventoDTO.fecha());
        evento.setDireccion(eventoDTO.direccion());
        evento.setCiudad(eventoDTO.ciudad());
        evento.setTipoEvento(eventoDTO.tipoEvento());

        evento.getLocalidades().clear();
        for (LocalidadEventoDTO localidadDTO : eventoDTO.localidades()) {
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
    public Long eliminarEvento(Long id) throws Exception {
        Evento evento = getEvento(id);
        evento.setEstadoEvento(EstadoEvento.INACTIVO);
        eventoRepo.save(evento);
        return id;
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionEventoDTO obtenerInformacionEvento(Long id) throws Exception {
        Evento evento = getEvento(id);
        return mapToInformacionEventoDTO(evento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumenEventoDTO> listarEventos() {

        List<Evento> eventos = eventoRepo.findAll();                //Obtenemos todos los eventos
                                                                    //eventos   de la base de datos

        List<ResumenEventoDTO> items = new ArrayList<>();              //Creamos una lista de DTOs


        for (Evento evento : eventos) {                          //Recorremos la lista de eventos y por cada uno
            items.add( new ResumenEventoDTO(                        //creamos un DTO y lo agregamos a la lista
                    evento.getNombre(),
                    evento.getFecha(),
                    evento.getDireccion(),
                    evento.getCantidadLocalidades(),
                    evento.getTipoEvento(),
                    evento.getEstadoEvento()
            ));
        }

        return items;

    }

    //TODO
    @Override
    public List<ItemEventoDTO> filtrarEventos(FiltroEventoDTO filtroDTO) throws Exception {

        List<Evento> listaEventos = eventoRepo.findByNombreAndCiudadAndTipoEvento(filtroDTO.nombre(),filtroDTO.ciudad(),filtroDTO.tipoEvento());
        ArrayList<ItemEventoDTO> items = new ArrayList<>();
        for (Evento evento : listaEventos){

            items.add( new ItemEventoDTO(evento.getNombre(),evento.getFecha(),evento.getTipoEvento()));

        }

        return items;
    }

    @Override
    public List<ResumenEventoDTO> buscarEventosPorNombre(String nombre) {

        List<Evento> listaEventos = eventoRepo.findByNombreContainingIgnoreCase(nombre);
        List<ResumenEventoDTO> resumenEventos = new ArrayList<>();
        for (Evento evento : listaEventos){

            resumenEventos.add(new ResumenEventoDTO( evento.getNombre(), evento.getFecha(),
                    evento.getDireccion(), evento.getCantidadLocalidades(), evento.getTipoEvento(), evento.getEstadoEvento()));

        }

        return resumenEventos;

    }

    @Override
    public void cambiarEstadoEvento(Long id, EstadoEvento nuevoEstado) throws Exception {

        Optional<Evento> eventoOp = eventoRepo.findById(id);
        if(eventoOp.isEmpty()) throw new Exception();

        Evento evento = eventoOp.get();

        evento.setEstadoEvento(nuevoEstado);
        eventoRepo.save(evento);

    }



    //TODO
    @Override
    @Transactional
    public void agregarImagenEvento(Long idEvento, MultipartFile imagen) throws Exception {
        Evento evento = getEvento(idEvento);
        String rutaImagen = archivoService.subirImagen(imagen);
        evento.setImagen(rutaImagen);
        eventoRepo.save(evento);
    }

    @Override
    @Transactional
    public void agregarImagenLocalidad(Long idEvento, String nombreLocalidad, MultipartFile imagen) throws Exception {
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
    public byte[] generarReporteVentasPDF(Long idEvento) throws Exception {
        // Implementación para generar el reporte PDF
        return new byte[0];
    }

    @Override
    public byte[] generarReporteVentasXML(Long idEvento) throws Exception {
        return new byte[0];
    }

    private InformacionEventoDTO mapToInformacionEventoDTO(Evento evento) {
        return new InformacionEventoDTO(
                evento.getId(),
                evento.getNombre(),
                evento.getArtista().toString(),
                evento.getDescripcion(),
                evento.getFecha(),
                evento.getDireccion(),
                evento.getCiudad(),
                evento.getTipoEvento(),
                evento.getEstadoEvento(),
                evento.getLocalidades().stream().map(this::mapToLocalidadEventoDTO).toList(),
                evento.getImagen()
        );
    }

    private LocalidadEventoDTO mapToLocalidadEventoDTO(Localidad localidad) {
        return new LocalidadEventoDTO(
                localidad.getNombre(),
                localidad.getPrecio(),
                localidad.getCapacidad(),
                localidad.getDescripcion(),
                localidad.getImagen()
        );
    }


    private Evento getEvento(Long id) throws Exception {
        Optional<Evento> optionalEvento = eventoRepo.findById(id);
        if (optionalEvento.isEmpty()) {
            throw new Exception("No se encontró el evento con el id " + id);
        }
        return optionalEvento.get();
        }
}