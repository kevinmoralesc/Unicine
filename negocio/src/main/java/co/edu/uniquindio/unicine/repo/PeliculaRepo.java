package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.dto.HorarioSalaDTO;
import co.edu.uniquindio.unicine.dto.PeliculaFuncion;
import co.edu.uniquindio.unicine.entidades.EstadoPelicula;
import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Genero;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import java.util.List;

@Repository
public interface PeliculaRepo extends JpaRepository<Pelicula,Integer> {

    @Query("select p from Pelicula p where p.nombre like concat('%',:nombre,'%')")
    List<Pelicula> buscarPelicula(String nombre);

    @Query("select p from Pelicula p where p.nombre like concat('%',:nombre,'%') and p.estado = :estado ")
    List<Pelicula> buscarPeliculaEstado(String nombre, Boolean estado);

    @Query("select p from Pelicula p where p.nombre like concat('%',:nombre,'%') and :genero member of p.generos")
    List<Pelicula> buscarPeliculaGenero(String nombre, Genero genero);

    @Query("select new co.edu.uniquindio.unicine.dto.HorarioSalaDTO(f.horario, f.sala)  from Pelicula p join p.funciones f where p.codigo = :codigoPelicula and f.sala.teatro.codigo = :codigoTeatro")
    List<HorarioSalaDTO> listarHorarios(Integer codigoPelicula, Integer codigoTeatro);

    @Query("select p from Pelicula p join p.generos g where g = :generoPelicula order by p.nombre asc ")
    List<Pelicula> listarPeliculas(Genero generoPelicula);

    @Query("select p from Pelicula p where :generoPelicula member of p.generos order by p.nombre asc ")
    List<Pelicula> listarPeliculas2(Genero generoPelicula);

    @Query("select distinct f.pelicula from Funcion f where f.sala.teatro.ciudad.codigo = :codigoCiudad and f.pelicula.estado = :estadoPelicula")
    List<Pelicula> listarPeliculasEstado(Integer codigoCiudad, EstadoPelicula estadoPelicula);

    @Query("select distinct f.pelicula from Funcion f where f.pelicula.estado = :estadoPelicula")
    List<Pelicula> listarPeliculasEstado(EstadoPelicula estadoPelicula);

    @Query("select distinct f from Funcion f where f.sala.teatro.ciudad.codigo = :codigoCiudad and f.pelicula.codigo= :codigoPelicula")
    List<Funcion> listarPeliculaCiudad(Integer codigoCiudad, Integer codigoPelicula);

    @Query("select new co.edu.uniquindio.unicine.dto.PeliculaFuncion(p, f) from Pelicula p left join p.funciones f where p.nombre like concat('%',:nombre,'%') ")
    List<PeliculaFuncion> buscarPeliculas(String nombre);
    @Query("select p from Pelicula p where p.nombre = :nombrePelicula")
    Optional<Pelicula> obtenerPeliculaNombre(String nombrePelicula);

}