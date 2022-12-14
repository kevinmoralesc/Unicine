package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.dto.FuncionDTO;
import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.entidades.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FuncionRepo extends JpaRepository<Funcion,Integer> {
    @Query("select f.pelicula.nombre from Funcion f where f.codigo = :codigoFuncion")
    String obtenerNombrePelicula(Integer codigoFuncion);
    @Query("select distinct f.pelicula from Funcion f ")
    List<Pelicula> obtenerPeliculas();
    @Query("select new co.edu.uniquindio.unicine.dto.FuncionDTO(f.pelicula.nombre, f.sala.codigo, f.sala.teatro.direccion, f.sala.teatro.ciudad.nombre , f.horario) from Funcion f where f.pelicula.codigo = :codigoPelicula")
    List<FuncionDTO> listaFunciones(Integer codigoPelicula);
    @Query("select f from Funcion f where f.sala.teatro.codigo = :codigoTeatro and f.compras is empty ")
    List<Funcion> obtenerFuncionesSinCompra(Integer codigoTeatro);
    @Query("select f from Funcion f where f.sala.teatro.codigo = :codigoTeatro and f.horario.fechaInicio < :fechaInicio or f.horario.fechaFin > :fechaFin ")
    List<Funcion> obtenerFuncionesTeatro(Integer codigoTeatro, LocalDate fechaInicio, LocalDate fechaFin);
    @Query("select f from Funcion f where f.horario = :horario and f.sala = :sala")
    Funcion comprobarFuncion(Horario horario, Sala sala);





}
