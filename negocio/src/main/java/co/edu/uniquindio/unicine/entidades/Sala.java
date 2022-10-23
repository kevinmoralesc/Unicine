package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sala implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(nullable = false,length = 50)
    private String nombre;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Teatro teatro;

    @ToString.Exclude
    @OneToMany(mappedBy = "sala")
    private List<Funcion> funciones;

    @ManyToOne
    @JoinColumn(nullable = false)
    private DistribucionSillas distribucionSillas;
    @Builder
    public Sala(String nombre, Teatro teatro, DistribucionSillas distribucionSillas) {
        this.nombre = nombre;
        this.teatro = teatro;
        this.distribucionSillas = distribucionSillas;
    }
}
