package co.edu.uniquindio.unicine.entidades;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;


@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Persona implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false,length = 40)
    @Length(max = 40)
    private String nombre;

    @NotNull
    @Email
    @Column(nullable = false,length = 200)
    @Length(max = 200)
    private String correo;

    @ToString.Exclude
    @Column(nullable = false,length = 250)
    @Length(max = 250)
    private String password;


    public Persona(String nombre, String correo, String password) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
    }
}
