package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteTest {

    @Autowired
    private ClienteRepo clienteRepo;

    @Test
    public void registrar(){

        ArrayList<String> telefonos = new ArrayList<>();
        telefonos.add("312219");
        Cliente cliente = new Cliente("Pepito","pepe@email.com","123","ruta","Activo",telefonos);
        Cliente guardado = clienteRepo.save(cliente);

        Assertions.assertNotNull(guardado);
    }

    @Test
    public void eliminar(){

        ArrayList<String> telefonos = new ArrayList<>();
        telefonos.add("312219");
        Cliente cliente = new Cliente("Pepito","pepe@email.com","123","ruta","Activo",telefonos);
        cliente.setCodigo(1);
        Cliente guardado = clienteRepo.save(cliente);

        clienteRepo.delete(cliente);

        Optional <Cliente> buscado = clienteRepo.findById(1);

        Assertions.assertNull(buscado.orElse(null));
    }

    @Test
    public void actualizar(){
        ArrayList<String> telefonos = new ArrayList<>();
        telefonos.add("312219");
        Cliente cliente = new Cliente("Pepito","pepe@email.com","123","ruta","Activo",telefonos);
        cliente.setCodigo(1);
        Cliente guardado = clienteRepo.save(cliente);

        guardado.setCorreo("pepe_nuevo@email.com");

        Cliente clienteNuevo = clienteRepo.save(guardado);

        Assertions.assertEquals("pepe_nuevo@email.com", clienteNuevo.getCorreo());

    }

    @Test
    public void obtener(){

        ArrayList<String> telefonos = new ArrayList<>();
        telefonos.add("312219");
        Cliente cliente = new Cliente("Pepito","pepe@email.com","123","ruta","Activo",telefonos);

        Cliente guardado = clienteRepo.save(cliente);


        Optional <Cliente> buscado = clienteRepo.findById(12);

        System.out.println(buscado.orElse(null));

    }

    @Test
    public void listar(){

        ArrayList<String> telefonos = new ArrayList<>();
        telefonos.add("312219");
        Cliente cliente = new Cliente("Pepito","pepe@email.com","123","ruta","Activo",telefonos);

        clienteRepo.save(cliente);

        Cliente cliente1 = new Cliente("luis","luis@email.com","123","ruta","Activo",telefonos);
        clienteRepo.save(cliente1);

        List<Cliente> lista = clienteRepo.findAll();
        System.out.println(lista);

    }

}
