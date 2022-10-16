package co.edu.uniquindio.unicine.entidades;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy = "sala")
    private List<Funcion> funciones;

    @ManyToOne
    @JoinColumn(nullable = false)
    private DistribucionSillas distribucionSillas;

    public Sala(String nombre, Teatro teatro) {
        this.nombre = nombre;
        this.teatro = teatro;
    }
}
