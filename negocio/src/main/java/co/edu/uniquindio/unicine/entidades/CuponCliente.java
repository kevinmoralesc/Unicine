package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CuponCliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(nullable = false)
    private Boolean estado;

    @ToString.Exclude
    @OneToOne(mappedBy = "cuponCliente")
    private Compra compra;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cupon cupon;

    public CuponCliente(Boolean estado, Compra compra, Cliente cliente, Cupon cupon) {
        this.estado = estado;
        this.compra = compra;
        this.cliente = cliente;
        this.cupon = cupon;
    }
}
