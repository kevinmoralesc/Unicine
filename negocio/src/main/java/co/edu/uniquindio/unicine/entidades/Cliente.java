package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente extends Persona implements Serializable {

    @Column(nullable = false,length = 30)
    private String nombre;

    @Column(nullable = false)
    private String urlFoto;

    @Column(nullable = false)
    private String estado;

    @ElementCollection
    private List<String> telefonos;

    @OneToMany(mappedBy = "cliente")
    private List<Compra> compra;

    @OneToMany(mappedBy = "cliente")
    private List<CuponCliente> cuponClientes;



}
