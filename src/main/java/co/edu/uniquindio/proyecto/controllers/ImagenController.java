package co.edu.uniquindio.proyecto.controllers;


import co.edu.uniquindio.proyecto.services.util.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/imagenes")
@RequiredArgsConstructor
public class ImagenController {

    private final CloudinaryService cloudinaryService;

    @PostMapping
    public ResponseEntity<String> subirImagen(@RequestParam("file") MultipartFile file) {
        try {
            String url = cloudinaryService.subirImagen(file);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al subir la imagen: " + e.getMessage());
        }
    }
}
