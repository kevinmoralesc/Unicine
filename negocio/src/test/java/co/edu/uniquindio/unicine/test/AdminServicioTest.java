package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
public class AdminServicioTest {

    @Autowired
    private AdminServicio adminServicio;

    @Test
    @Sql("classpath:dataset.sql")
    public void cambiarPassword()  {

        try {
            adminServicio.cambiarPassword("kevinmoralesc1234@gmail.com");

        }catch (Exception e){

            throw new RuntimeException(e);

        }

    }

    //------------------------------------------------- Ciudad -------------------------------------------------

    @Test
    @Sql("classpath:dataset.sql")
    public void crearCiudadTest(){

        Ciudad ciudad = Ciudad.builder().nombre("Armenia").build();
        Ciudad nueva = adminServicio.crearCiudad(ciudad);
        Assertions.assertEquals(nueva,ciudad);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obternerCiudadTest(){

        try {
            Ciudad ciudad = adminServicio.obtenerCiudad(1);
            Assertions.assertTrue(false);
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCiudadesTest(){

        List<Ciudad> ciudades = adminServicio.listarCiudades();
        Assertions.assertEquals(5,ciudades.size());


    }

    //---------------------------------------------- Pelicula --------------------------------------------------

    @Test
    @Sql("classpath:dataset.sql")
    public void crearPeliculaTest(){

        Genero[] generos = {Genero.ACCION,Genero.CIENCIA_FICCION};
        Pelicula pelicula = Pelicula.builder().nombre("Interstellar").generos(Arrays.asList(generos)).reparto("Nombres").estado(true).sinopsis("travesia espacial").urlImagen("ruta").urlTrailer("ruta").build();
        Pelicula nueva = adminServicio.crearPelicula(pelicula);
        Assertions.assertEquals(nueva,pelicula);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obternerPeliculaTest(){

        try {
            Pelicula pelicula = adminServicio.obtenerPelicula(1);

        } catch (Exception e) {
            Assertions.assertTrue(false);
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarPeliculaTest(){

        try {
            Pelicula pelicula = adminServicio.obtenerPelicula(1);
            pelicula.setNombre("Nuevo nombre");
            Pelicula nueva = adminServicio.actualizarPelicula(pelicula);
            Assertions.assertEquals("Nuevo nombre", nueva.getNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarPeliculaTest() {

        try {
            adminServicio.eliminarPelicula(1);

        } catch (Exception e) {
            Assertions.assertTrue(false);
        }

        try {
            Pelicula cupon = adminServicio.obtenerPelicula(1);

        } catch (Exception e) {

            Assertions.assertTrue(true);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarPeliculasTest(){

       List<Pelicula> peliculas = adminServicio.listarPeliculas();
       Assertions.assertEquals(6,peliculas.size());

    }
    //---------------------------------------------- Cupon ------------------------------------------------------

    @Test
    @Sql("classpath:dataset.sql")
    public void crearCuponTest(){

        Cupon cupon = Cupon.builder().criterio("").descripcion("Nuevo cliente").descuento(15F).fechaVencimiento(LocalDate.parse("2022-10-10")).build();
        Cupon nuevo = adminServicio.crearCupon(cupon);
        Assertions.assertEquals(nuevo,cupon);


    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obternerCuponTest(){

        try {
            Cupon cupon = adminServicio.obtenerCupon(1);

        } catch (Exception e) {
            Assertions.assertTrue(false);
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarCuponTest(){

        try {
            Cupon cupon = adminServicio.obtenerCupon(2);
            cupon.setDescripcion("Nueva descripcion");
            Cupon nuevo = adminServicio.actualizarCupon(cupon);
            Assertions.assertEquals("Nueva descripcion", nuevo.getDescripcion());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarCuponTest() {

        try {
            adminServicio.eliminarCupon(2);

        } catch (Exception e) {
            Assertions.assertTrue(false);
        }

        try {
            Cupon cupon = adminServicio.obtenerCupon(2);

        } catch (Exception e) {

            Assertions.assertTrue(true);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCuponesTest(){

        List<Cupon> cupones = adminServicio.listarCupones();
        Assertions.assertEquals(5,cupones.size());

    }
    //---------------------------------------------- Confiteria --------------------------------------------------

    @Test
    @Sql("classpath:dataset.sql")
    public void crearConfiteriaTest(){

        Confiteria confiteria = Confiteria.builder().nombre("Crispetas").precio(1000.0).url_imagen("ruta").build();
        Confiteria nueva = adminServicio.crearConfiteria(confiteria);
        Assertions.assertEquals(nueva,confiteria);

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obternerConfiteriaTest(){

        try {
            Confiteria confiteria = adminServicio.obtenerConfiteria(1);

        } catch (Exception e) {
            Assertions.assertTrue(false);
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarConfiteriaTest(){

        try {
            Confiteria confiteria = adminServicio.obtenerConfiteria(2);
            confiteria.setNombre("Nuevo nombre");
            Confiteria nueva = adminServicio.actualizarConfiteria(confiteria);
            Assertions.assertEquals("Nuevo nombre", nueva.getNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarConfiteriaTest() {

        try {
            adminServicio.eliminarConfiteria(2);

        } catch (Exception e) {
            Assertions.assertTrue(false);
        }

        try {
            Confiteria confiteria = adminServicio.obtenerConfiteria(2);

        } catch (Exception e) {

            Assertions.assertTrue(true);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarConfiteriaTest(){

        List<Confiteria> confiterias = adminServicio.listarConfiteria();
        Assertions.assertEquals(7,confiterias.size());

    }

    //---------------------------------------------- AdministradorTeatro ----------------------------------------------


    @Test
    @Sql("classpath:dataset.sql")
    public void crearAdministradorTeatroTest(){

        AdministradorTeatro administradorTeatro = new AdministradorTeatro("andres","andres@email.com","4444");
        AdministradorTeatro nuevo = adminServicio.crearAdminTeatro(administradorTeatro);
        Assertions.assertEquals(nuevo,administradorTeatro);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obternerAdminTeatroTest(){

        try {
            AdministradorTeatro administradorTeatro = adminServicio.obtenerAdminTeatro(3);

        } catch (Exception e) {
            Assertions.assertTrue(false);
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarAdminTeatroTest(){

        try {
            AdministradorTeatro administradorTeatro = adminServicio.obtenerAdminTeatro(3);
            administradorTeatro.setNombre("Nuevo nombre");
            AdministradorTeatro nuevo = adminServicio.actualizarAdminTeatro(administradorTeatro);
            Assertions.assertEquals("Nuevo nombre", nuevo.getNombre());
            System.out.println(nuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarAdminTeatroTest() {

        try {
            adminServicio.eliminarAdminTeatro(3);

        } catch (Exception e) {
            Assertions.assertTrue(false);
        }

        try {
            Confiteria confiteria = adminServicio.obtenerConfiteria(2);

        } catch (Exception e) {

            Assertions.assertTrue(true);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarAdminsTeatrosTest(){

        List<AdministradorTeatro> administradoresTeatros = adminServicio.listarAdminsTeatro();
        Assertions.assertEquals(5,administradoresTeatros.size());

    }


}
