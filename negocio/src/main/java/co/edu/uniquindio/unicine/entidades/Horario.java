package co.edu.uniquindio.unicine.entidades;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Horario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(nullable = false)
    private LocalDate dia;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    @OneToMany(mappedBy = "horario")
    private List<Funcion> funcion;

    public Horario(LocalDate dia, LocalTime hora, LocalTime horaInicio, LocalTime horaFin) {
        this.dia = dia;
        this.hora = hora;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}
