package co.edu.uniquindio.unicine.entidades;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Entrada implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Positive
    @Column(nullable = false)
    private Float precio;

    @Positive
    @Column(nullable = false)
    private Integer fila;

    @Positive
    @Column(nullable = false)
    private Integer columna;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Compra compra;

    public Entrada(Float precio, Integer fila, Integer columna) {
        this.precio = precio;
        this.fila = fila;
        this.columna = columna;
    }
}
