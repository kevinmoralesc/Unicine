package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.dto.FuncionDTO;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.repo.FuncionRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FuncionTest {

    @Autowired
    private FuncionRepo funcionRepo;

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPeliculaFuncion() {

        String nombrePelicula = funcionRepo.obtenerNombrePelicula(3);
        Assertions.assertEquals("El conjuro",nombrePelicula);

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPeliculas(){

        List<Pelicula> peliculas = funcionRepo.obtenerPeliculas();
        peliculas.forEach(System.out::println);


    }


    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerFuncion() {

        List<FuncionDTO> funcion = funcionRepo.listaFunciones(1);
        funcion.forEach(System.out::println);

    }
}
