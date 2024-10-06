package co.edu.uniquindio.proyecto.dto.cuentaDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CrearCuentaDTO(

        //Anotaciones de Springboot Starter Validation
        //Estas anotaciones son para los DTO para los datos de entrada

        @NotBlank @Length(max = 10)
        String cedula,
        @NotBlank @Length(max = 50)
        String nombre,
        @NotBlank @Length(max = 10)
        String telefono,
        @Length(max = 80)
        String direccion,
        @NotBlank @Length(max = 40) @Email
        String email,
        @NotBlank @Length(min = 7, max = 20)
        String password

) {


}
