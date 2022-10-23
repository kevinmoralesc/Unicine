package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Sala;
import co.edu.uniquindio.unicine.entidades.Teatro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepo extends JpaRepository<Sala,Integer> {
    @Query("select s from Sala s where s.teatro = :teatro and s.nombre = :nombre")
    Sala comprobarSala(Teatro teatro, String nombre);
}
