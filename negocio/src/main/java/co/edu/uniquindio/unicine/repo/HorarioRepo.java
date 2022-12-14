package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface HorarioRepo extends JpaRepository<Horario,Integer> {

    @Query("select h from Horario h where h.dia = :dia and h.hora = :hora")
    Horario comprobarExistencia(String dia, String hora);
}
