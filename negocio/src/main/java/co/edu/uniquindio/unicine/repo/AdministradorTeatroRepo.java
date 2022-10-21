package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.AdministradorTeatro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorTeatroRepo extends JpaRepository<AdministradorTeatro,Integer> {

    @Query("select a from AdministradorTeatro a where a.correo = :correo and a.password = :password")
    AdministradorTeatro comprobarAutenticacion(String correo, String password);

    @Query("select a from AdministradorTeatro a where a.codigo = :codigo")
    AdministradorTeatro obtenerAdministradorTeatro(String codigo);


}
