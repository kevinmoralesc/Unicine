package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DistribucionSillas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(nullable = false)
    private String esquema;

    @Positive
    @Column(nullable = false)
    private Integer totalSillas;

    @Positive
    @Column(nullable = false)
    private Integer filas;

    @Positive
    @Column(nullable = false)
    private Integer columnas;

    @ToString.Exclude
    @OneToMany(mappedBy = "distribucionSillas")
    private List<Sala> salas;

    public DistribucionSillas(String esquema, Integer totalSillas, Integer filas, Integer columnas) {
        this.esquema = esquema;
        this.totalSillas = totalSillas;
        this.filas = filas;
        this.columnas = columnas;
    }
}
