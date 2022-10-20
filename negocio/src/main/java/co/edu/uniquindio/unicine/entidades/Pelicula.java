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
@ToString
public class Pelicula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String sinopsis;

    @Column(nullable = false)
    private String urlTrailer;

    @Column(nullable = false)
    private String urlImagen;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @Column(nullable = false)
    private List<Genero> generos;

    @Column(nullable = false, length = 20)
    private Boolean estado;

    @Column(nullable = false)
    private String reparto;

    @ToString.Exclude
    @OneToMany(mappedBy = "pelicula")
    private List<Funcion> funciones;


    @Builder
    public Pelicula(String nombre, String sinopsis, String urlTrailer, String urlImagen,List<Genero> generos, Boolean estado, String reparto) {
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.urlTrailer = urlTrailer;
        this.urlImagen = urlImagen;
        this.generos = generos;
        this.estado = estado;
        this.reparto = reparto;
    }
}
