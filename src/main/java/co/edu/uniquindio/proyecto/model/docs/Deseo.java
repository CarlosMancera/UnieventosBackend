package co.edu.uniquindio.proyecto.model.docs;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("deseos")
public class Deseo {

    @EqualsAndHashCode.Include
    @Id
    private String id;

    private ObjectId cuenta;
    private ObjectId evento;
    private boolean recibeInfo;

    @Builder
    public Deseo(ObjectId cuenta, ObjectId evento, boolean recibeInfo) {
        this.cuenta = cuenta;
        this.evento = evento;
        this.recibeInfo = recibeInfo;
    }
}
