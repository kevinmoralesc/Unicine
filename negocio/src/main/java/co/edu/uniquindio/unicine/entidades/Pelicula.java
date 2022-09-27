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
public class Pelicula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(nullable = false, length = 20)
    private String nombre;

    @Column(nullable = false)
    private String sinopsis;

    @Column(nullable = false)
    private String urlTrailer;

    @Column(nullable = false)
    private String urlImagen;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Genero genero;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(nullable = false)
    private String reparto;

    @OneToMany(mappedBy = "pelicula")
    private List<Funcion> funciones;


    public Pelicula(String nombre, String sinopsis, String urlTrailer, String urlImagen, Genero genero, String estado, String reparto) {
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.urlTrailer = urlTrailer;
        this.urlImagen = urlImagen;
        this.genero = genero;
        this.estado = estado;
        this.reparto = reparto;
    }
}
