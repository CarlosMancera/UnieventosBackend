package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.dto.artistasDTO.ResumenArtistaDTO;
import co.edu.uniquindio.proyecto.model.docs.Artista;
import co.edu.uniquindio.proyecto.model.enums.EstadoArtista;
import co.edu.uniquindio.proyecto.repositories.ArtistaRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ArtistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ArtistaServiceImpl implements ArtistaService {

    private final ArtistaRepo artistaRepo;

    // ... Aqui vamos a almacenar metodos que se usan para crearArtista, editarArtista, eliminarArtista

    @Override
    @Transactional(readOnly = true)
    public List<ResumenArtistaDTO> listarArtistas() {
        List<Artista> artistas = artistaRepo.findAll();
        return artistas.stream()
                .map(this::mapToResumenArtistaDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumenArtistaDTO> buscarArtistasPorNombre(String nombre) {
        List<Artista> artistas = artistaRepo.findByNombreContainingIgnoreCase(nombre);
        return artistas.stream()
                .map(this::mapToResumenArtistaDTO)
                .toList();
    }

    private ResumenArtistaDTO mapToResumenArtistaDTO(Artista artista) {
        return new ResumenArtistaDTO(
                artista.getNombre(),
                artista.getGeneros(),
                artista.getEstadoArtista(),
                artista.getContacto().getEmail(),
                artista.getContacto().getTelefono()
        );
    }

    @Override
    public String crearArtista(CrearArtistaDTO artista) throws Exception {
        if (existeNombre(artista.nombre())) {
            throw new Exception("Ya existe un artista con el nombre " + artista.nombre());
        }

        Artista nuevoArtista = new Artista();
        nuevoArtista.setNombre(artista.nombre());
        nuevoArtista.setGeneros(artista.generos());
        nuevoArtista.setTipo(artista.tipo());
        nuevoArtista.setContacto(new Contacto(artista.email(), artista.telefono()));
        nuevoArtista.setTarifa(artista.tarifa());
        nuevoArtista.setReferencias(artista.referencias());
        nuevoArtista.setBiografia(artista.biografia());
        nuevoArtista.setEstado(EstadoArtista.DISPONIBLE);

        Artista artistaCreado = artistaRepo.save(nuevoArtista);

        return artistaCreado.getId().toString();
    }

    @Override
    public String editarArtista(EditarArtistaDTO artista) throws Exception {
        Optional<Artista> optionalArtista = getArtista(artista.id());

        Artista artistaModificado = optionalArtista.get();
        artistaModificado.setNombre(artista.nombre());
        artistaModificado.setGeneros(artista.generos());
        artistaModificado.setTipo(artista.tipo());
        artistaModificado.getContacto().setEmail(artista.email());
        artistaModificado.getContacto().setTelefono(artista.telefono());
        artistaModificado.setTarifa(artista.tarifa());
        artistaModificado.setReferencias(artista.referencias());
        artistaModificado.setBiografia(artista.biografia());

        artistaRepo.save(artistaModificado);

        return artistaModificado.getId();
    }

    @Override
    public String eliminarArtista(String id) throws Exception {
        Optional<Artista> optionalArtista = getArtista(id);

        Artista artista = optionalArtista.get();
        artista.setEstado(EstadoArtista.ELIMINADO);

        artistaRepo.save(artista);

        return artista.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionArtistaDTO obtenerInformacionArtista(String id) throws Exception {
        Optional<Artista> optionalArtista = getArtista(id);

        Artista artista = optionalArtista.get();

        return new InformacionArtistaDTO(
                artista.getId(),
                artista.getNombre(),
                artista.getGeneros(),
                artista.getTipo(),
                artista.getContacto().getEmail(),
                artista.getContacto().getTelefono(),
                artista.getTarifa(),
                artista.getReferencias(),
                artista.getBiografia(),
                artista.getEstado()
        );
    }

    private Optional<Artista> getArtista(String id) throws Exception {
        Optional<Artista> optionalArtista = artistaRepo.findById(id);

        if (optionalArtista.isEmpty()) {
            throw new Exception("No existe un artista con el id " + id);
        }

        return optionalArtista;
    }

    private boolean existeNombre(String nombre) {
        return artistaRepo.findByNombre(nombre).isPresent();
    }


}
