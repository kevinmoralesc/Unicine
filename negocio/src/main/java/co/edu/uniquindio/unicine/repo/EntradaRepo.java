package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaRepo extends JpaRepository<Entrada,Integer> {

    @Query("select e.compra from Entrada e where e = :entrada ")
    Compra obtenerCompra (Entrada entrada);
}
