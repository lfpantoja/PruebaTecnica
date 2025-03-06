package prueba.tecnica.PruebaTecnica.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@MappedSuperclass
public abstract class Persona {
    private String nombre;
    private String genero;
    private int edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}