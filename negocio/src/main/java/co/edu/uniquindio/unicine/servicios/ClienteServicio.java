package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClienteServicio {

    Cliente obtenerCliente(Integer codigoCliente)throws Exception;

    Cliente login(String correo, String password)throws Exception;

    Cliente registrarCliente(Cliente cliente) throws Exception;

    Cliente actualizarCliente(Cliente cliente) throws Exception;

    void eliminarCliente(Integer codigoCliente) throws Exception;

    List<Cliente> listarCliente();

    List<Compra> listarHistorial(Integer codigoCliente) throws Exception;

    Compra hacerCompra(Compra compra) throws Exception;

    boolean redirCupon(Integer codigoCupon) throws Exception;
}
