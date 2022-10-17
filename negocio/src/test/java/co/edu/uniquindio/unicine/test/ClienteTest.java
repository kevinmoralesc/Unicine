package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.Cupon;
import co.edu.uniquindio.unicine.entidades.CuponCliente;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import org.aspectj.apache.bcel.util.ClassPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteTest {

    @Autowired
    private ClienteRepo clienteRepo;

    @Test
    @Sql("classpath:dataset.sql")
    public void registrar(){
        ArrayList<String> telefonos = new ArrayList<>();
        telefonos.add("312219");
        Cliente cliente = new Cliente("Pepito","pepe@email.com","123","ruta",true,telefonos);
        Cliente guardado = clienteRepo.save(cliente);
        Assertions.assertNotNull(guardado);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminar(){
        Cliente buscado = clienteRepo.findById(1).orElse(null);
        clienteRepo.delete(buscado);
        Assertions.assertNull(clienteRepo.findById(1).orElse(null));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizar(){
        Cliente buscado = clienteRepo.findById(4).orElse(null);
        buscado.setCorreo("pepe_nuevo@email.com");
        Cliente clienteNuevo = clienteRepo.save(buscado);
        Assertions.assertEquals("pepe_nuevo@email.com", clienteNuevo.getCorreo());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtener(){
        Optional <Cliente> buscado = clienteRepo.findById(4);
        Assertions.assertNotNull(buscado.orElse(null));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listar(){
        List<Cliente> lista = clienteRepo.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPorCorreo(){
       Cliente cliente = clienteRepo.findByCorreo("luis@email.com");
       Assertions.assertNotNull(cliente);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void comprobarAutenticacion(){
        Cliente cliente = clienteRepo.findByCorreoAndPassword("luis@email.com","efe34");
        Assertions.assertNotNull(cliente);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void paginador(){
        List<Cliente> clientes = clienteRepo.findAll(PageRequest.of(0,3)).toList();
        clientes.forEach(System.out::println);

    }
    @Test
    @Sql("classpath:dataset.sql")
    public void paginadorEstado(){
        List<Cliente> clientes = clienteRepo.obtenerPorEstado(true,PageRequest.of(0,3));
        clientes.forEach(System.out::println);

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void ordenarRegistros(){
        List<Cliente> clientes = clienteRepo.findAll(PageRequest.of(0,3,Sort.by("nombre"))).toList();
        clientes.forEach(System.out::println);

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerCompras(){
        List<Compra> compras = clienteRepo.obtenerCompras("pepe@email.com");
        compras.forEach(System.out::println);

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerCupones(){
        List<CuponCliente> cupones = clienteRepo.obtenerCuponesJoin("pepe@email.com");
        Assertions.assertEquals(2,cupones.size());

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerComprasPorCliente(){
        List<Object[]> compras = clienteRepo.obtenerComprasTodos();
        compras.forEach( o ->
                System.out.println(o[0] +",  "+ o[1] +",  "+ o[2])

        );

    }



}
