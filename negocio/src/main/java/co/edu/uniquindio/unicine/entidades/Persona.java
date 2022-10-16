package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Persona implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @Column(nullable = false,length = 15)
    private Integer codigo;

    //@Column(nullable = false,length = 40)
    //private String nombre;

    @Column(nullable = false,length = 25)
    private String correo;

    @Column(nullable = false,length = 20)
    private String password;


}
