package co.edu.uniquindio.proyecto.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
public interface ImagenesService {

    String subirImagen(MultipartFile imagen) throws Exception;
    void eliminarImagen(String nombreImagen) throws Exception;
}
