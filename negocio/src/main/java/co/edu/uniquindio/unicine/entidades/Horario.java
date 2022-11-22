package co.edu.uniquindio.unicine.entidades;

import lombok.*;

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
@ToString
public class Horario implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        private Integer codigo;

        @Column(nullable = false)
        private String dia;

        @Column(nullable = false)
        private LocalTime hora;

        @Column(nullable = false)
        private LocalDate fechaInicio;

        @Column(nullable = false)
        private LocalDate fechaFin;

        @ToString.Exclude
        @OneToMany(mappedBy = "horario")
        private List<Funcion> funcion;
        @Builder
        public Horario(String dia, LocalTime hora, LocalDate fechaInicio, LocalDate fechaFin) {
                this.dia = dia;
                this.hora = hora;
                this.fechaInicio = fechaInicio;
                this.fechaFin = fechaFin;
        }
}
