package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.DistribucionSillas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DistribucionSillasRepo extends JpaRepository<DistribucionSillas,Integer> {

    @Query("select d from DistribucionSillas d where d.columnas = :columna and d.esquema = :esquema and d.filas = :fila and d.totalSillas = :totalSilla")
    DistribucionSillas comprobarDistribucion(Integer columna, String esquema, Integer fila, Integer totalSilla);
}
