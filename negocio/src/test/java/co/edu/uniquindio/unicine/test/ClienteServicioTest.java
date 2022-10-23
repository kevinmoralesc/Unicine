package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import co.edu.uniquindio.unicine.servicios.EmailServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class ClienteServicioTest {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private EmailServicio emailServicio;

    @Autowired
    private AdminServicio adminServicio;

    //-------------------------------------------- Gestion Cliente ---------------------------------------------
    @Test
    @Sql("classpath:dataset.sql")
    public void registrarClienteTest(){

        Cliente cliente = Cliente.builder().nombre("Juanito").password("1234").correo("juanito@email.com").urlFoto("ruta").build();
        try {
            Cliente nuevo = clienteServicio.registrarCliente(cliente);
            System.out.println(cliente);
            Assertions.assertNotNull(nuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarClienteTest(){

        try {
            Cliente cliente = clienteServicio.obtenerCliente(1);
            cliente.setNombre("Nuevo nombre");
            Cliente nuevo = clienteServicio.actualizarCliente(cliente);
            Assertions.assertEquals("Nuevo nombre", nuevo.getNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void activarCuentaClienteTest(){

        try {

            Cliente cliente = clienteServicio.obtenerCliente(3);
            clienteServicio.activarCuentaCliente(cliente);

            Assertions.assertEquals(true,cliente.isEstado());

            Cupon cuponRegristro = adminServicio.obtenerCupon(4);
            CuponCliente cuponCliente = CuponCliente.builder().estado(true).cliente(cliente).cupon(cuponRegristro).build();
            clienteServicio.asisgnarCupon(cliente.getCodigo(),cuponCliente);
            cliente.getCuponClientes().forEach(System.out::println);
            Assertions.assertEquals(2,cliente.getCuponClientes().size());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void cambiarPassword()  {

        try {
            Cliente cliente = clienteServicio.obtenerCliente(1);
            clienteServicio.cambiarPassword(cliente.getCodigo(),"1234569","1dsd34");
            Assertions.assertEquals("1234569",cliente.getPassword());
        }catch (Exception e){

            throw new RuntimeException(e);

        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarClienteTest(){

        try {
            clienteServicio.eliminarCliente(1);

        } catch (Exception e) {
            Assertions.assertTrue(false);
        }

        try{
            Cliente cliente = clienteServicio.obtenerCliente(1);
        }catch (Exception e){

            Assertions.assertTrue(true);
        }

    }
    @Test
    @Sql("classpath:dataset.sql")
    public void listarClienteTest(){
        List<Cliente> lista = clienteServicio.listarCliente();
        Assertions.assertEquals(6,lista.size());
    }

    @Test
    public void enviarCorreoTest(){
        emailServicio.enviarEmail("Prueba de envío","Este es un mensaje", "kevinmoralesc1234@gmail.com");

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void buscarPeliculaGenero(){

        List<Pelicula> peliculas = clienteServicio.buscarPelicula("Harry Potter");
        Assertions.assertEquals(2,peliculas.size());

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void buscarPelicula(){

        List<Pelicula> peliculas = clienteServicio.buscarPeliculaGenero("Harry Potter", Genero.CIENCIA_FICCION);
        Assertions.assertEquals(2,peliculas.size());

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void loginTest(){
        try {
            Cliente cliente = clienteServicio.login("luis@email.com","efe34");
            Assertions.assertNotNull(cliente);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void historialComprasTest() {

        try {
            List<Compra> compras = clienteServicio.listarHistorial(1);
            Assertions.assertEquals(2,compras.size());

        }catch (Exception e){

            throw new RuntimeException(e);

        }


    }

}
