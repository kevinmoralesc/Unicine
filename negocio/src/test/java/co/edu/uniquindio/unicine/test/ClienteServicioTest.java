package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import co.edu.uniquindio.unicine.servicios.EmailServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ClienteServicioTest {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private EmailServicio emailServicio;

    @Test
    @Sql("classpath:dataset.sql")
    public void registrarClienteTest(){

        Cliente cliente = Cliente.builder().nombre("Juanito").password("1234").correo("juanito@email.com").urlFoto("ruta").build();
        try {
            Cliente nuevo = clienteServicio.registrarCliente(cliente);
            Assertions.assertNotNull(nuevo);
        } catch (Exception e) {
            Assertions.assertTrue(false);
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
        lista.forEach(System.out::println);
    }

    @Test
    public void enviarCorreoTest(){
        emailServicio.enviarEmail("Prueba de envío","Este es un mensaje", "kevinmoralesc1234@gmail.com");

    }






}
