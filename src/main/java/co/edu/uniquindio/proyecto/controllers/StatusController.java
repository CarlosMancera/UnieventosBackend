package co.edu.uniquindio.proyecto.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/")
    public String status() {
        return "✅ Backend UniEventos está en funcionamiento";
    }
}
