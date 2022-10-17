package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Compra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MedioPago medioPago;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Positive
    @Column(nullable = false)
    private Float valorTotal;

    @ToString.Exclude
    @OneToMany(mappedBy = "compra")
    private List<Entrada> entradas;

    @ToString.Exclude
    @OneToOne
    private CuponCliente cuponCliente;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Funcion funcion;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;

    @ToString.Exclude
    @OneToMany(mappedBy = "compra")
    private List<CompraConfiteria> compraConfiterias;

    public Compra(MedioPago medioPago, LocalDateTime fecha,List<Entrada> entradas, CuponCliente cuponCliente, Funcion funcion, Cliente cliente, List<CompraConfiteria> compraConfiterias) {
        this.medioPago = medioPago;
        this.fecha = fecha;
        this.entradas = entradas;
        this.cuponCliente = cuponCliente;
        this.funcion = funcion;
        this.cliente = cliente;
        this.compraConfiterias = compraConfiterias;
    }
}
