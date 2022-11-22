package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pelicula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Lob
    @Column(nullable = false)
    private String sinopsis;

    @Column(nullable = false)
    private String urlTrailer;

    @ElementCollection
    @Column(nullable = false)
    private Map<String, String> imagenes;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @Column(nullable = false)
    private List<Genero> generos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPelicula estado;

    @Lob
    @Column(nullable = false)
    private String reparto;

    @ToString.Exclude
    @OneToMany(mappedBy = "pelicula")
    private List<Funcion> funciones;


    @Builder
    public Pelicula(String nombre, String sinopsis, String urlTrailer,List<Genero> generos, EstadoPelicula estado, String reparto) {
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.urlTrailer = urlTrailer;
        this.generos = generos;
        this.estado = estado;
        this.reparto = reparto;
    }

    public String getImagenPrincipal(){

        if (!imagenes.isEmpty()){
            String primera = imagenes.keySet().toArray()[0].toString();
            return imagenes.get(primera);
        }

        return "";

    }
}
