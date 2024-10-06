package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.artistasDTO.CrearArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.EditarArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.InformacionArtistaDTO;
import co.edu.uniquindio.proyecto.model.docs.Artista;
import co.edu.uniquindio.proyecto.model.enums.EstadoArtista;
import co.edu.uniquindio.proyecto.repositories.ArtistaRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ArtistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ArtistaServiceImpl implements ArtistaService {

    private final ArtistaRepo artistaRepo;

    @Override
    @Transactional(readOnly = true)
    public List<InformacionArtistaDTO> listarArtistas() {


        List<Artista> artistas = artistaRepo.findAll();
        //usuarios de la base de datos

        List<InformacionArtistaDTO> items = new ArrayList<>();


        for (Artista artista : artistas) {
            items.add( new InformacionArtistaDTO(
                    artista.getNombre(),
                    artista.getGenero(),
                    artista.getEmail(),
                    artista.getTelefono()
            ));
        }

        return items;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InformacionArtistaDTO> buscarArtistasPorNombre(String nombre) {
        List<Artista> artistas = artistaRepo.findByNombreContainingIgnoreCase(nombre);
        List<InformacionArtistaDTO> resultado = new ArrayList<>();

        for (Artista artista : artistas) {
            resultado.add( new InformacionArtistaDTO(
                    artista.getNombre(),
                    artista.getGenero(),
                    artista.getEmail(),
                    artista.getTelefono()
            ));
        }

        return resultado;
    }

    @Override
    public String crearArtista(CrearArtistaDTO artista) throws Exception {
        if (existeNombre(artista.nombre())) {
            throw new Exception("Ya existe un artista con el nombre " + artista.nombre());
        }

        Artista nuevoArtista = new Artista();
        nuevoArtista.setNombre(artista.nombre());
        nuevoArtista.setGenero(artista.genero());
        nuevoArtista.setEmail(artista.email());
        nuevoArtista.setTelefono(artista.telefono());
        nuevoArtista.setEstado(EstadoArtista.DISPONIBLE);

        Artista artistaCreado = artistaRepo.save(nuevoArtista);

        return artistaCreado.getId().toString();
    }

    @Override
    public String editarArtista(EditarArtistaDTO artista) throws Exception {
        Optional<Artista> optionalArtista = getArtista(artista.id());

        Artista artistaModificado = optionalArtista.get();
        artistaModificado.setNombre(artista.nombre());
        artistaModificado.setGenero(artista.genero());
        artistaModificado.setEmail(artista.email());
        artistaModificado.setTelefono(artista.telefono());

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
                artista.getNombre(),
                artista.getGenero(),
                artista.getEmail(),
                artista.getTelefono()
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
