package co.edu.uniquindio.proyecto.dto.teamDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDTO {
    private String nombre;
    private String ciudad;
    private String escudoUrl;
    private String entrenador;
}
