package co.edu.uniquindio.unicine.entidades;

import jdk.jfr.Enabled;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

    @ElementCollection
    @Column(nullable = false)
    private Map<String, String> imagen;

    @ToString.Exclude
    @OneToMany(mappedBy = "confiteria")
    private List<CompraConfiteria> compraConfiterias;

    @Builder
    public Confiteria(String nombre, Double precio, String url_imagen) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getImagenPrincipal(){

        if (!imagen.isEmpty()){
            String primera = imagen.keySet().toArray()[0].toString();
            return imagen.get(primera);
        }

        return "";

    }
}
