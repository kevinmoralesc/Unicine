package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Cliente extends Persona implements Serializable {

    @ElementCollection
    @Column(nullable = false)
    private Map<String, String> imagenes;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;
    @Column(nullable = false)
    private boolean estado;
    @ElementCollection
    private List<String> telefonos;

    @ToString.Exclude
    @OneToMany(mappedBy = "cliente")
    private List<Compra> compras;

    @ToString.Exclude
    @OneToMany(mappedBy = "cliente")
    private List<CuponCliente> cuponClientes;

    @Column(nullable = false)
    private String urlFoto;


    @Builder
    public Cliente(String nombre, String correo, String password, boolean estado, List<String> telefonos,LocalDate fechaNacimiento) {
        super(nombre, correo, password);
        this.fechaNacimiento = fechaNacimiento;
        this.estado = false;
        this.telefonos = telefonos;
    }

    public String getImagenPrincipal(){

        if (!imagenes.isEmpty()){
            String primera = imagenes.keySet().toArray()[0].toString();
            return imagenes.get(primera);
        }

        return"";

    }
}
