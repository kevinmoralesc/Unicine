package co.edu.uniquindio.unicine.entidades;

import jdk.jfr.Enabled;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Confiteria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;


    @Column(nullable = false,length = 25)
    private String nombre;


    @Positive
    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private String url_imagen;

    @OneToMany(mappedBy = "confiteria")
    private List<CompraConfiteria> compraConfiterias;

    public Confiteria(String nombre, Double precio, String url_imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.url_imagen = url_imagen;
    }
}
