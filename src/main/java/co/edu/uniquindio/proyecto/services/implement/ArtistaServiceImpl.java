package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.artistasDTO.CrearArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.EditarArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.InformacionArtistaDTO;
import co.edu.uniquindio.proyecto.model.entities.Artista;
import co.edu.uniquindio.proyecto.model.enums.EstadoArtista;
import co.edu.uniquindio.proyecto.repositories.ArtistaRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ArtistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistaServiceImpl implements ArtistaService {

    private final ArtistaRepo artistaRepo;

    @Override
    @Transactional(readOnly = true)
    public List<InformacionArtistaDTO> listarArtistas() {
        return artistaRepo.findAll().stream()
                .map(artista -> new InformacionArtistaDTO(
                        artista.getNombre(),
                        artista.getGenero(),
                        artista.getEmail(),
                        artista.getTelefono()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InformacionArtistaDTO> buscarArtistasPorNombre(String nombre) {
        return artistaRepo.findByNombreContainingIgnoreCase(nombre).stream()
                .map(artista -> new InformacionArtistaDTO(
                        artista.getNombre(),
                        artista.getGenero(),
                        artista.getEmail(),
                        artista.getTelefono()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String crearArtista(CrearArtistaDTO artista) throws Exception {
        if (existeNombre(artista.nombre())) {
            throw new Exception("Ya existe un artista con el nombre " + artista.nombre());
        }

        Artista nuevoArtista = Artista.builder()
                .nombre(artista.nombre())
                .genero(artista.genero())
                .email(artista.email())
                .telefono(artista.telefono())
                .estado(EstadoArtista.DISPONIBLE)
                .build();

        return artistaRepo.save(nuevoArtista).getId().toString();
    }

    @Override
    public String editarArtista(EditarArtistaDTO artista) throws Exception {
        Artista artistaModificado = getArtista(artista.id());

        artistaModificado.setNombre(artista.nombre());
        artistaModificado.setGenero(artista.genero());
        artistaModificado.setEmail(artista.email());
        artistaModificado.setTelefono(artista.telefono());

        return artistaRepo.save(artistaModificado).getId().toString();
    }

    @Override
    public String eliminarArtista(Long id) throws Exception {
        Artista artista = getArtista(id);
        artista.setEstado(EstadoArtista.ELIMINADO);
        return artistaRepo.save(artista).getId().toString();
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionArtistaDTO obtenerInformacionArtista(Long id) throws Exception {
        Artista artista = getArtista(id);
        return new InformacionArtistaDTO(
                artista.getNombre(),
                artista.getGenero(),
                artista.getEmail(),
                artista.getTelefono()
        );
    }

    private Artista getArtista(Long id) throws Exception {
        return artistaRepo.findById(id)
                .orElseThrow(() -> new Exception("No existe un artista con el id " + id));
    }

    private boolean existeNombre(String nombre) {
        return artistaRepo.findByNombre(nombre).isPresent();
    }

}
